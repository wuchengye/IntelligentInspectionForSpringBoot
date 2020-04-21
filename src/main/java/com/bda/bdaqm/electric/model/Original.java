package com.bda.bdaqm.electric.model;

import java.io.Serializable;

public class Original implements Serializable{

	private static final long serialVersionUID = 2672210943114462553L;
	
	private int original;
	private String workTask;
	private String ticketType;
	private String principalName;
	private String departmentOname;
	private String functionLocationName;
	private String planStartTime;
	private String planEndTime;
	private String workPlace;
	private String workState;
	private String ticketNo;
	private String signName;
	private String ticketSignTime;
	private String watchName;
	private String receiveTime;
	private String pmisName;
	private String permissionTime;
	private String endPmisName;
	private String workEndTime;
	private String delayTime;
	
	private String permissionUnitName;
	private String signUnitName;
	
	
	
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
	public int getOriginal() {
		return original;
	}
	public void setOriginal(int original) {
		this.original = original;
	}
	public String getWorkTask() {
		return workTask;
	}
	public void setWorkTask(String workTask) {
		this.workTask = workTask;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	
	public String getPrincipalName() {
		return principalName;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public String getDepartmentOname() {
		return departmentOname;
	}
	public void setDepartmentOname(String departmentOname) {
		this.departmentOname = departmentOname;
	}
	public String getFunctionLocationName() {
		return functionLocationName;
	}
	public void setFunctionLocationName(String functionLocationName) {
		this.functionLocationName = functionLocationName;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}
	public String getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
	}
	public String getWorkState() {
		return workState;
	}
	public void setWorkState(String workState) {
		this.workState = workState;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getTicketSignTime() {
		return ticketSignTime;
	}
	public void setTicketSignTime(String ticketSignTime) {
		this.ticketSignTime = ticketSignTime;
	}
	public String getWatchName() {
		return watchName;
	}
	public void setWatchName(String watchName) {
		this.watchName = watchName;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getPmisName() {
		return pmisName;
	}
	public void setPmisName(String pmisName) {
		this.pmisName = pmisName;
	}
	public String getPermissionTime() {
		return permissionTime;
	}
	public void setPermissionTime(String permissionTime) {
		this.permissionTime = permissionTime;
	}
	public String getEndPmisName() {
		return endPmisName;
	}
	public void setEndPmisName(String endPmisName) {
		this.endPmisName = endPmisName;
	}
	public String getWorkEndTime() {
		return workEndTime;
	}
	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
	}
	public String getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}
	
}
