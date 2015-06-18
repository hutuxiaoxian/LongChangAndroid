package com.hutu.longchang.widget;

import com.hutu.longchang.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HeadTitleView  extends RelativeLayout{



	public final static int mRgiteVisbel = 1;
	public final static int mLeftVisbel = 2;
	
	private Context mContext;
	private Button mLeftBtn,mRegitBtn;

	private TextView mTitleName;
	
	public HeadTitleView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}
	public HeadTitleView(Context context, AttributeSet attrs){
		super(context,attrs);
		this.mContext = context;
		init();
	}
	
	public HeadTitleView(Context context , AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		this.mContext = context;
		init();
		
	}
	private void init(){
		LayoutInflater.from(mContext).inflate(R.layout.view_titlebar, this);
		mLeftBtn = (Button) this.findViewById(R.id.titlebar_back);
		mRegitBtn = (Button) this.findViewById(R.id.titlebar_action);
		mTitleName = (TextView) this.findViewById(R.id.titlebar_text);
	}
	
	public void setTitle(String titleName){
		mTitleName.setText(titleName);
	}
	public void setVisible(int visible){
		switch (visible) {
		case mRgiteVisbel:
			mRegitBtn.setVisibility(View.GONE);
			break;
		case mLeftVisbel:
			mLeftBtn.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
}
