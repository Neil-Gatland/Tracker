<%@ include file="headerFE.jsp" %>
<%
String snrId = request.getAttribute("snrId")==null?"":(String)request.getAttribute("snrId");
String operation = request.getAttribute("operation")==null?"":(String)request.getAttribute("operation");
String userMessage = request.getAttribute("userMessage")==null?"":(String)request.getAttribute("userMessage");
String site = request.getAttribute("site")==null?"":(String)request.getAttribute("site");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="homeFE.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.HOME_FE%>"/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="operation" id="operation" value="<%=operation%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="selectedStatus" id="selectedStatus" value=""/>
<script language="javascript">
<!--
var selectedSnrId = "<%=snrId%>";
var selectedSite = "<%=site%>";
var selectedOperation = "<%=operation%>";
var userMessage = "<%=userMessage%>";

function thisScreenLoad() {
	if ((!userMessage=='')&&(!userMessage=='Logon Successful')) {
		alert(userMessage);
	}
	if (!selectedSnrId=='') {
		// Block actions that the FE cannot update
		if ( 	(selectedOperation=='fieldCommissioningBO') ||
		   		(selectedOperation=='txProvisioning') ||
		   		(selectedOperation=='performanceMonitoring') ||
		   		(selectedOperation=='closureCode') ||
		   		(selectedOperation=='initialHOP') ) {
			alert("You are not authorised to update this item");
		} else {
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
	<col width="61%"/>
	<col width="39%"/>
<tbody>
<tr>
	<td colspan="2" height="5px" class="ldTitle9"></td>
</tr><tr>
	<td class="ldTitle9">&nbsp;</td>
	<td class="ldTitle9">Welcome, <%=thisU.getNameForLastUpdatedBy()%></td>
</tr><tr>
	<td colspan="2" height="5px" class="ldTitle9"></td>
</tr><tr>
	<td colspan="2" height="15px" class="ldFEWhite" title="Press to pick up any changes"
		style="cursor:pointer;" onClick="refresh()">
		Refresh
	</td>
</tr><tr>
	<td colspan="2" height="15px" class="ldTitle9"></td>
</tr>
</tbody>
</colgroup>
</table>
<%=uB.getFELiveDashboardSitesHTML() %>
<%@ include file="feUpdateProgressStatus.txt" %>
</form>
</body>
</html>