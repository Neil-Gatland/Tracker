package com.devoteam.tracker.model;

import java.io.Serializable;

public class SNRTechnology implements Serializable {
	private long snrId;
	private long technologyId;
	private String technologyName;
	private String implemented;
	private String technologyNameDisplay; 
	private String technologyColumn; 

	public SNRTechnology(long snrId, long technologyId,
			String technologyName, String implemented) {
		this.snrId = snrId;
		this.technologyId = technologyId;
		this.technologyName = technologyName;
		this.implemented = implemented;
	}

	public SNRTechnology (long snrId, long technologyId, String technologyName,
			String technologyNameDisplay, String implemented, String technologyColumn) {
		this.snrId = snrId;
		this.technologyId = technologyId;
		this.technologyName = technologyName;
		this.technologyNameDisplay = technologyNameDisplay;
		this.implemented = implemented;
		this.technologyColumn = technologyColumn;
	}

	public SNRTechnology(long snrId, String technologyName, 
			String technologyNameDisplay, long technologyId) {
		this.snrId = snrId;
		this.technologyId = technologyId;
		this.technologyName = technologyName;
		this.technologyNameDisplay = technologyNameDisplay;
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
	
	public String getSelectName() {
		return technologyName.replaceAll("\\s","");
	}
	
	public String getImplemented() {
		return implemented;
	}
	
	public void setImplemented(String implemented) {
		this.implemented = implemented;
	}
	
	public boolean isYes() {
		return implemented!=null&&implemented.equalsIgnoreCase("Y");
	}
	
	public boolean isNo() {
		return implemented!=null&&implemented.equalsIgnoreCase("N");
	}
	
	public boolean hasValue() {
		return implemented!=null&&(implemented.equalsIgnoreCase("Y")||
				implemented.equalsIgnoreCase("N"));
	}
	
	public String getTechnologyNameDisplay() {
		return technologyNameDisplay;
	}
	
	public String getTechnologyColumn() {
		return technologyColumn;
	}
}
