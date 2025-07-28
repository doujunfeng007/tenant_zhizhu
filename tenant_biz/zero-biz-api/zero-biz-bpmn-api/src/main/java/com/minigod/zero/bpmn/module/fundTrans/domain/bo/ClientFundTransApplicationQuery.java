package com.minigod.zero.bpmn.module.fundTrans.domain.bo;

import lombok.Data;

/**
 * @ClassName: ClientFundTransApplicationQuery
 * @Description:
 * @Author chenyu
 * @Date 2024/12/11
 * @Version 1.0
 */
@Data
public class ClientFundTransApplicationQuery {
    private String tenantId;
    private Long userId;
}
