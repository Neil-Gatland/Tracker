package com.devoteam.tracker.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class SNRAccessDetail {
	private long snrId; //1
	private String p1SiteInd; //2
	private String ramsInd; //3
	private String healthSafetyInd; //4
	private String accessConfirmedInd; //5
	private String obassInd; //6
	private String escortInd; //7
	private String tefOutageRequired; //8
	private String vfArrangeAccess; //9
	private String twoManSite; //10
	private String oohWeekendInd; //11
	private String crINReference; //12
	private String tefOutageNos; //13
	private double outagePeriod; //14
	private double consumableCost; //15
	private double accessCost; //16
	private String siteName; //17
	private String siteAccessInformation; //18	
	private String permitType; //19	
	private String accessStatus; //20	

	private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.UK);
	private DecimalFormat numberFormatter = new DecimalFormat("#.##");
	
	public SNRAccessDetail(
			long snrId,
			String p1SiteInd,
			String ramsInd, 
			String healthSafetyInd, 
			String accessConfirmedInd,
			String obassInd, 
			String escortInd,
			String tefOutageRequired, 
			String vfArrangeAccess, 
			String twoManSite, 
			String oohWeekendInd, 
			String crINReference, 
			String tefOutageNos, 
			double outagePeriod, 
			double consumableCost, 
			double accessCost, 
			String siteName, 
			String siteAccessInformation,
			String permitType,
			String accessStatus) {
		this.snrId = snrId;
		this.p1SiteInd = p1SiteInd;
		this.ramsInd = ramsInd;
		this.healthSafetyInd = healthSafetyInd;
		this.accessConfirmedInd = accessConfirmedInd;
		this.obassInd = obassInd;
		this.escortInd = escortInd;
		this.tefOutageRequired = tefOutageRequired;
		this.vfArrangeAccess = vfArrangeAccess;
		this.twoManSite = twoManSite;
		this.oohWeekendInd = oohWeekendInd;
		this.crINReference = crINReference;
		this.tefOutageNos = tefOutageNos;
		this.outagePeriod = outagePeriod;
		this.consumableCost = consumableCost;
		this.accessCost = accessCost;
		this.siteName = siteName;
		this.siteAccessInformation = siteAccessInformation;
		this.permitType = permitType;
		this.accessStatus = accessStatus;
	}

	
	public long getSNRId() {
		return snrId;
	}
	
	public String getP1SiteInd() {
		return p1SiteInd;
	}
	
	public String getRAMSInd() {
		return ramsInd;
	}

	public String getHealthSafetyInd() {
		return healthSafetyInd;
	}
	
	public String getAccessConfirmedInd() {
		return accessConfirmedInd;
	}

	public String getOBASSInd() {
		return obassInd;
	}
	
	public String getEscortInd() {
		return escortInd;
	}
	
	public String getTEFOutageRequired() {
		return tefOutageRequired;
	}
	
	public String getVFArrangeAccess() {
		return vfArrangeAccess;
	}
	
	public String getTwoManSite() {
		return twoManSite;
	}
	
	public String getOOHWeekendInd() {
		return oohWeekendInd;
	}
	
	public String getCRINReference() {
		return crINReference;
	}
	
	public String getTEFOutageNos() {
		return tefOutageNos;
	}
	
	public double getOutagePeriod() {
		return outagePeriod;
	}
	
	public String getOutagePeriodString() {
		return numberFormatter.format(outagePeriod);
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
	
	public String getSiteName() {
		return siteName;
	}
	
	public String getSiteAccessInformation() {
		return siteAccessInformation;
	}
	
	public String getAccessStatus() {
		return accessStatus;
	}
	
	public String getPermitType() {
		return permitType;
	}

}
