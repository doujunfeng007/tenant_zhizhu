package com.minigod.auth.granter.captcha;

import com.alibaba.fastjson.JSONObject;
import com.minigod.auth.service.AppUserDetails;
import com.minigod.auth.service.AuthorizationServicesUserDetails;
import com.minigod.auth.service.MinigodUserDetailsService;
import com.minigod.auth.utils.TokenUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.platform.enums.SmsTemplate;
import com.minigod.zero.platform.utils.SmsUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;

import java.util.HashSet;

/**
 * @author: guangjie.liao
 * @Date: 2024/4/18 10:46
 * @Description: 手机短信登陆认证
 */
public class CaptchaAuthenticationProvider implements AuthenticationProvider {

	private ZeroRedis minigodRedis;
	private MinigodUserDetailsService userDetailsService;

    public CaptchaAuthenticationProvider(MinigodUserDetailsService userDetailsService,ZeroRedis minigodRedis){
        this.userDetailsService = userDetailsService;
		this.minigodRedis = minigodRedis;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		CaptchaAuthenticationToken captchaAuthenticationToken = (CaptchaAuthenticationToken) authentication;

		String mobileNumber = (String) captchaAuthenticationToken.getPrincipal();
		String code = (String) captchaAuthenticationToken.getCredentials();
		String areaCode   = captchaAuthenticationToken.getAreaCode();
		String tenantId   = captchaAuthenticationToken.getTenantId();
		String packageNum = captchaAuthenticationToken.getPackageNum();
		String captchaKey = captchaAuthenticationToken.getCaptchaKey();
		Long channelId = captchaAuthenticationToken.getChannelId();
		Long brokerId = captchaAuthenticationToken.getBrokerId();

		JSONObject param = new JSONObject();
		param.put("packageNum",packageNum);
		param.put("channelId",channelId);
		param.put("brokerId",brokerId);
		param.put("mobileNumber",mobileNumber);

		// 判断验证码
		Boolean flag = SmsUtil.builder().templateCode(SmsTemplate.LOGIN_VERIFICATION_CODE.getCode())
			.captchaKey(captchaKey).captchaCode(code).areaCode(areaCode).phoneNumber(mobileNumber).validate();
		if (code == null || !flag) {
			throw new UserDeniedAuthorizationException(TokenUtil.CAPTCHA_NOT_CORRECT);
		}
		//账号信息验证
		AppUserDetails userDetails =
			((AuthorizationServicesUserDetails) userDetailsService).loadUserByUsername(mobileNumber,areaCode,tenantId,param.toJSONString());
				CaptchaAuthenticationToken result = new CaptchaAuthenticationToken(userDetails, authentication.getCredentials(), new HashSet<>());
		result.setDetails(authentication.getDetails());
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CaptchaAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
