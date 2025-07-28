/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth.endpoint;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.minigod.auth.service.ISaasAuthService;
import com.minigod.auth.utils.TokenUtil;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.exception.BusinessException;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.jwt.props.JwtProperties;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.wf.captcha.SpecCaptcha;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.minigod.zero.core.cache.constant.CacheConstant.*;

/**
 * minigodEndPoint
 *
 * @author zsdp
 */
@NonDS
@Slf4j
@RestController
@AllArgsConstructor
public class MiniGodTokenEndPoint {

	private final TokenStore tokenStore;
	private final ZeroRedis minigodRedis;
	private final JwtProperties jwtProperties;
	private final ClientDetailsService clientDetailsService;

	@Autowired
	private ISaasAuthService saasAuthService;


	/**
	 * 登录页面
	 */
	@GetMapping("/oauth/login")
	public ModelAndView require(ModelAndView model) {
		model.setViewName("login");
		return model;
	}

	/**
	 * 授权页面
	 */
	@GetMapping("/oauth/confirm_access")
	public ModelAndView confirm(HttpSession session, ModelAndView model) {
		Object auth = session.getAttribute("authorizationRequest");
		if (auth != null) {
			AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
			model.addObject("client", clientDetailsService.loadClientByClientId(authorizationRequest.getClientId()));
			model.addObject("principal", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		}
		model.setViewName("confirm");
		return model;
	}

	/**
	 * 用户信息
	 */
	@GetMapping("/oauth/user-info")
	public R<Authentication> currentUser(Authentication authentication) {
		return R.data(authentication);
	}

	/**
	 * 验证码
	 */
	@GetMapping("/oauth/captcha")
	public Kv captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = StringUtil.randomUUID();
		// 存入redis并设置过期时间为30分钟
		minigodRedis.setEx(CacheNames.CAPTCHA_KEY + key, verCode, Duration.ofMinutes(30));
		// 将key和base64返回给前端
		return Kv.create().set("key", key).set("image", specCaptcha.toBase64());
	}

	/**
	 * 退出登录
	 */
	@GetMapping("/oauth/logout")
	public R logout() {
		ZeroUser user = AuthUtil.getUser();
		String token = Jwt2Util.getToken(WebUtil.getRequest().getHeader(TokenConstant.TENANT_TOKEN));
		// 清空redis保存的token
		if (user != null && jwtProperties.getState()) {
			Jwt2Util.removeAccessToken(String.valueOf(user.getUserId()), token);
		}
		// 清空资源服务器保存的token
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
		OAuth2RefreshToken refreshToken = null;
		if (accessToken != null && StringUtil.isNoneBlank(accessToken.getValue())) {
			refreshToken = accessToken.getRefreshToken();
			tokenStore.removeAccessToken(accessToken);
		}
		if (refreshToken != null && StringUtil.isNoneBlank(refreshToken.getValue())) {
			tokenStore.removeRefreshToken(refreshToken);
		}
		return R.success();
	}

	/**
	 * 缓存清空
	 */
	@GetMapping("/oauth/clear-cache")
	public Kv clearCache() {
		CacheUtil.clear(BIZ_CACHE);
		CacheUtil.clear(USER_CACHE);
		CacheUtil.clear(DICT_CACHE);
		CacheUtil.clear(FLOW_CACHE);
		CacheUtil.clear(SYS_CACHE);
		CacheUtil.clear(PARAM_CACHE);
		CacheUtil.clear(RESOURCE_CACHE);
		CacheUtil.clear(MENU_CACHE);
		CacheUtil.clear(DICT_CACHE, Boolean.FALSE);
		CacheUtil.clear(MENU_CACHE, Boolean.FALSE);
		CacheUtil.clear(SYS_CACHE, Boolean.FALSE);
		CacheUtil.clear(PARAM_CACHE, Boolean.FALSE);
		return Kv.create().set("success", "true").set("msg", "success");
	}
	// 令牌请求的端点
	@Autowired
	private TokenEndpoint tokenEndpoint;

	/**
	 * 重写/oauth/token这个默认接口，返回的数据格式统一
	 */
	@PostMapping(value = "/auth/token")
	public R postAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
		throws HttpRequestMethodNotSupportedException {
		OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
		return R.data(accessToken);
	}



	@PostMapping("/oauth/token")
	public R<Object> getToken(Principal principal, @RequestParam Map<String, String> parameters) {
		try {
			//构造客户端Token
			/**
			 * 租户端数据租户id默认00000
			 */
			parameters.put(ZeroConstant.MDC_TENANT_ID_KEY ,TokenConstant.DEFAULT_TENANT_ID);
			ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
			Map<String, Object> mktMap = this.convertToMap(accessToken.getBody());
			String custId = (String) accessToken.getBody().getAdditionalInformation().get(TokenUtil.CUSTID);
			/**
			 * 登录SAAS端
			 */
			String sourceType = WebUtil.getHeader(TokenConstant.SOURCE_TYPE);
			R result = saasAuthService.login(custId,sourceType);
			if (!result.isSuccess()) {
				return R.fail(I18nUtil.getMessage(CommonConstant.LOGIN_AGAIN_PLEASE));
			}
			String data = JSONUtil.toJsonStr(result.getData());
			Map<String, Object> tenantDataMap = JsonUtil.readMap(data);
			String saasToken = (String) tenantDataMap.get(TokenConstant.ACCESS_TOKEN);
			String quotToken = (String) tenantDataMap.get(TokenConstant.QUOT_TOKEN);
			mktMap.put(TokenConstant.TENANT_TOKEN, accessToken.getBody().getValue());
			mktMap.put(TokenConstant.ACCESS_TOKEN, saasToken);
			mktMap.put(TokenConstant.QUOT_TOKEN, quotToken);

			return R.data(mktMap);
		} catch (BusinessException e) {
			return R.fail();
		} catch (ServerWebInputException | UsernameNotFoundException e) {
			return R.fail(e.getMessage());
		} catch (HttpRequestMethodNotSupportedException e) {
			throw new ServerWebInputException("App登录请求失败");
		}
	}

	/**
	 * token校验
	 * @return
	 */
	@PostMapping(value = "/oauth/check_token")
	public R checkToken(){
		String token = Jwt2Util.getToken(WebUtil.getRequest().getHeader(TokenConstant.TENANT_TOKEN));
		if (StringUtils.isBlank(token) ) {
			throw new BusinessException(ResultCode.PARAM_MISS);
		}
		Claims claims = Jwt2Util.parseJWT(token);
		if (claims == null){
			throw new BusinessException(ResultCode.UN_AUTHORIZED);
		}
		String custId = String.valueOf(claims.get(TokenConstant.USER_ID));
		String accessToken = Jwt2Util.getAccessToken(custId, token);
		if (StringUtils.isBlank(accessToken) || "null".equals(accessToken)) {
			throw new BusinessException(ResultCode.ACCESS_TOKEN_INVALID);
		}
		if (!token.equalsIgnoreCase(accessToken)) {
			Claims newClaims = Jwt2Util.parseJWT(accessToken);
			String loginTime = String.valueOf(newClaims.get(TokenConstant.LOGIN_TIME));
			String terminal = String.valueOf(newClaims.get(TokenConstant.TERMINAL));
			if (StringUtils.isBlank(loginTime) || loginTime.equals("null")){
				loginTime =DateUtil.formatDateTime(new Date());
			}
			Map<String, String> data = new HashMap<>();
			data.put("loginTime", loginTime);
			data.put("terminal", terminal);
			data.put("message", "已在别的设备登录");
			//互踢状态码
			return R.data(4401,data,"设备其他地方登录");
		}
		ZeroUser user = AuthUtil.getTenantUser();
		return R.data(user);
	}

	private Map<String, Object> convertToMap(OAuth2AccessToken accessToken) {
		Map<String, Object> result = new LinkedHashMap<>();
		if (accessToken instanceof DefaultOAuth2AccessToken) {
			result.put(OAuth2AccessToken.ACCESS_TOKEN, accessToken.getValue());
			result.put(OAuth2AccessToken.TOKEN_TYPE, accessToken.getTokenType());
			result.put(OAuth2AccessToken.EXPIRES_IN, accessToken.getExpiresIn());
			result.put(OAuth2AccessToken.REFRESH_TOKEN, accessToken.getRefreshToken());
			result.putAll(accessToken.getAdditionalInformation());
		} else {
			throw new IllegalArgumentException("Unsupported access token type: " + accessToken.getClass());
		}
		return result;
	}

}
