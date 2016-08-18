package com.devoteam.tracker.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class SNRCommentary {
	private long snrId;
	private Date commentaryDT;
	private long preCheckId;
	private String commentaryType;
	private String commentary;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private static int numberOfColumns = 5;
	
	public SNRCommentary(long snrId, Date commentaryDT, long preCheckId,
			String commentaryType, String commentary, Date lastUpdatedDate, 
			String lastUpdatedBy) {
		this.snrId = snrId;
		this.commentaryDT = commentaryDT;
		this.preCheckId = preCheckId;
		this.commentaryType = commentaryType;
		this.commentary = commentary;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public long getSNRId() {
		return snrId;
	}
	
	public Date getCommentaryDT() {
		return commentaryDT;
	}
	
	public String getCommentaryDTString() {
		return commentaryDT==null?"":dateFormatter.format(commentaryDT);
	}
	
	public long getPreCheckId() {
		return preCheckId;
	}
	
	public String getPreCheckIdString() {
		return preCheckId==-1?"":Long.toString(preCheckId);
	}
	
	public String getCommentaryType() {
		return commentaryType;
	}
	
	public String getCommentary() {
		return commentary;
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
	
	public String[] getValueArray() {
		String[] values = {this.getCommentaryDTString(),
			commentaryType, commentary, lastUpdatedBy};
		return values;
	}
	
	/*public static int getNumberOfColumns() {
		return numberOfColumns;
	}*/
}
