package com.hutu.longchang.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.DetailsActivity;
import com.hutu.longchang.adapter.DetaisLiuchengAdapter;
import com.hutu.longchang.model.Commodityd;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;

public class DetaisLichengFragment extends BaseFragment implements
		NetWorkCallBack {

	private ListView mList;
	private Request request = new Request(mActivity, this);
	private Commodityd comm = null;
	private Bundle bundle = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mActivity instanceof DetailsActivity) {
			Intent intent = mActivity.getIntent();
			comm = (Commodityd) intent.getSerializableExtra("commond");
		}
		request.stateFlowInfo(comm.getIntCls(), comm.getRegNo());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_detailliucheng, null);
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
		mList = (ListView) mView.findViewById(R.id.detailiucheng_list);
		setTitle("状态查询");
	}

	@Override
	public void initListener() {

	}

	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		if (respCode == 0) {
			if (flag == Request.getReqeustFlag(Request.HostName
					+ "?method=SBStateFlowInfo")) {
				try {
					JSONArray json = new JSONArray(msg);
					if(json.length() <= 0){
						Toast.makeText(mActivity, "暂无流程信息", Toast.LENGTH_SHORT).show();
					}
					ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
					for(int i = 0;i < json.length() ; i++){
						JSONObject jsonobj = json.getJSONObject(i);
						HashMap<String, String>  map = new HashMap<String, String>();
						map.put("timer",jsonobj.getString("FlowName"));
						map.put("msg", jsonobj.getString("FlowDate"));
						list.add(map);
					}
					DetaisLiuchengAdapter adapter = new DetaisLiuchengAdapter(list, mActivity);
					mList.setAdapter(adapter);
//					String flowName = jsonArray.getString("FlowName");
//					String flowDate = jsonArray.getString("FlowDate");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
//			else{
//				
//			}
		}
	}
}
