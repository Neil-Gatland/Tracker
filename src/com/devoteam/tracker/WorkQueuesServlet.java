package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.Option;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.StringUtil;

public class WorkQueuesServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1914613112220276187L;
	private final String[] filters = {"filterScheduledStart", 
			"filterScheduledEnd", "filterSite", "filterNRId", 
			"filterStatus",	"filterImplementationStatus", "filterPCO", 
			"filterPrevImpl", "filterJobType", "filterCRQINCRaised"};
	private Map<String, String> filterValues = new HashMap<String, String>();

	/**
	 * 
	 */
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/workQueues.jsp";
		try {
			HttpSession session = req.getSession(false);
			if (session == null) {
				destination = "/logon.jsp";
				session = req.getSession(true);
				session.setAttribute("userMessage", "Please enter a valid user id and password");
			} else {
				String whichFilter = req.getParameter("whichFilter");
				String buttonPressed = req.getParameter("buttonPressed");
				String snrId = req.getParameter("snrId");
				String nrId = req.getParameter("nrId");
		    	req.setAttribute("buttonPressed", buttonPressed);
		    	req.setAttribute("snrId", snrId);
		    	req.setAttribute("snrStatus", req.getParameter("snrStatus"));
		    	req.setAttribute("historyInd", req.getParameter("historyInd"));
		    	req.setAttribute("customerId", req.getParameter("customerId"));
		    	req.setAttribute("efComplete", req.getParameter("efComplete"));
				String site = req.getParameter("site");
		    	req.setAttribute("site", site);
		    	req.setAttribute("nrId", nrId);
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
				if (buttonPressed.equals("addCommentarySubmit")) {
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
				    	req.setAttribute("buttonPressed", "viewCom");
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: Unable to add commentary, " + ex.getMessage());
			        	req.removeAttribute("buttonPressed");
				    } finally {
						cstmt.close();
						conn.close();
				    }
				} else if (buttonPressed.equals("holdRel")) {
				    try {
				    	preserveFilters(req);
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
			        	req.removeAttribute("buttonPressed");
						cstmt.close();
						conn.close();
				    }
				} else if (buttonPressed.equals("preCheckThis")) {
					session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, 
							ServletConstants.PRE_CHECK_MAINTENANCE);
			    	//req.setAttribute("snrIdFilter", snrId);
					req.setAttribute("filterNRId", nrId);
		        	req.removeAttribute("snrId");
					destination = "/preCheckMaintenance.jsp";
				} else if (buttonPressed.equals("updNRId")) {
			    	req.setAttribute("nrIdU", req.getParameter("nrId"));
			    	preserveFilters(req);
				} else if (buttonPressed.equals("updateNRIdSubmit")) {
					String nrIdU = req.getParameter("nrIdU");
			    	req.setAttribute("nrIdU", nrIdU);
			    	preserveFilters(req);
				    try {
				    	conn = DriverManager.getConnection(url);
				    	cstmt = conn.prepareCall("{call CheckNRIdExists(?)}");
						cstmt.setString(1, nrIdU);
						boolean found = cstmt.execute();
						boolean valid = false; 
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							valid = !rs.next();
						}
						req.setAttribute("nrIdValid", valid?"true":"false");
						if (valid) {
							req.setAttribute("userMessage", "Click 'Confirm' to complete NR Id update");
						} else {	
							req.setAttribute("userMessage", "Error: NR Id exists already");
						}
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: Unable to validate NR Id, " + ex.getMessage());
			        	req.removeAttribute("buttonPressed");
				    } finally {
						cstmt.close();
						conn.close();
				    }
				} else if (buttonPressed.equals("confirmNRIdSubmit")) {
					String nrIdU = req.getParameter("nrIdU");
					preserveFilters(req);
				    try {
				    	conn = DriverManager.getConnection(url);
				    	cstmt = conn.prepareCall("{call UpdateNRId(?,?,?)}");
						cstmt.setLong(1, Long.parseLong(snrId));
						cstmt.setString(2, nrIdU);
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
							req.setAttribute("userMessage", "NR Id updated");
					    	req.setAttribute("nrId", nrIdU);
						} else {
				        	req.setAttribute("userMessage", "Error: Unable to update NR Id");
						}
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: Unable to update NR Id, " + ex.getMessage());
				    } finally {
			        	req.removeAttribute("buttonPressed");
						cstmt.close();
						conn.close();
				    }
				} else if (buttonPressed.equals("updED")) {
				    try {
				    	preserveFilters(req);
				    	conn = DriverManager.getConnection(url);
				    	cstmt = conn.prepareCall("{call GetEvenflowDates(?)}");
						cstmt.setLong(1, Long.parseLong(snrId));
						boolean found = cstmt.execute();
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								req.setAttribute("ef345ClaimDT", rs.getString(1));
								req.setAttribute("ef360ClaimDT", rs.getString(2));
								req.setAttribute("ef390ClaimDT", rs.getString(3));
								req.setAttribute("ef400ClaimDT", rs.getString(4));
								req.setAttribute("ef410ClaimDT", rs.getString(5));
							} else {
					        	req.setAttribute("userMessage", "Error: Unable to get Evenflow dates");
							}
						} else {
				        	req.setAttribute("userMessage", "Error: Unable to get Evenflow dates");
						}
				    } catch (SQLException ex) {
			        	req.removeAttribute("buttonPressed");
			        	req.setAttribute("userMessage", "Error: Unable to get Evenflow dates, " + ex.getMessage());
				    } finally {
						cstmt.close();
						conn.close();
				    }
				} else if (buttonPressed.equals("updateEFDTSubmit")) {
					preserveFilters(req);
					String ef345ClaimDT = req.getParameter("ef345ClaimDT");
					String ef360ClaimDT = req.getParameter("ef360ClaimDT");
					String ef390ClaimDT = req.getParameter("ef390ClaimDT");
					String ef400ClaimDT = req.getParameter("ef400ClaimDT");
					String ef410ClaimDT = req.getParameter("ef410ClaimDT");
				    try {
				    	conn = DriverManager.getConnection(url);
				    	cstmt = conn.prepareCall("{call UpdateEvenflowDates(?,?,?,?,?,?,?)}");
						cstmt.setLong(1, Long.parseLong(snrId));
						cstmt.setString(2, StringUtil.hasNoValue(ef345ClaimDT)?null:ef345ClaimDT);
						cstmt.setString(3, StringUtil.hasNoValue(ef360ClaimDT)?null:ef360ClaimDT);
						cstmt.setString(4, StringUtil.hasNoValue(ef390ClaimDT)?null:ef390ClaimDT);
						cstmt.setString(5, StringUtil.hasNoValue(ef400ClaimDT)?null:ef400ClaimDT);
						cstmt.setString(6, StringUtil.hasNoValue(ef410ClaimDT)?null:ef410ClaimDT);
						cstmt.setString(7, thisU.getNameForLastUpdatedBy());
						boolean found = cstmt.execute();
						String ok = "N";
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								ok = rs.getString(1);
							}
						}
						if (ok.equalsIgnoreCase("Y")) {
							req.setAttribute("userMessage", "Evenflow dates updated");
						} else {
				        	req.setAttribute("userMessage", "Error: Unable to update Evenflow dates");
						}
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: Unable to update Evenflow dates, " + ex.getMessage());
				    } finally {
			        	req.removeAttribute("buttonPressed");
						cstmt.close();
						conn.close();
				    }
				} else if (buttonPressed.equals("viewSiteCom")) {
			    	req.setAttribute("commentarySite", site);
				} else if (buttonPressed.equals("clearAll")) {
			    	req.setAttribute(filters[0], "");
			    	req.setAttribute(filters[1], "");
					for (int i = 2; i < filters.length; i++) {
				    	req.setAttribute(filters[i], "All");
					}
				} else if (buttonPressed.equals("closeNR")) {
				    try {
				    	preserveFilters(req);
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
	    } catch (SQLException ex) {
        	req.setAttribute("userMessage", "Error: " + ex.getMessage());
        	req.removeAttribute("buttonPressed");
	    } finally {
			Random r = new Random();
			String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	      	dispatcher.forward(req,resp);
	    }
	}
	
	private void preserveFilters(HttpServletRequest request ){
		request.setAttribute("filterSite",request.getParameter("filterSite"));
		request.setAttribute("filterNRId",request.getParameter("filterNRId"));
		request.setAttribute("filterStatus",request.getParameter("filterStatus"));
		request.setAttribute("filterImplementationStatus",request.getParameter("filterImplementationStatus"));
		request.setAttribute("filterPCO",request.getParameter("filterPCO"));		
		request.setAttribute("filterPrevImpl",request.getParameter("filterPrevImpl"));		
		request.setAttribute("filterJobType",request.getParameter("filterJobType"));		
		request.setAttribute("filterCRQINCRaised",request.getParameter("filterCRQINCRaised"));		
		request.setAttribute("filterScheduledStart",request.getParameter("filterScheduledStart"));	
		request.setAttribute("filterScheduledEnd",request.getParameter("filterScheduledEnd"));		
	}

}
