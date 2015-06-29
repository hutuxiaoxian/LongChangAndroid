package com.hutu.longchang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hutu.longchang.R;
import com.hutu.longchang.fragment.BaseFragment;
import com.hutu.longchang.fragment.Constant;
import com.hutu.longchang.fragment.DetaisFragment;
import com.hutu.longchang.fragment.DetaisImageFragment;
import com.hutu.longchang.fragment.DetaisLichengFragment;
import com.hutu.longchang.fragment.DetaisgonggaoFragment;
import com.hutu.longchang.model.Commodityd;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;

public class DetailsActivity extends BaseActivity implements NetWorkCallBack,
		OnClickListener {
	private RelativeLayout rel1, rel2, rel3, rel4 ;
	private LinearLayout linlayout;
	private TextView mTxtWen, mTxtTu, mTxtliu, mTxtgong;
	public int search = 0;
//	private Commodityd comm = (Commodityd) mActivity.getIntent().getSerializableExtra("commond");
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_details);
		init();
		search = (Integer) getIntent().getSerializableExtra("search");
		if(search == 1){
			linlayout.setVisibility(View.GONE);
			rel3.setBackgroundColor(Color.parseColor("#EDD99F"));
			mTxtliu.setTextColor(Color.parseColor("#efefef"));
			rel1.setBackgroundColor(Color.parseColor("#000000"));
			rel2.setBackgroundColor(Color.parseColor("#000000"));
			rel4.setBackgroundColor(Color.parseColor("#000000"));
//			BaseFragment fragment = new DetaisLichengFragment();
//			Bundle bundle = new Bundle();
//			bundle.putInt("search", search);
//			fragment.setArguments(bundle);
			 getSupportFragmentManager()
			 .beginTransaction()
			 .replace(R.id.details_layout, new DetaisLichengFragment(),
			 Constant.TAG_DETAISLICHENG).commit();
		}else{
			getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.details_layout, new DetaisFragment(),
					Constant.TAG_DETAIS).commit();
		}
	}

	private void init() {
		linlayout = (LinearLayout) findViewById(R.id.liner_layout);
		rel1 = (RelativeLayout) findViewById(R.id.rells1);
		rel1.setBackgroundColor(Color.parseColor("#EDD99F"));
		rel2 = (RelativeLayout) findViewById(R.id.rells2);
		rel3 = (RelativeLayout) findViewById(R.id.rells3);
		rel4 = (RelativeLayout) findViewById(R.id.rells4);
		mTxtWen = (TextView) findViewById(R.id.txtwen);
		mTxtWen.setTextColor(Color.parseColor("#efefef"));
		mTxtTu = (TextView) findViewById(R.id.txttu);
		mTxtliu = (TextView) findViewById(R.id.txtliu);
		mTxtgong = (TextView) findViewById(R.id.txtgong);
		rel1.setOnClickListener(this);
		rel2.setOnClickListener(this);
		rel3.setOnClickListener(this);
		rel4.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rells1:
			rel1.setBackgroundColor(Color.parseColor("#EDD99F"));
			mTxtWen.setTextColor(Color.parseColor("#efefef"));
			rel2.setBackgroundColor(Color.parseColor("#000000"));
			rel3.setBackgroundColor(Color.parseColor("#000000"));
			rel4.setBackgroundColor(Color.parseColor("#000000"));
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.details_layout, new DetaisFragment(),
							Constant.TAG_DETAIS).commit();
			break;
		case R.id.rells2:
			rel2.setBackgroundColor(Color.parseColor("#EDD99F"));
			mTxtTu.setTextColor(Color.parseColor("#efefef"));
			rel1.setBackgroundColor(Color.parseColor("#000000"));
			rel3.setBackgroundColor(Color.parseColor("#000000"));
			rel4.setBackgroundColor(Color.parseColor("#000000"));
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.details_layout, new DetaisImageFragment(),
							Constant.TAG_DETAISIMAGE).commit();
			break;
		case R.id.rells3:
			rel3.setBackgroundColor(Color.parseColor("#EDD99F"));
			mTxtliu.setTextColor(Color.parseColor("#efefef"));
			rel1.setBackgroundColor(Color.parseColor("#000000"));
			rel2.setBackgroundColor(Color.parseColor("#000000"));
			rel4.setBackgroundColor(Color.parseColor("#000000"));
			 getSupportFragmentManager()
			 .beginTransaction()
			 .replace(R.id.details_layout, new DetaisLichengFragment(),
			 Constant.TAG_DETAISLICHENG).commit();
			break;
		case R.id.rells4:
			rel4.setBackgroundColor(Color.parseColor("#EDD99F"));
			mTxtgong.setTextColor(Color.parseColor("#efefef"));
			rel1.setBackgroundColor(Color.parseColor("#000000"));
			rel2.setBackgroundColor(Color.parseColor("#000000"));
			rel3.setBackgroundColor(Color.parseColor("#000000"));
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.details_layout, new DetaisgonggaoFragment(),
							Constant.TAG_DETAISGONGGAO).commit();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		
	}


}
