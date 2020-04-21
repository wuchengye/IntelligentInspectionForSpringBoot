package com.bda.bdaqm.electric.model;

import java.io.Serializable;
import java.util.List;

public class Statistics implements Serializable,Comparable<Statistics>{

	private static final long serialVersionUID = -7496504875919896188L;
	
	private int bdaStatisticsId;
	private String unit;
	private String unqualified;
	private String unstandard;
	private String standard;
	private String qualified;
	private String ticketTotal;
	private String passRate;
	private String standardRate;
	private String unMarked;
	private String liveWorking;
	private String lowVoltage;
	private String urgentRepairs;
	private String writtenForm;
	private String dispatching;
	private String stationOne;
	private String stationTwo;
	private String stationThree;
	private String lineOne;
	private String lineTwo;
	private String fireOne;
	private String fireTwo;
	private String month;
	private String departmentOid;
				   
	private String departmentOname;
	private String depatrmentOnameOld;
	
	private String oidlist;
	private String baseId;
	private String total;
	
	//单位名的显示顺序
	private int unitOrder;
	
	
	
	public String getUnMarked() {
		return unMarked;
	}
	public void setUnMarked(String unMarked) {
		this.unMarked = unMarked;
	}
	public String getTicketTotal() {
		return ticketTotal;
	}
	public void setTicketTotal(String ticketTotal) {
		this.ticketTotal = ticketTotal;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	
	public String getOidlist() {
		return oidlist;
	}
	public void setOidlist(String oidlist) {
		this.oidlist = oidlist;
	}
	public String getDepatrmentOnameOld() {
		return depatrmentOnameOld;
	}
	public void setDepatrmentOnameOld(String depatrmentOnameOld) {
		this.depatrmentOnameOld = depatrmentOnameOld;
	}
	public String getDepartmentOid() {
		return departmentOid;
	}
	public void setDepartmentOid(String departmentOid) {
		this.departmentOid = departmentOid;
	}
	public String getDepartmentOname() {
		return departmentOname;
	}
	public void setDepartmentOname(String departmentOname) {
		this.departmentOname = departmentOname;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getBdaStatisticsId() {
		return bdaStatisticsId;
	}
	public void setBdaStatisticsId(int bdaStatisticsId) {
		this.bdaStatisticsId = bdaStatisticsId;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getUnqualified() {
		return unqualified;
	}
	public void setUnqualified(String unqualified) {
		this.unqualified = unqualified;
	}
	public String getUnstandard() {
		return unstandard;
	}
	public void setUnstandard(String unstandard) {
		this.unstandard = unstandard;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getQualified() {
		return qualified;
	}
	public void setQualified(String qualified) {
		this.qualified = qualified;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPassRate() {
		return passRate;
	}
	public void setPassRate(String passRate) {
		this.passRate = passRate;
	}
	public String getStandardRate() {
		return standardRate;
	}
	public void setStandardRate(String standardRate) {
		this.standardRate = standardRate;
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
	public String getDispatching() {
		return dispatching;
	}
	public void setDispatching(String dispatching) {
		this.dispatching = dispatching;
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
	
	public int getUnitOrder() {
		return unitOrder;
	}
	public void setUnitOrder(int unitOrder) {
		this.unitOrder=unitOrder;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
		if(obj instanceof Statistics) {
			Statistics ks = (Statistics) obj;
			if(this.month.equals(ks.month)&&this.departmentOname.equals(ks.departmentOname)) {
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
//	@Override
//	public boolean equals(Object obj) {
//		if(obj instanceof Statistics) {
//			Statistics ks = (Statistics) obj;
//			if(this.month.equals(ks.month)&&this.departmentOname.equals(ks.departmentOname)&&this.departmentOid.equals(ks.departmentOid)) {
//				return true;
//			}else {
//				return false;
//			}
//		}
//		return false;
//	}
	@Override
	public int hashCode() {
		String str = departmentOname + month;
		return str.hashCode();
	}
@Override
public int compareTo(Statistics other) {
	// TODO Auto-generated method stub
	if(this.getUnitOrder()>=other.getUnitOrder()) {
		return 1;
	}
	return -1;
}
	
}
