package com.minigod.zero.biz.granter;

import com.minigod.zero.biz.config.ZeroAuthBizException;
import com.minigod.zero.biz.service.ZeroUserDetails;
import com.minigod.zero.biz.utils.TokenUtil;
import com.minigod.zero.common.cache.CacheNames;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
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

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author:yanghu.luo
 * @create: 2023-03-20 15:26
 * @Description: 交易账号登陆TokenGranter
 */
public class AccountTokenGranter extends AbstractTokenGranter {

	private static final String GRANT_TYPE = "trade_acct";

	private ZeroRedis zeroRedis;

	private ICustAuthClient custAuthClient;

	private UserDetailsService userDetailsService;

	protected AccountTokenGranter(UserDetailsService userDetailsService, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, ZeroRedis zeroRedis, ICustAuthClient custAuthClient) {
		super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.userDetailsService = userDetailsService;
		this.zeroRedis = zeroRedis;
		this.custAuthClient = custAuthClient;
	}

	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		HttpServletRequest request = WebUtil.getRequest();
		// 获取请求源
		String sourceType = request.getHeader(TokenUtil.SOURCE_TYPE_KEY);
		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		String tradeAccount = parameters.get("tradeAccount");

		String deviceCode = request.getHeader(TokenUtil.DEVICE_CODE);

		// 获取登录错误次数
		/* int count = getFailCount(GRANT_TYPE, tradeAccount);
		TODO 错误1次则需图形验证码校验
		if (count > 0) {
			String key = parameters.get("captchaKey");
			String code = parameters.get("captchaCode");
			// 获取图形验证码
			String redisCode = zeroRedis.get(CacheNames.CAPTCHA_KEY + key);
			// 判断验证码
			if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
				throw new UserDeniedAuthorizationException(TokenUtil.CAPTCHA_NOT_CORRECT);
			}
		}
		*/

		parameters.put(TokenUtil.DEVICE_CODE, deviceCode);
		parameters.put("osType", request.getHeader("osType"));
		parameters.put("osVersion", request.getHeader("osVersion"));
		parameters.put("password", parameters.get("password"));
		parameters.put("tradeAccount", parameters.get("tradeAccount"));
		// 校验密码
		R<Object> rt = custAuthClient.acctCheckPwd(parameters);
		if (!rt.isSuccess()) {
			//this.setFailCount(GRANT_TYPE, tradeAccount, count);
			throw new ZeroAuthBizException(rt.getCode(), rt.getMsg());
		}
		// 清除登录错误次数
		//delFailCount(GRANT_TYPE, tradeAccount);

		ESourceType source = ESourceType.of(sourceType);
		// 注册新客户
		RegisterReq custRegister = new RegisterReq();
		custRegister.setTradeAccount(tradeAccount);
		custRegister.setSourceType(source.getCategory());
		R<Object> result = custAuthClient.acctRegister(custRegister);// 客户表、账户表的写入
		if (!result.isSuccess()) {
			throw new ServerWebInputException(result.getMsg());
		}
		String custId = (String) result.getData();
		if (custId == null) {
			throw new UsernameNotFoundException("客户数据异常！");
		}
		// 根据客户ID进行初始化
		ZeroUserDetails zeroUserDetails = (ZeroUserDetails) userDetailsService.loadUserByUsername(custId);
		zeroUserDetails.setTenantId((String) client.getResourceIds().toArray()[0]);
		// 组装认证数据，关闭密码校验
		Authentication userAuth = new UsernamePasswordAuthenticationToken(zeroUserDetails, null, zeroUserDetails.getAuthorities());
		// ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		// 返回 OAuth2Authentication
		return new OAuth2Authentication(storedOAuth2Request, userAuth);
	}

	/**
	 * 获取账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @return int
	 */
	private int getFailCount(String tenantId, String username) {
		return Func.toInt(zeroRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username)), 0);
	}

	/**
	 * 设置账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 * @param count    次数
	 */
	private void setFailCount(String tenantId, String username, int count) {
		zeroRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username), count + 1, Duration.ofMinutes(30));
	}

	/**
	 * 清空账号错误次数
	 *
	 * @param tenantId 租户id
	 * @param username 账号
	 */
	private void delFailCount(String tenantId, String username) {
		zeroRedis.del(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, username));
	}

}
