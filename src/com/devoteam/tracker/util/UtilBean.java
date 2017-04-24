package com.devoteam.tracker.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.LiveDashboardSite;
import com.devoteam.tracker.model.ScheduleList;
import com.devoteam.tracker.model.DashboardProject;
import com.devoteam.tracker.model.EmailCopy;
import com.devoteam.tracker.model.JobType;
import com.devoteam.tracker.model.PMOAbort;
import com.devoteam.tracker.model.PreCheckListItem;
import com.devoteam.tracker.model.PreCheckOutstanding;
import com.devoteam.tracker.model.ReportParameter;
import com.devoteam.tracker.model.SNR;
import com.devoteam.tracker.model.SNRAccessDetail;
import com.devoteam.tracker.model.SNRBOInformation;
import com.devoteam.tracker.model.SNRCommentary;
import com.devoteam.tracker.model.SNRListItem;
import com.devoteam.tracker.model.SNRProgressReportItem;
import com.devoteam.tracker.model.SNRTechnology;
import com.devoteam.tracker.model.SNRTotalsReportItem;
import com.devoteam.tracker.model.SNRUserRole;
import com.devoteam.tracker.model.SiteConfiguration;
import com.devoteam.tracker.model.SiteProgress;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.model.UserAdminListItem;
import com.devoteam.tracker.model.UserJobType;
import com.devoteam.tracker.model.UserRole;

public class UtilBean {
	
	private User user;
	private String screen;
	private String url;
	private String message;
	private String prevDate;
	private String prevYearWeek;
	
	private int imageChoice;
	
	public UtilBean() {}

	public UtilBean(User user, String screen) {
		this.user = user;
		this.screen = screen;
	}
	
	public UtilBean(User user, String screen, String url) {
		this.user = user;
		this.screen = screen;
		this.url = url;
	}
	
	public String getMenu1() {
		// Standard menu items
		HTMLElement m0 = new HTMLElement("div");
		HTMLElement m0a = new HTMLElement("div", "float:left;width:2px","menu1", "&nbsp;");
		HTMLElement m0b = new HTMLElement("div", "float:right;width:2px", "menu1", 
			"&nbsp;");
		HTMLElement m0c = new HTMLElement("div", "overflow:hidden", "menu1", 
			"&nbsp;");
		HTMLElement m01 = new HTMLElement("div", "m01", "float:left;", 
			"menu1Item", "menuClick('" + ServletConstants.HOME + "')", 
			"invertClass('m01')", "invertClass('m01')", ServletConstants.HOME);
		// Determine if BO Home or FE home required (but not for change password screen)
		if (!userExpired()) {
			if (user.hasUserRole(UserRole.ROLE_B_O_ENGINEER)) {
				m01 = new HTMLElement("div", "m01", "float:left;",   
						"menu1Item", "menuClick('" + ServletConstants.HOME_BO + "')", 
						"invertClass('m01')", "invertClass('m01')", ServletConstants.HOME_BO);
			}
			if (user.hasUserRole(UserRole.ROLE_FIELD_ENGINEER)) {
				m01 = new HTMLElement("div", "m01", "float:left;",   
						"menu1Item", "menuClick('" + ServletConstants.HOME_FE + "')", 
						"invertClass('m01')", "invertClass('m01')", ServletConstants.HOME_FE);
			}
		}
		// All users have change password and log off buttons
		HTMLElement m02 = new HTMLElement("div", "m02", "float:right;", 
			"menu1Item", "menuClick('" + ServletConstants.CHANGE_PASSWORD + "')",
			"invertClass('m02')", "invertClass('m02')", 
			ServletConstants.CHANGE_PASSWORD);
		HTMLElement m03 = new HTMLElement("div", "m03", "float:right;", 
			"menu1Item", "menuClick('" + ServletConstants.LOG_OFF + "')", 
			"invertClass('m03')", "invertClass('m03')", ServletConstants.LOG_OFF);
		// Defone optional buttons
		HTMLElement m04 = new HTMLElement("div", "m04", "float:right;", 
			"menu1Item", "menuClick('" + ServletConstants.LIVE_DASHBOARD + "')", 
			"invertClass('m04')", "invertClass('m04')", ServletConstants.LIVE_DASHBOARD);
		HTMLElement m05 = new HTMLElement("div", "m05", "float:right;", 
			"menu1Item", "menuClick('" + ServletConstants.SITE_SEARCH + "')", 
			"invertClass('m05')", "invertClass('m05')", ServletConstants.SITE_SEARCH);
		HTMLElement m06 = new HTMLElement("div", "m06", "float:right;", 
			"menu1Item", "menuClick('" + ServletConstants.CLIENT_REPORTING + "')", 
			"invertClass('m06')", "invertClass('m06')", ServletConstants.CLIENT_REPORTING);
		HTMLElement m07 = new HTMLElement("div", "m07", "float:right;", 
			"menu1Item", "menuClick('" + ServletConstants.SCHEDULE_VIEW + "')", 
			"invertClass('m07')", "invertClass('m07')", ServletConstants.SCHEDULE_VIEW);
		// combine buttons
		m0.setValue(
				m0a.toString() + 
				(userExpired()?"":m01.toString()) + 
				m0b.toString() + 
				m03.toString() +  
				(screen.equals(ServletConstants.CHANGE_PASSWORD)?"":m02.toString()) + 
				((screen.equals(ServletConstants.CHANGE_PASSWORD))||
				 (user.hasUserRole(UserRole.ROLE_FIELD_ENGINEER))||
				 (screen.equals(ServletConstants.LIVE_DASHBOARD))
						?"":m04.toString()) +
				((screen.equals(ServletConstants.CHANGE_PASSWORD))||
				 (user.hasUserRole(UserRole.ROLE_FIELD_ENGINEER))||
				 (screen.equals(ServletConstants.SITE_SEARCH))
						?"":m05.toString()) +
				((screen.equals(ServletConstants.CHANGE_PASSWORD))||
				 (user.hasUserRole(UserRole.ROLE_FIELD_ENGINEER))||
				 (screen.equals(ServletConstants.CLIENT_REPORTING))
						?"":m06.toString()) +
				((screen.equals(ServletConstants.CHANGE_PASSWORD))||
				 (user.hasUserRole(UserRole.ROLE_FIELD_ENGINEER))||
				 (screen.equals(ServletConstants.SCHEDULE_VIEW))
						?"":m07.toString()) +
				m0c.toString());
		return m0.toString();
	}

	public String getMenu2() {
		HTMLElement m1 = new HTMLElement("div");
		HTMLElement m1a = new HTMLElement("div", "float:left;width:2px", "menu2", "&nbsp;");
		HTMLElement m1b = new HTMLElement("div", "float:right;width:2px", "menu2", "&nbsp;");
		HTMLElement m1c = new HTMLElement("div", "overflow:hidden", "menu2", "&nbsp;");
		ArrayList<HTMLElement> m1l = new ArrayList<HTMLElement>();
		if (screen.equals(ServletConstants.CUSTOMER_MENU)) {
			m1l.add(new HTMLElement("div", "m11", "float:left;", "menu2Item", "menuClick('" +
				ServletConstants.VIEW_SNR_HISTORY + "')", "invertClass('m11')", 
				"invertClass('m11')", /*ServletConstants.VIEW_SNR_HISTORY_SHORT,*/ 
				ServletConstants.VIEW_SNR_HISTORY));
			m1l.add(new HTMLElement("div", "m12", "float:left;", "menu2Item", "menuClick('" +
				ServletConstants.REPORTING + "')", "invertClass('m12')", 
				"invertClass('m12')", ServletConstants.REPORTING));
		} else if (screen.equals(ServletConstants.WORK_QUEUES)) {
			int i = 0;
			boolean confirmImpl = false;
			boolean viewSNR = false;
			boolean reporting = false;
			if (user.hasUserRole(UserRole.ROLE_B_O_ENGINEER)) {
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.BO + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.BO_SHORT, 
						ServletConstants.BO));
			}
			if (user.hasUserRole(UserRole.ROLE_SCHEDULER)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.RESCHED_REALLOC_CANCEL_SNR + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.RESCHED_REALLOC_CANCEL_SNR_SHORT, 
						ServletConstants.RESCHED_REALLOC_CANCEL_SNR));
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.REOPEN_CANCELLED_SNR + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.REOPEN_CANCELLED_SNR_SHORT,
						ServletConstants.REOPEN_CANCELLED_SNR));
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.VIEW_SNR_HISTORY + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", /*ServletConstants.VIEW_SNR_HISTORY_SHORT,*/ 
						ServletConstants.VIEW_SNR_HISTORY));
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.REPORTING + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.REPORTING));
				viewSNR = true;
				reporting = true;
			}
			if (user.hasUserRole(UserRole.ROLE_B_O_ENGINEER)) {				
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.PRE_CHECK_MAINTENANCE + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.PRE_CHECK_MAINTENANCE_SHORT, 
						ServletConstants.PRE_CHECK_MAINTENANCE));
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.CONFIRM_IMPLEMENTATION + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.CONFIRM_IMPLEMENTATION_SHORT, 
						ServletConstants.CONFIRM_IMPLEMENTATION));
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.LOAD_SITE_CONFIGURATION + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.LOAD_SITE_CONFIGURATION_SHORT, 
						ServletConstants.LOAD_SITE_CONFIGURATION));
				if (!viewSNR) {
					i++;
					m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
							ServletConstants.VIEW_SNR_HISTORY + "')", "invertClass('m1"+i+"')", 
							"invertClass('m1"+i+"')", /*ServletConstants.VIEW_SNR_HISTORY_SHORT,*/ 
							ServletConstants.VIEW_SNR_HISTORY));
				}	
				if (!reporting) {
					i++;
					m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
							ServletConstants.REPORTING + "')", "invertClass('m1"+i+"')", 
							"invertClass('m1"+i+"')", ServletConstants.REPORTING));
				}	
				confirmImpl = true;
			}
			if (user.hasUserRole(UserRole.ROLE_ADMINISTRATOR)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.USER_ADMINISTRATION + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.USER_ADMINISTRATION_SHORT,
						ServletConstants.USER_ADMINISTRATION));
			}
			if (user.hasUserRole(UserRole.ROLE_FINANCE_ADMIN)||(user.hasUserRole(UserRole.ROLE_PMO))) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.JOB_TYPE_MAINTENANCE + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.JOB_TYPE_MAINTENANCE_SHORT, 
						ServletConstants.JOB_TYPE_MAINTENANCE));
			}
			if (user.hasUserRole(UserRole.ROLE_ACCESS_ADMIN)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.UPDATE_ACCESS + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.UPDATE_ACCESS));
			}
			if (user.hasUserRole(UserRole.ROLE_CRM_ADMIN)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.UPDATE_CRM + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.UPDATE_CRM));
			}
			if ((user.hasUserRole(UserRole.ROLE_FIELD_ENGINEER)) && (!confirmImpl)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.CONFIRM_IMPLEMENTATION + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.CONFIRM_IMPLEMENTATION_SHORT, 
						ServletConstants.CONFIRM_IMPLEMENTATION));
			}
			if (user.hasUserRole(UserRole.ROLE_PMO)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.PMO + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.PMO));
			}
		}  else if (screen.equals(ServletConstants.SCHEDULING)) {
			m1l.add(new HTMLElement("div", "m11", "float:left;", "menu2Item", "menuClickSpec('uploadPot')", "invertClass('m11')", 
					"invertClass('m11')", ServletConstants.LOAD_POT));
			m1l.add(new HTMLElement("div", "m13", "float:left;", "menu2Item", "menuClickSpec('uploadSchedule')", "invertClass('m13')", 
					"invertClass('m13')", ServletConstants.SCHEDULE_SNR));
			m1l.add(new HTMLElement("div", "m14", "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.RESCHED_REALLOC_CANCEL_SNR + "')", "invertClass('m14')", 
					"invertClass('m14')", /*ServletConstants.RESCHED_REALLOC_CANCEL_SNR_SHORT,*/ 
					ServletConstants.RESCHED_REALLOC_CANCEL_SNR));			
		}  else if (screen.equals(ServletConstants.VIEW_SNR_HISTORY)) {
			m1l.add(new HTMLElement("div", "m11", "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.REPORTING + "')", "invertClass('m11')", 
					"invertClass('m11')", ServletConstants.REPORTING));			
		}  else if (screen.equals(ServletConstants.REPORTING)) {
			m1l.add(new HTMLElement("div", "m11", "float:left;", "menu2Item", 
					"menuClickSpec('dfReport')", "invertClass('m11')", 
					"invertClass('m11')", ServletConstants.CHOOSE_REPORT));
			m1l.add(new HTMLElement("div", "m12", "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.VIEW_SNR_HISTORY + "')", "invertClass('m12')", 
					"invertClass('m12')", /*ServletConstants.VIEW_SNR_HISTORY_SHORT,*/ 
					ServletConstants.VIEW_SNR_HISTORY));		
		}  else if (screen.equals(ServletConstants.HOME_BO)) {
			int i = 2;
			boolean confirmImpl = false;
			boolean viewSNR = false;
			boolean reporting = false;
			boolean outputSchedule = false;
			m1l.add(new HTMLElement("div", "m11", "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.EXPANDED + "')", "invertClass('m11')", 
					"invertClass('m11')", ServletConstants.EXPANDED_SHORT,
					ServletConstants.EXPANDED));
			if (user.hasUserRole(UserRole.ROLE_SCHEDULER)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.RESCHED_REALLOC_CANCEL_SNR + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.RESCHED_REALLOC_CANCEL_SNR_SHORT, 
						ServletConstants.RESCHED_REALLOC_CANCEL_SNR));
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.REOPEN_CANCELLED_SNR + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.REOPEN_CANCELLED_SNR_SHORT,
						ServletConstants.REOPEN_CANCELLED_SNR));				
				viewSNR = true;
				reporting = true;
			}	
			i++;
			m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.PRE_CHECK_MAINTENANCE + "')", "invertClass('m1"+i+"')", 
					"invertClass('m1"+i+"')", ServletConstants.PRE_CHECK_MAINTENANCE_SHORT, 
					ServletConstants.PRE_CHECK_MAINTENANCE));
			i++;
			m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.VIEW_SNR_HISTORY + "')", "invertClass('m1"+i+"')", 
					"invertClass('m1"+i+"')", /*ServletConstants.VIEW_SNR_HISTORY_SHORT,*/ 
					ServletConstants.VIEW_SNR_HISTORY));
			i++;
			m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.REPORTING + "')", "invertClass('m1"+i+"')", 
					"invertClass('m1"+i+"')", ServletConstants.REPORTING));
			i++;
			m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.LOAD_SITE_CONFIGURATION + "')", "invertClass('m1"+i+"')", 
					"invertClass('m1"+i+"')", ServletConstants.LOAD_SITE_CONFIGURATION_SHORT, 
					ServletConstants.LOAD_SITE_CONFIGURATION));
			if (user.hasUserRole(UserRole.ROLE_PMO)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.PMO + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.PMO));
			}
			if (user.hasUserRole(UserRole.ROLE_FINANCE_ADMIN)||(user.hasUserRole(UserRole.ROLE_PMO))) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.JOB_TYPE_MAINTENANCE + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.JOB_TYPE_MAINTENANCE_SHORT, 
						ServletConstants.JOB_TYPE_MAINTENANCE));
			}
			if (user.hasUserRole(UserRole.ROLE_ADMINISTRATOR)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.USER_ADMINISTRATION + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.USER_ADMINISTRATION_SHORT,
						ServletConstants.USER_ADMINISTRATION));
			}
		}
		StringBuilder mSB = new StringBuilder(m1a.toString());
		for (Iterator<HTMLElement> it = m1l.iterator(); it.hasNext(); ) {
			mSB.append(it.next().toString());
		}
		mSB.append(m1b.toString());
		mSB.append(m1c.toString());
		m1.setValue(mSB.toString());
		return m1.toString();
	}
	
	public boolean canSee() {
		boolean canSee = false;
		if (screen.equals(ServletConstants.CHANGE_PASSWORD)) {
			canSee = true;
		} else {
			if (user.getUserType().equals(User.USER_TYPE_CUSTOMER)) {
				/*canSee = screen.equals(ServletConstants.CUSTOMER_MENU) ||
					screen.equals(ServletConstants.VIEW_SNR_HISTORY) ||
					screen.equals(ServletConstants.REPORTING);*/
				canSee = screen.equals(ServletConstants.CUSTOMER_MENU) ||
					screen.equals(ServletConstants.LIVE_DASHBOARD) ||
					screen.equals(ServletConstants.SITE_SEARCH) ||
					screen.equals(ServletConstants.CLIENT_REPORTING)||
					screen.equals(ServletConstants.SCHEDULE_VIEW);
			} else if (user.getUserType().equals(User.USER_TYPE_THIRD_PARTY)) {
				if (screen.equals(ServletConstants.HOME_FE)) {
					canSee = user.hasUserRole(UserRole.ROLE_FIELD_ENGINEER);
				}
			} else /*if ((user.getUserType().equals(User.USER_TYPE_DEVOTEAM))*/ {
				if (screen.equals(ServletConstants.WORK_QUEUES)) {
					canSee = true;
				} else if (screen.equals(ServletConstants.SCHEDULE_VIEW)) {
					canSee = true;
				} else if (screen.equals(ServletConstants.MISSING_DATA)) {
					canSee = user.hasUserRole(UserRole.ROLE_SCHEDULER);;
				} else if (screen.equals(ServletConstants.JOB_TYPE_MAINTENANCE)) {
					canSee = user.hasUserRole(UserRole.ROLE_FINANCE_ADMIN)||user.hasUserRole(UserRole.ROLE_PMO);
				} else if (screen.equals(ServletConstants.USER_ADMINISTRATION)) {
					canSee = user.hasUserRole(UserRole.ROLE_ADMINISTRATOR);
				} else if (screen.equals(ServletConstants.SCHEDULING)) {
					canSee = user.hasUserRole(UserRole.ROLE_SCHEDULER);
				} else if (screen.equals(ServletConstants.PRE_CHECK_MAINTENANCE)) {
					canSee = user.hasUserRole(UserRole.ROLE_B_O_ENGINEER);
				} else if (screen.equals(ServletConstants.UPDATE_ACCESS)) {
					canSee = user.hasUserRole(UserRole.ROLE_ACCESS_ADMIN);
				} else if (screen.equals(ServletConstants.VIEW_ACCESS_DETAIL)) {
					canSee = user.hasUserRole(UserRole.ROLE_B_O_ENGINEER);
				} else if (screen.equals(ServletConstants.SITE_PROGRESS)) {
					canSee = user.hasUserRole(UserRole.ROLE_B_O_ENGINEER);
				} else if (screen.equals(ServletConstants.VIEW_COMMENTARY_DETAIL)) {
					canSee = user.hasUserRole(UserRole.ROLE_B_O_ENGINEER);
				} else if (screen.equals(ServletConstants.HOME_BO)) {
					canSee = user.hasUserRole(UserRole.ROLE_B_O_ENGINEER);
				} else if (screen.equals(ServletConstants.EXPANDED)) {
					canSee = user.hasUserRole(UserRole.ROLE_B_O_ENGINEER);
				} else if (screen.equals(ServletConstants.UPDATE_CRM)) {
					canSee = user.hasUserRole(UserRole.ROLE_CRM_ADMIN);
				} else if (screen.equals(ServletConstants.CONFIRM_IMPLEMENTATION)) {
					canSee = user.hasUserRole(UserRole.ROLE_B_O_ENGINEER) ||
						user.hasUserRole(UserRole.ROLE_FIELD_ENGINEER);
				} else if (screen.equals(ServletConstants.LOAD_SITE_CONFIGURATION)) {
					canSee = user.hasUserRole(UserRole.ROLE_B_O_ENGINEER);
				} else if (screen.equals(ServletConstants.RESCHED_REALLOC_CANCEL_SNR)) {
					canSee = user.hasUserRole(UserRole.ROLE_SCHEDULER);
				} else if (screen.equals(ServletConstants.REOPEN_CANCELLED_SNR)) {
					canSee = user.hasUserRole(UserRole.ROLE_SCHEDULER);
				} else if (screen.equals(ServletConstants.VIEW_SNR_HISTORY)) {
					canSee = user.hasUserRole(UserRole.ROLE_SCHEDULER) ||
						user.hasUserRole(UserRole.ROLE_B_O_ENGINEER);
				} else if (screen.equals(ServletConstants.REPORTING)) {
					canSee = user.hasUserRole(UserRole.ROLE_SCHEDULER) ||
						user.hasUserRole(UserRole.ROLE_B_O_ENGINEER);
				} else if (screen.equals(ServletConstants.PMO)) {
					canSee = user.hasUserRole(UserRole.ROLE_PMO);
				} else if (screen.equals(ServletConstants.LIVE_DASHBOARD)) {
					canSee = true;
				} else if (screen.equals(ServletConstants.CLIENT_REPORTING)) {
					canSee = true;
				} else if (screen.equals(ServletConstants.SITE_SEARCH)) {
					canSee = true;
				}				
			}
		}
		return canSee;
	}
	
	public Collection<SNRProgressReportItem> getSNRProgressReportList(String filterCustomer, 
			String filterPotName, String filterSite, String filterStatus) {
		ArrayList<SNRProgressReportItem> prList = new ArrayList<SNRProgressReportItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSNRProgressReport(?,?,?,?)}");
	    	cstmt.setString(1, filterCustomer);
	    	cstmt.setString(2, filterPotName);
	    	cstmt.setString(3, filterSite);
	    	cstmt.setString(4, filterStatus);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					prList.add(new SNRProgressReportItem(rs.getLong(1),
						rs.getString(2), rs.getLong(3), rs.getString(4),
						rs.getString(5), rs.getString(6),rs.getString(7),
						rs.getDate(8),rs.getString(9),rs.getTimestamp(10),
						rs.getString(11), rs.getInt(12)));
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
	    
	    return prList;
	}
	
	public String getSNRProgressReportHTML(HttpSession session, String filterCustomer, 
			String filterPotName, String filterSite, String filterStatus) {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		Collection<SNRProgressReportItem> prList = getSNRProgressReportList(filterCustomer, 
				filterPotName, filterSite, filterStatus);
		session.setAttribute(ServletConstants.SNR_PROGRESS_REPORT_ITEMS_IN_SESSION, prList);
		for (Iterator<SNRProgressReportItem> it = prList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			oddRow = !oddRow;
			SNRProgressReportItem spri = it.next();
			String[] values = spri.getValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);
				tr.appendValue(td.toString());
			}
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public Collection<SNRTotalsReportItem> getSNRTotalsReportList() {
		ArrayList<SNRTotalsReportItem> trList = new ArrayList<SNRTotalsReportItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetNRTotalsReport(?)}");
	    	cstmt.setString(1, user.getUserType().equalsIgnoreCase(User.USER_TYPE_CUSTOMER)
	    			?user.getCustomerNames()[0]:"All");
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					trList.add(new SNRTotalsReportItem(rs.getString(1),
						rs.getLong(2), rs.getLong(3), rs.getLong(4),
						rs.getLong(5), rs.getLong(6),rs.getLong(7),
						rs.getLong(8),rs.getLong(9),rs.getLong(10)));
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
	    
	    return trList;
	}
	
	public String getSNRTotalsReportHTML(HttpSession session) {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		Collection<SNRTotalsReportItem> trList = getSNRTotalsReportList();
		session.setAttribute(ServletConstants.SNR_TOTALS_REPORT_ITEMS_IN_SESSION, trList);
		for (Iterator<SNRTotalsReportItem> it = trList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			oddRow = !oddRow;
			SNRTotalsReportItem stri = it.next();
			String[] values = stri.getValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);
				tr.appendValue(td.toString());
			}
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", "");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}

	public Collection<SNRListItem> getSNRHistoryList(String filterSNRId,
		String filterCustomer, String filterPotId, String filterPotName, 
		String filterSite, String filterNRId, String filterStatus, 
		String filterImplementationStatus,
		String filterAbortType, String filterHistoryInd) {
		ArrayList<SNRListItem> snrList = new ArrayList<SNRListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSNRHistoryList(?,?,?,?,?,?,?,?,?,?)}");
	    	cstmt.setLong(1, filterSNRId.equals("")?-1:Long.parseLong(filterSNRId));
	    	cstmt.setString(2, filterCustomer);
	    	cstmt.setLong(3, filterPotId.equals("All")?-1:Long.parseLong(filterPotId));
	    	cstmt.setString(4, filterPotName);
	    	cstmt.setString(5, filterSite);
	    	cstmt.setString(6, filterNRId);
	    	cstmt.setString(7, filterStatus);
	    	cstmt.setString(8, filterImplementationStatus);
	    	cstmt.setString(9, filterAbortType);
	    	cstmt.setString(10, filterHistoryInd);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrList.add(new SNRListItem(rs.getLong(1),
						rs.getString(2), rs.getLong(3), rs.getString(4),
						rs.getString(5), rs.getString(6),rs.getString(7),
						rs.getString(8),rs.getString(9),
						rs.getString(10), rs.getInt(11), rs.getDate(12), 
						rs.getTimestamp(13)));
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
	    
	    return snrList;
	}
	
	public String getSNRHistoryListHTML(String filterSNRId,
			String filterCustomer, String filterPotId, String filterPotName, 
			String filterSite, String filterNRId, String filterStatus, 
			String filterImplementationStatus,
			String filterAbortType, String filterHistoryInd,
			long selectedSNRId, boolean selectedHistoryInd, 
			long selectedHistoryDT) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRListItem> snrList = getSNRHistoryList(filterSNRId,
			filterCustomer, filterPotId, filterPotName, filterSite, 
			filterNRId, filterStatus, filterImplementationStatus,
			filterAbortType, filterHistoryInd);
		for (Iterator<SNRListItem> it = snrList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			row++;
			oddRow = !oddRow;
			SNRListItem sli = it.next();
			String[] values = sli.getHistoryValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);
				if ((i == 10) && (!values[i].equals(""))) {
					td.setAttribute("title", sli.getAbortType());
				} else if (values[i].equals(sli.DUMMYNR)) {
					td.setAttribute("title", sli.getNRId());
				}

				tr.appendValue(td.toString());
			}
			HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
				"radio", Long.toString(sli.getSNRId()), 
				"snrSelect('"+sli.getSNRId()+"','"+sli.getHistoryInd()+"','"+
				sli.getHistoryDTMs()+"','"+sli.getSite()+"','"+sli.getNRId()+"')");
			if ((sli.getSNRId() == selectedSNRId) && (sli.isHistory() == selectedHistoryInd) &&
					((sli.isHistory() && (sli.getHistoryDTMs() == selectedHistoryDT)) || !sli.isHistory())) {
				input.setChecked(true);
			}
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
				input.toString());
			if (sli.getHistoryInd().equalsIgnoreCase("Y")) {
				td.setAttribute("title", "History Date: " + sli.getHistoryDTString());
			}
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}

	public Collection<SNRListItem> getSNRSummaryList(String status, String status2,
			String filterSite, String filterNRId, String filterBOEngineer, String filterStatus, 
			String filterScheduledStart, String filterScheduledEnd) {
		message = null;
		ArrayList<SNRListItem> snrList = new ArrayList<SNRListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
		Timestamp scheduledStartTS = null;
		Timestamp scheduledEndTS = null;
	    try {
			if (!StringUtil.hasNoValue(filterScheduledStart)) {
				try {
					scheduledStartTS = Timestamp.valueOf(filterScheduledStart.substring(6, 10) + "-" +
							filterScheduledStart.substring(3, 5) + "-" +	
							filterScheduledStart.substring(0, 2) + " 00:00:00");
				} catch (Exception ex) {
					throw new Exception("Invalid value entered for Scheduled Start filter");
				}
			}
			if (!StringUtil.hasNoValue(filterScheduledEnd)) {
				try {
					scheduledEndTS = Timestamp.valueOf(filterScheduledEnd.substring(6, 10) + "-" +
							filterScheduledEnd.substring(3, 5) + "-" +	
							filterScheduledEnd.substring(0, 2) + " 23:59:59");
				} catch (Exception ex) {
					throw new Exception("Invalid value entered for Scheduled End filter");
				}
			}
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSNRSummaryList(?,?,?,?,?,?,?,?,?)}");
	    	cstmt.setLong(1, user.getUserId());
	    	cstmt.setString(2, status);
	    	cstmt.setString(3, status2);
	    	cstmt.setString(4, filterSite);
	    	cstmt.setString(5, filterNRId);
	    	cstmt.setString(6, filterBOEngineer);
	    	cstmt.setString(7, filterStatus);
	    	cstmt.setTimestamp(8, scheduledStartTS);
	    	cstmt.setTimestamp(9, scheduledEndTS);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrList.add(new SNRListItem(rs.getLong(1), rs.getLong(2),
						rs.getString(3), rs.getLong(4), rs.getString(5),
						rs.getString(6), rs.getString(7),rs.getString(8),
						rs.getString(9),rs.getString(10),
						rs.getString(11),rs.getString(12),
						rs.getInt(13), rs.getString(14), rs.getDate(15), 
						rs.getString(16), rs.getString(17), rs.getString(18),
						rs.getString(19), rs.getString(20), rs.getString(21), 
						rs.getString(22), rs.getTimestamp(23), rs.getTimestamp(24), 
						rs.getTimestamp(25), rs.getTimestamp(26), rs.getTimestamp(27),
						rs.getString(28), rs.getString(29)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getSNRSummaryList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return snrList;
	}

	public Collection<SNRListItem> getWorkQueuesSNRSummaryList(String filterSite, String filterNRId, 
			String filterStatus, String filterImplementationStatus,  
			String filterPrevImpl, String filterJobType, String filterCRQINCRaised, 
			String filterScheduledStart, String filterScheduledEnd) {
		message = null;
		ArrayList<SNRListItem> snrList = new ArrayList<SNRListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
		Timestamp scheduledStartTS = null;
		Timestamp scheduledEndTS = null;

	    try {
			if (!StringUtil.hasNoValue(filterScheduledStart)) {
				try {
					scheduledStartTS = Timestamp.valueOf(filterScheduledStart.substring(6, 10) + "-" +
							filterScheduledStart.substring(3, 5) + "-" +	
							filterScheduledStart.substring(0, 2) + " 00:00:00");
				} catch (Exception ex) {
					throw new Exception("Invalid value entered for Scheduled Start filter");
				}
			}
			if (!StringUtil.hasNoValue(filterScheduledEnd)) {
				try {
					scheduledEndTS = Timestamp.valueOf(filterScheduledEnd.substring(6, 10) + "-" +
							filterScheduledEnd.substring(3, 5) + "-" +	
							filterScheduledEnd.substring(0, 2) + " 23:59:59");
				} catch (Exception ex) {
					throw new Exception("Invalid value entered for Scheduled End filter");
				}
			}
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetWorkQueuesSNRSummaryList(?,?,?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, filterSite);
	    	cstmt.setString(2, filterNRId);
	    	cstmt.setString(3, filterStatus);
	    	cstmt.setString(4, filterImplementationStatus);
	    	cstmt.setString(5, filterPrevImpl);
	    	cstmt.setString(6, filterJobType);
	    	cstmt.setString(7, filterCRQINCRaised);
	    	cstmt.setTimestamp(8, scheduledStartTS);
	    	cstmt.setTimestamp(9, scheduledEndTS);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrList.add(new SNRListItem(rs.getLong(1), rs.getLong(2),
						rs.getString(3), rs.getLong(4), rs.getString(5),
						rs.getString(6), rs.getString(7),rs.getString(8),
						rs.getString(9),rs.getString(10),
						rs.getString(11),rs.getString(12),
						rs.getInt(13), rs.getString(14), rs.getDate(15), 
						rs.getString(16), rs.getString(17), rs.getString(18),
						rs.getString(19), rs.getString(20), rs.getString(21),
						rs.getTimestamp(22), rs.getTimestamp(23), rs.getTimestamp(24),
						rs.getTimestamp(25), rs.getTimestamp(26)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getWorkQueuesSNRSummaryList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return snrList;
	}
	
	public String getSNRUpdateListHTML(long selectedSNRId, String title, String filterSite, 
			String filterNRId, String filterAccessStatus, String filterCRQStatus, 
			String filterScheduledStart, String filterScheduledEnd, String filterJobType) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRListItem> snrList = getSNRUpdateList(title, filterSite, filterNRId,
				filterAccessStatus, filterCRQStatus, filterScheduledStart, filterScheduledEnd,
				filterJobType);
		if (snrList.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "10");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		} else {
			for (Iterator<SNRListItem> it = snrList.iterator(); it.hasNext(); ) {
				HTMLElement tr = new HTMLElement("tr");
				row++;
				oddRow = !oddRow;
				SNRListItem sli = it.next();
				String[] values = sli.getUpdateValueArray(title);
				String cS = title.equals(ServletConstants.UPDATE_ACCESS)?""
						:sli.closeCRQ()?"r":sli.flagCRQRaised()?"g":"";
				for (int i = 0; i < values.length; i ++) {
					HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2")+cS, 
						values[i]);
					if (values[i].equals(sli.DUMMYNR)) {
						td.setAttribute("title", sli.getNRId());
					} else if (values[i].equals(sli.NPC_SHORT)) {
						td.setValue(sli.getNextPrecheckDisplay());
						td.setAttribute("title", sli.getNextPrecheck());
					} else if (values[i].equals(sli.CS_SHORT)) {
						td.setValue(sli.getCRQStatusDisplay());
						td.setAttribute("title", sli.getCRQStatus());
					} else if (values[i].equals(sli.FE_SHORT)) {
						td.setValue(sli.getFieldEngineersShort());
						td.setAttribute("title", sli.getFieldEngineers());
					} else if (values[i].equals(sli.TECH_SHORT)) {
						td.setValue(sli.getTechnologiesShort());
						td.setAttribute("title", sli.getTechnologies());
					}
					tr.appendValue(td.toString());
				}
				
				HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
					"radio", Long.toString(sli.getSNRId()), 
					"snrSelect('"+sli.getSNRId()+"','"+sli.getStatus()+"','"+
					sli.getCustomerId()+"','"+sli.getSite()+"','"+sli.getNRId()+"')");
				if (sli.getSNRId() == selectedSNRId) {
					input.setChecked(true);
				}
				HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2")+cS, 
					input.toString());
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		}
		return html.toString();
	}

	public Collection<SNRListItem> getSNRUpdateList(String title, String filterSite, 
			String filterNRId, String filterAccessStatus, String filterCRQStatus, 
			String filterScheduledStart, String filterScheduledEnd, String filterJobType) {
		message = null;
		ArrayList<SNRListItem> snrList = new ArrayList<SNRListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
		Timestamp scheduledStartTS = null;
		Timestamp scheduledEndTS = null;
	    try {
	    	if (!title.equals(ServletConstants.UPDATE_ACCESS)) {
				if (!StringUtil.hasNoValue(filterScheduledStart)) {
					try {
						scheduledStartTS = Timestamp.valueOf(filterScheduledStart.substring(6, 10) + "-" +
								filterScheduledStart.substring(3, 5) + "-" +	
								filterScheduledStart.substring(0, 2) + " 00:00:00");
					} catch (Exception ex) {
						throw new Exception("Invalid value entered for Scheduled Start filter");
					}
				}
				if (!StringUtil.hasNoValue(filterScheduledEnd)) {
					try {
						scheduledEndTS = Timestamp.valueOf(filterScheduledEnd.substring(6, 10) + "-" +
								filterScheduledEnd.substring(3, 5) + "-" +	
								filterScheduledEnd.substring(0, 2) + " 23:59:59");
					} catch (Exception ex) {
						throw new Exception("Invalid value entered for Scheduled End filter");
					}
				}
			}
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSNRUpdateList(?,?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, title);
	    	cstmt.setString(2, filterSite);
	    	cstmt.setString(3, filterNRId);
	    	cstmt.setString(4, filterAccessStatus);
	    	cstmt.setString(5, filterCRQStatus);
	    	cstmt.setTimestamp(6, scheduledStartTS);
	    	cstmt.setTimestamp(7, scheduledEndTS);
	    	cstmt.setString(8, filterJobType);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrList.add(new SNRListItem(rs.getLong(1), rs.getLong(2),
						rs.getString(3), rs.getLong(4), rs.getString(5),
						rs.getString(6), rs.getString(7),rs.getString(8),
						rs.getString(9),rs.getString(10),
						rs.getString(11),rs.getString(12),
						rs.getInt(13), rs.getString(14), rs.getDate(15), 
						rs.getString(16), rs.getString(17), rs.getString(18),
						rs.getString(19), rs.getString(20), rs.getString(21), 
						rs.getString(22), rs.getTimestamp(23), rs.getTimestamp(24), 
						rs.getTimestamp(25), rs.getTimestamp(26), rs.getTimestamp(27),
						rs.getString(28), rs.getString(29)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getSNRUpdateList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return snrList;
	}
	
	public String getSNRSummaryListHTML(long selectedSNRId, String status, String status2, 
			String filterSite, String filterNRId, String filterBOEngineer, String filterStatus, 
			String filterScheduledStart, String filterScheduledEnd) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRListItem> snrList = getSNRSummaryList(status, status2, 
				filterSite, filterNRId, filterBOEngineer, filterStatus, 
				filterScheduledStart, filterScheduledEnd);
		if (snrList.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "10");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		} else {
			for (Iterator<SNRListItem> it = snrList.iterator(); it.hasNext(); ) {
				HTMLElement tr = new HTMLElement("tr");
				row++;
				oddRow = !oddRow;
				SNRListItem sli = it.next();
				String[] values = sli.getMultiValueArray(status2);
				String cS = sli.isOverdue()||sli.hasNextPreCheck()?"r":
					sli.getStatus().equalsIgnoreCase(ServletConstants.STATUS_REQUESTED)|| 
					sli.getStatus().equalsIgnoreCase(ServletConstants.STATUS_AWAITING_SCHEDULING)||
					sli.getStatus().equalsIgnoreCase(ServletConstants.STATUS_SCHEDULED)?"g":"";
				for (int i = 0; i < values.length; i ++) {
					HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2")+cS, 
						values[i]);
					if (values[i].equals(sli.DUMMYNR)) {
						td.setAttribute("title", sli.getNRId());
					} else if (values[i].equals(sli.NPC_SHORT)) {
						td.setValue(sli.getNextPrecheckDisplay());
						td.setAttribute("title", sli.getNextPrecheck());
					} else if (values[i].equals(sli.CS_SHORT)) {
						td.setValue(sli.getCRQStatusDisplay());
						td.setAttribute("title", sli.getCRQStatus());
					} else if (values[i].equals(sli.BOE_SHORT)) {
						td.setValue(sli.getBOEngineersShort());
						td.setAttribute("title", sli.getBOEngineers());
					}
					tr.appendValue(td.toString());
				}
				HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
					"radio", Long.toString(sli.getSNRId()), 
					"snrSelect('"+sli.getSNRId()+"','"+sli.getStatus()+"','"+
					sli.getCustomerId()+"','"+sli.getSite()+"','"+sli.getNRId()+"',"+
					sli.getEFComplete()+")");
				if (sli.getSNRId() == selectedSNRId) {
					input.setChecked(true);
				}
				HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2")+cS, 
					input.toString());
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		}
		return html.toString();
	}
	
	public String getWorkQueuesSNRSummaryListHTML(String filterSite, String filterNRId, 
			String filterStatus, String filterImplementationStatus,
			String filterPrevImpl, String filterJobType, String filterCRQINCRaised, 
			String filterScheduledStart, String filterScheduledEnd, long selectedSNRId) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRListItem> snrList = getWorkQueuesSNRSummaryList(filterSite, 
				filterNRId, filterStatus, filterImplementationStatus,
				filterPrevImpl, filterJobType, filterCRQINCRaised, 
				filterScheduledStart, filterScheduledEnd);
		if (snrList.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "10");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		} else {
			for (Iterator<SNRListItem> it = snrList.iterator(); it.hasNext(); ) {
				HTMLElement tr = new HTMLElement("tr");
				row++;
				oddRow = !oddRow;
				SNRListItem sli = it.next();
				String[] values = sli.getSummaryValueArray();
				String cS = sli.getWorkQueuesItemCS();
				for (int i = 0; i < values.length; i ++) {
					HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2")+cS, 
						values[i]);
					if (values[i].equals(sli.DUMMYNR)) {
						td.setAttribute("title", sli.getNRId());
					} else if (values[i].equals(sli.NPC_SHORT)) {
						td.setValue(sli.getNextPrecheckDisplay());
						td.setAttribute("title", sli.getNextPrecheck());
					} else if (values[i].startsWith(sli.EF_DATE_SET)) {
						td.setValue(sli.ASTERISK);
						td.setAttribute("title", sli.getEFClaimDTTitle(values[i]));
					}
					tr.appendValue(td.toString());
				}
				HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
					"radio", Long.toString(sli.getSNRId()), 
					"snrSelect('"+sli.getSNRId()+"','"+sli.getStatus()+"','"+
					sli.getCustomerId()+"','"+sli.getSite()+"','"+sli.getNRId()+"'," +
					sli.getEFComplete() + ")");
				if (sli.getSNRId() == selectedSNRId) {
					input.setChecked(true);
				}
				HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2")+cS, 
					input.toString());
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		}
		return html.toString();
	}

	public Collection<SNRListItem> getPMOList(String filterSite, 
			String filterNRId, String filterStatus, 
			String filterScheduledStart, String filterScheduledEnd) {
		ArrayList<SNRListItem> snrList = new ArrayList<SNRListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
		Timestamp scheduledStartTS = null;
		Timestamp scheduledEndTS = null;
	    try {
			if (!StringUtil.hasNoValue(filterScheduledStart)) {
				try {
					scheduledStartTS = Timestamp.valueOf(filterScheduledStart.substring(6, 10) + "-" +
							filterScheduledStart.substring(3, 5) + "-" +	
							filterScheduledStart.substring(0, 2) + " 00:00:00");
				} catch (Exception ex) {
					throw new Exception("Invalid value entered for Scheduled Start filter");
				}
			}
			if (!StringUtil.hasNoValue(filterScheduledEnd)) {
				try {
					scheduledEndTS = Timestamp.valueOf(filterScheduledEnd.substring(6, 10) + "-" +
							filterScheduledEnd.substring(3, 5) + "-" +	
							filterScheduledEnd.substring(0, 2) + " 23:59:59");
				} catch (Exception ex) {
					throw new Exception("Invalid value entered for Scheduled End filter");
				}
			}
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetPMOList(?,?,?,?,?)}");
	    	cstmt.setString(1, filterSite);
	    	cstmt.setString(2, filterNRId);
	    	cstmt.setString(3, filterStatus);
	    	cstmt.setTimestamp(4, scheduledStartTS);
	    	cstmt.setTimestamp(5, scheduledEndTS);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrList.add(new SNRListItem(rs.getLong(1),
						rs.getString(2), rs.getString(3), rs.getDate(4), 
						rs.getTimestamp(5), rs.getTimestamp(6), 
						rs.getString(7)));
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
	    
	    return snrList;
	}
	
	public String getPMOListHTML(String filterSite, String filterNRId, String filterStatus, 
			String filterScheduledStart, String filterScheduledEnd, long selectedSNRId) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRListItem> snrList = getPMOList(filterSite, 
			filterNRId, filterStatus, filterScheduledStart, filterScheduledEnd);
		for (Iterator<SNRListItem> it = snrList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			row++;
			oddRow = !oddRow;
			SNRListItem sli = it.next();
			String[] values = sli.getPMOValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);
				if (values[i].equals(sli.DUMMYNR)) {
					td.setAttribute("title", sli.getNRId());
				}

				tr.appendValue(td.toString());
			}
			HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
				"radio", Long.toString(sli.getSNRId()), 
				"snrSelect('"+sli.getSNRId()+"','"+sli.getSite()+"','"+sli.getNRId()+"')");
			if ((sli.getSNRId() == selectedSNRId)) {
				input.setChecked(true);
			}
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
				input.toString());
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}

	public Collection<PMOAbort> getPMOAbortList(long snrId) {
		ArrayList<PMOAbort> pmoaList = new ArrayList<PMOAbort>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetPMOAbortsForSNR(?)}");
	    	cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					pmoaList.add(new PMOAbort(rs.getLong(1),
						rs.getString(2), rs.getTimestamp(3), 
						rs.getDate(4)));
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
	    
	    return pmoaList;
	}
	
	public String getPMOAbortListHTML(long snrId) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<PMOAbort> pmoaList = getPMOAbortList(snrId);
		for (Iterator<PMOAbort> it = pmoaList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			row++;
			oddRow = !oddRow;
			PMOAbort pmoa = it.next();
			String[] values = pmoa.getListValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);

				tr.appendValue(td.toString());
			}
			HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
				"radio", Long.toString(pmoa.getSNRId()), 
				"abortSelect('"+pmoa.getSNRId()+"','"+pmoa.getHistoryDT()+"','"+pmoa.getAbortType()+"')");
			/*if ((pmoa.getSNRId() == selectedSNRId)) {
				input.setChecked(true);
			}*/
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
				input.toString());
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}

	public SNRListItem getSelectedPMOItem(long snrId) {
		SNRListItem sli = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetPMOItem(?)}");
	    	cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					sli = new SNRListItem(rs.getLong(1),
						rs.getString(2), rs.getString(3), rs.getDate(4), 
						rs.getTimestamp(5), rs.getTimestamp(6), 
						rs.getString(7), rs.getDouble(8), rs.getDouble(9), 
						rs.getString(10), rs.getString(11));
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
	    
	    return sli;
	}
	
	private SNRListItem psli = null;
	public String getSelectedPMOItemHTML(long snrId) {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		psli = getSelectedPMOItem(snrId);
		HTMLElement tr = new HTMLElement("tr");
		oddRow = !oddRow;
		String[] values = psli.getPMOValueArray();
		for (int i = 0; i < values.length; i ++) {
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
				values[i]);

			tr.appendValue(td.toString());
		}
		HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
			"&nbsp;");
		tr.appendValue(td.toString());
		html.append(tr.toString());
		return html.toString();
	}
	
	public SNRListItem getSelectedPMOItem() {
		return psli;
	}

	public SNRListItem getSelectedSNRSummary(long snrId) {
		message = null;
		SNRListItem snrLI = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSelectedSNRSummary(?)}");
	    	cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrLI = new SNRListItem(rs.getLong(1), rs.getLong(2),
							rs.getString(3), rs.getLong(4), rs.getString(5),
							rs.getString(6), rs.getString(7),rs.getString(8),
							rs.getString(9),rs.getString(10),
							rs.getString(11),rs.getString(12),
							rs.getInt(13), rs.getString(14), rs.getDate(15), 
							rs.getString(16), rs.getString(17), rs.getString(18),
							rs.getString(19), rs.getString(20), rs.getString(21), 
							rs.getString(22), rs.getString(23), rs.getString(24));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getSelectedSNRSummary(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return snrLI;
	}
	
	public String getSelectedSNRSummaryHTML(long snrId, boolean updateSD, boolean confirm) {
		StringBuilder html = new StringBuilder();
		SNRListItem sli = getSelectedSNRSummary(snrId);
		if (sli == null) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "10");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		} else {
			HTMLElement tr = new HTMLElement("tr");
			String[] values = confirm?sli.getConfirmValueArray():sli.getMultiValueArray(updateSD);
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", "grid1", 
					values[i]);
				if (values[i].equals(sli.DUMMYNR)) {
					td.setAttribute("title", sli.getNRId());
				} else if (values[i].equals(sli.NPC_SHORT)) {
					td.setValue(sli.getNextPrecheckDisplay());
					td.setAttribute("title", sli.getNextPrecheck());
				} else if (values[i].equals(sli.UPDATE_SD)) {
					td.setValue(processDateTimeParameter("scheduledDT", 
							sli.getScheduledDateString(), false,	
							"width:70%", "scheduledDTChange()"));
				}
				tr.appendValue(td.toString());
			}
			HTMLElement td = new HTMLElement("td", "grid1");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public String getSelectedSNRSummaryHTML(long snrId, boolean updateSD, boolean confirm, String defaultSD) {
		StringBuilder html = new StringBuilder();
		SNRListItem sli = getSelectedSNRSummary(snrId);
		if (sli == null) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "10");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		} else {
			HTMLElement tr = new HTMLElement("tr");
			String[] values = confirm?sli.getConfirmValueArray():sli.getMultiValueArray(updateSD);
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", "grid1", 
					values[i]);
				if (values[i].equals(sli.DUMMYNR)) {
					td.setAttribute("title", sli.getNRId());
				} else if (values[i].equals(sli.NPC_SHORT)) {
					td.setValue(sli.getNextPrecheckDisplay());
					td.setAttribute("title", sli.getNextPrecheck());
				} else if (values[i].equals(sli.UPDATE_SD)) {
					if ((defaultSD==null)||(defaultSD.trim().equals(""))) {
						td.setValue(processDateTimeParameter("scheduledDT", 
								sli.getScheduledDateString(), false,	
								"width:70%", "scheduledDTChange()"));
					} else {
						td.setValue(processDateTimeParameter("scheduledDT", 
								defaultSD, false,	
								"width:70%", "scheduledDTChange()"));
					}
				}
				tr.appendValue(td.toString());
			}
			HTMLElement td = new HTMLElement("td", "grid1");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public String getSelectedSNRSummaryHTML(long snrId, String title) {
		StringBuilder html = new StringBuilder();
		SNRListItem sli = getSelectedSNRSummary(snrId);
		if (sli == null) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "10");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		} else {
			String cS = title.equals(ServletConstants.UPDATE_ACCESS)?""
					:sli.closeCRQ()?"r":sli.flagCRQRaised()?"g":"";
			HTMLElement tr = new HTMLElement("tr");
			String[] values = sli.getUpdateValueArray(title);
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", "grid1"+cS, 
					values[i]);
				if (values[i].equals(sli.DUMMYNR)) {
					td.setAttribute("title", sli.getNRId());
				} else if (values[i].equals(sli.NPC_SHORT)) {
					td.setValue(sli.getNextPrecheckDisplay());
					td.setAttribute("title", sli.getNextPrecheck());
				} else if (values[i].equals(sli.CS_SHORT)) {
					td.setValue(sli.getCRQStatusDisplay());
					td.setAttribute("title", sli.getCRQStatus());
				} else if (values[i].equals(sli.FE_SHORT)) {
					td.setValue(sli.getFieldEngineersShort());
					td.setAttribute("title", sli.getFieldEngineers());
				} else if (values[i].equals(sli.TECH_SHORT)) {
					td.setValue(sli.getTechnologiesShort());
					td.setAttribute("title", sli.getTechnologies());
				}
				tr.appendValue(td.toString());
			}
			HTMLElement td = new HTMLElement("td", "grid1");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}

	public Collection<SNRCommentary> getSNRCommentaryList(long snrId) {
		ArrayList<SNRCommentary> commentaryList = new ArrayList<SNRCommentary>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSNRCommentaryList(?)}");
	    	cstmt.setLong(1, snrId);
	    	//cstmt.setString(2, password);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					SNRCommentary snrC = new SNRCommentary(snrId,  
							rs.getDate(1), rs.getLong(2), rs.getString(3), 
							rs.getString(4), rs.getDate(5), rs.getString(6));
					commentaryList.add(snrC); 
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
	    
	    return commentaryList;
	}
	
	public String getSNRCommentaryListHTML(long snrId) {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		Collection<SNRCommentary> commentaryList = getSNRCommentaryList(snrId);
		for (Iterator<SNRCommentary> it = commentaryList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			oddRow = !oddRow;
			SNRCommentary sci = it.next();
			String[] values = sci.getValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);
				tr.appendValue(td.toString());
			}
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public String getImplCommentaryListHTML(long snrId) {
		StringBuilder html = new StringBuilder();
		Collection<SNRCommentary> commentaryList = getSNRCommentaryList(snrId);
		for (Iterator<SNRCommentary> it = commentaryList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			SNRCommentary sci = it.next();
			String[] values = sci.getValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", "grid2xs", 
					values[i]);
				tr.appendValue(td.toString());
			}
			html.append(tr.toString());
		}
		return html.toString();
	}

	public Collection<SNRCommentary> getSiteCommentaryList(String site) {
		ArrayList<SNRCommentary> commentaryList = new ArrayList<SNRCommentary>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSiteCommentaryList(?)}");
	    	cstmt.setString(1, site);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					SNRCommentary snrC = new SNRCommentary(rs.getLong(1),  
							rs.getDate(2), rs.getLong(3), rs.getString(4), 
							rs.getString(5), rs.getDate(6), rs.getString(7));
					commentaryList.add(snrC); 
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
	    
	    return commentaryList;
	}
	
	public String getSiteCommentaryListHTML(String site) {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		Collection<SNRCommentary> commentaryList = getSiteCommentaryList(site);
		for (Iterator<SNRCommentary> it = commentaryList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			oddRow = !oddRow;
			SNRCommentary sci = it.next();
			String[] values = sci.getValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);
				tr.appendValue(td.toString());
			}
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	private String customerFilterPrefix = "filter";
	
	public String getCustomerFilterHTML(String selectClass, String selectedOption, String prefix) {
		String html;
		customerFilterPrefix = prefix;
		html = getCustomerFilterHTML(selectClass, selectedOption);
		customerFilterPrefix = "filter";
		return html;
	}
	
	public String getCustomerFilterHTML(String selectClass, String selectedOption) {
		String html;
		if (user.getUserType().equalsIgnoreCase(User.USER_TYPE_CUSTOMER)) {
			HTMLElement input = new HTMLElement("input", customerFilterPrefix + "Customer", 
					customerFilterPrefix + "Customer", "hidden", selectedOption, "");
			html = input.toString();
		} else if (user.getUserType().equalsIgnoreCase(User.USER_TYPE_THIRD_PARTY)) {
	    	Select select = new Select(customerFilterPrefix + "Customer",  selectClass);
			Option option = new Option("All", "All", selectedOption.equals("All"));
			select.appendValue(option.toString());
	    	String[] customerNames = user.getCustomerNames();
	    	for (int i = 0; i < customerNames.length; i++) {
				option = new Option(customerNames[i], customerNames[i],
					selectedOption.equals(customerNames[i]));
				select.appendValue(option.toString());
	    	}
	    	html = select.toString();
		} else {
			String[] parameters = {"N"};
			html = getSelectHTML("Customer", customerFilterPrefix, selectClass, selectedOption, parameters);
		}
		return html;
	}
			
	private String[] parameterArray = null;
	private boolean disableSelect = false;
	private boolean multipleSelect = false;
	private boolean isTechnology = false;
	private String[] selectedArray = null;

	public String getSelectHTML(String selectItem, String prefix, 
			String selectClass, String selectedOption, String[] parameters) {
		parameterArray = parameters;
		String selectHTML = getSelectHTML(selectItem, prefix, selectClass, selectedOption);
		parameterArray = null;
		return selectHTML;
	}
	
	public String getSelectHTMLWithInitialValue(String selectItem, String prefix, 
			String selectClass, String selectedOption) {
		String[] thisArray = {selectedOption};
		parameterArray = thisArray;
		String selectHTML = getSelectHTML(selectItem, prefix, selectClass, selectedOption);
		parameterArray = null;
		return selectHTML;
	}
	
	public String getSelectHTMLWithInitialValue(String selectItem, String prefix, 
			String selectClass, String selectedOption, String onClickOption) {
		String[] thisArray = {selectedOption};
		parameterArray = thisArray;
		String selectHTML = getSelectHTML(selectItem, prefix, selectClass, selectedOption, onClickOption);
		parameterArray = null;
		return selectHTML;
	}
	
	public String getSelectHTMLWithInitialValue(String selectItem, String prefix, 
			String selectClass, String selectedOption, String onClickOption, String selectedOption2 ) {
		String[] thisArray = {selectedOption, selectedOption2};
		parameterArray = thisArray;
		String selectHTML = getSelectHTML(selectItem, prefix, selectClass, selectedOption, onClickOption);
		parameterArray = null;
		return selectHTML;
	}
	
	public String getSelectHTMLWithInitialValue(String selectItem, String prefix, 
			String selectClass, String selectedOption, boolean disabled) {
		String[] thisArray = {selectedOption};
		parameterArray = thisArray;
		disableSelect = disabled;
		String selectHTML = getSelectHTML(selectItem, prefix, selectClass, selectedOption);
		parameterArray = null;
		disableSelect = false;
		return selectHTML;
	}

	public String getMultiSelectHTML(String selectItem, String prefix, 
			String selectClass, String[] selectedOptions) {
		multipleSelect = true;
		selectedArray = selectedOptions;
		String selectHTML = getSelectHTML(selectItem, prefix, selectClass, "");
		multipleSelect = false;
		selectedArray = null;
		return selectHTML;
	}
	
	public String getSelectHTML(String selectItem, String prefix, 
			String selectClass, String selectedOption, boolean disabled) {
		disableSelect = disabled;
		String selectHTML = getSelectHTML(selectItem, prefix, selectClass, selectedOption);
		disableSelect = false;
		return selectHTML;
	}
	
	public String getTechnologySelectHTML(String selectItem, String prefix, 
				String selectClass, String selectedOption) {
		isTechnology = true;
		String selectHTML = getSelectHTML(selectItem, prefix, selectClass, selectedOption);
		isTechnology = false;
		return selectHTML;
	}
	
	public String getSelectHTML(String selectItem, String prefix, 
			String selectClass, String selectedOption, String onClickOption) {
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	Select select = new Select((prefix==null?"":prefix) + selectItem,  selectClass);
    	select.appendValue(" onclick=\""+onClickOption+"\"" );
	    try {
	    	conn = DriverManager.getConnection(url);
	    	StringBuilder sb = new StringBuilder("{call Get" + selectItem +
		    		"SelectList(");
	    	if (parameterArray != null) {
	    		for (int i = 0; i < parameterArray.length; i++) {
	    			sb.append("?,");
		    	}
		    	sb.setLength(sb.length()-1);
	    	}
	    	sb.append(")}");
	    	cstmt = conn.prepareCall(sb.toString());
	    	if (parameterArray != null) {
	    		for (int i = 0; i < parameterArray.length; i++) {
	    			cstmt.setString(i+1, parameterArray[i]);
		    	}
	    	}
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				ResultSetMetaData rsMetaData = rs.getMetaData();
				boolean oneCol = rsMetaData.getColumnCount()==1;
				while (rs.next()) {
					String col2 = oneCol?rs.getString(1):rs.getString(2); 
					boolean selected = false;
			    	if (multipleSelect) {
			    		if (selectedArray != null) {
				    		for (int i = 0; i <	selectedArray.length; i++) {
				    			if (selectedArray[i].equals(col2)) {
				    				selected = true;
				    				break;
				    			}
				    		}
			    		}
			    	} else {
			    		selected = selectedOption.equals(col2);
			    	}
					Option option = new Option(rs.getString(1), col2,
						selected);
					select.appendValue(option.toString());
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
		return select.toString();
	}
		
	public String getSelectHTML(String selectItem, String prefix, 
				String selectClass, String selectedOption) {
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	Select select = new Select((prefix==null?"":prefix) + selectItem,  selectClass);
    	String replacement = "";
    	if (multipleSelect) {
    		select.setAttribute("multiple", "multiple");
    	}
       	if (disableSelect) {
    		select.setAttribute("disabled", "disabled");
    		HTMLElement input = new HTMLElement("input");
    		input.setAttribute("type", "hidden");
    		input.setAttribute("name", "disabled" + selectItem);
    		input.setAttribute("id", "disabled" + selectItem);
    		input.setAttribute("value", selectedOption);
    		replacement = input.toString();
    	}
	    try {
	    	conn = DriverManager.getConnection(url);
	    	StringBuilder sb = new StringBuilder("{call Get" + (isTechnology?"Technology":selectItem) +
		    		"SelectList(");
	    	if (parameterArray != null) {
	    		for (int i = 0; i < parameterArray.length; i++) {
	    			sb.append("?,");
		    	}
		    	sb.setLength(sb.length()-1);
	    	}
	    	sb.append(")}");
	    	cstmt = conn.prepareCall(sb.toString());
	    	if (parameterArray != null) {
	    		for (int i = 0; i < parameterArray.length; i++) {
	    			cstmt.setString(i+1, parameterArray[i]);
		    	}
	    	}
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				ResultSetMetaData rsMetaData = rs.getMetaData();
				boolean oneCol = rsMetaData.getColumnCount()==1;
				while (rs.next()) {
					String col2 = oneCol?rs.getString(1):rs.getString(2); 
					boolean selected = false;
			    	if (multipleSelect) {
			    		if (selectedArray != null) {
				    		for (int i = 0; i <	selectedArray.length; i++) {
				    			if (selectedArray[i].equals(col2)) {
				    				selected = true;
				    				break;
				    			}
				    		}
			    		}
			    	} else {
			    		selected = selectedOption.equals(col2);
			    	}
					Option option = new Option(rs.getString(1), col2,
						selected);
					select.appendValue(option.toString());
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
			
		return select.toString() + replacement;
	}

	public SNR getSNRDetail(long snrId, boolean history, long historyDT) {
		SNR snrDetail = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	try {
	    	conn = DriverManager.getConnection(url);
	    	if (history) {
	    		cstmt = conn.prepareCall("{call GetSNRHistoryDetail(?,?)}");
		    	cstmt.setLong(1, snrId);
		    	cstmt.setTimestamp(2, new Timestamp(historyDT));
	    	} else {
	    		cstmt = conn.prepareCall("{call GetSNRDetail(?)}");
		    	cstmt.setLong(1, snrId);
	    	}
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
						rs.getTimestamp(47),rs.getString(48),
						rs.getTimestamp(49),rs.getTimestamp(50),rs.getString(51),
						rs.getDate(52),rs.getString(53),rs.getString(54),
						rs.getString(55),rs.getString(56),rs.getString(57),
						rs.getString(58),rs.getString(59),(history?rs.getTimestamp(60):null),
						rs.getString(61),rs.getString(62),rs.getString(63),
						rs.getString(64),rs.getString(65),rs.getString(66),
						rs.getString(67),rs.getString(68),rs.getTimestamp(69),
						rs.getString(70),rs.getTimestamp(71),rs.getString(72),rs.getTimestamp(73),
						rs.getString(74),rs.getTimestamp(75),rs.getString(76),rs.getTimestamp(77),
						rs.getString(78),rs.getTimestamp(79),rs.getString(80),rs.getTimestamp(81),
						rs.getString(82),rs.getTimestamp(83),rs.getString(84),rs.getTimestamp(85),
						rs.getString(86),rs.getTimestamp(87),rs.getString(88),rs.getTimestamp(89),
						rs.getString(90),rs.getTimestamp(91),rs.getString(92),rs.getTimestamp(93),
						rs.getString(94),rs.getTimestamp(95),rs.getString(96),rs.getTimestamp(97),
						rs.getString(98),rs.getTimestamp(99),rs.getString(100),rs.getTimestamp(101),
						rs.getString(102),rs.getTimestamp(103),	rs.getString(104),rs.getString(105));
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
	
	public SNRBOInformation getSNRBOInformation(long snrId) {
		SNRBOInformation snrBOI = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
	    	conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetSNRBOInformation(?)}");
	    	cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					snrBOI = new SNRBOInformation(rs.getLong(1),
						rs.getString(2), rs.getString(3),
						rs.getString(4),rs.getString(5),rs.getString(6),
						rs.getDate(7),rs.getString(8),rs.getString(9),
						rs.getString(10),rs.getString(11),rs.getString(12),
						rs.getDate(13),rs.getString(14),rs.getString(15),
						rs.getString(16),rs.getTimestamp(17),rs.getTimestamp(18),
						rs.getString(19),rs.getString(20),rs.getString(21),
						rs.getString(22),rs.getString(23),rs.getString(24),
						rs.getString(25),rs.getString(26),rs.getString(27),
						rs.getString(28),
						rs.getString(29),rs.getString(30),rs.getString(31),
						rs.getTimestamp(32),rs.getTimestamp(33),rs.getTimestamp(34),
						rs.getTimestamp(35),rs.getString(36),rs.getString(37),
						rs.getString(38),rs.getString(39),rs.getString(40),
						rs.getString(41),rs.getString(42),rs.getString(43),
						rs.getDate(44),rs.getDate(45),rs.getString(46),
						rs.getDate(47),rs.getDate(48),
						rs.getString(49),rs.getString(50),
						rs.getString(51),rs.getString(52),
						rs.getString(53),rs.getString(54),
						rs.getString(55),rs.getString(56),
						rs.getString(57));
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
	    
	    return snrBOI;
	}
	
	public ArrayList<SNRTechnology> getSNRBOTechnologiesToAdd(long snrId) {
		ArrayList<SNRTechnology> al = new ArrayList<SNRTechnology>();
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetSNRBOTechnologiesToAdd(?)}");
			cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					al.add(new SNRTechnology(snrId,
						rs.getString(2), rs.getString(3),
						rs.getLong(1)));
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
		
		return al;
	}
	
	public String getSNRBOTechnologiesToAddHTML(HttpSession session, long snrId) {
		StringBuilder html = new StringBuilder();
		Collection<SNRTechnology> snrTL = getSNRBOTechnologiesToAdd(snrId);
		session.setAttribute(ServletConstants.SNR_BO_TECHNOLOGY_ADD_COLLECTION_NAME_IN_SESSION, snrTL);
		for (Iterator<SNRTechnology> it = snrTL.iterator(); it.hasNext(); ) {
			SNRTechnology snrT = it.next();
			HTMLElement div = new HTMLElement("div");
			div.setAttribute("style", "padding-bottom:10px");
			HTMLElement check = new HTMLElement("input");
			check.setAttribute("type", "checkbox");
			check.setAttribute("name", "checkTech" + snrT.getTechnologyId());
			check.setAttribute("id", "checkTech" + snrT.getTechnologyId());
			check.setAttribute("value", String.valueOf(snrT.getTechnologyId()));
			div.appendValue(check.toString() + " " + snrT.getTechnologyNameDisplay());
			html.append(div.toString());
		}
		return html.toString();
	}
	
	public ArrayList<SNRTechnology> getSNRBOTechnologies(long snrId) {
		ArrayList<SNRTechnology> al = new ArrayList<SNRTechnology>();
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetSNRBOTechnologies(?)}");
			cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					al.add(new SNRTechnology(rs.getLong(1),
						rs.getLong(2), rs.getString(3),
						rs.getString(4),rs.getString(5),rs.getString(6)));
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
		
		return al;
	}
	
	public String getSNRBOTechnologiesHTML(long snrId, String activeAlarmsInd, String healthChecksInd,
			String incTicketNo, String hopDeliveredInd, String hopFilename, String sfrCompleted, 
			String ef360ClaimDT, String ef390ClaimDT, String implementationStatus, String abortType) {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		ArrayList<SNRTechnology> al = getSNRBOTechnologies(snrId);
		HTMLElement calImg = new HTMLElement("img");
		calImg.setAttribute("src", "images/cal.gif");
		calImg.setAttribute("style", "cursor:pointer");
		HTMLElement buttonUT = new HTMLElement("input", "listbutton", "");
		buttonUT.setAttribute("type", "button");
		buttonUT.setAttribute("onClick", "snrImplementationDetailClick('updateTech')");
		buttonUT.setAttribute("title", "Update technologies");
		buttonUT.setAttribute("value", "U");
		HTMLElement buttonAT = new HTMLElement("input", "listbutton", "");
		buttonAT.setAttribute("type", "button");
		buttonAT.setAttribute("onClick", "showAddBOTechnologies()");
		buttonAT.setAttribute("title", "Add technology");
		buttonAT.setAttribute("value", "A");
		HTMLElement buttonDT = new HTMLElement("input", "listbutton", "");
		buttonDT.setAttribute("type", "button");
		buttonDT.setAttribute("onClick", "showDelBOTechnologies()");
		buttonDT.setAttribute("title", "Delete technology");
		buttonDT.setAttribute("value", "D");
		for (int i = 0; i < 5; i++) {
			oddRow = !oddRow;
			SNRTechnology thisT = i<al.size()?al.get(i):null;
			HTMLElement input = null;
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td = new HTMLElement("td", oddRow?"grid1t":"grid2t", 
					i<al.size()?thisT.getTechnologyNameDisplay():"&nbsp;");
			td.setAttribute("colspan", "3");
			tr.appendValue(td.toString());
			td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					i<al.size()?getTechnologySelectHTML(thisT.getTechnologyColumn(), "select", "filter", thisT.getImplemented()):"&nbsp;");
			td.setAttribute("colspan", "2");
			tr.appendValue(td.toString());
			td = new HTMLElement("td", oddRow?"grid1br":"grid2br", 
					i==0?buttonUT.toString():i==1?buttonAT.toString():i==2?buttonDT.toString():"&nbsp;");
			tr.appendValue(td.toString());
			switch (i) {
			case 0:
				td = new HTMLElement("td", oddRow?"grid1t":"grid2t", "New Alarms");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1":"grid2", 
						getSelectHTMLWithInitialValue("ActiveAlarmsInd", "select", "filter", activeAlarmsInd));
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1t":"grid2t", "Health Checks");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1":"grid2", 
						getSelectHTMLWithInitialValue("HealthChecksInd", "select", "filter", healthChecksInd));
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1t":"grid2t", "INC Ticket Number");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				input = new HTMLElement("input", "incTicketNo", "incTicketNo", "text", 
						incTicketNo, "");
				input.setAttribute("style", "width:96%");
				td = new HTMLElement("td", oddRow?"grid1":"grid2", 
						input.toString());
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1":"grid2", 
						"&nbsp;");
				tr.appendValue(td.toString());
				break;
			case 1:
				td = new HTMLElement("td", oddRow?"grid1t":"grid2t", "HOP Completed");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1":"grid2", 
						getSelectHTMLWithInitialValue("HOPDeliveredInd", "select", "filter", hopDeliveredInd));
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1t":"grid2t", "HOP Filename");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				input = new HTMLElement("input", "implementationHOPFilename", "implementationHOPFilename", "text", 
						hopFilename, "");
				input.setAttribute("style", "width:96%");
				td = new HTMLElement("td", oddRow?"grid1":"grid2", 
						input.toString());
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1t":"grid2t", "SFR Completed");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1":"grid2", 
						getSelectHTMLWithInitialValue("SFRCompleted", "select", "filter", sfrCompleted));
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1":"grid2", 
						"&nbsp;");
				tr.appendValue(td.toString());
				break;
			case 2:
				td = new HTMLElement("td", oddRow?"grid1tbb":"grid2tbb", "EF360 Sign Off Date");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				input = new HTMLElement("input", "ef360ClaimDT", "ef360ClaimDT", "text", 
						ef360ClaimDT, "");
				input.setAttribute("style", "width:80%");
				input.setReadOnly(true);
				calImg.setAttribute("onclick", "javascript:NewCssCal ('ef360ClaimDT','ddMMyyyy','arrow')");
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						input.toString() + calImg.toString());
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1tbb":"grid2tbb", "EF390 Sign Off Date");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				input = new HTMLElement("input", "ef390ClaimDT", "ef390ClaimDT", "text", 
						ef390ClaimDT, "");
				input.setAttribute("style", "width:80%");
				input.setReadOnly(true);
				calImg.setAttribute("onclick", "javascript:NewCssCal ('ef390ClaimDT','ddMMyyyy','arrow')");
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						input.toString() + calImg.toString());
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						"&nbsp;");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				HTMLElement buttonUC = new HTMLElement("input", "listbutton", "");
				buttonUC.setAttribute("type", "button");
				buttonUC.setAttribute("onClick", "snrImplementationDetailClick('updateConf')");
				buttonUC.setAttribute("title", "Update Confirmation Details");
				buttonUC.setAttribute("value", "U");
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						buttonUC.toString());
				td.setAttribute("colspan", "3");
				td.setAttribute("align", "right");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						"&nbsp;");
				tr.appendValue(td.toString());
				break;
			case 3:
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", "&nbsp;");
				td.setAttribute("colspan", "15");
				tr.appendValue(td.toString());
				HTMLElement buttonDP = new HTMLElement("input", "listbutton", "");
				buttonDP.setAttribute("type", "button");
				buttonDP.setAttribute("onClick", "tbClick('detailP')");
				buttonDP.setAttribute("title", "Update Detailed Pre-Check");
				buttonDP.setAttribute("value", "D");
				HTMLElement buttonDF = new HTMLElement("input", "listbutton", "");
				buttonDF.setAttribute("type", "button");
				buttonDF.setAttribute("onClick", "tbClick('finalP')");
				buttonDF.setAttribute("title", "Update Final Pre-Check");
				buttonDF.setAttribute("value", "F");
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						buttonDP.toString() + "&nbsp;" + buttonDF.toString());
				td.setAttribute("colspan", "3");
				td.setAttribute("align", "right");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", "&nbsp;");
				tr.appendValue(td.toString());
				break;
			case 4:
				td = new HTMLElement("td", oddRow?"grid1tbb":"grid2tbb", "Implementation Status");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						getSelectHTMLWithInitialValue("ImplementationStatus", "select", "filter", implementationStatus));
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1tbb":"grid2tbb", "Abort Type");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						getSelectHTMLWithInitialValue("AbortType", "select", "filter", abortType));
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						"&nbsp;");
				td.setAttribute("colspan", "3");
				tr.appendValue(td.toString());
				HTMLElement buttonCC = new HTMLElement("input", "listbutton", "");
				buttonCC.setAttribute("type", "button");
				buttonCC.setAttribute("onClick", "showHideCompleteNR('show')");
				buttonCC.setAttribute("title", "Complete NR");
				buttonCC.setAttribute("value", "C");
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						buttonCC.toString());
				td.setAttribute("colspan", "3");
				td.setAttribute("align", "right");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1bb":"grid2bb", 
						"&nbsp;");
				tr.appendValue(td.toString());
				break;
			}
			html.append(tr.toString());
		}
		for (int i = 5; i < al.size(); i++) {
			oddRow = !oddRow;
			SNRTechnology thisT = al.get(i);
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td = new HTMLElement("td", oddRow?"grid1t":"grid2t", 
					thisT.getTechnologyNameDisplay());
			td.setAttribute("colspan", "3");
			tr.appendValue(td.toString());
			td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					getTechnologySelectHTML(thisT.getTechnologyColumn(), "select", "filter", thisT.getImplemented()));
			td.setAttribute("colspan", "2");
			tr.appendValue(td.toString());
			td = new HTMLElement("td", oddRow?"grid1br":"grid2br", "&nbsp;");
			tr.appendValue(td.toString());
			td = new HTMLElement("td", oddRow?"grid1":"grid2", "&nbsp;");
			td.setAttribute("colspan", "18");
			tr.appendValue(td.toString());
			td = new HTMLElement("td", oddRow?"grid1":"grid2", "&nbsp;");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	private String getSNRDetailHTML(SNR snrDetail) {
		StringBuilder html = new StringBuilder();
		if (snrDetail == null) {
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td = new HTMLElement("td", "grid1t", 
					"No SNR details retrieved");
			td.setAttribute("colspan", "6");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		} else {
			String[] titleArray = snrDetail.getTitleArray();
			String[] valueArray = snrDetail.getValueArray();
			boolean oddRow = false;
			//int row = 1;
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td = new HTMLElement("td", "grid2t", 
					titleArray[0]);
			tr.appendValue(td.toString());
			td = new HTMLElement("td", "grid2i", valueArray[0]);
			td.setAttribute("colspan", "5");
			tr.appendValue(td.toString());
			html.append(tr.toString());
			for (int i = 2; i < (titleArray.length/*-2*/); i+=3) {
				tr = new HTMLElement("tr");
				//row++;
				oddRow = !oddRow;
				for (int j = i; j < (i+3); j++) {
					td = new HTMLElement("td", oddRow?"grid1t":"grid2t", 
							titleArray[j]);
					tr.appendValue(td.toString());
					td = new HTMLElement("td", oddRow?"grid1i":"grid2i", 
							valueArray[j]);
					tr.appendValue(td.toString());
				}
				html.append(tr.toString());
			}
			oddRow = !oddRow;
			HTMLElement tre = new HTMLElement("tr");
			HTMLElement tde = new HTMLElement("td", oddRow?"grid1t":"grid2t", 
					titleArray[1]);
			tre.appendValue(tde.toString());
			tde = new HTMLElement("td", oddRow?"grid1i":"grid2i", valueArray[1]);
			tde.setAttribute("colspan", "4");
			tre.appendValue(tde.toString());
			html.append(tre.toString());
		}
		return html.toString();
	}
	
	public String getSNRDetailHTML(long snrId, boolean history, long historyDT) {
		return getSNRDetailHTML(getSNRDetail(snrId, history, historyDT));
	}
	
	public SiteConfiguration getSiteConfiguration(long customerId, String site) {
		SiteConfiguration siteConfig = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSiteConfiguration(?, ?)}");
	    	cstmt.setLong(1, customerId);
	    	cstmt.setString(2, site);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					siteConfig = new SiteConfiguration(rs.getLong(1),
						rs.getString(2), rs.getString(3),
						rs.getString(4),rs.getString(5),rs.getString(6),
						rs.getString(7),rs.getString(8),rs.getString(9),
						rs.getLong(10),rs.getLong(11),rs.getLong(12),
						rs.getLong(13),rs.getLong(14),rs.getLong(15),
						rs.getLong(16),rs.getLong(17),rs.getLong(18),
						rs.getLong(19),rs.getLong(20),rs.getLong(21),
						rs.getString(22),rs.getString(23),rs.getString(24),
						rs.getString(25),rs.getDate(26),rs.getString(27),
						rs.getString(28));
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
	    
	    return siteConfig;
	}
		
	private String getSiteConfigurationHTML(SiteConfiguration siteConfig, boolean update) {
		StringBuilder html = new StringBuilder();
		if (siteConfig == null) {
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td = new HTMLElement("td", "grid1t", 
					"No site configuration details retrieved");
			td.setAttribute("colspan", "4");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		} else {
			String[] titleArray = siteConfig.getTitleArray();
			String[] valueArray = siteConfig.getValueArray();
			boolean oddRow = false;
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td = new HTMLElement("td", "grid2t", 
					titleArray[0]);
			tr.appendValue(td.toString());
			td = new HTMLElement("td", "grid2i", valueArray[0]);
			td.setAttribute("colspan", "5");
			tr.appendValue(td.toString());
			html.append(tr.toString());
			for (int i = 1; i < (titleArray.length/*-2*/); i+=2) {
				tr = new HTMLElement("tr");
				oddRow = !oddRow;
				for (int j = i; j < (i+2); j++) {
					td = new HTMLElement("td", oddRow?"grid1t":"grid2t", 
							titleArray[j]);
					tr.appendValue(td.toString());
					td = new HTMLElement("td", oddRow?"grid1i":"grid2i", 
							valueArray[j]);
					tr.appendValue(td.toString());
				}
				html.append(tr.toString());
			}
			oddRow = !oddRow;
			tr = new HTMLElement("tr");
			td = new HTMLElement("td", oddRow?"grid1t":"grid2t", 
					siteConfig.getSiteNameTitle());
			tr.appendValue(td.toString());
			if (update) {
				HTMLElement input = new HTMLElement("input", "siteName",
						"siteName", "text", siteConfig.getSiteName(), "");
				input.setAttribute("style", "width:98%");
				input.setAttribute("type", "text");
				td = new HTMLElement("td", oddRow?"grid1i":"grid2i", 
						input.toString());
			} else {
				td = new HTMLElement("td", oddRow?"grid1i":"grid2i", 
						siteConfig.getSiteName());
			}
			tr.appendValue(td.toString());
			td = new HTMLElement("td", oddRow?"grid1t":"grid2t", 
					"&nbsp;");
			tr.appendValue(td.toString());
			td = new HTMLElement("td", oddRow?"grid1i":"grid2i", 
					"&nbsp;");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public String getSiteConfigurationHTML(long customerId, String site, boolean update) {
		return getSiteConfigurationHTML(getSiteConfiguration(customerId, site), update);
	}
	
	public Collection<UserAdminListItem> getUserAdministrationList() {
		ArrayList<UserAdminListItem> uaList = new ArrayList<UserAdminListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetUserAdministrationList()}");
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					uaList.add(new UserAdminListItem(rs.getLong(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getDate(7),rs.getString(8),
						rs.getString(9),rs.getString(10),rs.getString(11),
						rs.getString(12),rs.getString(13),rs.getString(14),
						rs.getString(15),rs.getString(16),rs.getString(17),
						rs.getDate(18),rs.getString(19),rs.getString(20),
						rs.getString(21)));
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
	    
	    return uaList;
	}
	
	public String getUserAdministrationListHTML(long selectedUserId) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<UserAdminListItem> uaList = getUserAdministrationList();
		for (Iterator<UserAdminListItem> it = uaList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			row++;
			oddRow = !oddRow;
			UserAdminListItem uali = it.next();
			String[] values = uali.getListValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);
				tr.appendValue(td.toString());
			}
			HTMLElement input = new HTMLElement("input", "userId"+row, "usrId",
				"radio", uali.getUserIdString(), 
				"userSelect('"+uali.getUserIdString()+"','"+uali.getStatus()+"','"+
				uali.getUsername()+"','"+uali.getUserType()+"','"+uali.getEmail()+"','"+uali.getContactNo()+"')");
			if (uali.getUserId() == selectedUserId) {
				input.setChecked(true);
			}
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
				input.toString());
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	private void addSNRTechnologyToRow(SNRTechnology snrT, HTMLElement tr,
			boolean oddRow, HttpServletRequest req) {
		HTMLElement td = new HTMLElement("td", oddRow?"grid1t":"grid2t", 
				snrT.getTechnologyName());
		tr.appendValue(td.toString());
    	Select select = new Select("select" + snrT.getSelectName(),  "filter");
		Option option = null;
		if (req.getAttribute("select" + snrT.getSelectName()) == null) {
			if (!snrT.hasValue()) {
				option = new Option("", "", true);
				select.appendValue(option.toString());
			}
			option = new Option("Y", "Y", snrT.isYes());
			select.appendValue(option.toString());
			option = new Option("N", "N", snrT.isNo());
			select.appendValue(option.toString());
		} else {
			String enteredValue = (String)req.getAttribute("select" + snrT.getSelectName());
			if (enteredValue.isEmpty()) {
				option = new Option("", "", true);
				select.appendValue(option.toString());
			}
			option = new Option("Y", "Y", enteredValue.equalsIgnoreCase("Y"));
			select.appendValue(option.toString());
			option = new Option("N", "N", enteredValue.equalsIgnoreCase("N"));
			select.appendValue(option.toString());
		}
		td = new HTMLElement("td", oddRow?"grid1":"grid2");
		td.appendValue(select.toString());
		tr.appendValue(td.toString());
	}
	
	public String getSNRTechnologyListHTML(long snrId, HttpServletRequest req, boolean oddRowIn) {
		HttpSession session = req.getSession();
		boolean oddRow = oddRowIn;
		StringBuilder html = new StringBuilder();
		ArrayList<SNRTechnology> snrTList = getSNRTechnologyList(snrId);
		session.setAttribute(ServletConstants.SNR_TECHNOLOGY_COLLECTION_NAME_IN_SESSION, snrTList);
		for (int i = 0; i < snrTList.size(); i++) {
			HTMLElement tr = new HTMLElement("tr");
			oddRow = !oddRow;
			addSNRTechnologyToRow(snrTList.get(i), tr, oddRow, req);
			i++;
			HTMLElement td = null;
			if (i < snrTList.size()) {
				addSNRTechnologyToRow(snrTList.get(i), tr, oddRow, req);
			} else {
				td = new HTMLElement("td", oddRow?"grid1t":"grid2t");
				tr.appendValue(td.toString());
				td = new HTMLElement("td", oddRow?"grid1":"grid2");
				tr.appendValue(td.toString());
			}
			td = new HTMLElement("td", oddRow?"grid1":"grid2");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public ArrayList<SNRTechnology> getSNRTechnologyList(long snrId) {
		message = null;
		ArrayList<SNRTechnology> snrTechList = new ArrayList<SNRTechnology>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSNRTechnologyList(?)}");
	    	cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrTechList.add(new SNRTechnology(snrId, rs.getLong(1), 
						rs.getString(2), rs.getString(3)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getSNRTechnologyList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return snrTechList;
	}

	public Collection<JobType> getJobTypeList() {
		message = null;
		ArrayList<JobType> jobTypeList = new ArrayList<JobType>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetJobTypes()}");
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					jobTypeList.add(new JobType(rs.getString(1), rs.getString(2), 
						rs.getDate(3), rs.getString(4), rs.getString(5), rs.getString(6),
						rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getJobTypeList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return jobTypeList;
	}
	
	public String getJobTypeListHTML(String selectedJobType) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<JobType> jtList = getJobTypeList();
		for (Iterator<JobType> it = jtList.iterator(); it.hasNext(); ) {
			HTMLElement trT = new HTMLElement("tr");
			HTMLElement trB = new HTMLElement("tr");
			row++;
			oddRow = !oddRow;
			JobType jtli = it.next();
			// job type
			HTMLElement tdJT = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getJobType());
			tdJT.setAttribute("rowspan", "2");
			trT.appendValue(tdJT.toString());
			// project requestor
			HTMLElement tdPR = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getProjectRequestor());
			trT.appendValue(tdPR.toString());
			// project requestor email
			HTMLElement tdPRE = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getProjectRequestorEmail());
			trB.appendValue(tdPRE.toString());
			// project manager
			HTMLElement tdPM = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getProjectManager());
			trT.appendValue(tdPM.toString());
			// project requestor email
			HTMLElement tdPME = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getProjectManagerEmail());
			trB.appendValue(tdPME.toString());
			// acting customer
			HTMLElement tdAC = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getActingCustomer());
			tdAC.setAttribute("rowspan", "2");
			trT.appendValue(tdAC.toString());
			// last updated by
			HTMLElement tdLUB = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getLastUpdatedBy());
			trT.appendValue(tdLUB.toString());
			// last updated date
			HTMLElement tdLUD = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getLastUpdatedDateString());
			trB.appendValue(tdLUD.toString());
			// redundant
			HTMLElement tdR = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getRedundant());
			tdR.setAttribute("rowspan", "2");
			trT.appendValue(tdR.toString());
			// bypassCompletionReport
			HTMLElement tdB = new HTMLElement("td", oddRow?"grid1":"grid2",jtli.getBypassCompletionReport());
			tdB.setAttribute("rowspan", "2");
			trT.appendValue(tdB.toString());
			// selected
			HTMLElement input = new HTMLElement("input", "jobType"+row, "jT",
					"radio", jtli.getJobType(), 
					"jobTypeSelect('"+jtli.getJobType()+"','"+
							jtli.getProjectRequestor()+"','"+
							jtli.getProjectRequestorEmail()+"','"+
							jtli.getProjectManager()+"','"+
							jtli.getProjectManagerEmail()+"','"+
							jtli.getActingCustomer()+"','"+
							jtli.getRedundant()+"','"+
							jtli.getBypassCompletionReport()+"')");
			HTMLElement tdS = new HTMLElement("td", oddRow?"grid1":"grid2",input.toString());
			tdS.setAttribute("rowspan", "2");
			trT.appendValue(tdS.toString());
			// add both rows to HTML
			html.append(trT.toString());
			html.append(trB.toString());
		}
		return html.toString();
	}

	public Collection<PreCheckOutstanding> getPreCheckOutstandingList(HttpSession session, 
			long snrId, String filterNRId, String filterJobType) {
		message = null;
		ArrayList<PreCheckOutstanding> preCheckList = new ArrayList<PreCheckOutstanding>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetPrechecksOutstanding(?,?,?)}");
	    	cstmt.setLong(1, snrId);
	    	cstmt.setString(2, filterNRId);
	    	cstmt.setString(3, filterJobType);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					preCheckList.add(new PreCheckOutstanding(rs.getLong(1), rs.getLong(2), 
						rs.getString(3), rs.getString(4), rs.getLong(5), rs.getDate(6),
						rs.getString(7), rs.getString(8), rs.getDate(9), 
						rs.getString(10), rs.getString(11), rs.getString(12)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getPreCheckOutstandingList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    session.setAttribute(ServletConstants.PRECHECK_COLLECTION_NAME_IN_SESSION, preCheckList);
	    return preCheckList;
	}
	
	public String getPreCheckOutstandingListHTML(HttpSession session, long snrId, 
			long selectedPreCheckId, String filterNRId, String filterJobType) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<PreCheckOutstanding> pcList = getPreCheckOutstandingList(session, 
				snrId, filterNRId, filterJobType);
		for (Iterator<PreCheckOutstanding> it = pcList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			row++;
			oddRow = !oddRow;
			PreCheckOutstanding pcli = it.next();
			String[] values = pcli.getListValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", ((oddRow?"grid1":"grid2")+
					(pcli.isOverdue()?"r":"")), values[i]);
				tr.appendValue(td.toString());
			}
			HTMLElement input = new HTMLElement("input", "preCheck"+row, "preCheckId",
				"radio", Long.toString(pcli.getPreCheckId()), 
				"preCheckSelect('"+Long.toString(pcli.getPreCheckId())+"','"+
						Long.toString(pcli.getSNRId())+"','"+pcli.getSite()
						+"','"+pcli.getNRId()+"')");
			if (pcli.getPreCheckId() == selectedPreCheckId) {
				input.setChecked(true);
			}
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
				input.toString());
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}

	public Collection<PreCheckOutstanding> getPreCheckBatchList(long snrId) {
		message = null;
		ArrayList<PreCheckOutstanding> preCheckList = new ArrayList<PreCheckOutstanding>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetPrechecksForBatch(?)}");
	    	cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					preCheckList.add(new PreCheckOutstanding(rs.getLong(1), rs.getLong(2), 
						rs.getString(3), rs.getString(4), rs.getLong(5), rs.getDate(6),
						rs.getString(7), rs.getString(8), rs.getDate(9), 
						rs.getString(10), rs.getString(11)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getPreCheckOutstandingList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return preCheckList;
	}
	
	public String getPreCheckBatchListHTML(long snrId) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<PreCheckOutstanding> pcList = getPreCheckBatchList(snrId);
		for (Iterator<PreCheckOutstanding> it = pcList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			row++;
			oddRow = !oddRow;
			PreCheckOutstanding pcli = it.next();
			String[] values = pcli.getBatchListValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", ((oddRow?"grid1":"grid2")+
					(pcli.isOverdue()?"r":"")), values[i]);
				tr.appendValue(td.toString());
			}
			HTMLElement input = new HTMLElement("input", "preCheckBatch"+row, "preCheckBatchId"+row,
				"radio", Long.toString(pcli.getPreCheckId()), 
				"preCheckBatchSelect('preCheckBatch"+row+"')");
			input.setAttribute("class", "multiRadio");
			input.setAttribute("onMouseOver", "multiRadioOMO('preCheckBatch"+row+"')");
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
				input.toString());
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		if (html.length() == 0) {
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td = new HTMLElement("td", "grid1", "No incomplete initial PreChecks found");
			td.setAttribute("colspan", "3");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public PreCheckOutstanding getPreCheckOutstanding(long preCheckId) {
		message = null;
		PreCheckOutstanding pCO = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetPrecheck(?)}");
	    	cstmt.setLong(1, preCheckId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					pCO = new PreCheckOutstanding(rs.getLong(1), rs.getLong(2), 
						rs.getString(3), rs.getString(4), rs.getLong(5), rs.getDate(6),
						rs.getString(7), rs.getString(8), rs.getDate(9), 
						rs.getString(10), rs.getString(11));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getPreCheckOutstanding(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return pCO;
	}
	
	public String getPreCheckOutstandingHTML(long preCheckId) {
		StringBuilder html = new StringBuilder();
		PreCheckOutstanding pCO = getPreCheckOutstanding(preCheckId);
		HTMLElement tr = new HTMLElement("tr");
		String[] values = pCO.getListValueArray();
		for (int i = 0; i < values.length; i ++) {
			HTMLElement td = new HTMLElement("td", ("grid1"+
				(pCO.isOverdue()?"r":"")), values[i]);
			tr.appendValue(td.toString());
		}
		HTMLElement td = new HTMLElement("td", "grid1");
		tr.appendValue(td.toString());
		html.append(tr.toString());
		return html.toString();
	}
	
	public Collection<PreCheckListItem> getPreCheckItemList(long preCheckId) {
		message = null;
		String previousItemName = "";
		ArrayList<PreCheckListItem> preCheckItemList = new ArrayList<PreCheckListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetPrecheckItemList(?)}");
	    	cstmt.setLong(1, preCheckId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					String itemName = rs.getString(2);
					if (!itemName.equals(previousItemName)) {
						PreCheckListItem pcli = new PreCheckListItem(rs.getLong(1), itemName, 
								rs.getString(3), rs.getString(4), rs.getString(5), 
								rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), 
								rs.getDate(10), rs.getDouble(11), rs.getDate(12), rs.getString(13));
						if (pcli.getStorageType().equals(PreCheckListItem.STORAGE_TYPE_STRING)) {
							pcli.addStringValueOption(rs.getString(15));
						} else if (pcli.getStorageType().equals(PreCheckListItem.STORAGE_TYPE_NUMBER)) {
							pcli.addNumberValueOption(rs.getDouble(16));
						} else if (pcli.getStorageType().equals(PreCheckListItem.STORAGE_TYPE_DATE)) {
							pcli.addDateValueOption(rs.getDate(17));
						} else if (pcli.getStorageType().equals(PreCheckListItem.STORAGE_TYPE_YNSTRING)) {
							pcli.addStringValueOption("Y");
							pcli.addStringValueOption("N");
						} else {
							throw new Exception("Unexpected storage type " + pcli.getStorageType() + 
									" for item " + itemName); 
						}
						preCheckItemList.add(pcli);
						previousItemName = itemName;
					} else {
						PreCheckListItem pcli = preCheckItemList.get(preCheckItemList.size()-1);
						if (pcli.hasFixedValues()) {
							if (pcli.getStorageType().equals(PreCheckListItem.STORAGE_TYPE_STRING)) {
								pcli.addStringValueOption(rs.getString(15));
							} else if (pcli.getStorageType().equals(PreCheckListItem.STORAGE_TYPE_NUMBER)) {
								pcli.addNumberValueOption(rs.getDouble(16));
							} else if (pcli.getStorageType().equals(PreCheckListItem.STORAGE_TYPE_DATE)) {
								pcli.addDateValueOption(rs.getDate(17));
							} else {
								throw new Exception("Unexpected storage type " + pcli.getStorageType() + 
										" for item " + itemName); 
							}
						} else {
							throw new Exception("Item " + itemName + " has more than one option but is " +
									"not marked as 'Fixed Values'"); 
						}
					}
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getPreCheckItemList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return preCheckItemList;
	}
	
	@SuppressWarnings("unchecked")
	public String getPreCheckItemListHTML(HttpSession session, long preCheckId) {
		boolean newRow = true;
		HTMLElement tr = null;
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		Collection<PreCheckListItem> pciList = null;
		if (session.getAttribute(ServletConstants.PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION) == null) {
			pciList = getPreCheckItemList(preCheckId);
			session.setAttribute(ServletConstants.PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION, pciList);
		} else {
			pciList = (Collection<PreCheckListItem>)
					session.getAttribute(ServletConstants.PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION);
		}
			
		for (Iterator<PreCheckListItem> it = pciList.iterator(); it.hasNext(); ) {
			if (newRow) {
				tr = new HTMLElement("tr");
				oddRow = !oddRow;
			}	
			PreCheckListItem pcli = it.next();
			HTMLElement td = new HTMLElement("td", (oddRow?"grid1t":"grid2t"), 
					pcli.getItemDescription());
			tr.appendValue(td.toString());
			Select select = null;
			HTMLElement input = null;
			String dateHTML = null;
			if (pcli.hasFixedValues()) {
		    	select = new Select("select" + pcli.getItemName(), "filter");
				Option option = new Option("", "",
						pcli.getStringValue().equals(""));
					select.appendValue(option.toString());
		    	Collection<String> stringValueOptions = pcli.getStringValueOptions();
				for(Iterator<String> itS = stringValueOptions.iterator(); itS.hasNext(); ) {
					String value = itS.next();
					option = new Option(value, value,
						pcli.getStringValue().equals(value));
					select.appendValue(option.toString());
				}
				select.setAttribute("style", "width:97%");
			}
			else {
				if (pcli.getStorageType().equals(PreCheckListItem.STORAGE_TYPE_DATE)) {
					dateHTML = processDateTimeParameter(pcli.getItemName(), null, 
							false,	"width:90%", null);
				} else {
					input = new HTMLElement("input", "select" + pcli.getItemName(), 
							"select" + pcli.getItemName(), "text", pcli.getStringValue(), 
							"");
					input.setAttribute("style", "width:95%");
					int maxLength = pcli.getLength();
					if (maxLength > 0) {
						if (pcli.getDecimalPlaces() > 0) {
							maxLength++;
						}
						input.setAttribute("maxlength", String.valueOf(maxLength));
					}
				}
			}
			HTMLElement td2 = new HTMLElement("td", oddRow?"grid1":"grid2", 
					dateHTML==null?(input==null?select.toString():input.toString()):dateHTML);
				tr.appendValue(td2.toString());
			if (!newRow) {
				HTMLElement td3 = new HTMLElement("td", oddRow?"grid1":"grid2");
				tr.appendValue(td3.toString());
				html.append(tr.toString());
			}
			newRow = !newRow;
		}
		if (!newRow) {
			tr.appendValue(new HTMLElement("td", oddRow?"grid1":"grid2").toString());
			tr.appendValue(new HTMLElement("td", oddRow?"grid1":"grid2").toString());
			tr.appendValue(new HTMLElement("td", oddRow?"grid1":"grid2").toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public Collection<SNRUserRole> getSNRUserRoleList(long snrId, boolean feOnly) {
		ArrayList<SNRUserRole> snrURList = new ArrayList<SNRUserRole>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSNRUserRoles(?,?)}");
	    	cstmt.setLong(1, snrId);
	    	cstmt.setString(2, feOnly?"Field Engineer":"All");
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrURList.add(new SNRUserRole(rs.getLong(1), rs.getLong(2),
						rs.getString(3), rs.getString(4), rs.getLong(5),
						rs.getString(6), rs.getInt(7), rs.getInt(8)));
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
	    
	    return snrURList;
	}
	
	private int feCount = 0;
	
	public int getFECount() {
		return feCount;
	}
	
	private int boCount = 0;
	
	public int getBOCount() {
		return boCount;
	}
	
	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getCurrentDateDisplay() {
		DateFormat dateFormat = new SimpleDateFormat("EEEE d MMMMM yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getSNRUserRoleListHTML(long snrId, long selectedUserId, boolean feOnly) {
		feCount = 0;
		boCount = 0;
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRUserRole> snrURList = getSNRUserRoleList(snrId, feOnly);
		for (Iterator<SNRUserRole> it = snrURList.iterator(); it.hasNext(); ) {
			HTMLElement tr = new HTMLElement("tr");
			row++;
			oddRow = !oddRow;
			SNRUserRole snrUR = it.next();
			String[] values = snrUR.getListValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);
				tr.appendValue(td.toString());
			}
			HTMLElement input = new HTMLElement("input", "userId"+row, "usrId",
				"radio", snrUR.getUserIdString(), 
				"snrUserRoleSelect('"+snrUR.getUserIdString()+"','" +snrUR.getRole()+
				"','" +snrUR.getNo()+"','" +snrUR.getCount()+"')");
			if (snrUR.getUserId() == selectedUserId) {
				input.setChecked(true);
			}
			if (snrUR.getRole().equals(UserRole.ROLE_FIELD_ENGINEER)) {
				feCount++;
			}
			if (snrUR.getRole().equals(UserRole.ROLE_B_O_ENGINEER)) {
				boCount++;
			}
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
				input.toString());
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public String getChangeBOListHTML(String boName) {
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	Select select = new Select("selectChangeBOList",  "filter");
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetChangeBOList(?)}");
   			cstmt.setString(1, boName);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}
	
	public String getAvailableUsersForRoleHTML(long snrId, String role) {
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	Select select = new Select("selectAvailableUsersForRole",  "filter");
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetAvailableUsersForRole(?,?)}");
   			cstmt.setLong(1, snrId);
   			cstmt.setString(2, role);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}
	

public String getAvailableUsersForRoleHTML(String snrId, String role) {
	return getAvailableUsersForRoleHTML(Long.parseLong(snrId), role);
}
	
public String getAvailableUsersForRoleHTML2(long snrId, String role) {
	Connection conn = null;
	CallableStatement cstmt = null;
	Select select = new Select("selectAltAvailableUsersForRole",  "filter");
    try {
    	conn = DriverManager.getConnection(url);
    	cstmt = conn.prepareCall("{call GetAvailableUsersForRole(?,?)}");
			cstmt.setLong(1, snrId);
			cstmt.setString(2, role);
		boolean found = cstmt.execute();
		if (found) {
			ResultSet rs = cstmt.getResultSet();
			while (rs.next()) {
				Option option = new Option(rs.getString(1), rs.getString(2),
					false);
				select.appendValue(option.toString());
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
	return select.toString();
}

public String getAvailableUsersForRoleHTML2(String snrId, String role) {
	return getAvailableUsersForRoleHTML2(Long.parseLong(snrId), role);
}
	
	public Collection<UserRole> getUserRoleList(long userId) {
		ArrayList<UserRole> urList = new ArrayList<UserRole>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetUserRoleList(?)}");
   			cstmt.setLong(1, userId);
			boolean found = cstmt.execute();
			if (found) {
				
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					urList.add(new UserRole(userId, rs.getString(1),
							rs.getString(2).equalsIgnoreCase("checked")));
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
	    return urList;
	}
	
	public String getUserRoleListHTML(HttpSession session, long userId, String userType) {
		StringBuilder html = new StringBuilder();
		Collection<UserRole> urList = getUserRoleList(userId);
		session.setAttribute(ServletConstants.USER_ROLE_COLLECTION_NAME_IN_SESSION, urList);
		for (Iterator<UserRole> it = urList.iterator(); it.hasNext(); ) {
			UserRole uR = it.next();
			HTMLElement div = new HTMLElement("div");
			div.setAttribute("style", "padding-bottom:10px");
			HTMLElement check = new HTMLElement("input");
			check.setAttribute("type", "checkbox");
			check.setAttribute("name", "check" + uR.getRole());
			check.setAttribute("id", "check" + uR.getRole());
			check.setAttribute("value", uR.getRole());
			if ((userType.equals(User.USER_TYPE_THIRD_PARTY) && 
					(!uR.getRole().equals(UserRole.ROLE_FIELD_ENGINEER)))) {
				check.setAttribute("disabled", "disabled");
			}
			check.setChecked(uR.getChecked());
			div.appendValue(check.toString() + " " + uR.getRole());
			html.append(div.toString());
		}
		return html.toString();
	}
	
	public Collection<ReportParameter> getReportParameters(String reportName) {
		ArrayList<ReportParameter> rpList = new ArrayList<ReportParameter>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetReportParameters(?)}");
   			cstmt.setString(1, reportName);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					rpList.add(new ReportParameter(rs.getString(1),
							rs.getInt(2), rs.getString(3)));
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
	    return rpList;
	}
	
	public String getReportParametersHTML(HttpSession session,String reportName) {
		StringBuilder html = new StringBuilder();
		Collection<ReportParameter> rpList = getReportParameters(reportName);
		session.setAttribute(ServletConstants.DOWNLOAD_REPORT_PARAMETERS_IN_SESSION, rpList);
		for (Iterator<ReportParameter> it = rpList.iterator(); it.hasNext(); ) {
			ReportParameter rp = it.next();
			HTMLElement div = new HTMLElement("div");
			div.setValue(rp.getParameterName());
			html.append(div.toString());
			div = new HTMLElement("div");
			div.setAttribute("style", "padding-bottom:10px");
			if (rp.getDatatype().equals(ReportParameter.DATATYPE_SELECT_STRING)) {
				div.setValue(processReportSelectStringParameter(rp.getParameterNameCondensed()));
			} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_SELECT_NUMBER)) {
				div.setValue(processReportSelectStringParameter(rp.getParameterNameCondensed()));
			} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_DATE)) {
				div.setValue(processDateTimeParameter(rp.getParameterNameCondensed(), null, false, "width:245px", null));
			} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_TIMESTAMP)) {
				div.setValue(processDateTimeParameter(rp.getParameterNameCondensed(), null, true, "width:245px", null));
			} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_STRING)) {
				div.setValue(processReportInputStringParameter(rp.getParameterNameCondensed()));
			} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_NUMBER)) {
				div.setValue(processReportInputStringParameter(rp.getParameterNameCondensed()));
			}
			html.append(div.toString());
		}
		
		return html.toString();
	}
	
	private String processDateTimeParameter(String parameterName, String parameterValue, 
			boolean withTime, String style, String onChange) {
		StringBuilder html = new StringBuilder();
		HTMLElement input = new HTMLElement("input", parameterName, parameterName, "text", 
				parameterValue==null?"":parameterValue, "");
		input.setAttribute("style", style);
		if (onChange != null) {
			input.setAttribute("onChange", onChange);
		}
		html.append(input.toString());
		HTMLElement img = new HTMLElement("img");
		img.setAttribute("src", "images/cal.gif");
		img.setAttribute("style", "cursor:pointer");
		img.setAttribute("onclick", "javascript:NewCssCal ('" + parameterName + "','ddMMyyyy','arrow'" +
				(withTime?",true,'24')":")"));
		html.append(img.toString());
		return html.toString();
	}
	
	private String processReportSelectStringParameter(String parameterName) {
		StringBuilder html = new StringBuilder();
		if (parameterName.equalsIgnoreCase("Customer")) {
			String filterCustomer = "All";
			if (user.getUserType().equalsIgnoreCase(User.USER_TYPE_CUSTOMER)) {
				filterCustomer = user.getCustomerNames()[0];
				html.append(filterCustomer);
			}
			html.append(getCustomerFilterHTML("filter", filterCustomer, "filterReport"));
		} else {
			html.append(getSelectHTML(parameterName, "filterReport", "filter", "All"));
		}
		return html.toString();
	}
	
	private String processReportInputStringParameter(String parameterName) {
		HTMLElement input = new HTMLElement("input", parameterName, parameterName, "text", "", "");
		input.setAttribute("style", "width:250px");
		return input.toString();
	}
	
	public String getCancelledSNRListHTML(long selectedSNRId) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRListItem> snrList = getCancelledSNRList();
		if (snrList.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "10");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		} else {
			for (Iterator<SNRListItem> it = snrList.iterator(); it.hasNext(); ) {
				HTMLElement tr = new HTMLElement("tr");
				row++;
				oddRow = !oddRow;
				SNRListItem sli = it.next();
				String[] values = sli.getCancelledValueArray();
				for (int i = 0; i < values.length; i ++) {
					HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2"), 
						values[i]);
					tr.appendValue(td.toString());
				}
				HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
					"radio", Long.toString(sli.getSNRId()), 
					"snrSelect('"+sli.getSNRId()+"')");
				if (sli.getSNRId() == selectedSNRId) {
					input.setChecked(true);
				}
				HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2"), 
					input.toString());
				tr.appendValue(td.toString());
				html.append(tr.toString());
			}
		}
		return html.toString();
	}

	public Collection<SNRListItem> getCancelledSNRList() {
		message = null;
		ArrayList<SNRListItem> snrList = new ArrayList<SNRListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetCancelledSNRList()}");
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrList.add(new SNRListItem(rs.getLong(1), rs.getString(2),
						rs.getString(3), rs.getDate(4), rs.getString(5),
						rs.getDate(6)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getCancelledSNRList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    
	    return snrList;
	}
	
	public Collection<SNRListItem> getDailySiteSchedule(String filterBOEngineer, String currentDate) {
		message = null;
		ArrayList<SNRListItem> snrList = new ArrayList<SNRListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
		Timestamp currentDateTS = null;

	    try {
			if (!StringUtil.hasNoValue(currentDate)) {
				try {
					currentDateTS = Timestamp.valueOf(currentDate.substring(6, 10) + "-" +
							currentDate.substring(3, 5) + "-" +	
							currentDate.substring(0, 2) + " 00:00:00");
				} catch (Exception ex) {
					throw new Exception("Invalid value entered for current date");
				}
			}
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call getDailySiteSchedule(?,?)}");
	    	cstmt.setString(1, filterBOEngineer);
	    	cstmt.setTimestamp(2, currentDateTS);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrList.add(new SNRListItem(rs.getLong(1), rs.getDate(2),
						rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7),rs.getString(8),
						rs.getString(9),rs.getString(10),rs.getString(11),
						rs.getString(12),rs.getString(13), rs.getString(14), 
						rs.getString(15),rs.getInt(16), rs.getString(17),
						rs.getString(18)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getDailySiteSchedule(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return snrList;
	}
	
	public String getDailySiteScheduleHTML(long selectedSNRId, String filterBOEngineer, String currentDate) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRListItem> snrList = getDailySiteSchedule(	filterBOEngineer, currentDate);
		if (snrList.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "16");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			} else {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1","No sites scheduled today");
				td.setAttribute("colspan", "16");
				tr.appendValue(td.toString());
				html.append(tr.toString());	
			}				
		} else {
			for (Iterator<SNRListItem> it = snrList.iterator(); it.hasNext(); ) {
				HTMLElement tr = new HTMLElement("tr");
				row++;
				oddRow = !oddRow;
				SNRListItem sli = it.next();
				String[] values = sli.getDailySiteScheduleArray();
				for (int i = 0; i < values.length; i++) {
					if (!(values[i]==null)) {
						HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2"),
								values[i]);
							if (values[i].equals(sli.DUMMYNR)) {
								td.setAttribute("title", sli.getNRId());
							} else if (values[i].equals(sli.BOE_SHORT)) {
								td.setValue(sli.getBOEngineersShort());
								td.setAttribute("title", sli.getBOEngineers());
							} else if (values[i].equals(sli.TECH_SHORT)) {
								td.setValue(sli.getTechnologiesShort());
								td.setAttribute("title", sli.getTechnologies());
							}
							tr.appendValue(td.toString());
					}
				}
				HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
						"radio", Long.toString(sli.getSNRId()), 
						"snrSelect('"+sli.getSNRId()+"','"+sli.getStatus()+"','"+sli.getSite()+"','"+sli.getNRId()+"')");
				if (sli.getSNRId() == selectedSNRId) {
					input.setChecked(true);
				}
				HTMLElement td2 = new HTMLElement("td", (oddRow?"grid1":"grid2"), 
					input.toString());
				tr.appendValue(td2.toString());
				html.append(tr.toString());
			}
		}
		return html.toString();
	}
		
	public SNRAccessDetail getSNRAccessDetail(long snrId) {
		SNRAccessDetail snrAD = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
	    	conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetAccessDetail(?)}");
	    	cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					snrAD = new SNRAccessDetail(rs.getLong(1),
						rs.getString(2), rs.getString(3),
						rs.getString(4),rs.getString(5),rs.getString(6),
						rs.getString(7),rs.getString(8),rs.getString(9),
						rs.getString(10),rs.getString(11),rs.getString(12),
						rs.getString(13),rs.getDouble(14),rs.getDouble(15),
						rs.getDouble(16),rs.getString(17),rs.getString(18),
						rs.getString(19),rs.getString(20));
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
	    return snrAD;
	}
	
	public Collection<SNRListItem> getOutstandingWorks(String filterBOEngineer) {
		message = null;
		ArrayList<SNRListItem> snrList = new ArrayList<SNRListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
		Timestamp scheduledStartTS = null;
		Timestamp scheduledEndTS = null;

	    try {
			conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call getOutstandingWorks(?)}");
	    	cstmt.setString(1, filterBOEngineer);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrList.add(new SNRListItem(rs.getLong(1), rs.getDate(2),
							rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7),rs.getString(8),
							rs.getString(9),rs.getTimestamp(10),rs.getTimestamp(11),
							rs.getTimestamp(12),rs.getTimestamp(13), rs.getTimestamp(14), 
							rs.getString(15), rs.getString(16), rs.getString(17),
							rs.getString(18),rs.getString(19)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getOutstandingWork(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return snrList;
	}
	
	public String getOutstandingWorksHTML(String filterBOEngineer) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRListItem> snrList = getOutstandingWorks(filterBOEngineer);
		if (snrList.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "18");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			} else {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1","No outstanding work");
				td.setAttribute("colspan", "18");
				tr.appendValue(td.toString());
				html.append(tr.toString());	
			}				
		} else {
			for (Iterator<SNRListItem> it = snrList.iterator(); it.hasNext(); ) {
				HTMLElement tr = new HTMLElement("tr");
				row++;
				oddRow = !oddRow;
				SNRListItem sli = it.next();
				String[] values = sli.getOutstandingWorksArray();
				for (int i = 0; i < values.length; i++) {
					if (!(values[i]==null)) {
						HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2"),
								values[i]);
							if (values[i].equals(sli.DUMMYNR)) {
								td.setAttribute("title", sli.getNRId());
							} else if (values[i].equals(sli.BOE_SHORT)) {
								td.setValue(sli.getBOEngineersShort());
								td.setAttribute("title", sli.getBOEngineers());
							} else if (values[i].equals(sli.TECH_SHORT)) {
								td.setValue(sli.getTechnologiesShort());
								td.setAttribute("title", sli.getTechnologies());
							} else if (values[i].equals(sli.NPC_SHORT)) {
								td.setValue(sli.getNextPrecheckDisplay());
								td.setAttribute("title", sli.getNextPrecheck());
							} else if (values[i].startsWith(sli.EF_DATE_SET)) {
								td.setValue(sli.ASTERISK);
								td.setAttribute("title", sli.getEFClaimDTTitle(values[i]));
							}
							tr.appendValue(td.toString());
					}
				}
				HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
						"radio", Long.toString(sli.getSNRId()), 
						"osWorkSelect('"+sli.getSNRId()+"','"+
										sli.getStatus()+"','"+
										sli.getSite()+"','"+
										sli.getNRId()+"','"+
										sli.getHopStatus()+"','"+
										sli.getSfrStatus()+"','"+
										sli.getEFOs()+"','"+
										sli.getPCOs()+"','"+
										sli.getProgressComplete()+"')");
				HTMLElement td2 = new HTMLElement("td", (oddRow?"grid1":"grid2"), 
					input.toString());
				tr.appendValue(td2.toString());
				html.append(tr.toString());
			}
		}
		return html.toString();
	}
	
	public Collection<SNRListItem> getWeeklySchedule(String filterBOEngineer,  
			int weekNo) {
		message = null;
		ArrayList<SNRListItem> snrList = new ArrayList<SNRListItem>();
    	Connection conn = null;
    	CallableStatement cstmt = null;

	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call getWeeklySchedule(?,?)}");
	    	cstmt.setString(1, filterBOEngineer);
	    	cstmt.setInt(2, weekNo);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					snrList.add(new SNRListItem(rs.getLong(1), rs.getDate(2),
							rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7),rs.getString(8),
							rs.getString(9),rs.getString(10),rs.getString(11),
							rs.getString(12),rs.getString(13), rs.getString(14), 
							rs.getString(15),rs.getInt(16), rs.getString(17),
							rs.getString(18)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getWeeklySchedule(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return snrList;
	}
	
	public String getWeeklyScheduleHTML(String filterBOEngineer,  
			int weekNo) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<SNRListItem> snrList = getWeeklySchedule(filterBOEngineer, weekNo);
		if (snrList.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "16");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			} else {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1","No sites scheduled for this week");
				td.setAttribute("colspan", "16");
				tr.appendValue(td.toString());
				html.append(tr.toString());	
			}				
		} else {
			for (Iterator<SNRListItem> it = snrList.iterator(); it.hasNext(); ) {
				HTMLElement tr = new HTMLElement("tr");
				row++;
				oddRow = !oddRow;
				SNRListItem sli = it.next();
				String[] values = sli.getWeeklyScheduleArray();
				for (int i = 0; i < values.length; i++) {
					if (!(values[i]==null)) {
						HTMLElement td = new HTMLElement("td", (oddRow?"grid1":"grid2"),
								values[i]);
							if (values[i].equals(sli.DUMMYNR)) {
								td.setAttribute("title", sli.getNRId());
							} else if (values[i].equals(sli.BOE_SHORT)) {
								td.setValue(sli.getBOEngineersShort());
								td.setAttribute("title", sli.getBOEngineers());
							} else if (values[i].equals(sli.TECH_SHORT)) {
								td.setValue(sli.getTechnologiesShort());
								td.setAttribute("title", sli.getTechnologies());
							}
							tr.appendValue(td.toString());
					}
				}
				html.append(tr.toString());
			}
		}
		return html.toString();
	}	
	
	public Collection<EmailCopy> getEmailCopy() {
		message = null;
		ArrayList<EmailCopy> emailCopy = new ArrayList<EmailCopy>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetEmailCopy()}");
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					emailCopy.add(new EmailCopy(rs.getDate(1), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7),rs.getString(8)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getEmailCopy(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return emailCopy;
	}
	
	public String getEmailCopyHTML() {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		Collection<EmailCopy> emailCopy = getEmailCopy();
		if (emailCopy.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "4");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			} else {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1","No copy emails to view");
				td.setAttribute("colspan", "4");
				tr.appendValue(td.toString());
				html.append(tr.toString());	
			}				
		} else {
			for (Iterator<EmailCopy> it = emailCopy.iterator(); it.hasNext(); ) {
				oddRow = !oddRow;
				EmailCopy ec = it.next();
				HTMLElement tr1 = new HTMLElement("tr");
				HTMLElement td11 = new HTMLElement("td", (oddRow?"grid1":"grid2"), ec.getProducedOnString());
				tr1.appendValue(td11.toString());
				HTMLElement td12 = new HTMLElement("td", (oddRow?"grid1":"grid2"), ec.getProducedBy());
				tr1.appendValue(td12.toString());
				HTMLElement td13 = new HTMLElement("td", (oddRow?"grid1":"grid2"), ec.getSender());
				tr1.appendValue(td13.toString());
				HTMLElement td14 = new HTMLElement("td", (oddRow?"grid1":"grid2"), ec.getSubject());
				tr1.appendValue(td14.toString());
				html.append(tr1.toString());
				HTMLElement tr2 = new HTMLElement("tr");
				HTMLElement td21 = new HTMLElement("td", (oddRow?"grid1":"grid2"), ec.getToList());
				td21.setAttribute("colspan", "2");
				tr2.appendValue(td21.toString());
				HTMLElement td22 = new HTMLElement("td", (oddRow?"grid1":"grid2"), ec.getCcList());
				tr2.appendValue(td22.toString());
				HTMLElement td23 = new HTMLElement("td", (oddRow?"grid1":"grid2"), ec.getBccList());
				tr2.appendValue(td23.toString());
				html.append(tr2.toString());
				HTMLElement tr3 = new HTMLElement("tr");
				HTMLElement td31 = new HTMLElement("td", (oddRow?"grid1":"grid2"), ec.getMessageBody());
				td31.setAttribute("colspan", "4");
				tr3.appendValue(td31.toString());
				html.append(tr3.toString());
			}
		}
		return html.toString();
	}	
	
	/*public String getDashboardLineData() {
		String lineData = "['Week', 'Scheduled', 'Attempted', 'Completed', "+	
							"'Partial', 'Abort (Cust)', 'Abort (Dvt)'],";
		Connection conn = null;
    	CallableStatement cstmt = null;	    
    	try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetDashboardProjectLineData(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					// get current week values
					String week = rs.getString(1);
					int scheduledTotal = rs.getInt(2);
					int attemptedTotal = rs.getInt(3);
					int completedTotal = rs.getInt(4);
					int partialTotal = rs.getInt(5);
					int abortCustTotal = rs.getInt(6);
					int abortDvtTotal = rs.getInt(7);
					// put current week values into required format
					lineData = lineData +
						"['"+week+"',"+
						String.valueOf(scheduledTotal)+","+
						String.valueOf(attemptedTotal)+","+
						String.valueOf(completedTotal)+","+
						String.valueOf(partialTotal)+","+
						String.valueOf(abortCustTotal)+","+
						String.valueOf(abortDvtTotal)+"],";
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetDashboardProjectLineData(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
    	// remove last comma
    	lineData = lineData.substring(0, lineData.length() - 1 );
		return lineData;
	}*/
	
	/*public String getDashboardPieData() {
		String pieData = "['Outcome', 'No.'],";
		int completedTotal = 0, partialTotal = 0, abortCustTotal = 0, abortDvtTotal = 0;
		Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetDashboardProjectPieData(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					completedTotal = rs.getInt(1);
					partialTotal = rs.getInt(2);
					abortCustTotal = rs.getInt(3);
					abortDvtTotal = rs.getInt(4);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetDashboardProjectPieData(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		pieData = 
			pieData +
			"['Completed', "+
			String.valueOf(completedTotal)+
			"],['Partial', "+
			String.valueOf(partialTotal)+
			"],['Abort (Cust)', "+
			String.valueOf(abortCustTotal)+
			"],['Abort(Dvt)', "+
			String.valueOf(abortDvtTotal)+
			"]";
		return pieData;
	}*/
		
	private DashboardProject getDashboardProject() {
		message = null;
		DashboardProject dP = new DashboardProject ( "", "Sunday",
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 );
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetDashboardProject(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					dP = new DashboardProject(rs.getString(1), rs.getString(2),
							rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),
							rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10),
							rs.getInt(11),rs.getInt(12),rs.getInt(13),rs.getInt(14),
							rs.getInt(15),rs.getInt(16),rs.getInt(17),rs.getInt(18),
							rs.getInt(19),rs.getInt(20),rs.getInt(21),rs.getInt(22),
							rs.getInt(23),rs.getInt(24),rs.getInt(25),rs.getInt(26),
							rs.getInt(27),rs.getInt(28),rs.getInt(29),rs.getInt(30),
							rs.getInt(31),rs.getInt(32),rs.getInt(33),rs.getInt(34),
							rs.getInt(35),rs.getInt(36),rs.getInt(37),rs.getInt(38),
							rs.getInt(39),rs.getInt(40),rs.getInt(41),rs.getInt(42),
							rs.getInt(43),rs.getInt(44),rs.getInt(45),rs.getInt(46),
							rs.getInt(47),rs.getInt(48),rs.getInt(49),rs.getInt(50),
							rs.getInt(51),rs.getInt(52),rs.getInt(53),rs.getInt(54),
							rs.getInt(55),rs.getInt(56),rs.getInt(57),rs.getInt(58),
							rs.getInt(59),rs.getInt(60),rs.getInt(61),rs.getInt(62));
				} 
			}
	    } catch (Exception ex) {
	    	message = "Error in getDashboardProject(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return dP;
	}
	
	public String getDashboardTableHTML() {
		StringBuilder html = new StringBuilder();
		DashboardProject dp = getDashboardProject();
		String currentDay = dp.getCurrentDay();
		// title bar
		HTMLElement tra = new HTMLElement("tr");
		HTMLElement tda0 = new HTMLElement("td", "dashHeadTitle", 
				getSelectHTMLWithInitialValue(
					"ProjectLD", "select", "filter", dp.getProject(), "navigationAction('go')", user.getFullname()));
		tda0.setAttribute("colspan", "4");
		tda0.setAttribute("onClick", "navigationAction('go')");
		tra.appendValue(tda0.toString());
		html.append(tra.toString());;
		// header row - part 1
		HTMLElement trc = new HTMLElement("tr");
		HTMLElement tdc0 = new HTMLElement("td", "dashColHeadCenterBoxTLH", "Week/Day");
		trc.appendValue(tdc0.toString());
		HTMLElement tdc1 = new HTMLElement("td", "dashColHeadCenter", "Scheduled");
		trc.appendValue(tdc1.toString());
		HTMLElement tdc2 = new HTMLElement("td", "dashColHeadCenter", "Attempted");
		trc.appendValue(tdc2.toString());
		HTMLElement tdc3 = new HTMLElement("td", "dashColHeadCenter", "Completed");
		trc.appendValue(tdc3.toString());		
		HTMLElement tdc4 = new HTMLElement("td", "dashColHeadCenter", "Partial");
		trc.appendValue(tdc4.toString());		
		HTMLElement tdc5 = new HTMLElement("td", "dashColHeadCenter", "Abort (Cust)");
		trc.appendValue(tdc5.toString());		
		HTMLElement tdc6 = new HTMLElement("td", "dashColHeadCenterBoxTRH", "Abort (Dvt)");
		trc.appendValue(tdc6.toString());
		html.append(trc.toString());
		// Week -2 line
		HTMLElement trd = new HTMLElement("tr");
		HTMLElement tdd0 = new HTMLElement("td", "dashColHeadCenterLeft", "Week (-2)");
		trd.appendValue(tdd0.toString());
		HTMLElement tdd1 = new HTMLElement("td", "dashCell", dp.getScheduledWeek2()); 
		trd.appendValue(tdd1.toString());
		HTMLElement tdd2 = new HTMLElement("td", "dashCell", dp.getAttemptedWeek2()); 
		trd.appendValue(tdd2.toString());
		HTMLElement tdd3 = new HTMLElement("td", "dashCell", dp.getCompletedWeek2()); 
		trd.appendValue(tdd3.toString());
		HTMLElement tdd4 = new HTMLElement("td", "dashCell", dp.getPartialWeek2()); 
		trd.appendValue(tdd4.toString());
		HTMLElement tdd5 = new HTMLElement("td", "dashCell", dp.getAbortCustWeek2()); 
		trd.appendValue(tdd5.toString());
		HTMLElement tdd6 = new HTMLElement("td", "dashCellBoxRight", dp.getAbortDvtWeek2()); 
		trd.appendValue(tdd6.toString());
		html.append(trd.toString());
		// week -1 line
		HTMLElement tre = new HTMLElement("tr");
		HTMLElement tde0 = new HTMLElement("td", "dashColHeadCenterDBot", "Week (-1)");
		tre.appendValue(tde0.toString());
		HTMLElement tde1 = new HTMLElement("td", "dashCellDBot", dp.getScheduledWeek1()); 
		tre.appendValue(tde1.toString());
		HTMLElement tde2 = new HTMLElement("td", "dashCellDBot", dp.getAttemptedWeek1()); 
		tre.appendValue(tde2.toString());
		HTMLElement tde3 = new HTMLElement("td", "dashCellDBot", dp.getCompletedWeek1()); 
		tre.appendValue(tde3.toString());
		HTMLElement tde4 = new HTMLElement("td", "dashCellDBot", dp.getPartialWeek1()); 
		tre.appendValue(tde4.toString());
		HTMLElement tde5 = new HTMLElement("td", "dashCellDBot", dp.getAbortCustWeek1()); 
		tre.appendValue(tde5.toString());
		HTMLElement tde6 = new HTMLElement("td", "dashCellDBotRight", dp.getAbortDvtWeek1()); 
		tre.appendValue(tde6.toString());
		html.append(tre.toString());
		// Monday line
		HTMLElement trf = new HTMLElement("tr");
		HTMLElement tdf0 = new HTMLElement("td", "dashColHeadCenterDTop", "Monday");
		trf.appendValue(tdf0.toString());
		HTMLElement tdf1 = new HTMLElement("td", 
								currentDay.equals("Monday")?"dashCellDTopFill":"dashCellDTop", 
								dp.getScheduledMon()); 
		trf.appendValue(tdf1.toString());
		HTMLElement tdf2 = new HTMLElement("td", 
								currentDay.equals("Monday")?"dashCellDTopFill":"dashCellDTop", 
								dp.getAttemptedMon()); 
		trf.appendValue(tdf2.toString());
		HTMLElement tdf3 = new HTMLElement("td", 
								currentDay.equals("Monday")?"dashCellDTopFill":"dashCellDTop", 
								dp.getCompletedMon()); 
		trf.appendValue(tdf3.toString());
		HTMLElement tdf4 = new HTMLElement("td", 
								currentDay.equals("Monday")?"dashCellDTopFill":"dashCellDTop", 
								dp.getPartialMon()); 
		trf.appendValue(tdf4.toString());
		HTMLElement tdf5 = new HTMLElement("td", 
								currentDay.equals("Monday")?"dashCellDTopFill":"dashCellDTop", 
								dp.getAbortCustMon()); 
		trf.appendValue(tdf5.toString());
		HTMLElement tdf6 = new HTMLElement("td", 
								currentDay.equals("Monday")?"dashCellDTopRightFill":"dashCellDTopRight", 
								dp.getAbortDvtMon()); 
		trf.appendValue(tdf6.toString());
		html.append(trf.toString());
		// Tuesday line
		HTMLElement trg = new HTMLElement("tr");
		HTMLElement tdg0 = new HTMLElement("td","dashColHeadCenterLeft", "Tuesday");
		trg.appendValue(tdg0.toString());
		HTMLElement tdg1 = new HTMLElement("td", 
								currentDay.equals("Tuesday")?"dashCellFill":"dashCell", 
								dp.getScheduledTue()); 
		trg.appendValue(tdg1.toString());
		HTMLElement tdg2 = new HTMLElement("td", 
								currentDay.equals("Tuesday")?"dashCellFill":"dashCell", 
								dp.getAttemptedTue()); 
		trg.appendValue(tdg2.toString());
		HTMLElement tdg3 = new HTMLElement("td", 
								currentDay.equals("Tuesday")?"dashCellFill":"dashCell", 
								dp.getCompletedTue()); 
		trg.appendValue(tdg3.toString());
		HTMLElement tdg4 = new HTMLElement("td", 
								currentDay.equals("Tuesday")?"dashCellFill":"dashCell", 
								dp.getPartialTue()); 
		trg.appendValue(tdg4.toString());
		HTMLElement tdg5 = new HTMLElement("td", 
								currentDay.equals("Tuesday")?"dashCellFill":"dashCell", 
								dp.getAbortCustTue()); 
		trg.appendValue(tdg5.toString());
		HTMLElement tdg6 = new HTMLElement("td", 
								currentDay.equals("Tuesday")?"dashCellBoxRightFill":"dashCellBoxRight", 
								dp.getAbortDvtTue()); 
		trg.appendValue(tdg6.toString());
		html.append(trg.toString());	
		// Wednesday line
		HTMLElement trh = new HTMLElement("tr");
		HTMLElement tdh0 = new HTMLElement("td", "dashColHeadCenterLeft", "Wednesday");
		trh.appendValue(tdh0.toString());
		HTMLElement tdh1 = new HTMLElement("td", 
								currentDay.equals("Wednesday")?"dashCellFill":"dashCell", 
								dp.getScheduledWed()); 
		trh.appendValue(tdh1.toString());
		HTMLElement tdh2 = new HTMLElement("td", 
								currentDay.equals("Wednesday")?"dashCellFill":"dashCell", 
								dp.getAttemptedWed()); 
		trh.appendValue(tdh2.toString());
		HTMLElement tdh3 = new HTMLElement("td", 
								currentDay.equals("Wednesday")?"dashCellFill":"dashCell", 
								dp.getCompletedWed()); 
		trh.appendValue(tdh3.toString());
		HTMLElement tdh4 = new HTMLElement("td", 
								currentDay.equals("Wednesday")?"dashCellFill":"dashCell", 
								dp.getPartialWed()); 
		trh.appendValue(tdh4.toString());
		HTMLElement tdh5 = new HTMLElement("td", 
								currentDay.equals("Wednesday")?"dashCellFill":"dashCell", 
								dp.getAbortCustWed()); 
		trh.appendValue(tdh5.toString());
		HTMLElement tdh6 = new HTMLElement("td", 
								currentDay.equals("Wednesday")?"dashCellBoxRightFill":"dashCellBoxRight", 
								dp.getAbortDvtWed()); 
		trh.appendValue(tdh6.toString());
		html.append(trh.toString());		
		// Thursday line
		HTMLElement tri = new HTMLElement("tr");
		HTMLElement tdi0 = new HTMLElement("td", "dashColHeadCenterLeft", "Thursday");
		tri.appendValue(tdi0.toString());
		HTMLElement tdi1 = new HTMLElement("td", 
								currentDay.equals("Thursday")?"dashCellFill":"dashCell", 
								dp.getScheduledThu()); 
		tri.appendValue(tdi1.toString());
		HTMLElement tdi2 = new HTMLElement("td", 
								currentDay.equals("Thursday")?"dashCellFill":"dashCell",
								dp.getAttemptedThu()); 
		tri.appendValue(tdi2.toString());
		HTMLElement tdi3 = new HTMLElement("td", 
								currentDay.equals("Thursday")?"dashCellFill":"dashCell", 
								dp.getCompletedThu()); 
		tri.appendValue(tdi3.toString());
		HTMLElement tdi4 = new HTMLElement("td", 
								currentDay.equals("Thursday")?"dashCellFill":"dashCell", 
								dp.getPartialThu()); 
		tri.appendValue(tdi4.toString());
		HTMLElement tdi5 = new HTMLElement("td", 
								currentDay.equals("Thursday")?"dashCellFill":"dashCell", 
								dp.getAbortCustThu()); 
		tri.appendValue(tdi5.toString());
		HTMLElement tdi6 = new HTMLElement("td", 
								currentDay.equals("Thursday")?"dashCellBoxRightFill":"dashCellBoxRight", 
								dp.getAbortDvtThu()); 
		tri.appendValue(tdi6.toString());
		html.append(tri.toString());			
		// Friday line
		HTMLElement trj = new HTMLElement("tr");
		HTMLElement tdj0 = new HTMLElement("td", "dashColHeadCenterLeft", "Friday");
		trj.appendValue(tdj0.toString());
		HTMLElement tdj1 = new HTMLElement("td", 
								currentDay.equals("Friday")?"dashCellFill":"dashCell", 
								dp.getScheduledFri()); 
		trj.appendValue(tdj1.toString());
		HTMLElement tdj2 = new HTMLElement("td", 
								currentDay.equals("Friday")?"dashCellFill":"dashCell", 
								dp.getAttemptedFri()); 
		trj.appendValue(tdj2.toString());
		HTMLElement tdj3 = new HTMLElement("td", 
								currentDay.equals("Friday")?"dashCellFill":"dashCell", 
								dp.getCompletedFri()); 
		trj.appendValue(tdj3.toString());
		HTMLElement tdj4 = new HTMLElement("td", 
								currentDay.equals("Friday")?"dashCellFill":"dashCell", 
								dp.getPartialFri()); 
		trj.appendValue(tdj4.toString());
		HTMLElement tdj5 = new HTMLElement("td", 
								currentDay.equals("Friday")?"dashCellFill":"dashCell", 
								dp.getAbortCustFri()); 
		trj.appendValue(tdj5.toString());
		HTMLElement tdj6 = new HTMLElement("td", 
								currentDay.equals("Friday")?"dashCellBoxRightFill":"dashCellBoxRight", 
								dp.getAbortDvtFri()); 
		trj.appendValue(tdj6.toString());
		html.append(trj.toString());		
		// Saturday line
		HTMLElement trk = new HTMLElement("tr");
		HTMLElement tdk0 = new HTMLElement("td", "dashColHeadCenterLeft", "Saturday");
		trk.appendValue(tdk0.toString());
		HTMLElement tdk1 = new HTMLElement("td", 
								currentDay.equals("Saturday")?"dashCellFill":"dashCell", 
								dp.getScheduledSat()); 
		trk.appendValue(tdk1.toString());
		HTMLElement tdk2 = new HTMLElement("td", 
								currentDay.equals("Saturday")?"dashCellFill":"dashCell", 
								dp.getAttemptedSat()); 
		trk.appendValue(tdk2.toString());
		HTMLElement tdk3 = new HTMLElement("td", 
								currentDay.equals("Saturday")?"dashCellFill":"dashCell", 
								dp.getCompletedSat()); 
		trk.appendValue(tdk3.toString());
		HTMLElement tdk4 = new HTMLElement("td", 
								currentDay.equals("Saturday")?"dashCellFill":"dashCell", 
								dp.getPartialSat()); 
		trk.appendValue(tdk4.toString());
		HTMLElement tdk5 = new HTMLElement("td", 
								currentDay.equals("Saturday")?"dashCellFill":"dashCell", 
								dp.getAbortCustSat()); 
		trk.appendValue(tdk5.toString());
		HTMLElement tdk6 = new HTMLElement("td", 
								currentDay.equals("Saturday")?"dashCellBoxRightFill":"dashCellBoxRight", 
								dp.getAbortDvtSat()); 
		trk.appendValue(tdk6.toString());
		html.append(trk.toString());		
		// Sunday line
		HTMLElement trl = new HTMLElement("tr");
		HTMLElement tdl0 = new HTMLElement("td","dashColHeadCenterBoxBLH","Sunday");
		trl.appendValue(tdl0.toString());
		HTMLElement tdl1 = new HTMLElement("td", 
								currentDay.equals("Sunday")?"dashCellBoxBotFill":"dashCellBoxBot", 
								dp.getScheduledSun()); 
		trl.appendValue(tdl1.toString());
		HTMLElement tdl2 = new HTMLElement("td", 
								currentDay.equals("Sunday")?"dashCellBoxBotFill":"dashCellBoxBot", 
								dp.getAttemptedSun()); 
		trl.appendValue(tdl2.toString());
		HTMLElement tdl3 = new HTMLElement("td", 
								currentDay.equals("Sunday")?"dashCellBoxBotFill":"dashCellBoxBot", 
								dp.getCompletedSun()); 
		trl.appendValue(tdl3.toString());
		HTMLElement tdl4 = new HTMLElement("td", 
								currentDay.equals("Sunday")?"dashCellBoxBotFill":"dashCellBoxBot", 
								dp.getPartialSun()); 
		trl.appendValue(tdl4.toString());
		HTMLElement tdl5 = new HTMLElement("td", 
								currentDay.equals("Sunday")?"dashCellBoxBotFill":"dashCellBoxBot", 
								dp.getAbortCustSun()); 
		trl.appendValue(tdl5.toString());
		HTMLElement tdl6 = new HTMLElement("td", 
								currentDay.equals("Sunday")?"dashCellBoxBRHFill":"dashCellBoxBRH", 
								dp.getAbortDvtSun()); 
		trl.appendValue(tdl6.toString());
		html.append(trl.toString());
		// empty row	
		HTMLElement trb = new HTMLElement("tr");
		HTMLElement tdb0 = new HTMLElement("td", "dashHeadEmpty", "&nbsp;");
		tdb0.setAttribute("colspan", "7");
		trb.appendValue(tdb0.toString());	
		html.append(trb.toString());
		// scheduled header row - part 1
		HTMLElement trm = new HTMLElement("tr");
		HTMLElement tdm0 = new HTMLElement("td", "dashColHeadCenterBoxTLHAlt", "&nbsp;");
		trm.appendValue(tdm0.toString());
		HTMLElement tdm1 = new HTMLElement("td", "dashColHeadCenterAlt", "Week(+1)");
		trm.appendValue(tdm1.toString());
		HTMLElement tdm2 = new HTMLElement("td", "dashColHeadCenterAlt", "Week(+2)");
		trm.appendValue(tdm2.toString());
		HTMLElement tdm3 = new HTMLElement("td", "dashColHeadCenterAlt", "Week(+3)");
		trm.appendValue(tdm3.toString());		
		HTMLElement tdm4 = new HTMLElement("td", "dashColHeadCenterAlt", "Week(+4)");
		trm.appendValue(tdm4.toString());		
		HTMLElement tdm5 = new HTMLElement("td", "dashColHeadCenterAlt", "Week(+5)");
		trm.appendValue(tdm5.toString());		
		HTMLElement tdm6 = new HTMLElement("td", "dashColHeadCenterBoxTRHAlt", "Week(+6)");
		trm.appendValue(tdm6.toString());
		html.append(trm.toString());
		// scheduled header row - part 2
		HTMLElement trn = new HTMLElement("tr");
		HTMLElement tdn0 = new HTMLElement("td", "dashColHeadCenterBoxBLHAlt", "Scheduled");
		trn.appendValue(tdn0.toString());
		HTMLElement tdn1 = new HTMLElement("td", "dashCellBoxBot", dp.getWeek1Scheduled());
		trn.appendValue(tdn1.toString());
		HTMLElement tdn2 = new HTMLElement("td", "dashCellBoxBot", dp.getWeek2Scheduled());
		trn.appendValue(tdn2.toString());
		HTMLElement tdn3 = new HTMLElement("td", "dashCellBoxBot", dp.getWeek3Scheduled());
		trn.appendValue(tdn3.toString());		
		HTMLElement tdn4 = new HTMLElement("td", "dashCellBoxBot", dp.getWeek4Scheduled());
		trn.appendValue(tdn4.toString());		
		HTMLElement tdn5 = new HTMLElement("td", "dashCellBoxBot", dp.getWeek5Scheduled());
		trn.appendValue(tdn5.toString());		
		HTMLElement tdn6 = new HTMLElement("td", "dashCellBoxBRH", dp.getWeek6Scheduled());
		trn.appendValue(tdn6.toString());
		html.append(trn.toString());
		// empty row
		html.append(trb.toString());
		return html.toString();
	}
	
	public String getDisplayProject() {
		message = null;
		String display = "inline";
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetDisplayProject(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					display = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getDisplayProject(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
		return display;
	}
	
	public SiteProgress getSiteProgress(long snrId) {
		message = null; 
		SiteProgress sp = new SiteProgress( "" );
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call getSiteProgress(?)}");
	    	cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					sp = new SiteProgress(
							rs.getString(1), rs.getString(2),rs.getString(3),rs.getDate(4),
							rs.getString(5), rs.getTimestamp(6), rs.getString(7),rs.getTimestamp(8),
							rs.getString(9), rs.getTimestamp(10),rs.getString(11),rs.getTimestamp(12),
							rs.getString(13), rs.getTimestamp(14), rs.getString(15),rs.getTimestamp(16),
							rs.getString(17), rs.getTimestamp(18),rs.getString(19),rs.getTimestamp(20),
							rs.getString(21), rs.getTimestamp(22), rs.getString(23),rs.getTimestamp(24),
							rs.getString(25), rs.getTimestamp(26),rs.getString(27),rs.getTimestamp(28),
							rs.getString(29), rs.getTimestamp(30), rs.getString(31),rs.getTimestamp(32),
							rs.getString(33), rs.getTimestamp(34),rs.getString(35),rs.getTimestamp(36),
							rs.getString(37), rs.getTimestamp(38), rs.getString(39),rs.getTimestamp(40),
							rs.getString(41), rs.getString(42), rs.getString(43), rs.getString(44));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getSiteProgress(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return sp;
	}
	
	public String getSiteProgressHTML(long snrId) {
		StringBuilder html = new StringBuilder();
		SiteProgress sp = getSiteProgress(snrId);
		if (sp.getSite().equals("")) {
			HTMLElement tr0 = new HTMLElement("tr");
			HTMLElement td1 = new HTMLElement("td", "grid1", "Error getting Site Progress Details");
			td1.setAttribute("colspan", "8");
			td1.setAttribute("align", "center");
			tr0.appendValue(td1.toString());
			html.append(tr0.toString());
		} else {
			HTMLElement tr0 = new HTMLElement("tr");
			HTMLElement td1 = new HTMLElement("td", "grid1", "<b>Site:</b>");
			tr0.appendValue(td1.toString());
			HTMLElement td2 = new HTMLElement("td", "grid1", sp.getSite());
			tr0.appendValue(td2.toString());
			HTMLElement td3 = new HTMLElement("td", "grid1", "<b>NR Id:</b>");
			tr0.appendValue(td3.toString());
			HTMLElement td4 = new HTMLElement("td", "grid1", sp.getNRId());
			tr0.appendValue(td4.toString());
			HTMLElement td5 = new HTMLElement("td", "grid1", "<b>Status:</b>");
			tr0.appendValue(td5.toString());
			HTMLElement td6 = new HTMLElement("td", "grid1", sp.getStatus());
			tr0.appendValue(td6.toString());
			HTMLElement td7 = new HTMLElement("td", "grid1", "<b>Scheduled:</b>");
			tr0.appendValue(td7.toString());
			HTMLElement td8 = new HTMLElement("td", "grid1", sp.getScheduledDateString());
			tr0.appendValue(td8.toString());
			html.append(tr0.toString());
			HTMLElement tra = new HTMLElement("tr");
			HTMLElement tda1 = new HTMLElement("td", "grid2", "&nbsp;");
			tda1.setAttribute("colspan", "8");
			tra.appendValue(tda1.toString());
			html.append(tra.toString());
			HTMLElement trb = new HTMLElement("tr");
			HTMLElement tdb1 = new HTMLElement("td", "grid1BlueBold", "Checked In:");
			trb.appendValue(tdb1.toString());
			HTMLElement tdb2 = new HTMLElement("td", "grid1", 
					getSelectHTMLWithInitialValue("CheckedIn", "select", "filter", sp.getCheckedIn()));
			tdb2.setAttribute("colspan", "2");
			trb.appendValue(tdb2.toString());
			HTMLElement tdb3 = new HTMLElement("td", "grid1", sp.getCheckedInDTString());
			trb.appendValue(tdb3.toString());
			HTMLElement tdb4 = new HTMLElement("td", "grid1OrangeBold", "Tx Provisioning:");
			trb.appendValue(tdb4.toString());
			HTMLElement tdb5 = new HTMLElement("td", "grid1", 
					getSelectHTMLWithInitialValue("TXProvisioning", "select", "filter", sp.getTXProvisioning()));
			tdb5.setAttribute("colspan", "2");
			trb.appendValue(tdb5.toString());
			HTMLElement tdb6 = new HTMLElement("td", "grid1", sp.getTXProvisioningDTString());
			trb.appendValue(tdb6.toString());
			html.append(trb.toString());			
			HTMLElement trc = new HTMLElement("tr");
			HTMLElement tdc1 = new HTMLElement("td", "grid2BlueBold", "Site Booked On:");
			trc.appendValue(tdc1.toString());
			HTMLElement tdc2 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("BookedOn", "select", "filter", sp.getBookedOn()));
			tdc2.setAttribute("colspan", "2");
			trc.appendValue(tdc2.toString());
			HTMLElement tdc3 = new HTMLElement("td", "grid2", sp.getBookedOnDTString());
			trc.appendValue(tdc3.toString());
			HTMLElement tdc4 = new HTMLElement("td", "grid2OrangeBold", "Field Work:");
			trc.appendValue(tdc4.toString());
			HTMLElement tdc5 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("FieldWork", "select", "filter", sp.getFieldWork()));
			tdc5.setAttribute("colspan", "2");
			trc.appendValue(tdc5.toString());
			HTMLElement tdc6 = new HTMLElement("td", "grid2", sp.getFieldWorkDTString());
			trc.appendValue(tdc6.toString());
			html.append(trc.toString());
			HTMLElement trd = new HTMLElement("tr");
			HTMLElement tdd1 = new HTMLElement("td", "grid1BlueBold", "Site Accessed:");
			trd.appendValue(tdd1.toString());
			HTMLElement tdd2 = new HTMLElement("td", "grid1d", 
					getSelectHTMLWithInitialValue("SiteAccessed", "select", "filter", sp.getSiteAccessed()));
			tdd2.setAttribute("colspan", "2");
			trd.appendValue(tdd2.toString());
			HTMLElement tdd3 = new HTMLElement("td", "grid1", sp.getSiteAccessedDTString());
			trd.appendValue(tdd3.toString());
			HTMLElement tdd4 = new HTMLElement("td", "grid1OrangeBold", "Site Unlocked:");
			trd.appendValue(tdd4.toString());
			HTMLElement tdd5 = new HTMLElement("td", "grid1", 
					getSelectHTMLWithInitialValue("SiteUnlocked", "select", "filter", sp.getSiteUnlocked()));
			tdd5.setAttribute("colspan", "2");
			trd.appendValue(tdd5.toString());
			HTMLElement tdd6 = new HTMLElement("td", "grid1", sp.getSiteUnlockedDTString());
			trd.appendValue(tdd6.toString());
			html.append(trd.toString());
			HTMLElement tre = new HTMLElement("tr");
			HTMLElement tde1 = new HTMLElement("td", "grid2BlueBold", "Physical Checks:");
			tre.appendValue(tde1.toString());
			HTMLElement tde2 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("PhysicalChecks", "select", "filter", sp.getPhysicalChecks()));
			tde2.setAttribute("colspan", "2");
			tre.appendValue(tde2.toString());
			HTMLElement tde3 = new HTMLElement("td", "grid2", sp.getPhysicalChecksDTString());
			tre.appendValue(tde3.toString());
			HTMLElement tde4 = new HTMLElement("td", "grid2OrangeBold", "Post Call Test:");
			tre.appendValue(tde4.toString());
			HTMLElement tde5 = new HTMLElement("td", "grid2d", 
					getSelectHTMLWithInitialValue("PostCallTest", "select", "filter", sp.getPostCallTest()));
			tde5.setAttribute("colspan", "2");
			tre.appendValue(tde5.toString());
			HTMLElement tde6 = new HTMLElement("td", "grid2", sp.getPostCallTestDTString());
			tre.appendValue(tde6.toString());
			html.append(tre.toString());
			HTMLElement trf = new HTMLElement("tr");
			HTMLElement tdf1 = new HTMLElement("td", "grid1BlueBold", "Pre Call Test:");
			trf.appendValue(tdf1.toString());
			HTMLElement tdf2 = new HTMLElement("td", "grid1", 
					getSelectHTMLWithInitialValue("PreCallTest", "select", "filter", sp.getPreCallTest()));
			tdf2.setAttribute("colspan", "2");
			trf.appendValue(tdf2.toString());
			HTMLElement tdf3 = new HTMLElement("td", "grid1", sp.getPreCallTestDTString());
			trf.appendValue(tdf3.toString());
			HTMLElement tdf4 = new HTMLElement("td", "grid1", "&nbsp;");
			tdf4.setAttribute("colspan", "4");
			trf.appendValue(tdf4.toString());
			html.append(trf.toString());
			HTMLElement trg = new HTMLElement("tr");
			HTMLElement tdg1 = new HTMLElement("td", "grid2", "&nbsp;");
			tdg1.setAttribute("colspan", "4");
			trg.appendValue(tdg1.toString());
			HTMLElement tdg4 = new HTMLElement("td", "grid2GreenBold", "Closure Code:");
			trg.appendValue(tdg4.toString());
			HTMLElement tdg5 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("ClosureCode", "select", "filter", sp.getClosureCode()));
			tdg5.setAttribute("colspan", "2");
			trg.appendValue(tdg5.toString());
			HTMLElement tdg6 = new HTMLElement("td", "grid2", sp.getClosureCodeDTString());
			trg.appendValue(tdg6.toString());
			html.append(trg.toString());			
			HTMLElement trh = new HTMLElement("tr");
			HTMLElement tdh1 = new HTMLElement("td", "grid1OrangeBold", "Site Locked:");
			trh.appendValue(tdh1.toString());
			HTMLElement tdh2 = new HTMLElement("td", "grid1", 
					getSelectHTMLWithInitialValue("SiteLocked", "select", "filter", sp.getSiteLocked()));
			tdh2.setAttribute("colspan", "2");
			trh.appendValue(tdh2.toString());
			HTMLElement tdh3 = new HTMLElement("td", "grid1", sp.getSiteLockedDTString());
			trh.appendValue(tdh3.toString());
			HTMLElement tdh4 = new HTMLElement("td", "grid1GreenBold", "&nbsp;");
			trh.appendValue(tdh4.toString());
			HTMLElement tdh5 = new HTMLElement("td", "grid1", 
					"<input id=\"currentCRQClosureCode\" "+
					"name=\"currentCRQClosureCode\" "+
					"value=\""+sp.getCrqClosureCode()+"\" "+
					"style=\"width:100%\" "+
					"type=\"text\" class=\"text\"/>");
			tdh5.setAttribute("colspan", "2");
			trh.appendValue(tdh5.toString());
			HTMLElement tdh6 = new HTMLElement("td", "grid1", "&nbsp;");
			trh.appendValue(tdh6.toString());
			html.append(trh.toString());
			HTMLElement tri = new HTMLElement("tr");
			HTMLElement tdi1 = new HTMLElement("td", "grid2OrangeBold", "HW Installed:");
			tri.appendValue(tdi1.toString());
			HTMLElement tdi2 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("HWInstalls", "select", "filter", sp.getHWInstalls()));
			tdi2.setAttribute("colspan", "2");
			tri.appendValue(tdi2.toString());
			HTMLElement tdi3 = new HTMLElement("td", "grid2", sp.getHWInstallsDTString());
			tri.appendValue(tdi3.toString());
			HTMLElement tdi4 = new HTMLElement("td", "grid2GreenBold", "Left Site:");
			tri.appendValue(tdi4.toString());
			HTMLElement tdi5 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("LeaveSite", "select", "filter", sp.getLeaveSite()));
			tdi5.setAttribute("colspan", "2");
			tri.appendValue(tdi5.toString());
			HTMLElement tdi6 = new HTMLElement("td", "grid2", sp.getLeaveSiteDTString());
			tri.appendValue(tdi6.toString());
			html.append(tri.toString());			
			HTMLElement trj = new HTMLElement("tr");
			HTMLElement tdj1 = new HTMLElement("td", "grid1OrangeBold", "Field Commissioning :");
			trj.appendValue(tdj1.toString());
			HTMLElement tdj2 = new HTMLElement("td", "grid1d", 
					getSelectHTMLWithInitialValue("CommissioningFE", "select", "filter", sp.getCommissioningFE()));
			tdj2.setAttribute("colspan", "2");
			trj.appendValue(tdj2.toString());
			HTMLElement tdj3 = new HTMLElement("td", "grid1", sp.getCommissioningFEDTString());
			trj.appendValue(tdj3.toString());
			HTMLElement tdj4 = new HTMLElement("td", "grid1GreenBold", "Booked Off Site:");
			trj.appendValue(tdj4.toString());
			HTMLElement tdj5 = new HTMLElement("td", "grid1", 
					getSelectHTMLWithInitialValue("BookOffSite", "select", "filter", sp.getBookOffSite()));
			tdj5.setAttribute("colspan", "2");
			trj.appendValue(tdj5.toString());
			HTMLElement tdj6 = new HTMLElement("td", "grid1", sp.getBookOffSiteDTString());
			trj.appendValue(tdj6.toString());
			html.append(trj.toString());
			HTMLElement trk = new HTMLElement("tr");
			HTMLElement tdk1 = new HTMLElement("td", "grid2OrangeBold", "BO Commissioning:");
			trk.appendValue(tdk1.toString());
			HTMLElement tdk2 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("CommissioningBO", "select", "filter", sp.getCommissioningBO()));
			tdk2.setAttribute("colspan", "2");
			trk.appendValue(tdk2.toString());
			HTMLElement tdk3 = new HTMLElement("td", "grid2", sp.getCommissioningBODTString());
			trk.appendValue(tdk3.toString());
			HTMLElement tdk4 = new HTMLElement("td", "grid2GreenBold", "Performance Monitoring:");
			trk.appendValue(tdk4.toString());
			HTMLElement tdk5 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("PerformanceMonitoring", "select", "filter", sp.getPerformanceMonitoring()));
			tdk5.setAttribute("colspan", "2");
			trk.appendValue(tdk5.toString());
			HTMLElement tdk6 = new HTMLElement("td", "grid2", sp.getPerformanceMonitoringDTString());
			trk.appendValue(tdk6.toString());
			html.append(trk.toString());			
			HTMLElement trl = new HTMLElement("tr");
			HTMLElement tdl1 = new HTMLElement("td", "grid2", "&nbsp;");
			tdl1.setAttribute("colspan", "4");
			trl.appendValue(tdl1.toString());
			HTMLElement tdl4 = new HTMLElement("td", "grid2GreenBold", "Hand Over Pack:");
			trl.appendValue(tdl4.toString());
			HTMLElement tdl5 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("InitialHOP", "select", "filter", sp.getInitialHOP()));
			tdl5.setAttribute("colspan", "2");
			trl.appendValue(tdl5.toString());
			HTMLElement tdl6 = new HTMLElement("td", "grid2", sp.getInitialHOPDTString());
			trl.appendValue(tdl6.toString());
			html.append(trl.toString());			
			HTMLElement trm = new HTMLElement("tr");
			HTMLElement tdm1 = new HTMLElement("td", "grid1", "&nbsp;");
			tdm1.setAttribute("colspan", "8");
			trm.appendValue(tdm1.toString());			
			html.append(trm.toString());			
			HTMLElement trn = new HTMLElement("tr");
			HTMLElement tdn2 = new HTMLElement("td", "grid2RedBold", "Issue Owner:");
			trn.appendValue(tdn2.toString());
			HTMLElement tdn3 = new HTMLElement("td", "grid2", 
					getSelectHTMLWithInitialValue("IssueOwner", "select", "filter", sp.getIssueOwner()));
			tdn3.setAttribute("colspan", "2");
			trn.appendValue(tdn3.toString());
			HTMLElement tdn4 = new HTMLElement("td", "grid2", "&nbsp;");
			tdn4.setAttribute("colspan", "5");
			trn.appendValue(tdn4.toString());
			html.append(trn.toString());			
			HTMLElement tro = new HTMLElement("tr");
			HTMLElement tdo2 = new HTMLElement("td", "grid1RedBold", "Risk Indicator:");
			tro.appendValue(tdo2.toString());
			HTMLElement tdo3 = new HTMLElement("td", "grid1", 
					getSelectHTMLWithInitialValue("RiskIndicator", "select", "filter", sp.getRiskIndicator()));
			tdo3.setAttribute("colspan", "2");
			tro.appendValue(tdo3.toString());
			HTMLElement tdo4 = new HTMLElement("td", "grid1", "&nbsp;");
			tdo4.setAttribute("colspan", "53");
			tro.appendValue(tdo4.toString());
			html.append(tro.toString());			
			HTMLElement trp = new HTMLElement("tr");
			HTMLElement tdp2 = new HTMLElement("td", "grid2RedBold", "Progress Issue:");
			trp.appendValue(tdp2.toString());
			HTMLElement tdp3 = new HTMLElement("td", "grid2", 
					"<input style=\"width:95%;\" type=\"text\" name=\"progressIssueAmended\" id=\"progressIssueAmended\" value=\""+sp.getProgressIssue()+"\" maxlength=\"200\">");
			tdp3.setAttribute("colspan", "4");
			trp.appendValue(tdp3.toString());
			HTMLElement tdp4 = new HTMLElement("td", "grid2", "&nbsp;");
			tdp4.setAttribute("colspan", "5");
			trp.appendValue(tdp4.toString());
			html.append(trp.toString());			
		}		
		return html.toString();
	}
	
	public String gotoProject(String project, String fullname) {
		String updateResult = "Error: Untrapped error with Goto_Project";
		String message = null; 
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call Goto_Project(?,?)}");
	    	cstmt.setString(1, project);
	    	cstmt.setString(2, fullname);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					updateResult = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in Goto_Project(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	 
		return updateResult;
	}
	
	public String updateSiteProgress(
			long snrId,
			String checkedIn,
			String bookedOn,
			String siteAccessed,
			String physicalChecks,
			String preCallTest,
			String siteLocked,
			String hwInstalls,
			String commissioningFE,
			String commissioningBO,
			String txProvisioning,
			String fieldWork,
			String siteUnlocked,
			String postCallTest,
			String closureCode,
			String leaveSite,
			String bookOffSite,
			String performanceMonitoring,
			String initialHOP,
			String issueOwner,
			String lastUpdatedBy,
			String crqClosureCode,
			String riskIndicator,
			String progressIssue ) {
		String updateResult = "Error: Untrapped error with UpdateSiteProgress";
		String message = null; 
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call UpdateSiteProgress(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	    	cstmt.setLong(1, snrId);
	    	cstmt.setString(2, checkedIn);
	    	cstmt.setString(3, bookedOn);
	    	cstmt.setString(4, siteAccessed);
	    	cstmt.setString(5, physicalChecks);
	    	cstmt.setString(6, preCallTest);
	    	cstmt.setString(7, siteLocked);
	    	cstmt.setString(8, hwInstalls);
	    	cstmt.setString(9, commissioningFE);
	    	cstmt.setString(10,commissioningBO);
	    	cstmt.setString(11, txProvisioning);
	    	cstmt.setString(12, fieldWork);
	    	cstmt.setString(13, siteUnlocked);
	    	cstmt.setString(14, postCallTest);
	    	cstmt.setString(15, closureCode);
	    	cstmt.setString(16, leaveSite);
	    	cstmt.setString(17, bookOffSite);
	    	cstmt.setString(18, performanceMonitoring);
	    	cstmt.setString(19, initialHOP);
	    	cstmt.setString(20, issueOwner);
	    	cstmt.setString(21, lastUpdatedBy);
	    	cstmt.setString(22, crqClosureCode);
	    	cstmt.setString(23, riskIndicator);
	    	cstmt.setString(24, progressIssue);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					updateResult = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in UpdateSiteProgress(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	 
		return updateResult;
	}
	
	public String GetLiveSitesFilter() {
		String value = "";
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetLiveSitesFilter(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					value = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetLiveSitesFilter(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		return value;
	}
	
	public String GetLiveSitesHeading() {
		String heading = "";
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetLiveSitesHeading()}");
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					heading = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetLiveSitesHeading(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		String display = "inline";
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetDisplayProject(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					display = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getDisplayProject(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    if (display.equals("inline")) {
	    	heading = heading + "&nbsp;" +
	    			"<img src=\"images/hide.png\" height=\"15\" width=\"15\" "+
	    			"onclick=\"navigationAction('hide')\" "+
	    			"style=\"cursor:pointer;\" "+
    				"title=\"Hide project counts table\">";
	    }
	    else {
	    	heading = heading + "&nbsp;" +
	    			"<img src=\"images/show.png\" height=\"15\" width=\"15\" "+
	    			"onclick=\"navigationAction('show')\" "+
	    			"style=\"cursor:pointer;\" "+
    				"title=\"Show project counts table\">";
	    }
	    heading = heading + "&nbsp;" +
	    		"<img src=\"images/fwd.png\" "+
				"height=\"15\" width=\"15\" border:1px solid black; "+
				"onClick=\"navigationAction('fwd')\""+
    			"style=\"cursor:pointer;\" "+
				"title=\"Manually refresh the live dashboard\">";
		return heading;
	}
	
	public Collection<LiveDashboardSite> getLiveDashboardSites() {
		message = null;
		ArrayList<LiveDashboardSite> LiveDashboardSite = new ArrayList<LiveDashboardSite>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetLiveDashboardSites(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					LiveDashboardSite.add(new LiveDashboardSite(
							rs.getString(1),  rs.getString(2),  rs.getString(3),  rs.getString(4), 
							rs.getString(5),  rs.getString(6),  rs.getString(7),  rs.getString(8),
							rs.getString(9),  rs.getString(10), rs.getString(11), rs.getString(12),
							rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), 
							rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
							rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24),
							rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28),
							rs.getString(29), rs.getString(30), rs.getString(31), rs.getString(32), 
							rs.getString(33), rs.getString(34)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getLiveDashboardSites(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return LiveDashboardSite;
	}
	
	public String getLiveDashboardSitesHTML() {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		Collection<LiveDashboardSite> LiveDashboardSite = getLiveDashboardSites();
		if (LiveDashboardSite.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1",	message);
				td.setAttribute("colspan", "29");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			} else {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1","No sites to display");
				td.setAttribute("colspan", "29");
				tr.appendValue(td.toString());
				html.append(tr.toString());	
			}
		} else {
			prevDate = "DD/MM";
			prevYearWeek = "YYYYWW";
			for (Iterator<LiveDashboardSite> it = LiveDashboardSite.iterator(); it.hasNext(); ) {
				oddRow = !oddRow;
				LiveDashboardSite ldc = it.next();
				// add break if previous row was for current date and now past current date
				if ( (!prevDate.equals(ldc.getScheduledDDMM()))  &&
						(prevDate.equals(ldc.getTodayDDMM()))) {
					HTMLElement tr1 = new HTMLElement("tr");	
					HTMLElement td11 = new HTMLElement("td", "", "" );
					td11.setAttribute("height", "2px");
					td11.setAttribute("colspan", "29");
					tr1.appendValue(td11.toString());				
					html.append(tr1.toString());
					HTMLElement tr2 = new HTMLElement("tr");		
					HTMLElement td21 = new HTMLElement("td", "ldGridBreak", "" );
					td21.setAttribute("height", "3px");
					td21.setAttribute("colspan", "29");
					tr2.appendValue(td21.toString());				
					html.append(tr2.toString());					
					html.append(tr1.toString());
				// add break if previous row was for current week and now past current week
				} else if ( (!prevYearWeek.equals(ldc.getCurrentYearWeek()))  &&
						(prevYearWeek.equals(ldc.getFirstYearWeek()))) {
					HTMLElement tr1 = new HTMLElement("tr");	
					HTMLElement td11 = new HTMLElement("td", "", "" );
					td11.setAttribute("height", "2px");
					td11.setAttribute("colspan", "29");
					tr1.appendValue(td11.toString());				
					html.append(tr1.toString());
					HTMLElement tr2 = new HTMLElement("tr");		
					HTMLElement td21 = new HTMLElement("td", "ldGridBreak", "" );
					td21.setAttribute("height", "3px");
					td21.setAttribute("colspan", "29");
					tr2.appendValue(td21.toString());				
					html.append(tr2.toString());					
					html.append(tr1.toString());
				} 	
				// output current row
				HTMLElement tr = new HTMLElement("tr");
				// Risk 
				String riskImage = "";
				if (!ldc.getRisk().isEmpty()) {
					riskImage = "<img src=\"images/"+ldc.getRisk()+"_Risk.png\" height=\"10px\" width=\"10px\">";
				}
				HTMLElement td0 = new HTMLElement("td", (oddRow?"ldGrid1":"ldGrid2"), riskImage );
				tr.appendValue(td0.toString());
				// customer
				HTMLElement td1 = new HTMLElement("td", (oddRow?"ldGrid1":"ldGrid2"), ldc.getCustomer());
				tr.appendValue(td1.toString());
				// project
				HTMLElement td3 = new HTMLElement("td", (oddRow?"ldGrid1":"ldGrid2"), ldc.getProject());
				tr.appendValue(td3.toString());
				// migration type
				HTMLElement td3b = new HTMLElement("td", (oddRow?"ldGrid1":"ldGrid2"), ldc.getMigrationType());
				tr.appendValue(td3b.toString());
				// site
				HTMLElement td4 = new HTMLElement("td", (oddRow?"ldGrid1":"ldGrid2"), ldc.getSite());
				tr.appendValue(td4.toString());
				// BO 
				HTMLElement td5 = new HTMLElement("td", (oddRow?"ldGrid1":"ldGrid2"), ldc.getBoList());
				tr.appendValue(td5.toString());
				// FE 
				HTMLElement td6 = new HTMLElement("td", (oddRow?"ldGrid1":"ldGrid2"), ldc.getFeList());
				tr.appendValue(td6.toString());
				// Overall status 
				HTMLElement td7 = new HTMLElement(
										"td", 
										(oddRow?"ldGrid1":"ldGrid2")+ldc.getOverallStatusColour(), 
										//(oddRow?"ldGrid1RDash":"ldGrid2RDash")+ldc.getOverallStatusColour(), 
										ldc.getOverallStatus());
				tr.appendValue(td7.toString());
				// Scheduled Date in DD/MM format 
				HTMLElement td28 = new HTMLElement("td", (oddRow?"ldGrid1":"ldGrid2"), ldc.getScheduledDDMM());
				tr.appendValue(td28.toString());
				// Checked In
				HTMLElement td8 = new HTMLElement("td", "ld"+ldc.getCheckedIn()+"LDash", "");
				tr.appendValue(td8.toString());
				// Booked On
				HTMLElement td9 = new HTMLElement("td", "ld"+ldc.getBookedOn(), "");
				tr.appendValue(td9.toString());
				// Site Accessed
				HTMLElement td10 = new HTMLElement("td", "ld"+ldc.getSiteAccessed(), "");
				tr.appendValue(td10.toString());
				// Physical Checks
				HTMLElement td11 = new HTMLElement("td", "ld"+ldc.getPhysicalChecks(), "");
				tr.appendValue(td11.toString());
				// Pre Call Test
				HTMLElement td12 = new HTMLElement("td", "ld"+ldc.getPreCallTest()+"RDash", "");
				tr.appendValue(td12.toString());
				// Site Locked
				HTMLElement td13 = new HTMLElement("td", "ld"+ldc.getSiteLocked()+"LDash", "");
				tr.appendValue(td13.toString());
				// HW Installs
				HTMLElement td14 = new HTMLElement("td", "ld"+ldc.getHwInstalls(), "");
				tr.appendValue(td14.toString());
				// Commissioning FE
				HTMLElement td15 = new HTMLElement("td", "ld"+ldc.getCommissioningFE(), "");
				tr.appendValue(td15.toString());
				// Commissioning BO
				HTMLElement td16 = new HTMLElement("td", "ld"+ldc.getCommissioningBO(), "");
				tr.appendValue(td16.toString());
				// TX Provisioning
				HTMLElement td17 = new HTMLElement("td", "ld"+ldc.getTxProvisioning(), "");
				tr.appendValue(td17.toString());
				// Field Work
				HTMLElement td18 = new HTMLElement("td", "ld"+ldc.getFieldWork(), "");
				tr.appendValue(td18.toString());
				// Site Unlocked
				HTMLElement td19 = new HTMLElement("td", "ld"+ldc.getSiteUnlocked(), "");
				tr.appendValue(td19.toString());
				// Post Call Test
				HTMLElement td20 = new HTMLElement("td", "ld"+ldc.getPostCallTest()+"RDash", "");
				tr.appendValue(td20.toString());
				// Closure Code
				HTMLElement td21 = new HTMLElement("td", "ld"+ldc.getClosureCode()+"LDash", "");
				tr.appendValue(td21.toString());
				// Leave Site
				HTMLElement td22 = new HTMLElement("td", "ld"+ldc.getLeaveSite(), "");
				tr.appendValue(td22.toString());
				// Book Off Site
				HTMLElement td23 = new HTMLElement("td", "ld"+ldc.getBookOffSite(), "");
				tr.appendValue(td23.toString());
				// Performance Monitoring
				HTMLElement td24 = new HTMLElement("td", "ld"+ldc.getPerformanceMonitoring(), "");
				tr.appendValue(td24.toString());
				// Initial HOP
				HTMLElement td25 = new HTMLElement("td", "ld"+ldc.getInitialHOP()+"RDash", "");
				tr.appendValue(td25.toString());
				// Devoteam Issue
				HTMLElement td26 = new HTMLElement("td", "ld"+ldc.getDevoteamIssue()+"LDash", "");
				tr.appendValue(td26.toString());
				// Customer Issue
				HTMLElement td27 = new HTMLElement("td", "ld"+ldc.getCustomerIssue(), "");
				tr.appendValue(td27.toString());
				// Complete row
				html.append(tr.toString());					
				// store current scheduled date and current year week
				prevDate = ldc.getScheduledDDMM();
				prevYearWeek = ldc.getCurrentYearWeek();
			}
		}
		return html.toString();
	}	
	
	public String imageChoice1() {
		String imageName = "dev_logo_rvb.png";
		Random r = new Random();
		imageChoice = r.nextInt(3) + 1;
		if (imageChoice==1) imageName = "ideas_banner_rvb.jpg";
		if (imageChoice==2) imageName = "Digital_battle_banner_rvb.png";
		if (imageChoice==3) imageName = "sherpa_banner_rvb.jpg";
		return imageName;
	}
	
	public String imageChoice2() {
		String imageName = "dev_logo_rvb.png";
		if (imageChoice==1) imageName = "up_banner_rvb.jpg";
		if (imageChoice==2) imageName = "RedCross_banner_rvb.jpg";
		if (imageChoice==3) imageName = "Flag_banner_rvb.png";
		return imageName;
	}
	
	public String imageChoice3() {
		String imageName = "dev_logo_rvb.png";
		if (imageChoice==1) imageName = "Flag_banner_rvb.png";
		if (imageChoice==2) imageName = "sherpa_banner_rvb.jpg";
		if (imageChoice==3) imageName = "Digital_battle_banner_rvb.png";
		return imageName;
	}
	
	public String externalTitle() {
		String title = "Client Access";
		if (user.getUserType().equals("Third Party")) {
			title = "Supplier Access";
		}
		return title;
	}
	
	public String getProjectFilterHTML() {
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	Select select = new Select("selectProjectFilter",  "filter");
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetProjectLDFilterList(?)}");
   			cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}
	
	public String getProjectReportingFilterHTML() {
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	Select select = new Select("selectProjectFilter",  "filter");
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetProjectReportingFilterList(?)}");
   			cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}
	
	public Collection<UserJobType> getUserJobTypes(long userId) {
		ArrayList<UserJobType> ujtList = new ArrayList<UserJobType>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetUserJobTypes(?)}");
   			cstmt.setLong(1, userId);
			boolean found = cstmt.execute();
			if (found) {
				
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					ujtList.add(new UserJobType(
							rs.getLong(1),
							rs.getString(2),
							rs.getString(3)));
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
	    return ujtList;
	}
		
	public String getUserJobTypesHTML(long userId) {
		boolean oddRow = false;
		int row = 0;
		StringBuilder html = new StringBuilder();
		Collection<UserJobType> ujtList = getUserJobTypes(userId);
		if (ujtList.isEmpty()) {
			if (message!=null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td1 = new HTMLElement("td", "grid1i", message);
				td1.setAttribute("align", "left");
				td1.setAttribute("colspan", "3");
				tr.appendValue(td1.toString());
				html.append(tr.toString());
				
			} else {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td1 = new HTMLElement("td", "grid1i", "No job types allocated");
				td1.setAttribute("align", "left");
				td1.setAttribute("colspan", "3");
				tr.appendValue(td1.toString());
				html.append(tr.toString());
			}
		} else {			
			for (Iterator<UserJobType> it = ujtList.iterator(); it.hasNext(); ) {
				row++;
				oddRow = !oddRow;
				UserJobType uJT = it.next();
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td1 = new HTMLElement("td", (oddRow?"grid1i":"grid2i"), uJT.getJobType());
				tr.appendValue(td1.toString());
				HTMLElement td2 = new HTMLElement("td", (oddRow?"grid1i":"grid2i"), uJT.getRedundant());
				td2.setAttribute("align", "center");
				tr.appendValue(td2.toString());
				HTMLElement input = new HTMLElement("input", "user"+row, "usrId",
													"radio", uJT.getUserIdString()+uJT.getJobType(),
													"deleteUserJobType("+userId+",'"+uJT.getJobType()+"')");
				HTMLElement td3 = new HTMLElement("td", (oddRow?"grid1i":"grid2i"), input.toString());
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					input.toString());
				td3.setAttribute("align", "center");
				tr.appendValue(td3.toString());
				html.append(tr.toString());
			}
		}
		return html.toString();
	}
	
	public String getAvailableJobTypesForUserHTML(long userId) {
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	Select select = new Select("selectJobTypesForUser",  "filter");
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetAvailableJobTypesForUser(?)}");
   			cstmt.setLong(1, userId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}
	
	public String getRecentCompletedSitesHTML() {
		//set up empty string array
		String[] csClass = new String[5];
		String[] csNRId = new String[5];
		String[] csTime = new String[5];
		String[] csStatus = new String[5];
		for (int j = 0;j<5;j++) {
			csClass[j] = "";
			csNRId[j] = "";
			csTime[j] = "";
			csStatus[j] = "";
		}
		int pos = 4;
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call Display_Recent_Completed_Sites(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					String nrId = rs.getString(1);
					String boxColour = rs.getString(2);
					String completedTime = rs.getString(3);
					String impStatus = rs.getString(4);
					if (pos>-1) {
						csNRId[pos] = nrId;
						csClass[pos] = "siteComp" + boxColour;
						csTime[pos] = completedTime;
						csStatus[pos] = impStatus;
					}
					pos--;
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in Display_Recent_Completed_Sites(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		StringBuilder html = new StringBuilder();
		HTMLElement tr = new HTMLElement("tr");
		HTMLElement td1 = new HTMLElement("td", csClass[0], csNRId[0] );
		td1.setAttribute("width", "50px");
		td1.setAttribute("height", "30px");
		td1.setAttribute("title", csStatus[0]+" at "+csTime[0]);
		tr.appendValue(td1.toString());
		HTMLElement tdSP = new HTMLElement("td", "", "&nbsp;");
		tdSP.setAttribute("width", "5px");
		tr.appendValue(tdSP.toString());
		HTMLElement td2 = new HTMLElement("td", csClass[1], csNRId[1]);
		td2.setAttribute("width", "50px");
		td2.setAttribute("height", "30px");
		td2.setAttribute("title", csStatus[1]+" at "+csTime[1]);
		tr.appendValue(td2.toString());
		tr.appendValue(tdSP.toString());
		HTMLElement td3 = new HTMLElement("td", csClass[2], csNRId[2]);
		td3.setAttribute("width", "50px");
		td3.setAttribute("height", "30px");
		td3.setAttribute("title", csStatus[2]+" at "+csTime[2]);
		tr.appendValue(td3.toString());
		HTMLElement tdSP3 = new HTMLElement("td", "", "&nbsp;");
		tr.appendValue(tdSP.toString());
		HTMLElement td4 = new HTMLElement("td", csClass[3], csNRId[3]);
		td4.setAttribute("width", "50px");
		td4.setAttribute("height", "30px");
		td4.setAttribute("title", csStatus[3]+" at "+csTime[3]);
		tr.appendValue(td4.toString());
		tr.appendValue(tdSP.toString());
		HTMLElement td5 = new HTMLElement("td", csClass[4],csNRId[4]);
		td5.setAttribute("width", "50px");
		td5.setAttribute("height", "30px");
		td5.setAttribute("title", csStatus[4]+" at "+csTime[4]);
		tr.appendValue(td5.toString());
		html.append(tr.toString());	
		return html.toString();
	}
	
	public String getSiteProgressIssuesHTML() {
		StringBuilder html = new StringBuilder();
		// add progress issue lines
		int issues = 0;
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSiteProgressIssues(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					HTMLElement trd = new HTMLElement("tr");
					HTMLElement tdd = new HTMLElement("td", "ldIssue", rs.getString(1));
					tdd.setAttribute("width", "500px");
					tdd.setAttribute("height", "5px");
					trd.appendValue(tdd.toString());
					html.append(trd.toString());
					issues++;
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetSiteProgressIssues(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    if (issues==0) {
    		HTMLElement trd = new HTMLElement("tr");
			HTMLElement tdd = new HTMLElement("td", "ldIssue", "None");
			tdd.setAttribute("width", "500px");
			tdd.setAttribute("height", "5px");
			trd.appendValue(tdd.toString());
			html.append(trd.toString());
	    }
		return html.toString();
	}
	
	public String logoFilename (String screen) {
		String name = "smart.png";
		if (screen.equals(ServletConstants.LIVE_DASHBOARD)) {
			name = "smart_LD.png";
		} else if (screen.equals(ServletConstants.SITE_SEARCH)) {
			name = "smart_SS.png";
		} else if (screen.equals(ServletConstants.CLIENT_REPORTING)) {
			name = "smart_CR.png";
		} else if (screen.equals(ServletConstants.HOME_FE)) {
			name = "smart_FE.png";
		} else if (	(screen.equals(ServletConstants.SCHEDULE_VIEW)) ||
					(screen.equals(ServletConstants.MISSING_DATA)) ) {
			name = "smart_S.png";
		}
		return name;
	}
	
	public String emailCopySiteHTML () {
		return getSelectHTMLWithInitialValue("EmailCopySite","select","filter",user.getFullname());
	}
	
	public String emailCopyNRIdHTML () {
		return getSelectHTMLWithInitialValue("EmailCopyNRId","select","filter",user.getFullname());
	}
	
	public String emailCopyProjectHTML () {
		return getSelectHTMLWithInitialValue("EmailCopyProject","select","filter",user.getFullname());
	}
	
	public String emailCopyUpgradeTypeHTML () {
		return getSelectHTMLWithInitialValue("EmailCopyUpgradeType","select","filter",user.getFullname());
	}
	
	public String emailCopyClientHTML () {
		return getSelectHTMLWithInitialValue("EmailCopyClient","select","filter",user.getFullname());
	}
	
	public String emailCopyCompletionTypeHTML (String completionType) {
		return getSelectHTMLWithInitialValue("EmailCopyCompletionType","select","filter",completionType);
	}
	
	public String projectFilterHTML () {
		return getSelectHTMLWithInitialValue("projectFilter","select","filter",user.getFullname());
	}
	
	public String getSiteCompletionReportListHTML
			( String action, String year, String month, String day, String week, 
					String client, String project, String site, String nrId,
					String startDT, String endDT, String completionType ) {
		StringBuilder html = new StringBuilder();
		if ((action.startsWith("fwd"))||
				(action.startsWith("rwd"))||
				(action.startsWith("init"))) {
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td= new HTMLElement("td", "siteReportDClass", "&nbsp;");
			td.setAttribute("colspan", "6");
			tr.appendValue(td.toString());
			html.append(tr.toString());
		} else {
			String dbAction = action;
			if (action.equals("chgMonth"))
				dbAction = "month";
			if (action.equals("chgDay"))
				dbAction = "day";
			if (action.equals("chgWeek"))
				dbAction = "week";
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetSiteCompletionReportList(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	   			cstmt.setString(1, dbAction);
	   			cstmt.setString(2, year);
	   			cstmt.setString(3, month);
	   			cstmt.setString(4, day);
	   			cstmt.setString(5, week);
	   			cstmt.setString(6, (client.equals("")?"All":client));
	   			cstmt.setString(7, (project.equals("")?"All":project));
	   			cstmt.setString(8, (site.equals("")?"All":site));
	   			cstmt.setString(9, (nrId.equals("")?"All":nrId));
	   			cstmt.setString(10, startDT);
	   			cstmt.setString(11, endDT);
	   			cstmt.setString(12, (completionType.equals("")?"All":completionType));
	   			cstmt.setString(13, user.getFullname());
				boolean found = cstmt.execute();
				boolean sitesDisplayed = false;
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						String repSite = rs.getString(1);
						String repNrId = rs.getString(2);
						String repDate = rs.getString(3);
						String repType = rs.getString(4);
						String repProject = rs.getString(5);
						String repClient = rs.getString(6);
						String repMigType = rs.getString(7);
						String repDisplaySite = rs.getString(8);
						HTMLElement tr = new HTMLElement("tr");
						String onClickRS = 
								"reportSelect('"+repSite+"','"+repNrId+"','"+repDate+"','"+repType+"')";
						HTMLElement td1 = new HTMLElement("td", "siteReportDClass", repDisplaySite);
						td1.setAttribute("onClick", onClickRS);
						tr.appendValue(td1.toString());
						HTMLElement td3 = new HTMLElement("td", "siteReportDClass", repDate);
						td3.setAttribute("onClick", onClickRS);
						tr.appendValue(td3.toString());
						String compClass = "siteReportDGreenClass";
						if (repType.equals("Partial")) 
							compClass = "siteReportDAmberClass";
						else if (repType.equals("Abort")) 
							compClass = "siteReportDRedClass";
						HTMLElement td4= new HTMLElement("td", compClass, repType);
						td4.setAttribute("onClick", onClickRS);
						tr.appendValue(td4.toString());
						HTMLElement td5 = new HTMLElement("td", "siteReportDClass", repClient);
						td5.setAttribute("onClick", onClickRS);
						tr.appendValue(td5.toString());
						HTMLElement td6 = new HTMLElement("td", "siteReportDClass", repProject);
						td6.setAttribute("onClick", onClickRS);
						tr.appendValue(td6.toString());
						HTMLElement td7 = new HTMLElement("td", "siteReportDClass", repMigType);
						td7.setAttribute("onClick", onClickRS);
						tr.appendValue(td7.toString());
						html.append(tr.toString());	
						sitesDisplayed = true;
					}
					if (!sitesDisplayed) {
						HTMLElement tr = new HTMLElement("tr");
						HTMLElement td= new HTMLElement("td", "siteReportDEClass", "No reports to display");
						td.setAttribute("colspan", "6");
						tr.appendValue(td.toString());
						html.append(tr.toString());
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
		}
		return html.toString();
	}
	
	public String getSiteCompletionReportCriteriaHTML
			( String action, String year, String month, String day, String week, 
			  String client, String project, String site, String nrId) {
		String criteria = "";
		if ((action.startsWith("fwd"))||
				(action.startsWith("rwd"))||
				(action.startsWith("init"))) {
			criteria = "";
		} else {
			criteria = "Reports for ";
			if (action.equals("chgMonth")) {
				criteria = criteria + decodeMonth(month) + " " + year;
			} else if (action.equals("chgDay")) {
				criteria = criteria + day + "/"+ month + "/" + year;
			} else if (action.equals("chgWeek")) {
				criteria = criteria + "week "+week+" / "+year;
			} else if (action.equals("site")) {
				criteria = criteria + "site " + site;
			} else if (action.equals("nrId")) {
				criteria = criteria + "nr id " + nrId;
			} else {
				criteria = criteria + " selected criteria";
			}
		}
		return criteria;
	}
	
	private String decodeMonth(String month) {
		String fullMonth="";
		if (month.equals("01")) fullMonth = "January";
		else if (month.equals("02")) fullMonth = "February";
		else if (month.equals("03")) fullMonth = "March";
		else if (month.equals("04")) fullMonth = "April";
		else if (month.equals("05")) fullMonth = "May";
		else if (month.equals("06")) fullMonth = "June";
		else if (month.equals("07")) fullMonth = "July";
		else if (month.equals("08")) fullMonth = "August";
		else if (month.equals("09")) fullMonth = "September";
		else if (month.equals("10")) fullMonth = "October";
		else if (month.equals("11")) fullMonth = "November";
		else if (month.equals("12")) fullMonth = "December";
		return fullMonth;
	}
	
	public String clientReportingHeader( String action, String year, String month, String day, String week, String project) {
		String header = decodeMonth(month) + " " + year;
		if (action.endsWith("Week")) {
			header = "Week "+ week + " / " + year;
		} else if (action.endsWith("Day")) {
			header = day + "/" + month + "/" + year;		
		}
		header = header + " - " + project;
		return header;	
	}
	
	public String getSuccessDetailHTML
			(String action, String year, String month, String day, String week, String project) {
		StringBuilder html = new StringBuilder();
		String dbAction = "Month";	
		int iCount = 0;
		if (action.endsWith("Week")) {
			dbAction = "Week";
		} else if (action.endsWith("Day")) {
			dbAction = "Day";		
		}
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	String 	storedMigrationType = "empty", 
    			storedPrevHead = "", 
    			storedCurrHead = "", 
    			storedCurrentPercentage = "N/A", 
    			storedPreviousPercentage = "N/A",
    			migrationType = "",
				currHead = "",
				prevHead = "",
				nowValue = "",
				percentage = "";
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSuccessRateDetail(?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, dbAction);
	    	cstmt.setString(2, year);
	    	cstmt.setString(3, month);
	    	cstmt.setString(4, day);
	    	cstmt.setString(5, week);
	    	cstmt.setString(6, user.getFullname());
	    	cstmt.setString(7, project);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					migrationType = rs.getString(1);
					currHead = rs.getString(2);
					prevHead = rs.getString(3);
					nowValue = rs.getString(4);
					percentage = rs.getString(5);
					if (!percentage.equals("N/A"))
						percentage = percentage + "%";
					if (storedMigrationType.equals("empty")) {
						storedPrevHead = prevHead;
						storedCurrHead = currHead; 
					}				
					if (!migrationType.equals(storedMigrationType)) {
						if (storedMigrationType.equals("empty")) {
							HTMLElement tr = new HTMLElement("tr");
							HTMLElement td1 = new HTMLElement("td", "popUpHead", "Work Type");
							tr.appendValue(td1.toString());
							HTMLElement td2 = new HTMLElement("td", "popUpHead", storedPrevHead);
							tr.appendValue(td2.toString());
							HTMLElement td3 = new HTMLElement("td", "popUpHead", storedCurrHead);
							tr.appendValue(td3.toString());
							html.append(tr.toString());
						} else {
							//write out line
							HTMLElement tr = new HTMLElement("tr");
							HTMLElement td1 = new HTMLElement("td", "popUpDetail", storedMigrationType);
							tr.appendValue(td1.toString());
							HTMLElement td2 = new HTMLElement("td", "popUpDetail", storedPreviousPercentage);
							tr.appendValue(td2.toString());
							HTMLElement td3 = new HTMLElement("td", "popUpDetail", storedCurrentPercentage);
							tr.appendValue(td3.toString());
							html.append(tr.toString());	
						}
						storedMigrationType = migrationType;
		    			storedCurrentPercentage = "N/A"; 
		    			storedPreviousPercentage = "N/A";				
					}
					// store relevant percentage values
					if (nowValue.equals(prevHead))						
						storedPreviousPercentage = percentage;						
					else
						storedCurrentPercentage = percentage;
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetSuccessRateDetail(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		if (!storedMigrationType.equals("empty")) {
			if (nowValue.equals(prevHead))
				storedPreviousPercentage = percentage;
			else
				storedCurrentPercentage = percentage;	
			//write out line
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td1 = new HTMLElement("td", "popUpDetail", storedMigrationType);
			tr.appendValue(td1.toString());
			HTMLElement td2 = new HTMLElement("td", "popUpDetail", storedPreviousPercentage);
			tr.appendValue(td2.toString());
			HTMLElement td3 = new HTMLElement("td", "popUpDetail", storedCurrentPercentage);
			tr.appendValue(td3.toString());
			html.append(tr.toString());
		} else {
			// write out blank line
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td1 = new HTMLElement("td", "popUpDetail", "No details to display");
			td1.setAttribute("colspan", "3");
			tr.appendValue(td1.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public String getSuccessSummaryHTML
			(String action, String year, String month, String day, String week, String project) {
		StringBuilder html = new StringBuilder();
		String dbAction = "Month";	
		int iCount = 0;
		if (action.endsWith("Week")) {
			dbAction = "Week";
		} else if (action.endsWith("Day")) {
			dbAction = "Day";		
		}	
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSuccessRateSummary(?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, dbAction);
	    	cstmt.setString(2, year);
	    	cstmt.setString(3, month);
	    	cstmt.setString(4, day);
	    	cstmt.setString(5, week);
	    	cstmt.setString(6, user.getFullname());
	    	cstmt.setString(7, project);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					HTMLElement tra = new HTMLElement("tr");
					HTMLElement tda1 = new HTMLElement(
											"td", 
											"clientHead", 
											"Success Rate" );
											/*clientReportingHeader(action, year, month, day, week, project) +
											"&nbsp;" +
											"- Success Rate" );*/
					tda1.setAttribute("id", "srAnchor");
					tda1.setAttribute("height", "20px");
					tda1.setAttribute("colspan", "4");
					tda1.setAttribute("onclick", "successRateDetailClick('open')");
					tda1.setAttribute("style", "cursor:pointer;");
					tra.appendValue(tda1.toString());
					html.append(tra.toString());
					HTMLElement trb = new HTMLElement("tr");
					HTMLElement tdb1 = new HTMLElement("td","clientHeadLarge","<");
					tdb1.setAttribute("rowspan", "2");
					tdb1.setAttribute("height", "60px");
					tdb1.setAttribute("onClick", "moveDate('back')");
					tdb1.setAttribute("style", "cursor:pointer;");
					trb.appendValue(tdb1.toString());
					HTMLElement tdb2 = new HTMLElement("td","clientHeadLarge"+rs.getString(3),rs.getString(1));
					tdb2.setAttribute("rowspan", "2");
					tdb2.setAttribute("height", "60px");
					tdb2.setAttribute("onclick", "successRateDetailClick('open')");
					tdb2.setAttribute("style", "cursor:pointer;");
					trb.appendValue(tdb2.toString());
					HTMLElement tdb3 = new HTMLElement(
											"td",
											"clientHead",
											"<img src=\"images/"+rs.getString(4)+"Arrow.png"+"\""+
											"height=\"30px\" width=\"30px\""+">");
					tdb3.setAttribute("height", "30px");
					trb.appendValue(tdb3.toString());
					HTMLElement tdb4 = new HTMLElement("td","clientHeadLarge",">");
					tdb4.setAttribute("rowspan", "2");
					tdb4.setAttribute("height", "60px");
					tdb4.setAttribute("onClick", "moveDate('forward')");
					tdb4.setAttribute("style", "cursor:pointer;");
					trb.appendValue(tdb4.toString());
					html.append(trb.toString());
					HTMLElement trc = new HTMLElement("tr");
					HTMLElement tdc1 = new HTMLElement("td","clientHead",rs.getString(2));
					tdc1.setAttribute("height", "30px");
					trc.appendValue(tdc1.toString());
					html.append(trc.toString());
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetSuccessRateSummary(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		return html.toString();
	}
	
	public String getOutageSummaryHTML
			(String action, String year, String month, String day, String week, String project) {
		StringBuilder html = new StringBuilder();
		String dbAction = "Month";	
		int iCount = 0;
		if (action.endsWith("Week")) {
			dbAction = "Week";
		} else if (action.endsWith("Day")) {
			dbAction = "Day";		
		}	
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetOutageSummary(?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, dbAction);
	    	cstmt.setString(2, year);
	    	cstmt.setString(3, month);
	    	cstmt.setString(4, day);
	    	cstmt.setString(5, week);
	    	cstmt.setString(6, user.getFullname());
	    	cstmt.setString(7, project);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					HTMLElement tra = new HTMLElement("tr");
					HTMLElement tda1 = new HTMLElement(
											"td", 
											"clientHead",
											"Outage" );
											/*clientReportingHeader(action, year, month, day, week, project) +
											"&nbsp;" +
											"- Outage" );*/
					tda1.setAttribute("id", "oAnchor");
					tda1.setAttribute("height", "20px");
					tda1.setAttribute("colspan", "4");
					tda1.setAttribute("onclick", "outageDetailClick('open')");
					tda1.setAttribute("style", "cursor:pointer;");
					tra.appendValue(tda1.toString());
					html.append(tra.toString());
					HTMLElement trb = new HTMLElement("tr");
					HTMLElement tdb1 = new HTMLElement("td","clientHeadLarge","<");
					tdb1.setAttribute("rowspan", "2");
					tdb1.setAttribute("height", "60px");
					tdb1.setAttribute("onClick", "moveDate('back')");
					tdb1.setAttribute("style", "cursor:pointer;");
					trb.appendValue(tdb1.toString());
					HTMLElement tdb2 = new HTMLElement("td","clientHeadLarge"+rs.getString(3),rs.getString(1));
					tdb2.setAttribute("rowspan", "2");
					tdb2.setAttribute("height", "60px");
					tdb2.setAttribute("onclick", "outageDetailClick('open')");
					tdb2.setAttribute("style", "cursor:pointer;");
					trb.appendValue(tdb2.toString());
					HTMLElement tdb3 = new HTMLElement(
											"td",
											"clientHead",
											"<img src=\"images/"+rs.getString(4)+"Arrow.png"+"\""+
											"height=\"30px\" width=\"30px\""+">");
					tdb3.setAttribute("height", "30px");
					trb.appendValue(tdb3.toString());
					HTMLElement tdb4 = new HTMLElement("td","clientHeadLarge",">");
					tdb4.setAttribute("rowspan", "2");
					tdb4.setAttribute("height", "60px");
					tdb4.setAttribute("onClick", "moveDate('forward')");
					tdb4.setAttribute("style", "cursor:pointer;");
					trb.appendValue(tdb4.toString());
					html.append(trb.toString());
					HTMLElement trc = new HTMLElement("tr");
					HTMLElement tdc1 = new HTMLElement("td","clientHead",rs.getString(2));
					tdc1.setAttribute("height", "30px");
					trc.appendValue(tdc1.toString());
					html.append(trc.toString());
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetOutageSummary(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		return html.toString();
	}
	
	public String getOutageDetailHTML
			(String action, String year, String month, String day, String week, String project) {
		StringBuilder html = new StringBuilder();
		String dbAction = "Month";	
		int iCount = 0;
		if (action.endsWith("Week")) {
			dbAction = "Week";
		} else if (action.endsWith("Day")) {
			dbAction = "Day";		
		}
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	String 	storedMigrationType = "empty", 
    			storedPrevHead = "", 
    			storedCurrHead = "", 
    			storedCurrentOutage = "N/A", 
    			storedPreviousOutage = "N/A",
    			migrationType = "",
				currHead = "",
				prevHead = "",
				nowValue = "",
				outage = "";
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetOutageDetail(?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, dbAction);
	    	cstmt.setString(2, year);
	    	cstmt.setString(3, month);
	    	cstmt.setString(4, day);
	    	cstmt.setString(5, week);
	    	cstmt.setString(6, user.getFullname());
	    	cstmt.setString(7, project);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					migrationType = rs.getString(1);
					currHead = rs.getString(2);
					prevHead = rs.getString(3);
					nowValue = rs.getString(4);
					outage = rs.getString(5);
					if (storedMigrationType.equals("empty")) {
						storedPrevHead = prevHead;
						storedCurrHead = currHead; 
					}				
					if (!migrationType.equals(storedMigrationType)) {
						if (storedMigrationType.equals("empty")) {
							HTMLElement tr = new HTMLElement("tr");
							HTMLElement td1 = new HTMLElement("td", "popUpHead", "Work Type");
							tr.appendValue(td1.toString());
							HTMLElement td2 = new HTMLElement("td", "popUpHead", storedPrevHead);
							tr.appendValue(td2.toString());
							HTMLElement td3 = new HTMLElement("td", "popUpHead", storedCurrHead);
							tr.appendValue(td3.toString());
							html.append(tr.toString());
						} else {
							//write out line
							HTMLElement tr = new HTMLElement("tr");
							HTMLElement td1 = new HTMLElement("td", "popUpDetail", storedMigrationType);
							tr.appendValue(td1.toString());
							HTMLElement td2 = new HTMLElement("td", "popUpDetail", storedPreviousOutage);
							tr.appendValue(td2.toString());
							HTMLElement td3 = new HTMLElement("td", "popUpDetail", storedCurrentOutage);
							tr.appendValue(td3.toString());
							html.append(tr.toString());	
						}
						storedMigrationType = migrationType;
		    			storedCurrentOutage = "N/A"; 
		    			storedPreviousOutage = "N/A";				
					}
					// store relevant percentage values
					if (nowValue.equals(prevHead))						
						storedPreviousOutage = outage;						
					else
						storedCurrentOutage = outage;
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetSuccessRateDetail(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		if (!storedMigrationType.equals("empty")) {
			if (nowValue.equals(prevHead))
				storedPreviousOutage = outage;
			else
				storedCurrentOutage = outage;	
			//write out line
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td1 = new HTMLElement("td", "popUpDetail", storedMigrationType);
			tr.appendValue(td1.toString());
			HTMLElement td2 = new HTMLElement("td", "popUpDetail", storedPreviousOutage);
			tr.appendValue(td2.toString());
			HTMLElement td3 = new HTMLElement("td", "popUpDetail", storedCurrentOutage);
			tr.appendValue(td3.toString());
			html.append(tr.toString());
		} else {
			// write out blank line
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td1 = new HTMLElement("td", "popUpDetail", "No details to display");
			td1.setAttribute("colspan", "3");
			tr.appendValue(td1.toString());
			html.append(tr.toString());
		}
		return html.toString();
	}
	
	public String getIncidentTotal
			( String action, String year, String month, String day, String week, String project) {
		String iCount = "-1", dbAction = "Month";	
		if (action.endsWith("Week")) {
			dbAction = "Week";
		} else if (action.endsWith("Day")) {
			dbAction = "Day";		
		}	
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetIncidentTotal(?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, dbAction);
	    	cstmt.setString(2, year);
	    	cstmt.setString(3, month);
	    	cstmt.setString(4, day);
	    	cstmt.setString(5, week);
	    	cstmt.setString(6, user.getFullname());
	    	cstmt.setString(7, project);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					iCount = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetIncidentTotal(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		return iCount;	
	}
	
	public String getIncidentDetailHTML
		(String action, String year, String month, String day, String week, String project) {
		StringBuilder html = new StringBuilder();
		String dbAction = "Month";	
		int iCount = 0;
		if (action.endsWith("Week")) {
			dbAction = "Week";
		} else if (action.endsWith("Day")) {
			dbAction = "Day";		
		}	
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetIncidentDetail(?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, dbAction);
	    	cstmt.setString(2, year);
	    	cstmt.setString(3, month);
	    	cstmt.setString(4, day);
	    	cstmt.setString(5, week);
	    	cstmt.setString(6, user.getFullname());
	    	cstmt.setString(7, project);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					iCount++;
					HTMLElement tr = new HTMLElement("tr");
					HTMLElement td0 = new HTMLElement(
							"td", 
							"popUpDetail","<img src=\"images/"+rs.getString(5)+"_circle.png\""+
									" height=\"15px\" width=\"15px\">");
					tr.appendValue(td0.toString());
					HTMLElement td1 = new HTMLElement("td", "popUpDetail", rs.getString(1));
					tr.appendValue(td1.toString());
					HTMLElement td2 = new HTMLElement("td", "popUpDetail", rs.getString(2));
					tr.appendValue(td2.toString());
					HTMLElement td3 = new HTMLElement("td", "popUpDetail", rs.getString(3));
					tr.appendValue(td3.toString());
					HTMLElement td4 = new HTMLElement("td", "popUpDetail", rs.getString(4));
					tr.appendValue(td4.toString());
					html.append(tr.toString());
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetIncidentDetail(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	    if (iCount==0) {
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td= new HTMLElement("td", "popUpDetail", "No incidents to display");
			td.setAttribute("colspan", "5");
			tr.appendValue(td.toString());
			html.append(tr.toString());
	    }
		return html.toString();	
	}
	
	public String getCompletionReportHeader(
			String action, String year, String month, String day, String week, 
			String client, String project, String site, String nrId,
			String startDT, String endDT, String completionType,
			String reportSite, String reportNrId, String reportDate, String reportType) {
		String title = "Closure report for ";
		if (reportSite.equals("")) {
			title = "";	
			String repSite = "", repNrId = "", repDate = "", repType = "";
			String dbAction = action;
			if (action.equals("chgMonth"))
				dbAction = "month";
			if (action.equals("chgDay"))
				dbAction = "day";
			if (action.equals("chgWeek"))
				dbAction = "week";
			message = null;
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
	    	try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetSiteCompletionReportListTop(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	   			cstmt.setString(1, dbAction);
	   			cstmt.setString(2, year);
	   			cstmt.setString(3, month);
	   			cstmt.setString(4, day);
	   			cstmt.setString(5, week);
	   			cstmt.setString(6, (client.equals("")?"All":client));
	   			cstmt.setString(7, (project.equals("")?"All":project));
	   			cstmt.setString(8, (site.equals("")?"All":site));
	   			cstmt.setString(9, (nrId.equals("")?"All":nrId));
	   			cstmt.setString(10, startDT);
	   			cstmt.setString(11, endDT);
	   			cstmt.setString(12, (completionType.equals("")?"All":completionType));
	   			cstmt.setString(13, user.getFullname());
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					if (rs.next()) {
						repSite = rs.getString(1);
						repNrId = rs.getString(2);
						repDate = rs.getString(3);
						repType = rs.getString(4);
						if (repType.equals("Completed")) {
							title = "Closure report for ";
						} else {
							title = repType + " report for ";
						}
						title = title + "site " + repSite + " on " + repDate;
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in GetSiteCompletionReportListTop(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	try {
		    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    }
		    	conn = null;
		    	cstmt = null;
		    }		
		} else {
			if (!reportType.equals("Completed")) title = reportType + " report for ";
			title = title + "site " + reportSite + " on " + reportDate;
		}		
		return title;
	}
	
	public String getCompletionReport(
			String action, String year, String month, String day, String week, 
			String client, String project, String site, String nrId,
			String startDT, String endDT, String completionType,
			String reportSite, String reportNrId, String reportDate, String reportType ) {
		String completionReport = "";	
		String repSite = "", repNrId = "", repDate = "", repType = "";
		String dbAction = action;
		if (action.equals("chgMonth"))
			dbAction = "month";
		if (action.equals("chgDay"))
			dbAction = "day";
		if (action.equals("chgWeek"))
			dbAction = "week";
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	if (reportType.length()>0) {
			repSite = reportSite;
			repNrId = reportNrId;
			repDate = reportDate;
			repType = reportType;
    	} else {
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetSiteCompletionReportListTop(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	   			cstmt.setString(1, dbAction);
	   			cstmt.setString(2, year);
	   			cstmt.setString(3, month);
	   			cstmt.setString(4, day);
	   			cstmt.setString(5, week);
	   			cstmt.setString(6, (client.equals("")?"All":client));
	   			cstmt.setString(7, (project.equals("")?"All":project));
	   			cstmt.setString(8, (site.equals("")?"All":site));
	   			cstmt.setString(9, (nrId.equals("")?"All":nrId));
	   			cstmt.setString(10, startDT);
	   			cstmt.setString(11, endDT);
	   			cstmt.setString(12, (completionType.equals("")?"All":completionType));;
	   			cstmt.setString(13, user.getFullname());
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					if (rs.next()) {
						repSite = rs.getString(1);
						repNrId = rs.getString(2);
						repDate = rs.getString(3);
						repType = rs.getString(4);
					}
				}
				else {
					repSite = reportSite;
					repNrId = reportNrId;
					repDate = reportDate;
					repType = reportType;
				}
		    } catch (Exception ex) {
		    	message = "Error in GetSiteCompletionReportListTop(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	try {
		    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    }
		    	conn = null;
		    	cstmt = null;
		    }		
    	}
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetSiteCompletionReport(?,?,?,?)}");
	    	cstmt.setString(1, repSite);
	    	cstmt.setString(2, repNrId);
	    	cstmt.setString(3, repDate);
	    	cstmt.setString(4, repType);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					completionReport = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetSiteCompletionReport(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		return completionReport;
	}
	
	public String getClientReportHTML(String action, String year, String month, String day, String week, String project) {
		StringBuilder html = new StringBuilder();
		String report = "&nbsp;", siteCounts = "&nbsp;";
		String dbAction = "Month";	
		if (action.endsWith("Week")) {
			dbAction = "Week";
			report = clientWeeklyReport(year,month,week,project);
		} else if (action.endsWith("Day")) {
			dbAction = "Day";
			report = clientDailyReport(year,month,day,project);
		}
		if (dbAction.equals("Month")) {
			report = clientMonthlyReport(year,month,project);
		}
		HTMLElement tr0 = new HTMLElement("tr");
		HTMLElement td0 = new HTMLElement(
				"td", 
				"dateSearchTop", 
				"&nbsp");
		td0.setAttribute("height", "10px");
		tr0.appendValue(td0.toString());
		html.append(tr0.toString());
		HTMLElement tra = new HTMLElement("tr");
		HTMLElement tda1 = new HTMLElement(
				"td", 
				"dateSearchTClass", 
				clientReportingHeader(action, year, month, day, week, project) );
		tda1.setAttribute("height", "20px");
		tda1.setAttribute("valign", "top");
		tda1.setAttribute("align", "center");
		tra.appendValue(tda1.toString());
		html.append(tra.toString());
		html.append(tr0.toString());
		HTMLElement trb = new HTMLElement("tr");
		HTMLElement tdb1 = new HTMLElement("td", "clientBox", report);
		tdb1.setAttribute("height", "405px");
		tdb1.setAttribute("width", "700px");
		tdb1.setAttribute("valign", "top");
		trb.appendValue(tdb1.toString());
		html.append(trb.toString());
		return html.toString();
	}
	
	public String clientMonthlyReport( String year, String month, String projectFilter ) {
		String report = 
				"<table style=\"table-layout:fixed;border-style:none;width:900px;\">"+
				"<colgroup>"+
				"<col width=\"50px\"/>"+
				"<col width=\"160px\"/>"+
				"<col width=\"160px\"/>"+
				"<col width=\"160px\"/>"+
				"<col width=\"160px\"/>"+
				"<col width=\"160px\"/>"+
				"<col width=\"50px\"/>"+
				"</colgroup><tbody><tr>"+
				"<td class=\"clientBox\" colspan=\"7\">"+
				"<img src=\"images/sc_monthly_report.png\" height=\"116px\" width=\"800px\"/></td>"+
				"</tr><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"repHead\">Week</td>"+
				"<td class=\"repHead\">Attempted</td>"+
				"<td class=\"repHead\">Completed</td>"+
				"<td class=\"repHead\">Partials</td>"+
				"<td class=\"repHead\">Aborts</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"</tr>";
		message = null;
		long attempted = 0, completed = 0, partials = 0, aborts = 0;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetCRMonthlyReport(?,?,?,?)}");
	    	cstmt.setString(1, year);
	    	cstmt.setString(2, month);
	    	cstmt.setString(3, user.getFullname());
	    	cstmt.setString(4, projectFilter);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					// report line
					report = report +
							"<tr><td class=\"clientBox\">&nbsp;</td>"+
							"<td class=\"weekCell\">"+
							rs.getString(1)+
							"</td><td class=\"totalCell\">"+
							rs.getString(2)+
							"</td><td class=\"totalCell\">"+
							rs.getString(3)+
							"</td><td class=\"totalCell\">"+
							rs.getString(4)+
							"</td><td class=\"totalCell\">"+
							rs.getString(5)+
							"</td></tr>";
					// increment counts
					attempted = attempted + rs.getLong(2);
					completed = completed + rs.getLong(3);
					partials = partials + rs.getLong(4);
					aborts = aborts + rs.getLong(5);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetCRMonthlyReport(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		report = report +				
				"</tbody></table>"+
				"<table style=\"table-layout:fixed;border-style:none;width:900px;\">"+
				"<colgroup>"+
				"<col width=\"50px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"50px\"/><tr>"+
				"</colgroup><tbody><tr>"+
				"<td class=\"clientBox\" colspan=\"9\">&nbsp;</td>"+
				"</tr><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBold\">Attempted</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBoldGreen\">Completed</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBoldAmber\">Partials</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBoldRed\">Aborts</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"</tr><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxGrey\">"+attempted+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxGreen\">"+completed+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxAmber\">"+partials+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxRed\">"+aborts+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"</tr></tbody></table>";
		return report;
	}
	
	public String clientWeeklyReport( String year, String month, String week, String projectFilter ) {
		String report = 
				"<div style=\"height:300px;overflow-y: auto; overflow-x: hidden\">"+
				"<table style=\"table-layout:fixed;border-style:none;width:900px;\">"+
				"<colgroup>"+
				"<col width=\"50px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"100px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"140px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"220px\"/>"+
				"<col width=\"40px\"/>"+
				"<col width=\"50px\"/>"+
				"</colgroup><tbody><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBox\" colspan=\"9\">"+
				"<img src=\"images/sc_weekly_report.png\" height=\"116px\" width=\"800px\"/></td>"+
				"</tr><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"repHead\">Day</td>"+
				"<td class=\"repHead\">Date</td>"+
				"<td class=\"repHead\">Site</td>"+
				"<td class=\"repHead\">Project</td>"+
				"<td class=\"repHead\">Work Type</td>"+
				"<td class=\"repHead\">Technologies</td>"+
				"<td class=\"repHead\">Status</td>"+
				"<td class=\"repHead\">Comments</td>"+
				"<td class=\"repHead\">Outage</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"</tr>";
		message = null;
		long attempted = 0, completed = 0, partials = 0, aborts = 0;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetClientReporting(?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, "Weekly");
	    	cstmt.setString(2, year);
	    	cstmt.setString(3, month);
	    	cstmt.setString(4, "01");
	    	cstmt.setString(5, week);
	    	cstmt.setString(6, user.getFullname());
	    	cstmt.setString(7, projectFilter);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					// report line
					report = report +
							"<tr><td class=\"clientBox\">&nbsp;</td>"+
							"<td class=\"totalCell\">"+
							rs.getString(1)+
							"</td><td class=\"totalCell\">"+
							rs.getString(2)+
							"</td><td class=\"totalCell\">"+
							rs.getString(3)+
							"</td><td class=\"totalCell\">"+
							rs.getString(4)+
							"</td><td class=\"totalCell\">"+
							rs.getString(5)+
							"</td><td class=\"totalCell\">"+
							rs.getString(6)+
							"</td><td class=\"totalCell\">"+
							rs.getString(7)+
							"</td><td class=\"totalCell\">"+
							rs.getString(8)+
							"</td><td class=\"totalCell\">"+
							rs.getString(9)+
							"</td></tr>";
					// increment relevant counts
					if (rs.getString(7).equals("Completed")) {
						completed++;
					} else if (rs.getString(7).equals("Partial")) {
						partials++;
					} else {
						aborts++;
					}
					attempted++;
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetClientReporting(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    }
		report = report +				
				"</tbody></table>"+
				"</div>"+
				"<table style=\"table-layout:fixed;border-style:none;width:900px;\">"+
				"<colgroup>"+
				"<col width=\"50px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"50px\"/><tr>"+
				"</colgroup><tbody><tr>"+
				"<td class=\"clientBox\" colspan=\"7\">&nbsp;</td>"+
				"</tr><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBold\">Attempted</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBoldGreen\">Completed</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBoldAmber\">Partials</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBoldRed\">Aborts</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"</tr><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxGrey\">"+attempted+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxGreen\">"+completed+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxAmber\">"+partials+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxRed\">"+aborts+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"</tr></tbody></table>";
		return report;
	}
	
	public String clientDailyReport( String year, String month, String day, String projectFilter ) {
		String report =
				"<div style=\"height:300px;overflow-y: auto; overflow-x: hidden\">"+
				"<table style=\"table-layout:fixed;border-style:none;width:900px;\">"+
				"<col width=\"50px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"140px\"/>"+
				"<col width=\"80px\"/>"+
				"<col width=\"200px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"40px\"/>"+				
				"<col width=\"40px\"/>"+				
				"<col width=\"50px\"/>"+
				"</colgroup><tbody><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBox\" colspan=\"9\">"+
				"<img src=\"images/sc_daily_report.png\" height=\"116px\" width=\"800px\"/></td>"+
				"</tr><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"repHead\">Site</td>"+
				"<td class=\"repHead\">Project</td>"+
				"<td class=\"repHead\">Work Type</td>"+
				"<td class=\"repHead\">Technologies</td>"+
				"<td class=\"repHead\">Status</td>"+
				"<td class=\"repHead\">Comments</td>"+
				"<td class=\"repHead\">Owner</td>"+
				"<td class=\"repHead\">Lock</td>"+
				"<td class=\"repHead\">Unlock</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<col width=\"50px\"/>"+
				"<col width=\"40px\"/>"+
				"<col width=\"80px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"90px\"/>"+
				"<col width=\"60px\"/>"+
				"<col width=\"150px\"/>"+
				"<col width=\"40px\"/>"+
				"<col width=\"40px\"/>"+				
				"<col width=\"40px\"/>"+				
				"<col width=\"50px\"/>"+
				"</colgroup><tbody>";		
		message = null;
		long attempted = 0, completed = 0, partials = 0, aborts = 0;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetClientReporting(?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, "Daily");
	    	cstmt.setString(2, year);
	    	cstmt.setString(3, month);
	    	cstmt.setString(4, day);
	    	cstmt.setString(5, "01");
	    	cstmt.setString(6, user.getFullname());
	    	cstmt.setString(7, projectFilter);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					// report line
					report = report +
							"<tr><td class=\"clientBox\">&nbsp;</td>"+
							"<td class=\"totalCell\">"+
							rs.getString(1)+
							"</td><td class=\"totalCell\">"+
							rs.getString(2)+
							"</td><td class=\"totalCell\">"+
							rs.getString(3)+
							"</td><td class=\"totalCell\">"+
							rs.getString(4)+
							"</td><td class=\"totalCell\">"+
							rs.getString(5)+
							"</td><td class=\"totalCell\">"+
							rs.getString(6)+
							"</td><td class=\"totalCell\">"+
							rs.getString(7)+
							"</td><td class=\"totalCell\">"+
							rs.getString(8)+
							"</td><td class=\"totalCell\">"+
							rs.getString(9)+
							"</td></tr>";
					// increment relevant counts
					if (rs.getString(5).equals("Completed")) {
						completed++;
					} else if (rs.getString(5).equals("Partial")) {
						partials++;
					} else {
						aborts++;
					}
					attempted++;
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetCRMonthlyReport(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		report = report +				
				"</tbody></table>"+
				"</div>"+
				"<table style=\"table-layout:fixed;border-style:none;width:900px;\">"+
				"<colgroup>"+
				"<col width=\"50px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"120px\"/>"+
				"<col width=\"110px\"/>"+
				"<col width=\"50px\"/><tr>"+
				"</colgroup><tbody><tr>"+
				"<td class=\"clientBox\" colspan=\"7\">&nbsp;</td>"+
				"</tr><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBold\">Attempted</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBoldGreen\">Completed</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBoldAmber\">Partials</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"clientBoxBoldRed\">Aborts</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"</tr><tr>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxGrey\">"+attempted+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxGreen\">"+completed+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxAmber\">"+partials+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"<td class=\"totalBoxRed\">"+aborts+"</td>"+
				"<td class=\"clientBox\">&nbsp;</td>"+
				"</tr></tbody></table>";
		return report;
	}
	
	public String[] GetLastCompletedSite() {
		String[] results = new String[4];
		String message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetLastCompletedSite(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					results[0] = rs.getString(1);
					results[1] = rs.getString(2);
					results[2] = rs.getString(3);
					results[3] = rs.getString(4);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetLastCompletedSite(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		return results;
	}
	
	public String getSiteSummaryHTML( String site, String nrId) {
		String report = "";
		if ((site.length()>0)||(nrId.length()>0 )) {
			report = 
				"<table style=\"table-layout:fixed;border-style:none;width:300px;\">"+
						"<col width=\"20px\"/>"+
						"<col width=\"70px\"/>"+
						"<col width=\"70px\"/>"+
						"<col width=\"70px\"/>"+
						"<col width=\"70px\"/>"+
				"</colgroup><tbody>";
			String outSite="", status = "", scheduledDate = "", implementationStatus = "",
					impStartDT = "", impEndDT = "", outageStartDT = "", outageEndDT = "",
					client = "", project = "", migType = "", BO = "", FE = "";
			message = null;
			long completed = 0, partials = 0, aborts = 0;
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetSiteSummary(?,?)}");
		    	cstmt.setString(1, site);
		    	cstmt.setString(2, nrId);
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						outSite = rs.getString(1);
						status = rs.getString(2);
						scheduledDate = rs.getString(3);
						implementationStatus = rs.getString(4);
						impStartDT = rs.getString(5); 
						impEndDT = rs.getString(6);
						outageStartDT = rs.getString(7); 
						outageEndDT = rs.getString(8);
						client = rs.getString(9); 
						project = rs.getString(10);
						migType = rs.getString(11); 
						BO = rs.getString(12); 
						FE = rs.getString(13);
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in GetSiteSummary(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } finally {
		    	try {
		    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    		if ((conn != null) && (!conn.isClosed())) conn.close();
			    } catch (SQLException ex) {
			    	ex.printStackTrace();
			    }
		    } 
			report = report +
					"<tr>"+
					"<td>&nbsp;</td>"+
					"</tr><tr>"+
					"<td>&nbsp;</td>"+
					"<td class=\"summaryLit\">Site:</td>"+
					"<td class=\"summaryDet\">"+outSite+"</td>"+
					"<td class=\"summaryLit\">Status:</td>"+
					"<td class=\"summaryDet\">"+status+"</td>"+
					"</tr><tr>"+
					"<td>&nbsp;</td>"+
					"<td class=\"summaryLit\">Client:</td>"+
					"<td colspan=\"3\" class=\"summaryDet\">"+client+"</td>"+
					"</tr><tr>"+
					"<td>&nbsp;</td>"+
					"<td class=\"summaryLit\">Project:</td>"+
					"<td colspan=\"3\" class=\"summaryDet\">"+project+"</td>"+
					"</tr><tr>"+
					"<td>&nbsp;</td>"+
					"<td class=\"summaryLit\">Scheduled Date:</td>"+
					"<td class=\"summaryDet\">"+scheduledDate+"</td>"+
					"<td class=\"summaryLit\">Migration Type:</td>"+
					"<td class=\"summaryDet\">"+migType+"</td>"+
					"</tr><tr>"+
					"<td>&nbsp;</td>"+
					"<td colspan=\"2\" class=\"summaryLit\">Implementation:</td>"+
					"<td colspan=\"2\" class=\"summaryDet\">"+implementationStatus+"</td>"+
					"</tr><tr>"+
					"<td>&nbsp;</td>"+
					"<td class=\"summaryLit\">Imp. Start:</td>"+
					"<td class=\"summaryDet\">"+impStartDT+"</td>"+
					"<td class=\"summaryLit\">Imp. End:</td>"+
					"<td class=\"summaryDet\">"+impEndDT+"</td>"+
					"</tr><tr>"+
					"<td>&nbsp;</td>"+
					"<td class=\"summaryLit\">Outage Start:</td>"+
					"<td class=\"summaryDet\">"+outageStartDT+"</td>"+
					"<td class=\"summaryLit\">Outage End:</td>"+
					"<td class=\"summaryDet\">"+outageEndDT+"</td>"+
					"<td>&nbsp;</td>"+
					"</tr><tr>"+
					"<td>&nbsp;</td>"+
					"<td class=\"summaryLit\">BO:</td>"+
					"<td colspan=\"3\" class=\"summaryDet\">"+BO+"</td>"+
					"</tr><tr>"+
					"<td>&nbsp;</td>"+
					"<td class=\"summaryLit\">FE:</td>"+
					"<td colspan=\"3\" class=\"summaryDet\">"+FE+"</td>"+
					"</tr>"+				
				"</tbody></table>";
		}
		return report;
	}
	
	private boolean getReportingBO(long userId) {
		boolean hasReporting = false;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call ReportingBO(?)}");
   			cstmt.setLong(1, userId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					if (rs.getString(1).equals("Y")) {
						hasReporting = true;
					}
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
		return hasReporting;
	}

	
	public ArrayList<SNRTechnology> getSNRBOTechnologiesToDel(long snrId) {
		ArrayList<SNRTechnology> al = new ArrayList<SNRTechnology>();
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetSNRBOTechnologiesToDel(?)}");
			cstmt.setLong(1, snrId);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					al.add(new SNRTechnology(snrId,
						rs.getString(2), rs.getString(3),
						rs.getLong(1)));
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
		
		return al;
	}
	
	public String getSNRBOTechnologiesToDelHTML(HttpSession session, long snrId) {
		StringBuilder html = new StringBuilder();
		Collection<SNRTechnology> snrTL = getSNRBOTechnologiesToDel(snrId);
		session.setAttribute(ServletConstants.SNR_BO_TECHNOLOGY_DEL_COLLECTION_NAME_IN_SESSION, snrTL);
		for (Iterator<SNRTechnology> it = snrTL.iterator(); it.hasNext(); ) {
			SNRTechnology snrT = it.next();
			HTMLElement div = new HTMLElement("div");
			div.setAttribute("style", "padding-bottom:10px");
			HTMLElement check = new HTMLElement("input");
			check.setAttribute("type", "checkbox");
			check.setAttribute("name", "checkTech" + snrT.getTechnologyId());
			check.setAttribute("id", "checkTech" + snrT.getTechnologyId());
			check.setAttribute("value", String.valueOf(snrT.getTechnologyId()));
			div.appendValue(check.toString() + " " + snrT.getTechnologyNameDisplay());
			html.append(div.toString());
		}
		return html.toString();
	}
	
	public Collection<LiveDashboardSite> getFELiveDashboardSites() {
		message = null;
		ArrayList<LiveDashboardSite> LiveDashboardSite = new ArrayList<LiveDashboardSite>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetFELiveDashboardSites(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					LiveDashboardSite.add(new LiveDashboardSite(
							rs.getString(1),  rs.getString(2),  rs.getString(3),  rs.getString(4), 
							rs.getString(5),  rs.getString(6),  rs.getString(7),  rs.getString(8),
							rs.getString(9),  rs.getString(10), rs.getString(11), rs.getString(12),
							rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), 
							rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
							rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24),
							rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28),
							rs.getString(29), rs.getString(30), rs.getString(31), rs.getLong(32),
							rs.getString(33), rs.getString(34),rs.getString(35)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getFELiveDashboardSites(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return LiveDashboardSite;
	}
	
	public String getFELiveDashboardSitesHTML() {
		StringBuilder html = new StringBuilder();
		String refreshHTML = 
				"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
				"<col width=\"5%\"/>" +
				"<col width=\"90%\"/>" +
				"<col width=\"5%\"/>" +
				"</colgroup>" +
				"<tbody>" +
				"<tr>" +
				"<td></td>"+
				"<td height=\"60px\" class=\"FERefresh\" " +
				"style=\"cursor:pointer;\" onclick=\"refresh()\" "+
				"title=\"Press to pick up any changes\">" +
				"Refresh"+
				"</td>" +
				"<td></td>"+
				"</tr>" +
				"</tbody></table>";
		String separatorBlankHTML = 
				"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
				"<col width=\"100%\"/>" +
				"</colgroup>" +
				"<tbody>" +
				"<tr><td height=\"5px\"></td></tr>" +
				"<tr><td height=\"5px\"></td></tr>" +
				"<tr><td height=\"5px\"></td></tr>" +
				"</tbody></table>";
		Collection<LiveDashboardSite> LiveDashboardSite = getFELiveDashboardSites();		
		if (LiveDashboardSite.isEmpty()) {
			String emptyHTML = 
					"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
							"<col width=\"100%\"/>" +
							"</colgroup>" +
							"<tbody>" +
							"<tr>" +
							"<td class=\"ldFEHead\">";	
			if (message != null) {
				emptyHTML = emptyHTML + message;
			} else {
				emptyHTML = emptyHTML + "No sites currrently allocated to you";
			}
			emptyHTML = emptyHTML +
					"</td>" +
					"</tr>" +
					"</tbody></table>";
			html.append(emptyHTML);
		} else {
			for (Iterator<LiveDashboardSite> it = LiveDashboardSite.iterator(); it.hasNext(); ) {
				LiveDashboardSite lds = it.next();
				boolean oddRow = false;
				String separatorGrayHTML = 
						"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
						"<col width=\"100%\"/>" +
						"</colgroup>" +
						"<tbody>" +
						"<tr><td height=\"5px\"></td></tr>" +
						"<tr><td height=\"5px\" class=\"ldFEThinGray\"></td></tr>" +
						"<tr><td height=\"5px\"></td></tr>" +
						"</tbody></table>";
				String siteHTML = 
						"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
						"<col width=\"10%\"/>" +
						"<col width=\"40%\"/>" +
						"<col width=\"0%\"/>" +
						"<col width=\"40%\"/>" +
						"<col width=\"10%\"/>" +
						"</colgroup>" +
						"<tbody>" +
						"<tr>"; 
				String expandCollapse = getFESiteExpandCollapse( user.getFullname(), lds.getSite() );
				String feStatus = "white", feComment = "Not Started";
				if (	(lds.getOverallStatus().equals("Completed")) || 
						(lds.getOverallStatus().equals("Partial"))|| 
						(lds.getOverallStatus().equals("Performance IP")) ) {
					feStatus = "green";
					feComment = "Completed";
				} else if (lds.getOverallStatus().startsWith("In Progress")) {
					feStatus = "orange";
					feComment = "In Progress";
				} else if (	(lds.getOverallStatus().equals("Aborted")) ||
							(lds.getOverallStatus().equals("Waiting Decision")) ){
					feStatus = "red";
					feComment = "Waiting Decision or Aborted";
				}
				siteHTML = siteHTML +
						"<td class=\"ldFEHeadSite"+(lds.getFeNo().equals("2")?"RO":"")+"\" height=\"60px\" >" +
						"<img height=\"50px\" width=\"50px\" width src=\"images/"+feStatus+"FE.png\" "+
						"title=\""+feComment+"\">"+
						"</td>" +
						"<td class=\"ldFEHeadSite"+(lds.getFeNo().equals("2")?"RO":"")+"\" id=\"anchor"+lds.getSnrId()+"\">" +
						lds.getSite() +
						"</td><td class=\"ldFEHeadSite"+(lds.getFeNo().equals("2")?"RO":"")+"\">&nbsp;" +
						"</td><td class=\"ldFEHeadSite"+(lds.getFeNo().equals("2")?"RO":"")+"\">" +
						lds.getPostcode() +
						"</td><td class=\"ldFEHeadSite"+(lds.getFeNo().equals("2")?"RO":"")+"\" "+
						"title=\"" +
						(expandCollapse.equals("C")?"Show site details":"Hide site details") +
						"\" style=\"cursor:pointer;\" "+
						"onClick=\"toggleExpandCollapse('"+lds.getSite()+"')\">" +
						"<img src=\"images/" +
						(expandCollapse.equals("C")?"show.png":"hide.png") +
						"\" height=\"40px\" width=\"40px\" />" +
						"</td>" +
						"</tr>" +
						"</tbody></table>";
				html.append(siteHTML);
				html.append(separatorBlankHTML);
				if (expandCollapse.equals("E")) {
					String actionsHTML =
							"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
							"<col width=\"4%\"/>" +
							"<col width=\"27%\"/>" +
							"<col width=\"5%\"/>" +
							"<col width=\"27%\"/>" +
							"<col width=\"5%\"/>" +
							"<col width=\"27%\"/>" +
							"<col width=\"5%\"/>" +
							"</colgroup>" +
							"<tbody>" +
							"<tr><td></td>" +
							"<td height=\"60px\" class=\"FEAction\" "+
							"title=\"Click for site work details\" " +
							"style=\"cursor:pointer;\" " +
							"onclick=\"workDetails('"+lds.getSnrId()+"')\">"+
							"Work Details" +
							"</td><td></td>" +
							"<td height=\"60px\" class=\"FEAction\" "+
							"title=\"Click for site access details\" " +
							"style=\"cursor:pointer;\" " +
							"onclick=\"accessDetails('"+lds.getSnrId()+"')\">"+
							"Access Details" +
							"</td><td></td>" +
							"<td height=\"60px\" class=\"FEAction\" "+
							"title=\"Click for site CRQ Details\"\" " +
							"style=\"cursor:pointer;\" " +
							"onclick=\"crqDetails('"+lds.getSnrId()+"')\">"+
							"CRQ Details" +
							"</td><td></td></tr></tbody></table>";
					html.append(actionsHTML);
					html.append(separatorBlankHTML);
					String siteDetailHTML = 
							"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
							"<col width=\"40%\"/>" +
							"<col width=\"60%\"/>" +
							"</colgroup>" +
							"<tbody>";
					siteDetailHTML = siteDetailHTML +
							"<tr><td height=\"40px\" class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							"Client:" +
							"</td><td class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							lds.getCustomer() +
							"</td></tr>";
					oddRow = !oddRow;
					siteDetailHTML = siteDetailHTML +
							"<tr><td height=\"40px\" class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							"Project:" +
							"</td><td class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							lds.getProject() +
							"</td></tr>";
					oddRow = !oddRow;
					siteDetailHTML = siteDetailHTML +
							"<tr><td height=\"40px\" class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							"Work Type:" +
							"</td><td class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							lds.getMigrationType() +
							"</td></tr>";
					oddRow = !oddRow;
					siteDetailHTML = siteDetailHTML +
							"<tr><td height=\"40px\" class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							"BO Engineer:" +
							"</td><td class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							lds.getBoList() +
							"</td></tr>";
					oddRow = !oddRow;
					siteDetailHTML = siteDetailHTML +
							"<tr><td height=\"40px\" class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							"Contact No.:" +
							"</td><td class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							lds.getBoContactNo() +
							"</td></tr>";
					oddRow = !oddRow;
					siteDetailHTML = siteDetailHTML +
							"<tr><td height=\"40px\" class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+"\">" +
							"Overall Status:" +
							"</td><td class=\""+(oddRow?"ldFEGrid1":"ldFEGrid2")+lds.getOverallStatusColour()+"\">" +
							lds.getOverallStatus() +
							"</td></tr>";
					siteDetailHTML = siteDetailHTML + 
							"</td></tr></tbody></table>";
					html.append(siteDetailHTML);
					html.append(separatorBlankHTML);
					String startingHTML =
						"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
						"<col width=\"19%\"/>" +
						"<col width=\"1%\"/>" +
						"<col width=\"19%\"/>" +
						"<col width=\"1%\"/>" +
						"<col width=\"19%\"/>" +
						"<col width=\"1%\"/>" +
						"<col width=\"19%\"/>" +
						"<col width=\"1%\"/>" +
						"<col width=\"19%\"/>" +
						"<col width=\"1%\"/>" +
						"</colgroup>" +
						"<tbody>" +
						"<tr>" +
						"<td height=\"140px\" class=\"ldFE"+lds.getCheckedIn()+"\" "+
						"title=\"Checked In (FE/BO)\" " +
						"style=\"cursor:pointer;\" " +
						(lds.getFeNo().equals("2")?"":
						"onClick=\"updateProgress('checkedIn','"+lds.getSnrId()+"','"+lds.getCheckedIn()+"')\"") +
						">Checked<br>In" +
						"</td>" +
						"<td></td>" +
						"<td height=\"140px\" class=\"ldFE"+lds.getBookedOn()+"\" "+
						"title=\"Site Booked On (FE/BO)\" " +
						"style=\"cursor:pointer;\" " +
						(lds.getFeNo().equals("2")?"":
						"onClick=\"updateProgress('bookedOn','"+lds.getSnrId()+"','"+lds.getBookedOn()+"')\"") +
						">Site<br>Booked<br>On" +
						"</td>" +
						"<td></td>" +
						"<td height=\"140px\" class=\"ldFE"+lds.getSiteAccessed()+"\" "+
						"title=\"Site Accessed (FE)\" " +
						"style=\"cursor:pointer;\" " +	
						(lds.getFeNo().equals("2")?"":					
						"onClick=\"updateProgress('siteAccessed','"+lds.getSnrId()+"','"+lds.getSiteAccessed()+"')\"") +
						">Site<br>Accessed" +
						"</td>" +
						"<td></td>" +
						"<td height=\"140px\" class=\"ldFE"+lds.getPhysicalChecks()+"\" "+
						"title=\"Physical Checks (FE)\" " +
						"style=\"cursor:pointer;\" " +
						(lds.getFeNo().equals("2")?"":
						"onClick=\"updateProgress('physicalChecks','"+lds.getSnrId()+"','"+lds.getPhysicalChecks()+"')\"") +
						"Physical<br>Checks" +
						"</td>" +
						"<td></td>" +
						"<td height=\"140px\" class=\"ldFE"+lds.getPreCallTest()+"\" "+
						"title=\"Pre Call Test (FE)\" " +
						"style=\"cursor:pointer;\" " +
						(lds.getFeNo().equals("2")?"":
						"onClick=\"updateProgress('preCallTest','"+lds.getSnrId()+"','"+lds.getPreCallTest()+"')\"") +
						">Pre-Call<br> Test" +
						"</td>" +
						"<td></td>" +
						"</tr>" +
						"</tbody></table>";
					html.append(startingHTML);
					html.append(separatorGrayHTML);
					String progress1HTML =
							"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"</colgroup>" +
							"<tbody>" +
							"<tr>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getSiteLocked()+"\" "+
							"title=\"Site Locked (BO/FE)\" " +
							"style=\"cursor:pointer;\" " +
							(lds.getFeNo().equals("2")?"":
							"onClick=\"updateProgress('siteLocked','"+lds.getSnrId()+"','"+lds.getSiteLocked()+"')\"") +
							">Site<br>Locked" +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getHwInstalls()+"\" "+
							"title=\"HW Installed (FE)\" " +
							"style=\"cursor:pointer;\" " +
							(lds.getFeNo().equals("2")?"":
							"onClick=\"updateProgress('hwInstalls','"+lds.getSnrId()+"','"+lds.getHwInstalls()+"')\"") +
							">HW<br>Installed" +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getCommissioningFE()+"\" "+
							"title=\"Field Commissioning (FE)\" " +
							"style=\"cursor:pointer;\" " +
							(lds.getFeNo().equals("2")?"":
							"onClick=\"updateProgress('commissioningFE','"+lds.getSnrId()+"','"+lds.getCommissioningFE()+"')\"") +
							"Field<br>Comm." +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getCommissioningBO()+"BO\" "+
							"title=\"Back Office Commissioning (BO)\">" +
							"Back<br>Office<br>Comm." +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getTxProvisioning()+"BO\" "+
							"title=\"Tx Provisioning (Client))\">" +
							"Tx Prov." +
							"</td>" +
							"<td></td>" +
							"</tr>" +
							"</tbody></table>";
					html.append(progress1HTML);	
					String progress2HTML =
							"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
							"<col width=\"10.5%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"10.5%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"10.5%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"11.5%\"/>" +
							"</colgroup>" +
							"<tbody>" +
							"<tr><td height=\"15px\"></td></tr>"+
							"<tr>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getFieldWork()+"\" "+
							"title=\"Field Work (FE)\" " +
							"style=\"cursor:pointer;\" " +
							(lds.getFeNo().equals("2")?"":
							"onClick=\"updateProgress('fieldWork','"+lds.getSnrId()+"','"+lds.getFieldWork()+"')\"") +
							">Field<br>Work" +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getSiteUnlocked()+"\" "+
							"title=\"Site Unlocked (BO/FE)\" " +
							"style=\"cursor:pointer;\" " +
							(lds.getFeNo().equals("2")?"":
							"onClick=\"updateProgress('siteUnlocked','"+lds.getSnrId()+"','"+lds.getSiteUnlocked()+"')\"") +
							">Site<br>Unlocked" +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getPostCallTest()+"\" "+
							"title=\"Post Call test (FE)\" " +
							"style=\"cursor:pointer;\" " +
							(lds.getFeNo().equals("2")?"":
							"onClick=\"updateProgress('postCallTest','"+lds.getSnrId()+"','"+lds.getCommissioningFE()+"')\"") +
							">Post-Call<br>Test" +
							"</td><td></td></tr></tbody></table>";
					html.append(progress2HTML);					
					html.append(separatorGrayHTML);
					String completingHTML = 
							"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"<col width=\"19%\"/>" +
							"<col width=\"1%\"/>" +
							"</colgroup>" +
							"<tbody>" +
							"<tr>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getClosureCode()+"BO\" "+
							"title=\"Closure Code (BO)\">" +
							"Closure<br>Code" +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getLeaveSite()+"\" "+
							"title=\"Left Site (BO/FE)\" " +
							"style=\"cursor:pointer;\" " +
							(lds.getFeNo().equals("2")?"":
							"onClick=\"updateProgress('leaveSite','"+lds.getSnrId()+"','"+lds.getLeaveSite()+"') \"") +
							">Leave<br>Site" +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getBookOffSite()+"\" "+
							"title=\"Booked Off Site (FE)\" " +
							"style=\"cursor:pointer;\" " +
							(lds.getFeNo().equals("2")?"":
							"onClick=\"updateProgress('bookOffSite','"+lds.getSnrId()+"','"+lds.getBookOffSite()+"') \")") +
							">Booked<br>Off<br> Site" +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getPerformanceMonitoring()+"BO\" "+
							"title=\"Performance (BO)\">" +
							"Prf" +
							"</td>" +
							"<td></td>" +
							"<td height=\"140px\" class=\"ldFE"+lds.getInitialHOP()+"BO\" "+
							"title=\"Hand Over Pack (BO)\">" +
							"Hand<br> Over<br>Pack" +
							"</td>" +
							"</tr>" +
							"</tbody></table>";				
					html.append(completingHTML);			
					html.append(separatorBlankHTML);
					html.append(refreshHTML);			
					html.append(separatorBlankHTML);
					String issuesHTML = 
							"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
									"<col width=\"2%\"/>" +
									"<col width=\"40%\"/>" +
									"<col width=\"16%\"/>" +
									"<col width=\"40%\"/>" +
									"<col width=\"2%\"/>" +
							"</colgroup>" +
							"<tbody>" +
							"<tr>" +
							"<td></td>" +
							"<td height=\"80px\" class=\"ldFE"+lds.getDevoteamIssue()+"\" "+
							"title=\"Devoteam Issue\"\">" +
							"Devoteam<br>Issue" +
							"</td>" +
							"<td></td>" +
							"<td height=\"80px\" class=\"ldFE"+lds.getCustomerIssue()+"\" "+
							"title=\"Vodafone<br>Issue\"\">" +
							"Vodafone<br> Issue" +
							"</td>"+
							"<td></td>" +
							"</tr></tbody></table>";			
					html.append(issuesHTML);
					html.append(separatorBlankHTML);				
				}	
			}
		}
		return html.toString();
	}
	
	public String getFESiteExpandCollapse( String username, String site) {
		String expandCollapse = "E";
		String message = null; 
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetFESiteExpandCollapse(?,?)}");
	    	cstmt.setString(1, username);
	    	cstmt.setString(2, site);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					expandCollapse = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetFESiteExpandCollapse(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	 
		return expandCollapse;
	}
	
	public String toggleFESiteExpandCollapse( String username, String site) {
		String result = "";
		String message = null; 
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call ToggleFESiteExpandCollapse(?,?)}");
	    	cstmt.setString(1, username);
	    	cstmt.setString(2, site);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					result = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetFESiteExpandCollapse(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	 
		return result;
	}
	
	public String getProgressItemStatusAllowed(String operation, String snrId, String status) {
		String allowed = "X";
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetProgressItemStatusAllowed(?,?,?)}");
	    	cstmt.setString(1, operation);
	    	cstmt.setString(2, snrId);
	    	cstmt.setString(3, status);
	    	if (snrId.equals(""))
	    		cstmt.setString(2, "-1");	    	
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					allowed = rs.getString(1);
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
		return allowed;
	}
	
	public String getProgressItemStatusHTML(String operation, String snrId) {
    	Connection conn = null;
    	CallableStatement cstmt = null;
    	Select select = new Select("selectProgressItemStatus",  "filter");
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetProgressItemStatusList(?,?)}");
	    	cstmt.setString(1, operation);
	    	cstmt.setString(2, snrId);
	    	if (snrId.equals(""))
	    		cstmt.setString(2, "-1");
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}
	
	public String getFEAccessDetails(String snrId) {
		StringBuilder html = new StringBuilder();
		if (!snrId.equals("")) {
			Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetFEAccessDetails(?)}");
		    	cstmt.setString(1, snrId);
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						String siteAccessInformation = rs.getString(1);
						String accessStatus = rs.getString(2);
						String accessConfirmed = rs.getString(3);
						String vfArrangeAccess = rs.getString(4);
						String tefOutageRequired = rs.getString(5);
						String outagePeriod = rs.getString(6);
						String permitType = rs.getString(7);
						String site = rs.getString(8);
						String siteHeader =
								"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
								"<col width=\"100%\"/>"+
								"</colgroup>" +
								"<tbody>" +
								"<tr><td class=\"FEAccessCRQTitle\">Site: "+site+
								"<div class=\"FeX\" title=\"close\" "+
								"onClick=\"feAccessDetailsClick('close')\">X</div>" +
								"</td><tr></tbody></table>";
						html.append(siteHeader);
						String accessInfo = 
								"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
								"<col width=\"100%\"/>"+
								"</colgroup>" +
								"<tbody>" +
								"<tr><td class=\"ldFEGrid1\">Site Access Information</td><tr>" +
								"<tr><td class=\"ldFEGrid2Bold\">"+siteAccessInformation+"</td><tr>"+
								"</tbody></table>";
						html.append(accessInfo);
						String status = 
								"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
								"<col width=\"100%\"/>"+
								"</colgroup>" +
								"<tbody>" +
								"<tr><td class=\"ldFEGrid1\">Access Status</td><tr>" +
								"<tr><td class=\"ldFEGrid2Bold\">"+accessStatus+"</td><tr>"+
								"</tbody></table>";
						html.append(status);
						String body = 
								"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
								"<col width=\"50%\"/>"+
								"<col width=\"50%\"/>"+
								"</colgroup>" +
								"<tbody><tr>"+
								"<td class=\"ldFEGrid1\">Access Confirmed?</td>"+
								"<td class=\"ldFEGrid1\">VF Arranged?</td>"+
								"</tr><tr>"+
								"<td class=\"ldFEGrid2Bold\">"+accessConfirmed+"</td>"+
								"<td class=\"ldFEGrid2Bold\">"+vfArrangeAccess+"</td>"+
								"</tr><tr>"+
								"<td class=\"ldFEGrid1\">TEF Outage Required?</td>"+
								"<td class=\"ldFEGrid1\">Outage Period</td>"+
								"</tr><tr>"+
								"<td class=\"ldFEGrid2Bold\">"+tefOutageRequired+"</td>"+
								"<td class=\"ldFEGrid2Bold\">"+outagePeriod+"</td>"+
								"</tr></tbody></table>";
						html.append(body);
						String permit = 
								"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
								"<col width=\"100%\"/>"+
								"</colgroup>" +
								"<tbody>" +
								"<tr><td class=\"ldFEGrid1\">Permit Type</td><tr>" +
								"<tr><td class=\"ldFEGrid2Bold\">"+permitType+"</td><tr>"+
								"</tbody></table>";
						html.append(permit);
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
		}    	
		return html.toString();
	}
	
	public String getFECRQDetails(String snrId) {
		StringBuilder html = new StringBuilder();
		if (!snrId.equals("")) {
			Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetFECRQDetails(?)}");
		    	cstmt.setString(1, snrId);
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						String vfCRQ = rs.getString(1);
						String crqStatus = rs.getString(2);
						String crqStart = rs.getString(3);
						String crqEnd = rs.getString(4);
						String tefOutageRequired = rs.getString(5);
						String tefCRQ = rs.getString(6);
						String outagePeriod = rs.getString(7);
						String technologies = rs.getString(8);
						String site = rs.getString(9);
						String siteHeader =
								"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
								"<col width=\"100%\"/>"+
								"</colgroup>" +
								"<tbody>" +
								"<tr><td class=\"FEAccessCRQTitle\">Site: "+site+
								"<div class=\"FeX\" title=\"close\" "+
								"onClick=\"feCRQDetailsClick('close')\">X</div>" +
								"</td><tr></tbody></table>";
						html.append(siteHeader);
						String crqBody = 
								"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
								"<col width=\"50%\"/>"+
								"<col width=\"50%\"/>"+
								"</colgroup>" +
								"<tbody><tr>"+
								"<td colspan=\"2\" class=\"ldFEGrid1\">CRQ Status</td>"+
								"</tr><tr>"+
								"<td colspan=\"2\" class=\"ldFEGrid2Bold\">"+crqStatus+"</td>"+
								"</tr><tr>"+
								"<td class=\"ldFEGrid1\">VF CRQ</td>"+
								"<td class=\"ldFEGrid1\">TEF CRQ</td>"+
								"</tr><tr>"+
								"<td class=\"ldFEGrid2Bold\">"+vfCRQ+"</td>"+
								"<td class=\"ldFEGrid2Bold\">"+tefCRQ+"</td>"+
								"</tr><tr>"+
								"<td class=\"ldFEGrid1\">TEF Outage Required?</td>"+
								"<td class=\"ldFEGrid1\">Outage Period</td>"+
								"</tr><tr>"+
								"<td class=\"ldFEGrid2Bold\">"+tefOutageRequired+"</td>"+
								"<td class=\"ldFEGrid2Bold\">"+outagePeriod+"</td>"+
								"</tr><tr>"+
								"<td class=\"ldFEGrid1\">VF CRQ Start</td>"+
								"<td class=\"ldFEGrid1\">VF CRQ End</td>"+
								"</tr><tr>"+
								"<td class=\"ldFEGrid2Bold\">"+crqStart+"</td>"+
								"<td class=\"ldFEGrid2Bold\">"+crqEnd+"</td>"+
								"</tr><tr>"+
								"<td colspan=\"2\" class=\"ldFEGrid1\">Technologies</td>"+
								"</tr><tr>"+
								"<td colspan=\"2\" class=\"ldFEGrid2Bold\">"+technologies+"</td>"+
								"</tr></tbody></table>";
						html.append(crqBody);
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
		}    	
		return html.toString();
	}
	
	public String getFEWorkDetails(String snrId) {
		StringBuilder html = new StringBuilder();
		if (!snrId.equals("")) {
			Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetFEWorkDetails(?)}");
		    	cstmt.setString(1, snrId);
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						String workDetails = rs.getString(1);
						String site = rs.getString(2);
						String siteHeader =
								"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
								"<col width=\"100%\"/>"+
								"</colgroup>" +
								"<tbody>" +
								"<tr><td class=\"FEAccessCRQTitle\">Site: "+site+
								"<div class=\"FeX\" title=\"close\" "+
								"onClick=\"feWorkDetailsClick('close')\">X</div>" +
								"</td><tr></tbody></table>";
						html.append(siteHeader);
						String wdBody = 
								"<table style=\"table-layout:fixed;border-style:none;width:100%;\">"+
								"<col width=\"100%\"/>"+
								"</colgroup>" +
								"<tbody><tr>"+
								"<td class=\"ldFEGrid1\">Work Details</td>"+
								"</tr><tr>"+
								"<td height=\"380px\"class=\"ldFEGrid2Bold\" valign=\"top\">"+workDetails+"</td>"+
								"</tr></tbody></table>";
						html.append(wdBody);
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
		}    	
		return html.toString();
	}
	
	public String updateProgressItemStatus(
			String operation,
			long snrId,
			String newStatus,
			String lastUpdatedBy ) {
		String updateResult = "Error: Untrapped error with UpdateProgressItemStatus";
		String message = null; 
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call UpdateProgressItemStatus(?,?,?,?)}");
	    	cstmt.setString(1, operation);
	    	cstmt.setLong(2, snrId);
	    	cstmt.setString(3, newStatus);
	    	cstmt.setString(4, lastUpdatedBy);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					updateResult = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in UpdateProgressItemStatus(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	 
		return updateResult;
	}
	
	public String operationDisp( String operation) {
		String result = operation;
		if (operation.equals("checkedIn"))
			result = "Checked In";
		else if (operation.equals("bookedOn"))
			result = "Site Booked On";
		else if (operation.equals("siteAccessed"))
			result = "Site Accessed";
		else if (operation.equals("physicalChecks"))
			result = "Physical Checks";
		else if (operation.equals("preCallTest"))
			result = "Pre Call Test";
		else if (operation.equals("siteLocked"))
			result = "Site Locked";
		else if (operation.equals("hwInstalls"))
			result = "HW Installed";
		else if (operation.equals("commissioningFE"))
			result = "Field Commissioning";
		else if (operation.equals("commissioningBO"))
			result = "Back Office Commissioning";
		else if (operation.equals("commissioningBO"))
			result = "Back Office Commissioning";
		else if (operation.equals("txProvisioning"))
			result = "Tx Provisioning";
		else if (operation.equals("fieldWork"))
			result = "Field Work";
		else if (operation.equals("siteUnlocked"))
			result = "Site Unlocked";
		else if (operation.equals("postCallTest"))
			result = "Post Call Test";
		else if (operation.equals("closureCode"))
			result = "Closure Code";
		else if (operation.equals("leaveSite"))
			result = "Left Site";
		else if (operation.equals("bookOffSite"))
			result = "Site Booked Off";
		else if (operation.equals("performanceMonitoring"))
			result = "Performance";
		else if (operation.equals("initialHOP"))
			result = "Hand Over Pack";		
		return result;
	}
	
	public Collection<ScheduleList> getScheduleList(
			String project,
			String upgradeType,
			String site,
			String nrId,
			String siteStatus,
			String fromDate,
			String toDate,
			String siteList,
			String initialEntry ) {
		message = null;
		ArrayList<ScheduleList> ScheduleList = new ArrayList<ScheduleList>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetScheduleList(?,?,?,?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, user.getFullname());
	    	cstmt.setString(2, project);
	    	cstmt.setString(3, upgradeType);
	    	cstmt.setString(4, site);
	    	cstmt.setString(5, nrId);
	    	cstmt.setString(6, siteStatus);
	    	cstmt.setString(7, fromDate);
	    	cstmt.setString(8, toDate);
	    	cstmt.setString(9, siteList);
	    	cstmt.setString(10, initialEntry);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					ScheduleList.add(new ScheduleList(
							rs.getString(1),  rs.getString(2),  rs.getString(3),  rs.getString(4), 
							rs.getString(5),  rs.getString(6),  rs.getString(7),  rs.getString(8),
							rs.getString(9),  rs.getString(10), rs.getString(11), rs.getDate(12),
							rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), 
							rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
							rs.getString(21), rs.getString(22), rs.getString(23), rs.getInt(24)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getScheduleList(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return ScheduleList;
	}
	
	public String getScheduleListHTML(
			String project,
			String upgradeType,
			String site,
			String nrId,
			String siteStatus,
			String fromDate,
			String toDate,
			String siteList,
			String multiSiteEdit,
			String initialEntry) {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		Collection<ScheduleList> ScheduleList = 
			getScheduleList(project,upgradeType,site,nrId,siteStatus,fromDate,toDate,siteList,initialEntry);
		if (ScheduleList.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1s",	message);
				td.setAttribute("colspan", "21");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			} else {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1s","No sites to display");
				td.setAttribute("colspan", "21");
				tr.appendValue(td.toString());
				html.append(tr.toString());	
			}
		} 
		else {
			// determine if user is a scheduler and can update 
			boolean canUpdate = user.hasUserRole(UserRole.ROLE_SCHEDULER);
			int row=0;
			for (Iterator<ScheduleList> it = ScheduleList.iterator(); it.hasNext(); ) {
				oddRow = !oddRow;
				row++;				
				ScheduleList sl = it.next();
				String nrIdOrSite = (sl.getNrId().contains("DUMMY")?sl.getSite():sl.getNrId());
				HTMLElement tr = new HTMLElement("tr");
				// Schedule Date
				HTMLElement td1 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getScheduleDate());
				if ((canUpdate)) {
					if (multiSiteEdit.equals("Y")) {
						td1 = new HTMLElement(
								"td", 
								(oddRow?"grid1s":"grid2s"),
								"<input type=\"text\" size=\"7\" "+
								"class=\""+(oddRow?"updateSite1":"updateSite2")+"\" "+
								"name=\"newSDate"+row+"\" "+
								"id=\"newSDate"+row+"\" "+
								"value=\""+sl.getScheduledDateString()+"\" "+
								"onClick=\"javascript:NewCssCal('newSDate"+row+"','ddMMyyyy','arrow')\" "+
								"onChange=\"recordEdit("+sl.getSnrId()+","+row+","+
											"'scheduledDate','"+sl.getScheduledDateString()+"')\" "+
								"style=\"cursor:pointer;\" readonly/>");
					} else if ((sl.getSiteStatus().equals("Scheduled"))||
								(sl.getSiteStatus().equals("Awaiting Scheduling"))	) {
						td1.setAttribute("style", "cursor:pointer;");
						td1.setAttribute("id", "scheduledDate"+row);
						td1.setAttribute("name", "scheduledDate"+row);
						td1.setAttribute(
								"onclick", 
								"updateSite"+ 
									"('"+sl.getSnrId()+
									"','"+
									sl.getScheduledDateString()+
									"','scheduledDate','"+
									row+"','"+
									nrIdOrSite+"')");
						
					}										
				}
				tr.appendValue(td1.toString());
				// Site
				HTMLElement td2 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getSite());
				tr.appendValue(td2.toString());
				// NR Id
				HTMLElement td3 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getNrId());
				tr.appendValue(td3.toString());
				// Site Status
				HTMLElement td4 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getSiteStatus());
				tr.appendValue(td4.toString());
				// Project
				HTMLElement td5 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getProject());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td5.setAttribute("style", "cursor:pointer;");
					td5.setAttribute("id", "project"+row);
					td5.setAttribute("name", "project"+row);
					td5.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getProject()+"','project','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td5.toString());
				// Upgrade Type
				HTMLElement td6 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getUpgradeType());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td6.setAttribute("style", "cursor:pointer;");
					td6.setAttribute("id", "upgradeType"+row);
					td6.setAttribute("name", "upgradeType"+row);
					td6.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getUpgradeType()+"','upgradeType','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td6.toString());
				// BO
				HTMLElement td7 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getBo());				
				if ((canUpdate)) {
					if (multiSiteEdit.equals("Y")) {
						td7 = new HTMLElement(
								"td", 
								(oddRow?"grid1s":"grid2s"),
								getAvailableBOEngineers(sl.getBo(),row,oddRow));
						td7.setAttribute(
								"onChange", 
								"recordEdit("+sl.getSnrId()+","+row+","+"'boEngineer','"+sl.getBo()+"')");
					} else if ((sl.getSiteStatus().equals("Scheduled"))||
								(sl.getSiteStatus().equals("Awaiting Scheduling"))	) {

						td7.setAttribute("style", "cursor:pointer;");
						td7.setAttribute("id", "boEngineer"+row);
						td7.setAttribute("name", "boEngineer"+row);
						td7.setAttribute(
								"onclick",  
								"updateSite"+ 
										"('"+sl.getSnrId()+"','"+sl.getBo()+"','boEngineer','"+
											row+"','"+
											nrIdOrSite+"')");
					}										
				}
				td7.setAttribute("title", sl.getBoAll());
				tr.appendValue(td7.toString());
				// FE
				HTMLElement td8 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getFe());
				if ((canUpdate)) {
					if (multiSiteEdit.equals("Y")) {
						td8 = new HTMLElement(
								"td", 
								(oddRow?"grid1s":"grid2s"),
								getAvailableFEEngineers(sl.getFe(),row,oddRow));
						td8.setAttribute(
								"onChange", 
								"recordEdit("+sl.getSnrId()+","+row+","+"'feEngineer','"+sl.getFe()+"')");
					} else if ((sl.getSiteStatus().equals("Scheduled"))||
								(sl.getSiteStatus().equals("Awaiting Scheduling"))	) {

						td8.setAttribute("style", "cursor:pointer;");
						td8.setAttribute("id", "feEngineer"+row);
						td8.setAttribute("name", "feEngineer"+row);
						td8.setAttribute(
								"onclick",  
								"updateSite"+ 
										"('"+sl.getSnrId()+"','"+sl.getFe()+"','feEngineer','"+
											row+"','"+
											nrIdOrSite+"')");
					}										
				}
				td8.setAttribute("title", sl.getFeAll());
				tr.appendValue(td8.toString());
				// Hardware Vendor
				HTMLElement td9 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getHardwareVendor());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td9.setAttribute("style", "cursor:pointer;");
					td9.setAttribute("id", "hardwareVendor"+row);
					td9.setAttribute("name", "hardwareVendor"+row);
					td9.setAttribute(
							"onclick", 
					"updateSite"+ 
						"('"+sl.getSnrId()+"','"+sl.getHardwareVendor()+"','hardwareVendor','"+
							row+"','"+
							nrIdOrSite+"')");
				}
				tr.appendValue(td9.toString());
				// Vodafone 2G
				HTMLElement td10 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getVodafone2G());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td10.setAttribute("style", "cursor:pointer;");
					td10.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getVodafone2G()+"','vodafone2G','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td10.toString());
				// Vodafone 3G
				HTMLElement td11 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getVodafone3G());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td11.setAttribute("style", "cursor:pointer;");
					td11.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getVodafone3G()+"','vodafone3G','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td11.toString());
				// Vodafone 4G
				HTMLElement td12 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getVodafone4G());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td12.setAttribute("style", "cursor:pointer;");
					td12.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getVodafone4G()+"','vodafone4G','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td12.toString());
				// TEF 2G
				HTMLElement td13 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getTef2G());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td13.setAttribute("style", "cursor:pointer;");
					td13.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getTef2G()+"','tef2G','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td13.toString());
				// TEF 4G
				HTMLElement td14 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getTef3G());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td14.setAttribute("style", "cursor:pointer;");
					td14.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getTef3G()+"','tef3G','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td14.toString());
				// TEF 4G
				HTMLElement td15 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getTef4G());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td15.setAttribute("style", "cursor:pointer;");
					td15.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getTef4G()+"','tef4G','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td15.toString());
				// Paknet and Paging
				HTMLElement td16 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getPaknetPaging());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td16.setAttribute("style", "cursor:pointer;");
					td16.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getPaknetPaging()+"','paknetPaging','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td16.toString());
				// SecGW Change
				HTMLElement td17 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getSecGWChange());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td17.setAttribute("style", "cursor:pointer;");
					td17.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getSecGWChange()+"','secGWChange','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td17.toString());
				// Power
				HTMLElement td18 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getPower());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td18.setAttribute("style", "cursor:pointer;");
					td18.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getPower()+"','power','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td18.toString());
				// Survey
				HTMLElement td19 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getSurvey());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td19.setAttribute("style", "cursor:pointer;");
					td19.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getSurvey()+"','survey','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td19.toString());
				// Other
				HTMLElement td20 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getOther());
				if ( (canUpdate) &&
						( 	(sl.getSiteStatus().equals("Scheduled")) ||
							(sl.getSiteStatus().equals("Awaiting Scheduling"))	) ) {
					td20.setAttribute("style", "cursor:pointer;");
					td20.setAttribute(
							"onclick",  
							"updateSite"+ 
									"('"+sl.getSnrId()+"','"+sl.getOther()+"','other','"+
										row+"','"+
										nrIdOrSite+"')");
				}
				tr.appendValue(td20.toString());
				String select = "";
				if (canUpdate) {
					HTMLElement input = new HTMLElement("input", "snrId"+row, "snrId",
							"radio", Long.toString(sl.getSnrId()), 
							"rescheduleSelect('"+sl.getSnrId()+
							"','"+sl.getScheduledDateString()+
							"','"+sl.getSiteStatus()+
							"','"+nrIdOrSite+"')");
					select = input.toString();
				}
				HTMLElement td21 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), select);
				tr.appendValue(td21.toString());
				// Complete row
				html.append(tr.toString());	
			}
		}
		return html.toString();
	}
	
	public String getScheduleHeaderHTML(String snrId) {
		StringBuilder html = new StringBuilder();
		if (!snrId.equals("")) {
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetScheduleHeader(?)}");
		    	cstmt.setLong(1, Long.parseLong(snrId));
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						String scheduleDate = rs.getString(1);
						String site = rs.getString(2);
						String nrId = rs.getString(3);
						String siteStatus = rs.getString(4);
						String project = rs.getString(5);
						String upgradeType = rs.getString(6);
						String hardwareVendor = rs.getString(7);
						String scheduleCommentary = rs.getString(8);
						HTMLElement trBlank = new HTMLElement("tr");
						HTMLElement tdBlank = new HTMLElement("td", "", "");
						tdBlank.setAttribute("height", "5px");
						tdBlank.setAttribute("colspan", "6");
						trBlank.appendValue(tdBlank.toString());
						HTMLElement tr1 = new HTMLElement("tr");
						// Site
						HTMLElement td11 = new HTMLElement("td", "grid1s", site);
						td11.setAttribute("name", "scheduleSite");
						td11.setAttribute("id", "scheduleSite");
						tr1.appendValue(td11.toString());
						// NR Id
						HTMLElement td12 = new HTMLElement("td", "grid1s", nrId);
						td12.setAttribute("name", "scheduleNrId");
						td12.setAttribute("id", "scheduleNrId");
						tr1.appendValue(td12.toString());
						// Site Status
						HTMLElement td13 = new HTMLElement("td", "grid1s", siteStatus);
						tr1.appendValue(td13.toString());
						// Project
						HTMLElement td14 = new HTMLElement("td", "grid1s", project);
						tr1.appendValue(td14.toString());
						// Upgrade Type
						HTMLElement td15 = new HTMLElement("td", "grid1s", upgradeType);
						tr1.appendValue(td15.toString());
						// Hardware Vendor
						HTMLElement td16 = new HTMLElement("td", "grid1s", hardwareVendor);
						tr1.appendValue(td16.toString());
						html.append(tr1.toString());
						html.append(trBlank.toString());
						// Scheduled date
						HTMLElement tr2 = new HTMLElement("tr");
						HTMLElement td21 = new HTMLElement(
								"td", 
								"schAltHead", 
								"Scheduled Date:");						
						td21.setAttribute("colspan", "2");								
						tr2.appendValue(td21.toString());
						String inputBox =
								"<input id=\"schDate\" name=\"schDate\" class=\"schText\" " +
								" size=\"10\" value=\""+scheduleDate+"\" " +
								"onclick=\"javascript:NewCssCal('schDate','ddMMyyyy','arrow')\" " +
								"readonly=\"\" type=\"text\" " +
								"style=\"cursor:pointer;\">";
						String dateBox = 
								"<img src=\"images/cal.gif\" " +
								"onclick=\"javascript:NewCssCal('schDate','ddMMyyyy','arrow')\" "+
								"style=\"cursor:pointer;\">";
						HTMLElement td22 = new HTMLElement(
								"td", 
								"schAltHead", 
								inputBox+dateBox);						
						td22.setAttribute("colspan", "`");							
						tr2.appendValue(td22.toString());
						// Schedule or Reschedule plus Cancel Scheduling buttons
						if (siteStatus.equals("Awaiting Scheduling")) {
							String button1 = 
								"<input style=\"width=120px\" type=\"button\" "+
								"onClick=\"rescheduleClick('schedule')\" value=\"Schedule\"/>";
							HTMLElement td23 = new HTMLElement(
									"td", 
									"schAltHead", 
									button1);						
							td23.setAttribute("colspan", "3");	
							tr2.appendValue(td23.toString());
						} else {
							String button1 = 
								"<input style=\"width=120px\" type=\"button\" "+
								"onClick=\"rescheduleClick('reschedule')\" value=\"Reschedule\"/>";
							HTMLElement td23 = new HTMLElement(
									"td", 
									"schAltHead", 
									button1);		
							tr2.appendValue(td23.toString());
							String button2 =
									"<input style=\"width=120px\" type=\"button\" "+
									"onClick=\"rescheduleClick('cancelSchedule')\" value=\"Cancel Schedule\"/>";
							HTMLElement td24 = new HTMLElement(
									"td", 
									"schAltHead", 
									button2);						
							td24.setAttribute("colspan", "2");	
							tr2.appendValue(td24.toString());
						}										
						html.append(tr2.toString());
						html.append(trBlank.toString());
						// Commentary
						HTMLElement tr3 = new HTMLElement("tr");
						HTMLElement td31 = new HTMLElement(
								"td", 
								"schAltHead", 
								"Schedule Commentary:");						
						td31.setAttribute("colspan", "2");					
						td31.setAttribute("valign", "top");		
						tr3.appendValue(td31.toString());
						String commentaryBox = 
								"<textarea id=\"commentary\" name=\"commentary\" class=\"schText\" " +
								"resize=\"none\" maxlength=\"2000\" " +
								"style=\"width:430px;height:100px;resize:none;\">"+scheduleCommentary+"</textarea>";
						HTMLElement td32 = new HTMLElement(
								"td", 
								"schAltHead", 
								commentaryBox);						
						td32.setAttribute("colspan", "4");	
						tr3.appendValue(td32.toString());									
						html.append(tr3.toString());						
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in getScheduleHeader(): " + ex.getMessage();
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
		return html.toString();
	}
	

	
	public String getScheduleEngineersHTML(String snrId) {
		StringBuilder html = new StringBuilder();
		boolean oddRow = false;
		if (!snrId.equals("")) {
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetSNRUserRoles(?,?)}");
		    	cstmt.setLong(1, Long.parseLong(snrId));
		    	cstmt.setString(2, "All");
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						//long snrId = rs.getLong(1);
						long userId = rs.getLong(2);	
						String role = rs.getString(3);	
						String name = rs.getString(4);
						long thirdPartyId = rs.getLong(5);
						String thirdPartyName = rs.getString(6);
						String rank = rs.getString(7);
						String roleCount = rs.getString(8);
						oddRow = !oddRow;
						HTMLElement tr = new HTMLElement("tr");
						HTMLElement td0 = new HTMLElement("td", "schAltHead", "" );
						tr.appendValue(td0.toString());
						HTMLElement td1 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), name);
						tr.appendValue(td1.toString());
						HTMLElement td2 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), role);
						tr.appendValue(td2.toString());
						HTMLElement td3 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), rank);
						tr.appendValue(td3.toString());
						HTMLElement td4 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), thirdPartyName);
						tr.appendValue(td4.toString());
						HTMLElement td5 = new HTMLElement(
											"td", 
											(oddRow?"grid1s":"grid2s"), 
											"<img src=\"/images/edit.png\" height=\"15px\" width=\"15px\" "+
											"onClick=\"chgEng('"+snrId+"','"+userId+"','"+rank+"','"+
												(role.startsWith("BO")?"BO":"FE")+"','"+roleCount+"')\" "+
											"title=\"Change Engineer\">" +
											"<img src=\"/images/delete.png\" height=\"15px\" width=\"15px\" "+
											"onClick=\"delEng('"+snrId+"','"+userId+"','"+rank+"','"+
												(role.startsWith("BO")?"BO":"FE")+"','"+roleCount+"')\" "+
											"title=\"Delete Engineer\">");
						tr.appendValue(td5.toString());
						html.append(tr.toString());
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in GetSNRUserRoles(): " + ex.getMessage();
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
		return html.toString();
	}
		
	public String canSchedule(String snrId) {
		String scheduleMessage = "Unable to check for scheduling";
		if (!snrId.equals("")) {
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call canSchedule(?)}");
		    	cstmt.setLong(1, Long.parseLong(snrId));
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						scheduleMessage = rs.getString(1);
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in CanSchedule(): " + ex.getMessage();
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
		return scheduleMessage;
	}

	public String getAvailableFEEngineers(String snrId) {
		Connection conn = null;
		CallableStatement cstmt = null;
		Select select = new Select("selectAvailableFEEngineers",  "filter");
		if (!snrId.equals("")) {
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetAvailableUsersForRole(?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setString(2, "Field Engineer");
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						Option option = new Option(rs.getString(1), rs.getString(2),
							false);
						select.appendValue(option.toString());
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
		}
		return select.toString();
	}

	public String getAvailableFEEngineers(String currentValue, int row, boolean oddRow) {
		Connection conn = null;
		CallableStatement cstmt = null;
		Select select = new Select("selectAvailableFEEngineers"+row, (oddRow?"schFilter1":"schFilter2"));
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetAllUsersForRole(?,?)}");
			cstmt.setString(1, "Field Engineer");
			cstmt.setString(2, currentValue);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}
	
	public String getAvailableBOEngineers(String snrId) {
		Connection conn = null;
		CallableStatement cstmt = null;
		Select select = new Select("selectAvailableBOEngineers",  "filter");
		if (!snrId.equals("")) {
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetAvailableUsersForRole(?,?)}");
					cstmt.setLong(1, Long.parseLong(snrId));
					cstmt.setString(2, "BO Engineer");
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						Option option = new Option(rs.getString(1), rs.getString(2),
							false);
						select.appendValue(option.toString());
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
		}
		return select.toString();
	}
	
	public String getAvailableBOEngineers(String currentValue, int row, boolean oddRow) {
		Connection conn = null;
		CallableStatement cstmt = null;
		Select select = new Select("selectAvailableBOEngineers"+row,  (oddRow?"schFilter1":"schFilter2"));
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetAllUsersForRole(?,?)}");
			cstmt.setString(1, "BO Engineer");
			cstmt.setString(2, currentValue);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}
	
	public int getMaxScheduleEdits() {
		int noEdits = -999;
		Connection conn = null;
		CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetMaxScheduleEdits()}");
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					noEdits = rs.getInt(1);
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
		return noEdits;
	}
	
	public String GetScheduleProjectHTML() {
		return getSelectHTMLWithInitialValue("ScheduleProject","select","filter",user.getFullname());
	}
	
	public Collection<ScheduleList> getMissingData() {
		message = null;
		ArrayList<ScheduleList> ScheduleList = new ArrayList<ScheduleList>();
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetMissingData(?)}");
	    	cstmt.setString(1, user.getFullname());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					ScheduleList.add(new ScheduleList(
							rs.getString(1),  rs.getString(2),  rs.getString(3),  rs.getString(4), 
							rs.getString(5),  rs.getString(6),  rs.getString(7),  rs.getString(8),
							rs.getString(9),  rs.getString(10), rs.getString(11), rs.getDate(12),
							rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), 
							rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
							rs.getString(21), rs.getString(22), rs.getString(23), rs.getInt(24)));
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getMissingData(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return ScheduleList;
	}

	public String getMissingDataHTML() {
		boolean oddRow = false;
		StringBuilder html = new StringBuilder();
		Collection<ScheduleList> ScheduleList = 
			getMissingData();
		if (ScheduleList.isEmpty()) {
			if (message != null) {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1s", message);
				td.setAttribute("colspan", "20");
				tr.appendValue(td.toString());
				html.append(tr.toString());
			} else {
				HTMLElement tr = new HTMLElement("tr");
				HTMLElement td = new HTMLElement("td", "grid1s","No sites with missing data to display");
				td.setAttribute("colspan", "20");
				tr.appendValue(td.toString());
				html.append(tr.toString());	
			}
		} 
		else {
			int row=0;
			for (Iterator<ScheduleList> it = ScheduleList.iterator(); it.hasNext(); ) {
				oddRow = !oddRow;
				row++;				
				ScheduleList sl = it.next();
				HTMLElement tr = new HTMLElement("tr");
				// Schedule Date
				HTMLElement td1 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getScheduleDate());
				if (sl.getScheduleDate().equals("")) {
					td1 = new HTMLElement(
							"td", 
							"missing",
							"<input type=\"text\" size=\"7\" "+
							"class=\"missing\" "+
							"name=\"newSDate"+row+"\" "+
							"id=\"newSDate"+row+"\" "+
							"value=\""+sl.getScheduledDateString()+"\" "+
							"onClick=\"javascript:NewCssCal('newSDate"+row+"','ddMMyyyy','arrow')\" "+
							"onChange=\"populateSiteColumn("+sl.getSnrId()+","+row+","+
										"'scheduledDate')\" "+
							"style=\"cursor:pointer;\" readonly/>");
				}
				tr.appendValue(td1.toString());
				// Site
				HTMLElement td2 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getSite());
				tr.appendValue(td2.toString());
				// NR Id
				HTMLElement td3 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getNrId());
				tr.appendValue(td3.toString());
				// Site Status
				HTMLElement td4 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getSiteStatus());
				tr.appendValue(td4.toString());
				// Project
				HTMLElement td5 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getProject());
				tr.appendValue(td5.toString());
				// Upgrade Type
				HTMLElement td6 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getUpgradeType());
				if (sl.getUpgradeType().equals("")) {
					td6 = new HTMLElement(
							"td", 
							"missing",
							"<input type=\"text\" size=\"7\" "+
							"class=\"missing\" "+
							"name=\"newUType"+row+"\" "+
							"id=\"newUType"+row+"\" "+
							"onChange=\"populateSiteColumn("+sl.getSnrId()+","+row+","+
										"'upgradeType')\" "+
							"style=\"cursor:pointer;\" >");
				}
				tr.appendValue(td6.toString());
				// BO
				HTMLElement td7 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getBo());	
				if (sl.getBo().equals("")) {
					td7 = new HTMLElement(
							"td", 
							"missing",
							getAvailableBOEngineers(sl.getBo(),row,"missing"));
					td7.setAttribute(
							"onChange", 
							"populateSiteColumn("+sl.getSnrId()+","+row+","+"'boEngineer')");
				}
				td7.setAttribute("title", sl.getBoAll());
				tr.appendValue(td7.toString());
				// FE
				HTMLElement td8 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getFe());	
				if (sl.getFe().equals("")) {				
					td8 = new HTMLElement(
							"td", 
							"missing",
							getAvailableFEEngineers(sl.getFe(),row,"missing"));
					td8.setAttribute(
							"onChange", 
							"populateSiteColumn("+sl.getSnrId()+","+row+","+"'feEngineer')");
				}
				td8.setAttribute("title", sl.getFeAll());
				tr.appendValue(td8.toString());
				// Hardware Vendor
				HTMLElement td9 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getHardwareVendor());
				if (sl.getHardwareVendor().equals("")) {
					td9 = new HTMLElement(
							"td", 
							"missing",
							"<input type=\"text\" size=\"7\" "+
							"class=\"missing\" "+
							"name=\"newHVendor"+row+"\" "+
							"id=\"newHVendor"+row+"\" "+
							"onChange=\"populateSiteColumn("+sl.getSnrId()+","+row+","+
										"'hardwareVendor')\" "+
							"style=\"cursor:pointer;\" >");
				}
				tr.appendValue(td9.toString());
				// Vodafone 2G
				HTMLElement td10 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getVodafone2G());
				tr.appendValue(td10.toString());
				// Vodafone 3G
				HTMLElement td11 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getVodafone3G());
				tr.appendValue(td11.toString());
				// Vodafone 4G
				HTMLElement td12 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getVodafone4G());
				tr.appendValue(td12.toString());
				// TEF 2G
				HTMLElement td13 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getTef2G());
				tr.appendValue(td13.toString());
				// TEF 4G
				HTMLElement td14 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getTef3G());
				tr.appendValue(td14.toString());
				// TEF 4G
				HTMLElement td15 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getTef4G());
				tr.appendValue(td15.toString());
				// Paknet and Paging
				HTMLElement td16 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getPaknetPaging());
				tr.appendValue(td16.toString());
				// SecGW Change
				HTMLElement td17 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getSecGWChange());
				tr.appendValue(td17.toString());
				// Power
				HTMLElement td18 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getPower());
				tr.appendValue(td18.toString());
				// Survey
				HTMLElement td19 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getSurvey());
				tr.appendValue(td19.toString());
				// Other
				HTMLElement td20 = new HTMLElement("td", (oddRow?"grid1s":"grid2s"), sl.getOther());
				tr.appendValue(td20.toString());
				// Complete row
				html.append(tr.toString());	
			}
		}
		return html.toString();
	}
	
	public String getAvailableBOEngineers(String currentValue, int row, String selectClass) {
		Connection conn = null;
		CallableStatement cstmt = null;
		Select select = new Select("selectAvailableBOEngineers"+row,  selectClass );
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetAllUsersForRole(?,?)}");
			cstmt.setString(1, "BO Engineer");
			cstmt.setString(2, currentValue);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}

	public String getAvailableFEEngineers(String currentValue, int row, String selectClass) {
		Connection conn = null;
		CallableStatement cstmt = null;
		Select select = new Select("selectAvailableFEEngineers"+row, selectClass);
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetAllUsersForRole(?,?)}");
			cstmt.setString(1, "Field Engineer");
			cstmt.setString(2, currentValue);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					Option option = new Option(rs.getString(1), rs.getString(2),
						false);
					select.appendValue(option.toString());
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
		return select.toString();
	}
	
	public String getScheduleListCount(
			String project,
			String upgradeType,
			String site,
			String nrId,
			String siteStatus,
			String fromDate,
			String toDate,
			String siteList,
			String initialEntry ) {
		String result = "";
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetScheduleListCount(?,?,?,?,?,?,?,?,?,?)}");
	    	cstmt.setString(1, user.getFullname());
	    	cstmt.setString(2, project);
	    	cstmt.setString(3, upgradeType);
	    	cstmt.setString(4, site);
	    	cstmt.setString(5, nrId);
	    	cstmt.setString(6, siteStatus);
	    	cstmt.setString(7, fromDate);
	    	cstmt.setString(8, toDate);
	    	cstmt.setString(9, siteList);
	    	cstmt.setString(10, initialEntry);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					result = rs.getString(1)+" sites found";
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in getScheduleListCount(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return result;
	}
	
	public boolean userExpired() {
		boolean expired = false;
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call CheckUserExpired(?)}");
	    	cstmt.setLong(1, user.getUserId());
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					if (rs.getString(1).equals("Y"))
						expired = true;
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in CheckUserExpired(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 	    
	    return expired;
	}
	
	public String getNrIdOrSite(
			String snrId ) {
		String result = "Not found";
		if (snrId.equals("")) {
			message = null;
	    	Connection conn = null;
	    	CallableStatement cstmt = null;
		    try {
		    	conn = DriverManager.getConnection(url);
		    	cstmt = conn.prepareCall("{call GetNrIdOrSite(?)}");
		    	cstmt.setLong(1, Long.parseLong(snrId));
				boolean found = cstmt.execute();
				if (found) {
					ResultSet rs = cstmt.getResultSet();
					while (rs.next()) {
						result = rs.getString(1);
					}
				}
		    } catch (Exception ex) {
		    	message = "Error in GetNrIdOrSite(): " + ex.getMessage();
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
