package com.devoteam.tracker.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.devoteam.tracker.model.CompletionReportDetail;

public class CompletionReport {

	private static String emailHeader = 
			"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"+
			"<meta name=\"Generator\" content=\"Microsoft Word 12 (filtered medium)\"><style><!--"+
			"@font-face {font-family:\"Cambria Math\"; panose-1:2 4 5 3 5 4 6 3 2 4;} "+
			"@font-face {font-family:Calibri; panose-1:2 15 5 2 2 2 4 3 2 4;} "+
			"@font-face {font-family:Tahoma; panose-1:2 11 6 4 3 5 4 4 2 4;} "+
			"@font-face {font-family:Verdana; panose-1:2 11 6 4 3 5 4 4 2 4;} "+
			"p.MsoSmall, li.MsoSmall, div.MsoSmall "+
			"{margin:0cm; margin-bottom:.0001pt; font-size:5.0pt;font-family:\"Verdana\",\"sans-serif\";} "+
			"p.MsoNormal, li.MsoNormal, div.MsoNormal "+
			"{margin:0cm; margin-bottom:.0001pt; font-size:11.0pt;font-family:\"Verdana\",\"sans-serif\";} "+
			"p.MsoMedium, li.MsoMedium, div.MsoMedium "+
			"{margin:0cm; margin-bottom:.0001pt; font-size:15.0pt;font-family:\"Verdana\",\"sans-serif\";} "+
			"p.MsoLarge, li.MsoLarge, div.MsoLarge "+
			"{margin:0cm; margin-bottom:.0001pt; font-size:20.0pt;font-family:\"Verdana\",\"sans-serif\";} "+
			"{mso-style-type:export-only;} @page WordSection1 "+
			"{size:612.0pt 792.0pt; margin:72.0pt 72.0pt 72.0pt 72.0pt;} "+
			"div.WordSection1 {page:WordSection1;} "+
			"--></style></head><body lang=\"EN-GB\" link=\"blue\" vlink=\"purple\">";
	private static String mainTableHeader =
			"<table class=\"MsoTableGrid\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\" "+
			"style=\"border-collapse:collapse;border:none\">";
	private static String serviceCentreRed =
			"<table class=\"MsoNormalTable\" border=\"0\" cellpadding=\"0\">"+
			"<tr><td>"+
			"<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\">"+
			"<a href=\"http://www.devoteam.com/\">"+
			"<span style=\"mso-ignore:vglayout\">"+
			"<img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/service_centre_red.png\" "+
			"alt=\"Devoteam Service Centre\">"+
			"</span><br style=\"mso-ignore:vglayout\" clear=\"ALL\"></a></p></td></tr></table>";
	private static String serviceCentreBlue =
			"<table class=\"MsoNormalTable\" border=\"0\" cellpadding=\"0\">"+
			"<tr><td>"+
			"<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\">"+
			"<a href=\"http://www.devoteam.com/\">"+
			"<span style=\"mso-ignore:vglayout\">"+
			"<img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/service_centre_blue.png\" "+
			"alt=\"Devoteam Service Centre\">"+
			"</span><br style=\"mso-ignore:vglayout\" clear=\"ALL\"></a></p></td></tr></table>";
	private static String serviceCentreGreen =
			"<table class=\"MsoNormalTable\" border=\"0\" cellpadding=\"0\">"+
			"<tr><td>"+
			"<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\">"+
			"<a href=\"http://www.devoteam.com/\">"+
			"<span style=\"mso-ignore:vglayout\">"+
			"<img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/service_centre_green.png\" "+
			"alt=\"Devoteam Service Centre\">"+
			"</span><br style=\"mso-ignore:vglayout\" clear=\"ALL\"></a></p></td></tr></table>";
	private static String serviceCentreYellow =
			"<table class=\"MsoNormalTable\" border=\"0\" cellpadding=\"0\">"+
			"<tr><td>"+
			"<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\">"+
			"<a href=\"http://www.devoteam.com/\">"+
			"<span style=\"mso-ignore:vglayout\">"+
			"<img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/service_centre_yellow.png\" "+
			"alt=\"Devoteam Service Centre\">"+
			"</span><br style=\"mso-ignore:vglayout\" clear=\"ALL\"></a></p></td></tr></table>";
	private static String title =
			"<table class=\"MsoNormalTable\" border=\"0\" cellpadding=\"0\">"+
			"<tr><td>"+
			"<p class=\"MsoNormal\" align=\"center\" style=\"text-align:center\">"+
			"<a href=\"http://www.devoteam.com/\">"+
			"<span style=\"mso-ignore:vglayout\">"+
			"<img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/automated_completion_report.png\" "+
			"alt=\"Devoteam Service Centre\">"+
			"</span><br style=\"mso-ignore:vglayout\" clear=\"ALL\"></a></p></td></tr></table>";
	private static String blankLine =
			"<table class=\"MsoTableGrid\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" "+
			"style=\"border-collapse:collapse;border:none;\"><tr>"+
			"<td><p class=\"MsoNormal\">&nbsp;</p></td>"+
			"</tr></table>";
	private static String techTableHeader =
			"<table class=\"MsoTableGrid\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" "+
			"style=\"border-collapse:collapse;border:none\">";
	private static String tableEnd =
			"</table>";
	private static String emailEnd =
			"</body></html>";
	private static String tableRowStart = "<tr>";
	private static String tableRowEnd = "</tr>";
	private static String tableCellTitleStart = 
			"<td width=\"180px\" height=\"30px\" valign=\"center\" "+
			"style=\"width:180pt;border:solid window text 1.0pt;"+
			"border-color:#53565A;background:#F8485E;padding:0cm 5.4pt 0cm 5.4pt\">"+
			"<p class=\"MsoNormal\"><b><span style=\"color:white\">";
	private static String tableCellTitleEnd = 
			"</span></b></p></td>";
	private static String tableCellDataStart =
			"<td width=\"150px\" valign=\"center\" style=\"width:150pt;border:solid windowtext 1.0pt;"+
			"border-color:#53565A;padding:0cm 5.4pt 0cm 5.4pt\">"+
			"<p class=\"MsoNormal\" align=\"left\" style=\"text-align:left\"><span style=\"color:black\">"; 
	private static String tableCellDataStartGreen =
			"<td width=\"150px\" valign=\"center\" style=\"width:150pt;border:solid windowtext 1.0pt;"+
			"border-color:#53565A;padding:0cm 5.4pt 0cm 5.4pt\">"+
			"<p class=\"MsoNormal\" align=\"left\" style=\"text-align:left\"><span style=\"color:#008000;\"><b>";
	private static String tableCellDataStartAmber =
			"<td width=\"150px\" valign=\"center\" style=\"width:150pt;border:solid windowtext 1.0pt;"+
			"border-color:#53565A;padding:0cm 5.4pt 0cm 5.4pt\">"+
			"<p class=\"MsoNormal\" align=\"left\" style=\"text-align:left\"><span style=\"color:#F56800;\"><b>";
	private static String tableCellDataStartRed =
			"<td width=\"150px\" valign=\"center\" style=\"width:150pt;border:solid windowtext 1.0pt;"+
			"border-color:#53565A;padding:0cm 5.4pt 0cm 5.4pt\">"+
			"<p class=\"MsoNormal\" align=\"left\" style=\"text-align:left\"><span style=\"color:#F8485E;\"><b>"; 
	private static String tableCellTechDataStart =
			"<td valign=\"center\" style=\"border:none windowtext 1.0pt;"+
			"border-left:none;padding:none\">"+
			"<p class=\"MsoNormal\" align=\"left\" style=\"text-align:left\"><span style=\"color:black;\">";
	private static String tableCellDataEnd =
			"</span></p></td>";
	private static String subjectPrefix = "Site ";
	private static String subjectCompletedSuffix = " - Closure Report";
	private static String subjectAbortedSuffix = " - Abort Report";
	private static String subjectPartialSuffix = " - Partial Report";
	private static String endMessagePrefix =
			"<table class=\"MsoTableGrid\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" "+
			"style=\"border-collapse:collapse;border:none\">"+
			"<tr><td width=\"350\" style=\"width:350pt;\"><p class=\"MsoNormal\">"+
			"<span style=\"color:black\">&nbsp;</span></p></td></tr>"+
			"<tr><td align=\"left\" width=\"330\" style=\"width:330pt;\"><p class=\"MsoNormal\">"+
			"<span style=\"color:black\"><i>";
	private static String endMessageSuffix =
			"</i></span></p></td></tr></table>";
	private static String spacePrefix =
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	private String currentCompletionStatus;
	private String linksLine =
			"<table class=\"MsoTableGrid\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" "+
			"style=\"border-collapse:collapse;border:none;\"><tr>"+
			"<td width=\"285px\" align=\"right\" style=\"width:285pt;\">"+
			"<p class=\"MsoNormal\"><a href=\"http://www.devoteam.com\"><span>"+
			"www.devoteam.com</span></a></p></td>"+
			"<td width=\"10px\" style=\"width:10pt;\"><p class=\"MsoNormal\">&nbsp;</p></td>"+
			"<td width=\"20px\" align=\"center\" style=\"width:20pt;\">"+
			"<p class=\"MsoNormal\"><a href=\"https://www.linkedin.com/company/devoteam\"><span>"+
			"<img border=\"0\" src=\"http://www.devoteam.com/devoteamimg/signature_linkedin.png\" "+
			"alt=\"Devoteam on Linkedin\"></span></a></p></td>"+
			"<td width=\"20px\" align=\"center\" style=\"width:20pt;\">"+
			"<p class=\"MsoNormal\"><a href=\"https://plus.google.com/+Devoteam-group\"><span>"+
			"<img border=\"0\" src=\"http://www.devoteam.com/devoteamimg/signature_google.png\" "+
			"alt=\"Devoteam on Linkedin\"></span></a></p></td>"+
			"<td width=\"20px\" align=\"center\" style=\"width:20pt;\">"+
			"<p class=\"MsoNormal\"><a href=\"https://twitter.com/devoteam\"><span>"+
			"<img border=\"0\" src=\"http://www.devoteam.com/devoteamimg/signature_twitter.png\" "+
			"alt=\"Devoteam on Linkedin\"></span></a></p></td>"+
			"<td width=\"18px\" style=\"width:18pt;\"><p class=\"MsoNormal\">&nbsp;</p></td></tr>"+
			"<td colspan=\"6\" align=\"right\">"+
			"<p class=\"MsoNormal\"><a href=\"http://www.devoteam.com\"><span>"+
			"<img border=\"0\" src=\"http://www.devoteam.com/devoteamimg/innovative_technology_consulting.png\" "+
			"alt=\"Devoteam on Linkedin\"></span></a></p></td></tr></table>";
	private String circlesLine = 
			"<table class=\"MsoTableGrid\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" "+
			"style=\"border-collapse:collapse;border:none;\"><tr>"+
			"<td align=\"center\"><p class=\"MsoNormal\">"+
			"<a href=\"http://www.devoteam.com/en/offers/it-transformation\">"+
			"<span><img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/it_transformation.png\"> "+
			"</span></a></p></td>"+
			"<td width=\"22\">&nbsp;</td>"+
			"<td align=\"center\"><p class=\"MsoNormal\">"+
			"<a href=\"http://www.devoteam.com/en/offers/digital-enabler\">"+
			"<span><img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/digital_enabler.png\"> "+
			"</span></a></p></td>"+	
			"<td width=\"22\">&nbsp;</td>"+
			"<td align=\"center\"><p class=\"MsoNormal\">"+
			"<a href=\"http://www.devoteam.com/en/offers/cloud-transformation\">"+
			"<span><img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/cloud_transformation.png\"> "+
			"</span></a></p></td>"+	
			"<td width=\"22\">&nbsp;</td>"+
			"<td align=\"center\"><p class=\"MsoNormal\">"+
			"<a href=\"http://www.devoteam.com/en/offers/it-service-excellence\">"+
			"<span><img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/it_service_excellence.png\"> "+
			"</span></a></p></td>"+
			"<td width=\"22\">&nbsp;</td>"+
			"<td align=\"center\"><p class=\"MsoNormal\">"+
			"<a href=\"http://www.devoteam.com/en/offers/risk-security\">"+
			"<span><img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/risk_and_security.png\"> "+
			"</span></a></p></td>"+	
			"<td width=\"22\">&nbsp;</td>"+
			"<td align=\"center\"><p class=\"MsoNormal\">"+
			"<a href=\"http://www.devoteam.com/en/offers/network-transformation\">"+
			"<span><img border=\"0\" "+
			"src=\"https://dvt-uk-rant-001.appspot.com/images/network_transformation.png\"> "+
			"</span></a></p></td>"+	
			"</tr></table>";
				
	public String cutdownCompletionReport(
			long snrId, String completionStatus, String completionDate, Connection conn, String completingBO) {
		String result = "";
		currentCompletionStatus = completionStatus;
		String reformattedCompletionDate = 
				completionDate.substring(6,10)+"-"+
				completionDate.substring(3,5)+"-"+
				completionDate.substring(0,2);
		CompletionReportDetail cRD = null;
		CallableStatement cstmt = null;
		try {
			cstmt = conn.prepareCall("{call GetCompletionReportDetail(?,?,?)}");
	    	cstmt.setLong(1, snrId);
	    	cstmt.setString(2, completionStatus);
	    	cstmt.setString(3, reformattedCompletionDate);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				if (rs.next()) {
					cRD = new CompletionReportDetail(
							rs.getLong(1),rs.getString(2), rs.getString(3),
							rs.getDate(4),rs.getString(5),rs.getString(6),
							rs.getString(7),rs.getString(8),rs.getString(9),
							rs.getString(10),rs.getString(11),rs.getString(12),
							rs.getString(13),rs.getString(14),rs.getString(15),
							rs.getString(16),rs.getString(17),rs.getString(18),
							rs.getString(19),rs.getString(20),rs.getString(21),
							rs.getString(22),rs.getString(23),rs.getString(24),
							rs.getString(25),rs.getString(26),rs.getString(27),
							rs.getString(28),rs.getString(29),rs.getString(30),
							rs.getString(31));
				}
			}
	    } catch (Exception ex) {
	    	result = " - automated completion report failed: GetCompletionReportDetail(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
		    } catch (SQLException ex) {
		    	result = " - automated completion report failed: closing GetCompletionReportDetail(): " + ex.getMessage();
		    	ex.printStackTrace();
		    } 
	    } 
		if (cRD.getSNRId()==snrId) {
			// format report
			String subject = subjectPrefix + cRD.getSite();
			if (currentCompletionStatus.equals("Completed")) {
				subject = subject + subjectCompletedSuffix;
			} else if (currentCompletionStatus.equals("Partial")) {
				subject = subject + subjectPartialSuffix;
			} else {
				subject = subject + subjectAbortedSuffix;
			}
			String toList = cRD.getToList();
			String ccList = cRD.getCcList();
			String bccList = cRD.getBccList();
			String messageBody = emailHeader;
			Random rn = new Random();
			int colorCode = rn.nextInt(3);
			switch (colorCode) {
				case 0: messageBody = messageBody + serviceCentreRed;  
						break;
				case 1: messageBody = messageBody + serviceCentreYellow;  
						break;
				case 2: messageBody = messageBody + serviceCentreBlue;  
						break;
				case 3: messageBody = messageBody + serviceCentreGreen;  
						break;
			}			
			messageBody = messageBody+title+mainTableHeader;			
			messageBody = addReportLine(messageBody,"Site:",cRD.getSite());
			messageBody = addReportLine(messageBody,"Completion Date:",cRD.getCompletionDateString());
			messageBody = addReportLine(messageBody,"Completion Status:",cRD.getCompletionStatus());
			messageBody = addReportLine(messageBody,"Project:",cRD.getProject());
			messageBody = addReportLine(
					messageBody,
					"Technologies:",
					technologies(cRD.getVodafone2G(),cRD.getVodafone3G(),cRD.getVodafone4G(),
							cRD.getTef2G(),cRD.getTef3G(),cRD.getTef4G(),cRD.getPaknetPaging(),
							cRD.getSecGWChange(),cRD.getPower(),cRD.getSurvey(),cRD.getOther()));
			messageBody = addReportLine(messageBody,"Hardware Vendor(s):",cRD.getHardwareVendor());
			messageBody = addReportLine(messageBody,"Lock Time:",cRD.getLockTime());
			messageBody = addReportLine(messageBody,"Unlock Time:",cRD.getUnlockTime());
			messageBody = addReportLine(messageBody,"Pre-test calls completed:",cRD.getPreTestCallsDone());
			messageBody = addReportLine(messageBody,"Post-test calls completed:",cRD.getPostTestCallsDone());
			messageBody = addReportLine(messageBody,"CRQ Closure Code:",cRD.getCrqClosureCode());
			messageBody = addReportLine(messageBody,"BO Engineer:",completingBO);
			messageBody = addReportLine(messageBody,"Field Engineer 1:",cRD.getFe1List());
			messageBody = addReportLine(messageBody,"Field Engineer 2:",cRD.getFe2List());
			messageBody = addReportLine(messageBody,"Remarks:",cRD.getSiteIssues());
			messageBody = messageBody + tableEnd;
			messageBody = messageBody + endMessagePrefix + cRD.getEmailEndMessage() + endMessageSuffix;
			messageBody = messageBody + blankLine+linksLine+circlesLine;
			messageBody = messageBody + emailEnd;
			cstmt = null;
			try {;
				cstmt = conn.prepareCall("{call AddEmailCopy(?,?,?,?,?,?,?)}");
		    	cstmt.setString(1, completingBO);
		    	cstmt.setString(2, subject);
		    	cstmt.setString(3, toList);
		    	cstmt.setString(4, ccList);
		    	cstmt.setString(5, bccList);
		    	cstmt.setString(6, messageBody);
		    	cstmt.setString(7, cRD.getEmailSendAddress());
				cstmt.execute();
			    } catch (Exception ex) {
			    	result = " - automated completion report failed: AddEmailCopy(): " + ex.getMessage();
			    	ex.printStackTrace();
			    } finally {
			    	try {
			    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
				    } catch (SQLException ex) {
				    	result = " - automated completion report failed: closing after AddEmailCopy(): " + ex.getMessage();
				    	ex.printStackTrace();
				    } finally {
				    	Email em = new Email();
				    	result = em.send(messageBody, cRD.getEmailSendAddress(), toList, ccList, bccList, subject);
				    }
			    } 
		} 		
		return result;
	}
	
	private String addReportLine (String currentBody, String title, String content) {
		String newString = currentBody;
		newString = newString + tableRowStart;
		newString = newString + tableCellTitleStart;
		newString = newString + title;
		newString = newString + tableCellTitleEnd;
		if ((title.equals("Site:"))||(title.equals("Completion Date:"))||(title.equals("Completion Status:"))) {
			if (currentCompletionStatus.equals("Completed")) {
				newString = newString + tableCellDataStartGreen;
			} else if (currentCompletionStatus.equals("Partial")) {
				newString = newString + tableCellDataStartAmber;
			} else if (currentCompletionStatus.equals("Aborted")) {
				newString = newString + tableCellDataStartRed;
			}
		} else {
			newString = newString + tableCellDataStart;
		}
		newString = newString + content;
		newString = newString + tableCellDataEnd;
		newString = newString + tableRowEnd;
		return newString;
	}
	
	private String technologies (
			String vf2g, String vf3g, String vf4g, 
			String tef2g, String tef3g, String tef4g,
			String paknetPaging, String secGWChange,
			String power, String survey, String other) {	
		StringBuilder technologies = new StringBuilder();
		technologies.append(techTableHeader);
		technologies.append(technology("Vodafone 2G:",vf2g));
		technologies.append(technology("Vodafone 3G:",vf3g));
		technologies.append(technology("Vodafone 4G:",vf4g));
		technologies.append(technology("TEF 2G:",tef2g));
		technologies.append(technology("TEF 3G:",tef3g));
		technologies.append(technology("TEF 4G:",tef4g));
		technologies.append(technology("Paknet and Paging:",paknetPaging));
		technologies.append(technology("SecGW Change:",secGWChange));
		technologies.append(technology("Power:",power));
		technologies.append(technology("Survey:",survey));
		technologies.append(technology("Other:",other));
		technologies.append(tableEnd);
		return technologies.toString();
	}
	
	private String technology ( String techName, String techValue ) {
		StringBuilder techString = new StringBuilder();
		if (techValue.startsWith("N/A")) {
			techString.append("");
		} else {
			techString.append(tableRowStart);
			techString.append(tableCellTechDataStart);
			techString.append(techName);
			techString.append(tableCellDataEnd);
			techString.append(tableCellTechDataStart);
			techString.append(spacePrefix);
			if (techValue.startsWith("Y")) {
				techString.append("Yes");				
			} else {
				techString.append("No");
			}
			techString.append(tableCellDataEnd);
			techString.append(tableRowEnd);
		}
		return techString.toString();
	}

}
