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

public class ShopAdapter extends BaseAdapter{

	private ArrayList<HashMap<String, String>> mList = null;
	private Context mContext;
	
	public ShopAdapter(ArrayList<HashMap<String, String>> mapList , Context context){
		mContext = context;
		mList = mapList;
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
		ViewHolder holder = null;
		if(null == convertView){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_shop_item, null);
			holder.intclsTxt = (TextView) convertView.findViewById(R.id.intcls_txt);
			holder.detailtypeTxt = (TextView) convertView.findViewById(R.id.detailtype_txt);
			holder.detailNameTxt = (TextView) convertView.findViewById(R.id.detailname_txt);
			convertView.setTag(holder);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(mList != null && mList.size() > 0){
			HashMap<String, String> map = mList.get(position);
			holder.intclsTxt.setText(map.get("intcls"));
			holder.detailtypeTxt.setText(map.get("DetailType"));
			holder.detailNameTxt.setText(map.get("DetailName"));
		}
		return convertView;
	}
	
	class ViewHolder{
		TextView intclsTxt;
		TextView detailtypeTxt;
		TextView detailNameTxt;
	}

}
