<%@ include file="headerLD.jsp" %>
<%
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String action = "cancelledSNR";
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="cancelledSNRList.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.VIEW_SNR_HISTORY%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<input type="hidden" name="whichFilter" id="whichFilter" value=""/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<script language="javascript">
<!--
var selectedSNRId = <%=snrId%>;
function thisScreenLoad() {
	//alert("<%=buttonPressed%>");
<%
	if (snrId != -1) {
%>
	snrSelect(<%=snrId%>);
<%	
	}
%>
}
function tbClickx(btn, snrId) {
	alert(btn+ " " +snrId);
}

function tbClick(btn) {
	//alert("selectedHistoryInd:"+selectedHistoryInd);
	document.getElementById("buttonPressed").value = btn;
	if (btn == "reopen") {
		if (!confirm("Please confirm reopening of this NR")) {
			return;
		} else {
			document.getElementById("snrId").value = selectedSNRId;
			document.getElementById("f1").action = "cancelledSNR";
			document.getElementById("f1").submit();
		}	
	}
	
}

function snrSelect(snrId) {
	document.getElementById("action").style.display = "inline";
	document.getElementById("reopen").style.display = "inline";
	selectedSNRId = snrId;
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
<col width="250px"/>
<col width="250px"/>
<col width="250px"/>
<col width="250px"/>
<col width="50px"/>
</colgroup>
<tbody>
<tr>
		<th id="hAnchor">Site</th>
		<th>NR Id</th>
		<th>Cancelled Date</th>
		<th>Previous Status</th>
		<th>Previous Scheduled Date</th>
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
<col width="250px"/>
<col width="250px"/>
<col width="250px"/>
<col width="250px"/>
<col width="50px"/>
</colgroup>
<tbody>
<%=uB.getCancelledSNRListHTML(snrId)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="reopen" onClick="tbClick('reopen')" onMouseOut="invertClass('reopen')" onMouseOver="invertClass('reopen')" style="float:right;display:none" class="menu2Item">Reopen</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</div>
<!-- mask -->
</form>
</body>
</html>