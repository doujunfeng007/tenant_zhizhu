package com.minigod.zero.biz.common.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class NewsUtil {
    // 字符串相似度缓存
	public final static Cache<Long, Double> stringSimilarDegreeCache = CacheBuilder.newBuilder()
		       .maximumSize(999999)
		       .expireAfterWrite(12, TimeUnit.HOURS)
		       .build();

    private static double getSimHash(final String s1,final String s2){
//    	MySimHash hash1 = new MySimHash(s1);
//		MySimHash hash2 = new MySimHash(s2);
//		return hash1.getSemblance(hash2);
    	return SimilarDegree(s1, s2);
    }

	public static double similarDegreeCache(final String s1, final String s2) {
		if (s1 == null || s2 == null) {
			return getSimHash(s1, s2);
		}
		// 使用2个hashCode作key
		long key1 = s1.hashCode() << 32;
		long key2 = s2.hashCode() & 0xFFFFFFFF;
		final long key = key1 | key2;

		Callable<? extends Double> loader = new Callable<Double>() {
			@Override
			public Double call() throws Exception {
//				L.info("计算相似度："+key+"，"+s1+"，"+s2);
				return getSimHash(s1, s2);
			}
		};

		double degree;
		try {
			degree = stringSimilarDegreeCache.get(key, loader);
//			L.info("key="+key+", degree="+degree+", size="+stringSimilarDegreeCache.size());
		} catch (ExecutionException e) {
			degree = getSimHash(s1, s2);
		}
		return degree;
	}

	/**
	 * 相似度比较
	 *
	 * @param strA
	 * @param strB
	 * @return
	 */
	public static double SimilarDegree(String strA, String strB) {

		String newStrA = removeSign(strA);

		String newStrB = removeSign(strB);

		int temp = Math.max(newStrA.length(), newStrB.length());

		int temp2 = longestCommonSubstring(newStrA, newStrB).length();

		return temp2 * 1.0 / temp;

	}

	private static String removeSign(String str) {

		StringBuffer sb = new StringBuffer();

		for (char item : str.toCharArray())

			if (charReg(item)) {

				// System.out.println("--"+item);

				sb.append(item);

			}

		return sb.toString();

	}

	private static boolean charReg(char charValue) {

		return (charValue >= 0x4E00 && charValue <= 0X9FA5)

			|| (charValue >= 'a' && charValue <= 'z')

			|| (charValue >= 'A' && charValue <= 'Z')

			|| (charValue >= '0' && charValue <= '9');

	}

	private static String longestCommonSubstring(String strA, String strB) {

		char[] chars_strA = strA.toCharArray();

		char[] chars_strB = strB.toCharArray();

		int m = chars_strA.length;

		int n = chars_strB.length;

		int[][] matrix = new int[m + 1][n + 1];

		for (int i = 1; i <= m; i++) {

			for (int j = 1; j <= n; j++) {

				if (chars_strA[i - 1] == chars_strB[j - 1])

					matrix[i][j] = matrix[i - 1][j - 1] + 1;

				else

					matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);

			}

		}

		char[] result = new char[matrix[m][n]];

		int currentIndex = result.length - 1;

		while (matrix[m][n] != 0) {

			if (matrix[m][n] == matrix[m][n - 1])

				n--;

			else if (matrix[m][n] == matrix[m - 1][n])

				m--;

			else {

				result[currentIndex] = chars_strA[m - 1];

				currentIndex--;

				n--;

				m--;

			}
		}

		return new String(result);

	}
}
