package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Date;

public class SystemParameters implements Serializable {

	private int passwordExpiry;
	private int displayCompleted;
	private int timeoutPeriod;
	private int timeoutPeriodSeconds;
	private int scheduleBefore;
	private int scheduleAfter;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private int liveDashboardRefresh;
	
	public SystemParameters(int passwordExpiry, int displayCompleted, 
			int timeoutPeriod, int scheduleBefore, int scheduleAfter, 
			Date lastUpdatedDate, String lastUpdatedBy, int liveDashboardRefresh) {
		this.passwordExpiry = passwordExpiry;
		this.displayCompleted = displayCompleted;
		this.timeoutPeriod = timeoutPeriod;
		this.timeoutPeriodSeconds = timeoutPeriod * 60;
		this.scheduleBefore = scheduleBefore;
		this.scheduleAfter = scheduleAfter;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.liveDashboardRefresh = liveDashboardRefresh;
	}
	
	public int getPasswordExpiry() {
		return passwordExpiry;
	}
	
	public int getDisplayCompleted() {
		return displayCompleted;
	}
	
	public int getTimeoutPeriod() {
		return timeoutPeriod;
	}
	
	public int getTimeoutPeriodSeconds() {
		return timeoutPeriodSeconds;
	}
	
	public int getLiveDashboardRefresh() {
		return liveDashboardRefresh;
	}
	
	public int getScheduleBefore() {
		return scheduleBefore;
	}
	
	public int getScheduleAfter() {
		return scheduleAfter;
	}
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
}
