<%@ include file="header.jsp" %>
<input type="hidden" name="fromScreen" id="fromScreen" value="workQueues.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.WORK_QUEUES%>"/>
<script language="javascript">
<!--
var selectedSNRId = 0;
function thisScreenLoad() {
	//alert('loaded');
}
function tbClickx(btn, snrId) {
	alert(btn+ " " +snrId);
}

function tbClick(btn) {
	alert(btn+ " " +selectedSNRId);
}

function snrSelect(snrId, status) {
	document.getElementById("action").style.visibility = "visible";
	document.getElementById("detail").style.visibility = "visible";
	document.getElementById("viewSNRHist").style.visibility = "visible";
	document.getElementById("viewSiteConf").style.visibility = "visible";
	document.getElementById("viewCom").style.visibility = "visible";
	document.getElementById("addCom").style.visibility = "visible";
    if ((status == 'Awaiting Scheduling') || (status == 'On Hold')) {
    	document.getElementById("holdRel").style.visibility = "visible";
	} else {
		document.getElementById("holdRel").style.visibility = "hidden";
	}
	selectedSNRId = snrId;
}
-->
</script>
<div style="width:1270px;margin:0 auto;margin-top:20px;">
<div style="
margin: 0; padding: 0; border-collapse: collapse; width: 1250px; height: 460px; overflow: hidden; border: 1px solid black;"
>
<table style="width: 1250px; height: 20px;"
>
<colgroup>
<col width="1250px"/>
</colgroup>
<tbody>
<tr>
		<th style="text-align:center">SNR Detail (Id: 2)</th>
</tr>
</tbody>
</table>
<div style="margin:0;padding:0;border-collapse:collapse;width:1250px;height:420px;overflow-x:hidden;overflow-y:auto;"
>
<table style="width: 1250px;"
>
<colgroup>
<col width="200px"/>
<col width="215px"/>
<col width="200px"/>
<col width="215px"/>
<col width="200px"/>
<col width="220px"/>
</colgroup>
<tbody>
<!--%=uB.getSNRSummaryListHTML()%  -->
<tr>
<td class="grid1t">Customer: </td>
<td colspan="5" class="grid1i">Vodafone</td>
</tr>
<tr>
<td class="grid2t">Site: </td>
<td class="grid2i">77878</td>
<td class="grid2t">NR Id: </td>
<td class="grid2i">012345678</td>
<td class="grid2t">Status: </td>
<td class="grid2i">Awaiting Scheduling</td>
</tr>
<tr>
<td class="grid1t">Upgrade Type: </td>
<td class="grid1i">New Batteries</td>
<td class="grid1t">Eastings: </td>
<td class="grid1i">000000000</td>
<td class="grid1t">Northings: </td>
<td class="grid1i">000000000</td>
</tr>
<tr>
<td class="grid2t">Postcode: </td>
<td class="grid2i">CV1 3TT</td>
<td class="grid2t">Region: </td>
<td class="grid2i">Midlands</td>
<td class="grid2t">EF345 Claim Date: </td>
<td class="grid2i">01/01/2014</td>
</tr>
<tr>
<td class="grid1t">EF360 Claim Date:</td>
<td class="grid1i">01/01/2014</td>
<td class="grid1t">EF400 Claim Date:</td>
<td class="grid1i">01/01/2014</td>
<td class="grid1t">EF410 Claim Date:</td>
<td class="grid1i">01/01/2014</td>
</tr>
<tr>
<td class="grid2t">P1 Site:</td>
<td class="grid2i">Y</td>
<td class="grid2t">OBASS:</td>
<td class="grid2i">Y</td>
<td class="grid2t">RAMS:</td>
<td class="grid2i">N</td>
</tr>
<tr>
<td class="grid1t">Escort:</td>
<td class="grid1i">N</td>
<td class="grid1t">Health & Safety:</td>
<td class="grid1i">Y</td>
<td class="grid1t">Scheduled Date:</td>
<td class="grid1i">01/01/2014</td>
</tr>
<tr>
<td class="grid2t">Outage Period:</td>
<td class="grid2i">1.5</td>
<td class="grid2t">Access Confirmed:</td>
<td class="grid2i">Y</td>
<td class="grid2t">Access Cost:</td>
<td class="grid2i">2.50</td>
</tr>
<tr>
<td class="grid1t">Consumable Cost:</td>
<td class="grid1i">0.99</td>
<td class="grid1t">OOH Weekend:</td>
<td class="grid1i">Y</td>
<td class="grid1t">CR/IN Reference:</td>
<td class="grid1i">CR</td>
</tr>
<tr>
<td class="grid2t">CR/IN Start Date:</td>
<td class="grid2i">01/01/2014</td>
<td class="grid2t">CR/IN End Date:</td>
<td class="grid2i">01/01/2014</td>
<td class="grid2t">CR/IN Used:</td>
<td class="grid2i">Y</td>
</tr>
<tr>
<td class="grid1t">CR/IN Closed:</td>
<td class="grid1i">N</td>
<td class="grid1t">Implementation Status:</td>
<td class="grid1i">Completed</td>
<td class="grid1t">Implementation Start Date:</td>
<td class="grid1i">01/01/2014</td>
</tr>
<tr>
<td class="grid2t">Implementation End Date:</td>
<td class="grid2i">01/01/2014</td>
<td class="grid2t">Abort Type:</td>
<td class="grid2i">Third Party</td>
<td class="grid2t">2G:</td>
<td class="grid2i">N</td>
</tr>
<tr>
<td class="grid1t">3G:</td>
<td class="grid1i">Y</td>
<td class="grid1t">4G:</td>
<td class="grid1i">Y</td>
<td class="grid1t">O2:</td>
<td class="grid1i">N</td>
</tr>
<tr>
<td class="grid2t">Health Checks:</td>
<td class="grid2i">Y</td>
<td class="grid2t">Active Alarms:</td>
<td class="grid2i">N</td>
<td class="grid2t">NSA NetActs:</td>
<td class="grid2i">N</td>
</tr>
<tr>
<td class="grid1t">HOP Delivered:</td>
<td class="grid1i">Y</td>
<td class="grid1t">HOP Filename:</td>
<td colspan="3" class="grid1i">test.txt</td>
</tr>
<tr>
<td class="grid2t">History Date:</td>
<td class="grid2i">01/01/2014</td>
<td class="grid2t">Last Updated Date:</td>
<td class="grid2i">01/01/2014</td>
<td class="grid2t">Last Updated By:</td>
<td class="grid2i">Farokh Engineer</td>
</tr>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;visibility:hidden" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="detail" onClick="tbClick('detail')" onMouseOut="invertClass('detail')" onMouseOver="invertClass('detail')" style="float:right;visibility:hidden" class="menu2Item">View SNR Detail</div>
<div id="viewSNRHist" onClick="tbClick('viewSNRHist')" onMouseOut="invertClass('viewSNRHist')" onMouseOver="invertClass('viewSNRHist')" style="float:right;visibility:hidden" class="menu2Item">View SNR History</div>
<div id="viewSiteConf" onClick="tbClick('viewSiteConf')" onMouseOut="invertClass('viewSiteConf')" onMouseOver="invertClass('viewSiteConf')" style="float:right;visibility:hidden" class="menu2Item">View Site Configuration</div>
<div id="viewCom" onClick="tbClick('viewCom')" onMouseOut="invertClass('viewCom')" onMouseOver="invertClass('viewCom')" style="float:right;visibility:hidden" class="menu2Item">View Commentary</div>
<div id="addCom" onClick="tbClick('addCom')" onMouseOut="invertClass('addCom')" onMouseOver="invertClass('addCom')" style="float:right;visibility:hidden" class="menu2Item">Add Commentary</div>
<div id="holdRel" onClick="tbClick('holdRel')" onMouseOut="invertClass('holdRel')" onMouseOver="invertClass('holdRel')" style="float:right;visibility:hidden" class="menu2Item">Hold/Release</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</div>
</form>
</body>
</html>