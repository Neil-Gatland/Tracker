<%@ include file="headerLD.jsp" %>
<%
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String selectedReport = request.getAttribute("selectedReport")==null?"":(String)request.getAttribute("selectedReport");
String reportScreen = request.getAttribute("reportScreen")==null?"":(String)request.getAttribute("reportScreen");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="reporting.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.REPORTING%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<script language="javascript">
<!-- 
function thisScreenLoad() {
<%if (reportScreen.equals(ServletConstants.NR_PROGRESS_REPORT_SCREEN)) {%>
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrP = document.getElementById("snrP");
	snrP.style.display = "inline";
	snrP.style.left = position.x + "px";
	snrP.style.top = position.y + "px";
	snrP.style.zIndex = "10";
<%} else if (reportScreen.equals(ServletConstants.NR_TOTALS_REPORT_SCREEN)) {%>
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var snrT = document.getElementById("snrT");
	snrT.style.display = "inline";
	snrT.style.left = position.x + "px";
	snrT.style.top = position.y + "px";
	snrT.style.zIndex = "10";
<%} else if (reportScreen.equals("downloadReport.jsp")) {%>
	var header = document.getElementById("anchor");
	var position = getPosition(header);
	var dR = document.getElementById("downloadReport");
	var posY = position.y - 20;
	dR.style.display = "inline";
	dR.style.left = position.x + "px";
	dR.style.top = posY + "px";
	dR.style.zIndex = "90";
<%}%>
}

function menuClickSpec(element) {
	var dR = document.getElementById("downloadReport");
	dR.style.display = "none";
	dR.style.left = "0px";
	dR.style.top = "0px";
	var snrP = document.getElementById("snrP");
	snrP.style.display = "none";
	snrP.style.left = "0px";
	snrP.style.top = "0px";
	var snrT = document.getElementById("snrT");
	snrT.style.display = "none";
	snrT.style.left = "0px";
	snrT.style.top = "0px";
	var anchor = document.getElementById("anchor");
	var position = getPosition(anchor);
	var thisElement = document.getElementById(element);
	var posY = position.y - 20;
	thisElement.style.display = "inline";
	thisElement.style.left = position.x + "px";
	thisElement.style.top = posY + "px";
	thisElement.style.zIndex = "90";
}

function rsClick(operation) {
	if (operation == "cancel") {
		hideRS();
	} else {
		if (document.getElementById("filterReport").selectedIndex > 0) {
			hideRS();
			document.getElementById("buttonPressed").value = operation;
			document.getElementById("f1").action = "reporting";
			document.getElementById("f1").submit();
		} else {
			alert("Please select a report");
		}
	}
}

function hideRS() {
	var filter = document.getElementById("dfReport");
	filter.style.display = "none";
	filter.style.left = "0px";
	filter.style.top = "0px";
}

function tbClick(btn) {
	document.getElementById("buttonPressed").value = btn;
	document.getElementById("f1").action = "reporting";
	document.getElementById("f1").submit();
	
}

-->
</script>
<div id="anchor">
<div style="width:1270px;margin:0 auto;margin-top:20px;">
<div style="
margin: 0; padding: 0; border-collapse: collapse; width: 1250px; height: 460px; overflow: hidden;"
>
<table style="width: 1250px; height: 20px;">
<tbody>
<tr>
		<td id="hAnchor"></td>
</tr>
</tbody>
</table>
</div>
</div>
<!-- Choose report -->
<div id="dfReport" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="rsClick('cancel')">x</div>
	<div style="clear:both">Report</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("Report", "filter", "filter", selectedReport) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:120px" type="button" class="button" onClick="rsClick('submit')" value="Submit" /></div>
		<div style="float:left"><input style="width:120px" type="button" class="button" onClick="rsClick('cancel')" value="Cancel" /></div>
	</div>
</div>
<%/*if (!reportScreen.isEmpty()) {
	RequestDispatcher r = request.getRequestDispatcher(reportScreen);
	r.include(request, response);
}*/%>
<!-- View NR Progress report -->
<%@ include file="viewNRProgress.txt" %>
<!-- View NR Totals report -->
<%@ include file="viewNRTotals.txt" %>
<!-- Download report -->
<%@ include file="downloadReport.txt" %>
</form>
</body>
</html>