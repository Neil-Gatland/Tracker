<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%

String message = session.getAttribute("userMessage")!=null?
	(String)session.getAttribute("userMessage"):	
	request.getAttribute("userMessage")!=null?
	(String)request.getAttribute("userMessage"):
	request.getParameter("timeout")!=null?"Your session has timed out. Please log in again.":"Please enter a valid user id and password to continue";
session.removeAttribute("userMessage");
//if (session != null) {
	session.invalidate();
//}
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Devoteam Tracker</title>
<link rel="stylesheet" href="css/dvt.css" type="text/css">
</head>
<body>
<form action="/logon" method="post">
	<div><img src="images/dvt.jpg" height="25%" width="25%"></div>
	<div class="menu1" style="text-align:center">Welcome to the Devoteam Tracker Application</div>
	<div class="menu2" style="text-align:center"><%=message%></div>
	<div>
	    <div style="float:left;width:50%;text-align:right;margin:10px">User Id:</div><div style="float:left;margin:10px"><input class="text" type="text" name="userId"></input></div>
	    <div style="clear:both;height:0px">&nbsp;</div><!-- just to make it work in IE! -->
	    <div style="float:left;width:50%;text-align:right;margin:10px">Password:</div><div style="float:left;margin:10px"><input class="text" type="password" name="password"></input></div>
	    <div style="clear:both;text-align:center"><input class="button" type="submit" value="Logon" /></div>
    </div>
</form>

</body>
</html>