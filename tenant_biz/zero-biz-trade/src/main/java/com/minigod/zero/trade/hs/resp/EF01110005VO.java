package com.minigod.zero.trade.hs.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 查找股票
 */
@Data
public class EF01110005VO extends StockHoldingVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 每股手数
	 */
    private int lotSize;

	/**
	 * 股票字类型
	 */
    private int secSType;
}
