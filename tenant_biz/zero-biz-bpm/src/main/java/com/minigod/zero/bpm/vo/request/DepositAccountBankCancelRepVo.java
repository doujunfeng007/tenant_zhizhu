package com.minigod.zero.bpm.vo.request;

import lombok.Data;

/**
 * @description:
 * @author: sunline
 * @date: 2019/7/5 10:30
 * @version: v1.0
 */
@Data
public class DepositAccountBankCancelRepVo {
    private String bankName; // 银行名称
    private String bankAccount; // 银行账号
    private String bankAccountName; // 账户户名
    private Integer bankType; ////银行卡类型 1:大陆银行卡 2:香港银行卡
    private String bankCode; // 银行代码
}
