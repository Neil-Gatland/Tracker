package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.User;
import com.devoteam.tracker.model.UserRole;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.UtilBean;

public class UserRoleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7975794158486334219L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		HttpSession session = req.getSession(false);
		if (session == null) {
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
  	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/logon.jsp"+ran);
  	      	dispatcher.forward(req,resp);
		} else {
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			if (thisU.getUserType().equalsIgnoreCase(User.USER_TYPE_CUSTOMER)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.CUSTOMER_MENU);
	  	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/customerMenu.jsp"+ran);
	  	      	dispatcher.forward(req,resp);
			} else {
				try {
					ArrayList<UserRole> userRoles = new ArrayList<UserRole>();
					Connection conn = DriverManager.getConnection(url);
					CallableStatement cstmt = conn.prepareCall("{call GetUserRoles(?)}");
					cstmt.setLong(1, thisU.getUserId());
					if (cstmt.execute()) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							userRoles.add(new UserRole(rs.getString(2), rs.getString(3)));
						}
						thisU.setUserRoles(userRoles);
						session.setAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION, thisU);
					}
					cstmt.close();
					conn.close();
					if (userRoles.size() == 0) {
			        	req.setAttribute("userMessage", 
			        			"This user has no assigned roles");
			  	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/logon.jsp"+ran);
			  	      	dispatcher.forward(req,resp);
			  	    } else  {
			  	    	String destination = "/workQueues.jsp", title = ServletConstants.WORK_QUEUES;
						if (thisU.hasUserRole(UserRole.ROLE_B_O_ENGINEER)) {
							destination = "/backOffice.jsp";
							title = ServletConstants.BACK_OFFICE;
						} else if (thisU.hasUserRole(UserRole.ROLE_FIELD_ENGINEER)) {
							destination = "/homeFE.jsp";
							title = ServletConstants.HOME_FE;
						}
						if (thisU.getUserType().equals(User.USER_TYPE_DEVOTEAM)) {
							UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
							String homescreen = uB.getHomeScreen(thisU.getFullname());
							if (homescreen.equals("Back Office")) {
								destination = "/backOffice.jsp";
								title = ServletConstants.BACK_OFFICE;
							} else if (homescreen.equals("Scheduling")) {
								destination = "/scheduleView.jsp";
								title = ServletConstants.SCHEDULE_VIEW;
							} else if (homescreen.equals("Analytics")) {
								destination = "/dataAnalytics.jsp";
								title = ServletConstants.DATA_ANALYTICS;
							} else if (homescreen.equals("Reporting")) {
								destination = "/clientReporting.jsp";
								title = ServletConstants.CLIENT_REPORTING;
							} else if (homescreen.equals("Site Search")) {
								destination = "/siteSearch.jsp";
								title = ServletConstants.SITE_SEARCH;
							} else if (homescreen.equals("Live Dashboard")) {
								destination = "/liveDashboard.jsp";
								title = ServletConstants.LIVE_DASHBOARD;
							} else if (homescreen.equals("CRQ/Access")) {
								destination = "/crqAccess.jsp";
								title = ServletConstants.CRQ_ACCESS;
							}
						}
						session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, title);
			  	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
			  	      	dispatcher.forward(req,resp);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					req.setAttribute("userMessage", "Error: UserRoleServlet, " + e.getMessage());
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/logon.jsp"+ran);
					dispatcher.forward(req,resp);
				}
			}
		}
	}
}