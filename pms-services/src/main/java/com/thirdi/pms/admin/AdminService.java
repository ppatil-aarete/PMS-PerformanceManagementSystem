package com.thirdi.pms.admin;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

public interface AdminService {

	public Map<Integer, JsonObject> fetchAllEmployeeData(Integer currentCycleId) throws ParseException;
	
	public Boolean sendEmail(Integer empId);
	
	public Boolean sendEmailToAll();
	
	public Boolean createCycle(Map<String,String> cycleDataMap);

	public Map<Integer, JsonObject> generateMetricsSheetData(Integer cycleId);

	public Boolean stopOtherRunningCycle();

	public Boolean updateReviewerRatings(Integer empId, Float ratings, Integer revId, Integer cycleId);

	public Boolean updateReviewerRemarks(Integer empId, String remarks, Integer revId, Integer cycleId);
	
	public Map<Integer,String> getEmployeesAboveConsultantProfie();
	
	public Map<Integer,String> getAllActiveEmployees();

	public Boolean mapEmployeeWithReviewer(String revId, String empList);
	
	public Boolean mapReviewerwithHRAdmin(String hrAdminID,String revs);
	
	public List<Integer> getReviewersMappedWithHRAdmin(Integer hrAdminID);
	
	public List<Integer> getEmployeesMappedWithReviewer(Integer reviewerId);

	public Boolean sendEmailToAnEmployee(int parseInt, String subject, String message);

	public Map<Integer, String> getHRAdminMember();

	public void sendReminderMailToReviewer();

	public Boolean sendEmailSelectedEmployee(String empList);
	
	public Map<Integer, JsonObject> exportData(Integer cycleId);

	public Boolean addGoalsInfo(HashMap<String, Object> result, Integer cycleId, Integer phaseId);

	
	
}
