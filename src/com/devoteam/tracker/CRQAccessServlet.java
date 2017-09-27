package com.devoteam.tracker;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CRQAccessServlet extends HttpServlet {

	private static final long serialVersionUID = 5350309995657448774L;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/crqAccess.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			boolean direct = req.getAttribute("buttonPressed") != null;
			String showRaiseCRQ = req.getParameter("showRaiseCRQ");
			String showCloseCRQ = req.getParameter("showCloseCRQ");
			String showOtherCRQ = req.getParameter("showOtherCRQ");
			String snrId = req.getParameter("snrId");
			session.setAttribute("prevScreen", "crqAccess");
			String buttonPressed = direct?(String)req.getAttribute("buttonPressed"):req.getParameter("buttonPressed");
			req.setAttribute("showRaiseCRQ", showRaiseCRQ);
			req.setAttribute("showCloseCRQ", showCloseCRQ);
			req.setAttribute("showOtherCRQ", showOtherCRQ);
			req.setAttribute("snrId", snrId);
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	  	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	  	dispatcher.forward(req,resp);		
	}
}
