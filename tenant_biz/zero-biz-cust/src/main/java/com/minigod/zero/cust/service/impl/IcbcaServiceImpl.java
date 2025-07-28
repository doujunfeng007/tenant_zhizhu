package com.minigod.zero.cust.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.minigod.zero.cust.service.IIcbcaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author:yanghu.luo
 * @create: 2023-03-06 16:04
 * @Description: 工银接口访问
 */

@Slf4j
@Service
public class IcbcaServiceImpl implements IIcbcaService {

	private final static String ACCOUNT_LOGIN_URL = "/account/login";
	private final static String SMS2FA_URL = "/notification/sms/2fa";
	private final static String SETTLEMENT_LIST_URL = "/account/query/client/settlement/list";
	private final static String REGISTER_SETT_ACC_URL = "/v1/icbcainner/RegisterSettAcc";
	private final static String ACCOUNT_QUERY_CLIENT_INFO_URL = "/account/query/client/info";
	private final static String ACCOUNT_QUERY_CLIENT_ADDR_URL = "/account/query/client/addr";
	private final static String NOTIFICATION_EMAIL_CMSALERT_URL = "/notification/email/cmsalert";
	private final static String ACCOUNT_QUERY_CLIENT_BASICQUOTE_URL = "/account/query/client/basicquote";

	/**
	 * 工银服务地址
	 */
	@Value("${icbca.url:''}")
	private String icbcaUrl;

	@Override
	public JSONObject accountLogin(Map<String,Object> data) {
		return icbcaRun(icbcaUrl + ACCOUNT_LOGIN_URL,data);
	}

	@Override
	public boolean sms2fa(Map<String,Object> data) {
		Object o = icbcaRun(icbcaUrl + SMS2FA_URL,data);
		if(o == null){
			return false;
		}
		return true;
	}

	@Override
	public JSONObject emailCmsalert(Map<String, Object> data) {
		return icbcaRun(icbcaUrl + NOTIFICATION_EMAIL_CMSALERT_URL,data);
	}

	@Override
	public JSONObject settlementList(Map<String,Object> data) {
		return icbcaRun(icbcaUrl + SETTLEMENT_LIST_URL,data);
	}

	@Override
	public boolean registerSettAcc(Map<String, Object> data) {
		Object o = icbcaRun(icbcaUrl + REGISTER_SETT_ACC_URL,data);
		if(o == null){
			return false;
		}
		return true;
	}

	@Override
	public JSONObject accountQueryClientInfo(Map<String,Object> data) {
		return icbcaRun(icbcaUrl + ACCOUNT_QUERY_CLIENT_INFO_URL,data);
	}

	@Override
	public JSONObject accountQueryClientAddr(Map<String, Object> data) {
		return icbcaRun(icbcaUrl + ACCOUNT_QUERY_CLIENT_ADDR_URL,data);
	}

	@Override
	public JSONObject accountQueryClientBasicquote(Map<String, Object> data) {
		return icbcaRun(icbcaUrl + ACCOUNT_QUERY_CLIENT_BASICQUOTE_URL,data);
	}

	/**
	 * 调用工银接口
	 */
	public JSONObject icbcaRun(String url, Map<String, Object> data){
		long startTime = System.currentTimeMillis();
		log.info("请求信息[{}]：{}", startTime, JSONObject.toJSONString(data));
		log.info("调用地址[{}]：{}",startTime,url);
		try{
			//String result = HttpUtil.post(url,data) 设置header是因为测试环境k8s无法连接到工银环境，k8s配置访问的地址又只能配置分割线(k8s不认下划线)，真实访问的时候需要下划线，故加上Host
			String result = HttpUtil.createPost(url).body(JSONObject.toJSONString(data)).header("Host","zhizhuqa_to_icbca.icbcasiauat.com").execute().body();
			log.info("调用结果[{}]：{}",startTime,result);
			JSONObject object = JSONObject.parseObject(result);
			if (object.get("response_biz_content") != null) {
				JSONObject content = object.getJSONObject("response_biz_content");
				if (content.getInteger("return_code") == 0) {
					return content;
				}
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
		} finally {
			log.info("请求耗时[{}]：{}", startTime, System.currentTimeMillis() - startTime);
		}
		return null;
	}
}
