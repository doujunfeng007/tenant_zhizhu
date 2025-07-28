package com.minigod.zero.auth.support;

import com.minigod.zero.auth.service.ZeroUserDetails;
import com.minigod.zero.auth.utils.TokenUtil;
import com.minigod.zero.core.jwt.JwtUtil;
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
	private final String salt = "SD0AQWT1CZBUPQW8EMS4FHAJ5ZXC6BCD";

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		ZeroUserDetails principal = (ZeroUserDetails) authentication.getUserAuthentication().getPrincipal();
		//token参数增强
		Map<String, Object> info = new HashMap<>(16);
		info.put(TokenUtil.TENANT_ID, Func.toStr(principal.getTenantId()));
		info.put(TokenUtil.CLIENT_ID, TokenUtil.getClientIdFromHeader());
		info.put(TokenUtil.DEVICE_CODE, Func.toStr(principal.getDeviceCode()));
		info.put(TokenUtil.NICK_NAME, Func.toStr(principal.getName()));
		info.put(TokenUtil.AVATAR, Func.toStr(principal.getAvatar()));
		info.put(TokenUtil.USER_ID, Func.toStr(principal.getUserId()));
		info.put(TokenUtil.USER_NAME, Func.toStr(principal.getUsername()));
		info.put(TokenUtil.ACCOUNT, Func.toStr(principal.getAccount()));
		info.put(TokenUtil.DEPT_ID, Func.toStr(principal.getDeptId()));
		info.put(TokenUtil.POST_ID, Func.toStr(principal.getPostId()));
		info.put(TokenUtil.ROLE_ID, Func.toStr(principal.getRoleId()));
		info.put(TokenUtil.ROLE_NAME, Func.toStr(principal.getRoleName()));
		info.put(TokenUtil.OAUTH_ID, Func.toStr(principal.getOauthId()));
		info.put(TokenConstant.LOGIN_TIME, DateUtil.formatDateTime(new Date()));
		info.put(TokenUtil.UPDATE_PWD,principal.getUpdatePwd());
		// info.put(TokenUtil.DETAIL, principal.getDetail());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);

		//token状态设置
		if (jwtProperties.getState()) {
			OAuth2AccessToken oAuth2AccessToken = jwtAccessTokenConverter.enhance(accessToken, authentication);
			String accessTokenValue = oAuth2AccessToken.getValue();
			String tenantId = principal.getTenantId();
			String userId = Func.toStr(principal.getUserId());
			String account = Func.toStr(principal.getAccount());
			JwtUtil.addAccessToken(tenantId, userId, account, accessTokenValue, accessToken.getExpiresIn());
			if (jwtProperties.getSingle()) {
				OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
				String refreshTokenValue = oAuth2RefreshToken.getValue();
				JwtUtil.addRefreshToken(tenantId, userId, account, refreshTokenValue, accessToken.getExpiresIn() * 168);
			}
		}

		return accessToken;
	}
}
