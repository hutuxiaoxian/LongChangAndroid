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

public class DetaisGonggaoAdapter extends BaseAdapter{

	private ArrayList<HashMap<String, String>> mList = null;
	private Context mContext;
	public DetaisGonggaoAdapter(ArrayList<HashMap<String, String>> list,Context context){
		this.mList = list;
		this.mContext = context;
	}
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodel hodel = null;
		if(null == convertView){
			hodel = new ViewHodel();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_detailgonggao_item, null);
			hodel.gonggaoTitle = (TextView) convertView.findViewById(R.id.detailgonggao_txt_title);
			hodel.gonggaoMsg = (TextView) convertView.findViewById(R.id.dtailgonggao_txt_neirong);
			convertView.setTag(hodel);
		}else{
			hodel = (ViewHodel) convertView.getTag();
		}
		if(mList != null && mList.size() > 0){
			HashMap<String, String> map = mList.get(position);
			hodel.gonggaoTitle.setText(map.get("Matters"));
			hodel.gonggaoMsg.setText(map.get("TrialNum"));
		}
		return convertView;
	}
	
	class ViewHodel{
		TextView gonggaoTitle,gonggaoMsg;
	}

}
