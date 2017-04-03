package com.devoteam.tracker.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PotDetail implements Serializable{

	private static final long serialVersionUID = 2505696353738643863L;
	private final static String[] detailDataTitles = 
		{	"Site", 
			"NR Id", 
			"Upgrade Type",
			"Schedule Date",
			"VF 2G?",
			"VF 3G?",
			"VF 4G?",
			"TEF 2G?",
			"TEF 3G?",
			"TEF 4G?",
			"P&P?",
			"Sec GW?",
			"Power?",
			"Survey?",
			"Other?",
			"Hardware Vendor",
			"BO Eng.",
			"FE Eng.",
			"FE Company" };
	private String site;
	private String nrId;
	private String upgradeType;
	private Date scheduledDate;
	private String vf2G;
	private String vf3G;
	private String vf4G;
	private String tef2G;
	private String tef3G;
	private String tef4G;
	private String paknetPaging;
	private String secGW;
	private String power;
	private String survey;
	private String other;
	private String hardwareVendor;
	private String boEng;
	private String feEng;
	private String feCompany;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	

	
	public PotDetail( 
			String site,
			String nrId,
			String upgradeType,
			Date scheduledDate,
			String vf2G,
			String vf3G,
			String vf4G,
			String tef2G,
			String tef3G,
			String tef4G,
			String paknetPaging,
			String secGW,
			String power,
			String survey,
			String other,
			String hardwareVendor,
			String boEng,
			String feEng,
			String feCompany ) {
		this.site = site;
		this.nrId = nrId;
		this.upgradeType = upgradeType;
		this.scheduledDate = scheduledDate;
		this.vf2G = vf2G;
		this.vf3G = vf3G;
		this.vf4G = vf4G;
		this.tef2G = tef2G;
		this.tef3G = tef3G;
		this.tef4G = tef4G;
		this.paknetPaging = paknetPaging;
		this.secGW = secGW;
		this.power = power;
		this.survey = survey;
		this.other = other;
		this.hardwareVendor = hardwareVendor;
		this.boEng = boEng;
		this.feEng = feEng;	
		this.feCompany = feCompany;			
	}
	
	public String getSite() {
		return site;
	}
	
	public String getNrId() {
		return nrId;
	}
	
	public String getUpgradeType() {
		return (upgradeType==null?"":upgradeType);
	}
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getScheduledDateString() {
		return (scheduledDate==null?"":dateFormatter.format(scheduledDate));
	}
	
	public String getVf2G() {
		return (vf2G==null?"N":vf2G);
	}
	
	public String getVf3G() {
		return (vf3G==null?"N":vf3G);
	}
	
	public String getVf4G() {
		return (vf4G==null?"N":vf4G);
	}
	
	public String getTef2G() {
		return (tef2G==null?"N":tef2G);
	}
	
	public String getTef3G() {
		return (tef3G==null?"N":tef3G);
	}

	public String getTef4G() {
		return (tef4G==null?"N":tef4G);
	}
	
	public String getPaknetPaging() {
		return (paknetPaging==null?"N":paknetPaging);
	}
	
	public String getSecGW() {
		return (secGW==null?"N":secGW);
	}
	
	public String getPower() {
		return (power==null?"N":power);
	}
	
	public String getSurvey() {
		return (survey==null?"N":survey);
	}
	
	public String getOther() {
		return (other==null?"N":other);
	}
	
	public String getHardwareVendor() {
		return (hardwareVendor==null?"":hardwareVendor);
	}
	
	public String getBoEng() {
		return boEng;
	}
	
	public String getFeEng() {
		return feEng;
	}
		
	public String getFeCompany() {
		return feCompany;
	}
	
	public static String[] getDetailDataTitles() {
		return detailDataTitles;
	}
}
