<%@ include file="headerLD.jsp" %>
<%
String filterCustomer;
String hCustomerClass;
String hCustomerClick;
String hCustomerTitle;
if (thisU.getUserType().equalsIgnoreCase(User.USER_TYPE_CUSTOMER)) {
	filterCustomer = thisU.getCustomerNames()[0];
	hCustomerClass = "";
	hCustomerClick = "";
	hCustomerTitle = "Current filter value: " + filterCustomer;
} else {
	String fC = (String)request.getAttribute("filterCustomer");
	filterCustomer = fC==null?"All":fC;
	hCustomerClass = filterCustomer.equals("All")?"thClick":"thClickS";
	hCustomerClick = "headerClick('hCustomer', false)";
	hCustomerTitle = filterCustomer.equals("All")?"Filter not set":("Current filter value: " + filterCustomer);
}
String filterSNRId = request.getAttribute("filterSNRId")==null
	?request.getParameter("filterSNRId")==null||request.getParameter("filterSNRId").equals("-1")
	?"":request.getParameter("filterSNRId"):(String)request.getAttribute("filterSNRId");
String hSNRIdTitle = filterSNRId.equals("")?"Filter not set":("Current filter value: " + filterSNRId);	
String hSNRIdClass = filterSNRId.equals("")?"thClick":"thClickS";	
String filterPotId = request.getAttribute("filterPotId")==null?"All":(String)request.getAttribute("filterPotId");
String hPotIdTitle = filterPotId.equals("All")?"Filter not set":("Current filter value: " + filterPotId);	
String hPotIdClass = filterPotId.equals("All")?"thClick":"thClickS";	
String filterPotName = request.getAttribute("filterPotName")==null?"All":(String)request.getAttribute("filterPotName");
String hPotNameTitle = filterPotName.equals("All")?"Filter not set":("Current filter value: " + filterPotName);	
String hPotNameClass = filterPotName.equals("All")?"thClick":"thClickS";	
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
String filterImplementationStatus = request.getAttribute("filterImplementationStatus")==null?"All":(String)request.getAttribute("filterImplementationStatus");
String hImplementationStatusTitle = (filterImplementationStatus.equals("All")?"Filter not set":("Current filter value: " + filterImplementationStatus));	
String hImplementationStatusClass = filterImplementationStatus.equals("All")?"thClick":"thClickS";	
String[] implementationStatusParameters = {"All"};
String filterPCO = request.getAttribute("filterPCO")==null?"All":(String)request.getAttribute("filterPCO");
String hPCOTitle = "Pre-Check Outstanding - " + (filterPCO.equals("All")?"filter not set":("current filter value: " + filterPCO));	
String hPCOClass = filterPCO.equals("All")?"thClick":"thClickS";	
String filterAbortType = request.getAttribute("filterAbortType")==null?"All":(String)request.getAttribute("filterAbortType");
String hAbortTypeTitle = "Abort Type - " + (filterAbortType.equals("All")?"filter not set":("current filter value: " + filterAbortType));	
String hAbortTypeClass = filterAbortType.equals("All")?"thClick":"thClickS";	
String[] abortTypeParameters = {"All"};
String filterHistory = request.getAttribute("filterHistory")==null?"All":(String)request.getAttribute("filterHistory");
String hHistoryTitle = "History - " + (filterHistory.equals("All")?"filter not set":("current filter value: " + filterHistory));	
String hHistoryClass = filterHistory.equals("All")?"thClick":"thClickS";	
String snrCommentaryType = request.getAttribute("snrCommentaryType")==null?"none":(String)request.getAttribute("snrCommentaryType");
boolean disableSNRCommentaryType = false;
long preCheckId = request.getAttribute("preCheckId")==null?-1:Long.parseLong((String)request.getAttribute("preCheckId"));

long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
boolean historyInd = request.getAttribute("historyInd")==null?false:((String)request.getAttribute("historyInd")).equals("true");
long historyDT = request.getAttribute("historyDT")==null?0:Long.parseLong((String)request.getAttribute("historyDT"));
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String action = "viewSNRHistory";
String site = request.getAttribute("site")==null?"-1":(String)request.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"none":(String)request.getAttribute("nrId");
String vcTitle = "Site: " + site + ", NR Id: " + nrId;
String isBO = thisU.hasUserRole(UserRole.ROLE_B_O_ENGINEER)==true?"Y":"N";
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="viewSNRHistory.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.VIEW_SNR_HISTORY%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<input type="hidden" name="whichFilter" id="whichFilter" value=""/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="historyInd" id="historyInd" value="<%=historyInd?"true":"false"%>"/>
<input type="hidden" name="historyDT" id="historyDT" value="<%=historyDT%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<script language="javascript">
<!--
var selectedSNRId = <%=snrId%>;
var selectedHistoryInd = "<%=historyInd?"true":"false"%>";
var selectedHistoryDT = <%=historyDT%>;
var selectedNRId = "<%=nrId%>";
var selectedSite = "<%=site%>";
function thisScreenLoad() {
	var isBO = "<%=isBO%>";	
	if (isBO=="Y") {
		document.getElementById("closeD").style.display = "inline";
	}	
	
<%
	if (snrId != -1) {
%>
//alert(<%=historyInd?"true":"false"%>);
	snrSelect(<%=snrId%>, "<%=historyInd?"Y":"N"%>", <%=historyDT%>);
<%
		if (buttonPressed.equals("showDetail")) {
%>
	var header = document.getElementById("hSite");
	var position = getPosition(header);
	var snrB = document.getElementById("snrB");
	snrB.style.display = "inline";
	snrB.style.left = position.x + "px";
	snrB.style.top = position.y + "px";
	snrB.style.zIndex = "10";
	var snrD = document.getElementById("snrD");
	snrD.style.display = "inline";
	snrD.style.left = position.x + "px";
	snrD.style.top = position.y + "px";
	snrD.style.zIndex = "20";
<%	
		} else if (buttonPressed.equals("viewCom")) {
%>
	showViewCommentaryH();
<%	
		} else if (buttonPressed.startsWith("addCom")) {
	%>
	showAddCommentaryH();
	<%	
		}
	}
%>
}
function tbClickx(btn, snrId) {
	alert(btn+ " " +snrId);
}

function tbClick(btn) {
	document.getElementById("buttonPressed").value = btn;
	document.getElementById("snrId").value = selectedSNRId;
	document.getElementById("nrId").value = selectedNRId;
	document.getElementById("site").value = selectedSite;
	document.getElementById("historyInd").value = selectedHistoryInd;
	document.getElementById("historyDT").value = selectedHistoryDT;
	document.getElementById("f1").action = "viewSNRHistory";
	document.getElementById("f1").submit();
}

function snrSelect(snrId, historyInd, historyDT, site, nrId) {
	document.getElementById("action").style.display = "inline";
	document.getElementById("showDetail").style.display = "inline";
	document.getElementById("viewCom").style.display = "inline";
	selectedSNRId = snrId;
	if (historyInd == "Y") {
		selectedHistoryInd = "true";	
	} else {
		selectedHistoryInd = "false";	
	}
	selectedHistoryDT = historyDT;	
	selectedNRId = nrId;
	selectedSite = site;
}

function filterClick(filterId, operation) {
	var filter = document.getElementById(filterId);
	filter.style.display = "none";
	filter.style.left = "0px";
	filter.style.top = "0px";
	if (operation != "cancel") {
		document.getElementById("whichFilter").value = "filter"+filterId.substring(2);
		document.getElementById("buttonPressed").value = operation;
		document.getElementById("f1").action = "viewSNRHistory";
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

function showViewCommentaryH() {
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

function showAddCommentaryH(extraScreen) {
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
<col width="200px"/>
<col width="260px"/>
<col width="225px"/>
<col width="225px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="90px"/>
<col width="90px"/>
<col width="50px"/>
</colgroup>
<tbody>
<tr>
		<th class="<%=hSiteClass%>" id="hSite" onClick="headerClick('hSite', false)" title="<%=hSiteTitle%>">Site</th>
		<th class="<%=hNRIdClass%>" id="hNRId" onClick="headerClick('hNRId', false)" title="<%=hNRIdTitle%>">NR Id</th>
		<th class="<%=hStatusClass%>" id="hStatus" onClick="headerClick('hStatus', false)" title="<%=hStatusTitle%>">NR Status</th>
		<th class="<%=hImplementationStatusClass%>" id="hImplementationStatus" onClick="headerClick('hImplementationStatus', true)" title="<%=hImplementationStatusTitle%>">Implementation Status</th>
		<th title="Commentary Count">CC</th>
		<th class="<%=hAbortTypeClass%>" id="hAbortType" onClick="headerClick('hAbortType', true)" title="<%=hAbortTypeTitle%>">A</th>
		<th class="<%=hHistoryClass%>" id="hHistory" onClick="headerClick('hHistory', true)" title="<%=hHistoryTitle%>">H</th>
		<th title="Scheduled Date">Sched. Date</th>
		<th id="hHistoryDate">History Date</th>
		<th>Select</th>
</tr>
</tbody>
</table>
<div style="margin:0;padding:0;border-collapse:collapse;width:1250px;height:420px;overflow-x:hidden;overflow-y:auto;"
>
<table id="table1" style="width: 1250px;"
>
<colgroup>
<col width="200px"/>
<col width="260px"/>
<col width="225px"/>
<col width="225px"/>
<col width="40px"/>
<col width="40px"/>
<col width="40px"/>
<col width="90px"/>
<col width="90px"/>
<col width="50px"/>
</colgroup>
<tbody>
<%=uB.getSNRHistoryListHTML(filterSNRId, filterCustomer, filterPotId, filterPotName, 
	filterSite, filterNRId, filterStatus, filterImplementationStatus,
	filterAbortType, filterHistory, snrId, historyInd, historyDT)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="showDetail" onClick="tbClick('showDetail')" onMouseOut="invertClass('showDetail')" onMouseOver="invertClass('showDetail')" style="float:right;display:none" class="menu2Item">View NR Detail</div>
<div id="viewCom" onClick="tbClick('viewCom')" onMouseOut="invertClass('viewCom')" onMouseOver="invertClass('viewCom')" style="float:right;display:none" class="menu2Item">View Commentary</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</div>
<!-- filters -->
<div id="dfSNRId" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfSNRId', 'cancel')">x</div>
	<div style="clear:both">SNR Id Filter</div>
	<div style="padding-bottom:10px"><input id="filterSNRId" name="filterSNRId" style="width:250px" type="text" value="<%=filterSNRId%>"></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSNRId', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSNRId', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfSNRId', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id="dfCustomer" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfCustomer', 'cancel')">x</div>
	<div style="clear:both">Customer Name Filter</div>
	<div style="padding-bottom:10px"><%=uB.getCustomerFilterHTML("filter", filterCustomer) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCustomer', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCustomer', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfCustomer', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id="dfPotId" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfPotId', 'cancel')">x</div>
	<div style="clear:both">Pot Id Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("PotId", "filter", "filter", filterPotId) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPotId', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPotId', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPotId', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id="dfPotName" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfPotName', 'cancel')">x</div>
	<div style="clear:both">Pot Name Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("PotName", "filter", "filter", filterPotName) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPotName', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPotName', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPotName', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
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
<div id ="dfImplementationStatus" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfImplementationStatus', 'cancel')">x</div>
	<div style="clear:both">Implementation Status Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("ImplementationStatus", "filter", "filter", filterImplementationStatus, implementationStatusParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfImplementationStatus', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfImplementationStatus', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfImplementationStatus', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfPCO" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfPCO', 'cancel')">x</div>
	<div style="clear:both">Pre-Check Outstanding Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("PCO", "filter", "filter", filterPCO) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPCO', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPCO', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfPCO', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfAbortType" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfAbortType', 'cancel')">x</div>
	<div style="clear:both">Abort Type Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("AbortType", "filter", "filter", filterAbortType, abortTypeParameters) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfAbortType', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfAbortType', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfAbortType', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<div id ="dfHistory" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfHistory', 'cancel')">x</div>
	<div style="clear:both">History Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("History", "filter", "filter", filterHistory) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfHistory', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfHistory', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfHistory', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<!-- end of filters -->
<!-- mask -->
<%@ include file="workQueuesBlank.jsp" %>
<!-- SNR detail -->
<%@ include file="viewSNRDetail.txt" %>
<!-- view SNR commentary -->
<%@ include file="viewSNRCommentary.txt" %>
<!-- add SNR commentary -->
<%@ include file="addSNRCommentary.txt" %>
</form>
</body>
</html>