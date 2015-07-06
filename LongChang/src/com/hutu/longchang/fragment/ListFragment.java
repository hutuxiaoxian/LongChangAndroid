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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.DetailsActivity;
import com.hutu.longchang.adapter.ListAdapter;
import com.hutu.longchang.model.Commodityd;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;
import com.hutu.longchang.widget.MyListView;
import com.hutu.longchang.widget.OnRefreshListener;

public class ListFragment extends BaseFragment implements NetWorkCallBack, OnItemClickListener, OnRefreshListener{

	private ArrayList<HashMap<String ,String>> mList = null; 
	private MyListView vList = null;
	private int search = 0;
	private String superType;
	private int countPage = 0;
	
	private int start,end;
	private HashMap<String, String> mRequest;
	private ListAdapter adapter;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		search = bundle.getInt("searchclass");
		superType = bundle.getString("type");
		countPage = bundle.getInt("count");
		if (countPage < 40){
			end = countPage;
		} else {
			end = 40;
		}
		mRequest = (HashMap<String, String>)bundle.getSerializable("request");
		start = 21;
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
		if ("same".equals(superType)) {
			showFragment(new SearchSameFragment(), Constant.TAG_SEARCHSAME);
		} else {
			showFragment(new SearchComplexFragment(), Constant.TAG_SEARCHCOMPLEX);
		}
		super.onPrevious();
	}
	@Override
	public void init() {
		setTitle("查询列表");
		vList = (MyListView)mView.findViewById(R.id.list_list);
		vList.setOnRefreshListener(this);
//		int[] to = new int[]{R.id.listitem_text_id, R.id.listitem_text_class, R.id.listitem_text_brand};
//		String[] from = new String[]{LISTITEM_KEY_ID, LISTITEM_KEY_CLASS, LISTITEM_KEY_BRAND};
		adapter = new ListAdapter(mList, mActivity);
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
		Intent intent = new Intent(mActivity,DetailsActivity.class);
		intent.putExtra("commond", comm);
		intent.putExtra("search", search);
		startActivity(intent);
//		
//		BaseFragment fragment = new DetaisFragment();
//		fragment.setArguments(bundle);
//		showFragment(fragment, Constant.TAG_DETAIS);
		
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
		vList.hideFooterView();
		if (respCode == 0) {
			try {
				JSONObject jsonObj = new JSONObject(msg);
				JSONArray json = jsonObj.getJSONArray("data");
				int count = jsonObj.getInt("total");
				start = end + 1;
				if (end + 20 > count) {
					end = count;
				} else {
					end += 20;
				}
				if(json.length() > 0 ){
					for(int i= 0;i< json.length() ;i++){
						JSONObject item = json.getJSONObject(i);
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("regNo", item.getString("RegNO"));
						map.put("classify", item.getString("IntCls"));
						String sbmc = item.has("SBMC")?item.optString("SBMC"):item.getString("TMCN")+item.getString("TMEN")+item.getString("TMZT");
						map.put("name", sbmc);
						mList.add(map);
					}
					adapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void onDownPullRefresh() {
		// TODO Auto-generated method stub
		System.out.println("...........");
	}
	@Override
	public void onLoadingMore() {
		// TODO Auto-generated method stub
		Request req = new Request(mActivity, this);
		if ("complex".equals(superType)) {
			req.zongHeInfos(mRequest.get("type"), mRequest.get("RegNO"), mRequest.get("Sbmc"), mRequest.get("applicant"), mRequest.get("Intcls"), start, end);
		} else if ("same".equals(superType)) {
			req.jinsichaxun(mRequest.get("TabNum"), mRequest.get("typeName"), mRequest.get("type"), start, end, Integer.parseInt(mRequest.get("jingmo")));
		}
	}

	
}
