package com.imageco.library;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.HybridBinarizer;


public class PictureCallBack implements PictureCallback {
	
	Activity c;
	public PictureCallBack(Activity activity) {
		c = activity;
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		if(data.length>0){
			/*Bitmap bt = BitmapFactory.decodeByteArray(data, 0, data.length);
			RGBLuminanceSource rgb = new RGBLuminanceSource(bt);
			BinaryBitmap bb = new BinaryBitmap(new HybridBinarizer(rgb));
        	try {
    			String str = new MultiFormatReader().decode(bb).getText();
    			System.out.println(str);
    			camera.cancelAutoFocus();
    			camera.stopPreview();
    			Intent intent = new Intent(c,PhotoresultActivity.class);
    			Bundle bundle = new Bundle();
    			bundle.putString("qrCode", str);
    	        intent.putExtras(bundle);
    	        c.startActivity(intent);
    	        c.finish();
    	        return;
    		} catch (NotFoundException e) {
    		}*/
		}
	}
	

}
