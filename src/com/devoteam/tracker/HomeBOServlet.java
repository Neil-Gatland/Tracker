package com.devoteam.tracker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.devoteam.tracker.util.Email;


import java.io.InputStream;
import java.io.OutputStream;

public class HomeBOServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5612653276956495612L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/homeBO.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			session.setAttribute("prevScreen", "homeBO");
			boolean direct = req.getAttribute("buttonPressed") != null;
			String buttonPressed = direct?(String)req.getAttribute("buttonPressed"):req.getParameter("buttonPressed");
			String snrId = direct?(String)req.getAttribute("snrId"):req.getParameter("snrId");
	    	req.setAttribute("snrId", snrId); 
	    	String snrStatus = direct?(String)req.getAttribute("snrStatus"):req.getParameter("snrStatus");
	    	req.setAttribute("snrStatus", snrStatus); 
	    	if (buttonPressed.equals("chgBOEngineerSubmit")) {
    			String selectChangeBOList = direct?(String)req.getAttribute("selectChangeBOList"):req.getParameter("selectChangeBOList");
	    		req.setAttribute("filterBOEngineer", selectChangeBOList);
	    	} else if (buttonPressed.equals("emailTestSubmit")) {
	    		String sender = direct?(String)req.getAttribute("sender"):req.getParameter("sender");
	    		String recipient = direct?(String)req.getAttribute("recipient"):req.getParameter("recipient");
	    		String subject = direct?(String)req.getAttribute("subject"):req.getParameter("subject");
	    		String messageBody = direct?(String)req.getAttribute("messageBody"):req.getParameter("messageBody");
	    		String includeWordDoc = direct?(String)req.getAttribute("includeWordDoc"):req.getParameter("includeWordDoc");
	    		Email em = new Email();
	    		String emailResult = "";
	    		// Attach sample word document if requested	    		
	    		if (includeWordDoc.startsWith("Y")) {
	    			// this does not work live!
	    			emailResult = em.send(messageBody, sender, recipient, subject, "docs/Dummy sample attachment.docx");
	    		} else {	    		
	    			emailResult = em.send(messageBody, sender, recipient, subject);
	    		}
	    		session.setAttribute("userMessage", emailResult);
	    	}
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
	}

}
