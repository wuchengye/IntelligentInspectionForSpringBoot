package com.bda.bdaqm.electric.model;

import java.io.Serializable;

/**
 * 工作票种类、作业类型
 * @author zengjie
 *
 */
public class TypeOfTicketAndWork implements Serializable{

	private static final long serialVersionUID = 3388188313088564215L;
	
	private Integer id;
	private String ticketType;
	private String workType;
	
	/////////////////////////////
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	
	
	
}
