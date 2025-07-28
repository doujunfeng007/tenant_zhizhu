package com.minigod.zero.biz.granter;

import com.minigod.zero.biz.service.ZeroUserDetails;
import com.minigod.zero.biz.utils.TokenUtil;
import com.minigod.zero.cms.feign.oms.ILanguageClient;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.req.RegisterReq;
import com.minigod.zero.cust.feign.ICustAuthClient;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
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
 * 手机号注册登录TokenGranter
 *
 * @author Chill
 */
public class RegisterTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "register";

	private CheckCaptchaCache checkCaptchaCache;

	private UserDetailsService userDetailsService;

	private ICustAuthClient custAuthClient;

	private ILanguageClient languageClient;

	protected RegisterTokenGranter(UserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
								   OAuth2RequestFactory requestFactory, CheckCaptchaCache checkCaptchaCache, ICustAuthClient custAuthClient, ILanguageClient languageClient) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.userDetailsService = userDetailsService;
		this.checkCaptchaCache = checkCaptchaCache;
		this.custAuthClient = custAuthClient;
		this.languageClient = languageClient;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		// 获取请求源
		String sourceType = WebUtil.getRequest().getHeader(TokenUtil.SOURCE_TYPE_KEY);
		// 租户id
		String tenantId = WebUtil.getRequest().getHeader(TokenUtil.TENANT_HEADER_KEY);
		logger.info("请求头中获取的租户id==="+tenantId);
		Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
		String area = parameters.get("area"); // 区号
		String phone = parameters.get("phone"); // 手机号
		String captchaKey = parameters.get("captchaKey");
		String captchaCode = parameters.get("captchaCode");
		String inviter = parameters.get("inviter"); // 邀请人 预留
		String channel = parameters.get("channel"); // 注册渠道 预留
		if (StringUtil.isAnyBlank(area, phone, captchaCode, captchaKey)) {
			throw new ServerWebInputException(TokenUtil.REQUIRE_PARAM_MISS);
		}
		// 校验短信验证码
		if (!checkCaptchaCache.checkCaptcha(phone, captchaCode, captchaKey)) {
			throw new ServerWebInputException(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
		}
		ESourceType source = ESourceType.of(sourceType);
		// 注册新客户
		RegisterReq custRegister = new RegisterReq();
		custRegister.setAreaCode(area);
		custRegister.setPhoneNum(phone);
		custRegister.setTenantId(tenantId);
		custRegister.setSourceType(source.getCategory());
		R<Object> result = custAuthClient.custRegister(custRegister);// 客户表写入
		if (!result.isSuccess()) {
			throw new ServerWebInputException(result.getMsg());
		}
		String custId = (String) result.getData();
		if (custId == null) {
			throw new UsernameNotFoundException("客户数据异常！");
		}
		// 封装客户信息
		ZeroUserDetails zeroUserDetails = (ZeroUserDetails) userDetailsService.loadUserByUsername(custId);
//		zeroUserDetails.setTenantId((String) client.getResourceIds().toArray()[0]);
		zeroUserDetails.setTenantId(tenantId);
		logger.info("登录封装的用户信息====="+ client.getResourceIds().toArray()[0].toString());
		// 组装认证数据，关闭密码校验
		Authentication userAuth = new UsernamePasswordAuthenticationToken(zeroUserDetails, null, zeroUserDetails.getAuthorities());
		// ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);

		// 返回 OAuth2Authentication
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}

}
