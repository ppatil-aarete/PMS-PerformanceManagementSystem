package com.thirdi.pms.competency;

public class StrengthWeakness {

private Integer empId;
private String description;
private String isStrengthOrWeakness;
private Integer phaseId;
private Integer nextRoleId;
private Integer updateStatus;


public Integer getEmpId() {
	return empId;
}
public void setEmpId(Integer empId) {
	this.empId = empId;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getIsStrengthOrWeakness() {
	return isStrengthOrWeakness;
}
public void setIsStrengthOrWeakness(String isStrengthOrWeakness) {
	this.isStrengthOrWeakness = isStrengthOrWeakness;
}
public Integer getPhaseId() {
	return phaseId;
}
public void setPhaseId(Integer phaseId) {
	this.phaseId = phaseId;
}
public Integer getNextRoleId() {
	return nextRoleId;
}
public void setNextRoleId(Integer nextRoleId) {
	this.nextRoleId = nextRoleId;
}

public Integer getUpdateStatus() {
	return updateStatus;
}
public void setUpdateStatus(Integer updateStatus) {
	this.updateStatus = updateStatus;
}

}
