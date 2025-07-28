package com.minigod.zero.trade.hs.constants;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataFormatUtil {

	/**
	 * 股票代码补全，港股5位代码，A股6位代码，不足补0
	 */
	public static String stockCodeFormat(String stockCode,String hsExchangeType){
		if(!StringUtils.isEmpty(hsExchangeType) && !StringUtils.isEmpty(stockCode)){
			try{
				if(hsExchangeType.equals(HsConstants.HSExchageType.HK.getCode()) && stockCode.length() < 5){
					int code = Integer.parseInt(stockCode);
					return String.format("%05d", code);
				}else if((hsExchangeType.equals(HsConstants.HSExchageType.SH.getCode())
					|| hsExchangeType.equals(HsConstants.HSExchageType.SZ.getCode())) && stockCode.length() < 6){
					int code = Integer.parseInt(stockCode);
					return String.format("%06d", code);
				}
			}catch (Exception e){
				//捕获转换异常，什么也不做
			}
		}
		return stockCode;
	}

	public static String hsTimeFormat(int time){
		return String.format("%06d",time);
	}

	public static String stkCodeToAssetId(String StockCode,String exchangeType){
		String assetId;
		if(StringUtils.isNotEmpty(StockCode) && org.apache.commons.lang.StringUtils.isNotEmpty(exchangeType)){
			if (exchangeType.equals(HsConstants.HSExchageType.HK.getCode())) {
				assetId = String.format("%05d", Integer.valueOf(StockCode)) + "." + EExchangeType.K.getMarketCode();
			} else if (exchangeType.equals(HsConstants.HSExchageType.SH.getCode()) || exchangeType.equals(HsConstants.HSExchageType.SHB.getCode())) {
				assetId = String.format("%06d", Integer.valueOf(StockCode)) + "." + EExchangeType.D.getMarketCode();
			}else if (exchangeType.equals(HsConstants.HSExchageType.SZ.getCode()) || exchangeType.equals(HsConstants.HSExchageType.SZB.getCode())) {
				assetId = String.format("%06d", Integer.valueOf(StockCode)) + "." + EExchangeType.H.getMarketCode();
			} else if (exchangeType.equals(HsConstants.HSExchageType.US.getCode())) {
				assetId = StockCode + "." + EExchangeType.P.getMarketCode();
			}else{
				assetId = StockCode+"."+exchangeType;
			}
			return assetId;
		}else{
			return  null;
		}
	}

	public static String paddLeftZeros(String stock_code,int length){
				return String.format("%05d", stock_code);
	}

    public static String changeToYYYY_MM_DD(String yyyymmdd){
        return new StringBuilder().append(yyyymmdd.substring(0,4)).append("-").append(yyyymmdd.substring(4,6))
                .append("-").append(yyyymmdd.substring(6)).toString();
    }

	   public static String  trimLeftZeros(String string){
		   if(!StringUtils.isEmpty(string)){
			   return string.replaceFirst("^[0]*", "");
		   }
		   return string;
	   }

	public static String formatDate(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Date formatStrToDate(String source,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

    public static int getWeekOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    public static int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

	/**
	 * int时分秒补全6位
	 * 921->000921, 1315->001315, 160600->160600
	 * @param hsTime
	 * @return HHmmss
	 */
	public static String intTimeCompletion(String hsTime) {
		if(org.apache.commons.lang.StringUtils.isNotEmpty(hsTime)){
			return String.format("%06d", Integer.valueOf(hsTime));
		}else {
			return hsTime;
		}
	}

	/**
	 * 转换整型int为时分秒
	 * 921->00:09:21, 1315->00:13:15, 160600->16:06:00
	 * @param hsTime
	 * @return HH:mm:ss
	 */
	public static String int2Time(String hsTime) {
		if(org.apache.commons.lang.StringUtils.isNotEmpty(hsTime)){
			StringBuffer buffer = new StringBuffer(String.format("%06d", Integer.valueOf(hsTime)));
			buffer.insert(2,":").insert(5,":");
			return buffer.toString();
		}else {
			return hsTime;
		}
	}
}
