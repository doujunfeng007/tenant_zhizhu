package com.minigod.zero.trade.sjmb.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.system.entity.TradeKey;
import com.minigod.zero.system.feign.ITradeKeyClient;
import com.minigod.zero.trade.sjmb.common.SjmbFunctionsUrlEnum;
import com.minigod.zero.trade.sjmb.resp.SjmbResponse;
import com.minigod.zero.trade.sjmb.service.ICounterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chen
 * @ClassName CounterServiceImpl.java
 * @Description 随机漫步柜台
 * @createTime 2024年01月10日 19:13:00
 */
@Service
@Slf4j
public class CounterServiceImpl implements ICounterService {


	@Value("${counter.openApi.base.url:}")
	private String openApiBaseUrl;

	@Value("${counter.openApi.app.key:}")
	private String openApiAppKey;

	@Value("${counter.openApi.secret:}")
	private String openApiSecret;

	@Value("${counter.brokerApi.base.url:}")
	private String brokerApiBaseUrl;

	@Value("${counter.brokerApi.app.key:}")
	private String brokerApiAppKey;

	@Value("${counter.brokerApi.secret:}")
	private String brokerApiSecret;

	@Value("${counter.brokerApi.operator:}")
	private String brokerApiOperator;

	@Value("${trade.type:}")
	private String counterType;

	@Resource
	private ZeroRedis zeroRedis;

	@Resource
	private ITradeKeyClient tradeKeyClient;


	@Override
	public SjmbResponse openApiSend(Object req, SjmbFunctionsUrlEnum urlEnum) {
		String result;
		String tenantId = AuthUtil.getTenantId();
		TradeKey tradeKey = getTradeKey(tenantId);
		Map<String, String> headers = openApiHeadParam(urlEnum, req, tradeKey);
		String url;
		if (null != tradeKey) {
			url = tradeKey.getOpenApiUrl()+urlEnum.getUrl();
		} else {
			url = openApiBaseUrl + urlEnum.getUrl();
		}
		try {
			log.info("请求的url为{}，请求参数={}", url, cn.hutool.json.JSONUtil.toJsonStr(req));
			log.info("openapi请求头参数=={}", cn.hutool.json.JSONUtil.toJsonStr(headers));
			result = HttpUtil.createPost(url).addHeaders(headers).body(cn.hutool.json.JSONUtil.toJsonStr(req)).timeout(20000).execute().body();
			log.info("返回的参数为{}", result);
		} catch (Exception e) {
			log.error("请求柜台异常", e);
			return null;
		}
		SjmbResponse resp = JSONUtil.fromJson(result, SjmbResponse.class);
		return resp;
	}

	@Override
	public SjmbResponse brokerApiSend(Object req, SjmbFunctionsUrlEnum urlEnum, HttpMethod method) {
		String result = "";
		String tenantId = AuthUtil.getTenantId();
		TradeKey tradeKey = getTradeKey(tenantId);
		Map<String, String> headers = brokerApiHeadParam(tradeKey);
		String url;
		if (null != tradeKey) {
			url = tradeKey.getBrokerApiUrl()+urlEnum.getUrl();
		} else {
			url = brokerApiBaseUrl + urlEnum.getUrl();
		}
		try {
			log.info("请求的url为{}，请求参数={}", url, cn.hutool.json.JSONUtil.toJsonStr(req));
			if (HttpMethod.GET.equals(method)) {
				Map reqMap = JSON.parseObject(JSONUtil.toCompatibleJson(req));
				result = HttpUtil.createGet(url).addHeaders(headers).form(reqMap).timeout(20000).execute().body();
			} else if (HttpMethod.POST.equals(method)) {
				result = HttpUtil.createPost(url).addHeaders(headers).body(JSONUtil.toCompatibleJson(req)).timeout(20000).execute().body();
			}
			log.info("返回的参数为{}", result);
		} catch (Exception e) {
			log.error("请求柜台异常", e.getMessage());
			return null;
		}
		SjmbResponse resp = JSONUtil.fromJson(result, SjmbResponse.class);
		return resp;
	}
	@Override
	public TradeKey getTradeKey(String tenantId) {

		TradeKey tradeKey = zeroRedis.protoGet(CacheNames.TRADE_KEY.concat(counterType).concat(tenantId), TradeKey.class);
		if (null == tradeKey) {
			tradeKey = tradeKeyClient.getTradeKeyByTenantId(tenantId,counterType);
			if (null != tradeKey) {
				log.info("数据库查询租户数据为tradeKey"+ cn.hutool.json.JSONUtil.toJsonStr(tradeKey));
				zeroRedis.protoSet(CacheNames.TRADE_KEY.concat(counterType).concat(tenantId), tradeKey);
			}
		}
		return tradeKey;
	}

	private Map<String, String> brokerApiHeadParam(TradeKey tradeKey) {

		Map<String, String> headers = new HashMap<>();
		if (null != tradeKey) {
			headers.put("OPERATOR", tradeKey.getBrokerApiOperator());
			headers.put("APPKEY", tradeKey.getBrokerApiKey());
			headers.put("SECRET", tradeKey.getBrokerApiSecret());
		} else {
			headers.put("OPERATOR", brokerApiOperator);
			headers.put("APPKEY", brokerApiAppKey);
			headers.put("SECRET", brokerApiSecret);
		}


		return headers;
	}

	/**
	 * openApi 请求头参数
	 *
	 * @return
	 */
	private Map<String, String> openApiHeadParam(SjmbFunctionsUrlEnum urlEnum, Object req, TradeKey tradeKey) {
		Map<String, String> headers = new HashMap<>();
		Map reqMap = JSON.parseObject(cn.hutool.json.JSONUtil.toJsonStr(req));

		if (!SjmbFunctionsUrlEnum.CLIENT_LOGIN.equals(urlEnum)) {
			String token = getOpenApiToken(reqMap);
			headers.put("Authorization", token);
		}
		if (null != tradeKey) {
			headers.put("APPKEY", tradeKey.getOpenApiKey());
			headers.put("SECRET", tradeKey.getOpenApiSecret());
		} else {
			headers.put("APPKEY", openApiAppKey);
			headers.put("SECRET", openApiSecret);
		}

		return headers;
	}

	private String getOpenApiToken(Map reqMap) {
		String key = CacheNames.TRADE_TOKEN;
		String accountId = reqMap.get("accountId").toString();
		String token = "";
		try {
			token = zeroRedis.hGet(key, accountId);
		} catch (Exception e) {
			log.error("获取redis中的交易token异常", e);
		}
		return token;
	}
}
