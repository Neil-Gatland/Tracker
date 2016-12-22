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

public class ClientReportingServlet extends HttpServlet {
	
	private static final long serialVersionUID = -2611616769857903795L;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/clientReporting.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			session.setAttribute("prevScreen", "clientReporting");
		}
		String action = req.getParameter("selectedAction");
		String year = req.getParameter("selectedYear");	
		String month = req.getParameter("selectedMonth");
		String day = req.getParameter("selectedDay");
		String week = req.getParameter("selectedWeek");
		String moveDate = req.getParameter("moveDate");
		String project = req.getParameter("selectedProject");
		if ((moveDate.equals("back"))||(moveDate.equals("forward"))) {
			String dateAction = "month";
			if (action.endsWith("Day")) {
				dateAction = "day";
			} else if (action.endsWith("Week")){
				dateAction = "week";
			}
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			String message = null;
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call MoveDate(?,?,?,?,?,?)}");
		    	cstmt.setString(1, moveDate);
		    	cstmt.setString(2, dateAction);
		    	cstmt.setString(3, year);
		    	cstmt.setString(4, month);
		    	cstmt.setString(5, day);
		    	cstmt.setString(6, week);
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						year = rs.getString(1);
						month = rs.getString(2);
						day = rs.getString(3);
						week = rs.getString(4);
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in MoveDate(): " + ex.getMessage();
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
		req.setAttribute("action", action);
		req.setAttribute("year", year);
		req.setAttribute("month", month);
		req.setAttribute("day", day);
		req.setAttribute("week", week);
		req.setAttribute("project",project);
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	  	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	  	dispatcher.forward(req,resp);
	    }
	}
