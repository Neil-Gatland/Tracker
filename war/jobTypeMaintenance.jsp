<%@ include file="header.jsp" %>
<%
String jobType = request.getAttribute("jobType")==null?"none":(String)request.getAttribute("jobType");
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="jobTypeMaint.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.JOB_TYPE_MAINTENANCE%>"/>
<input type="hidden" name="jobType" id="jobType" value="<%=jobType%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value="<%=buttonPressed%>"/>
<script language="javascript">
<!--
var selectedJobType = "<%=jobType%>";
function thisScreenLoad() {
	
}	
function jobTypeSelect(jobType,projectRequestor,projectRequestorEmail,projectManager,projectManagerEmail,actingCustomer) {
	document.getElementById("jobTypeDelete").style.visibility = "visible";
	document.getElementById("jobTypeAmend").style.visibility = "visible";
	selectedJobType = jobType;
	selectedProjectRequestor = projectRequestor;
	selectedProjectRequestorEmail = projectRequestorEmail;
	selectedProjectManager = projectManager;
	selectedProjectManagerEmail = projectManagerEmail;
	selectedActingCustomer = actingCustomer;
}
function tbClick(btn) {
	if (btn == "jobTypeDelete") {
		if (!confirm("Please confirm deletion of " + selectedJobType)) {
			return;
		} else {
			document.getElementById("buttonPressed").value = btn;
			document.getElementById("jobType").value = selectedJobType;
			var f1 = document.getElementById("f1");
			f1.action = "/jobTypeMaintenance";
			f1.submit();
		}
	} else if (btn == "jobTypeAdd") {
		var header = document.getElementById("hJobType");
		var position = getPosition(header);
		var aJT = document.getElementById("addJobType");
		aJT.style.display = "inline";
		aJT.style.left = position.x + "px";
		aJT.style.top = position.y + "px";
	} else if (btn == "jobTypeAmend") {
		var header = document.getElementById("hJobType");
		var position = getPosition(header);
		var aJT = document.getElementById("amendJobType");
		aJT.style.display = "inline";
		aJT.style.left = position.x + "px";
		aJT.style.top = position.y + "px";
		document.getElementById("currentJobType").value = selectedJobType;
		document.getElementById("amendProjectRequestor").value = selectedProjectRequestor;
		document.getElementById("amendProjectRequestorEmail").value = selectedProjectRequestorEmail;
		document.getElementById("amendProjectManager").value = selectedProjectManager;
		document.getElementById("amendProjectManagerEmail").value = selectedProjectManagerEmail;
		document.getElementById("amendActingCustomer").value = selectedActingCustomer;
	}
	
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
<col width="190px"/>
<col width="130px"/>
<col width="230px"/>
<col width="130px"/>
<col width="220px"/>
<col width="120px"/>
<col width="140px"/>
<col width="70px"/>
<col width="30px"/>
</colgroup>
<tbody>
<tr>
		<th id="hJobType">Job Type</th>
		<th>Project Requestor</th>
		<th>Project Requestor Email</th>
		<th>Project Manager</th>
		<th>Project Manager Email</th>
		<th>Acting Customer</th>
		<th>Last Updated By</th>
		<th title="Last Updated Date">LU Date</th>
		<th title="Select">&nbsp;</th>
</tr>
</tbody>
</table>
<div style="margin:0;padding:0;border-collapse:collapse;width:1250px;height:420px;overflow-x:hidden;overflow-y:auto;"
>
<table style="width: 1250px;"
>
<colgroup>
<col width="190px"/>
<col width="130px"/>
<col width="230px"/>
<col width="130px"/>
<col width="220px"/>
<col width="120px"/>
<col width="140px"/>
<col width="70px"/>
<col width="30px"/>
</colgroup>
<tbody>
<%=uB.getJobTypeListHTML(jobType)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="jobTypeAdd" onClick="tbClick('jobTypeAdd')" onMouseOut="invertClass('jobTypeAdd')" onMouseOver="invertClass('jobTypeAdd')" style="float:right" class="menu2Item">Add Job Type</div>
<div id="jobTypeAmend" onClick="tbClick('jobTypeAmend')" onMouseOut="invertClass('jobTypeAmend')" onMouseOver="invertClass('jobTypeAmend')" style="float:right;visibility:hidden" class="menu2Item">Amend Job Type</div>
<div id="jobTypeDelete" onClick="tbClick('jobTypeDelete')" onMouseOut="invertClass('jobTypeDelete')" onMouseOver="invertClass('jobTypeDelete')" style="float:right;visibility:hidden" class="menu2Item">Delete Job Type</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</div>
<!-- add Job Type -->
<%@ include file="addJobType.jsp" %>
<!-- amend Job Type -->
<%@ include file="amendJobType.txt" %>
</form>
</body>
</html>