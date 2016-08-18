package com.devoteam.tracker.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class EmailCopy {
	
	private Date producedOn; //1
	private String producedBy; //2
	private String sender; //3
	private String subject; //4
	private String toList; //5
	private String ccList; //6
	private String bccList; //7
	private String messageBody; //8
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public EmailCopy(
			Date producedOn,
			String producedBy,
			String sender,
			String subject,
			String toList,
			String ccList,
			String bccList,
			String messageBody) {
		this.producedOn = producedOn;
		this.producedBy = producedBy;
		this.sender = sender;
		this.subject = subject;
		this.toList = toList;
		this.ccList = ccList;
		this.bccList = bccList;
		this.messageBody = messageBody;
	}
	
	public Date getProducedOn() {
		return producedOn;
	}
	
	public String getProducedOnString() {
		return dateFormatter.format(producedOn);
	}

	public String getProducedBy() {
		return producedBy;
	}
	
	public String getSender() {
		return sender;		
	}

	public String getSubject() {
		return subject;		
	}
	
	public String getToList() {
		return toList;		
	}
	
	public String getCcList() {
		return ccList;		
	}
	
	public String getBccList() {
		return bccList;		
	}
	
	public String getMessageBody() {
		return messageBody;		
	}
	
	public String[] getEmailCopyArray1() {
		String[] values = {this.getProducedOnString(),producedBy,sender, subject};
		return values;
	}
	
	public String[] getEmailCopyArray2() {
		String[] values = {toList,ccList,bccList};
		return values;
	}

}
