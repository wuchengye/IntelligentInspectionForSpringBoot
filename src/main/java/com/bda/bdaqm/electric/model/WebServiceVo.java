package com.bda.bdaqm.electric.model;

import java.io.Serializable;

import javax.persistence.Table;

@Table(name="bda_webservice")
public class WebServiceVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 903252763909547302L;
	
	
	private String ticketId;   					//工作票ID
	private String ticketType;					//工作票类型
	private String workTask;					//工作任务
	private String station;						//厂站名称
	private String pullBreaker;					//应拉断路器(开关)
	private String pullSwitch;					//应拉隔离开关(刀闸)
	private String dcpowerLowpCircle;			//应投切的相关直流电源（空气开关、熔断器、连接片）、低压及二次回路
	private String switchEarthwireInsulation;	//应合上的接地刀闸 （双重名称或编号）、装设的接地线（装设地点）、应设绝 缘挡板
	private String billboard;					//应设遮栏、应挂标示牌（位置）
	private String safeotherCare;				//其他安全措施和注意事项
	private String highpDeviceState;
	private String powerCircleState;
	private String requiredSafty;
	private String pullBreakerSwitch;
	private String closeSwitchEarthwire;
	private String saftyAndCare;
	private String runningSafty;
	private String repareSafty;
	
	public String getHighpDeviceState() {
		return highpDeviceState;
	}
	public void setHighpDeviceState(String highpDeviceState) {
		this.highpDeviceState = highpDeviceState;
	}
	public String getPowerCircleState() {
		return powerCircleState;
	}
	public void setPowerCircleState(String powerCircleState) {
		this.powerCircleState = powerCircleState;
	}
	public String getRequiredSafty() {
		return requiredSafty;
	}
	public void setRequiredSafty(String requiredSafty) {
		this.requiredSafty = requiredSafty;
	}
	public String getPullBreakerSwitch() {
		return pullBreakerSwitch;
	}
	public void setPullBreakerSwitch(String pullBreakerSwitch) {
		this.pullBreakerSwitch = pullBreakerSwitch;
	}
	public String getCloseSwitchEarthwire() {
		return closeSwitchEarthwire;
	}
	public void setCloseSwitchEarthwire(String closeSwitchEarthwire) {
		this.closeSwitchEarthwire = closeSwitchEarthwire;
	}
	public String getSaftyAndCare() {
		return saftyAndCare;
	}
	public void setSaftyAndCare(String saftyAndCare) {
		this.saftyAndCare = saftyAndCare;
	}
	public String getRunningSafty() {
		return runningSafty;
	}
	public void setRunningSafty(String runningSafty) {
		this.runningSafty = runningSafty;
	}
	public String getRepareSafty() {
		return repareSafty;
	}
	public void setRepareSafty(String repareSafty) {
		this.repareSafty = repareSafty;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getPullBreaker() {
		return pullBreaker;
	}
	public void setPullBreaker(String pullBreaker) {
		this.pullBreaker = pullBreaker;
	}
	public String getPullSwitch() {
		return pullSwitch;
	}
	public void setPullSwitch(String pullSwitch) {
		this.pullSwitch = pullSwitch;
	}
	public String getDcpowerLowpCircle() {
		return dcpowerLowpCircle;
	}
	public void setDcpowerLowpCircle(String dcpowerLowpCircle) {
		this.dcpowerLowpCircle = dcpowerLowpCircle;
	}
	public String getSwitchEarthwireInsulation() {
		return switchEarthwireInsulation;
	}
	public void setSwitchEarthwireInsulation(String switchEarthwireInsulation) {
		this.switchEarthwireInsulation = switchEarthwireInsulation;
	}
	public String getBillboard() {
		return billboard;
	}
	public void setBillboard(String billboard) {
		this.billboard = billboard;
	}
	public String getSafeotherCare() {
		return safeotherCare;
	}
	public void setSafeotherCare(String safeotherCare) {
		this.safeotherCare = safeotherCare;
	}
	public String getWorkTask() {
		return workTask;
	}
	public void setWorkTask(String workTask) {
		this.workTask = workTask;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	
	

}
