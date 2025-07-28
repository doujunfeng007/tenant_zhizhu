package com.minigod.zero.bpmn.module.account.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: OpenAccountCallbackBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/9
 * @Version 1.0
 */
@Data
public class OpenAccountCallbackBo implements Serializable {
    private Long userId;
    private Integer status;
    private String applicationId;
}
