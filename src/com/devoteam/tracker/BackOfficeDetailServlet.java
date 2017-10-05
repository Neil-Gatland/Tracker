package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.PreCheckList;
import com.devoteam.tracker.model.SNRTechnology;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.UtilBean;

public class BackOfficeDetailServlet extends HttpServlet {
	
	private User thisU;
	private String url;
	private UtilBean uB;

	private static final long serialVersionUID = -5851646884200212922L;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/backOfficeDetail.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			boolean direct = req.getAttribute("buttonPressed") != null;
			session.setAttribute("prevScreen", "backOfficeDetail");
			req.setAttribute("userMessage","&nbsp;");
			String buttonPressed = direct?(String)req.getAttribute("buttonPressed"):req.getParameter("buttonPressed");
			thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			uB = new UtilBean(thisU, destination.substring(1), url);
			String progressItem = req.getParameter("progressItem");
			String currentStatus = req.getParameter("currentStatus");
			String issueOwner = req.getParameter("issueOwner");
			String riskIndicator = req.getParameter("riskIndicator");
			String progressIssue = req.getParameter("progressIssue");
			String closureCode = req.getParameter("closureCode");
			String newStatus = req.getParameter("newStatus");
			String addDetsOpen = req.getParameter("addDetsOpen");
			req.setAttribute("preCheckUpdates", req.getParameter("preCheckUpdates"));
			req.setAttribute( "returnHome", "N" );
			long snrId = Long.parseLong(req.getParameter("snrId"));
			if (buttonPressed.equals("updateSiteProgressItem")) {
				String updateResult = 
						uB.updateProgressItemStatus(
								progressItem, snrId, newStatus, thisU.getNameForLastUpdatedBy());
				req.setAttribute("userMessage", updateResult);
				if (progressItem.equals("closureCode")) {
					updateResult = uB.updateCRQClosureCode(snrId, closureCode, thisU.getNameForLastUpdatedBy());
					if (updateResult.startsWith("Y")) 		
						req.setAttribute("userMessage", "Failed to update CRQ Closure Code");
				}
			} else if (buttonPressed.equals("updateIssueOwner")) {
				String updateResult = 
						uB.updateIssueOwner(snrId, issueOwner, thisU.getNameForLastUpdatedBy());
				if (!updateResult.startsWith("Y"))
					req.setAttribute("userMessage", "Failed to update issue owner");	
			} else if (buttonPressed.equals("updateRiskIndicator")) {
				String updateResult = 
						uB.updateRiskIndicator(snrId, riskIndicator, thisU.getNameForLastUpdatedBy());
				if (!updateResult.startsWith("Y"))
					req.setAttribute("userMessage", "Failed to update risk indicator");	
			} else if (buttonPressed.equals("updateProgressIssue")) {
				String updateResult = 
						uB.updateProgressIssue(snrId, progressIssue, thisU.getNameForLastUpdatedBy());
				if (!updateResult.startsWith("Y"))
					req.setAttribute("userMessage", "Failed to update progress issue");	
			} else if (buttonPressed.equals("updatePreCheck")) {
				String updateList = req.getParameter("preCheckUpdates");
				req.setAttribute("userMessage", actionPreCheckUpdates(req.getParameter("preCheckUpdates")));
				req.setAttribute("preCheckUpdates", "");
			} else if (buttonPressed.startsWith("completePreCheck")) {
				String commentary = req.getParameter("commentary");
				String result =
						uB.completePreCheck(
								snrId, 
								req.getParameter("currentTab").replace(" PreCheck", ""), 
								buttonPressed.replace("completePreCheck",""), 
								commentary, 
								thisU.getNameForLastUpdatedBy());
				if (result.startsWith("Error")) {
					req.setAttribute("userMessage", result);
				} else if (result.equals("N")) {
					req.setAttribute("userMessage", "Error: Precheck not completed");
				} else {
					if (buttonPressed.replace("completePreCheck","").equals("Failed")) {
						req.setAttribute("userMessage", "Precheck failed");
						destination = "/backOffice.jsp";
					} else {
						req.setAttribute("userMessage", "Precheck completed");
					}
				}
			} else if (buttonPressed.equals("AddCommentary")) {
				String commentary = req.getParameter("commentary");
				long precheckId = Long.parseLong(req.getParameter("precheckId"));
				String commentaryType = "Implementation";
				if (req.getParameter("currentTab").contains("PreCheck"))
					commentaryType = "PreCheck";
				if (req.getParameter("currentTab").equals("Performance Monitoring"))
					commentaryType = "Performance";
				req.setAttribute(
						"userMessage",
						uB.addSNRCommentary(
								snrId, 
								precheckId, 
								commentaryType, 
								commentary, 
								thisU.getNameForLastUpdatedBy() ) );
			} else if (buttonPressed.equals("Update Planning and Scripting")) {
				String ef345Date = req.getParameter("ef345ClaimDT");
				String siteDataRequested = req.getParameter("siteDataRequested");
				String idfRequested = req.getParameter("idfRequested");
				String cellsPlanned2G = req.getParameter("cellsPlanned2G");
				String cellsPlanned3G = req.getParameter("cellsPlanned3G");
				String cellsPlanned4G = req.getParameter("cellsPlanned4G");
				String plannedDate = req.getParameter("plannedDate");
				String workDetails = req.getParameter("workDetails");
				req.setAttribute(
						"userMessage", 
						uB.updateScriptingAndPlanning(
								snrId, 
								ef345Date, 
								siteDataRequested, 
								idfRequested, 
								cellsPlanned2G, 
								cellsPlanned3G, 
								cellsPlanned4G, 
								plannedDate, 
								workDetails,
								thisU.getNameForLastUpdatedBy() ) );
			} else if (buttonPressed.equals("Update I and C Integration")) {
				String impStartDate = req.getParameter("impStartDT");
				String impEndDate = req.getParameter("impEndDT");
				String outageStartDate = req.getParameter("outageStartDT");
				String outageEndDate = req.getParameter("outageEndDT");
				String newAlarms = req.getParameter("newAlarms");
				String healthChecks = req.getParameter("healthChecks");
				String hopCompleted = req.getParameter("hopCompleted");
				String hopFilename = req.getParameter("hopFilename");
				String sfrCompleted = req.getParameter("sfrCompleted");
				String incTicketNo = req.getParameter("incTicketNo");
				String ef360Date = req.getParameter("ef360Date");
				String ef390Date = req.getParameter("ef390Date");
				String siteIssues = req.getParameter("siteIssues");
				req.setAttribute(
						"userMessage", 
						uB.updateIAndCIntegration(
								snrId, 
								impStartDate, 
								impEndDate, 
								outageStartDate, 
								outageEndDate, 
								newAlarms, 
								healthChecks, 
								hopCompleted, 
								hopFilename, 
								sfrCompleted, 
								incTicketNo, 
								ef360Date, 
								ef390Date, 
								siteIssues, 
								thisU.getNameForLastUpdatedBy() ));	
			} else if (buttonPressed.equals("Update Performance Monitoring")) {
				String performanceChecks = req.getParameter("performanceChecks");
				String ef400Date = req.getParameter("ef400ClaimDT");
				String ef410Date = req.getParameter("ef410ClaimDT");
				req.setAttribute(
						"userMessage", 
						uB.updatePerformanceMonitoring(
								snrId, 
								performanceChecks,
								ef400Date, 
								ef410Date, 
								thisU.getNameForLastUpdatedBy() ) );
			} else if (buttonPressed.equals("Update Sign Off and HOP")) {
				String hopUploaded = req.getParameter("hopUploaded");
				String sfrUploaded = req.getParameter("sfrUploaded");
				req.setAttribute(
						"userMessage", 
						uB.updateSignOffHOP(
								snrId, 
								hopUploaded,
								sfrUploaded,
								thisU.getNameForLastUpdatedBy() ) );
			} else if (buttonPressed.equals("confirmCompletion")) {
				String implementationStatus = req.getParameter("implementationStatus");
				String abortType = req.getParameter("abortType");
				String implementationEndDT = req.getParameter("implementationEndDT");
				String commentary = req.getParameter("commentary");
				// First validate that completion can occur
				String validationMessage = uB.getCompletionValidationMessage(snrId, implementationStatus, abortType);
				if (validationMessage.equals("OK")) {
					String updateMessage = "";
					if (implementationStatus.equals("Performance IP")) {
						updateMessage = uB.performanceIPSNRScreen(snrId, thisU.getNameForLastUpdatedBy() );
					} else {
						updateMessage = 
								uB.completeSNRScreen(
										snrId, 
										implementationStatus, 
										abortType, 
										implementationEndDT, 
										thisU.getNameForLastUpdatedBy() );
					}
					if (!updateMessage.startsWith("Error:")) {
						if (!commentary.equals("")) {
							uB.addSNRCommentary(
									snrId, 
									-1, 
									"Implementation", 
									commentary, 
									thisU.getNameForLastUpdatedBy() );
						}
						if (!implementationStatus.equals(ServletConstants.STATUS_COMPLETED)) {
							req.setAttribute( "returnHome", "Y" );
						}
					} else {
						
					}
					req.setAttribute( "userMessage", updateMessage );
				}
				else {
					req.setAttribute("userMessage", "Error: "+validationMessage);
				}
			} else if (buttonPressed.equals("toggleTechnology")) {
				long technologyId = Long.parseLong(req.getParameter("technologyId"));
				req.setAttribute(
						"userMessage", 
						uB.toggleTechnology(snrId, technologyId, thisU.getNameForLastUpdatedBy()));
			} else if (buttonPressed.equals("addBOTechnologiesSubmit")) {
				Collection<SNRTechnology> snrTL = 
						(Collection<SNRTechnology>) session.getAttribute(
								ServletConstants.SNR_BO_TECHNOLOGY_ADD_COLLECTION_NAME_IN_SESSION);
				Connection conn = null;
				CallableStatement cstmt = null;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	conn.setAutoCommit(false);
			    	boolean newTechAdded = false;
					for (Iterator<SNRTechnology> it = snrTL.iterator(); it.hasNext(); ) {
						SNRTechnology snrT = it.next();
						boolean checkedOnScreen = req.getParameter("checkTech" + snrT.getTechnologyId()) != null;
						if (checkedOnScreen) {
							newTechAdded = true;
					    	cstmt = conn.prepareCall("{call AddSNRBOTechnology(?,?,?)}");
							cstmt.setLong(1, snrId);
							cstmt.setLong(2,snrT.getTechnologyId());
							cstmt.setString(3, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (!rs.getString(1).equalsIgnoreCase("Y")) {
										throw new Exception("negative return code from Add_SNR_Technology()");
									}
								}
							}
						}
					}
					conn.commit();
					if (newTechAdded) {
						req.setAttribute("userMessage", "Technology list amended");
					} else {
						req.setAttribute("userMessage", "No new technologies added");
					}
			    } catch (Exception ex) {
			    	try {
			    		conn.rollback();
				    } catch (SQLException ex2) {
				    	ex2.printStackTrace();
				    } finally {
				    	req.setAttribute("userMessage", "Error: unable to add SNR BO Technologies, " + ex.getMessage());
				    }
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();					    		
				    } catch (SQLException ex) {
				    	req.setAttribute("userMessage", "Error: adding SNR BO Technologies, " + ex.getMessage());
				    }
			    }
			} else if (buttonPressed.equals("delBOTechnologiesSubmit")) {
				Collection<SNRTechnology> snrTL = 
						(Collection<SNRTechnology>) session.getAttribute(
								ServletConstants.SNR_BO_TECHNOLOGY_DEL_COLLECTION_NAME_IN_SESSION);
				Connection conn = null;
				CallableStatement cstmt = null;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	conn.setAutoCommit(false);
			    	boolean newTechDeleted = false;
					for (Iterator<SNRTechnology> it = snrTL.iterator(); it.hasNext(); ) {
						SNRTechnology snrT = it.next();
						boolean checkedOnScreen = req.getParameter("checkTech" + snrT.getTechnologyId()) != null;
						if (checkedOnScreen) {
							newTechDeleted = true;
					    	cstmt = conn.prepareCall("{call DelSNRBOTechnology(?,?)}");
							cstmt.setLong(1, snrId);
							cstmt.setLong(2,snrT.getTechnologyId());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (!rs.getString(1).equalsIgnoreCase("Y")) {
										throw new Exception("negative return code from DelSNRTechnology()");
									}
								}
							}
						}
					}
					conn.commit();
					if (newTechDeleted) {
						req.setAttribute("userMessage", "Technology list amended");
					} else {
						req.setAttribute("userMessage", "No technology deleted");
					}
			    } catch (Exception ex) {
			    	try {
			    		conn.rollback();
				    } catch (SQLException ex2) {
				    	ex2.printStackTrace();
				    } finally {
				    	req.setAttribute("userMessage", "Error: unable to delete SNR BO Technologies, " + ex.getMessage());
				    }
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
			    		if ((conn != null) && (!conn.isClosed())) conn.close();					    		
				    } catch (SQLException ex) {
				    	req.setAttribute("userMessage", "Error: deleting SNR BO Technologies, " + ex.getMessage());
				    }
			    }
			} else if (buttonPressed.equals("updateHardwareVendor")) {
				String hardwareVendor = req.getParameter("hardwareVendor");
				req.setAttribute(
						"userMessage", 
						uB.updateHardwareVendor(
								snrId, 
								hardwareVendor,
								thisU.getNameForLastUpdatedBy() ) );
			} else if (buttonPressed.equals("updateCRQClosureCode")) {
				String crqClosureCode = req.getParameter("crqCode");
				req.setAttribute(
						"userMessage", 
						uB.updateClosureCode(
								snrId, 
								crqClosureCode,
								thisU.getNameForLastUpdatedBy() ) );
			} else if (buttonPressed.equals("updateTests")) {
				String preTestCallsDone = req.getParameter("preTestCallsDone");
				String postTestCallsDone = req.getParameter("postTestCallsDone");
				req.setAttribute(
						"userMessage", 
						uB.updateTestCallsDone(
								snrId, 
								preTestCallsDone,
								postTestCallsDone,
								thisU.getNameForLastUpdatedBy() ) );
			}
			else if (buttonPressed.equals("changeFE")) {
				Long feUserId = Long.parseLong(req.getParameter("selectAvailableFEEngineers"));
				req.setAttribute(
						"userMessage", 
						uB.updateLeadFE(snrId, feUserId, thisU.getNameForLastUpdatedBy()));
			}
			req.setAttribute("currentTab", req.getParameter("currentTab"));
			req.setAttribute("snrId", req.getParameter("snrId"));
			req.setAttribute("addDetsOpen", req.getParameter("addDetsOpen"));
			req.setAttribute("filterBOEngineer", req.getParameter("filterBOEngineer"));
		} 
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	  	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	  	dispatcher.forward(req,resp);		
	}
	
	String actionPreCheckUpdates(String updatesList) {
		String result = "Error: Precheck not updated";
		boolean success = true;
		long preCheckId = 0;
		List<String> updates = Arrays.asList(updatesList.split("\\s*;\\s*"));
		for (int i=0; i<updates.size(); i++) {
			List<String> updateValues = Arrays.asList(updates.get(i).replace(";", ":").split("\\s*:\\s*"));
			String preCheckItem = "", storageType = "", newValue = "";
			for (int j=0; j<updateValues.size(); j++) {
				String delimitedValue = updateValues.get(j);
				switch (j) {
				case 0: 
					preCheckId = Long.parseLong(delimitedValue);
					break;
				case 1:
					preCheckItem = delimitedValue;
					break;
				case 2:
					if (delimitedValue.endsWith("String")) 
						storageType = "String";
					else
						storageType = delimitedValue;
					break;
				case 3:
					newValue = delimitedValue;
					break;
				}
			}
			// prepare SQL
			String preparedSQL = "UPDATE precheck_item ";
			if (storageType.equals("Number")) 
				preparedSQL = preparedSQL + "SET Number_Value = " +newValue + ", ";
			else if (storageType.equals("Date")) 
				preparedSQL = preparedSQL + "SET Date_Value = '" +newValue + "', ";
			else 
				preparedSQL = preparedSQL + "SET String_Value = '" +newValue + "', ";			
			preparedSQL = preparedSQL + 
					" Last_Updated_Date = NOW(), " +
					" Last_Updated_By = '" + thisU.getNameForLastUpdatedBy() + "' "+
					"WHERE preCheck_Id = " + 
					preCheckId + 
					" AND Item_Name = '" + 
					preCheckItem.replace("_", " ") + "'";
			//System.out.println(preparedSQL);
			PreparedStatement pstmt = null;	
			Connection conn = null;
			String message;
			try {
		    	conn = DriverManager.getConnection(url);
		    	pstmt = conn.prepareStatement(preparedSQL);
				pstmt.execute();
		    } catch (Exception ex) {
		    	message = "Error in prepared SQL for pre: " + ex.getMessage();
		    	ex.printStackTrace();
		    	success = false;
		    } finally {
		    	try {
		    		if ((pstmt != null) && (!pstmt.isClosed()))	pstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    	success = false;
			    }
		    }
		}
		if (success) {
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
	    	String message = "";
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call CheckPreCheckInProgress(?,?)}");
		    	cstmt.setLong(1, preCheckId);
		    	cstmt.setString(2, thisU.getNameForLastUpdatedBy());
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						if (rs.getString(1).equals("Y")) {
							result = "Precheck successfully updated";
						}
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in CheckPreCheckInProgress(): " + ex.getMessage();
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
		return result;
	}

}
