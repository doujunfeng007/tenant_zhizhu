package com.minigod.zero.biz.granter;

import com.minigod.zero.biz.service.ZeroUserDetails;
import com.minigod.zero.biz.utils.TokenUtil;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.social.props.SocialProperties;
import com.minigod.zero.core.social.utils.SocialUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.entity.CustThirdOauth;
import com.minigod.zero.cust.feign.ICustAuthClient;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.server.ServerWebInputException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 第三方登录认证类
 *
 * @author Chill
 */
public class SocialTokenGranter extends AbstractTokenGranter {
	private static final String GRANT_TYPE = "social";
	private static final Integer AUTH_SUCCESS_CODE = 2000;

	private UserDetailsService userDetailsService;
	private ICustAuthClient custAuthClient;
	private SocialProperties socialProperties;

	protected SocialTokenGranter(UserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, ICustAuthClient custAuthClient, SocialProperties socialProperties) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.userDetailsService = userDetailsService;
		this.custAuthClient = custAuthClient;
		this.socialProperties = socialProperties;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		// 获取请求源
		String sourceType = WebUtil.getRequest().getHeader(TokenUtil.SOURCE_TYPE_KEY);
		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		// 开放平台来源
		String sourceParameter = parameters.get("source");
		// 匹配是否有别名定义
		String source = socialProperties.getAlias().getOrDefault(sourceParameter, sourceParameter);
		// 开放平台授权码
		String code = parameters.get("code");
		// 开放平台状态吗
		String state = parameters.get("state");

		// 获取开放平台授权数据
		AuthRequest authRequest = SocialUtil.getAuthRequest(source, socialProperties);
		AuthCallback authCallback = new AuthCallback();
		authCallback.setCode(code);
		authCallback.setState(state);
		AuthResponse authResponse = authRequest.login(authCallback);
		AuthUser authUser;
		if (authResponse.getCode() == AUTH_SUCCESS_CODE) {
			authUser = (AuthUser) authResponse.getData();
		} else {
			throw new InvalidGrantException("social grant failure, auth response is not success");
		}

		// 保存第三方登录用户信息
		CustThirdOauth thirdOauth = new CustThirdOauth();
		thirdOauth.setSource(source);
		thirdOauth.setOpenId(authUser.getUuid());
		thirdOauth.setUsername(authUser.getUsername());
		thirdOauth.setNickname(authUser.getNickname());
		thirdOauth.setGender(authUser.getGender().getDesc());
		thirdOauth.setAvatar(authUser.getAvatar());
		thirdOauth.setEmail(authUser.getEmail());
		thirdOauth.setCompany(authUser.getCompany());
		thirdOauth.setLocation(authUser.getLocation());
		thirdOauth.setBlog(authUser.getBlog());
		R<Object> result = custAuthClient.registThirdOauth(thirdOauth, ESourceType.of(sourceType).getCategory());// 客户表写入
		if (!result.isSuccess()) {
			throw new ServerWebInputException(result.getMsg());
		}
		String custId = (String) result.getData();
		if (custId == null) {
			throw new UsernameNotFoundException("客户数据异常！");
		}
		// 组装数据
		ZeroUserDetails zeroUserDetails = (ZeroUserDetails) userDetailsService.loadUserByUsername(custId);
		zeroUserDetails.setTenantId((String) client.getResourceIds().toArray()[0]);
		// 组装认证数据，关闭密码校验
		Authentication userAuth = new UsernamePasswordAuthenticationToken(zeroUserDetails, null, zeroUserDetails.getAuthorities());
		// ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);

		// 返回 OAuth2Authentication
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}

}
