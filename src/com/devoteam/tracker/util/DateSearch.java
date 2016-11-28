package com.devoteam.tracker.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.devoteam.tracker.model.User;

public class DateSearch {
	
	private String year;			// year in YYYY format
	private String month;			// month in MM format
	private String displayMonth;	// month in Month YYYY format
	private String week1Mon;
	private String week1Tue;
	private String week1Wed;
	private String week1Thu;
	private String week1Fri;
	private String week1Sat;
	private String week1Sun;	
	private String week2Mon;
	private String week2Tue;
	private String week2Wed;
	private String week2Thu;
	private String week2Fri;
	private String week2Sat;
	private String week2Sun;	
	private String week3Mon;
	private String week3Tue;
	private String week3Wed;
	private String week3Thu;
	private String week3Fri;
	private String week3Sat;
	private String week3Sun;
	private String week4Mon;
	private String week4Tue;
	private String week4Wed;
	private String week4Thu;
	private String week4Fri;
	private String week4Sat;
	private String week4Sun;
	private String week5Mon;
	private String week5Tue;
	private String week5Wed;
	private String week5Thu;
	private String week5Fri;
	private String week5Sat;
	private String week5Sun;
	private String week6Mon;
	private String week6Tue;
	private String week6Wed;
	private String week6Thu;
	private String week6Fri;
	private String week6Sat;
	private String week6Sun;
	private String week1;
	private String week2;
	private String week3;
	private String week4;
	private String week5;
	private String week6;
	private int eomDay;
	private String message;		
	private String url;			
	
	public DateSearch(String url) {
		this.url = url;
		year = "";
		month = "";		
	}
	
	public void update ( String inputYear, String inputMonth, String dsAction ) {
		message = null;
    	Connection conn = null;
    	CallableStatement cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetDSNextYearMonth(?,?,?)}");
	    	cstmt.setString(1, inputYear);
	    	cstmt.setString(2, inputMonth);
	    	cstmt.setString(3, dsAction);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					year = rs.getString(1);
					month = rs.getString(2);
					displayMonth = rs.getString(3);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetDSNextYearMonth(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
		message = null;
    	conn = null;
    	cstmt = null;
	    try {
	    	conn = DriverManager.getConnection(url);
	    	cstmt = conn.prepareCall("{call GetDSDays(?,?)}");
	    	cstmt.setString(1, year);
	    	cstmt.setString(2, month);
			boolean found = cstmt.execute();
			if (found) {
				ResultSet rs = cstmt.getResultSet();
				while (rs.next()) {
					week1Mon = rs.getString(1);
					week1Tue = rs.getString(2);
					week1Wed = rs.getString(3);
					week1Thu = rs.getString(4);
					week1Fri = rs.getString(5);
					week1Sat = rs.getString(6);
					week1Sun = rs.getString(7);
					week2Mon = rs.getString(8);
					week2Tue = rs.getString(9);
					week2Wed = rs.getString(10);
					week2Thu = rs.getString(11);
					week2Fri = rs.getString(12);
					week2Sat = rs.getString(13);
					week2Sun = rs.getString(14);
					week3Mon = rs.getString(15);
					week3Tue = rs.getString(16);
					week3Wed = rs.getString(17);
					week3Thu = rs.getString(18);
					week3Fri = rs.getString(19);
					week3Sat = rs.getString(20);
					week3Sun = rs.getString(21);
					week4Mon = rs.getString(22);
					week4Tue = rs.getString(23);
					week4Wed = rs.getString(24);
					week4Thu = rs.getString(25);
					week4Fri = rs.getString(26);
					week4Sat = rs.getString(27);
					week4Sun = rs.getString(28);
					week5Mon = rs.getString(29);
					week5Tue = rs.getString(30);
					week5Wed = rs.getString(31);
					week5Thu = rs.getString(32);
					week5Fri = rs.getString(33);
					week5Sat = rs.getString(34);
					week5Sun = rs.getString(35);
					week6Mon = rs.getString(36);
					week6Tue = rs.getString(37);
					week6Wed = rs.getString(38);
					week6Thu = rs.getString(39);
					week6Fri = rs.getString(40);
					week6Sat = rs.getString(41);
					week6Sun = rs.getString(42);
					week1 = rs.getString(43);
					week2 = rs.getString(44);
					week3 = rs.getString(45);
					week4 = rs.getString(46);
					week5 = rs.getString(47);
					week6 = rs.getString(48);
					eomDay = rs.getInt(49);
				}
			}
	    } catch (Exception ex) {
	    	message = "Error in GetDSDays(): " + ex.getMessage();
	    	ex.printStackTrace();
	    } finally {
	    	try {
	    		if ((cstmt != null) && (!cstmt.isClosed()))	cstmt.close();
	    		if ((conn != null) && (!conn.isClosed())) conn.close();
		    } catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	    } 
	}
	
	public String searchBoxHTML() {
		StringBuilder html = new StringBuilder();
		int nextMonthDay = 1;
		int lastMonthDay = eomDay;
		// month line 
		HTMLElement tra = new HTMLElement("tr");
		HTMLElement tda0 = new HTMLElement("td","","&nbsp;");
		tra.appendValue(tda0.toString());
		HTMLElement tda1 = new HTMLElement("td","dateSearchHClass","&nbsp");
		tra.appendValue(tda1.toString());
		HTMLElement tda2 = new HTMLElement("td","dateSearchHClass","<<");
		tda2.setAttribute("onclick", "clickSearchBox('rwdYear')");
		tda2.setAttribute("title", "go back a year");
		tra.appendValue(tda2.toString());
		HTMLElement tda3 = new HTMLElement("td","dateSearchHClass","<");
		tda3.setAttribute("onclick", "clickSearchBox('rwdMonth')");
		tda3.setAttribute("title", "go back a month");
		tra.appendValue(tda3.toString());
		HTMLElement tda4 = new HTMLElement("td","dateSearchHClass",displayMonth);
		tda4.setAttribute("colspan", "3");
		tda4.setAttribute("onclick", "dateAction('chgMonth','"+year+"','"+month+"','0','0')");
		tra.appendValue(tda4.toString());
		HTMLElement tda5 = new HTMLElement("td","dateSearchHClass",">");
		tda5.setAttribute("onclick", "clickSearchBox('fwdMonth')");
		tda5.setAttribute("title", "go forward a month");
		tra.appendValue(tda5.toString());
		HTMLElement tda6 = new HTMLElement("td","dateSearchHClass",">>");
		tda6.setAttribute("onclick", "clickSearchBox('fwdYear')");
		tda6.setAttribute("title", "go forward a year");
		tra.appendValue(tda6.toString());
		html.append(tra.toString());
		// day name line
		HTMLElement trb = new HTMLElement("tr");
		HTMLElement tdb0 = new HTMLElement("td","","&nbsp;");
		trb.appendValue(tdb0.toString());
		HTMLElement tdb1 = new HTMLElement("td","dateSearchHClass","&nbsp");
		trb.appendValue(tdb1.toString());
		HTMLElement tdb2 = new HTMLElement("td","dateSearchDBClass","MON");
		trb.appendValue(tdb2.toString());
		HTMLElement tdb3 = new HTMLElement("td","dateSearchDBClass","TUE");
		trb.appendValue(tdb3.toString());
		HTMLElement tdb4 = new HTMLElement("td","dateSearchDBClass","WED");
		trb.appendValue(tdb4.toString());
		HTMLElement tdb5 = new HTMLElement("td","dateSearchDBClass","THU");
		trb.appendValue(tdb5.toString());
		HTMLElement tdb6 = new HTMLElement("td","dateSearchDBClass","FRI");
		trb.appendValue(tdb6.toString());
		HTMLElement tdb7 = new HTMLElement("td","dateSearchDBClass","SAT");
		trb.appendValue(tdb7.toString());
		HTMLElement tdb8 = new HTMLElement("td","dateSearchDBClass","SUN");
		trb.appendValue(tdb8.toString());
		html.append(trb.toString());
		// week 1 line
		HTMLElement trc = new HTMLElement("tr");
		HTMLElement tdc0 = new HTMLElement("td","","&nbsp;");
		trc.appendValue(tdc0.toString());
		HTMLElement tdc1 = new HTMLElement("td","dateSearchHClass",week1);
		tdc1.setAttribute("onclick", "dateAction('chgWeek','"+year+"','"+month+"','0','"+week1+"')");
		trc.appendValue(tdc1.toString());
		HTMLElement tdc8 = new HTMLElement("td","dateSearchDBClass",formatDay(week1Sun));
		if (week1Sun.equals("")) {
			tdc8 = new HTMLElement("td","dateSearchDClass",String.valueOf(lastMonthDay));
			lastMonthDay--;
		} else {
			tdc8.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week1Sun+"','0')");
		}
		HTMLElement tdc7 = new HTMLElement("td","dateSearchDBClass",formatDay(week1Sat));
		if (week1Sat.equals("")) {
			tdc7 = new HTMLElement("td","dateSearchDClass",String.valueOf(lastMonthDay));
			lastMonthDay--;
		} else {
			tdc7.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week1Sat+"','0')");
		}
		HTMLElement tdc6 = new HTMLElement("td","dateSearchDBClass",formatDay(week1Fri));
		if (week1Fri.equals("")) {
			tdc6 = new HTMLElement("td","dateSearchDClass",String.valueOf(lastMonthDay));
			lastMonthDay--;
		} else {
			tdc6.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week1Fri+"','0')");
		}
		HTMLElement tdc5 = new HTMLElement("td","dateSearchDBClass",formatDay(week1Thu));
		if (week1Thu.equals("")) {
			tdc5 = new HTMLElement("td","dateSearchDClass",String.valueOf(lastMonthDay));
			lastMonthDay--;
		} else {
			tdc5.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week1Thu+"','0')");
		}
		HTMLElement tdc4 = new HTMLElement("td","dateSearchDBClass",formatDay(week1Wed));
		if (week1Wed.equals("")) {
			tdc4 = new HTMLElement("td","dateSearchDClass",String.valueOf(lastMonthDay));
			lastMonthDay--;
		} else {
			tdc4.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week1Wed+"','0')");
		}
		HTMLElement tdc3 = new HTMLElement("td","dateSearchDBClass",formatDay(week1Tue));
		if (week1Tue.equals("")) {
			tdc3 = new HTMLElement("td","dateSearchDClass",String.valueOf(lastMonthDay));
			lastMonthDay--;
		} else {
			tdc3.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week1Tue+"','0')");
		}
		HTMLElement tdc2 = new HTMLElement("td","dateSearchDBClass",formatDay(week1Mon));
		if (week1Mon.equals("")) {
			tdc2 = new HTMLElement("td","dateSearchDClass",String.valueOf(lastMonthDay));
			lastMonthDay--;
		} else {
			tdc2.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week1Mon+"','0')");
		}
		trc.appendValue(tdc2.toString());
		trc.appendValue(tdc3.toString());
		trc.appendValue(tdc4.toString());
		trc.appendValue(tdc5.toString());
		trc.appendValue(tdc6.toString());
		trc.appendValue(tdc7.toString());
		trc.appendValue(tdc8.toString());		
		html.append(trc.toString());
		// week 2 line
		HTMLElement trd = new HTMLElement("tr");
		HTMLElement tdd0 = new HTMLElement("td","","&nbsp;");
		trd.appendValue(tdd0.toString());
		HTMLElement tdd1 = new HTMLElement("td","dateSearchHClass",week2);
		tdd1.setAttribute("onclick", "dateAction('chgWeek','"+year+"','"+month+"','0','"+week2+"')");
		trd.appendValue(tdd1.toString());
		HTMLElement tdd2 = new HTMLElement("td","dateSearchDBClass",formatDay(week2Mon));
		if (!week2Mon.equals("")) {
			tdd2.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week2Mon+"','0')");
		}
		trd.appendValue(tdd2.toString());
		HTMLElement tdd3 = new HTMLElement("td","dateSearchDBClass",formatDay(week2Tue));
		if (!week2Tue.equals("")) {
			tdd3.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week2Tue+"','0')");
		}
		trd.appendValue(tdd3.toString());
		HTMLElement tdd4 = new HTMLElement("td","dateSearchDBClass",formatDay(week2Wed));
		if (!week2Wed.equals("")) {
			tdd4.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week2Wed+"','0')");
		}
		trd.appendValue(tdd4.toString());
		HTMLElement tdd5 = new HTMLElement("td","dateSearchDBClass",formatDay(week2Thu));
		if (!week2Thu.equals("")) {
			tdd5.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week2Thu+"','0')");
		}
		trd.appendValue(tdd5.toString());
		HTMLElement tdd6 = new HTMLElement("td","dateSearchDBClass",formatDay(week2Fri));
		if (!week2Fri.equals("")) {
			tdd6.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week2Fri+"','0')");
		}
		trd.appendValue(tdd6.toString());
		HTMLElement tdd7 = new HTMLElement("td","dateSearchDBClass",formatDay(week2Sat));
		if (!week2Sat.equals("")) {
			tdd7.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week2Sat+"','0')");
		}
		trd.appendValue(tdd7.toString());
		HTMLElement tdd8 = new HTMLElement("td","dateSearchDBClass",formatDay(week2Sun));
		if (!week2Sun.equals("")) {
			tdd8.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week2Sun+"','0')");
		}
		trd.appendValue(tdd8.toString());
		html.append(trd.toString());
		// week 3 line
		HTMLElement tre = new HTMLElement("tr");
		HTMLElement tde0 = new HTMLElement("td","","&nbsp;");
		tre.appendValue(tde0.toString());
		HTMLElement tde1 = new HTMLElement("td","dateSearchHClass",week3);
		tde1.setAttribute("onclick", "dateAction('chgWeek','"+year+"','"+month+"','0','"+week3+"')");
		tre.appendValue(tde1.toString());
		HTMLElement tde2 = new HTMLElement("td","dateSearchDBClass",formatDay(week3Mon));
		if (!week3Mon.equals("")) {
			tde2.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week3Mon+"','0')");
		}
		tre.appendValue(tde2.toString());
		HTMLElement tde3 = new HTMLElement("td","dateSearchDBClass",formatDay(week3Tue));
		if (!week3Tue.equals("")) {
			tde3.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week3Tue+"','0')");
		}
		tre.appendValue(tde3.toString());
		HTMLElement tde4 = new HTMLElement("td","dateSearchDBClass",week3Wed);
		if (!week3Wed.equals("")) {
			tde4.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week3Wed+"','0')");
		}
		tre.appendValue(tde4.toString());
		HTMLElement tde5 = new HTMLElement("td","dateSearchDBClass",week3Thu);
		if (!week3Thu.equals("")) {
			tde5.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week3Thu+"','0')");
		}
		tre.appendValue(tde5.toString());
		HTMLElement tde6 = new HTMLElement("td","dateSearchDBClass",week3Fri);
		if (!week3Fri.equals("")) {
			tde6.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week3Fri+"','0')");
		}
		tre.appendValue(tde6.toString());
		HTMLElement tde7 = new HTMLElement("td","dateSearchDBClass",week3Sat);
		if (!week3Sat.equals("")) {
			tde7.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week3Sat+"','0')");
		}
		tre.appendValue(tde7.toString());
		HTMLElement tde8 = new HTMLElement("td","dateSearchDBClass",week3Sun);
		if (!week3Sun.equals("")) {
			tde8.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week3Sun+"','0')");
		}
		tre.appendValue(tde8.toString());
		html.append(tre.toString());
		// week 4 line
		HTMLElement trf = new HTMLElement("tr");
		HTMLElement tdf0 = new HTMLElement("td","","&nbsp;");
		trf.appendValue(tdf0.toString());
		HTMLElement tdf1 = new HTMLElement("td","dateSearchHClass",week4);
		tdf1.setAttribute("onclick", "dateAction('chgWeek','"+year+"','"+month+"','0','"+week4+"')");
		trf.appendValue(tdf1.toString());
		HTMLElement tdf2 = new HTMLElement("td","dateSearchDBClass",week4Mon);
		if (!week4Mon.equals("")) {
			tdf2.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week4Mon+"','0')");
		}
		trf.appendValue(tdf2.toString());
		HTMLElement tdf3 = new HTMLElement("td","dateSearchDBClass",week4Tue);
		if (!week4Tue.equals("")) {
			tdf3.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week4Tue+"','0')");
		}
		trf.appendValue(tdf3.toString());
		HTMLElement tdf4 = new HTMLElement("td","dateSearchDBClass",week4Wed);
		if (!week4Wed.equals("")) {
			tdf4.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week4Wed+"','0')");
		}
		trf.appendValue(tdf4.toString());
		HTMLElement tdf5 = new HTMLElement("td","dateSearchDBClass",week4Thu);
		if (!week4Thu.equals("")) {
			tdf5.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week4Thu+"','0')");
		}
		trf.appendValue(tdf5.toString());
		HTMLElement tdf6 = new HTMLElement("td","dateSearchDBClass",week4Fri);
		if (!week4Fri.equals("")) {
			tdf6.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week4Fri+"','0')");
		}
		trf.appendValue(tdf6.toString());
		HTMLElement tdf7 = new HTMLElement("td","dateSearchDBClass",week4Sat);
		if (!week4Sat.equals("")) {
			tdf7.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week4Sat+"','0')");
		}
		trf.appendValue(tdf7.toString());
		HTMLElement tdf8 = new HTMLElement("td","dateSearchDBClass",week4Sun);
		if (!week4Sun.equals("")) {
			tdf8.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week4Sun+"','0')");
		}
		trf.appendValue(tdf8.toString());
		html.append(trf.toString());
		// week 5 line
		HTMLElement trg = new HTMLElement("tr");
		HTMLElement tdg0 = new HTMLElement("td","","&nbsp;");
		trg.appendValue(tdg0.toString());
		HTMLElement tdg1 = new HTMLElement("td","dateSearchHClass",week5);
		tdg1.setAttribute("onclick", "dateAction('chgWeek','"+year+"','"+month+"','0','"+week5+"')");
		trg.appendValue(tdg1.toString());
		HTMLElement tdg2 = new HTMLElement("td","dateSearchDBClass",week5Mon);
		if (week5Mon.equals("")) {
			tdg2 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		}
		else {
			tdg2.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week5Mon+"','0')");
		}
		trg.appendValue(tdg2.toString());
		HTMLElement tdg3 = new HTMLElement("td","dateSearchDBClass",week5Tue);
		if (week5Tue.equals("")) {
			tdg3 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdg3.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week5Tue+"','0')");
		}
		trg.appendValue(tdg3.toString());
		HTMLElement tdg4 = new HTMLElement("td","dateSearchDBClass",week5Wed);
		if (week5Wed.equals("")) {
			tdg4 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdg4.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week5Wed+"','0')");
		}
		trg.appendValue(tdg4.toString());
		HTMLElement tdg5 = new HTMLElement("td","dateSearchDBClass",week5Thu);
		if (week5Thu.equals("")) {
			tdg5 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdg5.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week5Thu+"','0')");
		}
		trg.appendValue(tdg5.toString());
		HTMLElement tdg6 = new HTMLElement("td","dateSearchDBClass",week5Fri);
		if (week5Fri.equals("")) {
			tdg6 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;				
		} else {
			tdg6.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week5Fri+"','0')");
		}
		trg.appendValue(tdg6.toString());
		HTMLElement tdg7 = new HTMLElement("td","dateSearchDBClass",week5Sat);
		if (week5Sat.equals("")) {
			tdg7 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdg7.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week5Sat+"','0')");
		}
		trg.appendValue(tdg7.toString());
		HTMLElement tdg8 = new HTMLElement("td","dateSearchDBClass",week5Sun);
		if (week5Sun.equals("")) {
			tdg8 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdg8.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week5Sun+"','0')");
		}
		trg.appendValue(tdg8.toString());
		html.append(trg.toString());
		// week 6 line
		HTMLElement trh = new HTMLElement("tr");
		HTMLElement tdh0 = new HTMLElement("td","","&nbsp;");
		trh.appendValue(tdh0.toString());
		HTMLElement tdh1 = new HTMLElement("td","dateSearchHClass",week6);
		if (!week6Mon.equals(""))
			tdh1.setAttribute("onclick", "dateAction('chgWeek','"+year+"','"+month+"','0','"+week6+"')");
		trh.appendValue(tdh1.toString());
		HTMLElement tdh2 = new HTMLElement("td","dateSearchDBClass",week6Mon);
		if (week6Mon.equals("")) {
			tdh2 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdh2.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week6Mon+"','0')");
		}
		trh.appendValue(tdh2.toString());
		HTMLElement tdh3 = new HTMLElement("td","dateSearchDBClass",week6Tue);
		if (week6Tue.equals("")) {
			tdh3 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdh3.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week6Tue+"','0')");
		}
		trh.appendValue(tdh3.toString());
		HTMLElement tdh4 = new HTMLElement("td","dateSearchDBClass",week6Wed);
		if (week6Wed.equals("")) {
			tdh4 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdh4.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week6Wed+"','0')");
		}
		trh.appendValue(tdh4.toString());
		HTMLElement tdh5 = new HTMLElement("td","dateSearchDBClass",week6Thu);
		if (week6Thu.equals("")) {
			tdh5 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdh5.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week6Thu+"','0')");
		}
		trh.appendValue(tdh5.toString());
		HTMLElement tdh6 = new HTMLElement("td","dateSearchDBClass",week6Fri);
		if (week6Fri.equals("")) {
			tdh6 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdh6.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week6Fri+"','0')");
		}
		trh.appendValue(tdh6.toString());
		HTMLElement tdh7 = new HTMLElement("td","dateSearchDBClass",week6Sat);
		if (week6Sat.equals("")) {
			tdh7 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdh7.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week6Sat+"','0')");
		}
		trh.appendValue(tdh7.toString());
		HTMLElement tdh8 = new HTMLElement("td","dateSearchDBClass",week6Sun);
		if (week6Sun.equals("")) {
			tdh8 = new HTMLElement("td","dateSearchDClass", String.valueOf(nextMonthDay));
			nextMonthDay++;
		} else {
			tdh8.setAttribute("onclick", "dateAction('chgDay','"+year+"','"+month+"','"+week6Sun+"','0')");
		}
		trh.appendValue(tdh8.toString());
		html.append(trh.toString());
		return html.toString();
	}
	
	public String getYear() {
		return year;
	}
	
	public String getMonth() {
		return month;
	}
	
	public String formatDay( String fullDay) {
		String returnDay = fullDay;
		if (fullDay.startsWith("0"))
			returnDay = fullDay.substring(1,2);
		return returnDay;
	}

}
