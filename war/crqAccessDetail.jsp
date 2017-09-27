<%@ include file="headerLD.jsp" %>
<% 
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String showRaiseCRQ = request.getAttribute("showRaiseCRQ")==null?"N":(String)request.getAttribute("showRaiseCRQ");
String showCloseCRQ = request.getAttribute("showCloseCRQ")==null?"N":(String)request.getAttribute("showCloseCRQ");
String showOtherCRQ = request.getAttribute("showOtherCRQ")==null?"N":(String)request.getAttribute("showOtherCRQ");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="backOffice.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.CRQ_ACCESS_DETAIL%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="showRaiseCRQ" id="showRaiseCRQ" value="<%=showRaiseCRQ%>"/>
<input type="hidden" name="showCloseCRQ" id="showCloseCRQ" value="<%=showCloseCRQ%>"/>
<input type="hidden" name="showOtherCRQ" id="showOtherCRQ" value="<%=showOtherCRQ%>"/>
<input type="hidden" name="pendingUpdates" id ="pendingUpdates" value="N"/>
<input type="hidden" name="crqStatus" id="crqStatus" value=""/>
<input type="hidden" name="tefOutageRequired" id="tefOutageRequired" value=""/>
<input type="hidden" name="p1Site" id="p1Site" value=""/>
<input type="hidden" name="crIN" id="crIN" value=""/>
<input type="hidden" name="twoManSite" id="twoManSite" value=""/>
<input type="hidden" name="crUsed" id="crUsed" value=""/>
<input type="hidden" name="oohWeekend" id="oohWeekend" value=""/>
<input type="hidden" name="accessStatus" id="accessStatus" value=""/>
<input type="hidden" name="obass" id="obass" value=""/>
<input type="hidden" name="rams" id="rams" value=""/>
<input type="hidden" name="vfArrangeAccess" id="vfArrangeAccess" value=""/>
<input type="hidden" name="accessConfirmed" id="accessConfirmed" value=""/>
<input type="hidden" name="escort" id="escort" value=""/>
<input type="hidden" name="healthAndSafety" id="healthAndSafety" value=""/>
<script type="text/javascript">

function goBack() {
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="Y") {
		if (!confirm("There are outstanding updates. Press OK if you want to return and lose these updates")) {
			return;
		}
	}
	document.getElementById("toScreen").value = "<%=ServletConstants.CRQ_ACCESS%>";
	document.getElementById("f1").action = "navigation";
	document.getElementById("f1").submit();	
}

function flagPendingUpdates() {
	document.getElementById("pendingUpdates").value = "Y";	
}

function update(snrId) {
	var pendingUpdates = document.getElementById("pendingUpdates").value;
	if (pendingUpdates=="N") {
		alert("No updates to existring details made");
		return;
	}
	document.getElementById("crqStatus").value = document.getElementById("crqStatusSelect").value;
	document.getElementById("tefOutageRequired").value = document.getElementById("tefOutageRequiredSelect").value;
	document.getElementById("p1Site").value = document.getElementById("p1SiteSelect").value;
	document.getElementById("crIN").value = document.getElementById("crINSelect").value;
	document.getElementById("twoManSite").value = document.getElementById("twoManSiteSelect").value;
	document.getElementById("crUsed").value = document.getElementById("crUsedSelect").value;
	document.getElementById("oohWeekend").value = document.getElementById("oohWeekendSelect").value;
	document.getElementById("accessStatus").value = document.getElementById("accessStatusSelect").value;
	document.getElementById("obass").value = document.getElementById("obassSelect").value;
	document.getElementById("rams").value = document.getElementById("ramsSelect").value;
	document.getElementById("vfArrangeAccess").value = document.getElementById("vfArrangeAccessSelect").value;
	document.getElementById("accessConfirmed").value = document.getElementById("accessConfirmedSelect").value;
	document.getElementById("escort").value = document.getElementById("escortSelect").value;
	document.getElementById("healthAndSafety").value = document.getElementById("healthAndSafetySelect").value;
	document.getElementById("buttonPressed").value = "update";
	document.getElementById("toScreen").value = "<%=ServletConstants.CRQ_ACCESS_DETAIL%>";
	document.getElementById("f1").action = "crqAccessDetail";
	document.getElementById("f1").submit();	

}

</script>
<div style="width: 1250px;">
<table style="table-layout: fixed; border-style: none; width:1250px;">
<tr>
	<td height="5px"></td>
</tr>
<tr>
	<td><%=uB.getCrqAccessDetailHTML(snrId) %></td>
</tr>
<tr>
	<td height="10px"></td>
</tr>
<tr>
	<td colspan="2" align="right">
		<input class="button" style="width:70px;height:30px;" 
			title="Update amended CRQ /Access details" 
			onclick="update(<%=snrId%>)" 
			value="Update" type="button">
		<input class="button" style="width:70px;height:30px;" 
			title="Return to CRQ Access list screen" 
			onclick="goBack()" 
			value="Return" type="button">
	</td>
</tr>
</table>
</div>