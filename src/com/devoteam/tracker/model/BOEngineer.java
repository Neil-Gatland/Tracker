package com.devoteam.tracker.model;

import java.io.Serializable;

public class BOEngineer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1921341507518644385L;
	private String name;
	private long userId;
	private String firstName;
	private String surname;
	private String suffix;
	
	public BOEngineer(String name) {
		this.name = name;
		this.userId = 0;
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
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getUserId() {
		return userId;
	}
}
