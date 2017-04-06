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
String startDT = request.getAttribute("startDT")==null?"":(String)request.getAttribute("startDT");
String endDT = request.getAttribute("startDT")==null?"":(String)request.getAttribute("endDT");
String completionType = request.getAttribute("completionType")==null?"":(String)request.getAttribute("completionType");
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
<input type="hidden" name="selectedStartDT" id="selectedStartDT" value="<%=startDT%>"/>
<input type="hidden" name="selectedEndDT" id="selectedEndDT" value="<%=startDT%>"/>
<input type="hidden" name="selectedCompletionType" id="selectedCompletionType" value="<%=completionType%>"/>
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
	document.getElementById("selectedStartDT").value = "<%=startDT%>";
	document.getElementById("selectedEndDT").value = "<%=endDT%>";
	document.getElementById("selectedCompletionType").value = "<%=completionType%>";
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
	document.getElementById("selectedStartDT").value = '';
	document.getElementById("selectedEndDT").value = '';
	document.getElementById("selectedCompletionType").value = '';
	document.getElementById("toScreen").value = "<%=ServletConstants.SITE_SEARCH%>";
	document.getElementById("f1").action = "siteSearch";
	document.getElementById("f1").submit();
}

function search(operation) {
	var client = document.getElementById("selectEmailCopyClient").value;
	var project = document.getElementById("selectEmailCopyProject").value;
	var site = document.getElementById("selectEmailCopySite").value;
	var nrId = document.getElementById("selectEmailCopyNRId").value;
	var completionType = document.getElementById("selectEmailCopyCompletionType").value;
	var startDate = document.getElementById("startDT").value;
	var endDate = document.getElementById("endDT").value;
	if ((client=='')&&(project=='')&&(site=='')&&(nrId=='')&&(completionType=='')&&
			(startDate=='')&&(endDate=='')) {
		alert('Either client or project or site or nr id or type or one date must be selected');
		return;
	}
	if (!((site=='')||(nrId==''))) {
		alert('Only site or nr id can selected');
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
	document.getElementById("selectedStartDT").value = document.getElementById("startDT").value;
	document.getElementById("selectedEndDT").value = document.getElementById("endDT").value;
	document.getElementById("selectedCompletionType").value = document.getElementById("selectEmailCopyCompletionType").value;
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
	document.getElementById("startDT").value = "";
	document.getElementById("endDT").value = "";
	document.getElementById("selectCompletionType").value = "";
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
	document.getElementById("selectedStartDT").value = "";
	document.getElementById("selectedEndDT").value = "";
	document.getElementById("selectedCompletionType").value = "";
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
	<col width="320x"/>
	<col width="5px"/>
	<col width="915px"/>	
	<col width="5px"/>	
</colgroup>
<tbody>
	<tr>
		<td class="clientTop">&nbsp;</td>
	</tr> 
	<tr>
		<td>&nbsp;</td>
		<td height="360px" class="clientBox">
			<table style="table-layout: fixed; border-style: none;width:300px;">
			<colgroup>
				<col width="20px"/>
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
					<td colspan="9" class="dateSearchTop">
						Search by month or week or day
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="8" align="center">
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
					<td colspan="1" class="dateSearchTClass">
						&nbsp;
					</td>
					<td colspan="9" class="dateSearchTLClass">
						Search by selected criteria:
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTLCClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLCClass">
						Client:
					</td>
					<td colspan="5" class="dateSearchTLCClass">
						<%=uB.emailCopyClientHTML()%>
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTLCClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLCClass">
						Project:
					</td>
					<td colspan="5" class="dateSearchTLCClass">
						<%=uB.emailCopyProjectHTML()%>
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTLCClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLCClass">
						Site:
					</td>
					<td colspan="5" class="dateSearchTLCClass">
						<%=uB.emailCopySiteHTML()%>
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTLCClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLCClass">
						NR Id:
					</td>
					<td colspan="5" class="dateSearchTLCClass">
						<%=uB.emailCopyNRIdHTML()%>
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTLCClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLCClass">
						From Date:
					</td>
					<td colspan="6" align="left">
						<input 
							type="text" 
							size="10"
							id="startDT" 
							name="startDT" 
							value="<%=startDT%>"
							onclick="javascript:NewCssCal 
									('startDT','ddMMyyyy','arrow')" 
									style="cursor:pointer"
							readonly/>
						<img src="images/cal.gif" 
								onclick="javascript:NewCssCal 
										('startDT','ddMMyyyy','arrow')" 
								style="cursor:pointer"/>
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTLCClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLCClass">
						To Date:
					</td>
					<td colspan="6" align="left">
						<input 
							type="text" 
							size="10" 
							id="endDT" 
							name="endDT" 
							value="<%=endDT%>"
							onclick="javascript:NewCssCal 
									('endDT','ddMMyyyy','arrow')" 
							style="cursor:pointer;"
							readonly/>
						<img src="images/cal.gif" 
								onclick="javascript:NewCssCal 
										('endDT','ddMMyyyy','arrow')" 
								style="cursor:pointer"/>
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTLCClass">
						&nbsp;
					</td>
					<td colspan="3" class="dateSearchTLCClass">
						Type:
					</td>
					<td colspan="5" class="dateSearchTLCClass">
						<%=uB.emailCopyCompletionTypeHTML(completionType)%>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="dateSearchTLCClass">
						&nbsp;
					</td>
					<td title="search by criteria" valign="bottom" align="left">
						<div id="siteSearch" onClick="search('open')" 
							onMouseOut="invertClass('search')" 
							onMouseOver="invertClass('search')" 
							style="float:left;width:75px;display:inline;cursor:pointer;">
							<img src="images/dev_pictos_red_circle_rvb-05.png"
								height="40px" width="40px" >
							</div>
					</td>
					<td height="50px" title="clear all criteria" valign="bottom" align="center">
						<div id="siteSearch" onClick="clearSearchCriteria()" 
							onMouseOut="invertClass('clear')" onMouseOver="invertClass('clear')" 
							style="float:left;width:75px;display:inline;cursor:pointer;" >
							<img src="images/clear.png"
								height="40px" width="40px" ></div>
					</td>
				</tr>
				<tr>
					<td height="250px" valign="top">
						<%=uB.getSiteSummaryHTML(site, nrId) %>
					<td>
				<tr>
			</tbody>
			</table>
		</td>
		<td>&nbsp;</td>
		<td valign="top" height="650px" class="clientBox">
			<table>
			<colgroup>
				<col width="20px"/>
				<col width="500px"/>
				<col width="20px"/>
				<col width="449px"/>
				<col width="10px"/>
			</colgroup>	
			<tbody>
			<tr>
				<td>&nbsp;</td>		
				<td class="dateSearchTop">					
					<%=uB.getSiteCompletionReportCriteriaHTML
						(action, year, month, day, week, client, project, site, nrId)%>	
				</td>
				<td>&nbsp;</td>	
				<td  class="dateSearchTop">
					<%=uB.getCompletionReportHeader(action, year, month, day, week, 
													client, project, site, nrId, 
													startDT, endDT, completionType,
													reportSite, reportNrId, 
													reportDate, reportType) %>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>		
				<td height="650px" valign="top" class="whiteBox">
					<table height="10px">
					<colgroup>
						<col width="40px"/>
						<col width="65px"/>
						<col width="50px"/>
						<col width="90px"/>
						<col width="100px"/>
						<col width="90px"/>
					</colgroup>
					<tbody>
						<tr>
							<td class="siteReportHClass" valign="top" >Site</td>
							<td class="siteReportHClass" valign="top" >Date</td>
							<td class="siteReportHClass" valign="top" >Type</td>
							<td class="siteReportHClass" valign="top" >Client</td>
							<td class="siteReportHClass" valign="top" >Project</td>
							<td class="siteReportHClass" valign="top" >Work Type</td>
						</tr>
					</tbody>
					</table>
					<div style="height:650px;overflow-y: auto; overflow-x: hidden">
					<table>
					<colgroup>
						<col width="40px"/>
						<col width="60px"/>
						<col width="80px"/>
						<col width="100px"/>
						<col width="140px"/>
						<col width="70px"/>
					</colgroup>
					<tbody>
						<%=uB.getSiteCompletionReportListHTML(
								action, year, month, day, week, client, project, site, nrId,
								startDT, endDT, completionType)%>
					</tbody>
					</table>
					</div>
				</td>
				<td>&nbsp;
				</td>
				<td align="center" class="whiteBox" valign="top">				
					<div style="width: 440px;  height: 650px; overflow-y: auto; 
								overflow-x: hidden;" >
						<%=uB.getCompletionReport(
								action, year, month, day, week, 
								client, project, site, nrId,
								startDT, endDT, completionType,
								reportSite, reportNrId, reportDate, reportType) %>
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