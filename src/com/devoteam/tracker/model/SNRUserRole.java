package com.devoteam.tracker.model;

public class SNRUserRole {
	private long snrId;
	private long userId;
	private String role;
	private String userName;
	private long thirdPartyId;
	private String thirdPartyName;
	private int no;
	private int count;

	public SNRUserRole(long snrId, long userId, String role, String userName,
			long thirdPartyId, String thirdPartyName, int no, int count) {
		this.snrId = snrId;
		this.userId = userId;
		this.role = role;
		this.userName = userName;
		this.thirdPartyId = thirdPartyId;
		this.thirdPartyName = thirdPartyName;
		this.no = no;
		this.count = count;
	}
	
	public long getSNRId() {
		return snrId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public String getUserIdString() {
		return String.valueOf(userId);
	}
	
	public String getRole() {
		return role;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public long getThirdPartyId() {
		return thirdPartyId;
	}
	
	public String getThirdPartyName() {
		return thirdPartyName==null?"":thirdPartyName;
	}
	
	public int getNo() {
		return no;
	}
	
	public String getNoString() {
		return no==1||no==2?String.valueOf(no):"";
	}

	public int getCount() {
		return count;
	}
	
	public String[] getListValueArray() {
		String[] values = {this.getUserName(),
			this.getRole(), this.getNoString(),
			this.getThirdPartyName()};
		return values;
	}
}
