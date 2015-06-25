package com.hutu.longchang.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.BaseActivity;
import com.hutu.longchang.activity.MainActivity;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;
import com.hutu.longchang.util.SharedPreferencesUtil;
import com.hutu.longchang.widget.ProgressDialogView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends BaseFragment implements NetWorkCallBack {

	private EditText mUserPhone_et, mPassword_et;
	private Button mSubmit_Btn;
	private Button register_btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_login, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void init() {
		mUserPhone_et = (EditText) mView.findViewById(R.id.login_edit_account);
		mPassword_et = (EditText) mView.findViewById(R.id.login_edit_password);
		mSubmit_Btn = (Button) mView.findViewById(R.id.login_bt_login);
		register_btn = (Button) mView.findViewById(R.id.login_bt_register);
		setTitle("登录");
		if(!TextUtils.isEmpty(SharedPreferencesUtil.getInstance(mActivity).UserNameload())){
			mUserPhone_et.setText(SharedPreferencesUtil.getInstance(mActivity).UserNameload());
		}
	}

	@Override
	public void initListener() {
		mSubmit_Btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String account = mUserPhone_et.getText().toString().trim();
				String password = mPassword_et.getText().toString().trim();
				String msg = null;
				if (account.length() != 11) {
					msg = "账号格式不正确";
				} else if (password.length() < 4 || password.length() > 16) {
					msg = "密码长度请需要4-16位";
				}
				if (msg != null) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(
							mActivity);
					dialog.setMessage(msg);
					dialog.setTitle("提示");
					dialog.setPositiveButton("确认", null);
					dialog.setNegativeButton("取消", null);
					dialog.show();
				} else {
					ProgressDialogView.getInstance(mActivity).show();
					Request req = new Request(mActivity, LoginFragment.this);
					req.getLognUser(account, password);
				}
			}
		});
		register_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				showFragment(new RegisterFragment(), Constant.TAG_USERREGISTER);
			}
		});
		
//		mUserPhone_et.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View arg0, MotionEvent arg1) {
//				mUserPhone_et.setInputType(InputType.TYPE_CLASS_NUMBER);
//				return false;
//			}
//		});
//		mPassword_et.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View arg0, MotionEvent arg1) {
//				mPassword_et.setInputType(InputType.TYPE_CLASS_NUMBER);
//				return false;
//			}
//		});
	}

	@Override
	public void onPrevious() {
		super.onPrevious();
		mActivity.finish();
		System.exit(0);
	}

	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		System.out.println(msg);
		try {
			ProgressDialogView.getInstance(mActivity).dismiss();
			JSONObject jsonObject = new JSONObject(msg);
			String userlogin = jsonObject.getString("result");
			if ("1".equals(userlogin)) {
				userName = jsonObject.getString("UserName");
				SharedPreferencesUtil.getInstance(mActivity).saveUserName(userName);
				SharedPreferencesUtil.getInstance(mActivity).userNamesave(userName);
				userId = jsonObject.getString("UserID");
				showFragment(new MainFragment(), Constant.TAG_MAIN);
			} else if ("0".equals(userlogin)) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
				dialog.setTitle("提示");
				dialog.setMessage("用户名或密码错误");
				dialog.setPositiveButton("确认", null);
				dialog.setNegativeButton("取消", null);
				dialog.show();
			}else if("2".equals(userlogin)){
				AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
				dialog.setTitle("提示");
				dialog.setMessage("您还有没VIP，或者VIP已经过期");
				dialog.setPositiveButton("去购买", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface, int paramInt) {
						showFragment(new RechargeFragment(), Constant.TAG_USERCHONG);
					}
				});
				dialog.show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
