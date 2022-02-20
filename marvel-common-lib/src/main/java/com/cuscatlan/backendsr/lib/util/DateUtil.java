package com.cuscatlan.backendsr.lib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String formatDateToString (Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String result = null;
		sdf.setLenient(false);
		
		result = sdf.format(date);
		
		return result;
	}
	
	public static Date formatStringToDate (String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date result = null;
		sdf.setLenient(false);
		
		try {
			result = sdf.parse(date);
		} catch (ParseException e) {
			 result = new Date();
		}
		
		return result;
	}
	
}

