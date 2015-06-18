package com.hutu.longchang.fragment;

import com.hutu.longchang.R;
import com.hutu.longchang.util.Util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RechargeFragment extends BaseFragment{

	private TextView messageTx;
	private Button chongzhibtn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_recharge, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void init() {
		messageTx = (TextView) mView.findViewById(R.id.textView2);
		messageTx.setText(mMessage());
		chongzhibtn = (Button) mView.findViewById(R.id.button1);
		setTitle("支付");
	}
	@Override
	public void onPrevious() {
		super.onPrevious();
		showFragment(new LoginFragment(), Constant.TAG_USERLOGIN);
	}
	@Override
	public void initListener() {
		chongzhibtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View paramView) {
				showFragment(new RechargePaymentFragment(), Constant.TAG_USERRECHARGEPAYMENT);
			}
		});
	}
	
	public String mMessage(){
		return mRes.getString(R.string.vipchongzhi) + Util.currentTimer() + mRes.getString(R.string.vipchongzhi1);
	}
	

}
