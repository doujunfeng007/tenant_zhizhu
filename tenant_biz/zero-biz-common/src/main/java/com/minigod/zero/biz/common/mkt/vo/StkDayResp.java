package com.minigod.zero.biz.common.mkt.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-07-03 15:14
 * @Description: esop查询收盘价
 */
@Data
public class StkDayResp implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 交易日
	 */
	private String tradeDate;
	/**
	 * 收盘价
	 */
	private String closePrice;
}
