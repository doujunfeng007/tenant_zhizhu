package com.minigod.zero.biz.common.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends DateUtils {

	public static final String YYYY = "yyyy";

	public static final String YYYY_MM = "yyyy-MM";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

	public static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;

	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";

	/**
	 * 获取当前Date型日期
	 *
	 * @return Date() 当前日期
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 *
	 * @return String
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}

	/**
	 * 获取当前日期, 默认格式为yyyyMMdd
	 *
	 * @return String
	 */
	public static String getDate2() {
		return dateTimeNow(YYYYMMDD);
	}

	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	public static final Date dateTime(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 日期路径 即年/月/日 如20180808
	 */
	public static final String dateTime() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyyMMdd");
	}

	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseDate(Object str, String pattern) {
		DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
		return format.parseDateTime(str.toString()).toDate();
	}

	/**
	 * 获取服务器启动时间
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * 计算两个时间差
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	/**
	 * 获取月份第一天
	 * @return
	 */
	public static String getMonthFirstDay(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH,1);
		String first = dateTime(c.getTime());
		return first;

	}

	/**
	 * 获取两个时间之间差的天数
	 * @param beforeDate 开始时间
	 * @param afterDate 结束时间
	 * @return
	 */
	public static Long dateSubDay(Date beforeDate, Date afterDate){
		long beforeDateTime = beforeDate.getTime();
		long afterDateTime = afterDate.getTime();
		long betweenDays =(beforeDateTime - afterDateTime) / (1000 * 60 * 60 * 24);

		return betweenDays;
	}

	/**
	 * 获取两个时间之间差的天数
	 * @param beforeDate 开始时间
	 * @param afterDate 结束时间
	 * @return
	 */
	public static Long dateSubDay2(Date beforeDate, Date afterDate){
		long beforeDateTime = beforeDate.getTime();
		long afterDateTime = afterDate.getTime();
		long hoursDifference =(beforeDateTime - afterDateTime) / (1000 * 60 * 60);
		long betweenDays = hoursDifference / 24;

		if (hoursDifference % 24 > 0) {
			betweenDays++;
		}

		return betweenDays;
	}


	/**
	 * 时间转化为日期
	 * @param date
	 * @return
	 */
	public static Date formatRoundDate(Date date){
		try {
			return sdfTime.parse(sdfDate.format(date) + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取date的开始时间
	 * @param date
	 * @return
	 */
	public static Date getDayBegin(Date date){
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
	 * 获取date的开始时间
	 * @param dateStr
	 * @return
	 */
	public static String getDayBegin(String dateStr){
		return dateStr + " 00:00:00";
	}

	/**
	 * 获取date的结束时间
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date){
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
	 * 获取date的结束时间
	 * @param dateStr
	 * @return
	 */
	public static String getDayEnd(String dateStr){
		return dateStr + " 23:59:59";
	}

	public static DateTime nextMonth() {
		return offsetMonth(new DateTime(), 1);
	}

	public static DateTime lastMonth() {
		return offsetMonth(new DateTime(), -1);
	}

	public static DateTime offsetMonth(Date date, int offset) {
		return offset(date, DateField.MONTH, offset);
	}

	public static DateTime offset(Date date, DateField dateField, int offset) {
		return dateNew(date).offset(dateField, offset);
	}

	public static DateTime dateNew(Date date) {
		return new DateTime(date);
	}

	public static int daysBetween(Date smdate,Date bdate)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static String formatCn(Date date) {
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}
		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}
		if (delta < 12L * 4L * ONE_WEEK) {
			long months = toMonths(delta);
			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}

	/**
	 * @title:获取当前日期的前几天或者后几天日期
	 * @param: day 天数 负数代表前几天，正数代表后几天
	 * @return
	 */
	public static Date getDateByDay(int day) {
		// 获取当前日期
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, date.get(Calendar.DATE) + day);
		return date.getTime();
	}

	public static String formatDateTime(Date date) {
		return sdfTime.format(date);
	}

	public static String formatDate(Date date) {
		return sdfTime.format(date);
	}

	/**
	 * 获取当前月份
	 * @return
	 */
	public static int getMonth() {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前年份
	 * @return
	 */
	public static int getYear() {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR);
	}

	/**
	 * 获取当前小时
	 * @return
	 */
	public static int getHours() {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 自定义当前时间
	 * @return
	 */
	public static Date getDateTime(int hour, int minute, int second) {
		// 获取当前日期
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

	public static long getDaysBetween(Date start, Date end) {
		return Days.daysBetween(new org.joda.time.DateTime(start), new org.joda.time.DateTime(end)).getDays();
	}
}
