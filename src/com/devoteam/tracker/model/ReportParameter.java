package com.devoteam.tracker.model;

import java.io.Serializable;

public class ReportParameter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 912405415894455345L;
	private String parameterName;
	private int order;
	private String datatype;

	/** Constant for column Data_Type value "String" */
	public static final String DATATYPE_STRING = "String";

	/** Constant for column Data_Type value "Number" */
	public static final String DATATYPE_NUMBER = "Number";

	/** Constant for column Data_Type value "Date" */
	public static final String DATATYPE_DATE = "Date";

	/** Constant for column Data_Type value "Timestamp" */
	public static final String DATATYPE_TIMESTAMP = "Timestamp";

	/** Constant for column Data_Type value "String" */
	public static final String DATATYPE_SELECT_STRING = "SelectString";

	/** Constant for column Data_Type value "Number" */
	public static final String DATATYPE_SELECT_NUMBER = "SelectNumber";

	/** All values for column Status */
	public static final String[] DATATYPE_VALUES = new String[] {
		DATATYPE_STRING,
		DATATYPE_NUMBER,
		DATATYPE_DATE,
		DATATYPE_TIMESTAMP,
		DATATYPE_SELECT_STRING,
		DATATYPE_SELECT_NUMBER
	};
	
	public ReportParameter(String parameterName, int order, String datatype) {
		this.parameterName = parameterName;
		this.order = order;
		this.datatype = datatype;
	}

	public String getParameterName() {
		return parameterName;
	}

	public String getParameterNameCondensed() {
		return parameterName.replaceAll("\\s+","");
	}

	public int getOrder() {
		return order;
	}

	public String getDatatype() {
		return datatype;
	}

}
