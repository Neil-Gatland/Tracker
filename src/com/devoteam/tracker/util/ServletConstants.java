package com.devoteam.tracker.util;

/**
 * Constants used by servlets.
 *
 * <P>This includes name constants for storing attributes in sessions and requests
 *    as well as the path prefix for static data. </P>
 *
 * <P>Copyright (c) 2014  Devoteam</P>
 */

public interface ServletConstants {
	final static public String USER_MESSAGE_NAME_IN_SESSION = "userMessage";
	final static public String USER_OBJECT_NAME_IN_SESSION = "userObject";
	final static public String DB_CONNECTION_URL_IN_SESSION = "dbConnectURL";
	final static public String SCREEN_TITLE_IN_SESSION = "screenTitle";
	final static public String POT_SPREADSHEET_NAME_IN_SESSION = "potSpreadsheet";
	final static public String POT_SPREADSHEET_COPY_NAME_IN_SESSION = "potSpreadsheetCopy";
	final static public String PROBLEM_ARRAY_NAME_IN_SESSION = "problems";
	final static public String WARNING_ARRAY_NAME_IN_SESSION = "warnings";
	final static public String SCHEDULE_SPREADSHEET_NAME_IN_SESSION = "scheduleSpreadsheet";
	final static public String SCHEDULE_SPREADSHEET_COPY_NAME_IN_SESSION = "scheduleSpreadsheetCopy";
	final static public String PRECHECK_ITEM_COLLECTION_NAME_IN_SESSION = "preCheckItems";
	final static public String PRECHECK_COLLECTION_NAME_IN_SESSION = "preChecks";
	final static public String USER_ROLE_COLLECTION_NAME_IN_SESSION = "userRoles";
	final static public String SNR_TECHNOLOGY_COLLECTION_NAME_IN_SESSION = "snrTechnologyList";
	final static public String SNR_BO_TECHNOLOGY_ADD_COLLECTION_NAME_IN_SESSION = "snrBOTechnologyAddList";
	final static public String SNR_BO_TECHNOLOGY_DEL_COLLECTION_NAME_IN_SESSION = "snrBOTechnologyDelList";
	final static public String SNR_SCHEDULED_DATE_IN_SESSION = "snrScheduledDate";
	final static public String SCHEDULED_SNRS_IN_SESSION = "scheduledSNRs";
	final static public String INVALID_SNRS_IN_SESSION = "invalidSNRs";
	final static public String SCHEDULED_SNR_WARNINGS_IN_SESSION = "scheduledSNRWarnings";
	final static public String SCHEDULE_SNR_FILE_NAME_IN_SESSION = "scheduleSNRFileName";
	final static public String HOME = "Home";
	final static public String LOG_OFF = "Log Off";
	final static public String CHANGE_PASSWORD = "Change Password";
	final static public String VIEW_SNR_HISTORY = "View NR History";
	final static public String VIEW_SNR_HISTORY_SHORT = "View NR";
	final static public String REPORTING = "Reports";
	final static public String CUSTOMER_MENU = "Customer Menu";
	final static public String JOB_TYPE_MAINTENANCE = "Job Type Maintenance";
	final static public String JOB_TYPE_MAINTENANCE_SHORT = "Job Type";
	final static public String USER_ADMINISTRATION = "User Administration";
	final static public String USER_ADMINISTRATION_SHORT = "User";
	final static public String SCHEDULING = "Scheduling (old)";
	final static public String OUTPUT_SCHEDULE = "Output Schedule";
	final static public String PRE_CHECK_MAINTENANCE = "Pre-Check Maintenance";
	final static public String PRE_CHECK_MAINTENANCE_SHORT = "Pre-Check";
	final static public String UPDATE_ACCESS = "Update Access";
	final static public String UPDATE_CRM = "Update CRQ/INC";
	final static public String CONFIRM_IMPLEMENTATION = "Confirm Implementation";
	final static public String CONFIRM_IMPLEMENTATION_SHORT = "Confirm";
	final static public String LOAD_SITE_CONFIGURATION = "Load Site Configuration";
	final static public String LOAD_SITE_CONFIGURATION_SHORT = "Load";
	final static public String RESCHED_REALLOC_CANCEL_SNR = "Cancel";
	final static public String RESCHED_REALLOC_CANCEL_SNR_SHORT = "Cancel";
	final static public String WORK_QUEUES = "Work Queues";
	final static public String LOAD_POT = "Load Pot";
	final static public String MANUAL_POT_CREATION = "Manual Pot Creation";
	final static public String SCHEDULE_SNR = "Schedule NR";
	final static public String PRE_CHECK_STATUS_NOT_STARTED = "Not Started";
	final static public String PRE_CHECK_STATUS_IN_PROGRESS = "In Progress";
	final static public String PRE_CHECK_TYPE_FINAL = "Final";
	final static public String PRE_CHECK_TYPE_DETAILED = "Detailed";
	final static public String STATUS_REQUESTED = "Requested";
	final static public String STATUS_REJECTED = "Rejected";
	final static public String STATUS_SCHEDULED = "Scheduled";
	final static public String STATUS_PERFORMANCE_IP = "Performance IP";
	final static public String STATUS_COMPLETED = "Completed";
	final static public String STATUS_CLOSED = "Closed";
	final static public String STATUS_CANCELLED = "Cancelled";
	final static public String STATUS_AWAITING_SCHEDULING = "Awaiting Scheduling";
	final static public String CHOOSE_REPORT = "Choose Report";
	final static public String NR_PROGRESS_REPORT_SCREEN = "viewNRProgress.jsp";
	final static public String NR_TOTALS_REPORT_SCREEN = "viewNRTotals.jsp";
	final static public String SNR_PROGRESS_REPORT_ITEMS_IN_SESSION = "snrProgressReportItems";
	final static public String SNR_TOTALS_REPORT_ITEMS_IN_SESSION = "snrTotalsReportItems";
	final static public String DOWNLOAD_REPORT_PARAMETERS_IN_SESSION = "downloadReportParameters";
	final static public String SYSTEM_PARAMETERS_IN_SESSION = "systemParameters";
	final static public String REOPEN_CANCELLED_SNR_SHORT = "Reopen";
	final static public String REOPEN_CANCELLED_SNR = "Reopen Cancelled NR";
	final static public String HOME_BO = "Home (BO)";
	final static public String EXPANDED = "Expanded Home";
	final static public String EXPANDED_SHORT = "Expanded";
	final static public String BO = "BO Home";
	final static public String BO_SHORT = "BO";
	final static public String PMO = "PMO";
	final static public String VIEW_ACCESS_DETAIL = "View Access Detail";
	final static public String VIEW_COMMENTARY_DETAIL = "View Commentary Detail";
	final static public String LIVE_DASHBOARD = "Live Dashboard";
	final static public String SITE_PROGRESS = "Site Progress";
	final static public String SITE_SEARCH = "Site Search";
	final static public String CLIENT_REPORTING = "Reporting";
	final static public String HOME_FE = "Home (FE)";
	final static public String SCHEDULE_VIEW = "Scheduling";
	final static public String MISSING_DATA = "Missing Data";
	final static public String POT_HEADER_IN_SESSION = "PotHeader";
	final static public String POT_DETAIL_ARRAY_IN_SESSION = "PotDetailArray";
	final static public String DATA_ANALYTICS ="Analytics";
	final static public String BACK_OFFICE = "Back Office";

}
