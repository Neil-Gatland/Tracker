package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.devoteam.tracker.model.PotDetail;
import com.devoteam.tracker.model.PotHeader;
import com.devoteam.tracker.model.PotSpreadsheetSNR;
import com.devoteam.tracker.model.ScheduleList;
import com.devoteam.tracker.util.ServletConstants;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class ValidateFullPotServlet extends HttpServlet {

	private static final long serialVersionUID = -4808455757103243505L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private StringBuilder problems;
	private StringBuilder warnings;
	private String url;
	private Connection conn;
	private CallableStatement cstmt;

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
	    	session.removeAttribute(ServletConstants.WARNING_ARRAY_NAME_IN_SESSION);
	    	session.removeAttribute(ServletConstants.POT_HEADER_IN_SESSION);
	    	session.removeAttribute(ServletConstants.POT_DETAIL_ARRAY_IN_SESSION);
			String potFileName = req.getParameter("potFileName");
			String canConfirmPot = "N";
			ArrayList<PotDetail> potDetailList = new ArrayList<PotDetail>();
	        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
	        BlobKey blobKey = blobs.get("potXLS");
	        BlobstoreInputStream in = new BlobstoreInputStream(blobKey); 
			problems = new StringBuilder();
			warnings = new StringBuilder();
			try {
				// Check that the spreadsheet has exactly two workbooks
				Workbook workbook = WorkbookFactory.create(in);
				if (workbook.getNumberOfSheets() != 2) {
					throw new Exception("Workbook must have two sheets");
				}
				// Check that the first spreadsheet only has two rows
				Sheet headerSheet = workbook.getSheetAt(0);
				if ((headerSheet.getFirstRowNum() != 0) || (headerSheet.getLastRowNum() != 1)) {
					throw new Exception("Header sheet must only have two rows"); 
				}
				// Check that the first header spreadsheet has the expected columns
				Row titleRowH = headerSheet.getRow(0);
				String[] headerDataTitles = PotHeader.getHeaderDataTitles();
				//check number of columns in header 
	            if (titleRowH.getLastCellNum() != headerDataTitles.length) {
					throw new Exception("Header sheet must have " + 
						headerDataTitles.length + " columns");
	            }
				//check column titles in header spreadsheet 
	            int i = 0;
				for (Iterator<Cell> cIt = titleRowH.cellIterator(); cIt.hasNext(); ) {
					Cell c = cIt.next();
					String cV = c.getStringCellValue().trim().replaceAll(" +", " ");
					if (!headerDataTitles[i].equalsIgnoreCase(cV)) {
						problems.append("header sheet, column " + String.valueOf(i+1) + ": found: " +
							cV + ", expected: " + headerDataTitles[i] + "|");
					}
					i++;
				}
				// Check that the second sheet starts at row 1
				Sheet detailSheet = workbook.getSheetAt(1);
				if (detailSheet.getFirstRowNum() != 0) {
					throw new Exception("Data sheet must start at row 1"); 
				}
				// Check that the second detail sheet has the expected number of columns
	            Row detailRowH = detailSheet.getRow(0);
				String[] detailDataTitles = PotDetail.getDetailDataTitles();
	            if (detailRowH.getLastCellNum() != detailDataTitles.length) {
					throw new Exception("Header sheet must have " + 
						detailDataTitles.length + " columns");
	            }
				//check column titles in detail spreadsheet 
	            i = 0;
				for (Iterator<Cell> cIt = detailRowH.cellIterator(); cIt.hasNext(); ) {
					Cell c = cIt.next();
					String cV = c.getStringCellValue().trim().replaceAll(" +", " ");
					if (!detailDataTitles[i].equalsIgnoreCase(cV)) {
						problems.append("Header sheet, column " + String.valueOf(i+1) + ": found: " +
							cV + ", expected: " + detailDataTitles[i] + "|");
					}
					i++;
				}	
				// Validate customer name, job type and pot date
				Row dataRowH = headerSheet.getRow(1);
				String customerName = "", jobType = "", potDate ="";
				Date potDateCheck = null;
				// today date for past date checking
				Calendar today = Calendar.getInstance();
				today.set(Calendar.HOUR_OF_DAY,0);
				today.set(Calendar.MINUTE,0);
				today.set(Calendar.SECOND,0);
				today.set(Calendar.MILLISECOND,0);
				Date todayDate = today.getTime();
				i = 0;
				for (Iterator<Cell> cIt = dataRowH.cellIterator(); cIt.hasNext(); ) {
					Cell c = cIt.next();
					if (i==0) {
						customerName = c.getStringCellValue().trim().replaceAll(" +", " ");
					} else if (i==1) {
						jobType = c.getStringCellValue().trim().replaceAll(" +", " ");					
					} else if (i==2) {
						try {
							potDate = c.getDateCellValue().toLocaleString();
						} catch( Exception ex) {
							problems.append("Pot date is invalid|");
						}
						potDateCheck = c.getDateCellValue();
						if (todayDate.after(potDateCheck)) {
							warnings.append("Pot date is in the past|");
						}
					}
					i++;
				} 
				url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				if (!validCustomerName(customerName)) {
					problems.append("Customer does not exist on the database|");
				}
				if (!validJobType(jobType)) {
					problems.append("Job type does not exist on the database|");
				}
				if (problems.length()==0) {
					PotHeader ph = new PotHeader(customerName,jobType,potDateCheck);
					session.setAttribute(ServletConstants.POT_HEADER_IN_SESSION, ph);
				}
				// Validate data rows and trailer
				String site = "", nrId = "", upgradeType ="", hardwareVendor = "", 
						vf2G = "", vf3G = "", vf4G = "", tef2G = "", tef3G = "", tef4G = "",
						paknetPaging = "", secGW = "", power = "", survey = "", other = "",
						boEngineer = "", fieldEngineer = "", feCompany = "";	
				Date scheduledDateCheck = null;
				boolean trailerFound = false;	
				int missingCount = 0, missingWarningCount = 0;
				for (int row=1; row<detailSheet.getLastRowNum()+1; row++) {
		            Row detailRow = detailSheet.getRow(row);
		            int techCount = 0;
					i=0;
					if (trailerFound) {
						problems.append("There are site rows after the trailer|");
					}
					for (Iterator<Cell> cIt = detailRow.cellIterator(); cIt.hasNext(); ) {
						Cell c = cIt.next();
						if (i==0) {
							// Site
							try {
								Double siteDouble = c.getNumericCellValue();
								site = 	Double.toString(siteDouble).substring(0, Double.toString(siteDouble).length() -2);
							} catch( Exception ex) {
								warnings.append("Site on row "+row +" is not numeric|");
								site = c.getStringCellValue();
							}
							if (site.equals("999999")) {
								trailerFound = true;
							} else if ((site.equals("0"))||(site.equals(""))) {
								problems.append("Site is missing on row "+row+"|");
							} else if (!validSite(customerName,site)) {
								problems.append("Site on row "+row+" does not exist on database|");
							}
						} else if (i==1) {
							// NR Id							
							try {
								nrId = c.getStringCellValue().trim().replaceAll(" +", " ");
								if (nrId.equals("MISSING"))
										missingCount++;
							} catch( Exception ex) {
								problems.append("NR Id on row "+row +" is invalid|");
							}
							String checkResult = nrIdForSite(nrId,site);
							if (checkResult.startsWith("Error: ")) {
								problems.append(checkResult.substring(7,checkResult.length())+" (row "+row+")|");
							} else if (checkResult.startsWith("Warning: ")) {
								warnings.append(checkResult.substring(9,checkResult.length())+" (row "+row+")|");
								if (nrId.equals("MISSING"))
									missingWarningCount++;							}
						} else if (i==2) {
							// Upgrade type 
							try {
								upgradeType = c.getStringCellValue().trim().replaceAll(" +", " ");
							} catch( Exception ex) {
								problems.append("Upgrade type on row "+row +" is invalid|");
							}
						} else if (i==3) {
							// Scheduled date
							try {
								String scheduledDate = c.getDateCellValue().toLocaleString();
							} catch( Exception ex) {
								problems.append("Scheduled date on row "+row+ " is invalid|");
							} 
							scheduledDateCheck = c.getDateCellValue();
							if (todayDate.after(scheduledDateCheck)) {
								warnings.append("Scheduled date on row "+row+" is in the past|");
							}
						} else if ((i>=4)&&(i<=14)) {
							// Technology
							String technology = "";
							try {
								technology = c.getStringCellValue().trim().replaceAll(" +", " ");
							} catch( Exception ex) {
								problems.append(technologyName(i)+" on row "+row+ " is invalid|");
							}
							if (!((technology.equals("Y"))||
									(technology.equals("y"))||
									(technology.equals("N"))||
									(technology.equals("n")))) {
								problems.append(technologyName(i)+" on row "+row+" not either Y or N|");
							} else {
								if ((technology.equals("Y"))||(technology.equals("y"))) {
									techCount++;
								}	
								String techValue = technology;
								if (techValue.equals("y")) 
									techValue = "Y";
								else if (techValue.equals("n"))
									techValue = "N";
								switch (i) {
								case 4: 
									vf2G = techValue;
									break;
								case 5: 
									vf3G = techValue;
									break;
								case 6: 
									vf4G = techValue;
									break;
								case 7: 
									tef2G = techValue;
									break;
								case 8: 
									tef3G = techValue;
									break;
								case 9: 
									tef4G = techValue;
									break;
								case 10: 
									paknetPaging = techValue;
									break;
								case 11: 
									secGW = techValue;
									break;
								case 12: 
									power = techValue;;
									break;
								case 13: 
									survey = techValue;
									break;
								case 14: 
									other = techValue;
									break;
								}
							}							
						} else if (i==15) {
							// Hardware Vendor
							try {
								hardwareVendor = c.getStringCellValue().trim().replaceAll(" +", " ");
							} catch( Exception ex) {
								problems.append("Hardware vendor on row "+row +" is invalid|");
							} 
						} else if (i==16) {
							// BO Engineer
							try {
								boEngineer = c.getStringCellValue().trim().replaceAll(" +", " ");
							} catch( Exception ex) {
								problems.append("BO Engineer on row "+row +" is invalid|");
							} 
							if (!validEngineer(boEngineer,"BO Engineer")) {
								problems.append("BO Engineer on row "+row+" not found on the database|");
							}
						} else if (i==17) {
							// Field Engineer
							try {
								fieldEngineer = c.getStringCellValue().trim().replaceAll(" +", " ");
							} catch( Exception ex) {
								problems.append("Field Engineer on row "+row +" is invalid|");
							} 
							if (!validEngineer(fieldEngineer,"Field Engineer")) {
								problems.append("Field Engineer on row "+row+" not found on the database|");
							}
						} else if (i==18) {
							// FE Company
							try {
								feCompany = c.getStringCellValue().trim().replaceAll(" +", " ");
							} catch( Exception ex) {
								problems.append("FE Company on row "+row +" is invalid|");
							} 
							if (!validFeCompany(feCompany)) {
								problems.append("FE Company on row "+row+" not found on the database|");
							}
						}
						i++;
					}
					if ((techCount==0)&&(!site.equals("999999"))) {
						warnings.append("No technologies selected on row "+row+"|");
					}
					if (!site.equals("999999")) {
						potDetailList.add(
							new PotDetail(
								site,nrId,upgradeType,scheduledDateCheck,
								vf2G,vf3G,vf4G,tef2G,tef3G,tef4G,paknetPaging,secGW,power,survey,other,
								hardwareVendor,boEngineer,fieldEngineer,feCompany) );
					}
				}	
				if (!trailerFound) {
					problems.append("The expected trailer is missing on the data sheet|");
				}
				if ((missingCount>0)&&(missingCount==missingWarningCount)) {
					warnings.append("All sites marked as missing have existing NRs|");
				}
				if (problems.length() > 0) {
					session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
							"There are validation errors in the selected Pot spreadsheet");
					session.removeAttribute(ServletConstants.POT_HEADER_IN_SESSION);
				} else {
					if (warnings.length() > 0) {

						session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
								"Pot spreadsheet successfully validated - with warnings");
					} else {
						session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
								"Pot spreadsheet successfully validated - no warnings");
					}
					canConfirmPot = "Y";
					session.setAttribute(ServletConstants.POT_DETAIL_ARRAY_IN_SESSION, potDetailList);
				}				
			} catch (Exception ex) {
				session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
						"Error: " + ex.getMessage());
			} finally {
		        if (problems.length() > 0) {
					session.setAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION, problems.toString());
				}
		        if (warnings.length() > 0) {
					session.setAttribute(ServletConstants.WARNING_ARRAY_NAME_IN_SESSION, warnings.toString());
				}
		    }
			session.setAttribute("potLoadActive", "Y");
			session.setAttribute("potFileName", potFileName);
			session.setAttribute("canConfirmPot", canConfirmPot);
			Random r = new Random();
			String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
			resp.sendRedirect(destination+ran);
		}
	}
	
	private boolean validCustomerName(String customerName) {
		boolean valid = false;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call CheckCustomerNameExists(?)}");
			cstmt.setString(1, customerName);
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					if (rs.getLong(1)>0) {
						valid = true;
					}
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
		return valid;
	}
	
	private boolean validJobType(String jobType) {
		boolean valid = false;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call CheckJobTypeExists(?)}");
			cstmt.setString(1, jobType);
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					if (rs.getString(1).equals(jobType)) {
						valid = true;
					}
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
		return valid;
	}
	
	private boolean validSite(String customerName, String site) {
		boolean valid = false;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetSitePostcode(?,?)}");
			cstmt.setString(1, customerName);
			cstmt.setString(2, site);
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					if (!rs.getString(1).equals("Not Found")) {
						valid = true;
					}
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
		return valid;
	}
	
	private String nrIdForSite(String nrId, String site) {
		String result = "Error: Unable to run CheckNrIdForSite()";
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call CheckNrIdForSite(?,?)}");
			cstmt.setString(1, nrId);
			cstmt.setString(2, site);
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					result = rs.getString(1);
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
		return result;
	}
	
	private String technologyName(int column) {
		String name = "Unknown";
		switch (column) {
			case 4: 
				name = "VF 2G";
				break;
			case 5: 
				name = "VF 3G";
				break;
			case 6: 
				name = "VF 4G";
				break;
			case 7: 
				name = "TEF 2G";
				break;
			case 8: 
				name = "TEF 3G";
				break;
			case 9: 
				name = "TEF 4G";
				break;
			case 10: 
				name = "P & P";
				break;
			case 11: 
				name = "Sec GW";
				break;
			case 12: 
				name = "Power";
				break;
			case 13: 
				name = "Survey";
				break;
			case 14: 
				name = "Other";
				break;
		}
		return name;
	}
		
	private boolean validEngineer(String engineerName, String engineerType) {
		boolean valid = false;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call CheckEngineerExists(?,?)}");
			cstmt.setString(1, engineerName);
			cstmt.setString(2, engineerType);
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					if (rs.getString(1).equals("Y")) {
						valid = true;
					}
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
		return valid;
	}
	
	private boolean validFeCompany(String feCompany) {
		boolean valid = false;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call getThirdPartyIdFromName(?)}");
			cstmt.setString(1, feCompany);
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					if (rs.getLong(1)!=-1) {
						valid = true;
					}
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
		return valid;
	}
	
}
