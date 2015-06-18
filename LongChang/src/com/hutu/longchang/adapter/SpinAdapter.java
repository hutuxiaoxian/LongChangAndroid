package com.hutu.longchang.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.hutu.longchang.R;

public class SpinAdapter extends BaseAdapter implements SpinnerAdapter {
	
	String[] arrData;
	Context mContext;
	View v ;
	public SpinAdapter(Context context, String[] data){
		arrData = data;
		mContext = context;
		v = LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		
	}
	
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	@Override
	public boolean hasStableIds() {
		return false;
	}
	
	@Override
	public int getViewTypeCount() {
		return arrData.length;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
//			convertView = v.findViewById(R.id.text1);
//			convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
		
		((TextView)convertView).setText(arrData[position]);
		
		return convertView;
	}
	
	@Override
	public int getItemViewType(int position) {
		return position;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public Object getItem(int position) {
		return arrData[position];
	}
	
	@Override
	public int getCount() {
		return arrData.length;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
//			convertView = v.findViewById(R.id.text1);;
//			convertView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
		((TextView)convertView).setText(arrData[position]);
		
		return convertView;
	}
}
