<%@ include file="headerLD.jsp" %>
<%@ page import="com.devoteam.tracker.util.DataAnalytics"%>
<%	
String dataSourceName = request.getAttribute("dataSourceName")==null?"":(String)request.getAttribute("dataSourceName");
String dataTemplateName = request.getAttribute("dataTemplateName")==null?"":(String)request.getAttribute("dataTemplateName");
String chartData = request.getAttribute("chartData")==null?"":(String)request.getAttribute("chartData");
DataAnalytics dA = new DataAnalytics(thisU, url);
String parameter0 = request.getAttribute("parameter0")==null?"":(String)request.getAttribute("parameter0");
String parameter1 = request.getAttribute("parameter1")==null?"":(String)request.getAttribute("parameter1");
String parameter2 = request.getAttribute("parameter2")==null?"":(String)request.getAttribute("parameter2");
String parameter3 = request.getAttribute("parameter3")==null?"":(String)request.getAttribute("parameter3");
String parameter4 = request.getAttribute("parameter4")==null?"":(String)request.getAttribute("parameter4");
String parameter5 = request.getAttribute("parameter5")==null?"":(String)request.getAttribute("parameter5");
String parameter6 = request.getAttribute("parameter6")==null?"":(String)request.getAttribute("parameter6");
String parameter7 = request.getAttribute("parameter7")==null?"":(String)request.getAttribute("parameter7");
String parameter8 = request.getAttribute("parameter8")==null?"":(String)request.getAttribute("parameter8");
String parameter9 = request.getAttribute("parameter9")==null?"":(String)request.getAttribute("parameter9");
String reportSQL = request.getAttribute("reportSQL")==null?"":(String)request.getAttribute("reportSQL");
String reportName = request.getAttribute("reportName")==null?"":(String)request.getAttribute("reportName");
String chartDateLiteral = request.getAttribute("chartDateLiteral")==null?"":(String)request.getAttribute("chartDateLiteral");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="scheduleView.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.DATA_ANALYTICS%>"/>
<input type="hidden" name="selectedAction" id="selectedAction" value=""/> 
<input type="hidden" namde="chartData" id="chartData" value="<%=chartData%>"/>
<input type="hidden" namde="parameter0" id="parameter0" value="<%=parameter0%>"/>
<input type="hidden" namde="parameter1" id="parameter1" value="<%=parameter1%>"/>
<input type="hidden" namde="parameter2" id="parameter2" value="<%=parameter2%>"/>
<input type="hidden" namde="parameter3" id="parameter3" value="<%=parameter3%>"/>
<input type="hidden" namde="parameter4" id="parameter4" value="<%=parameter4%>"/>
<input type="hidden" namde="parameter5" id="parameter5" value="<%=parameter5%>"/>
<input type="hidden" namde="parameter6" id="parameter6" value="<%=parameter6%>"/>
<input type="hidden" namde="parameter7" id="parameter7" value="<%=parameter7%>"/>
<input type="hidden" namde="parameter8" id="parameter8" value="<%=parameter8%>"/>
<input type="hidden" namde="parameter9" id="parameter9" value="<%=parameter9%>"/>
<input type="hidden" namde="reportSQL" id="reportSQL" value="<%=reportSQL%>"/>
<input type="hidden" namde="reportName" id="reportName" value="<%=reportName%>"/>

<!--Load the AJAX API-->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">

function thisScreenLoad() {	
	if (!("<%=reportSQL%>"=="")) {
		document.getElementById("toScreen").value = "<%=ServletConstants.DATA_ANALYTICS%>";
		document.getElementById("f1").action = "downloadCSV";
		document.getElementById("f1").selectedAction.value = "";
		document.getElementById("f1").submit();	
	}
}

// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart','bar']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the chart, passes in the data and
// draws it.
function drawChart() {
	
	// find out chart type	
	var chartType = "<%=dA.GetDataTemplateChartType(dataTemplateName)%>";

	// Create the data table.
	var data = new google.visualization.DataTable();
	<%=chartData%>

  // Set chart options  
  var options = {'title':'<%=dataTemplateName%> '+
		  					'( <%=parameter0%> <%=parameter1%> <%=parameter2%> <%=parameter3%> <%=parameter4%>'+
		  					' <%=parameter5%> <%=parameter6%> <%=parameter7%> <%=parameter8%> <%=parameter9%>)',
                 'width':790,
                 'height':520,
                 'legend':'none'};
  if (chartType=="Scatter") {
	  options = {'title':'<%=dataTemplateName%> '+
							'( <%=parameter0%> <%=parameter1%> <%=parameter2%> <%=parameter3%> <%=parameter4%>'+
							' <%=parameter5%> <%=parameter6%> <%=parameter7%> <%=parameter8%> <%=parameter9%>)',
			     'width':790,
			     'height':520,
                 'hAxis': {'title':'<%=chartDateLiteral%>'},
                 'vAxis': {'title':'Time'},
	          	 'legend': 'none'};
  }

  // Instantiate and draw our chart, passing in some options.
  if (chartType=="Pie") {
	  if (!("<%=chartData%>"=="")) {
		  var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
		  chart.draw(data, options);
		  document.getElementById('png').outerHTML = 
			  '<a href="' + chart.getImageURI() + '" target="_blank">PNG chart version for copying</a>';
	  } 
  } else if (chartType=="Bar") {
	  if (!("<%=chartData%>"=="")) {
		  var chart = new google.charts.Bar(document.getElementById('chart_div'));
		  chart.draw( data, google.charts.Bar.convertOptions(options));
		  document.getElementById('png').outerHTML = 
			  '<a href="' + chart.getImageURI() + '" target="_blank">PNG chart version for copying</a>';
	  }
  } else if (chartType=="Scatter") {
	  if (!("<%=chartData%>"=="")) {
		  var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
		  chart.draw( data, google.charts.Bar.convertOptions(options));
		  document.getElementById('png').outerHTML = 
			  '<a href="' + chart.getImageURI() + '" target="_blank">PNG chart version for copying</a>';
	  }
  } 
}

function selectionChange(filter) {
	if (filter=="dataSource") {
		document.getElementById("selectDataTemplateName").value = "";
	}
	document.getElementById("toScreen").value = "<%=ServletConstants.DATA_ANALYTICS%>";
	document.getElementById("f1").action = "dataAnalytics";
	document.getElementById("f1").selectedAction.value = "";
	document.getElementById("f1").submit();
}

function createChart(dataTemplateName) {
	if (dataTemplateName=="") {
		alert("No data template selected");
		return;
	}
	document.getElementById("toScreen").value = "<%=ServletConstants.DATA_ANALYTICS%>";
	document.getElementById("f1").action = "dataAnalytics";
	document.getElementById("f1").selectedAction.value  = "createChart";
	document.getElementById("f1").submit();
}

function downloadRawData(dataTemplateName) {
	if (dataTemplateName=="") {
		alert("No data template selected");
		return;
	}
	document.getElementById("toScreen").value = "<%=ServletConstants.DATA_ANALYTICS%>";
	document.getElementById("f1").action = "dataAnalytics";
	document.getElementById("f1").selectedAction.value  = "downloadRawData";
	document.getElementById("f1").submit();
}

function downloadChartData(dataTemplateName) {
	if (dataTemplateName=="") {
		alert("No data template selected");
		return;
	}
	document.getElementById("toScreen").value = "<%=ServletConstants.DATA_ANALYTICS%>";
	document.getElementById("f1").action = "dataAnalytics";
	document.getElementById("f1").selectedAction.value  = "downloadChartData";
	document.getElementById("f1").submit();
}

</script>
<table style="table-layout: fixed; border-style: none;width:1250px;">
	<colgroup>
		<col width="2px"/>
		<col width="436px"/>
		<col width="10px"/>
		<col width="800px"/>
		<col width="2px"/>
	</colgroup>
<tbody>
	<tr>
		<td>&nbsp;</td>
		<td>
			<!--  left hand boxes column -->
			<table style="table-layout: fixed; border-style: none;width:436px;">
				<tr>
					<td height="10px"></td>
				</tr>
				<tr>
					<!--  data source and template selection -->
					<td height="110px" class="daBoxFilled">
						<table style="table-layout: fixed; border-style: none;width:436px">
							<col width="23px"/>
							<col width="140px"/>
							<col width="250px"/>
							<col width="23px"/>
						<tbody>
							<tr>
								<td>&nbsp;</td>
								<td class="daTitle">Data Source:</td>
								<td onChange="selectionChange('dataSource')">
									<%=uB.getSelectHTMLWithInitialValue
										("DataSourceName", "select", "filter", dataSourceName, "dummy()", "")%>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td colspan="4">&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td class="daTitle">Data Template:</td>
								<td onChange="selectionChange('dataTemplate')">
									<%=uB.getSelectHTMLWithInitialValue
										("DataTemplateName", "select", "filter", dataTemplateName, "dummy()", dataSourceName)%>
								</td>
								<td>&nbsp;</td>
							</tr>
						</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td height="7px"></td>
				</tr>
				<tr>
					<!--  parameter selection -->
					<td height="300px" class="daBoxFilled">
						<table style="table-layout: fixed; border-style: none;width:436px">
						<colgroup>
								<col width="23px"/>
								<col width="140px"/>
								<col width="250px"/>
								<col width="23px"/>
						</colgroup>
						<tbody>
						<%=dA.getParameterListHTML
							(dataTemplateName,
							 parameter0,
							 parameter1,
							 parameter2,
							 parameter3,
							 parameter4,
							 parameter5,
							 parameter6,
							 parameter7,
							 parameter8,
							 parameter9)%>
						</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td height="7px"></td>
				</tr>
				<tr>
					<!--  action buttons -->
					<td height="80px" class="daBoxFilled" align="center">
						<table tyle="table-layout: fixed; border-style: none;">
							<tr>
								<td align="center">								
									<input class="button" 
										style="width:100px;height:30px;" 
										title="Create chart for selected parameters"
										onClick="createChart('<%=dataTemplateName%>')"
										value="Create Chart" type="button">
								</td>
								<td align="center">								
									<input class="button" 
										style="width:150px;height:30px;" 
										title="Download raw data used to create chart"
										onClick="downloadRawData('<%=dataTemplateName%>')"
										value="Download Raw Data" type="button">
								</td>
								<td align="center">							
									<input class="button" 
										style="width:160px;height:30px;" 
										title="Download summarised data used to create the chart"
										onClick="downloadChartData('<%=dataTemplateName%>')"
										value="Download Chart Data" type="button">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="5px">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td>&nbsp;</td>
		<!--  Chart or Data area -->
		<td>
			<table style="table-layout: fixed; border-style: none;width:800px;">				
				<tr>
					<td height="520px" class="daBoxEmpty">
						<div id="chart_div"></div>
					</td>
				</tr>
				<tr>
					<td height="5px"><div id='png'></div></td>
				</tr>
			</table>
		</td>
		<td>&nbsp;</td>
	</tr>
</tbody>
</table>
</form>
</body>
</html>