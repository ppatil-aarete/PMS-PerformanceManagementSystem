package com.thirdi.pms.admin.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.thirdi.pms.admin.AdminDao;
import com.thirdi.pms.admin.AdminService;
import com.thirdi.pms.admin.modal.AppraisalCycle;
import com.thirdi.pms.login.api.LoginService;
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	AdminDao adminDao;
	
	Gson gson = new Gson();
	private final Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(value="/employeestatusdata.do", method=RequestMethod.POST)
	public void FetchAdminScreenData(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		JsonObject dataJson = new JsonObject();
		try {
			AppraisalCycle currentCycle = loginService.getCurrentAppraisalCycle();
			Map<Integer, JsonObject> allEmpData = adminService.fetchAllEmployeeData(currentCycle.getCycleId());
			dataJson.addProperty("status", true);
		    dataJson.add("empData", gson.toJsonTree(allEmpData));
		} catch (ParseException e) {
			dataJson.addProperty("status ", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			dataJson.addProperty("status ", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}
	@RequestMapping(value="/createnewcycle.do", method=RequestMethod.POST)
	public void CreateNewCycle(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String newCycleDataString = request.getParameter("cycleMap");
		JsonObject dataJson = new JsonObject();
		Map<String,String> newCycleDataMap = null;
		try {
			newCycleDataMap = gson.fromJson(newCycleDataString, new TypeToken<Map<String,String>>(){}.getType()); 
			Boolean stopOtherCycle = adminService.stopOtherRunningCycle(); 
			Boolean isNewCycleCreated = adminService.createCycle(newCycleDataMap);
			AppraisalCycle currentCycle = loginService.getCurrentAppraisalCycle();
			Integer totalCycles = adminDao.getTotalCycle();
			dataJson.addProperty("status", true);
		    dataJson.addProperty("empData", isNewCycleCreated);
		    dataJson.add("runningCycel", gson.toJsonTree(currentCycle));
		    dataJson.add("totalCycles", gson.toJsonTree(totalCycles));
		}catch(Exception e) {
			dataJson.addProperty("status", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}
	
	@RequestMapping(value="/generatemetricsheet.do", method=RequestMethod.POST)
	public void GenerateMetricsSheetData(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		JsonObject dataJson = new JsonObject();
		try {
		    AppraisalCycle currentCycle = loginService.getCurrentAppraisalCycle();
			Map<Integer,JsonObject> empMetricSheetData = adminService.generateMetricsSheetData(currentCycle.getCycleId());
			dataJson.addProperty("status", true);
		    dataJson.add("empMetricSheetData", gson.toJsonTree(empMetricSheetData));
		}catch(Exception e) {
			dataJson.addProperty("status", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}
	
	@RequestMapping(value="/sendemailtoallemp.do", method=RequestMethod.POST)
	public void SendEmail(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		JsonObject dataJson = new JsonObject();
		try {
			Boolean responseFlag = adminService.sendEmailToAll();
		    dataJson.addProperty("status", responseFlag);
		}catch(Exception e) {
			dataJson.addProperty("status", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}
	
	
	@RequestMapping(value="/sendemailtosingleemp.do", method=RequestMethod.POST)
	public void SendEmailToSingleEmp(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String empId = request.getParameter("empId");
		String subject = request.getParameter("subject");	
		String message = request.getParameter("message");
		
		JsonObject dataJson = new JsonObject();
		Boolean responseFlag = true;
		try {
			if(empId != null && StringUtils.hasText(subject) && StringUtils.hasText(message)){
				 responseFlag = adminService.sendEmailToAnEmployee(Integer.parseInt(empId),subject,message);
				 dataJson.addProperty("status", responseFlag);
			}else {
				 dataJson.addProperty("status", false);	
				 dataJson.addProperty("errorMessage", "Something went wrong.");
			}
		}catch(Exception e) {
			dataJson.addProperty("status", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}
	
	
	@RequestMapping(value="/updaterevscoreandremarks.do", method=RequestMethod.POST)
	public void UpdateRevAndRemarks(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String empId = request.getParameter("empId");
		String isRemarkOrRating = request.getParameter("flag");
		String value = request.getParameter("value");
		String revId = request.getParameter("revId");
		Integer cycleId = Integer.parseInt(request.getParameter("cycleId"));
		
		
		JsonObject dataJson = new JsonObject();
		Boolean responseFlag = null;
		try {
			if(empId != null && StringUtils.hasText(empId) && isRemarkOrRating != null && StringUtils.hasText(isRemarkOrRating) && value != null && StringUtils.hasText(value)){
				if(isRemarkOrRating.equals("ratings")) {
					responseFlag = adminService.updateReviewerRatings(Integer.parseInt(empId),Float.parseFloat(value), Integer.parseInt(revId), cycleId);
					Map<Integer,JsonObject> updatedMetricSheetDataMap = adminService.generateMetricsSheetData(cycleId);
					dataJson.add("updatedMetricSheetMap", gson.toJsonTree(updatedMetricSheetDataMap));
				}else {
					
					responseFlag = adminService.updateReviewerRemarks(Integer.parseInt(empId),value , Integer.parseInt(revId), cycleId);
					Map<Integer,JsonObject> updatedMetricSheetDataMap = adminService.generateMetricsSheetData(cycleId);
					dataJson.add("updatedMetricSheetMap", gson.toJsonTree(updatedMetricSheetDataMap));					
					
				}
				dataJson.addProperty("status", true);
			}else {
				dataJson.addProperty("status", false);
				dataJson.addProperty("errorMessage", "Oops something went wrong.");
			} 
		}catch(Exception e) {
			dataJson.addProperty("status", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}
	
	
	@RequestMapping(value="/mapreviewerandemployee.do", method=RequestMethod.POST)
	public void mapReviewerAndEmployeeScreenData(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		Boolean responseFlag = null;
		JsonObject dataJson = new JsonObject();
		try {
				Map<Integer,String> aboveConsultantProfileUserMap = adminService.getEmployeesAboveConsultantProfie();
				Map<Integer,String> allActiveUserMap = adminService.getAllActiveEmployees();
				dataJson.addProperty("status", true);
				dataJson.add("aboveConsultantMap", gson.toJsonTree(aboveConsultantProfileUserMap));
				dataJson.add("activeProfile", gson.toJsonTree(allActiveUserMap));
		}catch(Exception e) {
			dataJson.addProperty("status", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}
	
	@RequestMapping(value="/mapreviewerwithemployees.do", method=RequestMethod.POST)
	public void mapReviewerWithEmployees(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String empList = request.getParameter("empList");
		String revId = request.getParameter("revId");
		
		Iterator<JsonElement> iterator = new JsonParser().parse(empList).getAsJsonArray().iterator();
		String emps = "";
		while(iterator.hasNext()){
			emps += iterator.next().getAsString();
			if(iterator.hasNext())
				emps += ",";
		}
		JsonObject dataJson = new JsonObject();
		try {
			 if( StringUtils.hasText(emps) && revId != null && StringUtils.hasText(revId)){
				 Boolean responseFlag = adminService.mapEmployeeWithReviewer(revId,emps);
				 List<Integer> mappedList = adminService.getEmployeesMappedWithReviewer(Integer.parseInt(revId));
				 dataJson.addProperty("status", responseFlag);
				 dataJson.add("mappedEmployeeList", gson.toJsonTree(mappedList));
			 }else {
				 dataJson.addProperty("status", false);
				 dataJson.addProperty("errorMessage", "Oops! Something went wrong.");
			 }
		}catch(Exception e) {
			dataJson.addProperty("status", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}

	
//New method for HR-Admin
	
	
	
	@RequestMapping(value="/getHRAdminMember.do", method=RequestMethod.POST)
	public void getHRAdminMember(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		Boolean responseFlag = null;
		JsonObject dataJson = new JsonObject();
		try {
				Map<Integer,String> hrAdminMember = adminService.getHRAdminMember();
				Map<Integer,String> allActiveUserMap = adminService.getAllActiveEmployees();
				dataJson.addProperty("status", true);
				dataJson.add("hrAdminMemberMap", gson.toJsonTree(hrAdminMember));
				dataJson.add("activeProfile", gson.toJsonTree(allActiveUserMap));
		}catch(Exception e) {
			dataJson.addProperty("status", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}
	
	
	@RequestMapping(value="/mapreviewerwithhradmin.do", method=RequestMethod.POST)
	private void mapReviewersWithHRAdmin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String hrAdminID = request.getParameter("hrAdminID");
		String revIdList = request.getParameter("revIdList");
	
		Iterator<JsonElement> iterator = new JsonParser().parse(revIdList).getAsJsonArray().iterator();
		String revs = "";
		while(iterator.hasNext()){
			revs += iterator.next().getAsString();
			if(iterator.hasNext())
				revs += ",";
		}
		JsonObject dataJson = new JsonObject();
		try {
			 if( StringUtils.hasText(revs) && hrAdminID != null && StringUtils.hasText(hrAdminID)){
				 Boolean responseFlag = adminService.mapReviewerwithHRAdmin(hrAdminID,revs);
				 List<Integer> mappedList = adminService.getReviewersMappedWithHRAdmin(Integer.parseInt(hrAdminID));
				 dataJson.addProperty("status", responseFlag);
				 dataJson.add("mappedReviewerList", gson.toJsonTree(mappedList));
			 }else {
				 dataJson.addProperty("status", false);
				 dataJson.addProperty("errorMessage", "Oops! Something went wrong.");
			 }
		}catch(Exception e) {
			dataJson.addProperty("status", false);
			dataJson.addProperty("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());

		
	
	}
	
	private void writeDataInResponse(HttpServletResponse response, String jsonString) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		BufferedOutputStream contents = new BufferedOutputStream(response.getOutputStream());
		response.setHeader("Cache-Control", "no-cache");
		contents.write(jsonString.getBytes());
		contents.flush();
	}
	
	  @RequestMapping(value="/fetcheckedempprogresslist.do", method=RequestMethod.POST)
	  private void sendMailToSelectedEmployee(HttpServletRequest request,HttpServletResponse response) throws IOException{
	      String employeesId = request.getParameter("empList");

	      Iterator<JsonElement> iterator = new JsonParser().parse(employeesId).getAsJsonArray().iterator();
			String empList = "";
			while(iterator.hasNext()){
				empList += iterator.next().getAsString();
				if(iterator.hasNext())
					empList += ",";
			}
	        JsonObject dataJson = new JsonObject();
			try {
				Boolean responseFlag = adminService.sendEmailSelectedEmployee(empList);
			    dataJson.addProperty("status", responseFlag);
			}catch(Exception e) {
				dataJson.addProperty("status", false);
				dataJson.addProperty("errorMessage", e.getMessage());
				e.printStackTrace();
			}
			writeDataInResponse(response,dataJson.toString());
	  } 
	  
	  @RequestMapping(value="/dataExport.do", method=RequestMethod.POST)
		public void exportDataMap(HttpServletRequest request,HttpServletResponse response) throws IOException {
			
			JsonObject dataJson = new JsonObject();
			try {
				AppraisalCycle currentCycle = loginService.getCurrentAppraisalCycle();
				Map<Integer, JsonObject> exportDataMap = adminService.exportData(currentCycle.getCycleId());
				dataJson.addProperty("status", true);
			    dataJson.add("exportDataMap", gson.toJsonTree(exportDataMap));
			}catch(Exception e) {
				dataJson.addProperty("status", false);
				dataJson.addProperty("errorMessage", e.getMessage());
				e.printStackTrace();
			}
			writeDataInResponse(response,dataJson.toString());
		}
	  
	  @RequestMapping(value="/sendgoalsdata.do", method=RequestMethod.POST)
		public void sendGoalsData(HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		  String empId = request.getParameter("empId");
		  Integer cycleId = Integer.parseInt(request.getParameter("cycleId"));
		  String goalsData = request.getParameter("goalsMap");
		  Integer phaseId =  Integer.parseInt(request.getParameter("phaseId"));
		  
		  JSONObject dataJson = new JSONObject();
		  Map<String,JSONObject> goalDataMap = null;
			try {
				HashMap<String,Object> result =
				        new ObjectMapper().readValue(goalsData, HashMap.class);
				Boolean addGoals = adminService.addGoalsInfo(result, cycleId, phaseId);
			//	dataJson.addProperty("status", true);
			 //   dataJson.addProperty("goalsData", addGoals);
		}catch(Exception e) {
				dataJson.put("status", false);
				dataJson.put("errorMessage", e.getMessage());
				e.printStackTrace();
			}
			writeDataInResponse(response,dataJson.toString());
		}
}
