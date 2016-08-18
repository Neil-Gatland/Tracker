package com.devoteam.tracker.util;

import com.google.appengine.api.utils.SystemProperty;

public class DBDeterminer {
	public static String determineURL() {
	    try {
		      if (SystemProperty.environment.value() ==
		          SystemProperty.Environment.Value.Production) {
		        // Load the class that provides the new "jdbc:google:mysql://" prefix.
		        Class.forName("com.mysql.jdbc.GoogleDriver");
		        return "jdbc:google:mysql://dvt-uk-rant-001:dev/tracker?user=root";
		        //return TrackerProperties.getTrackerProperty(TrackerProperties.CLOUDSQLURL);
		        		//"jdbc:google:mysql://your-project-id:your-instance-name/tracker?user=root";
		      } else {
		        // Local MySQL instance to use during development.
		        Class.forName("com.mysql.jdbc.Driver");
		        return TrackerProperties.getTrackerProperty(TrackerProperties.MYSQLURL); 
		        		//"jdbc:mysql://127.0.0.1:3306/tracker?user=root&password=m14BFw!!";

		        // Alternatively, connect to a Google Cloud SQL instance using:
		        // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		      //return null;
		      return "error:"+e.getMessage();
		    }

		
	}

}
