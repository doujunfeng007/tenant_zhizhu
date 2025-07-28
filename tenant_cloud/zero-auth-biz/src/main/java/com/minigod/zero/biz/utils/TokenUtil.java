
package com.minigod.zero.biz.utils;

import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.utils.Charsets;
import com.minigod.zero.core.tool.utils.StringPool;
import com.minigod.zero.core.tool.utils.WebUtil;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;

import java.util.Base64;
import java.util.Calendar;

/**
 * 认证工具类
 *
 * @author Chill
 */
public class TokenUtil {

	public final static String HEADER = TokenConstant.HEADER;
	public final static String AVATAR = TokenConstant.AVATAR;
	public final static String ACCOUNT = TokenConstant.ACCOUNT;
	public final static String USER_NAME = TokenConstant.USER_NAME;
	public final static String NICK_NAME = TokenConstant.NICK_NAME;
	public final static String REAL_NAME = TokenConstant.REAL_NAME;
	public final static String USER_ID = TokenConstant.USER_ID;
	public final static String DEPT_ID = TokenConstant.DEPT_ID;
	public final static String POST_ID = TokenConstant.POST_ID;
	public final static String ROLE_ID = TokenConstant.ROLE_ID;
	public final static String ROLE_NAME = TokenConstant.ROLE_NAME;
	public final static String TENANT_ID = TokenConstant.TENANT_ID;
	public final static String OAUTH_ID = TokenConstant.OAUTH_ID;
	public final static String CLIENT_ID = TokenConstant.CLIENT_ID;
	public final static String DETAIL = TokenConstant.DETAIL;
	public final static String LICENSE = TokenConstant.LICENSE;
	public final static String LICENSE_NAME = TokenConstant.LICENSE_NAME;
	public final static String SESSION_ID = TokenConstant.SESSION_ID;
	public final static String SOURCE_TYPE_KEY = TokenConstant.SOURCE_TYPE;
	public final static String DEVICE_CODE = TokenConstant.DEVICE_CODE;
	public final static String CUST_ID = TokenConstant.CUST_ID;
	public final static String CUST_TYPE = TokenConstant.CUST_TYPE;
	public final static String CUST_PHONE = TokenConstant.CUST_PHONE;
	public final static String CUST_EMAIL = TokenConstant.CUST_EMAIL;
	public final static String OPEN_ID = TokenConstant.OPEN_ID;
	public final static String ACCOUNT_LIST = "acctList";
	public final static String STATUS = "status";
	public final static String ESOP_STATUS = "esopStatus";

	public final static String DEPT_HEADER_KEY = "Dept-Id";
	public final static String ROLE_HEADER_KEY = "Role-Id";
	public final static String CAPTCHA_HEADER_KEY = "Captcha-Key";
	public final static String CAPTCHA_HEADER_CODE = "Captcha-Code";
	public final static String REQUIRE_PARAM_MISS = "必要参数缺失，请检查";
	public final static String CAPTCHA_NOT_CORRECT = "验证码不正确，请重试";
	public final static String TENANT_HEADER_KEY = "Tenant-Id";
	public final static String TENANT_PARAM_KEY = "tenant_id";
	public final static String DEFAULT_TENANT_ID = "000000";
	public final static String ZHIZHU_TENANT_ID = "000001";
	public final static String TENANT_NOT_FOUND = "租户ID未找到";
	public final static String DEFAULT_USER_TYPE = "web";
	public final static String DEVICE_INFO_NULL = "设备类型或系统类型、版本缺失";
	public final static String TOKEN_NOT_PERMISSION = "令牌授权已过期";
	public final static String USER_NOT_FOUND = "用户名或密码错误";
	public final static String USER_HAS_NO_ROLE = "未获得用户的角色信息";
	public final static String USER_HAS_NO_TENANT = "未获得用户的租户信息";
	public final static String USER_HAS_NO_TENANT_PERMISSION = "租户授权已过期,请联系管理员";
	public final static String USER_HAS_TOO_MANY_FAILS = "登录错误次数过多,请稍后再试";
	public final static String HEADER_KEY = "Authorization";
	public final static String HEADER_PREFIX = "Basic ";
	public final static String DEFAULT_AVATAR = "";
	public final static String PASSWORD_KEY = "password";
	public final static String GRANT_TYPE_KEY = "grant_type";
	public final static String REFRESH_TOKEN_KEY = "refresh_token";
	public final static String IS_INVESTOR = "is_investor";

	/**
	 * 解码
	 */
	@SneakyThrows
	public static String[] extractAndDecodeHeader() {
		String header = WebUtil.getRequest().getHeader(TokenUtil.HEADER_KEY);
		if (header == null || !header.startsWith(TokenUtil.HEADER_PREFIX)) {
			throw new UnapprovedClientAuthenticationException("请求头中无client信息");
		}

		byte[] base64Token = header.substring(6).getBytes(Charsets.UTF_8_NAME);

		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException var7) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, Charsets.UTF_8_NAME);
		int index = token.indexOf(StringPool.COLON);
		if (index == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		} else {
			return new String[]{token.substring(0, index), token.substring(index + 1)};
		}
	}

	/**
	 * 获取请求头中的客户端id
	 */
	public static String getClientIdFromHeader() {
		String[] tokens = extractAndDecodeHeader();
		return tokens[0];
	}

	/**
	 * 获取token过期时间(次日凌晨3点)
	 *
	 * @return expire
	 */
	public static int getTokenValiditySecond() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
	}

	/**
	 * 获取refreshToken过期时间
	 *
	 * @return expire
	 */
	public static int getRefreshTokenValiditySeconds() {
		return 60 * 60 * 24 * 15;
	}

}
