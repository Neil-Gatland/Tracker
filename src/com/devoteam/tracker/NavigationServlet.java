package com.devoteam.tracker;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.SNRAccessDetail;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.model.UserRole;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.UtilBean;

public class NavigationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8423957186048261319L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession(false);
		String destination = "/logon.jsp";
		if (session == null) {
			session = req.getSession(true);
			session.setAttribute("userMessage", "");
		} else {
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String fromScreen = req.getParameter("fromScreen");
			session.setAttribute("currentProject", req.getParameter("currentProject"));
			if ((fromScreen != null) && (!fromScreen.isEmpty())) {
				session.setAttribute("fromScreen", fromScreen);
			}
			String fromScreenTitle = req.getParameter("screenTitle");
			if ((fromScreenTitle != null) && (!fromScreenTitle.isEmpty())) {
				session.setAttribute("fromScreenTitle", fromScreenTitle);
			}
			String toScreen = req.getParameter("toScreen");
			session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, toScreen);
			if ((toScreen == null) || (toScreen.equals(""))) {
	        	req.setAttribute("userMessage", "Please log in again");
			} else if (toScreen.equals(ServletConstants.LOG_OFF)) {
	        	req.setAttribute("userMessage", "You have now been logged off");
			} else if (toScreen.equals(ServletConstants.CHANGE_PASSWORD)) {
				req.setAttribute("userMessage", "Please enter old and new passwords.");
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.CHANGE_PASSWORD);
				destination = "/passwordChange.jsp";
			} else if (toScreen.equals(ServletConstants.CUSTOMER_MENU)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.CUSTOMER_MENU);
				destination = "/customerMenu.jsp";
			} else if (toScreen.equals(ServletConstants.HOME_FE)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.HOME_FE);
				destination = "/homeFE.jsp";
			} else if (toScreen.equals(ServletConstants.HOME)) {
				if (thisU.getUserType().equals(User.USER_TYPE_CUSTOMER)) {
					session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.CUSTOMER_MENU);
					destination = "/customerMenu.jsp";				
				/*} else if (thisU.getUserType().equals(User.USER_TYPE_DEVOTEAM)) {					
					if (thisU.hasUserRole(UserRole.ROLE_B_O_ENGINEER))  {
						session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.HOME_BO);
						destination = "/homeBO.jsp";
					} else {
						session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.WORK_QUEUES);
						destination = "/workQueues.jsp";
					}	*/	
				} else if ((thisU.getUserType().equals(User.USER_TYPE_THIRD_PARTY))&&
						(thisU.hasUserRole(UserRole.ROLE_FIELD_ENGINEER))) {
					session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.HOME_FE);
					destination = "/homeFE.jsp";					
				} else {
					session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.WORK_QUEUES);
					destination = "/workQueues.jsp";
				}
			} else if (toScreen.equals(ServletConstants.HOME_BO)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.HOME_BO);
				destination = "/homeBO.jsp";
			} else if (toScreen.equals(ServletConstants.HOME_FE)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.HOME_FE);
				destination = "/homeFE.jsp";
			} else if (toScreen.equals(ServletConstants.LIVE_DASHBOARD)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.LIVE_DASHBOARD);
				destination = "/liveDashboard.jsp";
				req.setAttribute("hideProject", req.getParameter("hideProject"));
			} else if (toScreen.equals(ServletConstants.SITE_SEARCH)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.SITE_SEARCH);
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
		    	UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
				String[] lastCompletedSiteDetails = uB.GetLastCompletedSite();
				req.setAttribute("reportSite", lastCompletedSiteDetails[0]);
				req.setAttribute("reportNrId", lastCompletedSiteDetails[1]);
				req.setAttribute("reportDate", lastCompletedSiteDetails[2]);
				req.setAttribute("reportType", lastCompletedSiteDetails[3]);
				destination = "/siteSearch.jsp";
			} else if (toScreen.equals(ServletConstants.CLIENT_REPORTING)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.CLIENT_REPORTING);
				destination = "/clientReporting.jsp";
			} else if (toScreen.equals(ServletConstants.SCHEDULE_VIEW)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.SCHEDULE_VIEW);
				session.setAttribute("potLoadActive", "N");
				destination = "/scheduleView.jsp";
			} else if (toScreen.equals(ServletConstants.MISSING_DATA)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.MISSING_DATA);
				destination = "/missingData.jsp";
			} else if (toScreen.equals(ServletConstants.EXPANDED)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.WORK_QUEUES);
				destination = "/workQueues.jsp";
			} else if (toScreen.equals(ServletConstants.BO)) {
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, ServletConstants.HOME_BO);
				req.setAttribute("snrId", req.getParameter("snrId"));
				req.setAttribute("snrStatus", req.getParameter("snrStatus"));
				destination = "/homeBO.jsp";
			} else {
				String snrId = req.getParameter("snrId")==null?"-1":req.getParameter("snrId");
				//session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, toScreen);
				if (toScreen.equals(ServletConstants.JOB_TYPE_MAINTENANCE)) {
					destination = "/jobTypeMaintenance.jsp";
				} else if (toScreen.equals(ServletConstants.PMO)) {
					destination = "/viewPMOList.jsp";
				} else if (toScreen.equals(ServletConstants.USER_ADMINISTRATION)) {
					destination = "/userAdministration.jsp";
				} else if (toScreen.equals(ServletConstants.SCHEDULING)) {
					destination = "/scheduling.jsp";
			    	session.removeAttribute(ServletConstants.POT_SPREADSHEET_NAME_IN_SESSION);
			    	session.removeAttribute(ServletConstants.POT_SPREADSHEET_COPY_NAME_IN_SESSION);
			    	session.removeAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION);
			    	session.removeAttribute(ServletConstants.SCHEDULE_SPREADSHEET_NAME_IN_SESSION);
			    	session.removeAttribute(ServletConstants.SCHEDULE_SPREADSHEET_COPY_NAME_IN_SESSION);
					session.removeAttribute(ServletConstants.SCHEDULED_SNRS_IN_SESSION);
					session.removeAttribute(ServletConstants.INVALID_SNRS_IN_SESSION);
					session.removeAttribute(ServletConstants.SCHEDULED_SNR_WARNINGS_IN_SESSION);
				} else if (toScreen.equals(ServletConstants.PRE_CHECK_MAINTENANCE)) {
					if (!snrId.equals("-1")) {
						/*String snrStatus = req.getParameter("snrStatus");
						if ((snrStatus.equalsIgnoreCase(ServletConstants.STATUS_SCHEDULED)) ||
								(snrStatus.equalsIgnoreCase( ServletConstants.STATUS_REQUESTED))) {*/
							req.setAttribute("filterNRId", req.getParameter("nrId"));
						//}
					}	
					destination = "/preCheckMaintenance.jsp";
				} else if (toScreen.equals(ServletConstants.UPDATE_ACCESS)) {
					//destination = "/updateAccess.jsp";
					req.setAttribute("listStatus1", ServletConstants.STATUS_SCHEDULED);
					destination = "/updateSNRList.jsp";
					if (!snrId.equals("-1")) {
						String snrStatus = req.getParameter("snrStatus");
						if ((snrStatus.equalsIgnoreCase(ServletConstants.STATUS_SCHEDULED))) {
							req.setAttribute("snrId", snrId);
							req.setAttribute("snrStatus", snrStatus);
							req.setAttribute("historyInd", req.getParameter("historyInd"));
							req.setAttribute("customerId", req.getParameter("customerId"));
							req.setAttribute("site", req.getParameter("site"));
							req.setAttribute("nrId", req.getParameter("nrId"));
							req.setAttribute("buttonPressed", "showAccessDetail");
							req.setAttribute("listStatus2", "All");
							destination = "/updateSNRList";
						}
					}
				} else if (toScreen.equals(ServletConstants.UPDATE_CRM)) {
					//destination = "/updateCRM.jsp";
					req.setAttribute("listStatus1", ServletConstants.STATUS_SCHEDULED);
					destination = "/updateSNRList.jsp";
					if (!snrId.equals("-1")) {
						String snrStatus = req.getParameter("snrStatus");
						if ((snrStatus.equalsIgnoreCase(ServletConstants.STATUS_SCHEDULED))) {
							req.setAttribute("snrId", snrId);
							req.setAttribute("snrStatus", snrStatus);
							req.setAttribute("historyInd", req.getParameter("historyInd"));
							req.setAttribute("customerId", req.getParameter("customerId"));
							req.setAttribute("site", req.getParameter("site"));
							req.setAttribute("nrId", req.getParameter("nrId"));
							req.setAttribute("buttonPressed", "showCRMDetail");
							req.setAttribute("listStatus2", "All");
							destination = "/updateSNRList";
						}
					}
				} else if (toScreen.equals(ServletConstants.CONFIRM_IMPLEMENTATION)) {
					//destination = "/confirmImpl.jsp";
					req.setAttribute("listStatus1", ServletConstants.STATUS_SCHEDULED);
					req.setAttribute("listStatus2", ServletConstants.STATUS_COMPLETED);
					destination = "/multiSNRList.jsp";
					if (!snrId.equals("-1")) {
						String snrStatus = req.getParameter("snrStatus");
						req.setAttribute("filterNRId", req.getParameter("nrId"));
						if ((snrStatus.equalsIgnoreCase(ServletConstants.STATUS_SCHEDULED)) ||
								(snrStatus.equalsIgnoreCase( ServletConstants.STATUS_COMPLETED))) {
							String prevScreen = req.getParameter("prevScreen");
							req.setAttribute("prevScreen", prevScreen);
							req.setAttribute("snrId", snrId);
							req.setAttribute("snrStatus", snrStatus);
							req.setAttribute("historyInd", req.getParameter("historyInd"));
							req.setAttribute("customerId", req.getParameter("customerId"));
							req.setAttribute("site", req.getParameter("site"));
							req.setAttribute("nrId", req.getParameter("nrId"));
							req.setAttribute("nextPreCheck", "");
							req.setAttribute("reallocType", "");
							req.setAttribute("buttonPressed", "showImplementationDetail");
							destination = "/multiSNRList";
						}
					}
				} else if (toScreen.equals(ServletConstants.LOAD_SITE_CONFIGURATION)) {
					destination = "/loadSiteConfig.jsp";
				} else if (toScreen.equals(ServletConstants.RESCHED_REALLOC_CANCEL_SNR)) {
					//destination = "/amendSNR.jsp";
					req.setAttribute("listStatus1", ServletConstants.STATUS_SCHEDULED);
					req.setAttribute("listStatus2", ServletConstants.STATUS_AWAITING_SCHEDULING);
					destination = "/multiSNRList.jsp";
					if (!snrId.equals("-1")) {
						String snrStatus = req.getParameter("snrStatus");
						if ((snrStatus.equalsIgnoreCase(ServletConstants.STATUS_SCHEDULED)) ||
								(snrStatus.equalsIgnoreCase( ServletConstants.STATUS_AWAITING_SCHEDULING))) {
							req.setAttribute("filterNRId", req.getParameter("nrId"));
						}
					}
				} else if (toScreen.equals(ServletConstants.VIEW_SNR_HISTORY)) {
					if (!snrId.equals("-1")) {
						req.setAttribute("filterNRId", req.getParameter("nrId"));
					}
					destination = "/viewSNRHistory.jsp";
				} else if (toScreen.equals(ServletConstants.VIEW_COMMENTARY_DETAIL)) {					
					destination = "/viewCommentaryDetail.jsp";
					req.setAttribute("snrId", req.getParameter("snrId"));
					req.setAttribute("site", req.getParameter("site"));
					req.setAttribute("nrId", req.getParameter("nrId"));
					req.setAttribute("snrStatus", req.getParameter("snrStatus"));
				} else if (toScreen.equals(ServletConstants.VIEW_ACCESS_DETAIL)) {
					destination = "/viewAccessDetail.jsp";
					req.setAttribute("snrId", req.getParameter("snrId"));
					req.setAttribute("site", req.getParameter("site"));
					req.setAttribute("nrId", req.getParameter("nrId"));
					req.setAttribute("snrStatus", req.getParameter("snrStatus"));
					if (!snrId.equals("-1")) {
						String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				    	UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
						SNRAccessDetail sAD = uB.getSNRAccessDetail(Long.parseLong(snrId));			
				    	req.setAttribute("p1SiteInd", sAD.getP1SiteInd());		
				    	req.setAttribute("ramsInd", sAD.getRAMSInd());		
				    	req.setAttribute("healthSafetyInd", sAD.getHealthSafetyInd());		
				    	req.setAttribute("accessConfirmedInd", sAD.getAccessConfirmedInd());		
				    	req.setAttribute("obassInd", sAD.getOBASSInd());		
				    	req.setAttribute("escortInd", sAD.getEscortInd());		
				    	req.setAttribute("tefOutageRequired", sAD.getTEFOutageRequired());		
				    	req.setAttribute("vfArrangeAccess", sAD.getVFArrangeAccess());		
				    	req.setAttribute("twoManSite", sAD.getTwoManSite());		
				    	req.setAttribute("oohWeekendInd", sAD.getOOHWeekendInd());		
				    	req.setAttribute("crINReference", sAD.getCRINReference());	
				    	req.setAttribute("tefOutageNos", sAD.getTEFOutageNos());
				    	req.setAttribute("outagePeriod", sAD.getOutagePeriodString());
				    	req.setAttribute("consumableCost", sAD.getConsumableCostString());
				    	req.setAttribute("accessCost", sAD.getAccessCostString());
				    	req.setAttribute("siteName", sAD.getSiteName());	
				    	req.setAttribute("siteAcessInformation", sAD.getSiteAccessInformation());
				    	req.setAttribute("permitType", sAD.getPermitType());	
				    	req.setAttribute("accessStatus", sAD.getAccessStatus());
					}
				} else if (toScreen.equals(ServletConstants.SITE_PROGRESS)) {
					destination = "/siteProgress.jsp";
					req.setAttribute("snrId", req.getParameter("snrId"));
					req.setAttribute("site", req.getParameter("site"));
					req.setAttribute("nrId", req.getParameter("nrId"));
					req.setAttribute("returnScreen", req.getParameter("fromScreen"));
				} else if (toScreen.equals(ServletConstants.REPORTING)) {
					destination = "/reporting.jsp";
				} else if (toScreen.equals(ServletConstants.REOPEN_CANCELLED_SNR)) {
					destination = "/cancelledSNRList.jsp";
				} else if (toScreen.equals(ServletConstants.OUTPUT_SCHEDULE)) {
					destination = "/downloadSchedule";
				} else {
					session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, fromScreenTitle);
					req.setAttribute("userMessage", "Not yet available");
					destination = "/" + fromScreen;
				}
			}
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
		dispatcher.forward(req,resp);
	}
}

