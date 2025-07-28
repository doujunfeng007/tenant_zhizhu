package com.minigod.zero.auth.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.minigod.zero.auth.constant.AuthConstant;
import com.minigod.zero.auth.utils.RSANewUtil;
import com.minigod.zero.auth.utils.TokenUtil;
import com.minigod.zero.common.cache.CacheNames;
import com.minigod.zero.core.jwt.JwtUtil;
import com.minigod.zero.core.jwt.props.JwtProperties;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.*;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.enums.SmsTemplate;
import com.minigod.zero.platform.utils.EmailUtil;
import com.minigod.zero.platform.utils.SmsUtil;
import com.minigod.zero.system.entity.Tenant;
import com.minigod.zero.system.feign.ISysClient;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.entity.UserInfo;
import com.minigod.zero.user.feign.IUserClient;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author Chill
 */
@Slf4j
@Service
@AllArgsConstructor
public class ZeroUserDetailsServiceImpl implements UserDetailsService,ZeroUserDetailsService {

	public static final Integer FAIL_COUNT = 5;

	private final IUserClient userClient;
	private final ISysClient sysClient;

	private final ZeroRedis zeroRedis;
	private final JwtProperties jwtProperties;

	@Override
	@SneakyThrows
	public ZeroUserDetails loadUserByUsername(String username) {
		HttpServletRequest request = WebUtil.getRequest();
		RequestContext context = extractRequestContext(request);

		validateRequest(context);
		validateTenant(context.getTenantId());
		checkLoginAttempts(context.getTenantId(), username);

		UserInfo userInfo = getUserInfo(context, username);
		validateUser(userInfo, context, username);

		User user = userInfo.getUser();
		handleMultiDeptRole(user, context);

		delFailCount(context.getTenantId(), username);

		return createUserDetails(userInfo);
	}

	private RequestContext extractRequestContext(HttpServletRequest request) {
		return RequestContext.builder()
			.headerDept(request.getHeader(TokenUtil.DEPT_HEADER_KEY))
			.headerRole(request.getHeader(TokenUtil.ROLE_HEADER_KEY))
			.headerTenant(request.getHeader(TokenUtil.TENANT_HEADER_KEY))
			.sourceType(request.getHeader(TokenUtil.SOURCE_TYPE_KEY))
			.paramTenant(request.getParameter(TokenUtil.TENANT_PARAM_KEY))
			.password(request.getParameter(TokenUtil.PASSWORD_KEY))
			.grantType(request.getParameter(TokenUtil.GRANT_TYPE_KEY))
			.build();
	}

	private void validateRequest(RequestContext context) {
		if (!judgeRefreshToken(context.getGrantType(), context.getSourceType(), WebUtil.getRequest())) {
			throw new UserDeniedAuthorizationException(TokenUtil.TOKEN_NOT_PERMISSION);
		}

		if (StringUtil.isAllBlank(context.getHeaderTenant(), context.getParamTenant())) {
			throw new InvalidClientException(TokenUtil.TENANT_NOT_FOUND);
		}
	}

	private void validateTenant(String tenantId) {
		R<Tenant> tenantResponse = sysClient.getTenant(tenantId);
		if (!tenantResponse.isSuccess() || TokenUtil.judgeTenant(tenantResponse.getData())) {
			throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_NO_TENANT_PERMISSION);
		}
	}

	private void checkLoginAttempts(String tenantId, String username) {
		int count = getFailCount(tenantId, username);
		if (count >= FAIL_COUNT) {
			throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_TOO_MANY_FAILS);
		}
	}

	private UserInfo getUserInfo(RequestContext context, String username) {
		R<UserInfo> result = userClient.userInfo(context.getTenantId(), username, context.getSourceType());
		if (!result.isSuccess() || result.getData() == null) {
			throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
		}
		return result.getData();
	}

	private void validateUser(UserInfo userInfo, RequestContext context, String username) {
		User user = userInfo.getUser();
		if (user == null || user.getId() == null) {
			setFailCount(context.getTenantId(), username, getFailCount(context.getTenantId(), username));
			throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
		}

		validatePassword(user, context, username);
		validateUserRoles(userInfo, context);
	}

	private void validatePassword(User user, RequestContext context, String username) {
		if (shouldValidatePassword(context)) {
			String password = getDecryptedPassword(context);
			if (!isPasswordValid(user, password, context)) {
				setFailCount(context.getTenantId(), username, getFailCount(context.getTenantId(), username));
				throw new OAuth2Exception(TokenUtil.USER_NOT_FOUND);
			}
		}
	}

	private boolean shouldValidatePassword(RequestContext context) {
		return context.getGrantType() != null &&
			!context.getGrantType().equals(TokenUtil.REFRESH_TOKEN_KEY) &&
			!context.getGrantType().equals("sms_code");
	}

	private String getDecryptedPassword(RequestContext context) {
		return context.getSourceType().startsWith(ESourceType.ESOP.getName()) ?
			RSANewUtil.decrypt(context.getPassword()) :
			context.getPassword();
	}

	private boolean isPasswordValid(User user, String password, RequestContext context) {
		if (!user.getPassword().equals(DigestUtil.hex(password))) {
			return context.getSourceType().startsWith(ESourceType.ESOP.getName()) &&
				userClient.checkOldLoginPwd(user.getAccount(), password).getData();
		}
		return true;
	}

	private void validateUserRoles(UserInfo userInfo, RequestContext context) {
		if (Func.isEmpty(userInfo.getRoles()) && !context.getSourceType().startsWith(ESourceType.ESOP.getName())) {
			throw new UserDeniedAuthorizationException(TokenUtil.USER_HAS_NO_ROLE);
		}
	}

	private void handleMultiDeptRole(User user, RequestContext context) {
		if (Func.isNotEmpty(context.getHeaderDept()) && user.getDeptId().contains(context.getHeaderDept())) {
			user.setDeptId(context.getHeaderDept());
		}
		if (Func.isNotEmpty(context.getHeaderRole()) && user.getRoleId().contains(context.getHeaderRole())) {
			R<List<String>> roleResult = sysClient.getRoleAliases(context.getHeaderRole());
			if (roleResult.isSuccess()) {
				user.setRoleId(context.getHeaderRole());
			}
		}
	}

	private ZeroUserDetails createUserDetails(UserInfo userInfo) {
		User user = userInfo.getUser();
		return new ZeroUserDetails(
			user.getId(), user.getTenantId(), StringPool.EMPTY, user.getName(), user.getRealName(),
			user.getDeptId(), user.getPostId(), user.getRoleId(), Func.join(userInfo.getRoles()),
			Func.toStr(user.getAvatar(), TokenUtil.DEFAULT_AVATAR), user.getAccount(), user.getAccount(),
			AuthConstant.ENCRYPT + user.getPassword(), "", "", user.getPhone(), true, true, true, true,
			AuthorityUtils.commaSeparatedStringToAuthorityList(Func.join(userInfo.getRoles())),user.getUpdatePwd()
		);
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

	/**
	 * 校验refreshToken合法性
	 *
	 * @param grantType 认证类型
	 * @param sourceType 请求源
	 * @param request   请求
	 */
	private boolean judgeRefreshToken(String grantType, String sourceType, HttpServletRequest request) {
		if (jwtProperties.getState() && jwtProperties.getSingle() && StringUtil.equals(grantType, TokenUtil.REFRESH_TOKEN_KEY)) {
			String refreshToken = request.getParameter(TokenUtil.REFRESH_TOKEN_KEY);
			Claims claims = JwtUtil.parseJWT(refreshToken);
			String tenantId = String.valueOf(claims.get(TokenConstant.TENANT_ID));
			String userId = String.valueOf(claims.get(TokenConstant.USER_ID));
			String account = String.valueOf(claims.get(TokenConstant.ACCOUNT));
			String token = JwtUtil.getRefreshToken(tenantId, userId, account, refreshToken);
			return StringUtil.equalsIgnoreCase(token, refreshToken);
		}
		return true;
	}


	@Override
	public R<Object> checkPwd(Map<String, String> parameters) {
		validateInputParameters(parameters);
		validateCaptcha(parameters);

		String tenantId = getTenantId();
		String userName = parameters.get("username");
		String password = parameters.get("password");
		String sourceType = WebUtil.getRequest().getHeader(TokenUtil.SOURCE_TYPE_KEY);

		UserInfo userInfo = getUserInfo(tenantId, userName, sourceType);
		validatePassword(password, userInfo.getUser().getPassword());

		generateAndStoreVerificationCode(tenantId, userName);
		Map<String,String> result = new HashMap<>();
//		result.put("code",verificationCode);
		result.put("area",userInfo.getUser().getArea());
		result.put("phone",userInfo.getUser().getArea() + "-" +userInfo.getUser().getPhone());
		result.put("email",userInfo.getUser().getEmail());
		return R.data(result);
	}

	@Override
	public R verificationCode(Map<String, String> parameters) {
		String userName = parameters.get("username");
		String loginAccount = parameters.get("loginAccount");

		if (StringUtils.isEmpty(userName)){
			throw new OAuth2Exception("用户名缺失");
		}
		if (StringUtils.isEmpty(loginAccount)){
			throw new OAuth2Exception("登录账号缺失");
		}
		String userKey = String.format(CacheNames.USER_VERIFICATION_CODE, getTenantId(), userName);
		String code = zeroRedis.get(userKey);
		log.info("获取验证码：{}",code);
		if (StringUtils.isEmpty(code)){
			throw new OAuth2Exception("用户名未验证");
		}
		//邮箱
		if (loginAccount.indexOf("@") > 0){
			R result =  EmailUtil.builder()
				.accepts(Arrays.asList(loginAccount))
				.captchaCode(code)
				.templateCode(EmailTemplate.INTEGRATED_MANAGEMENT_BACKEND.getCode())
				.sendCaptcha();
			if (!result.isSuccess()){
				throw new OAuth2Exception("验证码发送失败");
			}
			Map<String,String> data = (Map<String, String>) result.getData();
			return R.data(data.get("captchaKey"));
		}
		String[] loginAccountStr = loginAccount.split("-");
		String areaCode = loginAccountStr[0];
		String phone = loginAccountStr[1];
		if (StringUtils.isEmpty(areaCode)){
			throw new OAuth2Exception("手机区号缺失");
		}
		//短信
		return SmsUtil.builder()
			.areaCode(areaCode)
			.phoneNumber(phone)
			.templateCode(SmsTemplate.LOGIN_VERIFICATION_CODE.getCode())
			.captchaCode(code)
			.isValidateMessage()
			.sendSync();
	}

	@Override
	public ZeroUserDetails loadUserByPhone(String tenantId, String phone,String areaCode) {
		R<UserInfo> result = userClient.selectByPhone(tenantId,phone,areaCode);
		if (!result.isSuccess() || result.getData() == null) {
			throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
		}
		UserInfo userInfo = result.getData();
		if (userInfo == null){
			return null;
		}
		User user = userInfo.getUser();
		if (user == null){
			return null;
		}
		return createUserDetails(result.getData());
	}

	@Override
	public ZeroUserDetails loadUserByEmail(String tenantId, String email) {
		R<UserInfo> result = userClient.selectByEmail(tenantId,email);
		if (!result.isSuccess() || result.getData() == null) {
			throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
		}
		UserInfo userInfo = result.getData();
		if (userInfo == null){
			return null;
		}
		User user = userInfo.getUser();
		if (user == null){
			return null;
		}
		return createUserDetails(result.getData());
	}


	private void validateInputParameters(Map<String, String> parameters) {
		String[] requiredParams = {"username", "password"};
		for (String param : requiredParams) {
			if (StringUtils.isEmpty(parameters.get(param))) {
				throw new OAuth2Exception(param + " 缺失");
			}
		}
	}

	private void validateCaptcha(Map<String, String> parameters) {
		String captchaKey = initCaptchaKey();
		String captchaCode = initCaptchaCode();
		String redisCode = zeroRedis.get(CacheNames.CAPTCHA_KEY + captchaKey);

		if (captchaCode == null || !StringUtil.equalsIgnoreCase(redisCode, captchaCode)) {
			zeroRedis.del(CacheNames.CAPTCHA_KEY + captchaKey);
			throw new OAuth2Exception(TokenUtil.CAPTCHA_NOT_CORRECT);
		}

		zeroRedis.del(CacheNames.CAPTCHA_KEY + captchaKey);
	}

	private String getTenantId() {
		HttpServletRequest request = WebUtil.getRequest();
		String headerTenant = request.getHeader(TokenUtil.TENANT_HEADER_KEY);
		String paramTenant = request.getParameter(TokenUtil.TENANT_PARAM_KEY);
		return StringUtils.hasText(headerTenant) ? headerTenant : paramTenant;
	}

	private UserInfo getUserInfo(String tenantId, String userName, String sourceType) {
		R<UserInfo> result = userClient.userInfo(tenantId, userName, sourceType);
		if (!result.isSuccess() || result.getData() == null || result.getData().getUser() == null) {
			throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
		}
		return result.getData();
	}

	private void validatePassword(String inputPassword, String storedPassword) {
		if (!storedPassword.equals(DigestUtil.hex(inputPassword))) {
			throw new OAuth2Exception("密码错误");
		}
	}

	private String generateAndStoreVerificationCode(String tenantId, String userName) {
		String code = TokenUtil.generateRandomNumber();
		String userKey = String.format(CacheNames.USER_VERIFICATION_CODE, tenantId, userName);

		if (zeroRedis.exists(userKey)){
			zeroRedis.del(userKey);
		}
		zeroRedis.set(userKey, code);
		return code;
	}

	private String initCaptchaCode() {
		HttpServletRequest request = WebUtil.getRequest();
		String headerTenant = request.getHeader(TokenUtil.CAPTCHA_HEADER_CODE);
		String paramTenant = request.getParameter(TokenUtil.CAPTCHA_HEADER_CODE);
		return StringUtils.hasText(headerTenant) ? headerTenant : paramTenant;
	}

	private String initCaptchaKey() {
		HttpServletRequest request = WebUtil.getRequest();
		String headerTenant = request.getHeader(TokenUtil.CAPTCHA_HEADER_KEY);
		String paramTenant = request.getParameter(TokenUtil.CAPTCHA_HEADER_KEY);
		return StringUtils.hasText(headerTenant) ? headerTenant : paramTenant;
	}
}
