package com.minigod.zero.auth.endpoint;

import com.minigod.zero.auth.service.ZeroUserDetailsService;
import com.wf.captcha.SpecCaptcha;
import com.minigod.zero.common.cache.CacheNames;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.jwt.JwtUtil;
import com.minigod.zero.core.jwt.props.JwtProperties;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import me.zhyd.oauth.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.Duration;
import java.util.Map;

import static com.minigod.zero.core.cache.constant.CacheConstant.*;

/**
 * ZeroEndPoint
 *
 * @author Chill
 */
@NonDS
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX)
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
	@Autowired
	private ZeroUserDetailsService zeroUserDetailsService;

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

	@PostMapping("/oauth/verification_code")
	public R verificationCode(@RequestParam Map<String, String> parameters) {
		return zeroUserDetailsService.verificationCode(parameters);
	}

	/**
	 * 退出登录
	 */
	@GetMapping("/oauth/logout")
	public R<String> logout() {
		ZeroUser user = AuthUtil.getUser();
		String token = JwtUtil.getToken(WebUtil.getRequest().getHeader(TokenConstant.HEADER));
		// 清空redis保存的token
		if (user != null && jwtProperties.getState()) {
			JwtUtil.removeAccessToken(user.getTenantId(), String.valueOf(user.getUserId()), token);
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
			ResponseEntity<OAuth2AccessToken> result = tokenEndpoint.postAccessToken(principal, parameters);
			return R.data(result.getBody());
		} catch (ServerWebInputException | UsernameNotFoundException e) {
			return R.fail(e.getMessage());
		} catch (HttpRequestMethodNotSupportedException e) {
			throw new ServerWebInputException("后台登录请求失败");
		}
	}

	/**
	 * 账号密码校验
	 * @return
	 */
	@PostMapping("/oauth/checkPwd")
	public R<Object> checkPwd(@RequestParam Map<String, String> parameters){
		return zeroUserDetailsService.checkPwd(parameters);
	}
}
