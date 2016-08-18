package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Date;

public class UserRole implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2319165997371815741L;
	private long userId;
	private String role;
	private String feCapability;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private boolean checked;
	
	/** Constant for column Role value "Finance Administrator" */
	public static final String ROLE_FINANCE_ADMIN = "Finance Administrator";
	/** Constant for column Role value "Administrator" */
	public static final String ROLE_ADMINISTRATOR = "Administrator";
	/** Constant for column Role value "Scheduler" */
	public static final String ROLE_SCHEDULER = "Scheduler";
	/** Constant for column Role value "BO Engineer" */
	public static final String ROLE_B_O_ENGINEER = "BO Engineer";
	/** Constant for column Role value "Access Administrator" */
	public static final String ROLE_ACCESS_ADMIN = "Access Administrator";
	/** Constant for column Role value "CRM Administrator" */
	public static final String ROLE_CRM_ADMIN = "CRM Administrator";
	/** Constant for column Role value "Field Engineer" */
	public static final String ROLE_FIELD_ENGINEER = "Field Engineer";
	/** Constant for column Role value "PMO" */
	public static final String ROLE_PMO = "PMO";

	/** All values for column Role */
	public static final String[] ROLE_VALUES = new String[] {
		ROLE_FINANCE_ADMIN,
		ROLE_ADMINISTRATOR,
		ROLE_SCHEDULER,
		ROLE_B_O_ENGINEER,
		ROLE_ACCESS_ADMIN,
		ROLE_CRM_ADMIN,
		ROLE_FIELD_ENGINEER,
		ROLE_PMO
	};
	
	/** Values for column Role allowing only read access */
	public static final String[] READ_ONLY_ROLE_VALUES = new String[] {
		ROLE_FINANCE_ADMIN,
		ROLE_ADMINISTRATOR,
		ROLE_PMO
	};
	
	public UserRole(long userId, String role, String feCapability,
			Date lastUpdatedDate, String lastUpdatedBy) {
		this.userId = userId;
		this.role = role;
		this.feCapability = feCapability;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.checked = false;
	}
		
	public UserRole(long userId, String role, String feCapability) {
		this.userId = userId;
		this.role = role;
		this.feCapability = feCapability;
		this.checked = false;
	}
	
	public UserRole(long userId, String role, String feCapability,
			boolean checked) {
		this.userId = userId;
		this.role = role;
		this.feCapability = feCapability;
		this.checked = checked;
	}
	
	public UserRole(long userId, String role, boolean checked) {
		this.userId = userId;
		this.role = role;
		this.checked = checked;
	}
	
	public UserRole(String role, String feCapability) {
		this.role = role;
		this.feCapability = feCapability;
		this.checked = false;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getFECapability() {
		return feCapability;
	}
	
	public void setFECapability(String feCapability) {
		this.feCapability = feCapability;
	}
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public boolean getChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
