package com.devoteam.tracker.model;

public class SNRBOTechnology {
	private long snrId; //1
	private long technologyId; //2
	private String technologyName; //3
	private String technologyNameDisplay; //4
	private String implemented; //5
	private String technologyColumn; //6

	public SNRBOTechnology (long snrId, long technologyId, String technologyName,
			String technologyNameDisplay, String implemented, String technologyColumn) {
		this.snrId = snrId;
		this.technologyId = technologyId;
		this.technologyName = technologyName;
		this.technologyNameDisplay = technologyNameDisplay;
		this.implemented = implemented;
		this.technologyColumn = technologyColumn;
	}
	
	public long getSNRId() {
		return snrId;
	}
	
	public long getTechnologyId() {
		return technologyId;
	}
	
	public String getTechnologyName() {
		return technologyName;
	}
	
	public String getTechnologyNameDisplay() {
		return technologyNameDisplay;
	}
	
	public String getImplemented() {
		return implemented;
	}
	
	public String getTechnologyColumn() {
		return technologyColumn;
	}
}
