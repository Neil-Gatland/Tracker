<%@ include file="headerLD.jsp" %>
<% 
String action = "liveDashboard";
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="liveDashboard.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.LIVE_DASHBOARD%>"/>
<input type="hidden" name="prevScreen" id="prevScreen" value=""/>
<input type="hidden" name="selectedAction" id="selectedAction" value=""/>
<input type="hidden" name="selectedProject" id="selectedProject" value=""/>
<script language="javascript">;

	var visible = "yes";

	//Load the Visualization API and the corechart package.
	google.charts.load('current', {'packages':['corechart']});

	// Set a callback to run when the Google Visualization API is loaded.
	google.charts.setOnLoadCallback(drawPieChart);
	google.charts.setOnLoadCallback(drawLineChart);
	
	// pie chart function
	function drawPieChart() {
		var data = google.visualization.arrayToDataTable([<%=uB.getDashboardPieData()%>]);
      var options = {
        title: 'Migration outcome over last year',
        titleTextStyle: { fontSize: '10' },
        is3D: true,
        pieSliceText: 'value',
        legend: 'none',
		slices: {
		  0: { color: 'green' },
		  1: { color: 'orange'},
		  2: { color: 'red'},
		  3: { color: 'firebrick' } },
		 chartArea: { width: '100%', height: '100%', top: '10%' }
      };
      var chart = new google.visualization.PieChart(document.getElementById('piechart'));
      chart.draw(data, options);
	}
	
	// line chart function
	function drawLineChart() {
		var data = google.visualization.arrayToDataTable([<%=uB.getDashboardLineData()%>]);
		var options = {
        	title: 'Project Activity (last quarter, next month)',
            titleTextStyle: { fontSize: '10' },
          	curveType: 'none', 
			legend: 'none',
			colors: ['#0090FF','gray','green','orange','red','firebrick'],
			chartArea: { width: '70%', height: '70%', top: '10%' }
		};
		var chart = new google.visualization.LineChart(document.getElementById('linechart'));
        chart.draw(data, options);
	}

function thisScreenLoad() {	
}

function navigationAction(action) {	
	if (action=="fwd") {
		document.getElementById("toScreen").value = "<%=ServletConstants.LIVE_DASHBOARD%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	} else if (action=="rwd") {
		document.getElementById("selectedAction").value = "rewind";
		document.getElementById("toScreen").value = "<%=ServletConstants.LIVE_DASHBOARD%>";
		document.getElementById("f1").action = "liveDashboard";
		document.getElementById("f1").submit();
	} else if (action=="go") {
		var selectedProject = document.getElementById("selectProjectLD").value;
		if (selectedProject == "" ) {
			alert('No project selected for GO action');
		} else {
			document.getElementById("selectedAction").value = "go";
			document.getElementById("selectedProject").value = selectedProject;
			document.getElementById("toScreen").value = "<%=ServletConstants.LIVE_DASHBOARD%>";
			document.getElementById("f1").action = "liveDashboard";
			document.getElementById("f1").submit();
		}
	} else if (action=='hide') {
		if (visible == 'yes') {
			visible = "no";
			document.getElementById("top").style.display = "none";
			document.getElementById("siteList").style.height = "384px";
		} else {
			alert("Project is already hidden");
		}
	} else if (action=='show') {
		if (visible == 'no') {
			visible = "yes";
			document.getElementById("top").style.display = "inline";
			document.getElementById("siteList").style.height = "164px";				
		} else {
			alert("Project is already visible");
		}
	}
} 

function siteProgressItemsKeyClick(action) {
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("siteProgressItemsKey");
	if (aR.style.display != "inline") {
		aR.style.display = "inline";
		aR.style.left = position.x + "px";
		aR.style.top = position.y + "px";
		aR.style.zIndex = "10";
	} else {
		aR.style.display = "none";
		aR.style.left = "0px";
		aR.style.top = "0px";
	}
}
</script>
<div style="width:1270px;margin:0 auto;margin-top:10px; 
overflow-y: auto; overflow-x: hidden; border: none; height: 460x;">
<div id="top" height: 180px;">
<table style="table-layout: fixed; border-style: none;">
<tr>
<td width="35%" valign="top"> 
<!-- counts table section -->
<table style="width: 100%;height: 100%;table-layout: fixed;">
<colgroup>
<col width="16%"/>
<col width="14%"/>
<col width="14%"/>
<col width="14%"/>
<col width="14%"/>
<col width="14%"/>
<col width="14%"/>
</colgroup>
<tbody>
<%=uB.getDashboardTableHTML()%>
</tbody>
</table>
</td>
<td width="40%" valign="center" align="center"> 
<!--  line chart section -->
	<div id="linechart"></div>
</td>
<td width="5%" valign="center" align="center"> 
	<img src="images/lcLegend.png" height="40%" width="100%">
 </td>
<td width="30%" valign="center" align="center"> 
<!--  pie chart section -->
 	<div id="piechart"></div>
</td>
</tr>
<tr>
</table>
</div>
<div style="margin: 0; padding: 0; max-height: 640px; overflow; hidden;">
<table style="table-layout: fixed; border-style: none;">
<tr>
<td width="98%">
<!-- daily status section -->
<table style="height: 56px; width: 1250px; table-layout: fixed;">
<colgroup>
<col width="9%"/>
<col width="9%"/>
<col width="10%"/>
<col width="5%"/>
<col width="9%"/>
<col width="9%"/>
<col width="9%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
</colgroup>
<tbody>	
	<tr>
		<td id="hAnchor" class="ldBlank" colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td class="ldTitle" colspan="4"><%=uB.GetLiveSitesHeading()%></td>
		<td class="ldBlank" colspan="3">&nbsp;</td>
		<td class="ldAction" colspan="11">		
			<img src="images/key.png" height="15.9%" width="15.9%" 
				onClick="siteProgressItemsKeyClick()"
				title="Toggle site progress items key">
			<img src="images/fwd.png" height="15.9%" width="15.9%"
				onClick="navigationAction('fwd')"
				title="Go to next project">
			<img src="images/rwd.png" height="15.9%" width="15.9%" 
				onClick="navigationAction('rwd')"
				title="Go to previous project">
			<img src="images/go.png" height="15.9%" width="15.9%"
				onClick="navigationAction('go')"
				title="Go to selected project">
			<img src="images/show.png" height="15.9%" width="15.9%"
				onClick="navigationAction('show')"
				title="Show current project">
			<img src="images/hide.png" height="15.9%" width="15.9%"
				onClick="navigationAction('hide')"
				title="Hide current project">
			<%=uB.getSelectHTMLWithInitialValue("ProjectLD", "select", "filter", "&nbsp;")%>
		</td>			
	</tr>
	<tr>
		<td class="ldBlank" colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td class="ldHead" rowspan="2">Customer</td>
		<td class="ldHead" rowspan="2">Partner</td>
		<td class="ldHead" rowspan="2">Project</td>
		<td class="ldHead" rowspan="2">Site</td>
		<td class="ldHead" rowspan="2">BO</td>
		<td class="ldHead" rowspan="2">FE</td>
		<td class="ldHeadRightDash" rowspan="2">Overall Status</td>
		<td class="ldHeadRightDash" colspan="5">Starting</td>
		<td class="ldHeadRightDash" colspan="8">In Progress</td>
		<td class="ldHeadRightDash" colspan="5">Completing</td>
		<td class="ldHeadRightDash" colspan="2">Issues</td>
	</tr>
	<tr>	
		<td class="ldHeadLeftDash" title="Checked In (BO)">CI</td>
		<td class="ldHead" title="Booked On">BO</td>
		<td class="ldHead" title="Site Accessed (FE)">SA</td>
		<td class="ldHead" title="Physical Checks (FE)">PC</td>
		<td class="ldHeadRightDash" title="Pre Call Test (FE)">PCT</td>
		<td class="ldHeadLeftDash" title="Site Locked (BO/FE)">SL</td>
		<td class="ldHead" title="HW Installs (FE)">HWI</td>
		<td class="ldHead" title="Commissioning (FE)">CFE</td>
		<td class="ldHead" title="Commissioning (BO)">CBO</td>
		<td class="ldHead" title="Tx Provisioning (Client)">TXP</td>
		<td class="ldHead" title="Field Work (FE)">FW</td>
		<td class="ldHead" title="Site Unlocked (BO)">SU</td>
		<td class="ldHeadRightDash" title="Post Call Test (FE)">PCT</td>
		<td class="ldHeadLeftDash" title="Closure Code (BO)">CC</td>
		<td class="ldHead" title="Leave Site (BO)">LS</td>
		<td class="ldHead" title="Book Off Site (FE)">BOS</td>
		<td class="ldHead" title="Performance (BO)">P</td>
		<td class="ldHeadRightDash" title="HOP (BO)">H</td>
		<td class="ldHeadLeftDash" title="Devoteam">DVT</td>
		<td class="ldHeadRightDash" title="Customer">CST</td>
	</tr>
</tbody>
</table>
<div id="siteList" style="margin: 0; padding: 0; overflow-y: auto; overflow-x: hidden; 
max-width: 100%; height: 164px;">
<table style="width: 1250px; table-layout: fixed;">
<colgroup>
<col width="9%"/>
<col width="9%"/>
<col width="10%"/>
<col width="5%"/>
<col width="9%"/>
<col width="9%"/>
<col width="9%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
<col width="2%"/>
</colgroup>
<tbody>	
<%=uB.getLiveDashboardSitesHTML()%>
</tbody>
</div>
</div>
</table>
</td>
</tr>
</table>
</div>
<a href="" id="aLink" name="aLink" style="display:none"></a>
<!-- includes -->
<%@ include file="siteProgressItemsKey.txt" %>
</form>
</body>
</html>