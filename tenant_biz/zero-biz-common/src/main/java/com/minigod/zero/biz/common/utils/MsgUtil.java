package com.minigod.zero.biz.common.utils;

import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.WebUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:yanghu.luo
 * @create: 2023-09-22 18:25
 * @Description: 返回错误信息
 */
public class MsgUtil {
	private static Map<String,String> errorMsg = new HashMap<>();
	static {
		errorMsg.put(code(ResultCode.PHONE_NUMBER_NULL, CommonConstant.ZH_CN),"当前账户暂无开户预留手机号，请先前往设置-账户与安全-修改预留信息中添加手机号");
		errorMsg.put(code(ResultCode.PHONE_NUMBER_NULL, CommonConstant.ZH_HK),"當前帳戶暫無開戶預留手機號，請先前往設定-帳戶與安全-修改預留資訊中添加手機號");
		errorMsg.put(code(ResultCode.PHONE_NUMBER_NULL, CommonConstant.EN_US),"There is no reserved mobile phone number for the current account. Please go to Settings-Account and Security-Modify Reserved Information to add the mobile phone number");
	}

	/**
	 * 获取错误信息
	 */
	public static String getErrorMsg(ResultCode code){
		return errorMsg.get(code(code,WebUtil.getLanguage()));
	}

	public static String code(ResultCode code,String language){
		return new StringBuilder(code.getCode()).append("_").append(language).toString();
	}
}
