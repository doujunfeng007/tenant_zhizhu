package com.minigod.zero.biz.common.utils;


import com.minigod.zero.core.tool.exception.ZeroException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateUtils {

	public final static long DAY_TIMES = 24 * 60 * 60 * 1000;// 一天的毫秒数
	public final static int DAY_HOUR_TIMES = 60 * 60 * 1000;// 一小时的毫秒数
	public final static int DAY_MIN_TIMES = 60 * 1000;// 一分钟的毫秒数
	public final static int DAY_SEC_TIMES = 1000;// 一秒的毫秒数

	/**
	 * :
	 */
	private final static String DATE_SEPARATOR_COLON = ":";
	/**
	 * -
	 */
	private final static String DATE_SEPARATOR_MINUS = "-";

	private final static String DATE_SEPARATOR_SLASH = "/";
	/**
	 * ,
	 */
	private final static String DATE_SEPARATOR_COMMA = ",";
	/**
	 * T
	 */
	private final static String DATE_SEPARATOR_T = "T";
	/**
	 * +
	 */
	private final static String DATE_SEPARATOR_PLUS = "+";
	/**
	 * AM
	 */
	private final static String DATE_SEPARATOR_AM = "AM";

	/**
	 * am
	 */
	private final static String DATE_SEPARATOR_AM_LOWER_CASE = "am";

	/**
	 * PM
	 */
	private final static String DATE_SEPARATOR_PM = "PM";

	/**
	 * pm
	 */
	private final static String DATE_SEPARATOR_PM_LOWER_CASE = "pm";

	/**
	 * .
	 */
	private final static String DATE_SEPARATOR_DOT = ".";
	private final static String DATE_SEPARATOR_SPACE = " ";

	/**
	 * GMT
	 */
	private final static String DATE_SEPARATOR_GMT = "GMT";

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public final static String DATE_FROMAT_STRING1 = "yyyy-MM-dd HH:mm:ss";
	/**
	 * MM/dd/yy
	 */
	public final static String DATE_FROMAT_STRING2 = "MM/dd/yy";
	/**
	 * yyyy-MM-dd
	 */
	public final static String DATE_FROMAT_STRING3 = "yyyy-MM-dd";
	/**
	 * MMddyy
	 */
	public final static String DATE_FROMAT_STRING4 = "MMddyy";
	/**
	 * yyMMdd
	 */
	public final static String DATE_FROMAT_STRING5 = "yyMMdd";
	/**
	 * yyyy-MM-dd-HH
	 */
	public final static String DATE_FROMAT_STRING6 = "yyyy-MM-dd-HH";
	/**
	 * HHmmssSSS
	 */
	public final static String DATE_FROMAT_STRING7 = "HHmmssSSS";
	/**
	 * yyyy-MM-ddTHH:mm:ss
	 */
	public final static String DATE_FROMAT_STRING8 = "yyyy-MM-dd'T'HH:mm:ss";
	/**
	 * MMM dd, yyyy
	 */
	public final static String DATE_FROMAT_STRING9 = "MMM dd, yyyy"; // Feb
	// 09,2014
	/**
	 * MM/dd/yyyy
	 */
	public final static String DATE_FROMAT_STRING10 = "MM/dd/yyyy";
	/**
	 * yyyy-MM-dd'T'HH:mm:ssZZ 2013-01-20T15:30:00+0800
	 */
	public final static String DATE_FROMAT_STRING11 = "yyyy-MM-dd'T'HH:mm:ssZZ";

	/**
	 * dd/MM/yyyy
	 */
	public final static String DATE_FROMAT_STRING12 = "dd/MM/yyyy";
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public final static String DATE_FROMAT_STRING13 = "yyyy-MM-dd HH:mm";
	/**
	 * MM-dd-yyyy
	 */
	public final static String DATE_FROMAT_STRING14 = "MM-dd-yyyy";
	/**
	 * MM/dd/yyyy HH:mm:ss
	 */
	public final static String DATE_FROMAT_STRING15 = "MM/dd/yyyy HH:mm:ss";
	/**
	 * yyyyMMdd
	 */
	public final static String DATE_FROMAT_STRING16 = "yyyyMMdd";
	/**
	 * yyyy-MM-ddTHH
	 */
	public final static String DATE_FROMAT_STRING17 = "yyyy-MM-dd'T'HH";
	/**
	 * yyyy-MM-dd HH:mm:ssSSS
	 */
	public final static String DATE_FROMAT_STRING18 = "yyyy-MM-dd HH:mm:ssSSS";

	public final static String FROMAT_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	/**
	 * yyyy/MM/dd
	 */
	public final static String DATE_FROMAT_STRING19 = "yyyy/MM/dd";
	/**
	 * dd/MMM/yyy:HH:mm:ss Z
	 */
	public final static String DATE_FROMAT_STRING20 = "dd/MMM/yyy:HH:mm:ss Z"; // 27/Feb/2014:10:19:35
	// +0800
	/**
	 * MMM dd,yyyy HH:mm:ss a z Feb 10, 2014 9:29:05 PM PST
	 */
	public final static String DATE_FROMAT_STRING21 = "MMM dd,yyyy HH:mm:ss a z";

	/**
	 * dd MMM, yyyy HH:mm:ss a z
	 */
	public final static String DATE_FROMAT_STRING22 = "dd MMM, yyyy HH:mm:ss a z"; // 20
	// Apr,
	// 2014
	// 9:40:39
	// PM
	// JST
	/**
	 * dd-MMM-yy HH:mm:ss a z
	 */
	public final static String DATE_FROMAT_STRING23 = "dd-MMM-yy HH:mm:ss a z"; // 17-Apr-14
	// 1:55:53
	// PM
	// PDT
	/**
	 * dd MMM yyyy HH:mm:ss z
	 */
	public final static String DATE_FROMAT_STRING24 = "dd MMM yyyy HH:mm:ss z"; // 23
	// Apr
	// 2014
	// 02:08:36
	// BDT
	/**
	 * MMM dd yyyy HH:mm:ss z
	 */
	public final static String DATE_FROMAT_STRING25 = "MMM dd yyyy HH:mm:ss z"; // 23
	// Apr
	// 2014
	// 03:08:36
	// GMT+02:00
	/**
	 * dd.MM.yyyy HH:mm:ss z
	 */
	public final static String DATE_FROMAT_STRING26 = "dd.MM.yyyy HH:mm:ss z"; // 23.04.2014
	// 03:08:36
	// GMT+02:00

	/**
	 * yyyy-MM-dd'T'HH:mm:sszz 2014-07-16T13:56:39+02:00
	 */
	public final static String DATE_FROMAT_STRING27 = "yyyy-MM-dd'T'HH:mm:ss Z";

	/**
	 * MMM dd, yyyy, HH:mm a Feb 10, 2014 9:29 PM
	 */
	public final static String DATE_FROMAT_STRING28 = "MMM dd, yyyy, HH:mm a";

	/**
	 * yyyy-MM-dd'T'HH:mm:ss az 2014-04-20T4:29:59 PM+09:00
	 */
	public final static String DATE_FROMAT_STRING29 = "yyyy-MM-dd'T'HH:mm:ss aZ";

	public final static DateFormat format_yyyyMMdd_HHmmss = new SimpleDateFormat(DATE_FROMAT_STRING1);
	public final static DateFormat format_yyyyMMddHHmmssSSS = new SimpleDateFormat(FROMAT_yyyyMMddHHmmssSSS);
	public final static DateFormat format2 = new SimpleDateFormat(DATE_FROMAT_STRING2);
	public final static DateFormat format_yyyyMMdd = new SimpleDateFormat(DATE_FROMAT_STRING3);
	public final static DateFormat format4 = new SimpleDateFormat(DATE_FROMAT_STRING4);
	public final static DateFormat format5 = new SimpleDateFormat(DATE_FROMAT_STRING5);
	public final static DateFormat format6 = new SimpleDateFormat(DATE_FROMAT_STRING6);
	public final static DateFormat format7 = new SimpleDateFormat(DATE_FROMAT_STRING7);
	public final static DateFormat format8 = new SimpleDateFormat(DATE_FROMAT_STRING8);
	public final static DateFormat format9 = new SimpleDateFormat(DATE_FROMAT_STRING9, Locale.ENGLISH);
	public final static DateFormat format10 = new SimpleDateFormat(DATE_FROMAT_STRING10);
	/**
	 * yyyy-MM-dd'T'HH:mm:ssZZ 2013-01-20T15:30:00+0800
	 */
	public final static DateFormat format11 = new SimpleDateFormat(DATE_FROMAT_STRING11);
	public final static DateFormat format12 = new SimpleDateFormat(DATE_FROMAT_STRING12);
	public final static DateFormat format13 = new SimpleDateFormat(DATE_FROMAT_STRING13);
	public final static DateFormat format14 = new SimpleDateFormat(DATE_FROMAT_STRING14);
	public final static DateFormat format15 = new SimpleDateFormat(DATE_FROMAT_STRING15);
	public final static DateFormat format16 = new SimpleDateFormat(DATE_FROMAT_STRING16);
	public final static DateFormat format17 = new SimpleDateFormat(DATE_FROMAT_STRING17);
	public final static DateFormat format18 = new SimpleDateFormat(DATE_FROMAT_STRING18);
	public final static DateFormat format19 = new SimpleDateFormat(DATE_FROMAT_STRING19);
	public final static DateFormat format20 = new SimpleDateFormat(DATE_FROMAT_STRING20, Locale.ENGLISH);
	public final static DateFormat format21 = new SimpleDateFormat(DATE_FROMAT_STRING21, Locale.ENGLISH);
	public final static DateFormat format22 = new SimpleDateFormat(DATE_FROMAT_STRING22, Locale.ENGLISH);
	public final static DateFormat format23 = new SimpleDateFormat(DATE_FROMAT_STRING23, Locale.ENGLISH);
	public final static DateFormat format24 = new SimpleDateFormat(DATE_FROMAT_STRING24, Locale.ENGLISH);
	public final static DateFormat format25 = new SimpleDateFormat(DATE_FROMAT_STRING25, Locale.ENGLISH);
	public final static DateFormat format26 = new SimpleDateFormat(DATE_FROMAT_STRING26, Locale.ENGLISH);

	/**
	 * yyyy-MM-dd'T'HH:mm:sszz 2014-07-16T13:56:39+02:00
	 */
	public final static DateFormat format27 = new SimpleDateFormat(DATE_FROMAT_STRING27);

	/**
	 * MMM dd, yyyy, HH:mm a Feb 10, 2014 9:29 PM
	 */
	public final static DateFormat format28 = new SimpleDateFormat(DATE_FROMAT_STRING28);

	/**
	 * yyyy-MM-dd'T'HH:mm:ss az 2014-04-20T4:29:59 PM+09:00
	 */
	public final static DateFormat format29 = new SimpleDateFormat(DATE_FROMAT_STRING29);

	/*
	 * New Year's Day ---元旦----1月1日 Memorial Day ---阵亡将士纪念日---5月的最后一个星期一
	 * Independence Day ---独立日---7月4日 Labor Day ---劳动节---9月第一个星期一 Veteran's Day
	 * ---退伍军人节---10月的第四星期一 Thanksgiving Day ---感恩节---11月的第四个星期四 Christmas Day
	 * ---圣诞节---12月25日
	 */
	public final static String[] Holidays = new String[]{"1-1", "5-5-1",
		"7-4", "9-1-1", "10-4-1", "11-4-4", "12-25"};

	/**
	 * 最小日期，设定为1000年1月1日
	 */
	public static final Date MIN_DATE = date(1000, 1, 1);
	/**
	 * 最大日期，设定为8888年1月1日
	 */
	public static final Date MAX_DATE = date(8888, 1, 1);
	private static final long MILLIS_IN_A_SECOND = 1000;
	private static final long SECONDS_IN_A_MINUTE = 60;
	private static final long MINUTES_IN_AN_HOUR = 60;
	private static final long HOURS_IN_A_DAY = 24;

	//private static final int[] daysInMonth = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final int DAYS_IN_A_WEEK = 7;
	private static final int MONTHS_IN_A_YEAR = 12;


	/**
	 * 根据年月日构建日期对象。注意月份是从1开始计数的，即month为1代表1月份。
	 *
	 * @param year  年
	 * @param month 月。注意1代表1月份，依此类推。
	 * @param day   日
	 * @return 一个日期
	 */
	public static Date date(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static class TimeFormatter {

		public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

		public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

		public final static String YYYY_MM_DD = "yyyy-MM-dd";

		public final static String YYYYMMDD = "yyyyMMdd";

		public final static String YYYYMM = "yyyy-MM";

		public final static String HH_MM_SS = "HH:mm:ss";

		public final static String DD_MM_YYYY = "dd/MM/yyyy";

		public final static String CN_YYYY_MM_DD = "yyyy年MM月dd日";

		public final static String DDMMYYYY = "ddMMyyyy";

		public static final String YYYYMMDD2 = "yyyy.MM.dd";

		public final static String YYYYMMDD3 = "yyyy/MM/dd";
	}

	//计算特定日期是否在该区间内
	public static boolean contains(Date startDate, Date endDate, Date currDate) {
		Interval i = new Interval(new DateTime(startDate), new DateTime(endDate));
		return i.contains(new DateTime(currDate));
	}

	/**
	 * 将日期转换成指定格式的字符串
	 *
	 * @param date java.util.Date对象
	 * @return
	 */
	public static final String dateToString(Date date, String timeFormatter) {
		if (date == null) {
			return null;
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(timeFormatter);
	}

	public static final String dateToString(Long date, String formatter) {
		return dateToString(new Date(date), formatter);
	}

	/**
	 * 获取当前事件字符串
	 */
	public static final String currentDateStr() {
		return dateToString(new Date(), TimeFormatter.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 将指定的字符串解析成日期类型
	 *
	 * @param dateStr 字符串格式的日期
	 * @return
	 */
	public static Date stringToDate(String dateStr, String pattern) {
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return format.parseDateTime(dateStr).toDate();
	}

	public static Date stringToDate(String dateStr) {
		if (dateStr.length() == 7) {
			return stringToDate(dateStr, TimeFormatter.YYYYMM);
		} else if (dateStr.length() == 10) {
			return stringToDate(dateStr, TimeFormatter.YYYY_MM_DD);
		} else if (dateStr.length() == 19) {
			return stringToDate(dateStr, TimeFormatter.YYYY_MM_DD_HH_MM_SS);
		} else {
			try {
				return stringToDate(dateStr, TimeFormatter.YYYY_MM_DD_HH_MM_SS);
			} catch (Exception e) {
				throw new ZeroException("不支持的日期格式.");
			}
		}
	}

	/**
	 * 检查日期是否是昨天
	 *
	 * @param date
	 * @return
	 */
	public static boolean isYesterday(long date) {
		DateTime todayStart = getToday(0, 0, 0);
		todayStart.plusDays(-1);
		return date >= todayStart.getMillis() && date <= todayStart.getMillis() + DAY_TIMES;
	}

	/**
	 * 时间差
	 *
	 * @param source 当前时间
	 * @param target 目标时间
	 * @return
	 */
	public static long timePre(Date source, Date target) {
		return target.getTime() - source.getTime();
	}

	/**
	 * 时间差
	 *
	 * @param source 当前时间
	 * @param target 目标时间
	 * @return
	 */
	public static long timePre(String source, String target) {
		Date sourceTime = stringToDate(source);
		Date targetTime = stringToDate(target);
		return timePre(sourceTime, targetTime);
	}

	/**
	 * 检查日期是否是今天之前的
	 *
	 * @param date
	 * @return
	 */
	public static boolean isBeforeToday(long date) {
		DateTime todayStart = getToday(0, 0, 0);
		return date < todayStart.getMillis();
	}

	/**
	 * 添小时
	 *
	 * @param date
	 * @param hours
	 * @return
	 */
	public static final Date plusHours(Date date, int hours) {
		DateTime dt = new DateTime(date);
		dt = dt.plusHours(hours);
		return dt.toDate();
	}

	/**
	 * 添日
	 *
	 * @param date
	 * @param days
	 * @param timeFormatter
	 * @return
	 */
	public static final String plusDays(Date date, int days, String timeFormatter) {
		DateTime dt = new DateTime(date);
		dt = dt.plusDays(days);
		return dt.toString(timeFormatter);
	}

	/**
	 * 添日
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static final Date plusDays(Date date, int days) {
		DateTime dt = new DateTime(date);
		dt = dt.plusDays(days);
		return dt.toDate();
	}

	public static final String plusHours(Date date, int hours, String timeFormatter) {
		DateTime dt = new DateTime(date);
		dt = dt.plusHours(hours);
		return dt.toString(timeFormatter);
	}

	public static final String plusMins(Date date, int minutes, String timeFormatter) {
		DateTime dt = new DateTime(date);
		dt = dt.plusMinutes(minutes);
		return dt.toString(timeFormatter);
	}

	public static final Date plusMins(Date date, int minutes) {
		DateTime dt = new DateTime(date);
		dt = dt.plusMinutes(minutes);
		return dt.toDate();
	}

	public static final String plusMonths(Date date, int months, String timeFormatter) {
		DateTime dt = new DateTime(date);
		dt = dt.plusMonths(months);
		return dt.toString(timeFormatter);
	}

	public static final Date plusMonths(Date date, int months) {
		DateTime dt = new DateTime(date);
		dt = dt.plusMonths(months);
		return dt.toDate();
	}

	/**
	 * 检查日期是否在今天之后
	 *
	 * @param date
	 * @return
	 */
	public static boolean isAfterToday(long date) {
		DateTime todayStart = getToday(23, 59, 59);
		return date > todayStart.getMillis();
	}

	public static DateTime getToday(int hour, int min, int sec) {
		DateTime now = new DateTime();
		DateTime todayStart = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), hour, min, sec);
		return todayStart;
	}

	public static Date getTodayDate(int hour, int min, int sec) {
		DateTime now = new DateTime();
		DateTime todayStart = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), hour, min, sec);
		return todayStart.toDate();
	}

	public static DateTime getDateTime(int year, int month, int day, int hour, int min, int sec) {
		return new DateTime(year, month, day, hour, min, sec);
	}

	public static Date getDate(int year, int month, int day, int hour, int min, int sec) {
		return getDateTime(year, month, day, hour, min, sec).toDate();
	}

	/**
	 * 是否是今天
	 *
	 * @return
	 */
	public static boolean isToday(long date) {
		DateTime todayStart = getToday(0, 0, 0);
		return date >= todayStart.getMillis() && date <= todayStart.getMillis() + DAY_TIMES;
	}

	public static int getWeekDay() {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.DAY_OF_WEEK);
	}

	public static boolean isWorkDay() {
		int weekDay = DateUtils.getWeekDay();
		if (weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY) {
			return false;
		}
		return true;
	}

	/**
	 * 得到今天0时时间
	 *
	 * @return
	 */
	public static long getToday() {
		return getToday(0, 0, 0).getMillis();
	}

	/**
	 * 得到今天指定时分秒的毫秒数
	 *
	 * @return
	 */
	public static long getTodayTime(int hour, int minute, int second) {
		return getToday(hour, minute, second).getMillis();
	}

	/**
	 * 返回当天的月数(1月返回1,依此类推)
	 *
	 * @return
	 */
	public static int getMonth() {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.MONTH) + 1;
	}

	public static int getPreMonth() {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.MONTH, -1);
		return today.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回当天的号数
	 *
	 * @return
	 */
	public static int getDay() {
		return new DateTime().getDayOfMonth();
	}

	/**
	 * 返回指定时间的号数
	 *
	 * @return
	 */
	public static int getDay(Date date) {
		return new DateTime(date).getDayOfMonth();
	}

	/**
	 * 取得当前月的最大天数
	 *
	 * @return
	 */
	public static int getMaxDayInMon() {
		Calendar c = Calendar.getInstance();
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getMaxDayInMon(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getYear() {
		return new DateTime().getYear();
	}

	public static int getPerMonth() {
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusMonths(-1);
		return dateTime.getMonthOfYear();
	}

	public static long getDaysBetween(Date start, Date end) {
		return Days.daysBetween(new DateTime(start), new DateTime(end)).getDays();
	}

	public static long getYearsBetween(Date start, Date end) {
		return Years.yearsBetween(new DateTime(start), new DateTime(end)).getYears();
	}

	public static long getDaysBetween(String start, String end) {
		return getDaysBetween(DateUtils.stringToDate(start), DateUtils.stringToDate(end));
	}

	public static long getDaysBetweenInTimeLevelCase(Date start, Date end) {
		return getDaysBetween(start, end);
	}

	public static long getDaysBetweenInDateLevelCase(Date start, Date end) {
		return getDaysBetween(DateUtils.dateToString(start, TimeFormatter.YYYY_MM_DD), DateUtils.dateToString(end, TimeFormatter.YYYY_MM_DD));
	}

	/**
	 * 获取指定开始时间到结束时间总共包含的月数,时间区间为:[startDate,endDate)
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static long containMonths(Date startDate, Date endDate) throws ParseException {
		return Months.monthsBetween(new DateTime(startDate), new DateTime(endDate)).getMonths();
	}

	/**
	 * 开始时间到结束时间包含的天数
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static long containDays(Date startDate, Date endDate) throws ParseException {
		return Days.daysBetween(new DateTime(startDate), new DateTime(endDate)).getDays();
	}

	/**
	 * 返回给定时间加上plusValue天后的字符串形式
	 *
	 * @param date      指定时间
	 * @param plusValue 天数，或以为负数
	 * @param formatter 返回的时间格式
	 * @return
	 */
	public static String dateToString(Date date, int plusValue, String formatter) {
		DateTime dateTime = new DateTime(date);
		dateTime = dateTime.plusDays(plusValue);
		return dateTime.toString(formatter);
	}

	/**
	 * 将给定的毫秒时间段转换成"时:分:秒"格式
	 *
	 * @param interval 毫秒
	 * @return
	 */
	public static String getFomaterTime(long interval) {
		StringBuffer time = new StringBuffer();
		long h = interval / DAY_HOUR_TIMES;
		interval = interval - h * DAY_HOUR_TIMES;
		long m = interval / DAY_MIN_TIMES;
		interval = interval - m * DAY_MIN_TIMES;
		long s = interval / DAY_SEC_TIMES;
		return time.append(h).append(":").append(m).append(":").append(s).toString();
	}

	/**
	 * 获得本周周一的日期(时间为当前时间)
	 *
	 * @return
	 */
	public static Date getMonday() {
		Calendar cal = Calendar.getInstance();
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -day_of_week);
		return cal.getTime();
	}

	/**
	 * @return
	 * @title:获取当前日期的前几天或者后几天日期
	 * @param: day 天数 负数代表前几天，正数代表后几天
	 */
	public static Date getDateByDay(int day) {
		// 获取当前日期
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) + day);
		return date.getTime();
	}

	/**
	 * @return
	 * @title:获取当前日期的前几年或者后几年
	 * @param: year 天数 负数代表前几年，正数代表后几年
	 */
	public static Date getDateByYear(int year) {
		// 获取当前日期
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, date.get(Calendar.YEAR) + year);
		return date.getTime();
	}

	public static final String getGMT(Date date) {
		SimpleDateFormat gmtSimpleDateFormat = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		gmtSimpleDateFormat.setTimeZone(TimeZone.getTimeZone(DATE_SEPARATOR_GMT));
		String dt = gmtSimpleDateFormat.format(date);
		return dt;
	}

	/**
	 * 获得指定日期之后一段时期的日期。例如某日期之后3天的日期等。
	 *
	 * @param origDate 基准日期
	 * @param amount   时间数量
	 * @param timeUnit 时间单位，如年、月、日等。用Calendar中的常量代表
	 * @return 一个日期
	 */
	public static final Date dateAfter(Date origDate, int amount, int timeUnit) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(origDate);
		calendar.add(timeUnit, amount);
		return calendar.getTime();
	}

	/**
	 * 获得指定日期之前一段时期的日期。例如某日期之前3天的日期等。
	 *
	 * @param origDate 基准日期
	 * @param amount   时间数量
	 * @param timeUnit 时间单位，如年、月、日等。用Calendar中的常量代表
	 * @return 一个日期
	 */
	public static final Date dateBefore(Date origDate, int amount, int timeUnit) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(origDate);
		calendar.add(timeUnit, -amount);
		return calendar.getTime();
	}

	/**
	 * 格式化日期，只保留年月日
	 */
	public static Date formatYYYYMMDD(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取date的开始时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取date的结束时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取指定年月的第二个星期日的日期
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Calendar getSecondSundayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1, 0, 0, 0);// 日期设置为月份的第1天
		// 如果weekDay =1 是周日
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		int sunDay = Calendar.SUNDAY;
		int sumDay = 0;
		if (weekDay == sunDay) {
			sumDay = 7;
		} else {
			sumDay = (7 - weekDay + sunDay) + 7;
		}
		cal.add(Calendar.DAY_OF_MONTH, sumDay);
		return cal;
	}

	/**
	 * 获取指定年月的第二个星期日的日期
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static Calendar getFirstSundayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1, 23, 59, 59);// 日期设置为今年的5月1日
		// 如果weekDay =1 是周日
		int weekDay = cal.get(Calendar.DAY_OF_WEEK);
		int sunDay = Calendar.SUNDAY;
		int sumDay = 0;
		if (weekDay == sunDay) {
			sumDay = 0;
		} else {
			sumDay = (7 - weekDay + sunDay);
		}
		cal.add(Calendar.DAY_OF_MONTH, sumDay);
		return cal;
	}

	/**
	 * 将任意格式的String类型的日期转换成Date
	 * (目前支持系统中已经定义的所有日期格式)
	 *
	 * @param dateString 要转换的日期
	 * @return
	 */
	public static Date transformStringDate(String dateString) throws ParseException {
		// 字符串预处理
		if (StringUtils.isBlank(dateString) || "null".equals(dateString)) {
			return parseDateStringToDate(format_yyyyMMdd_HHmmss, "1970-01-01 00:00:00");
		}
		dateString = preproccessDataString(dateString);

		dateString = dateString.trim();
		Date date = null;
		DateFormat dateFormat = null;
		if (isLengthEqualDateFormatStringLength(dateString, DATE_FROMAT_STRING1)
			&& dateString.contains(DATE_SEPARATOR_COLON)
			&& dateString.contains(DATE_SEPARATOR_MINUS)
			&& !dateString.contains(DATE_SEPARATOR_T)) {
			dateFormat = format_yyyyMMdd_HHmmss;
		} else if (isLengthEqualDateFormatStringLength(dateString, DATE_FROMAT_STRING2)
			&& dateString.contains(DATE_SEPARATOR_SLASH)) {
			dateFormat = format2;
		} else if (isLengthEqualDateFormatStringLength(dateString, DATE_FROMAT_STRING3)
			&& dateString.contains(DATE_SEPARATOR_MINUS)) {
			// 因为DATE_FROMAT_STRING3和DATE_FROMAT_STRING14无法在外形上做区分，所以只能通过以下方式做语义上的区分
			date = parseDateStringToDate(format14, dateString);
			if (date == null) {
				return parseDateStringToDate(format_yyyyMMdd, dateString);
			}
			int month = transformDateToCalendar(date).get(Calendar.MONTH) + 1;
			if (month == Integer.parseInt(dateString.substring(0, 2))) {
				return date;
			} else {
				date = parseDateStringToDate(format_yyyyMMdd, dateString);
			}
			return date;
		} else if (isLengthEqualDateFormatStringLength(dateString, DATE_FROMAT_STRING4)
			&& !dateString.contains(DATE_SEPARATOR_COLON)
			&& !dateString.contains(DATE_SEPARATOR_MINUS)
			&& !dateString.contains(DATE_SEPARATOR_SLASH)) {
			// 因为DATE_FROMAT_STRING4和DATE_FROMAT_STRING5无法在外形上做区分，所以只能通过以下方式做语义上的区分
			date = parseDateStringToDate(format4, dateString);
			int month = transformDateToCalendar(date).get(Calendar.MONTH) + 1;
			if (month == Integer.parseInt(dateString.substring(0, 2))) {
				return date;
			} else {
				date = parseDateStringToDate(format5, dateString);
			}
			return date;
		} else if (isLengthEqualDateFormatStringLength(dateString, DATE_FROMAT_STRING6)
			&& dateString.contains(DATE_SEPARATOR_MINUS)
			&& !dateString.contains(DATE_SEPARATOR_T)) {
			dateFormat = format6;
		} else if ((dateString.length() == DATE_FROMAT_STRING7.length())
			&& !dateString.contains(DATE_SEPARATOR_COLON)
			&& !dateString.contains(DATE_SEPARATOR_MINUS)
			&& !dateString.contains(DATE_SEPARATOR_SLASH)) {
			date = parseDateStringToDate(format7, dateString);
			dateFormat = format7;
		} else if (isLengthEqualDateFormatStringLength(dateString, DATE_FROMAT_STRING8)
			&& dateString.contains(DATE_SEPARATOR_COLON)
			&& dateString.contains(DATE_SEPARATOR_MINUS)
			&& dateString.contains(DATE_SEPARATOR_T)) {
			dateFormat = format8;
		} else if (dateString.contains(DATE_SEPARATOR_COMMA)
			&& !isContainsAMOrPM(dateString)) {
			date = parseDateStringToDate(format9, dateString);
			int year = transformDateToCalendar(date).get(Calendar.YEAR);
			if (year == Integer.parseInt(dateString.substring(
				dateString.length() - 4))) {
				return date;
			}
			dateFormat = format9;
		} else if (isLengthEqualDateFormatStringLength(dateString, DATE_FROMAT_STRING10)
			&& dateString.contains(DATE_SEPARATOR_SLASH)) {
			// 因为DATE_FROMAT_STRING10(MM/dd/yyyy)、DATE_FROMAT_STRING12(dd/MM/yyyy)
			// 和DATE_FROMAT_STRING19(yyyy/MM/dd)无法在外形上做区分，所以只能通过以下方式做语义上的区分
			date = parseDateStringToDate(format12, dateString);
			int year = transformDateToCalendar(date).get(Calendar.YEAR);
			int month = transformDateToCalendar(date).get(Calendar.MONTH) + 1;
			boolean isDATE_FROMAT_STRING19 = !dateString.substring(0, 4)
				.contains(DATE_SEPARATOR_SLASH);
			if (isDATE_FROMAT_STRING19
				&& year != Integer.parseInt(dateString.substring(0, 4))) {
				date = parseDateStringToDate(format19, dateString);
			} else {
				int month2 = Integer.parseInt(dateString
					.split(DATE_SEPARATOR_SLASH)[1]);
				if (month == month2) {
					return date;
				} else {
					date = parseDateStringToDate(format10, dateString);
				}
			}
			return date;
		} else if (dateString.contains(DATE_SEPARATOR_COLON)
			&& dateString.contains(DATE_SEPARATOR_MINUS)
			&& dateString.contains(DATE_SEPARATOR_T)
			&& !isContainsAMOrPM(dateString)
			&& !isEndWithTimeZoneAbbreviation(dateString)
			&& isEndWithTimeZoneOffSetInHour(dateString)) {
			dateString = changePostFixTimeZoneFormate(dateString);
			dateFormat = format11;
		} else if (isLengthEqualDateFormatStringLength(dateString,
			DATE_FROMAT_STRING13)
			&& dateString.contains(DATE_SEPARATOR_COLON)
			&& dateString.contains(DATE_SEPARATOR_MINUS)) {
			dateFormat = format13;
		} else if (isLengthEqualDateFormatStringLength(dateString,
			DATE_FROMAT_STRING15)
			&& dateString.contains(DATE_SEPARATOR_COLON)
			&& dateString.contains(DATE_SEPARATOR_SLASH)) {
			dateFormat = format15;
		} else if (isLengthEqualDateFormatStringLength(dateString,
			DATE_FROMAT_STRING16)
			&& !dateString.contains(DATE_SEPARATOR_COLON)
			&& !dateString.contains(DATE_SEPARATOR_MINUS)
			&& !dateString.contains(DATE_SEPARATOR_SLASH)) {
			date = parseDateStringToDate(format16, dateString);
			int year = transformDateToCalendar(date).get(Calendar.YEAR);
			if (year == Integer.parseInt(dateString.substring(0, 4))) {
				return date;
			}
		} else if (isLengthEqualDateFormatStringLength(dateString,
			DATE_FROMAT_STRING17)
			&& dateString.contains(DATE_SEPARATOR_MINUS)
			&& dateString.contains(DATE_SEPARATOR_T)) {
			dateFormat = format17;
		} else if (isLengthEqualDateFormatStringLength(dateString,
			DATE_FROMAT_STRING18)
			&& dateString.contains(DATE_SEPARATOR_COLON)
			&& dateString.contains(DATE_SEPARATOR_MINUS)) {
			dateFormat = format18;
		} else if (dateString.contains(DATE_SEPARATOR_COLON)
			&& dateString.contains(DATE_SEPARATOR_SLASH)) {
			String flag = dateString.substring(dateString.length() - 5,
				dateString.length() - 4);
			if (flag.equals(DATE_SEPARATOR_PLUS)) {
				dateFormat = format20;
			}
		} else if (isMatchFormate21Or22(dateString)) {
			date = parseDateStringToDate(format22, dateString);

			if (date == null) {
				return parseDateStringToDate(format21, dateString);
			}

			int day = transformDateToCalendar(date).get(Calendar.DAY_OF_MONTH);
			if (day == Integer
				.parseInt(dateString.split(DATE_SEPARATOR_SPACE)[0])) {
				return date;
			}
		} else if (isMatchFormate23(dateString)) {
			dateFormat = format23;
		} else if (isMatchFormate24Or25(dateString)) {
			date = parseDateStringToDate(format24, dateString);
			if (date == null) {
				return parseDateStringToDate(format25, dateString);
			}
			int day = transformDateToCalendar(date).get(Calendar.DAY_OF_MONTH);
			if (day == Integer
				.parseInt(dateString.split(DATE_SEPARATOR_SPACE)[0])) {
				return date;
			}
		} else if (isMatchFormate26(dateString)) {
			dateFormat = format26;
		} else if (isMatchFormate28(dateString)) {
			dateFormat = format28;
		} else if (isMatchFormate29(dateString)) {
			dateFormat = format29;
			dateString = changePostFixTimeZoneFormate(dateString);
		}
		return transformDateStringToDate(dateFormat, dateString);
	}

	public static boolean isMatchFormate26(String dateString) {
		if (StringUtils.isEmpty(dateString)) {
			return false;
		}
		return !isContainsAMOrPM(dateString)
			&& isEndWithTimeZoneAbbreviation(dateString)
			&& dateString.contains(DATE_SEPARATOR_DOT)
			&& !dateString.contains(DATE_SEPARATOR_MINUS)
			&& !dateString.contains(DATE_SEPARATOR_COMMA);
	}


	public static boolean isMatchFormate24Or25(String dateString) {
		if (StringUtils.isEmpty(dateString)) {
			return false;
		}
		return !isContainsAMOrPM(dateString)
			&& isEndWithTimeZoneAbbreviation(dateString)
			&& !dateString.contains(DATE_SEPARATOR_MINUS)
			&& !dateString.contains(DATE_SEPARATOR_COMMA)
			&& !dateString.contains(DATE_SEPARATOR_DOT);
	}

	public static Calendar transformDateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 是否匹配 MMM dd, yyyy, HH:mm a 例如:Feb 10, 2014 9:29 PM
	 *
	 * @param dateString
	 * @return
	 */
	private static boolean isMatchFormate28(String dateString) {
		if (StringUtils.isEmpty(dateString)) {
			return false;
		}
		return isLengthEqualDateFormatStringLength(dateString,
			DATE_FROMAT_STRING28)
			&& isContainsAMOrPM(dateString)
			&& !isEndWithTimeZoneAbbreviation(dateString)
			&& dateString.contains(DATE_SEPARATOR_COMMA)
			&& dateString.contains(DATE_SEPARATOR_COLON);
	}

	/**
	 * 考虑两位的月份和日期只有一位时，比如08月写8,01日写1
	 *
	 * @param dateFormatString
	 * @return
	 */
	private static boolean isLengthEqualDateFormatStringLength(
		String inputDateString, String dateFormatString) {
		return (inputDateString.length() == dateFormatString.length()
			|| inputDateString.length() == dateFormatString.length() - 1 || inputDateString
			.length() == dateFormatString.length() - 2);
	}

	public static boolean isMatchFormate23(String dateString) {
		if (StringUtils.isEmpty(dateString)) {
			return false;
		}
		return isContainsAMOrPM(dateString)
			&& isEndWithTimeZoneAbbreviation(dateString)
			&& dateString.contains(DATE_SEPARATOR_MINUS)
			&& !dateString.contains(DATE_SEPARATOR_COMMA)
			&& !dateString.contains(DATE_SEPARATOR_DOT);
	}

	/**
	 * 判断是否以+XXXX(或-XXXX)这种以小时为单位的时间偏移量结尾
	 *
	 * @param dateString
	 * @return
	 */
	private static boolean isEndWithTimeZoneOffSetInHourLen5(String dateString) {
		String postFix = dateString.substring(dateString.length() - 5
		);
		return postFix.length() == 5
			&& (postFix.contains(DATE_SEPARATOR_PLUS) || postFix
			.contains(DATE_SEPARATOR_MINUS))
			&& !postFix.contains(DATE_SEPARATOR_COLON);
	}


	/**
	 * 判断是否以+XX:XX(或-XX:XX)，+XXXX(或-XXXX)这种以小时为单位的时间偏移量结尾
	 *
	 * @param dateString
	 * @return
	 */
	private static boolean isEndWithTimeZoneOffSetInHour(String dateString) {
		return isEndWithTimeZoneOffSetInHourLen5(dateString)
			|| isEndWithTimeZoneOffSetInHourLen6(dateString);
	}

	private static boolean isMatchFormate29(String dateString) {
		if (StringUtils.isEmpty(dateString)) {
			return false;
		}
		return isContainsAMOrPM(dateString)
			&& !isEndWithTimeZoneAbbreviation(dateString)
			&& isEndWithTimeZoneOffSetInHour(dateString)
			&& dateString.contains(DATE_SEPARATOR_MINUS)
			&& dateString.contains(DATE_SEPARATOR_COLON);
	}


	/**
	 * 判断是否以+XX:XX(或-XX:XX)这种以小时为单位的时间偏移量结尾
	 *
	 * @param dateString
	 * @return
	 */
	private static boolean isEndWithTimeZoneOffSetInHourLen6(String dateString) {
		String postFix = dateString.substring(dateString.length() - 6
		);
		return postFix.length() == 6
			&& (postFix.contains(DATE_SEPARATOR_PLUS) || postFix
			.contains(DATE_SEPARATOR_MINUS))
			&& postFix.contains(DATE_SEPARATOR_COLON);
	}

	/**
	 * 将以+XX:XX(或-XX:XX)形式结尾的暁去掉其中的:替换成+XXXX(或-XXXX)形式,因为SimpleDateFormat中只有+
	 * XXXX(或-XXXX)这种形式（用z表示）
	 *
	 * @param dateString
	 * @return
	 */
	private static String changePostFixTimeZoneFormate(String dateString) {
		if (isEndWithTimeZoneOffSetInHourLen6(dateString)) {
			String postFix = dateString.substring(dateString.length() - 6
			);
			postFix = postFix.replace(DATE_SEPARATOR_COLON, "");
			dateString = dateString.substring(0, dateString.length() - 6)
				+ postFix;
		}

		return dateString;
	}

	/**
	 * 对输入的dateString进行预处理
	 *
	 * @param dateString
	 * @return
	 */
	private static String preproccessDataString(String dateString) {
		if (dateString.contains("Z")) {
			dateString = dateString.replace("Z", "");
		}
		return dateString;
	}

	public static Date transformDateStringToDate(DateFormat format,
												 String dateString) throws ParseException {
		if (format != null) {
			synchronized (format) {
				try {
					return format.parse(dateString);
				} catch (ParseException e) {
					throw e;
				}
			}
		} else {
			// 如果出现异常则把无法解析的dateString记录下来，作为异常的信息抛出
			String message = "format is null,the dataString is " + dateString;
			throw new ParseException(message, 1);
		}

	}

	/**
	 * 将指定格式的String类型的日期转换成Date
	 *
	 * @param format     要转换的格式，应该与要转换的日期格式对应
	 * @param dateString 要转换的日期
	 * @return
	 */
	public static Date parseDateStringToDate(DateFormat format, String dateString) {
		if (format != null) {
			synchronized (format) {
				try {
					return format.parse(dateString);
				} catch (ParseException e) {
					return null;
				}
			}
		} else {
//			String message = "format is null,the dataString is " + dateString;
			return null;
		}

	}

	public static boolean isMatchFormate21Or22(String dateString) {
		if (StringUtils.isEmpty(dateString)) {
			return false;
		}
		return isContainsAMOrPM(dateString)
			&& isEndWithTimeZoneAbbreviation(dateString)
			&& dateString.contains(DATE_SEPARATOR_COLON)
			&& dateString.contains(DATE_SEPARATOR_COMMA)
			&& !dateString.contains(DATE_SEPARATOR_MINUS)
			&& !dateString.contains(DATE_SEPARATOR_DOT);
	}

	/**
	 * 判断输入的日期类型的字符串是否以时区缩写(PST、PDT、BDT等)结尾
	 *
	 * @param dateString
	 * @return
	 */
	private static boolean isEndWithTimeZoneAbbreviation(String dateString) {
		if (StringUtils.isEmpty(dateString)
			|| !dateString.contains(DATE_SEPARATOR_SPACE)) {
			return false;
		}
		String[] splits = dateString.split(DATE_SEPARATOR_SPACE);
		String endString = splits[splits.length - 1];
		return endString.contains(DATE_SEPARATOR_T);
	}

	/**
	 * 判断输入的日期类型的字符串是否含有‘AM’、'am'或者‘PM’、'pm',如果是小写则去替换成大写
	 *
	 * @param dateString
	 * @return
	 */
	private static boolean isContainsAMOrPM(String dateString) {
		if (StringUtils.isEmpty(dateString)) {
			return false;
		}
		if (dateString.contains(DATE_SEPARATOR_PM_LOWER_CASE)
			|| dateString.contains(DATE_SEPARATOR_AM_LOWER_CASE)) {
			dateString.replace(DATE_SEPARATOR_PM_LOWER_CASE, DATE_SEPARATOR_PM);
			dateString.replace(DATE_SEPARATOR_AM_LOWER_CASE, DATE_SEPARATOR_AM);
			return true;
		} else return dateString.contains(DATE_SEPARATOR_AM)
			|| dateString.contains(DATE_SEPARATOR_PM);
	}

	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	public static void main(String args[]) throws ParseException {
		// System.err.println(dateToString(getDateByDay(-3),
		// TimeFormatter.YYYY_MM_DD));
		// System.err.println(containMonths(DateUtils.stringToDate("2013-02-10",
		// TimeFormatter.YYYY_MM_DD),DateUtils.stringToDate("2013-02-10",
		// TimeFormatter.YYYY_MM_DD)));
		// System.err.println("-==="+dateToString(getMonday(),
		// TimeFormatter.FORMATTER1));

		Date s = stringToDate("2013-01-01 10:10:13");
		Date e = stringToDate("2013-01-01 10:10:13");
		Date c = stringToDate("2013-01-01 10:10:13");
		System.err.println(contains(s, e, c));
		System.err.println(stringToDate("2013-01-01 10:10:13"));
	}
}
