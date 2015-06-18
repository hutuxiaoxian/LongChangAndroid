package com.hutu.longchang.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class JSONArrayExt extends JSONArray {

//	private List<Object> values;
    /**
     * Construct an empty JSONArray.
     */
	public JSONArrayExt() {
		super();
//		values = getSuperList(this);
	}
	public JSONArrayExt(String str) throws JSONException {
//		this(new JSONTokener(str));
//		super(str);
	}
//	public JSONArrayExt(JSONTokener readFrom){
//		this();
//		try {
//			Object object = readFrom.nextValue();
//			if (object instanceof JSONArray) {
//			    values = getSuperList(((JSONArray) object));
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
	public Object get(int index){
		Object obj = null;
		try {
			obj = super.get(index);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	public boolean getBoolean(int index) {
		return getBoolean(index,false);
	}
	public boolean getBoolean(int index,boolean def){
		boolean bol = def;
		try {
			bol = super.getBoolean(index);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bol;
	}
	public JSONArrayExt getJSONArray(int index) {
		return getJSONArray(index, null);
	}
	public JSONArrayExt getJSONArray(int index,JSONArrayExt def){
		JSONArrayExt arr = def;
		try {
			arr = (JSONArrayExt) super.getJSONArray(index);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arr;
	}
	public JSONObjectExt getJSONObject(int index){
		return getJSONObject(index, null);
	}
	public JSONObjectExt getJSONObject(int index,JSONObjectExt def){
		JSONObjectExt arr = def;
		try {
			arr = new JSONObjectExt(super.getJSONObject(index).toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arr;
	}
	public String getString(int index){
		return getString(index, null);
	}
	public String getString(int index,String def){
		String arr = def;
		try {
			arr = super.getString(index);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arr;
	}
	public double getDouble(int index){
		return getDouble(index, 0.0000000d);
	}
	public double getDouble(int index,double def) {
		double arr = def;
		try {
			arr = super.getDouble(index);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arr;
	}
	public int getInt(int index) {
		return getInt(index,0);
	}
	public int getInt(int index,int def) {
		int arr = def;
		try {
			arr = super.getInt(index);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arr;
	}
	public long getLong(int index) {
		return getLong(index,0l);
	}
	public long getLong(int index,long def) {
		long arr = def;
		try {
			arr = super.getLong(index);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arr;
	}
	
//	private List<Object> getSuperList(Object parent){
//		String fname = super.getClass().getDeclaredFields()[0].getName();
//		try {
//			java.lang.reflect.Field field = getClass().getSuperclass().getDeclaredField(fname);
//			field.setAccessible(true);
//			return (List<Object>)field.get(parent);
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
