package com.minigod.zero.cust.service;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

/**
 * @author:yanghu.luo
 * @create: 2023-03-06 16:03
 * @Description: 工银接口访问
 */
public interface IIcbcaService {


	/**
	 * 调用工银登录接口
	 */
	JSONObject accountLogin(Map<String,Object> data);

	/**
	 * 工银短信发送
	 */
	boolean sms2fa(Map<String,Object> data);

	/**
	 * 工银邮件发送
	 */
	JSONObject emailCmsalert(Map<String,Object> data);

	/**
	 * 未绑定结算账户查询
	 */
	JSONObject settlementList(Map<String,Object> data);

	/**
	 * 结算账户绑定
	 */
	boolean registerSettAcc(Map<String,Object> data);

	/**
	 * 查询客户信息
	 */
	JSONObject  accountQueryClientInfo(Map<String,Object> data);

	/**
	 * 查询客户地址信息
	 */
	JSONObject  accountQueryClientAddr(Map<String,Object> data);

	/**
	 * 点击行情报价
	 */
	JSONObject  accountQueryClientBasicquote(Map<String,Object> data);
}
