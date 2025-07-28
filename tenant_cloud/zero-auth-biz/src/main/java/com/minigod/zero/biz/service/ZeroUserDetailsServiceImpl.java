package com.minigod.zero.biz.service;

import com.minigod.zero.biz.constant.AuthConstant;
import com.minigod.zero.biz.utils.TokenUtil;
import com.minigod.zero.cms.feign.oms.ILanguageClient;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.jwt.props.JwtProperties;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.params.DeviceInfo;
import com.minigod.zero.cust.cache.AcctInfo;
import com.minigod.zero.cust.cache.CustInfo;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.feign.ICustAuthClient;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebInputException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 用户信息
 *
 * @author Chill
 */
@Service
//@Primary //解决多个DetailsService死循环问题
@AllArgsConstructor
public class ZeroUserDetailsServiceImpl implements UserDetailsService {

	private final ICustAuthClient custAuthClient;
	private final JwtProperties jwtProperties;
	private final ILanguageClient languageClient;

	@Override
	@SneakyThrows
	public ZeroUserDetails loadUserByUsername(String custId) {
		HttpServletRequest request = WebUtil.getRequest();
		// 获取请求源
		String sourceType = request.getHeader(TokenUtil.SOURCE_TYPE_KEY);
		String grantType = request.getParameter(TokenUtil.GRANT_TYPE_KEY);

		// 判断令牌合法性
		if (!judgeRefreshToken(grantType, request)) {
			throw new ServerWebInputException(TokenUtil.TOKEN_NOT_PERMISSION);
		}

		// 获取设备号
		String deviceCode = request.getHeader(TokenUtil.DEVICE_CODE);
		// 设备信息 兜底设备上报
		String deviceType = request.getHeader("deviceType");
		String osType = request.getHeader("osType");
		String osVersion = request.getHeader("osVersion");
		if (!sourceType.equals(ESourceType.H5.getName()) && StringUtil.isAnyBlank(deviceCode, deviceType, osType, osVersion)) {
			throw new ServerWebInputException(TokenUtil.DEVICE_INFO_NULL);
		}
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceCode(deviceCode);// 设备号
		deviceInfo.setOpenCode(request.getParameter("openCode"));// 极光ID
		deviceInfo.setDeviceName(request.getHeader("deviceName"));// 设备名称
		deviceInfo.setDeviceType(Integer.parseInt(deviceType));// 设备类型(0PC;1手机;2平板 3WEB)
		deviceInfo.setDeviceModel(request.getHeader("deviceModel"));// 设备型号
		deviceInfo.setOsType(Integer.parseInt(osType));// 操作系统类型(0安卓，1苹果，2WP，4 windows)
		deviceInfo.setOsVersion(osVersion);// 操作系统版本
		deviceInfo.setAppVersion(request.getHeader("appVersion"));// APP版本
		deviceInfo.setChannel(sourceType);// 使用渠道
		CustDeviceEntity custDevice = Objects.requireNonNull(BeanUtil.copy(deviceInfo, CustDeviceEntity.class));

		// 远程调用，获取认证信息 兜底设备上报
		R<CustInfo> result = custAuthClient.getCustInfo(Long.parseLong(custId), custDevice, sourceType, CustEnums.LoginWayType.getCode(grantType));
		// 判断返回信息
		if (result.isSuccess()) {
			CustInfo custInfo = result.getData();
			// 用户不存在,但提示用户名与密码错误并锁定账号
			if (custInfo == null || custInfo.getCustId() == null) {
				throw new UsernameNotFoundException(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_login_failed"));
			}
			// 用户存在但密码错误,超过次数则锁定账号
			/*boolean checkPass = "account".equals(grantType) || "corp_author".equals(grantType);
			if (checkPass && !custInfo.getPassword().equals(DigestUtil.hex(RSANewUtil.decrypt(request.getParameter("password"))))) {
				throw new UsernameNotFoundException(TokenUtil.USER_NOT_FOUND);
			}*/
			// 缓存客户APP权限（多个账号在切换时缓存）
			List<AcctInfo> acctList = custInfo.getAcctList();
			if (acctList != null && acctList.size() == 1) {
				Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.APP_AUTH_KEY, custInfo.getCustId().toString(), acctList.get(0).getAppPermission());
			}
			int custType = "corp_author".equals(grantType) ? 4 : 1;
			if ("esop_acct".equals(grantType)) {
				custType = 5;
			}
			return new ZeroUserDetails(custInfo.getCustId().toString(), custInfo.getWechatId(), custInfo.getNickName(), custInfo.getCustIcon(), AuthConstant.ENCRYPT,
				custType, deviceCode, custInfo.getCellPhone(), custInfo.getCellEmail(), custInfo.getAcctList(), sourceType, custInfo.getStatus(), custInfo.getEsopStatus(),
				true, true, true, true, Collections.EMPTY_LIST);
		} else {
			throw new ServerWebInputException(result.getMsg());
		}
	}

	/**
	 * 校验refreshToken合法性
	 *
	 * @param grantType 认证类型
	 * @param request   请求
	 */
	private boolean judgeRefreshToken(String grantType, HttpServletRequest request) {
		if (jwtProperties.getState() && jwtProperties.getSingle() && StringUtil.equals(grantType, TokenUtil.REFRESH_TOKEN_KEY)) {
			String refreshToken = request.getParameter(TokenUtil.REFRESH_TOKEN_KEY);
			Claims claims = Jwt2Util.parseJWT(refreshToken);
			String custId = String.valueOf(claims.get(TokenConstant.CUST_ID));
			String token = Jwt2Util.getRefreshToken(custId, refreshToken);
			return StringUtil.equalsIgnoreCase(token, refreshToken);
		}
		return true;
	}

}
