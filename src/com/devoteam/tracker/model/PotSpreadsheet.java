package com.devoteam.tracker.model;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

public class PotSpreadsheet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3459145739098642797L;
	private final static String[] headerDataTitles = {"Customer Name", "Job Type",
		"Project Requestor", "Pot Name", "Pot Date"};
	private long potId;
	private long customerId;
	private String customerName;
	private String jobType;
	private String projectRequestor;
	private String potName;
	private Date potDate;
	private String potFileName;
	private boolean potExists;
	private boolean updateProjectRequestor;
	private ArrayList<PotSpreadsheetSNR> snrDataRows;
	
	public PotSpreadsheet (long potId, long customerId, String customerName, String jobType, 
			String projectRequestor, String potName, Date potDate,
			String potFileName, boolean potExists, boolean updateProjectRequestor) {
		this.potId = potId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.jobType = jobType;
		this.projectRequestor = projectRequestor;
		this.potName = potName;
		this.potDate = potDate;
		this.potFileName = potFileName;
		this.potExists = potExists;
		this.updateProjectRequestor = updateProjectRequestor;
		this.snrDataRows = new ArrayList<PotSpreadsheetSNR>(); 
	}
	
	public PotSpreadsheet (long potId, long customerId, String customerName, String jobType, 
			String projectRequestor, String potName, Date potDate, 
			String potFileName,	boolean potExists, boolean updateProjectRequestor,
			ArrayList<PotSpreadsheetSNR> snrDataRows) {
		this.potId = potId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.jobType = jobType;
		this.projectRequestor = projectRequestor;
		this.potName = potName;
		this.potDate = potDate;
		this.potFileName = potFileName;
		this.potExists = potExists;
		this.updateProjectRequestor = updateProjectRequestor;
		this.snrDataRows = snrDataRows; 
	}
	
	public void addSNRDataRow(PotSpreadsheetSNR snrDataRow) {
		this.snrDataRows.add(snrDataRow);
	}
	
	public void setSNRDataRows(ArrayList<PotSpreadsheetSNR> snrDataRows) {
		this.snrDataRows = snrDataRows;
	}
	
	public Collection<PotSpreadsheetSNR> getSNRDataRows() {
		return snrDataRows;
	}
	
	public long getPotId() {
		return potId;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public String getJobType() {
		return jobType;
	}
	
	public String getprojectRequestor() {
		return projectRequestor;
	}		
	
	public String getPotName() {
		return potName;
	}
	
	public String getPotFileName() {
		return potFileName;
	}
	
	public Date getPotDate() {
		return potDate;
	}
	
	public boolean getPotExists() {
		return potExists;
	}
	
	public boolean getUpdateProjectRequestor() {
		return updateProjectRequestor;
	}
	
	public static String[] getHeaderDataTitles() {
		return headerDataTitles;
	}
}
