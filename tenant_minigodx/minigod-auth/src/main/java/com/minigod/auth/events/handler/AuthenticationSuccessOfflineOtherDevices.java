package com.minigod.auth.events.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.StringUtils;
import com.minigod.auth.service.AppUserDetails;
import com.minigod.common.exceptions.BusinessException;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.customer.entity.CustomerDeviceInfoEntity;
import com.minigod.zero.customer.fegin.ICustomerInfoClient;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.enums.PushTypeEnum;
import com.minigod.zero.platform.utils.PushUtil;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/7 14:13
 * @description：登录成功，下线其他设备推送
 */
@Slf4j
@Component
public class AuthenticationSuccessOfflineOtherDevices implements AuthenticationSuccessHandler{
	private static final String OTHER = "其他";
	private static final String TITLE = "账号在别的设备登录";
	private static final String DEVICE_CODE = "deviceCode";
	private static final String LOGIN_DEVICE = "LOGIN_DEVICE:%s:%s";
	private static final String CONTENT = "您的账号在#time在#device设备上登录，请重新登录，如非本人操作，请尽快修改登录密码";

	@Autowired
	private ZeroRedis zeroRedis;

	@Autowired
	private ICustomerInfoClient customerInfoClient;

	@Override
	public void hand(AppUserDetails userDetails) {
		String key = String.format(LOGIN_DEVICE,userDetails.getTenantId(),userDetails.getCustId());
		String newDeviceCode = WebUtil.getHeader(DEVICE_CODE);
		log.info("请求头deviceCode：{}",newDeviceCode);
		if (StringUtils.isEmpty(newDeviceCode)){
			return;
		}
		R<CustomerDeviceInfoEntity> result = customerInfoClient.customerDeviceDetail(userDetails.getCustId(),newDeviceCode);
		if (!result.isSuccess()){
			throw new BusinessException("获取设备号失败");
		}
		CustomerDeviceInfoEntity deviceInfo = result.getData();
		if (deviceInfo == null || deviceInfo.getId() == null){
			log.info("账号{}未获取到设备信息",userDetails.getCustId());
			return;
		}
		log.info("账号{}当前登录设备{}",userDetails.getCustId(),newDeviceCode);
		if (!zeroRedis.exists(key)){
			CustomerDeviceInfoVO deviceInfoVO = new CustomerDeviceInfoVO();
			BeanUtils.copyProperties(deviceInfo,deviceInfoVO);
			zeroRedis.set(key,deviceInfoVO);
			return;
		}
		CustomerDeviceInfoVO deviceInfoVO = zeroRedis.get(key);
		log.info("缓存设备信息：{}", JSONObject.toJSONString(deviceInfoVO));
		//设备名称
		String deviceModel = deviceInfo.getDeviceName();
		if (newDeviceCode.equals(deviceInfoVO.getDeviceCode())){
			return;
		}
		log.info("账号{}踢下线设备{}",userDetails.getCustId(),deviceInfoVO.getOpenCode());
		PushUtil.builder()
			.msgGroup("P")
			.fromUserId(Constants.USERID_ALL_USER)
			.param(DateUtil.formatDateTime(new Date()))
			.param(deviceModel)
			.lang(WebUtil.getLanguage())
			.pushType(PushTypeEnum.WEAK_MSG.getTypeValue())
			.group(MsgStaticType.DisplayGroup.KICK_EACH_OTHER)
			.deviceInfoList(Arrays.asList(deviceInfoVO))
			.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
			.templateCode(PushTemplate.LOGGED_IN_ON_ANOTHER_DEVICE.getCode()).pushAsync();

		CustomerDeviceInfoVO newDeviceInfoVO = new CustomerDeviceInfoVO();
		BeanUtils.copyProperties(deviceInfo,newDeviceInfoVO);
		zeroRedis.set(key,newDeviceInfoVO);
	}

	@Override
	public int order() {
		return Order.SUCCESS.OFFLINE_OTHER_DEVICES.getCode();
	}
}
