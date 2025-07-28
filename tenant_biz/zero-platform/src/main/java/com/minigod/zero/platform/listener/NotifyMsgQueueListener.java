package com.minigod.zero.platform.listener;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.core.MessageSendManager;
import com.minigod.zero.platform.core.push.PushMessage;
import com.minigod.zero.mq.pulsar.annotation.PulsarConsumer;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MQ推送消息监听
 *
 * @author minigod
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class NotifyMsgQueueListener {

	@Autowired
	private MessageSendManager messageSendManager;

	@PulsarConsumer(topic = MqTopics.NOTIFY_MSG + "_${spring.profiles.active:unknown}", clazz = SendNotifyDTO.class, subscriptionType = SubscriptionType.Shared, subscriptionName = "NotifyMsgQueueListener")
	public void receiveNotifyMsg(SendNotifyDTO notifyMsg) {
		if (null == notifyMsg) {
			return;
		}
		PushMessage pushMessage = new PushMessage();
		BeanUtils.copyProperties(notifyMsg,pushMessage);
		pushMessage.setContent(notifyMsg.getMsgContent());
		pushMessage.setTemplateParam(notifyMsg.getParams());
		pushMessage.setUserIds(notifyMsg.getLstToUserId());
		if (notifyMsg.getSendTime() != null){
			pushMessage.setSendTime(DateUtil.parseDateToStr(
				StringUtil.isNotBlank(notifyMsg.getSendTimeStr()) ? notifyMsg.getSendTimeStr() : DateUtil.YYYY_MM_DD_HH_MM_SS,
				notifyMsg.getSendTime()));
		}
		pushMessage.setLanguage(notifyMsg.getLang());
		pushMessage.setDeviceList(notifyMsg.getDeviceInfoList());
		messageSendManager.send(pushMessage);
	}
}
