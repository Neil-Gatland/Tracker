package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.PotDetail;
import com.devoteam.tracker.model.PotHeader;
import com.devoteam.tracker.model.ScheduleList;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;

public class UploadFullPotServlet extends HttpServlet{

	private static final long serialVersionUID = -3727148707780866447L;
	private StringBuilder problems;
	private String url;
	private Connection conn;	
	private CallableStatement cstmt;
	private User thisU;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		String destination = "/scheduleView.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			// prevent loss of current search position in main screen
			session.setAttribute("action", req.getParameter("selectedAction"));
			session.setAttribute("year", req.getParameter("selectedYear"));
			session.setAttribute("month", req.getParameter("selectedMonth"));
			session.setAttribute("day", req.getParameter("selectedDay"));
			session.setAttribute("week", req.getParameter("selectedWeek"));
			session.setAttribute("project", req.getParameter("selectEmailCopyProject"));
			session.setAttribute("upgradeType", req.getParameter("selectEmailCopyUpgradeType"));
			session.setAttribute("site", req.getParameter("selectedEmailCopySite"));
			session.setAttribute("nrId", req.getParameter("selectEmailCopyNRId"));
			session.setAttribute("siteStatus", req.getParameter("selectSiteStatus"));
			session.setAttribute("startDate", req.getParameter("selectedStartDate"));
			session.setAttribute("endDate", req.getParameter("selectedEndDate"));
			// set up 
	    	session.removeAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION);
	    	// Get pot filename and load spreadsheet
			String potFileName = req.getParameter("potFileName");
			problems = new StringBuilder();
			try {
				// get validated details to create the pot
				PotHeader ph = (PotHeader)session.getAttribute(ServletConstants.POT_HEADER_IN_SESSION);
				String customerName = ph.getCustomerName();
				String jobType = ph.getJobType();
				String potDate = ph.getPotDateString();
				thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
				url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				long potId = createPot(customerName,jobType,potDate);
				if (potId > 1) {
					ArrayList<PotDetail> potDetailList = 
							(ArrayList<PotDetail>)
							session.getAttribute(ServletConstants.POT_DETAIL_ARRAY_IN_SESSION);
					for (Iterator<PotDetail> it = potDetailList.iterator(); it.hasNext(); ) {
						PotDetail pD = it.next();
						String site = pD.getSite();
						String nrId = pD.getNrId();
						String upgradeType = pD.getUpgradeType();
						String scheduledDate = pD.getScheduledDateString();
						String vf2G = pD.getVf2G();
						String vf3G = pD.getVf3G();
						String vf4G = pD.getVf4G();
						String tef2G = pD.getTef2G();
						String tef3G = pD.getTef3G();
						String tef4G = pD.getTef4G();
						String paknetPaging = pD.getPaknetPaging();
						String secGW = pD.getSecGW();
						String power = pD.getPower();
						String survey = pD.getSurvey();
						String other = pD.getOther();
						String hardwareVendor = pD.getHardwareVendor();
						String boEng = pD.getBoEng();
						String feEng = pD.getFeEng();
						String feCompany = pD.getFeCompany();
						// add site and connect to Pot
						long snrId = createSNR(
										potId,
										site,
										nrId,
										upgradeType,
										scheduledDate,
										vf2G,
										vf3G,
										vf4G,
										tef2G,
										tef3G,
										tef4G,
										paknetPaging,
										secGW,
										power,
										survey,
										other,
										hardwareVendor,
										boEng,
										feEng,
										feCompany);
						if (snrId < 0 ) {
							problems.append("Create snr for "+site+"/"+nrId+"/"+scheduledDate+
									" failed : return code: "+snrId+"|");
						}
					}
				} else {
					problems.append("Create pot for "+customerName+"/"+jobType+"/"+potDate+
									" failed : return code: "+potId+"|");
				}
			} catch (Exception ex) {
				session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
						"Error: " + ex.getMessage());
			} finally {
		        if (problems.length() > 0) {
					session.setAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION, problems.toString());		
					session.setAttribute("potLoadActive", "Y");
					session.setAttribute("potFileName",potFileName);
					session.setAttribute("canConfirmPot", "Y");
				} else {					
					session.setAttribute("potLoadActive", "C");
					session.removeAttribute("potFileName");
					session.setAttribute("canConfirmPot", "N");
					session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
							"Pot "+potFileName+" successfully uploaded");
				}
		    }	
			Random r = new Random();
			String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
			resp.sendRedirect(destination+ran);
		}
	}
	
	private long createPot(String companyName, String jobType, String potDate) {
		long potId = -99;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call CreateFullPot(?,?,?,?)}");
			cstmt.setString(1, companyName);
			cstmt.setString(2, jobType);
			cstmt.setString(3, potDate);
			cstmt.setString(4, thisU.getNameForLastUpdatedBy());
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					potId = rs.getLong(1);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    } 
		}
		return potId;
	}
	
	private long createSNR(
					long potId,
					String site,
					String nrId,
					String upgradeType,
					String scheduledDate,
					String vf2G,
					String vf3G,
					String vf4G,
					String tef2G,
					String tef3G,
					String tef4G,
					String paknetPaging,
					String secGW,
					String power,
					String survey,
					String other,
					String hardwareVendor,
					String boEng,
					String feEng,
					String feCompany ) {
		long snrId = -777;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call CreateFullSNR(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cstmt.setLong(1, potId);
			cstmt.setString(2, site);
			cstmt.setString(3, nrId);
			cstmt.setString(4, upgradeType);
			cstmt.setString(5, scheduledDate);
			cstmt.setString(6, vf2G);
			cstmt.setString(7, vf3G);
			cstmt.setString(8, vf4G);
			cstmt.setString(9, tef2G);
			cstmt.setString(10, tef3G);
			cstmt.setString(11, tef4G);
			cstmt.setString(12, paknetPaging);
			cstmt.setString(13, secGW);
			cstmt.setString(14, power);
			cstmt.setString(15, survey);
			cstmt.setString(16, other);
			cstmt.setString(17, hardwareVendor);
			cstmt.setString(18, boEng);
			cstmt.setString(19, feEng);
			cstmt.setString(20, feCompany);
			cstmt.setString(21, thisU.getNameForLastUpdatedBy());
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					snrId = rs.getLong(1);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    } 
		}
		return snrId;
	}

}
