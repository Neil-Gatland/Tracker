package com.devoteam.tracker.model;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.StringUtil;

public class SNRScheduleSpreadsheet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4416628145429452497L;
	private long snrId;
	private String status;
	private Date scheduledDate;
	private String workflowName;
	private String site;
	private String nrId;
	//private String jobType;
	private String upgradeType;
	private String commentary;
	private ArrayList<FieldEngineer> fieldEngineers;
	private String feName;
	private int feRank;
	private String feVendor;
	private ArrayList<BOEngineer> boEngineers;
	private ArrayList<String> problems;
	private String beName;
	private int beRank;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	private final static String[] snrTitles = {"Site", "NR Id", /*"Job Type", 
		"SNR Id",*/ "Upgrade Type", "Commentary", "Field Engineer", "No.", "Vendor", "BO Engineer", "No."};
	private final static int[] snrCellTypes = {Cell.CELL_TYPE_NUMERIC, Cell.CELL_TYPE_STRING, 
		/*Cell.CELL_TYPE_STRING, Cell.CELL_TYPE_NUMERIC,*/ Cell.CELL_TYPE_STRING, Cell.CELL_TYPE_STRING, 
		Cell.CELL_TYPE_STRING, Cell.CELL_TYPE_NUMERIC, Cell.CELL_TYPE_STRING, Cell.CELL_TYPE_STRING,
		Cell.CELL_TYPE_NUMERIC};
	private final static int[] columnWidths = {2304, 2560, /*"Job Type", 
		"SNR Id",*/ 4352, 3840, 4864, 2048, 2816, 4096, 2048};

	public SNRScheduleSpreadsheet(Row dataRow, int start, Date scheduledDate,
			String workflowName) {
		this.scheduledDate = scheduledDate;
		this.workflowName = workflowName;
		Cell cell = dataRow.getCell(start);
		this.site = cell.getCellType()==cell.CELL_TYPE_NUMERIC
				?Integer.toString((int)cell.getNumericCellValue())
				:cell.getStringCellValue().trim();
		this.nrId = dataRow.getCell(start+1).getStringCellValue().trim();
		//cell = dataRow.getCell(start+2);
		//this.jobType = cell==null?null:cell.getStringCellValue().trim();
		//this.snrId = (long) dataRow.getCell(start+3).getNumericCellValue();
		//cell = dataRow.getCell(start+4);
		//this.upgradeType = cell==null?null:cell.getStringCellValue().trim();
		this.upgradeType = getStringCellValue(dataRow.getCell(start+2));		
		//cell = dataRow.getCell(start+3);
		//this.commentary = cell==null?null:cell.getStringCellValue().trim();
		this.commentary = getStringCellValue(dataRow.getCell(start+3));		
		//cell = dataRow.getCell(start+4);
		//this.feName = cell==null?null:cell.getStringCellValue().trim();
		this.feName = getStringCellValue(dataRow.getCell(start+4));		
		/*cell = dataRow.getCell(start+5);
		this.feRank = cell==null?0:cell.getCellType()==cell.CELL_TYPE_NUMERIC
				?(int)cell.getNumericCellValue()
				:Integer.parseInt(cell.getStringCellValue().trim());*/
		this.feRank = getNumericCellValue(dataRow.getCell(start+5));		
		//cell = dataRow.getCell(start+6);
		//this.feVendor = cell==null?null:cell.getStringCellValue().trim();
		this.feVendor = getStringCellValue(dataRow.getCell(start+6));		
		//cell = dataRow.getCell(start+7);
		//this.beName = cell==null?null:cell.getStringCellValue().trim();
		this.beName = getStringCellValue(dataRow.getCell(start+7));
		this.beRank = getNumericCellValue(dataRow.getCell(start+8));
		fieldEngineers = new ArrayList<FieldEngineer>();
		boEngineers = new ArrayList<BOEngineer>();
		if (feName != null) {
			fieldEngineers.add(new FieldEngineer(feName, feRank, feVendor));
		}
		if (beName != null) {
			//boEngineers.add(new BOEngineer(beName));
			boEngineers.add(new BOEngineer(beName, beRank));
		}
		problems = new ArrayList<String>();
		snrId = -1;
	}
	
	private String getStringCellValue(Cell cell) {
		String value = null; 
		try {
			if (cell != null) {
				String temp = cell.getStringCellValue().trim();
				if ((temp!= null) && (!temp.isEmpty())) {
					value = temp;
				}
			}
		} catch (Exception ex) {
			//ignore - just return null
		}
		return value;
	}
	
	private int getNumericCellValue(Cell cell) {
		int value = 0; 
		try {
			if (cell != null) {
				if (cell.getCellType()==cell.CELL_TYPE_NUMERIC) {
					value = (int)cell.getNumericCellValue();
				} else {	
					String temp = cell.getStringCellValue().trim();
					if ((temp!= null) && (!temp.isEmpty())) {
						value = Integer.parseInt(temp);
					}
				}		
			}
		} catch (Exception ex) {
			//ignore - just return zero
		}
		return value;
	}

	public SNRScheduleSpreadsheet(Date scheduledDate, String workflowName, String site,
			String nrId, String upgradeType, String commentary) {
		this.scheduledDate = scheduledDate;
		this.workflowName = workflowName;
		this.site = site;
		this.nrId = nrId;
		this.upgradeType = upgradeType;
		this.commentary = commentary;
		fieldEngineers = new ArrayList<FieldEngineer>();
		boEngineers = new ArrayList<BOEngineer>();
		problems = new ArrayList<String>();
		snrId = -1;
	}

	public SNRScheduleSpreadsheet(Date scheduledDate, String workflowName, String site,
			String nrId, String upgradeType, String commentary, String feName, int feRank,
			String feVendor, String beName) {
		this.scheduledDate = scheduledDate;
		this.workflowName = workflowName;
		this.site = site;
		this.nrId = nrId;
		this.upgradeType = upgradeType;
		this.commentary = commentary;
		this.feName = feName;
		this.feRank = feRank;
		this.feVendor = feVendor;
		this.beName = beName;
		fieldEngineers = new ArrayList<FieldEngineer>();
		boEngineers = new ArrayList<BOEngineer>();
		problems = new ArrayList<String>();
		snrId = -1;
	}

	public SNRScheduleSpreadsheet(Date scheduledDate, String workflowName, String site,
			String nrId, String upgradeType, String commentary, String feName, int feRank,
			String feVendor, String beName, int beRank) {
		this.scheduledDate = scheduledDate;
		this.workflowName = workflowName;
		this.site = site;
		this.nrId = nrId;
		this.upgradeType = upgradeType;
		this.commentary = commentary;
		this.feName = feName;
		this.feRank = feRank;
		this.feVendor = feVendor;
		this.beName = beName;
		this.beRank = beRank;
		fieldEngineers = new ArrayList<FieldEngineer>();
		boEngineers = new ArrayList<BOEngineer>();
		problems = new ArrayList<String>();
		snrId = -1;
	}
	
	public long getSNRId() {
		return snrId;
	}
	
	public void setSNRId(long snrId) {
		this.snrId = snrId;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getStatusDisplay() {
		return StringUtil.hasNoValue(status)?"unknown":status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean canSchedule() {
		return ((!StringUtil.hasNoValue(status)) &&
				((status.equalsIgnoreCase(ServletConstants.STATUS_AWAITING_SCHEDULING)) ||
						(status.equalsIgnoreCase(ServletConstants.STATUS_SCHEDULED)) ||
						(status.equalsIgnoreCase(ServletConstants.STATUS_COMPLETED))
				));
	}
	
	public Date getScheduledDate() {
		return scheduledDate;
	}
	
	public String getScheduledDateString() {
		return scheduledDate==null?"":dateFormatter.format(scheduledDate);
	}
	
	public String getWorkflowName() {
		return workflowName;
	}
	
	public String getSite() {
		return site;
	}
	
	public String getNRId() {
		return nrId;
	}
	
	/*public String getJobType() {
		return jobType;
	}*/
	
	public String getUpgradeType() {
		return upgradeType;
	}
	
	public String getUpgradeTypeString() {
		return upgradeType==null?"":upgradeType;
	}
	
	public String getCommentary() {
		return commentary;
	}
	
	public String getCommentaryString() {
		return commentary==null?"":commentary;
	}
	
	public void addFieldEngineers(ArrayList<FieldEngineer> fieldEngineers) {
		this.fieldEngineers.addAll(fieldEngineers);
	}
	
	public void addFieldEngineer(String fieldEngineer, int rank, String vendor) {
		fieldEngineers.add(new FieldEngineer(fieldEngineer, rank, vendor));
	}
	
	public ArrayList<FieldEngineer> getFieldEngineers() {
		return fieldEngineers;
	}
	
	public void addBOEngineers(ArrayList<BOEngineer> boEngineers) {
		this.boEngineers.addAll(boEngineers);
	}
	
	/*public void addBOEngineer(String boEngineer) {
		boEngineers.add(new BOEngineer(boEngineer));
	}*/
	
	public void addBOEngineer(String boEngineer, int rank) {
		boEngineers.add(new BOEngineer(boEngineer, rank));
	}
	
	public ArrayList<BOEngineer> getBOEngineers() {
		return boEngineers;
	}
	
	public ArrayList<String> getProblems() {
		return problems;
	}
	
	public void addProblem(String problem) {
		problems.add(problem);
	}
	
	public static String[] getSNRTitles() {
		return snrTitles;
	}
	
	public static int[] getColumnWidths() {
		return columnWidths;
	}
	
	public String[] getSpreadsheetValues() {
		/*String[] spreadsheetValues = {site, nrId, this.getUpgradeTypeString(), 
				this.getCommentaryString(), this.getFENameString(), this.getFERankString(), 
				this.getFEVendorString(), this.getBENameString()};*/
		String[] spreadsheetValues = {site, nrId, upgradeType, 
				commentary, feName, this.getFERankString(), 
				feVendor, beName, this.getBERankString()};
		return spreadsheetValues;
	}
	
	public int getRowCount() {
		int rowCount = fieldEngineers.size()>boEngineers.size()?fieldEngineers.size():boEngineers.size();
		return rowCount<1?1:rowCount;
	}
	
	public String getFEName() {
		return feName;
	}
	
	public String getFENameString() {
		return feName==null?"":feName;
	}
	
	public int getFERank() {
		return feRank;
	}
	
	public String getFERankString() {
		return feRank<1?null:Integer.toString(feRank);
	}
	
	public String getFEVendor() {
		return feVendor;
	}
	
	public String getFEVendorString() {
		return feVendor==null?"":feVendor;
	}
	
	public String getBEName() {
		return beName;
	}
	
	public String getBENameString() {
		return beName==null?"":beName;
	}
	
	public String getBERankString() {
		return beRank<1?null:Integer.toString(beRank);
	}
}
