package com.minigod.zero.bpmn.module.bank.vo;

import com.minigod.zero.bpmn.module.bank.entity.DepositBankBillFlow;
import lombok.Data;

/**
 * @ClassName: DepositBankBillFlowVo
 * @Description:
 * @Author chenyu
 * @Date 2024/4/8
 * @Version 1.0
 */
@Data
public class DepositBankBillFlowVo extends DepositBankBillFlow {
    private Integer dealPermissions;
}
