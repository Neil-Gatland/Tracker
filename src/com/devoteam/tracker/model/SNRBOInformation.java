package com.devoteam.tracker.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SNRBOInformation {
	private long snrId; //1
	private String site; //2
	private String nrId; //3
	private String boEngineerList; //4
	private String feList; //5
	private String status; //6
	private Date scheduledDate; //7
	private String nextPreCheck; //8
	private String firstAttempt; //9
	private String hopStatus; //10
	private String cramerCompleted; //11
	private String scriptsReceived; //12
	private Date ef345ClaimDT; //13
	private String alarms; //14
	private String healthCheck; //15
	private String crInReference; //16
	private Timestamp crInStartDT; //17
	private Timestamp crInEndDT; //18
	private String tefOutageNos; //19
	private String jobType; //20
	private String twoGInd; //21
	private String threeGInd; //22
	private String fourGInd; //23
	private String tef2GInd; //24
	private String tef3GInd; //25
	private String tef4GInd; //26
	private String paknetPaging; //27
	private String secGWChange; //28
	private String power; //29
	private String survey; //30
	private String other; //31
	private Timestamp implementationStartDT; //32
	private Timestamp implementationEndDT; //33
	private Timestamp implOutageStartDT; //34
	private Timestamp implOutageEndDT; //35
	private String activeAlarmsInd; //36
	private String healthChecksInd; //37
	private String incTicketNo; //38
	private String hopDeliveredInd; //39
	private String hopFilename; //40
	private String sfrCompleted; //41
	private String implementationStatus; //42
	private String abortType; //43
	private Date ef360ClaimDT; //44
	private Date ef390ClaimDT; //45
	private String performanceChecks; //46
	private Date ef400ClaimDT; //47
	private Date ef410ClaimDT; //48
	private String hopOnSharepoint; //49
	private String sfrOnSharepoint; //50
	private String workDetails; //51
	private String hardwareVendor; //52
	private String preTestCallsDone; //53
	private String postTestCallsDone; //54
	private String crqClosureCode; //55
	private String siteIssues; //56
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat timestampFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	public SNRBOInformation (long snrId, String site, String nrId,
			String boEngineerList, String feList, String status,
			Date scheduledDate, String nextPreCheck, String firstAttempt,
			String hopStatus, String cramerCompleted, String scriptsReceived,
			Date ef345ClaimDT, String alarms, String healthCheck,
			String crInReference, Timestamp crInStartDT, Timestamp crInEndDT,
			String tefOutageNos, String jobType, String twoGInd,
			String threeGInd, String fourGInd, String tef2GInd,
			String tef3GInd, String tef4GInd, String paknetPaging,
			String secGWChange, String power, String survey,
			String other, Timestamp implementationStartDT,
			Timestamp implementationEndDT, Timestamp implOutageStartDT,
			Timestamp implOutageEndDT, String activeAlarmsInd,
			String healthChecksInd, String incTicketNo,
			String hopDeliveredInd, String hopFilename, String sfrCompleted,
			String implementationStatus, String abortType, Date ef360ClaimDT,
			Date ef390ClaimDT, String performanceChecks, Date ef400ClaimDT,
			Date ef410ClaimDT, String hopOnSharepoint, String sfrOnSharepoint,
			String workDetails, String hardwareVendor, String preTestCallsDone, 
			String postTestCallsDone, String crqClosureCode, String siteIssues) {
		this.snrId = snrId;
		this.site = site;
		this.nrId = nrId;
		this.boEngineerList = boEngineerList;
		this.feList = feList;
		this.status = status;
		this.scheduledDate = scheduledDate;
		this.nextPreCheck = nextPreCheck;
		this.firstAttempt = firstAttempt;
		this.hopStatus = hopStatus;
		this.cramerCompleted = cramerCompleted;
		this.scriptsReceived = scriptsReceived;
		this.ef345ClaimDT = ef345ClaimDT;
		this.alarms = alarms;
		this.healthCheck = healthCheck;
		this.crInReference = crInReference;
		this.crInStartDT = crInStartDT;
		this.crInEndDT = crInEndDT;
		this.tefOutageNos = tefOutageNos;
		this.jobType = jobType;
		this.twoGInd = twoGInd;
		this.threeGInd = threeGInd;
		this.fourGInd = fourGInd;
		this.tef2GInd = tef2GInd;
		this.tef3GInd = tef3GInd;
		this.tef4GInd = tef4GInd;
		this.paknetPaging = paknetPaging;
		this.secGWChange = secGWChange;
		this.power = power;
		this.survey = survey;
		this.other = other;
		this.implementationStartDT = implementationStartDT;
		this.implementationEndDT = implementationEndDT;
		this.implOutageStartDT = implOutageStartDT;
		this.implOutageEndDT = implOutageEndDT;
		this.activeAlarmsInd = activeAlarmsInd;
		this.healthChecksInd = healthChecksInd;
		this.incTicketNo = incTicketNo;
		this.hopDeliveredInd = hopDeliveredInd;
		this.hopFilename = hopFilename;
		this.sfrCompleted = sfrCompleted;
		this.implementationStatus = implementationStatus;
		this.abortType = abortType;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef390ClaimDT = ef390ClaimDT;
		this.performanceChecks = performanceChecks;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
		this.hopOnSharepoint = hopOnSharepoint;
		this.sfrOnSharepoint = sfrOnSharepoint;
		this.workDetails = workDetails;
		this.hardwareVendor = hardwareVendor;
		this.preTestCallsDone = preTestCallsDone;
		this.postTestCallsDone = postTestCallsDone;
		this.crqClosureCode = crqClosureCode;
		this.siteIssues = siteIssues;
	}
	
	public long getSNRId() {
		return snrId;
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
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getScheduledDateString() {
		return scheduledDate==null?"":dateFormatter.format(scheduledDate);
	}
	
	public String getCRINReference() {
		return crInReference==null?"":crInReference;
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
	
	public String getHealthChecksInd() {
		return healthChecksInd==null?"":healthChecksInd;
	}
	
	public String getActiveAlarmsInd() {
		return activeAlarmsInd==null?"":activeAlarmsInd;
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
	
	public String getSFRCompleted() {
		return sfrCompleted==null?"":sfrCompleted;
	}
	
	public String getSFROnSharepoint() {
		return sfrOnSharepoint==null?"":sfrOnSharepoint;
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
	
	public String getTEFOutageNos() {
		return tefOutageNos==null?"":tefOutageNos;
	}
	
	public String getBOEngineerList() {
		return boEngineerList==null?"":boEngineerList;
	}
	
	public String getFEList() {
		return feList==null?"":feList;
	}
	
	public String getNextPreCheck() {
		return nextPreCheck==null?"":nextPreCheck;
	}
	
	public String getFirstAttempt() {
		return firstAttempt==null?"":firstAttempt;
	}
	
	public String getHOPStatus() {
		return hopStatus==null?"":hopStatus;
	}
	
	public String getCramerCompleted() {
		return cramerCompleted==null?"":cramerCompleted;
	}
	
	public String getScriptsReceived() {
		return scriptsReceived==null?"":scriptsReceived;
	}
	
	public String getAlarms() {
		return alarms==null?"":alarms;
	}
	
	public String getHealthCheck() {
		return healthCheck==null?"":healthCheck;
	}
	
	public String getJobType() {
		return jobType==null?"":jobType;
	}
	
	public String getTEF2GInd() {
		return tef2GInd==null?"":tef2GInd;
	}
	
	public String getTEF3GInd() {
		return tef3GInd==null?"":tef3GInd;
	}
	
	public String getTEF4GInd() {
		return tef4GInd==null?"":tef4GInd;
	}
	
	public String getPaknetPaging() {
		return paknetPaging==null?"":paknetPaging;
	}
	
	public String getSecGWChange() {
		return secGWChange==null?"":secGWChange;
	}
	
	public String getPower() {
		return power==null?"":power;
	}
	
	public String getSurvey() {
		return survey==null?"":survey;
	}
	
	public String getOther() {
		return other==null?"":other;
	}
	
	public String getIncTicketNo() {
		return incTicketNo==null?"":incTicketNo;
	}
	
	public String getPerformanceChecks() {
		return performanceChecks==null?"":performanceChecks;
	}

	public String getWorkDetails() {
		return workDetails==null?"":workDetails;
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
	
	public String getSiteIssues() {
		return siteIssues==null?"":siteIssues;
	}
	
}
