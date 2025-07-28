package com.minigod.zero.bpm.config;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.mq.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MqProducerConfig {
	@Bean
	public ProducerFactory producerFactory() {
		return new ProducerFactory()
			.addProducer(MqTopics.BPM_OPEN_ACCOUNT_TOPIC, String.class)
			.addProducer(MqTopics.MARGIN_CREDIT_APPLY_TOPIC, String.class)
			.addProducer(MqTopics.CUST_OPERATION_LOG_MESSAGE, AddMessageReq.class)
			.addProducer(MqTopics.WITHDRAWAL_APPLY_TOPIC, "bpm", AddMessageReq.class)
			.addProducer(MqTopics.DEPOSIT_APPLY_TOPIC, "bpm", AddMessageReq.class);
	}
}
