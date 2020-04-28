package com.bda.bdaqm.admin.model;

import java.io.Serializable;

import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Table(name="bda_auth_role_permission")
public class RolePermission implements Serializable{

	private static final long serialVersionUID = 5879130031866952893L;
	public static final String FIELD_ROLE_ID = "roleId";
	public static final String FIELD_ACTION = "action";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_PRIMKEY = "primkey";
	private String roleId;
	private String action;
	private String name;
	private String primkey;
	private String permission;

	public RolePermission() {
	}

	public RolePermission(String roleId) {
		this(roleId, (String) null, (String) null, (String) null);
	}

	public RolePermission(String roleId, String action, String name, String primkey) {
		this.roleId = roleId;
		this.action = action;
		this.name = name;
		this.primkey = primkey;
		if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(action) && StringUtils.isNotEmpty(primkey)) {
			this.permission = String.format("%s:%s:%s", new Object[]{name, action, primkey});
		}

	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimkey() {
		return this.primkey;
	}

	public void setPrimkey(String primkey) {
		this.primkey = primkey;
	}

	public boolean equals(Object obj) {
		return this == obj || obj instanceof RolePermission && !StringUtils.isEmpty(this.permission)
				&& this.permission.equals(((RolePermission) obj).getPermission());
	}

	public int hashCode() {
		return this.permission.hashCode() * 31 + 17;
	}
	
}
