package com.minigod.zero.biz.common.utils;

import cn.hutool.core.date.DateUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.Func;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ReverseComparator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.comparators.ComparatorChain;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	/**
	 * <code>DT<code>
	 *
	 */
	public static class DT {
		public static final String YYYYMMDD = "yyyy-MM-dd";
		public static final String HHMMSS = "HH:mm:ss";
		public static final String DATETIME_FMT = "yyyy-MM-dd HH:mm:ss";

		/**
		 *
		 * @param sTime
		 * @return
		 * @throws ParseException
		 * @throws ParseException
		 */
		public static Date parseHHMMSS(String sTime) throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat(HHMMSS);
			return sdf.parse(sTime);
		}

		/**
		 * @param date
		 * @param addMin
		 * @return
		 */

		public static Date addDateOneDay(Date date, int addMin) {
			if (null == date) {
				return date;
			}
			Calendar c = Calendar.getInstance();
			c.setTime(date); // 设置当前日期
			c.add(Calendar.MINUTE, addMin);
			date = c.getTime();
			return date;
		}

		/**
		 * @param oDate
		 * @return
		 */
		public static String formatYYYYMMDD(Date oDate) {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
			return sdf.format(oDate);
		}

		/**
		 * @param oDate
		 * @return
		 */
		public static String formatHHMMSS(Date oDate) {
			SimpleDateFormat sdf = new SimpleDateFormat(HHMMSS);
			return sdf.format(oDate);
		}

		/**
		 *
		 * @param date
		 * @return
		 */
		public static String fotmatDatetime(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FMT);
			return sdf.format(date);
		}

		/**
		 * @param sDate
		 * @return
		 * @throws ParseException
		 * @throws ParseException
		 */
		public static Date parseYYYYMMDD(String sDate) throws ParseException,
				ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
			return sdf.parse(sDate);
		}

		/**
		 *
		 * @param date
		 * @return
		 */
		public static String formatDatetime(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FMT);
			return sdf.format(date);
		}

		/**
		 * 检查指定的datetime，是否符合yyyy-MM-dd HH:mm:ss的格式
		 *
		 * @return
		 */
		public static boolean isDatetimeFmt(String datetime) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FMT);
			try {
				sdf.parse(datetime);
			} catch (ParseException e) {
				return false;
			}
			return true;
		}

		/**
		 * 检查指定的time，是否符合HH:mm:ss的格式
		 *
		 * @return
		 */
		public static boolean isTimeFmt(String time) {
			SimpleDateFormat sdf = new SimpleDateFormat(HHMMSS);
			try {
				sdf.parse(time);
			} catch (ParseException e) {
				return false;
			}
			return true;
		}
	}

	/**
	 * 判断字符串中是否全部为英文字母
	 *
	 * @param s
	 * @return
	 */
	private static Pattern EngOrDec = Pattern.compile("^[\\da-zA-Z]+$");
	public static boolean ji(String s) {
		Matcher matcher = EngOrDec.matcher(s);
		if(matcher.find()){
			return true;
		} else{
			return false;
		}
	}

	/**
	 * 判断字符串是否为数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean number(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否为中文
	 *
	 * @param str
	 * @return
	 */
	public static boolean isChineseChar(String str) {

		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

	/**
	 * List 转成 Map
	 * @param lst
	 * @param strKeyField
	 * @return
	 */
	public static <T> Map<Object, T> listToMap(List<T> lst, String strKeyField) {
		if (CollectionUtils.isEmpty(lst)) {
			return Collections.emptyMap();
		}
		Map<Object, T> mapResult = new HashMap<Object, T>();
		for (T t : lst) {
			if (t == null) {
				continue;
			}
			Object objKey = BeanUtil.getProperty(t, strKeyField);
			mapResult.put(objKey, t);
		}
		return mapResult;
	}

	public static <T> Map<String, T> listToMapByParent(List<T> lst, String strKeyField) {
		if (CollectionUtils.isEmpty(lst)) {
			return Collections.emptyMap();
		}
		Map<String, T> mapResult = new HashMap<String, T>();
		for (T t : lst) {
			if (t == null) {
				continue;
			}
			String fieldValue = String.valueOf(getFieldValue(t, strKeyField));
			mapResult.put(fieldValue, t);
		}
		return mapResult;
	}

	public static Object getFieldValue(Object object, String fieldName){

		//根据 对象和属性名通过反射 调用上面的方法获取 Field对象
		Field field = getDeclaredField(object, fieldName) ;
		//抑制Java对其的检查
		field.setAccessible(true) ;
		try {
			//获取 object 中 field 所代表的属性值
			return field.get(object) ;
		} catch(Exception e) {
			e.printStackTrace() ;
		}
		return null;
	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredField
	 * @param object : 子类对象
	 * @param fieldName : 父类中的属性名
	 * @return 父类中的属性对象
	 */
	public static Field getDeclaredField(Object object, String fieldName) {
		Field field;
		Class<?> clazz = object.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (Exception e) {
				//这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				//如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
			}
		}
		return null;
	}

	/**
	 * 根据指定的List对象的字段进行排序
	 *
	 * @param lst
	 * @param sField
	 * @param iOrder 1 - 倒序， 其他数字 - 正序
	 */
	@SuppressWarnings("unchecked")
	public static <T> void sort(List<T> lst, String sField, int iOrder) {
		Comparator<T> fieldCompare = new BeanComparator<T>(sField);
		if (iOrder == 1) {
			fieldCompare = new ReverseComparator(fieldCompare);
		}
		Collections.sort(lst, fieldCompare);
	}

	/**
	 * 多个字段排序
	 *
	 * @param lst
	 * @param sFields
	 * @param iOrders
	 *            1 - 倒序， 其他数字 - 正序
	 */
	@SuppressWarnings("unchecked")
	public static <T> void sort(List<T> lst, String[] sFields, int[] iOrders) {
		if (sFields == null || iOrders == null || sFields.length == 0 || iOrders.length == 0
				|| sFields.length != iOrders.length) {
			throw new RuntimeException("参数错误");
		}
		ComparatorChain multiSort = new ComparatorChain();
		for (int i = 0; i < sFields.length; i++) {
			Comparator<T> fieldCompare = new BeanComparator<T>(sFields[i],
					ComparatorUtils.nullHighComparator(null));
			multiSort.addComparator(fieldCompare, iOrders[i] == 1);
		}
		Collections.sort(lst, multiSort);
	}



	/**
	 * 其实用interface也可以，但是不知道为什么interface的eclipse提示不够完善，所以用abstract Class
	 * @author THINK
	 *
	 * @param <T>
	 * @param <U>
	 */
	public static abstract class FieldSelector<T,U>{
		public abstract U select(T t);
	}


	public static <T,U> List<U> listToFieldList(Collection<T> lst, FieldSelector<T,U> selector){

		if (CollectionUtils.isEmpty(lst)) {
			return Collections.emptyList();
		}
		List<U> lstResult = new ArrayList<U>();
		for (T t : lst) {
			U field = selector.select(t);
			if (field == null) {
				continue;
			}
			lstResult.add(field);
		}
		return lstResult;
	}


	public static <T,U> Map<U, List<T>> listToMapList(Collection<T> lst, FieldSelector<T,U> selector){

		if (CollectionUtils.isEmpty(lst)) {
			return Collections.emptyMap();
		}

		Map<U, List<T>> mapResult = new HashMap<U, List<T>>();
		for (T t : lst) {
			U field = selector.select(t);
			if (field == null) {
				continue;
			}

			List<T> list = mapResult.get(field);
			if(list == null){
				list = new ArrayList<T>();
				mapResult.put(field, list);
			}
			list.add(t);
		}

		return mapResult;
	}


	/**
	 * 比较两个对象是否相等
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean equals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.getClass() != o2.getClass()) {
			return false;
		}
		if (o1 instanceof Boolean) {
			return ((Boolean) o1).booleanValue() == ((Boolean) o2).booleanValue();
		}
		if (o1 instanceof Integer) {
			return ((Integer) o1).intValue() == ((Integer) o2).intValue();
		}
		if (o1 instanceof Long) {
			return ((Long) o1).longValue() == ((Long) o2).longValue();
		}
		if (o1 instanceof Float) {
			return ((Float) o1).floatValue() == ((Float) o2).floatValue();
		}
		if (o1 instanceof Double) {
			return ((Double) o1).doubleValue() == ((Double) o2).doubleValue();
		}
		if (o1 instanceof Date) {
			return ((Date) o1).getTime() == ((Date) o2).getTime();
		}
		if (o1 instanceof String) {
			return ((String) o1).equals(o2);
		}
		return o1 == o2;
	}

	/**
	 * List<Date> 转换成List<String>
	 * @param datelist
	 * @return
	 */
	public static List<String> transferDate2String(List<Date> datelist){
		List<String> StringList = new ArrayList<String>();
		for(Date date: datelist){
			if(null != date){
				StringList.add(DateUtil.formatDate(date).substring(2));
			}
		}
		return StringList;
	}

	/**
	 * List<Date> 转换成List<String>
	 * @param dateList
	 * @return
	 */
	public static List<String> transferDate2String_(List<Date> dateList){
		List<String> StringList = new ArrayList<>();
		for(Date date: dateList){
			if(null != date){
				StringList.add(DateUtil.formatDate(date));
			}
		}
		return StringList;
	}

	/**
	 * @throws Exception
	 *
	    * @Title: list2Map
	    * @Description:将List转换成Map
	    * @param @param paramsList
	    * @param @param methodName
	    * @param @param clazz
	    * @param @return    参数
	    * @return Map<Integer,T>    返回类型
	    * @throws
	 */
	public static <V,M> Map<V, M> list2Map(Class<V> clazzKey , List<M> paramsList, String methodName,
                                           Class<M> clazz) throws Exception {

		Map<V,M> returnMap = new HashMap<V,M>();
		for (M obj : paramsList) {
			try {
				Method method = clazz.getMethod(methodName);
				V key = (V) method.invoke(obj);
				returnMap.put(key, obj);
			} catch (Exception e) {
				throw e;
			}
		}
		return returnMap;
	}

	public static Integer conventTime(String timeHHMMSS0) {
		if (StringUtils.isNotBlank(timeHHMMSS0)) {
			String timeHHMMSS1 = "00:00:00";
			Date date0 = DateUtil.parse(timeHHMMSS0, "HH:mm:ss");
			Date date1 = DateUtil.parse(timeHHMMSS1, "HH:mm:ss");

			DateTime dateTime0 = new DateTime(date0);
			DateTime dateTime1 = new DateTime(date1);

			long time0 = getToday(dateTime0.getHourOfDay(), dateTime0.getMinuteOfHour(), dateTime0.getSecondOfMinute()).toDate().getTime() / 1000;
			long time1 = getToday(dateTime1.getHourOfDay(), dateTime1.getMinuteOfHour(), dateTime1.getSecondOfMinute()).toDate().getTime() / 1000;
			return (int) (time0 - time1);
		}
		return null;
	}

	public static DateTime getToday(int hour, int min, int sec) {
		DateTime now = new DateTime();
		DateTime todayStart = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), hour, min, sec);
		return todayStart;
	}
	public static int compareVersion(String version1,String version2){
		int arr1[] = {0,0,0};
		int arr2[] = {0,0,0};
		String v1[] = version1.split("\\.");
		String v2[] = version2.split("\\.");
		for(int i=0;i<v1.length;i++){
			if(StringUtils.isNotEmpty(v1[i])){
				arr1[i] = Integer.valueOf(v1[i]);
			}
		}
		for(int i=0;i<v2.length;i++){
			if(StringUtils.isNotEmpty(v2[i])){
				arr2[i] = Integer.valueOf(v2[i]);
			}
		}

		return compare(arr1,arr2,0,3);//从0位开始比较，往后递归
	}

	/**
	 * 比较，前提是两个数组的长度要相等
	 *
	 * @param arr
	 * @param brr
	 * @param i
	 * @param n 比较的最大位数，如果输入的超出比较位数的数组，返回前面N位比较的结果
	 * @return
	 */
	public  static int compare(int arr[],int brr[],int i,int n){
		if(i<n){
			if(arr[i]>brr[i]){
				return 1;
			}else if(arr[i]<brr[i]){
				return -1;
			}else{
				return compare(arr,brr,i+1,n);
			}
		}else{
			return 0;
		}

	}
	/**
	 * <code>Stock</code> 获取行情历史数据方便类.
	 public static class Stock {
	 public static final String YAHOO_FINANCE_URL = "http://table.finance.yahoo.com/table.csv?";

	 //public static final String YAHOO_FINANCE_URL_TODAY = "http://download.finance.yahoo.com/d/quotes.csv?";

	 //public static final String RT_QUOTE_URL = "http://finance.yahoo.com/d/quotes.csv?";

	 //public static final String RT_QUOT_URL = "http://hq.sinajs.cn/list=";


	 //从YAHOO获取股票行情历史数据

	 public static List<StkDailyVO> getStockData(String stkCode, String fromDate, String toDate, String sType) {
	 List<StkDailyVO> list = new ArrayList<>();
	 String[] datefromInfo = fromDate.split("-");
	 String[] toDateInfo = toDate.split("-");

	 String sCode = stkCode;
	 // a – 起始时间，月
	 String a = (Integer.valueOf(datefromInfo[1]) - 1) + "";
	 // b – 起始时间，日
	 String b = datefromInfo[2];
	 // c – 起始时间，年
	 String c = datefromInfo[0];
	 // d – 结束时间，月
	 String d = (Integer.valueOf(toDateInfo[1]) - 1) + "";
	 // e – 结束时间，日
	 String e = toDateInfo[2];
	 // f – 结束时间，年
	 String f = toDateInfo[0];

	 String params = "&a=" + a + "&b=" + b + "&c=" + c + "&d=" + d
	 + "&e=" + e + "&f=" + f + "&g=" + sType;
	 String url = YAHOO_FINANCE_URL
	 + "s="
	 + (stkCode.contains("SH") ? stkCode.replaceAll("SH", "SS")
	 : stkCode) + params;

	 URL MyURL = null;
	 URLConnection con = null;
	 InputStreamReader ins = null;
	 BufferedReader in = null;
	 try {
	 MyURL = new URL(url);
	 con = MyURL.openConnection();
	 ins = new InputStreamReader(con.getInputStream(), "UTF-8");
	 in = new BufferedReader(ins);
	 String newLine = in.readLine();
	 while ((newLine = in.readLine()) != null) {
	 String stockInfo[] = newLine.trim().split(",");
	 StkDailyVO sd = new StkDailyVO();
	 sd.setAssetId(sCode);
	 sd.setStkCode(sCode.substring(0, sCode.lastIndexOf(".")));
	 sd.setDate(stockInfo[0]);

	 sd.setOpen(new BigDecimal(stockInfo[1]));
	 sd.setHigh(new BigDecimal(stockInfo[2]));
	 sd.setLow(new BigDecimal(stockInfo[3]));
	 sd.setClose(new BigDecimal(stockInfo[4]));
	 sd.setVolume(new BigDecimal(stockInfo[5]));
	 sd.setPrevClose(new BigDecimal(stockInfo[6]));
	 list.add(sd);
	 }
	 } catch (Exception ex) {
	 ex.printStackTrace();
	 return null;
	 } finally {
	 if (in != null)
	 try {
	 in.close();
	 } catch (IOException ex) {
	 ex.printStackTrace();
	 }
	 }
	 return list;
	 }
	 }*/
	public static String getLang(HttpServletRequest request, String field, String defaultField){
		return Func.toStr(request.getHeader(field), defaultField);
	}
}
