package com.devoteam.tracker;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.UtilBean;

public class LiveDashboardServlet extends HttpServlet {

	private static final long serialVersionUID = -8608740790298618637L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/liveDashboard.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid user id and password");
		} else {
			session.setAttribute("prevScreen", "liveDashboard");
		}
		String selectedAction = req.getParameter("selectedAction");
		if ((selectedAction.equals("rewind")) || (selectedAction.equals("go"))) {
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
			if (selectedAction.equals("rewind")) {
				String updateResult = uB.rewindProject(thisU.getFullname());
				if (updateResult.equals("Y")) {
					updateResult = uB.rewindProject(thisU.getFullname());
				}
				if (updateResult.equals("Y")) {
					session.setAttribute("userMessage", "");
				} else {
					session.setAttribute("userMessage", "Failed going back to previous project");
				}
			} else {
				String selectedProject = req.getParameter("selectedProject");
				String updateResult = uB.gotoProject(selectedProject,thisU.getFullname());
				if (updateResult.equals("Y")) {
					session.setAttribute("userMessage", "");
				} else {
					session.setAttribute("userMessage", "Failed going back to selected project");
				}
			}			    		
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
	}
	
	
}
