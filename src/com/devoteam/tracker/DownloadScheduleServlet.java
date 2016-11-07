package com.devoteam.tracker;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.devoteam.tracker.model.BOEngineer;
import com.devoteam.tracker.model.FieldEngineer;
import com.devoteam.tracker.model.SNRScheduleSpreadsheet;
import com.devoteam.tracker.model.SystemParameters;
import com.devoteam.tracker.model.UserRole;
import com.devoteam.tracker.util.ServletConstants;

public class DownloadScheduleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1645997977448341289L;
	private Workbook wb;
	private Sheet sheet;
	private Font f;
	private Font f2;
	private Font f3;
	private CellStyle cs1;
	private CellStyle cs2;
	private CellStyle cs3;
	private CellStyle cs2w;
	private CellStyle cs3w;
	private CellStyle cs4;
	private CellStyle cs5;
	private CellStyle cs6;
	private CellStyle cs7;
	private CellStyle cs8;
	private CellStyle cs9;
	private String[] snrTitles =  SNRScheduleSpreadsheet.getSNRTitles(); 
	private int[] columnWidths =  SNRScheduleSpreadsheet.getColumnWidths(); 
	private SimpleDateFormat dayOfWeek = new SimpleDateFormat("EEEE"); 
	private String url;
	private ArrayList<SNRScheduleSpreadsheet> scheduledSNRs;
	private HashMap<String, Integer> workflowNames;
	private HashMap<GregorianCalendar, ArrayList<SNRScheduleSpreadsheet>> formattedSNRs;
	private int todaysCol = 0;
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
		HttpSession session = req.getSession(false);
		if (session == null) {
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
			//resp.sendRedirect("/logon.jsp"+ran);
  	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/logon.jsp"+ran);
  	      	dispatcher.forward(req,resp);
		} else {	
			try {
		    	url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String formattedDate = sdf.format(date);
		        
		        if (getScheduledSNRs()) {
		        	SystemParameters sP = (SystemParameters)session.getAttribute(ServletConstants.SYSTEM_PARAMETERS_IN_SESSION);
		        	formatScheduledSNRs();
		        	
			        GregorianCalendar rightNow = new GregorianCalendar();
			        GregorianCalendar today = new GregorianCalendar(rightNow.get(Calendar.YEAR), 
			        		rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DATE));
			        GregorianCalendar firstDay = new GregorianCalendar(rightNow.get(Calendar.YEAR), 
			        		rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DATE));
			        firstDay.add(Calendar.DATE, (sP.getScheduleBefore() * -1));
			        GregorianCalendar lastDay = new GregorianCalendar(rightNow.get(Calendar.YEAR), 
			        		rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DATE));
			        lastDay.add(Calendar.DATE, sP.getScheduleAfter());
			        GregorianCalendar thisDay = new GregorianCalendar(firstDay.get(Calendar.YEAR), 
			        		firstDay.get(Calendar.MONTH), firstDay.get(Calendar.DATE));
			        
			        /*int dd = today.get(Calendar.DATE);
			        int mm = today.get(Calendar.MONTH) + 1;
			        int yyyy = today.get(Calendar.YEAR);
			        dd = firstDay.get(Calendar.DATE);
			        mm = firstDay.get(Calendar.MONTH) + 1;
			        yyyy = firstDay.get(Calendar.YEAR);
			        dd = lastDay.get(Calendar.DATE);
			        mm = lastDay.get(Calendar.MONTH) + 1;
			        yyyy = lastDay.get(Calendar.YEAR);*/
			        
			        
			     	wb = new XSSFWorkbook();
			     	initialiseCellStyles();
			        sheet = wb.createSheet("Tracker Schedule");
			        setUpTitleRows();      
			        int len = snrTitles.length;
			        /*for (int i = 1; i < 32; i++) { 
			        	int start = 1 + (len * (i - 1));
			        	setUpDayHeader(start, new GregorianCalendar(2014,9,i));        
			        }*/	
			        
			        int i = 0;
			        while ((!thisDay.after(lastDay))/* && (i<200)*/) {
				        /*dd = thisDay.get(Calendar.DATE);
				        mm = thisDay.get(Calendar.MONTH) + 1;
				        yyyy = thisDay.get(Calendar.YEAR);*/
			        	int start = 1 + (len * i);
			        	setUpDayHeader(start, thisDay, today);      
			        	setUpDayData(start, thisDay); 
			        	i++;
			        	thisDay.add(Calendar.DATE, 1);
			        }
			        
			        sheet.setColumnWidth(1, 8*256);
			        sheet.createFreezePane(1, 0);
			        wb.setActiveSheet(0);
		        	sheet.showInPane((short)5, (short)todaysCol);
	
			        
					resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			        // forces download 
			        String headerKey = "Content-Disposition";
			        String headerValue = String.format("attachment; filename=\"%s\"", "schedule_" + formattedDate + ".xlsx");
			        resp.setHeader(headerKey, headerValue);
			        // obtains response's output stream
			        OutputStream outputStream = resp.getOutputStream();
			        wb.write(outputStream);
			        outputStream.close();
		        } else {
		        	throw new Exception("Unable to get scheduled SNRs");
		        }
			} catch (Exception ex) {
				session.setAttribute(ServletConstants.USER_MESSAGE_NAME_IN_SESSION, 
						"Error: " + ex.getMessage());
				session.setAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION, "Work Queues");
				//resp.sendRedirect("/workQueues.jsp"+ran);
	  	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/workQueues.jsp"+ran);
	  	      	dispatcher.forward(req,resp);
			}
		}
	}
	
	private void initialiseCellStyles() {
        f = wb.createFont();
        f.setColor(HSSFColor.WHITE.index);
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);
        f2 = wb.createFont();
        f2.setColor(HSSFColor.YELLOW.index);
        cs1 = wb.createCellStyle();
        cs1.setFont(f);
        cs1.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        cs1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs1.setAlignment(CellStyle.ALIGN_CENTER);
        cs1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs2 = wb.createCellStyle();
        cs2.setFont(f);
        cs2.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
        cs2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        cs2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setLeftBorderColor(HSSFColor.WHITE.index);
        cs3 = wb.createCellStyle();
        cs3.setFont(f);
        cs3.setFillForegroundColor(HSSFColor.OLIVE_GREEN.index);
        cs3.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);
        cs3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        String excelFormatPattern = DateFormatConverter.convert(Locale.ENGLISH, "MMM-yyyy");
        DataFormat poiFormat = wb.createDataFormat();
        cs3.setDataFormat(poiFormat.getFormat(excelFormatPattern));
        cs3.setBorderLeft(CellStyle.BORDER_THIN);
        cs3.setLeftBorderColor(HSSFColor.WHITE.index);
        cs2w = wb.createCellStyle();
        cs2w.setFont(f);
        cs2w.setFillForegroundColor(HSSFColor.RED.index);
        cs2w.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs2w.setAlignment(CellStyle.ALIGN_CENTER);
        cs2w.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs2w.setBorderLeft(CellStyle.BORDER_THIN);
        cs2w.setLeftBorderColor(HSSFColor.WHITE.index);
        cs3w = wb.createCellStyle();
        cs3w.setFont(f);
        cs3w.setFillForegroundColor(HSSFColor.RED.index);
        cs3w.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs3w.setAlignment(CellStyle.ALIGN_CENTER);
        cs3w.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        //String excelFormatPattern = DateFormatConverter.convert(Locale.ENGLISH, "MMM-yyyy");
        //DataFormat poiFormat = wb.createDataFormat();
        cs3w.setDataFormat(poiFormat.getFormat(excelFormatPattern));
        cs3w.setBorderLeft(CellStyle.BORDER_THIN);
        cs3w.setLeftBorderColor(HSSFColor.WHITE.index);
        cs4 = wb.createCellStyle();
        cs4.setFont(f2);
        cs4.setFillForegroundColor(HSSFColor.DARK_BLUE.index);
        cs4.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //cs4.setAlignment(CellStyle.ALIGN_CENTER);
        cs4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs4.setBorderBottom(CellStyle.BORDER_THIN);
        cs4.setBottomBorderColor(HSSFColor.YELLOW.index);
        cs4.setBorderTop(CellStyle.BORDER_THIN);
        cs4.setTopBorderColor(HSSFColor.YELLOW.index);
        cs4.setBorderLeft(CellStyle.BORDER_THIN);
        cs4.setLeftBorderColor(HSSFColor.YELLOW.index);
        cs4.setBorderRight(CellStyle.BORDER_THIN);
        cs4.setRightBorderColor(HSSFColor.YELLOW.index);
        f3 = wb.createFont();
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cs5 = wb.createCellStyle();
        cs5.setFont(f3);
        cs5.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs5.setAlignment(CellStyle.ALIGN_CENTER);
        cs6 = wb.createCellStyle();
        cs6.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs7 = wb.createCellStyle();
        cs7.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs7.setAlignment(CellStyle.ALIGN_CENTER);
        cs8 = wb.createCellStyle();
        cs8.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs8.setWrapText(true);
        cs9 = wb.createCellStyle();
        cs9.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs9.setAlignment(CellStyle.ALIGN_LEFT);
	}
	
	private void setUpTitleRows() {
        sheet.createRow(0);
        sheet.createRow(1);
        sheet.createRow(2);
        sheet.createRow(3);
        sheet.createRow(4);
        
        int i;
        for (i = 0; i < 5; i++) {
            Cell cell = sheet.getRow(i).createCell(0);
            cell.setCellStyle(cs1);
            if (i == 0) {
            	cell.setCellValue("Workflow");
            } else if (i == 1) {
            	cell.setCellValue("Name");
            }
        }
        
        //Set<String> keySet = workflowNames.keySet();
        SortedSet<String> keySet = new TreeSet<String>(workflowNames.keySet());
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
        	String workflowName = it.next();
        	int j;
        	int end = i + workflowNames.get(workflowName).intValue() + 1;
        	for (j = i; j < end; j++) {
                sheet.createRow(j);
                if (j > i) {
                    Cell cell = sheet.getRow(j).createCell(0);
                    cell.setCellStyle(cs5);
                    cell.setCellValue(workflowName);
                }
        	}
        	i = j;
        }
        sheet.setColumnWidth(0, 12*256);
	}
	
	private void setUpDayData(int startCell, GregorianCalendar date) {
		if (formattedSNRs.containsKey(date)) {
			String prevWorkflowName = null;
			int rowNum = 0;
			ArrayList<SNRScheduleSpreadsheet> todaysSNRs = formattedSNRs.get(date);
			for (Iterator<SNRScheduleSpreadsheet> it = todaysSNRs.iterator(); it.hasNext(); ) {
				SNRScheduleSpreadsheet sss = it.next();
				if ((prevWorkflowName == null) || (!prevWorkflowName.equals(sss.getWorkflowName()))) {
					rowNum = findWorkflowStartRow(sss.getWorkflowName());
					prevWorkflowName = sss.getWorkflowName();
				}
				Row row = sheet.getRow(rowNum);
				String[] snrTitles = SNRScheduleSpreadsheet.getSNRTitles();
				String[] spreadsheetValues = sss.getSpreadsheetValues();
				int end = startCell + spreadsheetValues.length;
				for (int i = startCell; i < end; i++) {
					Cell cell = row.createCell(i);
					String thisTitle = snrTitles[i-startCell];
					if (thisTitle.equals("Site")) {
						cell.setCellStyle(cs9);
						//cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						//cell.setCellValue(spreadsheetValues[i-startCell]==null?0:Double.parseDouble(spreadsheetValues[i-startCell]));
						if (spreadsheetValues[i-startCell]!=null) {
							cell.setCellValue(spreadsheetValues[i-startCell]);
						}
					} else if (thisTitle.equals("Commentary")) {
						cell.setCellStyle(cs8);
						cell.setCellValue(spreadsheetValues[i-startCell]);
					} else if (thisTitle.equals("No.")) {
						cell.setCellStyle(cs7);
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (spreadsheetValues[i-startCell]!=null) {
							cell.setCellValue(Double.parseDouble(spreadsheetValues[i-startCell]));
						}
					} else {
						cell.setCellStyle(cs6);
						cell.setCellValue(spreadsheetValues[i-startCell]);
					}
				}
				rowNum++;
			}
		}
	}
	
	private int findWorkflowStartRow(String workflowName) {
		int rowNum = 0;
		for (int i = 5; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(0);
			if (cell != null) {
				String cellValue = cell.getStringCellValue();
				//String cellValue = sheet.getRow(i).getCell(0).getStringCellValue();
				if ((cellValue != null) && (cellValue.equals(workflowName))) {
					rowNum = i;
					break;
				}
			}
		}
		return rowNum;
	}
	
	private void setUpDayHeader(int startCell, GregorianCalendar date, 
			GregorianCalendar today) {
		int endCell = startCell + (snrTitles.length - 1);
		
		String suffix = determineSuffix(date);
		int dow = date.get(Calendar.DAY_OF_WEEK);
		Date d = new Date(date.getTime().getTime());
		Cell cell = sheet.getRow(0).createCell(startCell);
        cell.setCellStyle(dow==1||dow==7?cs2w:cs2);
        cell.setCellValue("Week " + date.get(Calendar.WEEK_OF_YEAR) + suffix);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, startCell, endCell));

        cell = sheet.getRow(1).createCell(startCell);
        cell.setCellStyle(dow==1||dow==7?cs3w:cs3);
        cell.setCellValue(d);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, startCell, endCell));

        cell = sheet.getRow(2).createCell(startCell);
        cell.setCellStyle(dow==1||dow==7?cs2w:cs2);
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(date.get(Calendar.DATE));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, startCell, endCell));

        cell = sheet.getRow(3).createCell(startCell);
        cell.setCellStyle(dow==1||dow==7?cs2w:cs2);
        cell.setCellValue(dayOfWeek.format(d));
        if (date.equals(today)) {
        	//cell.setAsActiveCell();
        	//sheet.showInPane((short)7, (short)startCell);
        	todaysCol = startCell;
        }
        sheet.addMergedRegion(new CellRangeAddress(3, 3, startCell, endCell));
        
        for (int i = 0; i < snrTitles.length; i++) {
            cell = sheet.getRow(4).createCell(startCell+i);
            cell.setCellStyle(cs4);
            cell.setCellValue(snrTitles[i]);
            //sheet.setColumnWidth(startCell+i, (snrTitles[i].length()+5)*256);
            sheet.setColumnWidth(startCell+i, columnWidths[i]);
        }
	}
	
	private String determineSuffix(GregorianCalendar date) {
		String suffix = "";
		GregorianCalendar date2 = new GregorianCalendar(date.get(Calendar.YEAR), 
				date.get(Calendar.MONTH), date.get(Calendar.DATE)); 
		int dow = date2.get(Calendar.DAY_OF_WEEK);
		int startMonth = -1;
		int endMonth = -1;
		if (dow == 1) {
			endMonth = date2.get(Calendar.MONTH);
			date2.add(Calendar.DATE, -6);
			startMonth = date2.get(Calendar.MONTH);
		} else if (dow == 2) {
			startMonth = date2.get(Calendar.MONTH);
			date2.add(Calendar.DATE, 6);
			endMonth = date2.get(Calendar.MONTH);
		} else {
			int diff = (dow - 2) * -1;
			date2.add(Calendar.DATE, diff);
			startMonth = date2.get(Calendar.MONTH);
			date2.add(Calendar.DATE, 6);
			endMonth = date2.get(Calendar.MONTH);
		}
	
		if (startMonth != endMonth) {
			int thisMonth = date.get(Calendar.MONTH);
			suffix = startMonth==thisMonth?"a":"b";
		}
		return suffix;
	}

    private boolean getScheduledSNRs() 
        	throws Exception {
    	boolean ok = false;
		scheduledSNRs = new ArrayList<SNRScheduleSpreadsheet>();
		SNRScheduleSpreadsheet prevSSS = null;
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = DriverManager.getConnection(url);
			cstmt = conn.prepareCall("{call GetScheduleForOutput()}");
			if (cstmt.execute()) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					SNRScheduleSpreadsheet sss = new SNRScheduleSpreadsheet(rs.getDate(1), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
					if (rs.getString(7).equalsIgnoreCase(UserRole.ROLE_FIELD_ENGINEER)) {
						sss.addFieldEngineer(rs.getString(8), rs.getInt(9), rs.getString(10));
					} else if (rs.getString(7).equalsIgnoreCase(UserRole.ROLE_B_O_ENGINEER)) {
						sss.addBOEngineer(rs.getString(8), rs.getInt(9));
					} else {
						throw new Exception("Unexpected role " + rs.getString(7) + 
								"found in getScheduledSNRs()");
					}
					if ((prevSSS == null) || (!prevSSS.getSite().equals(sss.getSite())) ||
							(!prevSSS.getNRId().equals(sss.getNRId()))) {
						scheduledSNRs.add(sss);
						prevSSS = sss;
					} else {
						SNRScheduleSpreadsheet storedSSS = scheduledSNRs.get(scheduledSNRs.size()-1);
						storedSSS.addFieldEngineers(sss.getFieldEngineers());
						storedSSS.addBOEngineers(sss.getBOEngineers());
					}
				}
			}
			ok = true;
		} catch (SQLException e) {
			throw new Exception("calling GetScheduleForOutput(), " + e.getMessage());
		} finally {
			cstmt.close();
			conn.close();
		}
		return ok;
    }
	
    private void formatScheduledSNRs() 
        	throws Exception {
    	ArrayList<SNRScheduleSpreadsheet> todaysSNRs = new ArrayList<SNRScheduleSpreadsheet>();
    	formattedSNRs = new HashMap<GregorianCalendar, ArrayList<SNRScheduleSpreadsheet>>();
		workflowNames = new HashMap<String, Integer>();
		//SNRScheduleSpreadsheet prevSSS = null;
		java.sql.Date prevDate = null;
		String prevWorkflowName = null;
		int workflowNameCount = 0;
    	for (Iterator<SNRScheduleSpreadsheet> it = scheduledSNRs.iterator(); it.hasNext(); ) {
    		SNRScheduleSpreadsheet sss = it.next();

    		if ((prevDate == null) || (!prevDate.equals(sss.getScheduledDate())) ||
					(prevWorkflowName == null) || (!prevWorkflowName.equals(sss.getWorkflowName()))) {
    		//if (((prevDate != null) && (!prevDate.equals(sss.getScheduledDate()))) ||
				//	(prevWorkflowName == null) || (!prevWorkflowName.equals(sss.getWorkflowName()))) {
    			if (prevWorkflowName != null) {
					if (workflowNames.containsKey(prevWorkflowName)) {
						Integer thisCount = workflowNames.get(prevWorkflowName);
						if (workflowNameCount > thisCount.intValue()) {
							workflowNames.put(prevWorkflowName, workflowNameCount);
						}
					} else {
						workflowNames.put(prevWorkflowName,  workflowNameCount);
					}
    			}
    			
    			if ((prevDate != null) && (!prevDate.equals(sss.getScheduledDate()))) {
    				Calendar cal = Calendar.getInstance();
    				cal.setTime(new Date(prevDate.getTime()));
    				GregorianCalendar keyDate = new GregorianCalendar(cal.get(Calendar.YEAR), 
    						cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
    				formattedSNRs.put(keyDate, todaysSNRs);
    				todaysSNRs = new ArrayList<SNRScheduleSpreadsheet>();
    			}
    			
				prevDate = sss.getScheduledDate();
				prevWorkflowName = sss.getWorkflowName();
				workflowNameCount = sss.getRowCount();
    			addFormattedSNR(todaysSNRs, sss);
				
			} else {
				workflowNameCount += sss.getRowCount();
				addFormattedSNR(todaysSNRs, sss);
				/*ArrayList<BOEngineer> bes = sss.getBOEngineers();
				ArrayList<FieldEngineer> fes = sss.getFieldEngineers();
				for (int i = 0; i < sss.getRowCount(); i++) {
					String feName = "";
					int feRank = -1;
					String feVendor = "";
					String beName = "";
					if (i < fes.size()) {
						FieldEngineer fe = fes.get(i);
						feName = fe.getName();
						feRank = fe.getRank();
						feVendor = fe.getVendor();
					}
					if (i < bes.size()) {
						BOEngineer be = bes.get(i);
						beName = be.getName();
					}
					String commentary = i==0?sss.getCommentaryString():"";
					todaysSNRs.add(new SNRScheduleSpreadsheet(sss.getScheduledDate(), 
							sss.getWorkflowName(), sss.getSite(), sss.getNRId(), 
							sss.getUpgradeType(), commentary, feName, feRank,
							feVendor, beName));
				}*/
			}
    	}

    	if ((prevWorkflowName != null)) {
			if (workflowNames.containsKey(prevWorkflowName)) {
				Integer thisCount = workflowNames.get(prevWorkflowName);
				if (workflowNameCount > thisCount.intValue()) {
					workflowNames.put(prevWorkflowName, workflowNameCount);
				}
			} else {
				workflowNames.put(prevWorkflowName,  workflowNameCount);
			}
		}
    	
		if ((prevDate != null) /*&& (!prevDate.equals(sss.getScheduledDate()))*/) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(prevDate.getTime()));
			GregorianCalendar keyDate = new GregorianCalendar(cal.get(Calendar.YEAR), 
					cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
			formattedSNRs.put(keyDate, todaysSNRs);
			//todaysSNRs = new ArrayList<SNRScheduleSpreadsheet>();
		} 
    }

    private void addFormattedSNR(ArrayList<SNRScheduleSpreadsheet> todaysSNRs,
    		SNRScheduleSpreadsheet sss) {
		ArrayList<BOEngineer> bes = sss.getBOEngineers();
		ArrayList<FieldEngineer> fes = sss.getFieldEngineers();
		for (int i = 0; i < sss.getRowCount(); i++) {
			String feName = null;
			int feRank = -1;
			int beRank = -1;
			String feVendor = null;
			String beName = null;
			if (i < fes.size()) {
				FieldEngineer fe = fes.get(i);
				feName = fe.getName();
				feRank = fe.getRank();
				feVendor = fe.getVendor();
			}
			if (i < bes.size()) {
				BOEngineer be = bes.get(i);
				beName = be.getName();
				beRank = be.getRank();
			}
			String commentary = i==0?sss.getCommentaryString():"";
			todaysSNRs.add(new SNRScheduleSpreadsheet(sss.getScheduledDate(), 
					sss.getWorkflowName(), sss.getSite(), sss.getNRId(), 
					sss.getUpgradeType(), commentary, feName, feRank,
					feVendor, beName, beRank));
		}
    }
    
 	/*Integer red = 75;
    Integer green = 172;
    Integer blue = 198;


    Map<Integer, HSSFColor> m = HSSFColor.getIndexHash(); 
    Collection<HSSFColor> c = m.values();
    for (Iterator<HSSFColor> it = c.iterator(); it.hasNext(); ) {
    	HSSFColor h = it.next();
    	short[] t = h.getTriplet();
    	for (int i = 0; i < t.length; i++) {
    		short s = t[i];
    		String s2 = Short.toString(s);
    	}
    	if ((t[0] == 75) && (t[1] == 172) && (t[2] == 198)) {
    		short si = h.getIndex();
    		String s2 = Short.toString(si);
    	}
    	String st = t.toString();
    	String hS = h.getHexString();
    	Short x = h.getIndex();
    }
    Set<Integer> s = m.keySet();
    for (Iterator<Integer> it = s.iterator(); it.hasNext(); ) {
    	HSSFColor c = m.
    }
    
    byte[] rgb = {(byte)75, (byte)172, (byte)198};  
    XSSFColor xC = new XSSFColor(rgb);
    
    */

}
