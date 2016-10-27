package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.User;
import com.devoteam.tracker.model.UserAdminListItem;
import com.devoteam.tracker.model.UserRole;
import com.devoteam.tracker.util.Option;
import com.devoteam.tracker.util.ServletConstants;

public class UserAdministrationServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1442741890591540892L;

	/**
	 * 
	 */
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/userAdministration.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			String buttonPressed = req.getParameter("buttonPressed");
			String userId = req.getParameter("userId");
			String userName = req.getParameter("userName");
			String userType = req.getParameter("userType");
			String userEmail = req.getParameter("userEmail");
			String jobType = req.getParameter("jobType");
	    	req.setAttribute("buttonPressed", buttonPressed);
	    	req.setAttribute("userId", userId);
	    	req.setAttribute("userStatus", req.getParameter("userStatus"));
	    	req.setAttribute("userName", userName);
	    	req.setAttribute("userType", userType);
	    	req.setAttribute("userEmail", userEmail);
	    	req.setAttribute("jobType", jobType);
			if (buttonPressed.equals("amendUserStatusSubmit")) {
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdateUserStatus(?,?,?)}");
					cstmt.setLong(1, Long.parseLong(userId));
					cstmt.setString(2, req.getParameter("selectUserStatus"));
					cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					cstmt.execute();
		        	req.setAttribute("userMessage", "Status amended for user " + userName);
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: unable to amend user status, " + ex.getMessage());
		        	req.removeAttribute("buttonPressed");
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    }
			    } 
			} else if (buttonPressed.equals("addUserJobTypeSubmit")) {
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call AddUserJobType(?,?,?)}");
					cstmt.setLong(1, Long.parseLong(userId));
					cstmt.setString(2, jobType);
					cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							if (!rs.getString(1).equalsIgnoreCase("Y")) {
								throw new Exception("negative return code from AddUserJobType()");
							}
						}
					}
		        	req.setAttribute("userMessage", "Job type "+jobType+" added for user " + userName);
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: unable to add user job type, " + ex.getMessage());
		        	req.removeAttribute("buttonPressed");
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    }
			    } 
			} else if (buttonPressed.equals("deleteUserJobTypeSubmit")) {
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call DeleteUserJobType(?,?)}");
					cstmt.setLong(1, Long.parseLong(userId));
					cstmt.setString(2, jobType);
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							if (!rs.getString(1).equalsIgnoreCase("Y")) {
								throw new Exception("negative return code from DeleteUserJobType()");
							}
						}
					}
		        	req.setAttribute("userMessage", "Job type "+jobType+" deleted for user " + userName);
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: unable to delete user job type, " + ex.getMessage());
		        	req.removeAttribute("buttonPressed");
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    }
			    } 
			} else if (buttonPressed.equals("amendUserEmailSubmit")) {
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdateUserEmail(?,?,?)}");
					cstmt.setLong(1, Long.parseLong(userId));
					cstmt.setString(2, req.getParameter("email"));
					cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					cstmt.execute();
		        	req.setAttribute("userMessage", "Email amended for user " + userName);
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: unable to amend user email, " + ex.getMessage());
		        	req.removeAttribute("buttonPressed");
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    }
			    }
			} else if (buttonPressed.equals("amendUserRolesSubmit")) {
				Collection<UserRole> urList = (Collection<UserRole>) session.getAttribute(ServletConstants.USER_ROLE_COLLECTION_NAME_IN_SESSION);
				boolean rolesChanged = false;
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	conn.setAutoCommit(false);
					for (Iterator<UserRole> it = urList.iterator(); it.hasNext(); ) {
						UserRole uR = it.next();
						boolean checkedOnScreen = req.getParameter("check" + uR.getRole()) != null;
						if (uR.getChecked() != checkedOnScreen) {
							rolesChanged = true;
					    	cstmt = conn.prepareCall("{call UpdateUserRole(?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(userId));
							cstmt.setString(2,uR.getRole());
							cstmt.setString(3, null); //FE Capability - TBA
							cstmt.setString(4, thisU.getNameForLastUpdatedBy());
							cstmt.setString(5, checkedOnScreen?"Y":"N");
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (!rs.getString(1).equalsIgnoreCase("Y")) {
										throw new Exception("negative return code from UpdateUserRole()");
									}
								}
							}
						}
					}
					conn.commit();
					if (rolesChanged) {
						req.setAttribute("userMessage", "Roles amended for user " + userName);
					} else {
						req.setAttribute("userMessage", "Roles have not changed for user " + userName);
					}
			    } catch (Exception ex) {
			    	try {
			    		conn.rollback();
				    } catch (SQLException ex2) {
				    	ex2.printStackTrace();
				    } finally {
				    	req.setAttribute("userMessage", "Error: unable to amend user roles, " + ex.getMessage());
				    }
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    }
			    }
			} else if (buttonPressed.equals("resetPwd")) {
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call ResetUserPassword(?,?)}");
					cstmt.setLong(1, Long.parseLong(userId));
					cstmt.setString(2, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					String ret = null;
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							ret = rs.getString(1);
						}
					}
					if ((ret == null) || (ret.equalsIgnoreCase("fail"))) {
			        	req.setAttribute("userMessage", "Error: unable to reset user password");
					} else {
						req.setAttribute("userMessage", "Password for user " + userName + " reset to " + ret);
					}
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: unable to reset user password, " + ex.getMessage());
		        	req.removeAttribute("buttonPressed");
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    }
			    } 
			} else if (buttonPressed.startsWith("addUserSubmit")) {
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
				String userFirstName = req.getParameter("userFirstName");
				String userSurname = req.getParameter("userSurname");
				String newEmail = req.getParameter("newEmail");
				boolean nameExists = false;
				boolean useSameName = false;
				if (!buttonPressed.equals("addUserSubmitConfirm")) {
				    try {
				    	conn = DriverManager.getConnection(url);
				    	cstmt = conn.prepareCall("{call CheckUserName(?,?)}");
						cstmt.setString(1, userFirstName);
						cstmt.setString(2, userSurname);
						boolean found = cstmt.execute();
						String ret = null;
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							nameExists = rs.next();
						}
				    } catch (Exception ex) {
			        	req.setAttribute("userMessage", "Error: unable to check user name, " + ex.getMessage());
			        	req.removeAttribute("buttonPressed");
				    } finally {
				    	try {
				    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
				    		if ((conn != null) && (!conn.isClosed())) conn.close();
					    } catch (SQLException ex) {
					    	ex.printStackTrace();
					    }
				    } 
				} else {
					useSameName = true;
				}
				String selectUserType = req.getParameter("selectUserType");
				String selectThirdParty = req.getParameter("selectThirdParty");
				String[] selectCustomerId = req.getParameterValues("selectCustomerId");
				req.setAttribute("userFirstName", userFirstName);
				req.setAttribute("userSurname", userSurname);
				req.setAttribute("userEmail", newEmail);
				req.setAttribute("selectUserType", selectUserType);
				req.setAttribute("selectThirdParty", selectThirdParty);
				req.setAttribute("selectCustomerId", selectCustomerId);
				ArrayList<Long> customerIds = new ArrayList<Long>();
				if (!selectUserType.equals(User.USER_TYPE_DEVOTEAM)) {
					long custId = 0;
					if (selectCustomerId != null) {
						for(int i = 0; i < selectCustomerId.length; i++) {
							try {
								custId = Long.parseLong(selectCustomerId[i].substring(selectCustomerId[i].indexOf("|")+1));
							} catch (NumberFormatException ex) {
								custId = -1; 
							} finally {
								customerIds.add(new Long(custId));
							}
						}
		
					}
				}
				if (nameExists) {
			    	req.setAttribute("buttonPressed", "confirmAddUserSubmit");
				} else {
				    try {
				    	conn = DriverManager.getConnection(url);
				    	conn.setAutoCommit(false);
				    	cstmt = conn.prepareCall("{call AddUser(?,?,?,?,?,?)}");
						cstmt.setString(1, selectUserType);
						cstmt.setString(2, userSurname);
						cstmt.setString(3, userFirstName);
						cstmt.setString(4, useSameName?"Y":"N");
						cstmt.setString(5, thisU.getNameForLastUpdatedBy());
						cstmt.setString(6, newEmail);
						boolean found = cstmt.execute();
						long newUserId = -1;
						String newPassword = null;
						String newSuffix = null;
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								newUserId = rs.getLong(1);
								newPassword = rs.getString(2);
								newSuffix = rs.getString(3);
							}
						}
						if (newUserId == -1) {
				        	req.setAttribute("userMessage", "Error: unable to add new user");
						} else if (newUserId == -2) {
					        	req.setAttribute("userMessage", "Error: unable to add new user (suffix limit reached)");
						} else {
							req.setAttribute("userMessage", "New user " + userFirstName + " " + userSurname +
									(useSameName?" "+newSuffix:"") + " (id: " + newUserId + ") created with password " + 
									newPassword);
						}
						cstmt.close();
						if ((newUserId > 0) && (!selectUserType.equals(User.USER_TYPE_DEVOTEAM))) {
							if (selectUserType.equals(User.USER_TYPE_CUSTOMER)) {
						    	cstmt = conn.prepareCall("{call Add_Customer_User(?,?,?)}");
								cstmt.setLong(1, newUserId);
								cstmt.setLong(2, customerIds.get(0).longValue());
								cstmt.setString(3, thisU.getNameForLastUpdatedBy());
								found = cstmt.execute();
								if (found) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										if (!rs.getString(1).equalsIgnoreCase("Y")) {
											throw new Exception("negative return code from Add_Customer_User()");
										}
									}
								}
								cstmt.close();
							} else { //(selectUserType.equals(User.USER_TYPE_THIRD_PARTY))
						    	cstmt = conn.prepareCall("{call Add_Third_Party_User(?,?,?)}");
								cstmt.setLong(1, newUserId);
								cstmt.setLong(2, Long.parseLong(selectThirdParty));
								cstmt.setString(3, thisU.getNameForLastUpdatedBy());
								found = cstmt.execute();
								if (found) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										if (!rs.getString(1).equalsIgnoreCase("Y")) {
											throw new Exception("negative return code from Add_Third_Party_User()");
										}
									}
								}
								cstmt.close();
								for (Iterator<Long> it =customerIds.iterator(); it.hasNext(); ) {
							    	cstmt = conn.prepareCall("{call Add_Customer_User(?,?,?)}");
									cstmt.setLong(1, newUserId);
									cstmt.setLong(2, it.next().longValue());
									cstmt.setString(3, thisU.getNameForLastUpdatedBy());
									found = cstmt.execute();
									if (found) {
										ResultSet rs = cstmt.getResultSet();
										if (rs.next()) {
											if (!rs.getString(1).equalsIgnoreCase("Y")) {
												throw new Exception("negative return code from Add_Customer_User()");
											}
										}
									}
									cstmt.close();
								}
							}
						}
						conn.commit();
						
				    } catch (Exception ex) {
				    	try {
				    		conn.rollback();
					    } catch (SQLException ex2) {
					    	ex2.printStackTrace();
					    }
			        	req.setAttribute("userMessage", "Error: unable to add new user, " + ex.getMessage());
			        	req.removeAttribute("buttonPressed");
				    } finally {
				    	try {
				    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
				    		if ((conn != null) && (!conn.isClosed())) conn.close();
					    } catch (SQLException ex) {
					    	ex.printStackTrace();
					    }
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
