package com.hutu.longchang.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.ListActivity;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;
import com.hutu.longchang.widget.ProgressDialogView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchComplexFragment extends BaseFragment implements
		NetWorkCallBack {

	private EditText eClassify;
	private EditText eRegister;
	private EditText eName;
	private EditText ePeople;
	private Spinner spinner;
	private Button searchBtn;
	private Button info_Btn;

	String[] arrData = new String[] { "精确" ,"模糊"};
	ArrayList<HashMap<String, String>> classifyData = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_search_complex, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onPrevious() {
		super.onPrevious();
		showFragment(new MainFragment(), Constant.TAG_MAIN);
	}

	@Override
	public void init() {
		setTitle("综合查询");
		ProgressDialogView.getInstance(mActivity).show();
		Request req = new Request(mActivity, this);
		req.fenLeiListAll();
		eClassify = (EditText) mView
				.findViewById(R.id.search_comple_edit_classify);
		// eClassify = (Spinner)findViewById(R.id.search_comple_edit_classify);
		eRegister = (EditText) mView
				.findViewById(R.id.search_comple_edit_register);
		eName = (EditText) mView.findViewById(R.id.search_comple_edit_name);
		ePeople = (EditText) mView.findViewById(R.id.search_comple_edit_people);
		spinner = (Spinner) mView.findViewById(R.id.search_comple_spinner);
		ArrayAdapter<String> ada = new ArrayAdapter<String>(mActivity,
				R.layout.spinner_item, arrData);
		spinner.setAdapter(ada);
		searchBtn = (Button) mView.findViewById(R.id.search_comple_bt_search);
		
		info_Btn = (Button) mView.findViewById(R.id.bangzhu_btn);
		Bundle bundle = new Bundle();
		bundle = getArguments();
		if(bundle != null){
			String getClassify = bundle.getSerializable("data").toString();
			eClassify.setText(getClassify);
		}
	}

	@Override
	public void initListener() {
		info_Btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				Bundle bundle = new Bundle();
//				bundle.putSerializable("data", classifyData);
				bundle.putInt("type", 1);
				BaseFragment fragment = new ShopListFragment();
				fragment.setArguments(bundle);
				showFragment(fragment, Constant.TAG_SHOPLIST);
			}
		});
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Request req = new Request(mActivity, SearchComplexFragment.this);
				// HashMap<String ,String> map = classifyData.get((int)
				// eClassify.getSelectedItemId());
				// String classify = map.get("classify");
				String classify = eClassify.getText().toString();
				String reg = eRegister.getText().toString();
				reg = reg.trim();
				String name = eName.getText().toString();
				name = name.trim();
				String people = ePeople.getText().toString();
				people = people.trim();

				String lei = "" + spinner.getSelectedItemId();
				String msg =  null;
				if(name.length() != 0 && people.length() != 0){
					msg = "商品名称与申请人只能有一个";
				}
				if(reg.length() == 0){
					if(name.length() == 0 && people.length() == 0){
						msg = "注册号商品名称与申请人至少输入一个";
					}
				}else{
					if(name.length() != 0 && people.length() != 0){
						msg = "商品名称与申请人只输入一个";
					}
				}
				if(msg != null){
					AlertDialog.Builder dialog = new AlertDialog.Builder(
							mActivity);
					dialog.setMessage(msg);
					dialog.setTitle("提示");
					dialog.setPositiveButton("确认", null);
					dialog.setNegativeButton("取消", null);
					dialog.show();	
				} else {
					ProgressDialogView.getInstance(mActivity).show();
//					req.zongHeInfo(classify, reg, name, people, null);
					req.zongHeInfos(lei, reg, name, people, classify, 1, 20);
				}
			}
		});
	}

	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		ProgressDialogView.getInstance(mActivity).dismiss();
		if (respCode == 0) {
			if (flag == Request.getReqeustFlag(Request.HostName
					+ "?method=SBZongHeInfo")) {
				try {
					JSONObject jsonObj = new JSONObject(msg);
					int count = jsonObj.getInt("total");
					JSONArray json = jsonObj.getJSONArray("data");
					if (json.length() > 0) {
						ArrayList<HashMap<String, String>> arrData = new ArrayList<HashMap<String, String>>();
						for (int i = 0; i < json.length(); i++) {
							JSONObject obj = json.getJSONObject(i);
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("regNo", obj.getString("RegNO"));
							map.put("classify", obj.getString("IntCls"));
							map.put("name", obj.getString("SBMC"));
							map.put("TMEN",obj.getString("TMEN"));
//							map.put("AppDate",obj.getString("AppDate"));
//							map.put("RegDate",obj.getString("RegDate"));
//							map.put("TrialDate",obj.getString("TrialDate"));
//							map.put("InterRegDate",obj.getString("InterRegDate"));
//							map.put("ValidDate",obj.getString("ValidDate"));
//							map.put("TrialNum",obj.getString("TrialNum"));
//							map.put("RegNum",obj.getString("RegNum"));
							map.put("TMApplicant",obj.getString("TMApplicant"));
//							map.put("TMAddress",obj.getString("TMAddress"));
//							map.put("TMDetail",obj.getString("TMDetail"));
//							map.put("SimilarGroup",obj.getString("SimilarGroup"));
//							map.put("ap.put("TMAgent",obj.getString("TMAgent"));
//							mTMType",obj.getString("TMType"));
//							map.put("ISTotal",obj.getString("ISTotal"));
//							map.put("TMAreaNum",obj.getString("TMAreaNum"));
//							map.put("TMRemark",obj.getString("TMRemark"));
//							map.put("TMStatus",obj.getString("TMStatus"));
//							map.put("TMTY",obj.getString("TMTY"));
//							map.put("TMDY",obj.getString("TMDY"));
							arrData.add(map);
						}
						BaseFragment fragment = new ListFragment();
						Bundle bundle = new Bundle();
						bundle.putInt("count", count);
						bundle.putString("type", "complex");
						bundle.putSerializable("data", arrData);
						
						String classify = eClassify.getText().toString();
						String reg = eRegister.getText().toString();
						reg = reg.trim();
						String name = eName.getText().toString();
						name = name.trim();
						String people = ePeople.getText().toString();
						people = people.trim();

						String lei = "" + spinner.getSelectedItemId();
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("type", lei);
						map.put("RegNO", reg);
						map.put("Sbmc", name);
						map.put("applicant", people);
						map.put("Intcls", classify);
						bundle.putSerializable("request", map);
						
						fragment.setArguments(bundle);
						showFragment(fragment, Constant.TAG_SEARCHLIST);
						// Intent intent = new Intent(this, ListActivity.class);
						// intent.putExtra("data", arrData);
						// startActivity(intent);
					} else {
						AlertDialog.Builder dialog = new AlertDialog.Builder(
								mActivity);
						dialog.setMessage("没有找到相关数据");
						dialog.setTitle("提示");
						dialog.setPositiveButton("确认", null);
						dialog.setNegativeButton("取消", null);
						dialog.show();
					}
					return;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
				dialog.setMessage("参数不正确");
				dialog.setTitle("提示");
				dialog.setPositiveButton("确认", null);
				dialog.setNegativeButton("取消", null);
				dialog.show();
			} else if (flag == Request.getReqeustFlag(Request.HostName
					+ "?method=SBFenLeiListAll")) {
				try {
					JSONArray json = new JSONArray(msg);
					if (json.length() > 0) {
						String arr[] = new String[json.length()];
						for (int i = 0; i < json.length(); i++) {
							JSONObject obj = json.getJSONObject(i);
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("classify", obj.getString("IntCls"));
							map.put("DetailName", obj.getString("DetailName"));
							arr[i] = obj.getString("DetailName");
							classifyData.add(map);
						}
						// ArrayAdapter<String> ada = new
						// ArrayAdapter<String>(this, R.layout.spinner_item
						// ,arr);
						// eClassify.setAdapter(ada);
						return;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
				dialog.setMessage("参数不正确");
				dialog.setTitle("提示");
				dialog.setPositiveButton("确认", null);
				dialog.setNegativeButton("取消", null);
				dialog.show();
			}
		}

	}

}
