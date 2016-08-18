package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Date;

public class PotSpreadsheetSNR implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4129641738233396043L;
	private final static String[] snrTrailerValues = {"999999", "999999-999999", 
		"TRAILER", "ZZ99 9ZZ"};
	private final static String[] snrDataTitles = {"Site", "NR Id", "Upgrade Type",
		"Postcode", "Vodafone 2G?", "Vodafone 3G?", "Vodafone 4G?", "TEF 2G?",
		"TEF 3G?", "TEF 4G?", "Paknet and Paging?", "SecGW Changes?", 
		"Power?", "Survey?", "Hardware Vendor"};
	private long snrId;
	private String site;
	private String nrId;
	private String upgradeType;
	private String eastings;
	private String northings;
	private String postcode;
	private String region;
	private Date ef345ClaimDT;
	private Date ef360ClaimDT;
	private Date ef400ClaimDT;
	private Date ef410ClaimDT;
	private String vodafone2G;
	private String vodafone3G;
	private String vodafone4G;
	private String tef2G;
	private String tef3G;
	private String tef4G;
	private String paknetPaging;
	private String secGWChanges;
	private String power;
	private String survey;
	private String hardwareVendor;
	private boolean snrExists;

	public final static String INC = "INC"; 
	public final static String MISSING = "MISSING"; 

	public PotSpreadsheetSNR (String site,
			String nrId, String upgradeType, String eastings,
			String northings, String postcode, String region, Date ef345ClaimDT,
			Date ef360ClaimDT, Date ef400ClaimDT, Date ef410ClaimDT, 
			String vodafone2G, String vodafone3G, String vodafone4G, String tef2G,
			String tef3G, String tef4G, String paknetPaging, String secGWChanges,
			String power, String survey) {
		this.site = site;
		this.nrId = nrId;
		this.upgradeType = upgradeType;
		this.eastings = eastings;
		this.northings = northings;
		this.postcode = postcode;
		this.region = region;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
		this.vodafone2G = vodafone2G;
		this.vodafone3G = vodafone3G;
		this.vodafone4G = vodafone4G;
		this.tef2G = tef2G;
		this.tef3G = tef3G;
		this.tef4G = tef4G;
		this.paknetPaging = paknetPaging;
		this.secGWChanges = secGWChanges;
		this.power = power;
		this.survey = survey;
		this.snrExists = false;
		this.snrId = -1;
	}

	public PotSpreadsheetSNR (String site,
			String nrId, String upgradeType, String postcode,  
			String vodafone2G, String vodafone3G, String vodafone4G, String tef2G,
			String tef3G, String tef4G, String paknetPaging, String secGWChanges,
			String power, String survey) {
		this.site = site;
		this.nrId = nrId;
		this.upgradeType = upgradeType;
		this.postcode = postcode;
		this.vodafone2G = vodafone2G;
		this.vodafone3G = vodafone3G;
		this.vodafone4G = vodafone4G;
		this.tef2G = tef2G;
		this.tef3G = tef3G;
		this.tef4G = tef4G;
		this.paknetPaging = paknetPaging;
		this.secGWChanges = secGWChanges;
		this.power = power;
		this.survey = survey;
		this.snrExists = false;
		this.snrId = -1;
	}

	public PotSpreadsheetSNR (String site,
			String nrId, String upgradeType, String postcode,  
			String vodafone2G, String vodafone3G, String vodafone4G, String tef2G,
			String tef3G, String tef4G, String paknetPaging, String secGWChanges,
			String power, String survey, String hardwareVendor) {
		this.site = site;
		this.nrId = nrId;
		this.upgradeType = upgradeType;
		this.postcode = postcode;
		this.vodafone2G = vodafone2G;
		this.vodafone3G = vodafone3G;
		this.vodafone4G = vodafone4G;
		this.tef2G = tef2G;
		this.tef3G = tef3G;
		this.tef4G = tef4G;
		this.paknetPaging = paknetPaging;
		this.secGWChanges = secGWChanges;
		this.power = power;
		this.survey = survey;
		this.hardwareVendor = hardwareVendor;
		this.snrExists = false;
		this.snrId = -1;
	}
		
	public PotSpreadsheetSNR (long snrId, String site,
			String nrId, Date ef345ClaimDT,
			Date ef360ClaimDT, Date ef400ClaimDT, Date ef410ClaimDT) {
		this.site = site;
		this.nrId = nrId;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
		this.snrExists = true;
		this.snrId = snrId;
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
	
	public String getUpgradeType() {
		return upgradeType;
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
	
	public Date getEF360ClaimDT() {
		return ef360ClaimDT;
	}
	
	public Date getEF400ClaimDT() {
		return ef400ClaimDT;
	}
	
	public Date getEF410ClaimDT() {
		return ef410ClaimDT;
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
	
	public String getTEF2G() {
		return tef2G;
	}
	
	public String getTEF3G() {
		return tef3G;
	}
	
	public String getTEF4G() {
		return tef4G;
	}
	
	public String getPaknetPaging() {
		return paknetPaging;
	}
	
	public String getSecGWChanges() {
		return secGWChanges;
	}
	
	public String getPower() {
		return power;
	}
	
	public String getSurvey() {
		return survey;
	}
	
	public String getHardwareVendor() {
		return hardwareVendor;
	}
	
	public boolean getSNRExists() {
		return snrExists;
	}
	
	public static String[] getSNRDataTitles() {
		return snrDataTitles;
	}
	
	public static String[] getSNRTrailerValues() {
		return snrTrailerValues;
	}
}
