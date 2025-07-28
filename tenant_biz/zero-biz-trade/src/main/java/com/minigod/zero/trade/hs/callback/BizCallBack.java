package com.minigod.zero.trade.hs.callback;


import com.hundsun.t2sdk.impl.client.ClientSocket;
import com.hundsun.t2sdk.interfaces.ICallBackMethod;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import com.minigod.zero.trade.hs.constants.T2sdkAsyncCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 异步发送请求到恒生柜台，在此回调方法中接收结果
 */
public class BizCallBack implements ICallBackMethod {
    private Logger logger = LoggerFactory.getLogger(BizCallBack.class);

    @Override
    public void execute(IEvent event, ClientSocket arg1) {
        long senderId = event.getIntegerAttributeValue(EventTagdef.TAG_EVENT_ID);
        if (event.getReturnCode() != EventReturnCode.I_OK) {
            logger.info("t2sdk.response error, senderId={}, errorNo={}, errorInfo={}", senderId, event.getErrorNo(), event.getErrorInfo());
        }
        CountDownLatch countDownLatch = T2sdkAsyncCache.requestMap.get(senderId);
        if (countDownLatch != null) {
            T2sdkAsyncCache.responseMap.put(senderId, event);//响应结果存入responseMap
            countDownLatch.countDown();//唤醒等待线程
        } else {
            //等待超时的响应
            logger.error("t2sdk.response timeout, senderId={}", senderId);
        }
    }
}
