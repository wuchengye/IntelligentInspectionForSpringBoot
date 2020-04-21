package com.bda.bdaqm.electric.model;

import java.io.Serializable;

public class TypeAndDate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 202011791652569529L;
	private String type;
	private String date;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
