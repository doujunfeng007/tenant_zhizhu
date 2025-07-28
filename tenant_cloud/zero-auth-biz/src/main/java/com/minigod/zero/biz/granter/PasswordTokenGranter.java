package com.minigod.zero.biz.granter;

import com.minigod.zero.biz.service.ZeroUserDetails;
import com.minigod.zero.biz.utils.TokenUtil;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.req.RegisterReq;
import com.minigod.zero.cust.feign.ICustAuthClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.server.ServerWebInputException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 账户密码TokenGranter
 *
 * @author Chill
 */
public class PasswordTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "account";

	//private final static int FAIL_COUNT = 5;

	private ICustAuthClient custAuthClient;

	private UserDetailsService userDetailsService;

	protected PasswordTokenGranter(UserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
								   OAuth2RequestFactory requestFactory, ICustAuthClient custAuthClient/*, CommonCache commonCache*/) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.userDetailsService = userDetailsService;
		this.custAuthClient = custAuthClient;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		String sourceType = WebUtil.getRequest().getHeader(TokenUtil.SOURCE_TYPE_KEY);
		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		String checkType = parameters.get("checkType");
		String username = parameters.get("username");
		String password = parameters.get("password");
		String areaCode = parameters.get("areaCode");
		// Protect from downstream leaks of password
		parameters.remove("password");
		if (StringUtil.isAnyBlank(checkType, username, password)) {
			throw new ServerWebInputException(TokenUtil.REQUIRE_PARAM_MISS);
		}
		// 密码校验失败5次锁定
		/*int count = commonCache.getFailCount(GRANT_TYPE, username);
		if (count >= FAIL_COUNT) {
			throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_TOO_MANY_FAILS);
		}*/
		// 校验客户
		ESourceType source = ESourceType.of(sourceType);
		RegisterReq custRegister = new RegisterReq();
		custRegister.setCheckType(Integer.parseInt(checkType));
		custRegister.setUsername(username);
		custRegister.setPassword(password);
		custRegister.setAreaCode(areaCode);
		custRegister.setSourceType(source.getCategory());
		R<Object> result = custAuthClient.custLogin(custRegister);// 校验密码
		if (!result.isSuccess()) {
			// commonCache.setFailCount(GRANT_TYPE, username, count);
			throw new ServerWebInputException(result.getMsg());
		}
		String custId = (String) result.getData();
		if (custId == null) {
			throw new UsernameNotFoundException("客户数据异常！");
		}
		// 成功则清除登录错误次数
		//commonCache.delFailCount(GRANT_TYPE, username);
		// 封装客户信息
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
