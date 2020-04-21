package com.bda.bdaqm.electric.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="gzpfx01.BDA_HOUR_MODULUS")
public class HourModulus implements Serializable{
	
	private static final long serialVersionUID = 2099655091012483475L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select BDA_MODULUS_ID.nextVal from system.dual")
	private int hourModulusId;
	private String type;
	private String lineOne;
	private String lineTwo;
	private String fireOne;
	private String fireTwo;
	private String liveWorking;
	private String lowVoltage;
	private String urgentRepairs;
	private String writtenForm;
	private String stationOne;
	private String stationTwo;
	private String stationThree;
	private String special;
	
	/////////////////////////////
	public int getHourModulusId() {
		return hourModulusId;
	}
	public void setHourModulusId(int hourModulusId) {
		this.hourModulusId = hourModulusId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getWrittenForm() {
		return writtenForm;
	}
	public void setWrittenForm(String writtenForm) {
		this.writtenForm = writtenForm;
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
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}

}
