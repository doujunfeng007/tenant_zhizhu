package com.minigod.zero.biz.support;

import com.minigod.zero.biz.service.ZeroUserDetails;
import com.minigod.zero.biz.utils.TokenUtil;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.jwt.props.JwtProperties;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.Func;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt返回参数增强
 *
 * @author Chill
 */
@AllArgsConstructor
public class ZeroJwtTokenEnhancer implements TokenEnhancer {

	private final JwtAccessTokenConverter jwtAccessTokenConverter;
	private final JwtProperties jwtProperties;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		ZeroUserDetails principal = (ZeroUserDetails) authentication.getUserAuthentication().getPrincipal();
		//token参数增强
		Map<String, Object> info = new HashMap<>(16);
		info.put(TokenUtil.TENANT_ID, principal.getTenantId());
		info.put(TokenUtil.CLIENT_ID, TokenUtil.getClientIdFromHeader());
		info.put(TokenUtil.DEVICE_CODE, Func.toStr(principal.getDeviceCode()));
		info.put(TokenUtil.NICK_NAME, Func.toStr(principal.getNickName()));
		info.put(TokenUtil.CUST_EMAIL, Func.toStr(principal.getCustEmail()));
		info.put(TokenUtil.AVATAR, Func.toStr(principal.getAvatar()));
		info.put(TokenUtil.CUST_ID, Func.toStr(principal.getCustId()));
		info.put(TokenUtil.STATUS, Func.toStr(principal.getStatus()));
		info.put(TokenUtil.ESOP_STATUS, Func.toStr(principal.getEsopStatus()));
		info.put(TokenUtil.CUST_TYPE, Func.toStr(principal.getCustType()));
		info.put(TokenUtil.CUST_PHONE, Func.toStr(principal.getCustPhone()));
		info.put(TokenUtil.ACCOUNT_LIST, principal.getAcctList());
		info.put(TokenConstant.LOGIN_TIME, DateUtil.formatDateTime(new Date()));
		info.put(TokenConstant.TERMINAL, principal.getTerminal());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		OAuth2AccessToken oAuth2AccessToken = jwtAccessTokenConverter.enhance(accessToken, authentication);
		String accessTokenValue = oAuth2AccessToken.getValue();
		//token状态设置
		if (jwtProperties.getState()) {
			String custId = Func.toStr(principal.getCustId());
			Jwt2Util.addAccessToken(custId, accessTokenValue, accessToken.getExpiresIn());
			if (jwtProperties.getSingle()) {
				OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
				String refreshTokenValue = oAuth2RefreshToken.getValue();
				Jwt2Util.addRefreshToken(custId, refreshTokenValue, accessToken.getExpiresIn() * 168);
			}
		}
		return accessToken;
	}
}
