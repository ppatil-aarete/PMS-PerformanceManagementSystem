package com.thirdi.pms.admin;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Recipient {

	private String firstName;
	private String lastName;
	private String email;
	private String role;
	private String status;
	private Map<String,Object> parameterMap;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String,Object> getParameterMap() {
		return parameterMap;
	}
	
	public void setParameterMap(Map<String,Object> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
}
