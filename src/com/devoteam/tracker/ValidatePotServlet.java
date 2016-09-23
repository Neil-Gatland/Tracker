package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
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

import com.devoteam.tracker.model.Pot;
import com.devoteam.tracker.model.PotSpreadsheet;
import com.devoteam.tracker.model.PotSpreadsheetSNR;
import com.devoteam.tracker.model.SNR;
import com.devoteam.tracker.model.UserRole;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.StringUtil;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ValidatePotServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 453505036269787268L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private StringBuilder problems;
	private String url;
	private Connection conn;
	private CallableStatement cstmt;

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
			String potFileName = req.getParameter("potFileName");
			String customerName = null;
	    	session.removeAttribute(ServletConstants.POT_SPREADSHEET_NAME_IN_SESSION);
	    	session.removeAttribute(ServletConstants.POT_SPREADSHEET_COPY_NAME_IN_SESSION);
	    	session.removeAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION);
	        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
	        BlobKey blobKey = blobs.get("potXLS");
	        BlobstoreInputStream in = new BlobstoreInputStream(blobKey); 
			problems = new StringBuilder();
	        
	        try {
				String selectCustomerId = null;
				long customerId = -1;
				//String customerName = null;
				if  (req.getParameter("selectCustomerId") == null) {
					throw new Exception("Customer has not been selected on screen"); 
				} else {
					selectCustomerId = req.getParameter("selectCustomerId");
					String[] cS = selectCustomerId.split(Pattern.quote("|"));
					customerName = cS[0];
					customerId = Long.parseLong(cS[1]);
				}	
				Workbook workbook = WorkbookFactory.create(in);
				if (workbook.getNumberOfSheets() != 2) {
					throw new Exception("Workbook must have 2 sheets");
				}
				Sheet headerSheet = workbook.getSheetAt(0);
				if ((headerSheet.getFirstRowNum() != 0) || (headerSheet.getLastRowNum() != 1)) {
					throw new Exception("First sheet must have only rows 1 and 2"); 
				}
				Sheet snrSheet = workbook.getSheetAt(1);
				if (snrSheet.getFirstRowNum() != 0) {
					throw new Exception("Second sheet must start at row 1"); 
				}
				Row titleRowH = headerSheet.getRow(0);
				String[] headerDataTitles = PotSpreadsheet.getHeaderDataTitles();
				//check number of columns
	            if (titleRowH.getLastCellNum() != headerDataTitles.length) {
					throw new Exception("First sheet must have " + 
						headerDataTitles.length + " columns");
	            }
	            Row titleRowS = snrSheet.getRow(0);
				String[] snrDataTitles = PotSpreadsheetSNR.getSNRDataTitles();
	            if (titleRowS.getLastCellNum() != snrDataTitles.length) {
					throw new Exception("Second sheet must have " + 
						snrDataTitles.length + " columns");
	            }
				//check titles
	            int i = 0;
				for (Iterator<Cell> cIt = titleRowH.cellIterator(); cIt.hasNext(); ) {
					Cell c = cIt.next();
					String cV = c.getStringCellValue().trim().replaceAll(" +", " ");
					if (!headerDataTitles[i].equalsIgnoreCase(cV)) {
						problems.append("Sheet 1, column " + String.valueOf(i+1) + ": found: " +
							cV + ", expected: " + headerDataTitles[i] + "|");
					}
					i++;
				}
	            i = 0;
				for (Iterator<Cell> cIt = titleRowS.cellIterator(); cIt.hasNext(); ) {
					Cell c = cIt.next();
					String cV = c.getStringCellValue().trim().replaceAll(" +", " ");
					if (!snrDataTitles[i].equalsIgnoreCase(cV)) {
						problems.append("Sheet 2, column " + String.valueOf(i+1) + ": found: " +
							cV + ", expected: " + snrDataTitles[i] + "|");
					}
					i++;
				}
				if (problems.length() > 0) {
					//session.setAttribute("customerName", customerName);
					//session.setAttribute("potFileName", potFileName);
					throw new Exception("Invalid Spreadsheet"); 
				}
				//check trailer
	            Row lastRowS = snrSheet.getRow(snrSheet.getLastRowNum());
				String[] snrTrailerValues = PotSpreadsheetSNR.getSNRTrailerValues();
	            i = 0;
				for (Iterator<Cell> cIt = lastRowS.cellIterator(); cIt.hasNext(); ) {
					Cell c = cIt.next();
					String cV = c.getCellType()==Cell.CELL_TYPE_NUMERIC?
							Long.toString((long)c.getNumericCellValue()):
							c.getStringCellValue().trim().replaceAll(" +", " ");
					if (i < snrTrailerValues.length) { 		
						if (!snrTrailerValues[i].equalsIgnoreCase(cV)) {
							throw new Exception("Second sheet trailer row format is incorrect"); 
						}
					} else if (!cV.isEmpty()) {
						throw new Exception("Second sheet trailer row format is incorrect - " +
								"non empty cells after expected trailer values"); 
					}
					i++;
				}
				//check customers match
				Row dataRowH = headerSheet.getRow(1);
				String sheetCustomer = dataRowH.getCell(0).getStringCellValue().trim().replaceAll(" +", " ");
				if (!sheetCustomer.equalsIgnoreCase(customerName)) {
					throw new Exception("Spreadsheet customer (" + sheetCustomer +
						") does not match screen customer (" + customerName + ")"); 
				}
				//check for existing pot
				url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				String sheetJobType = dataRowH.getCell(1).getStringCellValue().trim().replaceAll(" +", " ");
				String sheetProjectRequestor = dataRowH.getCell(2).getStringCellValue().trim().replaceAll(" +", " ");
				String sheetPotName = dataRowH.getCell(3).getStringCellValue().trim().replaceAll(" +", " ");
				Date sheetPotDate = null;
				conn = null;
				cstmt = null;
				//Pot dbPot = null;
				boolean potExists = false;
				try {
					conn = DriverManager.getConnection(url);
					cstmt = conn.prepareCall("{call GetPotFromName(?)}");
					cstmt.setString(1, sheetPotName);
					if (cstmt.execute()) {
						ResultSet rs = cstmt.getResultSet();
						/*if (rs.next()) {
							dbPot = new Pot(rs.getLong(1), rs.getString(2), rs.getLong(3), 
									rs.getString(4), rs.getString(5), rs.getString(6));
						}*/
						potExists = rs.next();
					}
				} catch (SQLException e) {
					throw new Exception("Unable to validate pot against database, " + e.getMessage());
				} finally {
					cstmt.close();
					conn.close();
				}
				boolean updateProjectRequestor = false; 
				//if (dbPot != null) {
				if (potExists) {
					throw new Exception("Pot " + sheetPotName +
							" is in the database already and cannot be reloaded"); 
	
					/*if (!sheetCustomer.equalsIgnoreCase(dbPot.getCustomerName())) {
						throw new Exception("Spreadsheet customer (" + sheetCustomer +
							") does not match database customer (" + dbPot.getCustomerName() + 
							") for this pot"); 
					}
					if (!sheetJobType.equalsIgnoreCase(dbPot.getJobType())) {
						throw new Exception("Spreadsheet job type (" + sheetJobType +
							") does not match database job type (" + dbPot.getJobType() + 
							") for this pot"); 
					}
					problems.append("Existing pot, any existing associated SNRs will only have EF dates updated from spreadsheet|");
					if (!sheetProjectRequestor.equalsIgnoreCase(dbPot.getProjectRequestor())) {
						updateProjectRequestor = true; 
						problems.append("Spreadsheet project requestor (" + sheetProjectRequestor +
							") does not match database project requestor (" + dbPot.getProjectRequestor() + 
							") for this job type - database value will be updated|");
					}
					customerId = dbPot.getCustomerId();*/
				} else {
					//validate job type for new pot
					try {
						conn = DriverManager.getConnection(url);
						cstmt = conn.prepareCall("{call GetJobType(?)}");
						cstmt.setString(1, sheetJobType);
						if (cstmt.execute()) {
							ResultSet rs = cstmt.getResultSet();
							if (rs.next()) {
								String dbProjectRequestor = rs.getString(2);
								if (!sheetProjectRequestor.equalsIgnoreCase(dbProjectRequestor)) {
									updateProjectRequestor = true; 
									problems.append("Spreadsheet project requestor (" + sheetProjectRequestor +
										") does not match database project requestor (" + dbProjectRequestor + 
										") for this job type - database value will be updated|");
								}
							} else {
								throw new Exception("Spreadsheet job type (" + sheetJobType +
										") does not match any existing database job type"); 
							}
						}
					} catch (SQLException e) {
						throw new Exception("Unable to validate pot against database, " + e.getMessage());
					} finally {
						cstmt.close();
						conn.close();
					}
				}
	
				try {
					sheetPotDate = new Date(dataRowH.getCell(4).getDateCellValue().getTime());
				} catch (Exception ex) {
					throw new Exception("Invalid pot date in spreadsheet, " + ex.getMessage()); 
				}
				//if we've got this far the first sheet must be OK, so create the pot 
				PotSpreadsheet sheetPot = new PotSpreadsheet(-1,
						customerId, customerName, sheetJobType,	sheetProjectRequestor, 
						sheetPotName, sheetPotDate, potFileName, false,
						updateProjectRequestor);
				/*PotSpreadsheet sheetPot = new PotSpreadsheet(dbPot==null?-1:dbPot.getPotId(),
						customerId, customerName, sheetJobType,	sheetProjectRequestor, 
						sheetPotName, sheetPotDate, potFileName, dbPot!=null,
						updateProjectRequestor);
				if (dbPot != null) {
					//get existing snrs for validation
					try {
						conn = DriverManager.getConnection(url);
						cstmt = conn.prepareCall("{call GetSNRsForPot(?)}");
						cstmt.setLong(1, dbPot.getPotId());
						if (cstmt.execute()) {
							ResultSet rs = cstmt.getResultSet();
							while (rs.next()) {
								dbPot.addSNRDataRow(new SNR(rs.getLong(1), 
										rs.getString(2), rs.getString(3)));
							}
						}
					} catch (SQLException e) {
						throw new Exception("Unable to validate SNRs against database, " + e.getMessage());
					} finally {
						cstmt.close();
						conn.close();
					}
				}*/
				//ArrayList<PotSpreadsheetSNR> sheetSNRs = new ArrayList<PotSpreadsheetSNR>();
				for (int j = 1; j < snrSheet.getLastRowNum(); j++) {
					PotSpreadsheetSNR sheetSNR = null;
		            Row thisRow = snrSheet.getRow(j);
		            String thisSiteId = validatePossibleNumericString(thisRow.getCell(0), 
		            		"Site", j);
		            String thisNRId = validateNRId(thisRow.getCell(1), j,
		            		thisSiteId);
		            /*Date thisEF345ClaimDate = validateExtractDateCell(thisRow.getCell(7), 
				            "EF345 Claim Date", j);
		            Date thisEF360ClaimDate = validateExtractDateCell(thisRow.getCell(8), 
				            "EF360 Claim Date", j);
		            Date thisEF400ClaimDate = validateExtractDateCell(thisRow.getCell(9), 
				            "EF400 Claim Date", j);
		            Date thisEF410ClaimDate = validateExtractDateCell(thisRow.getCell(10), 
				            "EF410 Claim Date", j);
		            boolean comboExists = false;
		            long existingSNRId = -1;
					if (dbPot != null) { //only do this validation if pot exists already
						//check if this particular site/nrId combination exists
						for (Iterator<SNR> it = dbPot.getSNRDataRows().iterator(); it.hasNext(); ) {
							SNR thisSNR = it.next();
							if ((thisSiteId.equals(thisSNR.getSite())) &&
									(thisNRId.equals(thisSNR.getNRId()))) {
								comboExists = true;
								existingSNRId = thisSNR.getSNRId();
								break;
							}
						}
						if (!comboExists) {
			            	problems.append("NR Id in row " + String.valueOf(j+1) +
				            		" is not present in the existing pot. It will be added|");
						}
					}*/
					//check if site exists
					boolean siteExists = false;
					try {
						conn = DriverManager.getConnection(url);
						cstmt = conn.prepareCall("{call GetSiteDetails(?)}");
						cstmt.setString(1, thisSiteId);
						if (cstmt.execute()) {
							ResultSet rs = cstmt.getResultSet();
							siteExists = rs.next();
						}
					} catch (SQLException e) {
						throw new Exception("Unable to check site against database, " + e.getMessage());
					} finally {
						cstmt.close();
						conn.close();
					}
					if (siteExists) {
						// check if site/nrId combination exists
						try {
							conn = DriverManager.getConnection(url);
							cstmt = conn.prepareCall("{call GetSNRFromSiteAndNRId(?,?)}");
							cstmt.setString(1, thisSiteId);
							cstmt.setString(2, thisNRId);
							if (cstmt.execute()) {
								ResultSet rs = cstmt.getResultSet();
								if (rs.next()) {
									if (rs.getLong(1) != -1) {
										boolean cancelled = 
												rs.getString(2).equalsIgnoreCase(ServletConstants.STATUS_CANCELLED);
										throw new Exception("Site/NR Id combination " + thisSiteId + "/" +
												thisNRId + " exists already" + 
												(cancelled?(". It has a status of '" + ServletConstants.STATUS_CANCELLED +
												" and will need to be reopened."):""));
									}
								}
							}
						} catch (SQLException e) {
							throw new Exception("calling GetSNRFromSiteAndNRId(), " + e.getMessage());
						} finally {
							cstmt.close();
							conn.close();
						}

						//if ((dbPot == null) || (!comboExists)) { 
							//only do this validation if pot or site/nrId combo not found
				            String thisUpgradeType = validateExtractStringCell(thisRow.getCell(2), 
				            		"Upgrade Type", j);
				            /*String thisEastings = validateNumericString(thisRow.getCell(3), 
					            	"Eastings", j);
				            String thisNorthings = validateNumericString(thisRow.getCell(4), 
					            	"Northings", j);*/
				            String thisPostcode = validateExtractStringCell(thisRow.getCell(3), 
				            		"Postcode", j);
				            if (!validatePostcode(thisPostcode)) {
				            	problems.append("Postcode in row " + String.valueOf(j+1) +
				            		" appears to be invalid|");
				            }
				            //String thisRegion = validateExtractStringCell(thisRow.getCell(6), 
				            	//	"Region", j);
				            String thisVodafone2G = validateExtractYNCell(thisRow.getCell(4), 
				            		"Vodafone 2G", j);
				            String thisVodafone3G = validateExtractYNCell(thisRow.getCell(5), 
				            		"Vodafone 3G", j);
				            String thisVodafone4G = validateExtractYNCell(thisRow.getCell(6), 
				            		"Vodafone 4G", j);
				            String thisTEF2G = validateExtractYNCell(thisRow.getCell(7), 
				            		"TEF 2G", j);
				            String thisTEF3G = validateExtractYNCell(thisRow.getCell(8), 
				            		"TEF 3G", j);
				            String thisTEF4G = validateExtractYNCell(thisRow.getCell(9), 
				            		"TEF 4G", j);
				            String thisPaknetPaging = validateExtractYNCell(thisRow.getCell(10), 
				            		"Paknet and Paging", j);
				            String thisSecGWChanges = validateExtractYNCell(thisRow.getCell(11), 
				            		"SecGW Changes", j);
				        	String power = validateExtractYNCell(thisRow.getCell(12), 
				            		"Power", j);
				        	String survey = validateExtractYNCell(thisRow.getCell(13), 
				            		"Survey", j);
				            String hardwareVendor = validateExtractStringCell(thisRow.getCell(14), 
				            		"Hardware Vendor", j);
		
				            sheetSNR = new PotSpreadsheetSNR(thisSiteId, thisNRId, thisUpgradeType,
				            		thisPostcode, thisVodafone2G, thisVodafone3G, 
				            		thisVodafone4G, thisTEF2G, thisTEF3G, thisTEF4G, 
				            		thisPaknetPaging, thisSecGWChanges, power, survey, hardwareVendor);
		
				            /*sheetSNR = new PotSpreadsheetSNR(thisSiteId, thisNRId, thisUpgradeType,
				            		thisPostcode, thisVodafone2G, thisVodafone3G, 
				            		thisVodafone4G, thisTEF2G, thisTEF3G, thisTEF4G, 
				            		thisPacknetPaging, thisSecGWChanges, power, survey);*/
						/*} else {
				            sheetSNR = new PotSpreadsheetSNR(existingSNRId, thisSiteId,
				            		thisNRId, thisEF345ClaimDate, thisEF360ClaimDate, 
				            		thisEF400ClaimDate, thisEF410ClaimDate);
						}*/
						sheetPot.addSNRDataRow(sheetSNR);
					} else {
						throw new Exception("Site " + thisSiteId + " not found on the database - " +
			            		"row " + String.valueOf(j+1));
					}
				}
				/*if (dbPot != null) {
					//flag any existing nrIds not on the sheet
					for (Iterator<SNR> it = dbPot.getSNRDataRows().iterator(); it.hasNext(); ) {
						boolean comboExists = false;
						SNR thisSNR = it.next();
						for (Iterator<PotSpreadsheetSNR> it2 = sheetPot.getSNRDataRows().iterator(); it2.hasNext(); ) {
							PotSpreadsheetSNR psSNR = it2.next();
							if ((psSNR.getSite().equals(thisSNR.getSite())) &&
									(psSNR.getNRId().equals(thisSNR.getNRId()))) {
								comboExists = true;
								break;
							}
						}
						if (!comboExists) {
			            	problems.append("Site/NR Id " + thisSNR.getSite() + "/" +
			            		thisSNR.getNRId() + " found on database but not in spreadsheet|");
						}
					}
				}*/
		        session.setAttribute(ServletConstants.POT_SPREADSHEET_NAME_IN_SESSION, sheetPot);
	
			} catch (Exception ex) {
				session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
						"Error: " + ex.getMessage());
			} finally {
				session.setAttribute("customerName", customerName);
				session.setAttribute("potFileName", potFileName);
		        if (problems.length() > 0) {
					session.setAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION, problems.toString());
				}
		        blobs.remove("potXLS");
		    }
	    }
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		resp.sendRedirect(destination+ran);
    }
    
    private void checkMissing(String thisSiteId) throws Exception {
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetOpenDummyNRIdsForSite(?)}");
			cstmt.setString(1, thisSiteId);
			if (cstmt.execute()) {
				StringBuilder sb = new StringBuilder();
				ResultSet rs = cstmt.getResultSet();
				while(rs.next()) {
					sb.append(rs.getString(1));
					sb.append(", ");
				}
				if (sb.length() > 0) {
					sb.setLength(sb.length()-2);
					problems.append("Site " + thisSiteId + " with missing NR Id has open dummy NRs already: " +
							sb.toString() + "|");
				}
			} else {
				throw new SQLException("cstmt.execute() is false");
			}
		} catch (SQLException e) {
			throw new Exception("Unable to check missing NR Id against database, " + e.getMessage());
		} finally {
			cstmt.close();
			conn.close();
		}
   	
    }

    private String validateNRId(Cell thisCell, int row, String thisSiteId) throws Exception {
    	String thisNRId = validateExtractStringCell(thisCell, "NR Id", row);
    	if (StringUtil.hasNoValue(thisNRId)) {
        	throw new Exception("NR id in row " + 
            		Integer.toString(row+1) + " has no value. If it is not yet known, " +
        			"enter " + PotSpreadsheetSNR.MISSING);
    	} else if (thisNRId.toUpperCase().equals(PotSpreadsheetSNR.MISSING)) {
    		checkMissing(thisSiteId);
    	} else {	
	    	if (thisNRId.toUpperCase().startsWith(PotSpreadsheetSNR.INC)) {
	        	try {
	            	Long.parseLong(thisNRId.substring(PotSpreadsheetSNR.INC.length()));
	        	} catch (NumberFormatException nfe ) {
	            	throw new Exception("NR id (" + thisNRId + ") in row " + 
	                		Integer.toString(row+1) + " is not correctly " +
	            			"formatted as an incident number");
	        	}
	    	} else {
		        if (!thisNRId.startsWith(thisSiteId)) {
		        	throw new Exception("NR id (" + thisNRId + ") in row " + 
		        		Integer.toString(row+1) + " is not an incident number " +
		        		"and does not start with site id (" + 
		        		thisSiteId +") from that row");
		        } else if ((thisNRId.length() <= (thisSiteId.length()+1)) ||
		        	(thisNRId.charAt(thisSiteId.length()) != '-')) {
		        	throw new Exception("Format of NR id (" + thisNRId + ") in row " + 
		            		Integer.toString(row+1) + " is not correct. It should " +
		            		"be an incident number or " +
		        			"consist of site id and job no, separated by a dash");
		        } else {
		        	try {
		            	Long.parseLong(thisNRId.substring(thisSiteId.length()+1));
		        	} catch (NumberFormatException nfe ) {
		            	throw new Exception("Job no of NR id (" + thisNRId + ") in row " + 
		                		Integer.toString(row+1) + " is not numeric");
		        	}
		        }
	        }
    	}
        return thisNRId; 
    }

    private Date validateExtractDateCell(Cell thisCell, String columnName, int row) throws Exception {
		try {
			java.util.Date d = null;
    		if (thisCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				d = thisCell.getDateCellValue();
    		} else {
        		String strValue = thisCell.getStringCellValue().trim();
        		if (strValue.length() == 0) {
        			d = null;
        		} else {
	        		String[] dateParts = strValue.split(Pattern.quote("/"));
	        		GregorianCalendar gC = new GregorianCalendar(Integer.parseInt(dateParts[2]),
	        			Integer.parseInt(dateParts[1])-1, Integer.parseInt(dateParts[0]));
	        		d = new Date(gC.getTimeInMillis());
        		}
    		}
    		return d==null?null:new Date(d.getTime());
		} catch (Exception ex) {
        	throw new Exception("Invalid value for " + columnName + " in row " + 
        			Integer.toString(row+1) + ", " + ex.getMessage());
        }
    }

    private String validateExtractStringCell(Cell thisCell, String columnName, int row) throws Exception {
    	try {
    		return thisCell==null?null:thisCell.getStringCellValue().trim();
        } catch (Exception ex) {
        	throw new Exception("Invalid value for " + columnName + " in row " + 
        			Integer.toString(row+1) + ", " + ex.getMessage());
        }
    }

    private String validateExtractYNCell(Cell thisCell, String columnName, int row) throws Exception {
    	try {
    		String strValue = thisCell.getStringCellValue().trim();
    		if ((!strValue.equalsIgnoreCase("Y")) && (!strValue.equalsIgnoreCase("N"))) {
    			throw new Exception(" only 'Y' or 'N' allowed"); 
    		}
    		return strValue;
        } catch (Exception ex) {
        	throw new Exception("Invalid value for " + columnName + " in row " + 
        			Integer.toString(row+1) + ", " + ex.getMessage());
        }
    }

    private String validatePossibleNumericString(Cell thisCell, String columnName, int row) throws Exception {
    	try {
    		String s = null;
    		//extract as string but make sure it's numeric
    		if (thisCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
    			s = Long.toString((long)thisCell.getNumericCellValue());
    		} else {
    			s = thisCell==null?null:thisCell.getStringCellValue().trim();
    			//Long.parseLong(s);
    		}
    		return s;
        } catch (Exception ex) {
        	throw new Exception("Invalid value for " + columnName + " in row " + 
        			Integer.toString(row+1) + ", " + ex.getMessage());
        }
    }

    private long validateExtractLongCell(Cell thisCell, String columnName, int row) throws Exception {
    	try {
    		return Long.parseLong(thisCell.getStringCellValue().trim());
        } catch (Exception ex) {
        	throw new Exception("Invalid value for " + columnName + " in row " + 
        			Integer.toString(row+1) + ", " + ex.getMessage());
        }
    }

    private boolean validatePostcode(String code) {
    	String regexp="^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)$";
    	Pattern pattern = Pattern.compile(regexp);
    	Matcher matcher = pattern.matcher(code.toUpperCase());
    	return matcher.matches();
    }   
}
