package com.bda.bdaqm.admin.bean;


import java.util.List;

import com.bda.bdaqm.admin.model.RolePermission;


public class PermissionBean {
	private String roleId;
	private List<RolePermission> addList;
	private List<RolePermission> delList;
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public List<RolePermission> getAddList() {
		return addList;
	}
	public void setAddList(List<RolePermission> addList) {
		this.addList = addList;
	}
	public List<RolePermission> getDelList() {
		return delList;
	}
	public void setDelList(List<RolePermission> delList) {
		this.delList = delList;
	}
	
}
