package com.hutu.longchang.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.hutu.longchang.util.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

/**
 * 接口请求
 * 
 * @author hutu
 * 
 */
public class Request {

	/**
	 * conn 网络请求对象 mHandler 数据返回handler urlTime
	 */
	public static final String HostName = "http://120.55.73.239:8001/SBService.aspx";
		
	private static Map<String, Integer> requestFlagMap = new HashMap<String, Integer>();

	// private Connect conn;
	private NetWorkCallBack mCallBask;
	private static ExecutorService pool;
	private static HashMap<String, Runnable> urlTime;
	private int mIndex;
	private Context context;

	public Request(Context context, NetWorkCallBack callBack) {
		this(context, callBack, 0);
		this.context = context;
	}

	public Request(Context context, NetWorkCallBack callBack, int index) {
		this.context = context;

		mCallBask = callBack;
		if (pool == null) {
			pool = Executors.newFixedThreadPool(4);
		}
		if (urlTime == null) {
			urlTime = new HashMap<String, Runnable>();
		}
		mIndex = index;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
//			((BaseActivity)context).hiddenProgressDialog();
			String urlPath = msg.getData().getString(Connect.PATH);
			if (Connect.NET_WORK_ERROR == msg.what) {
//				Log.d(MainActivity.CITY_HUNTER_TAG, "network error" + msg.what);
				AlertDialog.Builder dialog = new AlertDialog.Builder(context);
				dialog.setTitle("网络异常");
				dialog.setMessage("请确认网络通畅。");
				dialog.setNegativeButton("确认", null);
				dialog.setPositiveButton("取消", null);
				dialog.show();
				
				return;
			}
//			JSONObjectExt jsonObj = new JSONObjectExt(msg.getData().getString(
//					Connect.RESPONSE));
//			int serverCode = jsonObj.getInt("respcode");
//			if (Connect.RESPONSE_CODE_TOKEN_EXPIRE == serverCode) {
////				Log.d(MainActivity.CITY_HUNTER_TAG, "token expire: "+msg.getData().getString(Connect.RESPONSE));
////				if (com.cityhunter.model.social.MyGoods.getInstance().getProgressbardialog() != null) {
////					com.cityhunter.model.social.MyGoods.getInstance().getProgressbardialog().dismiss();
////				}
//				AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//				dialog.setPositiveButton("知道了", null);
//				
//				return;
//			}
			mCallBask.afterResponseFetched(msg.what, getReqeustFlag(urlPath),
					msg.getData().getString(Connect.RESPONSE));
		};
	};

	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {

				return true;
			} else {
				return false;
			}
		}
		return false;

	}

	private void connect(final String url, final HashMap<String, ?> map) {
		if (isNetworkConnected(context)) {
			getReqeustFlag(url);
			if (map != null && map.size() > 0)
				sendHttpPostRequest(url, map, null);
			else
				httpGetConnect(url);
		} else {
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("网络异常");
			dialog.setMessage("请确认网络通畅。");
			dialog.setNegativeButton("确认", null);
			dialog.setPositiveButton("取消", null);
			dialog.show();
			return;
		}

	}

	/*
	 * to get the corresponding request flag. a new one will be generated if
	 * there's no flag for the request url .
	 */
	public static int getReqeustFlag(String url) {
		int n = url.indexOf('&');
		String u = null;
		if(n > 0 ){
			u = url.substring(0,n);
		}else{
			u = url;
		}
		if (!requestFlagMap.containsKey(u))
			requestFlagMap.put(u, requestFlagMap.size());
		return requestFlagMap.get(u);
	}

	private void httpGetConnect(final String url) {
		boolean isNewUrl = urlTime.containsKey(url);
		if (!isNewUrl) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					int runStep = 0;
					System.out.println("http get ürl:" + url);
					while (runStep < 3) {
						Connect conn = new Connect(url, mHandler);
						conn.getReauest();
						int code = conn.response();
						if (code != -100) {
							// 约定-100为链接超时,少连续请求处理
							urlTime.remove(url);
							break;
						} else {
							runStep++;
						}
					}
					if (runStep >= 3) {
						System.out.println("链接超时");
					}
				}
			};
			urlTime.put(url, runnable);
			if (pool instanceof ScheduledExecutorService || pool == null) {
				pool = Executors.newScheduledThreadPool(4);
			}
			pool.execute(runnable);
		}
	}

	/**
	 * 网络请求
	 * 
	 * @param url
	 *            地址
	 * @param map
	 *            post 参数
	 * @param file
	 *            文件
	 */
	public void sendHttpPostRequest(final String url, final HashMap<String, ?> map,
			final Object file) {

		boolean isNewUrl = urlTime.containsKey(url);
		if (!isNewUrl) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					int runStep = 0;
					while (runStep < 3) {
						Connect conn = new Connect(url, mHandler);
						conn.setBoundary("293iosfksdfkiowjksdf31jsiuwq003s02dsaffafass3qw");
						if (file instanceof Bitmap) {
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							((Bitmap) file).compress(Bitmap.CompressFormat.PNG,
									100, baos);
							Log.d("CODE_2", "分享     bitmap   =="+url+ new String(map.toString()));
							conn.postRequsetFile(map, baos.toByteArray());
						} else if (file instanceof File) {

						} else {
							Log.d("CODE_2", "分享=="+url+ new String(map.toString()));
							conn.postRequest(map);
						}
						int code = conn.response();
						if (code != -100) {
							// 约定-100为链接超时,少连续请求处理
							urlTime.remove(url);
							break;
						} else {
							runStep++;
						}
					}
					
					if (runStep >= 3) {
						System.out.println("链接超时");
					}
				}
			};
			urlTime.put(url, runnable);
			if (pool instanceof ScheduledExecutorService || pool == null) {
				pool = Executors.newScheduledThreadPool(4);
			}
			pool.execute(runnable);
		}
	}
	
	public void sendHttpGetRequest(String url) {
		connect(url, null);
	}
	
	public void jinsichaxun(String TabNum, String typeName, String type,int start, int end ,int jingmo){
		try {
			typeName = URLEncoder.encode(typeName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = HostName + "?method=SBJinSiChaXun&TabNum="+TabNum+"&type="+type+"&TypeName="+typeName;
		url = url + "&IsHuanXu=" + jingmo + "&IsJhz="+ jingmo + "&IsRyjhz="+ jingmo +"&IsNhqtbq=" + jingmo + "&IsWqxt=" + jingmo + "&IsBfxt="+ jingmo +"&IsBhz=" + jingmo + "&IsDyxt=" + jingmo+ "&IsNx=" + jingmo +"" + "&Start=" + start + "&End=" + 20;
		httpGetConnect(url);
	}
	
	public void typeInfo(int FlType, String FlName){

		String url = HostName + "?method=SBTypeInfo";
		if (FlName == null) {
			try {
				FlName = URLEncoder.encode(FlName, "UTF-8");
				url += ("&FlName=" + FlName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			url += ("&FlType=" + FlType);
		}
		httpGetConnect(url);
	}
	/**
	 * 商标综合查询
	 * @param TabNum	国际分类号	
	 * @param RegNO		注册号/申请号
	 * @param SPName	商标名称
	 * @param SQNameCN	申请人名称(中文)
	 * @param SQNameEN	申请人名称(英文)
	 */
	public void zongHeInfo(String TabNum, String RegNO, String SPName, String SQNameCN, String SQNameEN){
		String url = HostName + "?method=SBZongHeInfo";
		
		if (TabNum != null && TabNum.length() > 0) {
			url += ("&TabNum=" + TabNum);
		}
		
		if (RegNO != null && RegNO.length() > 0) {
			url += ("&RegNO=" + RegNO);
		}
		
		if (SPName != null && SPName.length() > 0) {
			try {
				SPName = URLEncoder.encode(SPName, "UTF-8");
				url += ("&SPName=" + SPName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		if (SQNameCN != null && SQNameCN.length() > 0) {
			try {
				SQNameCN = URLEncoder.encode(SQNameCN, "UTF-8");
				url += ("&SQNameCN=" + SQNameCN);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		if (SQNameEN != null && SQNameEN.length() > 0) {
			try {
				SQNameEN = URLEncoder.encode(SQNameEN, "UTF-8");
				url += ("&SQNameEN=" + SQNameEN);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		httpGetConnect(url);
		
	}
	/**
	 * 商标状态查询 
	 * @param RegNO	注册号/申请号
	 */
	public void stateList(String RegNO){
		String url = HostName + "?method=SBStateList&RegNO=" + RegNO;
		httpGetConnect(url);
	}
	/**
	 * 商标流程
	 * @param TabNum	国际分类号
	 * @param RegNO		注册号/申请号
	 */
	public void stateFlowInfo(String TabNum, String RegNO){
		String url = HostName + "?method=SBStateFlowInfo&RegNO=" + RegNO + "&TabNum=" + TabNum;
		httpGetConnect(url);
	}
	/**
	 * 查询二进制图片  返回byte[](byte)
	 * @param RegNO 注册号/申请号
	 */
	public void imgByte(String RegNO){
		String url = HostName + "?method=SBImgByte&RegNO=" + RegNO;
		httpGetConnect(url);
	}
	/**
	 * 商品分类列表
	 */
	public void fenLeiListAll(){
		String url = HostName + "?method=SBFenLeiListAll";
		httpGetConnect(url);
	}

	/**
	 * 注册用户 注册成功返回1 注册失败返回0
	 */
	public void setRegisterUser(String account, String password){
		String url = HostName + "?method=SetRegisterUser&UserName="+account+"&PassWord="+password;
		httpGetConnect(url);
	}
	
	public void setRegisterCode(String account , String code){
		String url = "http://utf8.sms.webchinese.cn/?Uid=longchang&Key=bbb1e31d8d3609525c43&smsMob=" + account + "&smsText=%%E6%%82%%A8%%E7%%9A%%84%%E9%%AA%%8C%%E8%%AF%%81%%E7%%A0%%81:";
		url = url + code;
	}
	
	/**
	 * 用户登录 登录成功返回1；登录失败返回0；
	 */
	public void getLognUser(String account, String password){
		String url = HostName + "?method=GetLognUser&UserName="+account+"&PassWord="+password;
		httpGetConnect(url);
	}
	
	/**
	 * 商标图像、服务列表、类似群 查询
	 */
	public void goodsImgInfo(){
		String url = HostName + "?method=SBGoodsImgInfo";
		httpGetConnect(url);
	}
	/**
	 * 根据条形码获取商标数据
	 * @param BarCode 条码
	 */
	public void getBarCodeInfo(String BarCode){
		String url = HostName + "?method=GetBarCodeInfo&BarCode=" + BarCode;
		System.out.println("GETURL-----" + url);
		httpGetConnect(url);
	}
	
	/**
	 * 根据名称获取商标数据
	 * @param detaiName
	 */
	public void getDetailInfo (String detaiName,int start , int end){
		try {
			String name= URLEncoder.encode(detaiName,"UTF-8");
			String url = HostName + "?method=GetDetailInfo&DetailName=" + name;
			url = url + "&Start=" + start + "&End=" + end;
			httpGetConnect(url);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
