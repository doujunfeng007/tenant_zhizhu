package com.minigod.zero.bpm.mq.product;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.mq.pulsar.producer.ProducerCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Zhe.Xiao
 */
@Slf4j
@Component
public class DepositApplyProducer {

	@Resource
	private ProducerCollector producerCollector;

	public boolean sendMsg(AddMessageReq message) {
		try {
			producerCollector.getProducer(MqTopics.DEPOSIT_APPLY_TOPIC).sendAsync(message);
		} catch (Exception e) {
			log.error("入金申请推送MQ异常:{}", JSON.toJSONString(message), e);
			return false;
		}
		log.info("入金申请推送MQ成功: {}", JSON.toJSONString(message));
		return true;
	}

}
