package com.hutu.longchang.widget;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.BaseActivity;

public class HeadTadderView extends RelativeLayout{

	private Button shouyeBtn, huiyuanBtn, shaomiaoBtn;
	private Context mContext;
	public HeadTadderView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	public HeadTadderView(Context context, AttributeSet attrs){
		super(context,attrs);
		this.mContext = context;
		init();
	}
	
	public HeadTadderView(Context context , AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		this.mContext = context;
		init();
		
	}
	private void init(){
		LayoutInflater.from(mContext).inflate(R.layout.view_tabbar, this);
		shouyeBtn = (Button) this.findViewById(R.id.pager_bt_main);
		shaomiaoBtn = (Button) this.findViewById(R.id.pager_bt_scan);
		huiyuanBtn = (Button) this.findViewById(R.id.pager_bt_account);
	}
	
}
