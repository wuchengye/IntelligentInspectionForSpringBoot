package com.bda.bdaqm.electric.model;

import java.io.Serializable;

/**
 * 工作票类型统计实体类
 *
 */
public class TicketTypeStatistic implements Serializable{

	private static final long serialVersionUID = -7777994427493917755L;
	
	private String time;				//时间月份
	private String stationOne;			//厂站一种票
	private String stationTwo;			//厂站二种票
	private String stationThree;		//厂站三种票
	private String lineOne;				//线路一种票	
	private String lineTwo;				//线路二种票
	private String fireOne;				//动火一种票
	private String fireTwo;				//动火二种票
	private String liveWorking;			//带电作业工作票
	private String lowVoltage;			//低压配电网工作票
	private String urgentRepairs;		//紧急抢修工作票
	private String qualified;			//合格的工作票数量
	private String noQualified;			//不合格的工作票数量
	private String standard;			//规范的工作票数量
	private String noStandard;			//不规范的工作票数量
	private String principal;			//局内负责人
	private String constructUnit;		//施工单位	
	
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStationOne() {
		return stationOne;
	}
	public void setStationOne(String stationOne) {
		this.stationOne = stationOne;
	}
	public String getStationTwo() {
		return stationTwo;
	}
	public void setStationTwo(String stationTwo) {
		this.stationTwo = stationTwo;
	}
	public String getStationThree() {
		return stationThree;
	}
	public void setStationThree(String stationThree) {
		this.stationThree = stationThree;
	}
	public String getLineOne() {
		return lineOne;
	}
	public void setLineOne(String lineOne) {
		this.lineOne = lineOne;
	}
	public String getLineTwo() {
		return lineTwo;
	}
	public void setLineTwo(String lineTwo) {
		this.lineTwo = lineTwo;
	}
	public String getFireOne() {
		return fireOne;
	}
	public void setFireOne(String fireOne) {
		this.fireOne = fireOne;
	}
	public String getFireTwo() {
		return fireTwo;
	}
	public void setFireTwo(String fireTwo) {
		this.fireTwo = fireTwo;
	}
	public String getLiveWorking() {
		return liveWorking;
	}
	public void setLiveWorking(String liveWorking) {
		this.liveWorking = liveWorking;
	}
	public String getLowVoltage() {
		return lowVoltage;
	}
	public void setLowVoltage(String lowVoltage) {
		this.lowVoltage = lowVoltage;
	}
	public String getUrgentRepairs() {
		return urgentRepairs;
	}
	public void setUrgentRepairs(String urgentRepairs) {
		this.urgentRepairs = urgentRepairs;
	}
	public String getQualified() {
		return qualified;
	}
	public void setQualified(String qualified) {
		this.qualified = qualified;
	}
	public String getNoQualified() {
		return noQualified;
	}
	public void setNoQualified(String noQualified) {
		this.noQualified = noQualified;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getNoStandard() {
		return noStandard;
	}
	public void setNoStandard(String noStandard) {
		this.noStandard = noStandard;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getConstructUnit() {
		return constructUnit;
	}
	public void setConstructUnit(String constructUnit) {
		this.constructUnit = constructUnit;
	}
	
	
	
	
}
