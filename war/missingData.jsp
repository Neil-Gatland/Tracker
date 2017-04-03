<%@ include file="headerLD.jsp" %>
<input type="hidden" name="snrId" id="snrId" value=""/>
<input type="hidden" name="column" id="column" value=""/>
<input type="hidden" name="newValue" id="newValue" value=""/>
<input type="hidden" name="thirdPartyId" id="thirdPartyId" value=""/>
<script language="javascript">

function tbClick(btn) {
	if (btn == "return") {
		document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
		document.getElementById("f1").action = "navigation";
		document.getElementById("f1").submit();
	}
}

function populateSiteColumn(snrId,row,column) {
	var newValue = "";
	if (column=="scheduledDate") {
		newValue = document.getElementById("newSDate"+row).value;
	} else if (column=="upgradeType") {
		newValue = document.getElementById("newUType"+row).value;
	} else if (column=="boEngineer") {
		newValue = document.getElementById("selectAvailableBOEngineers"+row).value;
	} else if (column=="feEngineer") {
		newValue = document.getElementById("selectAvailableFEEngineers"+row).value;
	} else if (column="hardwareVendor") {
		newValue = document.getElementById("newHVendor"+row).value;
	}
	document.getElementById("snrId").value = snrId;
	document.getElementById("column").value = column;
	document.getElementById("newValue").value = newValue;
	if (column=="feEngineer") {
		document.getElementById('selectThirdParty').value = "";
		var header = document.getElementById("selectAvailableFEEngineers"+row);
		var position = getPositionNoOffset(header);
		var feD = document.getElementById("feDetails");
		feD.style.display = "inline";
		feD.style.left = position.x + "px";
		feD.style.top = ( position.y - 15 ) + "px";
		feD.style.zIndex = "20";	
	} else {		
		document.getElementById("toScreen").value = "<%=ServletConstants.SCHEDULE_VIEW%>";
		document.getElementById("f1").action = "missingData";
		document.getElementById("f1").submit();			
	}
}

</script>
<table style="table-layout:fixed;border-style:none;width:1250px;display:inline;">
<colgroup>		
	<col width="61px"/>	
	<col width="61px"/>	
	<col width="129px"/>	
	<col width="122px"/>	
	<col width="94px"/>	
	<col width="50px"/>	
	<col width="113px"/>	
	<col width="113px"/>	
	<col width="106px"/>	
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>			
	<col width="35px"/>	
	<col width="16px"/>	
</colgroup>
</tbody>
<tr>
	<td height="5px"></td>
</tr>
<tr>
	<td class="schHead">Schedule Date</td>
	<td class="schHead">Site</td>
	<td class="schHead">NR Id</td>
	<td class="schHead">Site Status</td>
	<td class="schHead">Project</td>
	<td class="schHead">Upgrade Type</td>
	<td class="schHead">BO Eng.</td>
	<td class="schHead">FE Eng.</td>
	<td class="schHead">Hardware Vendor</td>
	<td class="schHead" title="Vodafone 2G">VF 2G</td>
	<td class="schHead" title="Vodafone 3G">VF 3G</td>
	<td class="schHead" title="Vodafone 4G">VF 4G</td>
	<td class="schHead">TEF 2G</td>
	<td class="schHead">TEF 3G</td>
	<td class="schHead">TEF 4G</td>
	<td class="schHead" title="Paknet and Paging">P&P</td>
	<td class="schHead" title="SecGW Change">Sec GW</td>
	<td class="schHead">Power</td>
	<td class="schHead">Survey</td>
	<td class="schHead">Other</td>
	<td>&nbsp;</td>
</tr>
</tbody>
</table>
<div id="siteList" style="margin: 0; padding: 0; overflow-y: scroll; overflow-x: hidden; display; inline; 
max-width: 100%; height: 550px;">
<table style="table-layout:fixed;border-style:none;width:1250px;display:inline;">
<colgroup>		
	<col width="60px"/>	
	<col width="60px"/>	
	<col width="125px"/>	
	<col width="120px"/>	
	<col width="100px"/>	
	<col width="55px"/>	
	<col width="110px"/>	
	<col width="110px"/>	
	<col width="105px"/>	
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
	<col width="35px"/>		
</colgroup>
<tbody>
<%=uB.getMissingDataHTML()%>
</tbody>
</table>
</div>
<div class="menu2" style="height:2px;width:1250px;"></div>
<div id="tm" style="width:1250px;">
<div style="float:left;width:2px;" class="menu2">&nbsp;</div>
<div id="action" style="float:left;display:inline;" class="menu2">Action:</div>
<div style="float:right;width:2px;" class="menu2">&nbsp;</div>
<div id="return" onClick="tbClick('return')" onMouseOut="invertClass('return')" onMouseOver="invertClass('return')" style="float:right;display:inline" class="menu2Item">Return</div>
<div id="tmAnchor" class="menu2">&nbsp;</div>
</div>
<div class="menu2" style="height:2px;width:1250px;"></div>
<%@ include file="missingDataFE.txt" %>
</form>
</body>
</html>