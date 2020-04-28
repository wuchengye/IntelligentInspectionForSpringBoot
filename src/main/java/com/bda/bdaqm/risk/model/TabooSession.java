package com.bda.bdaqm.risk.model;

import javax.persistence.Table;

@Table(name="bdaqm_service_taboo_language")
public class TabooSession {
	
	private String sessionId;
	private String fileName;
	private String filePath;
	private String keyword;
	private String recordDate;
	private String ismute45s;
	private String muteLocation;
	private String contactTime;
	private String checkStatus;
	private String checkAccounts;
	private String personIsProblem;
	private String transferIstrue;
	private String problemDescribe;
	private String trueTransferContent;
	private String remark;
	private String dataUpdateTime;
	private String recordStartDate;
	private String recordEndDate;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getIsmute45s() {
		return ismute45s;
	}
	public void setIsmute45s(String ismute45s) {
		this.ismute45s = ismute45s;
	}
	public String getMuteLocation() {
		return muteLocation;
	}
	public void setMuteLocation(String muteLocation) {
		this.muteLocation = muteLocation;
	}
	public String getContactTime() {
		return contactTime;
	}
	public void setContactTime(String contactTime) {
		this.contactTime = contactTime;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getCheckAccounts() {
		return checkAccounts;
	}
	public void setCheckAccounts(String checkAccounts) {
		this.checkAccounts = checkAccounts;
	}
	public String getPersonIsProblem() {
		return personIsProblem;
	}
	public void setPersonIsProblem(String personIsProblem) {
		this.personIsProblem = personIsProblem;
	}
	public String getTransferIstrue() {
		return transferIstrue;
	}
	public void setTransferIstrue(String transferIstrue) {
		this.transferIstrue = transferIstrue;
	}
	public String getProblemDescribe() {
		return problemDescribe;
	}
	public void setProblemDescribe(String problemDescribe) {
		this.problemDescribe = problemDescribe;
	}
	public String getTrueTransferContent() {
		return trueTransferContent;
	}
	public void setTrueTransferContent(String trueTransferContent) {
		this.trueTransferContent = trueTransferContent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDataUpdateTime() {
		return dataUpdateTime;
	}
	public void setDataUpdateTime(String dataUpdateTime) {
		this.dataUpdateTime = dataUpdateTime;
	}
	public String getRecordStartDate() {
		return recordStartDate;
	}
	public void setRecordStartDate(String recordStartDate) {
		this.recordStartDate = recordStartDate;
	}
	public String getRecordEndDate() {
		return recordEndDate;
	}
	public void setRecordEndDate(String recordEndDate) {
		this.recordEndDate = recordEndDate;
	}
	

}
