package com.lewei.www;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class T {
	
	public static void main(String[] args) throws ParseException {
		Date d = new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		c.add(c.DATE, 1);
		d = c.getTime();
		System.out.println(d);
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		System.out.println(sdf.parse("23:59:59").getTime()/1000-sdf.parse("22:59:59").getTime()/1000);
	}
}
