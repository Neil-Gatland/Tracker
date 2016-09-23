package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.SNRAccessDetail;
import com.devoteam.tracker.model.SNRBOInformation;
import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.UtilBean;

public class ViewAccessDetailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6416765985989991314L;

	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/viewAccessDetail.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			String snrId = req.getParameter("snrId");
	    	req.setAttribute("snrId", snrId);
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
	}
	
}
