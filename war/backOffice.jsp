<%@ include file="headerLD.jsp" %>
<% 
String currentBO = thisU.getFullname();
String filterBOEngineer = request.getAttribute("filterBOEngineer")==null?currentBO:(String)request.getAttribute("filterBOEngineer");
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String currentDate = uB.getCurrentDate();
String week = request.getAttribute("week")==null?"":(String)request.getAttribute("week");
String weekAction = request.getAttribute("weekAction")==null?"NOW":(String)request.getAttribute("weekAction");
String showSchedule = request.getAttribute("showSchedule")==null?"N":(String)request.getAttribute("showSchedule");
String showOSWork = request.getAttribute("showOSWork")==null?"N":(String)request.getAttribute("showOSWork");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="backOffice.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.BACK_OFFICE%>"/>
<input type="hidden" name="filterBOEngineer" id="filterBOEngineer" value="<%=filterBOEngineer%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<input type="hidden" name="snrId" id="snrId" value=""/>
<input type="hidden" name="site" id="site" value=""/>
<input type="hidden" name="nrId" id="nrId" value=""/>
<input type="hidden" name="week" id="week" value="<%=week%>"/>
<input type="hidden" name="weekAction" id="weekAction" value="<%=weekAction%>"/>
<input type="hidden" name="showSchedule" id="showSchedule" value="<%=showSchedule%>"/>
<input type="hidden" name="showOSWork" id="showOSWork" value="<%=showOSWork%>"/>
<script type="text/javascript">

function thisScreenLoad() {
	if ("<%=showSchedule%>"=="Y") {
		document.getElementById("schedule").style.display = "inline";
		document.getElementById("scheduleHide").style.display = "none";
		document.getElementById("scheduleShow").style.display = "inline";
	} else {
		document.getElementById("schedule").style.display = "none";
		document.getElementById("scheduleHide").style.display = "inline";
		document.getElementById("scheduleShow").style.display = "none";
	}
	if ("<%=showOSWork%>"=="Y") {
		document.getElementById("osWorks").style.display = "inline";
		document.getElementById("osWorksCountHide").style.display = "none";
		document.getElementById("osWorksCountShow").style.display = "inline";
	} else {
		document.getElementById("osWorks").style.display = "none";
		document.getElementById("osWorksCountHide").style.display = "inline";
		document.getElementById("osWorksCountShow").style.display = "none";
	}
}

function toggleOSWorks() {
	var current = document.getElementById("osWorks").style.display;
	if (current=="none") {
		document.getElementById("osWorks").style.display = "inline";
		document.getElementById("osWorksCountHide").style.display = "none";
		document.getElementById("osWorksCountShow").style.display = "inline";
		document.getElementById("showOSWork").value = "Y";
	} else {
		document.getElementById("osWorks").style.display = "none";
		document.getElementById("osWorksCountHide").style.display = "inline";
		document.getElementById("osWorksCountShow").style.display = "none";
		document.getElementById("showOSWork").value = "N";
	}
}

function toggleSchedule() {
	var current = document.getElementById("schedule").style.display;
	if (current=="none") {
		document.getElementById("schedule").style.display = "inline";
		document.getElementById("scheduleHide").style.display = "none";
		document.getElementById("scheduleShow").style.display = "inline";
		document.getElementById("showSchedule").value = "Y";
	} else {
		document.getElementById("schedule").style.display = "none";
		document.getElementById("scheduleHide").style.display = "inline";
		document.getElementById("scheduleShow").style.display = "none";
		document.getElementById("showSchedule").value = "N";
	}
}

function tbClick(btn) {
	if (btn == "changeBOEngineer") {
		var header = document.getElementById("hChgBO");
		var position = getPosition(header);
		var aR = document.getElementById("chgBOEngineer");
		aR.style.display = "inline";
		aR.style.left = position.x + "px";
		aR.style.top = position.y + "px";
		aR.style.zIndex = "10";
	} 
}

function emailCopy() {
	var header = document.getElementById("hAnchor");
	var position = getPosition(header);
	var aR = document.getElementById("emailCopy");
	aR.style.display = "inline";
	aR.style.left = position.x + "px";
	aR.style.top = position.y + "px";
	aR.style.zIndex = "10";
}

function snrDetail(snrId,filterBOEngineer) {
	document.getElementById("snrId").value = snrId;
	document.getElementById("week").value = '<%=uB.getNextScheduledWeek(week, weekAction) %>';
	document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE_DETAIL%>";
	document.getElementById("f1").action = "navigation";
	document.getElementById("f1").submit();
}

function accessDetail(snrId,site,nrId) {
	document.getElementById("snrId").value = snrId;
	document.getElementById("site").value = site;
	document.getElementById("nrId").value = nrId;
	document.getElementById("week").value = '<%=uB.getNextScheduledWeek(week, weekAction) %>';
	document.getElementById("toScreen").value = "<%=ServletConstants.VIEW_ACCESS_DETAIL%>";
	document.getElementById("f1").action = "navigation";
	document.getElementById("f1").submit();
}

function moveWeek(operation) {
	document.getElementById("week").value = '<%=uB.getNextScheduledWeek(week, weekAction) %>';
	document.getElementById("weekAction").value = operation;
	document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE%>";
	document.getElementById("f1").action = "backOffice";
	document.getElementById("f1").submit();	
}

</script>
<div style="width:1250px;" >
<table style="table-layout: fixed; border-style: none; width:1250px;">
	<tr>
		<td height="5px" class="clientBox"></td>
	</tr>
	<tr>
		<td height="240px" class="clientBox">
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="305px"/>
				<col width="305px"/>
				<col width="305px"/>
				<col width="305px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="whiteMenu1">Daily Site Schedule</td>
					<td class="whiteMenu1"><%=uB.getCurrentDateDisplay()%></td>
					<td id="hChgBO" class="whiteMenu1"
						onClick="tbClick('changeBOEngineer')"
						title="Current BO Engineer - click to change"
						style="cursor:pointer;">
						<%=filterBOEngineer%>
					</td>
					<td class="whiteMenu1" 
						onClick="emailCopy()">View Email Copies</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="120px"/>
				<col width="120px"/>
				<col width="100px"/>
				<col width="90px"/>
				<col width="80px"/>
				<col width="170px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="90px"/>
				<col width="170px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td id="hAnchor" >&nbsp;</td>
					<td class="menu1">Site</td>
					<td class="menu1">Status</td>
					<td class="menu1">Job Type</td>
					<td class="menu1">Upgrade Type</td>
					<td class="menu1" title="Technologies">Tech(s)</td>
					<td class="menu1" title="Hardware Vendor">HW Vendor</td>
					<td class="menu1">FE(s)</td>
					<td class="menu1">VF CRQ</td>
					<td class="menu1">TEF CRQ</td>
					<td class="menu1" title="Access Status">Access</td>
					<td class="menu1">BO Engineer(s)</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			<div style="overflow-y: auto; overflow-x: hidden; height: 200px;">
			<table style="width: 1250px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="120px"/>
				<col width="120px"/>
				<col width="100px"/>
				<col width="90px"/>
				<col width="80px"/>
				<col width="170px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="90px"/>
				<col width="170px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
			</tbody>
				<%=uB.getDailySiteScheduleHTML(filterBOEngineer, currentDate)%>
			</table>
			</div>
		</td>
	</tr>
	<tr>
		<td height="5px" class="clientBox"></td>
	</tr>
	<tr>
		<td class="clientBox">	
			<div id="osWorksCountHide" style="display: inline";">		
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="20px"/>
				<col width="1200px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="whiteMenu2">
					<img src="images/show.png" 
						height="15px" 
						width="15px" 
						title="click to show outstanding work" 
						style="cursor:pointer;"
						onClick="toggleOSWorks()">
					</td>
					<td class="whiteMenu2" 
						title="click to show outstanding work" 
						style="cursor:pointer;"
						onClick="toggleOSWorks()">
						Outstanding Work (<%=uB.getOutstandingWorksCount(filterBOEngineer)%>)
					</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>	
			<div id="osWorksCountShow" style="display: none";">		
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="20px"/>
				<col width="1200px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="whiteMenu2">
					<img src="images/hide.png" 
						height="15px" 
						width="15px" 
						title="click to hide outstanding work" 
						style="cursor:pointer;"
						onClick="toggleOSWorks()">
					</td>
					<td class="whiteMenu2"
						title="click to hide outstanding work" 
						style="cursor:pointer;"
						onClick="toggleOSWorks()">
						Outstanding Work (<%=uB.getOutstandingWorksCount(filterBOEngineer)%>)
					</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>
			<div id="osWorks" style="display: none";">
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="200px"/>
				<col width="100px"/>
				<col width="110px"/>
				<col width="100px"/>
				<col width="200px"/>
				<col width="30px"/>
				<col width="30px"/>
				<col width="30px"/>
				<col width="30px"/>
				<col width="30px"/>
				<col width="40px"/>
				<col width="40px"/>
				<col width="40px"/>
				<col width="40px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="menu1">Site</td>
					<td class="menu1">Status</td>
					<td class="menu1">Job Type</td>
					<td class="menu1" title="Scheduled Date">Sch. Date</td>
					<td class="menu1" title="Technologies">Tech(s)</td>
					<td class="menu1" title="Hardware Vendor">HW Vendor</td>
					<td class="menu1">BO Engineer(s)</td>
					<td class="menu1" colspan="5">Evenflow</td>
					<td class="menu1" title="HOP Status">HOP</td>
					<td class="menu1" title="SFR Status">SFR</td>
					<td class="menu1" title="Next PreCheck">NPC</td>
					<td class="menu1" title="Progress Complete">PC</td>
				</tr>
			</tbody>
			</table>
			<div style="overflow-y: auto; overflow-x: hidden; height: 125px;">
			<table style="width: 1250px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="100px"/>
				<col width="100px"/>
				<col width="200px"/>
				<col width="100px"/>
				<col width="110px"/>
				<col width="100px"/>
				<col width="200px"/>
				<col width="30px"/>
				<col width="30px"/>
				<col width="30px"/>
				<col width="30px"/>
				<col width="30px"/>
				<col width="40px"/>
				<col width="40px"/>
				<col width="40px"/>
				<col width="40px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
			</tbody>
				<%=uB.getOutstandingWorksHTML(filterBOEngineer)%>
			</table>
			</div>
			</div>
		</td>
	</tr>
	<tr>
		<td height="5px" class="clientBox"></td>
	</tr>
	<tr>
		<td class="clientBox">		
			<div id="scheduleHide" style="display: inline";">		
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="20px"/>
				<col width="70px"/>
				<col width="15px"/>
				<col width="50px"/>
				<col width="1050px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="whiteMenu3">
					<img src="images/show.png" 
						height="15px" 
						width="15px" 
						title="click to show schedule" 
						style="cursor:pointer;"
						onClick="toggleSchedule()">
					</td>
					<td class="whiteMenu3"
						title="click to show schedule" 
						style="cursor:pointer;"
						onClick="toggleSchedule()">
						Schedule
					</td>
					<td class="whiteMenu3" 
						onClick="moveWeek('BACKWARD')"
						title="click to go back one week"
						style="cursor:pointer;">
						<
					</td>
					<td class="whiteMenu3"><%=uB.getNextScheduledWeek(week, weekAction) %></td>					
					<td class="whiteMenu3" 
						onClick="moveWeek('FORWARD')"
						title="click to go forward one week"
						style="cursor:pointer;">
						>
					</td>
					<td class="whiteMenu3">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>	
			<div id="scheduleShow" style="display: none";">		
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="20px"/>
				<col width="70px"/>
				<col width="15px"/>
				<col width="50px"/>
				<col width="1050px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="whiteMenu3">
					<img src="images/hide.png" 
						height="15px" 
						width="15px" 
						title="click to hide schedule" 
						style="cursor:pointer;"
						onClick="toggleSchedule()">
					</td>
					<td class="whiteMenu3"
						title="click to hide schedule" 
						style="cursor:pointer;"
						onClick="toggleSchedule()">
						Schedule
					</td>
					<td class="whiteMenu3" 
						onClick="moveWeek('BACKWARD')"
						title="click to go back one week"
						style="cursor:pointer;">
						<
					</td>
					<td class="whiteMenu3"><%=uB.getNextScheduledWeek(week, weekAction) %></td>					
					<td class="whiteMenu3" 
						onClick="moveWeek('FORWARD')"
						title="click to go forward one week"
						style="cursor:pointer;">
						>
					</td>
					<td class="whiteMenu3">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>
			<div id="schedule" style="display: none";">
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="100px"/>
				<col width="110px"/>
				<col width="110px"/>
				<col width="90px"/>
				<col width="120px"/>
				<col width="80px"/>
				<col width="140px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="150px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="menu1">Site</td>
					<td class="menu1">Status</td>
					<td class="menu1">Job Type</td>
					<td class="menu1" title="Scheduled Date">Sch. Date</td>
					<td class="menu1">Upgrade Type</td>
					<td class="menu1" title="Technologies">Tech(s)</td>
					<td class="menu1" title="Hardware Vendor">HW Vendor</td>
					<td class="menu1">FE(s)</td>
					<td class="menu1">VF CRQ</td>
					<td class="menu1">TEF CRQ</td>
					<td class="menu1" title="Access Status">Access</td>
					<td class="menu1">BO Engineer(s)</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>			
			<div style="overflow-y: auto; overflow-x: hidden; height: 125px;">
			<table style="width: 1250px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="100px"/>
				<col width="110px"/>
				<col width="110px"/>
				<col width="90px"/>
				<col width="120px"/>
				<col width="80px"/>
				<col width="140px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="150px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
			<%=uB.getWeekScheduleHTML(filterBOEngineer, week) %>
			</tbody>
			</table>			
			</div>
			<div>
		</td>
	</tr>
	<tr>
		<td height="5px" class="clientBox"></td>
	</tr>
</table>
</div>
<!--  INCLUDES  -->
<%@ include file="chgBOEngineer.txt" %>
<%@ include file="emailCopy.txt" %>
</form>
</body>
</html>