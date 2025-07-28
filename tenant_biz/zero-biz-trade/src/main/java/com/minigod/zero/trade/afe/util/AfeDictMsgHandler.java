package com.minigod.zero.trade.afe.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson2.JSON;
import com.github.houbb.opencc4j.util.ZhConverterUtil;

import cn.hutool.core.io.resource.ResourceUtil;

public class AfeDictMsgHandler {

	/**
	 * 简体 zh-hans
	 */
	public static final String LANG_ZH_HANS = "zh-hans";
	/**
	 * 繁体 zh-hant
	 */
	public static final String LANG_ZH_HANT = "zh-hant";
	/**
	 * 英文 en
	 */
	public static final String LANG_EN = "en";
	/**
	 * key：语言类型 --> 业务字段类型 --> 字典Code
	 */
	public static Map<String, Map<String, Map<String,String>>> dictMap = new HashMap<>();

	static {
		Document document = null;
		InputStream inputStream = ResourceUtil.getStream("hs/data_dictionary.xml");
		SAXReader reader = new SAXReader();
		try {
			reader.setEncoding("utf-8");
			document = reader.read(inputStream);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		allData(document);
	}
	static void allData(Document document) {
		// 获取文档的根元素
		Element root = document.getRootElement();
		// 遍历当前元素(在此是根元素)的子元素
		Map<String, Map<String,String>> scItemType = new HashMap<>(16);
		Map<String, Map<String,String>> tcItemType = new HashMap<>(16);
		Map<String, Map<String,String>> enItemType = new HashMap<>(16);

		for (Iterator<Element> i_pe = root.elementIterator("Dict"); i_pe.hasNext();) {
			Element e_pe = i_pe.next();
			// 获取当前元素属性的值
			String dictType = e_pe.attributeValue("name");

			Map<String, String> scItem = new HashMap<>(16);
			Map<String, String> tcItem = new HashMap<>(16);
			Map<String, String> engItem = new HashMap<>(16);
			for (Iterator<Element> i_pei = e_pe.elementIterator("Item"); i_pei.hasNext();) {
				Element e_pei = i_pei.next();
				String dictCode = e_pei.attributeValue("value");
				String scMsg = e_pei.attributeValue("SimplifiedChinese");
				String tcMsg = e_pei.attributeValue("TraditionalChinese");
				String enMsg = e_pei.attributeValue("English");

				// 简体为空，繁体不为空，使用繁体翻译成简体
				if(StringUtils.isEmpty(scMsg) && StringUtils.isNotEmpty(tcMsg)){
					scMsg = ZhConverterUtil.convertToSimple(tcMsg);
				}
				// 繁体为空，简体不为空，使用简体翻译成繁体
				if(StringUtils.isEmpty(tcMsg) && StringUtils.isNotEmpty(scMsg)){
					tcMsg = ZhConverterUtil.convertToTraditional(scMsg);
				}

				scItem.put(dictCode,scMsg);
				tcItem.put(dictCode,tcMsg);
				engItem.put(dictCode,enMsg);
			}
			scItemType.put(dictType, scItem);
			tcItemType.put(dictType, tcItem);
			enItemType.put(dictType, engItem);
		}
		dictMap.put(LANG_ZH_HANS, scItemType);
		dictMap.put(LANG_ZH_HANT, tcItemType);
		dictMap.put(LANG_EN, enItemType);
		System.out.println(JSON.toJSONString(dictMap));
	}

	/**
	 * 获取业务字典名称
	 * @param dictType 业务类型
	 * @param code 业务字典
	 * @param lang	语言
	 * @return
	 */
	public static String getName(String dictType,String code,String lang){
		Map<String, Map<String,String>> langMap = dictMap.get(lang);
		if(langMap != null){
			Map<String,String> dictTypeMap = langMap.get(dictType);
			if(dictTypeMap != null){
				return dictTypeMap.get(code);
			}
		}
		return code;
	}
}
