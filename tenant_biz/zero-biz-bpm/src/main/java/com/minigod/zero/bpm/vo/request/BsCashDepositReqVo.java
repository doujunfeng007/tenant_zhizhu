package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BsCashDepositReqVo {
    private String clientId;//证券账号
    private String moneyType;//币种 0-人民币 1-美元 2-港币
    private BigDecimal occurbalance;//发生金额
    private Integer bsBank;//银证转账银行类型[1-CMBC 2-CMB 3-CCB]
    private String depositStatementId;//银证入金id
    private String valueDate;//生效日期，例20220831（不传取的是系统初始化日期，不能大于系统初始化日期，用于利息回算）
}
