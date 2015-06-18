package com.hutu.longchang.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MainPagerAdapter extends PagerAdapter {

	private ArrayList<View> mList;
	public MainPagerAdapter(ArrayList<View> list) {
		mList = list;
		
	}
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public boolean isViewFromObject(View v, Object obj) {
		return false;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView(mList.get(position));
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager)container).addView(mList.get(position));
		return mList.get(position);
	}
	
}
