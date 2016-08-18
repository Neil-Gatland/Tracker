<%@ include file="header.jsp" %>
<!--input type="hidden" id="fromScreen" value="passwordChange.jsp"/-->
<input type="hidden" name="buttonPressed" id="buttonPressed" value=""/>
<div style="float:left;width:50%;text-align:right;margin:10px">Old Password:</div><div style="float:left;margin:10px"><input class="text" type="password" name="oldPassword"></input></div>
<div style="clear:both;height:0px">&nbsp;</div>
<div style="float:left;width:50%;text-align:right;margin:10px">New Password:</div><div style="float:left;margin:10px"><input class="text" type="password" name="newPassword"></input></div>
<div style="clear:both;height:0px">&nbsp;</div>
<div style="float:left;width:50%;text-align:right;margin:10px">Confirm New Password:</div><div style="float:left;margin:10px"><input class="text" type="password" name="confPassword"></input></div>
<div style="clear:both;height:0px">&nbsp;</div>
<div style="float:left;width:50%;text-align:right;margin:10px"><input class="button" onClick="btnClick('change')" value="Change Password" /></div>
<div style="float:left;text-align:left;margin:10px"><input class="button" onClick="btnClick('cancel')" value="Cancel" /></div>
</form>
<script type="text/javascript">
<!--
	function btnClick(operation) {
		document.getElementById("buttonPressed").value = operation;
		document.getElementById("f1").action = "changePassword";
		document.getElementById("f1").submit();
	}
//-->
</script>
</body>
</html>