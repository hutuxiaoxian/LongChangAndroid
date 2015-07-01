package com.hutu.longchang.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.hutu.longchang.R;
import com.hutu.longchang.fragment.BaseFragment;
import com.hutu.longchang.fragment.Constant;
import com.hutu.longchang.fragment.DetaisFragment;
import com.hutu.longchang.fragment.ErWeiFragment;
import com.hutu.longchang.fragment.ListFragment;
import com.hutu.longchang.fragment.LoginFragment;
import com.hutu.longchang.fragment.MainFragment;
import com.hutu.longchang.model.Commodityd;
import com.hutu.longchang.model.JSONArrayExt;
import com.hutu.longchang.model.JSONObjectExt;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;
import com.hutu.longchang.util.SharedPreferencesUtil;
import com.hutu.longchang.widget.HeadTadderView;
import com.imageco.library.CaptureActivity;

/**
 * 首页
 * 
 * @author hutu
 * 
 */
@SuppressLint("UseValueOf")
public class MainActivity extends BaseActivity implements NetWorkCallBack {

	private ArrayList<Commodityd> arrayList = new ArrayList<Commodityd>();
	private Button shouyeBtn, huiyuanBtn, shaomiaoBtn;
	public static final int SCAN_CODE = 0;
	private HeadTadderView tadderView = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		initlister();
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.main_relatlayout, new LoginFragment(),
						Constant.TAG_USERLOGIN).commit();
		// showBackBt(false);
		// getSupportFragmentManager().beginTransaction().add(R.id.fragment, new
		// LoginFragment(), Constant.TAG_USERLOGIN).commit();
	}

	private void init() {
		tadderView = (HeadTadderView) findViewById(R.layout.view_tabbar);
		shouyeBtn = (Button) findViewById(R.id.pager_bt_main);
		shaomiaoBtn = (Button)findViewById(R.id.pager_bt_scan);
		huiyuanBtn = (Button)findViewById(R.id.pager_bt_account);
	}

	private void initlister() {
		shaomiaoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(TextUtils.isEmpty(SharedPreferencesUtil.getInstance(MainActivity.this).loadUserName())){
					return;
				}
				Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
				startActivityForResult(intent, SCAN_CODE);
			}
		});
		shouyeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				if(TextUtils.isEmpty(SharedPreferencesUtil.getInstance(MainActivity.this).loadUserName())){
					Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
				}else{
					getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.main_relatlayout, new MainFragment(),
							Constant.TAG_USERLOGIN).commit();
				}
				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String result = null;
//		if (resultCode == 200) {
//			String code = data.getStringExtra("qrCode");
//			Request req = new Request(this, this);
//			code = "0000001";
//			req.getBarCodeInfo(code);
//			return;
//		}
		if(resultCode == 200){
//			if (resultCode == RESULT_OK) {
			Request req = new Request(this, this);
			result = data.getStringExtra("qrCode");
			req.getBarCodeInfo("0000001");
			System.out.println("条码++++++++++++" + result);
//			}else if (resultCode == RESULT_CANCELED) {
//				result = "没有扫描出任何东西";
//			}
//			ErWeiFragment mEwframent = new ErWeiFragment();
//			Bundle bundle = new Bundle();
//			bundle.putString("result", result);
//			mEwframent.setArguments(bundle);
//			getSupportFragmentManager()
//			.beginTransaction()
//			.replace(R.id.main_relatlayout, mEwframent,
//					Constant.TAG_ERWEI).commit();
			
			
//			BaseFragment fragment = new ListFragment();
//			Bundle bundle = new Bundle();
//			bundle.putSerializable("data", arrData);
//			fragment.setArguments(bundle);
//			showFragment(fragment, Constant.TAG_SEARCHLIST);
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(
					MainActivity.this);
			dialog.setTitle("提示");
			dialog.setMessage("确定要退出程序吗？");
			dialog.setPositiveButton("确认",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							MainActivity.this.finish();
							if(!TextUtils.isEmpty(SharedPreferencesUtil.getInstance(MainActivity.this).loadUserName())){
								SharedPreferencesUtil.getInstance(MainActivity.this).SharedPreferencesCler();
								System.exit(0);
							}
							
						}
					});
			dialog.setNegativeButton("取消", null);
			dialog.show();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		if (respCode == 0) {
			if (msg.length() == 0 || "1".equals(msg)) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setMessage("没有找到相关数据");
				dialog.setTitle("提示");
				dialog.setPositiveButton("确认",null);
				dialog.setNegativeButton("取消", null);
				dialog.show();
			} else {
				try {
					System.out.println("msg" + msg);
					JSONArray jsonArry = new JSONArray(msg);
					JSONObject json = jsonArry.getJSONObject(0);
					Commodityd dityd = new Commodityd();
					dityd.setIntCls(json.getString("IntCls"));
					dityd.setRegNo(json.getString("RegNO"));
					dityd.setTmcn(json.getString("TMCN"));
					dityd.setTmen(json.getString("TMEN"));
					dityd.setAppDate(json.getString("AppDate"));
					dityd.setRegDate(json.getString("RegDate"));
					dityd.setTrialDate(json.getString("TrialDate"));
					dityd.setInterRegDate(json.getString("InterRegDate"));
					dityd.setValidDate(json.getString("ValidDate"));
					dityd.setTrialNum(json.getString("TrialNum"));
					dityd.setRegNum(json.getString("RegNum"));
					dityd.setTmApplicant(json.getString("TMApplicant"));
					dityd.setTmAddress(json.getString("TMAddress"));
					dityd.setTmAgent(json.getString("TMAgent"));
					dityd.setTmDetail(json.getString("TMDetail"));
					dityd.setSimilarGroup(json.getString("SimilarGroup"));
					dityd.setTmType(json.getString("TMType"));
					dityd.setIsTotal(json.getString("ISTotal"));
					dityd.setTmAreaNum(json.getString("TMAreaNum"));
					dityd.setTmRemark(json.getString("TMRemark"));
					dityd.setTmStatus(json.getString("TMStatus"));
					dityd.setTmTY(json.getString("TMTY"));
					dityd.setTmDY(json.getString("TMDY"));
					//System.out.println("dsfdsfdsfdsfdsf" + dityd.getIntCls()+dityd.getRegNo()+dityd.getTmAddress());
					DetaisFragment fragment = new DetaisFragment();
					Bundle bundle = new Bundle();
					bundle.putSerializable("commond", dityd);
					fragment.setArguments(bundle);
					getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.main_relatlayout, fragment,
							Constant.TAG_DETAIS).commit();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * 
				 * [{"IntCls":"01"
				 * ,"RegNO":"10003107"
				 * ,"TMCN":"洪发"
				 * ,"TMEN":"HONG FA"
				 * ,"TMZT":""
				 * ,"TMSZ":""
				 * ,"AppDate":"2011-09-26"
				 * ,"RegDate":"2012-11-21"
				 * ,"InterRegDate":""
				 * ,"TrialDate":"2012-08-20"
				 * ,"ValidDate":"2022-11-20"
				 * ,"TrialNum":"1324"
				 * ,"RegNum":"1336"
				 * ,"TMApplicant":"榆树市宏发建筑材料厂"
				 * ,"TMAddress":"吉林省榆树市榆三公路第一小学西侧"
				 * ,"TMAgent":"吉林省思宇商标事务有限公司"
				 * ,"TMDetail":"粘胶液；粘接剂(冶金)；塑料胶；墙砖粘合剂；工业用胶；聚氨酯；氯丁胶；聚醋酸乙烯乳液；非办公、非家用淀粉浆糊(胶粘剂)；非文具、非家具用胶水"
				 * ,"SimilarGroup":"0115"
				 * ,"TMType":"普通商标"
				 * ,"ISTotal":""
				 * ,"TMAreaNum":"220182"
				 * ,"TMRemark":""
				 * ,"TMStatus":""
				 * ,"TMTY":"hongfa"
				 * ,"TMDY":"[hongfa]"}]
				 *
				 */
			}
		}
	}

}
