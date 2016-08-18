package com.devoteam.tracker.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class PreCheck {
	private long preCheckId;
	private long customerId;
	private String preCheckType;
	private long snrId;
	private Date preCheckScheduledDT;
	private Date preCheckActualDT;
	private String preCheckStatus;
	private String completionStatus;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

	public PreCheck(long preCheckId, long customerId, String preCheckType, 
			long snrId, Date preCheckScheduledDT, Date preCheckActualDT, 
			String preCheckStatus, String completionStatus, Date lastUpdatedDate,
			String lastUpdatedBy) {
		this.preCheckId = preCheckId;
		this.customerId = customerId;
		this.preCheckType = preCheckType;
		this.snrId = snrId;
		this.preCheckScheduledDT = preCheckScheduledDT;
		this.preCheckActualDT = preCheckActualDT;
		this.preCheckStatus = preCheckStatus;
		this.completionStatus = completionStatus;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public long getPreCheckId() {
		return preCheckId;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public String getPreCheckType() {
		return preCheckType;
	}
	
	public long getSNRId() {
		return snrId;
	}
	
	public Date getPreCheckScheduledDT() {
		return preCheckScheduledDT;
	}
	
	public String getPreCheckScheduledDTString() {
		return preCheckScheduledDT==null?"":dateFormatter.format(preCheckScheduledDT);
	}

	public Date getPreCheckActualDT() {
		return preCheckActualDT;
	}
	
	public String getPreCheckActualDTString() {
		return preCheckActualDT==null?"":dateFormatter.format(preCheckActualDT);
	}

	public String getPreCheckStatus() {
		return preCheckStatus;
	}
	
	public String getCompletionStatus() {
		return completionStatus;
	}
	
	public String getCompletionStatusDisplay() {
		return completionStatus==null?"":completionStatus;
	}
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public String getLastUpdatedDateString() {
		return lastUpdatedDate==null?"":dateFormatter.format(lastUpdatedDate);
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public String[] getListValueArray() {
		String[] values = {Long.toString(snrId), Long.toString(preCheckId), 
			this.getPreCheckType(), this.getPreCheckScheduledDTString(),
			this.getPreCheckActualDTString(), this.getPreCheckStatus(),
			this.getCompletionStatusDisplay(), this.getLastUpdatedDateString(), 
			this.getLastUpdatedBy()};
		return values;
	}
}
