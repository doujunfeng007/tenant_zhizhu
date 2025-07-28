package com.minigod.zero.resource.vo;

import lombok.Data;

/**
 * @author sunline
 * @version v1.0
 * @project: sunline
 * @description: 这里描述类的用处
 * @copyright: © 2017
 * @company:
 * @date 2017/3/10 17:48
 */
@Data
public class JFResp {
    private static final long serialVersionUID = 1L;
    private String success;
    private String num;

    private Object error;
}
