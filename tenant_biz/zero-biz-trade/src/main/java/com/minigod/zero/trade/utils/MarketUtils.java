package com.minigod.zero.trade.utils;

import com.minigod.zero.core.tool.enums.EMarket;
import org.apache.commons.lang.StringUtils;

public class MarketUtils {
    /**
     * 根据股票代码转assetId
     */
    public static String translateHkAssetId(String stockCode) {
        return StringUtils.leftPad(stockCode, 5, "0") + ".HK" ;
    }

	public static String extractStockCode(String assetId) {
		// 分割字符串，获取点前的部分
		String[] parts = assetId.split("\\.");
		String numberPart = parts[0];
		Integer number = Integer.parseInt(numberPart);
		// 转换为整数，自动去掉前导零
		return number.toString();
	}

	public static String translateUsStockCode(String assetId) {
		// 分割字符串，获取点前的部分
		String[] parts = assetId.split("\\.");
		String numberPart = parts[0];
		return numberPart;
	}

    /**
     * 根据股票代码转assetId
     */
    public static String translateUsAssetId(String stockCode) {
        return stockCode + ".US" ;
    }

    public static String getSymbol(String assetId) {
        return assetId.split("\\.")[0];
    }

	public static Long getOptionGroup(String assetId){
		Long optionGroup = 1L;
		if(assetId == null){
			return optionGroup;
		}
		String market = assetId;
		if(assetId.indexOf(".") != -1){
			market = assetId.substring(assetId.length() - 2);
		}
		if(EMarket.SH.toString().equals(market)){
			return 5L;
		}
		if(EMarket.SZ.toString().equals(market)){
			return 5L;
		}
		if(EMarket.HK.toString().equals(market)){
			return 3L;
		}
		if(EMarket.US.toString().equals(market)){
			return 4L;
		}
		return optionGroup;
	}
}
