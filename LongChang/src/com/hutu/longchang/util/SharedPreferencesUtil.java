package com.hutu.longchang.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	private SharedPreferences mSharedPreferences = null;
	private SharedPreferences msharePreferences = null;
	public static final String CONFIG_LONGCHANG = "config_longchang";
	public static final String CONFIG_LONGCHANG1 = "config_longchang1";

	private static String USERNAME = "username";
	
	private Context mContext;
	private static SharedPreferencesUtil INSTANCE = null;

	private Editor editor = null;
	private Editor editors = null;

	public SharedPreferencesUtil(Context context) {
		this.mContext = context;
		mSharedPreferences = mContext.getSharedPreferences(CONFIG_LONGCHANG,
				Context.MODE_PRIVATE);
		msharePreferences = mContext.getSharedPreferences(CONFIG_LONGCHANG1,
				Context.MODE_PRIVATE);
	}

	public static SharedPreferencesUtil getInstance(Context context) {
		if (null == INSTANCE) {
			INSTANCE = new SharedPreferencesUtil(context);
		}
		return INSTANCE;
	}

	public void saveUserName(String userName) {
		editor = mSharedPreferences.edit();
		editor.putString(USERNAME, userName);
		editor.commit();
	}

	public String loadUserName() {
		return mSharedPreferences.getString(USERNAME, null);
	}
	
	public void userNamesave(String userName){
		editors = msharePreferences.edit();
		editors.putString(USERNAME, userName);
		editors.commit();
	}

	public String UserNameload() {
		return msharePreferences.getString(USERNAME, null);
	}
	
	public void ShardPreferencesClear(){
		editors.clear();
		editors.commit();
	}
	public void SharedPreferencesCler() {
		editor.clear();
		editor.commit();

	}
}
