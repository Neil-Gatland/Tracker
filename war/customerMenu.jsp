<%@ include file="headerEXT.jsp" %>
<input type="hidden" name="fromScreen" id="fromScreen" value="customerMenu.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.CUSTOMER_MENU%>"/>
<div>
<table style="table-layout: fixed; border-style: none; width: 100%;">
	<colgroup>
		<col width="33.34%"/>	
		<col width="33.34%"/>
		<col width="33.34%"/>	
	</colgroup>
	<tbody><tr>
		<td class="lTitle7" align="left" valign="top" >		
			Powered by the Application Development Team @
			<a href="http://www.devoteam.co.uk/" target="_blank">www.devoteam.co.uk
			</a><td>
		<td colspan="2">&nbsp;<td>
		</tr><tr>
		<td align="center" height="200px" overflow="hidden">
			<a href="http://www.devoteam.co.uk/" target="_blank">
			<img src="images/<%=uB.imageChoice1()%>" height="100%x" width="100%"></a>
		</td>
		<td align="center" height="200px" overflow="hidden">
			<a href="http://www.devoteam.co.uk/" target="_blank">
			<img src="images/<%=uB.imageChoice2()%>" height="100%" width="100%"></a>
		</td>
		<td align="center" height="200px" overflow="hidden">
			<a href="http://www.devoteam.co.uk/">
			<img src="images/<%=uB.imageChoice3()%>" height="100%x" width="100%"></a>
		</td>
		</tr><tr>
		<td class="lTitle1" align="center" colspan="3">&nbsp;</td>
		</tr><tr>
		<td align="center" colspan="3"><font color="#53565A" size="5">
		<b>SMART</b> - <b>S</b>ervice <b>M</b>anagement <b>A</b>utomated <b>R</b>eference <b>T</b>ool
		</font></td>
		</tr><tr>
		<td class="lTitle2" align="center"  colspan="3">Please select from the Client options below</td>
		</tr><tr>
		<td class="lTitle2" align="center"  colspan="3">&nbsp;</td>
		</tr><tr>
		<td class="lTitle2" align="center" colspan="3">
			<table style="table-layout: fixed; border-style: none; width: 100%;">	
			<tbody>
			<tr>
				<td align="center" title="Press for live dashboard"
					onClick="menuClick('<%=ServletConstants.LIVE_DASHBOARD%>')">
					<img src="images/client_access.png" height="80px" width="80px">
				</td>
			</tr>	
			<tr>
				<td align="center" class="lTitle2" title="Press for live dashboard"
					onClick="menuClick('<%=ServletConstants.LIVE_DASHBOARD%>')">
					Live Dashboard
				</td>
			</tr>	
			</tbody>		
			</table>
		</td>				
		</tr>
	</tbody>
	</table>
</div>
<a href="" id="aLink" name="aLink" style="display:none"></a>
<!-- includes -->
<%@ include file="externalOptions.txt" %>
</form>
</body>
</html>