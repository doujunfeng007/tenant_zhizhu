package com.minigod.zero.trade.order.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 改单
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class UpdateOrderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 结果[true-成功 false-失败]
     */
    private boolean isSuccess;
}
