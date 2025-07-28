package com.minigod.zero.trade.vo;

import lombok.Data;

/**
 * IPO个股申购档位
 * @author sunline
 *
 */

@Data
public class IPOLevel {
	/**
	 * int[10] 数量
	 */
	private int quantity;
	/**
	 * double[19,2] 申购所需金额
	 */
	private double amount;

    /**
     * double[19,2] 使用资金
     * @return
     */
    private double useAmount;

    /**
     * double[19,2] 融资金额
     * @return
     */
    private double financingAmount;

    /**
     * 该档位现金申购是否可用
     */
    private boolean cashEnable;
    /**
     * 该档位融资申购是否可用
     */
    private boolean financingEnable;
}
