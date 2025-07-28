package com.minigod.zero.bpmn.module.account.vo;

import lombok.Data;

/**
 * @ClassName: SubAccountVo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/2
 * @Version 1.0
 */
@Data
public class SubAccountVO {
    private String subAccountId;
    private String accountId;
    private String status;
    private String subAccountType;
    private String createDate;
    private String feeRoleId;
    private String feeRateBeginDate;
    private String feeRateEndDate;
}
