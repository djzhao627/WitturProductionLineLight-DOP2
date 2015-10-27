package com.lewei.www;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class T {

	public static void main(String[] args) throws ParseException {
		Date d = new Date();
		Calendar c = new GregorianCalendar();
		c.setTime(d);
		c.add(c.DATE, 1);
		d = c.getTime();
		System.out.println(d);

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		System.out.println(sdf.parse("3:59:59").getTime() / 1000
				- sdf.parse("2:59:59").getTime() / 1000);

		for(int i=10;i<61;i++){
			System.out.print(i+",");
		}
		System.out.println();
		
		InputStream is = null;
		Workbook wb = null;
		Cell cell = null;
		Sheet sheet = null;
		try {
			is = new FileInputStream("D:\\plan.xls");
			
			wb = Workbook.getWorkbook(is);
			
			sheet = wb.getSheet(0);
			
			cell = sheet.getCell(2, 4);
			
			System.out.println(cell.getContents());
			
			wb.close();
			
			is.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
