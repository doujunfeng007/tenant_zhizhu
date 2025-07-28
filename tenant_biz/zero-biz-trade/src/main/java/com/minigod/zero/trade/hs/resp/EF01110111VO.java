package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EF01110111VO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 委托编号
	 */
	@JSONField(name="record_no")
	private String orderNo;
	/**
	 * 证券代码
	 */
	@JSONField(name="stock_code")
	private String stockCode;
	/**
	 * 买卖方向 1-买，2-卖
	 */
	@JSONField(name="entrust_bs")
	private String bsFlag;
	/**
	 * 成交数量
	 */
	@JSONField(name="business_amount")
	private BigDecimal businessAmount;
	/**
	 * 成交金额
	 */
	@JSONField(name="business_balance")
	private BigDecimal businessBalance;
}
