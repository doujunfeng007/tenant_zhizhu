package com.minigod.common.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @Author: guangjie.liao
 * @Date: 2024/4/14 15:04
 * @Description:
 */
public class NumberUtil {
	public NumberUtil() {
	}

	public static String getRandomNumber(int digCount) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(digCount);

		for(int i = 0; i < digCount; ++i) {
			sb.append((char)(48 + rnd.nextInt(10)));
		}

		return sb.toString();
	}

	public static String getStringRandom(int length) {
		String val = "";
		Random random = new Random();

		for(int i = 0; i < length; ++i) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			if ("char".equalsIgnoreCase(charOrNum)) {
				val = val + (char)(random.nextInt(26) + 97);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val = val + String.valueOf(random.nextInt(10));
			}
		}

		return val;
	}

	public static boolean isNumeric(String str) {
		int i = str.length();

		char chr;
		do {
			--i;
			if (i < 0) {
				return true;
			}

			chr = str.charAt(i);
		} while(chr >= '0' && chr <= '9');

		return false;
	}

	public static int getStrLength(String s) {
		if (s == null) {
			return 0;
		} else {
			char[] c = s.toCharArray();
			int len = 0;

			for(int i = 0; i < c.length; ++i) {
				++len;
				if (isCjkCharacter(c[i])) {
					++len;
				}
			}

			return len;
		}
	}

	public static boolean isCjkCharacter(char c) {
		Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(c);
		return localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || localUnicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION || localUnicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || localUnicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
	}

	public static double div(double value1, double value2, int scale) {
		if (scale < 0) {
			scale = 0;
		}

		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.divide(b2, scale, 4).doubleValue();
	}

	public static double sub(double value1, double value2) {
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		return b1.subtract(b2).doubleValue();
	}
}
