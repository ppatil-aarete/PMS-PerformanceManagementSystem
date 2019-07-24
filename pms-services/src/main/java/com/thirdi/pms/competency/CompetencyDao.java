package com.thirdi.pms.competency;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.thirdi.pms.admin.Recipient;
import com.thirdi.pms.admin.RecipientMail;

public interface CompetencyDao {

	public List<SelfAppraisal> getListOfEmpAppraisals(Integer empId,Integer currentCycleId, Boolean flag);
	
	public Map<Integer,Integer> getMappingOfIds();
	
	public Boolean updateMappingsInEployeeTable(final Map<Integer,Integer> idMapper);

	public Boolean updateCurrentPhaseOfEmployee(final Integer empId);
	
	public Integer getCurrentPhaseOfEmployee(final Integer empId);
	
	public Boolean updateIsFinalizedStatus(final Integer empId);

	public Boolean updateCompetenciesOfEmployee(final Integer empId, final PhaseStatus phaseNo, final Map<Integer, Object[]> questionAndResponseMap, final Integer cycleId,String statusOfMember) throws Exception;

	public Boolean updatePhaseOfEmployeeEntity(Integer empId, Integer cycleId,PhaseStatus currentPhase);

	public Boolean updatePhaseStatusOfEmplFlowEntity(Integer empId, Integer cycleId, PhaseStatus currentPhase);

	public Double[] getFinalScoreOfEachPhase(final Integer empId);

	public Boolean updateStrengthAndWeakness(Map<Integer, List<StrengthWeakness>> strWeakContainerMap);

	public List<StrengthWeakness> getStrengthsAndWeaknesses(Integer empId);

	public Map<Integer, Double[]> getMapOfFinalScore(Integer cycleId);

	public Boolean updateReviewerRatings(Integer empId, Float ratings , Integer revId, Integer cycleId);

	public Boolean updateReviewerRemarks(Integer empId, String remarks , Integer revId, Integer cycleId);

	public Map<Integer, String> getRemarkOfUser(Integer cycleId);
	
	public List<RecipientMail> fetchMailIdForEmp(Integer empId,Integer cycleId);

	public float getReviewerFinalRating(Integer cycleId, Integer empId);
	
}
