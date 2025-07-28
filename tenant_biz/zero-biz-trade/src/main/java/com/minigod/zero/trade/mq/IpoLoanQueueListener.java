package com.minigod.zero.trade.mq;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.cache.IpoApplyCache;
import com.minigod.zero.biz.common.cache.IpoApplyRecordCache;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.mq.pulsar.annotation.PulsarConsumer;
import com.minigod.zero.trade.service.IIpoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * ipo垫资异步消费
 */
@Slf4j
@Component
public class IpoLoanQueueListener{

	@Autowired
    private IIpoService ipoService;
    @Autowired
    private ZeroRedis zeroRedis;

    /**
     * ipo垫资消息
     *
     * @param message
     */
	@PulsarConsumer(topic = MqTopics.IPO_LOAN_APPLY, clazz = AddMessageReq.class, subscriptionType = SubscriptionType.Shared)
    public void onMessage(AddMessageReq message) {
        try {
            if (message == null) {
                return;
            }
            if (MqTopics.IPO_LOAN_APPLY.equals(message.getTopic())) {
                log.info("receive ipoLoan message: {}", message.getMessage());
                IpoApplyCache cache = JSON.parseObject(message.getMessage(), IpoApplyCache.class);
                //ipo使用抵扣券
                IpoVO ipoVO = cache.getIpoVO();
                if (ipoVO == null) {
					log.error("ipoLoan failed, ipoVO is null");
					return;
				}
				if (null != ipoVO.getRewardId() && ipoVO.getRewardId() > 0) {
				  /*  Integer subType = IpoConstant.ApplyType.FINANCING.equals(ipoVO.getType()) ? CommonEnums.SubRewardTypeEnum.TYP_1.value : CommonEnums.SubRewardTypeEnum.TYP_2.value;
					userRecord = goldRedEnvelopeService.useVipReward(ipoVO.getUserId(), subType, ipoVO.getRewardId());*/
					clearRedisApplyRecord(ipoVO);//清理redis中的垫资认购申请
				}
				R<Object> rt = ipoService.applyCommit(true, cache.getIpoVO(), cache.getParamJson(), null);
				if (rt.isSuccess()) {
					log.info("ipoLoan success, fundAccount={}, assetId={}", ipoVO.getCapitalAccount(), ipoVO.getAssetId());
					return;
				}
				log.error("ipoLoan failed！");
            }
        } catch (Exception e) {
            log.error("async ipoLoan error", e);
        }
    }

    /**
     * 清空redis里的认购请求
     *
     * @param ipoVO
     */
    private void clearRedisApplyRecord(IpoVO ipoVO) {
        IpoApplyRecordCache recordCache = zeroRedis.protoGet(ipoVO.getCapitalAccount(),IpoApplyRecordCache.class);
        if (recordCache != null && CollectionUtils.isNotEmpty(recordCache.getList())) {
            Iterator<IpoVO> ite = recordCache.getList().iterator();
            while (ite.hasNext()) {
                IpoVO cacheIpoVO = ite.next();
                if (cacheIpoVO.getAssetId().equalsIgnoreCase(ipoVO.getAssetId())) {
                    //清理已经提交到柜台的缓存
                    ite.remove();
                }
            }
            if (CollectionUtils.isNotEmpty(recordCache.getList())) {
                //更新redis缓存
				zeroRedis.protoSet(ipoVO.getCapitalAccount(),recordCache,300);
            } else {
                //fundAccount下没有缓存记录，清空key
				zeroRedis.hDel(IpoApplyRecordCache.class,ipoVO.getCapitalAccount());
            }
        }
    }

}
