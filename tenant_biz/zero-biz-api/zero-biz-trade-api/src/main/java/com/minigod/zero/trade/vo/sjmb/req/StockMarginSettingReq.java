package com.minigod.zero.trade.vo.sjmb.req;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author chen
 * @ClassName StockMarginSettingReq.java
 * @Description 设置可融资股票req
 * @createTime 2024年06月27日 10:49:00
 */
@Data
public class StockMarginSettingReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 股票code 00700.HK
	 */
	private String assetId;

	/**
	 * 市场HK  US
	 */
	private String exchangeType;

	/**
	 * 融资比例 0.8
	 */
	private BigDecimal marginRatio;
}
