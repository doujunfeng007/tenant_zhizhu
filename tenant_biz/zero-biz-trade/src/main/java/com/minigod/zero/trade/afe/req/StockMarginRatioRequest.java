package com.minigod.zero.trade.afe.req;

import lombok.Data;

/**
 * @author chen
 * @ClassName StockMarginRatioReq.java
 * @Description TODO
 * @createTime 2024年06月20日 16:28:00
 */
@Data
public class StockMarginRatioRequest extends ReqVO{

	/**
	 * 市场 MARKETID
	 */
	private String marketId;
}
