package com.thirdi.pms.admin;

public enum RoleStatus {

	user(0), hrAdmin(1), admin(2);
	
	private Integer roleStatus;

	public Integer getRoleStatus() {
		return this.roleStatus;
	}

	private RoleStatus(Integer roleStatus) {
		this.roleStatus = roleStatus;
	}
}