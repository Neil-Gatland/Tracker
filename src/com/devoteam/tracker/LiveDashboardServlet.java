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
import com.devoteam.tracker.util.UtilBean;

public class LiveDashboardServlet extends HttpServlet {

	private static final long serialVersionUID = -8608740790298618637L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/liveDashboard.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid user id and password");
		} else {
			session.setAttribute("prevScreen", "liveDashboard");
		}
		String selectedAction = req.getParameter("selectedAction");
		User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
		if ((selectedAction.equals("show")) || (selectedAction.equals("hide"))) {				
			String hideProject = "N";
			if ((selectedAction.equals("hide"))) {
				hideProject="Y";
			}
			session.setAttribute("hideProject",hideProject);
			String message = "", ok = "";
			String display = "inline";
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
	    	try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call UpdateDisplayProject(?,?)}");
		    	cstmt.setString(1, thisU.getFullname());
		    	cstmt.setString(2, hideProject);
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						ok = rs.getString(1);
						if (ok.equals("N")) {
							message = message+"Failed to set Hide_Project in UpdateDisplayProject()";
						}
					}
				} else {
					message= message+"not found";
				}
		    } catch (Exception ex) {
		    	message = message+"Error in UpdatetDisplayProject(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	session.setAttribute("userMessage", message );
		    	try {
		    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    }
		    } 
		}	     
		if ((selectedAction.equals("rewind")) || (selectedAction.equals("go"))) {
			UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
			if (selectedAction.equals("rewind")) {
				String updateResult = uB.rewindProject(thisU.getFullname());
				if (updateResult.equals("Y")) {
					updateResult = uB.rewindProject(thisU.getFullname());
				}
				if (updateResult.equals("Y")) {
					session.setAttribute("userMessage", "");
				} else {
					session.setAttribute("userMessage", "Failed going back to previous project");
				}
			} else {
				String selectedProject = req.getParameter("selectedProject");
				String updateResult = uB.gotoProject(selectedProject,thisU.getFullname());
				if (updateResult.equals("Y")) {
					session.setAttribute("userMessage", "");
				} else {
					session.setAttribute("userMessage", "Failed going to selected project");
				}
			}			    		
		}		
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
	}
	
	
}
