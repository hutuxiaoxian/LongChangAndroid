package com.hutu.longchang.util;

import java.text.SimpleDateFormat;
import java.util.Random;

public class Util {

	public static String  currentTimer(){
		SimpleDateFormat  sDateFormat    =   new  SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");       
		return  sDateFormat.format(new   java.util.Date()); 
	}
	
	/**
	 * 生成6位验证码
	 */
	public static String registerCode(){
		Random random = new Random();
		int n = random.nextInt(1000000);
		return String.format("%06d", n);		
	}
	
}
