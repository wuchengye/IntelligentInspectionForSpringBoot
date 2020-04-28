package com.bda.bdaqm.electric.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="bda_work_ticket")
public class WorkTicket implements Serializable{

	private static final long serialVersionUID = -5539507554921996437L;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_BDA_WORKTICKET_ID.nextVal from dual")
	private String id;
	private String ticketId;
	private String ticketType;
	private String title;
	private String serialNo;
	private String charger;
	private String unit;
	private String planStartTime;
	private String planEndTime;
	private String totalNum;
	private String worker;
	private String task;
	private String workPlace;
	private String breaker;
	private String disconecter;
	private String content_1;
	private String content_2;
	private String needSetSign;
	private String isLanding;
	private String needSecondProcess;
	private String otherAttention;
	private String measureOrder;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getCharger() {
		return charger;
	}
	public void setCharger(String charger) {
		this.charger = charger;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getWorkPlace() {
		return workPlace;
	}
	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}
	public String getBreaker() {
		return breaker;
	}
	public void setBreaker(String breaker) {
		this.breaker = breaker;
	}
	public String getDisconecter() {
		return disconecter;
	}
	public void setDisconecter(String disconecter) {
		this.disconecter = disconecter;
	}
	public String getContent_1() {
		return content_1;
	}
	public void setContent_1(String content_1) {
		this.content_1 = content_1;
	}
	public String getContent_2() {
		return content_2;
	}
	public void setContent_2(String content_2) {
		this.content_2 = content_2;
	}
	public String getNeedSetSign() {
		return needSetSign;
	}
	public void setNeedSetSign(String needSetSign) {
		this.needSetSign = needSetSign;
	}
	public String getIsLanding() {
		return isLanding;
	}
	public void setIsLanding(String isLanding) {
		this.isLanding = isLanding;
	}
	public String getNeedSecondProcess() {
		return needSecondProcess;
	}
	public void setNeedSecondProcess(String needSecondProcess) {
		this.needSecondProcess = needSecondProcess;
	}
	public String getOtherAttention() {
		return otherAttention;
	}
	public void setOtherAttention(String otherAttention) {
		this.otherAttention = otherAttention;
	}
	public String getMeasureOrder() {
		return measureOrder;
	}
	public void setMeasureOrder(String measureOrder) {
		this.measureOrder = measureOrder;
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
	
	
}
