<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ include file="headerLD.jsp" %>
<%
BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();	
String year = request.getAttribute("year")==null?"":(String)request.getAttribute("year");
String initialEntry = request.getAttribute("initialEntry")==null?"Y":(String)request.getAttribute("initialEntry");
year = session.getAttribute("year")==null?year:(String)session.getAttribute("year");
String month = request.getAttribute("month")==null?"":(String)request.getAttribute("month");
month = session.getAttribute("month")==null?month:(String)session.getAttribute("month");
String action = request.getAttribute("action")==null?"initialise":(String)request.getAttribute("action");
action = session.getAttribute("action")==null?action:(String)session.getAttribute("action");
String day = request.getAttribute("day")==null?"":(String)request.getAttribute("day");
day = session.getAttribute("day")==null?day:(String)session.getAttribute("day");
String week = request.getAttribute("week")==null?"":(String)request.getAttribute("week");
week = session.getAttribute("week")==null?year:(String)session.getAttribute("week");
String startDate = request.getAttribute("startDate")==null?"":(String)request.getAttribute("startDate");
startDate = session.getAttribute("startDate")==null?startDate:(String)session.getAttribute("startDate");
String endDate = request.getAttribute("endDate")==null?"":(String)request.getAttribute("endDate");
endDate = session.getAttribute("endDate")==null?endDate:(String)session.getAttribute("endDate");
String project = request.getAttribute("project")==null?"":(String)request.getAttribute("project");
String upgradeType = request.getAttribute("upgradeType")==null?"":(String)request.getAttribute("upgradeType");
String siteStatus = request.getAttribute("siteStatus")==null?"":(String)request.getAttribute("siteStatus");
siteStatus = session.getAttribute("siteStatus")==null?siteStatus:(String)session.getAttribute("siteStatus");
String site = request.getAttribute("site")==null?"":(String)request.getAttribute("site");
site = session.getAttribute("site")==null?site:(String)session.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"":(String)request.getAttribute("nrId");
nrId = session.getAttribute("nrId")==null?nrId:(String)session.getAttribute("nrId");
String showTH = request.getAttribute("showTH")==null?"Y":(String)request.getAttribute("showTH");
String snrId = request.getAttribute("snrId")==null?"":(String)request.getAttribute("snrId");
String rescheduleMessage = request.getAttribute("rescheduleMessage")==null?"":(String)request.getAttribute("rescheduleMessage");
String rescheduleAction = request.getAttribute("rescheduleAction")==null?"":(String)request.getAttribute("rescheduleAction");
String scheduledDate = request.getAttribute("scheduledDate")==null?"":(String)request.getAttribute("scheduledDate");
String column = request.getAttribute("column")==null?"":(String)request.getAttribute("column");
String userId = request.getAttribute("userId")==null?"":(String)request.getAttribute("userId");
String role = request.getAttribute("role")==null?"":(String)request.getAttribute("role");
String displayAddRole = request.getAttribute("displayAddRole")==null?"none":(String)request.getAttribute("displayAddRole");
String displayChgRole = request.getAttribute("displayChgRole")==null?"none":(String)request.getAttribute("displayChgRole");
String rank = request.getAttribute("rank")==null?"none":(String)request.getAttribute("rank");
String storedRank = request.getAttribute("storedRank")==null?"none":(String)request.getAttribute("storedRank");
String row = request.getAttribute("row")==null?"none":(String)request.getAttribute("row");
String currentValue = request.getAttribute("currentValue")==null?"":(String)request.getAttribute("currentValue");
String updatedValue = request.getAttribute("updatedValue")==null?"":(String)request.getAttribute("updatedValue");
String selectedSiteList = request.getAttribute("selectedSiteList")==null?"":(String)request.getAttribute("selectedSiteList");
String formattedSiteList = request.getAttribute("formattedSiteList")==null?"":(String)request.getAttribute("formattedSiteList");
String multiSiteEdit = request.getAttribute("multiSiteEdit")==null?"N":(String)request.getAttribute("multiSiteEdit");
String potLoadActive = session.getAttribute("potLoadActive")==null?"N":(String)session.getAttribute("potLoadActive");
String potFileName = session.getAttribute("potFileName")==null?"N":(String)session.getAttribute("potFileName");
boolean problemsFound =  session.getAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION)!=null;
boolean warningsFound =  session.getAttribute(ServletConstants.WARNING_ARRAY_NAME_IN_SESSION)!=null;
String problems = null;
String warnings = null;
boolean errorsFound = false;;
if (problemsFound) {
	problems = (String)session.getAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION);
	session.removeAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION);
	errorsFound = true;
}
if (warningsFound) {
	warnings = (String)session.getAttribute(ServletConstants.WARNING_ARRAY_NAME_IN_SESSION);
	session.removeAttribute(ServletConstants.WARNING_ARRAY_NAME_IN_SESSION);
}
String canConfirmPot = session.getAttribute("canConfirmPot")==null?"N":(String)session.getAttribute("canConfirmPot");
String nrIdOrSite = request.getAttribute("nrIdOrSite")==null?"":(String)request.getAttribute("nrIdOrSite");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="scheduleView.jsp"/>
<input type="hidden" name="initialEntry" id="initialEntry" value="<%=initialEntry%>"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.SCHEDULE_VIEW%>"/>
<input type="hidden" name="selectedAction" id="selectedAction" value="<%=action%>"/>
<input type="hidden" name="selectedYear" id="selectedYear" value="<%=year%>"/>
<input type="hidden" name="selectedMonth" id="selectedMonth" value="<%=month%>"/>
<input type="hidden" name="selectedDay" id="selectedDay" value="<%=day%>"/>
<input type="hidden" name="selectedWeek" id="selectedWeek" value="<%=week%>"/>
<input type="hidden" name="selectedSite" id="selectedSite" value="<%=site%>"/>
<input type="hidden" name="selectedNrId" id="selectedNrId" value="<%=nrId%>"/>
<input type="hidden" name="selectedStartDate" id="selectedStartDate" value="<%=startDate%>"/>
<input type="hidden" name="selectedEndDate" id="selectedEndDate" value="<%=endDate%>"/>
<input type="hidden" name="moveDate" id="moveDate" value=""/>
<input type="hidden" name="showTH" id="showTH" value="<%=showTH%>"/>
<input type="hidden" name="snrId" id="snrId" value=""/>
<input type="hidden" name="rescheduleMessage" id="rescheduleMessage" value="<%=rescheduleMessage%>"/>
<input type="hidden" name="rescheduleAction" id="rescheduleAction" value="<%=rescheduleAction%>"/>
<input type="hidden" name="scheduledDate" id="scheduledDate" value="<%=scheduledDate%>"/>
<input type="hidden" name="rescheduledDate" id="rescheduledDate" value=""/>
<input type="hidden" name="column" id="column" value="<%=column%>"/>
<input type="hidden" name="userId" id="userId" value="<%=userId%>"/>
<input type="hidden" name="role" id="role" value="<%=role%>"/>
<input type="hidden" name="displayAddRole" id="displayAddRole" value="<%=displayAddRole%>"/>
<input type="hidden" name="displayChgRole" id="displayChgRole" value="<%=displayChgRole%>"/>
<input type="hidden" name="rank" id="rank" value="<%=rank%>"/>
<input type="hidden" name="storedRank" id="storedRank" value="<%=storedRank%>"/>
<input type="hidden" name="row" id="row" value="<%=row%>"/>
<input type="hidden" name="currentValue" id="currentValue" value="<%=currentValue%>"/>
<input type="hidden" name="updatedValue" id="updatedValue" value="<%=updatedValue%>"/>
<input type="hidden" name="selectedSiteList" id="selectedSiteList" value="<%=selectedSiteList%>"/>
<input type="hidden" name="multiSiteEdit" id="multiSiteEdit" value="<%=multiSiteEdit%>"/>
<input type="hidden" name="editList" id="editList" value=""/>
<input type="hidden" name="nrIdOrSite" id ="nrIdOrSite" value="<%=nrIdOrSite%>"/>
<%@ page import="com.devoteam.tracker.util.DateSearch"%>
<%
	DateSearch dS = new DateSearch(url);
	dS.update(year, month, action);
%>
<script language="javascript">

var editCount = 0;
var maxEditCount = <%=uB.getMaxScheduleEdits()%>;

function thisScreenLoad() {	
	if ("<%=potLoadActive%>"=="Y") {
		var header = document.getElementById("m01");
		var position = getPosition(header);
		var aR = document.getElementById("potLoad");
		aR.style.display = "inline";
		aR.style.left = (position.x + 30) + "px";
		aR.style.top = (position.y + 30) + "px";
		aR.style.zIndex = "30";	
		document.getElementById("fileNameDisplay").innerHTML = "<%=potFileName%>";
		document.getElementById("potFileName").value = "";
		document.getElementById("select").disabled = false;
	}
	if ("<%=potLoadActive%>"=="C") {
		var pL = document.getElementById("potLoad");
		pL.style.display = "none";
		pL.style.left = "0px";
		pL.style.top = "0px";	
		document.getElementById("fileNameDisplay").innerHTML = "";
		document.getElementById("potFileName").value = "";
		document.getElementById("validate").style.display = "none";
		document.getElementById("cancelValidate").style.display = "none";
		document.getElementById("confirm").style.display = "none";
		document.getElementById("confirmValidate").style.display = "none";
	}
	var snrId = "<%=snrId%>";
	if (!snrId=="") {
		if ("<%=column%>"=='all') {
			var header = document.getElementById("m01");
			var position = getPosition(header);
			var aR = document.getElementById("reschedule");
			aR.style.display = "inline";
			aR.style.left = position.x + "px";
			aR.style.top = position.y + "px";
			aR.style.zIndex = "10";	
			document.getElementById("displayAddRole").value="none";
			document.getElementById("displayChgRole").value="none";
		}	
	}
	var rescheduleAction = document.getElementById("rescheduleAction").value;
	if ((rescheduleAction=="updateSiteboEngineer")||(rescheduleAction=="updateSitefeEngineer")) {
		var column = document.getElementById("column").value;
		var snrId = document.getElementById("snrId").value;
		var row = document.getElementById("row").value;
		var header = document.getElementById(column+row);
		// IE specifc code!
		var isIE = /*@cc_on!@*/false || !!document.documentMode;
		if (isIE) {
			if (rescheduleAction=="updateSiteboEngineer") {
				header = document.getElementById("boHdr");
			} else {
				header = document.getElementById("feHdr");
			}
		}
		var position = getPositionNoOffset(header);
		var aR = document.getElementById("updateSite");
		aR.style.display = "inline";
		aR.style.width = "200px";
		aR.style.height = "25px";
		aR.style.left = position.x + "px";
		aR.style.top = ( position.y - 15 ) + "px";
		aR.style.zIndex = "20";	
		document.getElementById("updHardwareVendor").style.display = "none"; 
		document.getElementById("updFeEngineer").style.display = "none"; 
		document.getElementById("updBoEngineer").style.display = "none"; 
		document.getElementById("updUpgradeType").style.display = "none"; 
		document.getElementById("updProject").style.display = "none"; 
		document.getElementById("updScheduledDate").style.display = "none";
		document.getElementById("updSiteDets").value = "Site: <%=nrIdOrSite%>";		
		if (column=="feEngineer") {
			document.getElementById("updFeEngineer").style.display = "inline";
		} else if (column=="boEngineer") {
			document.getElementById("updBoEngineer").style.display = "inline"; 
		}
	}
	document.getElementById("selectedAction").value = "<%=action%>";
	document.getElementById("selectedYear").value = "<%=year%>";
	document.getElementById("selectedMonth").value = "<%=month%>";
	document.getElementById("selectedDay").value = "<%=day%>";
	document.getElementById("selectedWeek").value = "<%=week%>";
	document.getElementById("selectEmailCopyProject").value = "<%=project%>";
	document.getElementById("selectEmailCopyUpgradeType").value = "<%=upgradeType%>";
	document.getElementById("selectEmailCopySite").value = "<%=site%>";
	document.getElementById("selectEmailCopyNRId").value = "<%=nrId%>";
	document.getElementById("selectSiteStatus").value = "<%=siteStatus%>";
	var testSDate = document.getElementById("sDate").value;
	var testEDate = document.getElementById("eDate").value;
	if ("<%=initialEntry%>"=="Y") {
		var tDate = new Date();
		var month = tDate.getMonth()+1;
		if (month<10) {
			month='0'+month;
		}
		var day = tDate.getDate();
		if ((day=='1')||(day=='2')||(day=='3')||(day=='4')||(day=='5')||
				(day=='6')||(day=='7')||(day=='8')||(day=='9')) {
			day = '0' + day;
		}
		var today = day+'/'+month+'/'+tDate.getFullYear();
		document.getElementById("sDate").value = today;
		document.getElementById("eDate").value = today;
		document.getElementById("initialEntry").value = "N";
		document.getElementById("selectedStartDate").value = today;
		document.getElementById("selectedEndDate").value = today;
	}
	var siteStatus = document.getElementById("selectSiteStatus").value;
	if (siteStatus=='') {
		document.getElementById("selectSiteStatus").value = 'Scheduled';
	}
	var showTH = document.getElementById("showTH").value;
	if (showTH=='N') {
		document.getElementById("TH").style.display = "none";
		document.getElementById("siteList").style.height = "530px";
	} else {
		document.getElementById("TH").style.display = "inline";
		document.getElementById("siteList").style.height = "255px";
	}
	<%	if (thisU.hasUserRole(UserRole.ROLE_SCHEDULER)) { %>
	document.getElementById("loadPot").style.display = "inline";
	document.getElementById("missingData").style.display = "inline";
	document.getElementById("action").style.display = "inline";
	<%} else {%>
	document.getElementById("loadPot").style.display = "none";
	document.getElementById("missingData").style.display = "none";
	document.getElementById("action").style.display = "none";
	<%}%>
}

function clickSearchBox(operation) {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	document.getElementById("selectedAction").value = operation;
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = "<%=dS.getMonth()%>";
	document.getElementById("selectedSiteList").value = "";
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();
}

function tbClick(btn) {
	if (btn == "missingData") {
		document.getElementById("toScreen").value = "<%=ServletConstants.MISSING_DATA%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	} else if (btn == "loadPot") {
		var header = document.getElementById("m01");
		var position = getPosition(header);
		var aR = document.getElementById("potLoad");
		aR.style.display = "inline";
		aR.style.left = (position.x + 30) + "px";
		aR.style.top = (position.y + 30) + "px";
		aR.style.zIndex = "30";	
		document.getElementById("fileNameDisplay").value = "";
		document.getElementById("potFileName").value = "";
		document.getElementById("select").disabled = false;
		document.getElementById("plErrors").style.display = "none";
		document.getElementById("plWarnings").style.display = "none";
	}
}

function dateAction(operation,year,month,day,week) {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	document.getElementById("selectedAction").value = operation;
	document.getElementById("selectedYear").value = year;
	document.getElementById("selectedMonth").value = month;
	document.getElementById("selectedDay").value = day;
	document.getElementById("selectedWeek").value = week;
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();
}

function moveDate(operation) {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	document.getElementById("moveDate").value = operation;
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();
}

function moveMonth(newMonth) {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	document.getElementById("selectedAction").value = 'chgMonth';
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = newMonth;
	document.getElementById("selectedDay").value = '00';
	document.getElementById("selectedWeek").value = '00';
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();		
}

function search(operation) {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	var project = document.getElementById("selectEmailCopyProject").value;
	var upgradeType = document.getElementById("selectEmailCopyUpgradeType").value;
	var site = document.getElementById("selectEmailCopySite").value;
	var nrId = document.getElementById("selectEmailCopyNRId").value;
	var siteStatus = document.getElementById("selectSiteStatus").value;
	var startDate = document.getElementById("sDate").value;
	var endDate = document.getElementById("eDate").value;
	if (!((site=='')||(nrId==''))) {
		alert('Only site or nr id can selected');
		return;
	}
	document.getElementById("selectedAction").value = "search";
	document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
	document.getElementById("selectedEndDate").value = document.getElementById("eDate").value;
	document.getElementById("selectedSiteList").value = "";
	document.getElementById("snrId").value = "";
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();		
}

function multiSearch(operation) {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	var sites = document.getElementById("siteList").value;
	if (sites=="") {
		alert('No sites selected for multiple site search');
		return;
	}
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = "<%=dS.getMonth()%>";
	document.getElementById("selectedAction").value = "multiSearch";
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();	
}

function clearSearchCriteria() {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	document.getElementById("selectEmailCopyProject").value = "";
	document.getElementById("selectEmailCopyUpgradeType").value = "";	
	document.getElementById("selectEmailCopySite").value = "";
	document.getElementById("selectEmailCopyNRId").value = "";
	var tDate = new Date();
	var month = tDate.getMonth()+1;
	if (month<10) {
		month='0'+month;
	}
	var today = tDate.getDate()+'/'+month+'/'+tDate.getFullYear();
	document.getElementById("sDate").value = today;
	document.getElementById("eDate").value = today;
	document.getElementById("selectSiteStatus").value = 'Scheduled';
	document.getElementById("selectedAction").value = "search";
	document.getElementById("sDate").value = "";
	document.getElementById("eDate").value = "";
	document.getElementById("selectedStartDate").value = "";
	document.getElementById("selectedEndDate").value = "";
	document.getElementById("selectedSiteList").value = "";
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();		
}

function clearMultiSearchCriteria() {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	document.getElementById("siteList").value = "";
	document.getElementById("selectedSiteList").value = "";
}

function navigationAction(operation) {
	if (operation=='hide') {
		document.getElementById("TH").style.display = "none";
		document.getElementById("siteList").style.height = "550px";
		document.getElementById("showTH").value = "N";
	} else {
		document.getElementById("TH").style.display = "inline";
		document.getElementById("siteList").style.height = "275px";
		document.getElementById("showTH").value = "Y";
	}
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();		
}

function updateSite(snrId,currentValue,column,row,nrIdOrSite) {	
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	var siteDets = "Site: "+ nrIdOrSite;
	if (multiSiteEdit=="Y") {
		return;
	}
	var updateSuffix = column;
	if ((column=='vodafone2G') ||
		(column=='vodafone3G') ||
		(column=='vodafone4G') ||
		(column=='tef2G') ||			
		(column=='tef3G') ||			
		(column=='tef4G') ||			
		(column=='paknetPaging') ||			
		(column=='secGWChange') ||		
		(column=='power') ||		
		(column=='other') ||		
		(column=='survey')) {
		var columnDesc = 'Unknown';
		updateSuffix = 'Technology';
		if (column=='vodafone2G') { columnDesc='Vodafone 2G'}
		if (column=='vodafone3G') { columnDesc='Vodafone 3G'}
		if (column=='vodafone4G') { columnDesc='Vodafone 4G'}
		if (column=='tef2G') { columnDesc='TEF 2G'}
		if (column=='tef3G') { columnDesc='TEF 3G'}
		if (column=='tef4G') { columnDesc='TEF 4G'}
		if (column=='paknetPaging') { columnDesc='Paknet and Paging'}
		if (column=='secGWChange') { columnDesc='SecGW Change'}
		if (column=='power') { columnDesc='Power'}
		if (column=='survey') { columnDesc='Survey'}
		if (column=='other') { columnDesc='Other'}
	}
	document.getElementById("snrId").value = snrId;
	document.getElementById("column").value = column;
	document.getElementById("row").value = row;
	document.getElementById("currentValue").value = currentValue;
	document.getElementById("rescheduleAction").value = "updateSite"+updateSuffix;			
	document.getElementById("selectedAction").value = "search";
	document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
	document.getElementById("selectedEndDate").value = document.getElementById("eDate").value; 
	document.getElementById("updSiteDets").value = siteDets; 
	document.getElementById("nrIdOrSite").value = nrIdOrSite; 
	if (updateSuffix=="Technology") {
		document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
		document.getElementById("f1").action = "scheduleView";
		document.getElementById("f1").submit();	
	} else {
		var isIE = /*@cc_on!@*/false || !!document.documentMode;
		if (!((column=="feEngineer")||(column=="boEngineer"))) {
			var header = document.getElementById(column+row);
			// IE specific code!
			if (isIE) {
				header = document.getElementById(column+"Hdr");
				if (column=="scheduledDate") { 
					header = document.getElementById("schDateHdr");
				}
			}
			var position = getPositionNoOffset(header);
			var aR = document.getElementById("updateSite");
			aR.style.display = "inline";
			if (column=="upgradeType") {
				aR.style.width = "120px";
			} else if (column=="scheduledDate") {
				aR.style.width = "150px";
			} else {
				aR.style.width = "200px";
			}
			aR.style.height = "25px";
			aR.style.left = position.x + "px";
			aR.style.top = ( position.y - 15 ) + "px";
			aR.style.zIndex = "20";	 
			document.getElementById("updFeEngineer").style.display = "none"; 
			document.getElementById("updBoEngineer").style.display = "none"; 
			document.getElementById("updHardwareVendor").style.display = "none"; 
			document.getElementById("updUpgradeType").style.display = "none"; 
			document.getElementById("updProject").style.display = "none"; 
			document.getElementById("updScheduledDate").style.display = "none"; 
		}
		if (column=='hardwareVendor') {
			document.getElementById("updHardwareVendor").style.display = "inline"; 
			document.getElementById("newHardwareVendor").value = currentValue;
			document.getElementById("hardwareVendor"+row).value ="xxxx";
		} else if (column=="feEngineer") {
			document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
			document.getElementById("f1").action = "scheduleView";
			document.getElementById("f1").submit(); 
		} else if (column=="boEngineer") {
			document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
			document.getElementById("f1").action = "scheduleView";
			document.getElementById("f1").submit(); 
		} else if (column=="upgradeType") {
			document.getElementById("updUpgradeType").style.display = "inline";  
			document.getElementById("newUpgradeType").value = currentValue; 
		} else if (column=="project") {
			document.getElementById("updProject").style.display = "inline"; 
		} else if (column=="scheduledDate") {
			document.getElementById("updScheduledDate").style.display = "inline";  
			document.getElementById("newScheduledDate").value = currentValue; 
		}
	}
}

function rescheduleSelect(snrId,scheduledDate,siteStatus,nrIdOrSite) {
	document.getElementById("rescheduleSite").style.display = "inline";
	document.getElementById("snrId").value = snrId;
	document.getElementById("column").value = "all";
	document.getElementById("rescheduleMessage").value = "";
	document.getElementById("nrIdOrSite").value = nrIdOrSite;
	document.getElementById("selectedAction").value = "search";
	if (siteStatus=="Scheduled") {
		document.getElementById("cancelScheduleSite").style.display = "inline";
	}
	if (siteStatus=="Awaiting Scheduling") {
		document.getElementById("cancelSite").style.display = "inline";
	}
}

function openReschedule() {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();	
}

function cancelSite() {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	var nrIdOrSite = document.getElementById("nrIdOrSite").value;
	if (!confirm("Please confirm cancellation of site "+nrIdOrSite+".")) {
		return;
	}	
	document.getElementById("rescheduleAction").value = "cancelSite";
	document.getElementById("selectedAction").value = "search";
	document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
	document.getElementById("selectedEndDate").value = document.getElementById("eDate").value;
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();
}

function cancelScheduleSite() {
	// ignore if there is a pending multi-site edit
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="Y") {
		return;
	}
	var nrIdOrSite = document.getElementById("nrIdOrSite").value;
	if (!confirm("Please confirm removal from schedule of site "+nrIdOrSite+".")) {
		return;
	}	
	document.getElementById("rescheduleAction").value = "cancelScheduleDirect";
	document.getElementById("selectedAction").value = "search";
	document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
	document.getElementById("selectedEndDate").value = document.getElementById("eDate").value;
	document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
	document.getElementById("f1").action = "scheduleView";
	document.getElementById("f1").submit();
}

function multiSiteEdit() {
	var multiSiteEdit = document.getElementById("multiSiteEdit").value;
	if (multiSiteEdit=="N") {
		// ignore if any single edit is open 
		var rescheduleDisplay = document.getElementById("reschedule").style.display;
		var updScheduledDateDisplay = document.getElementById("updScheduledDate").style.display;
		var updProjectDisplay = document.getElementById("updProject").style.display;
		var updUpgradeTypeDisplay = document.getElementById("updUpgradeType").style.display;
		var updBoEngineerDisplay = document.getElementById("updBoEngineer").style.display;
		var updFeEngineerDisplay = document.getElementById("updFeEngineer").style.display;
		var updHardwareVendorDisplay = document.getElementById("updHardwareVendor").style.display;
		var updateSiteDisplay = document.getElementById("updateSite").style.display;
		if (rescheduleDisplay=="inline") {
			return;
		}
		if ((updateSiteDisplay=="inline") &&
				(	(updScheduledDateDisplay=="inline") ||
					(updProjectDisplay=="inline") ||
					(updUpgradeTypeDisplay=="inline") ||
					(updBoEngineerDisplay=="inline") ||
					(updFeEngineerDisplay=="inline") ||
					(updHardwareVendorDisplay=="inline")) ) {
			return;
		}	
		editCount = 0;
		document.getElementById("multiSiteEdit").value = "Y";
		document.getElementById("selectedAction").value = "search";
		document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
		document.getElementById("selectedEndDate").value = document.getElementById("eDate").value;
		document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
		document.getElementById("f1").action = "scheduleView";
		document.getElementById("f1").submit();	
	} else {
		// add code to check number of edits greater zero
		var editCountString = editCount.toString();
		if (editCount>0) {
			if (confirm("Confirm commit of "+editCountString+" edits")) {
				document.getElementById("multiSiteEdit").value = "N";
				document.getElementById("selectedAction").value = "search";
				document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
				document.getElementById("selectedEndDate").value = document.getElementById("eDate").value;
				document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
				document.getElementById("f1").action = "scheduleView";
				document.getElementById("f1").submit()
			} else {
				if (confirm("Confirm that you do not want to apply the "+editCountString+" pending edits")) {
					document.getElementById("multiSiteEdit").value = "N";
					document.getElementById("selectedAction").value = "search";
					document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
					document.getElementById("selectedEndDate").value = document.getElementById("eDate").value;
					document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
					document.getElementById("f1").action = "scheduleView";
					document.getElementById("f1").submit();
				}
			}				
		} else {
			document.getElementById("multiSiteEdit").value = "N";
			document.getElementById("selectedAction").value = "search";
			document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
			document.getElementById("selectedEndDate").value = document.getElementById("eDate").value;
			document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
			document.getElementById("f1").action = "scheduleView";
			document.getElementById("f1").submit()
		}		
	}
}

function recordEdit(snrId,row,column,currentValue) {
	if (editCount==maxEditCount) {
		alert("This update cannot be recorded as max number of edits reached");
		return;
	}
	var editList = document.getElementById("editList").value;
	var newValue = "";
	if (column=="scheduledDate") {
		newValue = document.getElementById("newSDate"+row).value;
	} else if (column=="boEngineer") {
		newValue = document.getElementById("selectAvailableBOEngineers"+row).value;
	} else if (column=="feEngineer") {
		newValue = document.getElementById("selectAvailableFEEngineers"+row).value;
	}
	editList = editList + snrId+":"+newValue+":"+column+":"+currentValue+"|";
	document.getElementById("editList").value = editList;
	editCount = editCount + 1;
	var maxEditCountString = maxEditCount.toString();
	if (editCount==maxEditCount) {
		if (confirm("Max number of edits reached ("+maxEditCountString+"). Please confirm commit of your changes.")) {
			document.getElementById("multiSiteEdit").value = "N";
			document.getElementById("selectedAction").value = "search";
			document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
			document.getElementById("selectedEndDate").value = document.getElementById("eDate").value;
			document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
			document.getElementById("f1").action = "scheduleView";
			document.getElementById("f1").submit()
		} else {
			if (confirm("Confirm that you do not want to apply the "+maxEditCountString+" pending edits")) {
				document.getElementById("multiSiteEdit").value = "N";
				document.getElementById("selectedAction").value = "search";
				document.getElementById("selectedStartDate").value = document.getElementById("sDate").value;
				document.getElementById("selectedEndDate").value = document.getElementById("eDate").value;
				document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
				document.getElementById("f1").action = "scheduleView";
				document.getElementById("f1").submit();
			}
		}	
	}
}

</script>
<table id="TH" style="table-layout:fixed;border-style:none;width:1250px;display:inline;">
<tr>
<td width="340px" valign="top" class="clientBox">
	<table style="table-layout: fixed; border-style: none;width:310px;">
	<colgroup>
		<col width="30px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
	</colgroup>
	<tbody>
		<tr>
			<td colspan="9" class="dateSearchTop">
				Search by month or week or day
			</td>
		</tr>
		<tr>
			<td height="5px">
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="9" align="center">
				<table style="border-style:solid;border-width:1.0px;">
				<tr>
					<!-- <td class="dateSearchMClass" height="5px" width="3px"></td>-->
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('01')" style="cursor:pointer;">Jan</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('02')"  style="cursor:pointer;">Feb</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('03')"  style="cursor:pointer;">Mar</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('04')"  style="cursor:pointer;">Apr</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('05')"  style="cursor:pointer;">May</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('06')"  style="cursor:pointer;">Jun</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('07')"  style="cursor:pointer;">Jul</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('08')"  style="cursor:pointer;">Aug</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('09')"  style="cursor:pointer;">Sep</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('10')"  style="cursor:pointer;">Oct</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('11')"  style="cursor:pointer;">Nov</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('12')"  style="cursor:pointer;">Dec</td>
				</tr>
				</table>
			</td>
		</tr>
		<!-- calendar month box -->
		<%=dS.searchBoxHTML()%>		
	</tbody>
	</table>
</td>
<td width="455px" valign="top" class="clientBox">
	<table style="table-layout:fixed;border-style:none;width:455px;">
	<colgroup>		
		<col width="50px"/>
		<col width="120px"/>	
		<col width="185px"/>	
		<col width="100px"/>
	</colgroup>
	<tbody>
		<tr>
			<td colspan="4" class="dateSearchTop">
				Search by selected criteria
			</td>
		</tr>
		<tr>
			<td height="5px">
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="dateSearchTLCClass">Project:</td>			
			<td align="left">
				<%=uB.emailCopyProjectHTML()%>
			</td>
			<td align="right" rowspan="7">				
				<div id="siteSearch" 
					title="search by criteria"
					onClick="search('open')" 
					onMouseOut="invertClass('search')" 
					onMouseOver="invertClass('search')" 
					style="float:left;width:75px;display:inline;cursor:pointer;padding: 10px;">
					<img src="images/dev_pictos_red_circle_rvb-05.png"
						height="40px" width="40px" >
				</div>			
				<div id="clearSiteSearch" 
					title="reset to scheduled today only"
					onClick="clearSearchCriteria()" 
					onMouseOut="invertClass('clearSearchCriteria')" 
					onMouseOver="invertClass('clearSearchCriteria')" 
					style="float:left;width:75px;display:inline;cursor:pointer;padding: 10px;" >
					<img src="images/clear.png"
						height="40px" width="40px" >
				</div>
			<td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="dateSearchTLCClass">Upgrade Type:</td>			
			<td align="left">
				<%=uB.emailCopyUpgradeTypeHTML()%>				
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="dateSearchTLCClass">Site:</td>			
			<td align="left">
				<%=uB.emailCopySiteHTML()%>
			</td>
		</tr>
			<td>&nbsp;</td>
			<td class="dateSearchTLCClass">NR Id:</td>			
			<td align="left">
				<%=uB.emailCopyNRIdHTML()%>
			</td>
		</tr>
		</tr>
			<td>&nbsp;</td>
			<td class="dateSearchTLCClass">Site Status</td>			
			<td align="left">
				<%=uB.getSelectHTMLWithInitialValue("SiteStatus", "select", "filter", siteStatus)%>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="dateSearchTLCClass">From Date:</td>			
			<td align="left">
				<input 
					type="text" 
					size="10" 
					id="sDate" 
					name="sDate" 
					value="<%=startDate%>"
					onclick="javascript:NewCssCal 
							('sDate','ddMMyyyy','arrow')" 
					style="cursor:pointer;"
					readonly/>
				<img src="images/cal.gif" 
						onclick="javascript:NewCssCal 
								('sDate','ddMMyyyy','arrow')" 
						style="cursor:pointer"/>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="dateSearchTLCClass">To Date:</td>			
			<td align="left">
				<input 
					type="text" 
					size="10" 
					id="eDate" 
					name="eDate" 
					value="<%=endDate%>"
					onclick="javascript:NewCssCal 
							('eDate','ddMMyyyy','arrow')" 
					style="cursor:pointer;"
					readonly/>
				<img src="images/cal.gif" 
						onclick="javascript:NewCssCal 
								('eDate','ddMMyyyy','arrow')" 
						style="cursor:pointer"/>
			</td>
		</tr>
	</tbody>
	</table>
</td>
<td width="445px" valign="top" class="clientBox">
<table style="table-layout:fixed;border-style:none;width:445px;display:inline;">
<colgroup>		
	<col width="345px"/>
	<col width="100px"/>
</colgroup>
<tbody>
<tr>
	<td class="dateSearchTop" colspan="2">
		Multiple Site Search
	</td>
</tr>
<tr>
	<td height="10px"></td>
</tr>
<tr>
	<td align="center">
		<textarea 
			id="siteList" 
			name="siteList" 
			maxlength="200" 
			placeholder="Enter list of sites"
			style="max-height:150px;resize:none;width:250px;"><%=selectedSiteList%></textarea>
	</td>
	<td align="left">
		<div id="multiSiteSearch" 
			title="search for entered sites"
			onClick="multiSearch('open')" 
			onMouseOut="invertClass('multiSearch')" 
			onMouseOver="invertClass('multiSearch')" 
			style="float:left;width:75px;display:inline;cursor:pointer;padding: 10px;">
			<img src="images/dev_pictos_red_circle_rvb-05.png"
				height="40px" width="40px" >
		</div>
		<div id="clearMultiSiteSearch" 
			title="clear entered sites"
			onClick="clearMultiSearchCriteria()" 
			onMouseOut="invertClass('clearMultiSearchCriteria')" 
			onMouseOver="invertClass('clearMultiSearchCriteria')" 
			style="float:left;width:75px;display:inline;cursor:pointer;padding: 10px;" >
			<img src="images/clear.png"
				height="40px" width="40px" >
		</div>
	</td>
</tr>
</tbody>
</table>
</td>
</tr>
</table>
<table style="table-layout:fixed;border-style:none;width:1250px;display:inline;">
<colgroup>		
	<col width="60px"/>	
	<col width="60px"/>	
	<col width="125px"/>	
	<col width="120px"/>	
	<col width="100px"/>	
	<col width="55px"/>	
	<col width="110px"/>	
	<col width="110px"/>	
	<col width="106px"/>	
	<col width="35px"/>	
	<col width="35px"/>	
	<col width="35px"/>	
	<col width="35px"/>	
	<col width="35px"/>	
	<col width="35px"/>	
	<col width="35px"/>	
	<col width="35px"/>	
	<col width="35px"/>	
	<col width="35px"/>	
	<col width="35px"/>		
	<col width="20px"/>			
	<col class="dateSearchTop" width="16px"/>	
</colgroup>
</tbody>
<tr>
	<td colspan="3" 
		<%=(showTH.equals("Y")?
				"onclick=\"navigationAction('hide')\" title=\"click to show more search results\"":
				"onclick=\"navigationAction('show')\" title=\"click to show search criteria\"")%>
		class="dateSearchTLClass" style="cursor:pointer;">
		Search Results&nbsp;
		<%=(showTH.equals("Y")?
				"<img src=\"images/hide.png\" height=\"15\" width=\"15\">":
				"<img src=\"images/show.png\" height=\"15\" width=\"15\">")%>
	</td>
	<td colspan="19" class="dateSearchTopRight">
		<%=uB.getScheduleListCount(
				project, upgradeType, site, nrId, siteStatus, startDate, endDate, formattedSiteList,initialEntry)%>
	</td>
</tr>
<tr>
	<td class="schHead" 
		<% if (thisU.hasUserRole("Scheduler")) { %>
		id="schDateHdr"
		name="schDateHdr"
		style="cursor:pointer;" 
		title="click to toggle multiple site edit"
		onClick="multiSiteEdit()"
		<%} %>>
		Schedule Date
	</td>
	<td class="schHead">Site</td>
	<td class="schHead">NR Id</td>
	<td class="schHead">Site Status</td>
	<td id="projectHdr" name="projectHdr" class="schHead">Project</td>
	<td id="upgradeTypeHdr" name="upgradeTypeHdr" class="schHead">Upgrade Type</td>
	<td class="schHead" 
		<% if (thisU.hasUserRole("Scheduler")) { %>
		id="boHdr"
		name="boHdr"
		style="cursor:pointer;" 
		title="click to toggle multiple site edit"
		onClick="multiSiteEdit()"
		<%} %>>
		BO Eng.
	</td>
	<td class="schHead" 
		<% if (thisU.hasUserRole("Scheduler")) { %>
		id="feHdr"
		name="feHdr"
		style="cursor:pointer;" 
		title="click to toggle multiple site edit"
		onClick="multiSiteEdit()"
		<%} %>>
		FE Eng.
		</td>
	<td id="hardwareVendorHdr" name="hardwareVendorHdr" class="schHead">Hardware Vendor</td>
	<td class="schHead" title="Vodafone 2G">VF 2G</td>
	<td class="schHead" title="Vodafone 3G">VF 3G</td>
	<td class="schHead" title="Vodafone 4G">VF 4G</td>
	<td class="schHead">TEF 2G</td>
	<td class="schHead">TEF 3G</td>
	<td class="schHead">TEF 4G</td>
	<td class="schHead" title="Paknet and Paging">P&P</td>
	<td class="schHead" title="SecGW Change">Sec GW</td>
	<td class="schHead" title="Power">P</td>
	<td class="schHead" title="Survey">S</td>
	<td class="schHead" title="Other">O</td>
	<%	if (thisU.hasUserRole(UserRole.ROLE_SCHEDULER)) { %>
	<td class="schHead" title="Select">S</td>
	<%} else {%>
	<td class="schHead">&nbsp;</td>
	<%}%>
	<td class="dateSearchTop">&nbsp;</td>
</tr>
</tbody>
</table>
<div id="siteList" style="margin: 0; padding: 0; overflow-y: scroll; overflow-x: hidden; display; inline; 
max-width: 100%; height: <%=(showTH.equals("Y")?"295":"530")%>px;">
<table style="table-layout:fixed;border-style:none;width:1250px;display:inline;">
<colgroup>		
	<col width="60px"/>	
	<col width="60px"/>	
	<col width="125px"/>	
	<col width="120px"/>	
	<col width="100px"/>	
	<col width="55px"/>	
	<col width="110px"/>	
	<col width="110px"/>	
	<col width="105px"/>	
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="20px"/>	
</colgroup>
<tbody>
<%=uB.getScheduleListHTML(
		project, upgradeType, site, nrId, siteStatus, startDate, endDate, 
		formattedSiteList,multiSiteEdit,initialEntry)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px;width:1250px;"></div>
<div id="tm" style="width:1250px;">
<div style="float:left;width:2px;" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none;" class="menu2">Action:</div>
<div style="float:right;width:2px;" class="menu2">&nbsp;</div>
<div id="cancelScheduleSite" onClick="cancelScheduleSite()"  onMouseOut="invertClass('cancelScheduleSite')" onMouseOver="invertClass('cancelScheduleSite')" style="float:right;display:none" class="menu2Item">Cancel Schedule</div> 
<div id="cancelSite" onClick="cancelSite()"  onMouseOut="invertClass('cancelSite')" onMouseOver="invertClass('cancelSite')" style="float:right;display:none" class="menu2Item">Cancel Site</div> 
<div id="rescheduleSite" onClick="openReschedule()" onMouseOut="invertClass('rescheduleSite')" onMouseOver="invertClass('rescheduleSite')" style="float:right;display:none" class="menu2Item">Schedule/Reschedule</div>
<div id="loadPot" onClick="tbClick('loadPot')" onMouseOut="invertClass('loadPot')" onMouseOver="invertClass('loadPot')" style="float:right;display:none" class="menu2Item">Load Pot</div>
<div id="missingData" onClick="tbClick('missingData')" onMouseOut="invertClass('missingData')" onMouseOver="invertClass('missingData')" style="float:right;display:none" class="menu2Item">Missing Data</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px;width:1250px;"></div>
<a href="" id="aLink" name="aLink" style="display:none"></a>
<%@ include file="reschedule.txt" %>
<%@ include file="scheduleViewUpdateSite.txt" %>
<%@ include file="potLoad.txt" %>
</form>
</body>
</html>