package com.minigod.zero.auth.granter;

import com.minigod.zero.auth.constant.AuthConstant;
import com.minigod.zero.auth.service.ZeroUserDetails;
import com.minigod.zero.auth.utils.TokenUtil;
import com.minigod.zero.core.social.props.SocialProperties;
import com.minigod.zero.core.social.utils.SocialUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.entity.UserInfo;
import com.minigod.zero.user.entity.UserOauth;
import com.minigod.zero.user.feign.IUserClient;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 第三方登录认证类
 *
 * @author Chill
 */
public class SocialTokenGranter extends AbstractTokenGranter {
	private static final String GRANT_TYPE = "social";
	private static final Integer AUTH_SUCCESS_CODE = 2000;

	private IUserClient userClient;
	private SocialProperties socialProperties;

	protected SocialTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, IUserClient userClient, SocialProperties socialProperties) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.userClient = userClient;
		this.socialProperties = socialProperties;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		// 请求头租户信息
		HttpServletRequest request = WebUtil.getRequest();
		String tenantId = Func.toStr(request.getHeader(TokenUtil.TENANT_HEADER_KEY), TokenUtil.DEFAULT_TENANT_ID);

		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		String deviceCode = parameters.get(TokenUtil.DEVICE_CODE);
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

		// 组装数据
		UserOauth userOauth = Objects.requireNonNull(BeanUtil.copy(authUser, UserOauth.class));
		userOauth.setSource(authUser.getSource());
		userOauth.setTenantId(tenantId);
		userOauth.setUuid(authUser.getUuid());

		// 远程调用，获取认证信息
		R<UserInfo> result = userClient.userAuthInfo(userOauth);
		ZeroUserDetails zeroUserDetails;
		if (result.isSuccess()) {
			User user = result.getData().getUser();
			//Kv detail = result.getData().getDetail();
			if (user == null || user.getId() == null) {
				throw new InvalidGrantException("social grant failure, user is null");
			}
			zeroUserDetails = new ZeroUserDetails(user.getId(),
				tenantId, result.getData().getOauthId(), user.getName(), user.getRealName(), user.getDeptId(), user.getPostId(), user.getRoleId(), Func.join(result.getData().getRoles()), Func.toStr(userOauth.getAvatar(), TokenUtil.DEFAULT_AVATAR),
				user.getAccount(), userOauth.getUsername(), AuthConstant.ENCRYPT + user.getPassword(), deviceCode, "", user.getPhone(), true, true, true, true,
				AuthorityUtils.commaSeparatedStringToAuthorityList(Func.join(result.getData().getRoles())),user.getUpdatePwd());
		} else {
			throw new InvalidGrantException("social grant failure, feign client return error");
		}

		// 组装认证数据，关闭密码校验
		Authentication userAuth = new UsernamePasswordAuthenticationToken(zeroUserDetails, null, zeroUserDetails.getAuthorities());
		// ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);

		// 返回 OAuth2Authentication
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}

}
