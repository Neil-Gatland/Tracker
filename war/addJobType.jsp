<div id="addJobType" class="filter" style="width:540px">
	<div class="closeX" title="close" onClick="addJobTypeClick('cancel')">x</div>
	<div style="clear:both;text-align:center;overflow:hidden"><h2 class="white">Add Job Type</h2></div>
	<div>Job Type</div>
	<div style="padding-bottom:10px"><input style="width:95%" type="text" name="newJobType" id="newJobType" value="" maxlength="100"></div>
	<div>Project Requestor</div>
	<div style="padding-bottom:10px"><input style="width:95%" type="text" name="newProjectRequestor" id="newProjectRequestor" value="" maxlength="100"></div>
	<div>Project Requestor Email</div>
	<div style="padding-bottom:10px"><input style="width:95%" type="text" name="newProjectRequestorEmail" id="newProjectRequestorEmail" value="" maxlength="100"></div>
	<div>Project Manager</div>
	<div style="padding-bottom:10px"><input style="width:95%" type="text" name="newProjectManager" id="newProjectManager" value="" maxlength="100"></div>
	<div>Project Manager Email</div>
	<div style="padding-bottom:10px"><input style="width:95%" type="text" name="newProjectManagerEmail" id="newProjectManagerEmail" value="" maxlength="100"></div>
	<div>Acting Customer</div>
	<div style="padding-bottom:10px"><input style="width:95%" type="text" name="newActingCustomer" id="newActingCustomer" value="" maxlength="100"></div>
	<div>Redundant</div>
	<div>
		<select id="selectRedundantFilter" style="width:10%;" name="selectRedundantFilter" class="filter">
			<option value="N">N</option>
			<option value="Y">Y</option>
		</select>
	</div>
	<div>&nbsp;</div> 
	<div style="width:240px;margin:0 auto;padding-bottom:10px">
		<div style="float:left"><input style="width:120px" type="button" class="button" onClick="addJobTypeClick('jobTypeAdd')" value="Submit" /></div>
		<div style="float:left"><input style="width:120px" type="button" class="button" onClick="addJobTypeClick('cancel')" value="Cancel" /></div>
	</div>
</div>
<script language="javascript">
<!--
function addJobTypeClick(operation) {
	var f1 = document.getElementById("f1");
	if (operation == "cancel") {
		var aJT = document.getElementById("addJobType");
		aJT.style.display = "none";
		aJT.style.left = "0px";
		aJT.style.top = "0px";
	} else { //if (operation == "addJobTypeSubmit") {
		if (isWhitespaceOrEmpty(document.getElementById("newJobType").value)) {
			alert("Job Type cannot be blank")
		} else if (isWhitespaceOrEmpty(document.getElementById("newProjectRequestor").value)) {
			alert("Project Requestor cannot be blank")
		} else if (isWhitespaceOrEmpty(document.getElementById("newProjectRequestorEmail").value)) {
			alert("Project Requestor Email cannot be blank")
		} else if (isWhitespaceOrEmpty(document.getElementById("newProjectManager").value)) {
			alert("Project Manager cannot be blank")
		} else if (isWhitespaceOrEmpty(document.getElementById("newProjectManagerEmail").value)) {
			alert("Project Manager Email cannot be blank")
		} else if (isWhitespaceOrEmpty(document.getElementById("newActingCustomer").value)) {
			alert("Acting Customer cannot be blank")
		} else {
			document.getElementById("buttonPressed").value = operation;
			f1.action = "/jobTypeMaintenance";
			f1.submit();
		}
	}
}
	
-->
</script>