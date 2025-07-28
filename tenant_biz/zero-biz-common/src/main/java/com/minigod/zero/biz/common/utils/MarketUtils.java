package com.minigod.zero.biz.common.utils;

import org.apache.commons.lang3.StringUtils;

public class MarketUtils {
    /**
     * 根据股票代码转assetId
     */
    public static String translateHkAssetId(String stockCode) {
        return StringUtils.leftPad(stockCode, 5, "0") + ".HK" ;
    }

    /**
     * 根据股票代码转assetId
     */
    public static String translateUsAssetId(String stockCode) {
        return stockCode + ".US" ;
    }

    public static String getSymbol(String assetId) {
    	if(assetId.contains(".")){
			return assetId.split("\\.")[0];
		}
    	return assetId;
    }
}
