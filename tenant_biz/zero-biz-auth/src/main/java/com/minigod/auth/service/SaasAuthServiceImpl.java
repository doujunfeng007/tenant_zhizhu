package com.minigod.auth.service;

import cn.hutool.json.JSONUtil;
import com.minigod.auth.utils.TokenUtil;
import com.minigod.zero.core.http.util.HttpUtil;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.core.tool.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chen
 * @ClassName SaasAuthServiceImpl.java
 * @Description TODO
 * @createTime 2025年01月15日 18:53:00
 */
@Service
@Slf4j
public class SaasAuthServiceImpl implements ISaasAuthService {

	@Value("${saas.tenantId}")
	private String saasTenantId;

	@Value("${saas.minigodx.url}")
	private String saasMinigodxUrl;

	@Value("${saas.clientId}")
	private String clientId;

	@Value("${saas.clientSecret}")
	private String clientSecret;

	private static final String SAAS_LOGIN_URL = "/auth/oauth/token";
	private static final String SAAS_LOGIN_GRANT_TYPE = "cust_id";

	@Override
	public R login(String custId, String sourceType) {
		R result;
		try {
			Map<String, Object> params = new HashMap<>();
			params.put(ZeroConstant.MDC_TENANT_ID_KEY, saasTenantId);
			params.put(TokenConstant.CUST_ID,custId);
			params.put(TokenUtil.GRANT_TYPE_KEY, SAAS_LOGIN_GRANT_TYPE);
			log.info("登录SAAS端信息参数{}", params);
			String queryParams = params.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));
			String fullUrl = saasMinigodxUrl + SAAS_LOGIN_URL + "?" + queryParams;
			log.info("登录租户端信息地址{}", fullUrl);
			Map<String, String> headers = new HashMap<>();
			String authorization = getAuthorization();
			headers.put("Authorization", authorization);
			headers.put(TokenConstant.SOURCE_TYPE, sourceType);
			headers.put(TokenConstant.DEVICE_CODE, WebUtil.getHeader(TokenConstant.DEVICE_CODE));
			String response = HttpUtil.post(fullUrl, headers, null);
			log.info("登录租户端信息返回{}", response);
			result = JSONUtil.toBean(response, R.class);
		} catch (Exception e) {
			log.error("登录SAAS端异常", e);
			return R.fail("登录异常");
		}
		return result;
	}

	private String getAuthorization() {
		String authStr = clientId + ":" + clientSecret;
		String encodeStr = Base64.getEncoder().encodeToString(authStr.getBytes());
		return "Basic " + encodeStr;
	}
}
