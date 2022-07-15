package com.aptech.student.management.model;

import java.util.Date;

public class User {
	private long id;
	private String username;
	private String password;
	private String mobile;
	private String email;
	private boolean status;
	private Date lastLoginDate;
	private int loginAttempCount;
	private Date createdDate;
	private Date modifiedDate;
	
	
	
	public User(long id, String username, String password, String mobile, String email, boolean status,
			Date lastLoginDate, int loginAttempCount, Date createdDate, Date modifiedDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.mobile = mobile;
		this.email = email;
		this.status = status;
		this.lastLoginDate = lastLoginDate;
		this.loginAttempCount = loginAttempCount;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public int getLoginAttempCount() {
		return loginAttempCount;
	}
	public void setLoginAttempCount(int loginAttempCount) {
		this.loginAttempCount = loginAttempCount;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", mobile=" + mobile
				+ ", email=" + email + ", status=" + status + ", lastLoginDate=" + lastLoginDate + ", loginAttempCount="
				+ loginAttempCount + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + "]";
	}
	
	
}
