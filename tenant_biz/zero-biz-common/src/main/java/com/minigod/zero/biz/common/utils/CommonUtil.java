package com.minigod.zero.biz.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 通用工具类
 *
 * @author Chill
 */
public class CommonUtil {

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 *
	 * @param s 需要得到长度的字符串
	 * @return int 得到的字符串长度
	 */
	public static int getStrLength(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			//对于汉字、日语、韩语 一个相当于两个字符 +1
			if (isCjkCharacter(c[i])) {
				len++;
			}
		}
		return len;
	}

	//判断是否为 汉字、日语、韩语
	public static boolean isCjkCharacter(char c) {
		Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(c);
		return (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (localUnicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION) || (localUnicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (localUnicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
	}

	public static <T> List<List<T>> splitList(List<T> list, int len) {
		if (list == null || list.isEmpty() || len < 1) {
			return Collections.emptyList();
		}

		List<List<T>> result = new ArrayList<>();
		int size = list.size();
		int count = (size + len - 1) / len;

		for (int i = 0; i < count; i++) {
			List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}

		return result;
	}

	public static String getRequestJson(Map<String, String> requestMap) {
		Map<String, Object> finalMap = new HashMap<>();
		finalMap.put("params", requestMap);
		return JSONObject.toJSONString(finalMap);
	}

	public static String httpPost(String url, String param) {
		try {
			return HttpClientUtils.postJson(url, param, StandardCharsets.UTF_8, true);
		} catch (Exception e) {
			return JSON.toJSONString(R.fail(ResultCode.H5_DISPLAY_ERROR.getCode(), ResultCode.H5_DISPLAY_ERROR.getMessage()));
		}

	}

	public static boolean checkCode(String jsonResult) {
		if (StringUtils.isEmpty(jsonResult)) {
			return false;
		}
		JSONObject jsonObject = JSONObject.parseObject(jsonResult);
		String code = null == jsonObject.get("code") ? null : jsonObject.get("code").toString();
		String errorId = (String) jsonObject.get("errorId");
		return "0".equals(code) || "EM0512000000".equals(errorId);
	}

	public static String returnResult(String jsonResult) {
		JSONObject jsonObject = JSONObject.parseObject(jsonResult);
		String result = jsonObject.get("result").toString();
		return result;
	}

	public static String returnMessage(String jsonResult) {
		JSONObject jsonObject = JSONObject.parseObject(jsonResult);
		String code = null == jsonObject.get("code") ? null : jsonObject.get("code").toString();
		String message = CommonEnums.IpoTradeResp.getIpoTradeRespDesc(code);
		if (null == message) {
			message = (String) jsonObject.get("msg");
			if (StringUtils.isEmpty(message)) {
				message = (String) jsonObject.get("message");
			}
		}
		return message;
	}
}
