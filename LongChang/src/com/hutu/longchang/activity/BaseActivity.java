package com.hutu.longchang.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.hutu.longchang.R;

@SuppressLint("NewApi") 
public class BaseActivity extends FragmentActivity {

	public Resources mRes = null;
	public Activity mActivity = null;
	public FragmentManager mFragmentManager = null;
	public View mView = null;
	/**
	 * 添加fragment
	 * @param fragment
	 * @param id
	 * @param tag
	 */
	public void addFragment(Fragment fragment, int id, String tag) {
		FragmentTransaction fragmentTrans = mFragmentManager.beginTransaction();
		fragmentTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTrans.add(id, fragment, tag);
		fragmentTrans.addToBackStack(null);
		fragmentTrans.commit();
	}
	/**
	 * 删除fragment
	 * @param tag
	 */
	public void removeFragment(String tag) {
		FragmentTransaction fragmentTrans = mFragmentManager.beginTransaction();
		Fragment fragment = mFragmentManager.findFragmentByTag(tag);
		if(null != fragment && fragment.isAdded()) {
			fragmentTrans.remove(fragment);
			mFragmentManager.popBackStack();
			fragmentTrans.commit();
		}
	}
	@SuppressLint("HandlerLeak") 
	protected Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			
		};
	};
	
	
	public void setTitle(String title){
		TextView txt = (TextView)findViewById(R.id.titlebar_text);
		txt.setText(title);
	}
	
	public void setTitle(int title){
		TextView txt = (TextView)findViewById(R.id.titlebar_text);
		txt.setText(title);
	}
	
	public void backClick(View v){
		finish();
	}
	
	public void showBackBt(){
		showBackBt(true);
	}
	public void showBackBt(boolean isShow){
		Button back = (Button)findViewById(R.id.titlebar_back);
		back.setVisibility(isShow?1:0);
		back.setClickable(isShow);
	}
	
	public void actionClick(View v){
		
	}
	
}
