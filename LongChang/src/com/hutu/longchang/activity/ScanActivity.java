package com.hutu.longchang.activity;

import com.imageco.library.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 扫码
 * @author hutu
 *
 */
public class ScanActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void scan(){
		Intent intent = new Intent(this, CaptureActivity.class);
		startActivityForResult(intent, 1);
	}
	
}
