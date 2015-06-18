/**
 * 
 */
package com.hutu.longchang.widget;

import android.app.ProgressDialog;
import android.content.Context;


public class ProgressDialogView {
	
	private static ProgressDialogView INSTANCE = null;
	private static ProgressDialog mDialog = null;
	private Context mContext = null;

	/**
	 * 
	 */
	private ProgressDialogView(Context context) {
		this.mContext = context;
		init();
	}

	public static ProgressDialogView getInstance(Context context) {
		if(null == INSTANCE) {
			INSTANCE = new ProgressDialogView(context);
		} 
		
		return INSTANCE;
	}
	
	/**
	 * 
	 */
	private void init() {
		mDialog = ProgressDialog.show(mContext, "", "加载中", true, true);
	}
	
	public void show() {
		if(false == mDialog.isShowing()) {
			mDialog.show();
		}
	}
	
	public void dismiss() {
		if(null != mDialog && mDialog.isShowing()) {
			mDialog.dismiss();
			setCanceledOnTouchOutside(true);
		}
	}
	
	public void setCanceledOnTouchOutside(boolean boo) {
		mDialog.setCanceledOnTouchOutside(boo);
		mDialog.setCancelable(boo);
	}
	
}
