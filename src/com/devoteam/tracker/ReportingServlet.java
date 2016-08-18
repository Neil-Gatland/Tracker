package com.devoteam.tracker;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.devoteam.tracker.model.ReportParameter;
import com.devoteam.tracker.model.SNRProgressReportItem;
import com.devoteam.tracker.model.SNRTotalsReportItem;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.StringUtil;
import com.google.appengine.api.images.Image.Format;

public class ReportingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3763863523605809828L;
	private final String[] filters = {"filterCustomer", "filterPotName", 
			"filterSite", "filterStatus"};
	private Map<String, String> filterValues = new HashMap<String, String>();
	private String destination = "/reporting.jsp";
	private final SimpleDateFormat tsFormatter = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	private Workbook wb;
	private Sheet sheet;
	private Font f, f2, f3;
	private CellStyle cs1, cs2, cs3, cs4, cs5, cs5l, cs5m, cs5r, cs6, cs7, cs8, cs9, cs10, 
	cs11, cs12, cs13, cs13f, cs14, cs14f, cs15, cs15f, cs16, cs16f, cs17, cs18, csSel, csSelF;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid user id and password");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
		  	dispatcher.forward(req,resp);
		} else {
			String buttonPressed = req.getParameter("buttonPressed");
			String selectedReport = req.getParameter("filterReport");
			String whichFilter = req.getParameter("whichFilter");
			req.setAttribute("selectedReport", selectedReport);
			if (selectedReport != null) {
				String reportScreen = null;
				if (selectedReport.endsWith("|Y")) {
					reportScreen = "view" + 
						selectedReport.substring(0, selectedReport.lastIndexOf("|")).replaceAll("\\s+","") + 
						".jsp";
				} else {
					reportScreen = "downloadReport.jsp";
					req.setAttribute("reportName", selectedReport.substring(0, selectedReport.lastIndexOf("|")));
				}
				req.setAttribute("reportScreen", reportScreen);
			}
			if (buttonPressed.equals("submitDownloadReport")) {
				downloadReport(req, resp, ran);
			} else if (buttonPressed.startsWith("download")) {
				//req.s
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String formattedDate = sdf.format(date);
		        OutputStream oS = resp.getOutputStream();
				resp.setContentType("application/vnd.ms-excel");
		        // forces download 
		        String headerKey = "Content-Disposition";
				if (buttonPressed.equals("downloadProgress")) {
					Collection<SNRProgressReportItem> prList = (Collection<SNRProgressReportItem>)
							session.getAttribute(ServletConstants.SNR_PROGRESS_REPORT_ITEMS_IN_SESSION);
			        String headerValue = String.format("attachment; filename=\"%s\"", "NRProgress_" + formattedDate + ".csv");
			        resp.setHeader(headerKey, headerValue);
			        // obtains response's output stream
			        StringBuilder sB = new StringBuilder();
			        String[] columnTitles = SNRProgressReportItem.getColumnTitles();
			        for (int i = 0; i < columnTitles.length; i++) {
			        	sB.append(columnTitles[i]);
			        	sB.append(",");
			        }
			        //sB.setLength(sB.length()-1);
			        sB.setCharAt(sB.length()-1, '\n');
			        oS.write(sB.toString().getBytes());
			        for (Iterator<SNRProgressReportItem> it = prList.iterator(); it.hasNext(); ) {
			        	SNRProgressReportItem spri = it.next();
			        	sB = new StringBuilder();
			        	String[] downloadValues = spri.getDownloadValueArray();
				        for (int i = 0; i < downloadValues.length; i++) {
				        	sB.append(downloadValues[i]);
				        	sB.append(",");
				        }
				        //sB.setLength(sB.length()-1);
				        sB.setCharAt(sB.length()-1, '\n');
				        oS.write(sB.toString().getBytes());
			        }
				} else if (buttonPressed.equals("downloadTotals")) {
					Collection<SNRTotalsReportItem> trList = (Collection<SNRTotalsReportItem>)
							session.getAttribute(ServletConstants.SNR_TOTALS_REPORT_ITEMS_IN_SESSION);
			        String headerValue = String.format("attachment; filename=\"%s\"", "NRTotals_" + formattedDate + ".csv");
			        resp.setHeader(headerKey, headerValue);
			        // obtains response's output stream
			        StringBuilder sB = new StringBuilder();
			        String[] columnTitles = SNRTotalsReportItem.getColumnTitles();
			        for (int i = 0; i < columnTitles.length; i++) {
			        	sB.append(columnTitles[i]);
			        	sB.append(",");
			        }
			        //sB.setLength(sB.length()-1);
			        sB.setCharAt(sB.length()-1, '\n');
			        oS.write(sB.toString().getBytes());
			        for (Iterator<SNRTotalsReportItem> it = trList.iterator(); it.hasNext(); ) {
			        	SNRTotalsReportItem stri = it.next();
			        	sB = new StringBuilder();
			        	String[] downloadValues = stri.getValueArray();
				        for (int i = 0; i < downloadValues.length; i++) {
				        	sB.append(downloadValues[i]);
				        	sB.append(",");
				        }
				        //sB.setLength(sB.length()-1);
				        sB.setCharAt(sB.length()-1, '\n');
				        oS.write(sB.toString().getBytes());
			        }
				}
				oS.flush();
				oS.close();
			} else { 
				if (buttonPressed.equals("clearAll")) {
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
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
			  	dispatcher.forward(req,resp);
			}
		}
	}

	private void downloadReport(HttpServletRequest req, HttpServletResponse resp, String ran) throws IOException, ServletException {
		HttpSession session = req.getSession(false);
    	String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
    	String reportName = req.getParameter("reportName");
		Collection<ReportParameter> rpList = (Collection<ReportParameter>) 
				session.getAttribute(ServletConstants.DOWNLOAD_REPORT_PARAMETERS_IN_SESSION);
    	if ( reportName.startsWith("Weekly Breakdown")) {
    		// Weekly Breakdown has specialised Excel layout which cannot be automated like the other CSV reports
    		try {
        		// Get project name and year week
        		String projectName = "", yearWeek = "";
        		for (Iterator<ReportParameter> it = rpList.iterator(); it.hasNext(); ) {
					ReportParameter rp = it.next();
					String pName = rp.getParameterNameCondensed();
					String pValue = req.getParameter("filterReport" + rp.getParameterNameCondensed());
					if (pName.equals("ProjectName"))
						projectName = pValue;
					if (pName.equals("YearWeek"))
						yearWeek = pValue;
				}
        		// check project name has been selected
        		if (projectName.isEmpty()) {
        			String msg = "No project name selected";
        			throw new Exception(msg);
        		} else {
        			// set up sheet
        			wb = new XSSFWorkbook();
        			wb.setForceFormulaRecalculation(true);
        			String sheetName = "W" + yearWeek.substring(4,6) + "-" + yearWeek.substring(2, 4);
        			// get NR count data for project and week
    				Connection conn = null;
        			CallableStatement cstmt = null;
        			StringBuilder sql = new StringBuilder("{call GetDailyNRCount(?,?)}");
        			try {        				
            			conn = DriverManager.getConnection(url);
        				cstmt = conn.prepareCall(sql.toString());
            			cstmt.setString(1, projectName);
            			cstmt.setString(2, yearWeek);
            			if (cstmt.execute()) {
        					ResultSet rs = cstmt.getResultSet();
        					ResultSetMetaData rsmd = rs.getMetaData();
        					int cols = rsmd.getColumnCount() + 1;
        					if (cols > 0) {
        						if (rsmd.getColumnName(1).startsWith("Error")) {
        							// No data for the selected project and week
        							String msg = "";
        							if (rs.next()) {
        								msg = msg + rs.getString(1);
        							}
        							throw new Exception(msg);
        						} else {
        							// start building the sheet
        		        	        sheet = wb.createSheet(sheetName);        		        	        
        		        	        // set up fonts and cell formatting styles
        							initialiseCellStyles();
        							// set up initial title bar
        							setUpTitleBar();
        							String week = yearWeek.substring(4, 6);
        							String shortYear = yearWeek.substring(2,4);
        							if (week.substring(0, 1).equals("0"))
        								week = yearWeek.substring(5, 6);
        							// set up counts box with zero amounts
        							setUpEmptyCountsBox(week);
        							// overwrite counts box with actual amounts 
    								while (rs.next()) {
    									int currentRow = rs.getInt(10) + 7;
    									int scheduled = rs.getInt(4);
    									int attempted = rs.getInt(5);
    									int completed = rs.getInt(6);
    									int partial = rs.getInt(7);
    									int customer = rs.getInt(8);
    									int devoteam = rs.getInt(9);
    									Cell updateCell = sheet.getRow(currentRow).getCell(2);
    									updateCell.setCellValue(scheduled);
    									updateCell = sheet.getRow(currentRow).getCell(3);
    									updateCell.setCellValue(attempted);
    									updateCell = sheet.getRow(currentRow).getCell(4);
    									updateCell.setCellValue(completed);
    									updateCell = sheet.getRow(currentRow).getCell(5);
    									updateCell.setCellValue(partial);
    									updateCell = sheet.getRow(currentRow).getCell(6);
    									updateCell.setCellValue(customer);
    									updateCell = sheet.getRow(currentRow).getCell(7);
    									updateCell.setCellValue(devoteam);
    								}
    								// set up success percenatges bar
    								setUpSuccessRate();
    								// build detailed full breakdown
    								String dayName = "";
    								int currentRow = 14, firstRow = 18, lastRow = 18;
    								sql = new StringBuilder("{call GetDailyNRBreakdown(?,?)}");
    								cstmt = conn.prepareCall(sql.toString());
    		            			cstmt.setString(1, projectName);
    		            			cstmt.setString(2, yearWeek);
    		            			if (cstmt.execute()) {
    		            				rs= cstmt.getResultSet();
    		            				while (rs.next()) {
    		            					String currentDay = rs.getString(3);
    		            					if (!currentDay.equals(dayName)) {
    		            						// full breakdown header lines
    		            						currentRow++;
    		            						sheet.createRow(currentRow);
    		            						Row row = sheet.getRow(currentRow);
    		            						row.setHeight((short)460);
    		            						sheet.createRow(currentRow+1);
    		            						row = sheet.getRow(currentRow+1);
    		            						row.setHeight((short)460);
    		            						Cell cell = sheet.getRow(currentRow).createCell(9);
    		            						dayName = currentDay;
    		            						cell.setCellValue(dayName);
    		            						cell.setCellStyle(cs8);
    		            						cell = sheet.getRow(currentRow+1).createCell(9);
    		            						cell.setCellValue("Site Id");
    		            						cell.setCellStyle(cs4);   		            						
    		            						cell = sheet.getRow(currentRow).createCell(10);
    		            						cell.setCellStyle(cs8);
    		            						cell = sheet.getRow(currentRow+1).createCell(10);
    		            						cell.setCellValue("NR Id");
    		            						cell.setCellStyle(cs4);    		            						
    		            						cell = sheet.getRow(currentRow).createCell(11);
    		            						cell.setCellStyle(cs8);
    		            						cell = sheet.getRow(currentRow+1).createCell(11);
    		            						cell.setCellValue("Project");
    		            						cell.setCellStyle(cs4);   		            						
    		            						cell = sheet.getRow(currentRow).createCell(12);
    		            						cell.setCellStyle(cs8);
    		            						cell = sheet.getRow(currentRow+1).createCell(12);
    		            						cell.setCellValue("Job Type");
    		            						cell.setCellStyle(cs4);    		            						
    		            						cell = sheet.getRow(currentRow).createCell(13);
    		            						cell.setCellStyle(cs8);
    		            						cell = sheet.getRow(currentRow+1).createCell(13);
    		            						cell.setCellValue("Upgrade Type");
    		            						cell.setCellStyle(cs4);
    		            						cell = sheet.getRow(currentRow).createCell(14);
    		            						cell.setCellStyle(cs8);
    		            						cell = sheet.getRow(currentRow+1).createCell(14);
    		            						cell.setCellValue("Technologies");
    		            						cell.setCellStyle(cs4);
    		            						cell = sheet.getRow(currentRow).createCell(15);
    		            						cell.setCellStyle(cs8);
    		            						cell.setCellValue(rs.getString(6));
    		            						cell = sheet.getRow(currentRow+1).createCell(15);
    		            						cell.setCellValue("Status");
    		            						cell.setCellStyle(cs4);
    		            						cell = sheet.getRow(currentRow).createCell(16);
    		            						cell.setCellStyle(cs8);
    		            						cell = sheet.getRow(currentRow+1).createCell(16);
    		            						cell.setCellValue("Reason");
    		            						cell.setCellStyle(cs4);
    		            						cell = sheet.getRow(currentRow).createCell(17);
    		            						cell.setCellValue("Work Required");
    		            						cell.setCellStyle(cs4);
    		            						sheet.addMergedRegion(new CellRangeAddress(currentRow,currentRow,17,18));
    		            						cell = sheet.getRow(currentRow+1).createCell(17);
    		            						cell.setCellValue("Responsible");
    		            						cell.setCellStyle(cs4);
    		            						cell = sheet.getRow(currentRow+1).createCell(18);
    		            						cell.setCellValue("Action");
    		            						cell.setCellStyle(cs4);
    		            						cell = sheet.getRow(currentRow).createCell(19);
    		            						cell.setCellValue("Comments");
    		            						cell.setCellStyle(cs4);
    		            						sheet.addMergedRegion(new CellRangeAddress(currentRow,currentRow+1,19,19));
   		            							cell = sheet.getRow(currentRow).createCell(21);
    		            						cell.setCellValue("HoP");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,21,21);
    		            						cell = sheet.getRow(currentRow).createCell(22);
    		            						cell.setCellValue("EF");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,22,22);
    		            						cell = sheet.getRow(currentRow).createCell(23);
    		            						cell.setCellValue("SP");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,23,23);
    		            						cell = sheet.getRow(currentRow).createCell(24);
    		            						cell.setCellValue("SFR");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,24,24);
    		            						cell = sheet.getRow(currentRow).createCell(25);
    		            						cell.setCellValue("Success");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,25,25);
    		            						cell = sheet.getRow(currentRow).createCell(26);
    		            						cell.setCellValue("Abort");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,26,26);
    		            						cell = sheet.getRow(currentRow).createCell(27);
    		            						cell.setCellValue("OOH");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,27,27);
    		            						cell = sheet.getRow(currentRow).createCell(28);
    		            						cell.setCellValue("2nd Man");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,28,28);
    		            						cell = sheet.getRow(currentRow).createCell(29);
    		            						cell.setCellValue("Access");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,29,29);
    		            						cell = sheet.getRow(currentRow).createCell(30);
    		            						cell.setCellValue("Total Cost");
    		            						cell.setCellStyle(cs17);
    		            						borderCellRange(currentRow,currentRow+1,30,30);
    		            						cell = sheet.getRow(currentRow).createCell(32);
    		            						cell.setCellStyle(cs1);
    		            						sheet.addMergedRegion(new CellRangeAddress(currentRow,currentRow,32,33));
    		            						cell = sheet.getRow(currentRow+1).createCell(32);
    		            						cell.setCellValue("Lock");
    		            						cell.setCellStyle(cs4);
    		            						cell = sheet.getRow(currentRow+1).createCell(33);
    		            						cell.setCellValue("Un Lock");
    		            						cell.setCellStyle(cs4);
    		            						currentRow++;
    		            						currentRow++;
    		            					}
    		            					//full breakdown detail line
    		            					sheet.createRow(currentRow);
		            						Cell cell = sheet.getRow(currentRow).createCell(9);
		            						dayName = currentDay;
		            						// set relevant cell style for status of NR
		            						String implStatus = rs.getString(4);
		            						csSel = cs13; // completed is light green
		            						csSelF = cs13f;
		            						if (implStatus.equals("Partial")) { // partials are orange
		            							csSel = cs14;  
		            							csSelF = cs14f; }
		            						if (implStatus.equals("Aborted")) { // aborts are red
		            							csSel = cs15;  
		            							csSelF = cs15f; }
		            						if (implStatus.equals("Scheduled")) { // not attempted is yellow
		            							csSel = cs16;  
		            							csSelF = cs16f; }
		            						cell = sheet.getRow(currentRow).createCell(9);
		            						cell.setCellStyle(csSel);
		            						cell.setCellValue(rs.getString(7)); // site
		            						cell = sheet.getRow(currentRow).createCell(10);
		            						cell.setCellStyle(csSel);
		            						cell.setCellValue(rs.getString(14)); // nr id	            						
		            						cell = sheet.getRow(currentRow).createCell(11);
		            						cell.setCellStyle(csSel);		            						
		            						cell.setCellValue(rs.getString(1)); // project	            						
		            						cell = sheet.getRow(currentRow).createCell(12);
		            						cell.setCellStyle(csSel);		            						
		            						cell.setCellValue(rs.getString(15)); // job type		            						
		            						cell = sheet.getRow(currentRow).createCell(13);
		            						cell.setCellStyle(csSel);		            						
		            						cell.setCellValue(rs.getString(8)); // upgrade type
		            						cell = sheet.getRow(currentRow).createCell(14);
		            						cell.setCellStyle(csSel);
		            						cell.setCellValue(rs.getString(9)); // technology
		            						cell = sheet.getRow(currentRow).createCell(15);
		            						cell.setCellStyle(csSel);
		            						cell.setCellValue(rs.getString(4)); // status
		            						cell = sheet.getRow(currentRow).createCell(16);
		            						cell.setCellStyle(csSel);
		            						cell.setCellValue("-"); // reason
		            						cell = sheet.getRow(currentRow).createCell(17);
		            						cell.setCellStyle(csSel);
		            						String abortType = rs.getString(5); // responsible
		            						if (abortType.equals("Customer")) {
		            							cell.setCellValue("VF");  
		            						} else if (abortType.equals("-")) {
		            							cell.setCellValue("-");  
		            						} else {
		            							cell.setCellValue("DVT");
		            						}
		            						cell = sheet.getRow(currentRow).createCell(18);
		            						cell.setCellStyle(csSel);
		            						cell.setCellValue("-"); // action
		            						cell = sheet.getRow(currentRow).createCell(19);
		            						cell.setCellStyle(csSel);
		            						cell.setCellValue(rs.getString(13)); // comment
		            						cell = sheet.getRow(currentRow).createCell(21);
		            						cell.setCellStyle(csSelF);
		            						cell = sheet.getRow(currentRow).createCell(22);
		            						cell.setCellStyle(csSelF);
		            						cell = sheet.getRow(currentRow).createCell(23);
		            						cell.setCellStyle(csSelF);
		            						cell = sheet.getRow(currentRow).createCell(24);
		            						cell.setCellStyle(csSelF);
		            						cell = sheet.getRow(currentRow).createCell(25);
		            						cell.setCellStyle(csSelF);
		            						cell = sheet.getRow(currentRow).createCell(26);
		            						cell.setCellStyle(csSelF);
		            						cell = sheet.getRow(currentRow).createCell(27);
		            						cell.setCellStyle(csSelF);
		            						cell = sheet.getRow(currentRow).createCell(28);
		            						cell.setCellStyle(csSelF);
		            						cell = sheet.getRow(currentRow).createCell(29);
		            						cell.setCellStyle(csSelF);
		            						cell = sheet.getRow(currentRow).createCell(30); // total cost
		            						cell.setCellStyle(csSelF);
		            						String rowNo = String.valueOf(currentRow+1);
		            						cell.setCellFormula("SUM(T"+rowNo+":AB"+rowNo+")");
		            						cell = sheet.getRow(currentRow).createCell(32); // lock
		            						cell.setCellStyle(csSel);
		            						cell.setCellValue(rs.getString(10)); 
		            						cell = sheet.getRow(currentRow).createCell(33); // unlock
		            						cell.setCellStyle(csSel);
		            						cell.setCellValue(rs.getString(11)); 
		            						currentRow++;
    		            				}
    		            				lastRow = currentRow;
    		            				currentRow++;
    		            				// final grand total total headings
    		            				sheet.createRow(currentRow);
    		            				Cell cell = sheet.getRow(currentRow).createCell(25);
	            						cell.setCellValue("Success");
	            						cell.setCellStyle(cs17);
	            						cell = sheet.getRow(currentRow).createCell(26);
	            						cell.setCellValue("Abort");
	            						cell.setCellStyle(cs17);
	            						cell = sheet.getRow(currentRow).createCell(27);
	            						cell.setCellValue("OOH");
	            						cell.setCellStyle(cs17);
	            						cell = sheet.getRow(currentRow).createCell(28);
	            						cell.setCellValue("2nd Man");
	            						cell.setCellStyle(cs17);
	            						cell = sheet.getRow(currentRow).createCell(29);
	            						cell.setCellValue("Access");
	            						cell.setCellStyle(cs17);
	            						cell = sheet.getRow(currentRow).createCell(30);
	            						cell.setCellValue("Total Cost");
	            						cell.setCellStyle(cs17);
	            						// final grand totals
	            						currentRow++;
	            						sheet.createRow(currentRow);
	            						cell = sheet.getRow(currentRow).createCell(21);
	            						cell.setCellValue("WK "+week+"-"+shortYear+" Total (£)");
	            						cell.setCellStyle(cs5l);
	            						cell = sheet.getRow(currentRow).createCell(22);
	            						cell.setCellStyle(cs5m);
	            						cell = sheet.getRow(currentRow).createCell(23);
	            						cell.setCellStyle(cs5m);
	            						cell = sheet.getRow(currentRow).createCell(24);
	            						cell.setCellStyle(cs5r);
	            						String firstRowString = String.valueOf(firstRow);
	            						String lastRowString = String.valueOf(lastRow);
	            						cell = sheet.getRow(currentRow).createCell(25);
	            						cell.setCellFormula("SUM(X"+firstRowString+":X"+lastRowString+")");
	            						cell.setCellStyle(cs18);
	            						cell = sheet.getRow(currentRow).createCell(26);
	            						cell.setCellFormula("SUM(Y"+firstRowString+":Y"+lastRowString+")");
	            						cell.setCellStyle(cs18);
	            						cell = sheet.getRow(currentRow).createCell(27);
	            						cell.setCellFormula("SUM(Z"+firstRowString+":Z"+lastRowString+")");
	            						cell.setCellStyle(cs18);
	            						cell = sheet.getRow(currentRow).createCell(28);
	            						cell.setCellFormula("SUM(AA"+firstRowString+":AA"+lastRowString+")");
	            						cell.setCellStyle(cs18);
	            						cell = sheet.getRow(currentRow).createCell(29);
	            						cell.setCellFormula("SUM(AB"+firstRowString+":AB"+lastRowString+")");
	            						cell.setCellStyle(cs18);
	            						cell = sheet.getRow(currentRow).createCell(30);
	            						cell.setCellFormula("SUM(AC"+firstRowString+":AC"+lastRowString+")");
	            						cell.setCellStyle(cs18);
	            						sheet.setColumnWidth(10, 20*256);
	            						sheet.setColumnWidth(11, 17*256);
	            						sheet.setColumnWidth(12, 17*256);
	            						sheet.setColumnWidth(13, 13*256);
	            						sheet.setColumnWidth(14, 52*256);
	            						sheet.setColumnWidth(15, 14*256);
	            						sheet.setColumnWidth(16, 17*256);
	            						sheet.setColumnWidth(17, 12*256);
	            						sheet.setColumnWidth(18, 12*256);
	            						sheet.setColumnWidth(19, 36*256);
    		            			}
    		            			// force download  of spreadsheet 
        		    		        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        		    		        String headerKey = "Content-Disposition";
        		    		        String headerValue = String.format("attachment; filename=\"%s\"", "Weekly breakdown " + projectName+ " " + yearWeek + ".xlsx");
        		    		        resp.setHeader(headerKey, headerValue);
        		    		        // obtains response's output stream
        		    		        OutputStream outputStream = resp.getOutputStream();
        		    		        wb.write(outputStream);
        		    		        outputStream.close();        		    		        
        						}
        					}
            			}
        			} catch (Exception e) {
        				throw new Exception("Weekly Breakdown, " + e.getMessage());
        			} finally {
        				if ((cstmt !=null) && (!cstmt.isClosed())) cstmt.close();
        				if ((conn !=null) && (!conn.isClosed())) conn.close();
        			}
        		}      		
    		}
    		catch (Exception ex) {
    				session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
    						"Error: " + ex.getMessage());
    				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, "Reporting");
    	  	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/reporting.jsp"+ran);
    	  	      	dispatcher.forward(req,resp);
    		}
    	} else { // usual parameterised CSV download production
    		try {
    			Connection conn = null;
    			CallableStatement cstmt = null;
    			String reportNameCondensed = reportName.replaceAll("\\s+","");
    			StringBuilder sql = new StringBuilder("{call Get" + 
    					reportNameCondensed + "Report(");
    			if (rpList.size() > 0) {
    				for (int i = 0; i < rpList.size(); i++ ) {
    	   				sql.append("?,");
    				}
    				sql.setLength(sql.length()-1);
    			}
    			sql.append(")}");
    			try {
    				conn = DriverManager.getConnection(url);
    				cstmt = conn.prepareCall(sql.toString());
    				int i = 0;
    				for (Iterator<ReportParameter> it = rpList.iterator(); it.hasNext(); ) {
    					ReportParameter rp = it.next();
    					if (rp.getDatatype().equals(ReportParameter.DATATYPE_SELECT_STRING)) {
    						String strP = req.getParameter("filterReport" + rp.getParameterNameCondensed());
    						cstmt.setString(++i, strP);
    					} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_SELECT_NUMBER)) {
    						String strP = req.getParameter("filterReport" + rp.getParameterNameCondensed());
    						cstmt.setDouble(++i, Double.parseDouble(strP));
    					} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_DATE)) {
    						java.sql.Date dateP = null;
    						String strP = req.getParameter(rp.getParameterNameCondensed());
    						if (!StringUtil.hasNoValue(strP)) {
    							dateP = java.sql.Date.valueOf(strP.substring(6, 10) + "-" +
    									strP.substring(3, 5) + "-" + strP.substring(0, 2));
    						}
    						cstmt.setDate(++i, dateP);
    					} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_TIMESTAMP)) {
    						Timestamp tP = null;
    						String strP = req.getParameter(rp.getParameterNameCondensed());
    						if (!StringUtil.hasNoValue(strP)) {
    							tP = Timestamp.valueOf(strP.substring(6, 10) + "-" +
    									strP.substring(3, 5) + "-" + strP.substring(0, 2) + 
    									" " + strP.substring(11, 16) + ":00");
    						}
    						cstmt.setTimestamp(++i, tP);
    					} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_STRING)) {
    						String strP = req.getParameter(rp.getParameterNameCondensed());
    						cstmt.setString(++i, strP);
    					} else if (rp.getDatatype().equals(ReportParameter.DATATYPE_NUMBER)) {
    						String strP = req.getParameter(rp.getParameterNameCondensed());
    						cstmt.setDouble(++i, Double.parseDouble(strP));
    					}
    				}
    				if (cstmt.execute()) {
    					ResultSet rs = cstmt.getResultSet();
    					ResultSetMetaData rsmd = rs.getMetaData();
    					int cols = rsmd.getColumnCount() + 1;
    					if (cols > 0) {
    						if (rsmd.getColumnName(1).equalsIgnoreCase("Error")) {
    							String msg = "Unknown error";
    							if (rs.next()) {
    								msg = rs.getString(1);
    							}
    							throw new Exception(msg);
    						} else {
    							Date date = new Date();
    							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    							String formattedDate = sdf.format(date);
    					        OutputStream oS = resp.getOutputStream();
    							resp.setContentType("application/vnd.ms-excel");
    							//resp.setContentType("text/plain");
    					        // forces download 
    					        String headerKey = "Content-Disposition";
    					        String headerValue = String.format("attachment; filename=\"%s\"", reportNameCondensed + 
    					        		"_" + formattedDate + ".csv");
    					        resp.setHeader(headerKey, headerValue);
    					        StringBuilder sB = new StringBuilder();
    					        for (int c = 1; c < cols; c++) {
    					        	sB.append(rsmd.getColumnName(c).replace('_', ' '));
    					        	sB.append(",");
    					        }
    					        //sB.setLength(sB.length()-1);
    					        sB.setCharAt(sB.length()-1, '\n');
    					        oS.write(sB.toString().getBytes());
    					        String value;
    					        while (rs.next()) {
    					        	sB = new StringBuilder();
        					        for (int c = 1; c < cols; c++) {
        					        	int colType = rsmd.getColumnType(c);
           					        	if (colType == java.sql.Types.TIMESTAMP) {
        					        		Timestamp valueTS = rs.getTimestamp(c);
        					        		value = valueTS==null?"":tsFormatter.format(new Date(valueTS.getTime()));
        					        	} else if (colType == java.sql.Types.DATE) {
        					        		java.sql.Date valueDate = rs.getDate(c);
        					        		value = valueDate==null?"":dateFormatter.format(valueDate);
        					        	} else {
    	    					        	String valueStr = rs.getString(c);
    	    					        	value = valueStr==null?"":valueStr.replace(',', ' ');
        					        	}
        					        	sB.append(value);
        					        	sB.append(",");
        					        }
        					        sB.setCharAt(sB.length()-1, '\n');
        					        oS.write(sB.toString().getBytes());
    					        }
    					        oS.flush();
    							oS.close();
    						}
    					}
    				}
    			} catch (Exception e) {
    				throw new Exception("Download Report, " + e.getMessage());
    			} finally {
    				if ((cstmt !=null) && (!cstmt.isClosed())) cstmt.close();
    				if ((conn !=null) && (!conn.isClosed())) conn.close();
    			}
    		} catch (Exception ex) {
            	req.setAttribute("userMessage", "Error: " + ex.getMessage());
    			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
    		  	dispatcher.forward(req,resp);
    		}
    	}		
	}
	
	private void initialiseCellStyles() {
		// set up all cell styles used in weekly breakdown report
		f = wb.createFont();
        f.setColor(HSSFColor.WHITE.index);
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        short fh = 16;
		f.setFontHeightInPoints(fh);
        cs1 = wb.createCellStyle();
        cs1.setFont(f);
        cs1.setFillForegroundColor(HSSFColor.BLACK.index);
        cs1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs1.setAlignment(CellStyle.ALIGN_CENTER);
        cs1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		f2 = wb.createFont();
        f2.setColor(HSSFColor.WHITE.index);
        f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fh = 11;
		f2.setFontHeightInPoints(fh);
		cs2 = wb.createCellStyle();
        cs2.setFont(f2);
        cs2.setFillForegroundColor(HSSFColor.BLACK.index);
        cs2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        cs2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs2.setBottomBorderColor(HSSFColor.BLACK.index);
        cs2.setTopBorderColor(HSSFColor.BLACK.index);
        cs2.setRightBorderColor(HSSFColor.BLACK.index);
        cs2.setLeftBorderColor(HSSFColor.BLACK.index);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);   
		cs3 = wb.createCellStyle();
        cs3.setFont(f2);
        cs3.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        cs3.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs3.setAlignment(CellStyle.ALIGN_LEFT);
        cs3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs3.setBottomBorderColor(HSSFColor.BLACK.index);
        cs3.setTopBorderColor(HSSFColor.BLACK.index);
        cs3.setRightBorderColor(HSSFColor.BLACK.index);
        cs3.setLeftBorderColor(HSSFColor.BLACK.index);
        cs3.setBorderBottom(CellStyle.BORDER_THIN);
        cs3.setBorderTop(CellStyle.BORDER_THIN);
        cs3.setBorderRight(CellStyle.BORDER_THIN);
        cs3.setBorderLeft(CellStyle.BORDER_THIN);
        cs4 = wb.createCellStyle();
        cs4.setFont(f2);
        cs4.setBorderBottom(CellStyle.BORDER_THIN);
        cs4.setBorderTop(CellStyle.BORDER_THIN);
        cs4.setBorderRight(CellStyle.BORDER_THIN);
        cs4.setBorderLeft(CellStyle.BORDER_THIN);
        cs4.setBottomBorderColor(HSSFColor.BLACK.index);
        cs4.setTopBorderColor(HSSFColor.BLACK.index);
        cs4.setRightBorderColor(HSSFColor.BLACK.index);
        cs4.setLeftBorderColor(HSSFColor.BLACK.index);
        cs4.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        cs4.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs4.setAlignment(CellStyle.ALIGN_CENTER);
        cs4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		f3 = wb.createFont();
        f3.setColor(HSSFColor.BLACK.index);
        f3.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        fh = 11;
		f3.setFontHeightInPoints(fh);
        cs5 = wb.createCellStyle();
        cs5.setFont(f3);
        cs5.setFillForegroundColor(HSSFColor.WHITE.index);
        cs5.setFillPattern(CellStyle.NO_FILL);
        cs5.setAlignment(CellStyle.ALIGN_CENTER);
        cs5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs5.setBottomBorderColor(HSSFColor.BLACK.index);
        cs5.setTopBorderColor(HSSFColor.BLACK.index);
        cs5.setRightBorderColor(HSSFColor.BLACK.index);
        cs5.setLeftBorderColor(HSSFColor.BLACK.index);
        cs5.setBorderBottom(CellStyle.BORDER_THIN);
        cs5.setBorderTop(CellStyle.BORDER_THIN);
        cs5.setBorderRight(CellStyle.BORDER_THIN);
        cs5.setBorderLeft(CellStyle.BORDER_THIN);        
        cs5l = wb.createCellStyle();
        cs5l.setFont(f3);
        cs5l.setFillForegroundColor(HSSFColor.WHITE.index);
        cs5l.setFillPattern(CellStyle.NO_FILL);
        cs5l.setAlignment(CellStyle.ALIGN_LEFT);
        cs5l.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs5l.setBottomBorderColor(HSSFColor.BLACK.index);
        cs5l.setTopBorderColor(HSSFColor.BLACK.index);
        cs5l.setLeftBorderColor(HSSFColor.BLACK.index);
        cs5l.setBorderBottom(CellStyle.BORDER_THIN);
        cs5l.setBorderTop(CellStyle.BORDER_THIN);
        cs5l.setBorderLeft(CellStyle.BORDER_THIN);        
        cs5m = wb.createCellStyle();
        cs5m.setFont(f3);
        cs5m.setFillForegroundColor(HSSFColor.WHITE.index);
        cs5m.setFillPattern(CellStyle.NO_FILL);
        cs5m.setAlignment(CellStyle.ALIGN_LEFT);
        cs5m.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs5m.setBottomBorderColor(HSSFColor.BLACK.index);
        cs5m.setTopBorderColor(HSSFColor.BLACK.index);
        cs5m.setBorderBottom(CellStyle.BORDER_THIN);
        cs5m.setBorderTop(CellStyle.BORDER_THIN);        
        cs5r = wb.createCellStyle();
        cs5r.setFont(f3);
        cs5r.setFillForegroundColor(HSSFColor.WHITE.index);
        cs5r.setFillPattern(CellStyle.NO_FILL);
        cs5r.setAlignment(CellStyle.ALIGN_LEFT);
        cs5r.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs5r.setBottomBorderColor(HSSFColor.BLACK.index);
        cs5r.setTopBorderColor(HSSFColor.BLACK.index);
        cs5r.setRightBorderColor(HSSFColor.BLACK.index);
        cs5r.setBorderBottom(CellStyle.BORDER_THIN);
        cs5r.setBorderTop(CellStyle.BORDER_THIN);
        cs5r.setBorderRight(CellStyle.BORDER_THIN);        
        cs6 = wb.createCellStyle();
        cs6.setFont(f3);
        cs6.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cs6.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs6.setAlignment(CellStyle.ALIGN_CENTER);
        cs6.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs6.setBottomBorderColor(HSSFColor.BLACK.index);
        cs6.setTopBorderColor(HSSFColor.BLACK.index);
        cs6.setRightBorderColor(HSSFColor.BLACK.index);
        cs6.setLeftBorderColor(HSSFColor.BLACK.index);
        cs6.setBorderBottom(CellStyle.BORDER_THIN);
        cs6.setBorderTop(CellStyle.BORDER_THIN);
        cs6.setBorderRight(CellStyle.BORDER_THIN);
        cs6.setBorderLeft(CellStyle.BORDER_THIN);        
        cs7 = wb.createCellStyle();
        cs7.setFont(f2);
        cs7.setFillForegroundColor(HSSFColor.BLACK.index);
        cs7.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs7.setAlignment(CellStyle.ALIGN_CENTER);
        cs7.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs7.setBottomBorderColor(HSSFColor.BLACK.index);
        cs7.setTopBorderColor(HSSFColor.BLACK.index);
        cs7.setRightBorderColor(HSSFColor.BLACK.index);
        cs7.setLeftBorderColor(HSSFColor.BLACK.index);
        cs7.setBorderBottom(CellStyle.BORDER_THIN);
        cs7.setBorderTop(CellStyle.BORDER_THIN);
        cs7.setBorderRight(CellStyle.BORDER_THIN);
        cs7.setBorderLeft(CellStyle.BORDER_THIN); 
		cs8 = wb.createCellStyle();
        cs8.setFont(f2);
        cs8.setFillForegroundColor(HSSFColor.BLACK.index);
        cs8.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs8.setAlignment(CellStyle.ALIGN_LEFT);
        cs8.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs8.setBottomBorderColor(HSSFColor.BLACK.index);
        cs8.setTopBorderColor(HSSFColor.BLACK.index);
        cs8.setRightBorderColor(HSSFColor.BLACK.index);
        cs8.setLeftBorderColor(HSSFColor.BLACK.index);
        cs8.setBorderBottom(CellStyle.BORDER_THIN);
        cs8.setBorderTop(CellStyle.BORDER_THIN);
        cs8.setBorderRight(CellStyle.BORDER_THIN);
        cs8.setBorderLeft(CellStyle.BORDER_THIN);
		cs9 = wb.createCellStyle();
	    cs9.setFont(f3);
	    cs9.setFillForegroundColor(HSSFColor.GREEN.index);
	    cs9.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs9.setAlignment(CellStyle.ALIGN_LEFT);
	    cs9.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cs10 = wb.createCellStyle();
	    cs10.setFont(f3);
	    cs10.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
	    cs10.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs10.setAlignment(CellStyle.ALIGN_LEFT);
	    cs10.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cs11 = wb.createCellStyle();
	    cs11.setFont(f3);
	    cs11.setFillForegroundColor(HSSFColor.GREEN.index);
	    cs11.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs11.setAlignment(CellStyle.ALIGN_RIGHT);
	    cs11.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cs11.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
	    cs12 = wb.createCellStyle();
	    cs12.setFont(f3);
	    cs12.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
	    cs12.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs12.setAlignment(CellStyle.ALIGN_RIGHT);
	    cs12.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    cs12.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
	    cs13 = wb.createCellStyle();
	    cs13.setFont(f3);
	    cs13.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
	    cs13.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs13.setAlignment(CellStyle.ALIGN_CENTER);
	    cs13.setWrapText(true);
	    cs13.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs13.setBorderBottom(CellStyle.BORDER_THIN);
        cs13.setBorderTop(CellStyle.BORDER_THIN);
        cs13.setBorderRight(CellStyle.BORDER_THIN);
        cs13.setBorderLeft(CellStyle.BORDER_THIN);
        cs13.setBottomBorderColor(HSSFColor.BLACK.index);
        cs13.setTopBorderColor(HSSFColor.BLACK.index);
        cs13.setRightBorderColor(HSSFColor.BLACK.index);
        cs13.setLeftBorderColor(HSSFColor.BLACK.index);
	    cs13f = wb.createCellStyle();
	    cs13f.setFont(f3);
	    cs13f.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
	    cs13f.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs13f.setAlignment(CellStyle.ALIGN_CENTER);
	    cs13f.setWrapText(true);
	    cs13f.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs13f.setBorderBottom(CellStyle.BORDER_THIN);
        cs13f.setBorderTop(CellStyle.BORDER_THIN);
        cs13f.setBorderRight(CellStyle.BORDER_THIN);
        cs13f.setBorderLeft(CellStyle.BORDER_THIN);
        cs13f.setBottomBorderColor(HSSFColor.BLACK.index);
        cs13f.setTopBorderColor(HSSFColor.BLACK.index);
        cs13f.setRightBorderColor(HSSFColor.BLACK.index);
        cs13f.setLeftBorderColor(HSSFColor.BLACK.index);
        cs13f.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
	    cs14 = wb.createCellStyle();
	    cs14.setFont(f3);
	    cs14.setFillForegroundColor(HSSFColor.ORANGE.index);
	    cs14.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs14.setAlignment(CellStyle.ALIGN_CENTER);
	    cs14.setWrapText(true);
	    cs14.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs14.setBorderBottom(CellStyle.BORDER_THIN);
        cs14.setBorderTop(CellStyle.BORDER_THIN);
        cs14.setBorderRight(CellStyle.BORDER_THIN);
        cs14.setBorderLeft(CellStyle.BORDER_THIN);
        cs14.setBottomBorderColor(HSSFColor.BLACK.index);
        cs14.setTopBorderColor(HSSFColor.BLACK.index);
        cs14.setRightBorderColor(HSSFColor.BLACK.index);
        cs14.setLeftBorderColor(HSSFColor.BLACK.index);
	    cs14f = wb.createCellStyle();
	    cs14f.setFont(f3);
	    cs14f.setFillForegroundColor(HSSFColor.ORANGE.index);
	    cs14f.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs14f.setAlignment(CellStyle.ALIGN_CENTER);
	    cs14f.setWrapText(true);
	    cs14f.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs14f.setBorderBottom(CellStyle.BORDER_THIN);
        cs14f.setBorderTop(CellStyle.BORDER_THIN);
        cs14f.setBorderRight(CellStyle.BORDER_THIN);
        cs14f.setBorderLeft(CellStyle.BORDER_THIN);
        cs14f.setBottomBorderColor(HSSFColor.BLACK.index);
        cs14f.setTopBorderColor(HSSFColor.BLACK.index);
        cs14f.setRightBorderColor(HSSFColor.BLACK.index);
        cs14f.setLeftBorderColor(HSSFColor.BLACK.index);
        cs14f.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
	    cs15 = wb.createCellStyle();
	    cs15.setFont(f3);
	    cs15.setFillForegroundColor(HSSFColor.RED.index);
	    cs15.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs15.setAlignment(CellStyle.ALIGN_CENTER);
	    cs15.setWrapText(true);
	    cs15.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs15.setBorderBottom(CellStyle.BORDER_THIN);
        cs15.setBorderTop(CellStyle.BORDER_THIN);
        cs15.setBorderRight(CellStyle.BORDER_THIN);
        cs15.setBorderLeft(CellStyle.BORDER_THIN);
        cs15.setBottomBorderColor(HSSFColor.BLACK.index);
        cs15.setTopBorderColor(HSSFColor.BLACK.index);
        cs15.setRightBorderColor(HSSFColor.BLACK.index);
        cs15.setLeftBorderColor(HSSFColor.BLACK.index);
	    cs15f = wb.createCellStyle();
	    cs15f.setFont(f3);
	    cs15f.setFillForegroundColor(HSSFColor.RED.index);
	    cs15f.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs15f.setAlignment(CellStyle.ALIGN_CENTER);
	    cs15f.setWrapText(true);
	    cs15f.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs15f.setBorderBottom(CellStyle.BORDER_THIN);
        cs15f.setBorderTop(CellStyle.BORDER_THIN);
        cs15f.setBorderRight(CellStyle.BORDER_THIN);
        cs15f.setBorderLeft(CellStyle.BORDER_THIN);
        cs15f.setBottomBorderColor(HSSFColor.BLACK.index);
        cs15f.setTopBorderColor(HSSFColor.BLACK.index);
        cs15f.setRightBorderColor(HSSFColor.BLACK.index);
        cs15f.setLeftBorderColor(HSSFColor.BLACK.index);
        cs15f.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
	    cs16 = wb.createCellStyle();
	    cs16.setFont(f3);
	    cs16.setFillForegroundColor(HSSFColor.YELLOW.index);
	    cs16.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs16.setAlignment(CellStyle.ALIGN_CENTER);
	    cs16.setWrapText(true);
	    cs16.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs16.setBorderBottom(CellStyle.BORDER_THIN);
        cs16.setBorderTop(CellStyle.BORDER_THIN);
        cs16.setBorderRight(CellStyle.BORDER_THIN);
        cs16.setBorderLeft(CellStyle.BORDER_THIN);
        cs16.setBottomBorderColor(HSSFColor.BLACK.index);
        cs16.setTopBorderColor(HSSFColor.BLACK.index);
        cs16.setRightBorderColor(HSSFColor.BLACK.index);
        cs16.setLeftBorderColor(HSSFColor.BLACK.index);
	    cs16f = wb.createCellStyle();
	    cs16f.setFont(f3);
	    cs16f.setFillForegroundColor(HSSFColor.YELLOW.index);
	    cs16f.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    cs16f.setAlignment(CellStyle.ALIGN_CENTER);
	    cs16f.setWrapText(true);
	    cs16f.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs16f.setBorderBottom(CellStyle.BORDER_THIN);
        cs16f.setBorderTop(CellStyle.BORDER_THIN);
        cs16f.setBorderRight(CellStyle.BORDER_THIN);
        cs16f.setBorderLeft(CellStyle.BORDER_THIN);
        cs16f.setBottomBorderColor(HSSFColor.BLACK.index);
        cs16f.setTopBorderColor(HSSFColor.BLACK.index);
        cs16f.setRightBorderColor(HSSFColor.BLACK.index);
        cs16f.setLeftBorderColor(HSSFColor.BLACK.index);
        cs16f.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
        cs17 = wb.createCellStyle();
        cs17.setFont(f2);
        cs17.setBorderBottom(CellStyle.BORDER_THIN);
        cs17.setBorderTop(CellStyle.BORDER_THIN);
        cs17.setBorderRight(CellStyle.BORDER_THIN);
        cs17.setBorderLeft(CellStyle.BORDER_THIN);
        cs17.setBottomBorderColor(HSSFColor.BLACK.index);
        cs17.setTopBorderColor(HSSFColor.BLACK.index);
        cs17.setRightBorderColor(HSSFColor.BLACK.index);
        cs17.setLeftBorderColor(HSSFColor.BLACK.index);
        cs17.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        cs17.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs17.setAlignment(CellStyle.ALIGN_CENTER);
        cs17.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs17.setRotation((short)90);
        cs17.setWrapText(true);
        cs18 = wb.createCellStyle();
        cs18.setFont(f3);
        cs18.setFillForegroundColor(HSSFColor.WHITE.index);
        cs18.setFillPattern(CellStyle.NO_FILL);
        cs18.setAlignment(CellStyle.ALIGN_CENTER);
        cs18.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs18.setBottomBorderColor(HSSFColor.BLACK.index);
        cs18.setTopBorderColor(HSSFColor.BLACK.index);
        cs18.setRightBorderColor(HSSFColor.BLACK.index);
        cs18.setLeftBorderColor(HSSFColor.BLACK.index);
        cs18.setBorderBottom(CellStyle.BORDER_THIN);
        cs18.setBorderTop(CellStyle.BORDER_THIN);
        cs18.setBorderRight(CellStyle.BORDER_THIN);
        cs18.setBorderLeft(CellStyle.BORDER_THIN);
	    cs18.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
	}
	
	private void setUpTitleBar() {
		// weekly breakdown report title bar
		sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);        
        Cell cell = sheet.getRow(2).createCell(1);
        cell.setCellValue("Service Centre Daily Summary - Dashboard");
        cell.setCellStyle(cs1);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 17));
	}
	
	private void setUpEmptyCountsBox(String week) {
		// set up weekly report counts box and pre-fill with zero counts
		sheet.createRow(3);
		sheet.createRow(4);
		sheet.createRow(5);
		sheet.createRow(6);
		sheet.createRow(7);
		sheet.createRow(8);
		sheet.createRow(9);
		sheet.createRow(10);
		sheet.createRow(11);
		sheet.createRow(12);
		sheet.createRow(13);
		sheet.createRow(14);
		Cell cell = sheet.getRow(5).createCell(1);
		cell.setCellValue("Week - "+week);
		cell.setCellStyle(cs2);
		sheet.addMergedRegion(new CellRangeAddress(5,7,1,1));
		cell = sheet.getRow(8).createCell(1);
		cell.setCellValue("Monday");
		cell.setCellStyle(cs3);		
		cell = sheet.getRow(9).createCell(1);
		cell.setCellValue("Tuesday");
		cell.setCellStyle(cs3);		
		cell = sheet.getRow(10).createCell(1);
		cell.setCellValue("Wednesday");
		cell.setCellStyle(cs3);		
		cell = sheet.getRow(11).createCell(1);
		cell.setCellValue("Thursday");
		cell.setCellStyle(cs3);		
		cell = sheet.getRow(12).createCell(1);
		cell.setCellValue("Friday");
		cell.setCellStyle(cs3);		
		cell = sheet.getRow(13).createCell(1);
		cell.setCellValue("Saturday");
		cell.setCellStyle(cs3);		
		cell = sheet.getRow(14).createCell(1);
		cell.setCellValue("Sunday");
		cell.setCellStyle(cs3);		
		sheet.setColumnWidth(1, 12*256);
		cell = sheet.getRow(5).createCell(2);
		cell.setCellValue("Scheduled");
		cell.setCellStyle(cs4);	
		sheet.setColumnWidth(2, 11*256);
		borderCellRange(5,6,2,2);
		cell = sheet.getRow(5).createCell(3);
		cell.setCellValue("Attempted");
		cell.setCellStyle(cs4);	
		sheet.setColumnWidth(3, 11*256);
		borderCellRange(5,6,3,3);
		cell = sheet.getRow(5).createCell(4);
		cell.setCellValue("Completed");
		cell.setCellStyle(cs4);	
		sheet.setColumnWidth(4, 11*256);
		borderCellRange(5,6,4,4);
		cell = sheet.getRow(5).createCell(5);
		cell.setCellValue("Partial");
		cell.setCellStyle(cs4);
		sheet.setColumnWidth(5, 11*256);
		borderCellRange(5,6,5,5);
		cell = sheet.getRow(5).createCell(6);
		cell.setCellValue("Abort");
		cell.setCellStyle(cs4);
		borderCellRange(5,5,6,7);		
		cell = sheet.getRow(6).createCell(6);
		cell.setCellValue("VF");
		cell.setCellStyle(cs4);		
		sheet.setColumnWidth(6, 11*256);
		cell = sheet.getRow(6).createCell(7);
		cell.setCellValue("DVT");
		cell.setCellStyle(cs4);		
		sheet.setColumnWidth(7, 11*256);
		cell = sheet.getRow(8).createCell(2);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(8).createCell(3);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(8).createCell(4);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(8).createCell(5);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(8).createCell(6);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(8).createCell(7);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(9).createCell(2);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(9).createCell(3);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(9).createCell(4);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(9).createCell(5);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(9).createCell(6);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(9).createCell(7);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(10).createCell(2);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(10).createCell(3);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(10).createCell(4);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(10).createCell(5);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(10).createCell(6);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(10).createCell(7);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(11).createCell(2);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(11).createCell(3);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(11).createCell(4);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(11).createCell(5);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(11).createCell(6);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(11).createCell(7);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(12).createCell(2);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(12).createCell(3);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(12).createCell(4);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(12).createCell(5);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(12).createCell(6);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(12).createCell(7);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(13).createCell(2);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(13).createCell(3);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(13).createCell(4);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(13).createCell(5);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(13).createCell(6);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(13).createCell(7);
		cell.setCellValue("0");
		cell.setCellStyle(cs6);
		cell = sheet.getRow(14).createCell(2);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(14).createCell(3);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(14).createCell(4);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(14).createCell(5);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(14).createCell(6);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(14).createCell(7);
		cell.setCellValue("0");
		cell.setCellStyle(cs5);
		cell = sheet.getRow(7).createCell(2);
		cell.setCellFormula("SUM(C9:C15)");
		cell.setCellStyle(cs7);
		cell = sheet.getRow(7).createCell(2);
		cell.setCellFormula("SUM(C9:C15)");
		cell.setCellStyle(cs7);
		cell = sheet.getRow(7).createCell(3);
		cell.setCellFormula("SUM(D9:D15)");
		cell.setCellStyle(cs7);		
		cell = sheet.getRow(7).createCell(4);
		cell.setCellFormula("SUM(E9:E15)");
		cell.setCellStyle(cs7);
		cell = sheet.getRow(7).createCell(5);
		cell.setCellFormula("SUM(F9:F15)");
		cell.setCellStyle(cs7);
		cell = sheet.getRow(7).createCell(6);
		cell.setCellFormula("SUM(G9:G15)");
		cell.setCellStyle(cs7);
		cell = sheet.getRow(7).createCell(7);
		cell.setCellStyle(cs7);
		cell.setCellFormula("SUM(H9:H15)");		
	}
	
	private void setUpSuccessRate() {
		// creates weekly breakdown success percentage rows
		Cell cell = sheet.getRow(7).createCell(9);
		cell.setCellValue("DVT Success Rate");
		cell.setCellStyle(cs9);
		cell = sheet.getRow(7).createCell(10);
		cell.setCellStyle(cs9);
		cell = sheet.getRow(7).createCell(11);
		cell.setCellStyle(cs11);
		cell.setCellFormula("IF(D8=0,0,((E8+F8+G8)/D8))");
		cell = sheet.getRow(8).createCell(9);
		cell.setCellValue("Overall Success Rate");
		cell.setCellStyle(cs10);
		cell = sheet.getRow(8).createCell(10);
		cell.setCellStyle(cs10);
		cell = sheet.getRow(8).createCell(11);
		cell.setCellStyle(cs12);
		cell.setCellFormula("IF(D8=0,0,(E8/D8))");
	}
	
	private void borderCellRange( int startRow, int endRow, int startCell, int endCell) {
		// creates range and ensures it is correctly bordered
		CellRangeAddress cellRangeAddress = new CellRangeAddress(startRow, endRow, startCell, endCell);
		sheet.addMergedRegion(cellRangeAddress);
		RegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
		RegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
		RegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
		RegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
	}
}
