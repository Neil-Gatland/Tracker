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
import com.devoteam.tracker.util.DBDeterminer;
import com.devoteam.tracker.util.ServletConstants;
import com.devoteam.tracker.util.UtilBean;

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
			String snrId = req.getParameter("snrId");
	    	req.setAttribute("snrId", snrId); 
			String operation = req.getParameter("operation");
			String site = req.getParameter("site");
	    	req.setAttribute("site", site); 
	    	req.setAttribute("operation", operation); 
	    	if (operation.equals("toggle")) {
				User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
				String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
				UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
				String toggleResult = uB.toggleFESiteExpandCollapse(thisU.getFullname(), site);
				if (toggleResult.equals("Y")) 
					req.setAttribute("userMessage", "Toggled OK" );
				else
					req.setAttribute("userMessage", "Failed to toggle" );					
	    	} else {	    		
				String selectedStatus = req.getParameter("selectedStatus");
				if (!selectedStatus.equals("")) {
					User thisU = (User)session.getAttribute(ServletConstants.USER_OBJECT_NAME_IN_SESSION);
					String url = (String)session.getAttribute(ServletConstants.DB_CONNECTION_URL_IN_SESSION);
					UtilBean uB = new UtilBean(thisU, destination.substring(1), url);
					String updateResult = uB.updateProgressItemStatus(
							operation,
		    				Long.parseLong(snrId),
		    				selectedStatus,
							thisU.getNameForLastUpdatedBy());
		    		req.setAttribute("userMessage", updateResult);
					req.setAttribute("snrId", "");
				} else {
					session.setAttribute("userMessage", "");
				}
	    	}
			String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
	      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
	      	dispatcher.forward(req,resp);
		}
	}
}
