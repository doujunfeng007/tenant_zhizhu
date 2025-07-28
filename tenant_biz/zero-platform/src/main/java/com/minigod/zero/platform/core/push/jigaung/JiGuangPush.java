package com.minigod.zero.platform.core.push.jigaung;

import cn.jpush.api.push.PushResult;
import cn.jpush.api.schedule.ScheduleResult;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.platform.core.*;
import com.minigod.zero.platform.core.push.PushChannel;
import com.minigod.zero.platform.core.push.PushMessage;
import com.minigod.zero.platform.entity.PlatformMessageProperties;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/13 10:07
 * @description：包装极光推送
 */
@Slf4j
@Component
public class JiGuangPush implements PushChannel {

	private static volatile JiGuangProperties jiGuangProperties ;

	@Autowired
	private MessageMapperManager platformContentMapperManger;

	@Override
	public SendResult send(Message message) {
		PushMessage pushMessage = (PushMessage) message;

		SendResult sendResult = new SendResult();
		sendResult.setMsgId(message.getMsgId());
		sendResult.setMessageType(MessageType.PUSH);
		sendResult.setChannel(com.minigod.zero.platform.enums.PushChannel.JG.getCode());

		if (jiGuangProperties == null){
			getInstance(message.getTenantId());
		}

		if (pushMessage.getSendWay() == 0) {
			handlePushResult(sendResult, PushUtil.sendPush( jiGuangProperties.secret,jiGuangProperties.appKey, pushMessage));
		} else {
			handleScheduleResult(sendResult, PushUtil.delayedSendPush(jiGuangProperties.secret,jiGuangProperties.appKey, pushMessage));
		}

		return sendResult;
	}

	// Helper method to handle PushResult
	private void handlePushResult(SendResult sendResult, PushResult result) {
		log.info("极光推送返回结果：{}", JSONObject.toJSONString(result));
		if (!result.isResultOK()) {
			sendResult.setStatus(ResultCode.INTERNAL_ERROR.getCode());
			sendResult.setErrorMsg(result.error.getMessage());
		} else {
			sendResult.setSid(String.valueOf(result.msg_id));
			sendResult.setStatus(ResultCode.OK.getCode());
		}
	}

	// Helper method to handle ScheduleResult
	private void handleScheduleResult(SendResult sendResult, ScheduleResult result) {
		log.info("极光定时推送返回结果：{}", JSONObject.toJSONString(result));
		if (!result.isResultOK()) {
			sendResult.setStatus(ResultCode.INTERNAL_ERROR.getCode());
			sendResult.setErrorMsg(result.toString());
		} else {
			sendResult.setSid(result.getSchedule_id());
			sendResult.setStatus(ResultCode.OK.getCode());
		}
	}

	@Override
	public PlatformMessageProperties messageProperties(String tenantId) {
		return platformContentMapperManger.messageProperties().messageProperties(tenantId, MessageType.PUSH.getValue());
	}

	@Override
	public String pushChannelType() {
		return String.valueOf(com.minigod.zero.platform.enums.PushChannel.JG.getCode());
	}

	@Builder
	static class JiGuangProperties{
		private String appKey;
		private String secret;
	}

	public JiGuangProperties getInstance(String tenantId) {
		// 第一次检查实例是否存在
		if (jiGuangProperties == null) {
			synchronized (JiGuangProperties.class) {
				// 第二次检查实例是否存在
				if (jiGuangProperties == null) {
					PlatformMessageProperties messageProperties = messageProperties(tenantId);
					if (messageProperties == null){
						throw new ZeroException("未配置 appId");
					}

					jiGuangProperties = JiGuangProperties.builder()
						.appKey(messageProperties.getAppId())
						.secret(messageProperties.getAppKey())
						.build();
				}
			}
		}
		return jiGuangProperties;
	}
}
