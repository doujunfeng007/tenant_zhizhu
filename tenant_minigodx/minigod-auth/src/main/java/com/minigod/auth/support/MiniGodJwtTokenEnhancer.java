/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.auth.support;

import com.alibaba.fastjson2.JSONObject;
import com.minigod.auth.service.AppUserDetails;
import com.minigod.auth.utils.TokenUtil;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.jwt.props.JwtProperties;
import com.minigod.zero.core.tool.utils.Func;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * jwt返回参数增强
 *
 * @author zsdp
 */
@Slf4j
@AllArgsConstructor
public class MiniGodJwtTokenEnhancer implements TokenEnhancer {

	private final JwtAccessTokenConverter jwtAccessTokenConverter;
	private final JwtProperties jwtProperties;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		if (authentication.getUserAuthentication() != null){
			AppUserDetails principal =  (AppUserDetails) authentication.getUserAuthentication().getPrincipal();
			//token参数增强
			Map<String, Object> info = new HashMap<>(16);
			info.put(TokenUtil.CLIENT_ID, TokenUtil.getClientIdFromHeader());
			info.put(TokenUtil.USER_ID, Func.toStr(principal.getUserId()));
			info.put(TokenUtil.CUSTID, Func.toStr(principal.getUserId()));
			info.put(TokenUtil.ACCOUNT, principal.getAccount());
			info.put(TokenUtil.USER_NAME, principal.getUsername());
			info.put(TokenUtil.NICK_NAME, principal.getName());
			info.put(TokenUtil.REAL_NAME, principal.getRealName());
			info.put(TokenUtil.EXPIRES_IN,accessToken.getExpiresIn());
			info.put(TokenUtil.TENANT_ID,principal.getTenantId());
			info.put(TokenUtil.IS_WHITE_LIST,principal.getIsWhiteList());
			info.put(TokenUtil.ROLE_ID,principal.getRoleId());
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
			//token状态设置
			if (jwtProperties.getState()) {
				OAuth2AccessToken oAuth2AccessToken = jwtAccessTokenConverter.enhance(accessToken, authentication);
				String accessTokenValue = oAuth2AccessToken.getValue();
				String userId = Func.toStr(principal.getUserId());
				Jwt2Util.addAccessToken(userId, accessTokenValue, accessToken.getExpiresIn());
				if (jwtProperties.getSingle()) {
					OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
					String refreshTokenValue = oAuth2RefreshToken.getValue();
					Jwt2Util.addRefreshToken(userId, refreshTokenValue, accessToken.getExpiresIn() * 168);
				}
			}
		}
		return accessToken;
	}
}
