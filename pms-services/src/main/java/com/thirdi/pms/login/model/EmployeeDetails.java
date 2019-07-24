package com.thirdi.pms.login.model;

import com.thirdi.pms.competency.PhaseStatus;

public class EmployeeDetails {

private Integer selfId;
private PhaseStatus currentPhase; 
private Integer apprOrRevId;
private	Integer status;
private String completionDate; 

public Integer getSelfId() {
	return selfId;
}

public void setSelfId(Integer selfId) {
	this.selfId = selfId;
}

public PhaseStatus getCurrentPhase() {
	return currentPhase;
}

public void setCurrentPhase(PhaseStatus currentPhase) {
	this.currentPhase = currentPhase;
}

public Integer getNextUserRoleId() {
	return apprOrRevId;
}

public void setNextUserRoleId(Integer apprOrRevId) {
	this.apprOrRevId = apprOrRevId;
}

public Integer getStatus() {
	return status;
}

public void setStatus(Integer status) {
	this.status = status;
}

public String getCompletionDate() {
	return completionDate;
}

public void setCompletionDate(String completionDate) {
	this.completionDate = completionDate;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((currentPhase == null) ? 0 : currentPhase.hashCode());
	result = prime * result + ((apprOrRevId == null) ? 0 : apprOrRevId.hashCode());
	result = prime * result + ((selfId == null) ? 0 : selfId.hashCode());
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
	EmployeeDetails other = (EmployeeDetails) obj;
	if (currentPhase != other.currentPhase)
		return false;
	if (apprOrRevId == null) {
		if (other.apprOrRevId != null)
			return false;
	} else if (!apprOrRevId.equals(other.apprOrRevId))
		return false;
	if (selfId == null) {
		if (other.selfId != null)
			return false;
	} else if (!selfId.equals(other.selfId))
		return false;
	return true;
}



}
