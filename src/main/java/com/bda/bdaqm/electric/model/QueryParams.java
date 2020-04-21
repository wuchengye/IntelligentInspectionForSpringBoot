package com.bda.bdaqm.electric.model;

/**
 * 查询条件java类
 *
 */
public class QueryParams {

	private String batch;                   //校验批次
	private String ticketId;                //工作票ID
	private String stationLineName;         //站名/线路
	private String ticketType;              //工作票
	private String department;              //单位和班组
	private String workPrincipal;           //工作负责人
	private String ticketSigner;			//工作票签发人
	private String workLicensor;			//工作许可人
	private String workEndLicensor;         //工作终结许可人
	private String ticketCounterSigner;     //工作票会签人
	private String planStartTime;           //计划开始时间
	private String planEndTime;             //计划结束时间
	private String isOuterDept;             //外来单位
	private String ticketNo;                //工作票票号
	
	private String orgIds;
	private String level;
	
	private String ticketEndTime1;          //工作票终结时间
	private String ticketEndTime2;
	
	private String permissionTime1;         //工作许可时间
	private String permissionTime2;
	
	private String checkResult;             //合格性校验结果
	private String standardResult;          //规范性校验结果
	
	//父容器条件
	private String p_batch;
	private String p_ticketType;
	private String p_department;
	private String p_workPrincipal;
	private String p_ticketSigner;
	private String p_workLicensor;
	private String p_ticketEndTime1;
	private String p_ticketEndTime2;
	private String p_orgids;
	
	///////////////////
	public String getTicketType() {
		return ticketType;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getWorkPrincipal() {
		return workPrincipal;
	}
	public void setWorkPrincipal(String workPrincipal) {
		this.workPrincipal = workPrincipal;
	}
	public String getTicketSigner() {
		return ticketSigner;
	}
	public void setTicketSigner(String ticketSigner) {
		this.ticketSigner = ticketSigner;
	}
	public String getWorkLicensor() {
		return workLicensor;
	}
	public void setWorkLicensor(String workLicensor) {
		this.workLicensor = workLicensor;
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
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getStationLineName() {
		return stationLineName;
	}
	public void setStationLineName(String stationLineName) {
		this.stationLineName = stationLineName;
	}
	public String getWorkEndLicensor() {
		return workEndLicensor;
	}
	public void setWorkEndLicensor(String workEndLicensor) {
		this.workEndLicensor = workEndLicensor;
	}
	public String getTicketCounterSigner() {
		return ticketCounterSigner;
	}
	public void setTicketCounterSigner(String ticketCounterSigner) {
		this.ticketCounterSigner = ticketCounterSigner;
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
	public String getIsOuterDept() {
		return isOuterDept;
	}
	public void setIsOuterDept(String isOuterDept) {
		this.isOuterDept = isOuterDept;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getTicketEndTime1() {
		return ticketEndTime1;
	}
	public void setTicketEndTime1(String ticketEndTime1) {
		this.ticketEndTime1 = ticketEndTime1;
	}
	public String getTicketEndTime2() {
		return ticketEndTime2;
	}
	public void setTicketEndTime2(String ticketEndTime2) {
		this.ticketEndTime2 = ticketEndTime2;
	}
	public String getP_batch() {
		return p_batch;
	}
	public void setP_batch(String p_batch) {
		this.p_batch = p_batch;
	}
	public String getP_ticketType() {
		return p_ticketType;
	}
	public void setP_ticketType(String p_ticketType) {
		this.p_ticketType = p_ticketType;
	}
	public String getP_department() {
		return p_department;
	}
	public void setP_department(String p_department) {
		this.p_department = p_department;
	}
	public String getP_workPrincipal() {
		return p_workPrincipal;
	}
	public void setP_workPrincipal(String p_workPrincipal) {
		this.p_workPrincipal = p_workPrincipal;
	}
	public String getP_ticketSigner() {
		return p_ticketSigner;
	}
	public void setP_ticketSigner(String p_ticketSigner) {
		this.p_ticketSigner = p_ticketSigner;
	}
	public String getP_workLicensor() {
		return p_workLicensor;
	}
	public void setP_workLicensor(String p_workLicensor) {
		this.p_workLicensor = p_workLicensor;
	}
	public String getP_ticketEndTime1() {
		return p_ticketEndTime1;
	}
	public void setP_ticketEndTime1(String p_ticketEndTime1) {
		this.p_ticketEndTime1 = p_ticketEndTime1;
	}
	public String getP_ticketEndTime2() {
		return p_ticketEndTime2;
	}
	public void setP_ticketEndTime2(String p_ticketEndTime2) {
		this.p_ticketEndTime2 = p_ticketEndTime2;
	}
	public String getPermissionTime1() {
		return permissionTime1;
	}
	public void setPermissionTime1(String permissionTime1) {
		this.permissionTime1 = permissionTime1;
	}
	public String getPermissionTime2() {
		return permissionTime2;
	}
	public void setPermissionTime2(String permissionTime2) {
		this.permissionTime2 = permissionTime2;
	}
	public String getOrgIds() {
		return orgIds;
	}
	public void setOrgIds(String orgIds) {
		if(null != orgIds && orgIds.length() > 0){
			this.orgIds = orgIds.replaceAll("&amp;#39;", "'");
		}else{
			this.orgIds = orgIds;
		}
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getP_orgids() {
		return p_orgids;
	}
	public void setP_orgids(String p_orgids) {
		if(null != p_orgids && p_orgids.length() > 0){
			this.p_orgids = p_orgids.replaceAll("&amp;#39;", "'");
		}else{
			this.p_orgids = p_orgids;
		}
	}
	
	
}
