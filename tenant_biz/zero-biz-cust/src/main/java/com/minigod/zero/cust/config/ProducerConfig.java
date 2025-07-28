package com.minigod.zero.cust.config;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.mq.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Bean
    public ProducerFactory producerFactory() {
		return new ProducerFactory()
			.addProducer(MqTopics.BPM_COMPANY_AUTHOR_CUST_INFO, "bpm", AddMessageReq.class);
    }

}
