package com.devoteam.tracker.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class PMOAbort {
	private long snrId;
	private String abortType;
	private Timestamp historyDT;
	private Date scheduledDate;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public PMOAbort(long snrId, String abortType, 
			Timestamp historyDT, Date scheduledDate) {
		this.snrId = snrId;
		this.abortType = abortType;
		this.historyDT = historyDT;
		this.scheduledDate = scheduledDate;
	}

	public long getSNRId() {
		return snrId;
	}

	public String getAbortType() {
		return abortType;
	}
	
	public Timestamp getHistoryDT() {
		return historyDT;
	}
	
	public String getHistoryDateString() {
		return historyDT==null?"":dateFormatter.format(new java.util.Date(historyDT.getTime()));
	}
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getScheduledDateString() {
		return scheduledDate==null?"":dateFormatter.format(scheduledDate);
	}

	public String[] getListValueArray() {
		String[] values = {this.getAbortType(), this.getHistoryDateString(),
			this.getScheduledDateString()};
		return values;
	}
}
