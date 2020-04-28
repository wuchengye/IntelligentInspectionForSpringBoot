package com.bda.bdaqm.admin.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bda.admin.model.User.WithPasswordView;
import com.bda.admin.model.User.WithoutPasswordView;
import com.fasterxml.jackson.annotation.JsonView;

@Table(name = "bda_auth_user")
public class User implements Serializable{

	private static final long serialVersionUID = -4567119729368986098L;
	public static String FIELD_ACCOUNT = "account";
	public static String FIELD_USER_NAME = "userName";
	
	private Long userId;
	private String status;
	private String password;
	private String userName;
	private String account;
	private String orgId;
	
	private String createTime;
	private String createUserAccount;
	
	private String updateTime;
	private String updateUserAccount;

	public String toString() {
		return this.account;
	}
	public String getUserId() {
		if(userId == null){
			return "";
		}
		return userId+"";
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonView({WithPasswordView.class})
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonView({WithoutPasswordView.class})
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOrgId() {
		return this.orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateUserAccount() {
		return createUserAccount;
	}

	public void setCreateUserAccount(String createUserAccount) {
		this.createUserAccount = createUserAccount;
	}

	public String getUpdateUserAccount() {
		return updateUserAccount;
	}

	public void setUpdateUserAccount(String updateUserAccount) {
		this.updateUserAccount = updateUserAccount;
	}
	
}
