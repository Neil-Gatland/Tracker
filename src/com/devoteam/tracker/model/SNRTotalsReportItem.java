package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.Date;

import com.devoteam.tracker.util.ServletConstants;

public class SNRTotalsReportItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5074455541752689129L;
	private String customerName;
	private long totalSNRs;
	private long totalRequested;
	private long totalRejected;
	private long totalAwaitingScheduling;
	private long totalScheduled;
	private long totalCompleted;
	private long totalCancelled;
	private long totalOnHold;
	private long totalClosed;
	
	private final static String[] columnTitles = {"Customer Name", "Total SNRs", 
		"Total Requested", "Total Rejected", "Total Awaiting Scheduling", 
		"Total Scheduled", "Total Completed", "Total Cancelled", 
		"Total On Hold", "Total Closed"};
	
	public SNRTotalsReportItem(String customerName, long totalSNRs,
			long totalRequested, long totalRejected, long totalAwaitingScheduling, 
			long totalScheduled, long totalCompleted, long totalCancelled,
			long totalOnHold, long totalClosed) {
		this.customerName = customerName;
		this.totalSNRs = totalSNRs;
		this.totalRequested = totalRequested;
		this.totalRejected = totalRejected;
		this.totalAwaitingScheduling = totalAwaitingScheduling;
		this.totalScheduled = totalScheduled;
		this.totalCompleted = totalCompleted;
		this.totalCancelled = totalCancelled;
		this.totalOnHold = totalOnHold;
		this.totalClosed = totalClosed;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public long getTotalSNRs() {
		return totalSNRs;
	}
	
	public long getTotalRequested() {
		return totalRequested;
	}
	
	public long getTotalRejected() {
		return totalRejected;
	}
	
	public long getTotalAwaitingScheduling() {
		return totalAwaitingScheduling;
	}
	
	public long getTotalScheduled() {
		return totalScheduled;
	}
	
	public long getTotalCompleted() {
		return totalCompleted;
	}
	
	public long getTotalCancelled() {
		return totalCancelled;
	}
	
	public long getTotalOnHold() {
		return totalOnHold;
	}
	
	public long getTotalClosed() {
		return totalClosed;
	}
	
	public String[] getValueArray() {
		String[] values = {customerName,
				Long.toString(totalSNRs), Long.toString(totalRequested),  
				Long.toString(totalRejected), Long.toString(totalAwaitingScheduling),  
				Long.toString(totalScheduled), Long.toString(totalCompleted),  
				Long.toString(totalCancelled), Long.toString(totalOnHold), 
				Long.toString(totalClosed)};
		return values;
	}
	
	public static String[] getColumnTitles() {
		return columnTitles;
	}
	
}
