package com.hutu.longchang.fragment;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.DetailsActivity;
import com.hutu.longchang.model.Commodityd;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetaisFragment extends BaseFragment{

	private TextView mTxt_leibie,mTxt_zhuce,mTxt_shenqigriqi,mTxt_zhuceriqi;
	private TextView mTxt_shangbiaoname,mTxt_shiyongshangpin,mTxt_qunzu;
	private TextView mTxt_shenqingren,mTxt_shenqingdizhi,mTxt_dailiren,mTxt_shangbiaozhuangtai;

	private Commodityd comm = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(mActivity instanceof DetailsActivity){
			Intent intent = mActivity.getIntent();
			comm = (Commodityd) intent.getSerializableExtra("commond");
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_details, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onPrevious() {
		super.onPrevious();
		mActivity.finish();
	}
	@Override
	public void init() {
		mTxt_leibie = (TextView) mView.findViewById(R.id.details_leibie_txt);
		mTxt_leibie.setText(comm.getIntCls());
		mTxt_zhuce = (TextView) mView.findViewById(R.id.details_zhucehao_txt);
		mTxt_zhuce.setText(comm.getRegNo());
		mTxt_shenqigriqi = (TextView) mView.findViewById(R.id.details_shenqingriqi_txt);
		mTxt_shenqigriqi.setText(comm.getAppDate());
		mTxt_zhuceriqi = (TextView) mView.findViewById(R.id.details_zhuceriqi_txt);
		mTxt_zhuceriqi.setText(comm.getRegDate());
		mTxt_shangbiaoname = (TextView) mView.findViewById(R.id.details_shangpinname_txt);
		mTxt_shangbiaoname.setText(comm.getTmType());
		mTxt_shiyongshangpin = (TextView) mView.findViewById(R.id.details_shiyongshangpin_txt);
		mTxt_shiyongshangpin.setText(comm.getTmDetail());
		mTxt_qunzu = (TextView) mView.findViewById(R.id.details_leisiqunzu_txt);
		mTxt_qunzu.setText(comm.getSimilarGroup());
		mTxt_shenqingren = (TextView) mView.findViewById(R.id.details_shenqingren_txt);
		mTxt_shenqingren.setText(comm.getTmApplicant());
		mTxt_shenqingdizhi = (TextView) mView.findViewById(R.id.details_shenqingdizhi_txt);
		mTxt_shenqingdizhi.setText(comm.getTmAddress());
		mTxt_dailiren = (TextView) mView.findViewById(R.id.details_dailiren_txt);
		mTxt_dailiren.setText(comm.getTmAgent());
		mTxt_shangbiaozhuangtai = (TextView) mView.findViewById(R.id.details_shangbiaozhuangtai_txt);
		mTxt_shangbiaozhuangtai.setText(comm.getTmType());
	}

	@Override
	public void initListener() {
		
	}

}
