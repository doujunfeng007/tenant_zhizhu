package com.minigod.zero.platform.utils;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CashUtil {

	//无需精确到毫秒，因为大多操作系统的无法提供1毫秒级别的精确度
	public static String genTransId(){
		Date dateNow = new Date();
		SimpleDateFormat TRANS_ID_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
		String datePart = TRANS_ID_FORMAT.format(dateNow);
		return datePart + RandomStringGenerator.getRandomStringByLength(9);
	}



	/**
	 * 求SET交集
	 * @param setA
	 * @param setB
	 * @return
	 */
	public static <T> Set<T> union(Set<T> setA,Set<T> setB){
		HashSet<T> union = new HashSet<T>();
		for (T a : setA) {
			if(setB.contains(a)){
				union.add(a);
			}
		}
		return union;
	}

	/**
	 * 求A中没有,但B中有的集合
	 * @param setA
	 * @param setB
	 * @return
	 */
	public static <T> Set<T> inASetNotInBSet(Set<T> setA,Set<T> setB){
		HashSet<T> result = new HashSet<T>();
		for (T a : setA) {
			if(!setB.contains(a)){
				result.add(a);
			}
		}
		return result;
	}


	public static <T,U> List<U> listToFieldList(Collection<T> lst, FieldSelector<T,U> selector){

		if (CollectionUtil.isEmpty(lst)) {
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

	public static <T,U> Map<U,T> listToMap(Collection<T> lst, FieldSelector<T,U> selector){

		if (CollectionUtil.isEmpty(lst)) {
			return Collections.emptyMap();
		}
		Map<U,T> mapResult = new HashMap<U,T>();
		for (T t : lst) {
			U field = selector.select(t);
			if (field == null) {
				continue;
			}
			mapResult.put(field, t);
		}
		return mapResult;
	}

	public static <K,V> Map<K,V> listToMap(List<V> list, String keyField){
		Map<K,V> result = new HashMap<>();
		if(list != null && list.size() > 0 && keyField != null && !"".equals(keyField)){
			String firstLetter = keyField.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + keyField.substring(1);
			try {
				for(Object obj : list){
					Method method = obj.getClass().getMethod(getter,null);
					K key = (K) method.invoke(obj,null);
					if(key != null){
						result.put(key,(V) obj);
					}
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static <T,U> Map<U,List<T>> listToMapList(Collection<T> lst, FieldSelector<T,U> selector){

		if (CollectionUtil.isEmpty(lst)) {
			return Collections.emptyMap();
		}

		Map<U,List<T>> mapResult = new HashMap<U,List<T>>();
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
	 * 其实用interface也可以，但是不知道为什么interface的eclipse提示不够完善，所以用abstract Class
	 * @author sunline
	 *
	 * @param <T>
	 * @param <U>
	 */
	public static abstract class FieldSelector<T,U>{
		public abstract U select(T t);
	}


	public static String getMaxLen(String value,int maxLen){
		if(value == null){
			return null;
		}

		if(value.length() > maxLen){
			return value.substring(0,maxLen);
		}

		return value;
	}

	// 返回内容，直接显示给前端的用户看
	public static R getDisplayMsg(R respVO, String sErrMsgFormat, Object... params) {
		respVO.setCode(888);
		MessageFormat msgFormat = new MessageFormat(sErrMsgFormat);
		respVO.setMsg(msgFormat.format(params));
		return respVO;
	}





	public static class MD5{
		private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "a", "b", "c", "d", "e", "f"};


//	    private static Logger LOG = LoggerFactory.getLogger(MD5.class);

		/**
		 * 转换字节数组为16进制字串
		 * @param b 字节数组
		 * @return 16进制字串
		 */
		private static String byteArrayToHexString(byte[] b) {
			StringBuilder resultSb = new StringBuilder();
			for (byte aB : b) {
				resultSb.append(byteToHexString(aB));
			}
			return resultSb.toString();
		}

		/**
		 * 转换byte到16进制
		 * @param b 要转换的byte
		 * @return 16进制格式
		 */
		private static String byteToHexString(byte b) {
			int n = b;
			if (n < 0) {
				n = 256 + n;
			}
			int d1 = n / 16;
			int d2 = n % 16;
			return hexDigits[d1] + hexDigits[d2];
		}


		public static String getObjectMD5(Object obj){
			return MD5Encode(getObjectString(obj));
		}

		/**
		 * MD5编码
		 * @param origin 原始字符串
		 * @return 经过MD5加密之后的结果
		 */
		public static String MD5Encode(String origin) {
			String resultString = null;
			try {
				resultString = origin;
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(resultString.getBytes("UTF-8"));
				resultString = byteArrayToHexString(md.digest());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultString;
		}

		/**
		 * 将Object按照key1=value1&key2=value2的形式拼接起来，程序不递归。Value需为基本类型，或者其toString方法能表示其实际的值
		 * @param o
		 * @return
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static String getObjectString(Object o) {

			if(isBasicType(o)){
				return o.toString();
			}
			else if(o.getClass().isArray()){
				return getArrayValue((Object[])o);
			}else if(o instanceof Iterable){
				Iterable itrPropertie = (Iterable)o;
				return getItrValue(itrPropertie);
			}else if(o instanceof Map){
				return getOrderedMapString((Map)o);
			}else{
				return getComlpexObjectOrderedString(o);
			}
		}

		private static String getComlpexObjectOrderedString(Object o) {
			ArrayList<String> list = new ArrayList<String>();
			try{
//	        	LOG.error("检测：" + JSONUtil.toJson(o));
				PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(o.getClass(), Object.class).getPropertyDescriptors();
				for (PropertyDescriptor f : propertyDescriptors) {

					Object propertie = f.getReadMethod().invoke(o);
					if (propertie != null) {
						String objectString = getObjectString(propertie);
						if(StringUtils.isNotEmpty(objectString)){
							list.add(f.getName() + "=" + objectString + "&");
						}
					}
				}
			}catch(Exception e){
				throw new RuntimeException(e);
			}

			int size = list.size();
			String [] arrayToSort = list.toArray(new String[size]);
			Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < size; i ++) {
				sb.append(arrayToSort[i]);
			}

			return "{" + sb.toString() + "}";
		}

		@SuppressWarnings("rawtypes")
		private static String getItrValue(Iterable propertie) {

			StringBuilder result = new StringBuilder();
			result.append("[");

			Iterator iterator = propertie.iterator();
			while(iterator.hasNext()){
				Object next = iterator.next();
				result.append(getObjectString(next) + ",");
			}
			result.append("]");

			return result.toString();
		}

		private static String getArrayValue(Object[] propertie) {
			StringBuilder result = new StringBuilder();
			result.append("[");
			for(Object o :propertie){
				result.append(getObjectString(o) + ",");
			}
			result.append("]");
			return result.toString();
		}

		/**
		 * 将Object按照key1=value1&key2=value2的形式拼接起来，程序不递归。Value需为基本类型，或者其toString方法能表示其实际的值
		 * @param map
		 * @return
		 */
		private static String getOrderedMapString(Map<Object, Object> map) {
			ArrayList<String> list = new ArrayList<String>();
			for(Map.Entry<Object,Object> entry:map.entrySet()){
				if(entry.getValue() != null){
					Object key = entry.getKey();
					Object value = entry.getValue();
					String objectString = getObjectString(value);
					if(StringUtils.isNotEmpty(objectString)){
						list.add(getObjectString(key) + "=" + objectString + "&");
					}

				}
			}
			int size = list.size();
			String [] arrayToSort = list.toArray(new String[size]);
			Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < size; i ++) {
				sb.append(arrayToSort[i]);
			}
			return "{" + sb.toString() + "}";
		}


		private static boolean isBasicType(Object o){
			if(o instanceof String){
				return true;
			}
			if(o instanceof Integer){
				return true;
			}
			if(o instanceof Long){
				return true;
			}
			if(o instanceof Float){
				return true;
			}
			if(o instanceof Double){
				return true;
			}
			if(o instanceof Byte){
				return true;
			}
			if(o instanceof Boolean){
				return true;
			}
			if(o instanceof Enum){
				return true;
			}
			if(o instanceof BigDecimal){
				return true;
			}
			return false;
		}
	}



}
