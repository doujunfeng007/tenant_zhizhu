package com.minigod.zero.bpmn.module.account.ca.response;

import lombok.Data;

/**
 * @ClassName: verifyFourFactor
 * @Description:
 * @Author chenyu
 * @Date 2022/8/15
 * @Version 1.0
 */
@Data
public class VerifyFourFactor {
    private Boolean success;
    private String orderNo;
}
