<%@ include file="headerLD.jsp" %>
<% 
String year = request.getAttribute("year")==null?"":(String)request.getAttribute("year");
String month = request.getAttribute("month")==null?"":(String)request.getAttribute("month");
String action = request.getAttribute("action")==null?"initialise":(String)request.getAttribute("action");
String day = request.getAttribute("day")==null?"":(String)request.getAttribute("day");
String week = request.getAttribute("week")==null?"":(String)request.getAttribute("week");
String project = request.getAttribute("project")==null?"All":(String)request.getAttribute("project");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="clientReporting.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.CLIENT_REPORTING%>"/>
<input type="hidden" name="selectedAction" id="selectedAction" value="<%=action%>"/>
<input type="hidden" name="selectedYear" id="selectedYear" value="<%=year%>"/>
<input type="hidden" name="selectedMonth" id="selectedMonth" value="<%=month%>"/>
<input type="hidden" name="selectedDay" id="selectedDay" value="<%=day%>"/>
<input type="hidden" name="selectedWeek" id="selectedWeek" value="<%=week%>"/>
<input type="hidden" name="moveDate" id="moveDate" value=""/>
<input type="hidden" name="selectedProject" id="selectedProject" value="<%=project%>"/>
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
	document.getElementById("selectedProject").value = "<%=project%>";	
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

function moveMonth(newMonth) {
	//alert(newMonth);
	document.getElementById("selectedAction").value = 'chgMonth';
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = newMonth;
	document.getElementById("selectedDay").value = '00';
	document.getElementById("selectedWeek").value = '00';
	document.getElementById("toScreen").value = "<%=ServletConstants.CLIENT_REPORTING%>";
	document.getElementById("f1").action = "clientReporting";
	document.getElementById("f1").submit();		
}

function chgProjectFilter() {
	var header = document.getElementById("pAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("chgReportingProjectFilter");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "10";
}

</script>
<div>
<table style="table-layout:fixed;border-style:none;width:1250px;">
<colgroup>
	<col width="410px"/>
	<col width="10px"/>
	<col width="410px"/>
	<col width="10px"/>	
	<col width="410px"/>
</colgroup>
</tbody>
<tr>
<td height="1px" class="clientTop" colspan="5"></td>
<tr>
</tr>
<tr>
<td height="80px" width="410px" class="clientHead">
	<table>
	<colgroup>
		<col width="30px"/>
		<col width="270x"/>
		<col width="80px"/>
		<col width="30px"/>
	</colgroup>
	<tbody>
	<%=uB.getSuccessSummaryHTML(action, dS.getYear(), dS.getMonth(), day, week, project) %>
	</tbody>
	</table>	
</td>
<td height="80px" width="10px" class="clientTop">&nbsp;</td>
<td height="80px" width="410px" class="clientHead">
	<table>
	<colgroup>
		<col width="30px"/>
		<col width="270x"/>
		<col width="80px"/>
		<col width="30px"/>
	</colgroup>
	<tbody>
	<%=uB.getOutageSummaryHTML(action, dS.getYear(), dS.getMonth(), day, week, project) %>
	</tbody>
	</table>
</td>
<td height="80px" width="10px" class="clientTop">&nbsp;</td>
<td height="80px" width="410px" class="clientHead">
	<table>
	<colgroup>
		<col width="30px"/>
		<col width="270x"/>
		<col width="80px"/>
		<col width="30px"/>
	</colgroup>
	<tbody>
	<tr>
		<td id="iAnchor" height="20px" colspan="4" class="clientHead"
			title="Click for site commentary detail"
			onClick="incidentDetailClick('open')"
			style="cursor:pointer">
			<!--  <%=uB.clientReportingHeader(action, dS.getYear(), dS.getMonth(), day, week, project) %>&nbsp;
			- Site Commentary -->
			Site Commentary
		</td>
	</tr>
	<tr>
		<td height="60px" rowspan="2" class="clientHeadLarge"
			onClick="moveDate('back')"
			style="cursor:pointer">&#60;</td>
		<td height="60px" rowspan="2" colspan="2" class="clientHeadLarge" 
			title="Click for site commentary detail"
			onClick="incidentDetailClick('open')"
			style="cursor:pointer">
			<%=uB.getIncidentTotal(action, dS.getYear(), dS.getMonth(), day, week, project) %>
		</td>
		<td height="60px" rowspan="2" class="clientHeadLarge"
			onClick="moveDate('forward')"
			style="cursor:pointer">&#62;</td>
	</tr>	
	</tbody>
	</table>
</td>
</tr>
<tr>
<td height="5px" class="clientTop" colspan="5"></td>
<tr>
</tbody>
</table>
<table style="table-layout:fixed;border-style:none;width:1250px;">
<tr>
<td height="525px" width="340px" valign="top" class="clientBox">
	<table style="table-layout: fixed; border-style: none;width:310px;">
	<colgroup>
		<col width="30px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
		<col width="35px"/>
	</colgroup>
	<tbody>
		<tr>
			<td>&nbsp;</td>
			<td colspan="8" class="dateSearchTop">
				Search by Month or Week or Day
			</td>
		</tr>
		<tr>
			<td colspan="9" class="dateSearchTClass">
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="9" align="center">
				<table style="border-style:solid;border-width:1.0px;">
				<tr>
					<!-- <td class="dateSearchMClass" height="5px" width="3px"></td>-->
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('01')" style="cursor:pointer;">Jan</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('02')"  style="cursor:pointer;">Feb</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('03')"  style="cursor:pointer;">Mar</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('04')"  style="cursor:pointer;">Apr</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('05')"  style="cursor:pointer;">May</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('06')"  style="cursor:pointer;">Jun</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('07')"  style="cursor:pointer;">Jul</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('08')"  style="cursor:pointer;">Aug</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('09')"  style="cursor:pointer;">Sep</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('10')"  style="cursor:pointer;">Oct</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('11')"  style="cursor:pointer;">Nov</td>
					<td class="dateSearchMClass" height="5px" width="5px" 
						onClick="moveMonth('12')"  style="cursor:pointer;">Dec</td>
				</tr>
				</table>
			</td>
		</tr>
		<!-- calendar month box -->
		<%=dS.searchBoxHTML()%>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td id="pAnchor" colspan="2" class="dateSearchTLCClass" 
				onClick="chgProjectFilter()" 
				style="cursor:pointer;"
				title="click to change project filter">
				Project:
			<td colspan="6" class="dateSearchTLCClass" 
				onClick="chgProjectFilter()" 
				style="cursor:pointer;"
				title="click to change project filter">
				<%=project%>
			</td>	
		</tr>
	</tbody>
	</table>
</td>
<td height="525px" width="10px" class="clientTop">&nbsp;</td>
<td height="525px" width="900px" valign="top" class="clientBox">
	<table style="table-layout:fixed;border-style:none;width:900px;">
	<colgroup>
		<col width="900px"/>
	</colgroup>
	<tbody>
	<%=uB.getClientReportHTML(action, dS.getYear(), dS.getMonth(), day, week, project) %>
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
<%@ include file="chgReportingProject.txt" %>
<a href="" id="aLink" name="aLink" style="display:none"></a>
</form>
</body>
</html>