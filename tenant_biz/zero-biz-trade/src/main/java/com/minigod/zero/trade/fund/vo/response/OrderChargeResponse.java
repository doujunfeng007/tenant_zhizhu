package com.minigod.zero.trade.fund.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 查询客户默认费用
 * @author: Larry Lai
 * @date: 2020/4/14 10:08
 * @version: v1.0
 */

@Data
public class OrderChargeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 合计
     */
    private String total="0.00";

    /**
     * 佣金
     */
    private String commission="0.00";

    /**
     * 交易费
     */
    private String charge="0.00";

    /**
     * 印花税
     */
    private String stamp="0.00";

    /**
     * 清算费
     */
    private String clearing="0.00";

    /**
     * 交易征费
     */
    private String levy="0.00";
}
