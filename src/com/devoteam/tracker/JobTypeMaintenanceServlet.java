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

public class JobTypeMaintenanceServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1901127184620606347L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		String destination = "/jobTypeMaintenance.jsp";
		try {
			HttpSession session = req.getSession(false);
			if (session == null) {
				destination = "/logon.jsp";
				session = req.getSession(true);
				session.setAttribute("userMessage", "Please enter a valid user id and password");
			} else {
				String buttonPressed = req.getParameter("buttonPressed");
				String jobType = req.getParameter("jobType");
		    	req.setAttribute("buttonPressed", buttonPressed);
		    	req.setAttribute("jobType", jobType);
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null; 
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);		    	
				if (buttonPressed.equals("jobTypeDelete")) {
				    try {
				    	conn = DriverManager.getConnection(url);
				    	cstmt = conn.prepareCall("{call DeleteJobTypeScreen(?)}");
						cstmt.setString(1, jobType);
						boolean found = cstmt.execute();
						long ret = -1;
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								ret = rs.getLong(1);
							}
						}
						if (ret == -1) {
				        	req.setAttribute("userMessage", "Error: unable to delete job type");
						} else if (ret == 0) {
					        	req.setAttribute("userMessage", "Job type " + jobType + " deleted");
					        	req.removeAttribute("jobType");
						} else {
							req.setAttribute("userMessage", "Error: Cannot delete job type " + jobType + 
									"; it is used by " + ret + " pot" + (ret>1?"s":""));
						}
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: unable to delete job type, " + ex.getMessage());
			        	req.removeAttribute("buttonPressed");
				    } finally {
						cstmt.close();
						conn.close();
				    }
				} else if (buttonPressed.equals("jobTypeAdd")) {
					String newJobType = req.getParameter("newJobType");
			    	req.setAttribute("jobType", newJobType);
					String newProjectRequestor = req.getParameter("newProjectRequestor");
					String newProjectRequestorEmail = req.getParameter("newProjectRequestorEmail");
					String newProjectManager = req.getParameter("newProjectManager");
					String newProjectManagerEmail = req.getParameter("newProjectManagerEmail");
					String newActingCustomer = req.getParameter("newActingCustomer");
				    try {
				    	conn = DriverManager.getConnection(url);
				    	cstmt = conn.prepareCall("{call AddJobTypeScreen(?,?,?,?,?,?,?)}");
						cstmt.setString(1, newJobType.trim());
						cstmt.setString(2, newProjectRequestor.trim());
						cstmt.setString(3, thisU.getNameForLastUpdatedBy());
						cstmt.setString(4, newProjectManager.trim());
						cstmt.setString(5, newActingCustomer.trim());
						cstmt.setString(6, newProjectRequestorEmail.trim());
						cstmt.setString(7, newProjectManagerEmail.trim());
						boolean found = cstmt.execute();
						String ok = "N";
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								ok = rs.getString(1);
							}
						}
						if (ok.equalsIgnoreCase("Y")) {
				        	req.setAttribute("userMessage", "Job type " + newJobType + " added");
						} else if (ok.equalsIgnoreCase("E")) {
							req.setAttribute("userMessage", "Error: Job type " + newJobType + " exists already");
						} else {
				        	req.setAttribute("userMessage", "Error: unable to add job type");
						}
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: unable to add job type, " + ex.getMessage());
			        	req.removeAttribute("buttonPressed");
				    } finally {
						cstmt.close();
						conn.close();
				    }		
				} else if (buttonPressed.equals("jobTypeAmend")) {
					String currentJobType = req.getParameter("currentJobType");
			    	req.setAttribute("jobType", currentJobType);
					String amendProjectRequestor = req.getParameter("amendProjectRequestor");
					String amendProjectRequestorEmail = req.getParameter("amendProjectRequestorEmail");
					String amendProjectManager = req.getParameter("amendProjectManager");
					String amendProjectManagerEmail = req.getParameter("amendProjectManagerEmail");
					String amendActingCustomer = req.getParameter("amendActingCustomer");
				    try {
				    	conn = DriverManager.getConnection(url);
				    	cstmt = conn.prepareCall("{call AmendJobTypeScreen(?,?,?,?,?,?,?)}");
						cstmt.setString(1, currentJobType.trim());
						cstmt.setString(2, amendProjectRequestor.trim());
						cstmt.setString(3, thisU.getNameForLastUpdatedBy());
						cstmt.setString(4, amendProjectManager.trim());
						cstmt.setString(5, amendActingCustomer.trim());
						cstmt.setString(6, amendProjectRequestorEmail.trim());
						cstmt.setString(7, amendProjectManagerEmail.trim());
						boolean found = cstmt.execute();
						String ok = "N";
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								ok = rs.getString(1);
							}
						}
						if (ok.equalsIgnoreCase("Y")) {
				        	req.setAttribute("userMessage", "Job type " + currentJobType + " amended");
						} else {
				        	req.setAttribute("userMessage", "Error: unable to amend job type");
						}
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: unable to amend job type, " + ex.getMessage());
			        	req.removeAttribute("buttonPressed");
				    } finally {
						cstmt.close();
						conn.close();
				    }
				}    
			}
		} catch (Exception ex) {
        	req.setAttribute("userMessage", "Error: JobTypeMaintenanceServlet, " + ex.getMessage());
        	req.removeAttribute("buttonPressed");
		} finally {
		  	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
		  	dispatcher.forward(req,resp);
		}  	
	}
}
