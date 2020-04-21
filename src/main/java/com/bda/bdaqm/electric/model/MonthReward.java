package com.bda.bdaqm.electric.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="gzpfx01.BDA_MONTH_REWARD")
public class MonthReward implements Serializable, Comparable<MonthReward>{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1438389417775679950L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select gzpfx01.BDA_MONTH_REWARD_ID.nextVal from system.dual")
    private int monthRewardId;
    private String type;
    private String monthGrade;
    private String levelMoneyMonth;
    private String monthTrans;
    private String monthOperation;
    private String monthTwice;
    private String monthInspection;
    private String monthPower;
	public int getMonthRewardId() {
		return monthRewardId;
	}
	public void setMonthRewardId(int monthRewardId) {
		this.monthRewardId = monthRewardId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMonthGrade() {
		return monthGrade;
	}
	public void setMonthGrade(String monthGrade) {
		this.monthGrade = monthGrade;
	}
	public String getLevelMoneyMonth() {
		return levelMoneyMonth;
	}
	public void setLevelMoneyMonth(String levelMoneyMonth) {
		this.levelMoneyMonth = levelMoneyMonth;
	}
	public String getMonthTrans() {
		return monthTrans;
	}
	public void setMonthTrans(String monthTrans) {
		this.monthTrans = monthTrans;
	}
	public String getMonthOperation() {
		return monthOperation;
	}
	public void setMonthOperation(String monthOperation) {
		this.monthOperation = monthOperation;
	}
	public String getMonthTwice() {
		return monthTwice;
	}
	public void setMonthTwice(String monthTwice) {
		this.monthTwice = monthTwice;
	}
	public String getMonthInspection() {
		return monthInspection;
	}
	public void setMonthInspection(String monthInspection) {
		this.monthInspection = monthInspection;
	}
	public String getMonthPower() {
		return monthPower;
	}
	public void setMonthPower(String monthPower) {
		this.monthPower = monthPower;
	}
	
	@Override
	public String toString() {
		return "MonthReward [monthRewardId=" + monthRewardId + ", type=" + type + ", monthGrade=" + monthGrade
				+ ", levelMoneyMonth=" + levelMoneyMonth + ", monthTrans=" + monthTrans + ", monthOperation="
				+ monthOperation + ", monthTwice=" + monthTwice + ", monthInspection=" + monthInspection
				+ ", monthPower=" + monthPower + "]";
	}
	@Override
	public int compareTo(MonthReward o) {
		int i = Integer.parseInt(this.getLevelMoneyMonth()) - Integer.parseInt(o.getLevelMoneyMonth());//先按照奖励金额排序  
		return i;
	}
}
