package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 现金取出
 * @author: Larry Lai
 * @date: 2020/5/6 10:08
 * @version: v1.0
 */

@Data
public class CashWithdrawResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作结果[true-成功 false-失败]
     */
    private boolean isSuccess;
}
