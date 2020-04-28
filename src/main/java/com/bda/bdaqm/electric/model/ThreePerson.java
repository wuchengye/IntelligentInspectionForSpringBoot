package com.bda.bdaqm.electric.model;

import java.io.Serializable;

public class ThreePerson implements Serializable, Comparable<ThreePerson>{

	private static final long serialVersionUID = -7018801770564020327L;
	private String idd;
	private String unitName;
	private String deptmentName;
	private String groupName;
	private String specialty;
	private String classes;
	private String personName;
	private String threeType;
	private String ticketSign;
	private String workPrincipal;
	private String workPermission;
	private String approval;
	private String rigthTime;
	private String remark;
	private String theLast;
	private String keepTicketAmount;

	
	
	public String getIdd() {
		return idd;
	}
	public void setIdd(String idd) {
		this.idd = idd;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getDeptmentName() {
		return deptmentName;
	}
	public void setDeptmentName(String deptmentName) {
		this.deptmentName = deptmentName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getThreeType() {
		return threeType;
	}
	public void setThreeType(String threeType) {
		if("1".equals(this.ticketSign)){
			threeType = "工作票签发人";
		}
		if("1".equals(this.workPrincipal)){
			if(null!=threeType&&threeType.length()>0)
				threeType+="、工作负责人";
			else 
				threeType="工作负责人";
		}
		if("1".equals(this.workPermission)){
			if(null!=threeType&&threeType.length()>0)
				threeType+="、工作许可人";
			else 
				threeType="工作许可人";
		}
		this.threeType = threeType;
	}
	public String getTicketSign() {
		return ticketSign;
	}
	public void setTicketSign(String ticketSign) {
		this.ticketSign = ticketSign;
	}
	public String getWorkPrincipal() {
		return workPrincipal;
	}
	public void setWorkPrincipal(String workPrincipal) {
		this.workPrincipal = workPrincipal;
	}
	public String getWorkPermission() {
		return workPermission;
	}
	public void setWorkPermission(String workPermission) {
		this.workPermission = workPermission;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getRigthTime() {
		return rigthTime;
	}
	public void setRigthTime(String rigthTime) {
		this.rigthTime = rigthTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTheLast() {
		return theLast;
	}
	public void setTheLast(String theLast) {
		this.theLast = theLast;
	}
	public String getKeepTicketAmount() {
		return keepTicketAmount;
	}
	public void setKeepTicketAmount(String keepTicketAmount) {
		this.keepTicketAmount = keepTicketAmount;
	}
	@Override
	public int compareTo(ThreePerson o) {
		// TODO Auto-generated method stub
		int thisKeepTicketAmount=Integer.parseInt(this.getKeepTicketAmount());
		int otherKeepTicketAmount=Integer.parseInt(o.getKeepTicketAmount());
	    if(thisKeepTicketAmount==otherKeepTicketAmount) {
	    	return 0;
	    }else if(thisKeepTicketAmount>otherKeepTicketAmount) {
	    	return 1;
	    }
	    else return -1;
	}
	
	
}
