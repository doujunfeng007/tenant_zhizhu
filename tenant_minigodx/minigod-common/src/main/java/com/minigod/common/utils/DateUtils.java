package com.minigod.common.utils;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 时间工具类
 *
 * @author eric
 * @since 2024-10-25 17:21:05
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	public static final String YYYY = "yyyy";

	public static final String YYYY_MM = "yyyy-MM";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	private static final String[] PARSE_PATTERNS = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

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

	public static String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	public static String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	public static Date dateTime(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将日期字符串转换为yyyy-MM-dd格式的字符串
	 *
	 * @param dateStr 需要转换的日期字符串
	 * @return 格式化后的日期字符串，如"yyyy-MM-dd"，如果输入无效，则返回null
	 */
	public static String convertToYMD(String dateStr) {
		Date date = parseDate(dateStr);
		if (date == null) {
			return null;
		}
		return parseDateToStr(YYYY_MM_DD, date);
	}

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static String datePath() {
		Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 日期路径 即年/月/日 如20180808
	 */
	public static String dateTime() {
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
			return parseDate(str.toString(), PARSE_PATTERNS);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取服务器启动时间
	 */
	public static Date getServerStartDate() {
		long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * 计算相差天数
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
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
	 * 增加 LocalDateTime ==> Date
	 */
	public static Date toDate(LocalDateTime temporalAccessor) {
		ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}

	/**
	 * 增加 LocalDate ==> Date
	 */
	public static Date toDate(LocalDate temporalAccessor) {
		LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
		ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}
	/**
	 * 获取两个日期之间的所有日期列表（包含起始日期和结束日期）
	 *
	 * @param startTime 开始日期 (格式: yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss)
	 * @param endTime   结束日期 (格式: yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss)
	 * @return 日期列表 (格式: yyyy-MM-dd)
	 */
	public static List<String> getDaysBetween(String startTime, String endTime) {
		// 处理日期格式，确保统一使用yyyy-MM-dd格式
		String startDate = startTime.length() > 10 ? startTime.substring(0, 10) : startTime;
		String endDate = endTime.length() > 10 ? endTime.substring(0, 10) : endTime;

		// 解析日期字符串为LocalDate对象
		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);

		// 使用Stream生成日期序列，包含起始和结束日期
		return start.datesUntil(end.plusDays(1))
			.map(date -> date.format(DateTimeFormatter.ISO_LOCAL_DATE))
			.collect(Collectors.toList());
	}

}
