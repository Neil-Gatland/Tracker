package com.devoteam.tracker.util;

public class StringUtil {
	
	private static final String singleSpace = " ";
	private static final String carriageReturn = "\r";
	private static final String newLine = "\n";
	
	public static boolean hasNoValue(String input) {
		return input == null || input.trim().isEmpty();
	}
	
	public String formatttedSiteList(String rawSiteList) {
		String finalList = "";
		String site = "";
		boolean valid = true;
		// Firstly check that every character is numeric or is a 
		// carriage return, line throw or space. If not then set
		// return value to 'INVALID'. Otherwise format string with
		// each site delimited by a comma
		for (int i=0; i<rawSiteList.length(); i++) {
			String testChar = rawSiteList.substring(i, i+1);
			if ( 	(testChar.equals(singleSpace)) ||
					(testChar.equals(carriageReturn)) ||
					(testChar.equals(newLine)) ) {
				// if site string not empty write to final value
				if (!site.equals("")) {
					finalList = finalList + "'" + site + "',";
					site = "";
				}
			} else {				
				if (isNumeric(testChar)) {
					site = site + testChar;					
				} else {
					valid = false;
				}
			}
		}
		// ensure last site without any following characters is included
		if (!site.equals("")) {
			finalList = finalList + "'" + site + "',";			
		}
		// check validity 
		if ( (valid) && (!finalList.equals("")) ) {
			// remove last comma and add surrounding brackets to final value
			finalList = "(" + finalList.substring( 0, finalList.length() -1) + ")";
		} else {
			finalList = "INVALID";
		}
		return finalList;
	}
	
	private static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}

}
