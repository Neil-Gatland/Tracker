package com.devoteam.tracker.model;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.sql.Date;

import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.StringUtil;

public class SNRListItem {
	private long snrId;
	private long customerId;
	private String customerName;
	private long potId;
	private String potName;
	private String site;
	private String nrId;
	private String status;
	private String implementationStatus;
	//private String preCheckOutstanding;
	private String abortType;
	private String historyInd;
	private String technologies;
	private String fieldEngineers;
	private String boEngineers;
	private String closeCRQ;
	private int commentaryCount;
	private String previousImplementation;
	private String hopStatus;
	private Timestamp historyDT;
	private Date scheduledDate;
	private Date cancelledDate;
	private Date previousScheduledDate;
	private String previousStatus;
	private String nextPrecheck;
	private String jobType;
	private String crqINCRaised;
	private String crqINCReference;
	private String crqStatus;
	private String siteAccessConfirmed;
	private String accessStatus;
	private Timestamp ef345ClaimDT;
	private Timestamp ef360ClaimDT;
	private Timestamp ef390ClaimDT;
	private Timestamp ef400ClaimDT;
	private Timestamp ef410ClaimDT;
	private Timestamp implementationStartDT;
	private Timestamp implementationEndDT;
	private double accessCost;
	private double consumableCost; 
	private String twoManSite;
	private String oohWeekendInd;
	private String hardwareVendor;
	private String upgradeType;
	private String FE1;
	private String FE2;
	private String CRQ;
	private String TEF;
	private int siteVisits;
	private String sfrStatus;
	private String feList;
	private String progressIncomplete;

	private final String STATUS_A_S = ServletConstants.STATUS_AWAITING_SCHEDULING;
	private final String STATUS_A_S_SHORT = "Awaiting Sch.";
	private final String DUMMY = "DUMMY";
	public final String DUMMYNR = "*"+DUMMY+"*";
	public final String NPC_SHORT = "NPC_SHORT";
	public final String NPC_ALL_DONE = "* All done";
	public final String NPC_DETAILED = "Detailed";
	public final String NPC_FINAL = "Final";
	public final String CS_SHORT = "CS_SHORT";
	public final String BOE_SHORT = "BOE_SHORT";
	public final String FE_SHORT = "FE_SHORT";
	public final String TECH_SHORT = "TECH_SHORT";
	public final String UPDATE_SD = "UPDATE_SD";
	public final String EF_DATE_SET = "EF_DATE_SET";
	public final String ASTERISK = "*";
	private final SimpleDateFormat tsFormatter = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.UK);
	
	public SNRListItem(long snrId, String site, String nrId, Date cancelledDate, 
			String previousStatus, Date previousScheduledDate) {
		this.snrId = snrId;
		this.site = site;
		this.nrId = nrId;
		this.cancelledDate = cancelledDate;
		this.previousStatus = previousStatus;
		this.previousScheduledDate = previousScheduledDate;
	}
	
	public SNRListItem(long snrId, long customerId, String customerName, 
			long potId,	String potName, String site, String nrId, String status, 
			String implementationStatus) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.potId = potId;
		this.potName = potName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.implementationStatus = implementationStatus;
		this.closeCRQ = "N";
		this.commentaryCount = 0;
		this.previousImplementation = "N";
		this.historyDT = null;
		this.scheduledDate = null;
	}
	
	public SNRListItem(long snrId, long customerId, String customerName, 
			long potId,	String potName, String site, String nrId, String status, 
			String implementationStatus, 
			String technologies, String fieldEngineers) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.potId = potId;
		this.potName = potName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.implementationStatus = implementationStatus;
		this.technologies = technologies;
		this.fieldEngineers = fieldEngineers;
		this.closeCRQ = "N";
		this.commentaryCount = 0;
		this.previousImplementation = "N";
		this.historyDT = null;
		this.scheduledDate = null;
	}
	
	public SNRListItem(long snrId, long customerId, String customerName, 
			long potId,	String potName, String site, String nrId, String status, 
			String implementationStatus, 
			String technologies, String fieldEngineers, String closeCRQ,
			int commentaryCount, String previousImplementation) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.potId = potId;
		this.potName = potName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.implementationStatus = implementationStatus;
		this.technologies = technologies;
		this.fieldEngineers = fieldEngineers;
		this.closeCRQ = closeCRQ;
		this.commentaryCount = commentaryCount;
		this.previousImplementation = previousImplementation;
		this.historyDT = null;
		this.scheduledDate = null;
	}
	
	public SNRListItem(long snrId, long customerId, String customerName, 
			long potId,	String potName, String site, String nrId, String status, 
			String implementationStatus, 
			String technologies, String fieldEngineers, String closeCRQ,
			int commentaryCount, String previousImplementation,
			Date scheduledDate, String hopStatus, String nextPrecheck, 
			String jobType, String crqINCRaised, String siteAccessConfirmed,
			String crqINCReference, String accessStatus,
			String boEngineers, String crqStatus) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.potId = potId;
		this.potName = potName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.implementationStatus = implementationStatus;
		this.technologies = technologies;
		this.fieldEngineers = fieldEngineers;
		this.closeCRQ = closeCRQ;
		this.commentaryCount = commentaryCount;
		this.previousImplementation = previousImplementation;
		this.historyDT = null;
		this.scheduledDate = scheduledDate;
		this.hopStatus = hopStatus;
		this.nextPrecheck = nextPrecheck;
		this.jobType = jobType;
		this.crqINCRaised = crqINCRaised;
		this.siteAccessConfirmed = siteAccessConfirmed;
		this.crqINCReference = crqINCReference;
		this.accessStatus = accessStatus;
		this.boEngineers = boEngineers;
		this.crqStatus = crqStatus;
	}
	
	public SNRListItem(long snrId, long customerId, String customerName, 
			long potId,	String potName, String site, String nrId, String status, 
			String implementationStatus, 
			String technologies, String fieldEngineers, String closeCRQ,
			int commentaryCount, String previousImplementation,
			Date scheduledDate, String hopStatus, String nextPrecheck, 
			String jobType, String crqINCRaised, String siteAccessConfirmed,
			String crqINCReference, String accessStatus, Timestamp ef345ClaimDT, Timestamp ef360ClaimDT,
			Timestamp ef390ClaimDT, Timestamp ef400ClaimDT, Timestamp ef410ClaimDT,
			String boEngineers, String crqStatus) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.potId = potId;
		this.potName = potName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.implementationStatus = implementationStatus;
		this.technologies = technologies;
		this.fieldEngineers = fieldEngineers;
		this.closeCRQ = closeCRQ;
		this.commentaryCount = commentaryCount;
		this.previousImplementation = previousImplementation;
		this.historyDT = null;
		this.scheduledDate = scheduledDate;
		this.hopStatus = hopStatus;
		this.nextPrecheck = nextPrecheck;
		this.jobType = jobType;
		this.crqINCRaised = crqINCRaised;
		this.siteAccessConfirmed = siteAccessConfirmed;
		this.crqINCReference = crqINCReference;
		this.accessStatus = accessStatus;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef390ClaimDT = ef390ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
		this.boEngineers = boEngineers;
		this.crqStatus = crqStatus;
	}
	
	public SNRListItem(long snrId, long customerId, String customerName, 
			long potId,	String potName, String site, String nrId, String status, 
			String implementationStatus,
			String technologies, String fieldEngineers, String closeCRQ,
			int commentaryCount, String previousImplementation,
			Date scheduledDate, String hopStatus, String nextPrecheck, 
			String jobType, String crqINCRaised, String siteAccessConfirmed,
			String crqINCReference, Timestamp ef345ClaimDT, Timestamp ef360ClaimDT,
			Timestamp ef390ClaimDT, Timestamp ef400ClaimDT, Timestamp ef410ClaimDT) {
		this.snrId = snrId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.potId = potId;
		this.potName = potName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.implementationStatus = implementationStatus;
		this.technologies = technologies;
		this.fieldEngineers = fieldEngineers;
		this.closeCRQ = closeCRQ;
		this.commentaryCount = commentaryCount;
		this.previousImplementation = previousImplementation;
		this.historyDT = null;
		this.scheduledDate = scheduledDate;
		this.hopStatus = hopStatus;
		this.nextPrecheck = nextPrecheck;
		this.jobType = jobType;
		this.crqINCRaised = crqINCRaised;
		this.siteAccessConfirmed = siteAccessConfirmed;
		this.crqINCReference = crqINCReference;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef390ClaimDT = ef390ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
	}
	
	public SNRListItem(long snrId, String customerName, long potId,
			String potName, String site, String nrId, String status, 
			String implementationStatus,
			String abortType, String historyInd, int commentaryCount,
			Date scheduledDate, Timestamp historyDT) {
		this.snrId = snrId;
		this.customerName = customerName;
		this.potId = potId;
		this.potName = potName;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.implementationStatus = implementationStatus;
		this.abortType = abortType;
		this.historyInd = historyInd;
		this.closeCRQ = "N";
		this.commentaryCount = commentaryCount;
		this.previousImplementation = "N";
		this.historyDT = historyDT;
		this.scheduledDate = scheduledDate;
	}

	public SNRListItem(long snrId, String site, String nrId, 
			Date scheduledDate, Timestamp implementationStartDT,
			Timestamp implementationEndDT, String status) {
		this.snrId = snrId;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.implementationStartDT = implementationStartDT;
		this.implementationEndDT = implementationEndDT;
		this.scheduledDate = scheduledDate;
	}

	public SNRListItem(long snrId, String site, String nrId, 
			Date scheduledDate, Timestamp implementationStartDT,
			Timestamp implementationEndDT, String status, 
			double accessCost, double consumableCost, 
			String twoManSite, String oohWeekendInd) {
		this.snrId = snrId;
		this.site = site;
		this.nrId = nrId;
		this.status = status;
		this.implementationStartDT = implementationStartDT;
		this.implementationEndDT = implementationEndDT;
		this.scheduledDate = scheduledDate;
		this.accessCost = accessCost;
		this.consumableCost = consumableCost;
		this.twoManSite = twoManSite;
		this.oohWeekendInd = oohWeekendInd;
	}

	public long getSNRId() {
		return snrId;
	}
	
	public long getCustomerId() {
		return customerId;
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
	
	public String getNRIdDisplay() {
		return nrId.contains(DUMMY)?DUMMYNR:nrId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getPreviousStatus() {
		return previousStatus;
	}
	
	public String getStatusDisplay() {
		return status.equalsIgnoreCase(STATUS_A_S)?STATUS_A_S_SHORT:status;
	}
	
	public String getImplementationStatus() {
		return implementationStatus;
	}
	
	public String getAbortType() {
		return abortType;
	}
	
	public String getHistoryInd() {
		return historyInd;
	}
	
	public boolean isHistory() {
		return historyInd.equalsIgnoreCase("Y");
	}
	
	public String getTechnologies() {
		return technologies;
	}
	
	public String getTechnologiesDisplay() {
		return technologies==null?"":technologies.length()>50?TECH_SHORT:technologies;
	}
	
	public String getTechnologiesShort() {
		return getAbbreviation(technologies);
	}
	
	public String getFieldEngineers() {
		return fieldEngineers;
	}
	
	public String getFieldEngineersDisplay() {
		return fieldEngineers==null?"":fieldEngineers.length()>50?FE_SHORT:fieldEngineers;
	}
	
	public String getFieldEngineersShort() {
		return getAbbreviation(fieldEngineers);
	}
	
	public String getBOEngineers() {
		return boEngineers;
	}
	
	public String getBOEngineersDisplay() {
		return boEngineers==null?"":boEngineers.length()>50?BOE_SHORT:boEngineers;
	}
	
	public String getBOEngineersShort() {
		return getAbbreviation(boEngineers);
	}
	
	private String getAbbreviation(String input) {
		if (StringUtil.hasNoValue(input)) {
			return "";
		} else if (input.length() > 50) {
			String abbr = input.substring(0, 49);
			if (abbr.lastIndexOf(",") > 0) {
				abbr = abbr.substring(0, abbr.lastIndexOf(","));
			}
			return abbr + "<font color=\"red\"><b>+</b></font>";
		} else {
			return input;
		}
	}
	
	public String getCloseCRQ() {
		return closeCRQ;
	}
	
	public boolean closeCRQ() {
		return closeCRQ.equalsIgnoreCase("Y");
	}
	
	public boolean hasNextPreCheck() {
		return !StringUtil.hasNoValue(nextPrecheck) &&
				status.equals(ServletConstants.STATUS_SCHEDULED);
	}
	
	public boolean flagCRQRaised() {
		return crqINCRaised.equalsIgnoreCase("N") &&
				status.equals(ServletConstants.STATUS_SCHEDULED);
	}
	
	public boolean isOverdue() {
		if ((scheduledDate == null) || (status.equals(ServletConstants.STATUS_CANCELLED))
				|| (status.equals(ServletConstants.STATUS_CLOSED))
				|| (status.equals(ServletConstants.STATUS_COMPLETED))) {
			return false;
		} else {
			return scheduledDate.before(new java.util.Date());
		}
	}
	
	public int getCommentaryCount() {
		return commentaryCount;
	}
	
	public String getCommentaryCountString() {
		return Integer.toString(commentaryCount);
	}
	
	public String getPreviousImplementation() {
		return previousImplementation;
	}
	
	public String getHOPStatus() {
		return hopStatus;
	}
	
	public String getNextPrecheck() {
		return nextPrecheck==null?"":nextPrecheck;
	}
	
	public String getNextPrecheckDisplay() {
		return nextPrecheck==null?"":nextPrecheck.substring(0, 1);
	}
	
	public String getJobType() {
		return jobType;
	}
	
	public String getCRQINCRaised() {
		return crqINCRaised;
	}
	
	public String getCRQINCReference() {
		return crqINCReference;
	}
	
	public String getCRQStatus() {
		return crqStatus==null?"":crqStatus;
	}
	
	public String getCRQStatusDisplay() {
		if (StringUtil.hasNoValue(crqStatus)) {
			return "";
		} else {
			StringBuilder initial = new StringBuilder();
			String[] split = crqStatus.split(" ");
			for(String value : split){
				initial.append(value.substring(0,1));
			}
			return initial.toString();
		}
	}
	
	public String getSiteAccessConfirmed() {
		return siteAccessConfirmed;
	}
	
	public Timestamp getHistoryDT() {
		return historyDT;
	}
	
	public long getHistoryDTMs() {
		return historyDT==null?0:historyDT.getTime();
	}
	
	public String getHistoryDTString() {
		return historyDT==null?"":tsFormatter.format(new java.util.Date(historyDT.getTime()));
	}
	
	public String getHistoryDateString() {
		return historyDT==null?"":historyInd.equalsIgnoreCase("Y")?
				dateFormatter.format(new java.util.Date(historyDT.getTime())):"";
	}
	
	public String getImplementationStartDTString() {
		return implementationStartDT==null?"":tsFormatter.format(new java.util.Date(implementationStartDT.getTime()));
	}
	
	public String getImplementationEndDTString() {
		return implementationEndDT==null?"":tsFormatter.format(new java.util.Date(implementationEndDT.getTime()));
	}
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getScheduledDateString() {
		return scheduledDate==null?"":dateFormatter.format(scheduledDate);
	}
	
	public Date getPreviousScheduledDate() {
		return previousScheduledDate;
	}
	
	public String getPreviousScheduledDateString() {
		return previousScheduledDate==null?"":dateFormatter.format(previousScheduledDate);
	}
	
	public Date getCancelledDate() {
		return cancelledDate;
	}
	
	public String getCancelledDateString() {
		return cancelledDate==null?"":dateFormatter.format(cancelledDate);
	}
	
	public String getScheduledDDMM() {
		return scheduledDate==null?"":dateFormatter.format(scheduledDate).substring(0, 5);
	}
	
	public String getEF345ClaimDTSet() {
		//ef345ClaimDT = new Timestamp((new java.util.Date()).getTime());
		return ef345ClaimDT==null?"":(EF_DATE_SET + "_0");
	}
	
	public String getEF345ClaimDTTitle() {
		return ef345ClaimDT==null?"":("EF345 Claim DT - " + dateFormatter.format(ef345ClaimDT));
	}
	
	public String getEF360ClaimDTSet() {
		return ef360ClaimDT==null?"":(EF_DATE_SET + "_1");
	}
	
	public String getEF360ClaimDTTitle() {
		return ef360ClaimDT==null?"":("EF360 Claim DT - " + dateFormatter.format(ef360ClaimDT));
	}
	
	public String getEF390ClaimDTSet() {
		return ef390ClaimDT==null?"":(EF_DATE_SET + "_2");
	}
	
	public String getEF390ClaimDTTitle() {
		return ef390ClaimDT==null?"":("EF390 Claim DT - " + dateFormatter.format(ef390ClaimDT));
	}
	
	public String getEF400ClaimDTSet() {
		return ef400ClaimDT==null?"":(EF_DATE_SET + "_3");
	}
	
	public String getEF400ClaimDTTitle() {
		return ef400ClaimDT==null?"":("EF400 Claim DT - " + dateFormatter.format(ef400ClaimDT));
	}
	
	public String getEF410ClaimDTSet() {
		return ef410ClaimDT==null?"":(EF_DATE_SET + "_4");
	}
	
	public String getEF410ClaimDTTitle() {
		return ef410ClaimDT==null?"":("EF410 Claim DT - " + dateFormatter.format(ef410ClaimDT));
	}
	
	public double getAccessCost() {
		return accessCost;
	}
	
	public String getAccessCostString() {
		return currencyFormatter.format(accessCost);
	}
	
	public double getConsumableCost() {
		return consumableCost;
	}
	
	public String getConsumableCostString() {
		return currencyFormatter.format(consumableCost);
	}
	
	public String getOOHWeekendInd() {
		return oohWeekendInd==null?"":oohWeekendInd;
	}
	
	public String getTwoManSite() {
		return twoManSite==null?"":twoManSite;
	}
	
	public String getEFClaimDTTitle(String id) {
		String title = "unknown";
		String[] values = {this.getEF345ClaimDTTitle(),
				this.getEF360ClaimDTTitle(), this.getEF390ClaimDTTitle(), 
				this.getEF400ClaimDTTitle(), this.getEF410ClaimDTTitle()};
		try {
			int i = Integer.parseInt(id.substring(id.lastIndexOf("_") + 1));
			title = values[i];
		} catch(Exception ex) {
			//ignore it
		} 
		return title;
	}
	
	public String[] getEFTitleArray() {
		String[] values = {this.getEF345ClaimDTTitle(),
				this.getEF360ClaimDTTitle(), this.getEF390ClaimDTTitle(), 
				this.getEF400ClaimDTTitle(), this.getEF410ClaimDTTitle()};
		return values;
	}
	
	public boolean getEFComplete() {
		return ef345ClaimDT!=null&&ef360ClaimDT!=null&&ef390ClaimDT!=null&&ef400ClaimDT!=null&&ef410ClaimDT!=null;
	}
		
	public String[] getSummaryValueArray() {
		String[] values = {site, this.getNRIdDisplay(), this.getStatusDisplay(), 
				implementationStatus, jobType, this.getScheduledDateString(), 
				previousImplementation, this.getCommentaryCountString(), 
				NPC_SHORT, crqINCRaised, siteAccessConfirmed, hopStatus, 
				this.getEF345ClaimDTSet(),
				this.getEF360ClaimDTSet(), this.getEF390ClaimDTSet(), 
				this.getEF400ClaimDTSet(), this.getEF410ClaimDTSet()};
		return values;
	}
	
	public String[] getConfirmValueArray() {
		String[] values = {site, nrId, status, implementationStatus, 
				getBOEngineersDisplay(),
				previousImplementation, this.getCommentaryCountString(), 
				NPC_SHORT,	hopStatus, this.getScheduledDateString()};
		return values;
	}
	
	public String[] getMultiValueArray(String status2) {
		return status2.equalsIgnoreCase(STATUS_A_S)
				?getMultiValueArray(false)
				:getConfirmValueArray();
		
	}
	
	public String[] getMultiValueArray(boolean updateSD) {
		String[] values = {site, nrId, status, implementationStatus, 
				previousImplementation, this.getCommentaryCountString(), 
				NPC_SHORT,	hopStatus, updateSD?UPDATE_SD:this.getScheduledDateString()};
		return values;
	}
	
/*	public String[] getUpdateValueArray() {
		String[] values = {Long.toString(snrId), customerName,
			site, this.getNRIdDisplay(), 
			this.getStatusDisplay(), 
			implementationStatus, preCheckOutstanding, technologies,
			fieldEngineers, this.getScheduledDateString()};
		return values;
	}
*/	
	public String[] getUpdateValueArray(String title) {
		return title.equals(ServletConstants.UPDATE_ACCESS)
				?this.getUpdateAccessValueArray()
				:this.getUpdateCRMValueArray();
	}
	
	public String[] getUpdateCRMValueArray() {
		String[] values = {site, this.getNRIdDisplay(), this.getStatusDisplay(), 
			implementationStatus, crqINCReference, jobType, 
			CS_SHORT, 
			getTechnologiesDisplay(), this.getScheduledDateString()};
		return values;
	}
	
	public String[] getUpdateAccessValueArray() {
		String[] values = {site, this.getNRIdDisplay(), this.getStatusDisplay(), 
			implementationStatus, accessStatus, jobType, 
			NPC_SHORT, getFieldEngineersDisplay(), this.getScheduledDateString()};
		return values;
	}
	
	public String[] getHistoryValueArray() {
		String[] values = {site, nrId, status, implementationStatus, 
				this.getCommentaryCountString(),
				abortType.length()>1?abortType.substring(0, 1):abortType, 
				historyInd, this.getScheduledDateString(), 
				this.getHistoryDateString()};
		return values;
	}
	
	public String[] getPMOValueArray() {
		String[] values = {site, nrId, status, 
				this.getScheduledDateString(), 
				this.getImplementationStartDTString(),
				this.getImplementationEndDTString()};
		return values;
	}
	
	public String[] getCancelledValueArray() {
		String[] values = {site, nrId, this.getCancelledDateString(), 
				previousStatus, this.getPreviousScheduledDateString()};
		return values;
	}
	
	public String getWorkQueuesItemCS() {
		String cS = "";
		if (siteAccessConfirmed.equalsIgnoreCase("Y") && 
				crqINCRaised.equalsIgnoreCase("Y") &&
				this.getNextPrecheck().equalsIgnoreCase(NPC_ALL_DONE)) {
			cS = "gg";
		} else if (status.equals(ServletConstants.STATUS_REJECTED) ||
				status.equals(ServletConstants.STATUS_CLOSED) ||
				status.equals(ServletConstants.STATUS_CANCELLED)) {
			cS = "gr";
		} else if (this.getNextPrecheck().equalsIgnoreCase(NPC_DETAILED) ||
				this.getNextPrecheck().equalsIgnoreCase(NPC_FINAL)) {
			cS = "r";
		}
		return cS;
	}


	public String getHardwareVendor() {
		return hardwareVendor==null?" ":hardwareVendor;
	}
	
	public String getUpgradeType() {
		return upgradeType;
	}
	
	public String getFE1() {
		return FE1;
	}
	
	public String getFE2() {
		return FE2;
	}
	
	public String getCRQ() {
		return CRQ;
	}
	
	public String getTEF() {
		return TEF;
	}
	
	public String getSiteVisits() {
		return String.valueOf(siteVisits);
	}

	public SNRListItem(long snrId, Date scheduledDate, String status,
			String site, String nrId, String jobType,  
			String technologies, String hardwareVendor, String upgradeType, 
			String FE1,	String FE2, 
			String CRQ, String TEF, String accessStatus, String boEngineers, 
			int siteVisits, String customerName, String feList) {
		this.snrId = snrId;
		this.scheduledDate = scheduledDate;
		this.status = status;
		this.site = site;
		this.nrId = nrId;
		this.jobType = jobType;
		this.upgradeType = upgradeType;
		this.technologies = technologies;
		this.hardwareVendor = hardwareVendor;
		this.FE1 = FE1;
		this.FE2 = FE2;
		this.CRQ = CRQ;
		this.TEF = TEF;
		this.accessStatus = accessStatus;
		this.boEngineers = boEngineers;
		this.siteVisits = siteVisits;
		//this.customerName = customerName;
		this.feList = feList;
	}	

	/*public String[] getDailySiteScheduleArray() {
		String[] values = {site, this.getNRIdDisplay(), status,
				jobType, upgradeType,  getTechnologiesDisplay(), hardwareVendor,
				FE1, FE2, CRQ, TEF,	accessStatus, getBOEngineersDisplay(), 
				getSiteVisits(), customerName};
		return values;
	}*/

	/*public String[] getDailySiteScheduleArray() {
		String[] values = {site, this.getNRIdDisplay(), status,
				jobType, upgradeType,  getTechnologies(), getHardwareVendor(),
				FE1, FE2, CRQ, TEF,	accessStatus, getBOEngineersDisplay(), 
				getSiteVisits()};
		return values;
	}*/

	public String[] getDailySiteScheduleArray() {
		String[] values = {site, this.getNRIdDisplay(), status,
				jobType, upgradeType,  getTechnologies(), getHardwareVendor(),
				feList, CRQ, TEF,	accessStatus, getBOEngineersDisplay(), 
				getSiteVisits()};
		return values;
	}
	
	public String getHopStatus() {
		return hopStatus;
	}
	
	public String getSfrStatus() {
		return sfrStatus;
	}
	 
	public String getEFOs() {
		String efClaimsOS = "N";
		if ((ef345ClaimDT==null)||
				(ef360ClaimDT==null)||
				(ef390ClaimDT==null)||
				(ef400ClaimDT==null)||
				(ef410ClaimDT==null)) 
			efClaimsOS = "Y"; 
		return efClaimsOS;
	}
	 
	public String getPCOs() {
		String pcOS = "Y";
		if (nextPrecheck.startsWith("*")) 
			pcOS = "N"; 
		return pcOS;
	} 
	
	public String getProgressIncomplete() {
		return progressIncomplete;
	}

	public SNRListItem(long snrId, Date scheduledDate, String status,
			String site, String nrId, String jobType,  
			String technologies, String hardwareVendor,  String boEngineers, 
			Timestamp ef345ClaimDT, Timestamp ef360ClaimDT, Timestamp ef390ClaimDT, 
			Timestamp ef400ClaimDT, Timestamp ef410ClaimDT, String hopStatus,
			String sfrStatus, String nextPrecheck, String customerName,
			String progressIncomplete) {
		this.snrId = snrId;
		this.scheduledDate = scheduledDate;
		this.status = status;
		this.site = site;
		this.nrId = nrId;
		this.jobType = jobType;
		this.technologies = technologies;
		this.hardwareVendor = hardwareVendor;
		this.boEngineers = boEngineers;
		this.ef345ClaimDT = ef345ClaimDT;
		this.ef360ClaimDT = ef360ClaimDT;
		this.ef390ClaimDT = ef390ClaimDT;
		this.ef400ClaimDT = ef400ClaimDT;
		this.ef410ClaimDT = ef410ClaimDT;
		this.hopStatus = hopStatus;
		this.sfrStatus = sfrStatus;
		this.nextPrecheck = nextPrecheck;
		//this.customerName = customerName;
		this.progressIncomplete = progressIncomplete;
	}	

	public String[] getOutstandingWorksArray() {
		String[] values = {site, this.getNRIdDisplay(), status,
				jobType,  getScheduledDateString(), getTechnologies(), getHardwareVendor(),
				getBOEngineersDisplay(), this.getEF345ClaimDTSet(),
				this.getEF360ClaimDTSet(), this.getEF390ClaimDTSet(), 
				this.getEF400ClaimDTSet(), this.getEF410ClaimDTSet(),
				hopStatus, sfrStatus, NPC_SHORT, progressIncomplete};
				//hopStatus, sfrStatus, NPC_SHORT};
		return values;
	}		
	

	/*public String[] getWeeklyScheduleArray() {
		String[] values = {site, this.getNRIdDisplay(), status,
				jobType, getScheduledDateString(), upgradeType,  getTechnologies(), 
				getHardwareVendor(),FE1, FE2, CRQ, TEF,	accessStatus, 
				getBOEngineersDisplay(), getSiteVisits(), customerName};
		return values;
	}*/

	/*public String[] getWeeklyScheduleArray() {
		String[] values = {site, this.getNRIdDisplay(), status,
				jobType, getScheduledDateString(), upgradeType,  getTechnologies(), 
				getHardwareVendor(),FE1, FE2, CRQ, TEF,	accessStatus, 
				getBOEngineersDisplay(), getSiteVisits()};
		return values;
	}*/

	public String[] getWeeklyScheduleArray() {
		String[] values = {site, this.getNRIdDisplay(), status,
				jobType, getScheduledDateString(), upgradeType,  getTechnologies(), 
				getHardwareVendor(),feList, CRQ, TEF,	accessStatus, 
				getBOEngineersDisplay(), getSiteVisits()};
		return values;
	}
	
}
