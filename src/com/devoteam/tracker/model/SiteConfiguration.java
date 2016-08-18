package com.devoteam.tracker.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class SiteConfiguration {
	private long customerId;
	private String customerName;
	private String site;
	private String bsc;
	private String rnc;
	private String twoGVendor;
	private String threeGVendor;
	private String fourGVendor;
	private String threeGRBSType;
	private long noG9Sectors;
	private long noG18Sectors;
	private long noU21Sectors;
	private long noU21Carriers;
	private long noU9Sectors;
	private long noU9Carriers;
	private long noL8Sectors;
	private long noL8Carriers;
	private long noL26Sectors;
	private long noL26Carriers;
	private long no2GE1;
	private long no3GE11P;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private String eastings;
	private String northings;
	private String postcode;
	private String region;
	private String siteName;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private String[] titleArray = {
			"Customer:",
			//repeatable bit follows
			"BSC:", "RNC:", 
			"2G Vendor:", "3G Vendor:", 
			"4G Vendor:", "3G RBS Type:", 
			"No. G9 Sectors:", "No. G18 Sectors:", 
			"No. U21 Sectors:", "No. U21 Carriers:", 
			"No. U9 Sectors:", "No. U9 Carriers:", 
			"No. L8 Sectors:", "No. L8 Carriers:", 
			"No. L26 Sectors:", "No. L26 Carriers:", 
			"No. 2G E1s:", "No. 3G E1 1P:", 
			"Eastings:", "Northings:", 
			"Postcode:", "Region:", 
			"Last Updated By:", "Last Updated Date:"};
	
	public SiteConfiguration(long customerId, String customerName, String site,
			String bsc,	String rnc,	String twoGVendor,	String threeGVendor,
			String fourGVendor,	String threeGRBSType, long noG9Sectors,	
			long noG18Sectors,	long noU21Sectors,	long noU21Carriers,
			long noU9Sectors, long noU9Carriers, long noL8Sectors,	long noL8Carriers, 
			long noL26Sectors,	long noL26Carriers,	long no2GE1, long no3GE11P,	
			String eastings, String northings, String postcode, String region, 
			Date lastUpdatedDate, String lastUpdatedBy, String siteName) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.site = site;
		this.bsc = bsc;
		this.rnc = rnc;
		this.twoGVendor = twoGVendor;
		this.threeGVendor = threeGVendor;
		this.fourGVendor = fourGVendor;
		this.threeGRBSType = threeGRBSType;
		this.noG9Sectors = noG9Sectors;
		this.noG18Sectors = noG18Sectors;
		this.noU21Sectors = noU21Sectors;
		this.noU21Carriers = noU21Carriers;
		this.noU9Sectors = noU9Sectors;
		this.noU9Carriers = noU9Carriers;
		this.noL8Sectors = noL8Sectors;
		this.noL8Carriers = noL8Carriers;
		this.noL26Sectors = noL26Sectors;
		this.noL26Carriers = noL26Carriers;
		this.no2GE1 = no2GE1;
		this.no3GE11P = no3GE11P;
		this.eastings = eastings;
		this.northings = northings;
		this.postcode = postcode;
		this.region = region;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.siteName = siteName;
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
	
	public String getBSC() {
		return bsc==null?"":bsc;
	}
	
	public String getRNC() {
		return rnc==null?"":rnc;
	}
	
	public String get2GVendor() {
		return twoGVendor==null?"":twoGVendor;
	}
	
	public String get3GVendor() {
		return threeGVendor==null?"":threeGVendor;
	}
	
	public String get4GVendor() {
		return fourGVendor==null?"":fourGVendor;
	}
	
	public String get3GRBSType() {
		return threeGRBSType==null?"":threeGRBSType;
	}
	
	public long getNoG9Sectors() {
		return noG9Sectors;
	}
	
	public String getNoG9SectorsString() {
		return Long.toString(noG9Sectors);
	}
	
	public long getNoG18Sectors() {
		return noG18Sectors;
	}
	
	public String getNoG18SectorsString() {
		return Long.toString(noG18Sectors);
	}
	
	public long getNoU21Sectors() {
		return noU21Sectors;
	}
	
	public String getNoU21SectorsString() {
		return Long.toString(noU21Sectors);
	}
	
	public long getNoU21Carriers() {
		return noU21Carriers;
	}
	
	public String getNoU21CarriersString() {
		return Long.toString(noU21Carriers);
	}
	
	public long getNoU9Sectors() {
		return noU9Sectors;
	}
	
	public String getNoU9SectorsString() {
		return Long.toString(noU9Sectors);
	}
	
	public long getNoU9Carriers() {
		return noU9Carriers;
	}
	
	public String getNoU9CarriersString() {
		return Long.toString(noU9Carriers);
	}
	
	public long getNoL8Sectors() {
		return noL8Sectors;
	}
	
	public String getNoL8SectorsString() {
		return Long.toString(noL8Sectors);
	}
	
	public long getNoL8Carriers() {
		return noL8Carriers;
	}
	
	public String getNoL8CarriersString() {
		return Long.toString(noL8Carriers);
	}
	
	public long getNoL26Sectors() {
		return noL26Sectors;
	}
	
	public String getNoL26SectorsString() {
		return Long.toString(noL26Sectors);
	}
	
	public long getNoL26Carriers() {
		return noL26Carriers;
	}
	
	public String getNoL26CarriersString() {
		return Long.toString(noL26Carriers);
	}
	
	public long getNo2GE1() {
		return no2GE1;
	}
	
	public String getNo2GE1String() {
		return Long.toString(no2GE1);
	}
	
	public long getNo3GE11P() {
		return no3GE11P;
	}
	
	public String getNo3GE11PString() {
		return Long.toString(no3GE11P);
	}
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public String getLastUpdatedDateString() {
		return dateFormatter.format(lastUpdatedDate);
	}
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
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
	
	public String getSiteName() {
		return siteName==null?"":siteName;
	}
	
	public String getSiteNameTitle() {
		return "Site Name:";
	}
	
	public String[] getTitleArray() {
		return titleArray;
	}
	
	public String[] getValueArray() {
		String[] valueArray = {
			this.getCustomerName(),
			//repeatable bit follows
			this.getBSC(), this.getRNC(), 
			this.get2GVendor(), this.get3GVendor(), 
			this.get4GVendor(), this.get3GRBSType(), 
			this.getNoG9SectorsString(), this.getNoG18SectorsString(), 
			this.getNoU21SectorsString(), this.getNoU21CarriersString(), 
			this.getNoU9SectorsString(), this.getNoU9CarriersString(), 
			this.getNoL8SectorsString(), this.getNoL8CarriersString(), 
			this.getNoL26SectorsString(), this.getNoL26CarriersString(), 
			this.getNo2GE1String(), this.getNo3GE11PString(), 
			this.getEastings(), this.getNorthings(),
			this.getPostcode(), this.getRegion(),
			this.getLastUpdatedBy(), this.getLastUpdatedDateString()}; 
			//end of repeatable bit
	
		return valueArray;
	}
	
	
}
