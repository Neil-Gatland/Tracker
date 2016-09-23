package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
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

import com.devoteam.tracker.model.PreCheckListItem;
import com.devoteam.tracker.model.SNR;
import com.devoteam.tracker.model.SNRBOInformation;
import com.devoteam.tracker.model.SNRTechnology;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.CompletionReport;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.StringUtil;
import com.devoteam.tracker.util.UtilBean;

public class MultiSNRListServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7858360502461620835L;
	private final String[] filters = {"filterScheduledStart", 
			"filterScheduledEnd", "filterSite", "filterNRId", "filterBOEngineer", 
			"filterStatus"};
	private Map<String, String> filterValues = new HashMap<String, String>();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/multiSNRList.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			for (int i = 0; i < filters.length; i++) {
		    	req.setAttribute(filters[i], req.getParameter(filters[i]));
			}
			boolean direct = req.getAttribute("buttonPressed") != null;
			String whichFilter = req.getParameter("whichFilter");
			String buttonPressed = direct?(String)req.getAttribute("buttonPressed"):req.getParameter("buttonPressed");
			String snrId = direct?(String)req.getAttribute("snrId"):req.getParameter("snrId");
			String snrStatus = direct?(String)req.getAttribute("snrStatus"):req.getParameter("snrStatus");
			String historyInd = direct?(String)req.getAttribute("historyInd"):req.getParameter("historyInd");
			String customerId = direct?(String)req.getAttribute("customerId"):req.getParameter("customerId");
			String listStatus1 = direct?(String)req.getAttribute("listStatus1"):req.getParameter("listStatus1");
			String listStatus2 = direct?(String)req.getAttribute("listStatus2"):req.getParameter("listStatus2");
			String nextPreCheck = direct?(String)req.getAttribute("nextPreCheck"):req.getParameter("nextPreCheck");
	    	req.setAttribute("buttonPressed", buttonPressed);
	    	req.setAttribute("snrId", snrId);
	    	req.setAttribute("snrStatus", snrStatus);
	    	req.setAttribute("historyInd", historyInd);
	    	req.setAttribute("customerId", customerId);
	    	req.setAttribute("listStatus1", listStatus1);
	    	req.setAttribute("listStatus2", listStatus2);
			req.setAttribute("nextPreCheck", nextPreCheck);
			String reallocType = direct?(String)req.getAttribute("reallocType"):req.getParameter("reallocType");
			String site = direct?(String)req.getAttribute("site"):req.getParameter("site");
			String nrId = direct?(String)req.getAttribute("nrId"):req.getParameter("nrId");
			req.setAttribute("reallocType", reallocType);
			req.setAttribute("site", site);
			req.setAttribute("nrId", nrId);
			if (direct) {
				req.setAttribute("filterNRId", nrId);
			}
			req.setAttribute("preCheckId", req.getAttribute("preCheckId"));
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			Connection conn = null;
			CallableStatement cstmt = null;
			if ((buttonPressed.equals("showImplementationDetail")) || (buttonPressed.equals("resched")) || 
					(buttonPressed.equals("schedM"))) {
				UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
				if (buttonPressed.equals("showImplementationDetail")) {
					SNRBOInformation snr = uB.getSNRBOInformation(Long.parseLong(snrId));
			    	req.setAttribute("implementationStatus", snr.getImplementationStatus());
			    	req.setAttribute("implementationStartDT", snr.getImplementationStartDTString());
			    	req.setAttribute("implementationEndDT", snr.getImplementationEndDTString());
			    	req.setAttribute("implOutageStartDT", snr.getImplOutageStartDTString());
			    	req.setAttribute("implOutageEndDT", snr.getImplOutageEndDTString());
			    	//req.setAttribute("completingBOEngineer", snr.getCompletingBOEngineer());
			    	req.setAttribute("implementationAbortType", snr.getAbortType());
			    	req.setAttribute("implementation2GInd", snr.get2GInd());
			    	req.setAttribute("implementation3GInd", snr.get3GInd());
			    	req.setAttribute("implementation4GInd", snr.get4GInd());
			    	req.setAttribute("implementationTEF2GInd", snr.getTEF2GInd());
			    	req.setAttribute("implementationTEF3GInd", snr.getTEF3GInd());
			    	req.setAttribute("implementationTEF4GInd", snr.getTEF4GInd());
			    	req.setAttribute("implementationPaknetPaging", snr.getPaknetPaging());
			    	req.setAttribute("implementationSecGWChange", snr.getSecGWChange());
			    	req.setAttribute("implementationPower", snr.getPower());
			    	req.setAttribute("implementationSurvey", snr.getSurvey());
			    	req.setAttribute("implementationOther", snr.getOther());
			    	//req.setAttribute("implementationO2Ind", snr.getO2Ind());
			    	req.setAttribute("implementationHealthChecksInd", snr.getHealthChecksInd());
			    	req.setAttribute("implementationActiveAlarmsInd", snr.getActiveAlarmsInd());
			    	//req.setAttribute("implementationNSANetActsInd", snr.getNSANetActsInd());
			    	req.setAttribute("implementationHOPDeliveredInd", snr.getHOPDeliveredInd());
			    	req.setAttribute("implementationHOPFilename", snr.getHOPFilename());
			    	req.setAttribute("implementationHOPOnSharePoint", snr.getHOPOnSharepoint());
			    	//req.setAttribute("implementationEFUpdated", snr.getEFUpdated());
			    	req.setAttribute("implementationSFRCompleted", snr.getSFRCompleted());
			    	req.setAttribute("implementationSFROnSharePoint", snr.getSFROnSharepoint());
			    	req.setAttribute("ef345ClaimDT", snr.getEF345ClaimDTString());
			    	req.setAttribute("ef360ClaimDT", snr.getEF360ClaimDTString());
			    	req.setAttribute("ef390ClaimDT", snr.getEF390ClaimDTString());
			    	req.setAttribute("ef400ClaimDT", snr.getEF400ClaimDTString());
			    	req.setAttribute("ef410ClaimDT", snr.getEF410ClaimDTString());
					req.setAttribute("crInStartDT", snr.getCRINStartDTString());
					req.setAttribute("crInEndDT", snr.getCRINEndDTString());
					req.setAttribute("crInReference", snr.getCRINReference());
					req.setAttribute("tefOutageNos", snr.getTEFOutageNos());
					req.setAttribute("boEngineerList", snr.getBOEngineerList());
					req.setAttribute("feList", snr.getFEList());
					req.setAttribute("nextPreCheck", snr.getNextPreCheck());
					req.setAttribute("firstAttempt", snr.getFirstAttempt());
					req.setAttribute("hopStatus", snr.getHOPStatus());
					req.setAttribute("cramerCompleted", snr.getCramerCompleted());
					req.setAttribute("scriptsReceived", snr.getScriptsReceived());
					req.setAttribute("alarms", snr.getAlarms());
					req.setAttribute("healthCheck", snr.getHealthCheck());
					req.setAttribute("jobType", snr.getJobType());
					req.setAttribute("incTicketNo", snr.getIncTicketNo());
					req.setAttribute("performanceChecks", snr.getPerformanceChecks());
					req.setAttribute("workDetails", snr.getWorkDetails());
					req.setAttribute("hardwareVendor", snr.getHardwareVendor());
					req.setAttribute("preTestCallsDone", snr.getPreTestCallsDone());
					req.setAttribute("postTestCallsDone", snr.getPostTestCallsDone());
					req.setAttribute("crqClosureCode", snr.getCrqClosureCode());
					req.setAttribute("siteIssues", snr.getSiteIssues());
				} else if (buttonPressed.equals("realloc")) {
					SNR snr = uB.getSNRDetail(Long.parseLong(snrId), false, 0);
					req.setAttribute("scheduledDT", snr.getScheduledDateString());
			    	session.setAttribute(ServletConstants.SNR_SCHEDULED_DATE_IN_SESSION, snr.getScheduledDateString());
					req.setAttribute("reallocType", buttonPressed);
				} else if (buttonPressed.equals("resched")) {
					SNR snr = uB.getSNRDetail(Long.parseLong(snrId), false, 0);
					req.setAttribute("scheduledDT", snr.getScheduledDateString());
			    	session.setAttribute(ServletConstants.SNR_SCHEDULED_DATE_IN_SESSION, snr.getScheduledDateString());
					req.setAttribute("reallocType", buttonPressed);
				} else if (buttonPressed.equals("schedM")) {
					SNR snr = uB.getSNRDetail(Long.parseLong(snrId), false, 0);
					req.setAttribute("reallocType", buttonPressed);
					req.setAttribute("site", snr.getSite());
					req.setAttribute("nrId", snr.getNRId());
			    	session.setAttribute(ServletConstants.SNR_SCHEDULED_DATE_IN_SESSION, snr.getScheduledDateString());
				}
			} else if (buttonPressed.equals("closeNR")) {
			    try {
			    	String snrStatusNew = req.getParameter("snrStatusNew");
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call Update_SNR_Status(?,?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setString(2, snrStatusNew);
					cstmt.setString(3, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					String ok = "N";
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							ok = rs.getString(1);
						}
					}
					if (ok.equalsIgnoreCase("Y")) {
						req.setAttribute("userMessage", "Status updated");
				    	req.setAttribute("snrStatus", snrStatusNew);
					} else {
			        	req.setAttribute("userMessage", "Error: Unable to update status");
					}
			    } catch (SQLException ex) {
		        	req.setAttribute("userMessage", "Error: Unable to update status, " + ex.getMessage());
			    } finally {
			    	try {
				    	cstmt.close();
				    	conn.close();
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: updating status, " + ex.getMessage());
				    } finally {
			        	req.removeAttribute("buttonPressed");
				    }
			    }
			} else if ((buttonPressed.equals("updateEF345")) ||
					(buttonPressed.equals("updateWorkDetails")) ||
					(buttonPressed.equals("updateSiteIssues")) ||
					(buttonPressed.equals("updateSiteDetails")) ||
					(buttonPressed.equals("updateImplDates")) ||
					(buttonPressed.equals("updateTech")) ||
					(buttonPressed.equals("updateConf")) ||
					(buttonPressed.equals("completeNR")) ||
					(buttonPressed.equals("updatePerfChecks")) ||
					(buttonPressed.equals("updateUploadStatus")) ||
					(buttonPressed.equals("addComP")) ||
					(buttonPressed.equals("addComBO")) ||
					(buttonPressed.equals("addCommentarySubmit")) ||
					(buttonPressed.equals("addBOTechnologiesSubmit")) ||
					(buttonPressed.equals("detailP")) || 
					(buttonPressed.equals("finalP")) || 
					(buttonPressed.equals("closePI")) || 
					(buttonPressed.equals("deleteEngineer")) ||			
					(buttonPressed.equals("addRoleSubmit")) ||
					(buttonPressed.equals("addRole")) ||		
					(buttonPressed.equals("chgRoleSubmit")) ||
					(buttonPressed.equals("chgRole")) ||
					(buttonPressed.equals("updateI"))) {
		    	req.setAttribute("buttonPressed", "showImplementationDetail");
				String ef345ClaimDT = req.getParameter("ef345ClaimDT");
		    	req.setAttribute("ef345ClaimDT", ef345ClaimDT);
				String workDetails = req.getParameter("workDetails");
		    	req.setAttribute("workDetails",workDetails);
				String siteIssues = req.getParameter("siteIssues");
		    	req.setAttribute("siteIssues",siteIssues);
				String implementationStatus = req.getParameter("selectImplementationStatus")==null
						?req.getParameter("disabledImplementationStatus")
						:req.getParameter("selectImplementationStatus");
				String implementationStartDT = req.getParameter("implementationStartDT");
				String implementationEndDT = req.getParameter("implementationEndDT");
				String implOutageStartDT = req.getParameter("implOutageStartDT");
				String implOutageEndDT = req.getParameter("implOutageEndDT");
		    	req.setAttribute("implementationStatus", implementationStatus);
		    	req.setAttribute("implementationStartDT", implementationStartDT);
		    	req.setAttribute("implementationEndDT", implementationEndDT);
		    	req.setAttribute("implOutageStartDT", implOutageStartDT);
		    	req.setAttribute("implOutageEndDT", implOutageEndDT);
				String implementation2GInd = req.getParameter("implementation2GInd");
				String implementation3GInd = req.getParameter("implementation3GInd");
				String implementation4GInd = req.getParameter("implementation4GInd");
				String implementationTEF2GInd = req.getParameter("implementationTEF2GInd");
				String implementationTEF3GInd = req.getParameter("implementationTEF3GInd");
				String implementationTEF4GInd = req.getParameter("implementationTEF4GInd");
				String implementationPaknetPaging = req.getParameter("implementationPaknetPaging");
				String implementationSecGWChange = req.getParameter("implementationSecGWChange");
				String implementationPower = req.getParameter("implementationPower");
				String implementationSurvey = req.getParameter("implementationSurvey");
				String implementationOther = req.getParameter("implementationOther");
		    	req.setAttribute("implementation2GInd", implementation2GInd);
		    	req.setAttribute("implementation3GInd", implementation3GInd);
		    	req.setAttribute("implementation4GInd", implementation4GInd);
		    	req.setAttribute("implementationTEF2GInd", implementationTEF2GInd);
		    	req.setAttribute("implementationTEF3GInd", implementationTEF3GInd);
		    	req.setAttribute("implementationTEF4GInd", implementationTEF4GInd);
		    	req.setAttribute("implementationPaknetPaging", implementationPaknetPaging);
		    	req.setAttribute("implementationSecGWChange", implementationSecGWChange);
		    	req.setAttribute("implementationPower", implementationPower);
		    	req.setAttribute("implementationSurvey", implementationSurvey);
		    	req.setAttribute("implementationOther", implementationOther);
				String implementationActiveAlarmsInd = req.getParameter("selectActiveAlarmsInd");
				String implementationHealthChecksInd = req.getParameter("selectHealthChecksInd");
				String incTicketNo = req.getParameter("incTicketNo");
				String implementationHOPDeliveredInd = req.getParameter("selectHOPDeliveredInd");
				String implementationHOPFilename = req.getParameter("implementationHOPFilename");
				String implementationSFRCompleted = req.getParameter("selectSFRCompleted");
				String ef360ClaimDT = req.getParameter("ef360ClaimDT");
				String ef390ClaimDT = req.getParameter("ef390ClaimDT");
		    	req.setAttribute("implementationActiveAlarmsInd", implementationActiveAlarmsInd);
		    	req.setAttribute("implementationHealthChecksInd", implementationHealthChecksInd);
		    	req.setAttribute("incTicketNo", incTicketNo);
		    	req.setAttribute("implementationHOPDeliveredInd", implementationHOPDeliveredInd);
		    	req.setAttribute("implementationHOPFilename", implementationHOPFilename);
		    	req.setAttribute("implementationSFRCompleted", implementationSFRCompleted);
		    	req.setAttribute("ef360ClaimDT", ef360ClaimDT);
		    	req.setAttribute("ef390ClaimDT", ef390ClaimDT);
				String implementationAbortType = req.getParameter("selectAbortType");
		    	req.setAttribute("implementationAbortType", implementationAbortType);
		    	String performanceChecks = req.getParameter("selectPerformanceChecks");
				String ef400ClaimDT = req.getParameter("ef400ClaimDT");
				String ef410ClaimDT = req.getParameter("ef410ClaimDT");
		    	req.setAttribute("performanceChecks", performanceChecks);
		    	req.setAttribute("ef400ClaimDT", ef400ClaimDT);
		    	req.setAttribute("ef410ClaimDT", ef410ClaimDT);
		    	String preTestCallsDone = req.getParameter("selectPreTestCallsDone");
		    	String postTestCallsDone = req.getParameter("selectPostTestCallsDone");
		    	String crqClosureCode = req.getParameter("crqClosureCode");
		    	req.setAttribute("preTestCallsDone", preTestCallsDone);
		    	req.setAttribute("postTestCallsDone", postTestCallsDone);
		    	req.setAttribute("crqClosureCode", crqClosureCode);
				String implementationHOPOnSharePoint = req.getParameter("selectHOPOnSharePoint");
				String implementationSFROnSharePoint = req.getParameter("selectSFROnSharePoint");
		    	req.setAttribute("implementationHOPOnSharePoint", implementationHOPOnSharePoint);
		    	req.setAttribute("implementationSFROnSharePoint", implementationSFROnSharePoint);
				req.setAttribute("boEngineerList", req.getParameter("boEngineerList"));
				req.setAttribute("feList", req.getParameter("feList"));
				req.setAttribute("cramerCompleted", req.getParameter("cramerCompleted"));
				req.setAttribute("scriptsReceived", req.getParameter("scriptsReceived"));
				req.setAttribute("alarms", req.getParameter("alarms"));
				req.setAttribute("healthCheck", req.getParameter("healthCheck"));
				req.setAttribute("crInStartDT", req.getParameter("crInStartDT"));
				req.setAttribute("crInEndDT", req.getParameter("crInEndDT"));
				req.setAttribute("crInReference", req.getParameter("crInReference"));
				req.setAttribute("tefOutageNos", req.getParameter("tefOutageNos"));
				req.setAttribute("jobType", req.getParameter("jobType"));
				req.setAttribute("firstAttempt", req.getParameter("firstAttempt"));		    	
				try {
					if (buttonPressed.equals("updateImplDates")) {
						req.setAttribute("resetAnchor", "dateTimes");
						Timestamp implementationStartTS = null;
						Timestamp implementationEndTS = null;
						Timestamp implOutageStartTS = null;
						Timestamp implOutageEndTS = null;
						if ((!StringUtil.hasNoValue(implementationStatus)) &&
								(((!StringUtil.hasNoValue(implementationStartDT)) &&
								(StringUtil.hasNoValue(implementationEndDT))) || 
								((StringUtil.hasNoValue(implementationStartDT)) &&
								(!StringUtil.hasNoValue(implementationEndDT))))) {
							throw new Exception("Implementation dates - enter both or neither");
						}
						if (!StringUtil.hasNoValue(implementationStartDT)) {
							try {
								implementationStartTS = Timestamp.valueOf(implementationStartDT.substring(6, 10) + "-" +
										implementationStartDT.substring(3, 5) + "-" +	
										implementationStartDT.substring(0, 2) + " " + 
										implementationStartDT.substring(11, 16) + ":00");
							} catch (Exception ex) {
								throw new Exception("Invalid value entered for Implementation start date");
							}
						}
						if (!StringUtil.hasNoValue(implementationEndDT)) {
							try {
								implementationEndTS = Timestamp.valueOf(implementationEndDT.substring(6, 10) + "-" +
										implementationEndDT.substring(3, 5) + "-" +	
										implementationEndDT.substring(0, 2) + " " + 
										implementationEndDT.substring(11, 16) + ":59");
							} catch (Exception ex) {
								throw new Exception("Invalid value entered for Implementation end date");
							}
						}
						if ((!StringUtil.hasNoValue(implementationStartDT)) &&
								(!StringUtil.hasNoValue(implementationEndDT))) {
							if (implementationStartTS.after(implementationEndTS)) {
								throw new Exception("Implementation start date cannot be after Implementation end date");
							}
						}
						if ((!StringUtil.hasNoValue(implementationStatus)) &&
								(((!StringUtil.hasNoValue(implOutageStartDT)) &&
								(StringUtil.hasNoValue(implOutageEndDT))) || 
								((StringUtil.hasNoValue(implOutageStartDT)) &&
								(!StringUtil.hasNoValue(implOutageEndDT))))) {
							throw new Exception("Implementation Outage dates - enter both or neither");
						}
						if (!StringUtil.hasNoValue(implOutageStartDT)) {
							try {
								implOutageStartTS = Timestamp.valueOf(implOutageStartDT.substring(6, 10) + "-" +
										implOutageStartDT.substring(3, 5) + "-" +	
										implOutageStartDT.substring(0, 2) + " " + 
										implOutageStartDT.substring(11, 16) + ":00");
							} catch (Exception ex) {
								throw new Exception("Invalid value entered for Implementation Outage start date");
							}
						}
						if (!StringUtil.hasNoValue(implOutageEndDT)) {
							try {
								implOutageEndTS = Timestamp.valueOf(implOutageEndDT.substring(6, 10) + "-" +
										implOutageEndDT.substring(3, 5) + "-" +	
										implOutageEndDT.substring(0, 2) + " " + 
										implOutageEndDT.substring(11, 16) + ":59");
							} catch (Exception ex) {
								throw new Exception("Invalid value entered for Implementation Outage end date");
							}
						}
						if ((!StringUtil.hasNoValue(implOutageStartDT)) &&
								(!StringUtil.hasNoValue(implOutageEndDT))) {
							if (implOutageStartTS.after(implOutageEndTS)) {
								throw new Exception("Implementation Outage start date cannot be after Implementation Outage end date");
							}
						}
					    try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call UpdateImplementationDates(?,?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setTimestamp(2, implementationStartTS);
							cstmt.setTimestamp(3, implementationEndTS);
							cstmt.setTimestamp(4, implOutageStartTS);
							cstmt.setTimestamp(5, implOutageEndTS);
							cstmt.setString(6, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Implementation Dates updated");
									} else {
										throw new Exception("negative return code from UpdateImplementationDates()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to update Implementation Datex, " + ex.getMessage());
					    } finally {
					    	try {					    		
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: updating Implementation Dates, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("updateWorkDetails")) {
					    try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call UpdateWorkDetails(?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, workDetails);
							cstmt.setString(3, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Work Details updated");
									} else {
										throw new Exception("negative return code from UpdateWorkDetails()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to update Work Details, " + ex.getMessage());
					    } finally {
					    	try {					    		
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: updating Work Details, " + ex.getMessage());
						    }
					    }
				    } else if (buttonPressed.equals("updateSiteIssues")) {
						req.setAttribute("resetAnchor", "siteDetails");
					    try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call UpdateSiteIssues(?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, siteIssues);
							cstmt.setString(3, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Site Issues updated");
									} else {
										throw new Exception("negative return code from UpdateSiteIssues()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to update Site Issues, " + ex.getMessage());
					    } finally {
					    	try {					    		
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: updating Work Details, " + ex.getMessage());
						    }
					    }					
					} else if (buttonPressed.equals("addRole")) {
				    	req.setAttribute("buttonPressed", "addRole");
						req.setAttribute("snrUserRole", req.getParameter("snrUserRole"));
						req.setAttribute("workflowName", req.getParameter("selectWorkflowName"));	
						req.setAttribute("scheduleCommentary", req.getParameter("scheduleCommentary"));		
						String scheduledDT = req.getParameter("scheduledDT");
				    	req.setAttribute("scheduledDT", scheduledDT);					
					} else if (buttonPressed.equals("chgRole")) {
				    	req.setAttribute("buttonPressed", "chgRole");
						req.setAttribute("userId", req.getParameter("userId"));
						req.setAttribute("snrUserRole", req.getParameter("snrUserRole"));
						req.setAttribute("workflowName", req.getParameter("selectWorkflowName"));	
						req.setAttribute("scheduleCommentary", req.getParameter("scheduleCommentary"));		
						String scheduledDT = req.getParameter("scheduledDT");
				    	req.setAttribute("scheduledDT", scheduledDT);	
					} else if (buttonPressed.equals("closePI")) {
						req.setAttribute("resetAnchor", "techComp");
					} else if (buttonPressed.equals("deleteEngineer")) {
						req.setAttribute("workflowName", req.getParameter("selectWorkflowName"));	
						req.setAttribute("scheduleCommentary", req.getParameter("scheduleCommentary"));		
						String scheduledDT = req.getParameter("scheduledDT");
				    	req.setAttribute("scheduledDT", scheduledDT);	
				    	req.setAttribute("buttonPressed", reallocType);
					    try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call RemoveSNRUserRole(?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setLong(2, Long.parseLong(req.getParameter("userId")));
							cstmt.setString(3, req.getParameter("snrUserRole"));
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Engineer removed from NR");
										UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
										SNRBOInformation snr = uB.getSNRBOInformation(Long.parseLong(snrId));
										req.setAttribute("feList", snr.getFEList());
									} else {
										throw new Exception("negative return code from RemoveSNRUserRole()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to remove NR user role, " + ex.getMessage());
				        	if (ex.getMessage()==null)  
					    		req.setAttribute("userMessage", "Engineer removed from NR");
					    } finally {
					    	try {
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: removing NR user role, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("addRoleSubmit")) {
				    	req.setAttribute("buttonPressed", reallocType);
						req.setAttribute("workflowName", req.getParameter("selectWorkflowName"));	
						req.setAttribute("scheduleCommentary", req.getParameter("scheduleCommentary"));		
						String scheduledDT = req.getParameter("scheduledDT");
				    	req.setAttribute("scheduledDT", scheduledDT);
					    try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call AddSNRUserRole(?,?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setLong(2, Long.parseLong(req.getParameter("selectAvailableUsersForRole")));
							cstmt.setString(3, req.getParameter("snrUserRole"));
							cstmt.setLong(4, req.getParameter("selectThirdParty")==null?-1:Long.parseLong(req.getParameter("selectThirdParty")));
							cstmt.setLong(5, req.getParameter("selectFENumber")==null?-1:Long.parseLong(req.getParameter("selectFENumber")));
							cstmt.setString(6, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Engineer added to NR");
										UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
										SNRBOInformation snr = uB.getSNRBOInformation(Long.parseLong(snrId));
										req.setAttribute("feList", snr.getFEList());
									} else {
										throw new Exception("negative return code from AddSNRUserRole()");
									}
								}
							}
					    } catch (Exception ex) {				        	
					    	req.setAttribute("userMessage", "Error: unable to add NR user role, " + ex.getMessage());
					    	if (ex.getMessage()==null)  
					    		req.setAttribute("userMessage", "Engineer added to NR");  
					    } finally {
					    	try {
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: adding NR user role, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("chgRoleSubmit")) {
				    	req.setAttribute("buttonPressed", reallocType);
						req.setAttribute("workflowName", req.getParameter("selectWorkflowName"));	
						req.setAttribute("scheduleCommentary", req.getParameter("scheduleCommentary"));		
						String scheduledDT = req.getParameter("scheduledDT");
				    	req.setAttribute("scheduledDT", scheduledDT);	 
					    try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call ChangeSNRUserRole(?,?,?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setLong(2, Long.parseLong(req.getParameter("userId")));
							cstmt.setLong(3, Long.parseLong(req.getParameter("selectAltAvailableUsersForRole")));
							cstmt.setString(4, req.getParameter("snrUserRole"));
							cstmt.setLong(5, req.getParameter("selectAltThirdParty")==null?-1:Long.parseLong(req.getParameter("selectAltThirdParty")));
							cstmt.setLong(6, req.getParameter("selectAltFENumber")==null?-1:Long.parseLong(req.getParameter("selectAltFENumber")));
							cstmt.setString(7, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Engineer changed for SNR");
										UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
										SNRBOInformation snr = uB.getSNRBOInformation(Long.parseLong(snrId));
										req.setAttribute("feList", snr.getFEList());
									} else {
										throw new Exception("negative return code from ChangeSNRUserRole()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to change NR user role, " + ex.getMessage());
				        	if (ex.getMessage()==null)  
					    		req.setAttribute("userMessage", "Engineer changed for NR");
					    } finally {
					    	try {
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: changing SNR user role, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("updateEF345")) {
						if (StringUtil.hasNoValue(ef345ClaimDT)) {
							throw new Exception("EF345 Sign Off Date cannot be blank");
						} else {
						    try {
						    	Timestamp ef345ClaimTS = Timestamp.valueOf(ef345ClaimDT.substring(6, 10) + "-" +
										ef345ClaimDT.substring(3, 5) + "-" +	
										ef345ClaimDT.substring(0, 2) + " 00:00:00");
							} catch (Exception ex) {
								throw new Exception("Invalid value entered for EF345 Sign Off Date");
							}
						}
					    try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call UpdateEvenflowDates(?,?,?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, ef345ClaimDT);
							cstmt.setString(3, null);
							cstmt.setString(4, null);
							cstmt.setString(5, null);
							cstmt.setString(6, null);
							cstmt.setString(7, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "EF345 Sign Off Date updated");
									} else {
										throw new Exception("negative return code from UpdateEvenflowDates()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to update EF345 Sign Off Date, " + ex.getMessage());
					    } finally {
					    	try {
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: updating EF345 Sign Off Date, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("updateTech")) {
					    try {
					    	req.setAttribute("resetAnchor", "techComp");
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call UpdateTechnologies(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, req.getParameter("select2G"));
							cstmt.setString(3, req.getParameter("select3G"));
							cstmt.setString(4, req.getParameter("select4G"));
							cstmt.setString(5, req.getParameter("selectTEF2G"));
							cstmt.setString(6, req.getParameter("selectTEF3G"));
							cstmt.setString(7, req.getParameter("selectTEF4G"));
							cstmt.setString(8, req.getParameter("selectPaknetPaging"));
							cstmt.setString(9, req.getParameter("selectSecGWChange"));
							cstmt.setString(10, req.getParameter("selectPower"));
							cstmt.setString(11, req.getParameter("selectSurvey"));
							cstmt.setString(12, req.getParameter("selectOther"));
							cstmt.setString(13, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Technologies updated");
									} else {
										throw new Exception("negative return code from UpdateTechnologies()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to update technologies, " + ex.getMessage());
					    } finally {
					    	try {					    		
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: updating technologies, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("updateConf")) {
						req.setAttribute("resetAnchor", "techComp");
						Timestamp ef360ClaimTS = null;
						Timestamp ef390ClaimTS = null;
						if (!StringUtil.hasNoValue(ef360ClaimDT)) {
							/*throw new Exception("EF360 Sign Off Date cannot be blank");
						} else {*/
						    try {
						    	ef360ClaimTS = Timestamp.valueOf(ef360ClaimDT.substring(6, 10) + "-" +
										ef360ClaimDT.substring(3, 5) + "-" +	
										ef360ClaimDT.substring(0, 2) + " 00:00:00");
							} catch (Exception ex) {
								throw new Exception("Invalid value entered for EF360 Sign Off Date");
							}
						}
						if (!StringUtil.hasNoValue(ef390ClaimDT)) {
							/*throw new Exception("EF390 Sign Off Date cannot be blank");
						} else {*/
						    try {
						    	ef390ClaimTS = Timestamp.valueOf(ef390ClaimDT.substring(6, 10) + "-" +
										ef390ClaimDT.substring(3, 5) + "-" +	
										ef390ClaimDT.substring(0, 2) + " 00:00:00");
							} catch (Exception ex) {
								throw new Exception("Invalid value entered for EF390 Sign Off Date");
							}
						}
						try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call UpdateConfirmationDetails(?,?,?,?,?,?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, implementationActiveAlarmsInd);
							cstmt.setString(3, implementationHealthChecksInd);
							cstmt.setString(4, incTicketNo);
							cstmt.setString(5, implementationHOPDeliveredInd);
							cstmt.setString(6, implementationHOPFilename);
							cstmt.setString(7, implementationSFRCompleted);
							cstmt.setTimestamp(8, ef360ClaimTS);
							cstmt.setTimestamp(9, ef390ClaimTS);
							cstmt.setString(10, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Confirmation details updated");
									} else {
										throw new Exception("negative return code from UpdateConfirmationDetails()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to update confirmation details, " + ex.getMessage());
					    } finally {
					    	try {					    		
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: updating confirmation details, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("completeNR")) {			    		
						boolean pIP = false;
						req.setAttribute("resetAnchor", "techComp");
						if (StringUtil.hasNoValue(implementationStatus)) {
							throw new Exception("Implementation Status must be entered");
						} else if ((implementationStatus.equalsIgnoreCase("Aborted")) &&
								(StringUtil.hasNoValue(implementationAbortType))) {
							throw new Exception("Abort Type must be entered if Implementation Status is 'Aborted'");
						} else if ((!implementationStatus.equalsIgnoreCase("Aborted")) &&
								(!StringUtil.hasNoValue(implementationAbortType))) {
							throw new Exception("Abort Type must not be entered if Implementation Status is not 'Aborted'");
						} else if ((StringUtil.hasNoValue(implementationStartDT)) ||
								(StringUtil.hasNoValue(implementationEndDT))) {
							throw new Exception("Implementation start and end dates must be " +
								"entered to complete NR");
						} else if ((!implementationStatus.equalsIgnoreCase("Aborted")) &&
								((StringUtil.hasNoValue(implOutageStartDT)) ||
								(StringUtil.hasNoValue(implOutageEndDT)))) {
							throw new Exception("Outage start and end dates must be " +
								"entered to complete NR if Implementation Status is not 'Aborted'");
						} else if (implementationStatus.equalsIgnoreCase("Performance IP")) {
							if (!snrStatus.equalsIgnoreCase(ServletConstants.STATUS_SCHEDULED)) {
								throw new Exception("Implementation Status can only be set to 'Performance IP' " +
										"if Status is '" + ServletConstants.STATUS_SCHEDULED + "'");
							}
							pIP = true;
						}
						req.setAttribute("userMessage", "valiadtion OK");
						try {
							conn = DriverManager.getConnection(url);
					    	conn.setAutoCommit(false);
					    	if (pIP) {
								cstmt = conn.prepareCall("{call PerformanceIP_SNR_Screen(?,?)}");
								cstmt.setLong(1, Long.parseLong(snrId));
								cstmt.setString(2, thisU.getNameForLastUpdatedBy());
								boolean found = cstmt.execute();
								if (found) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										if (rs.getString(1).equalsIgnoreCase("Y")) {
											req.setAttribute("userMessage", "Implementation Status set to 'Performance IP'");
										} else {
											throw new Exception("negative return code from PerformanceIP_SNR_Screen()");
										}
									}
								}
					    	} else {
								cstmt = conn.prepareCall("{call Complete_SNR_Screen(?,?,?,?)}");
								cstmt.setLong(1, Long.parseLong(snrId));
								cstmt.setString(2, implementationStatus);
								cstmt.setString(3, StringUtil.hasNoValue(implementationAbortType)?null:implementationAbortType);
								cstmt.setString(4, thisU.getNameForLastUpdatedBy());
								boolean found = cstmt.execute();
								if (found) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										if (rs.getString(1).equalsIgnoreCase("Y")) {
											// create completion report email
											CompletionReport cr = new CompletionReport();
											String emailResult = cr.cutdownCompletionReport(
																Long.parseLong(snrId), 
																implementationStatus, 
																implementationEndDT, 
																conn,
																thisU.getNameForLastUpdatedBy());
											req.setAttribute("userMessage", "NR completed" + emailResult);
											if ((implementationStatus.equalsIgnoreCase("Aborted")) ||
													implementationStatus.equalsIgnoreCase("Partial")) {
										    	req.removeAttribute("buttonPressed");
										    	req.removeAttribute("snrId");
										    	destination = "/homeBO.jsp";
											}
										} else {
											//throw new Exception("negative return code from Complete_SNR_Screen()");
											req.setAttribute("userMessage", "negative return code from Complete_SNR_Screen()");
										}
									}
								}
					    	}
							String commentary = req.getParameter("cNRCommentaryText");
							if (!StringUtil.hasNoValue(commentary)) {
						    	cstmt = conn.prepareCall("{call AddSNRCommentary(?,?,?,?,?)}");
								cstmt.setLong(1, Long.parseLong(snrId));
								cstmt.setLong(2, -1);
								cstmt.setString(3, "Implementation");
								cstmt.setString(4, commentary);
								cstmt.setString(5, thisU.getNameForLastUpdatedBy());
								cstmt.execute();
							}
							conn.commit();
					    } catch (SQLException ex) {
				    		conn.rollback();
				    		req.setAttribute("userMessage", "Error: unable to complete NR, " + ex.getMessage());
					    } finally {
					    	try {
					    		cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: completing NR, " + ex.getMessage());
						    }
					    } 
					} else if (buttonPressed.equals("updatePerfChecks")) {
						req.setAttribute("resetAnchor", "performance");
						Timestamp ef400ClaimTS = null;
						Timestamp ef410ClaimTS = null;
						if (StringUtil.hasNoValue(ef400ClaimDT)) {
							throw new Exception("EF400 Sign Off Date cannot be blank");
						} else {
						    try {
						    	ef400ClaimTS = Timestamp.valueOf(ef400ClaimDT.substring(6, 10) + "-" +
										ef400ClaimDT.substring(3, 5) + "-" +	
										ef400ClaimDT.substring(0, 2) + " 00:00:00");
							} catch (Exception ex) {
								throw new Exception("Invalid value entered for EF400 Sign Off Date");
							}
						}
						if (!StringUtil.hasNoValue(ef410ClaimDT)) {
						    try {
						    	ef410ClaimTS = Timestamp.valueOf(ef410ClaimDT.substring(6, 10) + "-" +
										ef410ClaimDT.substring(3, 5) + "-" +	
										ef410ClaimDT.substring(0, 2) + " 00:00:00");
							} catch (Exception ex) {
								throw new Exception("Invalid value entered for EF410 Sign Off Date");
							}
						}
						try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call UpdatePerformanceChecks(?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, performanceChecks);
							cstmt.setTimestamp(3, ef400ClaimTS);
							cstmt.setTimestamp(4, ef410ClaimTS);
							cstmt.setString(5, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Performance checks updated");
									} else {
										throw new Exception("negative return code from UpdatePerformanceChecks()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to update performance checks, " + ex.getMessage());
					    } finally {
					    	try {					    		
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: updating performance checks, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("updateSiteDetails")) {
						req.setAttribute("resetAnchor", "siteDetails");
						try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call UpdateSiteDetails(?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, preTestCallsDone);
							cstmt.setString(3, postTestCallsDone);
							cstmt.setString(4, crqClosureCode);
							cstmt.setString(5, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Site details updated");
									} else {
										throw new Exception("negative return code from UpdateSiteDetails()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to update site details, " + ex.getMessage());
					    } finally {
					    	try {					    		
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: updating site details, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("updateUploadStatus")) {
						try {
							req.setAttribute("resetAnchor", "uploadStatus");
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call UpdateUploadStatus(?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, implementationHOPOnSharePoint);
							cstmt.setString(3, implementationSFROnSharePoint);
							cstmt.setString(4, thisU.getNameForLastUpdatedBy());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getString(1).equalsIgnoreCase("Y")) {
										req.setAttribute("userMessage", "Upload status updated");
									} else {
										throw new Exception("negative return code from UpdateUploadStatus()");
									}
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to update upload status, " + ex.getMessage());
					    } finally {
					    	try {					    		
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: updating upload status, " + ex.getMessage());
						    }
					    }
					} else if ((buttonPressed.equals("addComP")) || (buttonPressed.equals("addComBO"))) {
						if (buttonPressed.equals("addComP")) {
							req.setAttribute("snrCommentaryTypeInit", "Performance");
						}
				    	req.setAttribute("buttonPressed", buttonPressed);
				    	if (buttonPressed.equals("addComP")) {
			    			req.setAttribute("resetAnchor", "performance");
			    		}
					} else if (buttonPressed.equals("addCommentarySubmit")) {
					    try {
					    	String snrCommentaryType = req.getParameter("selectSNRCommentaryType");
					    	if (snrCommentaryType == null) {
					    		snrCommentaryType = req.getParameter("disabledSNRCommentaryType");
					    	}
					    	conn = DriverManager.getConnection(url);
					    	cstmt = conn.prepareCall("{call AddSNRCommentary(?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setLong(2, -1/*Long.parseLong(req.getParameter("selectPreCheckId"))*/);
							cstmt.setString(3, snrCommentaryType);
							cstmt.setString(4, req.getParameter("snrCommentaryText"));
							cstmt.setString(5, thisU.getNameForLastUpdatedBy());
							cstmt.execute();
				        	req.setAttribute("userMessage", "Commentary added");
					    	
					    	String extraScreen = req.getParameter("extraScreen");
					    	if ((extraScreen !=null) && (extraScreen.equals("snrImplementationDetail"))) {
					    		req.setAttribute("buttonPressed", "showImplementationDetail");
					    	} else {
					    		req.setAttribute("buttonPressed", "viewCom");
					    	}
					    } catch (SQLException ex) {
				        	req.setAttribute("userMessage", "Error: Unable to add commentary, " + ex.getMessage());
				        	req.removeAttribute("buttonPressed");
					    } finally {
					    	try {
					    		String snrCommentaryType = req.getParameter("selectSNRCommentaryType");
						    	if (snrCommentaryType == null) {
						    		snrCommentaryType = req.getParameter("disabledSNRCommentaryType");
						    	}
					    		if (snrCommentaryType.equals("Performance")) {
			    				req.setAttribute("resetAnchor", "performance");
					    		}
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: adding commentary, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("addBOTechnologiesSubmit")) {
						req.setAttribute("resetAnchor", "techComp");
						Collection<SNRTechnology> snrTL = 
								(Collection<SNRTechnology>) session.getAttribute(
										ServletConstants.SNR_BO_TECHNOLOGY_ADD_COLLECTION_NAME_IN_SESSION);
				    	conn = null;
				    	cstmt = null;
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
									cstmt.setLong(1, Long.parseLong(snrId));
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
								req.setAttribute("userMessage", "SNR BO Technology list amended");
							} else {
								req.setAttribute("userMessage", "No new technologies added");
							}
					    } catch (Exception ex) {
					    	try {
					    		conn.rollback();
						    } catch (SQLException ex2) {
						    	ex2.printStackTrace();
						    } finally {
						    	req.setAttribute("userMessage", "Error: unable to amend SNR BO Technologies, " + ex.getMessage());
						    }
					    } finally {
					    	try {
					    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
					    		if ((conn != null) && (!conn.isClosed())) conn.close();					    		
						    } catch (SQLException ex) {
						    	req.setAttribute("userMessage", "Error: amending SNR BO Technologies, " + ex.getMessage());
						    }
					    }
					} else if (buttonPressed.equals("updateI")) {
				    	req.setAttribute("buttonPressed", "detailP");
				    	req.setAttribute("preCheckId", req.getParameter("preCheckId"));
				    	req.setAttribute("resetAnchor", "techComp");
						@SuppressWarnings("unchecked")
						Collection<PreCheckListItem> pciList = (Collection<PreCheckListItem>) 
								session.getAttribute(ServletConstants.PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION);
						validatePreCheckItemUpdates(req, pciList, session);
					    try {
					    	conn = DriverManager.getConnection(url);
					    	conn.setAutoCommit(false);
							for (Iterator<PreCheckListItem> it = pciList.iterator(); it.hasNext(); ) {
								PreCheckListItem pcli = it.next();
								cstmt = conn.prepareCall("{call UpdatePreCheckItem(?,?,?,?,?,?,?)}");
								cstmt.setLong(1, pcli.getPreCheckId());
								cstmt.setString(2, pcli.getItemName());
								cstmt.setString(3, pcli.getStringValue());
								cstmt.setDate(4, pcli.getDateValue());
								cstmt.setDouble(5, pcli.getNumberValue());
								cstmt.setString(6, thisU.getNameForLastUpdatedBy());
								cstmt.setString(7, pcli.getStorageType());
								boolean found = cstmt.execute();
								if (found) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										if (!rs.getString(1).equalsIgnoreCase("Y")) {
											throw new Exception("negative return code from UpdatePreCheckItem()");
										}
									} else {
										throw new Exception("no return code from UpdatePreCheckItem()");
									}
								} else {
									throw new Exception("no return code from UpdatePreCheckItem()");
								}
							}
							conn.commit();
					    	req.setAttribute("buttonPressed", "showImplementationDetail");
				        	req.setAttribute("userMessage", "Pre-Check updated");
							UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
							SNRBOInformation snr = uB.getSNRBOInformation(Long.parseLong(snrId));
							req.setAttribute("cramerCompleted", snr.getCramerCompleted());
							req.setAttribute("scriptsReceived", snr.getScriptsReceived());
							req.setAttribute("alarms", snr.getAlarms());
							req.setAttribute("healthCheck", snr.getHealthCheck());
					    	req.setAttribute("implementation2GInd", snr.get2GInd());
					    	req.setAttribute("implementation3GInd", snr.get3GInd());
					    	req.setAttribute("implementation4GInd", snr.get4GInd());
					    	req.setAttribute("implementationTEF2GInd", snr.getTEF2GInd());
					    	req.setAttribute("implementationTEF3GInd", snr.getTEF3GInd());
					    	req.setAttribute("implementationTEF4GInd", snr.getTEF4GInd());
					    	req.setAttribute("implementationPaknetPaging", snr.getPaknetPaging());
					    	req.setAttribute("implementationSecGWChange", snr.getSecGWChange());
					    	req.setAttribute("implementationPower", snr.getPower());
					    	req.setAttribute("implementationSurvey", snr.getSurvey());
					    	req.setAttribute("implementationOther", snr.getOther());					    	
					    } catch (SQLException ex) {
					    	conn.rollback();
				        	req.setAttribute("userMessage", "Error: Unable to update Pre-Check items, " + ex.getMessage());
					    } finally {
							cstmt.close();
							conn.close();
					    }
						
					} else if ((buttonPressed.equals("detailP")) || (buttonPressed.equals("finalP"))) {
					    try {
							session.removeAttribute(ServletConstants.PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION);
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call GetPrecheckIdForSNRAndType(?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, buttonPressed.equals("detailP")?ServletConstants.PRE_CHECK_TYPE_DETAILED:
								ServletConstants.PRE_CHECK_TYPE_FINAL);
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									long preCheckId = rs.getLong(1);
									if (preCheckId > 0) {
										req.setAttribute("preCheckId", String.valueOf(preCheckId));
								    	req.setAttribute("buttonPressed", buttonPressed);
									} else {
										req.setAttribute("userMessage", (buttonPressed.equals("detailP")?ServletConstants.PRE_CHECK_TYPE_DETAILED:
											ServletConstants.PRE_CHECK_TYPE_FINAL) + " pre-check not found");
									}
								} else {
									throw new Exception(buttonPressed.equals("detailP")?ServletConstants.PRE_CHECK_TYPE_DETAILED:
										ServletConstants.PRE_CHECK_TYPE_FINAL + " pre-check id not found");
								}
							}
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: unable to get pre-check id, " + ex.getMessage());
					    } finally {
					    	try {
						    	cstmt.close();
						    	conn.close();
						    } catch (SQLException ex) {
					        	req.setAttribute("userMessage", "Error: getting pre-check id, " + ex.getMessage());
						    }
					    }
					}
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: " + ex.getMessage());
			    }
			} else if ((buttonPressed.equals("snrImplementationDetailSubmit")) ||
					(buttonPressed.equals("snrImplementationDetailComplete"))) {
		    	req.setAttribute("buttonPressed", "showImplementationDetail");
				try {
					String implementationStatus = req.getParameter("selectImplementationStatus")==null
							?req.getParameter("disabledImplementationStatus")
							:req.getParameter("selectImplementationStatus");
					String implementationStartDT = req.getParameter("implementationStartDT");
					String implementationEndDT = req.getParameter("implementationEndDT");
					String implOutageStartDT = req.getParameter("implOutageStartDT");
					String implOutageEndDT = req.getParameter("implOutageEndDT");
					String implementationAbortType = req.getParameter("selectAbortType");
					String implementation2GInd = req.getParameter("select2GInd");
					String implementation3GInd = req.getParameter("select3GInd");
					String implementation4GInd = req.getParameter("select4GInd");
					String implementationO2Ind = req.getParameter("selectO2Ind");
					String implementationHealthChecksInd = req.getParameter("selectHealthChecksInd");
					String implementationActiveAlarmsInd = req.getParameter("selectActiveAlarmsInd");
					String implementationNSANetActsInd = req.getParameter("selectNSANetActsInd");
					String implementationHOPDeliveredInd = req.getParameter("selectHOPDeliveredInd");
					String implementationHOPFilename = req.getParameter("implementationHOPFilename");
					String implementationHOPOnSharePoint = req.getParameter("selectHOPOnSharePoint");
					String implementationEFUpdated = req.getParameter("selectEFUpdated");
					String implementationSFRCompleted = req.getParameter("selectSFRCompleted");
					String implementationSFROnSharePoint = req.getParameter("selectSFROnSharePoint");
					String ef345ClaimDT = req.getParameter("ef345ClaimDT");
					String ef360ClaimDT = req.getParameter("ef360ClaimDT");
					String ef390ClaimDT = req.getParameter("ef390ClaimDT");
					String ef400ClaimDT = req.getParameter("ef400ClaimDT");
					String ef410ClaimDT = req.getParameter("ef410ClaimDT");
			    	req.setAttribute("ef345ClaimDT",ef345ClaimDT);
			    	req.setAttribute("ef360ClaimDT",ef360ClaimDT);
			    	req.setAttribute("ef390ClaimDT",ef390ClaimDT);
			    	req.setAttribute("ef400ClaimDT",ef400ClaimDT);
			    	req.setAttribute("ef410ClaimDT",ef410ClaimDT);
					req.setAttribute("crInStartDT", req.getParameter("crInStartDTr"));
					req.setAttribute("crInEndDT", req.getParameter("crInEndDTr"));
					req.setAttribute("crInReference", req.getParameter("crInReferenceR"));
					req.setAttribute("tefOutageNos", req.getParameter("tefOutageNosR"));
					
					ArrayList<SNRTechnology> snrTList = 
							(ArrayList<SNRTechnology>) session.getAttribute(ServletConstants.SNR_TECHNOLOGY_COLLECTION_NAME_IN_SESSION);
					Timestamp implementationStartTS = null;
					Timestamp implementationEndTS = null;
					Timestamp implOutageStartTS = null;
					Timestamp implOutageEndTS = null;
			    	req.setAttribute("implementationStatus", implementationStatus);
			    	req.setAttribute("completingBOEngineer", req.getParameter("completingBOEngineer"));
			    	req.setAttribute("implementationStartDT", implementationStartDT);
			    	req.setAttribute("implementationEndDT", implementationEndDT);
			    	req.setAttribute("implOutageStartDT", implOutageStartDT);
			    	req.setAttribute("implOutageEndDT", implOutageEndDT);
			    	req.setAttribute("implementationAbortType", implementationAbortType);
			    	req.setAttribute("implementation2GInd", implementation2GInd);
			    	req.setAttribute("implementation3GInd", implementation3GInd);
			    	req.setAttribute("implementation4GInd", implementation4GInd);
			    	req.setAttribute("implementationO2Ind", implementationO2Ind);
			    	req.setAttribute("implementationHealthChecksInd", implementationHealthChecksInd);
			    	req.setAttribute("implementationActiveAlarmsInd", implementationActiveAlarmsInd);
			    	req.setAttribute("implementationNSANetActsInd", implementationNSANetActsInd);
			    	req.setAttribute("implementationHOPDeliveredInd", implementationHOPDeliveredInd);
			    	req.setAttribute("implementationHOPFilename", implementationHOPFilename);
			    	req.setAttribute("implementationHOPOnSharePoint", implementationHOPOnSharePoint);
			    	req.setAttribute("implementationEFUpdated", implementationEFUpdated);
			    	req.setAttribute("implementationSFRCompleted", implementationSFRCompleted);
			    	req.setAttribute("implementationSFROnSharePoint", implementationSFROnSharePoint);
			    	req.setAttribute("buttonPressed", "showImplementationDetail");
			    	for (Iterator<SNRTechnology> it = snrTList.iterator(); it.hasNext(); ) {
			    		SNRTechnology snrT = it.next();
			    		String screenName = "select" + snrT.getSelectName();
			    		String implemented = req.getParameter(screenName);
				    	req.setAttribute(screenName, implemented);
			    		snrT.setImplemented(implemented);
			    	}
					session.setAttribute(ServletConstants.SNR_TECHNOLOGY_COLLECTION_NAME_IN_SESSION, snrTList);
					if ((implementationStatus.equalsIgnoreCase("Aborted")) &&
							(StringUtil.hasNoValue(implementationAbortType))) {
						throw new Exception("Abort Type Ind must be entered if Status is 'Aborted'");
					}
					if ((!StringUtil.hasNoValue(implementationStatus)) &&
							(((!StringUtil.hasNoValue(implementationStartDT)) &&
							(StringUtil.hasNoValue(implementationEndDT))) || 
							((StringUtil.hasNoValue(implementationStartDT)) &&
							(!StringUtil.hasNoValue(implementationEndDT))))) {
						throw new Exception("Implementation dates - enter both or neither");
					}
					if (!StringUtil.hasNoValue(implementationStartDT)) {
						try {
							implementationStartTS = Timestamp.valueOf(implementationStartDT.substring(6, 10) + "-" +
									implementationStartDT.substring(3, 5) + "-" +	
									implementationStartDT.substring(0, 2) + " " + 
									implementationStartDT.substring(11, 16) + ":00");
						} catch (Exception ex) {
							throw new Exception("Invalid value entered for Implementation start date");
						}
					}
					if (!StringUtil.hasNoValue(implementationEndDT)) {
						try {
							implementationEndTS = Timestamp.valueOf(implementationEndDT.substring(6, 10) + "-" +
									implementationEndDT.substring(3, 5) + "-" +	
									implementationEndDT.substring(0, 2) + " " + 
									implementationEndDT.substring(11, 16) + ":59");
						} catch (Exception ex) {
							throw new Exception("Invalid value entered for Implementation end date");
						}
					}
					if ((!StringUtil.hasNoValue(implementationStartDT)) &&
							(!StringUtil.hasNoValue(implementationEndDT))) {
						if (implementationStartTS.after(implementationEndTS)) {
							throw new Exception("Implementation start date cannot be after Implementation end date");
						}
					}
					if ((!StringUtil.hasNoValue(implementationStatus)) &&
							(((!StringUtil.hasNoValue(implOutageStartDT)) &&
							(StringUtil.hasNoValue(implOutageEndDT))) || 
							((StringUtil.hasNoValue(implOutageStartDT)) &&
							(!StringUtil.hasNoValue(implOutageEndDT))))) {
						throw new Exception("Implementation Outage dates - enter both or neither");
					}
					if (!StringUtil.hasNoValue(implOutageStartDT)) {
						try {
							implOutageStartTS = Timestamp.valueOf(implOutageStartDT.substring(6, 10) + "-" +
									implOutageStartDT.substring(3, 5) + "-" +	
									implOutageStartDT.substring(0, 2) + " " + 
									implOutageStartDT.substring(11, 16) + ":00");
						} catch (Exception ex) {
							throw new Exception("Invalid value entered for Implementation Outage start date");
						}
					}
					if (!StringUtil.hasNoValue(implOutageEndDT)) {
						try {
							implOutageEndTS = Timestamp.valueOf(implOutageEndDT.substring(6, 10) + "-" +
									implOutageEndDT.substring(3, 5) + "-" +	
									implOutageEndDT.substring(0, 2) + " " + 
									implOutageEndDT.substring(11, 16) + ":59");
						} catch (Exception ex) {
							throw new Exception("Invalid value entered for Implementation Outage end date");
						}
					}
					if ((!StringUtil.hasNoValue(implOutageStartDT)) &&
							(!StringUtil.hasNoValue(implOutageEndDT))) {
						if (implOutageStartTS.after(implOutageEndTS)) {
							throw new Exception("Implementation Outage start date cannot be after Implementation Outage end date");
						}
					}
					String errorType = "updating SNR implementation detail";
					try {
						conn = DriverManager.getConnection(url);
				    	conn.setAutoCommit(false);
						cstmt = conn.prepareCall("{call UpdateSNRImplementation(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						cstmt.setLong(1, Long.parseLong(snrId));
						cstmt.setString(2, implementationStatus);
						cstmt.setTimestamp(3, implementationStartTS);
						cstmt.setTimestamp(4, implementationEndTS);
						cstmt.setString(5, implementationAbortType);
						cstmt.setString(6, implementation2GInd);
						cstmt.setString(7, implementation3GInd);
						cstmt.setString(8, implementation4GInd);
						cstmt.setString(9, implementationO2Ind);
						cstmt.setString(10, implementationHealthChecksInd);
						cstmt.setString(11, implementationActiveAlarmsInd);
						cstmt.setString(12, implementationNSANetActsInd);
						cstmt.setString(13, implementationHOPDeliveredInd);
						cstmt.setString(14, implementationHOPFilename);
						cstmt.setString(15, implementationHOPOnSharePoint);
						cstmt.setString(16, implementationEFUpdated);
						cstmt.setString(17, implementationSFRCompleted);
						cstmt.setString(18, implementationSFROnSharePoint);
						cstmt.setString(19, thisU.getNameForLastUpdatedBy());
						cstmt.setTimestamp(20, implOutageStartTS);
						cstmt.setTimestamp(21, implOutageEndTS);
						cstmt.setString(22, StringUtil.hasNoValue(ef345ClaimDT)?null:ef345ClaimDT);
						cstmt.setString(23, StringUtil.hasNoValue(ef360ClaimDT)?null:ef360ClaimDT);
						cstmt.setString(24, StringUtil.hasNoValue(ef390ClaimDT)?null:ef390ClaimDT);
						cstmt.setString(25, StringUtil.hasNoValue(ef400ClaimDT)?null:ef400ClaimDT);
						cstmt.setString(26, StringUtil.hasNoValue(ef410ClaimDT)?null:ef410ClaimDT);
						boolean found = cstmt.execute();
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								if (!rs.getString(1).equalsIgnoreCase("Y")) {
									throw new Exception("negative return code from UpdateSNRImplementation()");
								}
							}
						}
						cstmt.close();
				    	for (Iterator<SNRTechnology> it = snrTList.iterator(); it.hasNext(); ) {
				    		SNRTechnology snrT = it.next();
				    		if (snrT.hasValue()) {
								cstmt = conn.prepareCall("{call UpdateSNRTechnology(?,?,?,?)}");
								cstmt.setLong(1, snrT.getSNRId());
								cstmt.setLong(2, snrT.getTechnologyId());
								cstmt.setString(3, snrT.getImplemented());
								cstmt.setString(4, thisU.getNameForLastUpdatedBy());
								found = cstmt.execute();
								if (found) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										if (!rs.getString(1).equalsIgnoreCase("Y")) {
											throw new Exception("negative return code from UpdateSNRTechnology()");
										}
									}
								}
								cstmt.close();
				    		}
				    	}
						req.setAttribute("userMessage", "Implementation detail for NR " + nrId +
								" updated");
						if (buttonPressed.equals("snrImplementationDetailComplete")) {
							errorType = "completing SNR";
							cstmt = conn.prepareCall("{call Complete_SNR(?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setString(2, implementationStatus);
							cstmt.setString(3, thisU.getNameForLastUpdatedBy());
							found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (!rs.getString(1).equalsIgnoreCase("Y")) {
										throw new Exception("negative return code from Complete_SNR()");
									}
								}
							}
							req.setAttribute("completingBOEngineer", thisU.getNameForLastUpdatedBy());
				        	req.setAttribute("userMessage", "NR " + nrId + " marked as completed");
							req.removeAttribute("buttonPressed");
						}
						conn.commit();
					} catch (Exception ex) {
				    	conn.rollback();
			        	req.setAttribute("userMessage", "Error: " + errorType + ", " + ex.getMessage());
				    } finally {
				    	try {
					    	if (cstmt != null && !cstmt.isClosed()) cstmt.close();
					    	conn.close();
					    } catch (SQLException ex) {
				        	req.setAttribute("userMessage", "Error: " + errorType + ", " + ex.getMessage());
					    }
				    	//req.setAttribute("buttonPressed", "showImplementationDetail");
				    } 
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: " + ex.getMessage());
			    }
			} else if (buttonPressed.equals("addComA")) {
				req.setAttribute("snrCommentaryTypeInit", "Access");
			} else if (buttonPressed.equals("addComI")) {
				req.setAttribute("snrCommentaryTypeInit", "Implementation");
			} else if (buttonPressed.equals("addComV")) {
				req.setAttribute("snrCommentaryTypeInit", "Implementation");
			} else if (buttonPressed.equals("rescheduleSNRSubmit")) {
				String scheduledDT = req.getParameter("scheduledDT");
		    	req.setAttribute("scheduledDT", scheduledDT);
		    	req.setAttribute("buttonPressed", "realloc");
				Timestamp scheduledTS = null;
			    try {
					if (StringUtil.hasNoValue(scheduledDT)) {
						throw new Exception("Scheduled date cannot be blank");
					} else {
					    try {
							scheduledTS = Timestamp.valueOf(scheduledDT.substring(6, 10) + "-" +
									scheduledDT.substring(3, 5) + "-" +	
									scheduledDT.substring(0, 2) + " 00:00:00");
						} catch (Exception ex) {
							throw new Exception("Invalid value entered for scheduled date");
						}
					}
					String scheduledDTOrig = (String) session.getAttribute(ServletConstants.SNR_SCHEDULED_DATE_IN_SESSION);
					if (!StringUtil.hasNoValue(scheduledDTOrig)) {
						Timestamp scheduledTSOrig = Timestamp.valueOf(scheduledDTOrig.substring(6, 10) + "-" +
								scheduledDTOrig.substring(3, 5) + "-" +	
								scheduledDTOrig.substring(0, 2) + " 00:00:00");
						if (scheduledTS.equals(scheduledTSOrig)) {
							throw new Exception("Scheduled date has not changed");
						}
					}
					Calendar cal = new GregorianCalendar();
					// reset hour, minutes, seconds and millis
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					/*Date today = new Date(cal.getTimeInMillis());
					if (scheduledTS.before(today)) {
						throw new Exception("Scheduled date cannot be in the past");
					}*/
				    try {
						conn = DriverManager.getConnection(url);
						cstmt = conn.prepareCall("{call RescheduleSNR(?,?,?)}");
						cstmt.setLong(1, Long.parseLong(snrId));
						cstmt.setTimestamp(2, scheduledTS);
						cstmt.setString(3, thisU.getNameForLastUpdatedBy());
						boolean found = cstmt.execute();
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								if (rs.getString(1).equalsIgnoreCase("Y")) {
									req.setAttribute("userMessage", "NR " + nrId +
											" rescheduled for " + scheduledDT);
								} else {
									throw new Exception("negative return code from RescheduleSNR()");
								}
							}
						}
				    } catch (Exception ex) {
			        	req.setAttribute("userMessage", "Error: unable to reschedule SNR, " + ex.getMessage());
				    } finally {
				    	try {
					    	cstmt.close();
					    	conn.close();
					    } catch (SQLException ex) {
				        	req.setAttribute("userMessage", "Error: rescheduling SNR, " + ex.getMessage());
					    }
				    }
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: " + ex.getMessage());
			    }
			} else if (buttonPressed.equals("cancel")) {
			    try {
			    	preserveFilters(req);
					conn = DriverManager.getConnection(url);
					cstmt = conn.prepareCall("{call Cancel_SNR(?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setString(2, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							if (rs.getString(1).equalsIgnoreCase("Y")) {
								req.setAttribute("userMessage", "NR " + nrId +
										" cancelled");
							} else {
								throw new Exception("negative return code from Cancel_SNR()");
							}
						}
					}
			    	req.removeAttribute("snrId");
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: unable to cancel SNR, " + ex.getMessage());
			    } finally {
			    	try {
				    	cstmt.close();
				    	conn.close();
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: cancelling SNR, " + ex.getMessage());
				    }
			    }
			/*} else if (buttonPressed.equals("deleteEngineer")) {
		    	req.setAttribute("buttonPressed", reallocType);
			    try {
					conn = DriverManager.getConnection(url);
					cstmt = conn.prepareCall("{call RemoveSNRUserRole(?,?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setLong(2, Long.parseLong(req.getParameter("userId")));
					cstmt.setString(3, req.getParameter("snrUserRole"));
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							if (rs.getString(1).equalsIgnoreCase("Y")) {
								req.setAttribute("userMessage", "Engineer removed from SNR");
							} else {
								throw new Exception("negative return code from RemoveSNRUserRole()");
							}
						}
					}
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: unable to remove SNR user role, " + ex.getMessage());
			    } finally {
			    	try {
				    	cstmt.close();
				    	conn.close();
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: removing SNR user role, " + ex.getMessage());
				    }
			    }
			} else if (buttonPressed.equals("addRole")) {
				req.setAttribute("snrUserRole", req.getParameter("snrUserRole"));*/
			} else if (buttonPressed.equals("realloc")) {
				req.setAttribute("reallocType", buttonPressed);
			/*} else if (buttonPressed.equals("addRoleSubmit")) {
		    	req.setAttribute("buttonPressed", reallocType);
			    try {
					conn = DriverManager.getConnection(url);
					cstmt = conn.prepareCall("{call AddSNRUserRole(?,?,?,?,?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setLong(2, Long.parseLong(req.getParameter("selectAvailableUsersForRole")));
					cstmt.setString(3, req.getParameter("snrUserRole"));
					cstmt.setLong(4, req.getParameter("selectThirdParty")==null?-1:Long.parseLong(req.getParameter("selectThirdParty")));
					cstmt.setLong(5, req.getParameter("selectFENumber")==null?-1:Long.parseLong(req.getParameter("selectFENumber")));
					cstmt.setString(6, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							if (rs.getString(1).equalsIgnoreCase("Y")) {
								req.setAttribute("userMessage", "Engineer added to SNR");
							} else {
								throw new Exception("negative return code from AddSNRUserRole()");
							}
						}
					}
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: unable to add SNR user role, " + ex.getMessage());
			    } finally {
			    	try {
				    	cstmt.close();
				    	conn.close();
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: adding SNR user role, " + ex.getMessage());
				    }
			    }*/
			} else if (buttonPressed.equals("cancelSM")) {
		    	req.removeAttribute("buttonPressed");
			    try {
					conn = DriverManager.getConnection(url);
					cstmt = conn.prepareCall("{call RemoveAllSNRUserRoles(?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setString(2, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							if (rs.getString(1).equalsIgnoreCase("Y")) {
								req.setAttribute("userMessage", "Scheduling cancelled and all engineers removed from SNR");
							} else {
								throw new Exception("negative return code from RemoveAllSNRUserRoles()");
							}
						}
					}
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: RemoveAllSNRUserRoles(), " + ex.getMessage());
			    } finally {
			    	try {
				    	cstmt.close();
				    	conn.close();
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: RemoveAllSNRUserRoles(), " + ex.getMessage());
				    }
			    }
			} else if (buttonPressed.equals("confirmSM")) {
				String scheduledDT = req.getParameter("scheduledDT");
				Timestamp scheduledTS = null;
				req.setAttribute("buttonPressed", "schedM");
			    try {
					Date scheduledDate = null;
					if (StringUtil.hasNoValue(scheduledDT)) {
						throw new Exception("Scheduled date cannot be blank");
					} else {
					    try {
							scheduledTS = Timestamp.valueOf(scheduledDT.substring(6, 10) + "-" +
									scheduledDT.substring(3, 5) + "-" +	
									scheduledDT.substring(0, 2) + " 00:00:00");
						} catch (Exception ex) {
							throw new Exception("Invalid value entered for scheduled date");
						}
					}
					String scheduledDTOrig = (String) session.getAttribute(ServletConstants.SNR_SCHEDULED_DATE_IN_SESSION);
					if (!StringUtil.hasNoValue(scheduledDTOrig)) {
						Timestamp scheduledTSOrig = Timestamp.valueOf(scheduledDTOrig.substring(6, 10) + "-" +
								scheduledDTOrig.substring(3, 5) + "-" +	
								scheduledDTOrig.substring(0, 2) + " 00:00:00");
						if (scheduledTS.equals(scheduledTSOrig)) {
							throw new Exception("Scheduled date has not changed");
						}
					}
					Calendar cal = new GregorianCalendar();
					// reset hour, minutes, seconds and millis
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					/*Date today = new Date(cal.getTimeInMillis());
					if (scheduledTS.before(today)) {
						throw new Exception("Scheduled date cannot be in the past");
					}*/
			    	req.removeAttribute("buttonPressed");
			    	req.removeAttribute("snrId");
				    try {
						conn = DriverManager.getConnection(url);
						cstmt = conn.prepareCall("{call Schedule_SNR(?,?,?,?,?,?)}");
						cstmt.setString(1, site);
						cstmt.setString(2, nrId);
						cstmt.setTimestamp(3, scheduledTS);
						cstmt.setString(4, req.getParameter("selectWorkflowName"));
						cstmt.setString(5, req.getParameter("scheduleCommentary"));
						cstmt.setString(6, thisU.getNameForLastUpdatedBy());
						boolean found = cstmt.execute();
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								if (rs.getString(1).equalsIgnoreCase("Y")) {
									req.setAttribute("userMessage", "SNR scheduled");
								} else {
									throw new Exception("negative return code from Schedule_SNR()");
								}
							}
						}
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: unable to schedule SNR, " + ex.getMessage());
				    } finally {
				    	try {
					    	cstmt.close();
					    	conn.close();
					    } catch (SQLException ex) {
				        	req.setAttribute("userMessage", "Error: scheduling SNR, " + ex.getMessage());
					    }
				    }
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: scheduling SNR, " + ex.getMessage());
			    }
			} else if (buttonPressed.equals("clearAll")) {
		    	req.setAttribute(filters[0], "");
		    	req.setAttribute(filters[1], "");
				for (int i = 2; i < filters.length; i++) {
			    	req.setAttribute(filters[i], "All");
				}
			} else {
				for (int i = 0; i < filters.length; i++) {
					filterValues.put(filters[i], req.getParameter(filters[i]));
				}
				if (buttonPressed.equals("clear")) {
					if (whichFilter.equals("filterScheduled")) {
						filterValues.put("filterScheduledStart", "");
						filterValues.put("filterScheduledEnd", "");
					} else {
						filterValues.put(whichFilter, "All");
					}
				}
				for (int i = 0; i < filters.length; i++) {
			    	req.setAttribute(filters[i], filterValues.get(filters[i]));
				}
			}
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
		//RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+"#techComp");
      	dispatcher.forward(req,resp);
	}

	private void validatePreCheckItemUpdates(HttpServletRequest req, 
			Collection<PreCheckListItem> pciList, HttpSession session) 
					throws Exception {
		String missing = null;
		String invalid = null;
		for (Iterator<PreCheckListItem> it = pciList.iterator(); it.hasNext(); ) {
			PreCheckListItem pcli = it.next();
			String screenValue = req.getParameter("select" + pcli.getItemName());
			if (pcli.isRequired() && StringUtil.hasNoValue(screenValue)) {
				missing = pcli.getItemDescription();
			} else if (!pcli.setStringValue(screenValue)) {
				invalid = pcli.getItemDescription();
			}
		}
		session.setAttribute(ServletConstants.PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION,
				pciList);
		if (missing != null) {
			throw new Exception("a value must be entered for " + missing);
		} else if (invalid != null) {
			throw new Exception("invalid value entered for " + invalid);
		}
	}
	
	private void preserveFilters(HttpServletRequest request ){
		request.setAttribute("filterSite",request.getParameter("filterSite"));
		request.setAttribute("filterNRId",request.getParameter("filterNRId"));
		request.setAttribute("filterStatus",request.getParameter("filterStatus"));
		request.setAttribute("filterScheduledStart",request.getParameter("filterScheduledStart"));	
		request.setAttribute("filterScheduledEnd",request.getParameter("filterScheduledEnd"));		
		request.setAttribute("filterScheduledEnd",request.getParameter("filterScheduledEnd"));		
		request.setAttribute("filterBOEngineer",request.getParameter("filterBOEngineer"));		
	}
}
