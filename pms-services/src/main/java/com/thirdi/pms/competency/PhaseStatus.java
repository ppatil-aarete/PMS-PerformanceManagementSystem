package com.thirdi.pms.competency;

public enum PhaseStatus {
	
	isfinalized(0),Self(1),Appraisar(2),Reviewer(3);
	
	private Integer phase;
	
	public Integer getPhaseValue(){
        return this.phase;
    }
	
	private PhaseStatus(Integer phase) {
		this.phase = phase;
	}
}
