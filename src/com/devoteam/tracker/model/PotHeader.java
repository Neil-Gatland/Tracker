package com.devoteam.tracker.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PotHeader implements Serializable {

	private static final long serialVersionUID = -4694860202324947660L;
	private final static String[] headerDataTitles = 
		{	"Customer Name", "Job Type", "Pot Date"};
	private String customerName;
	private String jobType;
	private Date potDate;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public PotHeader( String customerName, String jobType, Date potDate) {
		this.customerName = customerName;
		this.jobType = jobType;
		this.potDate = potDate;		
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public String getJobType() {
		return jobType;
	}

	public Date getPotDate() {
		return potDate;
	}

	public String getPotDateString() {
		return (potDate==null?"":dateFormatter.format(potDate));
	}
	
	public static String[] getHeaderDataTitles() {
		return headerDataTitles;
	}

}
