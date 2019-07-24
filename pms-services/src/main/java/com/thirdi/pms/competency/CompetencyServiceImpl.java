package com.thirdi.pms.competency;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thirdi.pms.admin.EmailMessage;
import com.thirdi.pms.admin.Recipient;
import com.thirdi.pms.admin.RecipientMail;
import com.thirdi.pms.admin.modal.AppraisalCycle;
import com.thirdi.pms.external.services.EmailMessageService;
import com.thirdi.pms.login.dao.LoginDao;

@Service
public class CompetencyServiceImpl implements CompetencyService{
	
	@Autowired
	CompetencyDao competencyDao;
	
	@Autowired
	EmailMessageService emailService;
	
	@Autowired
	AppraisalCycle appraisalCycle;
	
	@Autowired 
	LoginDao loginDao;
	
	@Autowired 
	RecipientMail recipientMail;
	
	@Transactional(readOnly=true)
	public List<SelfAppraisal> getListOfSelfAppraisal(Integer empId, Integer currentCycleId,Boolean flag) {
		return competencyDao.getListOfEmpAppraisals(empId,currentCycleId,flag);
	}
	
	@Transactional(readOnly=true)
	public Map<Integer, Integer> getMappingOfIds() {
		return competencyDao.getMappingOfIds();
	}
	
	@Transactional
	public Boolean updateMappingOfEmployeeFlowTable(Map<Integer, Integer> idMapper) {
		return competencyDao.updateMappingsInEployeeTable(idMapper);
	}
	
	@Transactional
	public Boolean updateCurrentPhaseStatus(Integer empId) {
		return null;
	}
	
	@Transactional
	public Integer getCurrentPhaseOfEmployee(Integer empId){
		return competencyDao.getCurrentPhaseOfEmployee(empId);
	}
	
	public TreeMap<String,TreeMap<Integer,Object[]>> getRatingsAndQuestionsOfCompetencies(List<SelfAppraisal> dataList) throws JSONException{
		TreeMap<String,TreeMap<Integer,Object[]>> dataMap = new TreeMap<String, TreeMap<Integer,Object[]>>();
	    if(dataList != null && dataList.size() > 0) {
	    	for(SelfAppraisal sa : dataList) {
	    		TreeMap<Integer,Object[]> subMap = dataMap.get(sa.getSection());	
	    		if(subMap == null ) {
	    			subMap = new TreeMap<Integer,Object[]>();
	    		}
	    		Integer questionId = sa.getQuestionId();
	    		Object[] filteredDataArray = subMap.get(questionId);
	    		if(filteredDataArray ==  null) {
	    			filteredDataArray = new  Object[9];
	    		}
	    		filteredDataArray = putRequiredDataInJson(sa,filteredDataArray); 
	    		subMap.put(questionId, filteredDataArray);
	    		dataMap.put(sa.getSection(), subMap);
	    	}
	    }
		return dataMap;
	}

	private Object[] putRequiredDataInJson(SelfAppraisal sa, Object[] dataArray) throws JSONException {
		if(sa != null) {
			if(sa.getQuestionColOrder() != null) {
				dataArray[0] = sa.getQuestionColOrder();
			}
			if(sa.getRemark1() != null && StringUtils.hasText(sa.getRemark1())) {
				dataArray[1] = sa.getRemark1();
			}
			if(sa.getRemark2() != null && StringUtils.hasText(sa.getRemark2())) {
				dataArray[2] = sa.getRemark2();
			}
			if(sa.getRemark3() != null && StringUtils.hasText(sa.getRemark3())){
				dataArray[3] = sa.getRemark3();
			}
			if(sa.getRating1() != null){
				dataArray[4] = sa.getRating1();
			}
			if(sa.getRating2() != null){
				dataArray[5] = sa.getRating2();
			}
			if(sa.getRating3() != null){
				dataArray[6] = sa.getRating3();
			}
			if(sa.getRatingYN() != null) {
				dataArray[7] = sa.getRatingYN();
			}
			if(sa.getWeightage() != null) {
				dataArray[8] = sa.getWeightage();
			}
		}
		return dataArray;
	} 
	
	public Map<Integer,String> getQuestionFromId(List<SelfAppraisal> dataList){
		Map<Integer,String> questionMap = new HashMap<Integer, String>();
		if(dataList != null && dataList.size() > 0){
			for(SelfAppraisal object : dataList){
				if(object.getQuestionId() != null && object.getQuestion() != null && StringUtils.hasText(object.getQuestion())) {
					questionMap.put(object.getQuestionId(), object.getQuestion());
				}
			}
		}
		return questionMap;
	}
	
	public Map<Integer,Integer> getRatingsFromId(List<SelfAppraisal> dataList){
		Map<Integer,Integer> ratingMap = new HashMap<Integer, Integer>();
		if(dataList != null && dataList.size() > 0){
			for(SelfAppraisal object : dataList){
				if(object.getRatingId1() != null && object.getRating1() != null){
				     ratingMap.put(object.getRatingId1(), object.getRating1());
				}
				if(object.getRatingId2() != null && object.getRating2() != null){
					ratingMap.put(object.getRatingId2(), object.getRating2());
				}
				if(object.getRatingId3() != null && object.getRating3() != null){
					ratingMap.put(object.getRatingId3(), object.getRating3());
				}
			}
		}
		return ratingMap;
	}
    
	public Map<String,JsonObject> getFinalScoreOfEverySection(TreeMap<String,TreeMap<Integer,Object[]>> parameterMap) throws JSONException{
		LinkedHashMap<String,JsonObject> dataMap = new LinkedHashMap<String, JsonObject>();
		DecimalFormat df = new DecimalFormat("#.00"); 
		if(parameterMap != null && parameterMap.size() > 0){
			for(String section : parameterMap.keySet()){
				JsonObject scoreContainer = new JsonObject();
				Double empFinalRatings = 0d;
				Double mngFinalRatings = 0d;
				Double revFinalRatings = 0d;
				Double sumWeightage = 0.0;
				Integer noOfQuestions = 0;  // No of questions
				TreeMap<Integer,Object[]> subMap = parameterMap.get(section);
				if(subMap != null && subMap.size() > 0){
					for(Integer qId : subMap.keySet()){
						Object[] dataArray = subMap.get(qId);
						 if(dataArray[7] != null && dataArray[7].equals("Y")){
							 	noOfQuestions += 1;    
							 	Double weightage = (Double) dataArray[8];
							 	sumWeightage += weightage;
							 	Double empScore =  (Double.parseDouble(dataArray[4].toString())) * weightage;
							 	Double mngScore =  (Double.parseDouble(dataArray[5].toString())) * weightage;
							 	Double revScore = (Double.parseDouble(dataArray[6].toString())) * weightage;
							 	empFinalRatings += empScore;
							 	mngFinalRatings += mngScore;
							 	revFinalRatings += revScore;
						 }
					}
					if(noOfQuestions > 0){
						Double empAverageScore = empFinalRatings / sumWeightage;
						Double mngAverageScore = mngFinalRatings / sumWeightage;
						Double revAverageScore = revFinalRatings / sumWeightage;
						scoreContainer.addProperty("e",empAverageScore);
						scoreContainer.addProperty("m",mngAverageScore);
						scoreContainer.addProperty("r",revAverageScore);
						dataMap.put(section, scoreContainer);
					}else {
						dataMap.put(section, scoreContainer);
					}
				}
			}
		}
		return dataMap;
	}

	public Map<Integer,Object[]> getQuestionAndResponseData(Map<String, Object[]> competencyDataMap) {
		Map<Integer,Object[]> questionsAndResponseMap = new HashMap<Integer, Object[]>();   
		if(competencyDataMap != null && competencyDataMap.size() > 0){
			for(String conjuctedKey : competencyDataMap.keySet()){
				String[] keyArray = conjuctedKey.split("_");
				Integer questionId = Integer.parseInt(keyArray[1]);
				Object[] dataArray = competencyDataMap.get(conjuctedKey);
				questionsAndResponseMap.put(questionId, dataArray);
			}
		}
		return questionsAndResponseMap;
	}

	@Transactional
	public Boolean updateEmployeeCompetencies(  Integer empId,PhaseStatus phaseNo,  Map<Integer, Object[]> questionAndResponseMap, Integer cycleId, String statusOfMember) throws Exception {
		return competencyDao.updateCompetenciesOfEmployee(empId,phaseNo,questionAndResponseMap,cycleId,statusOfMember);
	}
	
	@Transactional
	public Boolean updateCurrentPhaseOfEmployee(Integer cycleId, Integer empId, PhaseStatus currentPhase) {
		Boolean allUpdated = null;
		if(cycleId != null && empId != null && currentPhase != null){
			allUpdated = competencyDao.updatePhaseStatusOfEmplFlowEntity(empId,cycleId,currentPhase);
		}
		return allUpdated;
	}
	
	@Transactional
	public Boolean updateCurrentPhaseEmplEntity(Integer empId,Integer cycleId,PhaseStatus currentPhase) {
		return competencyDao.updatePhaseOfEmployeeEntity(empId,cycleId,currentPhase);
	}
	
	@Transactional(readOnly=true)
	public Double[] getFinalScoreOfEachPhase(Integer empId) {
		if(empId != null) {
			return competencyDao.getFinalScoreOfEachPhase(empId);
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public Map<Integer,Double[]> getMapOfFinalScore(Integer cycleId){
		if(cycleId != null) {
			return competencyDao.getMapOfFinalScore(cycleId);
		}
		return null;
	}
	
	public Map<Integer,String[]> fetchPerformanceIndicators(List<SelfAppraisal> dataList){
		Map<Integer,String[]> piMap = new HashMap<Integer, String[]>();
		if(dataList != null && dataList.size() > 0){
			for(SelfAppraisal sf : dataList){
				String[] piArray = new String[3];
				piArray[0] = sf.getpIndicatorId1(); // previous level
				piArray[1] = sf.getpIndicatorId2(); // current level
				piArray[2] = sf.getpIndicatorId3(); // next level
			    piMap.put(sf.getQuestionId(), piArray);
			}
		}
		return piMap;
	}
	
	public Map<Integer,String> checkQuestionIsRatedOrNot(List<SelfAppraisal> dataList){
		Map<Integer,String> questionMap = new HashMap<Integer, String>();
		if(dataList != null && dataList.size() > 0){
			for(SelfAppraisal object : dataList){
				if(object.getQuestionId() != null && object.getRatingYN() != null && StringUtils.hasText(object.getRatingYN())) {
					questionMap.put(object.getQuestionId(), object.getRatingYN());
				}
			}
		}
		return questionMap;
	}

	public Map<Integer, List<StrengthWeakness>> getStrengthAndweaknessDataMap(Map<String, String[]> strgWeakMap,PhaseStatus currentPhase,Integer appraiserId) {
		Map<Integer,List<StrengthWeakness>> dataMap = new HashMap<Integer, List<StrengthWeakness>>(); 
		Integer empId = null;
		List<StrengthWeakness> swList = new ArrayList<StrengthWeakness>();
		if(strgWeakMap != null && strgWeakMap.size() > 0){
			for(String conjuctedKey : strgWeakMap.keySet()) {
				String[] conjuctedKeyArray = conjuctedKey.split("_");
				empId = Integer.parseInt(conjuctedKeyArray[0]);
				String strengthOrWeakness = conjuctedKeyArray[1];
				String[] userStrAndWeaknessArray = strgWeakMap.get(conjuctedKey);
				for(int i=0; i < userStrAndWeaknessArray.length; i++){
					StrengthWeakness sw = new StrengthWeakness();
					sw.setDescription(userStrAndWeaknessArray[i]);
					sw.setIsStrengthOrWeakness(strengthOrWeakness);
					sw.setPhaseId(currentPhase.ordinal());
					if(currentPhase == PhaseStatus.Self){
						sw.setNextRoleId(empId);
					}else{
						sw.setNextRoleId(appraiserId);
					}
					swList.add(sw);
				}
			}
			dataMap.put(empId, swList);
		}
		return dataMap;
	}

	public Boolean updateStrengthAndWeaknesses(Map<Integer, List<StrengthWeakness>> strWeakContainerMap) {
		if(strWeakContainerMap != null && strWeakContainerMap.size() > 0) {
			Boolean isUpdated = competencyDao.updateStrengthAndWeakness(strWeakContainerMap);
			return isUpdated;
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public List<StrengthWeakness> getStrengthAndWeakness(Integer empId){
		if(empId != null) {
			List<StrengthWeakness> swList = competencyDao.getStrengthsAndWeaknesses(empId);
			return swList;
		}
		
		
		return null;
	}
	
	public Map<String,List<String>> prepareStrengthAndWeaknessMap(Integer empId){
		Map<String,List<String>> swMap = new HashMap<String, List<String>>();
		List<StrengthWeakness> swList = getStrengthAndWeakness(empId);
		List<String> emp_Strengths_List = new ArrayList<String>();
		List<String> emp_Weaknesses_List = new ArrayList<String>();
		List<String> appr_emp_Strengths_List = new ArrayList<String>();
		List<String> appr_emp_Weaknesses_List = new ArrayList<String>();
		if(swList != null && swList.size() > 0){
			for(StrengthWeakness object : swList){
				if(object.getIsStrengthOrWeakness().equals("Strengths") && object.getPhaseId().equals(1)){
					emp_Strengths_List.add(object.getDescription());
				}else if(object.getIsStrengthOrWeakness().equals("Weaknesses") && object.getPhaseId().equals(1)) {
					emp_Weaknesses_List.add(object.getDescription());
				}else if(object.getIsStrengthOrWeakness().equals("Strengths") && object.getPhaseId().equals(2)){
					appr_emp_Strengths_List.add(object.getDescription());
				}else {
					appr_emp_Weaknesses_List.add(object.getDescription());
				}
			}
			swMap.put("1_Strengths", emp_Strengths_List);
			swMap.put("1_Weaknesses", emp_Weaknesses_List);
			swMap.put("2_Strengths", appr_emp_Strengths_List);
			swMap.put("2_Weaknesses", appr_emp_Weaknesses_List);
		}
		return swMap;
	}
	
	@Transactional(readOnly=true)
	public Boolean checkDoesAllEmployeeHasSubmitted() {
		return null;
	}

	public Map<Integer, String> getRemarkOfUserById(Integer cycleId) {
		// TODO Auto-generated method stub
		return competencyDao.getRemarkOfUser(cycleId);
	}
	
	public Boolean sendEmailToAppr(Integer empId,Integer cycleId,String date,Integer phaseId) {
		List<RecipientMail> recipientMailList = competencyDao.fetchMailIdForEmp(empId,cycleId);
        AppraisalCycle currentCycle = loginDao.getCurrentAppraisalCycle();
		EmailMessage mail = new EmailMessage();
		mail.setSubject(currentCycle.getCycleName());
		emailService.sendApprEmail(recipientMailList, mail,date,phaseId);
		return true;
	}
	
	
	public float returnFinalReviewerRating(Integer cycleId, Integer empId) {
		 return competencyDao.getReviewerFinalRating(cycleId,empId);
	}


}
