<%@ include file="headerLD.jsp" %>
<% 
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String showRaiseCRQ = request.getAttribute("showRaiseCRQ")==null?"N":(String)request.getAttribute("showRaiseCRQ");
String showCloseCRQ = request.getAttribute("showCloseCRQ")==null?"N":(String)request.getAttribute("showCloseCRQ");
String showOtherCRQ = request.getAttribute("showOtherCRQ")==null?"N":(String)request.getAttribute("showOtherCRQ");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="backOffice.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.CRQ_ACCESS%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<input type="hidden" name="snrId" id="snrId" value=""/>
<input type="hidden" name="showRaiseCRQ" id="showRaiseCRQ" value="<%=showRaiseCRQ%>"/>
<input type="hidden" name="showCloseCRQ" id="showCloseCRQ" value="<%=showCloseCRQ%>"/>
<input type="hidden" name="showOtherCRQ" id="showOtherCRQ" value="<%=showOtherCRQ%>"/>
<script type="text/javascript">

function thisScreenLoad() {
	var showRaiseCRQ = "<%=showRaiseCRQ%>";
	if (showRaiseCRQ=="Y") {
		document.getElementById("raiseCRQ").style.display = "inline";
		document.getElementById("raiseCRQCountHide").style.display = "none";
		document.getElementById("raiseCRQCountShow").style.display = "inline";
	}
	var showCloseCRQ = "<%=showCloseCRQ%>";
	if (showCloseCRQ=="Y") {
		document.getElementById("closeCRQ").style.display = "inline";
		document.getElementById("closeCRQCountHide").style.display = "none";
		document.getElementById("closeCRQCountShow").style.display = "inline";
	}
	var showOtherCRQ = "<%=showOtherCRQ%>";
	if (showOtherCRQ=="Y") {
		document.getElementById("otherCRQ").style.display = "inline";
		document.getElementById("otherCRQCountHide").style.display = "none";
		document.getElementById("otherCRQCountShow").style.display = "inline";
	}
}

function toggleRaiseCRQ() {
	var current = document.getElementById("raiseCRQ").style.display;
	if (current=="none") {
		document.getElementById("raiseCRQ").style.display = "inline";
		document.getElementById("raiseCRQCountHide").style.display = "none";
		document.getElementById("raiseCRQCountShow").style.display = "inline";
		document.getElementById("showRaiseCRQ").value = "Y";
	} else {
		document.getElementById("raiseCRQ").style.display = "none";
		document.getElementById("raiseCRQCountHide").style.display = "inline";
		document.getElementById("raiseCRQCountShow").style.display = "none";
		document.getElementById("showRaiseCRQ").value = "N";
	}
}

function toggleCloseCRQ() {
	var current = document.getElementById("closeCRQ").style.display;
	if (current=="none") {
		document.getElementById("closeCRQ").style.display = "inline";
		document.getElementById("closeCRQCountHide").style.display = "none";
		document.getElementById("closeCRQCountShow").style.display = "inline";
		document.getElementById("showCloseCRQ").value = "Y";
	} else {
		document.getElementById("closeCRQ").style.display = "none";
		document.getElementById("closeCRQCountHide").style.display = "inline";
		document.getElementById("closeCRQCountShow").style.display = "none";
		document.getElementById("showCloseCRQ").value = "N";
	}
}

function toggleOtherCRQ() {
	var current = document.getElementById("otherCRQ").style.display;
	if (current=="none") {
		document.getElementById("otherCRQ").style.display = "inline";
		document.getElementById("otherCRQCountHide").style.display = "none";
		document.getElementById("otherCRQCountShow").style.display = "inline";
		document.getElementById("showOtherCRQ").value = "Y";
	} else {
		document.getElementById("otherCRQ").style.display = "none";
		document.getElementById("otherCRQCountHide").style.display = "inline";
		document.getElementById("otherCRQCountShow").style.display = "none";
		document.getElementById("showOtherCRQ").value = "N";
	}
}

function snrCRQAccessDetail(snrId) {
	document.getElementById("snrId").value = snrId;
	document.getElementById("toScreen").value = "<%=ServletConstants.CRQ_ACCESS_DETAIL%>";
	document.getElementById("f1").action = "navigation";
	document.getElementById("f1").submit();	
}

</script>
<div style="width:1250px;" >
<table style="table-layout: fixed; border-style: none; width:1250px;">
	<tr>
		<td height="5px" class="clientBox"></td>
	</tr>
	<tr>
		<td class="clientBox">	
			<div id="raiseCRQCountHide" style="display: inline;">		
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="20px"/>
				<col width="1215px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="whiteMenu2">
					<img src="images/show.png" 
						height="15px" 
						width="15px" 
						title="click to show CRQs to raise" 
						style="cursor:pointer;"
						onClick="toggleRaiseCRQ()">
					</td>
					<td class="whiteMenu3"  
						title="click to hide CRQs to raise" 
						onClick="toggleRaiseCRQ()" 
						style="cursor:pointer;">
						Raise CRQ(s) (<%=uB.getCRQAccessCount("raiseCRQ")%>)
					</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>	
			<div id="raiseCRQCountShow" style="display: none;">		
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="20px"/>
				<col width="1215px"/>
				<col width="15px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="whiteMenu2">
					<img src="images/hide.png" 
						height="15px" 
						width="15px" 
						title="click to hide CRQs to raise" 
						style="cursor:pointer;"
						onClick="toggleRaiseCRQ()">
					</td>
					<td class="whiteMenu3"  
						title="click to hide CRQs to raise" 
						onClick="toggleRaiseCRQ()" 
						style="cursor:pointer;">
						Raise CRQ(s) (<%=uB.getCRQAccessCount("raiseCRQ")%>)
					</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>
			<div id="raiseCRQ" style="display: none;">
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="115px"/>
				<col width="150px"/>
				<col width="80px"/>
				<col width="190px"/>
				<col width="80px"/>
				<col width="150px"/>
				<col width="30px"/>
				<col width="200px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="menu1">Site</td>
					<td class="menu1">Status</td>
					<td class="menu1">VF CRQ</td>
					<td class="menu1">Job Type</td>
					<td class="menu1">CRQ Status</td>
					<td class="menu1">TEF CRQ</td>
					<td class="menu1" title="Technologies">Tech(s)</td>
					<td class="menu1" title="Scheduled Date">Sch. Date</td>
					<td class="menu1" title="Access Status">Access</td>
					<td class="menu1" title="Next PreCheck">NPC</td>
					<td class="menu1" title="Field Engineers">FE(s)</td>
				</tr>
			</tbody>
			</table>
			<div style="overflow-y: auto; overflow-x: hidden; height: 185px;">
			<table style="width: 1250px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="115px"/>
				<col width="150px"/>
				<col width="80px"/>
				<col width="190px"/>
				<col width="80px"/>
				<col width="150px"/>
				<col width="30px"/>
				<col width="200px"/>
			</colgroup>
			<tbody>
				<%=uB.getCRQAccessHeaderHTML("raiseCRQ")%>
			</tbody>
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
			<div id="closeCRQCountHide" style="display: inline;">		
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
						title="click to show CRQs to close" 
						style="cursor:pointer;"
						onClick="toggleCloseCRQ()">
					</td>
					<td class="whiteMenu2"
						title="click to show CRQs to close" 
						style="cursor:pointer;"
						onClick="toggleCloseCRQ()">
						Close CRQ(s) (<%=uB.getCRQAccessCount("closeCRQ")%>)
					</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>	
			<div id="closeCRQCountShow" style="display: none;">		
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
						title="click to hide CRQs to close" 
						style="cursor:pointer;"
						onClick="toggleCloseCRQ()">
					</td>
					<td class="whiteMenu2"
						title="click to show CRQs to close" 
						style="cursor:pointer;"
						onClick="toggleCloseCRQ()">
						Close CRQ(s) (<%=uB.getCRQAccessCount("closeCRQ")%>)
					</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>
			<div id="closeCRQ" style="display: none;">
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="115px"/>
				<col width="150px"/>
				<col width="80px"/>
				<col width="190px"/>
				<col width="80px"/>
				<col width="150px"/>
				<col width="30px"/>
				<col width="200px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="menu1">Site</td>
					<td class="menu1">Status</td>
					<td class="menu1">VF CRQ</td>
					<td class="menu1">Job Type</td>
					<td class="menu1">CRQ Status</td>
					<td class="menu1">TEF CRQ</td>
					<td class="menu1" title="Technologies">Tech(s)</td>
					<td class="menu1" title="Scheduled Date">Sch. Date</td>
					<td class="menu1" title="Access Status">Access</td>
					<td class="menu1" title="Next PreCheck">NPC</td>
					<td class="menu1" title="Field Engineers">FE(s)</td>
				</tr>
			</tbody>
			</table>
			<div style="overflow-y: auto; overflow-x: hidden; height: 150px;">
			<table style="width: 1250px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="115px"/>
				<col width="150px"/>
				<col width="80px"/>
				<col width="190px"/>
				<col width="80px"/>
				<col width="150px"/>
				<col width="30px"/>
				<col width="200px"/>
			</colgroup>
			<tbody>
				<%=uB.getCRQAccessHeaderHTML("closeCRQ")%>
			</tbody>
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
			<div id="otherCRQCountHide" style="display: inline;">		
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
						title="click to show other sites" 
						style="cursor:pointer;"
						onClick="toggleOtherCRQ()">
					</td>
					<td class="whiteMenu3" 
						title="click to show other sites" 
						style="cursor:pointer;"
						onClick="toggleOtherCRQ()">
						Other Site(s) (<%=uB.getCRQAccessCount("otherCRQ")%>)
					</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>	
			<div id="otherCRQCountShow" style="display: none;">		
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
						title="click to hide other sites" 
						style="cursor:pointer;"
						onClick="toggleOtherCRQ()">
					</td>
					<td class="whiteMenu3" 
						title="click to show other sites" 
						style="cursor:pointer;"
						onClick="toggleOtherCRQ()">
						Other Site(s) (<%=uB.getCRQAccessCount("otherCRQ")%>)
					</td>
					<td>&nbsp;</td>
				</tr>
			</tbody>
			</table>
			</div>
			<div id="otherCRQ" style="display: none;">
			<table style="width: 1250px; height: 20px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="115px"/>
				<col width="150px"/>
				<col width="80px"/>
				<col width="190px"/>
				<col width="80px"/>
				<col width="150px"/>
				<col width="30px"/>
				<col width="200px"/>
			</colgroup>
			<tbody>
				<tr>
					<td>&nbsp;</td>
					<td class="menu1">Site</td>
					<td class="menu1">Status</td>
					<td class="menu1">VF CRQ</td>
					<td class="menu1">Job Type</td>
					<td class="menu1">CRQ Status</td>
					<td class="menu1">TEF CRQ</td>
					<td class="menu1" title="Technologies">Tech(s)</td>
					<td class="menu1" title="Scheduled Date">Sch. Date</td>
					<td class="menu1" title="Access Status">Access</td>
					<td class="menu1" title="Next PreCheck">NPC</td>
					<td class="menu1" title="Field Engineers">FE(s)</td>
				</tr>
			</tbody>
			</table>
			<div style="overflow-y: auto; overflow-x: hidden; height: 125px;">
			<table style="width: 1250px; table-layout: fixed;">
			<colgroup>
				<col width="15px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="80px"/>
				<col width="115px"/>
				<col width="150px"/>
				<col width="80px"/>
				<col width="190px"/>
				<col width="80px"/>
				<col width="150px"/>
				<col width="30px"/>
				<col width="200px"/>
			</colgroup>
			<tbody>
				<%=uB.getCRQAccessHeaderHTML("otherCRQ")%>
			</tbody>
			</table>
			</div>
			</div>
		</td>
	</tr>
</table>
</div>