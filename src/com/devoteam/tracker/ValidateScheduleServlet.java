package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.devoteam.tracker.model.BOEngineer;
import com.devoteam.tracker.model.FieldEngineer;
import com.devoteam.tracker.model.Pot;
import com.devoteam.tracker.model.SNR;
import com.devoteam.tracker.model.SNRScheduleSpreadsheet;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class ValidateScheduleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1906950674883213017L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private String url;
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

		String destination = "/scheduling.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
	    	String scheduleFileName = req.getParameter("scheduleFileName");
	    	session.removeAttribute(ServletConstants.SCHEDULE_SPREADSHEET_NAME_IN_SESSION);
	    	session.removeAttribute(ServletConstants.SCHEDULE_SPREADSHEET_COPY_NAME_IN_SESSION);
			session.removeAttribute(ServletConstants.SCHEDULED_SNRS_IN_SESSION);
			session.removeAttribute(ServletConstants.INVALID_SNRS_IN_SESSION);
			session.removeAttribute(ServletConstants.SCHEDULED_SNR_WARNINGS_IN_SESSION);
	    	url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
	        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
	        BlobKey blobKey = blobs.get("scheduleXLS");
	        BlobstoreInputStream in = new BlobstoreInputStream(blobKey); 
			String[] snrTitles = SNRScheduleSpreadsheet.getSNRTitles();
			boolean warnings = false;
	
	        try {
	        	Workbook workbook = WorkbookFactory.create(in);
	
	        	Sheet scheduleSheet = workbook.getSheetAt(0);
	        	
				Row weekRow = scheduleSheet.getRow(0);
				Row mYRow = scheduleSheet.getRow(1);
				Row dateRow = scheduleSheet.getRow(2);
				Row dayRow = scheduleSheet.getRow(3);
				Row headerRow = scheduleSheet.getRow(4);
				ArrayList<SNRScheduleSpreadsheet> validSNRs = new ArrayList<SNRScheduleSpreadsheet>();
				ArrayList<SNRScheduleSpreadsheet> invalidSNRs = new ArrayList<SNRScheduleSpreadsheet>();
				/*for (Iterator<Cell> cIt = dateRow.cellIterator(); cIt.hasNext(); ) {
					Cell c = cIt.next();
					String cV = c.getStringCellValue().trim();
					String cV2 = cV;
				}*/
				boolean firstWFRowFound = false;
				int firstDataRowNo = 0;
				int lastDataRowNo = 0;
				for (int i = 5; i <= scheduleSheet.getLastRowNum(); i++ ) {
					Row thisRow = scheduleSheet.getRow(i);
					if (thisRow != null) {
						Cell firstCell = thisRow.getCell(0);
						if (firstCell != null) {
							String cellValue = firstCell.getStringCellValue();
							if ((cellValue != null) && (!cellValue.isEmpty())) {
								if (!firstWFRowFound) {
									firstWFRowFound = true;
									firstDataRowNo = i;
								}
								lastDataRowNo = i;
							}
						}
					}
				}
				
				///Row firstRow = scheduleSheet.getRow(firstDataRowNo);
				for (int i = 1; i <= headerRow.getLastCellNum(); i++ ) {
					for (int j = firstDataRowNo; j <= lastDataRowNo; j++ ) {
						Row thisRow = scheduleSheet.getRow(j);
						Cell thisCell = thisRow.getCell(i);
						if (thisCell != null) {
							String cellValue = null;
							if (thisCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								cellValue = Double.toString(thisCell.getNumericCellValue());
							} else if (thisCell.getCellType() == Cell.CELL_TYPE_STRING) {
								cellValue = thisCell.getStringCellValue();
							}
							if ((cellValue != null) && (!cellValue.isEmpty())) {
								//check columns are correct
								for (int k = i; k < (i + snrTitles.length); k++) {
									String header = headerRow.getCell(k).getStringCellValue();
									if ((header == null) || (header.isEmpty()) || 
											(!header.trim().equals(snrTitles[k-i]))) {
										throw new Exception("Invalid column header, expected: " + 
												snrTitles[k-i] + ", found: " + (header==null?"null":header));
									}	
								}
								//if they are, find the date	
								Date scheduledDate = null;
								try {
									String startOfMonth = 
											dateFormatter.format(mYRow.getCell(i).getDateCellValue());
									int dd = (int) dateRow.getCell(i).getNumericCellValue();
									String jdbcDate = startOfMonth.substring(0, 8) + (dd<10?"0":"") + dd;
									scheduledDate = Date.valueOf(jdbcDate);
								} catch (Exception ex) {
									throw new Exception("Invalid date: " + i + ", " + j);
								}
								//store everything for this date
								SNRScheduleSpreadsheet prevSSS = null;
								for (int l = j; l <= lastDataRowNo; l++ ) {
									Row dataRow = scheduleSheet.getRow(l);
									thisCell = dataRow.getCell(i);
									if (thisCell != null) {
										cellValue = null;;
										if (thisCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
											cellValue = Double.toString(thisCell.getNumericCellValue());
										} else if (thisCell.getCellType() == Cell.CELL_TYPE_STRING) {
											cellValue = thisCell.getStringCellValue();
										}
										if ((cellValue != null) && (!cellValue.isEmpty())) {
											SNRScheduleSpreadsheet sss = new SNRScheduleSpreadsheet(dataRow, i,
													scheduledDate, dataRow.getCell(0).getStringCellValue().trim());
											boolean valid = false;
											//check snr exists
											SNR snr = getSNR(sss.getSite(), sss.getNRId());
											if (snr == null) {
												sss.addProblem("No existing database record found for site " + 
														sss.getSite() +	" and NR Id " + sss.getNRId());
											} else {
												sss.setSNRId(snr.getSNRId());
												sss.setStatus(snr.getStatus());
												//check field engineers, third parties and bo engineers exist
												valid = validateFieldEngineers(sss, prevSSS) && validateBOEngineers(sss);
											}
											
											if (valid) {
												if ((prevSSS == null) || (!prevSSS.getSite().equals(sss.getSite())) ||
														(!prevSSS.getNRId().equals(sss.getNRId()))) {
													validSNRs.add(sss);
													prevSSS = sss;
												} else {
													SNRScheduleSpreadsheet storedSSS = validSNRs.get(validSNRs.size()-1);
													storedSSS.addFieldEngineers(sss.getFieldEngineers());
													storedSSS.addBOEngineers(sss.getBOEngineers());
												}
											} else {
												invalidSNRs.add(sss);
											}
										}
									}
								}
								//jump to cell where next day should start (minus 1 for the i++)  
								i += (snrTitles.length - 1);
								break;
							}
						}
					}	
				}
				
				ArrayList<SNRScheduleSpreadsheet> scheduledSNRs = new ArrayList<SNRScheduleSpreadsheet>();
				scheduledSNRs.addAll(validSNRs);
				for (Iterator<SNRScheduleSpreadsheet> it = validSNRs.iterator(); it.hasNext(); ) {
					SNRScheduleSpreadsheet thisSSS = it.next();
					//check if this snr is also in invalidSNRs - if it is remove it from scheduledSNRs
					String site = thisSSS.getSite();
					String nrId = thisSSS.getNRId();
					boolean stillValid = true;
					for (Iterator<SNRScheduleSpreadsheet> it2 = invalidSNRs.iterator(); it2.hasNext(); ) {
						SNRScheduleSpreadsheet invSSS = it2.next();
						if ((invSSS.getSite().equals(site)) && (invSSS.getNRId().equals(nrId))) {
							scheduledSNRs.remove(thisSSS);
							stillValid = false;
							break;
						}
					}
					if (stillValid) {
					//check if each snr has a no. 1 field engineer
						boolean hasOne = false;
						for (Iterator<FieldEngineer> it2 = thisSSS.getFieldEngineers().iterator(); it2.hasNext(); ) {
							FieldEngineer fe = it2.next();
							if (fe.getRank() == 1) {
								hasOne = true;
								break;
							}
						}
						if ((!hasOne) && (!hasNo1FEOnDB(thisSSS.getSNRId()))) { 
							thisSSS.addProblem("Database record for site " + thisSSS.getSite() + 
									" and NR Id " + thisSSS.getNRId() + " currently has no primary field engineeer");
							warnings = true;
						}
						if ((thisSSS.getSNRId() != -1) && (!thisSSS.canSchedule())) { 
							thisSSS.addProblem("Database record for site " + thisSSS.getSite() + 
									" and NR Id " + thisSSS.getNRId() + " currently has a status of " +
									thisSSS.getStatusDisplay() + " and cannot be scheduled");
							warnings = true;
						}
					}
				}
				session.setAttribute(ServletConstants.SCHEDULED_SNRS_IN_SESSION, scheduledSNRs);
				session.setAttribute(ServletConstants.INVALID_SNRS_IN_SESSION, invalidSNRs);
				if (warnings) {
					session.setAttribute(ServletConstants.SCHEDULED_SNR_WARNINGS_IN_SESSION, "true");
				}
				session.setAttribute(ServletConstants.SCHEDULE_SNR_FILE_NAME_IN_SESSION, scheduleFileName);
	
			} catch (Exception ex) {
				session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
						"Error: " + ex.getMessage());
			} finally {
		        blobs.remove("scheduleXLS");
		    }
	    }
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		resp.sendRedirect(destination+ran);
	}

    private boolean hasNo1FEOnDB(long snrId) 
        	throws Exception {
    	boolean found = false;
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetSNRNo1FieldEngineer(?)}");
			cstmt.setLong(1, snrId);
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				found = rs.next();
			}
		} catch (SQLException e) {
			throw new Exception("calling GetSNRNo1FieldEngineer(), " + e.getMessage());
		} finally {
			cstmt.close();
			conn.close();
		}
		return found;
    }

    private boolean validateBOEngineers(SNRScheduleSpreadsheet sss) 
            	throws Exception {
        	boolean valid = true;
    	for (Iterator<BOEngineer> it = sss.getBOEngineers().iterator(); it.hasNext(); ) {
    		BOEngineer be = it.next();
    		if (be.getFirstName() != null) {
    			long userId = 0;
    			Connection conn = null;
    			CallableStatement cstmt = null;
    			try {
    				conn = DriverManager.getConnection(url);
    				cstmt = conn.prepareCall("{call GetUserIdFromName(?,?,?)}");
    				cstmt.setString(1, be.getFirstName());
    				cstmt.setString(2, be.getSurname());
    				cstmt.setString(3, be.getSuffix());
    				if (cstmt.execute()) {
    					ResultSet rs = cstmt.getResultSet();
    					if (rs.next()) {
    						userId = rs.getLong(1);
    					}
    				}
    			} catch (SQLException e) {
    				throw new Exception("calling GetUserIdFromName(), " + e.getMessage());
    			} finally {
    				if ((cstmt !=null) && (!cstmt.isClosed())) cstmt.close();
    				if ((conn !=null) && (!conn.isClosed())) conn.close();
    			}
    			if (userId > 0) {
    				be.setUserId(userId);
    			} else {	
        			sss.addProblem("BO Engineer name " + be.getName() + " is not in the database");
        			valid = false;
    			}
    			
    		} else {
    			sss.addProblem("BO Engineer name " + be.getName() + " is not in the expected format");
    			valid = false;
    		}
    	}
  	
    	return valid;
    }
    
    private boolean validateFieldEngineers(SNRScheduleSpreadsheet sss, SNRScheduleSpreadsheet prevSSS) 
            	throws Exception {
    	boolean valid = true;
    	if (sss.getFieldEngineers().isEmpty()) {
    		if ((prevSSS == null) || (!prevSSS.getSite().equals(sss.getSite())) ||
					(!prevSSS.getNRId().equals(sss.getNRId()))) {
    			sss.addProblem("No field engineers found in spreadsheet entry dated " + 
    					sss.getScheduledDateString() + " for site " + 
    					sss.getSite() +	" and NR Id " + sss.getNRId());
    			valid = false;
			}
    	} else {
	    	for (Iterator<FieldEngineer> it = sss.getFieldEngineers().iterator(); it.hasNext(); ) {
	    		FieldEngineer fe = it.next();
	    		String feName = fe.getName();
	    		if (fe.getRank() < 1) {
	    			sss.addProblem("Field Engineer " + feName + " no. must be 1 or higher");
	    			valid = false;
	    		}
	    		if (fe.getFirstName() != null) {
	    			long userId = 0;
	    			Connection conn = null;
	    			CallableStatement cstmt = null;
	    			try {
	    				conn = DriverManager.getConnection(url);
	    				cstmt = conn.prepareCall("{call GetUserIdFromNameAndType(?,?,?,?)}");
	    				cstmt.setString(1, fe.getFirstName());
	    				cstmt.setString(2, fe.getSurname());
	    				cstmt.setString(3, fe.getSuffix());
	    				cstmt.setString(4, User.USER_TYPE_THIRD_PARTY);
	    				if (cstmt.execute()) {
	    					ResultSet rs = cstmt.getResultSet();
	    					if (rs.next()) {
	    						userId = rs.getLong(1);
	    					}
	    				}
	    			} catch (SQLException e) {
	    				throw new Exception("calling GetUserIdFromNameAndType(), " + e.getMessage());
	    			} finally {
	    				cstmt.close();
	    				conn.close();
	    			}
	    			if (userId > 0) {
	    				fe.setUserId(userId);
	    			} else {
	        			sss.addProblem("Field Engineer " + feName + " is not in the database " +
	        					"as a third party user");
	        			valid = false;
	    			}
	    			
	    		} else {
	    			sss.addProblem("Field Engineer name " + feName + " is not in the expected format");
	    			valid = false;
	    		}
				long thirdPartyId = 0;
				Connection conn = null;
				CallableStatement cstmt = null;
				try {
					conn = DriverManager.getConnection(url);
					cstmt = conn.prepareCall("{call GetThirdPartyIdFromName(?)}");
					cstmt.setString(1, fe.getVendor());
					if (cstmt.execute()) {
						ResultSet rs = cstmt.getResultSet();
						if (rs.next()) {
							thirdPartyId = rs.getLong(1);
						}
					}
				} catch (SQLException e) {
					throw new Exception("calling GetThirdPartyIdFromName(), " + e.getMessage());
				} finally {
					cstmt.close();
					conn.close();
				}
				if (thirdPartyId > 0) {
					fe.setThirdPartyId(thirdPartyId);
				} else {	
	    			sss.addProblem("Vendor " + fe.getVendor() + " is not in the database as a third party");
	    			valid = false;
				}
	    	}
    	}
    	return valid;
    }
    
    private SNR getSNR(String site, String nrId) 
        	throws Exception {
    	long snrId = 0;
    	SNR snr = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetSNRFromSiteAndNRId(?,?)}");
			cstmt.setString(1, site);
			cstmt.setString(2, nrId);
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					snrId = rs.getLong(1);
					if (snrId != -1) {
						snr = new SNR(snrId, site, nrId, rs.getString(2));
					}
				}
			}
		} catch (SQLException e) {
			throw new Exception("calling GetSNRFromSiteAndNRId(), " + e.getMessage());
		} finally {
			cstmt.close();
			conn.close();
		}
    	return snr;
    }
}
