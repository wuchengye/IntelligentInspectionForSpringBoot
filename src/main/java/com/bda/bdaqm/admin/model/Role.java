package com.bda.bdaqm.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Mars
 */
@Table(name = "bda_auth_role")
public class Role implements Serializable{
	
	private static final long serialVersionUID = -1464269548362947526L;

	public static final String FIELD_ROLE_NAME = "roleName";
	
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select gzpfx01.seq_role_id.nextval from system.dual")
	@Id
	private String roleId;
	private String roleName;
	private String description;
	private String createTime;
	private String createUserAccount;
	
	private String updateTime;
	private String updateUserAccount;

	private Integer departmentId;
	private Integer ability;

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getAbility() {
		return ability;
	}

	public void setAbility(Integer ability) {
		this.ability = ability;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public String getDescription() {
		return this.description;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserAccount() {
		return updateUserAccount;
	}

	public void setUpdateUserAccount(String updateUserAccount) {
		this.updateUserAccount = updateUserAccount;
	}

	public String getCreateUserAccount() {
		return createUserAccount;
	}

	public void setCreateUserAccount(String createUserAccount) {
		this.createUserAccount = createUserAccount;
	}
	
}
