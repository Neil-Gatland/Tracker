<%@ include file="header.jsp" %>
<%
long userId = request.getAttribute("userId")==null?-1:Long.parseLong((String)request.getAttribute("userId"));
String userStatus = request.getAttribute("userStatus")==null?"none":(String)request.getAttribute("userStatus");
String userName = request.getAttribute("userName")==null?"none":(String)request.getAttribute("userName");
String userType = request.getAttribute("userType")==null?"none":(String)request.getAttribute("userType");
String buttonPressed = request.getAttribute("buttonPressed")==null?"none":(String)request.getAttribute("buttonPressed");
String userFirstName = request.getAttribute("userFirstName")==null?"":(String)request.getAttribute("userFirstName");
String userSurname = request.getAttribute("userSurname")==null?"":(String)request.getAttribute("userSurname");
String userEmail = request.getAttribute("userEmail")==null?"":(String)request.getAttribute("userEmail");
String userCustomer = request.getAttribute("userCustomer")==null?"":(String)request.getAttribute("userCustomer");
String selectUserType = request.getAttribute("selectUserType")==null?"":(String)request.getAttribute("selectUserType");
String[] selectCustomerId = request.getAttribute("selectCustomerId")==null?null:(String[])request.getAttribute("selectCustomerId");
String selectThirdParty = request.getAttribute("selectThirdParty")==null?"":(String)request.getAttribute("selectThirdParty");
String jobType = request.getAttribute("jobType")==null?"":(String)request.getAttribute("jobType");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="userAdministration.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.USER_ADMINISTRATION%>"/>
<input type="hidden" name="buttonPressed" id="buttonPressed" value="<%=buttonPressed%>"/>
<input type="hidden" name="userId" id="userId" value="<%=userId%>"/>
<input type="hidden" name="userStatus" id="userStatus" value="<%=userStatus%>"/>
<input type="hidden" name="userEmail" id="userEmail" value="<%=userEmail%>"/>
<input type="hidden" name="userName" id="userName" value="<%=userName%>"/>
<input type="hidden" name="userType" id="userType" value="<%=userType%>"/>
<input type="hidden" name="jobType" id="jobType" value="<%=jobType%>"/>
<script language="javascript">
<!--
var selectedUserId = <%=userId%>;
var selectedUserStatus = "<%=userStatus%>";
var selectedUserEmail = "<%=userEmail%>";
var selectedUserName = "<%=userName%>";
var selectedUserType = "<%=userType%>";

function thisScreenLoad() {
	var aUJT = document.getElementById("amendUserJobTypes");
	aUJT.style.display = "none";
	aUJT.style.left = "0px";
	aUJT.style.top = "0px";
<%	if (userId != -1) {%>
	userSelect(<%=userId%>, "<%=userStatus%>", "<%=userName%>", "<%=userType%>");
<%		if (buttonPressed.equals("amendStatus")) {%>
	var header = document.getElementById("hUserId");
	var position = getPosition(header);
	var aUS = document.getElementById("amendUserStatus");
	aUS.style.display = "inline";
	aUS.style.left = position.x + "px";
	aUS.style.top = position.y + "px";
<%		} else if (buttonPressed.equals("amendEmail")) {%>
	var header = document.getElementById("hUserId");
	var position = getPosition(header);
	var aUR = document.getElementById("amendUserEmail");
	aUR.style.display = "inline";
	aUR.style.left = position.x + "px";
	aUR.style.top = position.y + "px";
<%		} else if (buttonPressed.equals("amendRoles")) {%>
	var header = document.getElementById("hUserId");
	var position = getPosition(header);
	var aUR = document.getElementById("amendUserRoles");
	aUR.style.display = "inline";
	aUR.style.left = position.x + "px";
	aUR.style.top = position.y + "px";
<%		} else if ((buttonPressed.equals("amendJobTypes"))||
		(buttonPressed.equals("deleteUserJobTypeSubmit"))||
		(buttonPressed.equals("addUserJobTypeSubmit"))) {%>
	var header = document.getElementById("hUserId");
	var position = getPosition(header);
	var aUR = document.getElementById("amendUserJobTypes");
	aUR.style.display = "inline";
	aUR.style.left = position.x + "px";
	aUR.style.top = position.y + "px";
<%		} else if ((buttonPressed.equals("resetPwd")) && (!message.startsWith("Error: "))) {
%>	
	alert("<%=message%>. The user will be asked to choose a new password when logging on.");
<%		}
	} 	
	if ((buttonPressed.equals("newUser")) || (buttonPressed.equals("confirmAddUserSubmit"))) {%>
	var header = document.getElementById("hUserId");
	var position = getPosition(header);
	var aU = document.getElementById("addUser");
	aU.style.display = "inline";
	aU.style.left = position.x + "px";
	aU.style.top = position.y + "px";
	<%	if (buttonPressed.equals("confirmAddUserSubmit")) {%>
		if (confirm("This first name/surname combination exists already. Press 'OK' to create a new user with this name.")) {
			addUserClick('addUserSubmitConfirm');
		}
	<%	}
	}%>
}	
		
function userSelect(userId, userStatus, userName, userType, userEmail) {
	if (<%=thisU.getUserId()%> == userId) {
		document.getElementById("amendStatus").style.display = "none";
		document.getElementById("amendRoles").style.display = "none";
		document.getElementById("resetPwd").style.display = "none";
		document.getElementById("amendEmail").style.display = "none";
	} else {	
		document.getElementById("amendStatus").style.display = "inline";
		if (userType == "<%=User.USER_TYPE_CUSTOMER%>") {
			document.getElementById("amendRoles").style.display = "none";
		} else {	
			document.getElementById("amendRoles").style.display = "inline";
		}		
		document.getElementById("resetPwd").style.display = "inline";	
		document.getElementById("amendEmail").style.display = "inline";
		if ((userType=="<%=User.USER_TYPE_CUSTOMER%>")||(userType=="<%=User.USER_TYPE_THIRD_PARTY%>")) {
			document.getElementById("amendJobTypes").style.display = "inline";
		} else {
			document.getElementById("amendJobTypes").style.display = "none";
		}
	}
	selectedUserId = userId;
	selectedUserStatus = userStatus;
	selectedUserEmail = userEmail;
	selectedUserName = userName;
	selectedUserType = userType;
}

function tbClick(btn) {
	if (btn == "resetPwd") {
		if (!confirm("Please confirm password reset for " + selectedUserName)) {
			return;
		}
	}
	document.getElementById("buttonPressed").value = btn;
	document.getElementById("userId").value = selectedUserId;
	document.getElementById("userStatus").value = selectedUserStatus;
	document.getElementById("userEmail").value = selectedUserEmail;
	document.getElementById("userName").value = selectedUserName;
	document.getElementById("userType").value = selectedUserType;
	document.getElementById("f1").action = "userAdministration";
	document.getElementById("f1").submit();
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
<col width="70px"/>
<col width="300x"/>
<col width="70px"/>
<col width="70px"/>
<col width="275px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="255px"/>
<col width="50px"/>
</colgroup>
<tbody>
<tr>
		<th id="hUserId">User Id</th>
		<th>User Name</th>
		<th>Status</th>
		<th>Type</th>
		<th>Customer Name (Third Party)</th>
		<th title="Access Administrator">AA</th>
		<th title="Administrator">Ad</th>
		<th title="Back Office Engineer">BE</th>
		<th title="CRM Administrator">CA</th>
		<th title="Field Engineer">FE</th>
		<th title="Finanace Administrator">FA</th>
		<th title="PMO">P</th>
		<th title="Scheduler">Sc</th>
		<th title="Email">Email</th>
		<th>Select</th>
</tr>
</tbody>
</table>
<div style="margin:0;padding:0;border-collapse:collapse;width:1250px;height:420px;overflow-x:hidden;overflow-y:auto;"
>
<table style="width: 1250px;"
>
<colgroup>
<col width="70px"/>
<col width="300x"/>
<col width="70px"/>
<col width="70px"/>
<col width="275px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="20px"/>
<col width="255px"/>
<col width="50px"/>
</colgroup>
<tbody>
<%=uB.getUserAdministrationListHTML(userId)%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="newUser" onClick="tbClick('newUser')" onMouseOut="invertClass('newUser')" onMouseOver="invertClass('newUser')" style="float:right" class="menu2Item">Create New User</div>
<div id="amendStatus" onClick="tbClick('amendStatus')" onMouseOut="invertClass('amendStatus')" onMouseOver="invertClass('amendStatus')" style="float:right;display:none" class="menu2Item">Amend Status</div>
<div id="amendRoles" onClick="tbClick('amendRoles')" onMouseOut="invertClass('amendRoles')" onMouseOver="invertClass('amendRoles')" style="float:right;display:none" class="menu2Item">Amend Roles</div>
<div id="resetPwd" onClick="tbClick('resetPwd')" onMouseOut="invertClass('resetPwd')" onMouseOver="invertClass('resetPwd')" style="float:right;display:none" class="menu2Item">Reset Password</div>
<div id="amendEmail" onClick="tbClick('amendEmail')" onMouseOut="invertClass('amendEmail')" onMouseOver="invertClass('amendEmail')" style="float:right;display:none" class="menu2Item">Amend Email</div>
<div id="amendJobTypes" onClick="tbClick('amendJobTypes')" onMouseOut="invertClass('amendJobTypes')" onMouseOver="invertClass('amendJobTypes')" style="float:right;display: none;" class="menu2Item">Amend Job Types</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</div>
<!-- add user -->
<%@ include file="addUser.txt" %>
<!-- amend user roles -->
<%@ include file="amendUserRoles.txt" %>
<!-- amend user status -->
<%@ include file="amendUserStatus.txt" %>
<!-- amend user email -->
<%@ include file="amendUserEmail.txt" %>
<!-- amend user job types -->
<%@ include file="amendUserJobTypes.txt" %>
</form>
</body>
</html>