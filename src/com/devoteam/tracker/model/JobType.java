package com.devoteam.tracker.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class JobType {
	private String jobType;
	private String projectRequestor;
	private String projectManager;
	private String actingCustomer;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private String projectRequestorEmail;
	private String projectManagerEmail;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public JobType(String jobType, String projectRequestor,
			Date lastUpdatedDate, String lastUpdatedBy,
			String projectManager, String actingCustomer,
			String projectRequestorEmail, String projectManagerEmail ) {
		this.jobType = jobType;
		this.projectRequestor = projectRequestor;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.projectManager = projectManager;
		this.actingCustomer = actingCustomer;
		this.projectRequestorEmail = projectRequestorEmail;
		this.projectManagerEmail = projectManagerEmail;
	}

	public String getJobType() {
		return jobType;
	}

	public String getProjectRequestor() {
		return projectRequestor;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public String getActingCustomer() {
		return actingCustomer;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public String getLastUpdatedDateString() {
		return lastUpdatedDate==null?"":dateFormatter.format(lastUpdatedDate);
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public String getProjectRequestorEmail() {
		return projectRequestorEmail;
	}
	
	public String getProjectManagerEmail() {
		return projectManagerEmail;
	}
	
	/*public String[] getListValueArray() {
		String[] values = {this.getJobType(), this.getProjectRequestor(),
				this.getProjectManager(), this.getActingCustomer(),	
			this.getLastUpdatedBy(), this.getLastUpdatedDateString()};
		return values;
	}*/
	
	public String[] getListValueArray() {
		String[] values = {this.getJobType(), this.getProjectRequestor(),
				this.getProjectRequestorEmail(), this.getProjectManager(), 
				this.getProjectManagerEmail(), this.getActingCustomer(),	
			this.getLastUpdatedBy(), this.getLastUpdatedDateString()};
		return values;
	}
}
