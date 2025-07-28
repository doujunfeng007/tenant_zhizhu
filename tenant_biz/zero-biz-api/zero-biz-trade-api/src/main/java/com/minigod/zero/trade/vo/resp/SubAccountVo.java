package com.minigod.zero.trade.vo.resp;

import lombok.Data;

/**
 * @ClassName: SubAccountVo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/2
 * @Version 1.0
 */
@Data
public class SubAccountVo {
    private String subAccountId;
    private String accountId;
    private String status;
    private String subAccountType;
    private String createDate;
    private String feeRoleId;
    private String feeRateBeginDate;
    private String feeRateEndDate;
}
