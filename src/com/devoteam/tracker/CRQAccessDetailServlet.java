package com.devoteam.tracker;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class CRQAccessDetailServlet extends HttpServlet {
	
	private static final long serialVersionUID = -3513066560717311853L;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/crqAccessDetail.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			boolean direct = req.getAttribute("buttonPressed") != null;
			long snrId = Long.parseLong(req.getParameter("snrId"));
			String showRaiseCRQ = req.getParameter("showRaiseCRQ");
			String showCloseCRQ = req.getParameter("showCloseCRQ");
			String showOtherCRQ = req.getParameter("showOtherCRQ");
			session.setAttribute("prevScreen", "crqAccessDetail");
			String buttonPressed = direct?(String)req.getAttribute("buttonPressed"):req.getParameter("buttonPressed");
			if (buttonPressed.equals("update")) {
				String vfCRQ = reformatCRQ(req.getParameter("vfCRQ"));
				String crqStatus = req.getParameter("crqStatus");
				String outagePeriod = req.getParameter("outagePeriod");
				String crStartDT = req.getParameter("crStartDT");
				String crEndDT = req.getParameter("crEndDT");
				String outageStartDT = req.getParameter("outageStartDT");
				String outageEndDT = req.getParameter("outageEndDT");
				String tefOutageRequired = req.getParameter("tefOutageRequired");
				String tefCRQ = reformatCRQ(req.getParameter("tefCRQ"));
				String p1Site = req.getParameter("p1Site");
				String crIN = req.getParameter("crIN");
				String twoManSite = req.getParameter("twoManSite");
				String crUsed = req.getParameter("crUsed");
				String oohWeekend = req.getParameter("oohWeekend");
				String accessStatus = req.getParameter("accessStatus");
				String permitType = req.getParameter("permitType");
				String obass = req.getParameter("obass");
				String rams = req.getParameter("rams");
				String vfArrangeAccess = req.getParameter("vfArrangeAccess");
				String accessConfirmed = req.getParameter("accessConfirmed");
				String escort = req.getParameter("escort");
				String healthAndSafety = req.getParameter("healthAndSafety");
				String siteAccessInformation = req.getParameter("siteAccessInformation");
				String accessCost = req.getParameter("accessCost");
				String consumableCost = req.getParameter("consumableCost");
				String siteName = req.getParameter("siteName");
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
				String updateResult =
						uB.updateCRQAccessDetails(
								snrId, 
								thisU.getNameForLastUpdatedBy(),
								vfCRQ, 
								crqStatus, 
								outagePeriod, 
								crStartDT, 
								crEndDT, 
								outageStartDT,
								outageEndDT, 
								tefOutageRequired, 
								tefCRQ, 
								p1Site, 
								crIN, 
								twoManSite, 
								crUsed, 
								oohWeekend, 
								accessStatus, 
								permitType, 
								obass, 
								rams, 
								vfArrangeAccess, 
								accessConfirmed, 
								escort, 
								healthAndSafety, 
								siteAccessInformation, 
								accessCost, 
								consumableCost, 
								siteName);
				req.setAttribute("userMessage", updateResult);
			}
			req.setAttribute("showRaiseCRQ", showRaiseCRQ);
			req.setAttribute("showCloseCRQ", showCloseCRQ);
			req.setAttribute("showOtherCRQ", showOtherCRQ);
			req.setAttribute("snrId", Long.toString(snrId));
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	  	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	  	dispatcher.forward(req,resp);		
	}
	
	private String reformatCRQ( String crqRef) {
		String reformattedCRQ = crqRef;
		if (!StringUtil.hasNoValue(reformattedCRQ)) {
			int first0 = reformattedCRQ.indexOf("0");
			Pattern pN = Pattern.compile("[1-9]");
			Matcher mN = pN.matcher(reformattedCRQ);
			int firstN = -1;
			if (mN.find()) {
				firstN = mN.start();
			}
			while ((first0 < firstN) && (first0 != -1)) {
				reformattedCRQ = reformattedCRQ.replaceFirst("[0]", "");
				first0 = reformattedCRQ.indexOf("0");
				mN = pN.matcher(reformattedCRQ);
				if (mN.find()) {
					firstN = mN.start();
				}
			}					
		}
		return reformattedCRQ;
	}
}
