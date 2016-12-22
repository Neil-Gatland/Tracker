package com.devoteam.tracker;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devoteam.tracker.model.User;
import com.devoteam.tracker.util.ServletConstants;

public class SiteSearchServlet extends HttpServlet {

	private static final long serialVersionUID = -6067139082395715809L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String destination = "/siteSearch.jsp";
		HttpSession session = req.getSession(false);
		if (session == null) {
			destination = "/logon.jsp";
			session = req.getSession(true);
			session.setAttribute("userMessage", "Please enter a valid email address and password");
		} else {
			session.setAttribute("prevScreen", "siteSearch");
		}
		String action = req.getParameter("selectedAction");
		String year = req.getParameter("selectedYear");	
		String month = req.getParameter("selectedMonth");
		String day = req.getParameter("selectedDay");
		String week = req.getParameter("selectedWeek");
		String client = req.getParameter("selectedClient");
		String project = req.getParameter("selectedProject");
		String site = req.getParameter("selectedSite");
		String nrId = req.getParameter("selectedNrId");
		String startDT = req.getParameter("selectedStartDT");
		String endDT = req.getParameter("selectedEndDT");
		String completionType = req.getParameter("selectedCompletionType");
		String reportSite = req.getParameter("reportSite");
		String reportNrId = req.getParameter("reportNrId");
		String reportDate = req.getParameter("reportDate");
		String reportType = req.getParameter("reportType");
		req.setAttribute("action", action);
		req.setAttribute("year", year);
		req.setAttribute("month", month);
		req.setAttribute("day", day);
		req.setAttribute("week", week);
		req.setAttribute("client", client);
		req.setAttribute("project", project);
		req.setAttribute("site", site);
		req.setAttribute("nrId", nrId);
		req.setAttribute("startDT", startDT);
		req.setAttribute("endDT", endDT);
		req.setAttribute("completionType", completionType);
		req.setAttribute("reportSite", reportSite);
		req.setAttribute("reportNrId", reportNrId);
		req.setAttribute("reportDate", reportDate);
		req.setAttribute("reportType", reportType);
		Random r = new Random();
		String ran = "?ran=" + String.valueOf(Math.abs(r.nextLong()));
      	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination+ran);
      	dispatcher.forward(req,resp);
	}

}
