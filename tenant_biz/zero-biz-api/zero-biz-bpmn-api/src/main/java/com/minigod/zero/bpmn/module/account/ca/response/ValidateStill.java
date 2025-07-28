package com.minigod.zero.bpmn.module.account.ca.response;

import lombok.Data;

/**
 * @ClassName: VerifyFourVo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/12
 * @Version 1.0
 */
@Data
public class ValidateStill {
    /**
     * 单号
     */
    private String orderNo;
    /**
     * 活体返回图片
     */
    private String image;
}
