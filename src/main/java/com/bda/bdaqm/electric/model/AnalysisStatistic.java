package com.bda.bdaqm.electric.model;

import java.io.Serializable;

public class AnalysisStatistic implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5276967607504304333L;
	private String id;
	private String ticketId;
	private String permissionTime;
	private String workEndTime;
	private String planStartTime;
	private String planEndTime;
	private String orgId;
	private String orgName;
	private String threeUnitName;
	private String nameFullPath;
	private String orgLevel;
	private String isDelay;
	private String delayTime;
	private String checkResult;
	private String standardResult;
	private String workTask;
	private String major;
	private String ticketType;
	private String functionLocationName;
	private String workPermissionUid;
	private String workPrincipalUid;
	private String ticketNo;
	private String ticketSignUid;
	private String workState;
	private String pmisName;
	private String principalName;
	private String signName;
	private String dataType;
	private String pmisFullPath;//许可人单位
	private String signFullPath;//签发人单位
	
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime;
	}
	public String getPmisName() {
		return pmisName;
	}
	public void setPmisName(String pmisName) {
		this.pmisName = pmisName;
	}
	public String getPrincipalName() {
		return principalName;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getTicketSignUid() {
		return ticketSignUid;
	}
	public void setTicketSignUid(String ticketSignUid) {
		this.ticketSignUid = ticketSignUid;
	}
	public String getWorkState() {
		return workState;
	}
	public void setWorkState(String workState) {
		this.workState = workState;
	}
	public String getWorkPermissionUid() {
		return workPermissionUid;
	}
	public void setWorkPermissionUid(String workPermissionUid) {
		this.workPermissionUid = workPermissionUid;
	}
	public String getWorkPrincipalUid() {
		return workPrincipalUid;
	}
	public void setWorkPrincipalUid(String workPrincipalUid) {
		this.workPrincipalUid = workPrincipalUid;
	}
	public String getFunctionLocationName() {
		return functionLocationName;
	}
	public void setFunctionLocationName(String functionLocationName) {
		this.functionLocationName = functionLocationName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getPermissionTime() {
		return permissionTime;
	}
	public void setPermissionTime(String permissionTime) {
		this.permissionTime = permissionTime;
	}
	public String getWorkEndTime() {
		return workEndTime;
	}
	public void setWorkEndTime(String workEndTime) {
		this.workEndTime = workEndTime;
	}
	public String getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(String planEndTime) {
		this.planEndTime = planEndTime;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getThreeUnitName() {
		return threeUnitName;
	}
	public void setThreeUnitName(String threeUnitName) {
		this.threeUnitName = threeUnitName;
	}
	
	public String getNameFullPath() {
		return nameFullPath;
	}
	public void setNameFullPath(String nameFullPath) {
		this.nameFullPath = nameFullPath;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public String getIsDelay() {
		return isDelay;
	}
	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}
	public String getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getStandardResult() {
		return standardResult;
	}
	public void setStandardResult(String standardResult) {
		this.standardResult = standardResult;
	}
	public String getWorkTask() {
		return workTask;
	}
	public void setWorkTask(String workTask) {
		this.workTask = workTask;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getPmisFullPath() {
		return pmisFullPath;
	}
	public void setPmisFullPath(String pmisFullPath) {
		this.pmisFullPath = pmisFullPath;
	}
	public String getSignFullPath() {
		return signFullPath;
	}
	public void setSignFullPath(String signFullPath) {
		this.signFullPath = signFullPath;
	}

}
