<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.devoteam.tracker.util.ServletConstants"%>
<%@ page import="com.devoteam.tracker.util.UtilBean"%>
<%@ page import="com.devoteam.tracker.model.SystemParameters"%>
<%@ page import="com.devoteam.tracker.model.User"%>
<%@ page import="com.devoteam.tracker.model.UserRole"%>
<%@ page import="com.devoteam.tracker.model.DashboardProject"%>
<%
	if ((session == null) ||
			(session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION) == null)) {
		session.setAttribute("userMessage", "Please enter a valid user id and password");
		response.sendRedirect("logon.jsp");
		return;
	}
	SystemParameters sP = (SystemParameters)session.getAttribute(ServletConstants.SYSTEM_PARAMETERS_IN_SESSION);
	
	String title = (String)session.getAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION);
	User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
	String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
	String displayScreen = title;
	UtilBean uB = new UtilBean(thisU, displayScreen, url);
	if (!uB.canSee()) {
		session.setAttribute("userMessage", "Error: Access not authorised!");
		response.sendRedirect("logon.jsp");
		return;
	}
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--Load the AJAX API COMMENTING OUT AS CURRENTLY NOT USING IT
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>-->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="refresh" content="<%=sP.getTimeoutPeriodSeconds()%>; url=logon.jsp?timeout=true">
<title>Devoteam Tracker</title>
<link rel="stylesheet" type="text/css" href="css/datepickr.css">
<link rel="stylesheet" type="text/css" href="css/dvt.css">
<script language="javascript">
<!--
	function invertClass(elementName) {
		var className = document.getElementById(elementName).className;
		if (className.substr(className.length - 3) == "Neg") {
			document.getElementById(elementName).className = 
				className.substr(0, className.length - 3);
			//document.getElementById(elementName).style.cursor =
				//"auto";
		} else {
			document.getElementById(elementName).className = 
				className + "Neg";
			//document.getElementById(elementName).style.cursor =
				//"pointer";
		}
	}
	
	function menuClick(destination) {
		//alert(destination); 
		document.getElementById("toScreen").value = destination;
		//alert(document.getElementById("toScreen").value );
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	}
	
	function getPosition(element) {
	    var xPosition = 0;
	    var yPosition = 0;
	  
	    while(element) {
	        xPosition += (element.offsetLeft - element.scrollLeft + element.clientLeft);
	        yPosition += (element.offsetTop - element.scrollTop + element.clientTop);
	        element = element.offsetParent;
	    }
	    return { x: xPosition, y: yPosition };
	}

	function isWhitespaceOrEmpty(text) {
		   return !/[^\s]/.test(text);
	}


-->
</script>
<script src="js/datetimepicker_css.js"></script>
</head>
<body onLoad="thisScreenLoad()" bgcolor="#c0c0c0">
<form id="f1" name="devoTracker" method="post" action="navigation">
<input type="hidden" name="toScreen" id="toScreen" value=""/>
<table style="table-layout: fixed; border-style: none;width:100%;">
<colgroup>
	<col width="121.5px"/>
	<col width="121.5px"/>
<tbody>
<tr>
	<td align="left" valign="center">
		<a href="http://www.devoteam.co.uk/" target="_blank">
		<img src="images/devo_full.png" height="39px" width="121.5px">
	</td>
	<td align="right" valign="center">
		<img src="images/<%=uB.logoFilename(displayScreen) %>" height="39px" width="94.5px">
	</td>
	<td>
		&nbsp;
	</td>
</tr>
<tr>
	<td colspan="3">
		<%=uB.getMenu1()%>
	</td>
</tr>
</tbody>
</table>
<!--/body>
</html-->