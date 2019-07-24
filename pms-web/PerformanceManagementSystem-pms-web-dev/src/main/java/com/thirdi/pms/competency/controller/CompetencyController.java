package com.thirdi.pms.competency.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.thirdi.pms.competency.CompetencyService;
import com.thirdi.pms.competency.PhaseStatus;
import com.thirdi.pms.competency.SelfAppraisal;
import com.thirdi.pms.competency.StrengthWeakness;
import com.thirdi.pms.login.api.LoginService;

@Controller
@RequestMapping("/competency")	
public class CompetencyController {

    @Autowired
    CompetencyService competencyService;
    
    @Autowired
    LoginService loginService;
    
    private final Log logger = LogFactory.getLog(getClass());
    Gson gson = new Gson();
	
	@RequestMapping(value="/getcompetencies.do", method=RequestMethod.POST)
	public void getCompetency(HttpServletRequest request,HttpServletResponse response) throws URISyntaxException, IOException, JSONException {
		 
		String empIdString = request.getParameter("empId");
		String apprCycleId = request.getParameter("cycleId");
	    	
		JSONObject dataJson = new JSONObject();
		//DailyCronJob cronJob = new DailyCronJob();
		//cronJob.checkReviewerDate();
		try {
			if(empIdString != null && !StringUtils.isEmpty(empIdString) && apprCycleId != null && !StringUtils.isEmpty(apprCycleId) ) {
				Integer empId = Integer.parseInt(empIdString);
				Integer current_phase = competencyService.getCurrentPhaseOfEmployee(empId);
				List<SelfAppraisal> selfAppraisalList = competencyService.getListOfSelfAppraisal(Integer.parseInt(empIdString), Integer.parseInt(apprCycleId), false);
				TreeMap<String,TreeMap<Integer,Object[]>> sectionDataMap = competencyService.getRatingsAndQuestionsOfCompetencies(selfAppraisalList);
				Map<String,JsonObject> finalScoreMap = competencyService.getFinalScoreOfEverySection(sectionDataMap);
				Map<Integer,String> questionMap = competencyService.getQuestionFromId(selfAppraisalList);
				Double[] eachPhaseScoreArr = competencyService.getFinalScoreOfEachPhase(empId);
				Map<Integer,String[]> piMap = competencyService.fetchPerformanceIndicators(selfAppraisalList);
				Map<Integer,String> questionRatingStatusMap = competencyService.checkQuestionIsRatedOrNot(selfAppraisalList);
				Map<String,List<String>> swMap = competencyService.prepareStrengthAndWeaknessMap(empId);
				dataJson.put("status", true);
				dataJson.put("sectionMap", gson.toJsonTree(sectionDataMap));
				dataJson.put("questionMap", gson.toJsonTree(questionMap));
			    dataJson.put("currentPhase", gson.toJsonTree(current_phase));
			    dataJson.put("finalScoreMap", gson.toJsonTree(finalScoreMap));
			    dataJson.put("eachPhaseScore",gson.toJsonTree(eachPhaseScoreArr));
			    dataJson.put("ratingStatusMap",gson.toJsonTree(questionRatingStatusMap));
			    dataJson.put("piMap", gson.toJsonTree(piMap));
			    dataJson.put("swMap", gson.toJsonTree(swMap));
			}else {
				dataJson.put("status", false);
				dataJson.put("errorMessage", "No data found.");
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
			dataJson.put("status", false);
			dataJson.put("errorMessage", "Something went wrong");
		}catch(Exception e) {
			dataJson.put("status", false);
			dataJson.put("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}

	
	@RequestMapping(value="/savecompetencies.do", method=RequestMethod.POST)
	public void SaveCompetencies(HttpServletRequest request,HttpServletResponse response) throws URISyntaxException, IOException, JSONException {
		 
		String competencyMap = request.getParameter("dataMap");
		String empIdStr = request.getParameter("empId");
		String apprCycleId =request.getParameter("cycleId"); 
		String currentPhaseNo =request.getParameter("phaseId");
		//String swMap = request.getParameter("strgWeakMap");
		String appraiserIdString = request.getParameter("appraiserId");
		String statusOfMember = request.getParameter("status");
		JSONObject dataJson = new JSONObject();
		try {
			Map<String,Object[]> competencyDataMap = null;
			//Map<String,String[]> strgWeakMap = null; 
			if(!StringUtils.isEmpty(empIdStr) && !StringUtils.isEmpty(competencyMap) && !StringUtils.isEmpty(currentPhaseNo) && !StringUtils.isEmpty(apprCycleId) /*&& !StringUtils.isEmpty(swMap)*/) {
				Integer empId = Integer.parseInt(empIdStr);
				Integer cycleId = Integer.parseInt(apprCycleId);
				Integer receivedCurrentPhase = Integer.parseInt(currentPhaseNo);
				Integer appraiserId = Integer.parseInt(appraiserIdString);
				PhaseStatus currentPhaseStatus = PhaseStatus.values()[receivedCurrentPhase];
				Integer phaseValidator = competencyService.getCurrentPhaseOfEmployee(empId);
				if(phaseValidator >= receivedCurrentPhase){
					competencyDataMap = gson.fromJson(competencyMap, new TypeToken<Map<String,Object[]>>(){}.getType()); 
					Map<Integer,Object[]> questionAndResponseMap = competencyService.getQuestionAndResponseData(competencyDataMap);
			//	strgWeakMap = gson.fromJson(swMap, new TypeToken<Map<String,String[]>>(){}.getType());
				//  Map<Integer,List<StrengthWeakness>> strWeakContainerMap = competencyService.getStrengthAndweaknessDataMap(strgWeakMap,currentPhaseStatus,appraiserId); 
					  Boolean updateResponse = competencyService.updateEmployeeCompetencies(empId,currentPhaseStatus,questionAndResponseMap,cycleId,statusOfMember);
						//Boolean updateStrengthAndWeakness = competencyService.CreateStrengthAndWeaknessWithSave(strWeakContainerMap); 
					Integer current_phase = competencyService.getCurrentPhaseOfEmployee(empId);
					List<SelfAppraisal> selfAppraisalList = competencyService.getListOfSelfAppraisal(empId, Integer.parseInt(apprCycleId), false);
					TreeMap<String,TreeMap<Integer,Object[]>> sectionDataMap = competencyService.getRatingsAndQuestionsOfCompetencies(selfAppraisalList);
					Map<String,JsonObject> finalScoreMap = competencyService.getFinalScoreOfEverySection(sectionDataMap);
					Map<Integer,String> questionMap = competencyService.getQuestionFromId(selfAppraisalList);
					Double[] eachPhaseScoreArr = competencyService.getFinalScoreOfEachPhase(empId); 
					Map<Integer,JsonObject> teamDataMap = loginService.getMyTeamData(empId,cycleId);
					//Map<String,List<String>> swDataMap = competencyService.prepareStrengthAndWeaknessMap(empId);
					dataJson.put("status", true);
					dataJson.put("sectionMap", gson.toJsonTree(sectionDataMap));
					dataJson.put("questionMap", gson.toJsonTree(questionMap));
					dataJson.put("finalScoreMap", gson.toJsonTree(finalScoreMap));
					dataJson.put("eachPhaseScore",gson.toJsonTree(eachPhaseScoreArr));
					dataJson.put("myTeamData", gson.toJsonTree(teamDataMap));
					//dataJson.put("swMap", gson.toJsonTree(swDataMap));
				}else {
					dataJson.put("status", false);
					dataJson.put("errorMessage", "Oops! Something went wrong!");
				}
			}else {
				dataJson.put("status", false);
				dataJson.put("errorMessage", "Oops! Something went wrong! Please contact your administrator.");
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
			dataJson.put("status", false);
			dataJson.put("errorMessage", e.getMessage());
		}catch(Exception e) {
			dataJson.put("status", false);
			dataJson.put("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		writeDataInResponse(response,dataJson.toString());
	}
		
	
	@RequestMapping(value="/updatecompetencies.do", method=RequestMethod.POST)
	public void UpdateCompetencies(HttpServletRequest request,HttpServletResponse response) throws URISyntaxException, IOException, JSONException {
		 
		String competencyMap = request.getParameter("dataMap");
		String empIdStr = request.getParameter("empId");
		String apprCycleId =request.getParameter("cycleId"); 
		String currentPhaseNo =request.getParameter("phaseId");
		String swMap = request.getParameter("strgWeakMap");
		String appraiserIdString = request.getParameter("appraiserId");
		String statusOfMember = request.getParameter("status");
		JSONObject dataJson = new JSONObject();
		try {
			Map<String,Object[]> competencyDataMap = null;
			Map<String,String[]> strgWeakMap = null; 
			if(!StringUtils.isEmpty(empIdStr) && !StringUtils.isEmpty(competencyMap) && !StringUtils.isEmpty(currentPhaseNo) && !StringUtils.isEmpty(apprCycleId) && !StringUtils.isEmpty(swMap)) {
				Integer empId = Integer.parseInt(empIdStr);
				Integer cycleId = Integer.parseInt(apprCycleId);
				Integer receivedCurrentPhase = Integer.parseInt(currentPhaseNo);
				Integer appraiserId = Integer.parseInt(appraiserIdString);
				PhaseStatus currentPhaseStatus = PhaseStatus.values()[receivedCurrentPhase];
				Integer phaseValidator = competencyService.getCurrentPhaseOfEmployee(empId);
				if(phaseValidator >= receivedCurrentPhase){
					competencyDataMap = gson.fromJson(competencyMap, new TypeToken<Map<String,Object[]>>(){}.getType()); 
					Map<Integer,Object[]> questionAndResponseMap = competencyService.getQuestionAndResponseData(competencyDataMap);
					strgWeakMap = gson.fromJson(swMap, new TypeToken<Map<String,String[]>>(){}.getType());
				    Map<Integer,List<StrengthWeakness>> strWeakContainerMap = competencyService.getStrengthAndweaknessDataMap(strgWeakMap,currentPhaseStatus,appraiserId); 
					Boolean updateResponse = competencyService.updateEmployeeCompetencies(empId,currentPhaseStatus,questionAndResponseMap,cycleId,statusOfMember);
					Boolean updateStrengthAndWeakness = competencyService.updateStrengthAndWeaknesses(strWeakContainerMap); 
							//following service will update status of employee in [appr_empl_flow] table.
					Boolean updatePhase = competencyService.updateCurrentPhaseOfEmployee(cycleId,empId,currentPhaseStatus);
					if(currentPhaseStatus == PhaseStatus.Self) {
						currentPhaseStatus = PhaseStatus.Appraisar;
					}else if(currentPhaseStatus == PhaseStatus.Appraisar) {
						currentPhaseStatus = PhaseStatus.Reviewer;
					}
					//following service will update phase of employee in [appr_empl] table.
					Boolean isPhaseUpdated = competencyService.updateCurrentPhaseEmplEntity(empId,cycleId,currentPhaseStatus);
					Integer current_phase = competencyService.getCurrentPhaseOfEmployee(empId);
					List<SelfAppraisal> selfAppraisalList = competencyService.getListOfSelfAppraisal(empId, Integer.parseInt(apprCycleId), false);
					TreeMap<String,TreeMap<Integer,Object[]>> sectionDataMap = competencyService.getRatingsAndQuestionsOfCompetencies(selfAppraisalList);
					Map<String,JsonObject> finalScoreMap = competencyService.getFinalScoreOfEverySection(sectionDataMap);
					Map<Integer,String> questionMap = competencyService.getQuestionFromId(selfAppraisalList);
					Double[] eachPhaseScoreArr = competencyService.getFinalScoreOfEachPhase(empId); 
					JsonObject userDahsboardInfoJson = loginService.prepareEmployeeReviewCycleDetails(empId,cycleId);
					Map<Integer,JsonObject> teamDataMap = loginService.getMyTeamData(empId,cycleId);
					Map<String,List<String>> swDataMap = competencyService.prepareStrengthAndWeaknessMap(empId);
					Boolean isAllHaveSubmitted = competencyService.checkDoesAllEmployeeHasSubmitted();
					dataJson.put("status", true);
					dataJson.put("sectionMap", gson.toJsonTree(sectionDataMap));
					dataJson.put("questionMap", gson.toJsonTree(questionMap));
					dataJson.put("currentPhase", gson.toJsonTree(current_phase));
					dataJson.put("finalScoreMap", gson.toJsonTree(finalScoreMap));
					dataJson.put("eachPhaseScore",gson.toJsonTree(eachPhaseScoreArr));
					dataJson.put("userGeneralInfo", gson.toJsonTree(userDahsboardInfoJson));
					dataJson.put("myTeamData", gson.toJsonTree(teamDataMap));
					dataJson.put("swMap", gson.toJsonTree(swDataMap));
				}else {
					dataJson.put("status", false);
					dataJson.put("errorMessage", "Oops! Something went wrong!");
				}
			}else {
				dataJson.put("status", false);
				dataJson.put("errorMessage", "Oops! Something went wrong! Please contact your administrator.");
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
			dataJson.put("status", false);
			dataJson.put("errorMessage", e.getMessage());
		}catch(Exception e) {
			dataJson.put("status", false);
			dataJson.put("errorMessage", e.getMessage());
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
	
	@RequestMapping(value="/sendmailtoappr.do", method=RequestMethod.POST) 
	public void sendMailToAppr(HttpServletRequest request,HttpServletResponse response) {
	    String empId = request.getParameter("empId");
	    String cycleId = request.getParameter("cycleId");
	    String CurrentTime = request.getParameter("date");
	    String phaseId = request.getParameter("phaseId");
	    JsonObject dataJson = new JsonObject();
			try {
				Boolean responseFlag = competencyService.sendEmailToAppr(Integer.parseInt(empId),Integer.parseInt(cycleId),CurrentTime,Integer.parseInt(phaseId));
			    dataJson.addProperty("status", responseFlag);
			}catch(Exception e) {
				dataJson.addProperty("status", false);
				dataJson.addProperty("errorMessage", e.getMessage());
				e.printStackTrace();
			}
			try {
				writeDataInResponse(response,dataJson.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
	} 
	
	@RequestMapping(value="/getreviewerfinalrating.do", method=RequestMethod.POST)
	public void getReviewerFinalRating(HttpServletRequest request,HttpServletResponse response){
		 String cycleId = request.getParameter("cycleId");
		 String empId = request.getParameter("empId");
		 JsonObject dataJson = new JsonObject();
		 
			 float finalRating = competencyService.returnFinalReviewerRating(Integer.parseInt(cycleId),Integer.parseInt(empId));
			 dataJson.addProperty("finalRating",finalRating);
		try {
			writeDataInResponse(response,dataJson.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	
	
	
}


