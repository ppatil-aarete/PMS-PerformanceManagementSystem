package com.thirdi.pms.login.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.thirdi.pms.admin.modal.AppraisalCycle;
import com.thirdi.pms.login.model.EmployeeDetails;
import com.thirdi.pms.login.model.LoginUser;
import com.thirdi.pms.login.model.Menu;

public interface LoginDao {

	public int checkLogin(String username,String password);
	
	public List<Menu> getMenu(String username);
	
	public LoginUser getUser(String username);
	
	public AppraisalCycle getCurrentAppraisalCycle();

	public Map<Integer, String> getNameOfUserById();
	
	public Map<Integer, String> getDesignationOfUserById();

	public List<EmployeeDetails> getAllPhaseStatusAndSuperiorsDetails(Integer emp_number, Boolean isTeamData, Integer currentCycleId);

	public Set<Integer> getMappingOfEmpAndAppr(Integer emp_number, Integer currentCycleId);

	public List<EmployeeDetails> getAllPhaseStatusAndSuperiorsDetailsOfAllTeamMembers(Set<Integer> apprAndEmpMappingList);

	public Integer getCountOfLoginUserByName(String username);
	
	public LoginUser getLoginUserByName(String username);

	public Set<Integer> getActiveEmpMemberIdsSet(Integer cycleId);

	public Integer getNumberOfCurrentAppraisalCycle();
}
