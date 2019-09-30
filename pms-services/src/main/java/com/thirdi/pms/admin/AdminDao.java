package com.thirdi.pms.admin;

import java.util.List;
import java.util.Map;

import com.thirdi.pms.goal.model.Goal;

public interface AdminDao {

	public 	Map<Integer,String> getFunctionOfEmployeeWithIdMap();

	public Boolean creatNewCycle(Map<String, String> cycleDataMap);

	public Boolean stopOtherCycle();
	
	public Integer getTotalCycle();
	
	public List<Recipient> fetchAllRrecipients(Integer empId);
	
	public Map<Integer,String> getEmployeesAboveConsultantProfie();
	
	public Map<Integer,String> getAllActiveEmployees();

	public Boolean mapEmployeeWithReviewer(List<Integer> employeeList,Integer reviewerId,Integer cycleId);
	
	public List<Integer> getEmployeesMappedWithReviewer(Integer reviewerId);

	public Boolean mapReviewerWithHRAdmin(List<Integer> reviewerList,Integer hrId,Integer runningCycleId);
	
	public List<Integer> getReviewersMappedWithHRAdmin(Integer hrId);

	public Map<Integer, String> getHRAdminMember();

	public void fillData();

	public List<Recipient> fetchAllLevelTwoReviewer(List<Integer> reviewerList);

	public List<Recipient> fetchAllRecipientsForReviewerReminder();

	public Boolean addGoalsInformation(List<Goal> goal, Integer cycleId, Integer phaseId);

}
