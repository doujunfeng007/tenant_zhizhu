package com.minigod.zero.trade.vo.sjmb.resp;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author chen
 * @ClassName StockMarginRatioVO.java
 * @Description TODO
 * @createTime 2024年06月18日 14:56:00
 */
@Data
public class StockMarginRatioVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 孖展比例
	 */
	private BigDecimal marginRatio;

	/**
	 *  股票名称
	 */
	private String stockName;

	/**
	 * 证券代码
	 */
	private String assetId;

	/**
	 * 市场
	 */
	private String exchangeType;

	/**
	 * 股票代码
	 */
	private String stockCode;

	/**
	 * 起始日期
	 */
	private Integer startDate;

	/**
	 * 截止日期
	 */
	private Integer endDate;
}
