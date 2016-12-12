<%@ include file="headerLD.jsp" %>
<%
String year = request.getAttribute("year")==null?"":(String)request.getAttribute("year");
String month = request.getAttribute("month")==null?"":(String)request.getAttribute("month");
String action = request.getAttribute("action")==null?"initialise":(String)request.getAttribute("action");
String day = request.getAttribute("day")==null?"":(String)request.getAttribute("day");
String week = request.getAttribute("week")==null?"":(String)request.getAttribute("week");
String client = request.getAttribute("client")==null?"":(String)request.getAttribute("client");
String project = request.getAttribute("project")==null?"":(String)request.getAttribute("project");
String site = request.getAttribute("site")==null?"":(String)request.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"":(String)request.getAttribute("nrId");
String reportSite = request.getAttribute("reportSite")==null?"":(String)request.getAttribute("reportSite");
String reportNrId = request.getAttribute("reportNrId")==null?"":(String)request.getAttribute("reportNrId");
String reportDate = request.getAttribute("reportDate")==null?"":(String)request.getAttribute("reportDate");
String reportType = request.getAttribute("reportType")==null?"":(String)request.getAttribute("reportType");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="siteSearch.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.SITE_SEARCH%>"/>
<input type="hidden" name="selectedAction" id="selectedAction" value="<%=action%>"/>
<input type="hidden" name="selectedYear" id="selectedYear" value="<%=year%>"/>
<input type="hidden" name="selectedMonth" id="selectedMonth" value="<%=month%>"/>
<input type="hidden" name="selectedDay" id="selectedDay" value="<%=day%>"/>
<input type="hidden" name="selectedWeek" id="selectedWeek" value="<%=week%>"/>
<input type="hidden" name="selectedClient" id="selectedClient" value="<%=client%>"/>
<input type="hidden" name="selectedProject" id="selectedProject" value="<%=project%>"/>
<input type="hidden" name="selectedSite" id="selectedSite" value="<%=site%>"/>
<input type="hidden" name="selectedNrId" id="selectedNrId" value="<%=nrId%>"/>
<input type="hidden" name="reportSite" id="reportSite" value="<%=reportSite%>"/>
<input type="hidden" name="reportNrId" id="reportNrId" value="<%=reportNrId%>"/>
<input type="hidden" name="reportDate" id="reportDate" value="<%=reportDate%>"/>
<input type="hidden" name="reportType" id="reportType" value="<%=reportType%>"/>
<%@ page import="com.devoteam.tracker.util.DateSearch"%>
<%
	DateSearch dS = new DateSearch(url);
	dS.update(year, month, action);
%>
<script language="javascript">

function thisScreenLoad() {	
	document.getElementById("selectedAction").value = "<%=action%>";
	document.getElementById("selectEmailCopyClient").value = "<%=client%>";
	document.getElementById("selectEmailCopyProject").value = "<%=project%>";
	document.getElementById("selectEmailCopySite").value = "<%=site%>";
	document.getElementById("selectEmailCopyNRId").value = "<%=nrId%>";
	document.getElementById("selectedYear").value = "<%=year%>";
	document.getElementById("selectedMonth").value = "<%=month%>";
	document.getElementById("selectedDay").value = "<%=day%>";
	document.getElementById("selectedWeek").value = "<%=week%>";
	document.getElementById("selectedClient").value = "<%=client%>";
	document.getElementById("selectedProject").value = "<%=project%>";
	document.getElementById("selectedSite").value = "<%=site%>";
	document.getElementById("selectedNrId").value = "<%=nrId%>";
}

function clickSearchBox(operation) {
	document.getElementById("selectedAction").value = operation;
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = "<%=dS.getMonth()%>";
	document.getElementById("reportSite").value = '';
	document.getElementById("reportNrId").value = '';
	document.getElementById("reportDate").value = '';
	document.getElementById("reportType").value = '';
	document.getElementById("toScreen").value = "<%=ServletConstants.SITE_SEARCH%>";
	document.getElementById("f1").action = "siteSearch";
	document.getElementById("f1").submit();
}

function dateAction(operation,year,month,day,week) {
	//alert(operation+":"+year+":"+month+":"+day+":"+week);
	document.getElementById("selectedAction").value = operation;
	document.getElementById("selectedYear").value = year;
	document.getElementById("selectedMonth").value = month;
	document.getElementById("selectedDay").value = day;
	document.getElementById("selectedWeek").value = week;
	document.getElementById("selectedSite").value = '';
	document.getElementById("selectedNrId").value = '';
	document.getElementById("selectedClient").value = '';
	document.getElementById("selectedProject").value = '';
	document.getElementById("reportSite").value = '';
	document.getElementById("reportNrId").value = '';
	document.getElementById("reportDate").value = '';
	document.getElementById("reportType").value = '';
	document.getElementById("toScreen").value = "<%=ServletConstants.SITE_SEARCH%>";
	document.getElementById("f1").action = "siteSearch";
	document.getElementById("f1").submit();
}

function search(operation) {
	var client = document.getElementById("selectEmailCopyClient").value;
	var project = document.getElementById("selectEmailCopyProject").value;
	var site = document.getElementById("selectEmailCopySite").value;
	var nrId = document.getElementById("selectEmailCopyNRId").value;
	if ((client=='')&&(project=='')&&(site=='')&&(nrId=='')) {
		alert('No search criteria selected');
		return;
	}
	if (!((site=='')||(nrId==''))) {
		alert('Only enter Site or NR Id');
		return;
	}
	document.getElementById("selectedAction").value = operation;
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = "<%=dS.getMonth()%>";
	document.getElementById("selectedDay").value = "";
	document.getElementById("selectedWeek").value = "";
	document.getElementById("selectedClient").value = document.getElementById("selectEmailCopyClient").value;
	document.getElementById("selectedProject").value = document.getElementById("selectEmailCopyProject").value;
	document.getElementById("selectedSite").value = document.getElementById("selectEmailCopySite").value;
	document.getElementById("selectedNrId").value = document.getElementById("selectEmailCopyNRId").value;
	document.getElementById("toScreen").value = "<%=ServletConstants.SITE_SEARCH%>";
	document.getElementById("reportSite").value = '';
	document.getElementById("reportNrId").value = '';
	document.getElementById("reportDate").value = '';
	document.getElementById("reportType").value = '';
	document.getElementById("f1").action = "siteSearch";
	document.getElementById("f1").submit();
}

function clearSearchCriteria() {
	document.getElementById("selectEmailCopyClient").value = "";
	document.getElementById("selectEmailCopyProject").value = "";
	document.getElementById("selectEmailCopySite").value = "";
	document.getElementById("selectEmailCopyNRId").value = "";
	document.getElementById("selectedClient").value = "";
	document.getElementById("selectedProject").value = "";
	document.getElementById("selectedSite").value = "";
	document.getElementById("selectedNrId").value = "";
}

function reportSelect(site,nrId,date,type) {
	//alert(site+":"+nrId+":"+date+":"+type);
	document.getElementById("reportSite").value = site;
	document.getElementById("reportNrId").value = nrId;
	document.getElementById("reportDate").value = date;
	document.getElementById("reportType").value = type;
	document.getElementById("toScreen").value = "<%=ServletConstants.SITE_SEARCH%>";
	document.getElementById("f1").action = "siteSearch";
	document.getElementById("f1").submit();
}

function moveMonth(newMonth) {
	//alert(newMonth);
	document.getElementById("selectedAction").value = 'chgMonth';
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = newMonth;
	document.getElementById("selectedDay").value = '00';
	document.getElementById("selectedWeek").value = '00';
	document.getElementById("selectedSite").value = '';
	document.getElementById("selectedNrId").value = '';
	document.getElementById("reportSite").value = '';
	document.getElementById("reportNrId").value = '';
	document.getElementById("reportDate").value = '';
	document.getElementById("reportType").value = '';
	document.getElementById("toScreen").value = "<%=ServletConstants.SITE_SEARCH%>";
	document.getElementById("f1").action = "siteSearch";
	document.getElementById("f1").submit();	
	
}

</script>
<div>
<table style="table-layout: fixed; border-style: none;width:1250px;">
<colgroup>
	<col width="5px"/>
	<col width="360x"/>
	<col width="5px"/>
	<col width="875px"/>	
	<col width="5px"/>	
</colgroup>
<tbody>
	<tr>
		<td class="clientTop">&nbsp;</td>
	</tr> 
	<tr>
		<td>&nbsp;</td>
		<td height="360px" class="clientBox">
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
					<td colspan="1" class="dateSearchTLClass">
						&nbsp;
					</td>
					<td colspan="9" class="dateSearchTLClass">
						Search by month or week or day:
					</td>
				</tr>
				<tr>
					<td colspan="9" class="dateSearchTClass">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="dateSearchTClass">&nbsp;<td>
					<td colspan="9" align="center">
						<table>
						<tr>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('01')" title="go to January">J</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px"" 
								onClick="moveMonth('02')" title="go to February">F</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('03')" title="go to March">M</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('04')" title="go to April">A</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('05')" title="go to May">M</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('06')" title="go to June">J</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('07')" title="go to July">J</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('08')" title="go to August">A</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('09')" title="go to September">S</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('10')" title="go to October">O</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('11')" title="go to November">N</td>
							<td>&nbsp;</td>
							<td class="dateSearchMClass" height="5px" width="5px" 
								onClick="moveMonth('12')" title="go to December">D</td>
						</tr>
						</table>
					</td>				
				</tr>
				<!-- calendar month box -->
				<%=dS.searchBoxHTML()%>
				<tr>
					<td colspan="9" class="dateSearchTClass">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTClass">
						&nbsp;
					</td>
					<td colspan="9" class="dateSearchTLClass"">
						Search by selected criteria:
					</td>
				</tr>
				<tr>
					<td colspan="9" class="dateSearchTLClass">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLClass">
						Client:
					</td>
					<td colspan="5" class="dateSearchTClass">
						<%=uB.emailCopyClientHTML()%>
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLClass">
						Project:
					</td>
					<td colspan="5" class="dateSearchTClass">
						<%=uB.emailCopyProjectHTML()%>
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLClass">
						Site:
					</td>
					<td colspan="5" class="dateSearchTClass">
						<%=uB.emailCopySiteHTML()%>
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLClass">
						NR Id:
					</td>
					<td colspan="5" class="dateSearchTClass">
						<%=uB.emailCopyNRIdHTML()%>
					</td>
				</tr>
				<tr>
					<td colspan="9" class="dateSearchTClass">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTClass">
						&nbsp;
					</td>
					<td class="dateSearchTClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTClass">
						<div id="siteSearch" onClick="search('open')" 
							onMouseOut="invertClass('search')" onMouseOver="invertClass('search')" 
							style="float:left;width:75px;display:inline" class="menu2Item">Search</div>
					</td>
					<td class="dateSearchTClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTClass">
						<div id="siteSearch" onClick="clearSearchCriteria()" 
							onMouseOut="invertClass('clear')" onMouseOver="invertClass('clear')" 
							style="float:left;width:75px;display:inline" class="menu2Item">Clear</div>
					</td>
				</tr>
				<tr>
					<td height="600px">&nbsp;<td>
				<tr>
			</tbody>
			</table>
		</td>
		<td>&nbsp;</td>
		<td valign="top" height="560px" class="clientBox">
			<table>
			<colgroup>
				<col width="20px"/>
				<col width="360px"/>
				<col width="40px"/>
				<col width="549px"/>
				<col width="10px"/>
			</colgroup>	
			<tbody>
			<tr>
				<td>&nbsp;</td>		
				<td class="dateSearchTClass">					
					<%=uB.getSiteCompletionReportCriteriaHTML(action, year, month, day, week, client, project, site, nrId)%>	
				</td>
				<td>&nbsp;</td>	
				<td  class="dateSearchTClass">
					<%=uB.getCompletionReportHeader(reportSite, reportNrId, reportDate, reportType) %>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>		
				<td height="560px" valign="top" class="whiteBox">
					<table height="10px">
					<colgroup>
						<col width="40px"/>
						<col width="120px"/>
						<col width="60px"/>
						<col width="80px"/>
					</colgroup>
					<tbody>
						<tr>
							<td class="siteReportHClass" valign="top" >Site</td>
							<td class="siteReportHClass" valign="top" >NR Id</td>
							<td class="siteReportHClass" valign="top" >Date</td>
							<td class="siteReportHClass" valign="top" >Type</td>
						</tr>
					</tbody>
					</table>
					<div style="height:550px;overflow-y: auto; overflow-x: hidden">
					<table>
					<colgroup>
						<col width="40px"/>
						<col width="120px"/>
						<col width="70px"/>
						<col width="70px"/>
					</colgroup>
					<tbody>
						<%=uB.getSiteCompletionReportListHTML(action, year, month, day, week, client, project, site, nrId)%>	
					</tbody>
					</table>
					</div>
				</td>
				<td>&nbsp;
				</td>
				<td height="560px" align="center" class="whiteBox">				
					<div style="width: 549px; overflow-y: auto; overflow-x: auto; 
						position: relative; left: 30px;">
						<%=uB.getCompletionReport(reportSite, reportNrId, reportDate, reportType)%>
					</div>
				<td>
				<td>&nbsp;</td>
			</tr>
			</tbody>	
			</table>
		</td>
		<td>&nbsp;</td>
	</tr>
</table>
</div>
<a href="" id="aLink" name="aLink" style="display:none"></a>
</form>
</body>
</html>