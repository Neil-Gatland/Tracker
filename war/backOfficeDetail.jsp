<%@ include file="headerLD.jsp" %>
<% 
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
String currentBO = thisU.getFullname();
String filterBOEngineer = request.getAttribute("filterBOEngineer")==null?currentBO:(String)request.getAttribute("filterBOEngineer");
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String currentDate = uB.getCurrentDate();
String week = request.getAttribute("week")==null?"":(String)request.getAttribute("week");
String weekAction = request.getAttribute("weekAction")==null?"NOW":(String)request.getAttribute("weekAction");
String showSchedule = request.getAttribute("showSchedule")==null?"N":(String)request.getAttribute("showSchedule");
String showOSWork = request.getAttribute("showOSWork")==null?"N":(String)request.getAttribute("showOSWork");
String currentTab = request.getAttribute("currentTab")==null?"":(String)request.getAttribute("currentTab");
String preCheckUpdates = request.getAttribute("preCheckUpdates")==null?"":(String)request.getAttribute("preCheckUpdates");
String addDetsOpen = request.getAttribute("addDetsOpen")==null?"":(String)request.getAttribute("addDetsOpen");
String returnHome = request.getAttribute("returnHome")==null?"":(String)request.getAttribute("returnHome");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="backOfficeDetail.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.BACK_OFFICE_DETAIL%>"/>
<input type="hidden" name="filterBOEngineer" id="filterBOEngineer" value="<%=filterBOEngineer%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="week" id="week" value="<%=week%>"/>
<input type="hidden" name="weekAction" id="weekAction" value="<%=weekAction%>"/>
<input type="hidden" name="showSchedule" id="showSchedule" value="<%=showSchedule%>"/>
<input type="hidden" name="showOSWork" id="showOSWork" value="<%=showOSWork%>"/>
<input type="hidden" name="currentTab" id="currentTab" value="<%=currentTab%>"/>
<input type="hidden" name="progressItem" id="progressItem" value=""/>
<input type="hidden" name="currentStatus" id="currentStatus" value=""/>
<input type="hidden" name="issueOwner" id="issueOwner" value=""/>
<input type="hidden" name="riskIndicator" id="riskIndicator" value=""/>
<input type="hidden" name="progressIssue" id="progressIssue" value=""/>
<input type="hidden" name="closureCode" id="closureCode" value=""/>
<input type="hidden" name="newStatus" id="newStatus" value=""/>
<input type="hidden" name="preCheckUpdates" id="preCheckUpdates" value=""/>
<input type="hidden" name="commentary" id="commentary" value=""/>
<input type="hidden" name="precheckId" id="preCheckId" value=""/>
<input type="hidden" name="pendingUpdates" id ="pendingUpdates" value="N"/>
<input type="hidden" name="pendingCompletion" id ="pendingCompletion" value="N"/>
<input type="hidden" name="siteDataRequested" id="siteDataRequested" value=""/>
<input type="hidden" name="idfRequested" id="idfRequested" value=""/>
<input type="hidden" name="cellsPlanned2G" id="cellsPlanned2G" value=""/>
<input type="hidden" name="cellsPlanned3G" id="cellsPlanned3G" value=""/>
<input type="hidden" name="cellsPlanned4G" id="cellsPlanned4G" value=""/>
<input type="hidden" name="newAlarms" id="newAlarms" value=""/>
<input type="hidden" name="healthChecks" id="healthChecks" value=""/>
<input type="hidden" name="hopCompleted" id="hopCompleted" value=""/>
<input type="hidden" name="sfrCompleted" id="sfrCompleted" value=""/>
<input type="hidden" name="implementationStatus" id="implementationStatus" value=""/>
<input type="hidden" name="implementationEndDT" id="implementationEndDT" value=""/>
<input type="hidden" name="abortType" id="abortType" value=""/>
<input type="hidden" name="technologyId" id ="technologyId" value=""/>
<input type="hidden" name="addDetsOpen" id="addDetsOpen" value="<%=addDetsOpen%>"/>
<input type="hidden" name="preTestCallsDone" id="preTestCallsDone" value=""/>
<input type="hidden" name="postTestCallsDone" id="postTestCallsDone" value=""/>
<input type="hidden" name="performanceChecks" id="performanceChecks" value=""/>
<input type="hidden" name="hopUploaded" id="hopUploaded" value=""/>
<input type="hidden" name="sfrUploaded" id="sfrUploaded" value=""/>
<script type="text/javascript">

function thisScreenLoad() {	
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		//document.getElementById("additionalDetails").style.display = "inline";
		var header = document.getElementById("generalAnchor");
		var position = getPosition(header);
		var dT = document.getElementById("additionalDetails");
		dT.style.display = "inline";
		dT.style.left = position.x + "px";
		dT.style.top = position.y + "px";
		dT.style.zIndex = "120";
	}
	if ("<%=returnHome%>"=="Y") {
		document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE%>";
		document.getElementById("f1").action = "backOffice";
		document.getElementById("f1").submit();
	} 
}

function checkForReturn(boEngineer) {
	if (confirm("Return to home page as "+boEngineer)) {
		var addDetsOpen = document.getElementById("addDetsOpen").value;
		if (addDetsOpen=="Y") {
			alert("Please close additional details screen");
			return;
		}
		var preCheckUpdates = document.getElementById("preCheckUpdates").value;
		if (!(preCheckUpdates=="")) {
			if (!confirm("There are outstanding precheck updates. Press OK if you want to return and lose these updates?")) {
				return;
			}
		}
		var pendingUpdates = document.getElementById("pendingUpdates").value;
		if (pendingUpdates=="Y") {
			if (!confirm("There are outstanding updates. Press OK if you want to return and lose these updates?")) {
				return;
			}
		}
		var pendingCompletion = document.getElementById("pendingCompletion").value;
		if (pendingCompletion=="Y") {

			if (!confirm("There is a pending completion. Press OK if you want to return and ignore this")) {
				return;
			}
		}
		document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE%>";
		document.getElementById("f1").action = "backOffice";
		document.getElementById("f1").submit();	
	}
}

function goBack() {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	if (!(preCheckUpdates=="")) {
		if (!confirm("There are outstanding precheck updates. Press OK if you want to return and lose these updates?")) {
			return;
		}
	}
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="Y") {
		if (!confirm("There are outstanding updates. Press OK if you want to return and lose these updates?")) {
			return;
		}
	}
	var pendingCompletion = document.getElementById("pendingCompletion").value;
	if (pendingCompletion=="Y") {

		if (!confirm("There is a pending completion. Press OK if you want to return and ignore this")) {
			return;
		}
	}
	document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE%>";
	document.getElementById("f1").action = "backOffice";
	document.getElementById("f1").submit();	
}

function setCurrentTab(selectedTab) {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	if (!(preCheckUpdates=="")) {
		if (!confirm("There are outstanding precheck updates. Press OK if you want to change tabs and lose these updates?")) {
			return;
		}
	}
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="Y") {

		if (!confirm("There are outstanding updates. Press OK if you want to change tabs and lose these updates?")) {
			return;
		}
	}
	var pendingCompletion = document.getElementById("pendingCompletion").value;
	if (pendingCompletion=="Y") {

		if (!confirm("There is a pending completion. Press OK if you want to change tabs and ignore this")) {
			return;
		}
	}
	document.getElementById("currentTab").value = selectedTab;
	document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE%>";
	document.getElementById("f1").action = "backOfficeDetail";
	document.getElementById("f1").submit();	
}

function updateSiteProgress(snrId,progressItem,currentStatus,issueOwner,closureCode) {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	if (!(preCheckUpdates=="")) {
		if (!confirm("There are outstanding precheck updates. Press OK if you want to update site progress and lose these updates?")) {
			return;
		}
	}
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="Y") {

		if (!confirm("There are outstanding updates. Press OK if you want to update site progress and lose these updates?")) {
			return;
		}
	}
	var pendingCompletion = document.getElementById("pendingCompletion").value;
	if (pendingCompletion=="Y") {

		if (!confirm("There is a pending completion. Press OK if you want to update site progress and ignore this")) {
			return;
		}
	}
	document.getElementById("progressItem").value = progressItem;
	document.getElementById("crqClosureCode").style.display = "none";	
	document.getElementById("displayProgressItem").style.width = "100%";
	var progressItemDesc = progressItem;
	if (progressItem=="checkedIn") 
		progressItemDesc = "Checked In";
	else if (progressItem=="bookedOn") 
		progressItemDesc = "Site Booked On";
	else if (progressItem=="bookedOn") 
		progressItemDesc = "Site Booked On";
	else if (progressItem=="siteAccessed")
		progressItemDesc = "Site Accessed";
	else if (progressItem=="physicalChecks")			
		progressItemDesc = "Physical Checks";
	else if (progressItem=="preCallTest")
		progressItemDesc = "Pre Call Test";
	else if (progressItem=="siteLocked")
		progressItemDesc = "Site Locked";
	else if (progressItem=="hwInstalls")
		progressItemDesc = "HW Installs";
	else if (progressItem=="commissioningFE")
		progressItemDesc = "Field Commissioning";
	else if (progressItem=="commissioningBO")
		progressItemDesc = "Back Office Commissioning";
	else if (progressItem=="commissioningBO")
		progressItemDesc = "Back Office Commissioning";
	else if (progressItem=="txProvisioning")
		progressItemDesc = "Tx Provisioning";
	else if (progressItem=="fieldWork")
		progressItemDesc = "Field Work";
	else if (progressItem=="siteUnlocked")
		progressItemDesc = "Site Unlocked";
	else if (progressItem=="postCallTest")
		progressItemDesc = "Post Call Test";
	else if (progressItem=="closureCode") {
		progressItemDesc = "Closure Code";
		document.getElementById("crqClosureCode").style.display = "inline";
		document.getElementById("crqClosureCode").value = closureCode;
		document.getElementById("displayProgressItem").style.width = "45%";
	} else if (progressItem=="leaveSite")
		progressItemDesc = "Left Site";
	else if (progressItem=="bookOffSite")
		progressItemDesc = "Site Booked Off";
	else if (progressItem=="performanceMonitoring")
		progressItemDesc = "Performance";
	else if (progressItem=="initialHOP")
		progressItemDesc = "Hand Over Pack";	
	document.getElementById("displayProgressItem").value = progressItemDesc;	
	document.getElementById("currentStatus").value = currentStatus;	
	document.getElementById("progressItem").value = progressItem;
	document.getElementById("issueOwner").value = issueOwner;
	document.getElementById("buttonPressed").value = "updateSiteProgressItem";
	var header = document.getElementById(progressItem+"Anchor");
	var position = getPosition(header);
	var aR = document.getElementById("UpdateSiteProgressItem");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "10";
}

function updateIssueOwner(snrId, issueOwner) {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}	
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	if (!(preCheckUpdates=="")) {
		if (!confirm("There are outstanding precheck updates. Press OK if you want to update issue owner and lose these updates?")) {
			return;
		}
	}
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="Y") {

		if (!confirm("There are outstanding updates. Press OK if you want to update issue owner and lose these updates?")) {
			return;
		}
	}
	var pendingCompletion = document.getElementById("pendingCompletion").value;
	if (pendingCompletion=="Y") {

		if (!confirm("There is a pending completion. Press OK if you want to update issue ownerand ignore this")) {
			return;
		}
	}
	document.getElementById("issueOwner").value = issueOwner;
	document.getElementById("buttonPressed").value = "updateIssueOwner";
	var header = document.getElementById("IssueOwnerAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("UpdateIssueOwnerId");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "20";
}

function updateRiskIndicator(snrId, riskIndicator) {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	if (!(preCheckUpdates=="")) {
		if (!confirm("There are outstanding precheck updates. Press OK if you want to update risk indicator and lose these updates?")) {
			return;
		}
	}
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="Y") {

		if (!confirm("There are outstanding updates. Press OK if you want to update risk indicator and lose these updates?")) {
			return;
		}
	}
	var pendingCompletion = document.getElementById("pendingCompletion").value;
	if (pendingCompletion=="Y") {

		if (!confirm("There is a pending completion. Press OK if you want to update risk indicator and ignore this")) {
			return;
		}
	}
	document.getElementById("riskIndicator").value = riskIndicator;
	document.getElementById("buttonPressed").value = "updateRiskIndicator";
	var header = document.getElementById("RiskIndicatorAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("UpdateRiskIndicatorId");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "30";
}

function updateProgressIssue(snrId, progressIssue) {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	if (!(preCheckUpdates=="")) {
		if (!confirm("There are outstanding precheck updates. Press OK if you want to update progress issue and lose these updates?")) {
			return;
		}
	}
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="Y") {

		if (!confirm("There are outstanding updates. Press OK if you want to update progress issue and lose these updates?")) {
			return;
		}
	}
	var pendingCompletion = document.getElementById("pendingCompletion").value;
	if (pendingCompletion=="Y") {

		if (!confirm("There is a pending completion. Press OK if you want to update progress issue and ignore this")) {
			return;
		}
	}
	document.getElementById("progressIssueNew").value = progressIssue;
	document.getElementById("progressIssue").value = progressIssue;
	document.getElementById("buttonPressed").value = "updateProgressIssue";
	var header = document.getElementById("leaveSiteAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("UpdateProgressIssueId");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "40";
}

function recordPreCheckUpdate(preCheckId,control,column,storageType) {
	var newValue = document.getElementById(control).value;
	var preCheckIdNum = preCheckId.toString();
	var update = preCheckIdNum + ":" + column + ":"+ storageType + ":" + newValue + ";";
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	document.getElementById("preCheckUpdates").value = preCheckUpdates + update;
}

function flagPendingUpdates() {
	document.getElementById("pendingUpdates").value = "Y";	
}

function flagPendingCompletion() {
	document.getElementById("pendingCompletion").value = "Y";	
}

function updatePreCheck() {
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	if (preCheckUpdates=="") {
		alert("There are no outstanding precheck changes to apply");
		return
	} 		
	document.getElementById("buttonPressed").value = "updatePreCheck";
	document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE%>";
	document.getElementById("f1").action = "backOfficeDetail";
	document.getElementById("f1").submit();	
}

function updateImplementation() {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (!(pendingUpdates=="Y")) {
		alert('No pending updates to apply');
	}
	document.getElementById("buttonPressed").value = "Update <%=currentTab%>";
	if ("<%=currentTab%>"=="Planning and Scripting") {
		document.getElementById("siteDataRequested").value = document.getElementById("siteDataRequestedSelect").value;
		document.getElementById("idfRequested").value = document.getElementById("idfRequestedSelect").value;
		document.getElementById("cellsPlanned2G").value = document.getElementById("cellsPlanned2GSelect").value;
		document.getElementById("cellsPlanned3G").value = document.getElementById("cellsPlanned3GSelect").value;
		document.getElementById("cellsPlanned4G").value = document.getElementById("cellsPlanned4GSelect").value;
	} else if ("<%=currentTab%>"=="I and C Integration") {
		document.getElementById("newAlarms").value = document.getElementById("newAlarmsSelect").value;
		document.getElementById("healthChecks").value = document.getElementById("healthChecksSelect").value;
		document.getElementById("hopCompleted").value = document.getElementById("HOPCompletedSelect").value;
		document.getElementById("sfrCompleted").value = document.getElementById("SFRCompletedSelect").value;
		document.getElementById("implementationStatus").value = document.getElementById("implementationStatusSelect").value;
		document.getElementById("abortType").value = document.getElementById("abortTypeSelect").value;
	} else if ("<%=currentTab%>"=="Performance Monitoring") {
		document.getElementById("performanceChecks").value = document.getElementById("performanceChecksSelect").value;
	} else if ("<%=currentTab%>"=="Sign Off and HOP") {
		document.getElementById("hopUploaded").value = document.getElementById("HOPUploadedSelect").value;
		document.getElementById("sfrUploaded").value = document.getElementById("SFRUploadedSelect").value;
	}
	document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE_DETAIL%>";
	document.getElementById("f1").action = "backOfficeDetail";
	document.getElementById("f1").submit();		
}

function toggleTechnology(technologyId) {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="Y") {

		if (!confirm("There are outstanding updates. Press OK if you want to update the technology and lose these updates?")) {
			return;
		}
	}
	var pendingCompletion = document.getElementById("pendingCompletion").value;
	if (pendingCompletion=="Y") {

		if (!confirm("There is a pending completion. Press OK if you want to update the technology and ignore this")) {
			return;
		}
	}	
	document.getElementById("technologyId").value = technologyId;
	document.getElementById("buttonPressed").value = "toggleTechnology";
	document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE_DETAIL%>";
	document.getElementById("f1").action = "backOfficeDetail";
	document.getElementById("f1").submit();		
}

function completeImplementation() {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var pendingCompletion = document.getElementById("pendingCompletion").value;
	if (!(pendingCompletion=="Y")) {
		alert('No pending completion details to apply');
		return;
	}
	var implementationStatus = document.getElementById("implementationStatusSelect").value;
	if (implementationStatus=="") {
		alert("Implementation status must be supplied");
		return
	}
	var abortType = document.getElementById("abortTypeSelect").value;
	if ((implementationStatus=="Aborted")&&(abortType=="")) {
		alert("Abort type must be supplied if implementation status is 'Aborted'");
		return;
	}
	var abortType = document.getElementById("abortTypeSelect").value;
	if ((!(implementationStatus=="Aborted"))&&(!(abortType==""))) {
		alert("Abort type must be not supplied for implementation status '"+implementationStatus+"'");
		return;
	}
	var impStartDT = document.getElementById("impStartDT").value;
	var impEndDT = document.getElementById("impEndDT").value;
	if ((impStartDT=="")||(impEndDT=="")) {
		alert("Implementation start and end must be supplied");
		return;
	}
	var outageStartDT = document.getElementById("outageStartDT").value;
	var outageEndDT = document.getElementById("outageEndDT").value;
	if (((outageStartDT=="")||(outageEndDT==""))&&(!(implementationStatus=="Aborted"))) {
		alert("Outage start and end must be supplied for implementation status '"+implementationStatus+"'");
		return;
	}
	var siteIssues = document.getElementById("siteIssues").value;
	if (siteIssues=="") {
		alert("Site issues must be populated for a completion");
		return
	}
	document.getElementById("implementationStatus").value = document.getElementById("implementationStatusSelect").value;
	document.getElementById("implementationEndDT").value = document.getElementById("impEndDT").value;
	document.getElementById("abortType").value = document.getElementById("abortTypeSelect").value;
	document.getElementById("completionComment").value = "";
	var header = document.getElementById("generalAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("ConfirmCompleteId");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "80";
}

function completePreCheck(preCheckId) {
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	if (!(preCheckUpdates=="")) {
		if (!confirm("There are outstanding precheck updates. Press OK if you want to complete and lose these updates?")) {
			return;
		}
	}
	document.getElementById("PreCheckCompletionComment").value = "";
	var header = document.getElementById("generalAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("CompletePreCheckId");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "50";
}

function addPreCheckCommentary(preCheckId) {
	var preCheckUpdates = document.getElementById("preCheckUpdates").value;
	if (!(preCheckUpdates=="")) {
		if (!confirm("There are outstanding precheck updates. Press OK if you want to add commentary and lose these updates?")) {
			return;
		}
	}
	document.getElementById("AddComment").value = "";
	document.getElementById("preCheckId").value = preCheckId;
	var header = document.getElementById("generalAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("AddCommentaryId");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "60";
}

function addImplementationCommentary() {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="Y") {

		if (!confirm("There are outstanding updates. Press OK if you want to add commentary and lose these updates?")) {
			return;
		}
	}
	var pendingCompletion = document.getElementById("pendingCompletion").value;
	if (pendingCompletion=="Y") {

		if (!confirm("There is a pending completion. Press OK if you want to add commentary and ignore this")) {
			return;
		}
	}
	document.getElementById("AddComment").value = "";
	document.getElementById("preCheckId").value = "-1";
	var header = document.getElementById("generalAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("AddCommentaryId");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "60";
}

function viewCommentary() {
	var addDetsOpen = document.getElementById("addDetsOpen").value;
	if (addDetsOpen=="Y") {
		alert("Please close additional details screen");
		return;
	}
	var header = document.getElementById("generalAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("ViewCommentaryId");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "70";
}

function moveToSite(snrId,tab) {
	document.getElementById("snrId").value=snrId;
	document.getElementById("buttonPressed").value = "passThrough";
	document.getElementById("currentTab").value = tab;
	document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE%>";
	document.getElementById("f1").action = "backOfficeDetail";
	document.getElementById("f1").submit();		
}

</script>
<div style="width: 1250px;">
<table style="table-layout: fixed; border-style: none; width:1250px;">
<colgroup>
	<col width="10px"/>
	<col width="140px"/>
	<col width="25px"/>
	<col width="40px"/>
	<col width="140px"/>
	<col width="25px"/>
	<col width="40px"/>
	<col width="140px"/>
	<col width="25px"/>
	<col width="40px"/>
	<col width="140px"/>
	<col width="25px"/>
	<col width="40px"/>
	<col width="140px"/>
	<col width="25px"/>
	<col width="40px"/>
	<col width="140px"/>
	<col width="25px"/>
	<col width="40px"/>
	<col width="10px"/>
</colgroup>
<tbody>
	<tr>
		<td colspan="2" height="5px"></td>
	</tr>
	<%=uB.getBackOfficeDetailTabHTML(snrId, filterBOEngineer, currentTab) %>
</tbody>
</table>
<table style="table-layout: fixed; border-style: none; width:1250px;">
<colgroup>
	<col width="10px"/>
	<col width="200px"/>
	<col width="10px"/>
	<col width="10px"/>
	<col width="1020px"/>
	<col width="10px"/>
</colgroup>
<tbody>
	<tr>
		<td colspan="7" height="1px"></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td class="clientBox">
			<table style="table-layout: fixed; border-style: none; width:200px;">
			<colgroup>
				<col width="10px"/>
				<col width="80px"/>
				<col width="10px"/>
				<col width="90px"/>
				<col width="10px"/>
			<colgroup>
			<tbody>
				<%=uB.getBOSiteProgressHTML(snrId) %>
			</tbody>
			</table>
		</td>
		<td>&nbsp;</td>
		<% if (uB.getIssueOwner(snrId).equals("None")) { %>
		<td class="clientBox">&nbsp;</td>
		<td class="clientBox">
		<% } else { %>
		<td class="clientBoxIssue">&nbsp;</td>
		<td class="clientBoxIssue">
		<% } %>
			<table style="table-layout: fixed; border-style: none;">
			<colgroup>
				<col width="30%"/>
				<col width="10%"/>
				<col width="60%"/>
			</colgroup>
			<tbody>
				<tr>
					<td id="generalAnchor" name="generalAnchor" class="boHeaderSite">
						<%=uB.getSiteOrNRId(snrId)%><br><%=uB.getIssueStatusHTML(snrId) %>
					</td>
					<td class="boHeader" style="cursor:pointer" onClick="checkForReturn('<%=filterBOEngineer%>')">					
						<% if(!currentBO.equals(filterBOEngineer)) { %>
						Switched to <br> <%=filterBOEngineer%>
						<% } else { %>
						&nbsp;
				<%} %>
					</td>
					<td align="right">
						<div class="scImage">
							<img src="images/bo_sc_header.png" height="75%" width="75%">
							<h9>
								<table style="table-layout: fixed; border-style: none;">
									<col width="50%"/>
									<col width="40%"/>
								<tbody>
								<tr>
									<td>&nbsp;</td>
									<td align="center"><%=uB.getProjectName(snrId)%></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td align="center"><%=uB.getScheduledDate(snrId)%></td>							
								</tr>
								</tbody>
								</table>
							</h9>							
						</div>
					</td>		
				</tr>
				<tr>
					<td colspan="3">
						<table style="table-layout: fixed; border-style: none; width: 1020px">
						<colgroup>
							<col width="5px"/>
							<col width="680px"/>
							<col width="15px"/>
							<col width="300px"/>
							<col width="15px"/>
						</colgroup>
						<tbody>
							<tr>
								<td height="2px">
							</tr>
							<tr>
								<td height="400px">&nbsp;</td>
								<td class="whiteBoxBO" valign="top">
									<%=uB.getBackOfficeDetailHTML(snrId, currentTab)%>
								</td>
								<td>&nbsp;</td>
								<td class="whiteBoxBO" valign="top">
									<div id="OtherSiteList" style="margin: 0; padding: 0; overflow-y: auto; 
										overflow-x: hidden; display; inline; 
										width: 300px; height: 450px;"/>
									<table style="table-layout:fixed;border-style:none:width:300px;">
										<colgroup>
											<col width="90px"/>
											<col width="140px"/>
											<col width="70px"/>
										</colgroup>
										<tbody>
											<tr>
												<td class="otherSiteHeader">Other Site</td>
												<td class="otherSiteHeader">Project</td>
												<td class="otherSiteHeader" title="Scheduled Date">Sch. Date</td>
											</tr>
											<%=uB.listOtherSitesHTML(snrId, currentTab, filterBOEngineer)%>
										</tbody>
										</table>
									</div>
								</td>
								<td>&nbsp;</td>
							</tr>
						</tbody>
						</table>
					</td>
				</tr>
			</tbody>
			</table>
		</td>
		<td class="clientBox">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</tbody>
</table>
</div>
<!--  INCLUDES  -->
<%@ include file="updateSiteProgressItem.txt" %>
<%@ include file="updateIssueOwner.txt" %>
<%@ include file="updateRiskIndicator.txt" %>
<%@ include file="updateProgressIssue.txt" %>
<%@ include file="backOfficeCompletePreCheck.txt" %>
<%@ include file="backOfficeAddCommentary.txt" %>
<%@ include file="backOfficeViewCommentary.txt" %>
<%@ include file="backOfficeConfirmComplete.txt" %>
<%@ include file="backOfficeAddTechnologies.txt" %>
<%@ include file="backOfficeDelTechnologies.txt" %>
<%@ include file="backOfficeAdditionalDetails.txt" %>
<%@ include file="backOfficeChangeFE.txt" %>
</form>
</body>
</html>