package com.devoteam.tracker.model;

import com.devoteam.tracker.util.StringUtil;

public class crqAccessHeader {
	
	private long snrId;
	private String site;
	private String status;
	private String vfCRQ;
	private String jobType;
	private String crqStatus;
	private String tefCRQ;
	private String technologyList;
	private String scheduledDate;
	private String accessStatus;
	private String nextPreCheck;
	private String feList;	

	public final String FE_SHORT = "FE_SHORT";
	public final String TECH_SHORT = "TECH_SHORT";
	
	public crqAccessHeader(
		long snrId,
		String site,
		String status,
		String vfCRQ,
		String jobType,
		String crqStatus,
		String tefCRQ,
		String technologyList,
		String scheduledDate,
		String accessStatus,
		String nextPreCheck,
		String feList ) {
		this.snrId =snrId;
		this.site = site;
		this.status = status;
		this.vfCRQ = vfCRQ;
		this.jobType = jobType;
		this.crqStatus = crqStatus;
		this.tefCRQ = tefCRQ;
		this.technologyList = technologyList;
		this.scheduledDate = scheduledDate;
		this.accessStatus = accessStatus;
		this.nextPreCheck = nextPreCheck;
		this.feList = feList;		
	};

	public long getSNRId() {
		return snrId;
	}
	
	public String getSite() {
		return site;
	}
	
	public String getVfCRQ() {
		return vfCRQ;
	}
	
	public String getJobType() {
		return jobType;
	}
	
	public String getCRQStatus() {
		return crqStatus;
	}
	
	public String getTefCRQ() {
		return tefCRQ;
	}	
	
	public String getTechnologies() {
		return technologyList;
	}
	
	public String getTechnologiesDisplay() {
		return technologyList==null?"":technologyList.length()>50?TECH_SHORT:technologyList;
	}
	
	public String getTechnologiesShort() {
		return getAbbreviation(technologyList);
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public String getAccessStatus() {
		return accessStatus;
	}

	public String getNextPreCheck() {
		return nextPreCheck;
	}

	public String getNextPreCheckDisplay() {
		return nextPreCheck==""?"":nextPreCheck.substring(0,1);
	}
	
	public String getFeList() {
		return feList;
	}
	
	public String getFeListDisplay() {
		return feList==null?"":feList.length()>50?FE_SHORT:feList;
	}
	
	public String getFeListShort() {
		return getAbbreviation(feList);
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
	
}
