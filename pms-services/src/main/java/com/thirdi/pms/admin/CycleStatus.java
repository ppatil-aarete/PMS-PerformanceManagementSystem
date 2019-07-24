package com.thirdi.pms.admin;

public enum CycleStatus {
     
	isRunning(0),isFinished(1);
	
    private Integer cycle;
	
	public Integer getPhaseValue(){
        return this.cycle;
    }
	
	private CycleStatus(Integer cycle) {
		this.cycle = cycle;
	}
}
