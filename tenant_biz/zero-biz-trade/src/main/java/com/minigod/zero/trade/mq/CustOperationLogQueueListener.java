package com.minigod.zero.trade.mq;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.mq.pulsar.annotation.PulsarConsumer;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.service.ICustOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class CustOperationLogQueueListener {

	@Resource
	private ICustOperationLogService custOperationLogService;

	@PulsarConsumer(topic = MqTopics.CUST_OPERATION_LOG_MESSAGE, clazz = AddMessageReq.class, subscriptionType = SubscriptionType.Shared, subscriptionName = MqTopics.CUST_OPERATION_LOG_MESSAGE)
	public void onMessage(AddMessageReq message) {
		try{
			if (message != null) {
				if (MqTopics.CUST_OPERATION_LOG_MESSAGE.equals(message.getTopic())) {
					long start = System.currentTimeMillis() ;
					CustOperationLogEntity entity = JSON.parseObject(message.getMessage(),CustOperationLogEntity.class);
					if(null != entity){
						entity.setCreateTime(new Date());
						custOperationLogService.save(entity);
					}
					long end = System.currentTimeMillis() - start;
					log.info("cost:" + end +",CustOperationLogQueueListener msg:"+ message.getMessage());
				}
			}
		}catch (Exception e){
			log.error("保存用户操作日志异常",e);
		}
	}
}
