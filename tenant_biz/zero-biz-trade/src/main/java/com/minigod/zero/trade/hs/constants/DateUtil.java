package com.minigod.zero.trade.hs.constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by huhu on 2016/7/5.
 */
public class DateUtil {

    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String DATETIME_FMT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 获得指定日期的前一天
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay){
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-1);

        String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }
    /**
     * 获得指定日期的后一天
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+1);

        String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    /**
     * 返回美股当前的夏令时/冬令时状态
     * @return
     */
    public static String determineTimeZoneIdForQuot() {
    	boolean isInDst = isInDST(new Date(),Locale.US);
        if(isInDst){
            return "EDT";
        }else{
            return "EST";
        }
	}

    public static boolean isInDST(Date date, Locale locale) {
        if (null != date && null != locale) {
            Date dstBegin = null;
            Date dstEnd = null;
            if (locale == Locale.US) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
                calendar.set(Calendar.MONTH, Calendar.MARCH);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                calendar.set(Calendar.HOUR_OF_DAY, 2);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                dstBegin = calendar.getTime();
                calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
                calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                calendar.set(Calendar.HOUR_OF_DAY, 2);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                dstEnd = calendar.getTime();
                return dstBegin.compareTo(date) <= 0 && dstEnd.compareTo(date) > 0;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }


    public static String formatDate(Date oDate , String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(oDate);
    }


    /**
     * @param sDate
     * @return
     * @throws ParseException
     * @throws ParseException
     */
    public static Date parseDatetime(String sDate , String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(sDate);
    }


    public static String getWeekOfDate(Date date) {
        String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

	/**
	 * 比较日期的大小
	 * @param date1
	 * @param date2
	 * @return 1：date1>date2 -1:date1<date2 0:相等
	 */
	public static int compareDate(Date date1 , Date date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String df1 = format.format(date1);
			String df2 = format.format(date2);
			Date dt1 = df.parse(df1);
			Date dt2 = df.parse(df2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static boolean isBetweenTime(Date startDate, Date endDate, Date date) throws ParseException{
		String strStartDate = formatYYYYMMDD(startDate);
		String strEndDate = formatYYYYMMDD(endDate);
		String strNowDate = formatYYYYMMDD(date);

		Date start = parseYYYYMMDD(strStartDate);
		Date end = parseYYYYMMDD(strEndDate);
		Date now = parseYYYYMMDD(strNowDate);
		if (now.getTime() >= start.getTime() && now.getTime() <= end.getTime()) {
			return true;
		}
		return false;
	}

	public static Date parseYYYYMMDD(String sDate) throws ParseException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		return sdf.parse(sDate);
	}

	public static String formatYYYYMMDD(Date oDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		return sdf.format(oDate);
	}/**
	 * 获取指定时间的前几天或后几天日期
	 */
	public static String getDay(Date date, int day){
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, day);
		return sf.format(c.getTime());
	}

	/**
	 * 获取指定时间的前几月或以后几月日期
	 */
	public static String getMouthDate(Date date , int mouth){
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, mouth);
		return sf.format(c.getTime());
	}}
