package com.bda.bdaqm.electric.model;

public class OrganizationVo {
	private String id;
	private String organizationName;
	private String organizationOid;
	private String superorganizationId;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrganizationOid() {
		return organizationOid;
	}
	public void setOrganizationOid(String organizationOid) {
		this.organizationOid = organizationOid;
	}
	public String getSuperorganizationId() {
		return superorganizationId;
	}
	public void setSuperorganizationId(String superorganizationId) {
		this.superorganizationId = superorganizationId;
	}
}
