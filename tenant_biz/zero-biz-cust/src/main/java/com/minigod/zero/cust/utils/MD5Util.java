package com.minigod.zero.cust.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.common.utils.MD5Util
 * @Date: 2023年02月16日 14:05
 * @Description:
 */
@Slf4j
public class MD5Util {

	private static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	public MD5Util() {
	}

	public static final String md5(String str) {
		try {
			if (str != null && str.length() != 0) {
				int len = str.length();
				str = str.replaceAll("\\s*", "");
				if (str.length() == 0) {
					str = leftPad(str, len, '0');
				}

				byte[] strTemp = str.getBytes("utf-8");
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(strTemp);
				byte[] md = mdTemp.digest();
				int j = md.length;
				char[] s = new char[j * 2];
				int k = 0;

				for(int i = 0; i < j; ++i) {
					byte byte0 = md[i];
					s[k++] = hexDigits[byte0 >>> 4 & 15];
					s[k++] = hexDigits[byte0 & 15];
				}

				return new String(s);
			} else {
				throw new RuntimeException("encrypt parameter error.");
			}
		} catch (Exception var10) {
			log.error("encrypt parameter error",var10);
		}
		return null;
	}

	public static final String md5(String str, String charset) {
		try {
			if (str != null && str.length() != 0) {
				int len = str.length();
				str = str.replaceAll("\\s*", "");
				if (str.length() == 0) {
					str = leftPad(str, len, '0');
				}

				byte[] strTemp = str.getBytes(charset);
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(strTemp);
				byte[] md = mdTemp.digest();
				int j = md.length;
				char[] temp = new char[j * 2];
				int k = 0;

				for(int i = 0; i < j; ++i) {
					byte byte0 = md[i];
					temp[k++] = hexDigits[byte0 >>> 4 & 15];
					temp[k++] = hexDigits[byte0 & 15];
				}

				return new String(temp);
			} else {
				throw new RuntimeException("encrypt parameter error.");
			}
		} catch (Exception var11) {
			log.error("encrypt parameter error",var11);
		}
		return null;
	}

	private static String leftPad(String str, int length, char ch) {
		char[] chs = new char[length];
		Arrays.fill(chs, ch);
		char[] src = str.toCharArray();
		System.arraycopy(src, 0, chs, length - src.length, src.length);
		return new String(chs);
	}

	public static String md5salt(String pwd, Object salt) {
		pwd = SecurityKey.getIdKey() + pwd + salt;
		return md5(pwd);
	}

}
