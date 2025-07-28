package com.minigod.zero.system.cache;

import com.minigod.zero.core.tool.utils.WebUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/30 17:54
 * @description：
 */
public class LanguageUtils {
	public static HashMap<String,  String> localeMap;

	public static String DEFAULT_LANGUAGE = "zh-CN";

	static{
		localeMap = new HashMap<>();
		//简体
		localeMap.put("zh_CN","zh-CN");
		localeMap.put("zh-CN","zh-CN");
		localeMap.put("zh_cn", "zh-CN");
		localeMap.put("zh-hans", "zh-CN");
		//繁体
		localeMap.put("zh-hant", "zh-HK");
		localeMap.put("zh_HK", "zh-HK");
		localeMap.put("zh-HK", "zh-HK");
		localeMap.put("zh_hk", "zh-HK");
		//英文
		localeMap.put("en", "en-US");
		localeMap.put("en_US", "en-US");
		localeMap.put("en-US", "en-US");
		localeMap.put("en_us", "en-US");
	}

	public static String getLanguage(){
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null){
			return DEFAULT_LANGUAGE;
		}
		String language = request.getHeader("Accept-Language");
		if (!StringUtils.isEmpty(language)){
			return localeMap.get(language);
		}
		return DEFAULT_LANGUAGE;
	}

}
