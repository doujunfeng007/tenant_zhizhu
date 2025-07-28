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
public class FundFreezeReq {

    private String externalId;

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

//    /**
//     * 冻结有效期开始
//     */
//    private Long validFrom;
//
//    /**
//     * 冻结有效期结束
//     */
//    private Long validTo;

    /**
     * 备注
     */
    private String remark;


    private Long transactTime;

    private Long businessDate;

}
