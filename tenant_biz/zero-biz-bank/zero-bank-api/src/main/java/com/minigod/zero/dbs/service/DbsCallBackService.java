package com.minigod.zero.dbs.service;

import com.minigod.zero.core.tool.api.R;

/**
 * @ClassName: DbsCallBackService
 * @Description: DBS回调加密报文处理
 * @Author chenyu
 * @Date 2024/3/21
 * @Version 1.0
 */
public interface DbsCallBackService {
    /**
     * icn
     * @param text 银行报文信息
     */
    R<String> icn(String text);
    /**
     * edda-Ack2
     * @param text 银行报文信息
     */
    R<String> eddaAck2(String text);

    /**
     * edda-Ack2
     * @param text 银行报文信息
     */
    R<String> remitAck(String text);

    /**
     * edda-Ack2
     * @param text 银行报文信息
     */
    R<String> idn(String text);
}
