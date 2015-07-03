package com.hutu.longchang.fragment;

import org.json.JSONException;
import org.json.JSONObject;
import com.hutu.longchang.R;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;
import com.hutu.longchang.util.Util;
import com.hutu.longchang.widget.ProgressDialogView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ShowToast") public class RegisterFragment extends BaseFragment implements NetWorkCallBack{
	private EditText eAccount;
	private EditText ePassword;
	private EditText eRePassowrd;
	private Button registerBtn;
	private EditText mYanzhengtxt;
	private Button mYanzhengBtn;
	private String code = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_register, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onPrevious() {
		showFragment(new LoginFragment(), Constant.TAG_USERLOGIN);
		super.onPrevious();
	}
	@Override
	public void init() {
		eAccount = (EditText)mView.findViewById(R.id.register_edit_account);
		ePassword = (EditText)mView.findViewById(R.id.register_edit_password);
		eRePassowrd = (EditText)mView.findViewById(R.id.register_edit_password2);
		registerBtn = (Button) mView.findViewById(R.id.register_bt_register);
		mYanzhengtxt = (EditText) mView.findViewById(R.id.register_yanzheng_et);
		mYanzhengBtn = (Button) mView.findViewById(R.id.register_yanzheng_btn);
		setTitle("注册");
	}

	@Override
	public void initListener() {
		registerBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				registerClick();
			}
		});
		mYanzhengBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				yanzhengClick();
			}
		});
	}
	
	public void yanzhengClick(){
		String account = eAccount.getText().toString().trim();
		String password = ePassword.getText().toString().trim();
		String rePassword = eRePassowrd.getText().toString().trim();
		String msg = null;
		if (account.length() != 11) {
			msg = "账号格式不正确";
		} else if(password.length() < 4 || password.length() > 16) {
			msg = "密码长度请需要4-16位";
		} else if(!password.equals(rePassword)) {
			msg = "两次输入的密码不一致";
		}
		if (msg != null) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
			dialog.setMessage(msg);
			dialog.setTitle("提示");
			dialog.setPositiveButton("确认", null);
			dialog.setNegativeButton("取消", null);
			dialog.show();
		} else {
			Request request = new Request(mActivity, this);
			code = Util.registerCode();
			System.out.println(code);
			ProgressDialogView.getInstance(mActivity).show();
			request.setRegisterCode(account,code);
		}
	}
	public void registerClick() {
		String account = eAccount.getText().toString().trim();
		String password = ePassword.getText().toString().trim();
		String rePassword = eRePassowrd.getText().toString().trim();
		String yanZheng = mYanzhengtxt.getText().toString().trim();
		String msg = null;
		
		if (account.length() != 11) {
			msg = "账号格式不正确";
		} else if(password.length() < 4 || password.length() > 16) {
			msg = "密码长度请需要4-16位";
		} else if(!password.equals(rePassword)) {
			msg = "两次输入的密码不一致";
		} else if(TextUtils.isEmpty(yanZheng)){
			msg = "验证码不能为空";
		} else if(yanZheng.length() != 6){
			msg = "验证码为4位数字";
		} else if(!yanZheng.equals(code)){
			msg = "验证码输入错误";
		}
		
		if (msg != null) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
			dialog.setMessage(msg);
			dialog.setTitle("提示");
			dialog.setPositiveButton("确认", null);
			dialog.setNegativeButton("取消", null);
			dialog.show();
		} else {
			// 注册请求
			ProgressDialogView.getInstance(mActivity).show();
			Request req = new Request(mActivity, this);
			req.setRegisterUser(account, password);
		}
	}

	@SuppressLint("ShowToast") @Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		ProgressDialogView.getInstance(mActivity).dismiss();
		if(flag == Request.getReqeustFlag(Request.HostName + "?method=SetRegisterUser")){
			try {
				JSONObject jsonobj = new JSONObject(msg);
				if("1".equals(jsonobj.getString("result"))){
					AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
					dialog.setMessage("注册成功");
					dialog.setTitle("提示");
					dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface paramDialogInterface, int paramInt) {
							showFragment(new LoginFragment(), Constant.TAG_USERLOGIN);
						}
					});
					dialog.show();
				}else{
					AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
					dialog.setMessage("账号已存在");
					dialog.setTitle("提示");
					dialog.setPositiveButton("确认", null);
					dialog.setNegativeButton("取消", null);
					dialog.show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			if ("1".equals(msg)){
				Toast.makeText(mActivity, "验证码获取成功", 0);
			}
		}
		
	}

}
