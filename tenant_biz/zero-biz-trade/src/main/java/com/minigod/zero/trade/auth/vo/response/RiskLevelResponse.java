package com.minigod.zero.trade.auth.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 校验密码
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class RiskLevelResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 风险等级
     */
    private Integer level = 0;
}
