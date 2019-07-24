package com.thirdi.pms.login.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.JsonObject;
import com.thirdi.pms.admin.modal.AppraisalCycle;
import com.thirdi.pms.competency.CompetencyService;
import com.thirdi.pms.competency.PhaseStatus;
import com.thirdi.pms.login.api.LoginService;
import com.thirdi.pms.login.dao.LoginDao;
import com.thirdi.pms.login.model.EmployeeDetails;
import com.thirdi.pms.login.model.LoginUser;
import com.thirdi.pms.login.model.Menu;

@Service
public class LoginServiceImpl implements LoginService{
	
	final static Logger logger = Logger.getLogger(Class.class);
	
	@Autowired
	LoginDao loginDao;
	
	@Autowired
	CompetencyService competencyService;
	
	@Transactional(readOnly=true)
	public int checkLogin(String username,String password){
		
		logger.debug("Inside checkLogin");
		return loginDao.checkLogin(username, password);
	}
	
	@Deprecated
	@Transactional(readOnly=true)
	public Integer getCountOfLoginUserByName(String username){
		if(username !=  null && StringUtils.hasText(username)){
			return loginDao.getCountOfLoginUserByName(username);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public List<LoginUser> getLoginUserByName(String username){
		/*if(username !=  null && StringUtils.hasText(username)){
			return loginDao.getLoginUserByName(username);
		}*/
		return null;
	} 
	
	@Transactional(readOnly=true)
	public List<Menu> getMenu(String username){
		return loginDao.getMenu(username);
	}

	@Transactional(readOnly=true)
	public LoginUser getUser(String username) {
		return loginDao.getUser(username);
	}

	@Transactional(readOnly=true)
	public AppraisalCycle getCurrentAppraisalCycle() {
		return loginDao.getCurrentAppraisalCycle();
	}
	
	@Transactional(readOnly=true)
	public Map<Integer,String> getNameOfUserById(){
		Map<Integer,String> userNameMap = new HashMap<Integer, String>(); 
		userNameMap = loginDao.getNameOfUserById();
		return userNameMap;
	}
	
	@Transactional(readOnly=true)
	public Map<Integer,String> getDesignationOfUserById(){
		Map<Integer,String> designationMap = new HashMap<Integer, String>(); 
		designationMap = loginDao.getDesignationOfUserById();
		return designationMap;
	}
	
	public JsonObject prepareEmployeeReviewCycleDetails(Integer empId,Integer currentCycleId) throws ParseException {
		JsonObject detailsContainer = new JsonObject();
		Map<Integer,String> userIdNameMap = getNameOfUserById();
		Map<Integer,Integer> idMap = competencyService.getMappingOfIds(); 
		List<EmployeeDetails> empFlowDataListUnsorted = loginDao.getAllPhaseStatusAndSuperiorsDetails(empId,false,currentCycleId);
		List<EmployeeDetails> empFlowDataList = sortEmployeeDetailsByPhase(empFlowDataListUnsorted);
		String phaseStatus = "";
		String completionDate = "";
		String appraiserName = "";
		String employeeName = ""; 
		if(empFlowDataList != null && empFlowDataList.size() > 0){
			for(EmployeeDetails empDetails : empFlowDataList){
				if(empDetails != null){
					PhaseStatus  phaseName = empDetails.getCurrentPhase();
					Integer progressOfCycle = empDetails.getStatus();
					Integer apprId = empDetails.getNextUserRoleId();
					Integer emp_number = idMap.get(empId);
					employeeName = userIdNameMap.get(emp_number);
					System.out.println(phaseName+"-----"+progressOfCycle);
					if(phaseName == PhaseStatus.Appraisar){
						appraiserName = userIdNameMap.get(apprId);
					}
					if(empDetails.getCompletionDate() != null){
						completionDate = empDetails.getCompletionDate();
					}
					if(phaseName == PhaseStatus.Reviewer && progressOfCycle.equals(1)) {
						phaseStatus = "Completed";
					}
					if(phaseName == PhaseStatus.Reviewer && progressOfCycle.equals(0)) {
						phaseStatus = "Reviewer Assessment - pending";
					}
					if((phaseName == PhaseStatus.Appraisar && progressOfCycle.equals(0))) {
						phaseStatus = "Appraiser Assessment - pending";
					}
					if(phaseName == PhaseStatus.Self && progressOfCycle.equals(0)){
						phaseStatus = "Self Assessment - pending";
					}
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			if(completionDate != null && StringUtils.hasText(completionDate)) {
				completionDate = sdf.format(sdf2.parse(completionDate));
			}
			detailsContainer.addProperty("status", phaseStatus);
			detailsContainer.addProperty("completionDate", completionDate);
			detailsContainer.addProperty("appraiserName", appraiserName);
			detailsContainer.addProperty("employeeName", employeeName);
		}
		return detailsContainer;
	}

	public Map<Integer, JsonObject> getMyTeamData(Integer empId,Integer currentCycleId) throws ParseException {
		Map<Integer,JsonObject> teamDataMap = new HashMap<Integer, JsonObject>();
		if(empId != null){
			Map<Integer,Integer> idMap = competencyService.getMappingOfIds();
			Integer emp_number = idMap.get(empId);  //emp_number = hs_hr_emp_id
			Map<Integer,String> userIdNameMap = getNameOfUserById();
			Set<Integer> apprAndEmpMappingList = loginDao.getMappingOfEmpAndAppr(emp_number,currentCycleId);
			List<EmployeeDetails> myTeamDataListUnsorted = loginDao.getAllPhaseStatusAndSuperiorsDetailsOfAllTeamMembers(apprAndEmpMappingList);
			if(myTeamDataListUnsorted != null && myTeamDataListUnsorted.size() > 0 && apprAndEmpMappingList != null && apprAndEmpMappingList.size() > 0){
				for(Integer emp_appr_Id : apprAndEmpMappingList){
					JsonObject detailsContainer = new JsonObject();
					String phaseStatus = "";
					String completionDate = "";
					String appraiserName = "";
					String employeeName = "";
					String myRole = "";
					List<EmployeeDetails> myTeamDataList = fetchEmployeeRecordAndSortList(myTeamDataListUnsorted,emp_appr_Id);
					if(myTeamDataList != null && myTeamDataList.size() > 0){
						for(EmployeeDetails empDetails : myTeamDataList){
							PhaseStatus  phaseName = empDetails.getCurrentPhase();
							Integer progressOfCycle = empDetails.getStatus();
							Integer apprId = empDetails.getNextUserRoleId();
							employeeName = userIdNameMap.get(idMap.get(emp_appr_Id));
							if(phaseName == PhaseStatus.Appraisar){
								appraiserName = userIdNameMap.get(apprId);
							}
							if(empDetails.getCompletionDate() != null && empDetails.getCompletionDate() != ""){
								completionDate = empDetails.getCompletionDate();
							}
							if(phaseName == PhaseStatus.Reviewer && progressOfCycle.equals(1)) {
								phaseStatus = "Completed";
							}
							if((phaseName == PhaseStatus.Reviewer && progressOfCycle.equals(0))) {
								phaseStatus = "Reviewer Assessment - pending";
							}
							if((phaseName == PhaseStatus.Appraisar && progressOfCycle.equals(0))) {
								phaseStatus = "Appraiser Assessment - pending";
							}
							if(phaseName == PhaseStatus.Self && progressOfCycle.equals(0)){
								phaseStatus = "Self Assessment - pending";
							}
							if(phaseName == PhaseStatus.Reviewer && emp_number.equals(empDetails.getNextUserRoleId())){
								myRole = "Reviewer";
							}
							if(phaseName == PhaseStatus.Appraisar && emp_number.equals(empDetails.getNextUserRoleId())){
								myRole = "Appraiser";
							}
						}
						detailsContainer.addProperty("status", phaseStatus);
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
						if(completionDate != null && StringUtils.hasText(completionDate)) {
							completionDate = sdf.format(sdf2.parse(completionDate));
						}
						detailsContainer.addProperty("completionDate",completionDate);
						detailsContainer.addProperty("appraiserName", appraiserName);
						detailsContainer.addProperty("employeeName", employeeName);
						detailsContainer.addProperty("role", myRole);
						detailsContainer.addProperty("goalStatus", "");
						teamDataMap.put(emp_appr_Id, detailsContainer);
					}
				}
			}
		}
		return teamDataMap;
	}

	public List<EmployeeDetails> fetchEmployeeRecordAndSortList(List<EmployeeDetails> empDetailsList,Integer empId){
		List<EmployeeDetails> sortedDataLsit = new ArrayList<EmployeeDetails>();
		if(empDetailsList != null & empDetailsList.size() > 0 && empId != null) {
			for(EmployeeDetails empDetails : empDetailsList){
				if(empDetails.getSelfId().equals(empId)) {
					sortedDataLsit.add(empDetails);
				}
			}
		}
		sortedDataLsit = sortEmployeeDetailsByPhase(sortedDataLsit);
		return sortedDataLsit;
	}
	
	private List<EmployeeDetails> sortEmployeeDetailsByPhase(List<EmployeeDetails> employeeDetailsList) {
			if(employeeDetailsList != null && employeeDetailsList.size() > 0){
				Collections.sort( employeeDetailsList, new Comparator<EmployeeDetails>(){
					public int compare( EmployeeDetails o1, EmployeeDetails o2 ){
						return (o2.getCurrentPhase()).compareTo( o1.getCurrentPhase());
						}
					} );
			}
			return employeeDetailsList;
	}

	public Integer getNumberOfCurrentAppraisalCycle() {
		return loginDao.getNumberOfCurrentAppraisalCycle();
	}
	
}
