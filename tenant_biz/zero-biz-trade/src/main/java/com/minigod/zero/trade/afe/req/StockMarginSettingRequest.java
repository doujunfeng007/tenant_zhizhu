package com.minigod.zero.trade.afe.req;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author chen
 * @ClassName StockMarginSettingRequest.java
 * @Description TODO
 * @createTime 2024年06月27日 11:39:00
 */
@Data
public class StockMarginSettingRequest extends ReqVO{

	/**
	 * 市场 MARKETID
	 */
	private String marketId;

	/**
	 * 股票代码  00700
	 */
	private String instrumentId;

	/**
	 * 融资比例  0.8
	 */
	private BigDecimal marginPercentage;


}
