package com.hutu.longchang.model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Connect {
//	private String url;
	private final int timeout = 30000;
	private HttpURLConnection connect;

	private String boundary = "&";
	private Handler mHandler;
	public static final String HostName = Request.HostName;
    private String urlPath=null;
    
    public static final String RESPONSE="response";
    public static final String PATH="path";
    
    public static final int RESPONSE_CODE_OK=1;
    public static final int RESPONSE_CODE_BUSINESS_ERROR=2;
    public static final int RESPONSE_CODE_TOKEN_EXPIRE=3;
    public static final int NET_WORK_ERROR=-1;
    
	public Connect(String url,Handler handler){
		this(url,null,handler);
	}
	public Connect(String url,Map<String,String> map,Handler handler){
		mHandler = handler;
		urlPath=url;
		if(!url.startsWith("http")){
			url = HostName+url;
		}
	    
		try {
			Log.d("CODE_2", "url       "+url);
			connect = (HttpURLConnection) new URL(url).openConnection();
			connect.setDoOutput(true);
			connect.setDoInput(true);
			connect.setUseCaches(false);
//			connect.setRequestProperty("Accept-Encoding", "gzip");
			connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connect.setRequestProperty("Charset", "UTF-8");
			connect.setReadTimeout(timeout);
			connect.setConnectTimeout(timeout);
			if(map == null){
			}else{
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getReauest(){
		try {
			connect.setRequestMethod("GET");
			
			connect.connect();
		} catch (ProtocolException e) {
			e.printStackTrace();
			connect.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
			connect.disconnect();
		}
	}
	
	public void postRequest(HashMap<String,?>map){
		this.postRequsetFile(map, null);
	}
	
	public void postRequsetFile(HashMap<String,?>map,byte[] fileByte) {
		try {
//			long contentLength = 0;
			connect.setRequestMethod("POST");
			if(!"&".equals(boundary)){
				connect.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			}
//			contentLength += fileByte.length;
			byte[]param = getParam(map);
			
			String str = "--"+boundary+"\r\n";
			str += "Content-Disposition: form-data; name=\"Filedata\"; filename=\"img.png\"\r\n";
			str += "Content-Type: image/png\r\nContent-Transfer-Encoding: binary\r\n\r\n";
//			contentLength += param.length;
			String end = ("\r\n--"+boundary+"--\r\n");
//			contentLength += end.getBytes().length;
//			contentLength += str.getBytes().length;
//
//			connect.setRequestProperty("Content-Length", contentLength+"");
			
			DataOutputStream dos = new DataOutputStream(connect.getOutputStream());
			dos.write(param);
			dos.flush();
			if(fileByte!=null){
				dos.write(str.getBytes());
				dos.flush();
				dos.write(fileByte);
				dos.flush();
				dos.write(end.getBytes());
				dos.flush();
			}
			dos.close();
			Log.d("CODE_2", "地址   =="+connect.getURL());
			connect.connect();		
		} catch (ProtocolException e) {
			connect.disconnect();
			e.printStackTrace();
		} catch (IOException e) {
			connect.disconnect();
			e.printStackTrace();
		}
	}
	
	public int response(){
		int code = -1;
		try {
			code = connect.getResponseCode();
			if(code==200){
				BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream(),"UTF-8"));
				
				String temp;
				StringBuffer buffer = new StringBuffer();
				while ((temp = br.readLine()) != null) {
					buffer.append(temp);
				}
				Message msg = new Message();  
				Bundle data = new Bundle();
				data.putString(RESPONSE, buffer.toString());
				data.putString(PATH, urlPath);
				msg.what = 0;
				msg.setData(data);
				msg.obj = "网络请求成功";
				mHandler.sendMessage(msg);
			}else{
				Message msg = new Message();
				Bundle dataWhenFail=new Bundle();
				dataWhenFail.putString(PATH, urlPath);
				msg.what = Connect.NET_WORK_ERROR;
				msg.obj = "网络请求错误:"+connect.getResponseCode()+" "+connect.getResponseMessage()+" "+urlPath;
				msg.setData(dataWhenFail);
				System.out.println("error:"+msg.obj);
				mHandler.sendMessage(msg);
			}
		} catch(SocketTimeoutException e){
			code = -100;
			connect.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("CODE_2", ""+e.toString());
			connect.disconnect();
		}
		return code;
	}
	
	private byte[] getParam(HashMap<String,?> map){
		if("&".equals(boundary)){
			StringBuffer sb = new StringBuffer();
			for(String key:map.keySet()){
				sb.append(boundary+key+"="+map.get(key));
			}
			String param = sb.toString().substring(boundary.length(),sb.toString().length());
			return param.getBytes();
		}else{
			byte[] arr = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				String endStr = "--"+ boundary + "\r\n";
				baos.write(endStr.getBytes());
				for(String key:map.keySet()){
					String str = "";
					Object obj = map.get(key);
					if(obj instanceof String){
						str += "Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n";
						str += map.get(key);
						str += "\r\n";
						baos.write(str.getBytes());
						baos.write(endStr.getBytes());
						baos.flush();
					}else if(obj instanceof Bitmap){
						str += "Content-Disposition:form-data;name=\"" + key + "\";fileName=\"file\"\r\n\r\n";
						str += "Content-Type:image/png\r\nContent-Transfer-Encoding:binary\r\n\r\n";
						baos.write(str.getBytes());
						ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
				        ((Bitmap)obj).compress(Bitmap.CompressFormat.PNG, 100, baos2);
				        baos.write(baos2.toByteArray());
						baos.write(endStr.getBytes());
				        baos.flush();
				        baos2.close();
					}else if(obj instanceof byte[]){
						str += "Content-Disposition:form-data;name=\"" + key + "\";fileName=\"file\"\r\n\r\n";
						str += "Content-Type:application/octet-stream\r\nContent-Transfer-Encoding:binary\r\n\r\n";
						baos.write(str.getBytes());
						baos.write(endStr.getBytes());
						baos.flush();
						baos.write(((byte[])obj));
						baos.flush();
					}
				}
				arr = baos.toByteArray();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return arr;
		}
	}
	
	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}
}
