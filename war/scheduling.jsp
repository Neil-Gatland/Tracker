<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.devoteam.tracker.model.SNRScheduleSpreadsheet"%>
<%@ include file="header.jsp" %>
<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	boolean potToUpload = session.getAttribute(ServletConstants.POT_SPREADSHEET_NAME_IN_SESSION)!=null;
	boolean problemsFound =  session.getAttribute(ServletConstants.PROBLEM_ARRAY_NAME_IN_SESSION)!=null;
	ArrayList<SNRScheduleSpreadsheet> scheduledSNRs = (ArrayList<SNRScheduleSpreadsheet>)session.getAttribute(ServletConstants.SCHEDULED_SNRS_IN_SESSION);
	ArrayList<SNRScheduleSpreadsheet> invalidSNRs = (ArrayList<SNRScheduleSpreadsheet>)session.getAttribute(ServletConstants.INVALID_SNRS_IN_SESSION);
	String snrFileName = (String)session.getAttribute(ServletConstants.SCHEDULE_SNR_FILE_NAME_IN_SESSION);
	boolean warnings = session.getAttribute(ServletConstants.SCHEDULED_SNR_WARNINGS_IN_SESSION) != null;
	boolean invalidRows = invalidSNRs != null && invalidSNRs.size() > 0;
	boolean validRows = scheduledSNRs != null && scheduledSNRs.size() > 0;
	boolean expand = warnings || invalidRows;
	boolean selectFile = scheduledSNRs == null && invalidSNRs == null;
%>
<input type="hidden" name="fromScreen" id="fromScreen" value="scheduling.jsp"/>
<input type="hidden" name="screenTitle" id="screenTitle" value="<%=ServletConstants.SCHEDULING%>"/>
<script language="javascript">
<!-- 
function thisScreenLoad() {
<%if (potToUpload || problemsFound) {%>
	var anchor = document.getElementById("anchor");
	var position = getPosition(anchor);
	var uPC = document.getElementById("uploadPot");
	uPC.style.display = "inline";
	uPC.style.left = position.x + "px";
	uPC.style.top = position.y + "px";
<%} else if (validRows || invalidRows) {%>
	var anchor = document.getElementById("anchor");
	var position = getPosition(anchor);
	var uSC = document.getElementById("uploadSchedule");
	uSC.style.display = "inline";
	uSC.style.left = position.x + "px";
	uSC.style.top = position.y + "px";
<%}%>
}

function menuClickSpec(element) {
	var anchor = document.getElementById("anchor");
	var position = getPosition(anchor);
	var thisElement = document.getElementById(element);
	thisElement.style.display = "inline";
	thisElement.style.left = position.x + "px";
	thisElement.style.top = position.y + "px";
}

-->
</script>
<div id="anchor"></div>
<!-- Upload Pot -->
<%@ include file="uploadPot.txt" %>
<!-- Upload Schedule -->
<%@ include file="uploadSchedule.txt" %>
</form>
</body>
</html>