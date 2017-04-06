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
import com.devoteam.tracker.util.StringUtil;
import com.devoteam.tracker.util.UtilBean;

public class ScheduleViewServlet extends HttpServlet{

	private static final long serialVersionUID = -14129465518271187L;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/scheduleView.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			session.setAttribute("prevScreen", "scheduleView");
		}
		session.removeAttribute("action");
		session.removeAttribute("year");
		session.removeAttribute("month");
		session.removeAttribute("day");
		session.removeAttribute("week");
		session.removeAttribute("project");
		session.removeAttribute("upgradeType");
		session.removeAttribute("site");
		session.removeAttribute("nrId");
		session.removeAttribute("siteStatus");
		session.removeAttribute("startDate");
		session.removeAttribute("endDate");
		String action = req.getParameter("selectedAction");
		String initialEntry = req.getParameter("initialEntry");	
		String year = req.getParameter("selectedYear");	
		String month = req.getParameter("selectedMonth");
		String day = req.getParameter("selectedDay");
		String week = req.getParameter("selectedWeek");
		String moveDate = req.getParameter("moveDate");
		String project = req.getParameter("selectEmailCopyProject");
		String upgradeType = req.getParameter("selectEmailCopyUpgradeType");
		String site = req.getParameter("selectEmailCopySite");
		String nrId = req.getParameter("selectEmailCopyNRId");
		String siteStatus = req.getParameter("selectSiteStatus");
		String startDate = req.getParameter("selectedStartDate");
		String endDate = req.getParameter("selectedEndDate");
		String showTH = req.getParameter("showTH");
		String snrId = req.getParameter("snrId");
		String rescheduleMessage = req.getParameter("rescheduleMessage");
		String rescheduleAction = req.getParameter("rescheduleAction");
		String scheduledDate = req.getParameter("scheduledDate");
		String rescheduledDate = req.getParameter("rescheduledDate");
		String commentary = req.getParameter("commentary");
		String column = req.getParameter("column");
		String currentValue = req.getParameter("currentValue");
		String updatedValue = req.getParameter("updatedValue");
		String userId = req.getParameter("userId");
		String role = req.getParameter("role");
		String displayAddRole = req.getParameter("displayAddRole");
		String displayChgRole = req.getParameter("displayChgRole");
		String rank = req.getParameter("rank");		
		String storedRank = req.getParameter("storedRank");		
		String row = req.getParameter("row");				
		String selectedSiteList = req.getParameter("siteList").trim();
		String multiSiteEdit = req.getParameter("multiSiteEdit");
		String editList = req.getParameter("editList");
		req.setAttribute("userMessage","&nbsp;");
		User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
		UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
		int maxEdits = uB.getMaxScheduleEdits();
		String message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
		if ((moveDate.equals("back"))||
			(moveDate.equals("forward"))) {
			String dateAction = "month";
			if (action.endsWith("Day")) {
				dateAction = "day";
			} else if (action.endsWith("Week")){
				dateAction = "week";
			}
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
		if (!action.equals("search")) {
			try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetStartAndEndDate(?,?,?,?,?)}");
		    	cstmt.setString(1, action);
		    	cstmt.setString(2, year);
		    	cstmt.setString(3, month);
		    	cstmt.setString(4, day);
		    	cstmt.setString(5, week);
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						startDate = rs.getString(1);
						endDate = rs.getString(2);
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in GetStartAndEndDate(): " + ex.getMessage();
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
		if (action.equals("multiSearch")) {
			StringUtil sU = new StringUtil();
			String formattedSiteList = sU.formatttedSiteList(selectedSiteList);
			if (formattedSiteList.equals("INVALID")) {
				req.setAttribute("userMessage","Error: Multiple site search list not valid, can only contain numbers, spaces and line returns");
				req.setAttribute("formattedSiteList", "" );
			} else {
				req.setAttribute("formattedSiteList", formattedSiteList );
			}			
		} else {
			req.setAttribute("formattedSiteList", "" );			
		}
		if (rescheduleAction.equals("updateSiteTechnology")) {
			try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call UpdateSiteTechnology(?,?,?,?)}");
		    	cstmt.setString(1, snrId);
		    	cstmt.setString(2, column);
		    	cstmt.setString(3, currentValue);
		    	cstmt.setString(4, thisU.getNameForLastUpdatedBy());
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						String techAction = "added";
						if (currentValue.equals("x")) {
							techAction = "removed";
						}
						String techName = rs.getString(1);
						if (techName.equals("N")) {
							req.setAttribute("userMessage","Error: Technology "+techName+" failed to be "+techAction+" for site");
						} else {
							req.setAttribute("userMessage","Technology "+techName+" "+techAction+" for site");						}
					}
				}
		    } catch (Exception ex) {
		    	rescheduleMessage = "Error in UpdateSiteTechnology(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	try {
		    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    } 
		    }	
			rescheduleAction = "";
		} else if (rescheduleAction.equals("updateSiteConfirm")) {
			if (column.equals("hardwareVendor")) {
				try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdateHardwareVendor(?,?,?)}");
			    	cstmt.setString(1, snrId);
			    	cstmt.setString(2, updatedValue);
			    	cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								req.setAttribute("userMessage","Hardware vendor updated");
							} else {
								req.setAttribute("userMessage","Error: Failed to update hardware vendor");
							}
							rescheduleAction = "";
						}
					}
			    } catch (Exception ex) {
			    	rescheduleMessage = "Error in UpdateHardwareVendor(): " + ex.getMessage();
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
				try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdateSiteEngineer(?,?,?,?,?)}");
			    	cstmt.setLong(1, Long.parseLong(snrId));
			    	cstmt.setLong(2, Long.parseLong(updatedValue));
			    	cstmt.setString(3, (column.equals("boEngineer")?"BO Engineer":"Field Engineer"));
			    	cstmt.setString(4, currentValue);
			    	cstmt.setString(5, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								req.setAttribute("userMessage","Engineer updated");
							} else {
								req.setAttribute("userMessage","Error: Failed to update engineer");
							}
							rescheduleAction = "";
						}
					}
			    } catch (Exception ex) {
			    	rescheduleMessage = "Error in UpdateSiteEngineer(): " + ex.getMessage();
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
			    	cstmt.setString(2, updatedValue);
			    	cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								req.setAttribute("userMessage","Upgrade Type updated");
							} else {
								req.setAttribute("userMessage","Error: Failed to update upgrade type");
							}
							rescheduleAction = "";
						}
					}
			    } catch (Exception ex) {
			    	rescheduleMessage = "Error in UpdateUpgradeType(): " + ex.getMessage();
			    	ex.printStackTrace();
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    } 
			    }
			} else if (column.equals("scheduledDate")) {
				try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdateScheduledDate(?,?,?)}");
			    	cstmt.setLong(1, Long.parseLong(snrId));
			    	cstmt.setString(2, updatedValue);
			    	cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								req.setAttribute("userMessage","Scheduled date updated");
							} else if (rs.getString(1).equals("S")) {
								req.setAttribute("userMessage"," ");
							} else {
								req.setAttribute("userMessage","Error: Failed to update scheduled date");
							}
							rescheduleAction = "";
						}
					} 
				} catch (Exception ex) {
			    	rescheduleMessage = "Error in UpdateScheduledDate(): " + ex.getMessage();
			    	ex.printStackTrace();
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    } 
			    }
			} else if (column.equals("project")) {
				try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdateScheduledProject(?,?,?)}");
			    	cstmt.setLong(1, Long.parseLong(snrId));
			    	cstmt.setString(2, updatedValue);
			    	cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								req.setAttribute("userMessage","Project updated");
							} else {
								req.setAttribute("userMessage","Error: Failed to update project");
							}
							rescheduleAction = "";
						}
					}
			    } catch (Exception ex) {
			    	rescheduleMessage = "Error in UpdateScheduledProject(): " + ex.getMessage();
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
			rescheduleAction = "";
	    } else if (rescheduleAction.equals("reschedule")) {
			try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call Reschedule_SNR(?,?,?,?)}");
		    	cstmt.setString(1, snrId);
		    	cstmt.setString(2, mySQLDateFormat(rescheduledDate));
		    	cstmt.setString(3, commentary);
		    	cstmt.setString(4, thisU.getNameForLastUpdatedBy());
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						if (rs.getString(1).equals("Y")) {
							rescheduleMessage = "Site has been rescheduled";
						} else {
							rescheduleMessage = "Failed to rescheduled site";
						}
						rescheduleAction = "";
						scheduledDate = rescheduledDate;
					}
				}
		    } catch (Exception ex) {
		    	rescheduleMessage = "Error in Reschedule_SNR(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	try {
		    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    } 
		    }	
		} else if (rescheduleAction.equals("schedule")) {
			try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call ScheduleSNR(?,?,?,?,?)}");
		    	cstmt.setLong(1, Long.parseLong(snrId));
		    	String mysqlScheduleDate = 
		    			rescheduledDate.substring(6, 10)+"-"+
    					rescheduledDate.substring(3, 5)+"-"+
    					rescheduledDate.substring(0, 2);
		    	cstmt.setString(2, mysqlScheduleDate);
		    	cstmt.setString(3, "IP Ran");
		    	cstmt.setString(4, commentary);
		    	cstmt.setString(5, thisU.getNameForLastUpdatedBy());
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						if (rs.getString(1).equals("Y")) {
							rescheduleMessage = "Site is now scheduled";
						} else {
							rescheduleMessage = "Failed to schedule site";
						}
						rescheduleAction = "";
					}
				}
		    } catch (Exception ex) {
		    	rescheduleMessage = "Error in Schedule_SNR(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	try {
		    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    } 		    }
		    } else if ((rescheduleAction.equals("cancelSchedule"))||(rescheduleAction.equals("cancelScheduleDirect"))) {
				try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call CancelSchedule_SNR(?,?)}");
			    	cstmt.setString(1, snrId);
			    	cstmt.setString(2, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						while (rs.next()) {
							if (rs.getString(1).equals("Y")) {
								rescheduleMessage = "Site is no longer scheduled";
								if (rescheduleAction.equals("cancelScheduleDirect")) {
									snrId = "";
									req.setAttribute("userMessage","Site is no longer scheduled");
								}
							} else {
								rescheduleMessage = "Failed to cancel scheduling for site";
							}
							rescheduleAction = "";
						}
					}
			    } catch (Exception ex) {
			    	rescheduleMessage = "Error in CancelSchedule_SNR(): " + ex.getMessage();
			    	ex.printStackTrace();
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();
				    } catch (SQLException ex) {
				    	ex.printStackTrace();
				    } 
			    }	
		} else if (rescheduleAction.equals("delEng")) {
			try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call RemoveSNRUSerRole(?,?,?)}");
		    	cstmt.setString(1, snrId);
		    	cstmt.setString(2, userId);
		    	cstmt.setString(3, role);
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						if (rs.getString(1).equals("Y")) {
							rescheduleMessage = "Deleted "+role;
						} else {
							rescheduleMessage = "Failed to delete "+role;
						}
						rescheduleAction = "";
					}
				}
		    } catch (Exception ex) {
		    	rescheduleMessage = "Error in RemoveSNRUSerRole(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	try {
		    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    } 
		    }	
		} else if (rescheduleAction.equals("addEngSubmit")) {
			try {
				conn = DriverManager.getConnection(url);
				cstmt = conn.prepareCall("{call AddSNRUserRole(?,?,?,?,?,?)}");
				cstmt.setLong(1, Long.parseLong(snrId));
				cstmt.setLong(2, Long.parseLong(req.getParameter("selectAvailableUsersForRole")));
				cstmt.setString(3, role);
				cstmt.setLong(4, req.getParameter("selectThirdParty")==null?-1:Long.parseLong(req.getParameter("selectThirdParty")));
				cstmt.setLong(5, req.getParameter("selectFENumber")==null?-1:Long.parseLong(req.getParameter("selectFENumber")));
				cstmt.setString(6, thisU.getNameForLastUpdatedBy());
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						if (rs.getString(1).equals("Y")) {
							rescheduleMessage = "Added "+role;
						} else {
							rescheduleMessage = "Failed to add "+role;
						}
						rescheduleAction = "";
					}
				}
		    } catch (Exception ex) {
		    	rescheduleMessage = "Error in AddeSNRUserRole(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	try {
		    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    } 
		    }	
		} else if (rescheduleAction.equals("chgEngSubmit")) {
			try {
				conn = DriverManager.getConnection(url);
				cstmt = conn.prepareCall("{call ChangeSNRUserRole(?,?,?,?,?,?,?)}");
				cstmt.setLong(1, Long.parseLong(snrId));
				cstmt.setLong(2, Long.parseLong(req.getParameter("userId")));
				cstmt.setLong(3, Long.parseLong(req.getParameter("selectAltAvailableUsersForRole")));
				cstmt.setString(4, role);
				cstmt.setLong(5, req.getParameter("selectAltThirdParty")==null?-1:Long.parseLong(req.getParameter("selectAltThirdParty")));
				cstmt.setLong(6, req.getParameter("selectAltFENumber")==null?-1:Long.parseLong(req.getParameter("selectAltFENumber")));
				cstmt.setString(7, thisU.getNameForLastUpdatedBy());
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						if (rs.getString(1).equals("Y")) {
							rescheduleMessage = "Changed "+role;
						} else {
							rescheduleMessage = "Failed to change "+role;
						}
						rescheduleAction = "";
					}
				}
		    } catch (Exception ex) {
		    	rescheduleMessage = "Error in ChangeSNRUserRole(): " + ex.getMessage();
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
		// process any multiple edits
		if (!editList.equals("")) {
			int expectedEdits = 0, actualEdits = 0;
			String[][] edits = decodeEdits(editList,maxEdits);
			for( int editCount=0; editCount<maxEdits; editCount++) {
				String snrIdString = edits[editCount][0];
				String editValue = edits[editCount][1];
				String editColumn = edits[editCount][2];
				String originalValue = edits[editCount][3];
				if (!snrIdString.equals("")) {
					// apply the relevant update
					expectedEdits++;
					if (editColumn.equals("scheduledDate")) {
						try {
					    	conn = DriverManager.getConnection(url);
					    	cstmt = conn.prepareCall("{call UpdateScheduledDate(?,?,?)}");
					    	cstmt.setLong(1, Long.parseLong(snrIdString));
					    	cstmt.setString(2, editValue);
					    	cstmt.setString(3, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								while (rs.next()) {
									if (rs.getString(1).equals("Y")) {
										actualEdits++;
									} else if (rs.getString(1).equals("S")) {
										actualEdits++;
									} 
								}
							} 
						} catch (Exception ex) {
					    	rescheduleMessage = "Error in UpdateScheduledDate(): " + ex.getMessage();
					    	ex.printStackTrace();
					    } finally {
					    	try {
					    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
					    		if ((conn != null) && (!conn.isClosed())) conn.close();
						    } catch (SQLException ex) {
						    	ex.printStackTrace();
						    } 
					    }
					} else if ((editColumn.equals("boEngineer"))||
							(editColumn.equals("feEngineer"))) {
						try {
					    	conn = DriverManager.getConnection(url);
					    	cstmt = conn.prepareCall("{call UpdateSiteEngineer(?,?,?,?,?)}");
					    	cstmt.setLong(1, Long.parseLong(snrIdString));
					    	cstmt.setLong(2, Long.parseLong(editValue));
					    	cstmt.setString(3, (editColumn.equals("boEngineer")?"BO Engineer":"Field Engineer"));
					    	cstmt.setString(4, originalValue);
					    	cstmt.setString(5, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								while (rs.next()) {
									if (rs.getString(1).equals("Y")) {
										actualEdits++;
									} 
								}
							}
					    } catch (Exception ex) {
					    	rescheduleMessage = "Error in UpdateSiteEngineer(): " + ex.getMessage();
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
				}
				if (actualEdits==expectedEdits) {
					req.setAttribute("userMessage",actualEdits+" edits successfully applied");
				} else {
					req.setAttribute(
							"userMessage",
							"Error: Only "+actualEdits+" applied out of "+expectedEdits+" expected edits");
				}				
			}
		}
		req.setAttribute("action", action);
		req.setAttribute("year", year);
		req.setAttribute("initialEntry", initialEntry);
		req.setAttribute("month", month);
		req.setAttribute("day", day);
		req.setAttribute("week", week);
		req.setAttribute("project", project);
		req.setAttribute("upgradeType", upgradeType);
		req.setAttribute("site", site);
		req.setAttribute("nrId", nrId);
		req.setAttribute("siteStatus", siteStatus);
		req.setAttribute("startDate", startDate);
		req.setAttribute("endDate", endDate);
		req.setAttribute("showTH", showTH);
		req.setAttribute("snrId", snrId);
		req.setAttribute("rescheduleMessage", rescheduleMessage);
		req.setAttribute("rescheduleAction", rescheduleAction);
		req.setAttribute("scheduledDate", scheduledDate);
		req.setAttribute("column", column);
		req.setAttribute("currentValue", currentValue);
		req.setAttribute("updatedValue", updatedValue);
		req.setAttribute("userId", userId);
		req.setAttribute("role", role);
		req.setAttribute("displayAddRole", displayAddRole);
		req.setAttribute("displayChgRole", displayChgRole);
		req.setAttribute("rank", rank);
		req.setAttribute("storedRank", storedRank);
		req.setAttribute("row", row);
		req.setAttribute("selectedSiteList", selectedSiteList.trim());	
		req.setAttribute("multiSiteEdit", multiSiteEdit);
		session.setAttribute("potLoadActive", "N");
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	  	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	  	dispatcher.forward(req,resp);
	    }
	
	private String mySQLDateFormat ( String inDate ) {
		return inDate.substring(6,10) + "-" + inDate.substring(3,5) + "-" + inDate.substring(0,2);
	}
	
	private String[][] decodeEdits ( String editList, int noRows ) {
		if (noRows>0) {
			String[][] edits = new String[noRows][4];
			// initialise table to empty strings
			for (int i=0; i<edits.length; i++) {
				for (int j=0; j< edits[i].length; j++) {
					edits[i][j]="";
				}
			}
			// process edit list and load into array
			int editCount=0, columnCount=0;
			String currentColumn = "";
			for( int i=0; i<editList.length(); i++) {
				String currentChar = editList.substring(i,i+1);
				if (currentChar.equals(":")) {
					edits[editCount][columnCount] = currentColumn;	
					currentColumn = "";
					columnCount++;
				} else if (currentChar.equals("|")) {
					edits[editCount][columnCount] = currentColumn;	
					currentColumn = "";
					columnCount = 0;
					editCount++;
				} else {
					currentColumn = currentColumn + currentChar;
				}
			}
			return edits;
		} else {
			return new String[20][4];
		}
			
		
	}
}
