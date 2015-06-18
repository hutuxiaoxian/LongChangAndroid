package com.hutu.longchang.model;

import java.io.Serializable;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONObjectExt extends JSONObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7930847313934715121L;
	public JSONObjectExt() {
		super();
	}
	public JSONObjectExt(String str){
		this(new JSONTokener(str));
	}
	public JSONObjectExt(Map<?, ?> copy){
		super(copy);
	}
	public JSONObjectExt(JSONTokener x){
		this();
		try {
			char c;
			String key;
 
			if (x.nextClean() != '{') {
	            throw x.syntaxError("A JSONObject text must begin with '{'");
			}
			for (;;) {
			    c = x.nextClean();
			    switch (c) {
			    case 0:
	                throw x.syntaxError("A JSONObject text must end with '}'");
			    case '}':
			        return;
			    default:
			        x.back();
			        key = x.nextValue().toString();
			    }
 
			    /*
			     * The key is followed by ':'. We will also tolerate '=' or '=>'.
			     */
 
			    c = x.nextClean();
			    if (c == '=') {
			        if (x.next() != '>') {
			            x.back();
			        }
			    } else if (c != ':') {
	                throw x.syntaxError("Expected a ':' after a key");
			    }
			    put(key, x.nextValue());
 
			    /*
			     * Pairs are separated by ','. We will also tolerate ';'.
			     */
 
			    switch (x.nextClean()) {
			    case ';':
			    case ',':
			        if (x.nextClean() == '}') {
			            return;
			        }
			        x.back();
			        break;
			    case '}':
			        return;
			    default:
	                throw x.syntaxError("Expected a ',' or '}'");
			    }
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Object get(String name) {
		return get(name, null);
	}
	public Object get(String name,Object def) {
		Object obj = def;
		try {
			obj = super.get(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	@Override
	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}
	public boolean getBoolean(String name,boolean def) {
		boolean obj = def;
		try {
			obj = super.getBoolean(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	@Override
	public double getDouble(String name) {
		return getDouble(name, 0.000000d);
	}
	public double getDouble(String name,double def) {
		double obj = def;
		try {
			obj = super.getDouble(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	@Override
	public int getInt(String name) {
		return getInt(name, 0);
	}
	public int getInt(String name,int def) {
		int obj = def;
		try {
			obj = super.getInt(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	@Override
	public JSONArrayExt getJSONArray(String name) {
		return getJSONArray(name, null);
	}
	public JSONArrayExt getJSONArray(String name,JSONArrayExt def) {
		JSONArrayExt obj = def;
		try {
			JSONArray arr = super.getJSONArray(name);
			obj = new JSONArrayExt(arr.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	@Override
	public JSONObjectExt getJSONObject(String name) {
		return getJSONObject(name, null);
	}
	public JSONObjectExt getJSONObject(String name,JSONObjectExt def) {
		JSONObjectExt obj = def;
		try {
			JSONObject json = super.getJSONObject(name);
			obj = new JSONObjectExt(json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	@Override
	public long getLong(String name){
		return getLong(name, 0l);
	}
	public long getLong(String name,long def) {
		long obj = def;
		try {
			obj = super.getLong(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
	@Override
	public String getString(String name) {
		return getString(name, null);
	}
	public String getString(String name,String def) {
		String obj = def;
		try {
			obj = super.getString(name);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e){
			
		}
		return obj;
	}
	
	@Override
	public JSONObject put(String name, Object value) {
		JSONObject json = null;
		try {
			json = super.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@Override
	public JSONObject put(String name, boolean value){
		JSONObject json = null;
		try {
			json = super.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject put(String name, double value) throws JSONException {
		JSONObject json = null;
		try {
			json = super.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	@Override
	public JSONObject put(String name, int value) throws JSONException {
		JSONObject json = null;
		try {
			json = super.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject put(String name, long value) throws JSONException {
		JSONObject json = null;
		try {
			json = super.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public JSONObject putOpt(String name, Object value) throws JSONException {
		JSONObject json = null;
		try {
			json = super.put(name, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}
