package com.bda.bdaqm.electric.model;

import java.io.Serializable;

public class FunctionLocationName implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ticketId;
	private String nameFullPath;
	private String functionLocationName;
	private String type;
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getNameFullPath() {
		return nameFullPath;
	}
	public void setNameFullPath(String nameFullPath) {
		this.nameFullPath = nameFullPath;
	}
	public String getFunctionLocationName() {
		return functionLocationName;
	}
	public void setFunctionLocationName(String functionLocationName) {
		this.functionLocationName = functionLocationName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
