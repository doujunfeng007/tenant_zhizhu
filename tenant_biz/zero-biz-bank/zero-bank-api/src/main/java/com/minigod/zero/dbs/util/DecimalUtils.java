package com.minigod.zero.dbs.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @ClassName: DecimalUtils
 * @Description:
 * @Author chenyu
 * @Date 2023/3/29
 * @Version 1.0
 */
public class DecimalUtils {

    /** 精度 */
    static int DECIMAL_DEFAULT_SCALE = 2;

    /** 金额格式 */
    static String DECIMAL_DEFAULT_FORMAT = "#,##0.00";

    public static BigDecimal toDecimal(Double d) {
        return toDecimal(d, 2);
    }

    public static BigDecimal toDecimal(Double d, Integer scale) {
        return BigDecimal.valueOf(d).setScale(scale, RoundingMode.HALF_UP);
    }

    public static Double toDouble(BigDecimal d) {
        return toDouble(d, 2);
    }

    public static Double toDouble(BigDecimal d, Integer scale) {
        return d.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

	/**
	 * 金额格式化  三位带逗号
	 * @param decimal
	 * @return
	 */
    public static String formatDecimal(BigDecimal decimal){
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_DEFAULT_FORMAT);
        if(decimal != null){
            return decimalFormat.format(decimal);
        }
        return null;
    }

    public static String formatPlainDecimal(BigDecimal decimal){
        return formatPlainDecimal(decimal, DECIMAL_DEFAULT_SCALE, RoundingMode.DOWN);
    }

    public static String formatPlainDecimal(BigDecimal decimal, int newScale){
        return formatPlainDecimal(decimal, newScale, RoundingMode.DOWN);
    }

    public static String formatPlainDecimal(BigDecimal decimal, int newScale, RoundingMode roundingMode){
        if(decimal != null){
            return decimal.setScale(newScale, roundingMode).toPlainString();
        }
        return null;
    }
}
