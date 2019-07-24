package com.thirdi.pms.admin;

import java.util.Map;

public class EmailMessage{
	
	private String templatePath;
	private String subject;
	private String mailContent;
	private String contentType;
	
	public EmailMessage() {
		contentType = "text/plain";
	}
	
	public String getContentType() {
        return contentType;
    }
 
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
	
	public String getTemplatePath() {
		return templatePath;
	}
	
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getMailContent() {
        return mailContent;
    }
	
    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
	
}
