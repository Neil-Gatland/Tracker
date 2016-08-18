<%@ include file="header.jsp" %>
<%
long snrIdFilter = request.getAttribute("snrIdFilter")==null?-1:Long.parseLong((String)request.getAttribute("snrIdFilter"));
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
long preCheckId = request.getAttribute("preCheckId")==null?-1:Long.parseLong((String)request.getAttribute("preCheckId"));
String snrCommentaryType = request.getAttribute("snrCommentaryType")==null?"none":(String)request.getAttribute("snrCommentaryType");
String preChecksOutOfOrder = request.getAttribute("preChecksOutOfOrder")==null?"false":(String)request.getAttribute("preChecksOutOfOrder");
boolean disableSNRCommentaryType = false;
if (request.getAttribute("snrCommentaryTypeInit") != null) {
	snrCommentaryType = (String)request.getAttribute("snrCommentaryTypeInit");
	disableSNRCommentaryType = true;
}
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String action = "preCheckMaintenance";
String[] nrIdParameters = {"N"};
String filterNRId = request.getAttribute("filterNRId")==null?"All":(String)request.getAttribute("filterNRId");
String hNRIdTitle = filterNRId.equals("All")?"Filter not set":("Current filter value: " + filterNRId);	
String hNRIdClass = filterNRId.equals("All")?"thClick":"thClickS";	
String filterJobType = request.getAttribute("filterJobType")==null?"All":(String)request.getAttribute("filterJobType");
String hJobTypeTitle = "Job Type - " + (filterJobType.equals("All")?"filter not set":("current filter value: " + filterJobType));	
String hJobTypeClass = filterJobType.equals("All")?"thClick":"thClickS";	
boolean multi = false;
String extraVis = "";
String rName = "closeI";
String site = request.getAttribute("site")==null?"-1":(String)request.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"none":(String)request.getAttribute("nrId");
String vcTitle = "Site: " + site + ", NR Id: " + nrId;
String isBO = thisU.hasUserRole(UserRole.ROLE_B_O_ENGINEER)==true?"Y":"N";
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="preCheckMaint.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.PRE_CHECK_MAINTENANCE%>"/>
<input type="hidden" name="snrIdFilter" id="snrIdFilter" value="<%=snrIdFilter%>"/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="preCheckId" id="preCheckId" value="<%=preCheckId%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value="<%=buttonPressed%>"/>
<input type="hidden" name="whichFilter" id="whichFilter" value=""/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<script language="javascript">
<!--
var selectedPreCheckId = <%=preCheckId%>;
var selectedSNRId = <%=snrId%>;
var selectedNRId = "<%=nrId%>";
var selectedSite = "<%=site%>";
function thisScreenLoad() {

	var isBO = "<%=isBO%>";	
	if (isBO=="Y") {
		document.getElementById("closeD").style.display = "inline";
	}	
	
<%if (snrIdFilter != -1) {%>
	document.getElementById("action").style.display = "inline";
	document.getElementById("showAll").style.display = "inline";
<%}
  if (snrId != -1) {%>
	document.getElementById("action").style.display = "inline";
	document.getElementById("preCheckUpdate").style.display = "inline";
	document.getElementById("rrcSNR").style.display = "inline";
   	document.getElementById("addCom").style.display = "inline";
   	document.getElementById("viewCom").style.display = "inline";
<%}
  if (buttonPressed.startsWith("addCom")) {
	if (buttonPressed.equals("addComI")) {
%>
	showPreCheckItems();
	showAddCommentary("pCI");
<%	
			} else {
%>
	showAddCommentary();
<%  }
  } else if (buttonPressed.startsWith("viewCom")) {
		if (buttonPressed.equals("viewComI")) {
%>
	showPreCheckItems();
<%		
		}
%>		
	showViewCommentary();
<%} else if (buttonPressed.equals("preCheckUpdate")) {%>
	showPreCheckItems();
<%} else if (buttonPressed.equals("showBU")) {%>
	showPreCheckBatch();
<%}%>

}	

function tbClick(btn) {
	if (btn == "completeI") {
<%if (preChecksOutOfOrder.equals("true")) {%>
		alert("This pre-check cannot be completed until all previous outstanding pre-checks for its NR have been completed")
<%} else {%>
		var header = document.getElementById("hAnchor");
		var position = getPosition(header);
		var completePC = document.getElementById("completePC");
		completePC.style.display = "inline";
		completePC.style.left = position.x + "px";
		completePC.style.top = position.y + "px";
		completePC.style.zIndex = "30";
<%}%>
	} else if (btn == "closeC") {
		hideViewCommentary();
	} else if (btn == "closeD")	{
		document.getElementById("toScreen").value = "<%=ServletConstants.BO%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();	
	} else {
		document.getElementById("buttonPressed").value = btn;
		document.getElementById("preCheckId").value = selectedPreCheckId;
		document.getElementById("snrId").value = selectedSNRId;
		document.getElementById("nrId").value = selectedNRId;
		document.getElementById("site").value = selectedSite;
		document.getElementById("f1").action = "preCheckMaintenance";
		document.getElementById("f1").submit();
	}
}

function preCheckSelect(preCheckId, snrId, site, nrId) {
	document.getElementById("action").style.display = "inline";
   	document.getElementById("preCheckUpdate").style.display = "inline";
   	//document.getElementById("rrcSNR").style.display = "inline";
   	document.getElementById("addCom").style.display = "inline";
   	document.getElementById("viewCom").style.display = "inline";
	selectedPreCheckId = preCheckId;
	selectedSNRId = snrId;
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
		document.getElementById("f1").action = "preCheckMaintenance";
		document.getElementById("f1").submit();
	}
}

function headerClick(headerId, showLeft, anchorId) {
	var header = document.getElementById(anchorId||headerId);
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
	filter.style.zIndex = "20";
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
<col width="110px"/>
<col width="40px"/>
<col width="80px"/>
<col width="120px"/>
<col width="370px"/>
<col width="200px"/>
<col width="80px"/>
<col width="50px"/>
</colgroup>
<tbody>
<tr>
		<th class="<%=hNRIdClass%>" id="hAnchor" onClick="headerClick('hNRId', false, 'hAnchor')" title="<%=hNRIdTitle%>">NR Id</th>
		<th>Pre-Check Type</th>
		<th title="Pre-Check Days">PCD</th>
		<th title="Pre-Check Scheduled Date">PCSD</th>
		<th>Pre-Check Status</th>
		<th class="<%=hJobTypeClass%>" id="hJobType" onClick="headerClick('hJobType', false)" title="<%=hJobTypeTitle%>">Job Type</th>
		<th>Last Updated By</th>
		<th title="Last Updated Date">LU Date</th>
		<th>Select</th>
</tr>
</tbody>
</table>
<div style="margin:0;padding:0;border-collapse:collapse;width:1250px;height:420px;overflow-x:hidden;overflow-y:auto;"
>
<table style="width: 1250px;"
>
<colgroup>
<col width="200px"/>
<col width="110px"/>
<col width="40px"/>
<col width="80px"/>
<col width="120px"/>
<col width="370px"/>
<col width="200px"/>
<col width="80px"/>
<col width="50px"/>
</colgroup>
<tbody>
<%=uB.getPreCheckOutstandingListHTML(session, snrIdFilter, 
		preCheckId, filterNRId, filterJobType)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="closeD" onClick="tbClick('closeD')" onMouseOut="invertClass('closeD')" onMouseOver="invertClass('closeD')" style="float:right;display:none" class="menu2Item">Return</div>
<div id="showBU" onClick="tbClick('showBU')" onMouseOut="invertClass('showBU')" onMouseOver="invertClass('showBU')" style="float:right;" class="menu2Item">Batch Initial PreChecks</div>
<div id="showAll" onClick="tbClick('showAll')" onMouseOut="invertClass('showAll')" onMouseOver="invertClass('showAll')" style="float:right;display:none" class="menu2Item" title="Show outstanding pre-checks for all SNRs">Show All</div>
<div id="preCheckUpdate" onClick="tbClick('preCheckUpdate')" onMouseOut="invertClass('preCheckUpdate')" onMouseOver="invertClass('preCheckUpdate')" style="float:right;display:none" class="menu2Item">Update Pre-Check</div>
<div id="rrcSNR" onClick="tbClick('rrcSNR')" onMouseOut="invertClass('rrcSNR')" onMouseOver="invertClass('rrcSNR')" style="float:right;display:none" class="menu2Item" title="Reschedule/Reallocate/Cancel SNR">RRC SNR</div>
<div id="viewCom" onClick="tbClick('viewCom')" onMouseOut="invertClass('viewCom')" onMouseOver="invertClass('viewCom')" style="float:right;display:none" class="menu2Item">View Commentary</div>
<div id="addCom" onClick="tbClick('addCom')" onMouseOut="invertClass('addCom')" onMouseOver="invertClass('addCom')" style="float:right;display:none" class="menu2Item">Add Commentary</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</div>
<!-- filters -->
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
<div id ="dfJobType" class="filter" style="width:270px">
	<div class="closeX" title="close" onClick="filterClick('dfJobType', 'cancel')">x</div>
	<div style="clear:both">Job Type Filter</div>
	<div style="padding-bottom:10px"><%=uB.getSelectHTML("JobType", "filter", "filter", filterJobType) %></div>
	<div style="width:240px;margin:0 auto;">
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfJobType', 'ok')" value="OK" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfJobType', 'clear')" value="Clear" /></div>
		<div style="float:left"><input style="width:80px" type="button" class="button" onClick="filterClick('dfJobType', 'clearAll')" value="Clear All" /></div>
	</div>
</div>
<!-- end of filters -->
<!-- mask -->
<%@ include file="workQueuesBlank.jsp" %>
<!-- view SNR commentary -->
<%@ include file="viewSNRCommentary.txt" %>
<!-- add SNR commentary -->
<%@ include file="addSNRCommentary.txt" %>
<!-- pre-check items -->
<%@ include file="preCheckItems.txt" %>
<!-- pre-check batch update -->
<%@ include file="preCheckBatch.txt" %>
</form>
</body>
</html>