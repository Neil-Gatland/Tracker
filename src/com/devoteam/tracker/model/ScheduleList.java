package com.devoteam.tracker.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class ScheduleList {

	private String scheduleDate;	//1
	private String site; 			//2
	private String nrId; 			//3
	private String siteStatus; 		//4
	private String project; 		//5
	private String upgradeType; 	//6
	private String bo;		 		//7
	private String fe;		 		//8
	private String hardwareVendor;	//9
	private String boAll; 			//10
	private String feAll; 			//11
	private Date scheduledDate; 	//12
	private String vodafone2G;	 	//13
	private String vodafone3G; 		//14
	private String vodafone4G;	 	//15
	private String tef2G; 			//16
	private String tef3G; 			//17
	private String tef4G; 			//18
	private String paknetPaging; 	//19
	private String secGWChange; 	//20
	private String power; 			//18
	private String survey; 			//19
	private String other; 			//20
	private int snrId;	 			//20	

	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public ScheduleList(
			String scheduleDate,
			String site,
			String nrId,
			String siteStatus,
			String project,
			String upgradeType,
			String bo,
			String fe,
			String hardwareVendor,
			String boAll,
			String feAll,
			Date scheduledDate,
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
			int snrId	) {
		this.scheduleDate = scheduleDate;
		this.site = site;
		this.nrId = nrId;
		this.siteStatus = siteStatus;
		this.project = project;
		this.upgradeType = upgradeType;
		this.bo = bo;
		this.fe = fe;
		this.hardwareVendor = hardwareVendor;
		this.boAll = boAll;
		this.feAll= feAll;
		this.scheduledDate = scheduledDate;
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
		this.snrId = snrId;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public String getSite() {
		return site;
	}

	public String getNrId() {
		return nrId;
	}

	public String getSiteStatus() {
		return siteStatus;
	}

	public String getProject() {
		return project;
	}

	public String getUpgradeType() {
		return upgradeType;
	}

	public String getBo() {
		return bo;
	}

	public String getFe() {
		return fe;
	}

	public String getHardwareVendor() {
		return hardwareVendor;
	}

	public String getBoAll() {
		return boAll;
	}

	public String getFeAll() {
		return feAll;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getScheduledDateString() {
		
		return (scheduledDate==null?"":dateFormatter.format(scheduledDate));
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

	public int getSnrId() {
		return snrId;
	}
	
}
