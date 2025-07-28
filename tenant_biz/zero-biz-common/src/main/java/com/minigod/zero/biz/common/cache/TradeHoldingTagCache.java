package com.minigod.zero.biz.common.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于查询分组时 ，将持仓列表的股票加入到自选分组里面
 */
@Data
public class TradeHoldingTagCache implements Serializable {
	private Long custId;
	private Long updateTimes;
}
