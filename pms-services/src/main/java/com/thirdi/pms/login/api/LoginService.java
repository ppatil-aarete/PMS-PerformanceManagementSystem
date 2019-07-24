package com.thirdi.pms.login.api;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.thirdi.pms.admin.modal.AppraisalCycle;
import com.thirdi.pms.login.model.EmployeeDetails;
import com.thirdi.pms.login.model.LoginUser;
import com.thirdi.pms.login.model.Menu;

public interface LoginService {

	int checkLogin(String username,String password);
	
	public List<Menu> getMenu(String username);
	
	public LoginUser getUser(String username);
	
	public AppraisalCycle getCurrentAppraisalCycle();
	
	public JsonObject prepareEmployeeReviewCycleDetails(Integer empId,Integer currentCyleId) throws ParseException;

	public Map<Integer, JsonObject> getMyTeamData(Integer empId,Integer currentCycleId) throws ParseException;
	
	public List<LoginUser> getLoginUserByName(String username);
	
	public Map<Integer,String> getNameOfUserById();
	
	public Map<Integer,String> getDesignationOfUserById();
	
	public List<EmployeeDetails> fetchEmployeeRecordAndSortList(List<EmployeeDetails> empDetailsList,Integer empId);

	public Integer getNumberOfCurrentAppraisalCycle();
	
}
