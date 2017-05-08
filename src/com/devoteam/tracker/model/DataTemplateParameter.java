package com.devoteam.tracker.model;

public class DataTemplateParameter {
	
	private String columnDBName;
	private String columnDisplayName;
	private String parameterDataType;
	private int parameterLength;
	private int parameterDecPlaces;
	private String parameterValueType;
	private String parameterDBTable;
	private String parameterDBColumn;
	private String parameterRestriction;
	
	public DataTemplateParameter(		
		String columnDBName,
		String columnDisplayName,
		String parameterDataType,
		int parameterLength,
		int parameterDecPlaces,
		String parameterValueType,
		String parameterDBTable,
		String parameterDBColumn,
		String parameterRestriction		
	) {
		this.columnDBName = columnDBName;
		this.columnDisplayName = columnDisplayName;
		this.parameterDataType = parameterDataType;
		this.parameterLength = parameterLength;
		this.parameterDecPlaces = parameterDecPlaces;
		this.parameterValueType = parameterValueType;
		this.parameterDBTable = parameterDBTable;
		this.parameterDBColumn = parameterDBColumn;
		this.parameterRestriction = parameterRestriction;
	}
	
	public String getColumnDBName() {
		return columnDBName;
	}
	
	public String getColumnDisplayName() {
		return columnDisplayName;
	}
	
	public String getParameterDataType() {
		return parameterDataType;
	}
	
	public int getParameterLength() {
		return parameterLength;
	}
	
	public int getParameterDecPlaces() {
		return parameterDecPlaces;
	}
	
	public String getParameterValueType() {
		return parameterValueType;
	}
	
	public String getParameterDBTable() {
		return parameterDBTable;
	}
	
	public String getParameterDBColumn() {
		return parameterDBColumn;
	}
	
	public String getParameterRestriction() {
		return parameterRestriction;
	}

}
