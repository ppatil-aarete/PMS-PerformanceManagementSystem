package com.thirdi.pms.competency;

public class SelfAppraisal {

private Integer empId;
private String userName;
private String section;
private Integer questionId;
private String question;
private Double weightage;
private Integer sectionColOrder;
private Integer questionColOrder;
private String pIndicatorId1;
private String pIndicatorId2;
private String pIndicatorId3;
private Integer ratingId1;
private Integer ratingId2;
private Integer ratingId3;
private Integer rating1;
private Integer rating2;
private Integer rating3;
private Integer cycleId;
private String ratingYN;
private Integer phase1Status;
private Integer phase2Status;
private Integer phase3Status;
private Boolean isFinalized;
private PhaseStatus cuurent_Phase_Id;
private String remark1;
private String remark2;
private String remark3;


public String getRemark1() {
	return remark1;
}

public void setRemark1(String remark1) {
	this.remark1 = remark1;
}

public String getRemark2() {
	return remark2;
}

public void setRemark2(String remark2) {
	this.remark2 = remark2;
}

public String getRemark3() {
	return remark3;
}

public void setRemark3(String remark3) {
	this.remark3 = remark3;
}


public String getRatingYN() {
	return ratingYN;
}

public void setRatingYN(String ratingYN) {
	this.ratingYN = ratingYN;
}

public Integer getPhase1Status() {
	return phase1Status;
}

public Integer getQuestionId() {
	return questionId;
}

public void setQuestionId(Integer questionId) {
	this.questionId = questionId;
}

public String getpIndicatorId1() {
	return pIndicatorId1;
}

public void setpIndicatorId1(String pIndicatorId1) {
	this.pIndicatorId1 = pIndicatorId1;
}

public String getpIndicatorId2() {
	return pIndicatorId2;
}

public void setpIndicatorId2(String pIndicatorId2) {
	this.pIndicatorId2 = pIndicatorId2;
}

public String getpIndicatorId3() {
	return pIndicatorId3;
}

public void setpIndicatorId3(String pIndicatorId3) {
	this.pIndicatorId3 = pIndicatorId3;
}

public void setPhase1Status(Integer phase1Status) {
	this.phase1Status = phase1Status;
}

public Integer getPhase2Status() {
	return phase2Status;
}

public void setPhase2Status(Integer phase2Status) {
	this.phase2Status = phase2Status;
}

public Integer getPhase3Status() {
	return phase3Status;
}

public void setPhase3Status(Integer phase3Status) {
	this.phase3Status = phase3Status;
}

public Boolean getIsFinalized() {
	return isFinalized;
}

public void setIsFinalized(Boolean isFinalized) {
	this.isFinalized = isFinalized;
}

public PhaseStatus getCuurent_Phase_Id() {
	return cuurent_Phase_Id;
}

public void setCuurent_Phase_Id(PhaseStatus cuurent_Phase_Id) {
	this.cuurent_Phase_Id = cuurent_Phase_Id;
}

public Integer getCycleId() {
	return cycleId;
}

public void setCycleId(Integer cycleId) {
	this.cycleId = cycleId;
}

public Integer getEmpId() {
	return empId;
}

public void setEmpId(Integer empId) {
	this.empId = empId;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getSection() {
	return section;
}

public void setSection(String section) {
	this.section = section;
}

public String getQuestion() {
	return question;
}

public void setQuestion(String question) {
	this.question = question;
}

public Integer getSectionColOrder() {
	return sectionColOrder;
}

public void setSectionColOrder(Integer sectionColOrder) {
	this.sectionColOrder = sectionColOrder;
}

public Integer getQuestionColOrder() {
	return questionColOrder;
}

public void setQuestionColOrder(Integer questionColOrder) {
	this.questionColOrder = questionColOrder;
}
public Integer getRatingId1() {
	return ratingId1;
}

public void setRatingId1(Integer ratingId1) {
	this.ratingId1 = ratingId1;
}

public Integer getRatingId2() {
	return ratingId2;
}

public void setRatingId2(Integer ratingId2) {
	this.ratingId2 = ratingId2;
}

public Integer getRatingId3() {
	return ratingId3;
}

public void setRatingId3(Integer ratingId3) {
	this.ratingId3 = ratingId3;
}

public Integer getRating1() {
	return rating1;
}

public void setRating1(Integer rating1) {
	this.rating1 = rating1;
}

public Integer getRating2() {
	return rating2;
}

public void setRating2(Integer rating2) {
	this.rating2 = rating2;
}

public Integer getRating3() {
	return rating3;
}

public void setRating3(Integer rating3) {
	this.rating3 = rating3;
}

public Double getWeightage() {
	return weightage;
}

public void setWeightage(Double weightage) {
	this.weightage = weightage;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((empId == null) ? 0 : empId.hashCode());
	result = prime * result + ((userName == null) ? 0 : userName.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	SelfAppraisal other = (SelfAppraisal) obj;
	if (empId == null) {
		if (other.empId != null)
			return false;
	} else if (!empId.equals(other.empId))
		return false;
	if (userName == null) {
		if (other.userName != null)
			return false;
	} else if (!userName.equals(other.userName))
		return false;
	return true;
}





}
