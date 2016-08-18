<%@ include file="header.jsp" %>
<%
String listStatus1 = request.getAttribute("listStatus1")==null?"none":(String)request.getAttribute("listStatus1");
String listStatus2 = request.getAttribute("listStatus2")==null?"none":(String)request.getAttribute("listStatus2");
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
String nrId = request.getAttribute("nrId")==null?"":(String)request.getAttribute("nrId");
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
String implOutageStartDT = request.getAttribute("implOutageStartDT")==null?"":(String)request.getAttribute("implOutageStartDT");
String implOutageEndDT = request.getAttribute("implOutageEndDT")==null?"":(String)request.getAttribute("implOutageEndDT");
String feLevel = request.getAttribute("feLevel")==null?"":(String)request.getAttribute("feLevel");
String action = "updateSNRList";
String accessStatus = request.getAttribute("accessStatus")==null?"":(String)request.getAttribute("accessStatus");
String permitType = request.getAttribute("permitType")==null?"":(String)request.getAttribute("permitType");
String tefOutageRequired = request.getAttribute("tefOutageRequired")==null?"":(String)request.getAttribute("tefOutageRequired");
String vfArrangeAccess = request.getAttribute("vfArrangeAccess")==null?"":(String)request.getAttribute("vfArrangeAccess");
String twoManSite = request.getAttribute("twoManSite")==null?"":(String)request.getAttribute("twoManSite");
String siteAccessInfomation = request.getAttribute("siteAccessInfomation")==null?"":(String)request.getAttribute("siteAccessInfomation");
String siteName = request.getAttribute("siteName")==null?"":(String)request.getAttribute("siteName");
String tefOutageNos = request.getAttribute("tefOutageNos")==null?"":(String)request.getAttribute("tefOutageNos");
String[] nrIdParameters = {"N"};
String[] siteParameters = {"N"};
String[] accessStatusParameters = {"All"};
String[] crqStatusParameters = {"All"};
String filterSite = request.getAttribute("filterSite")==null?"All":(String)request.getAttribute("filterSite");
String hSiteTitle = filterSite.equals("All")?"Filter not set":("Current filter value: " + filterSite);	
String hSiteClass = filterSite.equals("All")?"thClick":"thClickS";	
String filterNRId = request.getAttribute("filterNRId")==null?"All":(String)request.getAttribute("filterNRId");
String hNRIdTitle = filterNRId.equals("All")?"Filter not set":("Current filter value: " + filterNRId);	
String hNRIdClass = filterNRId.equals("All")?"thClick":"thClickS";	
String filterAccessStatus = request.getAttribute("filterAccessStatus")==null?"All":(String)request.getAttribute("filterAccessStatus");
String hAccessStatusTitle = "Access Status - " + (filterAccessStatus.equals("All")?"Filter not set":("Current filter value: " + filterAccessStatus));	
String hAccessStatusClass = filterAccessStatus.equals("All")?"thClick":"thClickS";	
String filterCRQStatus = request.getAttribute("filterCRQStatus")==null?"All":(String)request.getAttribute("filterCRQStatus");
String hCRQStatusTitle = "CRQ Status - " + (filterCRQStatus.equals("All")?"Filter not set":("Current filter value: " + filterCRQStatus));	
String hCRQStatusClass = filterCRQStatus.equals("All")?"thClick":"thClickS";	
String filterJobType = request.getAttribute("filterJobType")==null?"All":(String)request.getAttribute("filterJobType");
String hJobTypeTitle = "Job Type - " + (filterJobType.equals("All")?"filter not set":("current filter value: " + filterJobType));	
String hJobTypeClass = filterJobType.equals("All")?"thClick":"thClickS";	
String filterScheduledStart = request.getAttribute("filterScheduledStart")==null?"":(String)request.getAttribute("filterScheduledStart");
String filterScheduledEnd = request.getAttribute("filterScheduledEnd")==null?"":(String)request.getAttribute("filterScheduledEnd");
StringBuilder hScheduledTitle = new StringBuilder("Scheduled Date - ");
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
String vcTitle = "Site: " + site + ", NR Id: " + nrId;
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="workQueues.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.WORK_QUEUES%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value="<%=buttonPressed%>"/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<input type="hidden" name="snrStatus" id="snrStatus" value="<%=snrStatus%>"/>
<input type="hidden" name="snrStatusNew" id="snrStatusNew" value=""/>
<input type="hidden" name="customerId" id="customerId" value="<%=customerId%>"/>
<input type="hidden" name="historyInd" id="historyInd" value="<%=historyInd?"true":"false"%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="listStatus1" id="listStatus1" value="<%=listStatus1%>"/>
<input type="hidden" name="listStatus2" id="listStatus2" value="<%=listStatus2%>"/>
<input type="hidden" name="whichFilter" id="whichFilter" value=""/>
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
		if (buttonPressed.equals("showAccessDetail")) {
%>
	showAccessDetail();
	/*var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var snrAD = document.getElementById("snrAccessDetail");
	snrAD.style.display = "inline";
	snrAD.style.left = position.x + "px";
	snrAD.style.top = position.y + "px";
	snrAD.style.zIndex = "20";*/
<%	
		} else if (buttonPressed.equals("showDetail")) {
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
		} else if (buttonPressed.equals("realloc")) {
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
<%	
		} else if (buttonPressed.startsWith("viewSiteConf")) {
			if (buttonPressed.equals("viewSiteConfI")) {
%>
	showImplementationDetail();
<%	
			} else if (buttonPressed.equals("viewSiteConfA")) {%>
	showAccessDetail();
<%			}%>
	showSiteConfiguation();
<%		} else if (buttonPressed.equals("showCRMDetail")) {
%>
	showCRMDetail();
	/*var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var snrCRM = document.getElementById("snrCRMDetail");
	snrCRM.style.display = "inline";
	snrCRM.style.left = position.x + "px";
	snrCRM.style.top = position.y + "px";
	snrCRM.style.zIndex = "20";*/
<%	
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
			if (buttonPressed.equals("viewComA")) {
%>
	showAccessDetail();
<%			} else if (buttonPressed.equals("viewComC")) {	%>
	showCRMDetail();
<%			}%>
	showViewCommentary();
<%	
		} else if (buttonPressed.startsWith("addCom")) {
			if (buttonPressed.equals("addComA")) {	%>
	showAccessDetail();
	showAddCommentary("snrAccessDetail");
	<%		} else if (buttonPressed.equals("addComC")) {	%>
	showCRMDetail();
	showAddCommentary("snrCRMDetail");
	<%		} else  {	%>
	showAddCommentary();
	<%	
			}
		} else if (buttonPressed.startsWith("viewSiteCom")) {
			vcTitle = "Site: " + site;
			if (buttonPressed.equals("viewSiteComA")) {	%>
	showAccessDetail();
	<%		} %>
	showViewCommentary();
<%		}
	}
%>
}

function showImplementationDetail() {
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var snrI = document.getElementById("snrImplementationDetail");
	snrI.style.display = "inline";
	snrI.style.left = position.x + "px";
	snrI.style.top = position.y + "px";
	snrI.style.zIndex = "20";
}

function showCRMDetail() {
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var snrCRM = document.getElementById("snrCRMDetail");
	snrCRM.style.display = "inline";
	snrCRM.style.left = position.x + "px";
	snrCRM.style.top = position.y + "px";
	snrCRM.style.zIndex = "20";
}

function showAccessDetail() {
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB2");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var anchor = document.getElementById("hAnchorB2");
	position = getPosition(anchor);
	var snrAD = document.getElementById("snrAccessDetail");
	snrAD.style.display = "inline";
	snrAD.style.left = position.x + "px";
	snrAD.style.top = position.y + "px";
	snrAD.style.zIndex = "20";
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
	} else if (btn == "cancel") {	
		if (!confirm("Please confirm cancellation of SNR " + selectedSNRId)) {
			return;
		}
	}
		
	document.getElementById("buttonPressed").value = btn;
	document.getElementById("snrId").value = selectedSNRId;
	document.getElementById("snrStatus").value = selectedSNRStatus;
	document.getElementById("historyInd").value = "false";
	document.getElementById("customerId").value = selectedCustomerId;
	document.getElementById("site").value = selectedSite;
	document.getElementById("nrId").value = selectedNRId;
	document.getElementById("f1").action = "updateSNRList";
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
		document.getElementById("f1").action = "updateSNRList";
		document.getElementById("f1").submit();
	}
}

function snrSelect(snrId, status, customerId, site, nrId) {
	document.getElementById("action").style.display = "inline";
<%if (title.equals(ServletConstants.UPDATE_ACCESS)) {%>
	document.getElementById("accessDetail").style.display = "inline";
	document.getElementById("viewSiteComU").style.display = "inline";
<%} else if (title.equals(ServletConstants.UPDATE_CRM)) {%>
	document.getElementById("crmDetail").style.display = "inline";
<%}%>
	document.getElementById("addComU").style.display = "inline";
	document.getElementById("viewComU").style.display = "inline";
	document.getElementById("viewSiteConf").style.display = "inline";
	selectedSNRId = snrId;
	selectedSNRStatus = status;
	selectedCustomerId = customerId;
	selectedSite = site;
	selectedNRId = nrId;
	document.getElementById("snrId").value = selectedSNRId;
	document.getElementById("site").value = selectedSite;
	document.getElementById("nrId").value = selectedNRId;
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
<%if (displayScreen.equals(ServletConstants.UPDATE_ACCESS)) {%>
<col width="100px"/>
<col width="120px"/>
<col width="85px"/>
<col width="85px"/>
<col width="80px"/>
<col width="215px"/>
<col width="35px"/>
<col width="370px"/>
<col width="110px"/>
<%} else {%>
<col width="100px"/>
<col width="120px"/>
<col width="85px"/>
<col width="85px"/>
<col width="150px"/>
<col width="215px"/>
<col width="30px"/>
<col width="330px"/>
<col width="85px"/>
<%}%>
<col width="50px"/>
</colgroup>
<tbody>
<tr>
		<th class="<%=hSiteClass%>" id="hAnchor" onClick="headerClick('hSite', false, 'hAnchor')" title="<%=hSiteTitle%>">Site</th>
		<th class="<%=hNRIdClass%>" id="hNRId" onClick="headerClick('hNRId', false)" title="<%=hNRIdTitle%>">NR Id</th>
		<th>Status</th>
		<th title="Implementation Status">Impl. Status</th>
<%if (displayScreen.equals(ServletConstants.UPDATE_ACCESS)) {%>
		<th class="<%=hAccessStatusClass%>" id="hAccessStatus" onClick="headerClick('hAccessStatus', false)" title="<%=hAccessStatusTitle%>">Acc. Status</th>
		<th>Job Type</th>
		<th title="Next PreCheck">NPC</th>
		<th>Field Engineers</th>
		<th>Scheduled Date</th>
<%} else {%>
		<th>CRQ/INC Reference</th>
		<th class="<%=hJobTypeClass%>" id="hJobType" onClick="headerClick('hJobType', false)" title="<%=hJobTypeTitle%>">Job Type</th>
		<th class="<%=hCRQStatusClass%>" id="hCRQStatus" onClick="headerClick('hCRQStatus', false)" title="<%=hCRQStatusTitle%>">CS</th>
		<th>Technologies</th>
		<th class="<%=hScheduledClass%>" id="hScheduled" onClick="headerClick('hScheduled', true)" title="<%=hScheduledTitle.toString()%>">Scheduled</th>
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
<%if (displayScreen.equals(ServletConstants.UPDATE_ACCESS)) {%>
<col width="100px"/>
<col width="120px"/>
<col width="85px"/>
<col width="85px"/>
<col width="80px"/>
<col width="215px"/>
<col width="35px"/>
<col width="370px"/>
<col width="110px"/>
<%} else {%>
<col width="100px"/>
<col width="120px"/>
<col width="85px"/>
<col width="85px"/>
<col width="150px"/>
<col width="215px"/>
<col width="30px"/>
<col width="330px"/>
<col width="85px"/>
<%}%>
<col width="50px"/>
</colgroup>
<tbody>
<%=uB.getSNRUpdateListHTML(snrId, displayScreen, filterSite, 
		filterNRId, filterAccessStatus, filterCRQStatus, 
		filterScheduledStart, filterScheduledEnd, filterJobType)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="accessDetail" onClick="tbClick('showAccessDetail')" onMouseOut="invertClass('accessDetail')" onMouseOver="invertClass('accessDetail')" style="float:right;display:none" class="menu2Item">Access Detail</div>
<div id="crmDetail" onClick="tbClick('showCRMDetail')" onMouseOut="invertClass('crmDetail')" onMouseOver="invertClass('crmDetail')" style="float:right;display:none" class="menu2Item">CRQ/INC Detail</div>
<div id="viewComU" onClick="tbClick('viewCom')" onMouseOut="invertClass('viewComU')" onMouseOver="invertClass('viewComU')" style="float:right;display:none" class="menu2Item">View Commentary</div>
<div id="addComU" onClick="tbClick('addCom<%=title.equals(ServletConstants.UPDATE_ACCESS)?"A":"C"%>')" onMouseOut="invertClass('addComU')" onMouseOver="invertClass('addComU')" style="float:right;display:none" class="menu2Item">Add Commentary</div>
<div id="viewSiteComU" onClick="tbClick('viewSiteComU')" onMouseOut="invertClass('viewSiteComU')" onMouseOver="invertClass('viewSiteComU')" style="float:right;display:none" class="menu2Item">View Site Commentary</div>
<div id="viewSiteConf" onClick="tbClick('viewSiteConf')" onMouseOut="invertClass('viewSiteConf')" onMouseOver="invertClass('viewSiteConf')" style="float:right;display:none" class="menu2Item">View Site Configuration</div>
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
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSite', 'ok', 'hAnchor')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSite', 'clear', 'hAnchor')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSite', 'clearAll', 'hAnchor')" value="Clear All" /></div>
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
<div id ="dfAccessStatus" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfAccessStatus', 'cancel')">x</div>
	<div style="clear:both">Access Status Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("AccessStatus", "filter", "filter", filterAccessStatus, accessStatusParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfAccessStatus', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfAccessStatus', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfAccessStatus', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfJobType" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfJobType', 'cancel')">x</div>
	<div style="clear:both">Job Type Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("JobType", "filter", "filter", filterJobType) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfJobType', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfJobType', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfJobType', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfCRQStatus" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfCRQStatus', 'cancel')">x</div>
	<div style="clear:both">CRQ Status Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("CRQStatus", "filter", "filter", filterCRQStatus, crqStatusParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCRQStatus', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCRQStatus', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCRQStatus', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfScheduled" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfScheduled', 'cancel')">x</div>
	<div style="clear:both">Scheduled From Filter</div>
	<div style="padding-bottom:10px">
		<input style="width:240px" type="text" id="filterScheduledStart" name="filterScheduledStart" value="<%=filterScheduledStart%>" />
		<img src="images/cal.gif" onclick="javascript:NewCssCal ('filterScheduledStart','ddMMyyyy','arrow',undefined,undefined,undefined,undefined,true)" style="cursor:pointer"/>
	</div>
	<div style="clear:both">Scheduled To Filter</div>
	<div style="padding-bottom:10px">
		<input style="width:240px" type="text" id="filterScheduledEnd" name="filterScheduledEnd" value="<%=filterScheduledEnd%>" />
		<img src="images/cal.gif" onclick="javascript:NewCssCal ('filterScheduledEnd','ddMMyyyy','arrow',undefined,undefined,undefined,undefined,true)" style="cursor:pointer"/>
	</div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfScheduled', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfScheduled', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfScheduled', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<!-- mask -->
<%@ include file="updateSNRListBlank.txt" %>
<%@ include file="workQueuesBlank.jsp" %>
<!-- view SNR commentary -->
<%@ include file="viewSNRCommentary.txt" %>
<!-- add SNR commentary -->
<%@ include file="addSNRCommentary.txt" %>
<!-- SNR Access Detail -->
<%@ include file="snrAccessDetail.txt" %>
<!-- Site Configuration -->
<%@ include file="viewSiteConfiguration.txt" %>
<!-- SNR CRM Detail -->
<%@ include file="snrCRMDetail.txt" %>
</form>
</body>
</html>