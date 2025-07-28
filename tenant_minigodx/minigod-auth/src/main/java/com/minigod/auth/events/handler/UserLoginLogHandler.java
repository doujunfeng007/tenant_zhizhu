package com.minigod.auth.events.handler;

import com.minigod.auth.constant.LoginConstant;
import com.minigod.auth.enums.LoginActionEnum;
import com.minigod.auth.enums.LoginTypeEnum;
import com.minigod.auth.service.AppUserDetails;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.customer.dto.CustomerLoginLogDTO;
import com.minigod.zero.customer.entity.CustomerIpAddress;
import com.minigod.zero.customer.fegin.UserLoginLogClient;
import lombok.extern.slf4j.Slf4j;
import com.minigod.zero.core.tool.api.R;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.minigod.zero.core.tool.utils.WebUtil.getRequest;

/**
 * 用户登录系统日志
 */
@Slf4j
@Component
public class UserLoginLogHandler implements AuthenticationSuccessHandler {
	private final UserLoginLogClient userLoginLogClient;

	public UserLoginLogHandler(UserLoginLogClient userLoginLogClient) {
		this.userLoginLogClient = userLoginLogClient;
	}

	/**
	 * 处理登录日志拦截
	 *
	 * @param userDetails
	 */
	@Override
	public void hand(AppUserDetails userDetails) {
		CustomerLoginLogDTO custLoginLog = new CustomerLoginLogDTO();
		String ip = WebUtil.getIP();
		String publicIPAddress = this.getClientIP();
		R<CustomerIpAddress> ipAddress = userLoginLogClient.findIpAddress(ip);
		if (ipAddress.isSuccess()) {
			CustomerIpAddress ipAddressEntity = ipAddress.getData();
			if (ipAddressEntity != null) {
				custLoginLog.setRegionCode(ipAddressEntity.getRegionCode());
				custLoginLog.setRegionCnName(ipAddressEntity.getRegionCnName());
				custLoginLog.setCountryCnName(ipAddressEntity.getCountryCnName());
				custLoginLog.setCountryCode(ipAddressEntity.getCountryCode());
			} else {
				log.info("用户【{}】custID={},登录系统日志查询IP地址信息,IP={},查询结果为空!", userDetails.getUsername(), userDetails.getCustId(), ip);
			}
		}
		custLoginLog.setCustId(userDetails.getCustId());
		custLoginLog.setCustName(userDetails.getName());
		custLoginLog.setEmail(userDetails.getEmail());
		custLoginLog.setPhoneNumber(userDetails.getPhoneNumber());
		custLoginLog.setAreaCode(userDetails.getAreaCode());

		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		String deviceCode = request.getHeader("DeviceCode");
		String deviceModel = WebUtil.getHeader(LoginConstant.DEVICE_MODEL);
		String appVersion = WebUtil.getHeader(LoginConstant.APP_VERSION);
		String sourceType = WebUtil.getHeader(TokenConstant.SOURCE_TYPE);
		String tenantId = WebUtil.getHeader(TokenConstant.TENANT_KEY);
		String osType = WebUtil.getHeader(LoginConstant.OS_TYPE);

		custLoginLog.setAppVersion(appVersion);
		custLoginLog.setDeviceCode(deviceCode);
		custLoginLog.setDeviceModel(deviceModel);
		custLoginLog.setSourceType(sourceType);
		if (tenantId != null) {
			custLoginLog.setTenantId(tenantId);
		} else {
			custLoginLog.setTenantId(userDetails.getTenantId());
		}
		custLoginLog.setOsType(Integer.parseInt(osType));
		custLoginLog.setIp(WebUtil.getIP());
		custLoginLog.setAction(LoginActionEnum.IN.getCode());
		custLoginLog.setType(LoginTypeEnum.LOGIN.getCode());
		custLoginLog.setLoginType(CustEnums.LoginWayType.REGISTER.getCode());
		R r = userLoginLogClient.save(custLoginLog);
		if (r.isSuccess()) {
			log.info("用户【{}】custID={},登录系统日志保存成功!", userDetails.getUsername(), userDetails.getCustId());
		} else {
			log.error("用户{}】custID={},登录系统日志保存失败!", userDetails.getUsername(), userDetails.getCustId());
		}
	}

	/**
	 * 拦截器执行顺序
	 *
	 * @return
	 */
	@Override
	public int order() {
		return Order.SUCCESS.SAVE_LOGIN_LOG.getCode();
	}

	/***
	 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP
	 */
	public String getClientIP() {
		String ip = getRequest().getHeader("X-Real-IP");
		log.info("X-Real-IP 初始化值的ip:{} ", ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("X-Forwarded-For");
			log.info("X-Forwarded-For赋值成功ip:{} ", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("Proxy-Client-IP");
			log.info("Proxy-Client-IP赋值成功ip:{} ", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("WL-Proxy-Client-IP");
			log.info("WL-Proxy-Client-IP赋值成功ip:{} ", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getRemoteAddr();
			log.info("RemoteAddr赋值成功ip:{} ", ip);
		}
		if (ip == null) {
			log.info("获取不到ip");
		}
		return ip;
	}
}
