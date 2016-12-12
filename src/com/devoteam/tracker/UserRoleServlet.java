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
			/*} else if (thisU.getUserType().equalsIgnoreCase(User.USER_TYPE_THIRD_PARTY)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.HOME_FE);
	  	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/homeFE.jsp"+ran);
	  	      	dispatcher.forward(req,resp);*/
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
							destination = "/homeBO.jsp";
							title = ServletConstants.HOME_BO;
						} else if (thisU.hasUserRole(UserRole.ROLE_FIELD_ENGINEER)) {
							destination = "/homeFE.jsp";
							title = ServletConstants.HOME_FE;
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
