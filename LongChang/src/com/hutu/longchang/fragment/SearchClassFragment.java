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
import android.widget.Button;
import android.widget.EditText;

public class SearchClassFragment extends BaseFragment implements NetWorkCallBack{
	private Button searchBtn;
	private EditText mText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_search_class, null);	
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onPrevious() {
		super.onPrevious();
		showFragment(new MainFragment(), Constant.TAG_MAIN);
	}
	@Override
	public void init() {
		setTitle("状态查询");
		searchBtn = (Button) mView.findViewById(R.id.searc_class_bt_name);
		mText = (EditText) mView.findViewById(R.id.searc_class_edit_name);
	}

	@Override
	public void initListener() {
		searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String context = mText.getText().toString();
				context = context.trim();
				if(context.length() == 0){
					AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
					dialog.setTitle("提示");
					dialog.setMessage("请输入查询关键字");
					dialog.setPositiveButton("确认",null);
					dialog.setNegativeButton("取消",null);
					dialog.show();
				}else{
					ProgressDialogView.getInstance(mActivity).show();
					Request req = new Request(mActivity,SearchClassFragment.this);
					req.stateList(context);
				}
			}
		});
	}
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		ProgressDialogView.getInstance(mActivity).dismiss();
		if(respCode == 0){
			if(flag == Request.getReqeustFlag(Request.HostName + "?method=SBStateList")){
				try {
					JSONArray json = new JSONArray(msg);
					if(json.length() > 0 ){
						ArrayList<HashMap<String ,String>>arrData = new ArrayList<HashMap<String ,String>>();
						for(int i= 0;i< json.length() ;i++){
							JSONObject obj = json.getJSONObject(i);
							HashMap<String ,String> map = new HashMap<String, String>();
							map.put("regNo", obj.getString("RegNO"));
							map.put("classify", obj.getString("IntCls"));
							map.put("name", obj.getString("TMCN"));
							map.put("TMEN",obj.getString("TMEN"));
							map.put("AppDate",obj.getString("AppDate"));
							map.put("RegDate",obj.getString("RegDate"));
							map.put("TrialDate",obj.getString("TrialDate"));
							map.put("InterRegDate",obj.getString("InterRegDate"));
							map.put("ValidDate",obj.getString("ValidDate"));
							map.put("TrialNum",obj.getString("TrialNum"));
							map.put("RegNum",obj.getString("RegNum"));
							map.put("TMApplicant",obj.getString("TMApplicant"));
							map.put("TMAddress",obj.getString("TMAddress"));
							map.put("TMAgent",obj.getString("TMAgent"));
							map.put("TMDetail",obj.getString("TMDetail"));
							map.put("SimilarGroup",obj.getString("SimilarGroup"));
							map.put("TMType",obj.getString("TMType"));
							map.put("ISTotal",obj.getString("ISTotal"));
							map.put("TMAreaNum",obj.getString("TMAreaNum"));
							map.put("TMRemark",obj.getString("TMRemark"));
							map.put("TMStatus",obj.getString("TMStatus"));
							map.put("TMTY",obj.getString("TMTY"));
							map.put("TMDY",obj.getString("TMDY"));
							arrData.add(map);
						}
						BaseFragment fragment = new ListFragment();
						Bundle bundle = new Bundle();
						bundle.putSerializable("data", arrData);
						bundle.putInt("searchclass", 1);
						fragment.setArguments(bundle);
						showFragment(fragment, Constant.TAG_SEARCHLIST);
						
//						Intent intent = new Intent(this, ListActivity.class);
//						intent.putExtra("data", arrData);
//						startActivity(intent);
					}else{
						AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
						dialog.setTitle("提示");
						dialog.setMessage("没有找到相关数据");
						dialog.setPositiveButton("确认",null);
						dialog.setNegativeButton("取消",null);
						dialog.show();
					}
					return;
				} catch (JSONException e) {
					e.printStackTrace();
				}

				AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
				dialog.setTitle("提示");
				dialog.setMessage("参数不正确");
				dialog.setPositiveButton("确认",null);
				dialog.setNegativeButton("取消",null);
				dialog.show();
			}
		}
	}

}
