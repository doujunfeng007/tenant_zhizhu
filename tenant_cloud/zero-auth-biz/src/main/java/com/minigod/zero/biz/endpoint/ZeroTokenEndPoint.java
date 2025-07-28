package com.minigod.zero.biz.endpoint;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minigod.zero.biz.utils.TokenUtil;
import com.wf.captcha.SpecCaptcha;
import com.minigod.zero.biz.config.ZeroAuthBizException;
import com.minigod.zero.common.cache.CacheNames;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.jwt.props.JwtProperties;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.SignUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.security.Principal;
import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.minigod.zero.core.cache.constant.CacheConstant.*;

/**
 * ZeroEndPoint
 *
 * @author Chill
 */
@Slf4j
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX)
public class ZeroTokenEndPoint {

	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private JwtProperties jwtProperties;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Resource
	private TokenStore tokenStore;
	@Resource
	private TokenEndpoint tokenEndpoint;
	@Resource
	private RestTemplate restTemplate;

	private final static String SAAS_GRANT_TYPE = "saas_client";
	// 智珠应用端
	@Value("${zhizhu.oauth.tenantId}")
	public String tenantId; //租户ID
	@Value("${zhizhu.oauth.saasApp}")
	public String saasAppAuth;
	@Value("${zhizhu.oauth.saasPc}")
	public String saasPcAuth;
	//private final static String MARKET_LOGIN_URL = "/api/zero-auth-mkt/back/oauth/token";
	//private final static String MARKET_LOGOUT_URL = "/api/zero-auth-mkt/back/oauth/logout";
	//private final static String MARKET_LOGIN_URL = "http://192.168.1.249:32001/back/oauth/token";
	//private final static String MARKET_LOGOUT_URL = "http://192.168.1.249:32001/back/oauth/logout";
	private final static String MARKET_LOGIN_URL = "http://10.10.1.8:32001/back/oauth/token";
	private final static String MARKET_LOGOUT_URL = "http://10.10.1.8:32001/back/oauth/logout";
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
	public R<Kv> captcha() {
		SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
		String verCode = specCaptcha.text().toLowerCase();
		String key = StringUtil.randomUUID();
		// 存入redis并设置过期时间为30分钟
		zeroRedis.setEx(CacheNames.CAPTCHA_KEY + key, verCode, Duration.ofMinutes(30));
		// 将key和base64返回给前端
		return R.data(Kv.create().set("key", key).set("image", specCaptcha.toBase64()));
	}

	/**
	 * 退出登录
	 */
	@GetMapping("/oauth/logout")
	public R<String> logout() {
		ZeroUser user = AuthUtil.getUser();
		Long custId = AuthUtil.getCustId();
		// 清空quotToken
		if (custId != null && custId != AuthUtil.GUEST_CUST_ID) {
			if (WebUtil.getHeader(TokenConstant.QUOT_TOKEN) == null) {
				return R.fail("行情Token无效，请检查！");
			}
			MultiValueMap<String, Object> mvParams = new LinkedMultiValueMap<>();
			mvParams.add(TokenUtil.GRANT_TYPE_KEY, SAAS_GRANT_TYPE);
			mvParams.add(TokenUtil.TENANT_PARAM_KEY, tenantId);
			mvParams.add(TokenUtil.CUST_ID, custId);
			this.postSend(MARKET_LOGOUT_URL, this.getHeaders(mvParams, WebUtil.getHeader(TokenUtil.SOURCE_TYPE_KEY), Boolean.FALSE), JSONObject.class, mvParams);
		}
		String token = Jwt2Util.getToken(WebUtil.getRequest().getHeader(TokenConstant.HEADER));
		// 清空redis保存的token
		if (user != null && jwtProperties.getState()) {
			Jwt2Util.removeAccessToken(String.valueOf(custId), token);
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
		return R.success("退出登录成功");
	}

	/**
	 * 缓存清空
	 */
	@GetMapping("/oauth/clear-cache")
	public R<String> clearCache() {
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
		return R.success();
	}


	@PostMapping("/oauth/token")
	public R<Object> getToken(Principal principal, @RequestParam Map<String, String> parameters) {
		try {
			//构造客户端Token
			ResponseEntity<OAuth2AccessToken> accessToken = tokenEndpoint.postAccessToken(principal, parameters);
			Map<String, Object> mktMap = this.convertToMap(accessToken.getBody());
			String custId = (String) accessToken.getBody().getAdditionalInformation().get(TokenUtil.CUST_ID);

			// 租户客户端Http远程登录获取行情权限（Redis 1号库 分布式锁2号库）
			if (StringUtil.isNotBlank(custId)) {
				log.info("行情登录：" + custId);
				MultiValueMap<String, Object> mvParams = new LinkedMultiValueMap<>();
				mvParams.add(TokenUtil.GRANT_TYPE_KEY, SAAS_GRANT_TYPE);
				mvParams.add(TokenUtil.TENANT_PARAM_KEY, tenantId);
				mvParams.add(TokenUtil.CUST_ID, custId);
				JSONObject	data = this.postSend(MARKET_LOGIN_URL, this.getHeaders(mvParams, WebUtil.getHeader(TokenUtil.SOURCE_TYPE_KEY), Boolean.TRUE), JSONObject.class, mvParams);
				if (data != null) {
					log.info("登录行情成功");
					mktMap.put(TokenConstant.QUOT_TOKEN, data.get(TokenConstant.ACCESS_TOKEN));
					mktMap.put(TokenUtil.OPEN_ID, data.get(TokenUtil.OPEN_ID));
				}
			}
			// 行情服务不分离架构登录行情
			/*if (!SAAS_GRANT_TYPE.equals(parameters.get(TokenUtil.GRANT_TYPE_KEY))) {
				Map<String, String> params = new LinkedHashMap<>();
				params.put(TokenUtil.GRANT_TYPE_KEY, SAAS_GRANT_TYPE);
				params.put(TokenUtil.TENANT_PARAM_KEY, TokenUtil.ZHIZHU_TENANT_ID);
				params.put(TokenUtil.CUST_ID, custId);
				accessToken = tokenEndpoint.postAccessToken(principal, params);
				if (accessToken != null) {
					mktMap.put(TokenUtil.QUOT_TOKEN, accessToken.getBody().getValue());
					mktMap.put(TokenUtil.QUOT_AUTH, accessToken.getBody().getAdditionalInformation().get(TokenUtil.QUOT_AUTH));
				}
			}*/
			return R.data(mktMap);
		} catch (ZeroAuthBizException e) {
			return R.fail(e.getCode(),e.getMessage());
		}  catch (ServerWebInputException | UsernameNotFoundException e) {
			return R.fail(e.getMessage());
		} catch (HttpRequestMethodNotSupportedException e) {
			throw new ServerWebInputException("App登录请求失败");
		}
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

	public <T extends Serializable> T postSend(String url, MultiValueMap httpHeader, Class<T> clazz, Object params) {
		try {
			HttpEntity<Object> requestEntity = new HttpEntity<>(params, httpHeader);
			R<T> result = restTemplate.postForObject(url, requestEntity, R.class);
			if (result == null) {
				return null;
			}
			if (result.getCode() == R.success().getCode() && result.getData() != null) {
				return new ObjectMapper().convertValue(result.getData(), clazz);
			}
		} catch (Exception e) {
			throw new RuntimeException("Http-Rest调用异常", e);
		}
		return null;
	}

	private MultiValueMap getHeaders(MultiValueMap<String, Object> params, String sourceType, Boolean flag) {
		HttpHeaders requestHeaders = new HttpHeaders();
		Long ts = new Date().getTime();
		String sign = SignUtil.sign(JSONObject.toJSONString(params), ts.toString());
		requestHeaders.add("TS", ts.toString());
		requestHeaders.add("Sign", sign);
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		requestHeaders.add(TokenConstant.SOURCE_TYPE, sourceType);
		requestHeaders.add(TokenConstant.REAL_IP, WebUtil.getIP());
		requestHeaders.add(TokenConstant.LANGUAGE, WebUtil.getLanguage());
		requestHeaders.add(TokenConstant.DEVICE_CODE, WebUtil.getHeader(TokenUtil.DEVICE_CODE));
		if (flag) {
			if (ESourceType.PC.getName().equals(sourceType)) {
				requestHeaders.add(TokenUtil.HEADER_KEY, TokenUtil.HEADER_PREFIX + saasPcAuth);
			} else {
				requestHeaders.add(TokenUtil.HEADER_KEY, TokenUtil.HEADER_PREFIX + saasAppAuth);
			}
		}else {
			requestHeaders.add(TokenUtil.HEADER, WebUtil.getHeader(TokenUtil.HEADER));
			requestHeaders.add(TokenConstant.QUOT_TOKEN, WebUtil.getHeader(TokenConstant.QUOT_TOKEN));
		}
		return requestHeaders;
	}

}
