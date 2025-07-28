package com.minigod.zero.dbs.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class DbsBaseRequestVO implements Serializable {

    // 业务银行编码
    private String businessBankCode;
    // 消息流水
    private String msgId;
    // 业务流水号
    private String applicationId;
    // 客户流水号
    private String cusRef;
    // 租户 ID
    private String tenantId;

}
