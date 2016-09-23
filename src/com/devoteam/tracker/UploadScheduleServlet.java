package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.BOEngineer;
import com.devoteam.tracker.model.FieldEngineer;
import com.devoteam.tracker.model.SNRScheduleSpreadsheet;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;

public class UploadScheduleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1882546362130268848L;

	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		String destination = "/scheduling.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			ArrayList<SNRScheduleSpreadsheet> scheduledSNRs = 
					(ArrayList<SNRScheduleSpreadsheet>) session.getAttribute(ServletConstants.SCHEDULED_SNRS_IN_SESSION);	
	    	session.removeAttribute(ServletConstants.SCHEDULE_SPREADSHEET_NAME_IN_SESSION);
	    	session.removeAttribute(ServletConstants.SCHEDULE_SPREADSHEET_COPY_NAME_IN_SESSION);
			session.removeAttribute(ServletConstants.SCHEDULED_SNRS_IN_SESSION);
			session.removeAttribute(ServletConstants.INVALID_SNRS_IN_SESSION);
			session.removeAttribute(ServletConstants.SCHEDULED_SNR_WARNINGS_IN_SESSION);
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			try {
				Connection conn = null;
				CallableStatement cstmt = null;
				int scheduledCount = 0;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	conn.setAutoCommit(false);
			    	for (Iterator<SNRScheduleSpreadsheet> it = scheduledSNRs.iterator(); it.hasNext(); ) {
			    		SNRScheduleSpreadsheet sss = it.next();
			    		cstmt = conn.prepareCall("{call Schedule_SNR(?,?,?,?,?,?)}");
			    		cstmt.setString(1, sss.getSite());
			    		cstmt.setString(2, sss.getNRId());
			    		cstmt.setDate(3, sss.getScheduledDate());
			    		cstmt.setString(4, sss.getWorkflowName());
			    		cstmt.setString(5, sss.getCommentary());
			    		cstmt.setString(6, thisU.getNameForLastUpdatedBy());
			    		boolean scheduled = false;
						if (cstmt.execute()) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								scheduled = rs.getString(1).equals("Y");
							}
						}
						cstmt.close();
						
						if (scheduled) {
					    	for (Iterator<FieldEngineer> it2 = sss.getFieldEngineers().iterator(); it2.hasNext(); ) {
					    		FieldEngineer fe = it2.next();
					    		cstmt = conn.prepareCall("{call Allocate_SNR_FE(?,?,?,?,?)}");
					    		cstmt.setLong(1, sss.getSNRId());
					    		cstmt.setLong(2, fe.getUserId());
					    		cstmt.setLong(3, fe.getThirdPartyId());
					    		cstmt.setInt(4, fe.getRank());
					    		cstmt.setString(5, thisU.getNameForLastUpdatedBy());
								if (cstmt.execute()) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										if (!rs.getString(1).equals("Y")) {
											throw new SQLException("Negative return code from Allocate_SNR_FE() for SNR Id " +
													sss.getSNRId() + " and Field Engineer " + fe.getName());
										}
									}
								}
								cstmt.close();
					    	}
	
					    	for (Iterator<BOEngineer> it3 = sss.getBOEngineers().iterator(); it3.hasNext(); ) {
					    		BOEngineer be = it3.next();
					    		cstmt = conn.prepareCall("{call Allocate_SNR_BO(?,?,?)}");
					    		cstmt.setLong(1, sss.getSNRId());
					    		cstmt.setLong(2, be.getUserId());
					    		cstmt.setString(3, thisU.getNameForLastUpdatedBy());
								if (cstmt.execute()) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										if (!rs.getString(1).equals("Y")) {
											throw new SQLException("Negative return code from Allocate_SNR_BO() for SNR Id " +
													sss.getSNRId() + " and BO Engineer " + be.getName());
										}
									}
								}
								cstmt.close();
					    	}
					    	
					    	scheduledCount++;
						} else {
							throw new SQLException("Negative return code from Schedule_SNR() for Site/NR Id " +
									sss.getSite() + "/" + sss.getNRId());
						}
			    		
			    	}
			    	
					conn.commit();
					session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
							scheduledCount + " SNR" + (scheduledCount!=1?"s":"") + " scheduled");
			    } catch (SQLException ex) {
			    	conn.rollback();
			    	session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
			    			"Error: Unable to schedule SNRs, " + ex.getMessage());
			    } finally {
					cstmt.close();
					conn.close();
		        	req.removeAttribute("buttonPressed");
			    }
	
			} catch (Exception ex) {
				session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
						"Error: " + ex.getMessage());
			}
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		//resp.sendRedirect(destination+ran);
      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
	}

}
