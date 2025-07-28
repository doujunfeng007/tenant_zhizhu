package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询最大可卖
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class MaxmumSellResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 可卖数量
     */
    private Integer enableQty = 0;
}
