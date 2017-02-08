package com.devoteam.tracker.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class CompletionReportDetail {
	private long snrId; //1
	private String site; //2
	private String nrId; //3
	private Date completionDate; //4
	private String completionStatus; //5
	private String hardwareVendor; //6
	private String lockTime; //7
	private String unlockTime; //8
	private String preTestCallsDone; //9
	private String postTestCallsDone; //10
	private String crqClosureCode; //11
	private String fe1List; //12
	private String fe2List; //13
	private String siteIssues; //14
	private String vodafone2G; //15
	private String vodafone3G; //16
	private String vodafone4G; //17
	private String tef2G; //18
	private String tef3G; //19
	private String tef4G; //20
	private String paknetPaging; //21
	private String secGWChange; //22
	private String power; //23
	private String survey; //24
	private String other; //25
	private String toList; //26
	private String ccList; //27
	private String bccList; //28
	private String project; //29
	private String emailEndMessage; //30
	private String emailSendAddress; //30
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
	
	public CompletionReportDetail(	
			long snrId,
			String site,
			String nrId,
			Date completionDate,
			String completionStatus,
			String hardwareVendor,
			String lockTime,
			String unlockTime,
			String preTestCallsDone,
			String postTestCallsDone,
			String crqClosureCode,
			String fe1List,
			String fe2List,
			String siteIssues,
			String vodafone2G,
			String vodafone3G,
			String vodafone4G,
			String tef2G,
			String tef3G,
			String tef4G,
			String paknetPaging,
			String secGWChange,
			String power,
			String survey,
			String other,
			String toList,
			String ccList,
			String bccList,
			String project,
			String emailEndMessage,
			String emailSendAddress) {
		this.snrId = snrId;
		this.site = site;
		this.nrId = nrId;
		this.completionDate = completionDate;
		this.completionStatus = completionStatus;
		this.hardwareVendor = hardwareVendor;
		this.lockTime = lockTime;
		this.unlockTime = unlockTime;
		this.preTestCallsDone = preTestCallsDone;
		this.postTestCallsDone = postTestCallsDone;
		this.crqClosureCode = crqClosureCode;
		this.fe1List = fe1List;
		this.fe2List = fe2List;
		this.siteIssues = siteIssues;
		this.vodafone2G = vodafone2G;
		this.vodafone3G = vodafone3G;
		this.vodafone4G = vodafone4G;
		this.tef2G = tef2G;
		this.tef3G = tef3G;
		this.tef4G = tef4G;
		this.paknetPaging = paknetPaging;
		this.secGWChange = secGWChange;
		this.power = power;
		this.survey = survey;
		this.other = other;
		this.toList = toList;
		this.ccList = ccList;
		this.bccList = bccList;
		this.project = project;
		this.emailEndMessage = emailEndMessage;
		this.emailSendAddress = emailSendAddress;
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
		
	public Date getCompletionDate() {
		return completionDate;
	}
	
	public String getCompletionDateString() {
		return completionDate==null?"":dateFormatter.format(completionDate);
	}
	
	public String getCompletionDateString2() {
		return completionDate==null?"":dateFormatter2.format(completionDate)+" 00:00:00";
	}
	
	public String getCompletionStatus() {
		return completionStatus;
	}
	
	public String getHardwareVendor() {
		return hardwareVendor;
	}
	
	public String getLockTime() {
		return lockTime;
	}
	
	public String getUnlockTime() {
		return unlockTime;
	}
	
	public String getPreTestCallsDone() {
		return preTestCallsDone;
	}

	public String getPostTestCallsDone() {
		return postTestCallsDone;
	}
	
	public String getCrqClosureCode() {
		return crqClosureCode;
	}
	
	public String getFe1List() {
		return fe1List;
	}
	
	public String getFe2List() {
		return fe2List;
	}
	
	public String getSiteIssues() {
		return siteIssues;
	}
	
	public String getVodafone2G() {
		return vodafone2G;
	}
	
	public String getVodafone3G() {
		return vodafone3G;
	}
	
	public String getVodafone4G() {
		return vodafone4G;
	}
	
	public String getTef2G() {
		return tef2G;
	}
	
	public String getTef3G() {
		return tef3G;
	}
	
	public String getTef4G() {
		return tef4G;
	}
	
	public String getPaknetPaging() {
		return paknetPaging;
	}
	
	public String getSecGWChange() {
		return secGWChange;
	}
	
	public String getPower() {
		return power;
	}
	
	public String getSurvey() {
		return survey;
	}
	
	public String getOther() {
		return other;
	}
	
	public String getToList() {
		return toList;
	}
	
	public String getCcList() {
		return ccList;
	}
	
	public String getBccList() {
		return bccList;
	}
	
	public String getProject() {
		return project;
	}
	
	public String getEmailEndMessage() {
		return emailEndMessage;
	}
	
	public String getEmailSendAddress() {
		return emailSendAddress;
	}

}
