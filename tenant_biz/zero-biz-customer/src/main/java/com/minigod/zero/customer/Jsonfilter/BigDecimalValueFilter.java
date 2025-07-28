package com.minigod.zero.customer.Jsonfilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class BigDecimalValueFilter implements ValueFilter {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String DECIMAL_DEFAULT_FORMAT = "#,##0.00";
	private static DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_DEFAULT_FORMAT);
	@Override
    public Object process(Object object,String name,Object value){
        if(null != value && value instanceof BigDecimal){
            BigDecimal bigDecimal = (BigDecimal) value;
			//String bigDecimal1 = .toString();
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
		String s = new String("123.456");
		BigDecimal bigDecimal = new BigDecimal("123.456");
		String jsonString = JSON.toJSONString(bigDecimal, new BigDecimalValueFilter());
		System.out.println(jsonString);
	}*/
}
