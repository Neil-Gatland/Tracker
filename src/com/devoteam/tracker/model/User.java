package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class User implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 6110746788531470480L;

	/** Constant for column Status value "Active" */
	public static final String STATUS_ACTIVE = "Active";

	/** Constant for column Status value "Suspended" */
	public static final String STATUS_SUSPENDED = "Suspended";

	/** Constant for column Status value "Inactive" */
	public static final String STATUS_INACTIVE = "Inactive";

	/** All values for column Status */
	public static final String[] STATUS_VALUES = new String[] {
		STATUS_ACTIVE,
		STATUS_SUSPENDED,
		STATUS_INACTIVE
	};
	
	/** Constant for column User_Type value "Devoteam" */
	public static final String USER_TYPE_DEVOTEAM = "Devoteam";

	/** Constant for column User_Type value "Third Party" */
	public static final String USER_TYPE_THIRD_PARTY = "Third Party";

	/** Constant for column User_Type value "Customer" */
	public static final String USER_TYPE_CUSTOMER = "Customer";

	/** All values for column User_Type */
	public static final String[] USER_TYPE_VALUES = new String[] {
		USER_TYPE_DEVOTEAM,
		USER_TYPE_THIRD_PARTY,
		USER_TYPE_CUSTOMER
	};
	
	private long userId;
	private long customerId;
	private String userType;
	private String surname;
	private String firstname;
	private String usernameSuffix;
	private String status;
	private String password;
	private Date changeDT;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private Collection<UserRole> userRoles;
	private Map<Long, String> customers = new HashMap<Long, String>();
	
	public User(long userId, long customerId, String userType, String surname, String firstname,
		String usernameSuffix, String status, String password, Date changeDT) {
		this.userId = userId;
		this.customerId = customerId;
		this.userType = userType;
		this.surname = surname;
		this.firstname = firstname;
		this.usernameSuffix = usernameSuffix;
		this.status = status;
		this.password = password;
		this.changeDT = changeDT;
	}
		
	public User(long userId, long customerId, String userType, String surname, String firstname,
		String usernameSuffix, String status, String password, Date changeDT,
		Date lastUpdatedDate, String lastUpdatedBy) {
		this.userId = userId;
		this.customerId = customerId;
		this.userType = userType;
		this.surname = surname;
		this.firstname = firstname;
		this.usernameSuffix = usernameSuffix;
		this.status = status;
		this.password = password;
		this.changeDT = changeDT;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
		
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long userId) {
		this.customerId = customerId;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getUsernameSuffix() {
		return usernameSuffix;
	}
	
	public void setUsernameSuffix(String usernameSuffix) {
		this.usernameSuffix = usernameSuffix;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getChangeDT() {
		return changeDT;
	}
	
	public void setChangeDT(Date changeDT) {
		this.changeDT = changeDT;
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
	
	public String getNameForLastUpdatedBy() {
		StringBuilder sB = new StringBuilder(firstname);
		sB.append(" ");
		sB.append(surname);
		if ((usernameSuffix != null) && (usernameSuffix.length() > 0)) {
			sB.append(" ");
			sB.append(usernameSuffix);
		}
		if (sB.length() > 200) {
			sB.setLength(200);
		}
		return sB.toString();
	}
	
	public String getNameForLastUpdatedBy2() {
		StringBuilder sB = new StringBuilder(firstname);
		sB.append(surname);
		if ((usernameSuffix != null) && (usernameSuffix.length() > 0)) {
			sB.append(usernameSuffix);
		}
		sB.append(".");
		sB.append(surname);
		if (sB.length() > 200) {
			sB.setLength(200);
		}
		return sB.toString();
	}

	public String getFullname() {
	StringBuilder sB = new StringBuilder(firstname);
	if ((usernameSuffix != null) && (usernameSuffix.length() > 0)) {
		sB.append(usernameSuffix);
	}
	sB.append(".");
	sB.append(surname);
	if (sB.length() > 200) {
		sB.setLength(200);
	}
	return sB.toString();
}
	
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public Collection<UserRole> getUserRoles() {
		return userRoles;
	}
	
	public void setUserRoles(Collection<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	public boolean hasUserRole(String userRole) {
		boolean hasUserRole = false;
		if (!userType.equals("Customer")) { // ignore Customer users as they never have a role
			for (Iterator<UserRole> it = userRoles.iterator(); it.hasNext(); ) {
				UserRole uR = it.next();
				if (userRole.equals(uR.getRole())) {
					hasUserRole = true;
					break;
				}
			}
		}		
		return hasUserRole;
	}
	
	public boolean hasOnlyReadUserRoles() {
		String[] readOnlyRoles = UserRole.READ_ONLY_ROLE_VALUES;
		boolean readOnly = true;
		for (Iterator<UserRole> it = userRoles.iterator(); it.hasNext(); ) {
			UserRole uR = it.next();
			if (!containsRole(readOnlyRoles, uR.getRole())) {
				readOnly = false;
				break;
			}
		}
		return readOnly;
	}
	
	private boolean containsRole(String[] readOnlyRoles, String thisRole) {
		boolean found = false;
		for (int i = 0; i < readOnlyRoles.length; i++) {
			if (readOnlyRoles[i].equals(thisRole)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
	public void addCustomer(long customerId, String customerName) {
		customers.put(new Long(customerId), customerName);
	}
	
	public void setCustomers(Map<Long, String> customers) {
		this.customers = customers;
	}
	
	public Map<Long, String> getCustomers() {
		return customers;
	}
	
	public long[] getCustomerIds() {
		Set<Long> keySet = customers.keySet();
		Long[] keyArray = (Long[]) keySet.toArray();
		long[] customerIds = new long[customers.size()];
		for (int i = 0; i < customers.size(); i++) {
			customerIds[i] = keyArray[i].longValue();
		}
		return customerIds;
	}
	
	public String[] getCustomerNames() {
		Collection<String> values = customers.values();
		String[] customerNames = new String[values.size()];
		int i = 0;
		for (Iterator<String> it = values.iterator(); it.hasNext(); ) {
			customerNames[i++] = it.next();
		}
		return  customerNames;
	}
}
