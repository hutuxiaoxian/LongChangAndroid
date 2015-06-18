package com.hutu.longchang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hutu.longchang.R;
import com.hutu.longchang.model.NetWorkCallBack;

public class ErWeiFragment extends BaseFragment implements NetWorkCallBack{

	private TextView msgText;
	private Bundle bundle = null;
	private String mMsage = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bundle = getArguments();
		mMsage = bundle.getString("result");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_erwei, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		
	}

	@Override
	public void init() {
		msgText =(TextView) mView.findViewById(R.id.textView1); 
		msgText.setText(mMsage);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

}
