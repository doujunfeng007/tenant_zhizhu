package com.minigod.zero.platform.listener;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.mq.pulsar.annotation.PulsarConsumer;
import com.minigod.zero.platform.core.MessageSendManager;
import com.minigod.zero.platform.core.email.EmailMessage;
import com.minigod.zero.platform.dto.SendEmailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MQ邮件消息监听
 *
 * @author minigod
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailMsgQueueListener {

	@Autowired
	private MessageSendManager messageSendManager;

	@PulsarConsumer(topic = MqTopics.EMAIL_MSG + "_${spring.profiles.active:unknown}", clazz = SendEmailDTO.class, subscriptionType = SubscriptionType.Shared, subscriptionName = "EmailMsgQueueListener")
	public void receiveEmailMsg(SendEmailDTO sendEmailDTO) {
		if (null == sendEmailDTO) {
			return;
		}
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setLanguage(sendEmailDTO.getLang());
		emailMessage.setTitle(sendEmailDTO.getTitle());
		emailMessage.setContent(sendEmailDTO.getContent());
		emailMessage.setAccepts(sendEmailDTO.getAccepts());
		emailMessage.setTemplateCode(sendEmailDTO.getCode());
		emailMessage.setTenantId(sendEmailDTO.getTenantId());
		emailMessage.setCarbonCopy(sendEmailDTO.getCarbonCopy());
		emailMessage.setTitleParam(sendEmailDTO.getTitleParam());
		emailMessage.setAttachmentUrls(sendEmailDTO.getAttachmentUrls());
		emailMessage.setBlindCarbonCopy(sendEmailDTO.getBlindCarbonCopy());
		emailMessage.setTemplateParam(sendEmailDTO.getList());
		messageSendManager.send(emailMessage);

	}
}
