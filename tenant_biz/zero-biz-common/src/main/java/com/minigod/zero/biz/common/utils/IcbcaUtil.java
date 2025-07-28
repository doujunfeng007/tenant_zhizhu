package com.minigod.zero.biz.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author:yanghu.luo
 * @create: 2023-03-28 18:12
 * @Description: 手机号处理
 */
public class IcbcaUtil {

	/**
	 * 返回手机号码的区号
	 */
	public static String getPhoneArea(String phone) {
		return phone.substring(0,2);
	}

	/**
	 * 去掉手机号的区号
	 */
	public static String getPhoneNumber(String phone) {
		return phone.substring(2);
	}

	public static String getAccountType(String icbcaAccType){
		// M0001 保证金账户(Margin Trading Client)
		if(StringUtils.equals(icbcaAccType, "M0001")){
			return "2";
		}else{
			return "1";
		}
	}

	public static int getAccountStatus(String icbcaAccStatus){
		/**
		 * A 活动(active)
		 * C 关闭(close)
		 * S 暂停(suspend)
		 */
		if(StringUtils.equals(icbcaAccStatus,"A")){
			return 1;
		}else if(StringUtils.equals(icbcaAccStatus,"S")){
			return 2;
		}else if(StringUtils.equals(icbcaAccStatus,"C")){
			return 3;
		}else if(StringUtils.equals(icbcaAccStatus,"D")){
			return 4;
		}
		return 0;
	}

	public static int getMoneyType(String icbcaMoneyType){
		if(StringUtils.equals(icbcaMoneyType,"CNY")){
			return 1;
		}else if(StringUtils.equals(icbcaMoneyType,"HKD")){
			return 2;
		}else if(StringUtils.equals(icbcaMoneyType,"USD")){
			return 3;
		}
		return 0;
	}
}
