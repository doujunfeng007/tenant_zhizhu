package com.minigod.zero.data.Jsonfilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BigDecimalValueFilter implements ValueFilter {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static Set<String> targetFields = new HashSet<>(Arrays.asList(
		"fundTotalQuantity",
		"fundBusinessPrice",
		"fundTodayBalance",
		"fundTodayChangeBalance",
		"fundClosingPrice",
		"fundNetBalance"
	));;
	private static String DECIMAL_DEFAULT_FORMAT = "#,##0.00";
	private static String DECIMAL_FORMAT = "#,##0.0000";
	private static DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_DEFAULT_FORMAT);
	private static DecimalFormat decimalFormatFour = new DecimalFormat(DECIMAL_FORMAT);
	@Override
    public Object process(Object object,String name,Object value){
        if(null != value && value instanceof BigDecimal){
            BigDecimal bigDecimal = (BigDecimal) value;
			if (targetFields.contains(name)) {
				BigDecimal obj = bigDecimal.setScale(4, BigDecimal.ROUND_DOWN);
				//4位小数
				return decimalFormatFour.format(obj);
			}
			BigDecimal obj = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
			return decimalFormat.format(obj);
        }
		if (value instanceof Timestamp) {
			// 将 Date 转换为指定的字符串格式
			return dateFormat.format(value);
		}
		if (null == value && value instanceof BigDecimal) {
			// 将 Date 转换为指定的字符串格式
			return  BigDecimal.ZERO;
		}


		return value;
    }

	/*public static void main(String[] args) {
		String s = new String("17509.80000000");
		BigDecimal bigDecimal = new BigDecimal("17509.80000000");
		*//*String jsonString = JSON.toJSONString(bigDecimal, new BigDecimalValueFilter());
		System.out.println(jsonString);*//*
		BigDecimal obj = bigDecimal.setScale(4, BigDecimal.ROUND_DOWN);
		System.out.println(decimalFormatFour.format(obj));
		// 使用 decimalFormatFour 格式化
	}*/
}
