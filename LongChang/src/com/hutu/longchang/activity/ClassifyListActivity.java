package com.hutu.longchang.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hutu.longchang.R;

public class ClassifyListActivity extends BaseActivity{

	private ArrayList<HashMap<String, String>> arr;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_classifylist);
		
		arr = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("data");
		ArrayList<String> arrStr = new ArrayList<String>();
		for(HashMap<String, String> item : arr){
			arrStr.add(item.get("DetailName"));
		}
		ListView list = (ListView)findViewById(R.id.classifylist_list);
		list.setAdapter(new ArrayAdapter<String >(this, R.layout.spinner_item, arrStr));
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> ada, View v, int i,
					long n) {
				Intent intent = new Intent();
				intent.putExtra("data", arr.get(i).get("classify"));
				ClassifyListActivity.this.setResult(0xF1, intent);
				finish();
			}
		});
	}
}
