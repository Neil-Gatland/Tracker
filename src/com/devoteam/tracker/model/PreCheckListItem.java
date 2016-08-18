package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class PreCheckListItem implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6586258529907516019L;
	private long preCheckId;
	private String itemName;
	private String itemDescription;
	private String storageType;
	private String fixedValuesInd;
	private int valueSeq;
	private ArrayList<String> stringValueOptions = new ArrayList<String>();
	private ArrayList<Date> dateValueOptions = new ArrayList<Date>();
	private ArrayList<Double> numberValueOptions = new ArrayList<Double>();
	private String stringValue;
	private Date dateValue;
	private double numberValue;
	private Date lastUpdatedDate;
	private String lastUpdatedBy;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private String requiredInd;
	private int length;
	private int decimalPlaces;

	/** Constant for column Fixed_Values_Ind value "Y" */
	public static final String FIXED_VALUES_IND_Y = "Y";

	/** Constant for column Fixed_Values_Ind value "N" */
	public static final String FIXED_VALUES_IND_N = "N";

	/** All values for column Fixed_Values_Ind */
	public static final String[] FIXED_VALUES_IND_VALUES = new String[] {
		FIXED_VALUES_IND_Y,
		FIXED_VALUES_IND_N
	};
	
	/** Constant for column Storage_Type value "String" */
	public static final String STORAGE_TYPE_STRING = "String";

	/** Constant for column Storage_Type value "Date" */
	public static final String STORAGE_TYPE_DATE = "Date";

	/** Constant for column Storage_Type value "Number" */
	public static final String STORAGE_TYPE_NUMBER = "Number";

	/** Constant for column Storage_Type value "YNString" (special value for Yes/No items)*/
	public static final String STORAGE_TYPE_YNSTRING = "YNString";

	/** All values for column Storage_Type */
	public static final String[] STORAGE_TYPE_VALUES = new String[] {
		STORAGE_TYPE_STRING,
		STORAGE_TYPE_YNSTRING,
		STORAGE_TYPE_DATE,
		STORAGE_TYPE_NUMBER
	};
	
	
	public PreCheckListItem(long preCheckId, String itemName, String itemDescription,
			String storageType, String fixedValuesInd, int length, int decimalPlaces,
			String requiredInd, String stringValue, Date dateValue,	
			double numberValue,Date lastUpdatedDate, String lastUpdatedBy) {
		this.preCheckId = preCheckId;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.storageType = storageType;
		this.fixedValuesInd = fixedValuesInd;
		this.length = length;
		this.decimalPlaces = decimalPlaces;
		this.requiredInd = requiredInd;
		this.stringValue = stringValue;
		this.dateValue = dateValue;
		this.numberValue = numberValue;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public PreCheckListItem(long preCheckId, String itemName, String itemDescription,
			String storageType, String fixedValuesInd, int length, int decimalPlaces,
			String requiredInd, String stringValue,
			Date lastUpdatedDate, String lastUpdatedBy) {
		this.preCheckId = preCheckId;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.storageType = storageType;
		this.fixedValuesInd = fixedValuesInd;
		this.length = length;
		this.decimalPlaces = decimalPlaces;
		this.requiredInd = requiredInd;
		this.stringValue = stringValue;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public PreCheckListItem(long preCheckId, String itemName, String itemDescription,
			String storageType, String fixedValuesInd, int length, int decimalPlaces,
			String requiredInd, Date dateValue,
			Date lastUpdatedDate, String lastUpdatedBy) {
		this.preCheckId = preCheckId;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.storageType = storageType;
		this.fixedValuesInd = fixedValuesInd;
		this.length = length;
		this.decimalPlaces = decimalPlaces;
		this.requiredInd = requiredInd;
		this.dateValue = dateValue;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public PreCheckListItem(long preCheckId, String itemName, String itemDescription,
			String storageType, String fixedValuesInd, int length, int decimalPlaces,
			String requiredInd, double numberValue,
			Date lastUpdatedDate, String lastUpdatedBy) {
		this.preCheckId = preCheckId;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.storageType = storageType;
		this.fixedValuesInd = fixedValuesInd;
		this.length = length;
		this.decimalPlaces = decimalPlaces;
		this.requiredInd = requiredInd;
		this.numberValue = numberValue;
		this.lastUpdatedDate = lastUpdatedDate;
		this.lastUpdatedBy = lastUpdatedBy;
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
	
	public String getRequiredInd() {
		return requiredInd;
	}
	
	public boolean isRequired() {
		return requiredInd.equalsIgnoreCase("Y");
	}
	
	public int getLength() {
		return length;
	}
	
	public int getDecimalPlaces() {
		return decimalPlaces;
	}
	
	public int getValueSeq() {
		return valueSeq;
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
	
	public boolean setStringValue(String stringValue) {
		boolean valid = true;
		if (storageType.equals(STORAGE_TYPE_DATE)) {
			try {
				this.dateValue = new Date(dateFormatter.parse(stringValue).getTime());
			} catch (ParseException pe) {
				valid = false;
			}
		} else if (storageType.equals(STORAGE_TYPE_NUMBER)) {
			try {
				this.numberValue = Double.parseDouble(stringValue);
			} catch (NumberFormatException nfe) {
				valid = false;
			}
		} else {
			this.stringValue = stringValue;
		}
		return valid;
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
	
	public void addStringValueOption(String option) {
		stringValueOptions.add(option);
	}
	
	public void setStringValueOptions(Collection<String> options) {
		stringValueOptions.addAll(options);
	}
	
	public Collection<String> getStringValueOptions() {
		return stringValueOptions;
	}
	
	public void addDateValueOption(Date option) {
		dateValueOptions.add(option);
		stringValueOptions.add(option==null?"":dateFormatter.format(option));
	}
	
	public void setDateValueOptions(Collection<Date> options) {
		dateValueOptions.addAll(options);
		for(Iterator<Date> it = options.iterator(); it.hasNext(); ) {
			Date option = it.next();
			stringValueOptions.add(option==null?"":dateFormatter.format(option));
		}
	}
	
	public Collection<Date> getDateValueOptions() {
		return dateValueOptions;
	}
	
	public void addNumberValueOption(double option) {
		numberValueOptions.add(new Double(option));
		stringValueOptions.add(String.format("%,.2f", option));
	}

	public void setNumberValueOptions(Collection<Double> options) {
		numberValueOptions.addAll(options);
		for(Iterator<Double> it = options.iterator(); it.hasNext(); ) {
			Double option = it.next();
			stringValueOptions.add(String.format("%,.2f", option.doubleValue()));
		}
	}

	public Collection<Double> getNumberValueOptions() {
		return numberValueOptions;
	}
}
