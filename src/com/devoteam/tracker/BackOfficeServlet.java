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

public class BackOfficeServlet extends HttpServlet {

	private static final long serialVersionUID = -2990366991942398132L;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/backOffice.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			boolean direct = req.getAttribute("buttonPressed") != null;
			session.setAttribute("prevScreen", "backOffice");
			String buttonPressed = direct?(String)req.getAttribute("buttonPressed"):req.getParameter("buttonPressed");
			if (buttonPressed.equals("chgBOEngineerSubmit")) {
    			String selectChangeBOList = direct?(String)req.getAttribute("selectChangeBOList"):req.getParameter("selectChangeBOList");
	    		req.setAttribute("filterBOEngineer", selectChangeBOList);
	    	} else {
	    		req.setAttribute("filterBOEngineer", req.getParameter("filterBOEngineer"));
	    	}
			User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
			String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
			UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
			String week = req.getParameter("week");
			String weekAction = req.getParameter("weekAction");
			String showSchedule = req.getParameter("showSchedule");
			String showOSWork = req.getParameter("showOSWork");
			if ((weekAction.equals("BACKWARD"))||(weekAction.equals("FORWARD"))) {
				week = uB.getNextScheduledWeek(week, weekAction);
				weekAction = "NOW";
			}
			req.setAttribute("week", week);
			req.setAttribute("weekAction", weekAction);
			req.setAttribute("showSchedule", showSchedule);
			req.setAttribute("showOSWork", showOSWork);
		}
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	  	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	  	dispatcher.forward(req,resp);		
	}

}
