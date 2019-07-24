package com.thirdi.pms.admin.modal;

import org.springframework.stereotype.Component;

import com.thirdi.pms.admin.CycleStatus;
@Component
public class AppraisalCycle {

	private Integer cycleId;
	private String cycleName;
	private String startDate;
	private String endate;
	private String selfApprStartDate;
	private String selfApprEndDate;
	private String mngApprStartDate;
	private String mngApprEndDate;
	private String revApprStartDate;
	private String revApprEndDate;
	private CycleStatus isFinalized;
	private String finalizedDate;
	private String financialYear;
	
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public Integer getCycleId() {
		return cycleId;
	}
	public void setCycleId(Integer cycleId) {
		this.cycleId = cycleId;
	}
	public String getCycleName() {
		return cycleName;
	}
	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndate() {
		return endate;
	}
	public void setEndate(String endate) {
		this.endate = endate;
	}
	public String getSelfApprStartDate() {
		return selfApprStartDate;
	}
	public void setSelfApprStartDate(String selfApprStartDate) {
		this.selfApprStartDate = selfApprStartDate;
	}
	public String getSelfApprEndDate() {
		return selfApprEndDate;
	}
	public void setSelfApprEndDate(String selfApprEndDate) {
		this.selfApprEndDate = selfApprEndDate;
	}
	public String getMngApprStartDate() {
		return mngApprStartDate;
	}
	public void setMngApprStartDate(String mngfApprStartDate) {
		this.mngApprStartDate = mngfApprStartDate;
	}
	public String getMngApprEndDate() {
		return mngApprEndDate;
	}
	public void setMngApprEndDate(String mngfApprEndDate) {
		this.mngApprEndDate = mngfApprEndDate;
	}
	public String getRevApprStartDate() {
		return revApprStartDate;
	}
	public void setRevApprStartDate(String revApprStartDate) {
		this.revApprStartDate = revApprStartDate;
	}
	public String getRevApprEndDate() {
		return revApprEndDate;
	}
	public void setRevApprEndDate(String revApprEndDate) {
		this.revApprEndDate = revApprEndDate;
	}
	public String getFinalizedDate() {
		return finalizedDate;
	}
	public void setFinalizedDate(String finalizedDate) {
		this.finalizedDate = finalizedDate;
	}
	public CycleStatus getIsFinalized() {
		return isFinalized;
	}
	public void setIsFinalized(CycleStatus isFinalized) {
		this.isFinalized = isFinalized;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cycleId == null) ? 0 : cycleId.hashCode());
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
		AppraisalCycle other = (AppraisalCycle) obj;
		if (cycleId == null) {
			if (other.cycleId != null)
				return false;
		} else if (!cycleId.equals(other.cycleId))
			return false;
		return true;
	}
	
	
}
