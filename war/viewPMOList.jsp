<%@ include file="headerLD.jsp" %>
<%
String filterSite = request.getAttribute("filterSite")==null?"All":(String)request.getAttribute("filterSite");
String hSiteTitle = filterSite.equals("All")?"Filter not set":("Current filter value: " + filterSite);	
String hSiteClass = filterSite.equals("All")?"thClick":"thClickS";	
String filterNRId = request.getAttribute("filterNRId")==null?"All":(String)request.getAttribute("filterNRId");
String hNRIdTitle = filterNRId.equals("All")?"Filter not set":("Current filter value: " + filterNRId);	
String hNRIdClass = filterNRId.equals("All")?"thClick":"thClickS";	
String[] nrIdParameters = {"Y"};
String[] siteParameters = {"Y"};
String filterStatus = request.getAttribute("filterStatus")==null?"All":(String)request.getAttribute("filterStatus");
String hStatusTitle = filterStatus.equals("All")?"Filter not set":("Current filter value: " + filterStatus);	
String hStatusClass = filterStatus.equals("All")?"thClick":"thClickS";	
String[] statusParameters = {"All"};
//String snrCommentaryType = request.getAttribute("snrCommentaryType")==null?"none":(String)request.getAttribute("snrCommentaryType");
long preCheckId = request.getAttribute("preCheckId")==null?-1:Long.parseLong((String)request.getAttribute("preCheckId"));
String filterScheduledStart = request.getAttribute("filterScheduledStart")==null?"":(String)request.getAttribute("filterScheduledStart");
String filterScheduledEnd = request.getAttribute("filterScheduledEnd")==null?"":(String)request.getAttribute("filterScheduledEnd");

StringBuilder hScheduledTitle = new StringBuilder("Scheduled Date - ");
if (filterScheduledStart.equals("")) {
	if (filterScheduledEnd.equals("")) {
		hScheduledTitle.append("filter not set");
	} else {
		hScheduledTitle.append("current filter value: all dates up to and including ");
		hScheduledTitle.append(filterScheduledEnd);
	}
} else {
	if (filterScheduledEnd.equals("")) {
		hScheduledTitle.append("current filter value: all dates from ");
		hScheduledTitle.append(filterScheduledStart);
		hScheduledTitle.append(" (inclusive)");
	} else {
		hScheduledTitle.append("current filter value: all dates between ");
		hScheduledTitle.append(filterScheduledStart);
		hScheduledTitle.append(" and ");
		hScheduledTitle.append(filterScheduledEnd);
		hScheduledTitle.append(" (inclusive)");
	}
}
String hScheduledClass = filterScheduledStart.equals("")?"thClick":"thClickS";	

String filterCanBeClosed = request.getAttribute("filterCanBeClosed")==null?"All":(String)request.getAttribute("filterCanBeClosed");
String hCanBeClosedTitle = filterCanBeClosed.equals("All")?"Filter not set":("Current filter value: " + filterCanBeClosed);	
String hCanBeClosedClass = filterCanBeClosed.equals("All")?"thClick":"thClickS";	
String[] canBeClosedParameters = {"All"};

long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String action = "viewPMO";
boolean disableSNRCommentaryType = true;
String snrCommentaryType = "PMO";
String site = request.getAttribute("site")==null?"-1":(String)request.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"none":(String)request.getAttribute("nrId");
String vcTitle = "Site: " + site + ", NR Id: " + nrId;
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="viewPMOList.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.PMO%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<input type="hidden" name="whichFilter" id="whichFilter" value=""/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<input type="hidden" name="historyDT" id="historyDT" value=""/>
<input type="hidden" name="previousAbortType" id="previousAbortType" value=""/>
<input type="hidden" name="onDetail" id="onDetail" value=""/>
<script language="javascript">
<!--
var selectedSNRId = <%=snrId%>;
var selectedNRId = "<%=nrId%>";
var selectedSite = "<%=site%>";
function thisScreenLoad() {
	//alert("<%=buttonPressed%>");
<%
	if (snrId != -1) {
%>
	snrSelect(<%=snrId%>, "<%=site%>", "<%=nrId%>");
<%
		if (buttonPressed.equals("showDetail")) {
%>
	showDetail();
<%	
		} else if (buttonPressed.startsWith("viewCom")) {
			if (buttonPressed.equals("viewComD")) {
%>
	showDetail();
<%
			}
%>
	showViewCommentaryP();
<%	
		} else if (buttonPressed.startsWith("addCom")) {
			if (buttonPressed.equals("addComD")) {
	%>
	showDetail();
	<%
			}
	%>
	showAddCommentaryP();
	<%	
		}
	}
%>
}
function tbClickx(btn, snrId) {
	alert(btn+ " " +snrId);
}

function tbClick(btn) {
	if (btn == 'CloseD') {
		var pmoD = document.getElementById("pmoD");
		pmoD.style.display = "none";
		pmoD.style.left = "0px";
		pmoD.style.top = "0px";
		pmoD.style.zIndex = "0";
	} else if (btn == 'closeNR') {
		if (!confirm("Please confirm status update to Closed" )) {
				return;
		}
		document.getElementById("buttonPressed").value = btn;
		document.getElementById("snrId").value = selectedSNRId;
		document.getElementById("f1").action = "viewPMO";
		document.getElementById("f1").submit();
	} else {
		document.getElementById("buttonPressed").value = btn;
		document.getElementById("snrId").value = selectedSNRId;
		document.getElementById("f1").action = "viewPMO";
		document.getElementById("f1").submit();
	}	
}

function snrSelect(snrId, site, nrId, canBeClosed) {
	document.getElementById("action").style.display = "inline";
	document.getElementById("showDetail").style.display = "inline";
	document.getElementById("viewCom").style.display = "inline";
	document.getElementById("addCom").style.display = "inline";
	selectedSNRId = snrId;
	selectedNRId = nrId;
	selectedSite = site;
	document.getElementById("snrId").value = selectedSNRId;
	document.getElementById("nrId").value = selectedNRId;
	document.getElementById("site").value = selectedSite;
	if (canBeClosed=="Y") {
		document.getElementById("closeNR").style.display = "inline";
	} else {
		document.getElementById("closeNR").style.display = "none";
	}
}

function filterClick(filterId, operation) {
	var filter = document.getElementById(filterId);
	filter.style.display = "none";
	filter.style.left = "0px";
	filter.style.top = "0px";
	if (operation != "cancel") {
		document.getElementById("whichFilter").value = "filter"+filterId.substring(2);
		document.getElementById("buttonPressed").value = operation;
		document.getElementById("f1").action = "viewPMO";
		document.getElementById("f1").submit();
	}
}

function headerClick(headerId, showLeft) {
	var header = document.getElementById(headerId);
	var position = getPosition(header);
	var filter = document.getElementById("df"+headerId.substring(1));
	var posL;
	if (showLeft == true) {
		posL = (position.x + header.offsetWidth) - 270;
	} else {
		posL = position.x;
	}
	filter.style.display = "inline";
	filter.style.left = posL + "px";
	filter.style.top = position.y + "px";
}

function showDetail() {
	var header = document.getElementById("hSite");
	var position = getPosition(header);
	var pmoD = document.getElementById("pmoD");
	pmoD.style.display = "inline";
	pmoD.style.left = position.x + "px";
	pmoD.style.top = position.y + "px";
	pmoD.style.zIndex = "10";
	document.getElementById("onDetail").value = "true";
}

function showViewCommentaryP() {
	var header = document.getElementById("hSite");
	var position = getPosition(header);
	var snrBC = document.getElementById("snrBC");
	snrBC.style.display = "inline";
	snrBC.style.left = position.x + "px";
	snrBC.style.top = position.y + "px";
	snrBC.style.zIndex = "70";
	var snrC = document.getElementById("snrC");
	snrC.style.display = "inline";
	snrC.style.left = position.x + "px";
	snrC.style.top = position.y + "px";
	snrC.style.zIndex = "80";
}

function showAddCommentaryP(extraScreen) {
	if (extraScreen != null) {
		document.getElementById("extraScreen").value = extraScreen;
	}
	var header = document.getElementById("hSite");
	var position = getPosition(header);
	var snrB = document.getElementById("snrBC");
	snrBC.style.display = "inline";
	snrBC.style.left = position.x + "px";
	snrBC.style.top = position.y + "px";
	snrBC.style.zIndex = "70";
	var snrC = document.getElementById("snrC");
	snrC.style.display = "inline";
	snrC.style.left = position.x + "px";
	snrC.style.top = position.y + "px";
	snrC.style.zIndex = "80";
	header = document.getElementById("hComLUB");
	position = getPosition(header);
	var aC = document.getElementById("addCommentary");
	aC.style.display = "inline";
	aC.style.left = (position.x-41) + "px";
	aC.style.top = position.y + "px";
	aC.style.zIndex = "90";
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
<col width="250px"/>
<col width="250px"/>
<col width="250px"/>
<col width="100px"/>
<col width="150px"/>
<col width="150px"/>
<col width="50px"/>
<col width="50px"/>
</colgroup>
<tbody>
<tr>
		<th class="<%=hSiteClass%>" id="hSite" onClick="headerClick('hSite', false)" title="<%=hSiteTitle%>">Site</th>
		<th class="<%=hNRIdClass%>" id="hNRId" onClick="headerClick('hNRId', false)" title="<%=hNRIdTitle%>">NR Id</th>
		<th class="<%=hStatusClass%>" id="hStatus" onClick="headerClick('hStatus', false)" title="<%=hStatusTitle%>">NR Status</th>
		<th class="<%=hScheduledClass%>" id="hScheduled" onClick="headerClick('hScheduled', true)" title="<%=hScheduledTitle.toString()%>">Scheduled</th>
		<th title="Implementation Start Date">Implementation Start</th>
		<th title="Implementation End Date">Implementation End</th>
		<th class="<%=hCanBeClosedClass%>" id="hCanBeClosed" onClick="headerClick('hCanBeClosed', true)" title="<%=hCanBeClosedTitle.toString()%>">Close?</th>
		<th>Select</th>
</tr>
</tbody>
</table>
<div style="margin:0;padding:0;border-collapse:collapse;width:1250px;height:420px;overflow-x:hidden;overflow-y:auto;"
>
<table id="table1" style="width: 1250px;"
>
<colgroup>
<col width="250px"/>
<col width="250px"/>
<col width="250px"/>
<col width="100px"/>
<col width="150px"/>
<col width="150px"/>
<col width="50px"/>
<col width="50px"/>
</colgroup>
<tbody>
<%=uB.getPMOListHTML(filterSite, filterNRId, filterStatus, 
		filterScheduledStart, filterScheduledEnd, snrId, filterCanBeClosed)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="showDetail" onClick="tbClick('showDetail')" onMouseOut="invertClass('showDetail')" onMouseOver="invertClass('showDetail')" style="float:right;display:none" class="menu2Item">View PMO Detail</div>
<div id="viewCom" onClick="tbClick('viewCom')" onMouseOut="invertClass('viewCom')" onMouseOver="invertClass('viewCom')" style="float:right;display:none" class="menu2Item">View Commentary</div>
<div id="addCom" onClick="tbClick('addCom')" onMouseOut="invertClass('addCom')" onMouseOver="invertClass('addCom')" style="float:right;display:none" class="menu2Item">Add Commentary</div>
<div id="closeNR" onClick="tbClick('closeNR')" onMouseOut="invertClass('closeNR')" onMouseOver="invertClass('closeNR')" style="float:right;display:none" class="menu2Item">Close NR</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</div>
<!-- filters -->
<div id="dfSite" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfSite', 'cancel')">x</div>
	<div style="clear:both">Site Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("Site", "filter", "filter", filterSite, siteParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSite', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSite', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSite', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id="dfNRId" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfNRId', 'cancel')">x</div>
	<div style="clear:both">NR Id Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("NRId", "filter", "filter", filterNRId, nrIdParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfNRId', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfNRId', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfNRId', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfStatus" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfStatus', 'cancel')">x</div>
	<div style="clear:both">Status Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("Status", "filter", "filter", filterStatus, statusParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfStatus', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfStatus', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfStatus', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfScheduled" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfScheduled', 'cancel')">x</div>
	<div style="clear:both">Scheduled From Filter</div>
	<div style="padding-bottom:10px">
		<input style="width:240px" type="text" id="filterScheduledStart" name="filterScheduledStart" value="<%=filterScheduledStart%>" />
		<img src="images/cal.gif" onclick="javascript:NewCssCal ('filterScheduledStart','ddMMyyyy','arrow')" style="cursor:pointer"/>
	</div>
	<div style="clear:both">Scheduled To Filter</div>
	<div style="padding-bottom:10px">
		<input style="width:240px" type="text" id="filterScheduledEnd" name="filterScheduledEnd" value="<%=filterScheduledEnd%>" />
		<img src="images/cal.gif" onclick="javascript:NewCssCal ('filterScheduledEnd','ddMMyyyy','arrow')" style="cursor:pointer"/>
	</div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfScheduled', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfScheduled', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfScheduled', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfCanBeClosed" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfCanBeClosed', 'cancel')">x</div>
	<div style="clear:both">Close Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("CanBeClosed", "filter", "filter", filterCanBeClosed, canBeClosedParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCanBeClosed', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCanBeClosed', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCanBeClosed', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<!-- end of filters -->
<!-- detail -->
<%@ include file="viewPMODetail.txt" %>
<!-- view SNR commentary -->
<%@ include file="viewSNRCommentary.txt" %>
<!-- add SNR commentary -->
<%@ include file="addSNRCommentary.txt" %>
</form>
</body>
</html>