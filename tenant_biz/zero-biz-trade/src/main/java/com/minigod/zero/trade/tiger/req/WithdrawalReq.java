package com.minigod.zero.trade.tiger.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chen
 * @ClassName Withdrawal.java
 * @Description TODO
 * @createTime 2025年02月19日 15:38:00
 */
@Data
public class WithdrawalReq {

    private String accountId;

    /**
     * 账户子类型(用于隔离资金交易不同品种， 默认值为 SEC) SEC
     */
    private String segType;

    private BigDecimal amount;

    /**
     *  USD  HKD  SGD AUD
     */
    private String currency;

    private String reason;

    /**
     * 加盟商的唯一出金 id，在出金历史中需要全局唯一。生成规则：{brokerClientId}_{加盟商侧自行生成uuid}（总长度需小于32个)
     */
    private String externalId;
}
