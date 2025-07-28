package com.minigod.zero.trade.afe.push;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.utils.MarketUtils;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.cust.apivo.CustTradePushInfoVO;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.feign.ICustDeviceClient;
import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.trade.afe.util.AfeUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 成交推送异步处理
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TradePushMsgProducer {
    private final IPlatformMsgClient platformMsgClient;
    private final ICustTradeClient custTradeClient;
    private final ICustDeviceClient custDeviceClient;
    private final RedisLockClient redisLockClient;
    private final ZeroRedis zeroRedis;

    public void sendMessage(OrderPushVO msg) {
        log.info("成交消息推送到客户：{}", JSON.toJSONString(msg));
        String key = CacheNames.TRADE_PUSH.concat(new StringBuilder().append(msg.getTradeAccount()).append(":")
            .append(msg.getStockCD()).append(":").append(msg.getOrderId()).toString());

        try {
            // 获取分布式锁
            int lockMills = 1000 * 30;// 锁定30秒
            boolean lock =
                redisLockClient.tryLock(key, LockType.REENTRANT, lockMills, lockMills, TimeUnit.MILLISECONDS);
            if (lock) {
                String valueKey = key.concat(":value");
                String exist = zeroRedis.get(valueKey);
                if (StringUtils.isNotBlank(exist)) {
                    log.info("消息已推送：{}", valueKey);
                    return;
                } else {
                    zeroRedis.setEx(valueKey, valueKey, CacheNames.TRADE_PUSH_EXPIRE_TIME);
                }
            }
        } catch (Exception e) {
            log.error("trade push sendMessage error", e);
        } finally {
            // 释放分布式锁
            redisLockClient.unLock(key, LockType.REENTRANT);
        }

        R<CustTradePushInfoVO> r = custTradeClient.getCustTradePushInfo(msg.getTradeAccount());
        if (!r.isSuccess() || r.getData() == null) {
            log.error("获取客户有效账号信息失败" + msg.getTradeAccount());
            return;
        }
        CustTradePushInfoVO tradePushInfo = r.getData();
        CustDeviceEntity custDevice = custDeviceClient.getCustDevice(tradePushInfo.getCustId());

        String market = msg.getMarketId();
        String assetId = "";
        if ("SEHK".equals(market)) {
            assetId = MarketUtils.translateHkAssetId(msg.getStockCD());
        } else if ("US".equals(market)) {
            assetId = MarketUtils.translateUsAssetId(msg.getStockCD());
        } else if ("CN".equals(market)) {
            assetId = MarketUtils.translateUsAssetId(msg.getStockCD());
        }

        StkInfo stkInfo = zeroRedis.protoGet(assetId, StkInfo.class);
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(msg.getPrice())); // 成交价格 0
        params.add(msg.getCurrCD()); // 币种 1
        params.add(AfeUtil.getEntrustBsDesc(msg.getSide(), custDevice.getLang())); // 卖出 2
        params.add(AfeUtil.getStockName(stkInfo, custDevice.getLang(), msg.getTName())); // 股票名称 3
        params.add(assetId); // 股票代码 4
        params.add(String.valueOf(Integer.valueOf(msg.getOstdQty()) / stkInfo.getLotSize())); // 成交手数 5
        params.add(msg.getOstdQty()); // 成交数量 6

        // push
        SendNotifyDTO dto = new SendNotifyDTO();
        List<Long> lstToUserId = new ArrayList<>();
        lstToUserId.add(tradePushInfo.getCustId());
        // 发送用户
        dto.setLstToUserId(lstToUserId);
        // 发送分组 消息显示分组 【12015-交易提醒 13001-行情提醒 13002-新股提醒 12007-服务通知 13003-活动推送 13004-热点资讯】
        dto.setDisplayGroup(MsgStaticType.DisplayGroup.TRADE_PUSH);
        // 发送消息模板
        dto.setTemplateCode(CommonTemplateCode.Push.TRADE_PUSH);
        // 模板中参数
        dto.setParams(params);
        dto.setLang(custDevice.getLang());
        platformMsgClient.sendNotify(dto);

        // email
        log.info("收到成交回报消息，发送邮件到客户：{}", msg);
        String entrustBs1 = "股票买入成功";
        String entrustBs2 = "股票卖出成功";
        String stockName = msg.getTName();
        SendEmailDTO emailDTO = new SendEmailDTO();
        if (custDevice.getLang() == null || "zh-hans".equals(custDevice.getLang())) {
            emailDTO.setTitle("交易完成");
            entrustBs1 = "股票买入成功";
            entrustBs2 = "股票卖出成功";
            stockName = AfeUtil.getStockName(stkInfo, "zh-hans", msg.getTName());
        } else if ("zh-hant".equals(custDevice.getLang())) {
            emailDTO.setTitle("交易完成");
            entrustBs1 = "股票買入成功";
            entrustBs2 = "股票賣出成功";
            stockName = AfeUtil.getStockName(stkInfo, "zh-hant", msg.getTName());
        } else {
            emailDTO.setTitle("Transaction is complete");
        }

        List<String> list = new ArrayList<>();
        // 买卖方向 1-买入，2-卖出
        if ("B".equals(msg.getSide())) {
            list.add(entrustBs1); // 标题 0
            list.add("Stock purchased successfully"); // 标题内容-英文 1
            list.add(entrustBs1); // 标题内容 2
            list.add("stock purchased successfully"); // 标题内容 3
        } else {
            list.add(entrustBs2);
            list.add("Stock sold successfully");
            list.add(entrustBs2);
            list.add("stock sold successfully");
        }

        list.add(tradePushInfo.getCustName()); // 客户名称 4
        list.add(new StringBuffer().append(tradePushInfo.getGivenNameSpell()).append(" ")
            .append(tradePushInfo.getFamilyNameSpell()).toString()); // 客户名称-英文 5
        list.add(tradePushInfo.getTradeAccount()); // 账户号码 6
        list.add(stockName); // 股票名称 7
        list.add(AfeUtil.getStockName(stkInfo, "en", msg.getTName())); // 股票名称-英文 8
        list.add(assetId); // 股票代码 9
        list.add(msg.getPrice()); // 成交价格 10
        list.add(msg.getOstdQty()); // 成交数量 11
        list.add(DateUtil.format(new Date(), DateUtil.PATTERN_DATE)); // 日期 12
        list.add(DateUtil.format(new Date(), DateUtil.PATTERN_DATE)); // 日期-英文 13
        List<String> accepts = new ArrayList<>();
        accepts.add(tradePushInfo.getEmail());
        // 接收人
        emailDTO.setAccepts(accepts);
        // 模板编码
        emailDTO.setCode(CommonTemplateCode.Email.TRADE_COMPLETE);
        // 模板参数
        emailDTO.setList(list);
        emailDTO.setLang(custDevice.getLang());
        platformMsgClient.sendEmail(emailDTO);
    }
}
