package com.devoteam.tracker.model;

import java.io.Serializable;

public class FieldEngineer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8065325192964328588L;
	private String name;
	private String firstName;
	private String surname;
	private String suffix;
	private int rank;
	private String vendor;
	private long userId;
	private long thirdPartyId;
	
	public FieldEngineer(String name, int rank, String vendor) {
		this.name = name;
		this.rank = rank;
		this.vendor = vendor;
		this.userId = 0;
		this.thirdPartyId = 0;
		this.suffix = null;
   		int dot = name.indexOf(".");
		if (dot > 0) {
			String part1 = name.substring(0, dot);
			String end = part1.substring(dot-1, dot);
			this.firstName = part1;
			this.surname = name.substring(dot+1);
			if (end.equals(end.toUpperCase())) { //name has a suffix
				suffix = end;
				firstName = part1.substring(0, dot-1);
			}
		} else {
			this.firstName = null;
			this.surname = null;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getSuffix() {
		return suffix;
	}
	
	public int getRank() {
		return rank;
	}
	
	public String getVendor() {
		return vendor;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setThirdPartyId(long thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}
	
	public long getThirdPartyId() {
		return thirdPartyId;
	}
}
