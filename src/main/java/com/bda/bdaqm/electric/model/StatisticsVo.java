package com.bda.bdaqm.electric.model;

import java.io.Serializable;

public class StatisticsVo implements Serializable{

	private static final long serialVersionUID = -7496504875919896188L;
	
	
	private String id;
	private String ticketType;
	private String departmentOid;
	private String workEndTime;
	private String departmentOname;
	private String permissionUnitName;
	private String signUnitName;
	private String departName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getDepartmentOid() {
		return departmentOid;
	}
	public void setDepartmentOid(String departmentOid) {
		this.departmentOid = departmentOid;
	}
	public String getWorkEndTime() {
		return workEndTime;
	}
	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
	}
	public String getDepartmentOname() {
		return departmentOname;
	}
	public void setDepartmentOname(String departmentOname) {
		this.departmentOname = departmentOname;
	}
	public String getPermissionUnitName() {
		return permissionUnitName;
	}
	public void setPermissionUnitName(String permissionUnitName) {
		this.permissionUnitName = permissionUnitName;
	}
	public String getSignUnitName() {
		return signUnitName;
	}
	public void setSignUnitName(String signUnitName) {
		this.signUnitName = signUnitName;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	
	
}
