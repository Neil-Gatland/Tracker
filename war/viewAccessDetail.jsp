<%@ include file="headerLD.jsp" %>
<%
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
String site = request.getAttribute("site")==null?"-1":(String)request.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"-1":(String)request.getAttribute("nrId");
String snrStatus = request.getAttribute("snrStatus")==null?"-1":(String)request.getAttribute("snrStatus");
String action = "viewAccessDetail";
String p1SiteInd = request.getAttribute("p1SiteInd")==null?" ":(String)request.getAttribute("p1SiteInd");
String ramsInd = request.getAttribute("ramsInd")==null?" ":(String)request.getAttribute("ramsInd");
String healthSafetyInd = request.getAttribute("healthSafetyInd")==null?" ":(String)request.getAttribute("healthSafetyInd");
String accessConfirmedInd = request.getAttribute("accessConfirmedInd")==null?" ":(String)request.getAttribute("accessConfirmedInd");
String obassInd = request.getAttribute("obassInd")==null?" ":(String)request.getAttribute("obassInd");
String escortInd = request.getAttribute("escortInd")==null?" ":(String)request.getAttribute("escortInd");
String tefOutageRequired = request.getAttribute("tefOutageRequired")==null?" ":(String)request.getAttribute("tefOutageRequired");
String vfArrangeAccess = request.getAttribute("vfArrangeAccess")==null?" ":(String)request.getAttribute("vfArrangeAccess");
String twoManSite = request.getAttribute("twoManSite")==null?" ":(String)request.getAttribute("twoManSite");
String oohWeekendInd = request.getAttribute("oohWeekendInd")==null?" ":(String)request.getAttribute("oohWeekendInd");
String crINReference = request.getAttribute("crINReference")==null?" ":(String)request.getAttribute("crINReference");		
String tefOutageNos = request.getAttribute("tefOutageNos")==null?" ":(String)request.getAttribute("tefOutageNos");		
String outagePeriod = request.getAttribute("outagePeriod")==null?" ":(String)request.getAttribute("outagePeriod");		
String consumableCost = request.getAttribute("consumableCost")==null?" ":(String)request.getAttribute("consumableCost");		
String accessCost = request.getAttribute("accessCost")==null?" ":(String)request.getAttribute("accessCost");		
String siteName = request.getAttribute("siteName")==null?" ":(String)request.getAttribute("siteName");		
String siteAcessInformation = request.getAttribute("siteAcessInformation")==null?" ":(String)request.getAttribute("siteAcessInformation");			
String permitType = request.getAttribute("permitType")==null?" ":(String)request.getAttribute("permitType");			
String accessStatus = request.getAttribute("accessStatus")==null?" ":(String)request.getAttribute("accessStatus");
String week = request.getAttribute("week")==null?"":(String)request.getAttribute("week");
String weekAction = request.getAttribute("weekAction")==null?"NOW":(String)request.getAttribute("weekAction");
String showSchedule = request.getAttribute("showSchedule")==null?"N":(String)request.getAttribute("showSchedule");
String showOSWork = request.getAttribute("showOSWork")==null?"N":(String)request.getAttribute("showOSWork");		
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="viewAccessDetail.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.VIEW_ACCESS_DETAIL%>"/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<input type="hidden" name="snrStatus" id="snrStatus" value="<%=snrStatus%>"/>
<input type="hidden" name="week" id="week" value="<%=week%>"/>
<input type="hidden" name="weekAction" id="weekAction" value="<%=weekAction%>"/>
<input type="hidden" name="showSchedule" id="showSchedule" value="<%=showSchedule%>"/>
<input type="hidden" name="showOSWork" id="showOSWork" value="<%=showOSWork%>"/>
<script language="javascript">
<!--

function thisScreenLoad() {
	//alert("snrId ="+"<%=snrId%>"+" VF CRQ = "+"<%=crINReference%>");
} 

function tbClick(btn) {
		if (btn == "closeD")	{
			document.getElementById("toScreen").value = "<%=ServletConstants.BACK_OFFICE%>";
			document.getElementById("f1").action = "navigation";
			document.getElementById("f1").submit();
	} 
}

-->
</script>
<div style="width:1250px;margin:0 auto;margin-top:10px;">
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="208px"/>
<col width="208px"/>
<col width="208px"/>
<col width="208px"/>
<col width="208px"/>
<col width="208px"/>
</colgroup>
<tbody>
<tr>		
	<th class="menu1" colspan="6" align="left">Site: <%=site%> NR Id: <%=nrId%></th>
</tr>
</tbody>
</table>
</div>
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;">
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="208px"/>
<col width="208px"/>
<col width="208px"/>
<col width="208px"/>
<col width="208px"/>
<col width="208px"/>
</colgroup>
<tbody>
<tr>		
	<td class="grid1t">Access Status:</td>		
	<td class="grid1" colspan="5"><%=accessStatus%></td>
</tr>
<tr>		
	<td class="grid2t">P1 Site:</td>		
	<td class="grid2"><%=p1SiteInd%></td>		
	<td class="grid2t">RAMS:</td>		
	<td class="grid2"><%=ramsInd%></td>		
	<td class="grid2t">Health and Safety:</td>		
	<td class="grid2"><%=healthSafetyInd%></td>
</tr>
<tr>			
	<td class="grid1t">Access Confirmed:</td>		
	<td class="grid1"><%=accessConfirmedInd%></td>			
	<td class="grid1t">OBASS:</td>		
	<td class="grid1"><%=obassInd%></td>			
	<td class="grid1t">Escort:</td>		
	<td class="grid1"><%=escortInd%></td>	
</tr>
<tr>			
	<td class="grid2t">TEF Outage Required:</td>		
	<td class="grid2"><%=tefOutageRequired%></td>			
	<td class="grid2t">VF Arrange Access:</td>		
	<td class="grid2"><%=vfArrangeAccess%></td>			
	<td class="grid2t">Two Man Site:</td>		
	<td class="grid2"><%=twoManSite%></td>	
</tr>
<tr>			
	<td class="grid1t">OOH Weekend:</td>		
	<td class="grid1"><%=oohWeekendInd%></td>			
	<td class="grid1t">VF CRQ:</td>		
	<td class="grid1"><%=crINReference%></td>			
	<td class="grid1t">TEF CRQ:</td>		
	<td class="grid1"><%=tefOutageNos%></td>	
</tr>
<tr>			
	<td class="grid2t">Outage Period:</td>		
	<td class="grid2"><%=outagePeriod%></td>			
	<td class="grid2t">Consumeable Cost:</td>		
	<td class="grid2"><%=consumableCost%></td>			
	<td class="grid2t">Access Cost:</td>		
	<td class="grid2"><%=accessCost%></td>	
</tr>
<tr>			
	<td class="grid1t">Site Name:</td>		
	<td class="grid1" colspan="3"><%=siteName%></td>			
	<td class="grid1t">Permit Type:</td>		
	<td class="grid1"><%=permitType%></td>
</tr>
<tr>			
	<td class="grid2t" valign="top">Site Access Information:</td>		
	<td class="grid2" rowspan="10" colspan="5" valign="top">
		<textarea readonly maxlength="2000" style="resize:vertical;width:98%;font-family: arial, verdana, helvetica, sans-serif;" 
			id="siteAccessInfomation" name="siteAccessInfomation"><%=siteAcessInformation%></textarea></td>
</tr>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px"></div>
<div id="tm">
<div style="float:left;width:2px" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:none" class="menu2">Action:</div>
<div style="float:right;width:2px" class="menu2">&nbsp;</div>
<div id="closeD" onClick="tbClick('closeD')" onMouseOut="invertClass('closeD')" onMouseOver="invertClass('closeD')" style="float:right;display:inline" class="menu2Item">Return</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px"></div>
</div>
</form>
</body>
</html>