package com.bda.bdaqm.electric.model;

import javax.persistence.Table;

@Table(name="o_gea_lcam_sc_sp_pd_wticket_base_test")
public class TicketBase {
	private String srcOperationType;
	private String transactionTime;
	private String oprationTime;
	private String oprationId;
	private String ticketType;
	private String workState;
	private String workPrincipalUid;
	private String ticketReceiveUid;
	private String ticketSignUid;
	private String workPermissionUid;
	private String workEndPermissionUid;
	private String watchUid;
	private String ticketEndWatchUid;
	private String departmentOid;
	private String planStartTime;
	private String planEndTime;
	private String receiveTime;
	private String permissionTime;
	private String workEndTime;
	private String ticketEndTime;
	private String createTime;
	private String flowState;
	private String workMemberCount;
	private String whetherOuterDept;
	private String isCommunicationTicket;
	private String secondbillCount;
	private String isExitSwitch;
	private String whetherSecondbill;
	private String whetherQualified;
	private String whetherNomative;
	private String qualifiedState;
	private String whetherGuardianSign;
	private String guardianUid;
	private String workPrincipalTel;
	private String ticketNo;
	private String createOid;
	private String createUid;
	private String processInsId;
	private String functionLocationId;
	private String functionLocationName;
	private String departmentOname;
	private String optimisticLockVersion;
	private String updateTime;
	private String dataFrom;
	private String bureauCode;
	private String provinceCode;
	private String ticketCounterSignUid;
	private String workMemberUid;
	private String workMemberUname;
	private String workTask;
	private String workPlace;
	private String powerGridFlag;
	private String ticketSignTime;
	private String ticketCounterSignTime;
	private String functionName;
	private String permissionFlag;
	private String major;
	private String outOrIn;
	private String id;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSrcOperationType() {
		return srcOperationType;
	}
	public void setSrcOperationType(String srcOperationType) {
		this.srcOperationType = srcOperationType;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	
	public String getOprationTime() {
		return oprationTime;
	}
	public void setOprationTime(String oprationTime) {
		this.oprationTime = oprationTime;
	}
	public String getOprationId() {
		return oprationId;
	}
	public void setOprationId(String oprationId) {
		this.oprationId = oprationId;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getWorkState() {
		return workState;
	}
	public void setWorkState(String workState) {
		this.workState = workState;
	}
	public String getWorkPrincipalUid() {
		return workPrincipalUid;
	}
	public void setWorkPrincipalUid(String workPrincipalUid) {
		this.workPrincipalUid = workPrincipalUid;
	}
	public String getTicketReceiveUid() {
		return ticketReceiveUid;
	}
	public void setTicketReceiveUid(String ticketReceiveUid) {
		this.ticketReceiveUid = ticketReceiveUid;
	}
	public String getTicketSignUid() {
		return ticketSignUid;
	}
	public void setTicketSignUid(String ticketSignUid) {
		this.ticketSignUid = ticketSignUid;
	}
	public String getWorkPermissionUid() {
		return workPermissionUid;
	}
	public void setWorkPermissionUid(String workPermissionUid) {
		this.workPermissionUid = workPermissionUid;
	}
	public String getWorkEndPermissionUid() {
		return workEndPermissionUid;
	}
	public void setWorkEndPermissionUid(String workEndPermissionUid) {
		this.workEndPermissionUid = workEndPermissionUid;
	}
	public String getWatchUid() {
		return watchUid;
	}
	public void setWatchUid(String watchUid) {
		this.watchUid = watchUid;
	}
	public String getTicketEndWatchUid() {
		return ticketEndWatchUid;
	}
	public void setTicketEndWatchUid(String ticketEndWatchUid) {
		this.ticketEndWatchUid = ticketEndWatchUid;
	}
	public String getDepartmentOid() {
		return departmentOid;
	}
	public void setDepartmentOid(String departmentOid) {
		this.departmentOid = departmentOid;
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
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
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
	public String getTicketEndTime() {
		return ticketEndTime;
	}
	public void setTicketEndTime(String ticketEndTime) {
		this.ticketEndTime = ticketEndTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFlowState() {
		return flowState;
	}
	public void setFlowState(String flowState) {
		this.flowState = flowState;
	}
	public String getWorkMemberCount() {
		return workMemberCount;
	}
	public void setWorkMemberCount(String workMemberCount) {
		this.workMemberCount = workMemberCount;
	}
	public String getWhetherOuterDept() {
		return whetherOuterDept;
	}
	public void setWhetherOuterDept(String whetherOuterDept) {
		this.whetherOuterDept = whetherOuterDept;
	}
	public String getIsCommunicationTicket() {
		return isCommunicationTicket;
	}
	public void setIsCommunicationTicket(String isCommunicationTicket) {
		this.isCommunicationTicket = isCommunicationTicket;
	}
	public String getSecondbillCount() {
		return secondbillCount;
	}
	public void setSecondbillCount(String secondbillCount) {
		this.secondbillCount = secondbillCount;
	}
	public String getIsExitSwitch() {
		return isExitSwitch;
	}
	public void setIsExitSwitch(String isExitSwitch) {
		this.isExitSwitch = isExitSwitch;
	}
	public String getWhetherSecondbill() {
		return whetherSecondbill;
	}
	public void setWhetherSecondbill(String whetherSecondbill) {
		this.whetherSecondbill = whetherSecondbill;
	}
	public String getWhetherQualified() {
		return whetherQualified;
	}
	public void setWhetherQualified(String whetherQualified) {
		this.whetherQualified = whetherQualified;
	}
	public String getWhetherNomative() {
		return whetherNomative;
	}
	public void setWhetherNomative(String whetherNomative) {
		this.whetherNomative = whetherNomative;
	}
	public String getQualifiedState() {
		return qualifiedState;
	}
	public void setQualifiedState(String qualifiedState) {
		this.qualifiedState = qualifiedState;
	}
	public String getWhetherGuardianSign() {
		return whetherGuardianSign;
	}
	public void setWhetherGuardianSign(String whetherGuardianSign) {
		this.whetherGuardianSign = whetherGuardianSign;
	}
	public String getGuardianUid() {
		return guardianUid;
	}
	public void setGuardianUid(String guardianUid) {
		this.guardianUid = guardianUid;
	}
	public String getWorkPrincipalTel() {
		return workPrincipalTel;
	}
	public void setWorkPrincipalTel(String workPrincipalTel) {
		this.workPrincipalTel = workPrincipalTel;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getCreateOid() {
		return createOid;
	}
	public void setCreateOid(String createOid) {
		this.createOid = createOid;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public String getProcessInsId() {
		return processInsId;
	}
	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}
	public String getFunctionLocationId() {
		return functionLocationId;
	}
	public void setFunctionLocationId(String functionLocationId) {
		this.functionLocationId = functionLocationId;
	}
	public String getFunctionLocationName() {
		return functionLocationName;
	}
	public void setFunctionLocationName(String functionLocationName) {
		this.functionLocationName = functionLocationName;
	}
	public String getDepartmentOname() {
		return departmentOname;
	}
	public void setDepartmentOname(String departmentOname) {
		this.departmentOname = departmentOname;
	}
	public String getOptimisticLockVersion() {
		return optimisticLockVersion;
	}
	public void setOptimisticLockVersion(String optimisticLockVersion) {
		this.optimisticLockVersion = optimisticLockVersion;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getDataFrom() {
		return dataFrom;
	}
	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}
	public String getBureauCode() {
		return bureauCode;
	}
	public void setBureauCode(String bureauCode) {
		this.bureauCode = bureauCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getTicketCounterSignUid() {
		return ticketCounterSignUid;
	}
	public void setTicketCounterSignUid(String ticketCounterSignUid) {
		this.ticketCounterSignUid = ticketCounterSignUid;
	}
	public String getWorkMemberUid() {
		return workMemberUid;
	}
	public void setWorkMemberUid(String workMemberUid) {
		this.workMemberUid = workMemberUid;
	}
	public String getWorkMemberUname() {
		return workMemberUname;
	}
	public void setWorkMemberUname(String workMemberUname) {
		this.workMemberUname = workMemberUname;
	}
	public String getWorkTask() {
		return workTask;
	}
	public void setWorkTask(String workTask) {
		this.workTask = workTask;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getPowerGridFlag() {
		return powerGridFlag;
	}
	public void setPowerGridFlag(String powerGridFlag) {
		this.powerGridFlag = powerGridFlag;
	}
	public String getTicketSignTime() {
		return ticketSignTime;
	}
	public void setTicketSignTime(String ticketSignTime) {
		this.ticketSignTime = ticketSignTime;
	}
	public String getTicketCounterSignTime() {
		return ticketCounterSignTime;
	}
	public void setTicketCounterSignTime(String ticketCounterSignTime) {
		this.ticketCounterSignTime = ticketCounterSignTime;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getPermissionFlag() {
		return permissionFlag;
	}
	public void setPermissionFlag(String permissionFlag) {
		this.permissionFlag = permissionFlag;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getOutOrIn() {
		return outOrIn;
	}
	public void setOutOrIn(String outOrIn) {
		this.outOrIn = outOrIn;
	}
	
	
}
