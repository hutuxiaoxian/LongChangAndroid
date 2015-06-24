package com.hutu.longchang.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.hutu.longchang.R;
import com.hutu.longchang.adapter.ShopAdapter;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;
import com.hutu.longchang.widget.MyListView;
import com.hutu.longchang.widget.OnRefreshListener;

public class ShopListFragment extends BaseFragment implements NetWorkCallBack,
		OnRefreshListener {

	private MyListView mListView;
	private ImageView imageView;
	private EditText searTxt;
	private ShopAdapter adapter;
	private ArrayList<HashMap<String, String>> mapList  = new ArrayList<HashMap<String, String>>();
	private Request request = new Request(mActivity, ShopListFragment.this);
	private int start,end = 0 ;
	private String name = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_shoplist, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void init() {
		imageView = (ImageView) mView.findViewById(R.id.image_view);
		mListView = (MyListView) mView.findViewById(R.id.serach_list);
		searTxt = (EditText) mView.findViewById(R.id.edit_sear_name);
		start = 1;
		end = 20;
	}

	@Override
	public void initListener() {
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(mListView.getCount() > 0){
					mapList.clear();
				}
				name = searTxt.getText().toString().trim();
				start = 1;
				end = 20;
				if (TextUtils.isEmpty(searTxt.getText().toString().trim())) {
					Toast.makeText(mActivity, "请输入查询关键字", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				request.getDetailInfo(name,start,end);
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int item = arg2;
				System.out.println("itemid+++++++++" + item);
				
				if(mapList.size() + 1 != arg2){
					Bundle bundle = new Bundle();
					BaseFragment fragment = null;
					bundle.putSerializable("data", mapList.get(item).get("intcls"));
					if (getArguments().getInt("type") == 0) {
						fragment = new SearchSameFragment();
						fragment.setArguments(bundle);
						showFragment(fragment, Constant.TAG_SEARCHSAME);
					}
					if (getArguments().getInt("type") == 1) {
						fragment = new SearchComplexFragment();
						fragment.setArguments(bundle);
						showFragment(fragment, Constant.TAG_SEARCHCOMPLEX);
					}
				}
			}
		});
	}

	@Override
	public void onPrevious() {
		super.onPrevious();
		if (getArguments().getInt("type") == 0) {
			showFragment(new SearchSameFragment(), Constant.TAG_SEARCHSAME);
		}
		if (getArguments().getInt("type") == 1) {
			showFragment(new SearchComplexFragment(),
					Constant.TAG_SEARCHCOMPLEX);
		}
	}

	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		if (flag == Request.getReqeustFlag(Request.HostName
				+ "?method=GetDetailInfo")) {
			try {
				JSONObject jsonObject = new JSONObject(msg);
				JSONArray json = jsonObject.getJSONArray("data");
				if (json.length() > 0) {
					for (int i = 0; i < json.length(); i++) {
						JSONObject obj = json.getJSONObject(i);
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("DetailName", obj.getString("DetailName"));
						map.put("Row", obj.getString("Row"));
						map.put("DetailType", obj.getString("DetailType"));
						map.put("intcls", obj.getString("intcls"));
						mapList.add(map);
					}
					if(start == 1 && end == 20){
						adapter = new ShopAdapter(mapList, mActivity);
						mListView.setAdapter(adapter);
						mListView.setOnRefreshListener(this);
					}
					adapter.notifyDataSetChanged();
					// 控制脚布局隐藏
					mListView.hideFooterView();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onDownPullRefresh() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				SystemClock.sleep(2000);
				//上拉刷新
//				if(mapList.size() > 0){
//					adapter = new shopAdapter(mapList, mActivity);
//					mListView.setAdapter(adapter);
//				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
//				adapter.notifyDataSetChanged();
//				mListView.hideHeaderView();
			}
		}.execute(new Void[] {});
	}

	@Override
	public void onLoadingMore() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				//下拉加载
//				SystemClock.sleep(2000);
				if(!TextUtils.isEmpty(name)){
					request.getDetailInfo(name,start = start + 20,end = end + 20);
				}
//				HashMap<String, String> map = new HashMap<String, String>();
//				map.put("DetailName", "DetailName");
//				map.put("Row", "Row");
//				map.put("DetailType", "DetailType");
//				map.put("intcls", "intcls");
//				mapList.add(map);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				
			}
		}.execute(new Void[] {});
	}
}
