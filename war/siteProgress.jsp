<%@ include file="header.jsp" %>
<%
String returnScreen = request.getAttribute("returnScreen")==null?"":(String)request.getAttribute("returnScreen");
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
String snrStatus = request.getAttribute("snrStatus")==null?"Scheduled":(String)request.getAttribute("snrStatus");
String site = request.getAttribute("site")==null?"none":(String)request.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"none":(String)request.getAttribute("nrId");
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="siteProgress.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.SITE_PROGRESS%>"/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<input type="hidden" name="snrStatus" id="snrStatus" value="<%=snrStatus%>"/>
<input type="hidden" name="prevScreen" id="prevScreen" value=""/>
<input type="hidden" name="returnScreen" id="returnScreen" value="<%=returnScreen%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<input type="hidden" name="checkedIn" id="checkedIn" value=""/>
<input type="hidden" name="bookedOn" id="bookedOn" value=""/>
<input type="hidden" name="siteAccessed" id="siteAccessed" value=""/>
<input type="hidden" name="physicalChecks" id="physicalChecks" value=""/>
<input type="hidden" name="preCallTest" id="preCallTest" value=""/>
<input type="hidden" name="siteLocked" id="siteLocked" value=""/>
<input type="hidden" name="hwInstalls" id="hwInstalls" value=""/>
<input type="hidden" name="commissioningFE" id="commissioningFE" value=""/>
<input type="hidden" name="commissioningBO" id="commissioningBO" value=""/>
<input type="hidden" name="txProvisioning" id="txProvisioning" value=""/>
<input type="hidden" name="fieldWork" id="fieldWork" value=""/>
<input type="hidden" name="siteUnlocked" id="siteUnlocked" value=""/>
<input type="hidden" name="postCallTest" id="postCallTest" value=""/>
<input type="hidden" name="closureCode" id="closureCode" value=""/>
<input type="hidden" name="leaveSite" id="leaveSite" value=""/>
<input type="hidden" name="bookOffSite" id="bookOffSite" value=""/>
<input type="hidden" name="performanceMonitoring" id="performanceMonitoring" value=""/>
<input type="hidden" name="initialHOP" id="initialHOP" value=""/>
<input type="hidden" name="issueOwner" id="issueOwner" value=""/>
<input type="hidden" name="crqClosureCode" id="crqClosureCode" value=""/>
<input type="hidden" name="riskIndicator" id="riskIndicator" value=""/>
<input type="hidden" name="progressIssue" id="progressIssue" value=""/>
<script language="javascript">
<!--
var selectedSnrId = "<%=snrId%>";
var selectedReturnScreen = "<%=returnScreen%>";
var selectedSNRStatus = "<%=snrStatus%>";
var selectedSite = "<%=site%>";
var selectedNRId = "<%=nrId%>";

function thisScreenLoad() {	
} 

function tbClick(btn) {
	document.getElementById("prevScreen").value = "siteProgress";
	if (btn == "closed")	{
		if (selectedReturnScreen=="workQueues.jsp") {
			document.getElementById("toScreen").value = "<%=ServletConstants.CONFIRM_IMPLEMENTATION%>";	
		} else {
			document.getElementById("toScreen").value = "<%=ServletConstants.HOME_BO%>";
		};
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	} else if (btn == "update") {
		document.getElementById("buttonPressed").value = "update";
		document.getElementById("checkedIn").value = document.getElementById("selectCheckedIn").value;
		document.getElementById("bookedOn").value = document.getElementById("selectBookedOn").value;
		document.getElementById("siteAccessed").value = document.getElementById("selectSiteAccessed").value;
		document.getElementById("physicalChecks").value = document.getElementById("selectPhysicalChecks").value;
		document.getElementById("preCallTest").value = document.getElementById("selectPreCallTest").value;
		document.getElementById("siteLocked").value = document.getElementById("selectSiteLocked").value;
		document.getElementById("hwInstalls").value = document.getElementById("selectHWInstalls").value;
		document.getElementById("commissioningFE").value = document.getElementById("selectCommissioningFE").value;
		document.getElementById("commissioningBO").value = document.getElementById("selectCommissioningBO").value;
		document.getElementById("txProvisioning").value = document.getElementById("selectTXProvisioning").value;
		document.getElementById("fieldWork").value = document.getElementById("selectFieldWork").value;
		document.getElementById("siteUnlocked").value = document.getElementById("selectSiteUnlocked").value;
		document.getElementById("postCallTest").value = document.getElementById("selectPostCallTest").value;
		document.getElementById("closureCode").value = document.getElementById("selectClosureCode").value;
		document.getElementById("leaveSite").value = document.getElementById("selectLeaveSite").value;
		document.getElementById("bookOffSite").value = document.getElementById("selectBookOffSite").value;
		document.getElementById("performanceMonitoring").value = document.getElementById("selectPerformanceMonitoring").value;
		document.getElementById("initialHOP").value = document.getElementById("selectInitialHOP").value;
		document.getElementById("issueOwner").value = document.getElementById("selectIssueOwner").value;
		document.getElementById("crqClosureCode").value = document.getElementById("currentCRQClosureCode").value;
		document.getElementById("crqClosureCode").value = document.getElementById("currentCRQClosureCode").value;
		document.getElementById("riskIndicator").value = document.getElementById("selectRiskIndicator").value;
		document.getElementById("progressIssue").value = document.getElementById("progressIssueAmended").value;
		var issueCount = 0;
		if (document.getElementById("selectCheckedIn").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectBookedOn").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectSiteAccessed").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectPhysicalChecks").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectPreCallTest").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectSiteLocked").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectHWInstalls").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectCommissioningFE").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectCommissioningBO").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectTXProvisioning").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectFieldWork").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectSiteUnlocked").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectPostCallTest").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectClosureCode").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectLeaveSite").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectBookOffSite").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectPerformanceMonitoring").value == "Issue") { issueCount = issueCount + 1; }
		if (document.getElementById("selectInitialHOP").value == "Issue") { issueCount = issueCount + 1; }
		if (issueCount > 1 ){
			alert("Only one item can have an issue, please correct");	
		} else if ( (issueCount == 1) && 
				(document.getElementById("selectIssueOwner").value != "Both") && 
				(document.getElementById("selectIssueOwner").value != "Customer") && 
				(document.getElementById("selectIssueOwner").value != "Devoteam") ) {
			alert("Issue owner has not been selected for the current issue");
		} else if ( (issueCount == 0) && 
				( (document.getElementById("selectIssueOwner").value == "Both") || 
				  (document.getElementById("selectIssueOwner").value == "Customer") || 
				  (document.getElementById("selectIssueOwner").value == "Devoteam") ) ) {
			alert("Issue owner cannot be selected if there is not an issue");	
		} else {
			// OK to process update
			document.getElementById("toScreen").value = "<%=ServletConstants.SITE_PROGRESS%>";
			document.getElementById("f1").action = "siteProgress";
			document.getElementById("f1").submit();
		}	
	} 
}

-->
</script>
<div style="width:1250px;margin:0 auto;margin-top:10px;">
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px;height: 460px;
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;  table-layout: fixed;">
<colgroup>
<col width="14%"/>
<col width="13%"/>
<col width="12%"/>
<col width="11%"/>
<col width="14%"/>
<col width="13%"/>
<col width="12%"/>
<col width="11%"/>
</colgroup>
<tbody>
<tr>
<th colspan="8" class="menu1" align="center">Site Progress Detail</th>
</tr>
<%=uB.getSiteProgressHTML(snrId)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="closed" onClick="tbClick('closed')" onMouseOut="invertClass('closed')" onMouseOver="invertClass('closed')" style="float:right;display:inline" class="menu2Item">Return</div>
<div id="update" onClick="tbClick('update')" onMouseOut="invertClass('update')" onMouseOver="invertClass('update')" style="float:right;display:inline" class="menu2Item">Update</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</form>
</body>
</html>