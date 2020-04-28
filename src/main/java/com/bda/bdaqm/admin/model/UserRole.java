package com.bda.bdaqm.admin.model;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="bda_auth_user_role")
public class UserRole {

	@Id
	private String roleId;
	@Id
	private String userId;

	public UserRole() {
	}

	public UserRole(String roleId, String userId) {
		this.roleId = roleId;
		this.userId = userId;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
