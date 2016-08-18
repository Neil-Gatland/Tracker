<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.devoteam.tracker.util.ServletConstants"%>
<%
if (session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION) == null) {
	session.setAttribute("userMessage", "Please enter a valid user id and password");
	response.sendRedirect("logon.jsp");
	return;
}

String message = session.getAttribute("userMessage")!=null?
	(String)session.getAttribute("userMessage"):	
	request.getAttribute("userMessage")!=null?
	(String)request.getAttribute("userMessage"):"";
session.removeAttribute("userMessage");
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Devoteam Tracker</title>
</head>
<body>
<div>Choose Role</div>
<div></div><font color="red"><%=message%></font></div>
</body>
</html>