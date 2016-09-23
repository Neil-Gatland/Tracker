package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;

public class PasswordChangeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4773199893627949087L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession(false);
		String destination = "/passwordChange.jsp";
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			String fromScreen = (String)session.getAttribute("fromScreen");
			//String fromScreen = req.getParameter("fromScreen");
			if (req.getParameter("buttonPressed").equals("cancel")) {
				if (fromScreen == null) {
					destination = "/logon.jsp";
		        	req.setAttribute("userMessage", 
		        			"You must change your password before you can continue");
				} else {
					destination = "/" + fromScreen;
					session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, (String)session.getAttribute("fromScreenTitle"));
		        	req.setAttribute("userMessage", 
		        			"Password not changed");
				}
				//dispatcher.forward(req,resp);
			} else {
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		        String oldPassword = req.getParameter("oldPassword");
		        String newPassword = req.getParameter("newPassword");
		        String confPassword = req.getParameter("confPassword");
				Pattern all = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{6,20})");
				//@#$%
		        if (oldPassword == "" || newPassword == "" || confPassword == "") {
		        	req.setAttribute("userMessage", 
		        			"Error: Please enter all values");
					//dispatcher.forward(req,resp);
		        } else if (oldPassword.equals(newPassword)) {
		        	req.setAttribute("userMessage", 
		        			"Error: New password cannot be the same as old password");
					//dispatcher.forward(req,resp);
			    } else if (!confPassword.equals(newPassword)) {
		        	req.setAttribute("userMessage", 
		        			"Error: New password and confirmation do not match");
					//dispatcher.forward(req,resp);
				} else if (!all.matcher(newPassword).matches()) {
		        	req.setAttribute("userMessage", 
		        			"Error: New password must be between 6 and 20 characters long and contain at least one upper case, one lower case and one non-alphanumeric character");
					//dispatcher.forward(req,resp);
			    } else {
					try {
						Connection conn = DriverManager.getConnection(url);
						CallableStatement cstmt = conn.prepareCall("{call UpdateUserPassword(?,?,?,?)}");
						cstmt.setLong(1, thisU.getUserId());
						cstmt.setString(2, thisU.getNameForLastUpdatedBy());
						cstmt.setString(3, oldPassword);
						cstmt.setString(4, newPassword);
						boolean found = cstmt.execute();
						boolean success = false;
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								int ret = rs.getInt(1);
								if (ret == -1) {
						        	req.setAttribute("userMessage", 
						        			"Old password is incorrect! Try again!");
								} else if (ret == -2) {
							        	req.setAttribute("userMessage", 
							        			"New password must differ from previous passwords! Try again!");
								} else if (ret == 0) {
									//dispatcher = getServletContext().getRequestDispatcher("/success.jsp");
						        	session.setAttribute("userMessage", 
						        			"Password changed");
									//dispatcher.forward(req,resp);
						        	success = true;
								} else {
						        	req.setAttribute("userMessage", 
						        			"Error: Unable to change password");
								}
							} else {
					        	req.setAttribute("userMessage", 
					        			"Error: Unable to change password");
							}
						} else {
				        	req.setAttribute("userMessage", 
				        			"Error: Unable to change password");
						}
			
						cstmt.close();
						conn.close();
						if (success) {
							if (fromScreen == null) {
								destination = "/userRole";
							} else {
								session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, (String)session.getAttribute("fromScreenTitle"));
								destination = "/" + fromScreen;
							}
						} 
						//dispatcher.forward(req,resp);
					} catch (Exception e) {
					      e.printStackTrace();
					      req.setAttribute("userMessage", "Error: " + e.getMessage());
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
