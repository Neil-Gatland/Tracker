package com.devoteam.tracker.model;

public class UserJobType {

	private long userId;
	private String jobType;
	private String redundant;
	
	public UserJobType(long userId, String jobType, String redundant) {
		this.userId = userId;
		this.jobType = jobType;
		this.redundant = redundant;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public String getUserIdString() {
		return String.valueOf(userId);
	}
	
	public String getJobType() {
		return jobType;
	}
	
	public String getRedundant() {
		return redundant;
	}
}
