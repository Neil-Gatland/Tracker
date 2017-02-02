<%@ include file="header.jsp" %>
<%
boolean multiC = ((String)session.getAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION)).equals(ServletConstants.CONFIRM_IMPLEMENTATION);
String listStatus1 = request.getAttribute("listStatus1")==null?"none":(String)request.getAttribute("listStatus1");
String listStatus2 = request.getAttribute("listStatus2")==null?"none":(String)request.getAttribute("listStatus2");
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
long userId = request.getAttribute("userId")==null?-1:Long.parseLong((String)request.getAttribute("userId"));
boolean historyInd = request.getAttribute("historyInd")==null?false:((String)request.getAttribute("historyInd")).equals("true");
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String snrStatus = request.getAttribute("snrStatus")==null?"none":(String)request.getAttribute("snrStatus");
String snrCommentaryType = request.getAttribute("snrCommentaryType")==null?"none":(String)request.getAttribute("snrCommentaryType");
boolean disableSNRCommentaryType = false;
if (request.getAttribute("snrCommentaryTypeInit") != null) {
	snrCommentaryType = (String)request.getAttribute("snrCommentaryTypeInit");
	disableSNRCommentaryType = true;
}
long preCheckId = request.getAttribute("preCheckId")==null?-1:Long.parseLong((String)request.getAttribute("preCheckId"));
long customerId = request.getAttribute("customerId")==null?-1:Long.parseLong((String)request.getAttribute("customerId"));
String site = request.getAttribute("site")==null?"-1":(String)request.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"-1":(String)request.getAttribute("nrId");
String p1SiteInd = request.getAttribute("p1SiteInd")==null?"":(String)request.getAttribute("p1SiteInd");
String obassInd = request.getAttribute("obassInd")==null?"":(String)request.getAttribute("obassInd");
String ramsInd = request.getAttribute("ramsInd")==null?"":(String)request.getAttribute("ramsInd");
String escortInd = request.getAttribute("escortInd")==null?"":(String)request.getAttribute("escortInd");
String healthSafetyInd = request.getAttribute("healthSafetyInd")==null?"":(String)request.getAttribute("healthSafetyInd");
String accessConfirmedInd = request.getAttribute("accessConfirmedInd")==null?"":(String)request.getAttribute("accessConfirmedInd");
String oohWeekendInd = request.getAttribute("oohWeekendInd")==null?"":(String)request.getAttribute("oohWeekendInd");
String outagePeriod = request.getAttribute("outagePeriod")==null?"0":(String)request.getAttribute("outagePeriod");
String accessCost = request.getAttribute("accessCost")==null?"0":(String)request.getAttribute("accessCost");
String consumableCost = request.getAttribute("consumableCost")==null?"0":(String)request.getAttribute("consumableCost");
String crInReference = request.getAttribute("crInReference")==null?"":(String)request.getAttribute("crInReference");
String crInInd = request.getAttribute("crInInd")==null?"":(String)request.getAttribute("crInInd");
String crInStartDT = request.getAttribute("crInStartDT")==null?"":(String)request.getAttribute("crInStartDT");
String crInEndDT = request.getAttribute("crInStartDT")==null?"":(String)request.getAttribute("crInEndDT");
String crInOutageStartDT = request.getAttribute("crInOutageStartDT")==null?"":(String)request.getAttribute("crInOutageStartDT");
String crInOutageEndDT = request.getAttribute("crInOutageStartDT")==null?"":(String)request.getAttribute("crInOutageEndDT");
String crInUsed = request.getAttribute("crInUsed")==null?"":(String)request.getAttribute("crInUsed");
String crqStatus = request.getAttribute("crqStatus")==null?"":(String)request.getAttribute("crqStatus");
String implementationStatus = request.getAttribute("implementationStatus")==null?"":(String)request.getAttribute("implementationStatus");
String implementationStartDT = request.getAttribute("implementationStartDT")==null?"":(String)request.getAttribute("implementationStartDT");
String implementationEndDT = request.getAttribute("implementationEndDT")==null?"":(String)request.getAttribute("implementationEndDT");
String implementationAbortType = request.getAttribute("implementationAbortType")==null?"":(String)request.getAttribute("implementationAbortType");
String implementation2GInd = request.getAttribute("implementation2GInd")==null?"":(String)request.getAttribute("implementation2GInd");
String implementation3GInd = request.getAttribute("implementation3GInd")==null?"":(String)request.getAttribute("implementation3GInd");
String implementation4GInd = request.getAttribute("implementation4GInd")==null?"":(String)request.getAttribute("implementation4GInd");
String implementationO2Ind = request.getAttribute("implementationO2Ind")==null?"":(String)request.getAttribute("implementationO2Ind");
String implementationTEF2GInd = request.getAttribute("implementationTEF2GInd")==null?"":(String)request.getAttribute("implementationTEF2GInd");
String implementationTEF3GInd = request.getAttribute("implementationTEF3GInd")==null?"":(String)request.getAttribute("implementationTEF3GInd");
String implementationTEF4GInd = request.getAttribute("implementationTEF4GInd")==null?"":(String)request.getAttribute("implementationTEF4GInd");
String implementationPaknetPaging = request.getAttribute("implementationPaknetPaging")==null?"":(String)request.getAttribute("implementationPaknetPaging");
String implementationSecGWChange = request.getAttribute("implementationSecGWChange")==null?"":(String)request.getAttribute("implementationSecGWChange");
String implementationPower = request.getAttribute("implementationPower")==null?"":(String)request.getAttribute("implementationPower");
String implementationSurvey = request.getAttribute("implementationSurvey")==null?"":(String)request.getAttribute("implementationSurvey");
String implementationOther = request.getAttribute("implementationOther")==null?"":(String)request.getAttribute("implementationOther");
String implementationHealthChecksInd = request.getAttribute("implementationHealthChecksInd")==null?"":(String)request.getAttribute("implementationHealthChecksInd");
String implementationActiveAlarmsInd = request.getAttribute("implementationActiveAlarmsInd")==null?"":(String)request.getAttribute("implementationActiveAlarmsInd");
String implementationNSANetActsInd = request.getAttribute("implementationNSANetActsInd")==null?"":(String)request.getAttribute("implementationNSANetActsInd");
String implementationHOPDeliveredInd = request.getAttribute("implementationHOPDeliveredInd")==null?"":(String)request.getAttribute("implementationHOPDeliveredInd");
String implementationHOPFilename = request.getAttribute("implementationHOPFilename")==null?"":(String)request.getAttribute("implementationHOPFilename");
String implementationHOPOnSharePoint = request.getAttribute("implementationHOPOnSharePoint")==null?"":(String)request.getAttribute("implementationHOPOnSharePoint");
String implementationEFUpdated = request.getAttribute("implementationEFUpdated")==null?"":(String)request.getAttribute("implementationEFUpdated");
String implementationSFRCompleted = request.getAttribute("implementationSFRCompleted")==null?"":(String)request.getAttribute("implementationSFRCompleted");
String implementationSFROnSharePoint = request.getAttribute("implementationSFROnSharePoint")==null?"":(String)request.getAttribute("implementationSFROnSharePoint");
String scheduledDT = request.getAttribute("scheduledDT")==null?"":(String)request.getAttribute("scheduledDT");
String snrUserRole = request.getAttribute("snrUserRole")==null?"":(String)request.getAttribute("snrUserRole");
long snrUserRoleCount = request.getAttribute("snrUserRoleCount")==null?-1:Long.parseLong((String)request.getAttribute("snrUserRoleCount"));
String feLevel = request.getAttribute("feLevel")==null?"":(String)request.getAttribute("feLevel");
String action = "multiSNRList";
long historyDT = 0;
String implOutageStartDT = request.getAttribute("implOutageStartDT")==null?"":(String)request.getAttribute("implOutageStartDT");
String implOutageEndDT = request.getAttribute("implOutageEndDT")==null?"":(String)request.getAttribute("implOutageEndDT");
String completingBOEngineer = request.getAttribute("completingBOEngineer")==null?"":(String)request.getAttribute("completingBOEngineer");
String ef345ClaimDT = request.getAttribute("ef345ClaimDT")==null?"":(String)request.getAttribute("ef345ClaimDT");
String ef360ClaimDT = request.getAttribute("ef360ClaimDT")==null?"":(String)request.getAttribute("ef360ClaimDT");
String ef390ClaimDT = request.getAttribute("ef390ClaimDT")==null?"":(String)request.getAttribute("ef390ClaimDT");
String ef400ClaimDT = request.getAttribute("ef400ClaimDT")==null?"":(String)request.getAttribute("ef400ClaimDT");
String ef410ClaimDT = request.getAttribute("ef410ClaimDT")==null?"":(String)request.getAttribute("ef410ClaimDT");
String tefOutageNos = request.getAttribute("tefOutageNos")==null?"":(String)request.getAttribute("tefOutageNos");
String reallocType = multiC?"realloc":request.getAttribute("reallocType")==null?"":(String)request.getAttribute("reallocType");
String snrUserRoleListHTML = snrId==-1?"":uB.getSNRUserRoleListHTML(snrId, userId, multiC);
String workflowName = request.getAttribute("workflowName")==null?"":(String)request.getAttribute("workflowName");
String scheduleCommentary = request.getAttribute("scheduleCommentary")==null?"":(String)request.getAttribute("scheduleCommentary");
int feCount = uB.getFECount();
int boCount = uB.getBOCount();
String boEngineerList = request.getAttribute("boEngineerList")==null?"":(String)request.getAttribute("boEngineerList");
String feList = request.getAttribute("feList")==null?"":(String)request.getAttribute("feList");
String preTestCallsDone = request.getAttribute("preTestCallsDone")==null?"":(String)request.getAttribute("preTestCallsDone");
String postTestCallsDone = request.getAttribute("postTestCallsDone")==null?"":(String)request.getAttribute("postTestCallsDone");
String crqClosureCode = request.getAttribute("crqClosureCode")==null?"":(String)request.getAttribute("crqClosureCode");
String siteIssues = request.getAttribute("siteIssues")==null?"":(String)request.getAttribute("siteIssues");
String workDetails = request.getAttribute("workDetails")==null?"":(String)request.getAttribute("workDetails");
String hardwareVendor = request.getAttribute("hardwareVendor")==null?"":(String)request.getAttribute("hardwareVendor");
String nextPreCheck = request.getAttribute("nextPreCheck")==null?"":(String)request.getAttribute("nextPreCheck");
String firstAttempt = request.getAttribute("firstAttempt")==null?"":(String)request.getAttribute("firstAttempt");
String hopStatus = request.getAttribute("hopStatus")==null?"":(String)request.getAttribute("hopStatus");
String cramerCompleted = request.getAttribute("cramerCompleted")==null?"":(String)request.getAttribute("cramerCompleted");
String scriptsReceived = request.getAttribute("scriptsReceived")==null?"":(String)request.getAttribute("scriptsReceived");
String alarms = request.getAttribute("alarms")==null?"":(String)request.getAttribute("alarms");
String healthCheck = request.getAttribute("healthCheck")==null?"":(String)request.getAttribute("healthCheck");
String jobType = request.getAttribute("jobType")==null?"":(String)request.getAttribute("jobType");
String incTicketNo = request.getAttribute("incTicketNo")==null?"":(String)request.getAttribute("incTicketNo");
String performanceChecks = request.getAttribute("performanceChecks")==null?"":(String)request.getAttribute("performanceChecks");
String[] nrIdParameters = {"N"};
String[] siteParameters = {"N"};
String filterSite = request.getAttribute("filterSite")==null?"All":(String)request.getAttribute("filterSite");
String hSiteTitle = filterSite.equals("All")?"Filter not set":("Current filter value: " + filterSite);	
String hSiteClass = filterSite.equals("All")?"thClick":"thClickS";	
String filterNRId = request.getAttribute("filterNRId")==null?"All":(String)request.getAttribute("filterNRId");
String hNRIdTitle = filterNRId.equals("All")?"Filter not set":("Current filter value: " + filterNRId);	
String hNRIdClass = filterNRId.equals("All")?"thClick":"thClickS";
String fullname = multiC?thisU.getFullname():"All";
String filterBOEngineer = request.getAttribute("filterBOEngineer")==null?fullname:(String)request.getAttribute("filterBOEngineer");
String hBOEngineerTitle = filterBOEngineer.equals("All")?"Filter not set":("Current filter value: " + filterBOEngineer);	
String hBOEngineerClass = filterBOEngineer.equals("All")?"thClick":"thClickS";	
String filterStatus = request.getAttribute("filterStatus")==null?"All":(String)request.getAttribute("filterStatus");
String hStatusTitle = filterStatus.equals("All")?"Filter not set":("Current filter value: " + filterStatus);	
String hStatusClass = filterStatus.equals("All")?"thClick":"thClickS";	
String[] statusParameters = {"All"};
String filterScheduledStart = request.getAttribute("filterScheduledStart")==null?"":(String)request.getAttribute("filterScheduledStart");
String filterScheduledEnd = request.getAttribute("filterScheduledEnd")==null?"":(String)request.getAttribute("filterScheduledEnd");
StringBuilder hScheduledTitle = new StringBuilder("Scheduled Date - ");
String progressCompleted = request.getAttribute("progressCompleted")==null?"N":(String)request.getAttribute("progressCompleted");
if (filterScheduledStart.equals("")) {
	if (filterScheduledEnd.equals("")) {
		hScheduledTitle.append("filter not set");
	} else {
		hScheduledTitle.append("current filter value: all dates up to and including ");
		hScheduledTitle.append(filterScheduledEnd);
	}
} else {
	if (filterScheduledEnd.equals("")) {
		hScheduledTitle.append("current filter value: all dates from ");
		hScheduledTitle.append(filterScheduledStart);
		hScheduledTitle.append(" (inclusive)");
	} else {
		hScheduledTitle.append("current filter value: all dates between ");
		hScheduledTitle.append(filterScheduledStart);
		hScheduledTitle.append(" and ");
		hScheduledTitle.append(filterScheduledEnd);
		hScheduledTitle.append(" (inclusive)");
	}
}
String hScheduledClass = filterScheduledStart.equals("")?"thClick":"thClickS";	
String extraVis = multiC?";display:none":"";
String rName = multiC?"closePI":"closeI";
String vcTitle = "Site: " + site + ", NR Id: " + nrId;
String resetAnchor = request.getAttribute("resetAnchor")==null?"null":(String)request.getAttribute("resetAnchor");
String prevScreen = request.getAttribute("prevScreen")==null?"":(String)request.getAttribute("prevScreen");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="workQueues.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.WORK_QUEUES%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value="<%=buttonPressed%>"/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="snrStatus" id="snrStatus" value="<%=snrStatus%>"/>
<input type="hidden" name="snrStatusNew" id="snrStatusNew" value=""/>
<input type="hidden" name="customerId" id="customerId" value="<%=customerId%>"/>
<input type="hidden" name="historyInd" id="historyInd" value="<%=historyInd?"true":"false"%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<input type="hidden" name="listStatus1" id="listStatus1" value="<%=listStatus1%>"/>
<input type="hidden" name="listStatus2" id="listStatus2" value="<%=listStatus2%>"/>
<input type="hidden" name="whichFilter" id="whichFilter" value=""/>
<input type="hidden" name="nextPreCheck" id="nextPreCheck" value="<%=nextPreCheck%>"/>
<input type="hidden" name="preCheckId" id="preCheckId" value="<%=preCheckId%>"/>
<input type="hidden" name="prevScreen" id="prevScreen" value="<%=prevScreen%>"/>
<input type="hidden" name="progressCompleted" id="progressCompleted" value="<%=progressCompleted%>"/>
<input type="hidden" name="implementationStatus" id="implementationStatus" value="<%=implementationStatus%>"/>
<script language="javascript">
<!--
var selectedSNRId = <%=snrId%>;
var selectedSNRStatus = "<%=snrStatus%>";
var selectedCustomerId = <%=customerId%>;
var selectedSite = "<%=site%>";
var selectedNRId = "<%=nrId%>";

function thisScreenLoad() {

<%
	if (snrId != -1) {
%>
	snrSelect(<%=snrId%>, "<%=snrStatus%>", <%=customerId%>, "<%=site%>", "<%=nrId%>");
	
<%
		if (buttonPressed.equals("showDetail")) {
%>
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var snrD = document.getElementById("snrD");
	snrD.style.display = "inline";
	snrD.style.left = position.x + "px";
	snrD.style.top = position.y + "px";
	snrD.style.zIndex = "20";
<%	
		} else if (buttonPressed.equals("resched")) {
%>
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var rSNR = document.getElementById("rescheduleSNR");
	rSNR.style.display = "inline";
	rSNR.style.left = position.x + "px";
	rSNR.style.top = position.y + "px";
	rSNR.style.zIndex = "20";
<%	
		} else if ((buttonPressed.equals("detailP")) ||
				(buttonPressed.equals("finalP"))) {
%>
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var pCI = document.getElementById("pCI");
	pCI.style.display = "inline";
	pCI.style.left = position.x + "px";
	pCI.style.top = position.y + "px";
	pCI.style.zIndex = "99";
<%	
		} else if (buttonPressed.equals("schedM")) {
%>
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var raSNR = document.getElementById("reallocateSNR");
	raSNR.style.display = "inline";
	raSNR.style.left = position.x + "px";
	raSNR.style.top = position.y + "px";
	raSNR.style.zIndex = "20";
	document.getElementById("cancelSM").style.display = "inline";
	var sDT = document.getElementById("scheduledDT").value;
	if ((sDT.length == 0) || (<%=feCount%> == 0)|| (<%=boCount%> == 0)) {
		document.getElementById("confirmSM").style.display = "none";
	} else {
		document.getElementById("confirmSM").style.display = "inline";
	}
<%	
		} else if (buttonPressed.equals("realloc")) {
			if (multiC) {
%>
	showImplementationDetail();	
<%			}%>
	showReallocate();
<%	

		} else if (buttonPressed.equals("addRole")) {
%>
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var raSNR = document.getElementById("reallocateSNR");
	raSNR.style.display = "inline";
	raSNR.style.left = position.x + "px";
	raSNR.style.top = position.y + "px";
	raSNR.style.zIndex = "20";
	var aR = document.getElementById("addRole");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "30";
<%			if (reallocType.equals("schedM")) {%> 	
	document.getElementById("cancelSM").style.display = "inline";
<%
			}


		} else if (buttonPressed.equals("chgRole")) {
%>
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var raSNR = document.getElementById("reallocateSNR");
	raSNR.style.display = "inline";
	raSNR.style.left = position.x + "px";
	raSNR.style.top = position.y + "px";
	raSNR.style.zIndex = "20";
	var aR = document.getElementById("chgRole");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "30";
<%			if (reallocType.equals("schedM")) {%> 	
	document.getElementById("cancelSM").style.display = "inline";
<%
			}

		} else if (buttonPressed.equals("showImplementationDetail")) {
%>
	showImplementationDetail();	
<%	
			if ((snrStatus.equalsIgnoreCase(ServletConstants.STATUS_SCHEDULED)) &&
					(thisU.hasUserRole(UserRole.ROLE_B_O_ENGINEER))) {
%>
	document.getElementById("completeI").style.display = "inline";
<%
			}
		} else if (buttonPressed.startsWith("viewCom")) {
			if (buttonPressed.equals("viewComI")) {
%>
	showImplementationDetail();	
<%	
			}
%>
	showViewCommentary();
<%	
		} else if (buttonPressed.startsWith("addCom")) {
			if (buttonPressed.equals("addComI")) {
%>
	showImplementationDetail();	
	showAddCommentary("snrImplementationDetail");
<%	
			} else if ((buttonPressed.equals("addComP")) || (buttonPressed.equals("addComBO"))) {
%>
	showImplementationDetail();	
	showAddPerformanceCommentary();
<%	
			} else {
%>
	showAddCommentary();
<%
			}
		}
	}
%>
// had to put this last so it would work with IE!
if ("<%=resetAnchor%>" != "null" ) {
	gotoAnchor("<%=resetAnchor%>"); }
}

function showReallocate() {
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "95";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var raSNR = document.getElementById("reallocateSNR");
	raSNR.style.display = "inline";
	raSNR.style.left = position.x + "px";
	raSNR.style.top = position.y + "px";
	raSNR.style.zIndex = "99";
}

function showSiteProgress() {
	/*var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "94";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var spSNR = document.getElementById("siteProgress");
	spSNR.style.display = "inline";
	spSNR.style.left = position.x + "px";
	spSNR.style.top = position.y + "px";
	spSNR.style.zIndex = "98";*/
	document.getElementById("toScreen").value = "<%=ServletConstants.SITE_PROGRESS%>";
	document.getElementById("f1").action = "navigation";
	document.getElementById("f1").submit();
}

function showAddPerformanceCommentary() {
	var header = document.getElementById("hSD");
	var position = getPosition(header);
	var aC = document.getElementById("addCommentary");
	aC.style.display = "inline";
	aC.style.left = (position.x-92) + "px";
	aC.style.top = position.y + "px";
	aC.style.zIndex = "90";
	document.getElementById("extraScreen").value = "snrImplementationDetail";
}

function showImplementationDetail() {
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrIH");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorIH");
	position = getPosition(anchor);
	var snrI = document.getElementById("snrImplementationDetail");
	snrI.style.display = "inline";
	snrI.style.left = position.x + "px";
	snrI.style.top = position.y + "px";
	snrI.style.zIndex = "20";
}

function tbClick(btn) {
	if (btn == "holdRel") {
		if (!confirm("Please confirm status update (" +
			selectedSNRStatus + " to " + 
			(selectedSNRStatus=="On Hold"?"Awaiting Scheduling":"On Hold") + ")" )) {
			return;
		}
		document.getElementById("snrStatusNew").value = 
			selectedSNRStatus=="On Hold"?"Awaiting Scheduling":"On Hold";
	} else if (btn == "closeNR") {
		if (!confirm("Please confirm status update (" +
				selectedSNRStatus + " to Closed)" )) {
				return;
			}
			document.getElementById("snrStatusNew").value = "Closed";
	} else if (btn == "cancel") {	
		if (!confirm("Please confirm cancellation of SNR " + selectedSNRId)) {
			return;
		}
	} else if (btn == "closeC") {
		hideViewCommentary();
		return;
	}
		
	document.getElementById("buttonPressed").value = btn;
	document.getElementById("snrId").value = selectedSNRId;
	document.getElementById("snrStatus").value = selectedSNRStatus;
	document.getElementById("historyInd").value = "false";
	document.getElementById("customerId").value = selectedCustomerId;
	document.getElementById("site").value = selectedSite;
	document.getElementById("nrId").value = selectedNRId;
	document.getElementById("f1").action = "multiSNRList";
	document.getElementById("f1").submit();
		
		
}

function headerClick(headerId, showLeft, anchorId) {
	var header = document.getElementById(anchorId||headerId);
	var position = getPosition(header);
	var filter = document.getElementById("df"+headerId.substring(1));
	var posL;
	if (showLeft == true) {
		posL = (position.x + header.offsetWidth) - 270;
	} else {
		posL = position.x;
	}
	filter.style.display = "inline";
	filter.style.left = posL + "px";
	filter.style.top = position.y + "px";
	filter.style.zIndex = "20";
}

function filterClick(filterId, operation) {
	var filter = document.getElementById(filterId);
	filter.style.display = "none";
	filter.style.left = "0px";
	filter.style.top = "0px";
	if (operation != "cancel") {
		document.getElementById("whichFilter").value = "filter"+filterId.substring(2);
		document.getElementById("buttonPressed").value = operation;
		document.getElementById("f1").action = "multiSNRList";
		document.getElementById("f1").submit();
	}
}

function snrSelect(snrId, status, customerId, site, nrId, efComplete) {
	document.getElementById("action").style.display = "inline";
	document.getElementById("closeNR").style.display = "none";
<%if (title.equals(ServletConstants.CONFIRM_IMPLEMENTATION)) {%>
	document.getElementById("implDetail").style.display = "inline";
	//document.getElementById("addComI").style.display = "inline";
	document.getElementById("viewCom").style.display = "inline";
<%  if (thisU.hasUserRole(UserRole.ROLE_PMO)) {%>
	if ((efComplete == true) && (status == '<%=ServletConstants.STATUS_COMPLETED%>'))  {
		document.getElementById("closeNR").style.display = "inline";
	}
<%  } 
  }	else if (title.equals(ServletConstants.RESCHED_REALLOC_CANCEL_SNR)) {%>
	document.getElementById("detail").style.display = "inline";
	document.getElementById("cancel").style.display = "inline";
	if (status == '<%=ServletConstants.STATUS_AWAITING_SCHEDULING%>') {
		document.getElementById("realloc").style.display = "none";
		document.getElementById("resched").style.display = "none";
		document.getElementById("schedM").style.display = "inline";
	} else {	
		document.getElementById("realloc").style.display = "inline";
		document.getElementById("resched").style.display = "inline";
		document.getElementById("schedM").style.display = "none";
	}
<%}%>
	selectedSNRId = snrId;
	selectedSNRStatus = status;
	selectedCustomerId = customerId;
	selectedSite = site;
	selectedNRId = nrId;
	document.getElementById("snrId").value = selectedSNRId;
}


-->
</script>
<div style="width:1270px;margin:0 auto;margin-top:20px;">
<div style="
margin: 0; padding: 0; border-collapse: collapse; width: 1250px; height: 460px; overflow: hidden; border: 1px solid black;"
>
<table style="width: 1250px; height: 20px;"
>
<colgroup>
<col width="200px"/>
<col width="260px"/>
<%if (listStatus2.equalsIgnoreCase(ServletConstants.STATUS_AWAITING_SCHEDULING)) {%>		
<col width="200px"/>
<col width="200px"/>
<col width="50px"/>
<col width="50px"/>
<col width="50px"/>
<col width="50px"/>
<%} else { %>		
<col width="85px"/>
<col width="85px"/>
<col width="270px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<%}%>		
<col width="150px"/>
<col width="50px"/>
</colgroup>
<tbody>
<tr>
		<th class="<%=hSiteClass%>" id="hAnchor" onClick="headerClick('hSite', false, 'hAnchor')" title="<%=hSiteTitle%>">Site</th>
		<th class="<%=hNRIdClass%>" id="hNRId" onClick="headerClick('hNRId', false)" title="<%=hNRIdTitle%>">NR Id</th>
<%if (listStatus2.equalsIgnoreCase(ServletConstants.STATUS_AWAITING_SCHEDULING)) {%>		
		<th class="<%=hStatusClass%>" id="hStatus" onClick="headerClick('hStatus', false)" title="<%=hStatusTitle%>">NR Status</th>
		<th>Implementation Status</th>
<%} else { %>		
		<th>Status</th>
		<th title="Implementation Status">Impl. Status</th>
		<th class="<%=hBOEngineerClass%>" id="hBOEngineer" onClick="headerClick('hBOEngineer', false)" title="<%=hBOEngineerTitle%>">BO Engineers</th>
<%}%>		
		<th title="Previous partial or aborted implementation">P</th>
		<th title="Commentary Count">CC</th>
		<th title="Next PreCheck">NPC</th>
		<th title="HOP Created">H</th>
<%if (listStatus2.equalsIgnoreCase(ServletConstants.STATUS_AWAITING_SCHEDULING)) {%>		
		<th class="<%=hScheduledClass%>" id="hScheduled" onClick="headerClick('hScheduled', true)" title="<%=hScheduledTitle.toString()%>">Scheduled Date</th>
<%} else { %>		
		<th>Scheduled Date</th>
<%}%>		
		<th>Select</th>
</tr>
</tbody>
</table>
<div style="margin:0;padding:0;border-collapse:collapse;width:1250px;height:420px;overflow-x:hidden;overflow-y:auto;"
>
<table style="width: 1250px;"
>
<colgroup>
<col width="200px"/>
<col width="260px"/>
<%if (listStatus2.equalsIgnoreCase(ServletConstants.STATUS_AWAITING_SCHEDULING)) {%>		
<col width="200px"/>
<col width="200px"/>
<col width="50px"/>
<col width="50px"/>
<col width="50px"/>
<col width="50px"/>
<%} else { %>		
<col width="85px"/>
<col width="85px"/>
<col width="270px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<%}%>		
<col width="150px"/>
<col width="50px"/>
</colgroup>
<tbody>
<%=uB.getSNRSummaryListHTML(snrId, listStatus1, listStatus2, filterSite, filterNRId, 
		filterBOEngineer, filterStatus,	filterScheduledStart, filterScheduledEnd)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="detail" onClick="tbClick('showDetail')" onMouseOut="invertClass('detail')" onMouseOver="invertClass('detail')" style="float:right;display:none" class="menu2Item">View NR Detail</div>
<div id="accessDetail" onClick="tbClick('showAccessDetail')" onMouseOut="invertClass('accessDetail')" onMouseOver="invertClass('accessDetail')" style="float:right;display:none" class="menu2Item">Access Detail</div>
<div id="crmDetail" onClick="tbClick('showCRMDetail')" onMouseOut="invertClass('crmDetail')" onMouseOver="invertClass('crmDetail')" style="float:right;display:none" class="menu2Item">CRM Detail</div>
<div id="implDetail" onClick="tbClick('showImplementationDetail')" onMouseOut="invertClass('implDetail')" onMouseOver="invertClass('implDetail')" style="float:right;display:none" class="menu2Item">Implementation Detail</div>
<div id="viewCom" onClick="tbClick('viewCom')" onMouseOut="invertClass('viewCom')" onMouseOver="invertClass('viewCom')" style="float:right;display:none" class="menu2Item">View Commentary</div>
<div id="addCom" onClick="tbClick('addCom')" onMouseOut="invertClass('addCom')" onMouseOver="invertClass('addCom')" style="float:right;display:none" class="menu2Item">Add Commentary</div>
<div id="cancel" onClick="tbClick('cancel')" onMouseOut="invertClass('cancel')" onMouseOver="invertClass('cancel')" style="float:right;display:none" class="menu2Item">Cancel</div>
<div id="realloc" onClick="tbClick('realloc')" onMouseOut="invertClass('realloc')" onMouseOver="invertClass('realloc')" style="float:right;display:none" class="menu2Item">Reschedule / Reallocate</div>
<div id="resched" onClick="tbClick('resched')" onMouseOut="invertClass('resched')" onMouseOver="invertClass('resched')" style="float:right;display:none" class="menu2Item"></div>
<div id="closeNR" onClick="tbClick('closeNR')" onMouseOut="invertClass('closeNR')" onMouseOver="invertClass('closeNR')" style="float:right;display:none" class="menu2Item">Close NR</div>
<div id="schedM" onClick="tbClick('schedM')" onMouseOut="invertClass('schedM')" onMouseOver="invertClass('schedM')" style="float:right;display:none" class="menu2Item">Schedule Manually</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</div>
<!-- filters -->
<div id="dfSite" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfSite', 'cancel')">x</div>
	<div style="clear:both">Site Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("Site", "filter", "filter", filterSite, siteParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSite', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSite', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSite', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id="dfNRId" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfNRId', 'cancel')">x</div>
	<div style="clear:both">NR Id Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("NRId", "filter", "filter", filterNRId, nrIdParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfNRId', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfNRId', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfNRId', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id="dfBOEngineer" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfBOEngineer', 'cancel')">x</div>
	<div style="clear:both">BO Engineer Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("BOEngineer", "filter", "filter", filterBOEngineer) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfBOEngineer', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfBOEngineer', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfBOEngineer', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfStatus" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfStatus', 'cancel')">x</div>
	<div style="clear:both">NR Status Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("Status", "filter", "filter", filterStatus, statusParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfStatus', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfStatus', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfStatus', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfScheduled" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfScheduled', 'cancel')">x</div>
	<div style="clear:both">Scheduled From Filter</div>
	<div style="padding-bottom:10px">
		<input style="width:240px" type="text" id="filterScheduledStart" name="filterScheduledStart" value="<%=filterScheduledStart%>" />
		<img src="images/cal.gif" onclick="javascript:NewCssCal ('filterScheduledStart','ddMMyyyy','arrow')" style="cursor:pointer"/>
	</div>
	<div style="clear:both">Scheduled To Filter</div>
	<div style="padding-bottom:10px">
		<input style="width:240px" type="text" id="filterScheduledEnd" name="filterScheduledEnd" value="<%=filterScheduledEnd%>" />
		<img src="images/cal.gif" onclick="javascript:NewCssCal ('filterScheduledEnd','ddMMyyyy','arrow')" style="cursor:pointer"/>
	</div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfScheduled', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfScheduled', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfScheduled', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<!-- mask -->
<%@ include file="multiSNRListBlank.txt" %>
<%@ include file="workQueuesBlank.jsp" %>
<!-- view SNR commentary -->
<%@ include file="viewSNRCommentary.txt" %>
<!-- add SNR commentary -->
<%@ include file="addSNRCommentary.txt" %>
<!-- SNR Implementation Header -->
<%@ include file="snrImplementationHeader.txt" %>
<!-- SNR Implementation Detail -->
<%@ include file="snrImplementationDetail.txt" %>
<!-- Reallocate SNR -->
<%@ include file="reallocateSNR.txt" %>
<!-- Reschedule SNR -->
<%@ include file="rescheduleSNR.txt" %>
<!-- SNR detail -->
<%@ include file="viewSNRDetail.txt" %>
<!-- Add Role -->
<%@ include file="addRole.txt" %>
<!-- Change Role -->
<%@ include file="chgRole.txt" %>
<!-- Add BO Technologies -->
<%@ include file="addBOTechnologies.txt" %>
<!-- Delete BO Technologies -->
<%@ include file="delBOTechnologies.txt" %>
<!-- PreCheck Items -->
<%@ include file="preCheckItems.txt" %>
</form>
</body>
</html>