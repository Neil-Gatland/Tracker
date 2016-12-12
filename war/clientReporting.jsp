<%@ include file="headerLD.jsp" %>
<% 
String year = request.getAttribute("year")==null?"":(String)request.getAttribute("year");
String month = request.getAttribute("month")==null?"":(String)request.getAttribute("month");
String action = request.getAttribute("action")==null?"initialise":(String)request.getAttribute("action");
String day = request.getAttribute("day")==null?"":(String)request.getAttribute("day");
String week = request.getAttribute("week")==null?"":(String)request.getAttribute("week");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="clientReporting.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.CLIENT_REPORTING%>"/>
<input type="hidden" name="selectedAction" id="selectedAction" value="<%=action%>"/>
<input type="hidden" name="selectedYear" id="selectedYear" value="<%=year%>"/>
<input type="hidden" name="selectedMonth" id="selectedMonth" value="<%=month%>"/>
<input type="hidden" name="selectedDay" id="selectedDay" value="<%=day%>"/>
<input type="hidden" name="selectedWeek" id="selectedWeek" value="<%=week%>"/>
<input type="hidden" name="moveDate" id="moveDate" value=""/>
<%@ page import="com.devoteam.tracker.util.DateSearch"%>
<%
	DateSearch dS = new DateSearch(url);
	dS.update(year, month, action);
%>
<script language="javascript">

function thisScreenLoad() {	
	document.getElementById("selectedAction").value = "<%=action%>";
	document.getElementById("selectedYear").value = "<%=year%>";
	document.getElementById("selectedMonth").value = "<%=month%>";
	document.getElementById("selectedDay").value = "<%=day%>";
	document.getElementById("selectedWeek").value = "<%=week%>";
}

function clickSearchBox(operation) {
	document.getElementById("selectedAction").value = operation;
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = "<%=dS.getMonth()%>";
	document.getElementById("toScreen").value = "<%=ServletConstants.CLIENT_REPORTING%>";
	document.getElementById("f1").action = "clientReporting";
	document.getElementById("f1").submit();
}

function dateAction(operation,year,month,day,week) {
	document.getElementById("selectedAction").value = operation;
	document.getElementById("selectedYear").value = year;
	document.getElementById("selectedMonth").value = month;
	document.getElementById("selectedDay").value = day;
	document.getElementById("selectedWeek").value = week;
	document.getElementById("toScreen").value = "<%=ServletConstants.CLIENT_REPORTING%>";
	document.getElementById("f1").action = "clientReporting";
	document.getElementById("f1").submit();
}

function moveDate(operation) {
	document.getElementById("moveDate").value = operation;
	document.getElementById("toScreen").value = "<%=ServletConstants.CLIENT_REPORTING%>";
	document.getElementById("f1").action = "clientReporting";
	document.getElementById("f1").submit();
}

</script>
<div>
<table style="table-layout:fixed;border-style:none;width:1250px;">
<colgroup>
	<col width="400px"/>
	<col width="20px"/>
	<col width="400px"/>
	<col width="20px"/>	
	<col width="400px"/>
</colgroup>
</tbody>
<tr>
<td height="1px" class="clientTop" colspan="5"></td>
<tr>
</tr>
<tr>
<td height="80px" width="400px" class="clientHead">
	<table>
	<colgroup>
		<col width="50px"/>
		<col width="300px"/>
		<col width="100px"/>
		<col width="50px"/>
	</colgroup>
	<tbody>
	<%=uB.getSuccessSummaryHTML(action, dS.getYear(), dS.getMonth(), day, week) %>
	</tbody>
	</table>	
</td>
<td height="80px" width="20px" class="clientTop">&nbsp;</td>
<td height="80px" width="400px" class="clientHead">
	<table>
	<colgroup>
		<col width="50px"/>
		<col width="300px"/>
		<col width="100px"/>
		<col width="50px"/>
	</colgroup>
	<tbody>
	<%=uB.getOutageSummaryHTML(action, dS.getYear(), dS.getMonth(), day, week) %>
	</tbody>
	</table>
</td>
<td height="80px" width="20px" class="clientTop">&nbsp;</td>
<td height="80px" width="400px" class="clientHead">
	<table>
	<colgroup>
		<col width="30px"/>
		<col width="260px"/>
		<col width="80px"/>
		<col width="30px"/>
	</colgroup>
	<tbody>
	<tr>
		<td id="iAnchor" height="20px" colspan="4" class="clientHead"
			title="Click for incident detail"
			onClick="incidentDetailClick('open')">
			<%=uB.clientReportingHeader(action, dS.getYear(), dS.getMonth(), day, week) %>&nbsp;
			- Incidents
		</td>
	</tr>
	<tr>
		<td height="60px" rowspan="2" class="clientHeadLarge"
			onClick="moveDate('back')">&#60;</td>
		<td height="60px" rowspan="2" colspan="2" class="clientHeadLarge" 
			title="Click for incident detail"
			onClick="incidentDetailClick('open')">
			<%=uB.getIncidentTotal(action, dS.getYear(), dS.getMonth(), day, week) %>
		</td>
		<td height="60px" rowspan="2" class="clientHeadLarge"
			onClick="moveDate('forward')">&#62;</td>
	</tr>	
	</tbody>
	</table>
</td>
</tr>
<tr>
<td height="1px" class="clientTop" colspan="5"></td>
<tr>
</tbody>
</table>
<table style="table-layout:fixed;border-style:none;width:1250px;">
<tr>
<td height="525px" width="500px" valign="top" class="clientBox">
	<table>
	<colgroup>
		<col width="10px"/>
		<col width="50px"/>
		<col width="50px"/>
		<col width="50px"/>
		<col width="50px"/>
		<col width="50px"/>
		<col width="50px"/>
		<col width="50px"/>
		<col width="50px"/>
	</colgroup>
	<tbody>
		<tr>
			<td colspan="9" class="dateSearchTClass">
				&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="9" class="dateSearchTClass">
				Search by Month or Week or Day
			</td>
		</tr>
		<tr>
			<td colspan="9" class="dateSearchTClass">
				&nbsp;
			</td>
		</tr>
		<!-- calendar month box -->
		<%=dS.searchBoxHTML()%>
	</tbody>
	</table>
</td>
<td height="525px" width="10px" class="clientTop">&nbsp;</td>
<td height="525px" width="700px" class="clientBox">
	<table style="table-layout:fixed;border-style:none;width:700px;">
	<colgroup>
		<col width="700px"/>
	</colgroup>
	<tbody>
	<%=uB.getClientReportHTML(action, dS.getYear(), dS.getMonth(), day, week) %>
	</tbody>
	</table>
</td>
</tr>
</table>
</div>
<!-- includes -->
<%@ include file="crIncidentDetail.txt" %>
<%@ include file="crSuccessRateDetail.txt" %>
<%@ include file="crOutageDetail.txt" %>
<a href="" id="aLink" name="aLink" style="display:none"></a>
</form>
</body>
</html>