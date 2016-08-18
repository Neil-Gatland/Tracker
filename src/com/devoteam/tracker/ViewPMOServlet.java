package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;

public class ViewPMOServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8208582788857824551L;
	private final String[] filters = {"filterScheduledStart", 
			"filterScheduledEnd", "filterSite", "filterNRId", 
			"filterStatus"};
	private Map<String, String> filterValues = new HashMap<String, String>();
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/viewPMOList.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid user id and password");
		} else {
			String whichFilter = req.getParameter("whichFilter");
			String buttonPressed = req.getParameter("buttonPressed");
			String snrId = req.getParameter("snrId");
	    	req.setAttribute("snrId", snrId);
	    	req.setAttribute("nrId", req.getParameter("nrId"));
	    	req.setAttribute("site", req.getParameter("site"));
	    	req.setAttribute("buttonPressed", buttonPressed);
			for (int i = 0; i < filters.length; i++) {
		    	req.setAttribute(filters[i], req.getParameter(filters[i]));
			}
			if (buttonPressed.equals("addCommentarySubmit")) {
				String onDetail = req.getParameter("onDetail")==null?"":req.getParameter("onDetail");
		    	req.setAttribute("buttonPressed", "viewCom" + (onDetail.equals("true")?"D":""));
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
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
			    } catch (SQLException ex) {
		        	req.setAttribute("userMessage", "Error: Unable to add commentary, " + ex.getMessage());
		        	req.removeAttribute("buttonPressed");
			    } finally {
			    	try {
						if ((cstmt != null) && (!cstmt.isClosed())) cstmt.close();
						if ((conn != null) && (!conn.isClosed()))conn.close();
				    } catch (SQLException ex) {
				    	
				    }
			    }
			} else if (buttonPressed.equals("updateD")) {
		    	req.setAttribute("buttonPressed", "showDetail");
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
			    try {
			        NumberFormat format = NumberFormat.getInstance(Locale.UK);
			        double aC = 0;
			    	double cC = 0;
			    	String field = "";
			    	try {
			    		field = "access cost";
			    		aC = format.parse(req.getParameter("accessCost")).doubleValue();
			    		field = "consumable cost";
			    		cC = format.parse(req.getParameter("consumableCost")).doubleValue();
			    	} catch (ParseException pe) {
			    		throw new Exception("invalid value for " + field);
			    	}
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdatePMOItem(?,?,?,?,?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setString(2, req.getParameter("selectTwoManSite"));
					cstmt.setString(3, req.getParameter("selectOOHWeekendInd"));
					cstmt.setDouble(4, aC);
					cstmt.setDouble(5, cC);
					cstmt.setString(6, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					String ok = "N";
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							ok = rs.getString(1);
						}
					}
					if (ok.equalsIgnoreCase("Y")) {
			        	req.setAttribute("userMessage", "Detail updated");
					} else {
			        	req.setAttribute("userMessage", "Error: Unable to update detail");
					}
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: Unable to update detail, " + ex.getMessage());
		        	//req.removeAttribute("buttonPressed");
			    } finally {
			    	try {
						if ((cstmt != null) && (!cstmt.isClosed())) cstmt.close();
						if ((conn != null) && (!conn.isClosed()))conn.close();
				    } catch (SQLException ex) {
				    	
				    }
			    }
			} else if (buttonPressed.equals("updateAT")) {
		    	req.setAttribute("buttonPressed", "showDetail");
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
			    try {
			    	conn = DriverManager.getConnection(url);
			    	cstmt = conn.prepareCall("{call UpdatePMOAbortType(?,?,?,?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setString(2, req.getParameter("historyDT"));
					cstmt.setString(3, req.getParameter("selectAbortType"));
					cstmt.setString(4, req.getParameter("previousAbortType"));
					cstmt.setString(5, thisU.getNameForLastUpdatedBy());
					boolean found = cstmt.execute();
					String ok = "N";
					if (found) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							ok = rs.getString(1);
						}
					}
					if (ok.equalsIgnoreCase("Y")) {
			        	req.setAttribute("userMessage", "Abort type updated");
					} else {
			        	req.setAttribute("userMessage", "Error: Unable to update abort type");
					}
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: Unable to update abort type, " + ex.getMessage());
		        	//req.removeAttribute("buttonPressed");
			    } finally {
			    	try {
						if ((cstmt != null) && (!cstmt.isClosed())) cstmt.close();
						if ((conn != null) && (!conn.isClosed()))conn.close();
				    } catch (SQLException ex) {
				    	
				    }
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
			Random r = new Random();
			String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	      	dispatcher.forward(req,resp);
		}
	}

}
