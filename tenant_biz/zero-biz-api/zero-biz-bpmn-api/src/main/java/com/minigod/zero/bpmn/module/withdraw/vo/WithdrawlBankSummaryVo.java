package com.minigod.zero.bpmn.module.withdraw.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 银行汇总报表信息
 *
 * @author chenyu
 * @title WithdrawlBankSummaryDto
 * @date 2023-04-15 15:05
 * @description
 */
@Data
public class WithdrawlBankSummaryVo implements Serializable {

    private String payBankCode;
    private String payBankName;
    private String ccy;
    private String ccyName;

    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]
     */
    private Integer transferType;
    private String transferTypeName;

    private String dealedCount;
    private String dealedAmount;
    private String remark;

}
