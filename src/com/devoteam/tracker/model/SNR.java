package com.devoteam.tracker.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SNR {
	private long snrId;
	private long customerId;
	private String customerName;
	private String site;
	private String nrId;
	private String status;
	private String upgradeType;
	private String eastings;
	private String northings;
	private String postcode;
	private String region;
	private Date ef345ClaimDT;
	private Date ef360ClaimDT;
	private Date ef390ClaimDT;
	private Date ef400ClaimDT;
	private Date ef410ClaimDT;
	private String p1SiteInd;
	private String obassInd;
	private String ramsInd;
	private String escortInd;
	private String healthSafetyInd;
	private Date scheduledDate;
	private double outagePeriod;
	private String accessConfirmedInd;
	private double accessCost;
	private double consumableCost;
	private String oohWeekendInd;
	private String crInReference;
	private String crInInd;
	private Timestamp crInStartDT;
	private Timestamp crInEndDT;
	//private Timestamp crInOutageStartDT;
	//private Timestamp crInOutageEndDT;
	private String crInUsed;
	private String crqStatus;
	private String implementationStatus;
	private Timestamp implementationStartDT;
	private Timestamp implementationEndDT;
	private String abortType;
	private String twoGInd;
	private String threeGInd;
	private String fourGInd;
	private String o2Ind;
	private String healthChecksInd;
	private String activeAlarmsInd;
	private String nsaNetActsInd;
	private String hopDeliveredInd;
	private String hopFilename;
	private String hopOnSharepoint;
	private String efUpdated; 
	private String sfrCompleted;
	private String sfrOnSharepoint;
	private Timestamp lastUpdatedDate;
	private String lastUpdatedBy;
	private Timestamp historyDate;
	private Timestamp implOutageStartDT;
	private Timestamp implOutageEndDT;
	private String completingBOEngineer;
	private String accessStatus;
	private String permitType;
	private String tefOutageRequired;
	private String vfArrangeAccess;
	private String twoManSite;
	private String siteAccessInfomation;
	private String tefOutageNos;
	private String completingFEsList;
	private String incTicketNo;
	private String hardwareVendor;
	private String preTestCallsDone;
	private String postTestCallsDone;
	private String crqClosureCode;
	private String siteIssues;
	private String checkedIn;
	private Timestamp checkedInDT;
	private String bookedOn;
	private Timestamp bookedOnDT;
	private String siteAccessed;
	private Timestamp siteAccessedDT;
	private String physicalChecks;
	private Timestamp physicalChecksDT;
	private String preCallTest;
	private Timestamp preCallTestDT;
	private String siteLocked;
	private Timestamp siteLockedDT;
	private String hwInstalls;
	private Timestamp hwInstallsDT;
	private String commissioningFE;
	private Timestamp commissioningFEDT;
	private String commissioningBO;
	private Timestamp commissioningBODT;
	private String txProvisioning;
	private Timestamp txProvisioningDT;
	private String fieldWork;
	private Timestamp fieldWorkDT;
	private String siteUnlocked;
	private Timestamp siteUnlockedDT;
	private String postCallTest;
	private Timestamp postCallTestDT;
	private String closureCode;
	private Timestamp closureCodeDT;
	private String leaveSite;
	private Timestamp leaveSiteDT;
	private String bookOffSite;
	private Timestamp bookOffSiteDT;
	private String performanceMonitoring;
	private Timestamp performanceMonitoringDT;
	private String initialHOP;
	private Timestamp initialHOPDT;
	private String issueOwner;
	private String riskIndicator;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat timestampFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.UK);
	private DecimalFormat numberFormatter = new DecimalFormat("#.##");
	private String[] titleArray = {
			"Customer:",
			"Site Issues:",
			//repeatable bit follows
			"Site:", "EF345 Claim Date:", "P1 Site:", 
			"NR Id:", "EF360 Claim Date:", "OBASS:",  
			"Status:", "EF390 Claim Date:", "RAMS:",  
			"Upgrade Type:", "EF400 Claim Date:", "Escort:",
			"Postcode", "EF410 Claim Date:", "Health &amp; Safety:", 
			"Completing BO Engineer:", "Scheduled Date:", "Access Confirmed:", 
			"Implementation Status:", "Implementation Start Date:", "OOH Weekend:", 
			"Abort Type:", "Implementation End Date:", "Health Checks:", 
			"CRQ/INC Reference:", "Impl. Outage Start DT:", "Active Alarms:", 
			"CRQ/INC Ind:", "Impl. Outage End DT:", "Access Status:", 
			"CRQ/INC Used:", "CRQ/INC Start Date:", "VF Arrange Access:", 
			"CRQ Status:", "CRQ/INC End Date:", "Permit Type:",  
			"TEF Outage No(s):", "TEF Outage Required:", "Two Man Site:",
			"Completing FEs:", "INC Ticket No.:", "Access Cost:",
			"Hardware Vendor:", "EF Updated:", "Consumable Cost:", 
			"Outage Period:","SFR Completed:","SFR On Sharepoint:",
			"HOP Filename:", "HOP Delivered:", "HOP On Sharepoint:", 
			"Pre-Test Calls Done:", "NSA NetActs:", "CRQ Closure Code:", 
			"Post-Test Calls Done:", "", "", 
			"Site Progress:", "Issue Owner:", "Risk Indicator:",
			"Last Updated By:", "Last Updated Date:", "History Date:"};
	//end of repeatable bit 

	public SNR (long snrId,	String site, String nrId) {
		this.snrId = snrId;
		this.site = site;
		this.nrId = nrId;
	}

	public SNR (long snrId,	String site, String nrId,	String status) {
		this.snrId = snrId;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
	}
	
	public SNR (long snrId,	String site, String nrId, String upgradeType,
			String eastings, String northings, String postcode, Date ef345ClaimDT,
			Date ef360ClaimDT, Date ef400ClaimDT, Date ef410ClaimDT) {
		this.snrId = snrId;
		this.site = site;
		this.nrId = nrId;
		this.upgradeType = upgradeType;
		this.eastings = eastings;
		this.northings = northings;
		this.postcode = postcode;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
	}

	public SNR (long snrId,	long customerId, String customerName, String site,
		String nrId, String status, String upgradeType,	String eastings,
		String northings, String postcode, String region, Date ef345ClaimDT,
		Date ef360ClaimDT, Date ef400ClaimDT, Date ef410ClaimDT, 
		String p1SiteInd, String obassInd, String ramsInd, String escortInd,
		String healthSafetyInd, Date scheduledDate, double outagePeriod,
		String accessConfirmedInd, double accessCost, double consumableCost,
		String oohWeekendInd, String crInReference, String crInInd, 
		Timestamp crInStartDT, Timestamp crInEndDT, /*Timestamp crInOutageStartDT, 
		Timestamp crInOutageEndDT,*/ String crInUsed, String crqStatus,
		String implementationStatus, Timestamp implementationStartDT,
		Timestamp implementationEndDT, String abortType, String twoGInd,
		String threeGInd, String fourGInd, String o2Ind, String healthChecksInd,
		String activeAlarmsInd, String nsaNetActsInd, String hopDeliveredInd,
		String hopFilename, String hopOnSharepoint, String efUpdated, 
		String sfrCompleted, String sfrOnSharepoint, Timestamp lastUpdatedDate, 
		String lastUpdatedBy, Timestamp historyDate) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.upgradeType = upgradeType;
		this.eastings = eastings;
		this.northings = northings;
		this.postcode = postcode;
		this.region = region;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
		this.p1SiteInd = p1SiteInd;
		this.obassInd = obassInd;
		this.ramsInd = ramsInd;
		this.escortInd = escortInd;
		this.healthSafetyInd = healthSafetyInd;
		this.scheduledDate = scheduledDate;
		this.outagePeriod = outagePeriod;
		this.accessConfirmedInd = accessConfirmedInd;
		this.accessCost = accessCost;
		this.consumableCost = consumableCost;
		this.oohWeekendInd = oohWeekendInd;
		this.crInReference = crInReference;
		this.crInInd = crInInd;
		this.crInStartDT = crInStartDT;
		this.crInEndDT = crInEndDT;
		this.crInUsed = crInUsed;
		this.crqStatus = crqStatus;
		this.implementationStatus = implementationStatus;
		this.implementationStartDT = implementationStartDT;
		this.implementationEndDT = implementationEndDT;
		this.abortType = abortType;
		this.twoGInd = twoGInd;
		this.threeGInd = threeGInd;
		this.fourGInd = fourGInd;
		this.o2Ind = o2Ind;
		this.healthChecksInd = healthChecksInd;
		this.activeAlarmsInd = activeAlarmsInd;
		this.nsaNetActsInd = nsaNetActsInd;
		this.hopDeliveredInd = hopDeliveredInd;
		this.hopFilename = hopFilename;
		this.hopOnSharepoint = hopOnSharepoint;
		this.efUpdated = efUpdated;
		this.sfrCompleted = sfrCompleted;
		this.sfrOnSharepoint = sfrOnSharepoint;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.historyDate = historyDate; 
		this.crInInd = crInInd;
	}

	public SNR (long snrId,	long customerId, String customerName, String site,
			String nrId, String status, String upgradeType,	
			String postcode, Date ef345ClaimDT,
			Date ef360ClaimDT, Date ef400ClaimDT, Date ef410ClaimDT, 
			String p1SiteInd, String obassInd, String ramsInd, String escortInd,
			String healthSafetyInd, Date scheduledDate, double outagePeriod,
			String accessConfirmedInd, double accessCost, double consumableCost,
			String oohWeekendInd, String crInReference, String crInInd, 
			Timestamp crInStartDT, Timestamp crInEndDT, /*Timestamp crInOutageStartDT, 
			Timestamp crInOutageEndDT,*/ String crInUsed, String crqStatus,
			String implementationStatus, Timestamp implementationStartDT,
			Timestamp implementationEndDT, String abortType, String twoGInd,
			String threeGInd, String fourGInd, String o2Ind, String healthChecksInd,
			String activeAlarmsInd, String nsaNetActsInd, String hopDeliveredInd,
			String hopFilename, String hopOnSharepoint, String efUpdated, 
			String sfrCompleted, String sfrOnSharepoint, Timestamp lastUpdatedDate, 
			String lastUpdatedBy, Timestamp implOutageStartDT, Timestamp implOutageEndDT,
			String completingBOEngineer, Date ef390ClaimDT, String accessStatus,
			String permitType, String tefOutageRequired, String vfArrangeAccess,
			String twoManSite, String siteAccessInfomation, String tefOutageNos, 
			Timestamp historyDate) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.upgradeType = upgradeType;
		this.postcode = postcode;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef390ClaimDT = ef390ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
		this.p1SiteInd = p1SiteInd;
		this.obassInd = obassInd;
		this.ramsInd = ramsInd;
		this.escortInd = escortInd;
		this.healthSafetyInd = healthSafetyInd;
		this.scheduledDate = scheduledDate;
		this.outagePeriod = outagePeriod;
		this.accessConfirmedInd = accessConfirmedInd;
		this.accessCost = accessCost;
		this.consumableCost = consumableCost;
		this.oohWeekendInd = oohWeekendInd;
		this.crInReference = crInReference;
		this.crInInd = crInInd;
		this.crInStartDT = crInStartDT;
		this.crInEndDT = crInEndDT;
		this.crInUsed = crInUsed;
		this.crqStatus = crqStatus;
		this.implementationStatus = implementationStatus;
		this.implementationStartDT = implementationStartDT;
		this.implementationEndDT = implementationEndDT;
		this.abortType = abortType;
		this.twoGInd = twoGInd;
		this.threeGInd = threeGInd;
		this.fourGInd = fourGInd;
		this.o2Ind = o2Ind;
		this.healthChecksInd = healthChecksInd;
		this.activeAlarmsInd = activeAlarmsInd;
		this.nsaNetActsInd = nsaNetActsInd;
		this.hopDeliveredInd = hopDeliveredInd;
		this.hopFilename = hopFilename;
		this.hopOnSharepoint = hopOnSharepoint;
		this.efUpdated = efUpdated;
		this.sfrCompleted = sfrCompleted;
		this.sfrOnSharepoint = sfrOnSharepoint;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.historyDate = historyDate; 
		this.crInInd = crInInd;
		this.implOutageStartDT = implOutageStartDT;
		this.implOutageEndDT = implOutageEndDT;
		this.completingBOEngineer = completingBOEngineer;
		this.accessStatus = accessStatus;
		this.permitType = permitType;
		this.tefOutageRequired = tefOutageRequired;
		this.tefOutageNos = tefOutageNos;
		this.vfArrangeAccess = vfArrangeAccess;
		this.twoManSite = twoManSite;
		this.siteAccessInfomation = siteAccessInfomation;
	}

	public SNR (long snrId,	long customerId, String customerName, String site,
			String nrId, String status, String upgradeType,	
			String postcode, Date ef345ClaimDT,
			Date ef360ClaimDT, Date ef400ClaimDT, Date ef410ClaimDT, 
			String p1SiteInd, String obassInd, String ramsInd, String escortInd,
			String healthSafetyInd, Date scheduledDate, double outagePeriod,
			String accessConfirmedInd, double accessCost, double consumableCost,
			String oohWeekendInd, String crInReference, String crInInd, 
			Timestamp crInStartDT, Timestamp crInEndDT, /*Timestamp crInOutageStartDT, 
			Timestamp crInOutageEndDT,*/ String crInUsed, String crqStatus,
			String implementationStatus, Timestamp implementationStartDT,
			Timestamp implementationEndDT, String abortType, String twoGInd,
			String threeGInd, String fourGInd, String o2Ind, String healthChecksInd,
			String activeAlarmsInd, String nsaNetActsInd, String hopDeliveredInd,
			String hopFilename, String hopOnSharepoint, String efUpdated, 
			String sfrCompleted, String sfrOnSharepoint, Timestamp lastUpdatedDate, 
			String lastUpdatedBy, Timestamp implOutageStartDT, Timestamp implOutageEndDT,
			String completingBOEngineer, Date ef390ClaimDT, String accessStatus,
			String permitType, String tefOutageRequired, String vfArrangeAccess,
			String twoManSite, String siteAccessInfomation, String tefOutageNos, 
			Timestamp historyDate, String completingFEsList, String incTicketNo, 
			String hardwareVendor, String preTestCallsDone, String postTestCallsDone,
			String crqClosureCode) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.upgradeType = upgradeType;
		this.postcode = postcode;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef390ClaimDT = ef390ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
		this.p1SiteInd = p1SiteInd;
		this.obassInd = obassInd;
		this.ramsInd = ramsInd;
		this.escortInd = escortInd;
		this.healthSafetyInd = healthSafetyInd;
		this.scheduledDate = scheduledDate;
		this.outagePeriod = outagePeriod;
		this.accessConfirmedInd = accessConfirmedInd;
		this.accessCost = accessCost;
		this.consumableCost = consumableCost;
		this.oohWeekendInd = oohWeekendInd;
		this.crInReference = crInReference;
		this.crInInd = crInInd;
		this.crInStartDT = crInStartDT;
		this.crInEndDT = crInEndDT;
		this.crInUsed = crInUsed;
		this.crqStatus = crqStatus;
		this.implementationStatus = implementationStatus;
		this.implementationStartDT = implementationStartDT;
		this.implementationEndDT = implementationEndDT;
		this.abortType = abortType;
		this.twoGInd = twoGInd;
		this.threeGInd = threeGInd;
		this.fourGInd = fourGInd;
		this.o2Ind = o2Ind;
		this.healthChecksInd = healthChecksInd;
		this.activeAlarmsInd = activeAlarmsInd;
		this.nsaNetActsInd = nsaNetActsInd;
		this.hopDeliveredInd = hopDeliveredInd;
		this.hopFilename = hopFilename;
		this.hopOnSharepoint = hopOnSharepoint;
		this.efUpdated = efUpdated;
		this.sfrCompleted = sfrCompleted;
		this.sfrOnSharepoint = sfrOnSharepoint;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.historyDate = historyDate; 
		this.crInInd = crInInd;
		this.implOutageStartDT = implOutageStartDT;
		this.implOutageEndDT = implOutageEndDT;
		this.completingBOEngineer = completingBOEngineer;
		this.accessStatus = accessStatus;
		this.permitType = permitType;
		this.tefOutageRequired = tefOutageRequired;
		this.tefOutageNos = tefOutageNos;
		this.vfArrangeAccess = vfArrangeAccess;
		this.twoManSite = twoManSite;
		this.siteAccessInfomation = siteAccessInfomation;
		this.completingFEsList = completingFEsList;
		this.incTicketNo = incTicketNo;
		this.hardwareVendor = hardwareVendor;
		this.preTestCallsDone = preTestCallsDone;
		this.postTestCallsDone = postTestCallsDone;
		this.crqClosureCode = crqClosureCode;
	}

	public SNR (long snrId,	String p1SiteInd, String obassInd, String ramsInd, 
		String escortInd, String healthSafetyInd, double outagePeriod,
		String accessConfirmedInd, double accessCost, String oohWeekendInd) {
		this.snrId = snrId;
		this.p1SiteInd = p1SiteInd;
		this.obassInd = obassInd;
		this.ramsInd = ramsInd;
		this.escortInd = escortInd;
		this.healthSafetyInd = healthSafetyInd;
		this.outagePeriod = outagePeriod;
		this.accessConfirmedInd = accessConfirmedInd;
		this.accessCost = accessCost;
		this.oohWeekendInd = oohWeekendInd;
	}
	
	public SNR (long snrId,	long customerId, String customerName, String site,
			String nrId, String status, String upgradeType,	
			String postcode, Date ef345ClaimDT,
			Date ef360ClaimDT, Date ef400ClaimDT, Date ef410ClaimDT, 
			String p1SiteInd, String obassInd, String ramsInd, String escortInd,
			String healthSafetyInd, Date scheduledDate, double outagePeriod,
			String accessConfirmedInd, double accessCost, double consumableCost,
			String oohWeekendInd, String crInReference, String crInInd, 
			Timestamp crInStartDT, Timestamp crInEndDT, String crInUsed, String crqStatus,
			String implementationStatus, Timestamp implementationStartDT,
			Timestamp implementationEndDT, String abortType, String twoGInd,
			String threeGInd, String fourGInd, String o2Ind, String healthChecksInd,
			String activeAlarmsInd, String nsaNetActsInd, String hopDeliveredInd,
			String hopFilename, String hopOnSharepoint, String efUpdated, 
			String sfrCompleted, String sfrOnSharepoint, Timestamp lastUpdatedDate, 
			String lastUpdatedBy, Timestamp implOutageStartDT, Timestamp implOutageEndDT,
			String completingBOEngineer, Date ef390ClaimDT, String accessStatus,
			String permitType, String tefOutageRequired, String vfArrangeAccess,
			String twoManSite, String siteAccessInfomation, String tefOutageNos, 
			Timestamp historyDate, String completingFEsList, String incTicketNo, 
			String hardwareVendor, String preTestCallsDone, String postTestCallsDone,
			String crqClosureCode, String siteIssues, String checkedIn,
			Timestamp checkedInDT, String bookedOn, Timestamp bookedOnDT,
			String siteAccessed, Timestamp siteAccessedDT, String physicalChecks,
			Timestamp physicalChecksDT, String preCallTest, Timestamp preCallTestDT,
			String siteLocked, Timestamp siteLockedDT, String hwInstalls,
			Timestamp hwInstallsDT, String commissioningFE, Timestamp commissioningFEDT,
			String commissioningBO, Timestamp commissioningBODT, String txProvisioning,
			Timestamp txProvisioningDT, String fieldWork, Timestamp fieldWorkDT,
			String siteUnlocked, Timestamp siteUnlockedDT, String postCallTest,
			Timestamp postCallTestDT, String closureCode, Timestamp closureCodeDT,
			String leaveSite, Timestamp leaveSiteDT, String bookOffSite,
			Timestamp bookOffSiteDT, String performanceMonitoring, Timestamp performanceMonitoringDT,
			String initialHOP, Timestamp initialHOPDT, String issueOwner, String riskIndicator ) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.upgradeType = upgradeType;
		this.postcode = postcode;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef390ClaimDT = ef390ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
		this.p1SiteInd = p1SiteInd;
		this.obassInd = obassInd;
		this.ramsInd = ramsInd;
		this.escortInd = escortInd;
		this.healthSafetyInd = healthSafetyInd;
		this.scheduledDate = scheduledDate;
		this.outagePeriod = outagePeriod;
		this.accessConfirmedInd = accessConfirmedInd;
		this.accessCost = accessCost;
		this.consumableCost = consumableCost;
		this.oohWeekendInd = oohWeekendInd;
		this.crInReference = crInReference;
		this.crInInd = crInInd;
		this.crInStartDT = crInStartDT;
		this.crInEndDT = crInEndDT;
		this.crInUsed = crInUsed;
		this.crqStatus = crqStatus;
		this.implementationStatus = implementationStatus;
		this.implementationStartDT = implementationStartDT;
		this.implementationEndDT = implementationEndDT;
		this.abortType = abortType;
		this.twoGInd = twoGInd;
		this.threeGInd = threeGInd;
		this.fourGInd = fourGInd;
		this.o2Ind = o2Ind;
		this.healthChecksInd = healthChecksInd;
		this.activeAlarmsInd = activeAlarmsInd;
		this.nsaNetActsInd = nsaNetActsInd;
		this.hopDeliveredInd = hopDeliveredInd;
		this.hopFilename = hopFilename;
		this.hopOnSharepoint = hopOnSharepoint;
		this.efUpdated = efUpdated;
		this.sfrCompleted = sfrCompleted;
		this.sfrOnSharepoint = sfrOnSharepoint;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.historyDate = historyDate; 
		this.crInInd = crInInd;
		this.implOutageStartDT = implOutageStartDT;
		this.implOutageEndDT = implOutageEndDT;
		this.completingBOEngineer = completingBOEngineer;
		this.accessStatus = accessStatus;
		this.permitType = permitType;
		this.tefOutageRequired = tefOutageRequired;
		this.tefOutageNos = tefOutageNos;
		this.vfArrangeAccess = vfArrangeAccess;
		this.twoManSite = twoManSite;
		this.siteAccessInfomation = siteAccessInfomation;
		this.completingFEsList = completingFEsList;
		this.incTicketNo = incTicketNo;
		this.hardwareVendor = hardwareVendor;
		this.preTestCallsDone = preTestCallsDone;
		this.postTestCallsDone = postTestCallsDone;
		this.crqClosureCode = crqClosureCode;
		this.siteIssues = siteIssues;
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
		this.riskIndicator = riskIndicator;
	}
	public long getSNRId() {
		return snrId;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public String getCustomerName() {
		return customerName;
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
	
	public String getUpgradeType() {
		return upgradeType==null?"":upgradeType;
	}
	
	public String getEastings() {
		return eastings;
	}
	
	public String getNorthings() {
		return northings;
	}
	
	public String getPostcode() {
		return postcode;
	}
	
	public String getRegion() {
		return region;
	}
	
	public Date getEF345ClaimDT() {
		return ef345ClaimDT;
	}
	
	public String getEF345ClaimDTString() {
		return ef345ClaimDT==null?"":dateFormatter.format(ef345ClaimDT);
	}
	
	public Date getEF360ClaimDT() {
		return ef360ClaimDT;
	}
	
	public String getEF360ClaimDTString() {
		return ef360ClaimDT==null?"":dateFormatter.format(ef360ClaimDT);
	}
	
	public Date getEF390ClaimDT() {
		return ef390ClaimDT;
	}
	
	public String getEF390ClaimDTString() {
		return ef390ClaimDT==null?"":dateFormatter.format(ef390ClaimDT);
	}
	
	public Date getEF400ClaimDT() {
		return ef400ClaimDT;
	}
	
	public String getEF400ClaimDTString() {
		return ef400ClaimDT==null?"":dateFormatter.format(ef400ClaimDT);
	}
	
	public Date getEF410ClaimDT() {
		return ef410ClaimDT;
	}
	
	public String getEF410ClaimDTString() {
		return ef410ClaimDT==null?"":dateFormatter.format(ef410ClaimDT);
	}
	
	public String getP1SiteInd() {
		return p1SiteInd==null?"":p1SiteInd;
	}
	
	public String getOBASSInd() {
		return obassInd==null?"":obassInd;
	}
	
	public String getRAMSInd() {
		return ramsInd==null?"":ramsInd;
	}
	
	public String getEscortInd() {
		return escortInd==null?"":escortInd;
	}
	
	public String getHealthSafetyInd() {
		return healthSafetyInd==null?"":healthSafetyInd;
	}
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getScheduledDateString() {
		return scheduledDate==null?"":dateFormatter.format(scheduledDate);
	}
	
	public double getOutagePeriod() {
		return outagePeriod;
	}
	
	public String getOutagePeriodString() {
		return numberFormatter.format(outagePeriod);
	}
	
	public String getAccessConfirmedInd() {
		return accessConfirmedInd==null?"":accessConfirmedInd;
	}
	
	public double getAccessCost() {
		return accessCost;
	}
	
	public String getAccessCostString() {
		return currencyFormatter.format(accessCost);
	}
	
	public double getConsumableCost() {
		return consumableCost;
	}
	
	public String getConsumableCostString() {
		return currencyFormatter.format(consumableCost);
	}
	
	public String getOOHWeekendInd() {
		return oohWeekendInd==null?"":oohWeekendInd;
	}
	
	public String getCRINReference() {
		return crInReference==null?"":crInReference;
	}
	
	public String getCRINInd() {
		return crInInd==null?"":crInInd;
	}
	
	public Timestamp getCRINStartDT() {
		return crInStartDT;
	}
	
	public String getCRINStartDTString() {
		return crInStartDT==null?"":timestampFormatter.format(crInStartDT);
	}
	
	public Timestamp getCRINEndDT() {
		return crInEndDT;
	}
	
	public String getCRINEndDTString() {
		return crInEndDT==null?"":timestampFormatter.format(crInEndDT);
	}
	
	public String getCRINUsed() {
		return crInUsed==null?"":crInUsed;
	}
	
	public String getCRQStatus() {
		return crqStatus==null?"":crqStatus;
	}
	
	public String getImplementationStatus() {
		return implementationStatus==null?"":implementationStatus;
	}
	
	public Timestamp getImplementationStartDT() {
		return implementationStartDT;
	}
	
	public String getImplementationStartDTString() {
		return implementationStartDT==null?"":timestampFormatter.format(implementationStartDT);
	}
	
	public Timestamp getImplementationEndDT() {
		return implementationEndDT;
	}
	
	public String getImplementationEndDTString() {
		return implementationEndDT==null?"":timestampFormatter.format(implementationEndDT);
	}
	
	public String getAbortType() {
		return abortType==null?"":abortType;
	}
	
	public String get2GInd() {
		return twoGInd==null?"":twoGInd;
	}
	
	public String get3GInd() {
		return threeGInd==null?"":threeGInd;
	}
	
	public String get4GInd() {
		return fourGInd==null?"":fourGInd;
	}
	
	public String getO2Ind() {
		return o2Ind==null?"":o2Ind;
	}
	
	public String getHealthChecksInd() {
		return healthChecksInd==null?"":healthChecksInd;
	}
	
	public String getActiveAlarmsInd() {
		return activeAlarmsInd==null?"":activeAlarmsInd;
	}
	
	public String getNSANetActsInd() {
		return nsaNetActsInd==null?"":nsaNetActsInd;
	}
	
	public String getHOPDeliveredInd() {
		return hopDeliveredInd==null?"":hopDeliveredInd;
	}
	
	public String getHOPFilename() {
		return hopFilename==null?"":hopFilename;
	}
	
	public String getHOPOnSharepoint() {
		return hopOnSharepoint==null?"":hopOnSharepoint;
	}
	
	public String getEFUpdated() {
		return efUpdated==null?"":efUpdated;
	}
	
	public String getSFRCompleted() {
		return sfrCompleted==null?"":sfrCompleted;
	}
	
	public String getSFROnSharepoint() {
		return sfrOnSharepoint==null?"":sfrOnSharepoint;
	}
	
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public String getLastUpdatedDateString() {
		return timestampFormatter.format(lastUpdatedDate);
	}
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public Timestamp getHistoryDate() {
		return historyDate;
	}

	public String getHistoryDateString() {
		return historyDate==null?"":timestampFormatter.format(historyDate);
	}
	
	public Timestamp getImplOutageStartDT() {
		return implOutageStartDT;
	}

	public String getImplOutageStartDTString() {
		return implOutageStartDT==null?"":timestampFormatter.format(implOutageStartDT);
	}
	
	public Timestamp getImplOutageEndDT() {
		return implOutageEndDT;
	}

	public String getImplOutageEndDTString() {
		return implOutageEndDT==null?"":timestampFormatter.format(implOutageEndDT);
	}
	
	public String getCompletingBOEngineer() {
		return completingBOEngineer==null?"":completingBOEngineer;
	}
	
	public String getAccessStatus() {
		return accessStatus==null?"":accessStatus;
	}
	
	public String getPermitType() {
		return permitType==null?"":permitType;
	}
	
	public String getTEFOutageRequired() {
		return tefOutageRequired==null?"":tefOutageRequired;
	}
	
	public String getTEFOutageNos() {
		return tefOutageNos==null?"":tefOutageNos;
	}
	
	public String getVFArrangeAccess() {
		return vfArrangeAccess==null?"":vfArrangeAccess;
	}
	
	public String getTwoManSite() {
		return twoManSite==null?"":twoManSite;
	}
	
	public String getSiteAccessInfomation() {
		return siteAccessInfomation==null?"":siteAccessInfomation;
	}
	
	public String getCompletingFEsList() {
		return completingFEsList==null?"":completingFEsList;
	}
	
	public String getincTicketNo() {
		return incTicketNo==null?"":incTicketNo;
	}
	
	public String getHardwareVendor() {
		return hardwareVendor==null?"":hardwareVendor;
	}
	
	public String getPreTestCallsDone() {
		return preTestCallsDone==null?"":preTestCallsDone;
	}
	
	public String getPostTestCallsDone() {
		return postTestCallsDone==null?"":postTestCallsDone;
	}
	
	public String getCrqClosureCode() {
		return crqClosureCode==null?"":crqClosureCode;
	}
	
	public String getSiteProgress() {
		String sp = "<table><tr>";
		sp = sp + "<td width=\"20px\" title=\"Checked In"+fmtDT(checkedInDT)+"\" "
				+ "class=\"" + boxClass(checkedIn) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Site Booked On"+fmtDT(checkedInDT)+"\" "
				+ "class=\"" + boxClass(bookedOn) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Site Accessed"+fmtDT(checkedInDT)+"\" "
				+ "class=\"" + boxClass(siteAccessed) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Physical Checks"+fmtDT(checkedInDT)+"\" "
				+ "class=\"" + boxClass(physicalChecks) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Pre Call Test"+fmtDT(checkedInDT)+"\" "
				+ "class=\"" + boxClass(preCallTest) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Site Locked"+fmtDT(siteLockedDT)+"\" "
				+ "class=\"" + boxClass(siteLocked) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"HW Installed"+fmtDT(hwInstallsDT)+"\" "
				+ "class=\"" + boxClass(hwInstalls) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Field Commission (FE)"+fmtDT(commissioningFEDT)+"\" "
				+ "class=\"" + boxClass(commissioningFE) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Field Commission (BO)"+fmtDT(commissioningBODT)+"\" "
				+ "class=\"" + boxClass(commissioningBO) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Tx Provisioning"+fmtDT(txProvisioningDT)+"\" "
				+ "class=\"" + boxClass(txProvisioning) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Field Work"+fmtDT(fieldWorkDT)+"\" "
				+ "class=\"" + boxClass(fieldWork) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Site Unlocked"+fmtDT(siteUnlockedDT)+"\" "
				+ "class=\"" + boxClass(siteUnlocked) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Post Call Test"+fmtDT(postCallTestDT)+"\" "
				+ "class=\"" + boxClass(postCallTest) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Closure Code"+fmtDT(closureCodeDT)+"\" "
				+ "class=\"" + boxClass(closureCode) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Left Site"+fmtDT(leaveSiteDT)+"\" "
				+ "class=\"" + boxClass(leaveSite) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Site Booked off"+fmtDT(bookOffSiteDT)+"\" "
				+ "class=\"" + boxClass(bookOffSite) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Performance"+fmtDT(performanceMonitoringDT)+"\" "
				+ "class=\"" + boxClass(performanceMonitoring) + "\">&nbsp;"+"</td>";
		sp = sp + "<td width=\"20px\" title=\"Hand Off Pack"+fmtDT(initialHOPDT)+"\" "
				+ "class=\"" + boxClass(postCallTest) + "\">&nbsp;"+"</td>";
		sp = sp + "<tr></table>";		
		return sp;
	}
	
	private String boxClass(String progressStatus) {
		String bc = "";
		if (progressStatus==null)
			bc = "ldWhite";
		else if (progressStatus.equals("Not Applicable"))
			bc = "ldGray";
		else if (progressStatus.equals("In Progress"))
			bc = "ldAmber";
		else if (progressStatus.equals("Completed"))
			bc = "ldGreen";
		else if (progressStatus.equals("Issue"))
			bc = "ldRed";
		else
			bc = "ldWhite";
		return bc;
	}
	
	private String fmtDT(Timestamp progressStatusDT) {
		String dt = "";
		if (!(progressStatusDT==null))
			dt = " ("+timestampFormatter.format(progressStatusDT)+")";
		return dt;
	}
	
	public String getIssueOwner() {
		return issueOwner==null?"":issueOwner;
	}
	
	public String getRiskIndicator() {
		return riskIndicator==null?"":riskIndicator;
	}
	
	public String[] getTitleArray() {
		return titleArray;
	}
	
	public String getSiteIssues() {
		return siteIssues==null?"":siteIssues;
	}
	
	public String[] getValueArray() {
		String[] valueArray = {
				this.getCustomerName(),
				this.getSiteIssues(),
			//repeatable bit follows
			this.getSite(), this.getEF345ClaimDTString(), this.getP1SiteInd(), 
			this.getNRId(), this.getEF360ClaimDTString(), this.getOBASSInd(),  
			this.getStatus(), this.getEF390ClaimDTString(), this.getRAMSInd(),  
			this.getUpgradeType(), this.getEF400ClaimDTString(), this.getEscortInd(),  
			this.getPostcode(), this.getEF410ClaimDTString(), this.getHealthSafetyInd(), 
			this.getCompletingBOEngineer(), this.getScheduledDateString(), this.getAccessConfirmedInd() , 
			this.getImplementationStatus(), this.getImplementationStartDTString(), this.getOOHWeekendInd(), 
			this.getAbortType(), this.getImplementationEndDTString(), this.getHealthChecksInd(), 
			this.getCRINReference(), this.getImplOutageStartDTString(), this.getActiveAlarmsInd(), 
			this.getCRINInd(), this.getImplOutageEndDTString(), this.getAccessStatus(), 
			this.getCRINUsed(), this.getCRINStartDTString(), this.getVFArrangeAccess(), 
			this.getCRQStatus(), this.getCRINEndDTString(), this.getPermitType(),  
			this.getTEFOutageNos(), this.getTEFOutageRequired(), this.getTwoManSite(),
			this.getCompletingFEsList(), this.getincTicketNo(), this.getAccessCostString(), 
			this.getHardwareVendor(), this.getEFUpdated(), this.getConsumableCostString(),
			this.getOutagePeriodString(),this.getSFRCompleted(),this.getSFROnSharepoint(),
			this.getHOPFilename(), this.getHOPDeliveredInd(), this.getHOPOnSharepoint(), 
			this.getPreTestCallsDone(), this.getNSANetActsInd(), this.getCrqClosureCode(), 
			this.getPostTestCallsDone(), "", "", 
			this.getSiteProgress(), this.getIssueOwner(), this.getRiskIndicator(),
			this.getLastUpdatedBy(), this.getLastUpdatedDateString(), this.getHistoryDateString()}; 
			//end of repeatable bit 
	
		return valueArray;
	}
	
	
}
