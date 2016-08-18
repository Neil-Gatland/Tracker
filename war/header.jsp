<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.devoteam.tracker.util.ServletConstants"%>
<%@ page import="com.devoteam.tracker.util.UtilBean"%>
<%@ page import="com.devoteam.tracker.model.SystemParameters"%>
<%@ page import="com.devoteam.tracker.model.User"%>
<%@ page import="com.devoteam.tracker.model.UserRole"%>
<%
	if ((session == null) ||
			(session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION) == null)) {
		session.setAttribute("userMessage", "Please enter a valid user id and password");
		response.sendRedirect("logon.jsp");
		return;
	}
	SystemParameters sP = (SystemParameters)session.getAttribute(ServletConstants.SYSTEM_PARAMETERS_IN_SESSION);
	
	String message = session.getAttribute("userMessage")!=null?
		(String)session.getAttribute("userMessage"):	
		request.getAttribute("userMessage")!=null?
		(String)request.getAttribute("userMessage"):"&nbsp;";
	session.removeAttribute("userMessage");
	String messageClass = "infoMsg"; 
	if (message.startsWith("Error: ")) {
		messageClass = "errorMsg";
		message = message.substring(7);
	}
	String title = (String)session.getAttribute(ServletConstants.SCREEN_TITLE_IN_SESSION);
	User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
	String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
	String displayScreen = title;
	//String displayScreen = (String)session.getAttribute("screenTitle");
	//session.setAttribute("fromScreen", displayScreen);
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
<body onLoad="thisScreenLoad()">
<form id="f1" name="devoTracker" method="post" action="navigation">
<input type="hidden" name="toScreen" id="toScreen" value=""/>
<div style="float:left;width:30%"><img src="images/dvt_only.png" height="15%" width="15%"><img src="images/dvt_mg.png" height="15%" width="15%"></div>
<div style="float:right;width:30%">&nbsp;</div>
<div style="text-align:center;overflow:hidden"><h2><%=displayScreen%></h2></div>
<div style="clear:both;text-align:center;font-weight:bold;" class="<%=messageClass%>"><%=message%></div>
<div class="menu1" style="height:2px"></div>
<%=uB.getMenu1()%>
<div class="menu1" style="clear:both;height:2px"></div>
<div class="menu2" style="height:2px"></div>
<%=uB.getMenu2()%>
<div class="menu2" style="clear:both;height:2px"></div>
<!--/body>
</html-->