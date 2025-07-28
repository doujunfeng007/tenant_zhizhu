package com.minigod.zero.platform.handler;

import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.platform.client.ICustomerInfoClient;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import com.minigod.zero.platform.utils.PlatformUtil;
import com.minigod.zero.resource.dto.JPushDTO;
import com.minigod.zero.resource.feign.IJPushClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StrongMsgHandler {

	@Value("${jg.aos.app_key:a43a3297d84706956ad562b5}")
	private String AOS_APP_KEY;
	@Value("${jg.aos.master_secret:923aa7e60833d3f675e75722}")
	private String AOS_MASTER_SECRET;
	@Value("${jg.ios.app_key:0cb49f92a05da4c1507a6990}")
	private String IOS_APP_KEY;
	@Value("${jg.ios.master_secret:344142186653963cffbfe4c9}")
	private String IOS_MASTER_SECRET;
	@Value("${jg.apns.istest:false}")
	private Boolean istest;

	@Resource
	private IJPushClient jPushClient;
	@Resource
	private ICustomerInfoClient custDeviceClient;


	public R strongMsgHandler(int devType, List<Long> lstUserIds, String notificationExtras, boolean isOfflinePush, int intPushType, String title, Set<Long> succUsers,List<CustomerDeviceInfoVO> deviceList) {

		List<CustomerDeviceInfoVO> newDeviceList = deviceList;
		if (CollectionUtil.isEmpty(newDeviceList) && !CollectionUtil.isEmpty(lstUserIds)){
			R<List<CustomerDeviceInfoVO>> result = custDeviceClient.customerDeviceList(lstUserIds);
			if (!result.isSuccess()){
				throw new ServiceException("发送失败，获取用户设备信息失败："+result.getMsg());
			}
			newDeviceList = result.getData();
		}
		//新版本 极光推送
		if (!CollectionUtils.isEmpty(newDeviceList)) {
			Map<Integer, List<CustomerDeviceInfoVO>> deviceGroup = newDeviceList.stream().collect(Collectors.groupingBy(CustomerDeviceInfoVO::getOsType));
			// ANDROID设备列表、用户列表
			List<CustomerDeviceInfoVO> androidDeviceList = deviceGroup.get(CommonEnums.OsTypeEnum.OS_ANDROID.getTypeValue());
			if(!CollectionUtils.isEmpty(androidDeviceList)){
				Set<String> lstAndroidDeviceIdSet = androidDeviceList.stream().map(CustomerDeviceInfoVO::getOpenCode).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
				Set<Long> lstAndroidUserIdSet = androidDeviceList.stream().filter(next -> StringUtils.isNotBlank(next.getOpenCode())).map(CustomerDeviceInfoVO::getCustId).collect(Collectors.toSet());
				if (devType == PlatformOsTypeEnum.OS_ALL.getTypeValue() || devType == PlatformOsTypeEnum.OS_ANDROID.getTypeValue()) {
					log.info("方法：sendMsgToUserList ======= 安卓强消息(极光)  ======");
					jPush(PlatformOsTypeEnum.OS_ANDROID.getTypeValue(), isOfflinePush, intPushType, title, succUsers, lstAndroidDeviceIdSet, lstAndroidUserIdSet, notificationExtras);
				}
			}

			// IOS设备列表、用户列表
			List<CustomerDeviceInfoVO> iosDeviceList = deviceGroup.get(CommonEnums.OsTypeEnum.OS_IOS.getTypeValue());
			if(!CollectionUtils.isEmpty(iosDeviceList)){
				Set<String> lstIosDeviceIdSet = iosDeviceList.stream().map(CustomerDeviceInfoVO::getOpenCode).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
				Set<Long> lstIosUserIdSet = iosDeviceList.stream().filter(next -> StringUtils.isNotBlank(next.getOpenCode())).map(CustomerDeviceInfoVO::getCustId).collect(Collectors.toSet());
				if (devType == PlatformOsTypeEnum.OS_ALL.getTypeValue() || devType == PlatformOsTypeEnum.OS_IOS.getTypeValue()) {
					log.info("方法：sendMsgToUserList ======= 苹果强消息(极光)  ======");
					jPush(PlatformOsTypeEnum.OS_IOS.getTypeValue(), isOfflinePush, intPushType, title, succUsers, lstIosDeviceIdSet, lstIosUserIdSet, notificationExtras);
				}
			}
		}
		return null;
	}


	private void jPush(int osType, boolean isOfflinePush, int pushType, String title, Set<Long> succUsers,
						 Set<String> lstDeviceIdSet, Set<Long> lstUserIdSet, String notificationExtras) {
		if (CollectionUtils.isEmpty(lstDeviceIdSet)) {
			log.warn("请求的用户没有在线的{}设备", PlatformOsTypeEnum.getTypeName(osType));
			return;
		}

		// 调用推送服务给指定设备推送信息
		try {
			List<String> lstDeviceIdList = new ArrayList<>(lstDeviceIdSet);
			// 给用户发送强提醒消息
			String authorization = PlatformUtil.getAuthorization(osType, IOS_APP_KEY, IOS_MASTER_SECRET, AOS_APP_KEY, AOS_MASTER_SECRET);
			JPushDTO jPushDTO = new JPushDTO();
			jPushDTO.setAuthorization(authorization);
			jPushDTO.setLstTokenId(lstDeviceIdList);
			jPushDTO.setTitle(title);
			jPushDTO.setTransMessage(notificationExtras);
			jPushDTO.setOsType(osType);
			jPushDTO.setPushType(pushType);
			jPushDTO.setOfflinePush(isOfflinePush);
			jPushDTO.setApnsTest(istest);
			jPushClient.saveAndPushMsgToList(jPushDTO);
			succUsers.addAll(lstUserIdSet);
		} catch (Exception e) {
			log.error("推送消息给{}设备用户异常", PlatformOsTypeEnum.getTypeName(osType), e);
		}
	}

}
