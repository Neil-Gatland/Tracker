<%@ include file="headerLD.jsp" %>
<%
String year = request.getAttribute("year")==null?"":(String)request.getAttribute("year");
String month = request.getAttribute("month")==null?"":(String)request.getAttribute("month");
String action = request.getAttribute("action")==null?"initialise":(String)request.getAttribute("action");
String day = request.getAttribute("day")==null?"":(String)request.getAttribute("day");
String week = request.getAttribute("week")==null?"":(String)request.getAttribute("week");
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
	document.getElementById("selectEmailCopySite").value = "<%=site%>";
	document.getElementById("selectEmailCopyNRId").value = "<%=nrId%>";
	document.getElementById("selectedYear").value = "<%=year%>";
	document.getElementById("selectedMonth").value = "<%=month%>";
	document.getElementById("selectedDay").value = "<%=day%>";
	document.getElementById("selectedWeek").value = "<%=week%>";
	document.getElementById("selectedSite").value = "<%=site%>";
	document.getElementById("selectedNrId").value = "<%=nrId%>";
}

function clickSearchBox(operation) {
	document.getElementById("selectedAction").value = operation;
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = "<%=dS.getMonth()%>";
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
	document.getElementById("toScreen").value = "<%=ServletConstants.SITE_SEARCH%>";
	document.getElementById("f1").action = "siteSearch";
	document.getElementById("f1").submit();
}

function searchAction(operation) {
	if (operation=='site') {
		var selectedSite = document.getElementById("selectEmailCopySite").value;
		if (selectedSite=='') {
			alert('No site selected');
			return;
		}
		//alert(operation+":"+selectedSite);
	} else if (operation='nrId') {
		var selectedNrId = document.getElementById("selectEmailCopyNRId").value;
		if (selectedNrId=='') {
			alert('No NR Id selected');
			return;
		}
		//alert(operation+":"+selectedNrId);
	}
	document.getElementById("selectedAction").value = operation;
	document.getElementById("selectedYear").value = "<%=dS.getYear()%>";
	document.getElementById("selectedMonth").value = "<%=dS.getMonth()%>";
	document.getElementById("selectedDay").value = "";
	document.getElementById("selectedWeek").value = "";
	document.getElementById("selectedSite").value = document.getElementById("selectEmailCopySite").value;
	document.getElementById("selectedNrId").value = document.getElementById("selectEmailCopyNRId").value;
	document.getElementById("toScreen").value = "<%=ServletConstants.SITE_SEARCH%>";
	document.getElementById("f1").action = "siteSearch";
	document.getElementById("f1").submit();
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

</script>
<div>
<table style="table-layout: fixed; border-style: none;width:1250px;">
<colgroup>
	<col width="5px"/>
	<col width="420x"/>
	<col width="5px"/>
	<col width="795px"/>	
	<col width="5px"/>	
</colgroup>
<tbody>
	<tr>
		<td class="clientTop">&nbsp;</td>
	</tr> 
	<tr>
		<td>&nbsp;</td>
		<td height="360px" class="clientBox">
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
						Search by Month or Week or Day or Site or NR Id
					</td>
				</tr>
				<tr>
					<td colspan="9" class="dateSearchTClass">
						&nbsp;
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
					<td colspan="9" class="dateSearchTClass">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="1" class="dateSearchTClass">
						&nbsp;
					</td>
					<td class="dateSearchTClass">
						Site:
					</td>
					<td colspan="2" class="dateSearchTClass">
						<%=uB.emailCopySiteHTML()%>
					</td>
					<td colspan="1" class="dateSearchTClass">
						&nbsp;
					</td>
					<td class="dateSearchTClass">
						NR Id:
					</td>
					<td colspan="3" class="dateSearchTClass">
						<%=uB.emailCopyNRIdHTML()%>
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
						<div id="siteSearch" onClick="searchAction('site')" 
							onMouseOut="invertClass('siteSearch')" onMouseOver="invertClass('siteSearch')" 
							style="float:left;width:75px;display:inline" class="menu2Item">Search by Site</div>
					</td>
					<td class="dateSearchTClass">
						&nbsp;
					</td>
					<td colspan="2" class="dateSearchTClass">
						<div id="siteSearch" onClick="searchAction('nrId')" 
							onMouseOut="invertClass('nrIdSearch')" onMouseOver="invertClass('nrIdSearch')" 
							style="float:left;width:75px;display:inline" class="menu2Item">Search by NR Id</div>
					</td>
				</tr>
				<tr>
					<td height="200px">&nbsp;<td>
				<tr>
			</tbody>
			</table>
		</td>
		<td>&nbsp;</td>
		<td height="610px" class="clientBox">
			<table>
			<colgroup>
				<col width="20px"/>
				<col width="300px"/>
				<col width="40px"/>
				<col width="459px"/>
				<col width="10px"/>
			</colgroup>	
			<tbody>
			<tr>
				<td>&nbsp;</td>		
				<td class="dateSearchTClass">					
					<%=uB.getSiteCompletionReportCriteriaHTML(action, year, month, day, week, site, nrId)%>	
				</td>
				<td>&nbsp;</td>	
				<td  class="dateSearchTClass">
					<%=uB.getCompletionReportHeader(reportSite, reportNrId, reportDate, reportType) %>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>		
				<td height="590px" valign="top" class="whiteBox">
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
					<div style="height:580px;overflow-y: auto; overflow-x: hidden">
					<table>
					<colgroup>
						<col width="40px"/>
						<col width="120px"/>
						<col width="70px"/>
						<col width="70px"/>
					</colgroup>
					<tbody>
						<%=uB.getSiteCompletionReportListHTML(action, year, month, day, week, site, nrId)%>	
					</tbody>
					</table>
					</div>
				</td>
				<td>&nbsp;
				</td>
				<td height="590px" class="whiteBox">				
					<div style="height:590px; width: 459px; overflow-y: auto; overflow-x: auto; position: relative;">
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