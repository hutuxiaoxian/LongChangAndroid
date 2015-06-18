package com.hutu.longchang.mode.data;

import java.io.Serializable;

public class ListData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2930237170007882407L;
	private String regNo;
	private String classify;
	private String name;
	
	
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
