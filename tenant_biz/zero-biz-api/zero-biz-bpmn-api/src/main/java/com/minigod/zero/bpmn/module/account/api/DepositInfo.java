package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: DepositInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class DepositInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    private String receiveBank;
    private String outBank;
    private String bankCode;
    private String bankAccountNumber;
    private String accountHolderName;
    private Integer accountCurrencyType;
    private Integer depositCurrencyType;
    private BigDecimal depositMoney;
    private String depositDate;
	private Long depositBankId;
}
