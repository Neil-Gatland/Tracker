package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.PreCheckListItem;
import com.devoteam.tracker.model.PreCheckOutstanding;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.StringUtil;

public class PreCheckMaintenanceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5804774931227059953L;
	private final String[] filters = {"filterNRId", "filterJobType"};
	private Map<String, String> filterValues = new HashMap<String, String>();
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/preCheckMaintenance.jsp";
		try {
			HttpSession session = req.getSession(false);
			if (session == null) {
				destination = "/logon.jsp";
				session = req.getSession(true);
				session.setAttribute("userMessage", "Please enter a email address and password");
			} else {
				for (int i = 0; i < filters.length; i++) {
			    	req.setAttribute(filters[i], req.getParameter(filters[i]));
				}
				String whichFilter = req.getParameter("whichFilter");
				String buttonPressed = req.getParameter("buttonPressed");
				String snrId = req.getParameter("snrId");
				String snrIdFilter = req.getParameter("snrIdFilter");
				String preCheckId = req.getParameter("preCheckId");
		    	req.setAttribute("buttonPressed", buttonPressed);
		    	req.setAttribute("snrId", snrId);
		    	req.setAttribute("nrId", req.getParameter("nrId"));
		    	req.setAttribute("site", req.getParameter("site"));
		    	req.setAttribute("snrIdFilter", snrIdFilter);
		    	req.setAttribute("preCheckId", preCheckId);
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
		    	Connection conn = null;
		    	CallableStatement cstmt = null;
				if (buttonPressed.equals("showAll")) {
					req.removeAttribute("snrIdFilter");
				} else if (buttonPressed.equals("preCheckUpdate")) {
					session.removeAttribute(ServletConstants.PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION);
					@SuppressWarnings("unchecked")
					Collection<PreCheckOutstanding> pcList = 
							(Collection<PreCheckOutstanding>) session.getAttribute(ServletConstants.PRECHECK_COLLECTION_NAME_IN_SESSION);
					PreCheckOutstanding thisP = null; 
					for (Iterator<PreCheckOutstanding> it = pcList.iterator(); it.hasNext(); ) {
						PreCheckOutstanding pcli = it.next();
						if (pcli.getPreCheckId() == Long.parseLong(preCheckId)) {
							thisP = pcli;
							break;
						}
					}
					boolean outOfOrder = false;
					if (thisP != null) {
						for (Iterator<PreCheckOutstanding> it = pcList.iterator(); it.hasNext(); ) {
							PreCheckOutstanding pcli = it.next();
							if ((pcli.getPreCheckId() != thisP.getPreCheckId()) &&
									(pcli.getSNRId() == thisP.getSNRId()) &&
									(pcli.isIncomplete()) &&
									(pcli.getPreCheckScheduledDT() != null) &&
									(thisP.getPreCheckScheduledDT() != null) &&
									(pcli.getPreCheckScheduledDT().before(thisP.getPreCheckScheduledDT()))) {
								outOfOrder = true;
								break;
							}
						}
					}
					req.setAttribute("preChecksOutOfOrder", outOfOrder?"true":"false");
				} else if (buttonPressed.equals("addCommentarySubmit")) {
				    try {
				    	String snrCommentaryType = req.getParameter("selectSNRCommentaryType");
				    	if (snrCommentaryType == null) {
				    		snrCommentaryType = req.getParameter("disabledSNRCommentaryType");
				    	}
				    	conn = DriverManager.getConnection(url);
				    	cstmt = conn.prepareCall("{call AddSNRCommentary(?,?,?,?,?)}");
						cstmt.setLong(1, Long.parseLong(snrId));
						cstmt.setLong(2, Long.parseLong(preCheckId));
						cstmt.setString(3, snrCommentaryType);
						cstmt.setString(4, req.getParameter("snrCommentaryText"));
						cstmt.setString(5, thisU.getNameForLastUpdatedBy());
						cstmt.execute();
			        	req.setAttribute("userMessage", "Commentary added");
				    	String extraScreen = req.getParameter("extraScreen");
				    	if ((extraScreen !=null) && (extraScreen.equals("pCI"))) {
				    		req.setAttribute("buttonPressed", "viewComI");
				    	} else {
				    		req.setAttribute("buttonPressed", "viewCom");
				    	}
				    } catch (SQLException ex) {
			        	req.setAttribute("userMessage", "Error: Unable to add commentary, " + ex.getMessage());
			        	req.removeAttribute("buttonPressed");
				    } finally {
						cstmt.close();
						conn.close();
				    }
				} else if (buttonPressed.startsWith("addCom")) {
					req.setAttribute("snrCommentaryTypeInit", "PreCheck");
				} else if ((buttonPressed.equals("updateND")) || (buttonPressed.equals("updateF"))) {
			    	req.setAttribute("buttonPressed", "showBU");
				    try {
				    	conn = DriverManager.getConnection(url);
				    	conn.setAutoCommit(false);
			            for (Iterator<Map.Entry<String,String[]>> it = req.getParameterMap().entrySet().iterator(); it.hasNext(); ) {
			                Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();
			                if (entry.getKey().startsWith("preCheckBatchId")) {
						    	cstmt = conn.prepareCall("{call Complete_PreCheck(?,?,?)}");
								cstmt.setLong(1, Long.parseLong( entry.getValue()[0]));
								cstmt.setString(2, (buttonPressed.equals("updateND")?"Not Done":"Failed"));
								cstmt.setString(3, thisU.getNameForLastUpdatedBy());
								boolean ok = false;
								if (cstmt.execute()) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										ok = rs.getString(1).equalsIgnoreCase("Y");
									}
								}
								if (!ok) {
									throw new SQLException("negative return code from Complete_PreCheck()");
								}
			                }
			            }
						conn.commit();
			        	req.setAttribute("userMessage", "Pre-Check status updated to " +
			        			(buttonPressed.equals("updateND")?"'Not Done'":"'Failed'"));
				    } catch (SQLException ex) {
				    	conn.rollback();
			        	req.setAttribute("userMessage", "Error: Unable to update Pre-Check items, " + ex.getMessage());
				    } finally {
				    	if (cstmt != null) {
				    		cstmt.close();
				    	}
						conn.close();
				    }
				} else if (buttonPressed.equals("updateI")) {
		        	req.setAttribute("buttonPressed", "preCheckUpdate");
					@SuppressWarnings("unchecked")
					Collection<PreCheckListItem> pciList = (Collection<PreCheckListItem>) 
							session.getAttribute(ServletConstants.PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION);
					validatePreCheckItemUpdates(req, pciList, session);
				    try {
				    	conn = DriverManager.getConnection(url);
				    	conn.setAutoCommit(false);
						for (Iterator<PreCheckListItem> it = pciList.iterator(); it.hasNext(); ) {
							PreCheckListItem pcli = it.next();
							cstmt = conn.prepareCall("{call UpdatePreCheckItem(?,?,?,?,?,?,?)}");
							cstmt.setLong(1, pcli.getPreCheckId());
							cstmt.setString(2, pcli.getItemName());
							cstmt.setString(3, pcli.getStringValue());
							cstmt.setDate(4, pcli.getDateValue());
							cstmt.setDouble(5, pcli.getNumberValue());
							cstmt.setString(6, thisU.getNameForLastUpdatedBy());
							cstmt.setString(7, pcli.getStorageType());
							boolean found = cstmt.execute();
							if (found) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (!rs.getString(1).equalsIgnoreCase("Y")) {
										throw new Exception("negative return code from UpdatePreCheckItem()");
									}
								} else {
									throw new Exception("no return code from UpdatePreCheckItem()");
								}
							} else {
								throw new Exception("no return code from UpdatePreCheckItem()");
							}
						}
						conn.commit();
			        	req.setAttribute("userMessage", "Pre-Check updated");
				    } catch (SQLException ex) {
				    	conn.rollback();
			        	req.setAttribute("userMessage", "Error: Unable to update Pre-Check items, " + ex.getMessage());
				    } finally {
						cstmt.close();
						conn.close();
				    }
				} else if ((buttonPressed.equals("Passed")) || (buttonPressed.equals("Failed")) || 
						(buttonPressed.equals("Not Done"))) {
				    try {
				    	conn = DriverManager.getConnection(url);
				    	conn.setAutoCommit(false);
				    	cstmt = conn.prepareCall("{call Complete_PreCheck(?,?,?)}");
						cstmt.setLong(1, Long.parseLong(preCheckId));
						cstmt.setString(2, buttonPressed);
						cstmt.setString(3, thisU.getNameForLastUpdatedBy());
						boolean ok = false;
						if (cstmt.execute()) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								ok = rs.getString(1).equalsIgnoreCase("Y");
							}
						}
						if (!ok) {
							throw new Exception("negative return code from Complete_PreCheck()");
						}
						String commentary = req.getParameter("pcCommentaryText");
						if (!StringUtil.hasNoValue(commentary)) {
					    	cstmt = conn.prepareCall("{call AddSNRCommentary(?,?,?,?,?)}");
							cstmt.setLong(1, Long.parseLong(snrId));
							cstmt.setLong(2, Long.parseLong(preCheckId));
							cstmt.setString(3, "PreCheck");
							cstmt.setString(4, commentary);
							cstmt.setString(5, thisU.getNameForLastUpdatedBy());
							cstmt.execute();
						}
			        	req.setAttribute("userMessage", "Pre-Check completed");
			        	req.setAttribute("preCheckId", "-1");
						conn.commit();
				    } catch (SQLException ex) {
			    		conn.rollback();
			        	req.setAttribute("userMessage", "Error: Unable complete Pre-Check, " + ex.getMessage());
				    } finally {
			        	req.removeAttribute("buttonPressed");
						cstmt.close();
						conn.close();
				    }
				} else if (buttonPressed.equals("clearAll")) {
					for (int i = 0; i < filters.length; i++) {
				    	req.setAttribute(filters[i], "All");
					}
				} else {
					for (int i = 0; i < filters.length; i++) {
						filterValues.put(filters[i], req.getParameter(filters[i]));
					}
					if (buttonPressed.equals("clear")) {
						filterValues.put(whichFilter, "All");
					}
					for (int i = 0; i < filters.length; i++) {
				    	req.setAttribute(filters[i], filterValues.get(filters[i]));
					}
				}
			}
		} catch (Exception ex) {
        	req.setAttribute("userMessage", "Error: PreCheckMaintenanceServlet, " + ex.getMessage());
        	//req.removeAttribute("buttonPressed");
		} finally {
			Random r = new Random();
			String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		  	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
		  	dispatcher.forward(req,resp);
		}  	
	}
	
	private void validatePreCheckItemUpdates(HttpServletRequest req, 
			Collection<PreCheckListItem> pciList, HttpSession session) 
					throws Exception {
		String missing = null;
		String invalid = null;
		for (Iterator<PreCheckListItem> it = pciList.iterator(); it.hasNext(); ) {
			PreCheckListItem pcli = it.next();
			String screenValue = req.getParameter("select" + pcli.getItemName());
			if (pcli.isRequired() && StringUtil.hasNoValue(screenValue)) {
				missing = pcli.getItemDescription();
			} else if (!pcli.setStringValue(screenValue)) {
				invalid = pcli.getItemDescription();
			}
		}
		session.setAttribute(ServletConstants.PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION,
				pciList);
		if (missing != null) {
			throw new Exception("a value must be entered for " + missing);
		} else if (invalid != null) {
			throw new Exception("invalid value entered for " + invalid);
		}
	}

}
