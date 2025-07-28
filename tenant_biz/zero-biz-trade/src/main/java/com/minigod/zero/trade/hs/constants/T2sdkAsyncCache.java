package com.minigod.zero.trade.hs.constants;

import com.hundsun.t2sdk.interfaces.share.event.IEvent;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class T2sdkAsyncCache implements Serializable {
    private static final long serialVersionUID = 1L;
    //生成异步请求全局唯一id
    public static AtomicLong autoIncrement = new AtomicLong(0);
    //	缓存每个柜台请求id，senderId=CountDownLatch
    public static final Map<Long, CountDownLatch> requestMap = new ConcurrentHashMap<>();
    //	缓存每个柜台请求的异步响应，senderId=IEvent
    public static final Map<Long, IEvent> responseMap = new ConcurrentHashMap<>();

}
