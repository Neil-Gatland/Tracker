package com.devoteam.tracker.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.devoteam.tracker.util.Sendgrid;
import com.google.appengine.labs.repackaged.org.json.JSONException;

public class Email {
	
	public String sendResetEmail
			( 	String resetEmail,
				String resetPassword,
				String accountName,
				String accountPassword,
				String sender,
				String endMessage) {
		String result = "";
		
		// set SendGrid credentials
		Sendgrid mail = new Sendgrid(accountName,accountPassword);

		// set up email		
		mail.setTo(resetEmail);
		mail.setFrom(sender);
		mail.setSubject("SMART - Password reset");
		String html = 
			"Your password has been reset<br><br>New password: <b>"+resetPassword+"</b><br><br>"+
			"Use this password to log on, you will then be asked to change it immediately<br><br>"+
			"<i>"+endMessage+"</i>";
		mail.setHtml(html);
		String text = 
				"Your password has been reset<to new temporary password: "+resetPassword+". "+
				"Use this password to log on, you will then be asked to change it immediately. "+
				endMessage;
		mail.setText(text);		
		
		// send email
		try {			
			mail.send();
			result = "success";
		}
		catch (Exception e)
		{
			result = 
				"Password reset error (please contact support and note the following message): "+
			mail.getServerResponse();
			e.printStackTrace();
		}
		return result;
	}
		
	public String send 
			(	String messageBody, 
				String sender, 
				String recipientToList, 
				String recipientCCList, 
				String recipientBCCList, 
				String subject) {
		
		Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	    String result = " - automated email produced";
	    try {
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(sender));
	        // process recipient or recipients to list delimited by pipe separators
	        String[] rTo = recipients(recipientToList);
	        for ( int i=0; i < rTo.length; i++ ) { 
	        	if (rTo[i]!="") {
	        		msg.addRecipient(Message.RecipientType.TO,
		                    new InternetAddress(rTo[i]));
	        	}	        	
	        }
	        // process recipient or recipients cc list delimited by pipe separators
	        String[] rCC = recipients(recipientCCList);
	        for ( int i=0; i < rCC.length; i++ ) { 
	        	if (rCC[i]!="") {
	        		msg.addRecipient(Message.RecipientType.CC,
		                    new InternetAddress(rCC[i]));
	        	}	        	
	        }
	        // process recipient or recipients bcc list delimited by pipe separators
	        String[] rBCC = recipients(recipientBCCList);
	        for ( int i=0; i < rBCC.length; i++ ) { 
	        	if (rBCC[i]!="") {
	        		msg.addRecipient(Message.RecipientType.BCC,
		                    new InternetAddress(rBCC[i]));
	        	}	        	
	        }
	        msg.setSubject(subject);
	        Multipart mp = new MimeMultipart();
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent(messageBody, "text/html");
	        mp.addBodyPart(htmlPart);
	        msg.setContent(mp);
	        Transport.send(msg);
	    } catch (AddressException e) {
	        result = " - automated email address exception: "+e.getMessage();
	    } catch (MessagingException e) {
	    	result = " - automated email messaging exception: "+e.getMessage();
	    }	    
	    return result;
	}
	
	public String send 
			(	String messageBody, 
				String sender, 
				String recipientList, 
				String subject) {
		
		Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	    String result = "Email sent successfully";
	    try {
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(sender));
	        // process recipient or recipients delimited by pipe separators
	        String[] r = recipients(recipientList);
	        for ( int i=0; i < r.length; i++ ) { 
	        	if (r[i]!="") {
	        		msg.addRecipient(Message.RecipientType.TO,
		                    new InternetAddress(r[i]));
	        	}	        	
	        }
	        msg.setSubject(subject);
	        Multipart mp = new MimeMultipart();
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent(messageBody, "text/html");
	        mp.addBodyPart(htmlPart);
	        msg.setContent(mp);
	        Transport.send(msg);
	    } catch (AddressException e) {
	        result = "ERROR: Address exception: "+e.getMessage();
	    } catch (MessagingException e) {
	    	result = "ERROR: Messaging exception: "+e.getMessage();
	    }	    
	    return result;
	}
	
	public String send 
			(	String messageBody, 
				String sender, 
				String recipientList, 
				String subject, 
				String attachmentName ) {		
		Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	    String result = "Email sent successfully";
	    try {
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(sender));
	        // process recipient or recipients delimited by pipe separators
	        String[] r = recipients(recipientList);
	        for ( int i=0; i < r.length; i++ ) { 
	        	if (r[i]!="") {
	        		msg.addRecipient(Message.RecipientType.TO,
		                    new InternetAddress(r[i]));
	        	}	        	
	        }
	        msg.setSubject(subject);
	        Multipart mp = new MimeMultipart();
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent(messageBody, "text/html");
	        mp.addBodyPart(htmlPart,0);
	        MimeBodyPart attachmentPart = new MimeBodyPart();
	        DataSource source = new FileDataSource(attachmentName);
	        attachmentPart.setDataHandler(new DataHandler(source));
	        attachmentPart.setFileName(attachmentName);
	        mp.addBodyPart(attachmentPart,1);
	        msg.setContent(mp);
	        Transport.send(msg);
	    } catch (AddressException e) {
	        result = "ERROR: Address exception: "+e.getMessage();
	    } catch (MessagingException e) {
	    	result = "ERROR: Messaging exception: "+e.getMessage();
	    }	    
	    return result;
	}
	
	private String[] recipients (String rlist) {
		String[] list = { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
		int pos = 0;
		String test = "";
		for (int i=0; i < rlist.length(); i++) {
			String currentChar = rlist.substring(i,i+1);
			if (currentChar.equals("|")) {
				list[pos]= test;
				test = "";
				pos++;
			} else {
				test = test + currentChar;
			}			
		}
		if (test!="") {
			list[pos]= test;
		}
		return list;
	}
	
}
