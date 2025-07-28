package com.minigod.zero.platform.core.push.jigaung;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.HmosNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.schedule.ScheduleResult;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.core.push.PushMessage;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import com.minigod.zero.platform.enums.PushTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/13 10:16
 * @description：
 */
public class PushUtil {

	private static final Logger LOG = LoggerFactory.getLogger(PushUtil.class);
	/**
	 * 定时任务推送默认名称
	 */
	private static String DEFAULT_SCHEDULE_NAME = "DigiFIN-Task";

	/**
	 * 发送定时推送
	 * @param pushMessage
	 * @return
	 */
	public static ScheduleResult delayedSendPush(String appKey,String secret,PushMessage pushMessage){

		PushPayload pushPayload = buildPushPayLoad(pushMessage);

		JPushClient jPushClient = createClient(appKey,secret);

		String scheduleName = DEFAULT_SCHEDULE_NAME;
		if (StringUtil.isNotBlank(pushMessage.getScheduleName())){
			scheduleName = pushMessage.getScheduleName();
		}

		ScheduleResult pushResult = null;
		try {
			pushResult = jPushClient.createSingleSchedule(scheduleName,
				pushMessage.getSendTime(),pushPayload,appKey,secret);
		} catch (APIConnectionException e) {
			LOG.error("极光定时推送连接异常：{}",e.getMessage());
		} catch (APIRequestException e) {
			LOG.error("极光定时推送接口调用异常：{}",e.getMessage());
		}
		return pushResult;
	}


	/**
	 * 普通发送
	 * @param pushMessage
	 * @return
	 */
	public static PushResult sendPush(String appKey,String secret,PushMessage pushMessage){

		PushPayload pushPayload = buildPushPayLoad(pushMessage);
		PushResult pushResult = null;
		try {
			JPushClient jPushClient = createClient(appKey,secret);
			LOG.info("推送参数：{}", JSONObject.toJSONString(pushPayload));
			pushResult = jPushClient.sendPush(pushPayload);
		}  catch (APIConnectionException e) {
			LOG.error("极光推送连接异常：{}",e.getMessage());
		} catch (APIRequestException e) {
			LOG.error("极光推送接口调用异常：{}",e.getMessage());
		}
		return pushResult;
	}




	/**
	 * 构建极光推送参数
	 * @param pushMessage
	 * @return
	 */
	public static PushPayload buildPushPayLoad(PushMessage pushMessage){

		PushPayload.Builder builder = PushPayload.newBuilder();

		setPlatform(builder,pushMessage);

		setAudience(builder,pushMessage);

		setNotification(builder,pushMessage);

		//setMessage(builder,pushMessage);
		setInAppMessage(builder,pushMessage);

		setOptions(builder,pushMessage);

		return builder.build();
	}

	/**
	 * 设置离线通知栏推送
	 * @param builder
	 * @param pushMessage
	 */
	private static void setInAppMessage(PushPayload.Builder builder,PushMessage pushMessage){
		builder.setInappMessage(
			InappMessage.newBuilder()
				.setInappMessage(pushMessage.isOfflinePush()).build());
	}

	/**
	 *
	 * 设置推送消息内容
	 * 同时设置附加参数
	 * @param builder
	 * @param pushMessage
	 */
	private static void setMessage(PushPayload.Builder builder,PushMessage pushMessage){
		Message.Builder messageBuilder = Message.newBuilder()
			.setMsgContent(pushMessage.getContent())
			.setTitle(pushMessage.getTitle());

		if (pushMessage.getExtras() != null){
			messageBuilder.addExtras(pushMessage.getExtras());
		}
		builder.setMessage(messageBuilder.build());
	}

	/**
	 * 设置推送标题
	 * @param builder
	 * @param pushMessage
	 */
	private static void setNotification(PushPayload.Builder builder,PushMessage pushMessage){
		Notification.Builder notificationBuilder = Notification.newBuilder().setAlert(pushMessage.getTitle());
		//兼容现在逻辑，app处理
		if (pushMessage.getDisplayGroup() != null){
			setExtras(notificationBuilder,pushMessage);
		}

		setIosNotification(notificationBuilder,pushMessage);

		setHmosNotification(notificationBuilder,pushMessage);

		setAndroidNotification(notificationBuilder,pushMessage);

		builder.setNotification(notificationBuilder.build());
	}

	/**
	 * ios 推送
	 * @param notificationBuilder
	 * @param pushMessage
	 */
	private static void setIosNotification(Notification.Builder notificationBuilder,PushMessage pushMessage){
		JSONObject alert = new JSONObject();
		alert.put("title",pushMessage.getTitle());
		alert.put("body",pushMessage.getContent());
		notificationBuilder.addPlatformNotification(IosNotification.newBuilder().setAlert(alert).build());
	}

	/**
	 * 安卓推送
	 * @param notificationBuilder
	 * @param pushMessage
	 */
	private static void setAndroidNotification(Notification.Builder notificationBuilder,PushMessage pushMessage){
		notificationBuilder.addPlatformNotification(AndroidNotification.newBuilder()
			.setAlert(pushMessage.getContent())
			.setTitle(pushMessage.getTitle())
			.build());
	}

	/**
	 * 鸿蒙推送
	 * @param notificationBuilder
	 * @param pushMessage
	 */
	private static void setHmosNotification(Notification.Builder notificationBuilder,PushMessage pushMessage){
		notificationBuilder.addPlatformNotification(HmosNotification.newBuilder()
			.setAlert(pushMessage.getContent())
			.setTitle(pushMessage.getTitle())
			.build());
	}
	/**
	 * 设置系统推送范围
	 * @param builder
	 * @param pushMessage
	 */
	private static void setPlatform(PushPayload.Builder builder,PushMessage pushMessage){
		if (pushMessage.getDevType() == PlatformOsTypeEnum.OS_ALL.getTypeValue()){
			builder.setPlatform(Platform.all());
		}
		if (pushMessage.getDevType() == PlatformOsTypeEnum.OS_ANDROID.getTypeValue()){
			builder.setPlatform(Platform.android_quickapp_hmos());
		}
		if (pushMessage.getDevType() == PlatformOsTypeEnum.OS_IOS.getTypeValue()){
			builder.setPlatform(Platform.ios_quickapp());
		}

	}

	/**
	 * 设置推送群体
	 * @param builder
	 * @param pushMessage
	 */
	private static void setAudience(PushPayload.Builder builder,PushMessage pushMessage){
		if (InformEnum.MsgGroupEnum.PERSON.getTypeCode().equals(pushMessage.getMsgGroup())){
			builder.setAudience(Audience.registrationId(pushMessage.getRegistrationIds()));
		}
		if (InformEnum.MsgGroupEnum.ALL.getTypeCode().equals(pushMessage.getMsgGroup())){
			builder.setAudience(Audience.all());
		}
	}

	/**
	 * 设置推送参数
	 * secondary_push 表示推送优先走极光，极光不在线再走厂商，厂商作为辅助
	 * apnsProduction true：表示推送生产环境，false：表示推送开发环境。
	 * @param builder
	 * @param pushMessage
	 */
	private static void setOptions(PushPayload.Builder builder,PushMessage pushMessage){
		Options.Builder optionBuild = Options.newBuilder()
			.setApnsProduction(pushMessage.isApnsProduction());

		if (PushTypeEnum.STRONG_MSG.getTypeValue().equals(pushMessage.getPushType())) {
			HashMap<String, JsonObject> map = new HashMap<>(5, 1);
			String[] channels = {"xiaomi", "huawei", "oppo", "vivo", "meizu", "fcm"};

			for (String channel : channels) {
				map.put(channel, createJsonObject("secondary_push"));
			}
			optionBuild = Options.newBuilder().setThirdPartyChannelV2(map);
		}
		builder.setOptions(optionBuild.build());
	}

	/**
	 * 兼容现有逻辑，设置附加参数，正确附加参数应该设置PushMessage#extras参数
	 * @param notificationBuilder
	 * @param pushMessage
	 */
	private static void setExtras(Notification.Builder notificationBuilder,PushMessage pushMessage){
		notificationBuilder
			.addPlatformNotification(AndroidNotification.newBuilder()
				.addExtras(getExtras(pushMessage)).build())

			.addPlatformNotification(IosNotification.newBuilder()
				.addExtras(getExtras(pushMessage)).build());
	}


	private static JsonObject createJsonObject(String distribution) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("distribution", distribution);
		return jsonObject;
	}


	private static Map<String,String> getExtras(PushMessage pushMessage){
		Map<String,String> extrasMap = new HashMap<>();
		extrasMap.put("group", String.valueOf(pushMessage.getDisplayGroup()));
		extrasMap.put("msg", pushMessage.getContent());
		extrasMap.put("title", pushMessage.getTitle());
		extrasMap.put("url", pushMessage.getUrl());
		return extrasMap;
	}

	/**
	 * 创建客户端
	 * @param appKey
	 * @param secret
	 * @return
	 */
	private static JPushClient createClient(String appKey,String secret){
		ClientConfig clientConfig = ClientConfig.getInstance();
		return new JPushClient(appKey,secret,null,clientConfig);
	}
}
