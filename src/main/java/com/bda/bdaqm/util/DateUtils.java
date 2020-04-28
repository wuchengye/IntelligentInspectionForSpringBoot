package com.bda.bdaqm.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * General Utils for Solving Date
 * @author jay.huang
 *
 */
public class DateUtils {
	/**
	 * 获取当前时间戳
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	/**
	 * 根据给定毫秒格式话时间
	 */
	public static Timestamp getTimestampByMills(long Millis) {
		return new Timestamp(Millis);
	}
	/**
	 *获取今天日期
	 * @return java.sql.Date
	 */
	public static java.sql.Date getTodaySqlDate() {
		return getSqlDateByNum(-1);
	}
	/**
	 *获取明天日期
	 * @return java.sql.Date
	 */
	public static java.sql.Date getTomorrowSqlDate() {
		return getSqlDateByNum(0);
	}
	/**
	 * 根据参数获取日期，i从-1开始（如：i为-1时，获取今天日期；i为0时，获取明天日期
	 * @return java.sql.Date
	 */
	public static java.sql.Date getSqlDateByNum(int i) {
		return new java.sql.Date(System.currentTimeMillis() + 1000*60*60*24*(i+1));
	}
	/**
	 * 获取本周剩余的工作日天数 
	 * @return int 剩余天数
	 */
	public static int getRemainDaysInWeek() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(new java.util.Date());  
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek) {  
          cal.add(Calendar.DAY_OF_MONTH, 0);  
        }  
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        int remainDays = 7-1-day;
       return remainDays;
	}
	
	public static String formatTime(Date d){
		return formatTime(d, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 根据给定的格式格式化时间(日期
	 * @param d
	 * @param pattern 见StringPool.PATTERN_*
	 * @return
	 */
	public static String formatTime(Date d, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}
	/**
	 * 根据给定的格式格式化时间(日期
	 * @param s
	 * @param pattern 见StringPool.PATTERN_*
	 * @return
	 */
	public static String formatTime(Timestamp s, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(s);
	}
	/**
	 * 获取今天日期的timestamp对象(年月日 00:00:00
	 */
	public static Timestamp getTodayTimestamp() {
		String todayDate = getTodaySqlDate().toString() + " 00:00:00";
		return Timestamp.valueOf(todayDate);
	}
	
	/**
	 * 字符串转日期
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date strToDate(String dateStr) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(dateStr);
	}
	
	/**
	 * 获取当前中国时间（格式：yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static String getChinaTime(){
        return getChinaTimeByPattern("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取当前中国时间（自定义格式）
	 * @return
	 */
	public static String getChinaTimeByPattern(String pattern){
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String dateString = dateFormat.format(date);
        return dateString;
	}
	
}
