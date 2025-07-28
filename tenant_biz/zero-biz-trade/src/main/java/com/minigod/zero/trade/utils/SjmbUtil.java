package com.minigod.zero.trade.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:yanghu.luo
 * @create: 2023-05-11 19:46
 * @Description: 恒生柜台工具类
 */
public class SjmbUtil {


	/**
     * 根据数字字符串转换成map
	 * @param numberStr
     * @return
	 */
	public static Map<String, Object> getMapParamsByStr(String numberStr) {

		Map<String,Object> result =new HashMap<>();

		if(StringUtils.isNotEmpty(numberStr)){
			String[] parts = numberStr.split("\\.");
			// 将字符串部分转换为整数
			int part1 = Integer.parseInt(parts[0]);
			int part2=0;
			if(parts.length >1){
				part2 = Integer.parseInt(parts[1]);
			}
			result.put("value", part1);
			result.put("offset", part2);
			return result;

		}else{
			result.put("value",0);
			result.put("offset",0 );
			return result;
		}
	}
}
