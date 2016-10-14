<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%

String message = session.getAttribute("userMessage")!=null?
	(String)session.getAttribute("userMessage"):	
	request.getAttribute("userMessage")!=null?
	(String)request.getAttribute("userMessage"):
	request.getParameter("timeout")!=null?"Your session has timed out. Please log in again.":"";
session.removeAttribute("userMessage");
//if (session != null) {
	session.invalidate();
//}%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Devoteam Tracker</title>
<link rel="stylesheet" href="css/dvt.css" type="text/css">
<script language="javascript">

function resetPwd(action) {
	var userEmail = document.getElementById("email").value;
	if (userEmail=="") {
		alert("Please enter email address before asking for password reset");
	} else {
		if (confirm("Confirm request for password reset via email. This action cannot be be undone")) {
			document.getElementById("action").value = "resetPwd";
			document.getElementById("f1").action = "/logon";
			document.getElementById("f1").submit();
		}
	}	
}

</script>
</head>
<body bgcolor="#F0F0F0">
<form id="f1" action="/logon" method="post">
<input type="hidden" name="action" id="action" value=""/>

<div>
	<table style="width: 100%; table-layout: fixed; align: left;">
	<colgroup>
		<col width="37%"/>
		<col width="310px"/>
		<col width="37%x"/>	
	</colgroup>
	<tbody>
		<tr>
			<td rowspan="2">				
				<a href="http://www.devoteam.co.uk/" target="_blank">
				<img src="images/dev_logo_rvb.png" height="110px" width="264px"></a>
				</td>
			<td class="lTitle1" align="left" valign="bottom">SMART</td>
			<td >&nbsp;</td>
		</tr>
		<tr>
			<td class="lTitle2" align="left" valign="top">Part of the innovation engine</td>
			<td >&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="lTitle2">&nbsp;<td>
			<td >&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="lTitle3" align="left">Login</td>
			<td >&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="lTitle4" align="left">Email Address</td>
			<td >&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="lTitle4" align="left"><input class="lText" type="text" id="email" name="email"></input></td>
			<td >&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="lTitle4" align="left">Password</td>
			<td >&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="lTitle4" align="left"><input class="lText" type="password" name="password"></input></td>
			<td >&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td class="lTitle4" align="left"><input class="LButton" type="submit" value="&nbsp;&nbsp;Log In&nbsp;&nbsp;" /></td>
			<td >&nbsp;</td>
		</tr>		
		<tr>
			<td>&nbsp;</td>
			<td class="lTitle5" align="left" onClick="resetPwd()" title="Click to get new password">
				I forgot my password</td>
			<td >&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td class="lMessage" colspan="3" align="center"><%=message%><td>
		</tr>
	</table>
</div>
</form>

</body>
</html>