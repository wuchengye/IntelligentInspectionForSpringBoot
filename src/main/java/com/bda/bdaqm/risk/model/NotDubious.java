package com.bda.bdaqm.risk.model;

import javax.persistence.Table;

@Table(name="bdaqm_not_dubious")
public class NotDubious {
	
	private String sessionId;
	private String fileName;
	private String filePath;
	private String contactTime;
	private String recordDate;
	private String dataUpdateTime;
	private String recordStartDate;
	private String recordEndDate;
	
	
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
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
	public String getContactTime() {
		return contactTime;
	}
	public void setContactTime(String contactTime) {
		this.contactTime = contactTime;
	}
	
	
	

}
