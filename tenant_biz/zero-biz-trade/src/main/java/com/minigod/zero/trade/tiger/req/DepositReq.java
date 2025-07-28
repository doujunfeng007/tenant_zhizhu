package com.minigod.zero.trade.tiger.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName DepositReq.java
 * @Description TODO
 * @createTime 2025年02月18日 18:47:00
 */
@Data
public class DepositReq {

    private String accountId;

    /**1015202504
     * 外部id，要求全局唯一。生成规则：{brokerClientId}_{加盟商侧自行生成uuid}（总长度需小于32个字符串）
     */
    private String externalId;

    /**
     *  USD  HKD  SGD AUD
     */
    private String currency;

    private BigDecimal amount;
}
