package com.lewei.www;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TimeType extends Date {

public static void main(String[] args) throws ParseException {
			
	                    //统一格式, 为了方便，小时..都去掉了
		  SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd"); 
			 
			//定义区间值
		  Date dateAfter=df.parse("2011-12-22");   
		    Date dateBefor=df.parse("2011-1-19");
			
		    //接收要判断的Date
		    System.out.println("请输入你的日期格式为 yyyy-MM-dd");
		    Scanner scan=new Scanner(System.in);
		    String str=scan.next();
		    
		    //将你输入的String 数据转化为Date
		    Date time=df.parse(str);
		   
                                   //判断time是否在XX之后，并且 在XX之前
		  if(time.before(dateAfter) && time.after(dateBefor)){
		    	System.out.println(df.format(time)+"在此区间");
		    }
		    else{
		    	System.out.println(df.format(time)+"不在此区间");
		    }
			
			}

}