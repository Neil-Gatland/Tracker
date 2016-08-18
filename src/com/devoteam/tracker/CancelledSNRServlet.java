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

public class CancelledSNRServlet extends HttpServlet   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8613919966524022L;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/cancelledSNRList.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid user id and password");
		} else {
			String buttonPressed = req.getParameter("buttonPressed");
			String snrId = req.getParameter("snrId");
	    	req.setAttribute("buttonPressed", buttonPressed);
	    	req.setAttribute("snrId", snrId);
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			Connection conn = null;
			CallableStatement cstmt = null;
	    	if (buttonPressed.equals("reopen")) {
			    try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call ReOpen_Cancelled_SNR(?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setString(2, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					String ok = "N";
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							ok = rs.getString(1);
						}
					}
					if (ok.equalsIgnoreCase("Y")) {
						req.setAttribute("userMessage", "NR reopened");
						destination = "/workQueues.jsp";
					} else {
			        	req.setAttribute("userMessage", "Error: Unable to reopen NR");
					}
			    } catch (SQLException ex) {
		        	req.setAttribute("userMessage", "Error: Unable to reopen NR, " + ex.getMessage());
			    } finally {
			    	try {
				    	cstmt.close();
				    	conn.close();
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: reopening NR, " + ex.getMessage());
				    } finally {
			        	req.removeAttribute("buttonPressed");
				    }
			    }
	    		
	    	}
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
	}
}
