<%@ include file="headerFE.jsp" %>
<%
String snrId = request.getAttribute("snrId")==null?"":(String)request.getAttribute("snrId");
String operation = request.getAttribute("operation")==null?"":(String)request.getAttribute("operation");
String userMessage = request.getAttribute("userMessage")==null?"":(String)request.getAttribute("userMessage");
String site = request.getAttribute("site")==null?"":(String)request.getAttribute("site");
String showAccess = request.getAttribute("showAccess")==null?"":(String)request.getAttribute("showAccess");
String showCRQ = request.getAttribute("showCRQ")==null?"":(String)request.getAttribute("showCRQ");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="homeFE.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.HOME_FE%>"/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="operation" id="operation" value="<%=operation%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="selectedStatus" id="selectedStatus" value=""/>
<input type="hidden" name="showAccess" id="showAccess" value=""/>
<input type="hidden" name="showCRQ" id="showCRQ" value=""/>
<script language="javascript">
<!--
var selectedSnrId = "<%=snrId%>";
var selectedSite = "<%=site%>";
var selectedOperation = "<%=operation%>";
var userMessage = "<%=userMessage%>";
var showAccess = "<%=showAccess%>";
var showCRQ = "<%=showCRQ%>";

function thisScreenLoad() {
	if ((!userMessage=='')&&(!userMessage=='Logon Successful')) {
		alert(userMessage);
	}
	if (showAccess=='Y') {
		var header = document.getElementById("anchor"+selectedSnrId);
		var position = getPosition(header);
		var aUPS = document.getElementById("feAccessDetails");
		aUPS.style.display = "inline";
		aUPS.style.left = position.x + "px";
		aUPS.style.top = position.y + "px";
		document.getElementById("showAccess").value = "N";	
	} else if (showCRQ=='Y') {
		var header = document.getElementById("anchor"+selectedSnrId);
		var position = getPosition(header);
		var aUPS = document.getElementById("feCRQDetails");
		aUPS.style.display = "inline";
		aUPS.style.left = position.x + "px";
		aUPS.style.top = position.y + "px";
		document.getElementById("showCRQ").value = "N";			
	} else if (!selectedSnrId=='') {
		// Block actions that the FE cannot update
		if ( 	(selectedOperation=='fieldCommissioningBO') ||
		   		(selectedOperation=='txProvisioning') ||
		   		(selectedOperation=='performanceMonitoring') ||
		   		(selectedOperation=='closureCode') ||
		   		(selectedOperation=='initialHOP') ) {
			alert("You are not authorised to update this item");
		} else if (!(selectedOperation=='toggle')) {
			var header = document.getElementById("anchor"+selectedSnrId);
			var position = getPosition(header);
			var aUPS = document.getElementById("feUpdateProgressStatus");
			aUPS.style.display = "inline";
			aUPS.style.left = position.x + "px";
			aUPS.style.top = position.y + "px";
		}
	}
}

function toggleExpandCollapse(site) {
	document.getElementById("operation").value = "toggle";
	document.getElementById("site").value = site;
	document.getElementById("toScreen").value = "<%=ServletConstants.HOME_FE%>";
	document.getElementById("f1").action = "homeFE";
	document.getElementById("f1").submit();
}

function refresh() {
	document.getElementById("operation").value = "";
	document.getElementById("snrId").value = "";
	document.getElementById("toScreen").value = "<%=ServletConstants.HOME_FE%>";
	document.getElementById("f1").action = "homeFE";
	document.getElementById("f1").submit();	
}

function accessDetails(snrId) {
	document.getElementById("snrId").value = snrId;
	document.getElementById("showAccess").value = "Y";
	document.getElementById("operation").value = "showAccess";
	document.getElementById("toScreen").value = "<%=ServletConstants.HOME_FE%>";
	document.getElementById("f1").action = "homeFE";
	document.getElementById("f1").submit();	
}

function crqDetails(snrId) {
	document.getElementById("snrId").value = snrId;
	document.getElementById("showCRQ").value = "Y";
	document.getElementById("operation").value = "showCRQ";
	document.getElementById("toScreen").value = "<%=ServletConstants.HOME_FE%>";
	document.getElementById("f1").action = "homeFE";
	document.getElementById("f1").submit();
}

function updateProgress(operation,snrId,colour) {
	// Block actions that the FE cannot update
	if ( 	(operation=='commissioningBO') ||
	   		(operation=='txProvisioning') ||
	   		(operation=='closureCode') ||
	   		(operation=='performanceMonitoring') ||
	   		(operation=='initialHOP') ) {
		alert("You are not authorised to update this item");
	} else if (colour=='Red') {
		alert("You cannot remove an issue once started");
	} else {
		document.getElementById("operation").value = operation;
		document.getElementById("snrId").value = snrId;
		document.getElementById("toScreen").value = "<%=ServletConstants.HOME_FE%>";
		document.getElementById("f1").action = "homeFE";
		document.getElementById("f1").submit();
	}
}

-->
</script>
<table  style="table-layout:fixed;border-style:none;width:100%;">
<colgroup>
	<col width="100%"/>
<tbody>
<tr>
	<td height="15px"></td>
</tr><tr>
	<td class="FEName">Welcome, <%=thisU.getNameForLastUpdatedBy()%></td>
</tr><tr>
	<td height="5px"></td>
</tr><tr>
	<td height="30px" class="ldFEWhite" title="Press to pick up any changes"
		style="cursor:pointer;" onClick="refresh()">
		Refresh
	</td>
</tr><tr>
	<td height="15px"></td>
</tr>
</tbody>
</colgroup>
</table>
<%=uB.getFELiveDashboardSitesHTML() %>
<%@ include file="feUpdateProgressStatus.txt" %>
<%@ include file="feAccessDetails.txt" %>
<%@ include file="feCRQDetails.txt" %>
</form>
</body>
</html>