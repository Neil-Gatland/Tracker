package com.devoteam.tracker;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.DataAnalytics;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.UtilBean;

public class DownloadCSVServlet extends HttpServlet {
	
	private static final long serialVersionUID = 9010885538193827739L;
	private final SimpleDateFormat tsFormatter = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		HttpSession session = req.getSession(false);
		String destination = "";
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			destination = "/dataAnalytics.jsp";
			String reportName = (String)session.getAttribute("reportName");
			String reportSQL = (String)session.getAttribute("reportSQL");
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			String message = null;
	    	Connection conn = null;
	    	PreparedStatement pstmt = null;				
	    	try {
		    	conn = DriverManager.getConnection(url);
		    	pstmt = conn.prepareStatement(reportSQL);
				boolean found = pstmt.execute();
				if (found) {
					ResultSet rs = pstmt.getResultSet();
					ResultSetMetaData rsmd = rs.getMetaData();
					int cols = rsmd.getColumnCount() + 1;
					if (cols > 0) {
						if (rsmd.getColumnName(1).equalsIgnoreCase("Error")) {
							String msg = "Unknown error";
							if (rs.next()) {
								msg = rs.getString(1);
							}
							throw new Exception(msg);
						} else {
							Date date = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
							String formattedDate = sdf.format(date);
					        OutputStream oS = resp.getOutputStream();
							resp.setContentType("application/vnd.ms-excel");
					        String headerKey = "Content-Disposition";
					        String headerValue = String.format("attachment; filename=\"%s\"", 
					        		reportName + 
					        		"_" + formattedDate + ".csv");
					        resp.setHeader(headerKey, headerValue);
					        StringBuilder sB = new StringBuilder();
					        for (int c = 1; c < cols; c++) {
					        	sB.append(rsmd.getColumnName(c).replace('_', ' '));
					        	sB.append(",");
					        }
					        sB.setCharAt(sB.length()-1, '\n');
					        oS.write(sB.toString().getBytes());
					        String value;
					        while (rs.next()) {
					        	sB = new StringBuilder();
    					        for (int c = 1; c < cols; c++) {
    					        	int colType = rsmd.getColumnType(c);
       					        	if (colType == java.sql.Types.TIMESTAMP) {
    					        		Timestamp valueTS = rs.getTimestamp(c);
    					        		value = valueTS==null?"":tsFormatter.format(new Date(valueTS.getTime()));
    					        	} else if (colType == java.sql.Types.DATE) {
    					        		java.sql.Date valueDate = rs.getDate(c);
    					        		value = valueDate==null?"":dateFormatter.format(valueDate);
    					        	} else {
	    					        	String valueStr = rs.getString(c);
	    					        	value = valueStr==null?"":valueStr.replace(',', ' ');
    					        	}
    					        	sB.append(value);
    					        	sB.append(",");
    					        }
    					        sB.setCharAt(sB.length()-1, '\n');
    					        oS.write(sB.toString().getBytes());
					        }
							oS.flush();
							oS.close();
						}
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in running prepared SQL for CSV download: " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	try {
		    		if ((pstmt != null) && (!pstmt.isClosed()))	pstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    }
		    }
		}
	}


}
