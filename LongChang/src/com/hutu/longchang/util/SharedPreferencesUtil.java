package com.hutu.longchang.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {

	private SharedPreferences mSharedPreferences = null;
	public static final String CONFIG_LONGCHANG = "config_longchang";

	private static String USERNAME = "username";
	
	private Context mContext;
	private static SharedPreferencesUtil INSTANCE = null;

	private Editor editor = null;

	public SharedPreferencesUtil(Context context) {
		this.mContext = context;
		mSharedPreferences = mContext.getSharedPreferences(CONFIG_LONGCHANG,
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

	public void SharedPreferencesCler() {
		editor.clear();
		editor.commit();

	}
}
