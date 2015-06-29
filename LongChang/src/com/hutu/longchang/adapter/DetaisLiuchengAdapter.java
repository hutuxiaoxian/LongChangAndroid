package com.hutu.longchang.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import com.hutu.longchang.R;
import com.hutu.longchang.R.layout;
import com.hutu.longchang.model.Commodityd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetaisLiuchengAdapter extends BaseAdapter{

	private ArrayList<HashMap<String, String>> mList = null;
	private Context mContext;
	public DetaisLiuchengAdapter(ArrayList<HashMap<String, String>> list,Context context){
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
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHodel hodel = null;
		if(null == convertView){
			hodel = new ViewHodel();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_detailliucheng_listitem, null);
			hodel.timerTxt = (TextView) convertView.findViewById(R.id.detailliucheng_timer_txt);
			hodel.zhuangtaiTxt = (TextView) convertView.findViewById(R.id.detailliucheng_zhunangtai_txt);
			convertView.setTag(hodel);
		}else{
			hodel = (ViewHodel) convertView.getTag();
		}
		if(mList != null && mList.size() > 0){
			HashMap<String, String> map = mList.get(position);
			hodel.timerTxt.setText(map.get("timer"));
			hodel.zhuangtaiTxt.setText(map.get("msg"));
		}
		return convertView;
	}
	
	class ViewHodel{
		TextView timerTxt,zhuangtaiTxt;
	}

}
