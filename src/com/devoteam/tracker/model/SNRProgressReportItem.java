package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Date;

import com.devoteam.tracker.util.ServletConstants;

public class SNRProgressReportItem implements Serializable {
	private long snrId;
	private String customerName;
	private long potId;
	private String potName;
	private String site;
	private String nrId;
	private String status;
	private String implementationStatus;
	private String overdueInd;
	private int daysLate;
	private Timestamp implementationEndDT;
	private Date scheduledDate;
	private final String STATUS_A_S = "Awaiting Scheduling";
	private final String STATUS_A_S_SHORT = "Awaiting Sch.";
	private final SimpleDateFormat tsFormatter = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
	private final SimpleDateFormat tsdFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private final static String[] columnTitles = {"Customer Name", "Site", "NR Id", 
		"Status", "Scheduled Date", "Implementation Status", "implementation End DT", 
		"Overdue Ind", "Days Late"};
	
	public SNRProgressReportItem(long snrId, String customerName, 
			long potId,	String potName, String site, String nrId, String status, 
			Date scheduledDate, String implementationStatus, 
			Timestamp implementationEndDT, String overdueInd, int daysLate) {
		this.snrId = snrId;
		this.customerName = customerName;
		this.potId = potId;
		this.potName = potName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.scheduledDate = scheduledDate;
		this.implementationStatus = implementationStatus;
		this.implementationEndDT = implementationEndDT;
		this.overdueInd = overdueInd;
		this.daysLate = daysLate;
	}
	
	public long getSNRId() {
		return snrId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public long getPotId() {
		return potId;
	}
	
	public String getPotName() {
		return potName;
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
	
	public String getImplementationStatus() {
		return implementationStatus;
	}
	
	public String getImplementationStatusString() {
		return implementationStatus==null?"":implementationStatus;
	}
	
	public int getDaysLate() {
		return daysLate;
	}
	
	public String getDaysLateString() {
		return Integer.toString(daysLate);
	}
	
	public Timestamp getImplementationEndDT() {
		return implementationEndDT;
	}
	
	public String getImplementationEndDTString() {
		return implementationEndDT==null?"":tsFormatter.format(new java.util.Date(implementationEndDT.getTime()));
	}
	
	public String getImplementationEndDateString() {
		return implementationEndDT==null?"":tsdFormatter.format(new java.util.Date(implementationEndDT.getTime()));
	}
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getScheduledDateString() {
		return scheduledDate==null?"":dateFormatter.format(scheduledDate);
	}
	
	public String getScheduledDDMM() {
		return scheduledDate==null?"":dateFormatter.format(scheduledDate).substring(0, 5);
	}
	
	public String getOverdueInd() {
		return overdueInd;
	}
	
	public String[] getValueArray() {
		String[] values = {customerName, site, nrId, 
			status,	this.getScheduledDateString(), 
			this.getImplementationStatusString(), this.getImplementationEndDateString(), 
			overdueInd, this.getDaysLateString()};
		return values;
	}
	
	public String[] getDownloadValueArray() {
		String[] values = {customerName,site, nrId, 
			status, this.getScheduledDateString(), 
			this.getImplementationStatusString(), this.getImplementationEndDTString(), 
			overdueInd, this.getDaysLateString()};
		return values;
	}
	
	public static String[] getColumnTitles() {
		return columnTitles;
	}
	
}
