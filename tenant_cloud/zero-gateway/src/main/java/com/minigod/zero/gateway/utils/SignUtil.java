package com.minigod.zero.gateway.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUtil {

    public static boolean verifySign(String data, String timestamp, String urlSign) {
		try {
			data = URLDecoder.decode(data, "UTF-8");
			String digest = sign(data, timestamp);
			return digest.equals(urlSign);
		} catch (UnsupportedEncodingException e) {
			return false;
		}
    }

    public static String sign(String data, String timestamp) {
		data = handleData(data);
		return DigestUtils.sha256Hex(timestamp + data);
    }

	private static String handleData(String data) {
		if (data == null) {
			return "";
		}
		String regEx = "[^0-9A-Za-z\\u4e00-\\u9fa5]"; // 取字母/数字/中文
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(data);
		char[] chr = matcher.replaceAll("").toCharArray();
		Arrays.sort(chr);
		return String.valueOf(chr);
	}

    /*public static void main(String[] args) throws UnsupportedEncodingException {
		//String data = "{\"pwd\":\"4eTguGlXZjrpRo7hVJNRFw%3D%3D\",\"cert\":[{\"certCode\":\"13798293481\",\"certType\":\"身份证\"}],\"eventId\":-1,\"sessionId\":\"\",\"nickname\":\"\",\"userIcon\":\"\",\"gender\":\"\",\"deviceInfo\":{\"osVersion\":\"REL\",\"deviceName\":\"\",\"appVersion\":\"0.0.1\",\"deviceType\":1,\"deviceCode\":\"28500313-c1ab-448f-b614-ff356dc6542c\",\"deviceModel\":\"H60-L01\",\"osType\":0,\"channel\":\"official\",\"openCode\":\"ec1dc3ea7a54e5df189269a0f52d304f\"},\"key\":\"H4FLlwsHt17aL9n0qeXZfy3itAwclpqzFNX2TG37GqGhQJ%2FlOggkM8SCunL7V%2BfgzL6GKUI4%0Amo8uK4ZUJ5pKLqCsXIcMVOTZivJySTBX87NnFnluk3q5YofxzYChpF%2BdD4FqbPHpmsrFyvEu%0Ak10EHN%2BFqJfAPbp454LPT5mn%2B4pG0r3c8AudacHtN40%3D\"}";
		String data = "area=86&phone=13824386258&grant_type=saas_client&openCode=1234567&captchaKey=7a6f1f86e6ba43d0bb1931a4cd97c4b5&captchaCode=920664&tenantId=000001&custId=666666";
		String timestamp = new Date().getTime() + "";
		System.out.println(timestamp);
		String sign = sign(data, timestamp);
		System.out.println(sign);
		System.out.println(verifySign(data, timestamp, sign));
    }*/

}
