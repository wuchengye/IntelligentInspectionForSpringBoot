package com.bda.bdaqm.electric.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="gzpfx01.BDA_YEAR_REWARD")
public class YearReward implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3714465601809154698L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select gzpfx01.BDA_YEAR_REWARD_ID.nextVal from system.dual")
    private int monthRewardId;
    private String type;
    private String yearGrade;
    private String levelMoneyYear;
    private String yearTrans;
    private String yearOperation;
    private String yearTwice;
    private String yearInspection;
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
	public String getYearGrade() {
		return yearGrade;
	}
	public void setYearGrade(String yearGrade) {
		this.yearGrade = yearGrade;
	}
	public String getLevelMoneyYear() {
		return levelMoneyYear;
	}
	public void setLevelMoneyYear(String levelMoneyYear) {
		this.levelMoneyYear = levelMoneyYear;
	}
	public String getYearTrans() {
		return yearTrans;
	}
	public void setYearTrans(String yearTrans) {
		this.yearTrans = yearTrans;
	}
	public String getYearOperation() {
		return yearOperation;
	}
	public void setYearOperation(String yearOperation) {
		this.yearOperation = yearOperation;
	}
	public String getYearTwice() {
		return yearTwice;
	}
	public void setYearTwice(String yearTwice) {
		this.yearTwice = yearTwice;
	}
	public String getYearInspection() {
		return yearInspection;
	}
	public void setYearInspection(String yearInspection) {
		this.yearInspection = yearInspection;
	}
	public String getYearPower() {
		return yearPower;
	}
	public void setYearPower(String yearPower) {
		this.yearPower = yearPower;
	}
	private String yearPower;
	@Override
	public String toString() {
		return "YearReward [ " + "levelMoneyYear=" + levelMoneyYear + "]";
	}

	
}
