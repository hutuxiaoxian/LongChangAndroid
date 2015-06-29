package com.hutu.longchang.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.DetailsActivity;
import com.hutu.longchang.adapter.DetaisGonggaoAdapter;
import com.hutu.longchang.adapter.DetaisLiuchengAdapter;
import com.hutu.longchang.model.Commodityd;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;

public class DetaisgonggaoFragment extends BaseFragment implements NetWorkCallBack{

	private ListView mListView = null;
	private Request request = new Request(mActivity, this);
	private Commodityd comm = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mActivity instanceof DetailsActivity) {
			Intent intent = mActivity.getIntent();
			comm = (Commodityd) intent.getSerializableExtra("commond");
		}
		request.bullHzGonggao(comm.getRegNo());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_detailgonggao, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onPrevious() {
		// TODO Auto-generated method stub
		super.onPrevious();
		mActivity.finish();
	}
	@Override
	public void init() {
		mListView = (ListView) mView.findViewById(R.id.detailgonggao_list);
	}

	@Override
	public void initListener() {
	
	}
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		if(0 == respCode){
			if(flag == Request.getReqeustFlag(Request.HostName
					+ "?method=SBBulletinHzList")){
				try {
					JSONArray jsonarray = new JSONArray(msg);
					ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
					for (int i = 0; i < jsonarray.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						JSONObject jsonobject = jsonarray.getJSONObject(i);
						map.put("Matters", jsonobject.getString("Matters"));
						map.put("TrialNum", jsonobject.getString("TrialNum"));
						list.add(map);
					}
					DetaisGonggaoAdapter adapter = new DetaisGonggaoAdapter(list, mActivity);
					mListView.setAdapter(adapter);
					/*
					 * Matters
					 * TrialNum
					 */
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}

}
