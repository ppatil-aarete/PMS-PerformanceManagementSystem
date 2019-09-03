package com.thirdi.pms.competency;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jettison.json.JSONException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thirdi.pms.goal.model.Goal;

public interface CompetencyService {

	public List<SelfAppraisal> getListOfSelfAppraisal(Integer empId, Integer currentCycleId, Boolean flag);

	public Map<Integer, Integer> getMappingOfIds();

	public Boolean updateMappingOfEmployeeFlowTable(final Map<Integer, Integer> idMapper);

	public Integer getCurrentPhaseOfEmployee(Integer empId);

	public TreeMap<String, TreeMap<Integer, Object[]>> getRatingsAndQuestionsOfCompetencies(
			List<SelfAppraisal> dataList) throws JSONException;

	public Map<Integer, String> getQuestionFromId(List<SelfAppraisal> dataList);

	public Map<String, JsonObject> getFinalScoreOfEverySection(TreeMap<String, TreeMap<Integer, Object[]>> parameterMap)
			throws JSONException;

	public Map<Integer, Object[]> getQuestionAndResponseData(Map<String, Object[]> competencyDataMap);

	public Boolean updateEmployeeCompetencies(Integer empId, PhaseStatus current_phase,
			Map<Integer, Object[]> questionAndResponseMap, Integer cycleId, String statusOfMember) throws Exception;

	public Boolean updateCurrentPhaseOfEmployee(Integer cycleId, Integer empId, PhaseStatus currentPhase);

	public Double[] getFinalScoreOfEachPhase(final Integer empId);

	public Map<Integer, String[]> fetchPerformanceIndicators(List<SelfAppraisal> dataList);

	public Boolean updateCurrentPhaseEmplEntity(Integer empId, Integer cycleId, PhaseStatus currentPhase);

	public Map<Integer, String> checkQuestionIsRatedOrNot(List<SelfAppraisal> dataList);

	public Map<Integer, List<StrengthWeakness>> getStrengthAndweaknessDataMap(Map<String, String[]> strgWeakMap,
			PhaseStatus currentPhase, Integer appraiserId);

	public Boolean updateStrengthAndWeaknesses(Map<Integer, List<StrengthWeakness>> strWeakContainerMap);

	public Map<String, List<String>> prepareStrengthAndWeaknessMap(Integer empId);

	public Boolean checkDoesAllEmployeeHasSubmitted();

	public Map<Integer, Double[]> getMapOfFinalScore(Integer cycleId);

	public Map<Integer, String> getRemarkOfUserById(Integer cycleId);

	public Boolean sendEmailToAppr(Integer empId, Integer cycleId, String date, Integer phaseId);

	public float returnFinalReviewerRating(Integer cycleId, Integer empId);

	public Boolean  setGoals(List<Goal> goalsList);
}
