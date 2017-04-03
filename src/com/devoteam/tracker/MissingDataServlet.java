package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;

public class MissingDataServlet extends HttpServlet {

	private static final long serialVersionUID = 4077264961302055152L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/missingData.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			session.setAttribute("prevScreen", "missingData");
			String snrId = req.getParameter("snrId");
			String column = req.getParameter("column");
			String newValue = req.getParameter("newValue");
			String thirdPartyId = req.getParameter("thirdPartyId");
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
	    	req.setAttribute("userMessage"," " );
			if (column.equals("scheduledDate")) {
				try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdateScheduledDate(?,?,?)}");
			    	cstmt.setLong(1, Long.parseLong(snrId));
			    	cstmt.setString(2, newValue);
			    	cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								req.setAttribute("userMessage","Missing scheduled date added");
							} else {
								req.setAttribute("userMessage","Error: Failed to add missing scheduled date");
							}
						}
					} 
				} catch (Exception ex) {
					req.setAttribute("userMessage", "Error in UpdateScheduledDate(): " + ex.getMessage());
			    	ex.printStackTrace();
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    } 
			    }
			} else if (column.equals("upgradeType")) {
				try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdateUpgradeType(?,?,?)}");
			    	cstmt.setLong(1, Long.parseLong(snrId));
			    	cstmt.setString(2, newValue);
			    	cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								req.setAttribute("userMessage","Missing upgrade type added");
							} else {
								req.setAttribute("userMessage","Error: Failed to add missing upgrade type");
							}
						}
					}
			    } catch (Exception ex) {
			    	req.setAttribute("userMessage", "Error in UpdateUpgradeType(): " + ex.getMessage());
			    	ex.printStackTrace();
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    } 
			    }
			} else if (column.equals("hardwareVendor")) {
				try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdateHardwareVendor(?,?,?)}");
			    	cstmt.setString(1, snrId);
			    	cstmt.setString(2, newValue);
			    	cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								req.setAttribute("userMessage","Missing hardware vendor added");
							} else {
								req.setAttribute("userMessage","Error: Failed to add missing hardware vendor");
							}
						}
					}
			    } catch (Exception ex) {
			    	req.setAttribute("userMessage", "Error in UpdateHardwareVendor(): " + ex.getMessage());
			    	ex.printStackTrace();
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    } 
			    }
			} else if ((column.equals("boEngineer"))||(column.equals("feEngineer"))) {
				String role = (column.equals("boEngineer")?"BO Engineer":"Field Engineer");
				try {
					conn = DriverManager.getConnection(url);
					cstmt = conn.prepareCall("{call AddSNRUserRole(?,?,?,?,?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setLong(2, Long.parseLong(newValue));
					cstmt.setString(3, role);
					cstmt.setLong(4, (column.equals("boEngineer")?-1:Long.parseLong(thirdPartyId)));
					cstmt.setLong(5, 1);
					cstmt.setString(6, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								req.setAttribute("userMessage", "Added "+role);
							} else {
								req.setAttribute("userMessage", "Failed to add "+role);
							}
						}
					}
			    } catch (Exception ex) {
			    	req.setAttribute("userMessage", "Error in AddeSNRUserRole(): " + ex.getMessage());
			    	ex.printStackTrace();
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    } 
			    }
			}
			Random r = new Random();
			String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		  	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
		  	dispatcher.forward(req,resp);
		}
	}
	
}
