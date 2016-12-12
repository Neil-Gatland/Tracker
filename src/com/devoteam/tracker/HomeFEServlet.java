package com.devoteam.tracker;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HomeFEServlet extends HttpServlet {

	private static final long serialVersionUID = 2026529509232801092L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/homeFE.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			session.setAttribute("prevScreen", "homeFE");
			Random r = new Random();
			String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	      	dispatcher.forward(req,resp);
		}
	}
}
