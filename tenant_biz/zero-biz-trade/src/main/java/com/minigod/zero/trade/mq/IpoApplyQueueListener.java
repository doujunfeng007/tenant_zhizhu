package com.minigod.zero.trade.mq;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.mq.pulsar.annotation.PulsarConsumer;
import com.minigod.zero.trade.entity.IpoFinanceQueueOrderEntity;
import com.minigod.zero.trade.service.IIpoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ipo融资排队认购异步推送
 */
@Slf4j
@Component
public class IpoApplyQueueListener  {
    @Resource
    private IIpoService ipoService;

	@PulsarConsumer(topic = MqTopics.IPO_APPLY, clazz = AddMessageReq.class, subscriptionType = SubscriptionType.Shared)
    public void onMessage(AddMessageReq message) {
        try {
            if (message != null) {
                if (MqTopics.IPO_APPLY.equals(message.getTopic())) {
                    long start = System.currentTimeMillis() ;
                    IpoFinanceQueueOrderEntity order = JSON.parseObject(message.getMessage(), IpoFinanceQueueOrderEntity.class);
                    if(null != order) {
						ipoService.createIpoQueueOrder(order);
                    }
                    long end = System.currentTimeMillis() - start;
                    log.info("cost:" + end +",IpoApplyQueueListener msg:"+ message.getMessage());
                }
            }
        }catch (Exception e){
            log.error("IpoApplyQueueListener error:",e);
        }
    }
}
