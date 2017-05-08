package com.devoteam.tracker.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.devoteam.tracker.model.DataTemplateParameter;
import com.devoteam.tracker.model.ScheduleList;
import com.devoteam.tracker.model.User;

public class DataAnalytics {
	
	private User user;
	private String url;
	private String message;
	
	public DataAnalytics(User user, String url) {
		this.user = user;
		this.url = url;
	}
	
	public String GetDataTemplateChartType(String dataTemplateName) {
		String chartType = "";
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	 try {
 	    	conn = DriverManager.getConnection(url);
 	    	cstmt = conn.prepareCall("{call GetDataTemplateChartType(?)}");
 	    	cstmt.setString(1, dataTemplateName);
 			boolean found = cstmt.execute();
 			if (found) {
 				ResultSet rs = cstmt.getResultSet();
 				while (rs.next()) {
 					chartType = rs.getString(1);
 				}
 			}
 	    } catch (Exception ex) {
 	    	message = "Error in GetDataTemplateChartType(): " + ex.getMessage();
 	    	ex.printStackTrace();
 	    } finally {
 	    	try {
 	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
 	    		if ((conn != null) && (!conn.isClosed())) conn.close();
 		    } catch (SQLException ex) {
 		    	ex.printStackTrace();
 		    }
 	    } 
		return chartType;
	}
	
	public String GetDataSourceView(String dataSourceName) {
		String viewName = "";
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	 try {
 	    	conn = DriverManager.getConnection(url);
 	    	cstmt = conn.prepareCall("{call GetDataSourceView(?)}");
 	    	cstmt.setString(1, dataSourceName);
 			boolean found = cstmt.execute();
 			if (found) {
 				ResultSet rs = cstmt.getResultSet();
 				while (rs.next()) {
 					viewName = rs.getString(1);
 				}
 			}
 	    } catch (Exception ex) {
 	    	message = "Error in GetDataSourceView(): " + ex.getMessage();
 	    	ex.printStackTrace();
 	    } finally {
 	    	try {
 	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
 	    		if ((conn != null) && (!conn.isClosed())) conn.close();
 		    } catch (SQLException ex) {
 		    	ex.printStackTrace();
 		    }
 	    } 
		return viewName;
	}
	
	private Collection<DataTemplateParameter> getParameterList(String dataTemplateName ) {
		message = null;
		ArrayList<DataTemplateParameter> parameterList = new ArrayList<DataTemplateParameter>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetDataTemplateParameters(?)}");
	    	cstmt.setString(1, dataTemplateName);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					parameterList.add(new DataTemplateParameter(
							rs.getString(1),  rs.getString(2),  rs.getString(3),  rs.getInt(4), 
							rs.getInt(5),  rs.getString(6),  rs.getString(7),  rs.getString(8),
							rs.getString(9)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getParameterList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return parameterList;
	}
	
	public String getParameterListHTML
		( String dataTemplateName,
		  String parameter0,
		  String parameter1,
		  String parameter2,
		  String parameter3,
		  String parameter4,
		  String parameter5,
		  String parameter6,
		  String parameter7,
		  String parameter8,
		  String parameter9) {
		StringBuilder html = new StringBuilder();
		// Load parameters into an array
		String [] parameterArray = 
			{ parameter0, parameter1, parameter2, parameter3, parameter4,
			  parameter5, parameter6, parameter7, parameter8, parameter9 };
		int parameterPos = 0;
		Collection<DataTemplateParameter> ParameterList = 
			getParameterList(dataTemplateName);
		if (ParameterList.isEmpty()) {
			if (message != null) {
				// if there is a failure message display it
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "daTitle", message);
				td.setAttribute("colspan", "4");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			} else {
				// otherwise there are no parameters to display
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "daTitle","&nbsp;");
				td.setAttribute("colspan", "4");
				tr.appendValue(td.toString());
				html.append(tr.toString());	
			}
		} 
		else {
			// Display parameters
			for (Iterator<DataTemplateParameter> it = ParameterList.iterator(); it.hasNext(); ) {				
				DataTemplateParameter dtp = it.next();
				// parameter line
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td1 = new HTMLElement("td", "", "&nbsp;");
				tr.appendValue(td1.toString());
				HTMLElement td2 = new HTMLElement("td", "daTitle", dtp.getColumnDisplayName()+":");
				tr.appendValue(td2.toString());
				String selection = "";
				if (dtp.getParameterValueType().equals("Date Range")) {
					selection = 
						"<input type=\"text\" size=\"10\" id=\"selectStartDate\" "+
					    "value =\""+parameterArray[parameterPos]+"\" "+
						"name=\"selectStartDate\" onclick=\"javascript:NewCssCal "+
						"('selectStartdDate','ddMMyyyy','arrow')\" style=\"cursor:pointer;\" "+
						"readonly/> <img src=\"images/cal.gif\" onclick=\"javascript:NewCssCal"+
						"('selectStartDate','ddMMyyyy','arrow')\" style=\"cursor:pointer;\"> "+
						"<input type=\"text\" size=\"10\" id=\"selectEndDate\" "+
					    "value =\""+parameterArray[parameterPos+1]+"\" "+
						"name=\"selectEndDate\" onclick=\"javascript:NewCssCal "+
						"('selectEndDate','ddMMyyyy','arrow')\" style=\"cursor:pointer;\" "+
						"readonly/> <img src=\"images/cal.gif\" onclick=\"javascript:NewCssCal"+
						"('selectEndDate','ddMMyyyy','arrow')\" style=\"cursor:pointer;\">";
						parameterPos = parameterPos + 2;
				} else {
					selection = getParameterSelect(
							dtp.getColumnDisplayName(),
							dtp.getParameterValueType(),
							dtp.getParameterDBTable(),
							dtp.getParameterDBColumn(),
							dtp.getParameterRestriction(),
							dtp.getParameterDataType(),
							dataTemplateName,
							dtp.getColumnDBName(),
							parameterArray[parameterPos]);
					parameterPos++;
				}			
				
				HTMLElement td3 = new HTMLElement("td", "", selection);
				tr.appendValue(td3.toString());
				HTMLElement td4 = new HTMLElement("td", "", "&nbsp;");
				tr.appendValue(td4.toString());
				html.append(tr.toString());
				// blank line
				HTMLElement trBlank = new HTMLElement("tr");
				HTMLElement tdBlank = new HTMLElement("td", "daTitle", "&nbsp;");
				tdBlank.setAttribute("colspan", "4");
				trBlank.appendValue(tdBlank.toString());
				html.append(trBlank.toString());				
			}
		}
		return html.toString();
	}
	
	private String getParameterSelect(
			String displayName,
			String valueType,
			String dbTable,
			String dbColumn,
			String restriction,
			String dataType,
			String dataTemplateName,
			String dbName,
			String selectedValue) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Select select = new Select("select"+displayName+"ParameterList",  "" );
		String selectSQL = generateSelectionSQL
				(	valueType,
					dbTable,
					dbColumn,
					restriction,
					user.getFullname(),
					dataType,
					dataTemplateName,
					dbName,
					selectedValue);
	    try {
	    	conn = DriverManager.getConnection(url);
	    	pstmt = conn.prepareStatement(selectSQL);
			boolean found = pstmt.execute();
			if (found) {
				ResultSet rs = pstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
				}
			}
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((pstmt != null) && (!pstmt.isClosed()))	pstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    }
		return select.toString();
	}
	
	private String generateSelectionSQL 
	(	String valueType,
		String dbTable,
		String dbColumn,
		String restriction,
		String username,
		String dataType,
		String dataTemplateName,
		String dbName,
		String selectedValue) {
		String sql = "";
		if (valueType.equals("DB List")) {
			if (!selectedValue.equals("")) {
				sql = "SELECT '"+selectedValue+"' AS "+dbColumn+", '"+selectedValue+"' AS "+dbColumn+"Alt, "+
						"' ' AS Sort_Value UNION ALL ";
			}
			sql = sql + "SELECT DISTINCT "+dbColumn+", "+dbColumn+" AS "+dbColumn+"Alt, "+dbColumn+" AS Sort_Value "+
					"FROM "+dbTable+
					" WHERE "+dbColumn+" <> '"+selectedValue+"' ";
			if (restriction.equals("Username")) {
				if (dbColumn.equals("Project")) {
					sql = sql +
						" AND ( '"+username+"' IN ( SELECT CONCAT( firstname, IFNULL( username_suffix, '' ), "+
						"'.', surname ) FROM user WHERE user_type = 'Devoteam' )"+
						"OR Project IN ( SELECT Job_Type FROM user_job_type WHERE  User_Id = "+
						"( SELECT User_Id FROM user WHERE CONCAT( firstname, IFNULL( username_suffix, '' ), "+
						"'.', surname ) = '"+username+"' ) ) )";
				}
			}
			sql = sql +
				" ORDER BY Sort_Value";
		} else if (valueType.equals("Fixed List")) {
			if (!selectedValue.equals("")) {							
				sql = "SELECT '"+selectedValue+"', '"+ selectedValue+" ' ' AS Sort_Value UNION ALL ";
			}
			sql = 	sql +
					"SELECT String_Value, String_Value FROM data_template_column, data_template_column_value "+
					"WHERE data_template_column.Data_Template_Name = data_template_column_value.Data_Template_Name "+
					"AND data_template_column.Column_DB_Name = data_template_column_value.Column_DB_Name " +
					"AND data_template_column.Data_Template_Name = '"+dataTemplateName+"' "+
					"AND data_template_column.Column_DB_Name = '"+dbName+"' "+
					"AND data_template_column.Column_Type = 'Parameter' "+										
					"AND String_Value <> '"+selectedValue+"' "+
					"ORDER BY Sort_Value";
		}
		return sql;
	}

}
