<%@ include file="header.jsp" %>
<%
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
boolean historyInd = request.getAttribute("historyInd")==null?false:((String)request.getAttribute("historyInd")).equals("true");
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String snrStatus = request.getAttribute("snrStatus")==null?"none":(String)request.getAttribute("snrStatus");
String snrCommentaryType = request.getAttribute("snrCommentaryType")==null?"none":(String)request.getAttribute("snrCommentaryType");
boolean disableSNRCommentaryType = false;
long preCheckId = request.getAttribute("preCheckId")==null?-1:Long.parseLong((String)request.getAttribute("preCheckId"));
long customerId = request.getAttribute("customerId")==null?-1:Long.parseLong((String)request.getAttribute("customerId"));
String site = request.getAttribute("site")==null?"-1":(String)request.getAttribute("site");
String action = "workQueues";
long historyDT = 0;
String nrId = request.getAttribute("nrId")==null?"none":(String)request.getAttribute("nrId");
boolean nrIdValid = request.getAttribute("nrIdValid")==null?false:((String)request.getAttribute("nrIdValid")).equals("true");
String[] nrIdParameters = {"N"};
String[] siteParameters = {"N"};
String filterSite = request.getAttribute("filterSite")==null?"All":(String)request.getAttribute("filterSite");
String hSiteTitle = filterSite.equals("All")?"Filter not set":("Current filter value: " + filterSite);	
String hSiteClass = filterSite.equals("All")?"thClick":"thClickS";	
String filterNRId = request.getAttribute("filterNRId")==null?"All":(String)request.getAttribute("filterNRId");
String hNRIdTitle = filterNRId.equals("All")?"Filter not set":("Current filter value: " + filterNRId);	
String hNRIdClass = filterNRId.equals("All")?"thClick":"thClickS";	
String filterStatus = request.getAttribute("filterStatus")==null?"All":(String)request.getAttribute("filterStatus");
String hStatusTitle = filterStatus.equals("All")?"Filter not set":("Current filter value: " + filterStatus);	
String hStatusClass = filterStatus.equals("All")?"thClick":"thClickS";	
String[] statusParameters = {"All"};
String filterImplementationStatus = request.getAttribute("filterImplementationStatus")==null?"All":(String)request.getAttribute("filterImplementationStatus");
String hImplementationStatusTitle = "Implementation Status - " + (filterImplementationStatus.equals("All")?"Filter not set":("Current filter value: " + filterImplementationStatus));	
String hImplementationStatusClass = filterImplementationStatus.equals("All")?"thClick":"thClickS";	
String[] implementationStatusParameters = {"All"};
String filterPCO = request.getAttribute("filterPCO")==null?"All":(String)request.getAttribute("filterPCO");
String hPCOTitle = "Pre-Check Outstanding - " + (filterPCO.equals("All")?"filter not set":("current filter value: " + filterPCO));	
String hPCOClass = filterPCO.equals("All")?"thClick":"thClickS";	
String filterPrevImpl = request.getAttribute("filterPrevImpl")==null?"All":(String)request.getAttribute("filterPrevImpl");
String hPrevImplTitle = "Previous partial or aborted implementation - " + (filterPrevImpl.equals("All")?"filter not set":("current filter value: " + filterPrevImpl));	
String hPrevImplClass = filterPrevImpl.equals("All")?"thClick":"thClickS";	
String filterJobType = request.getAttribute("filterJobType")==null?"All":(String)request.getAttribute("filterJobType");
String hJobTypeTitle = "Job Type - " + (filterJobType.equals("All")?"filter not set":("current filter value: " + filterJobType));	
String hJobTypeClass = filterJobType.equals("All")?"thClick":"thClickS";	
String filterCRQINCRaised = request.getAttribute("filterCRQINCRaised")==null?"All":(String)request.getAttribute("filterCRQINCRaised");
String hCRQINCRaisedTitle = "CRQ/INC Raised - " + (filterCRQINCRaised.equals("All")?"filter not set":("current filter value: " + filterCRQINCRaised));	
String hCRQINCRaisedClass = filterCRQINCRaised.equals("All")?"thClick":"thClickS";	
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
String efComplete = request.getAttribute("efComplete")==null?"false":(String)request.getAttribute("efComplete");
String vcTitle = "Site: " + site + ", NR Id: " + nrId;
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="workQueues.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.WORK_QUEUES%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value="<%=buttonPressed%>"/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="filterSNRId" id="filterSNRId" value="<%=snrId%>"/>
<input type="hidden" name="snrStatus" id="snrStatus" value="<%=snrStatus%>"/>
<input type="hidden" name="snrStatusNew" id="snrStatusNew" value=""/>
<input type="hidden" name="customerId" id="customerId" value="<%=customerId%>"/>
<input type="hidden" name="historyInd" id="historyInd" value="<%=historyInd?"true":"false"%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<input type="hidden" name="efComplete" id="efComplete" value="<%=efComplete%>"/>
<input type="hidden" name="whichFilter" id="whichFilter" value=""/>
<input type="hidden" name="snrStatusNew" id="snrStatusNew" value=""/>
<script language="javascript">
<!--
var selectedSNRId = <%=snrId%>;
var selectedNRId = "<%=nrId%>";
var selectedSNRStatus = "<%=snrStatus%>";
var selectedCustomerId = <%=customerId%>;
var selectedSite = "<%=site%>";
var selectedEFComplete = "<%=efComplete%>";

function thisScreenLoad() {
<%
	if (snrId != -1) {
%>
	snrSelect(<%=snrId%>, "<%=snrStatus%>", <%=customerId%>, "<%=site%>", "<%=nrId%>", <%=efComplete%>);
<%
		if (buttonPressed.equals("showDetail")) {
%>
	var header = document.getElementById("hSite");
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
		} else if (buttonPressed.equals("viewSiteConf")) {
%>
	showSiteConfiguationW();
<%	
		} else if (buttonPressed.equals("viewSiteCom")) {
%>
	showSiteConfiguationW();
	showViewCommentaryW();
<%	
		} else if ((buttonPressed.equals("updNRId")) || (buttonPressed.equals("updateNRIdSubmit"))) {
%>
	var header = document.getElementById("hSite");
	var position = getPosition(header);
	var uN = document.getElementById("updateNRId");
	uN.style.display = "inline";
	uN.style.left = position.x + "px";
	uN.style.top = position.y + "px";
	uN.style.zIndex = "10";
<%	
		} else if (buttonPressed.equals("updED")) {
%>
	var header = document.getElementById("hSite");
	var position = getPosition(header);
	var uE = document.getElementById("updateEFDT");
	uE.style.display = "inline";
	uE.style.left = position.x + "px";
	uE.style.top = position.y + "px";
	uE.style.zIndex = "10";
<%	
		} else if (buttonPressed.equals("viewCom")) {
%>
	showViewCommentaryW();
<%	
		} else if (buttonPressed.startsWith("addCom")) {
	%>
	showAddCommentaryW();
	<%	
		}
	}
%>
}

function showViewCommentaryW() {
	var header = document.getElementById("hSite");
	var position = getPosition(header);
	var snrBC = document.getElementById("snrBC");
	snrBC.style.display = "inline";
	snrBC.style.left = position.x + "px";
	snrBC.style.top = position.y + "px";
	snrBC.style.zIndex = "70";
	var snrC = document.getElementById("snrC");
	snrC.style.display = "inline";
	snrC.style.left = position.x + "px";
	snrC.style.top = position.y + "px";
	snrC.style.zIndex = "80";
}

function showAddCommentaryW(extraScreen) {
	if (extraScreen != null) {
		document.getElementById("extraScreen").value = extraScreen;
	}
	var header = document.getElementById("hSite");
	var position = getPosition(header);
	var snrB = document.getElementById("snrBC");
	snrBC.style.display = "inline";
	snrBC.style.left = position.x + "px";
	snrBC.style.top = position.y + "px";
	snrBC.style.zIndex = "70";
	var snrC = document.getElementById("snrC");
	snrC.style.display = "inline";
	snrC.style.left = position.x + "px";
	snrC.style.top = position.y + "px";
	snrC.style.zIndex = "80";
	header = document.getElementById("hComLUB");
	position = getPosition(header);
	var aC = document.getElementById("addCommentary");
	aC.style.display = "inline";
	aC.style.left = (position.x-41) + "px";
	aC.style.top = position.y + "px";
	aC.style.zIndex = "90";
}

function showSiteConfiguationW() {
	var header = document.getElementById("hSite");
	var position = getPosition(header);
	var snrBC = document.getElementById("snrB");
	snrBC.style.display = "inline";
	snrBC.style.left = position.x + "px";
	snrBC.style.top = position.y + "px";
	snrBC.style.zIndex = "50";
	var snrS = document.getElementById("snrS");
	snrS.style.display = "inline";
	snrS.style.left = position.x + "px";
	snrS.style.top = position.y + "px";
	snrS.style.zIndex = "60";
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
	}	
		
	document.getElementById("buttonPressed").value = btn;
	document.getElementById("snrId").value = selectedSNRId;
	document.getElementById("snrStatus").value = selectedSNRStatus;
	document.getElementById("historyInd").value = "false";
	document.getElementById("customerId").value = selectedCustomerId;
	document.getElementById("site").value = selectedSite;
	document.getElementById("f1").action = "workQueues";
	document.getElementById("f1").submit();
		
		
}

function filterClick(filterId, operation) {
	var filter = document.getElementById(filterId);
	filter.style.display = "none";
	filter.style.left = "0px";
	filter.style.top = "0px";
	if (operation != "cancel") {
		document.getElementById("whichFilter").value = "filter"+filterId.substring(2);
		document.getElementById("buttonPressed").value = operation;
		document.getElementById("f1").action = "workQueues";
		document.getElementById("f1").submit();
	}
}

function headerClick(headerId, showLeft) {
	var header = document.getElementById(headerId);
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
}

function snrSelect(snrId, status, customerId, site, nrId, efComplete) {
	document.getElementById("action").style.display = "inline";
	document.getElementById("detail").style.display = "inline";
	//document.getElementById("viewSNRHist").style.display = "inline";
	document.getElementById("viewSiteConf").style.display = "inline";
	document.getElementById("viewCom").style.display = "inline";
	document.getElementById("closeNR").style.display = "none";
<%if (!thisU.hasOnlyReadUserRoles()) {%>
    document.getElementById("addCom").style.display = "inline";
    if ((status == 'Awaiting Scheduling') || (status == 'On Hold')) {
    	document.getElementById("holdRel").style.display = "inline";
	} else {
		document.getElementById("holdRel").style.display = "none";
	}
<%} else {%>
		document.getElementById("holdRel").style.display = "none";
<%}
  if (thisU.hasUserRole(UserRole.ROLE_B_O_ENGINEER)) {%>
	/*if ((status == 'Requested') || (status == 'Scheduled')) { 
		document.getElementById("preCheckThis").style.display = "inline";
	} else {
		document.getElementById("preCheckThis").style.display = "none";
	}*/
<%} else {%>
		//document.getElementById("preCheckThis").style.display = "none";
<%}
  if ((thisU.hasUserRole(UserRole.ROLE_SCHEDULER)) || 
		  (thisU.hasUserRole(UserRole.ROLE_B_O_ENGINEER))) {%>
		document.getElementById("updNRId").style.display = "inline";
<%} else {%>
		document.getElementById("updNRId").style.display = "none";
<%}
  if ((thisU.hasUserRole(UserRole.ROLE_SCHEDULER)) || 
		  (thisU.hasUserRole(UserRole.ROLE_B_O_ENGINEER))) {%>
	document.getElementById("updED").style.display = "inline";
<%} else {%>
		document.getElementById("updED").style.display = "none";
<%}
  if (thisU.hasUserRole(UserRole.ROLE_PMO)) {%>
		if ((efComplete == true) && (status == '<%=ServletConstants.STATUS_COMPLETED%>'))  {
			document.getElementById("closeNR").style.display = "inline";
		}
<%}%> 
	selectedSNRId = snrId;
	selectedNRId = nrId;
	selectedSNRStatus = status;
	selectedCustomerId = customerId;
	selectedSite = site;
	selectedEFComplete = efComplete;
	document.getElementById("snrId").value = selectedSNRId;
	document.getElementById("nrId").value = selectedNRId;
	document.getElementById("filterSNRId").value = selectedSNRId;
	document.getElementById("snrStatus").value = selectedSNRStatus;
	document.getElementById("site").value = selectedSite;
	document.getElementById("customerId").value = selectedCustomerId;
	document.getElementById("efComplete").value = selectedEFComplete;
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
<col width="100px"/>
<col width="120px"/>
<col width="90px"/>
<col width="90px"/>
<col width="400px"/>
<col width="80px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="75px"/>
<col width="50px"/>
</colgroup>
<tbody>
<tr>
		<th class="<%=hSiteClass%>" id="hSite" onClick="headerClick('hSite', false)" title="<%=hSiteTitle%>">Site</th>
		<th class="<%=hNRIdClass%>" id="hNRId" onClick="headerClick('hNRId', false)" title="<%=hNRIdTitle%>">NR Id</th>
		<th class="<%=hStatusClass%>" id="hStatus" onClick="headerClick('hStatus', false)" title="<%=hStatusTitle%>">NR Status</th>
		<th class="<%=hImplementationStatusClass%>" id="hImplementationStatus" onClick="headerClick('hImplementationStatus', false)" title="<%=hImplementationStatusTitle%>">Impl. Status</th>
		<th class="<%=hJobTypeClass%>" id="hJobType" onClick="headerClick('hJobType', false)" title="<%=hJobTypeTitle%>">Job Type</th>
		<th class="<%=hScheduledClass%>" id="hScheduled" onClick="headerClick('hScheduled', true)" title="<%=hScheduledTitle.toString()%>">Scheduled</th>
		<th class="<%=hPrevImplClass%>" id="hPrevImpl" onClick="headerClick('hPrevImpl', true)" title="<%=hPrevImplTitle%>">P</th>
		<th title="Commentary Count">CC</th>
		<th title="Next PreCheck">NPC</th>
		<th class="<%=hCRQINCRaisedClass%>" id="hCRQINCRaised" onClick="headerClick('hCRQINCRaised', true)" title="<%=hCRQINCRaisedTitle%>">CIR</th>
		<th title="Site Access Confirmed">SAC</th>
		<th title="HOP Status">H</th>
		<th title="Evenflow Summary">Evenflow</th>
		<th>Select</th>
</tr>
</tbody>
</table>
<div style="margin:0;padding:0;border-collapse:collapse;width:1250px;height:420px;overflow-x:hidden;overflow-y:auto;"
>
<table style="width: 1250px;"
>
<colgroup>
<col width="100px"/>
<col width="120px"/>
<col width="90px"/>
<col width="90px"/>
<col width="400px"/>
<col width="80px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="15px"/>
<col width="15px"/>
<col width="15px"/>
<col width="15px"/>
<col width="15px"/>
<col width="50px"/>
</colgroup>
<tbody>
<%=uB.getWorkQueuesSNRSummaryListHTML(filterSite, 
		filterNRId, filterStatus, filterImplementationStatus, 
		filterPrevImpl, filterJobType, filterCRQINCRaised, 
		filterScheduledStart, filterScheduledEnd, snrId)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="detail" onClick="tbClick('showDetail')" onMouseOut="invertClass('detail')" onMouseOver="invertClass('detail')" style="float:right;display:none" class="menu2Item">View NR Detail</div>
<!--div id="viewSNRHist" onClick="menuClick('<%=ServletConstants.VIEW_SNR_HISTORY%>')" onMouseOut="invertClass('viewSNRHist')" onMouseOver="invertClass('viewSNRHist')" style="float:right;display:none" class="menu2Item">View NR History</div-->
<div id="viewSiteConf" onClick="tbClick('viewSiteConf')" onMouseOut="invertClass('viewSiteConf')" onMouseOver="invertClass('viewSiteConf')" style="float:right;display:none" class="menu2Item">View Site Configuration</div>
<div id="viewCom" onClick="tbClick('viewCom')" onMouseOut="invertClass('viewCom')" onMouseOver="invertClass('viewCom')" style="float:right;display:none" class="menu2Item">View Commentary</div>
<div id="addCom" onClick="tbClick('addCom')" onMouseOut="invertClass('addCom')" onMouseOver="invertClass('addCom')" style="float:right;display:none" class="menu2Item">Add Commentary</div>
<div id="holdRel" onClick="tbClick('holdRel')" onMouseOut="invertClass('holdRel')" onMouseOver="invertClass('holdRel')" style="float:right;display:none" class="menu2Item">Hold/Release</div>
<!--div id="preCheckThis" onClick="tbClick('preCheckThis')" onMouseOut="invertClass('preCheckThis')" onMouseOver="invertClass('preCheckThis')" style="float:right;display:none" class="menu2Item">Pre-Check</div-->
<div id="updNRId" onClick="tbClick('updNRId')" onMouseOut="invertClass('updNRId')" onMouseOver="invertClass('updNRId')" style="float:right;display:none" class="menu2Item">Update NR Id</div>
<div id="updED" onClick="tbClick('updED')" onMouseOut="invertClass('updED')" onMouseOver="invertClass('updED')" style="float:right;display:none" class="menu2Item">Update Evenflow Dates</div>
<div id="closeNR" onClick="tbClick('closeNR')" onMouseOut="invertClass('closeNR')" onMouseOver="invertClass('closeNR')" style="float:right;display:none" class="menu2Item">Close NR</div>
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
<div id ="dfImplementationStatus" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfImplementationStatus', 'cancel')">x</div>
	<div style="clear:both">Implementation Status Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("ImplementationStatus", "filter", "filter", filterImplementationStatus, implementationStatusParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfImplementationStatus', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfImplementationStatus', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfImplementationStatus', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfPCO" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfPCO', 'cancel')">x</div>
	<div style="clear:both">Pre-Check Outstanding Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("PCO", "filter", "filter", filterPCO) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPCO', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPCO', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPCO', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfPrevImpl" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfPrevImpl', 'cancel')">x</div>
	<div style="clear:both">Prvious Implementation Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("PrevImpl", "filter", "filter", filterPrevImpl) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPrevImpl', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPrevImpl', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPrevImpl', 'clearAll')" value="Clear All" /></div>
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
<div id ="dfCRQINCRaised" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfCRQINCRaised', 'cancel')">x</div>
	<div style="clear:both">CRQ/INC Raised Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("CRQINCRaised", "filter", "filter", filterCRQINCRaised) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCRQINCRaised', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCRQINCRaised', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCRQINCRaised', 'clearAll')" value="Clear All" /></div>
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
<!-- end of filters -->
<!-- mask -->
<%@ include file="workQueuesBlank.jsp" %>
<!-- SNR detail -->
<%@ include file="viewSNRDetail.txt" %>
<!-- view SNR commentary -->
<%@ include file="viewSNRCommentary.txt" %>
<!-- add SNR commentary -->
<%@ include file="addSNRCommentary.txt" %>
<!-- Site Configuration -->
<%@ include file="viewSiteConfiguration.txt" %>
<!-- Update NR Id -->
<%@ include file="updateNRId.txt" %>
<!-- Update Evenflow Dates -->
<%@ include file="updateEFDT.txt" %>
</form>
</body>
</html>