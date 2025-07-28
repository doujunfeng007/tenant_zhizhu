package com.minigod.zero.platform.service;

import com.google.common.collect.Sets;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import com.minigod.zero.platform.enums.PushTypeEnum;
import com.minigod.zero.platform.handler.StrongMsgHandler;
import com.minigod.zero.platform.handler.WeakMsgHandler;
import com.minigod.zero.platform.utils.PlatformErrorUtil;
import com.minigod.zero.platform.utils.PlatformUtil;
import com.minigod.zero.resource.dto.JPushDTO;
import com.minigod.zero.resource.feign.IJPushClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class PushNotifyMsgAsyncNew {

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
	private StrongMsgHandler strongMsgHandler;
	@Resource
	private WeakMsgHandler weakMsgHandler;
	@Resource
	private IJPushClient jPushClient;

	/**
	 * @param devType
	 * @param notificationExtras
	 * @param title
	 * @param pushType
	 * @return
	 */
	public R sendMsgToApp(int devType, String notificationExtras, String title, int pushType) {
		log.info("发送给全站APP请求参数：devType " + PlatformOsTypeEnum.getTypeName(devType));
		log.info("发送给全站APP请求参数：notificationExtras " + notificationExtras);
		log.info("发送给全站APP请求参数：title " + title);
		log.info("发送给全站APP请求参数：pushType " + pushType);
		R respVO = R.success();

		// 判断推送类型如果是强类型消息
		if (PushTypeEnum.STRONG_MSG.getTypeValue().equals(pushType)) {
			if (devType == PlatformOsTypeEnum.OS_ALL.getTypeValue() || devType == PlatformOsTypeEnum.OS_ANDROID.getTypeValue()) {
				try {
					log.info("方法：sendMsgToApp ======= 安卓弱消息(极光)  ======");
					String authorization = PlatformUtil.getAuthorization(PlatformOsTypeEnum.OS_ANDROID.getTypeValue(), IOS_APP_KEY, IOS_MASTER_SECRET, AOS_APP_KEY, AOS_MASTER_SECRET);
					JPushDTO jPushDTO = new JPushDTO();
					jPushDTO.setAuthorization(authorization);
					jPushDTO.setTitle(title);
					jPushDTO.setTransMessage(notificationExtras);
					jPushDTO.setOsType(PlatformOsTypeEnum.OS_ANDROID.getTypeValue());
					jPushDTO.setPushType(pushType);
					jPushDTO.setOfflinePush(true);
					jPushDTO.setApnsTest(istest);
					jPushClient.saveAndPushMsgToApp(jPushDTO);
				} catch (Exception e) {
					log.error("消息推送失败", e);
					return PlatformErrorUtil.getPushErrorMsg(respVO, "发送通知给ANEROID用户失败" + e);
				}
			}

			if (devType == PlatformOsTypeEnum.OS_ALL.getTypeValue() || devType == PlatformOsTypeEnum.OS_IOS.getTypeValue()) {
				try {
					log.info("方法：sendMsgToApp ======= 苹果弱消息(极光)  ======");
					String authorization = PlatformUtil.getAuthorization(PlatformOsTypeEnum.OS_IOS.getTypeValue(), IOS_APP_KEY, IOS_MASTER_SECRET, AOS_APP_KEY, AOS_MASTER_SECRET);
					JPushDTO jPushDTO = new JPushDTO();
					jPushDTO.setAuthorization(authorization);
					jPushDTO.setTitle(title);
					jPushDTO.setTransMessage(notificationExtras);
					jPushDTO.setOsType(PlatformOsTypeEnum.OS_IOS.getTypeValue());
					jPushDTO.setPushType(pushType);
					jPushDTO.setOfflinePush(true);
					jPushDTO.setApnsTest(istest);
					jPushClient.saveAndPushMsgToApp(jPushDTO);
				} catch (Exception e) {
					log.error("消息推送失败", e);
					return PlatformErrorUtil.getPushErrorMsg(respVO, "发送通知给IOS用户失败" + e);
				}
			}
		}
		// 判断推送的类型如果是弱类型消息
		if (PushTypeEnum.WEAK_MSG.getTypeValue().equals(pushType)) {
			if (devType == PlatformOsTypeEnum.OS_ALL.getTypeValue() || devType == PlatformOsTypeEnum.OS_ANDROID.getTypeValue()) {
				// ANDROID设备
				try {
					log.info("方法：sendMsgToApp ======= 安卓弱消息(极光)  ======");
					// 给安卓用户发送弱提醒消息
					String authorization = PlatformUtil.getAuthorization(PlatformOsTypeEnum.OS_ANDROID.getTypeValue(), IOS_APP_KEY, IOS_MASTER_SECRET, AOS_APP_KEY, AOS_MASTER_SECRET);
					JPushDTO jPushDTO = new JPushDTO();
					jPushDTO.setAuthorization(authorization);
					jPushDTO.setTitle(title);
					jPushDTO.setTransMessage(notificationExtras);
					jPushDTO.setOsType(PlatformOsTypeEnum.OS_ANDROID.getTypeValue());
					jPushDTO.setPushType(pushType);
					jPushDTO.setOfflinePush(true);
					jPushDTO.setApnsTest(istest);
					jPushClient.saveAndPushMsgToApp(jPushDTO);
				} catch (Exception e) {
					log.error("消息推送失败", e);
					return PlatformErrorUtil.getPushErrorMsg(respVO, "发送透传给ANEROID用户失败" + e);
				}
			}

			if (devType == PlatformOsTypeEnum.OS_ALL.getTypeValue() || devType == PlatformOsTypeEnum.OS_IOS.getTypeValue()) {
				// IOS设备
				try {
					log.info("方法：sendMsgToApp ======= 苹果弱消息(极光)  ======");
					// 给IOS用户发送弱提醒消息
					String authorization = PlatformUtil.getAuthorization(PlatformOsTypeEnum.OS_IOS.getTypeValue(), IOS_APP_KEY, IOS_MASTER_SECRET, AOS_APP_KEY, AOS_MASTER_SECRET);
					JPushDTO jPushDTO = new JPushDTO();
					jPushDTO.setAuthorization(authorization);
					jPushDTO.setTitle(title);
					jPushDTO.setTransMessage(notificationExtras);
					jPushDTO.setOsType(PlatformOsTypeEnum.OS_IOS.getTypeValue());
					jPushDTO.setPushType(pushType);
					jPushDTO.setOfflinePush(true);
					jPushDTO.setApnsTest(istest);
					jPushClient.saveAndPushMsgToApp(jPushDTO);
				} catch (Exception e) {
					log.error("消息推送异常", e);
					return PlatformErrorUtil.getPushErrorMsg(respVO, "发送透传给IOS用户异常" + e);
				}
			}
		}
		respVO.setData(Sets.newHashSet(0));
		return respVO;
	}

	/**
	 * 给用户推送消息
	 *
	 * @return
	 */
	public R sendMsgToUserList(int devType, List<Long> lstUserIds, String title, String notificationExtras, boolean isOfflinePush, int pushType,List<CustomerDeviceInfoVO> deviceList) {
		log.info("发送给list请求参数 devType：{}", PlatformOsTypeEnum.getTypeName(devType));
		log.info("发送给list请求参数 lstUserIds:{}", lstUserIds);
		log.info("发送给list请求参数 title:{}", title);
		log.info("发送给list请求参数 notificationExtras:{}", notificationExtras);
		log.info("发送给list请求参数 isOfflinePush:{}", isOfflinePush);
		log.info("发送给list请求参数 pushType:{}", pushType);

		R respVO = R.success();

		Set<Long> succUsers = Sets.newHashSet();
		if (PushTypeEnum.STRONG_MSG.getTypeValue().equals(pushType)) {
			R responseVO = strongMsgHandler.strongMsgHandler(devType, lstUserIds, notificationExtras, isOfflinePush, pushType, title, succUsers,deviceList);
			if (responseVO != null) {
				return responseVO;
			}
		} else if (PushTypeEnum.WEAK_MSG.getTypeValue().equals(pushType)) {
			R responseVO = weakMsgHandler.weakMsgHandler(devType, lstUserIds, notificationExtras, isOfflinePush, pushType, title, succUsers,deviceList);
			if (responseVO != null) {
				return responseVO;
			}
		}

		respVO.setData(succUsers);
		return respVO;
	}

}
