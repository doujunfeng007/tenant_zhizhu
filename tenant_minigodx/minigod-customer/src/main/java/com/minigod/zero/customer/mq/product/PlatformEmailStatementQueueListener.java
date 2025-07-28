package com.minigod.zero.customer.mq.product;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.common.constant.MqTopics;
import com.minigod.zero.customer.dto.PlatformEmailStatementDTO;
import com.minigod.zero.customer.entity.CustomerFile;
import com.minigod.zero.customer.entity.CustomerStatementFileHistoryEntity;
import com.minigod.zero.customer.mapper.CustomerFileMapper;
import com.minigod.zero.customer.mapper.CustomerStatementFileHistoryMapper;
import com.minigod.zero.mq.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.mq.product.PlatformEmailStatementQueueListener
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/12/05 10:38
 * @Version: 1.0
 */
@Slf4j
@Component
public class PlatformEmailStatementQueueListener {
	@Resource
	private CustomerStatementFileHistoryMapper customerStatementFileHistoryMapper;
	@Resource
	private CustomerFileMapper customerFileMapper;

	@PulsarConsumer(topic = MqTopics.SENDCLOUD_EMAIL_SYNC_MESSAGE, clazz = PlatformEmailStatementDTO.class, subscriptionType = SubscriptionType.Shared, subscriptionName = MqTopics.SENDCLOUD_EMAIL_SYNC_MESSAGE)
	public void onMessage(PlatformEmailStatementDTO dto) {
		try{
			if (dto != null) {
				try {
					log.info("收到邮件同步记录消息：{}", JSON.toJSONString(dto));
					CustomerStatementFileHistoryEntity customerStatementFileHistoryEntity = customerStatementFileHistoryMapper.selectOne(Wrappers.<CustomerStatementFileHistoryEntity>lambdaQuery()
						.eq(CustomerStatementFileHistoryEntity::getSendEmailId, dto.getSendId()));
					if (customerStatementFileHistoryEntity!= null) {
						CustomerFile customerFile = customerFileMapper.selectById(customerStatementFileHistoryEntity.getStatementFileId());
						if (customerFile!= null) {
							customerFile.setStatus(dto.getSendStatus());
							customerFile.setErrorMsg(dto.getErrorMsg());
							customerFile.setUpdateTime(new Date());
							customerFileMapper.updateById(customerFile);
						}
					}
				} catch (Exception e) {
					log.error("回调邮件同步记录 操作异常",e);
				}
			}
		}catch (Exception e){
			log.error("回调邮件同步记录异常",e);
		}
	}
}
