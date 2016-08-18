package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.StringUtil;

public class PreCheckOutstanding implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -613758158831090614L;
	private long preCheckId;
	private long snrId;
	private String nrId;
	private String preCheckType;
	private String jobType;
	private long preCheckDays;
	private Date preCheckScheduledDT;
	private String preCheckStatus;
	private String overdue;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private String site;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

	public PreCheckOutstanding(long preCheckId, long snrId, String nrId, 
			String preCheckType, long preCheckDays, Date preCheckScheduledDT,  
			String preCheckStatus, String overdue, Date lastUpdatedDate,
			String lastUpdatedBy, String jobType) {
		this.preCheckId = preCheckId;
		this.snrId = snrId;
		this.nrId = nrId;
		this.preCheckType = preCheckType;
		this.preCheckDays = preCheckDays;
		this.preCheckScheduledDT = preCheckScheduledDT;
		this.preCheckStatus = preCheckStatus;
		this.overdue = overdue;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.jobType = jobType;
	}

	public PreCheckOutstanding(long preCheckId, long snrId, String nrId, 
			String preCheckType, long preCheckDays, Date preCheckScheduledDT,  
			String preCheckStatus, String overdue, Date lastUpdatedDate,
			String lastUpdatedBy, String jobType, String site) {
		this.preCheckId = preCheckId;
		this.snrId = snrId;
		this.nrId = nrId;
		this.preCheckType = preCheckType;
		this.preCheckDays = preCheckDays;
		this.preCheckScheduledDT = preCheckScheduledDT;
		this.preCheckStatus = preCheckStatus;
		this.overdue = overdue;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.jobType = jobType;
		this.site = site;
	}
	
	public long getPreCheckId() {
		return preCheckId;
	}
	
	public long getSNRId() {
		return snrId;
	}
	
	public String getNRId() {
		return nrId;
	}
	
	public String getSite() {
		return site;
	}
	
	public String getPreCheckType() {
		return preCheckType;
	}
	
	public String getJobType() {
		return jobType;
	}
	
	public long getPreCheckDays() {
		return preCheckDays;
	}
	
	public String getPreCheckDaysString() {
		return preCheckDays<=0?"":Long.toString(preCheckDays);
	}
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	
	public String getLastUpdatedDateString() {
		return lastUpdatedDate==null?"":dateFormatter.format(lastUpdatedDate);
	}

	public String getPreCheckStatus() {
		return preCheckStatus;
	}
	
	public boolean isIncomplete() {
		return ((!StringUtil.hasNoValue(preCheckStatus)) &&
				((preCheckStatus.equalsIgnoreCase(ServletConstants.PRE_CHECK_STATUS_NOT_STARTED)) ||
						(preCheckStatus.equalsIgnoreCase(ServletConstants.PRE_CHECK_STATUS_IN_PROGRESS))));
	}
	
	public String getOverdue() {
		return overdue;
	}
	
	public boolean isOverdue() {
		return overdue.equalsIgnoreCase("Y");
	}
	
	public Date getPreCheckScheduledDT() {
		return preCheckScheduledDT;
	}
	
	public String getPreCheckScheduledDTString() {
		return preCheckScheduledDT==null?"":dateFormatter.format(preCheckScheduledDT);
	}
	
	public String[] getBatchListValueArray() {
		String[] values = {this.getNRId(), this.getPreCheckScheduledDTString()};
		return values;
	}
	
	public String[] getListValueArray() {
		String[] values = {this.getNRId(), this.getPreCheckType(), this.getPreCheckDaysString(), 
			this.getPreCheckScheduledDTString(), this.getPreCheckStatus(), this.getJobType(),
			lastUpdatedBy, this.getLastUpdatedDateString()};
		return values;
	}
}
