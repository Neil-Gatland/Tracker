package com.devoteam.tracker.model;

import java.util.ArrayList;
import java.util.Collection;

public class Pot {
	private long potId;
	private String potName;
	private long customerId;
	private String customerName;
	private String jobType;
	private String projectRequestor;
	private ArrayList<SNR> snrDataRows;
	private ArrayList<Long> snrIds;

	public Pot(long potId, String potName, long customerId, String customerName,
			String jobType, String projectRequestor) {
		this.potId = potId;
		this.potName = potName;
		this.customerId = customerId;
		this.customerName =customerName;
		this.jobType = jobType;
		this.projectRequestor = projectRequestor;
		this.snrDataRows = new ArrayList<SNR>(); 
		this.snrIds = new ArrayList<Long>(); 
	}
	
	public void addSNRId(long snrId) {
		this.snrIds.add(new Long(snrId));
	}
	
	public long[] getSNRIds() {
		long[] primitives = new long[snrIds.size()];
		for (int i = 0; i < snrIds.size(); i++) {
			primitives[i] = snrIds.get(i).longValue();
		}
		return primitives;
	}
	
	public void addSNRDataRow(SNR snrDataRow) {
		this.snrDataRows.add(snrDataRow);
	}
	
	public void setSNRDataRows(ArrayList<SNR> snrDataRows) {
		this.snrDataRows = snrDataRows;
	}
	
	public Collection<SNR> getSNRDataRows() {
		return snrDataRows;
	}
	
	public long getPotId() {
		return potId;
	}
	
	public String getPotName() {
		return potName;
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
	
	public String getProjectRequestor() {
		return projectRequestor;
	}
}
