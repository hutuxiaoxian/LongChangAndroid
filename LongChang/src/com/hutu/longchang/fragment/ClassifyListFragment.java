package com.hutu.longchang.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.ClassifyListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ClassifyListFragment extends BaseFragment {

	private ArrayList<HashMap<String, String>> arr;
	private ListView mList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_classifylist, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	

	@Override
	public void init() {
		setTitle("国际分类");
		mList = (ListView) mView.findViewById(R.id.classifylist_list);
	}

	@Override
	public void onPrevious() {
		super.onPrevious();
		if(getArguments().getInt("type") == 0){
			showFragment(new SearchSameFragment(), Constant.TAG_SEARCHSAME);
		}
		if(getArguments().getInt("type") == 1){
			showFragment(new SearchComplexFragment(), Constant.TAG_SEARCHCOMPLEX);
		}
	}
	@Override
	public void initListener() {
		arr = (ArrayList<HashMap<String, String>>) getArguments().getSerializable("data");
		ArrayList<String> arrStr = new ArrayList<String>();
		for(HashMap<String, String> item : arr){
			arrStr.add(item.get("DetailName"));
		}
		mList.setAdapter(new ArrayAdapter<String>(mActivity, R.layout.spinner_item,arrStr));

		mList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> ada, View v, int i, long n) {
				//intent.putExtra("data", arr.get(i).get("classify"));
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", arr.get(i).get("classify"));
				if(getArguments().getInt("type") == 0){
					BaseFragment fragment = new SearchSameFragment();
					fragment.setArguments(bundle);
					showFragment(fragment, Constant.TAG_SEARCHSAME);
				}
				if(getArguments().getInt("type") == 1){
					BaseFragment fragment = new SearchComplexFragment();
					fragment.setArguments(bundle);
					showFragment(fragment, Constant.TAG_SEARCHCOMPLEX);
				}
				
			}
		});
	}

}
