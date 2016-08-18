package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UserAdminListItem implements Serializable {
	
	private static final long serialVersionUID = 5044187088301006299L;
	
	private long userId;
	private String userType;
	private String surname;
	private String firstname;
	private String usernameSuffix;
	private String status;
	private Date changeDT;
	private String customerName;
	private String thirdPartyName;
	private String administrator;
	private String boEngineer;
	private String scheduler;
	private String accessAdministrator;
	private String crmAdministrator;
	private String fieldEngineer;
	private String financeAdministrator;
	private String pmo;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private String email;
	
	public UserAdminListItem(long userId, String userType, String surname, String firstname,
		String usernameSuffix, String status, Date changeDT, String customerName, String thirdPartyName,
		String administrator, String boEngineer, String scheduler, String accessAdministrator,
		String crmAdministrator, String fieldEngineer, String financeAdministrator,
		String pmo, Date lastUpdatedDate, String lastUpdatedBy, String email) {
		this.userId = userId;
		this.userType = userType;
		this.surname = surname;
		this.firstname = firstname;
		this.usernameSuffix = usernameSuffix;
		this.status = status;
		this.changeDT = changeDT;
		this.customerName = customerName;
		this.thirdPartyName = thirdPartyName;
		this.administrator = administrator;
		this.boEngineer = boEngineer;
		this.scheduler = scheduler;
		this.accessAdministrator = accessAdministrator;
		this.crmAdministrator = crmAdministrator;
		this.fieldEngineer = fieldEngineer;
		this.financeAdministrator = financeAdministrator;
		this.pmo = pmo;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.email = email;
	}
		
	public long getUserId() {
		return userId;
	}
	
	public String getUserIdString() {
		return Long.toString(userId);
	}
	
	public String getUserType() {
		return userType;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getUsernameSuffix() {
		return usernameSuffix;
	}
	
	public String getUsername() {
		return firstname + " " + surname +
			(usernameSuffix==null?"":" " + usernameSuffix);
	}
	
	public String getStatus() {
		return status;
	}
	
	public Date getChangeDT() {
		return changeDT;
	}
	
	public String getCustomerName() {
		return customerName==null?"":customerName;
	}
	
	public String getThirdPartyName() {
		return thirdPartyName==null?"":thirdPartyName;
	}
	
	public String getCustomerPlusThirdParty() {
		return (customerName==null?"":customerName) +
			(thirdPartyName==null?"":" (" +thirdPartyName + ")");
	}
	
	public String getAdministrator() {
		return administrator.equalsIgnoreCase("Y")?"*":"";
	}
	
	public String getBOEngineer() {
		return boEngineer.equalsIgnoreCase("Y")?"*":"";
	}
	
	public String getScheduler() {
		return scheduler.equalsIgnoreCase("Y")?"*":"";
	}
	
	public String getAccessAdministrator() {
		return accessAdministrator.equalsIgnoreCase("Y")?"*":"";
	}
	
	public String getCRMAdministrator() {
		return crmAdministrator.equalsIgnoreCase("Y")?"*":"";
	}
	
	public String getFieldEngineer() {
		return fieldEngineer.equalsIgnoreCase("Y")?"*":"";
	}
	
	public String getFinanceAdministrator() {
		return financeAdministrator.equalsIgnoreCase("Y")?"*":"";
	}
	
	public String getPMO() {
		return pmo.equalsIgnoreCase("Y")?"*":"";
	}
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public String getEmail() {
		return email;
	}

	public String[] getListValueArray() {
		String[] values = {this.getUserIdString(), this.getUsername(),
			this.getStatus(), this.getUserType(), 
			this.getCustomerPlusThirdParty(), this.getAccessAdministrator(),
			this.getAdministrator(), this.getBOEngineer(),
			this.getCRMAdministrator(), this.getFieldEngineer(),
			this.getFinanceAdministrator(), this.getPMO(),
			this.getScheduler(), this.getEmail()};
		return values;
	}
	
	
}
