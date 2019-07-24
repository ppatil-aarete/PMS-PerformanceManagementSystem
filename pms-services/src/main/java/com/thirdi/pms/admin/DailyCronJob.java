/*package com.thirdi.pms.admin;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
@Configuration
@EnableScheduling
public class DailyCronJob {
	
	@Autowired
	AdminService adminService;
@Scheduled(fixedDelay=50000)
   public void checkReviewerDate()
   {
	   
       //System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());
       adminService.sendReminderMailToReviewer();
   }
}*/