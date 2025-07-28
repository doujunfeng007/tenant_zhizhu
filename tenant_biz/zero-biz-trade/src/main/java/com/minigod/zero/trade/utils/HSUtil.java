package com.minigod.zero.trade.utils;

import org.apache.commons.lang3.StringUtils;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.enums.EMarket;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.constants.HsDictMsgHandler;

/**
 * @author:yanghu.luo
 * @create: 2023-05-11 19:46
 * @Description: 恒生柜台工具类
 */
public class HSUtil {

	/**
	 * 是否是A股，美股，港股以外的市场
	 */
	public static boolean isOtherExchage(String hsExchageType){
		if(HsConstants.HSExchageType.SH.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.SHB.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.SZ.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.SZB.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.US.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.HK.getCode().equals(hsExchageType)
		) { return true;}
		return false;
	}

	/**
	 * 根据资产ID，获取恒生交易市场标识
	 */
	public static String getMarketToHS(String assetId){
		if(assetId == null || assetId.indexOf(".") == -1){
			return StringUtils.EMPTY;
		}
		String market = assetId.substring(assetId.length() - 2);
		if(EMarket.SH.toString().equals(market)){
			return HsConstants.HSExchageType.SH.getCode();
		}
		if(EMarket.SZ.toString().equals(market)){
			return HsConstants.HSExchageType.SZ.getCode();
		}
		if(EMarket.HK.toString().equals(market)){
			return HsConstants.HSExchageType.HK.getCode();
		}
		if(EMarket.US.toString().equals(market)){
			return HsConstants.HSExchageType.US.getCode();
		}
		throw new ZeroException("交易市场不对" + market);
	}

	public static String getHSMarket(String exchangeType){
		if(exchangeType == null){
			return StringUtils.EMPTY;
		}
		if(HsConstants.HSExchageType.SH.getCode().equals(exchangeType)){
			return EMarket.SH.toString();
		}
		if(HsConstants.HSExchageType.SZ.getCode().equals(exchangeType)){
			return EMarket.SZ.toString();
		}
		if(HsConstants.HSExchageType.HK.getCode().equals(exchangeType)){
			return EMarket.HK.toString();
		}
		if(HsConstants.HSExchageType.US.getCode().equals(exchangeType)){
			return EMarket.US.toString();
		}
		throw new ZeroException("交易市场不对" + exchangeType);
	}
	/**
	 * 根据恒生的币种类型，返回字符类型的币种类型
	 */
	public static String getMoneyTypeToHS(String moneyType){
		if(moneyType == null){
			return StringUtils.EMPTY;
		}
		if(HsConstants.HsMoneyType.CNY.getMoneyType().equals(moneyType)){
			return HsConstants.HsMoneyType.CNY.toString();
		}
		if(HsConstants.HsMoneyType.USD.getMoneyType().equals(moneyType)){
			return HsConstants.HsMoneyType.USD.toString();
		}
		if(HsConstants.HsMoneyType.HKD.getMoneyType().equals(moneyType)){
			return HsConstants.HsMoneyType.HKD.toString();
		}
		return moneyType;
	}
	/**
	 * 根据资产ID，获取证券代码
	 */
	public static String getStockCode(String assetId){
		if(assetId == null || assetId.indexOf(".") == -1){
			return StringUtils.EMPTY;
		}
		String code = assetId.substring(0,assetId.length() - 3);
		code = code.replaceAll("^(0+)", "");
		return code;
	}

	/**
	 * 获取证券名称
	 * 简体 zh-hans，繁体 zh-hant，英文 en
	 */
	public static String getStockName(StkInfo stkInfo, String lang, String srcName){
		if(StringUtils.isEmpty(lang) || stkInfo == null){
			return srcName;
		}
		if(lang.equals(HsDictMsgHandler.LANG_ZH_HANS) && StringUtils.isNotEmpty(stkInfo.getStkName())){
			return stkInfo.getStkName();
		}
		if(lang.equals(HsDictMsgHandler.LANG_ZH_HANT) && StringUtils.isNotEmpty(stkInfo.getTraditionalName())){
			return stkInfo.getTraditionalName();
		}
		if(lang.equals(HsDictMsgHandler.LANG_EN) && StringUtils.isNotEmpty(stkInfo.getEngName())){
			return stkInfo.getEngName();
		}

		// 简体为空，繁体不为空，使用繁体翻译成简体
		if(lang.equals(HsDictMsgHandler.LANG_ZH_HANS) && StringUtils.isEmpty(stkInfo.getStkName()) && StringUtils.isNotEmpty(stkInfo.getTraditionalName())){
			return ZhConverterUtil.convertToSimple(stkInfo.getTraditionalName());
		}
		// 繁体为空，简体不为空，使用简体翻译成繁体
		if(lang.equals(HsDictMsgHandler.LANG_ZH_HANT) && StringUtils.isEmpty(stkInfo.getTraditionalName()) && StringUtils.isNotEmpty(stkInfo.getStkName())){
			return ZhConverterUtil.convertToTraditional(stkInfo.getStkName());
		}

		return srcName;
	}
}
