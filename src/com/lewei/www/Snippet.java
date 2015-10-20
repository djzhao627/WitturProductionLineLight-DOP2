package com.lewei.www;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Snippet {
	public static void main(String[] args) throws ParseException{
	        SimpleDateFormat formate =   new SimpleDateFormat( "HH:mm" );  
	
	             Date date1=formate.parse("07:00");
	             Date date2=formate.parse("15:00");
	             System.out.println("date1的日期为:"+formate.format(date1));
	             System.out.println("date2的日期为:"+formate.format(date2));
	             if(formate.parse(formate.format(new Date())).getTime()>date1.getTime() && formate.parse(formate.format(new Date())).getTime()<date2.getTime())
	            	 System.out.println("早班");
	             if(date1.getTime()>date2.getTime())
	             {
	                 System.out.println("date1的日期大于date2");
	             }
	             else if(date1.getTime()==date2.getTime())
	             {
	                 System.out.println("date2的日期等于date1");
	             }
	             else
	             {
	                 System.out.println("date2的日期大于date1");             }
}
}

