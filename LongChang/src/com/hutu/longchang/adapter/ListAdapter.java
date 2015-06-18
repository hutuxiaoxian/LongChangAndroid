package com.hutu.longchang.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.hutu.longchang.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private ArrayList<HashMap<String, String>> mList;
	private Context mContext;
	public ListAdapter(ArrayList<HashMap<String, String>> list,Context context) {
		this.mList = list;
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(null == convertView){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_list, null);
			holder.name = (TextView) convertView.findViewById(R.id.text_brand);
			holder.classify = (TextView) convertView.findViewById(R.id.list_text_class);
			holder.regNo = (TextView) convertView.findViewById(R.id.list_text_id);
			holder.id = (TextView) convertView.findViewById(R.id.list_text_index);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(mList != null && mList.size() > 0){
			HashMap<String, String> map = mList.get(position);
			holder.name.setText(map.get("name"));
			holder.classify.setText(map.get("classify"));
			holder.regNo.setText(map.get("regNo"));
			holder.id.setText(String.valueOf(position + 1));
		}
		return convertView;
	}
	
	class ViewHolder{
		TextView regNo;
		TextView classify;
		TextView name;
		TextView id;
	}

}
