package com.thirdi.pms.login.model;

import com.thirdi.pms.admin.RoleStatus;

public class LoginUser{
	
	private String username;
	private String password;
	private int apprempid;
	private int empid;
	private String firstname;
	private String emailid;
	private String role;
	private String function;
	private RoleStatus roleStatus;
	
	
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
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
	
	public int getApprempid() {
		return apprempid;
	}
	
	public void setApprempid(int apprempid) {
		this.apprempid = apprempid;
	}
	
	public int getEmpid() {
		return empid;
	}
	
	public void setEmpid(int empid) {
		this.empid = empid;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getEmailid() {
		return emailid;
	}
	
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public RoleStatus getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(RoleStatus roleStatus) {
		this.roleStatus = roleStatus;
	}

	
}

