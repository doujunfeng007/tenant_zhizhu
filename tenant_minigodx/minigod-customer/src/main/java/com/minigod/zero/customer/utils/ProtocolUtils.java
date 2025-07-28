package com.minigod.zero.customer.utils;

import cn.hutool.core.util.ReflectUtil;
import com.minigod.zero.core.tool.constant.MktConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.utils.ProtocolUtils
 * @Date: 2023年02月15日 19:45
 * @Description:
 */
@Slf4j
public class ProtocolUtils {

	/**
	 * 基于对象组装URL
	 *
	 * @param t
	 * @return
	 */
	public static <T extends Serializable> String getUrl(String baseUrl, T t) {
		Map<String, Field> fieldMap = ReflectUtil.getFieldMap(t.getClass());
		StringBuffer sb = new StringBuffer(baseUrl);
		sb.append("?");
		for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
			String fieldName = entry.getKey();
			Field field = entry.getValue();
			try {
				//去掉静态方法
				if (!Modifier.isFinal(field.getModifiers())) {
					field.setAccessible(true);
					Object obj = field.get(t);
					if (obj != null) {
						sb.append(fieldName).append("=").append(escapeSpecialChar(obj.toString())).append("&");
					}
				}
			} catch (Exception e) {
				throw new RuntimeException("get url error.", e);
			}
		}
		if (sb != null && sb.length() > 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		return sb.toString();
	}

//	//手机号加密 数据那边出报表或者其他系统对接时，需要用到手机号，5.0系统不再对手机号做加密
// 	@Deprecated
//	public static String getEncryptPhone(String phone) {
//		return AESUtil.encrypt(phone, SecurityKey.MOBILE_PHONE_KEY);
//	}

	//通讯录名称加密
	public static String getEncryptNickName(String nickName) {
		return AESUtil.encrypt(nickName, SecurityKey.MOBILE_NAME_KEY);
	}

//	// 手机号解密  数据那边出报表或者其他系统对接时，需要用到手机号，5.0系统不再对手机号做加密
//	@Deprecated
//	public static String getDecryptPhone(String phone) {
//		return AESUtil.decrypt(phone, SecurityKey.MOBILE_PHONE_KEY);
//	}

	// 通讯录名称解密
	public static String getDecryptNickName(String nickName) {
		return AESUtil.decrypt(nickName, SecurityKey.MOBILE_NAME_KEY);
	}
	// token解密
	public static String getEncryptToken(String tokenStr, String key) {
		return AESUtil.encrypt(tokenStr, key);
	}
	// token解密
	public static String getDecryptToken(String tokenStr, String key) {
		return AESUtil.decrypt(tokenStr, key);
	}

	public static String getPasswordFromToken(String token){
		//从token中解出密码
		String tokenStr = getDecryptToken(token, SecurityKey.MOBILE_PHONE_KEY);
		String[] items = tokenStr.split("&");
		if(items.length >= 3){
			return items[1];
		}else{
			return null;
		}
	}
	public static String getDeviceCodeFromToken(String token){
		//从token中解出密码
		String tokenStr = getDecryptToken(token, SecurityKey.MOBILE_PHONE_KEY);
		String[] items = tokenStr.split("&");
		if(items.length >= 3){
			return items[0];
		}else{
			return null;
		}
	}
	/**
	 * 转义http中的
	 *
	 * @param value
	 * @return
	 */
	private static String escapeSpecialChar(String value) {
		return value.replaceAll("%", "%25").replaceAll(" ", "%20").replaceAll("/", "%2F").replaceAll("\\?", "%3F").replaceAll("\\+", "%2B").replaceAll("#", "%23")
			.replaceAll("&", "%26").replaceAll("=", "%3D");
	}

//	public static String getPwd(String pwd, String key) {
//		try {
//			pwd = pwd.trim();
//			pwd = URLDecoder.decode(pwd, "UTF-8");
//			key = URLDecoder.decode(key, "UTF-8");
//			//解析密码
//			pwd = RSANewUtil.decrypt(pwd, key);
//			return pwd;
//		} catch (Exception e) {
//			log.error("getPwd error, pwd={}, key={}", pwd, key);
//			log.error("getPwd error, e={}", e);
//			throw new RuntimeException("decoder pwd error.", e);
//		}
//	}

	/**
	 * 比较两个版本号的大小,设定版本号为X.X.X...形式，X不超过3位
	 * 1:version1>version2
	 * 0:version1=version2
	 * -1:version1<version2
	 * 规则:缺省的用0补全 如X.X->X.X.0
	 * 按照.进行切片，从左至右逐位比较
	 *
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String version2) {
		int[] arr1 = {0, 0, 0};
		int[] arr2 = {0, 0, 0};
		String[] v1 = version1.split("\\.");
		String[] v2 = version2.split("\\.");
		for (int i = 0; i < v1.length; i++) {
			if (StringUtils.isNotEmpty(v1[i])) {
				arr1[i] = Integer.parseInt(v1[i]);
			}
		}
		for (int i = 0; i < v2.length; i++) {
			if (StringUtils.isNotEmpty(v2[i])) {
				arr2[i] = Integer.parseInt(v2[i]);
			}
		}

		return compare(arr1, arr2, 0, 3);//从0位开始比较，往后递归
	}

	/**
	 * 比较，前提是两个数组的长度要相等
	 *
	 * @param arr
	 * @param brr
	 * @param i
	 * @param n   比较的最大位数，如果输入的超出比较位数的数组，返回前面N位比较的结果
	 * @return
	 */
	private static int compare(int[] arr, int[] brr, int i, int n) {
		if (i < n) {
			if (arr[i] > brr[i]) {
				return 1;
			} else if (arr[i] < brr[i]) {
				return -1;
			} else {
				return compare(arr, brr, i + 1, n);
			}
		} else {
			return 0;
		}

	}

	/**
	 * 手机号加*处理
	 *
	 * @param phone
	 * @return
	 */
	public static String phone2Star(String phone) {
		if (StringUtils.isBlank(phone)) {
			return "";
		}
		if (phone.contains("-")) {
			//国际号处理
			phone = phone.substring(phone.lastIndexOf("-") + 1);
			return phone.replaceAll("(\\d{3})\\d{2}(\\d{3})", "$1**$2");
		} else {
			//国内号处理
			return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		}
	}

	/**
	 * 手机号加*处理
	 * 格式：+86-123***7890
	 */
	public static String phone2Star(String area,String phone) {
		if (StringUtils.isBlank(phone)) {
			return "";
		}
		StringBuilder phoneArea = new StringBuilder();
		phoneArea.append("+");
		phoneArea.append(area);
		phoneArea.append("-");
		phoneArea.append(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		return phoneArea.toString();
	}

	/**
	 * 邮箱加*处理
	 * 格式：e*****e@example.com
	 */
	public static String maskEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return "";
		}
		String maskedEmail = email.replaceAll("([a-zA-Z0-9_.-]{2})([a-zA-Z0-9_.-]+)(\\w)(@[a-zA-Z0-9]+\\.[a-zA-Z]+)", "$1*****$3$4");
		return maskedEmail;
	}

	/**
	 * 格式化版本号，不够两位补0，如：1.4.7=>010407
	 * @param appVersion
	 * @return
	 */
	public static String formatVersion(String appVersion){
		StringBuffer buffer = new StringBuffer();
		String[] array = appVersion.split("\\.");
		for(String s : array){
			buffer.append(String.format("%02d",Integer.valueOf(s)));
		}
//        System.out.println(buffer.toString());
		return buffer.toString();
	}

	public static boolean isPhoneNumber(String phoneNumber) {
		String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phoneNumber);
		boolean isMatch = m.matches();
		return isMatch;
	}

	/**
	 * 替换中文，英文小括号
	 *
	 * @param str
	 * @return
	 */
	public static String replaceBracket(String str) {
		if (StringUtils.isNotBlank(str)) {
			return str.replaceAll("\\(|\\)|\\（|\\）", "");
		} else {
			return "";
		}
	}

	/**
	 * 获取当天剩余的时间，单位：秒
	 */
	public static int getTodayRemainSeconds() {
		long current = System.currentTimeMillis();	//当前时间毫秒数
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		long tomorrowZero = calendar.getTimeInMillis();
		long remainSecond = (tomorrowZero - current) / 1000;
		return (int)remainSecond;
	}

	/**
	 * 转换多语言类型
	 *
	 * @param clientLang
	 * @return
	 */
	public static String transferLanguage(String clientLang) {
		if (MktConstants.LANGUAGE_SIMPLIFIED.equals(clientLang)) {
			return MktConstants.LANG_SC;
		} else if (MktConstants.LANGUAGE_TRADITIONAL.equals(clientLang)) {
			return MktConstants.LANG_TC;
		} else if (MktConstants.LANGUAGE_ENGLISH.equals(clientLang)) {
			return MktConstants.LANG_EN;
		} else {
			return MktConstants.LANG_SC;
		}
	}


	public static String getPwd(String pwd) {
		try {
			pwd = pwd.trim();
			pwd = URLDecoder.decode(pwd, "UTF-8");
			//解析密码
			pwd = RSANewUtil.decrypt(pwd);
			return pwd;
		} catch (Exception e) {
			log.error("getPwd error, e={}", e);
			throw new RuntimeException("decoder pwd error.", e);
		}
	}
}
