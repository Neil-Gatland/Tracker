<%@ include file="header.jsp" %>
<% 
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
String snrStatus = request.getAttribute("snrStatus")==null?"none":(String)request.getAttribute("snrStatus");
String site = request.getAttribute("site")==null?"none":(String)request.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"none":(String)request.getAttribute("nrId");
String currentBO = thisU.getFullname();
String testEmail = currentBO.equals("General.Support")?"Email Test":"";
String currentDate = uB.getCurrentDate();
String displayDate = uB.getCurrentDateDisplay();
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String action = "homeBO";
String filterBOEngineer = request.getAttribute("filterBOEngineer")==null?currentBO:(String)request.getAttribute("filterBOEngineer");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="homeBO.jsp"/>
<input type="hidden" name="filterBOEngineer" id="filterBOEngineer" value="<%=filterBOEngineer%>"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.HOME_BO%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<input type="hidden" name="snrStatus" id="snrStatus" value="<%=snrStatus%>"/>
<input type="hidden" name="prevScreen" id="prevScreen" value=""/>
<script language="javascript">

var selectedSNRId = <%=snrId%>;
var selectedSNRStatus = <%=snrStatus%>;
var selectedSite = <%=site%>;
var selectedNRId = <%=nrId%>;

function thisScreenLoad() {
	if (selectedSNRId != -1){
		snrSelect(<%=snrId%>,"<%=snrStatus%>","<%=site%>","<%=nrId%>");
	}	
}

function tbClick(btn) {
	if ((btn == "showImplementationDetail")||(btn == "showImplementationDetail2"))	{
		document.getElementById("toScreen").value = "<%=ServletConstants.CONFIRM_IMPLEMENTATION%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	} else if ((btn == "siteProgress")||(btn == "siteProgress2")) {
		document.getElementById("toScreen").value = "<%=ServletConstants.SITE_PROGRESS%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	} else if (btn == "snrHist") {
		document.getElementById("toScreen").value = "<%=ServletConstants.VIEW_SNR_HISTORY%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	} else if (btn == "accessDetail") {
		document.getElementById("toScreen").value = "<%=ServletConstants.VIEW_ACCESS_DETAIL%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	} else if (btn == "showPreCheck") {
		document.getElementById("toScreen").value = "<%=ServletConstants.PRE_CHECK_MAINTENANCE%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	} else if (btn == "changeBOEngineer") {
		var header = document.getElementById("hChgBO");
		var position = getPosition(header);
		var aR = document.getElementById("chgBOEngineer");
		aR.style.display = "inline";
		aR.style.left = position.x + "px";
		aR.style.top = position.y + "px";
		aR.style.zIndex = "10";
	} else if ((btn == "viewCommentary")||(btn == "viewCommentary2") ) {
		document.getElementById("toScreen").value = "<%=ServletConstants.VIEW_COMMENTARY_DETAIL%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	}
}

function snrSelect(snrId,snrStatus,site,nrId) {
	document.getElementById("showImplementationDetail2").style.display = "none";
	document.getElementById("siteProgress2").style.display = "none";
	document.getElementById("viewCommentary2").style.display = "none";
	document.getElementById("showPreCheck").style.display = "none";
	if ((snrStatus == "<%=ServletConstants.STATUS_SCHEDULED%>") ||
			(snrStatus == "<%=ServletConstants.STATUS_PERFORMANCE_IP%>")||
			(snrStatus == "<%=ServletConstants.STATUS_COMPLETED%>")) {
		document.getElementById("showImplementationDetail").style.display = "inline";
		document.getElementById("siteProgress").style.display = "inline";
	} else {
		document.getElementById("showImplementationDetail").style.display = "none";
		document.getElementById("siteProgress").style.display = "none";
	}
	document.getElementById("accessDetail").style.display = "inline";
	document.getElementById("snrHist").style.display = "inline";
	document.getElementById("viewCommentary").style.display = "inline";
	selectedSNRId = snrId;
	selectedSNRStatus = snrStatus;
	selectedSite = site;
	selectedNRId = nrId;
	document.getElementById("snrId").value = selectedSNRId;
	document.getElementById("snrStatus").value = selectedSNRStatus;
	document.getElementById("prevScreen").value = "homeBO";
	document.getElementById("site").value = site;
	document.getElementById("nrId").value = nrId;
}

function osWorkSelect(snrId,snrStatus,site,nrId,hopStatus, sfrStatus, efOS, pcOS) {
	document.getElementById("showImplementationDetail").style.display = "none";
	document.getElementById("siteProgress").style.display = "none";
	document.getElementById("viewCommentary").style.display = "none";
	document.getElementById("accessDetail").style.display = "none";
	document.getElementById("snrHist").style.display = "none";
	if (((efOS=="Y")||(hopStatus!="Y")||(sfrStatus!="Y"))&&(snrStatus == "<%=ServletConstants.STATUS_COMPLETED%>")) {
		document.getElementById("showImplementationDetail2").style.display = "inline"; 
			document.getElementById("siteProgress2").style.display = "inline";
	} else {
		document.getElementById("showImplementationDetail2").style.display = "none";
		document.getElementById("siteProgress2").style.display = "none";
	}
	if ((pcOS=="Y")&&(snrStatus == "<%=ServletConstants.STATUS_SCHEDULED%>")) {
		document.getElementById("showPreCheck").style.display = "inline";
	} else {
		document.getElementById("showPreCheck").style.display = "none";
	}
	document.getElementById("viewCommentary2").style.display = "inline";
	selectedSNRId = snrId;
	selectedSNRStatus = snrStatus;
	selectedSite = site;
	selectedNRId = nrId;
	document.getElementById("snrId").value = selectedSNRId;
	document.getElementById("snrStatus").value = selectedSNRStatus;
	document.getElementById("prevScreen").value = "homeBO";
	document.getElementById("site").value = site;
	document.getElementById("nrId").value = nrId;
}

function gotoAnchor(thisAnchor) {
	var aLink = document.getElementById("aLink");
	aLink.href = "#" + thisAnchor;
	aLink.click();
}

function testMail(boName) {
	if (boName=="General.Support") {
		var header = document.getElementById("hAnchor");
		var position = getPosition(header);
		var aR = document.getElementById("emailTest");
		aR.style.display = "inline";
		aR.style.left = position.x + "px";
		aR.style.top = position.y + "px";
		aR.style.zIndex = "10";
	} 
}

function emailCopy() {
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("emailCopy");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "10";
}

</script>
<div style="width:1270px;margin:0 auto;margin-top:10px; 
overflow-y: auto; overflow-x: hidden; border: 1px solid black; height: 460px;">
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
<col width="15px"/>
</colgroup>
<tbody>
<tr>		
	<th id="hAnchor" class="menu1" colspan="3" align="left">Daily Site Schedule</th>
	<th class="menu1" colspan="3"  align="left"><%=displayDate%></th>
	<th id="hChgBO" onClick="tbClick('changeBOEngineer')" class="menu1" 
			title="Current BO Engineer - click to change" colspan="3"  align="left"><%=filterBOEngineer%></th>
	<th class="menu1" colspan="2"  onClick="emailCopy()" align="left">View Email Copies</th>
	<th id = "hETAnchor" class="menu1" onClick="testMail('<%=currentBO%>')" colspan="3"  align="left"><%=testEmail%></th>
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="35px"/>
<col width="40px"/>
<col width="35px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
<col width="15px"/>
</colgroup>
<tbody>
<tr class="altbar">		
	<th class="altBar">Site</th>
	<th class="altBar">NR Id</th>
	<th class="altBar">Status</th>
	<th class="altBar">Job Type</th>
	<th class="altBar" title="Migration Type">Mig. Type</th>
	<th class="altBar" title="Technologies">Tech(s)</th>
	<th class="altBar" title="Hardware Vendor">HW Vendor</th>
	<th class="altBar">FE(s)</th>
	<th class="altBar">VF CRQ</th>
	<th class="altBar">TEF CRQ</th>
	<th class="altBar" title="Access Status">Access</th>
	<th class="altBar" >BO Engineer(s)</th>
	<th class="altBar" title="No. Site Visits">SV</th>
	<th class="altBar" title="Select">&nbsp;</th>	
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse; width: 1250px; 
overflow-x: hidden; overflow-y: auto; border: 1px solid black;; max-height: 200px">
<table style="width: 1250px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="35px"/>
<col width="40px"/>
<col width="35px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
<col width="15px"/>
</colgroup>
<tbody>
<%=uB.getDailySiteScheduleHTML(snrId, filterBOEngineer,currentDate)%>
</tbody>
</table>
</div>
<div id="tm" class="menu2" style="width:1250px;border: 1px solid black;">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:inline;" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="viewCommentary" onClick="tbClick('viewCommentary')" onMouseOut="invertClass('viewCommentary')" onMouseOver="invertClass('viewCommentary')" style="float:right;display:none" class="menu2Item">View Commentary</div>
<div id="siteProgress" onClick="tbClick('siteProgress')" onMouseOut="invertClass('siteProgress')" onMouseOver="invertClass('siteProgress')" style="float:right;display:none" class="menu2Item">Site Progress</div>
<div id="showImplementationDetail" onClick="tbClick('showImplementationDetail')" onMouseOut="invertClass('showImplementationDetail')" onMouseOver="invertClass('showImplementationDetail')" style="float:right;display:none" class="menu2Item">Implementation Detail</div>
<div id="accessDetail" onClick="tbClick('accessDetail')" onMouseOut="invertClass('accessDetail')" onMouseOver="invertClass('accessDetail')" style="float:right;display:none" class="menu2Item">Show Access Detail</div>
<div id="snrHist" onClick="tbClick('snrHist')" onMouseOut="invertClass('snrHist')" onMouseOver="invertClass('snrHist')" style="float:right;display:none" class="menu2Item">View NR History</div>
<div id="uOW" onClick="gotoAnchor('outstandingWorks')" onMouseOut="invertClass('uOW')" onMouseOver="invertClass('uOW')" style="float:right" class="menu2Item">Outstanding Works</div>
<div id="uWS" onClick="gotoAnchor('weeklySchedule')" onMouseOut="invertClass('uWS')" onMouseOver="invertClass('uWS')" style="float:right" class="menu2Item">Weekly Schedule</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div style="width:1250px;margin:0 auto;margin-top:10px; border: 1px solid black;">
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="50px"/>
<col width="25px"/>
<col width="10px"/>
<col width="10px"/>
<col width="10px"/>
<col width="10px"/>
</colgroup>
<tbody>
<tr>		
	<th id="outstandingWorks" class="menu1" colspan="13" align="left">Outstanding Works</th>
</tr>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="50px"/>
<col width="25px"/>
<col width="10px"/>
<col width="10px"/>
<col width="10px"/>
<col width="10px"/>
</colgroup>
<tbody>
<tr>		
	<th class="altBar" id="hAnchor2">Site</th>
	<th class="altBar">NR Id</th>
	<th class="altBar">Status</th>
	<th class="altBar">Job Type</th>
	<th class="altBar" title="Scheduled Date">Sch. Date</th>
	<th class="altBar" title="Technologies">Tech(s)</th>
	<th class="altBar" title="Hardware Vendor">HW Vendor</th>
	<th class="altBar">BO Engineer(s)</th>
	<th class="altBar">Evenflow</th>
	<th class="altBar" title="HOP Status">HOP</th>
	<th class="altBar" title="SFR Status">SFR</th>
	<th class="altBar" title="Next PreCheck">NPC</th>
	<th class="altBar" title="Select">&nbsp;</th>	
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse; width: 1250px; 
overflow-x: hidden; overflow-y: auto; border: 1px solid black;; max-height: 200px">
<table style="width: 1250px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="50px"/>
<col width="5px"/>
<col width="5px"/>
<col width="5px"/>
<col width="5px"/>
<col width="5px"/>
<col width="10px"/>
<col width="10px"/>
<col width="10px"/>
<col width="10px"/>
</colgroup>
<tbody>
<%=uB.getOutstandingWorksHTML(filterBOEngineer)%>
</tbody>
</table>
</div>
<div id="tm2" class="menu2" style="width:1250px;border: 1px solid black;">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action2" style="float:left;display:inline;" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="viewCommentary2" onClick="tbClick('viewCommentary2')" onMouseOut="invertClass('viewCommentary')" onMouseOver="invertClass('viewCommentary2')" style="float:right;display:none" class="menu2Item">View Commentary</div>
<div id="siteProgress2" onClick="tbClick('siteProgress2')" onMouseOut="invertClass('siteProgress2')" onMouseOver="invertClass('siteProgress2')" style="float:right;display:none" class="menu2Item">Site Progress</div>
<div id="showImplementationDetail2" onClick="tbClick('showImplementationDetail2')" onMouseOut="invertClass('showImplementationDetail2')" onMouseOver="invertClass('showImplementationDetail2')" style="float:right;display:none" class="menu2Item">Implementation Detail</div>
<div id="showPreCheck" onClick="tbClick('showPreCheck')" onMouseOut="invertClass('showPreCheck')" onMouseOver="invertClass('showPreCheck')" style="float:right;display:none" class="menu2Item">Pre-Check</div>
<div id="uDSS" onClick="gotoAnchor('dailySiteSchedule')" onMouseOut="invertClass('uDSS')" onMouseOver="invertClass('uDSS')" style="float:right" class="menu2Item">Daily Site Schedule</div>
<div id="uWS" onClick="gotoAnchor('weeklySchedule')" onMouseOut="invertClass('uWS')" onMouseOver="invertClass('uWS')" style="float:right" class="menu2Item">Weekly Schedule</div>
<div id="tmAnchor2" class="menu2">&nbsp;</div>
</div>
<div style="width:1250px;margin:0 auto;margin-top:10px;">
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
</colgroup>
<tbody>
<tr>		
	<th id="weeklySchedule" class="menu1" colspan="14" align="left">Weekly Schedule</th>
</tr>
<tr>		
	<th class="menu1" colspan="1" align="left">&nbsp;</th>
	<th class="menu1" colspan="13"  align="left">Current Week</th>
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
</colgroup>
<tbody>
<tr>		
	<th class="altBar" id="hAnchor3">Site</th>
	<th class="altBar">NR Id</th>
	<th class="altBar">Status</th>
	<th class="altBar">Job Type</th>
	<th class="altBar" title="Scheduled Date">Sch. Date</th>
	<th class="altBar" title="Migration Type">Mig. Type</th>
	<th class="altBar" title="Technologies">Tech(s)</th>
	<th class="altBar" title="Hardware Vendor">HW Vendor</th>
	<th class="altBar">FE(s)</th>
	<th class="altBar">VF CRQ</th>
	<th class="altBar">TEF CRQ</th>
	<th class="altBar" title="Access Status">Access</th>
	<th class="altBar">BO Engineer(s)</th>
	<th class="altBar" title="No. Site Visits">SV</th>
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse; width: 1250px; 
overflow-x: hidden; overflow-y: auto; border: 1px solid black;; max-height: 200px">
<table style="width: 1250px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
</colgroup>
<tbody>
<%=uB.getWeeklyScheduleHTML(filterBOEngineer,1)%>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
</colgroup>
<tbody>
<tr>		
	<th class="menu1" colspan="1" align="left">&nbsp;</th>
	<th class="menu1" colspan="13"  align="left">Next Week</th>
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
</colgroup>
<tbody>
<tr>		
	<th class="altBar" id="hAnchor4">Site</th>
	<th class="altBar">NR Id</th>
	<th class="altBar">Status</th>
	<th class="altBar">Job Type</th>
	<th class="altBar" title="Scheduled Date">Sch. Date</th>
	<th class="altBar" title="Migration Type">Mig. Type</th>
	<th class="altBar" title="Technologies">Tech(s)</th>
	<th class="altBar" title="Hardware Vendor">HW Vendor</th>
	<th class="altBar">FE(s)</th>
	<th class="altBar">VF CRQ</th>
	<th class="altBar">TEF CRQ</th>
	<th class="altBar" title="Access Status">Access</th>
	<th class="altBar">BO Engineer(s)</th>
	<th class="altBar" title="No. Site Visits">SV</th>
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse; width: 1250px; 
overflow-x: hidden; overflow-y: auto; border: 1px solid black;; max-height: 200px">
<table style="width: 1250px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
</colgroup>
<tbody>
<%=uB.getWeeklyScheduleHTML(filterBOEngineer,2)%>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
</colgroup>
<tbody>
<tr>		
	<th class="menu1" colspan="1" align="left">&nbsp;</th>
	<th class="menu1" colspan="13"  align="left">Following Week</th>
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
</colgroup>
<tbody>
<tr>		
	<th class="altBar" id="hAnchor5">Site</th>
	<th class="altBar">NR Id</th>
	<th class="altBar">Status</th>
	<th class="altBar">Job Type</th>
	<th class="altBar" title="Scheduled Date">Sch. Date</th>
	<th class="altBar" title="Migration Type">Mig. Type</th>
	<th class="altBar" title="Technologies">Tech(s)</th>
	<th class="altBar" title="Hardware Vendor">HW Vendor</th>
	<th class="altBar">FE(s)</th>
	<th class="altBar">VF CRQ</th>
	<th class="altBar">TEF CRQ</th>
	<th class="altBar" title="Access Status">Access</th>
	<th class="altBar">BO Engineer(s)</th>
	<th class="altBar" title="No. Site Visits">SV</th>
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse; width: 1250px; 
overflow-x: hidden; overflow-y: auto; border: 1px solid black;; max-height: 200px">
<table style="width: 1250px;  table-layout: fixed;">
<colgroup>
<col width="20px"/>
<col width="30px"/>
<col width="30px"/>
<col width="50px"/>
<col width="30px"/>
<col width="20px"/>
<col width="40px"/>
<col width="40px"/>
<col width="60px"/>
<col width="35px"/>
<col width="35px"/>
<col width="30px"/>
<col width="50px"/>
<col width="10px"/>
</colgroup>
<tbody>
<%=uB.getWeeklyScheduleHTML(filterBOEngineer,3)%>
</tbody>
</table>
</div>
<div id="tm" class="menu2"  style="width:1250px;border: 1px solid black;margin:0 auto;margin-top:">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:inline;" class="menu2">Action:</div>
<div id="uDSS" onClick="gotoAnchor('dailySiteSchedule')" onMouseOut="invertClass('uDSS')" onMouseOver="invertClass('uDSS')" style="float:right" class="menu2Item">Daily Site Schedule</div>
<div id="uOW" onClick="gotoAnchor('outstandingWorks')" onMouseOut="invertClass('uOW')" onMouseOver="invertClass('uOW')" style="float:right" class="menu2Item">Outstanding Works</div>
<div id="uWS" onClick="gotoAnchor('weeklySchedule')" onMouseOut="invertClass('uWS')" onMouseOver="invertClass('uWS')" style="float:right" class="menu2Item">Weekly Schedule</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
</div>
</div>
<a href="" id="aLink" name="aLink" style="display:none"></a>
<!-- includes -->
<%@ include file="chgBOEngineer.txt" %>
<%@ include file="emailCopy.txt" %>
<%@ include file="emailTest.txt" %>
</form>
</body>
</html>