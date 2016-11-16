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
	private String prevScreen;
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
		HTMLElement m0 = new HTMLElement("div");
		HTMLElement m0a = new HTMLElement("div", "float:left;width:2px", 
			"menu1", "&nbsp;");
		HTMLElement m01 = new HTMLElement("div", "m01", "float:left;", 
			"menu1Item", "menuClick('" + ServletConstants.HOME + "')", 
			"invertClass('m01')", "invertClass('m01')", ServletConstants.HOME);
		if (!screen.equals(ServletConstants.CHANGE_PASSWORD)) {
			if (user.hasUserRole(UserRole.ROLE_B_O_ENGINEER)) {
				m01 = new HTMLElement("div", "m01", "float:left;",   
						"menu1Item", "menuClick('" + ServletConstants.HOME_BO + "')", 
						"invertClass('m01')", "invertClass('m01')", ServletConstants.HOME_BO);
			}
		}
		HTMLElement m02 = new HTMLElement("div", "m02", "float:right;", 
			"menu1Item", "menuClick('" + ServletConstants.CHANGE_PASSWORD + "')",
			"invertClass('m02')", "invertClass('m02')", 
			ServletConstants.CHANGE_PASSWORD);
		HTMLElement m03 = new HTMLElement("div", "m03", "float:right;", 
				"menu1Item", "menuClick('" + ServletConstants.LOG_OFF + "')", 
				"invertClass('m03')", "invertClass('m03')", ServletConstants.LOG_OFF);
		HTMLElement m04 = new HTMLElement("div", "m04", "float:right;", 
				"menu1Item", "menuClick('" + ServletConstants.LIVE_DASHBOARD + "')", 
				"invertClass('m04')", "invertClass('m04')", ServletConstants.LIVE_DASHBOARD);
		HTMLElement m0b = new HTMLElement("div", "float:right;width:2px", "menu1", 
			"&nbsp;");
		HTMLElement m0c = new HTMLElement("div", "overflow:hidden", "menu1", 
			"&nbsp;");
		if (screen.equals(ServletConstants.LIVE_DASHBOARD)){
			m0.setValue(m0a.toString() + m01.toString() + m0b.toString() + 
					m03.toString() + m0c.toString());
		}
		else
		{
			//if (user.getUserId()==1) {
				m0.setValue(m0a.toString() + m01.toString() + m0b.toString() + 
						m03.toString() + 
						m04.toString() + 
						(!screen.equals(ServletConstants.CHANGE_PASSWORD)?m02.toString():"") + 
						m0c.toString());
			/*} else {
				m0.setValue(m0a.toString() + m01.toString() + m0b.toString() + 
						m03.toString() + 
						//m04.toString() + // COMMENT OUT DASHBOARD 
						(!screen.equals(ServletConstants.CHANGE_PASSWORD)?m02.toString():"") + 
						m0c.toString());
			}*/
		}		
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
			boolean outputSchedule = false;
			if (user.hasUserRole(UserRole.ROLE_B_O_ENGINEER)) {
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.BO + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.BO_SHORT, 
						ServletConstants.BO));
			}
			if (user.hasUserRole(UserRole.ROLE_SCHEDULER)) {
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.SCHEDULING + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.SCHEDULING));
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
						ServletConstants.OUTPUT_SCHEDULE + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.OUTPUT_SCHEDULE));
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
				outputSchedule = true;
			}
			if (user.hasUserRole(UserRole.ROLE_B_O_ENGINEER)) {
				if (!outputSchedule) {
					i++;
					m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
							ServletConstants.OUTPUT_SCHEDULE + "')", "invertClass('m1"+i+"')", 
							"invertClass('m1"+i+"')", ServletConstants.OUTPUT_SCHEDULE));
					outputSchedule = true;
				}
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
				if (!outputSchedule) {
					i++;
					m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
							ServletConstants.OUTPUT_SCHEDULE + "')", "invertClass('m1"+i+"')", 
							"invertClass('m1"+i+"')", ServletConstants.OUTPUT_SCHEDULE));
					outputSchedule = true;
				}
				i++;
				m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
						ServletConstants.UPDATE_ACCESS + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.UPDATE_ACCESS));
			}
			if (user.hasUserRole(UserRole.ROLE_CRM_ADMIN)) {
				if (!outputSchedule) {
					i++;
					m1l.add(new HTMLElement("div", "m1"+i, "float:left;", "menu2Item", "menuClick('" +
							ServletConstants.OUTPUT_SCHEDULE + "')", "invertClass('m1"+i+"')", 
							"invertClass('m1"+i+"')", ServletConstants.OUTPUT_SCHEDULE));
					outputSchedule = true;
				}
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
			/*m1l.add(new HTMLElement("div", "m12", "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.MANUAL_POT_CREATION + "')", "invertClass('m12')", 
					"invertClass('m12')", ServletConstants.MANUAL_POT_CREATION));*/
			m1l.add(new HTMLElement("div", "m13", "float:left;", "menu2Item", "menuClickSpec('uploadSchedule')", "invertClass('m13')", 
					"invertClass('m13')", ServletConstants.SCHEDULE_SNR));
			m1l.add(new HTMLElement("div", "m14", "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.RESCHED_REALLOC_CANCEL_SNR + "')", "invertClass('m14')", 
					"invertClass('m14')", /*ServletConstants.RESCHED_REALLOC_CANCEL_SNR_SHORT,*/ 
					ServletConstants.RESCHED_REALLOC_CANCEL_SNR));
			
		}  else if (screen.equals(ServletConstants.RESCHED_REALLOC_CANCEL_SNR)) {
			m1l.add(new HTMLElement("div", "m11", "float:left;", "menu2Item", "menuClick('" +
					ServletConstants.SCHEDULING + "')", "invertClass('m11')", 
					"invertClass('m11')", ServletConstants.SCHEDULING));
			
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
						ServletConstants.SCHEDULING + "')", "invertClass('m1"+i+"')", 
						"invertClass('m1"+i+"')", ServletConstants.SCHEDULING));
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
				outputSchedule = true;
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
					ServletConstants.OUTPUT_SCHEDULE + "')", "invertClass('m1"+i+"')", 
					"invertClass('m1"+i+"')", ServletConstants.OUTPUT_SCHEDULE));
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
					screen.equals(ServletConstants.LIVE_DASHBOARD);
			} else /*if ((user.getUserType().equals(User.USER_TYPE_DEVOTEAM)) ||
					(user.getUserType().equals(User.USER_TYPE_THIRD_PARTY)))*/ {
				if (screen.equals(ServletConstants.WORK_QUEUES)) {
					canSee = true;
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
		int row = 0;
		StringBuilder html = new StringBuilder();
		psli = getSelectedPMOItem(snrId);
		HTMLElement tr = new HTMLElement("tr");
		row++;
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
					/*commentaryList.add(new SNRCommentary(rs.getLong(1), 
						rs.getDate(2), rs.getLong(3), rs.getString(4), 
						rs.getString(5), rs.getDate(6), rs.getString(7)));*/
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
	    	//cstmt.setString(2, password);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					SNRCommentary snrC = new SNRCommentary(rs.getLong(1),  
							rs.getDate(2), rs.getLong(3), rs.getString(4), 
							rs.getString(5), rs.getDate(6), rs.getString(7));
					commentaryList.add(snrC); 
					/*commentaryList.add(new SNRCommentary(rs.getLong(1), 
						rs.getDate(2), rs.getLong(3), rs.getString(4), 
						rs.getString(5), rs.getDate(6), rs.getString(7)));*/
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
		/*if (row < 19) {
			double height = ((18 - row) * (23 + (3/18))) + 3;
			String style = "height:" + height + "px";
			HTMLElement tr = new HTMLElement("tr");
			HTMLElement td = new HTMLElement("td", style, "grid2", "&nbsp;");
			td.setAttribute("colspan", Integer.toString(cols));
			tr.appendValue(td.toString());
			html.append(tr.toString());
		}*/
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
						rs.getString(64),rs.getString(65),rs.getString(66));
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
					i==0?buttonUT.toString():i==1?buttonAT.toString():"&nbsp;");
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
		
	/*private String getSNRImplementationDetailHTML(long snrId) {
		StringBuilder html = new StringBuilder();
		SNR snr = getSNRDetail(snrId, false, 0);
		
		return html.toString();
	}*/
	
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
			for (int i = 1; i < (titleArray.length/*-2*/); i+=3) {
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
			//int row = 1;
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
				//row++;
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
						rs.getDate(18),rs.getString(19),rs.getString(20)));
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
				uali.getUsername()+"','"+uali.getUserType()+"','"+uali.getEmail()+"')");
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
						rs.getString(7),rs.getString(8),rs.getString(9)));
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
			HTMLElement tr = new HTMLElement("tr");
			row++;
			oddRow = !oddRow;
			JobType jtli = it.next();
			String[] values = jtli.getListValueArray();
			for (int i = 0; i < values.length; i ++) {
				HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
					values[i]);
				tr.appendValue(td.toString());
			}
			HTMLElement input = new HTMLElement("input", "jobType"+row, "jT",
				"radio", jtli.getJobType(), 
				"jobTypeSelect('"+jtli.getJobType()+"','"+
						jtli.getProjectRequestor()+"','"+
						jtli.getProjectRequestorEmail()+"','"+
						jtli.getProjectManager()+"','"+
						jtli.getProjectManagerEmail()+"','"+
						jtli.getActingCustomer()+"','"+
						jtli.getRedundant()+"')");
			HTMLElement td = new HTMLElement("td", oddRow?"grid1":"grid2", 
				input.toString());
			tr.appendValue(td.toString());
			html.append(tr.toString());
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
					/*if (values[i].equals(sli.DUMMYNR)) {
						td.setAttribute("title", sli.getNRId());
					}*/
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
			//HTMLElement tdn1 = new HTMLElement("td", "grid2", "&nbsp;");
			//tdn1.setAttribute("colspan", "2");
			//trn.appendValue(tdn1.toString());
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
			//HTMLElement tdo1 = new HTMLElement("td", "grid1", "&nbsp;");
			//tdo1.setAttribute("colspan", "2");
			//tro.appendValue(tdo1.toString());
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
			//HTMLElement tdp1 = new HTMLElement("td", "grid2", "&nbsp;");
			//tdp1.setAttribute("colspan", "2");
			//trp.appendValue(tdp1.toString());
			HTMLElement tdp2 = new HTMLElement("td", "grid2RedBold", "Progress Issue:");
			trp.appendValue(tdp2.toString());
			HTMLElement tdp3 = new HTMLElement("td", "grid2", 
					"<input style=\"width:95%;\" type=\"text\" name=\"progressIssueAmended\" id=\"progressIssueAmended\" value=\""+sp.getProgressIssue()+"\" maxlength=\"200\">");
					//getSelectHTMLWithInitialValue("ProgressIssue", "select", "filter", sp.getProgressIssue()));
			tdp3.setAttribute("colspan", "4");
			trp.appendValue(tdp3.toString());
			HTMLElement tdp4 = new HTMLElement("td", "grid2", "&nbsp;");
			tdp4.setAttribute("colspan", "5");
			trp.appendValue(tdp4.toString());
			html.append(trp.toString());			
		}		
		return html.toString();
	}
	//<input style="width:95%" type="text" name="newActingCustomer" id="newActingCustomer" value="" maxlength="100">
	
	
	/*public String rewindProject(String fullname) {
		String updateResult = "Error: Untrapped error with Rewind_Project";
		String message = null; 
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call Rewind_Project(?)}");
	    	cstmt.setString(1, fullname);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					updateResult = rs.getString(1);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in Rewind_Project(): " + ex.getMessage();
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
	}*/
	
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
    				"title=\"Hide project counts table\">";
	    }
	    else {
	    	heading = heading + "&nbsp;" +
	    			"<img src=\"images/show.png\" height=\"15\" width=\"15\" "+
	    			"onclick=\"navigationAction('show')\" "+
    				"title=\"Show project counts table\">";
	    }
	    heading = heading + "&nbsp;" +
	    		"<img src=\"images/fwd.png\" "+
				"height=\"15\" width=\"15\" border:1px solid black; "+
				"onClick=\"navigationAction('fwd')\""+
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
					HTMLElement tr = new HTMLElement("tr");
					HTMLElement td0 = new HTMLElement("td", "ldGridBreak", "" );
					td0.setAttribute("height", "3px");
					td0.setAttribute("colspan", "29");
					tr.appendValue(td0.toString());					
					html.append(tr.toString());
				// add break if previous row was for current week and now past current week
				} else if ( (!prevYearWeek.equals(ldc.getCurrentYearWeek()))  &&
						(prevYearWeek.equals(ldc.getFirstYearWeek()))) {
					HTMLElement tr = new HTMLElement("tr");
					HTMLElement td0 = new HTMLElement("td", "ldGridBreak", "" );
					td0.setAttribute("height", "3px");
					td0.setAttribute("colspan", "29");
					tr.appendValue(td0.toString());					
					html.append(tr.toString());					
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
				//td8.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td8.toString());
				// Booked On
				HTMLElement td9 = new HTMLElement("td", "ld"+ldc.getBookedOn(), "");
				//td9.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td9.toString());
				// Site Accessed
				HTMLElement td10 = new HTMLElement("td", "ld"+ldc.getSiteAccessed(), "");
				//td10.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td10.toString());
				// Physical Checks
				HTMLElement td11 = new HTMLElement("td", "ld"+ldc.getPhysicalChecks(), "");
				//td11.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td11.toString());
				// Pre Call Test
				HTMLElement td12 = new HTMLElement("td", "ld"+ldc.getPreCallTest()+"RDash", "");
				//td12.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td12.toString());
				// Site Locked
				HTMLElement td13 = new HTMLElement("td", "ld"+ldc.getSiteLocked()+"LDash", "");
				//td13.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td13.toString());
				// HW Installs
				HTMLElement td14 = new HTMLElement("td", "ld"+ldc.getHwInstalls(), "");
				//td14.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td14.toString());
				// Commissioning FE
				HTMLElement td15 = new HTMLElement("td", "ld"+ldc.getCommissioningFE(), "");
				//td15.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td15.toString());
				// Commissioning BO
				HTMLElement td16 = new HTMLElement("td", "ld"+ldc.getCommissioningBO(), "");
				//td16.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td16.toString());
				// TX Provisioning
				HTMLElement td17 = new HTMLElement("td", "ld"+ldc.getTxProvisioning(), "");
				//td17.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td17.toString());
				// Field Work
				HTMLElement td18 = new HTMLElement("td", "ld"+ldc.getFieldWork(), "");
				//td18.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td18.toString());
				// Site Unlocked
				HTMLElement td19 = new HTMLElement("td", "ld"+ldc.getSiteUnlocked(), "");
				//td19.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td19.toString());
				// Post Call Test
				HTMLElement td20 = new HTMLElement("td", "ld"+ldc.getPostCallTest()+"RDash", "");
				//td20.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td20.toString());
				// Closure Code
				HTMLElement td21 = new HTMLElement("td", "ld"+ldc.getClosureCode()+"LDash", "");
				//td21.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td21.toString());
				// Leave Site
				HTMLElement td22 = new HTMLElement("td", "ld"+ldc.getLeaveSite(), "");
				//td22.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td22.toString());
				// Book Off Site
				HTMLElement td23 = new HTMLElement("td", "ld"+ldc.getBookOffSite(), "");
				//td23.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td23.toString());
				// Performance Monitoring
				HTMLElement td24 = new HTMLElement("td", "ld"+ldc.getPerformanceMonitoring(), "");
				//td24.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td24.toString());
				// Initial HOP
				HTMLElement td25 = new HTMLElement("td", "ld"+ldc.getInitialHOP()+"RDash", "");
				//td25.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td25.toString());
				// Devoteam Issue
				HTMLElement td26 = new HTMLElement("td", "ld"+ldc.getDevoteamIssue()+"LDash", "");
				//td26.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
				tr.appendValue(td26.toString());
				// Customer Issue
				//HTMLElement td27 = new HTMLElement("td", "ld"+ldc.getCustomerIssue()+"RDash", "");
				HTMLElement td27 = new HTMLElement("td", "ld"+ldc.getCustomerIssue(), "");
				//td27.setAttribute("onClick", "siteProgressStatusKeyClick('open')");
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
		//set up empty string a
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
	    // check no.issues and add blank lines if necessary
	    /*if (issues<3) {
	    	for (int i = issues; i<3; i++ ) {
	    		HTMLElement trd = new HTMLElement("tr");
				HTMLElement tdd = new HTMLElement("td", "ldIssue", "&nbsp;");
				tdd.setAttribute("width", "500px");
				tdd.setAttribute("height", "5px");
				trd.appendValue(tdd.toString());
				html.append(trd.toString());
	    	}
	    }*/
		return html.toString();
	}
	
}
