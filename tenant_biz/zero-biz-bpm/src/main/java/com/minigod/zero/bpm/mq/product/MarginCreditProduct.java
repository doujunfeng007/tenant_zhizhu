package com.minigod.zero.bpm.mq.product;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.mq.pulsar.producer.ProducerCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class MarginCreditProduct {
	@Resource
	private ProducerCollector producerCollector;

	public boolean sendMsg(String msg) {
		try {
			producerCollector.getProducer(MqTopics.MARGIN_CREDIT_APPLY_TOPIC).sendAsync(msg);
		} catch (Exception e) {
			log.error("信用额度调整申请推送至mq异常", e);
			return false;
		}
		return true;
	}
}
