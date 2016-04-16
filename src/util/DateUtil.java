package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {
	
	/**
	 * Return current date and time in db date time format
	 * @return String
	 */
	public static String getCurrentDateTime() {
		Date dt = new Date();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dt);
	}
	
	/**
	 * Convert a datetime string in the db to a java date
	 * @param dateString
	 * @return Date
	 */
	public static Date stringToDate(String dateString) {
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    try {
	        return  df.parse(dateString);
	    } catch (ParseException e) {
	        return null;
	    }
	}
}
