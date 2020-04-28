package com.bda.bdaqm.admin.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="bda_login_record")
public class LogRecord implements Serializable{

	private static final long serialVersionUID = -2691425284400372769L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select gzpfx01.seq_record_log_id.nextval from system.dual")
	private String id;
	private String userId;
	private String loginTime;
	private String logoutTime;
	
	
	public LogRecord(){
	}
	
	public LogRecord(String userId, String loginTime, String logoutTime) {
		super();
		this.userId = userId;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}
	
	
	
}
