package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
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
import com.devoteam.tracker.util.ServletConstants;

public class ViewSNRHistoryServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8208582788857824551L;
	private final String[] filters = {"filterSNRId", "filterCustomer",
			"filterPotId", "filterPotName", "filterSite", "filterNRId", 
			"filterStatus",	"filterImplementationStatus", "filterPCO", 
			"filterAbortType", "filterHistory"};
	private Map<String, String> filterValues = new HashMap<String, String>();
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/viewSNRHistory.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			String whichFilter = req.getParameter("whichFilter");
			String buttonPressed = req.getParameter("buttonPressed");
			String snrId = req.getParameter("snrId");
			String historyInd = req.getParameter("historyInd");
			String historyDT = req.getParameter("historyDT");
			req.setAttribute("nrId", req.getParameter("nrId"));
			req.setAttribute("site", req.getParameter("site"));
			if ((buttonPressed.equals("showDetail")) ||
					(buttonPressed.equals("viewCom")) ||
					(buttonPressed.equals("addComV")) ||
					(buttonPressed.equals("closeD"))) {
		    	req.setAttribute("buttonPressed", buttonPressed);
		    	req.setAttribute("snrId", snrId);
		    	req.setAttribute("historyInd", historyInd);
		    	req.setAttribute("historyDT", historyDT);
			} else if (buttonPressed.equals("addCommentarySubmit")) {
		    	req.setAttribute("snrId", snrId);
		    	req.setAttribute("historyInd", historyInd);
		    	req.setAttribute("historyDT", historyDT);
		    	req.setAttribute("buttonPressed", "viewCom");
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
			} else if (buttonPressed.equals("clearAll")) {
		    	req.setAttribute(filters[0], "");
				for (int i = 1; i < filters.length; i++) {
			    	req.setAttribute(filters[i], "All");
				}
			} else {
				for (int i = 0; i < filters.length; i++) {
					filterValues.put(filters[i], req.getParameter(filters[i]));
				}
				if (buttonPressed.equals("clear")) {
					filterValues.put(whichFilter, whichFilter.equals("filterSNRId")?"":"All");
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
