package com.hutu.longchang.fragment;

import com.hutu.longchang.R;
import com.hutu.longchang.util.SharedPreferencesUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainFragment extends BaseFragment{

	private RelativeLayout mJInSearch,mShangSearch,mzongShangSearch;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_main, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onPrevious() {
		super.onPrevious();
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				mActivity);
		dialog.setTitle("提示");
		dialog.setMessage("确定要退出程序吗？");
		dialog.setPositiveButton("确认",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mActivity.finish();
				SharedPreferencesUtil.getInstance(mActivity).SharedPreferencesCler();
				System.exit(0);
			}
		});
		dialog.setNegativeButton("取消",null);
		dialog.show();
	}
	@Override
	public void init() {
		mJInSearch = (RelativeLayout) mView.findViewById(R.id.button2);
		mShangSearch = (RelativeLayout) mView.findViewById(R.id.button1);
		mzongShangSearch = (RelativeLayout) mView.findViewById(R.id.button3);
	}

	@Override
	public void initListener() {
		mJInSearch.setOnClickListener(onClickListener);
		mShangSearch.setOnClickListener(onClickListener);
		mzongShangSearch.setOnClickListener(onClickListener);
	}
	private android.view.View.OnClickListener onClickListener = new android.view.View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			int id = arg0.getId();
			switch (id) {
			case R.id.button2:
				showFragment(new SearchSameFragment(), Constant.TAG_SEARCHSAME);
				break;
			case R.id.button3:
				showFragment(new SearchComplexFragment(), Constant.TAG_SEARCHCOMPLEX);
				break;
			case R.id.button1:
				showFragment(new SearchClassFragment(), Constant.TAG_SERCHCLASS);
				break;
			default:
				break;
			}
		}
	};

}
