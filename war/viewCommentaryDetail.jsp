<%@ include file="header.jsp" %>
<%
long snrId = request.getAttribute("snrId")==null?-1:Long.parseLong((String)request.getAttribute("snrId"));
String site = request.getAttribute("site")==null?"-1":(String)request.getAttribute("site");
String nrId = request.getAttribute("nrId")==null?"-1":(String)request.getAttribute("nrId");		
String snrStatus = request.getAttribute("snrStatus")==null?"-1":(String)request.getAttribute("snrStatus");
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="viewCommentaryDetail.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.VIEW_COMMENTARY_DETAIL%>"/>
<input type="hidden" name="snrId" id="snrId" value="<%=snrId%>"/>
<input type="hidden" name="site" id="site" value="<%=site%>"/>
<input type="hidden" name="nrId" id="nrId" value="<%=nrId%>"/>
<input type="hidden" name="snrStatus" id="snrStatus" value="<%=snrStatus%>"/>
<script language="javascript">
<!--

function thisScreenLoad() {
} 

function tbClick(btn) {
		if (btn == "closeD")	{
			document.getElementById("toScreen").value = "<%=ServletConstants.BO%>";
			document.getElementById("f1").action = "navigation";
			document.getElementById("f1").submit();
	} 
}

-->
</script>
<div style="width:1270px;margin:0 auto; margin-top:10px; height: 460px;">
<div style="margin: 0; padding: 0; border-collapse: collapse;width: 1250px; 
overflow: visible; border: 1px solid black;" >
<table style="width: 1250px;height: 20px;  table-layout: fixed;">
<colgroup>
<col width="100px"/>
<col width="100px"/>
<col width="175px"/>
<col width="500px"/>
<col width="125px"/>
<col width="250px"/>
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
<table style="width: 1250px; height: 20px;">
<colgroup>
<col width="100px"/>
<col width="100px"/>
<col width="175px"/>
<col width="500px"/>
<col width="125px"/>
<col width="250px"/>
</colgroup>
<tbody>
<tr>
		<th class="altBar">Date</th>
		<th class="altBar">Type</th>
		<th class="altBar">Commentary Text</th>
		<th class="altBar">&nbsp;</th>
		<th class="altBar">&nbsp;</th>
		<th  class="altBar">Last Updated By</th>
</tr>
</tbody>
</table>
<div style="margin: 0; padding: 0; border-collapse: collapse; width: 1250px; max-height: 420px;
overflow: visible; border: 1px solid black; overflow-y: auto; overflow-x: hidden;">
<table style="width: 1250px;">
<colgroup>
<col width="100px"/>
<col width="100px"/>
<col width="800px"/>
<col width="250px"/>
</colgroup>
<tbody>
<%=uB.getSNRCommentaryListHTML(snrId)%>
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