/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.imageco.library;

import java.util.Hashtable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

/**
 * Class DecodeHandler ...
 *
 * @author Administrator
 *         Created on 11-10-22
 */
final class DecodeHandler extends Handler {

    /**
     * Field TAG
     */
    private static final String TAG = DecodeHandler.class.getSimpleName();

    /**
     * Field activityImpl
     */
    private final CaptureActivity activity;
    /**
     * Field multiFormatReader
     */
    private final MultiFormatReader multiFormatReader;

    /**
     * Constructor DecodeHandler creates a new DecodeHandler instance.
     *
     * @param activity of type CaptureActivity
     * @param hints    of type Hashtable<DecodeHintType, Object>
     */
    DecodeHandler(CaptureActivity activity, Hashtable<DecodeHintType, Object> hints) {
        multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        this.activity = activity;
    }

    /**
     * Method handleMessage ...
     *
     * @param message of type Message
     */
    @Override
    public void handleMessage(Message message) {
        if (message.what == R.id.decode)
        {//Log.d(TAG, "Got decode message");
            decode((byte[]) message.obj, message.arg1, message.arg2);
        }
        else if (message.what == R.id.quit)
        {
            Looper.myLooper().quit();
        }
    }

    /**
     * Decode the data within the viewfinder rectangle, and time how long it took. For efficiency,
     * reuse the same reader objects from one decode to the next.
     *
     * @param data   The YUV preview frame.
     * @param width  The width of the preview frame.
     * @param height The height of the preview frame.
     */
    private void decode(byte[] data, int width, int height) {
//        long start = System.currentTimeMillis();
//        Result rawResult = null;
//        PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(data, width, height);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        try {
//            rawResult = multiFormatReader.decodeWithState(bitmap);
//        } catch (ReaderException re) {
//            // continue
//        } finally {
//            multiFormatReader.reset();
//        }
//
//        if (rawResult != null) {
//            long end = System.currentTimeMillis();
//            Log.d(TAG, "Found barcode (" + (end - start) + " ms):\n" + rawResult.toString());
//            Message message = Message.obtain(activity.getHandler(), R.id.decode_succeeded, rawResult);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(DecodeThread.BARCODE_BITMAP, source.renderCroppedGreyscaleBitmap());
//            message.setData(bundle);
//            //Log.d(TAG, "Sending decode succeeded message...");
//            message.sendToTarget();
//        } else {
//            Message message = Message.obtain(activity.getHandler(), R.id.decode_failed);
//            message.sendToTarget();
//        }
    	
    	byte[] rotatedData = new byte[data.length];
    	for (int y = 0; y < height; y++) {
    		for (int x = 0; x < width; x++)
    			rotatedData[x * height + height - y - 1] = data[x + y * width];
    	}

    	long start = System.currentTimeMillis();

    	//         PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(data, width, height);
    	//         PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(data, height, width);
    	// switch width and height
//    	Bitmap bt = BitmapFactory.decodeByteArray(rotatedData, 0, rotatedData.length);
//    	System.out.println(bt);
    	PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, height, width);

    	//这里是源代码,解码用的
    	Result rawResult = null;
    	BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
    	try {
    		rawResult = multiFormatReader.decodeWithState(bitmap);
    	} catch (ReaderException re) {
    		// continue
    	} finally {
    		multiFormatReader.reset();
    	}
    	if(rawResult != null){
    		Message message = Message.obtain(activity.getHandler(), R.id.decode_succeeded, rawResult);
    		Bundle bundle = new Bundle();
    		bundle.putParcelable(DecodeThread.BARCODE_BITMAP, source.renderCroppedGreyscaleBitmap());
    		message.setData(bundle);
    		//Log.d(TAG, "Sending decode succeeded message...");
    		message.sendToTarget();
    	} else {
    		Message message = Message.obtain(activity.getHandler(), R.id.decode_failed);
    		message.sendToTarget();
    	}
//    	decode(source);
    }

    private void decode(PlanarYUVLuminanceSource source){
    	Bitmap bt = source.renderCroppedGreyscaleBitmap();
    	//    	对比度加强

    	ColorMatrix cm = new ColorMatrix();
    	Canvas c = new Canvas(bt);
    	float mm[]= new float[]{
    			2.0f,0.0f,0.0f,0.0f,-120.0f,
    			0.0f,2.0f,0.0f,0.0f,-120.0f,
    			0.0f,0.0f,2.0f,0.0f,-120.0f,
    			0.0f,0.0f,0.0f,2.0f,-0.0f};
    	cm.set(mm);
    	Paint p = new Paint();
    	p.setColorFilter(new ColorMatrixColorFilter(cm));
    	c.drawBitmap(bt,0,0,p);
    	Matrix mat = new Matrix();
    	String str = null;
    	RGBLuminanceSource rgb = null;
    	BinaryBitmap bb = null;
//    	for(float i=0.6F;i<1.6F;i+=.2F){
    		//自己写的
//        	mat.postScale(i,i);
    		mat.postScale(1F, 1F);
//    		mat.postScale(.8F, .8F);
        	bt = Bitmap.createBitmap(bt,0,0,(int)(bt.getWidth()),(int)(bt.getHeight()),mat,true);
//    		try {
//    			FileOutputStream fos = new FileOutputStream("/sdcard/oo.png", true);
//    			bt.compress(Bitmap.CompressFormat.PNG, 100, fos);
//    			fos.flush();
//    		} catch (FileNotFoundException e) {
//    			e.printStackTrace();
//    		} catch (IOException e) {
//    			e.printStackTrace();
//    		}

          	rgb = new RGBLuminanceSource(bt);
          	bb = new BinaryBitmap(new HybridBinarizer(rgb));
        	try {
    			str = new MultiFormatReader().decode(bb).getText();
//    			break;
    		} catch (NotFoundException e) {
    		}
//    	}
    	if (str != null) {
        	Result rawResult = null;
    		try {
    			rawResult = multiFormatReader.decodeWithState(bb);
    		} catch (NotFoundException e1) {
    		}finally{
    			multiFormatReader.reset();
    		}
    		Message message = Message.obtain(activity.getHandler(), R.id.decode_succeeded, rawResult);
    		Bundle bundle = new Bundle();
    		bundle.putParcelable(DecodeThread.BARCODE_BITMAP, bt);
    		message.setData(bundle);
    		message.sendToTarget();
    	} else {
    		Message message = Message.obtain(activity.getHandler(), R.id.decode_failed);
    		message.sendToTarget();
    	}
    }
}
