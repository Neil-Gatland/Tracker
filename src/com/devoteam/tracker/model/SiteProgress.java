package com.devoteam.tracker.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SiteProgress {
	private String site; //1
	private String nrId; //2
	private String status; //3
	private Date scheduledDate; //4
	private String checkedIn; //5
	private Timestamp checkedInDT; //6
	private String bookedOn; //7
	private Timestamp bookedOnDT; //8
	private String siteAccessed; //9
	private Timestamp siteAccessedDT; //10
	private String physicalChecks; //11
	private Timestamp physicalChecksDT; //12
	private String preCallTest; //13
	private Timestamp preCallTestDT; //14
	private String siteLocked; //15
	private Timestamp siteLockedDT; //16
	private String hwInstalls; //17
	private Timestamp hwInstallsDT; //18
	private String commissioningFE; //19
	private Timestamp commissioningFEDT; //20
	private String commissioningBO; //21
	private Timestamp commissioningBODT; //22
	private String txProvisioning; //23
	private Timestamp txProvisioningDT; //24
	private String fieldWork; //25
	private Timestamp fieldWorkDT; //26
	private String siteUnlocked; //27
	private Timestamp siteUnlockedDT; //28
	private String postCallTest; //29
	private Timestamp postCallTestDT; //30
	private String closureCode; //31
	private Timestamp closureCodeDT; //32
	private String leaveSite; //33
	private Timestamp leaveSiteDT; //34
	private String bookOffSite; //35
	private Timestamp bookOffSiteDT; //36
	private String performanceMonitoring; //37
	private Timestamp performanceMonitoringDT; //38
	private String initialHOP; //39
	private Timestamp initialHOPDT; //40
	private String issueOwner; //41
	private String crqClosureCode; //42
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat timestampFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
	
	public SiteProgress
		(	String site,
			String nrId,
			String status,
			Date scheduledDate,
			String checkedIn,
			Timestamp checkedInDT,
			String bookedOn,
			Timestamp bookedOnDT ,
			String siteAccessed,
			Timestamp siteAccessedDT,
			String physicalChecks, 
			Timestamp physicalChecksDT,
			String preCallTest, 
			Timestamp preCallTestDT,
			String siteLocked, 
			Timestamp siteLockedDT,
			String hwInstalls, 
			Timestamp hwInstallsDT,
			String commissioningFE, 
			Timestamp commissioningFEDT,
			String commissioningBO, 
			Timestamp commissioningBODT,
			String txProvisioning, 
			Timestamp txProvisioningDT,
			String fieldWork, 
			Timestamp fieldWorkDT,
			String siteUnlocked, 
			Timestamp siteUnlockedDT,
			String postCallTest, 
			Timestamp postCallTestDT,
			String closureCode, 
			Timestamp closureCodeDT,
			String leaveSite, 
			Timestamp leaveSiteDT,
			String bookOffSite, 
			Timestamp bookOffSiteDT,
			String performanceMonitoring, 
			Timestamp performanceMonitoringDT,
			String initialHOP, 
			Timestamp initialHOPDT,
			String issueOwner,
			String crqClosureCode) {
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.scheduledDate = scheduledDate;
		this.checkedIn = checkedIn;
		this.checkedInDT = checkedInDT;
		this.bookedOn = bookedOn;
		this.bookedOnDT = bookedOnDT;
		this.siteAccessed = siteAccessed;
		this.siteAccessedDT = siteAccessedDT;
		this.physicalChecks = physicalChecks;
		this.physicalChecksDT = physicalChecksDT;
		this.preCallTest = preCallTest;
		this.preCallTestDT = preCallTestDT;
		this.siteLocked = siteLocked;
		this.siteLockedDT = siteLockedDT;
		this.hwInstalls = hwInstalls;
		this.hwInstallsDT = hwInstallsDT;
		this.commissioningFE = commissioningFE;
		this.commissioningFEDT = commissioningFEDT;
		this.commissioningBO = commissioningBO;
		this.commissioningBODT = commissioningBODT;
		this.txProvisioning = txProvisioning;
		this.txProvisioningDT = txProvisioningDT;
		this.fieldWork = fieldWork;
		this.fieldWorkDT = fieldWorkDT;
		this.siteUnlocked = siteUnlocked;
		this.siteUnlockedDT = siteUnlockedDT;
		this.postCallTest = postCallTest;
		this.postCallTestDT = postCallTestDT;
		this.closureCode = closureCode;
		this.closureCodeDT = closureCodeDT;
		this.leaveSite = leaveSite;
		this.leaveSiteDT = leaveSiteDT;
		this.bookOffSite = bookOffSite;
		this.bookOffSiteDT = bookOffSiteDT;
		this.performanceMonitoring = performanceMonitoring;
		this.performanceMonitoringDT = performanceMonitoringDT;
		this.initialHOP = initialHOP;
		this.initialHOPDT = initialHOPDT;
		this.issueOwner = issueOwner;
		this.crqClosureCode = crqClosureCode;
	}
	
	public SiteProgress
		(	String site ) {
		this.site = site;
	}

	public String getSite() {
		return site;
	}
	
	public String getNRId() {
		return nrId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getScheduledDateString() {
		return scheduledDate==null?"":dateFormatter.format(scheduledDate);
	}
	
	public String getCheckedIn() {
		return checkedIn==null?"":checkedIn;
	}
	
	public String getCheckedInDTString() {
		return checkedInDT==null?"":timestampFormatter.format(checkedInDT);
	}
	
	public String getBookedOn() {
		return bookedOn==null?"":bookedOn;
	}
		
	public String getBookedOnDTString() {
		return bookedOnDT==null?"":timestampFormatter.format(bookedOnDT);
	}
	
	public String getSiteAccessed() {
		return siteAccessed==null?"":siteAccessed;
	}
	
	public String getSiteAccessedDTString() {
		return siteAccessedDT==null?"":timestampFormatter.format(siteAccessedDT);
	}
	
	public String getPhysicalChecks() {
		return physicalChecks==null?"":physicalChecks;
	}
	
	public String getPhysicalChecksDTString() {
		return physicalChecksDT==null?"":timestampFormatter.format(physicalChecksDT);
	}
	
	public String getPreCallTest() {
		return preCallTest==null?"":preCallTest;
	}
	
	public String getPreCallTestDTString() {
		return preCallTestDT==null?"":timestampFormatter.format(preCallTestDT);
	}
	
	public String getSiteLocked() {
		return siteLocked==null?"":siteLocked;
	}
	
	public String getSiteLockedDTString() {
		return siteLockedDT==null?"":timestampFormatter.format(siteLockedDT);
	}
	
	public String getHWInstalls() {
		return hwInstalls==null?"":hwInstalls;
	}
	
	public String getHWInstallsDTString() {
		return hwInstallsDT==null?"":timestampFormatter.format(hwInstallsDT);
	}
	
	public String getCommissioningFE() {
		return commissioningFE==null?"":commissioningFE;
	}
	
	public String getCommissioningFEDTString() {
		return commissioningFEDT==null?"":timestampFormatter.format(commissioningFEDT);
	}
	
	public String getCommissioningBO() {
		return commissioningBO==null?"":commissioningBO;
	}
	
	public String getCommissioningBODTString() {
		return commissioningBODT==null?"":timestampFormatter.format(commissioningBODT);
	}
	
	public String getTXProvisioning() {
		return txProvisioning==null?"":txProvisioning;
	}
	
	public String getTXProvisioningDTString() {
		return txProvisioningDT==null?"":timestampFormatter.format(txProvisioningDT);
	}
	
	public String getFieldWork() {
		return fieldWork==null?"":fieldWork;
	}
	
	public String getFieldWorkDTString() {
		return fieldWorkDT==null?"":timestampFormatter.format(fieldWorkDT);
	}
	
	public String getSiteUnlocked() {
		return siteUnlocked==null?"":siteUnlocked;
	}
	
	public String getSiteUnlockedDTString() {
		return siteUnlockedDT==null?"":timestampFormatter.format(siteUnlockedDT);
	}
	
	public String getPostCallTest() {
		return postCallTest==null?"":postCallTest;
	}
	
	public String getPostCallTestDTString() {
		return postCallTestDT==null?"":timestampFormatter.format(postCallTestDT);
	}
	
	public String getClosureCode() {
		return closureCode==null?"":closureCode;
	}
	
	public String getClosureCodeDTString() {
		return closureCodeDT==null?"":timestampFormatter.format(closureCodeDT);
	}
	
	public String getLeaveSite() {
		return leaveSite==null?"":leaveSite;
	}
	
	public String getLeaveSiteDTString() {
		return leaveSiteDT==null?"":timestampFormatter.format(leaveSiteDT);
	}

	public String getBookOffSite() {
		return bookOffSite==null?"":bookOffSite;
	}
	
	public String getBookOffSiteDTString() {
		return bookOffSiteDT==null?"":timestampFormatter.format(bookOffSiteDT);
	}
	
	public String getPerformanceMonitoring() {
		return performanceMonitoring==null?"":performanceMonitoring;
	}
	
	public String getPerformanceMonitoringDTString() {
		return performanceMonitoringDT==null?"":timestampFormatter.format(performanceMonitoringDT);
	}
	
	public String getInitialHOP() {
		return initialHOP==null?"":initialHOP;
	}
	
	public String getInitialHOPDTString() {
		return initialHOPDT==null?"":timestampFormatter.format(initialHOPDT);
	}
	
	public String getIssueOwner() {
		return issueOwner==null?"":issueOwner;
	}
	
	public String getCrqClosureCode() {
		return crqClosureCode==null?"":crqClosureCode;
	}

}
