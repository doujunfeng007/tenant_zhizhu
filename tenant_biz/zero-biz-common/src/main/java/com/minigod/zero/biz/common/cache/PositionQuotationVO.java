package com.minigod.zero.biz.common.cache;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author:yanghu.luo
 * @create: 2023-06-02 19:04
 * @Description: 持仓重算获取行情数据
 */
@Data
public class PositionQuotationVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资产ID
	 */
	private String assetId;

	/**
	 * 最新价
	 */
	private BigDecimal price;

	/**
	 * 记录盘中最新价，第二天盘前用来作为前一天收盘价
	 */
	private BigDecimal normalPrice;

	/**
	 * 昨收
	 */
	private BigDecimal prevClose;

	/**
	 * 最高
	 */
	protected BigDecimal high;
	/**
	 * 最低
	 */
	protected BigDecimal low;

	/** 日期 */
	private String date;

	/** 时间 */
	private String time;

	/**
	 * 更新时间
	 */
	private long updateTime;

	/** 详细类型 101:A股 */
	protected int secSType;
}
