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

import com.devoteam.tracker.model.SiteProgress;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.UtilBean;

public class SiteProgressServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4161042526665887792L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/siteProgress.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			String snrId = req.getParameter("snrId");
	    	req.setAttribute("snrId", snrId);
			String site = req.getParameter("site");
	    	req.setAttribute("site", site);
			String nrId = req.getParameter("nrId");
	    	req.setAttribute("nrId", nrId);
			String snrStatus = req.getParameter("snrStatus");
	    	req.setAttribute("snrStatus", snrStatus);
			String returnScreen = req.getParameter("returnScreen");
	    	req.setAttribute("returnScreen", returnScreen);
	    	String buttonPressed = req.getParameter("buttonPressed");
	    	if (buttonPressed.equals("update")) {
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
				String checkedIn = req.getParameter("checkedIn");
				String bookedOn = req.getParameter("bookedOn");
				String siteAccessed = req.getParameter("siteAccessed");
				String physicalChecks = req.getParameter("physicalChecks");
				String preCallTest = req.getParameter("preCallTest");
				String siteLocked = req.getParameter("siteLocked");
				String hwInstalls = req.getParameter("hwInstalls");
				String commissioningFE = req.getParameter("commissioningFE");
				String commissioningBO = req.getParameter("commissioningBO");
				String txProvisioning = req.getParameter("txProvisioning");
				String fieldWork = req.getParameter("fieldWork");
				String siteUnlocked = req.getParameter("siteUnlocked");
				String postCallTest = req.getParameter("postCallTest");
				String closureCode = req.getParameter("closureCode");
				String leaveSite = req.getParameter("leaveSite");
				String bookOffSite = req.getParameter("bookOffSite");
				String performanceMonitoring = req.getParameter("performanceMonitoring");
				String initialHOP = req.getParameter("initialHOP");
				String issueOwner = req.getParameter("issueOwner");
				String crqClosureCode = req.getParameter("crqClosureCode");
				String riskIndicator = req.getParameter("riskIndicator");
				String progressIssue = req.getParameter("progressIssue");
	    		String updateResult = uB.updateSiteProgress(
	    				Long.parseLong(snrId),
	    				checkedIn,
						bookedOn,
						siteAccessed,
						physicalChecks,
						preCallTest,
						siteLocked,
						hwInstalls,
						commissioningFE,
						commissioningBO,
						txProvisioning,
						fieldWork,
						siteUnlocked,
						postCallTest,
						closureCode,
						leaveSite,
						bookOffSite,
						performanceMonitoring,
						initialHOP,
						issueOwner,
						thisU.getNameForLastUpdatedBy(),
						crqClosureCode,
						riskIndicator,
						progressIssue);
	    		session.setAttribute("userMessage", updateResult);
	    	}	    	
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
	}
}
