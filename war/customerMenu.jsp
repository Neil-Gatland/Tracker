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
		<td class="lTitle7" align="left" valign="bottom" colspan="3">
			<a href="http://www.devoteam.co.uk/" target="_blank">
			Powered by the Application Development Team @ www.devoteam.co.uk
			</a><td>
		</tr><tr>
		<td align="center">
			<a href="http://www.devoteam.co.uk/" target="_blank">
			<img src="images/<%=uB.imageChoice1()%>" height="50%" width="100%"></a>
		</td>
		<td align="center">
			<a href="http://www.devoteam.co.uk/" target="_blank">
			<img src="images/<%=uB.imageChoice2()%>" height="50%" width="100%"></a>
		</td>
		<td align="center">
			<a href="http://www.devoteam.co.uk/">
			<img src="images/<%=uB.imageChoice3()%>" height="50%" width="100%"></a>
		</td>
		</tr><tr>
		<td class="lTitle1" align="center" colspan="3">&nbsp;</td>
		</tr><tr>
		<td class="lTitle1" align="center" colspan="3">SMART - Service Management Automated Reference Tool</td>
		</tr><tr>
		<td class="lTitle2" align="center"  colspan="3">Please select from the Client options below</td>
		</tr><tr>
		<td class="lTitle2" align="center"  colspan="3">&nbsp;</td>
		</tr><tr>
		<td class="lTitle1" align="center" colspan="3">
			<table>
				<tr>
					<td class="lTitle2" align="center" title="Press for live dashboard"
						onClick="menuClick('<%=ServletConstants.LIVE_DASHBOARD%>')">
						<img src="images/client_access.png" height="70%" width="70%">
					</td>
					<!-- <td class="lTitle2" align="center">
						&nbsp;
					</td>
					<td class="lTitle2" align="center">
						&nbsp;
					</td>
					<td class="lTitle2" align="center">
						&nbsp;
					</td>
					<td class="lTitle2" align="center">
						&nbsp;
					</td>
					<td class="lTitle2" align="center">
						&nbsp;
					</td>  -->
				</tr>
				<tr>					
					<td class="lTitle2" align="center" title="Press for live dashboard"
						onClick="menuClick('<%=ServletConstants.LIVE_DASHBOARD%>')">
						Live Dashboard
					</td>
					<!-- <td class="lTitle2" align="center">
						&nbsp;
					</td>
					<td class="lTitle2" align="center">
						&nbsp;
					</td>
					<td class="lTitle2" align="center">
						&nbsp;
					</td>
					<td class="lTitle2" align="center">
						&nbsp;
					</td>
					<td class="lTitle2" align="center">
						&nbsp;
					</td>  -->
				</tr>
			</table>				
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