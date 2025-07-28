package com.minigod.zero.biz.common.mkt.ts;

import com.minigod.zero.core.tool.beans.QuotationVO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UsTsData extends QuotationVO {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易类型 是否过滤成交不需要画到分时
	 */
	private String tradetype;
	/**
	 * 分笔成交量
	 */
	private String lasttradeqty;

	/**
	 * 上一比分笔成交量
	 */
	private BigDecimal otcLasttradeqty;

	/**
	 * tick total volume 分笔累计成交量
	 */
	private BigDecimal tktVol;
	/**
	 * tick total turnOver 分笔累计成交金额
	 */
	private BigDecimal tktTo;

}
