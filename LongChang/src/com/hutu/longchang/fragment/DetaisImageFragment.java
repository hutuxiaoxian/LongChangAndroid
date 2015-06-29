package com.hutu.longchang.fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hutu.longchang.R;
import com.hutu.longchang.activity.DetailsActivity;
import com.hutu.longchang.alipy.Base64;
import com.hutu.longchang.model.Commodityd;
import com.hutu.longchang.model.NetWorkCallBack;
import com.hutu.longchang.model.Request;

public class DetaisImageFragment extends BaseFragment implements NetWorkCallBack{

	private ImageView image;
	private Request request = new Request(mActivity, this);
	private Commodityd comm = null;
	private Bitmap bitmap;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mActivity instanceof DetailsActivity) {
			Intent intent = mActivity.getIntent();
			comm = (Commodityd) intent.getSerializableExtra("commond");
		}
		request.imgByte(comm.getRegNo());
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_detaistupian, null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void init() {
		image = (ImageView) mView.findViewById(R.id.image_shangjia_tu);
	}

	@Override
	public void onPrevious() {
		// TODO Auto-generated method stub
		super.onPrevious();
		mActivity.finish();
	}
	
	@Override
	public void initListener() {
		
	}

	public Bitmap getBitmapFromByte(byte [] image){
		if(image != null){  
	    	if(bitmap!=null && !bitmap.isRecycled()){
	    		bitmap.recycle();
	    	}
	    	bitmap = null;
	    	BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;
	    	bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);   
	        return bitmap;   
	    }else{   
	        return null;   
	    }   
	}
	@Override
	public void afterResponseFetched(int respCode, int flag, String msg) {
		if(0 == respCode){
			if(flag == Request.getReqeustFlag(Request.HostName
					+ "?method=SBImgByte")){
				image.setImageBitmap(getBitmapFromByte(Base64.decode(msg)));			
//				System.out.println(msg);
			}
		}
	}

}
