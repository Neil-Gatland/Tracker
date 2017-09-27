package com.devoteam.tracker.model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class PreCheckList {	

	private long preCheckId;
	private String itemName;
	private String itemDescription;
	private String storageType;
	private String fixedValuesInd;
	private int length;
	private int decPlaces;
	private String requiredInd;
	private String stringValue;
	private Date dateValue;
	private double numberValue;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private int valueSeq;
	private String stringValueOption;
	private Date dateValueOption;
	private Double numberValueOption;
	private String technology;
	private String preCheckStatus;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	/** Constant for column Storage_Type value "String" */
	public static final String STORAGE_TYPE_STRING = "String";

	/** Constant for column Storage_Type value "Date" */
	public static final String STORAGE_TYPE_DATE = "Date";

	/** Constant for column Storage_Type value "Number" */
	public static final String STORAGE_TYPE_NUMBER = "Number";
	
	public PreCheckList(
			long preCheckId,
			String itemName,
			String itemDescription,
			String storageType,
			String fixedValuesInd,
			int length,
			int decPlaces,
			String requiredInd,
			String stringValue,
			Date dateValue,
			double numberValue,
			Date lastUpdatedDate,
			String lastUpdatedBy,
			int valueSeq,
			String stringValueOption,
			Date dateValueOption,
			Double numberValueOption,
			String technology,
			String preCheckStatus) {
		this.preCheckId = preCheckId;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.storageType = storageType;
		this.fixedValuesInd = fixedValuesInd;
		this.length = length;
		this.decPlaces = decPlaces;
		this.requiredInd = requiredInd;
		this.stringValue = stringValue;
		this.dateValue = dateValue;
		this.numberValue = numberValue;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.valueSeq = valueSeq;
		this.stringValueOption = stringValueOption;
		this.dateValueOption = dateValueOption;
		this.numberValueOption = numberValueOption;
		this.technology = technology;
		this.preCheckStatus = preCheckStatus;
	}
	
	public long getPreCheckId() {
		return preCheckId;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}
	
	public String getStorageType() {
		return storageType;
	}
	
	public String getFixedValuesInd() {
		return fixedValuesInd;
	}
	
	public boolean hasFixedValues() {
		return fixedValuesInd.equalsIgnoreCase("Y");
	}
	
	public int getLength() {
		return length;
	}
	
	public int getDecPlaces() {
		return decPlaces;
	}
	
	public String getRequiredInd() {
		return requiredInd;
	}
	
	public boolean isRequired() {
		return requiredInd.equalsIgnoreCase("Y");
	}
		
	public String getStringValue() {
		if (storageType.equals(STORAGE_TYPE_DATE)) {
			return getDateValueString();
		} else if (storageType.equals(STORAGE_TYPE_NUMBER)) {
			return getNumberValueString();
		} else {
			return stringValue==null?"":stringValue;
		}	
	}
	
	public Date getDateValue() {
		return dateValue;
	}
	
	public String getDateValueString() {
		return dateValue==null?"":dateFormatter.format(dateValue);
	}

	public double getNumberValue() {
		return numberValue;
	}
	
	public String getNumberValueString() {
		return String.format("%,.2f", numberValue);
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
	
	public int getValueSeq() {
		return valueSeq;
	}
	
	public String getStringValueOption() {
		return stringValueOption==null?"":stringValueOption;
	}
	
	public Date getDateValueOption() {
		return dateValueOption;
	}
	
	public Double getNumberValueOption() {
		return numberValueOption;
	}
	
	public String getPreCheckStatus() {
		return preCheckStatus;
	}
	
}
