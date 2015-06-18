package com.hutu.longchang.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.MainActivity;
import com.hutu.longchang.adapter.ListAdapter;
import com.hutu.longchang.model.Commodityd;
import com.hutu.longchang.model.NetWorkCallBack;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ListFragment extends BaseFragment implements NetWorkCallBack, OnItemClickListener{

	private ArrayList<HashMap<String ,String>> mList = null; 
	private ListView vList = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		mList = (ArrayList<HashMap<String, String>>) bundle.getSerializable("data");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_list, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onPrevious() {
		showFragment(new MainFragment(), Constant.TAG_MAIN);
		super.onPrevious();
	}
	@Override
	public void init() {
		vList = (ListView)mView.findViewById(R.id.list_list);
//		int[] to = new int[]{R.id.listitem_text_id, R.id.listitem_text_class, R.id.listitem_text_brand};
//		String[] from = new String[]{LISTITEM_KEY_ID, LISTITEM_KEY_CLASS, LISTITEM_KEY_BRAND};
		ListAdapter adapter = new ListAdapter(mList, mActivity);
		vList.setAdapter(adapter);
		
	}

	@Override
	public void initListener() {
		vList.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int paramInt, long paramLong) {
		Commodityd comm = returnComm(paramInt);
		Bundle bundle = new Bundle();
		bundle.putSerializable("commond", comm);
		BaseFragment fragment = new DetaisFragment();
		fragment.setArguments(bundle);
		showFragment(fragment, Constant.TAG_DETAIS);
		
	}
	
	public Commodityd returnComm(int paramInt){
		Commodityd dityd = new Commodityd();
		HashMap<String, String> map = (HashMap<String, String>) mList.get(paramInt);
		dityd.setIntCls(map.get("classify"));
		dityd.setRegNo(map.get("regNo"));
		dityd.setTmcn(map.get("name"));
		dityd.setTmen(map.get("TMEN"));
		dityd.setAppDate(map.get("AppDate"));
		dityd.setRegDate(map.get("RegDate"));
		dityd.setTrialDate(map.get("TrialDate"));
		dityd.setInterRegDate(map.get("InterRegDate"));
		dityd.setValidDate(map.get("ValidDate"));
		dityd.setTrialNum(map.get("TrialNum"));
		dityd.setRegNum(map.get("RegNum"));
		dityd.setTmApplicant(map.get("TMApplicant"));
		dityd.setTmAddress(map.get("TMAddress"));
		dityd.setTmAgent(map.get("TMAgent"));
		dityd.setTmDetail(map.get("TMDetail"));
		dityd.setSimilarGroup(map.get("SimilarGroup"));
		dityd.setTmType(map.get("TMType"));
		dityd.setIsTotal(map.get("ISTotal"));
		dityd.setTmAreaNum(map.get("TMAreaNum"));
		dityd.setTmRemark(map.get("TMRemark"));
		dityd.setTmStatus(map.get("TMStatus"));
		dityd.setTmTY(map.get("TMTY"));
		dityd.setTmDY(map.get("TMDY"));
		return dityd;
//		comm.setRegNo(map.get("regNo"));
//		comm.setmap.get("classify");
//		map.get("name");
//		map.get("TMEN");
//		map.get("AppDate");
//		map.get("RegDate");
//		map.get("TrialDate");
//		map.get("InterRegDate");
//		map.get("ValidDate");
//		map.get("TrialNum");
//		map.get("RegNum");
//		map.get("TMApplicant");
//		map.get("TMAddress");
//		map.get("TMAgent");
//		map.get("TMDetail");
//		map.get("SimilarGroup");
//		map.get("TMType");
//		map.get("ISTotal");
//		map.get("TMAreaNum");
//		map.get("TMRemark");
//		map.get("TMStatus");
//		map.get("TMTY");
//		map.get("TMDY");
	}
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		// TODO Auto-generated method stub
		
	}

}
