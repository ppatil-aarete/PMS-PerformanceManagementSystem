package com.thirdi.pms.external.services;

import java.util.List;

import com.thirdi.pms.admin.EmailMessage;
import com.thirdi.pms.admin.Recipient;
import com.thirdi.pms.admin.RecipientMail;

public interface EmailMessageService {

	public void sendEmail(List<Recipient> recipients,EmailMessage emailMessage);
	
	public void sendCustomEmail(Recipient recipient, EmailMessage mail);

	public void sendApprEmail(List<RecipientMail> recipientMailList, EmailMessage mail,String date,Integer phaseId);
	
	public String emailName(String name);
	
	public List<String> returnMail(Integer phaseId,List<RecipientMail> recipientMailList);
	
	public List<String> returnTempleteName(Integer phaseId);

	public void sendRevTwoEmail(List<Recipient> recipientList, EmailMessage mail,String templeteName);
	
}
