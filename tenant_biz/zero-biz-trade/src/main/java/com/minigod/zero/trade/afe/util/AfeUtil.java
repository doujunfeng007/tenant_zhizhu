package com.minigod.zero.trade.afe.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.enums.EMarket;
import com.minigod.zero.trade.hs.constants.HsConstants;

/**
 * @author:yanghu.luo
 * @create: 2023-05-11 19:46
 * @Description: 恒生柜台工具类
 */
public class AfeUtil {

	// 业务类型
	private static Map<String,String> businessFlagMap = new HashMap<>();;

	/**
	 * 旧行情市场和恒生市场转换
	 */
	private static Map<String,String> marketMap = new HashMap<>();

	/**
	 * 行情币种恒生币种转换
	 */
	private static Map<String,String> moneyTypeMap = new HashMap<>();
	/**
	 * 币种名称
	 */
	private static Map<String,String> moneyTypeDesc = new HashMap<>();
	/**
	 * 交易方向名称
	 */
	private static Map<String,String> entrustBsDesc = new HashMap<>();
	static {
		// secFund：证券存取；newStock：新股认购；secTransact：证券交易；com：公司行动；other：其他；accountFund：资金存取
		businessFlagMap.put("secFund","3001,3002,3101,3102");
		businessFlagMap.put("newStock","2914,2907,2908,2909,2910,2911,2912,2913,4016");
		businessFlagMap.put("secTransact","4001,4002");
		businessFlagMap.put("com","2055,2056,2704,2705,2706,3006,3007,4013,4014,4015,4017,2707,2708,4403,2709,2942,2974,2975,3019,3020,3021,3022,3023,3024");
		businessFlagMap.put("other","3010,3011,3012,3013,3014,2113,2114,2009,2010,2011,2013,2014,2017,2018,2020,2031,2032,2035,2036,2101,2102,2301,2302,2303,2304,2461,2462,2701,2702,2703,3003,3005,3105,3106,3900,3901,4019,4032,4034,4036,4037,3205,3206");
		businessFlagMap.put("accountFund","2952,2001,2002,2007,2008,2403,2404,2980,2981");

		marketMap.put("hk","K");
		marketMap.put("am","P");
		marketMap.put("ny","P");
		marketMap.put("oq","P");
		marketMap.put("ix","P");
		marketMap.put("us","P");
		marketMap.put("sz","v");
		marketMap.put("sh","t");

		moneyTypeMap.put("CNY","0");
		moneyTypeMap.put("HKD","2");
		moneyTypeMap.put("USD","1");

		moneyTypeDesc.put("0-zh-hans","人民币");
		moneyTypeDesc.put("0-zh-hant","人民幣");
		moneyTypeDesc.put("0-en","CNH");
		moneyTypeDesc.put("1-zh-hans","美元");
		moneyTypeDesc.put("1-zh-hant","美元");
		moneyTypeDesc.put("1-en","USD");
		moneyTypeDesc.put("2-zh-hans","港币");
		moneyTypeDesc.put("2-zh-hant","港幣");
		moneyTypeDesc.put("2-en","HKD");

		entrustBsDesc.put("B-zh-hans","买入");
		entrustBsDesc.put("B-zh-hant","買入");
		entrustBsDesc.put("B-en","buy ");
		entrustBsDesc.put("S-zh-hans","卖出");
		entrustBsDesc.put("S-zh-hant","賣出");
		entrustBsDesc.put("S-en","sell ");

	}
	public static String getBusinessFlag(String businessFlag){
		return businessFlagMap.get(businessFlag);
	}

	public static String getOldToHsmarket(String market){
		return marketMap.get(market);
	}

	public static String getMoneyTypeMap(String moneyType){
		return moneyTypeMap.get(moneyType);
	}

	/**
	 * 是否是A股，美股，港股以外的市场
	 */
	public static boolean isOtherExchage(String hsExchageType){
		if(HsConstants.HSExchageType.HK.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.US.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.SH.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.SZ.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.SHB.getCode().equals(hsExchageType)
			|| HsConstants.HSExchageType.SZB.getCode().equals(hsExchageType)
		) { return false;}
		return true;
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
	 * 根据恒生的币种类型，和语言，返回币种名称
	 */
	public static String getMoneyTypeDesc(String moneyType, String lang){
		if(StringUtils.isBlank(lang)){
			lang = "zh-hans";
		}
		return moneyTypeDesc.get(new StringBuilder().append(moneyType).append("-").append(lang).toString());
	}

	/**
	 * 根据恒生的币种类型，和语言，返回币种名称
	 */
	public static String getEntrustBsDesc(String entrustBs, String lang){
		if(StringUtils.isBlank(lang)){
			lang = "zh-hans";
		}
		return entrustBsDesc.get(new StringBuilder().append(entrustBs).append("-").append(lang).toString());
	}

	/**
	 * 按市场返回币种
	 */
	public static String getHSMoneyType(String assetId){
		String market = assetId;
		if(assetId.indexOf(".") != -1){
			market = assetId.substring(assetId.length() - 2);
		}
		if(EMarket.SH.toString().equals(market)){
			return HsConstants.HsMoneyType.CNY.getMoneyType();
		}
		if(EMarket.SZ.toString().equals(market)){
			return HsConstants.HsMoneyType.CNY.getMoneyType();
		}
		if(EMarket.HK.toString().equals(market)){
			return HsConstants.HsMoneyType.HKD.getMoneyType();
		}
		if(EMarket.US.toString().equals(market)){
			return HsConstants.HsMoneyType.USD.getMoneyType();
		}
		return HsConstants.HsMoneyType.HKD.getMoneyType();
	}

	/**
	 * 根据资产ID，获取恒生交易市场标识
	 */
	public static String getMarketToHS(String assetId){
		if(assetId == null){
			return StringUtils.EMPTY;
		}
		String market = assetId;
		if(assetId.indexOf(".") != -1){
			market = assetId.substring(assetId.length() - 2);
		}
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
		return market;
	}

	/**
	 * 恒生市场转换标准市场
	 * @param exchangeType
	 * @return
	 */
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
		return exchangeType;
	}

	/**
	 * 恒生市场转换三个市场
	 */
	public static String getHSMarketAHKUS(String exchangeType){
		if(exchangeType == null){
			return StringUtils.EMPTY;
		}
		if(HsConstants.HSExchageType.SH.getCode().equals(exchangeType)){
			return EMarket.ML.toString();
		}
		if(HsConstants.HSExchageType.SZ.getCode().equals(exchangeType)){
			return EMarket.ML.toString();
		}
		if(HsConstants.HSExchageType.HK.getCode().equals(exchangeType)){
			return EMarket.HK.toString();
		}
		if(HsConstants.HSExchageType.US.getCode().equals(exchangeType)){
			return EMarket.US.toString();
		}
		return exchangeType;
	}

	/**
	 * 交易市场转换， 0-A股 1-美股 2-港股 3-其他转换为恒生市场代码
	 * @param marketType
	 * @return
	 */
	public static String getMarket0123ToHS(String marketType){
		if(marketType == null){
			return StringUtils.EMPTY;
		}
		String exchangeType = marketType;
		if(marketType.equals("0")){
			exchangeType = HsConstants.HSExchageType.SH.getCode();
		}
		if(marketType.equals("1")){
			exchangeType = HsConstants.HSExchageType.US.getCode();
		}
		if(marketType.equals("2")){
			exchangeType = HsConstants.HSExchageType.HK.getCode();
		}
		return exchangeType;
	}

	/**
	 * 根据资产ID，获取证券代码
	 */
	public static String getStockCode(String assetId){
		String stockCode = StringUtils.EMPTY;
		if(assetId != null){
			if(assetId.indexOf(".") == -1){
				stockCode = assetId;
			}
			if(assetId.endsWith(EMarket.SH.toString()) || assetId.endsWith(EMarket.SZ.toString())){
				stockCode = assetId.substring(0,assetId.length() - 3);
			}else{
				stockCode = assetId.substring(0,assetId.length() - 3).replaceAll("^(0+)", "");
			}
			// 恒生柜台美股股票代码中的.是空格
			if(assetId.endsWith(EMarket.US.toString())){
				stockCode = stockCode.replace("."," ");
			}
		}
		return stockCode;
	}

	/**
	 * 获取证券名称
	 * 简体 zh-hans，繁体 zh-hant，英文 en
	 */
	public static String getStockName(StkInfo stkInfo, String lang, String srcName){
		if(StringUtils.isEmpty(lang) || stkInfo == null){
			return srcName;
		}
		if(lang.equals(AfeDictMsgHandler.LANG_ZH_HANS) && StringUtils.isNotEmpty(stkInfo.getStkName())){
			return stkInfo.getStkName();
		}
		if(lang.equals(AfeDictMsgHandler.LANG_ZH_HANT) && StringUtils.isNotEmpty(stkInfo.getTraditionalName())){
			return stkInfo.getTraditionalName();
		}
		if(lang.equals(AfeDictMsgHandler.LANG_EN) && StringUtils.isNotEmpty(stkInfo.getEngName())){
			return stkInfo.getEngName();
		}

		// 简体为空，繁体不为空，使用繁体翻译成简体
		if(lang.equals(AfeDictMsgHandler.LANG_ZH_HANS) && StringUtils.isEmpty(stkInfo.getStkName()) && StringUtils.isNotEmpty(stkInfo.getTraditionalName())){
			return ZhConverterUtil.convertToSimple(stkInfo.getTraditionalName());
		}
		// 繁体为空，简体不为空，使用简体翻译成繁体
		if(lang.equals(AfeDictMsgHandler.LANG_ZH_HANT) && StringUtils.isEmpty(stkInfo.getTraditionalName()) && StringUtils.isNotEmpty(stkInfo.getStkName())){
			return ZhConverterUtil.convertToTraditional(stkInfo.getStkName());
		}

		return srcName;
	}

	/**
	 *
	 * @param lang
	 * @param orderType 订单类型 1-普通订单，2-条件单
	 * @param operationType 操作类型 1-改单，2-撤单  0-下单
	 * @return
	 */
	public static String getSendEmailTypeName(String lang, Integer orderType,Integer operationType){
		String typeName = "";

		switch (lang) {
			case CommonConstant.ZH_CN:
				switch (operationType) {
					case 0:
						if(orderType == 1){
							typeName = "下单";
						}else{
							typeName = "下单（条件单）";
						}
						break;
					case 1:
						typeName = "改单";
						break;
					case 2:
						typeName = "撤单";
						break;
					default:
						break;
				}
				break;
			case CommonConstant.ZH_HK:
				switch (operationType) {
					case 0:
						if(orderType == 1){
							typeName = "落單";
						}else{
							typeName = "落單（條件單）";
						}
						break;
					case 1:
						typeName = "改單";
						break;
					case 2:
						typeName = "撤單";
						break;
					default:
						break;
				}
				break;
			default:
				switch (operationType) {
					case 0:
						if(orderType == 1){
							typeName = "New order";
						}else{
							typeName = "New order (Conditional)";
						}
						break;
					case 1:
						typeName = "Order modification";
						break;
					case 2:
						typeName = "Order cancellation";
						break;
					default:
						break;
				}
				break;
		}
		return typeName;
	}

	/**
	 *
	 * @param lang
	 * @param entrustBs 买卖方向 1-买入，2-卖出
	 * @return
	 */
	public static String getSendEmailEntrustBsName(String lang, String entrustBs){
		String entrustBsName = "";
		switch (lang) {
			case CommonConstant.ZH_CN:
				switch (entrustBs) {
					case "1":
						entrustBsName = "买入";
						break;
					case "2":
						entrustBsName = "卖出";
						break;
					default:
						break;
				}
				break;
			case CommonConstant.ZH_HK:
				switch (entrustBs) {
					case "1":
						entrustBsName = "買入";
						break;
					case "2":
						entrustBsName = "賣出";
						break;
					default:
						break;
				}
				break;
			default:
				switch (entrustBs) {
					case "1":
						entrustBsName = "Buy";
						break;
					case "2":
						entrustBsName = "Sell";
						break;
					default:
						break;
				}
				break;
		}
		return entrustBsName;
	}
}
