package com.thirdi.pms.admin;

import org.springframework.stereotype.Component;

@Component
public class RecipientMail {
	private String selfEmail;
	private String apprEmail;
	private String revEmail;
	
	public String getSelfEmail() {
		return selfEmail;
	}
	
	public void setSelfEmail(String selfEmail) {
		this.selfEmail = selfEmail;
	}
	
	public String getApprEmail() {
		return apprEmail;
	}
	
	public void setApprEmail(String apprEmail) {
		this.apprEmail = apprEmail;
	}
	
	public String getRevEmail() {
		return revEmail;
	}
	
	public void setRevEmail(String revEmail) {
		this.revEmail = revEmail;
	}
}
