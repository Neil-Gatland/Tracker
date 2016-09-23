package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.SNR;
import com.devoteam.tracker.model.SiteConfiguration;
import com.devoteam.tracker.model.SNRTechnology;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.StringUtil;
import com.devoteam.tracker.util.UtilBean;

public class UpdateSNRListServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7858360502461620835L;
	private final String[] filters = {"filterScheduledStart", 
			"filterScheduledEnd", "filterSite", "filterNRId", 
			"filterAccessStatus", "filterCRQStatus", "filterJobType"};
	private Map<String, String> filterValues = new HashMap<String, String>();

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/updateSNRList.jsp";
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
			String site = direct?(String)req.getAttribute("site"):req.getParameter("site");
			String nrId = direct?(String)req.getAttribute("nrId"):req.getParameter("nrId");
	    	req.setAttribute("buttonPressed", buttonPressed);
	    	req.setAttribute("snrId", snrId);
	    	req.setAttribute("nrId", nrId);
	    	req.setAttribute("snrStatus", snrStatus);
	    	req.setAttribute("historyInd", historyInd);
	    	req.setAttribute("customerId", customerId);
	    	req.setAttribute("site", site);
	    	req.setAttribute("listStatus1", listStatus1);
	    	req.setAttribute("listStatus2", listStatus2);
			if (direct) {
				req.setAttribute("filterNRId", nrId);
			}
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			Connection conn = null;
			CallableStatement cstmt = null;
			if ((buttonPressed.equals("showAccessDetail")) || (buttonPressed.equals("showCRMDetail"))) {
				SNR snr = getSNRDetail(Long.parseLong(snrId), url);
		    	req.setAttribute("crInReference", snr.getCRINReference());
		    	req.setAttribute("nrId", snr.getNRId());
				if (buttonPressed.equals("showAccessDetail")) {
					UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
					SiteConfiguration sC = 
							uB.getSiteConfiguration(Long.parseLong(customerId), site);
			    	req.setAttribute("siteName", sC.getSiteName());
			    	req.setAttribute("p1SiteInd", snr.getP1SiteInd());
			    	req.setAttribute("obassInd", snr.getOBASSInd());
			    	req.setAttribute("ramsInd", snr.getRAMSInd());
			    	req.setAttribute("escortInd", snr.getEscortInd());
			    	req.setAttribute("healthSafetyInd", snr.getHealthSafetyInd());
			    	req.setAttribute("accessConfirmedInd", snr.getAccessConfirmedInd());
			    	req.setAttribute("oohWeekendInd", snr.getOOHWeekendInd());
			    	req.setAttribute("outagePeriod", snr.getOutagePeriodString());
			    	req.setAttribute("accessCost", snr.getAccessCostString().substring(1));
			    	req.setAttribute("consumableCost", snr.getConsumableCostString().substring(1));
			    	req.setAttribute("accessStatus", snr.getAccessStatus());
			    	req.setAttribute("permitType", snr.getPermitType());
			    	req.setAttribute("tefOutageRequired", snr.getTEFOutageRequired());
			    	req.setAttribute("vfArrangeAccess", snr.getVFArrangeAccess());
			    	req.setAttribute("twoManSite", snr.getTwoManSite());
			    	req.setAttribute("siteAccessInfomation", snr.getSiteAccessInfomation());
			    	req.setAttribute("tefOutageNos", snr.getTEFOutageNos());
			    	destination = "/updateSNRList.jsp";
				} else if (buttonPressed.equals("showCRMDetail")) {
			    	req.setAttribute("crInInd", snr.getCRINInd());
			    	req.setAttribute("crInStartDT", snr.getCRINStartDTString());
			    	req.setAttribute("crInEndDT", snr.getCRINEndDTString());
			    	req.setAttribute("crInUsed", snr.getCRINUsed());
			    	req.setAttribute("crqStatus", snr.getCRQStatus());
			    	req.setAttribute("implOutageStartDT", snr.getImplOutageStartDTString());
			    	req.setAttribute("implOutageEndDT", snr.getImplOutageEndDTString());
			    	req.setAttribute("implementationStatus", snr.getImplementationStatus());
			    	req.setAttribute("p1SiteInd", snr.getP1SiteInd());
			    	req.setAttribute("outagePeriod", snr.getOutagePeriodString());
			    	req.setAttribute("tefOutageRequired", snr.getTEFOutageRequired());
			    	req.setAttribute("tefOutageNos", snr.getTEFOutageNos());
			    	destination = "/updateSNRList.jsp";
				}
			} else if (buttonPressed.equals("snrAccessDetailSubmit")) {
				try {
					String p1SiteInd = req.getParameter("selectP1SiteInd");
					String obassInd = req.getParameter("selectOBASSInd");
					String ramsInd = req.getParameter("selectRAMSInd");
					String escortInd = req.getParameter("selectEscortInd");
					String healthSafetyInd = req.getParameter("selectHealthSafetyInd");
					String outagePeriod = req.getParameter("outagePeriod");
					String accessConfirmedInd = req.getParameter("selectAccessConfirmedInd");
					String accessCost = req.getParameter("accessCost");
					String consumableCost = req.getParameter("consumableCost");
					String oohWeekendInd = req.getParameter("selectOOHWeekendInd");
					String accessStatus = req.getParameter("selectAccessStatus");
					String permitType = req.getParameter("permitType");
					String tefOutageRequired = req.getParameter("selectTEFOutageRequired");
					String vfArrangeAccess = req.getParameter("selectVFArrangeAccess");
					String twoManSite = req.getParameter("selectTwoManSite");
					String siteAccessInfomation = req.getParameter("siteAccessInfomation");
					String tefOutageNos = req.getParameter("tefOutageNos");
			    	req.setAttribute("p1SiteInd", p1SiteInd);
			    	req.setAttribute("obassInd", obassInd);
			    	req.setAttribute("ramsInd", ramsInd);
			    	req.setAttribute("escortInd", escortInd);
			    	req.setAttribute("healthSafetyInd", healthSafetyInd);
			    	req.setAttribute("accessConfirmedInd", accessConfirmedInd);
			    	req.setAttribute("oohWeekendInd", oohWeekendInd);
			    	req.setAttribute("outagePeriod", outagePeriod);
			    	req.setAttribute("accessCost", accessCost);
			    	req.setAttribute("consumableCost", consumableCost);
			    	req.setAttribute("accessStatus", accessStatus);
			    	req.setAttribute("permitType", permitType);
			    	req.setAttribute("tefOutageRequired", tefOutageRequired);
			    	req.setAttribute("vfArrangeAccess", vfArrangeAccess);
			    	req.setAttribute("twoManSite", twoManSite);
			    	req.setAttribute("siteAccessInfomation", siteAccessInfomation);
			    	req.setAttribute("tefOutageNos", tefOutageNos);
			    	req.setAttribute("crInReference", req.getParameter("crInReferenceR"));
			    	req.setAttribute("siteName", req.getParameter("siteNameR"));
			    	destination = "/updateSNRList.jsp";
			        NumberFormat format = NumberFormat.getInstance(Locale.UK);
			        double aC = 0;
			    	double cC = 0;
			    	double oP = 0;
			    	String field = "";
			    	try {
			    		field = "access cost";
			    		aC = format.parse(accessCost).doubleValue();
			    		field = "consumable cost";
			    		cC = format.parse(consumableCost).doubleValue();
			    		field = "outage period";
			    		oP = format.parse(outagePeriod).doubleValue();
			    	} catch (ParseException pe) {
			    		throw new Exception("invalid value for " + field);
			    	}
				    try {
						conn = DriverManager.getConnection(url);
						cstmt = conn.prepareCall("{call Update_SNR_Access(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						cstmt.setLong(1, Long.parseLong(snrId));
						cstmt.setString(2, p1SiteInd);
						cstmt.setString(3, obassInd);
						cstmt.setString(4, ramsInd);
						cstmt.setString(5, escortInd);
						cstmt.setString(6, healthSafetyInd);
						cstmt.setDouble(7, oP);
						cstmt.setString(8, accessConfirmedInd);
						cstmt.setDouble(9, aC);
						cstmt.setDouble(10, cC);
						cstmt.setString(11, oohWeekendInd);
						cstmt.setString(12, accessStatus);
						cstmt.setString(13, permitType);
						cstmt.setString(14, tefOutageRequired);
						cstmt.setString(15, vfArrangeAccess);
						cstmt.setString(16, twoManSite);
						cstmt.setString(17, siteAccessInfomation);
						cstmt.setString(18, tefOutageNos);
						cstmt.setString(19, thisU.getNameForLastUpdatedBy());
						boolean found = cstmt.execute();
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								if (rs.getString(1).equalsIgnoreCase("Y")) {
									req.setAttribute("userMessage", "Access detail for NR " + nrId +
											" updated");
								} else {
									throw new Exception("negative return code from Update_SNR_Access()");
								}
							}
						}
				    } catch (Exception ex) {
			        	req.setAttribute("userMessage", "Error: unable to update SNR access detail, " + ex.getMessage());
				    } finally {
				    	try {
				    		if (cstmt != null) {
				    			cstmt.close();
				    		}	
				    		if (conn != null) {
				    			conn.close();
				    		}	
					    } catch (Exception ex) {
				        	req.setAttribute("userMessage", "Error: updating SNR access detail, " + ex.getMessage());
					    } /*finally {
					    	req.setAttribute("buttonPressed", "showAccessDetail");
					    }*/
				    }
				} catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: Unable to update SNR access detail, " + ex.getMessage());
			    } finally {
			    	req.setAttribute("buttonPressed", "showAccessDetail");
			    }
			} else if (buttonPressed.equals("snrCRMDetailSubmit")) {
				String crInReference = req.getParameter("crInReference");
				if (!StringUtil.hasNoValue(crInReference)) {
					int first0 = crInReference.indexOf("0");
					Pattern pN = Pattern.compile("[1-9]");
					Matcher mN = pN.matcher(crInReference);
					int firstN = -1;
					if (mN.find()) {
						firstN = mN.start();
					}
					while ((first0 < firstN) && (first0 != -1)) {
						crInReference = crInReference.replaceFirst("[0]", "");
						first0 = crInReference.indexOf("0");
						mN = pN.matcher(crInReference);
						if (mN.find()) {
							firstN = mN.start();
						}
					}
					
				}
				String crInInd = req.getParameter("selectCRINInd");
				String crInStartDT = req.getParameter("crInStartDT");
				String crInEndDT = req.getParameter("crInEndDT");
				String crInUsed = req.getParameter("selectCRINUsed");
				String crqStatus = req.getParameter("selectCRQStatus");
				String implOutageStartDT = req.getParameter("implOutageStartDT");
				String implOutageEndDT = req.getParameter("implOutageEndDT");
				String implementationStatus = req.getParameter("hiddenImplementationStatus");
				String p1SiteInd = req.getParameter("selectCP1SiteInd");
				String outagePeriod = req.getParameter("outagePeriodC");
				String tefOutageRequired = req.getParameter("selectCTEFOutageRequired");
				String tefOutageNos = req.getParameter("tefOutageNosC");
				Timestamp crInStartTS = null;
				Timestamp crInEndTS = null;
				Timestamp implOutageStartTS = null;
				Timestamp implOutageEndTS = null;
		    	req.setAttribute("crInReference", crInReference);
		    	req.setAttribute("crInInd", crInInd);
		    	req.setAttribute("crInStartDT", crInStartDT);
		    	req.setAttribute("crInEndDT", crInEndDT);
		    	req.setAttribute("crInUsed", crInUsed);
		    	req.setAttribute("crqStatus", crqStatus);
		    	req.setAttribute("implOutageStartDT", implOutageStartDT);
		    	req.setAttribute("implOutageEndDT", implOutageEndDT);
		    	req.setAttribute("implementationStatus", implementationStatus);
		    	req.setAttribute("buttonPressed", "showCRMDetail");
		    	req.setAttribute("p1SiteInd", p1SiteInd);
		    	req.setAttribute("tefOutageNos", tefOutageNos);
		    	req.setAttribute("tefOutageRequired", tefOutageRequired);
		    	req.setAttribute("outagePeriod", outagePeriod);
		        NumberFormat format = NumberFormat.getInstance(Locale.UK);
				try {
			    	double oP = 0;
			    	String field = "";
			    	try {
			    		field = "outage period";
			    		oP = format.parse(outagePeriod).doubleValue();
			    	} catch (ParseException pe) {
			    		throw new Exception("invalid value for " + field);
			    	}
					if ((!StringUtil.hasNoValue(crInReference)) &&
							(StringUtil.hasNoValue(crInInd))) {
						throw new Exception("CRQ/INC Ind must be entered if CRQ/INC Reference is present");
					}
					if (((!StringUtil.hasNoValue(crInStartDT)) &&
							(StringUtil.hasNoValue(crInEndDT))) || 
							((StringUtil.hasNoValue(crInStartDT)) &&
							(!StringUtil.hasNoValue(crInEndDT)))) {
						throw new Exception("CRQ/INC dates - enter both or neither");
					}
					if ((!StringUtil.hasNoValue(crInStartDT)) &&
							(!StringUtil.hasNoValue(crInEndDT))) {
						try {
							crInStartTS = Timestamp.valueOf(crInStartDT.substring(6, 10) + "-" +
									crInStartDT.substring(3, 5) + "-" +	
									crInStartDT.substring(0, 2) + " " + 
									crInStartDT.substring(11, 16) + ":00");
						} catch (Exception ex) {
							throw new Exception("Invalid value entered for CRQ/INC start date");
						}
						try {
							crInEndTS = Timestamp.valueOf(crInEndDT.substring(6, 10) + "-" +
									crInEndDT.substring(3, 5) + "-" +	
									crInEndDT.substring(0, 2) + " " + 
									crInEndDT.substring(11, 16) + ":59");
						} catch (Exception ex) {
							throw new Exception("Invalid value entered for CRQ/INC end date");
						}
						if (crInStartTS.after(crInEndTS)) {
							throw new Exception("CRQ/INC start date cannot be after CRQ/INC end date");
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
		        	req.removeAttribute("buttonPressed");
				    try {
						conn = DriverManager.getConnection(url);
						cstmt = conn.prepareCall("{call Update_SNR_CRM(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						cstmt.setLong(1, Long.parseLong(snrId));
						cstmt.setString(2, crInReference);
						cstmt.setString(3, crInInd);
						cstmt.setTimestamp(4, crInStartTS);
						cstmt.setTimestamp(5, crInEndTS);
						cstmt.setString(6, crInUsed);
						cstmt.setString(7, crqStatus);
						cstmt.setString(8, thisU.getNameForLastUpdatedBy());
						cstmt.setTimestamp(9, implOutageStartTS);
						cstmt.setTimestamp(10, implOutageEndTS);
						cstmt.setString(11, p1SiteInd);
						cstmt.setDouble(12, oP);
						cstmt.setString(13, tefOutageRequired);
						cstmt.setString(14, tefOutageNos);
						boolean found = cstmt.execute();
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								if (rs.getString(1).equalsIgnoreCase("Y")) {
									req.setAttribute("userMessage", "CRM detail for NR " + nrId +
											" updated");
								} else {
									throw new Exception("negative return code from Update_SNR_CRM()");
								}
							}
						}
				    } catch (Exception ex) {
			        	req.setAttribute("userMessage", "Error: unable to update SNR CRM detail, " + ex.getMessage());
				    } finally {
				    	try {
					    	cstmt.close();
					    	conn.close();
					    } catch (SQLException ex) {
				        	req.setAttribute("userMessage", "Error: updating SNR CRM detail, " + ex.getMessage());
					    }
				    }
			    } catch (Exception ex) {
		        	req.setAttribute("userMessage", "Error: " + ex.getMessage());
			    } finally {
			    	req.setAttribute("buttonPressed", "showCRMDetail");
			    }
			} else if ((buttonPressed.equals("addComA")) || (buttonPressed.equals("viewComA")) || 
					(buttonPressed.equals("viewSiteComA")) || (buttonPressed.equals("viewSiteComU")) || 
					(buttonPressed.equals("viewSiteConfA")) || (buttonPressed.startsWith("updateSiteConf"))) {
				assignAccessVariables(req);
				if ((buttonPressed.equals("viewSiteComA")) || (buttonPressed.equals("viewSiteComU"))) {
					req.setAttribute("commentarySite", site);
				} else if (buttonPressed.startsWith("updateSiteConf")) {
					String siteName = req.getParameter("siteName");
			    	req.setAttribute("siteName", siteName);
			    	req.setAttribute("buttonPressed", 
			    			buttonPressed.equals("updateSiteConfA")?"viewSiteConfA":"viewSiteConf");
				    try {
						conn = DriverManager.getConnection(url);
						cstmt = conn.prepareCall("{call UpdateSiteName(?,?,?)}");
						Long.parseLong(req.getParameter("customerId"));
						req.getParameter("site");
						req.getParameter("siteName");
						cstmt.setLong(1, Long.parseLong(req.getParameter("customerId")));
						cstmt.setString(2, req.getParameter("site"));
						cstmt.setString(3, siteName);
						boolean found = cstmt.execute();
						if (found) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								if (rs.getString(1).equalsIgnoreCase("Y")) {
									req.setAttribute("userMessage", "Site name updated");
								} else {
									throw new Exception("negative return code from UpdateSiteName()");
								}
							}
						}
				    } catch (Exception ex) {
			        	req.setAttribute("userMessage", "Error: unable to update site name, " + ex.getMessage());
				    } finally {
				    	try {
					    	cstmt.close();
					    	conn.close();
					    } catch (SQLException ex) {
				        	req.setAttribute("userMessage", "Error: updating site name, " + ex.getMessage());
					    }
				    }
				}
			} else if ((buttonPressed.equals("addComC")) || (buttonPressed.equals("viewComC"))) {
				assignCRMVariables(req);
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
			    	if (extraScreen !=null) {
			    		if (extraScreen.equals("snrAccessDetail")) {
			    			assignAccessVariables(req);
			    			req.setAttribute("buttonPressed", "viewComA");
				    	} else if (extraScreen.equals("snrCRMDetail")) {
							assignCRMVariables(req);
			    			req.setAttribute("buttonPressed", "viewComC");
				    	}
			    	} else {
			    		req.setAttribute("buttonPressed", "viewCom");
			    	}
			    } catch (SQLException ex) {
		        	req.setAttribute("userMessage", "Error: Unable to add commentary, " + ex.getMessage());
		        	req.removeAttribute("buttonPressed");
			    } finally {
			    	try {
				    	cstmt.close();
				    	conn.close();
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: adding commentary, " + ex.getMessage());
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
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
	}

	private void assignAccessVariables(HttpServletRequest req) {
    	req.setAttribute("siteName", req.getParameter("siteNameR"));
    	req.setAttribute("p1SiteInd", req.getParameter("selectP1SiteInd"));
    	req.setAttribute("obassInd", req.getParameter("selectOBASSInd"));
    	req.setAttribute("ramsInd", req.getParameter("selectRAMSInd"));
    	req.setAttribute("escortInd", req.getParameter("selectEscortInd"));
    	req.setAttribute("healthSafetyInd", req.getParameter("selectHealthSafetyInd"));
    	req.setAttribute("accessConfirmedInd", req.getParameter("selectAccessConfirmedInd"));
    	req.setAttribute("oohWeekendInd", req.getParameter("selectOOHWeekendInd"));
    	req.setAttribute("outagePeriod", req.getParameter("outagePeriod"));
    	req.setAttribute("accessCost", req.getParameter("accessCost"));
    	req.setAttribute("consumableCost", req.getParameter("consumableCost"));
		req.setAttribute("snrCommentaryTypeInit", "Access");
		req.setAttribute("oohWeekendInd", req.getParameter("selectOOHWeekendInd"));
		req.setAttribute("accessStatus", req.getParameter("selectAccessStatus"));
		req.setAttribute("permitType", req.getParameter("permitType"));
		req.setAttribute("tefOutageRequired", req.getParameter("selectTEFOutageRequired"));
		req.setAttribute("vfArrangeAccess", req.getParameter("selectVFArrangeAccess"));
		req.setAttribute("twoManSite", req.getParameter("selectTwoManSite"));
		req.setAttribute("siteAccessInfomation", req.getParameter("siteAccessInfomation"));
		req.setAttribute("crInReference", req.getParameter("crInReferenceR"));
    	req.setAttribute("tefOutageNos", req.getParameter("tefOutageNos"));
	}
	
	private void assignCRMVariables(HttpServletRequest req) {
		req.setAttribute("crInReference", req.getParameter("crInReference"));
		req.setAttribute("crInInd", req.getParameter("selectCRINInd"));
		req.setAttribute("crInStartDT", req.getParameter("crInStartDT"));
		req.setAttribute("crInEndDT", req.getParameter("crInEndDT"));
		req.setAttribute("crInOutageStartDT", req.getParameter("crInOutageStartDT"));
		req.setAttribute("crInOutageEndDT", req.getParameter("crInOutageEndDT"));
		req.setAttribute("crInUsed", req.getParameter("selectCRINUsed"));
		req.setAttribute("crqStatus", req.getParameter("selectCRQStatus"));
		req.setAttribute("snrCommentaryTypeInit", "CR/IN");
    	req.setAttribute("p1SiteInd", req.getParameter("selectCP1SiteInd"));
    	req.setAttribute("outagePeriod", req.getParameter("outagePeriodC"));
		req.setAttribute("tefOutageRequired", req.getParameter("selectCTEFOutageRequired"));
    	req.setAttribute("tefOutageNos", req.getParameter("tefOutageNosC"));
}

	private SNR getSNRDetail(long snrId, String url) {
		SNR snrDetail = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
    		cstmt = conn.prepareCall("{call GetSNRDetailInit(?)}");
		    cstmt.setLong(1, snrId);
		    boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					snrDetail = new SNR(rs.getLong(1),
						rs.getLong(2), rs.getString(3),
						rs.getString(4),rs.getString(5),rs.getString(6),
						rs.getString(7),rs.getString(8),rs.getDate(9),
						rs.getDate(10),rs.getDate(11),rs.getDate(12),
						rs.getString(13),rs.getString(14),rs.getString(15),
						rs.getString(16),rs.getString(17),rs.getDate(18),
						rs.getDouble(19),rs.getString(20),rs.getDouble(21),
						rs.getDouble(22),rs.getString(23),rs.getString(24),
						rs.getString(25),rs.getTimestamp(26),rs.getTimestamp(27),
						rs.getString(28),
						rs.getString(29),rs.getString(30),rs.getTimestamp(31),
						rs.getTimestamp(32),rs.getString(33),rs.getString(34),
						rs.getString(35),rs.getString(36),rs.getString(37),
						rs.getString(38),rs.getString(39),rs.getString(40),
						rs.getString(41),rs.getString(42),rs.getString(43),
						rs.getString(44),rs.getString(45),rs.getString(46),
						rs.getTimestamp(47),rs.getString(48),rs.getTimestamp(49),
						rs.getTimestamp(50),rs.getString(51),rs.getDate(52),
						rs.getString(53),rs.getString(54),rs.getString(55),
						rs.getString(56),rs.getString(57),rs.getString(58),
						rs.getString(59),null);
				}
			}
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return snrDetail;
	}

}
