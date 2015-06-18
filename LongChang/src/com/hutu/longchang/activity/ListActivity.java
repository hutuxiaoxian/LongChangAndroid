package com.hutu.longchang.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hutu.longchang.R;
import com.hutu.longchang.model.NetWorkCallBack;
/**
 * 列表
 * @author hutu
 *
 */
public class ListActivity extends BaseActivity implements NetWorkCallBack, OnItemClickListener{

	private final static String LISTITEM_KEY_ID			 = "regNo";
	private final static String LISTITEM_KEY_CLASS		 = "classify";
	private final static String LISTITEM_KEY_BRAND		 = "name";
	
	private ListView vList;
	private ArrayList<HashMap<String, String>> dList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_list);
		
		dList = (ArrayList<HashMap<String,String>>)getIntent().getSerializableExtra("data");
		
		initView();
		
		
	}
	
	/**
	 * 初始化界面
	 */
	private void initView(){
		
		vList = (ListView)findViewById(R.id.list_list);
		int[] to = new int[]{R.id.listitem_text_id, R.id.listitem_text_class, R.id.listitem_text_brand};
		String[] from = new String[]{LISTITEM_KEY_ID, LISTITEM_KEY_CLASS, LISTITEM_KEY_BRAND};
		vList.setAdapter(new SimpleAdapter(getBaseContext(), dList, R.layout.listitem_list, from, to));
		
		vList.setOnItemClickListener(this);
	}
	
	private void getData(){
		// 获取数据
	}
	
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int i, long paramLong) {
		
	}
}
