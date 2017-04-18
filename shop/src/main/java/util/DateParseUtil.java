package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateParseUtil {

	public static String parseTimestampToDatetime(String time) throws ParseException {
		Calendar dayc1 = new GregorianCalendar();
		 
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss. S");
		Date daystart = df.parse(time);    //start_date是类似"2013-02-02"的字符串
		dayc1.setTime(daystart); 
		
		return dayc1.get(Calendar.YEAR)+"-"+String.format("%02d", dayc1.get(Calendar.MONTH)+1)+"-"+String.format("%02d",dayc1.get(Calendar.DAY_OF_MONTH))+" "+String.format("%02d",dayc1.get(Calendar.HOUR_OF_DAY))+":"+String.format("%02d",dayc1.get(Calendar.MINUTE))+":"+String.format("%02d",dayc1.get(Calendar.SECOND)); 
	}
	
	public static String parseTimestampToDate(String time) throws ParseException {
		Calendar dayc1 = new GregorianCalendar();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss. S");
		Date daystart = df.parse(time);    //start_date是类似"2013-02-02"的字符串
		dayc1.setTime(daystart); 
		
		return dayc1.get(Calendar.YEAR)+"-"+String.format("%02d", dayc1.get(Calendar.MONTH)+1)+"-"+String.format("%02d",dayc1.get(Calendar.DAY_OF_MONTH)); 
	}

	public static String getLastDate() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, -1);  //减1天
		return df.format(cal.getTime());
	}
	
	public static String getLastMonth() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, -1);  //减1个月
		return df.format(cal.getTime());
	}

	public static String getLastWeek() {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, -7);  //减7天
		return df.format(cal.getTime());
	}

}
