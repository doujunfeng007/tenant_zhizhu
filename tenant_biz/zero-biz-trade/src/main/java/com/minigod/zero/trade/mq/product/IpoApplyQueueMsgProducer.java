package com.minigod.zero.trade.mq.product;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.mq.pulsar.producer.ProducerCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class IpoApplyQueueMsgProducer {
	@Resource
	private ProducerCollector producerCollector;

	public boolean sendMsg(AddMessageReq msg){
		try {
			producerCollector.getProducer(MqTopics.IPO_APPLY).sendAsync(msg);
		} catch (Exception e) {
			log.error("IPO认购推送", e);
			return false;
		}
		return true;
	}
}
