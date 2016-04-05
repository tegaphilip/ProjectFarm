package util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {
	
	public static String getCurrentDateTime() {
		Date dt = new Date();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(dt);
	}
}
