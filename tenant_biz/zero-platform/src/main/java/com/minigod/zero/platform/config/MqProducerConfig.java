package com.minigod.zero.platform.config;

import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.mq.pulsar.producer.ProducerFactory;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.dto.PlatformEmailStatementDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MqProducerConfig {

	@Value("${spring.profiles.active:unknown}")
	private String activeProfile;

	@Bean
	public ProducerFactory producerFactory() {
		return new ProducerFactory()
			.addProducer(MqTopics.MOBILE_MSG + "_" + activeProfile, SendSmsDTO.class)
			.addProducer(MqTopics.EMAIL_MSG + "_" + activeProfile, SendEmailDTO.class)
			.addProducer(MqTopics.NOTIFY_MSG + "_" + activeProfile, SendNotifyDTO.class)
			.addProducer(MqTopics.SENDCLOUD_EMAIL_SYNC_MESSAGE , PlatformEmailStatementDTO.class);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
