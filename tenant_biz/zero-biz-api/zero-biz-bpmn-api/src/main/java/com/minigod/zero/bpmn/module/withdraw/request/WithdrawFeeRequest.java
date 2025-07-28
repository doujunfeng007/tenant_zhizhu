package com.minigod.zero.bpmn.module.withdraw.request;

import lombok.Data;

/**
 * @ClassName: WithdrawFeeRequest
 * @Description:
 * @Author chenyu
 * @Date 2024/4/1
 * @Version 1.0
 */
@Data
public class WithdrawFeeRequest {
    private String ccy;//币种
    private String bankCode;//银行代码
    private Integer transferType;//取款方式
}
