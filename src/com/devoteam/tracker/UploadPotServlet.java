package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.Pot;
import com.devoteam.tracker.model.PotSpreadsheet;
import com.devoteam.tracker.model.PotSpreadsheetSNR;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;

public class UploadPotServlet extends HttpServlet {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 5143344027279107635L;

	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		String destination = "/scheduling.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid user id and password");
		} else {
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			long potId = -1;
			try {
				Connection conn = null;
				CallableStatement cstmt = null;
				PotSpreadsheet sheetPot = (PotSpreadsheet)session.getAttribute(ServletConstants.POT_SPREADSHEET_COPY_NAME_IN_SESSION);
				boolean potDone = false;
				try {
					conn = DriverManager.getConnection(url);
					conn.setAutoCommit(false);
					/*if (sheetPot.getPotExists()) {
						cstmt = conn.prepareCall("{call UpdatePotFromSpreadsheet(?,?,?,?,?)}");
						cstmt.setLong(1, sheetPot.getPotId());
						cstmt.setString(2, sheetPot.getJobType());
						cstmt.setDate(3, sheetPot.getPotDate());
						cstmt.setString(4, sheetPot.getUpdateProjectRequestor()?sheetPot.getprojectRequestor():null);
						cstmt.setString(5, thisU.getNameForLastUpdatedBy());
					} else {*/
						cstmt = conn.prepareCall("{call CreatePotFromSpreadsheet(?,?,?,?,?,?)}");
						cstmt.setLong(1, sheetPot.getCustomerId());
						cstmt.setString(2, sheetPot.getJobType());
						cstmt.setString(3, sheetPot.getPotName());
						cstmt.setDate(4, sheetPot.getPotDate());
						cstmt.setString(5, sheetPot.getUpdateProjectRequestor()?sheetPot.getprojectRequestor():null);
						cstmt.setString(6, thisU.getNameForLastUpdatedBy());
					//}
					if (cstmt.execute()) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							long ret = rs.getLong(1);
							potDone = ret != -1;
							potId = /*sheetPot.getPotExists()?sheetPot.getPotId():*/ret;
						}
					}
					cstmt.close();
					if (potDone) {
						//try {
							//conn = DriverManager.getConnection(url);
							Collection<PotSpreadsheetSNR> snrDataRows = sheetPot.getSNRDataRows();
							for (Iterator<PotSpreadsheetSNR> it = snrDataRows.iterator(); it.hasNext(); ) {
								PotSpreadsheetSNR pSS = it.next();
								/*if (pSS.getSNRExists()) {
									cstmt = conn.prepareCall("{call UpdateSNRFromSpreadsheet(?,?,?,?,?,?)}");
									cstmt.setLong(1, pSS.getSNRId());
									cstmt.setDate(2, pSS.getEF345ClaimDT());
									cstmt.setDate(3, pSS.getEF360ClaimDT());
									cstmt.setDate(4, pSS.getEF400ClaimDT());
									cstmt.setDate(5, pSS.getEF410ClaimDT());
									cstmt.setString(6, thisU.getNameForLastUpdatedBy());
								} else {*/
									cstmt = conn.prepareCall("{call CreateSNRFromSpreadsheet(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
									cstmt.setLong(1, potId);
									cstmt.setLong(2, sheetPot.getCustomerId());
									cstmt.setString(3, pSS.getSite());
									cstmt.setString(4, pSS.getNRId());
									cstmt.setString(5, pSS.getUpgradeType());
									//cstmt.setString(6, pSS.getEastings());
									//cstmt.setString(7, pSS.getNorthings());
									cstmt.setString(6, pSS.getPostcode());
									/*cstmt.setString(9, pSS.getRegion());
									cstmt.setDate(10, pSS.getEF345ClaimDT());
									cstmt.setDate(11, pSS.getEF360ClaimDT());
									cstmt.setDate(12, pSS.getEF400ClaimDT());
									cstmt.setDate(13, pSS.getEF410ClaimDT());*/
									cstmt.setString(7, pSS.getVodafone2G());
									cstmt.setString(8, pSS.getVodafone3G());
									cstmt.setString(9, pSS.getVodafone4G());
									cstmt.setString(10, pSS.getTEF2G());
									cstmt.setString(11, pSS.getTEF3G());
									cstmt.setString(12, pSS.getTEF4G());
									cstmt.setString(13, pSS.getPaknetPaging());
									cstmt.setString(14, pSS.getSecGWChanges());
									cstmt.setString(15, pSS.getPower());
									cstmt.setString(16, pSS.getSurvey());
									cstmt.setString(17, pSS.getHardwareVendor());
									cstmt.setString(18, thisU.getNameForLastUpdatedBy());
								//}
								if (cstmt.execute()) {
									ResultSet rs = cstmt.getResultSet();
									if (rs.next()) {
										String ret = rs.getString(1);
										if (!ret.equalsIgnoreCase("Y")) {
											throw new SQLException("Unable to " + 
													(/*pSS.getSNRExists()?"update":*/"create") +
													" SNR " + pSS.getNRId() + ", " + ret);
										}
									}
								}
								cstmt.close();
							}
						/*} catch (SQLException e) {
							cstmt.close();
							throw new Exception("Problem uploading SNRs, " + e.getMessage());
						} finally {
							conn.close();
						}*/
					}
				} catch (SQLException e) {
					conn.rollback();
					//cstmt.close();
					//throw new Exception("Unable to " + 
						//	(/*sheetPot.getPotExists()?"update":*/"create") +
							//" pot, " + e.getMessage());
					throw new Exception("Unable to create pot: " + e.getMessage());
				} finally {
					conn.commit();
					conn.close();
				}
				String potName = sheetPot.getPotName();
				session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
						"Pot " + potName + " uploaded successfully");
			} catch (Exception ex) {
				session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
						"Error: " + ex.getMessage());
			}
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		//resp.sendRedirect(destination+ran);
      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
    }

}
