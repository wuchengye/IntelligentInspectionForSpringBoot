package com.bda.bdaqm.risk.model;

import javax.persistence.Table;

@Table(name="bdaqm_data_management")
public class DataManage {
	
	private String dataDate;
	private String recordCompression;
	private String recordVolume;
	private String qualityStartTime;
	
	
	public String getDataDate() {
		return dataDate;
	}
	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}
	public String getRecordCompression() {
		return recordCompression;
	}
	public void setRecordCompression(String recordCompression) {
		this.recordCompression = recordCompression;
	}
	public String getRecordVolume() {
		return recordVolume;
	}
	public void setRecordVolume(String recordVolume) {
		this.recordVolume = recordVolume;
	}
	public String getQualityStartTime() {
		return qualityStartTime;
	}
	public void setQualityStartTime(String qualityStartTime) {
		this.qualityStartTime = qualityStartTime;
	}
	
	
}
