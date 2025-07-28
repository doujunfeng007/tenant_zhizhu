package com.minigod.zero.platform.listener;

import com.google.common.util.concurrent.*;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.core.sms.model.SmsData;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.mq.pulsar.annotation.PulsarConsumer;
import com.minigod.zero.platform.core.MessageSendManager;
import com.minigod.zero.platform.core.sms.SmsMessage;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.service.IPlatformSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * MQ短信消息监听
 *
 * @author minigod
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MobileMsgQueueListener {

	@Autowired
	private MessageSendManager messageSendManager;

	@PulsarConsumer(topic = MqTopics.MOBILE_MSG + "_${spring.profiles.active:unknown}", clazz = SendSmsDTO.class, subscriptionType = SubscriptionType.Shared, subscriptionName = "MobileMsgQueueListener")
	public void receiveMobileMsg(SendSmsDTO sendSmsDTO) {
		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setAreaCode(sendSmsDTO.getAreaCode());
		smsMessage.setPhoneNumber(sendSmsDTO.getPhone());
		smsMessage.setTemplateParam(sendSmsDTO.getParams());
		smsMessage.setTemplateCode(sendSmsDTO.getTemplateCode());
		messageSendManager.send(smsMessage);
	}
}
