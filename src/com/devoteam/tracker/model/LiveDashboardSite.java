package com.devoteam.tracker.model;

public class LiveDashboardSite {
	
	private String customer;				//1
	private String partner;					//2
	private String project;					//3
	private String site;					//4
	private String boList;					//5
	private String feList;					//6
	private String overallStatus;			//7
	private String checkedIn;				//8
	private String bookedOn;				//9
	private String siteAccessed;			//10
	private String physicalChecks;			//11
	private String preCallTest;				//12
	private String siteLocked;				//13
	private String hwInstalls;				//14
	private String commissioningFE;			//15
	private String commissioningBO;			//16
	private String txProvisioning;			//17
	private String fieldWork;				//18
	private String siteUnlocked;			//19
	private String postCallTest;			//20
	private String closureCode;				//21
	private String leaveSite;				//22
	private String bookOffSite;				//23
	private String performanceMonitoring;	//24
	private String initialHOP;				//25
	private String devoteamIssue;			//26
	private String customerIssue;			//27
	private String percentage;				//28
	private String scheduledDDMM;			//29
	
	public LiveDashboardSite(
			String customer,
			String partner,
			String project,
			String site,
			String boList,
			String feList,
			String overallStatus,
			String checkedIn,
			String bookedOn,
			String siteAccessed,
			String physicalChecks,
			String preCallTest,
			String siteLocked,
			String hwInstalls,
			String commissioningFE,
			String commissioningBO,
			String txProvisioning,
			String fieldWork,
			String siteUnlocked,
			String postCallTest,
			String closureCode,
			String leaveSite,
			String bookOffSite,
			String performanceMonitoring,
			String initialHOP,
			String devoteamIssue,
			String customerIssue,
			String percentage,
			String scheduledDDMM ) {
		this.customer = customer;
		this.partner = partner;
		this.project = project;
		this.site = site;
		this.boList = boList;
		this.feList = feList;
		this.overallStatus = overallStatus;
		this.checkedIn = checkedIn;
		this.bookedOn = bookedOn;
		this.siteAccessed = siteAccessed;
		this.physicalChecks = physicalChecks;
		this.preCallTest = preCallTest;
		this.siteLocked = siteLocked;
		this.hwInstalls = hwInstalls;
		this.commissioningFE = commissioningFE;
		this.commissioningBO = commissioningBO;
		this.txProvisioning = txProvisioning;
		this.fieldWork = fieldWork;
		this.siteUnlocked = siteUnlocked;
		this.postCallTest = postCallTest;
		this.closureCode = closureCode;
		this.leaveSite = leaveSite;
		this.bookOffSite = bookOffSite;
		this.performanceMonitoring = performanceMonitoring;
		this.initialHOP = initialHOP;
		this.devoteamIssue = devoteamIssue;
		this.customerIssue = customerIssue;
		this.percentage = percentage;
		this.scheduledDDMM = scheduledDDMM;
	}
	
	public String getCustomer() {
		return customer;
	}
	
	public String getPartner() {
		return partner;
	}

	public String getProject() {
		return project;
	}
	
	public String getSite() {
		return site;
	}
	
	public String getBoList() {
		return boList;
	}
	
	public String getFeList() {
		return feList;
	}
	
	public String getOverallStatus() {
		return overallStatus+((overallStatus.equals("In Progress"))?" ("+percentage+"%)":"");
	}
	
	public String getOverallStatusColour() {
		String colour = "Black";
		if (overallStatus.equals("Aborted")) colour="Red";
		if (overallStatus.equals("Partial")) colour="Orange";
		if (overallStatus.equals("Completed")) colour="Green";
		if (overallStatus.equals("Waiting Decision")) colour="BlackBold";
		return colour;
	}
	
	public String getCheckedIn() {
		return checkedIn;
	}
	
	public String getBookedOn() {
		return bookedOn;
	}
	
	public String getSiteAccessed() {
		return siteAccessed;
	}
	
	public String getPhysicalChecks() {
		return physicalChecks;
	}
	
	public String getPreCallTest() {
		return preCallTest;
	}
	
	public String getSiteLocked() {
		return siteLocked;
	}
	
	public String getHwInstalls() {
		return hwInstalls;
	}
	
	public String getCommissioningFE() {
		return commissioningFE;
	}
	
	public String getCommissioningBO() {
		return commissioningBO;
	}
	
	public String getTxProvisioning() {
		return txProvisioning;
	}
	
	public String getFieldWork() {
		return fieldWork;
	}
	
	public String getSiteUnlocked() {
		return siteUnlocked;
	}
	
	public String getPostCallTest() {
		return postCallTest;
	}
	
	public String getClosureCode() {
		return closureCode;
	}
	
	public String getLeaveSite() {
		return leaveSite;
	}
	
	public String getBookOffSite() {
		return bookOffSite;
	}
	
	public String getPerformanceMonitoring() {
		return performanceMonitoring;
	}
	
	public String getInitialHOP() {
		return initialHOP;
	}
	
	public String getDevoteamIssue() {
		return devoteamIssue;
	}
	
	public String getCustomerIssue() {
		return customerIssue;
	}
	
	public String getPercentage() {
		return percentage;
	}
	
	public String getScheduledDDMM() {
		return scheduledDDMM;
	}

}
