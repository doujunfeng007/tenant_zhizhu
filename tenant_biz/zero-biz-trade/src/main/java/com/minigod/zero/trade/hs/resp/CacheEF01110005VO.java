package com.minigod.zero.trade.hs.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 缓存持仓数据
 */
@Data
public class CacheEF01110005VO implements Serializable {
    private static final long serialVersionUID = 1L;
	/**
	 * 交易账号
	 */
	private String tradeAccount;
	/**
	 * 资金账号
	 */
	private String fundAccount;
	/**
	 * 港币总市值
	 */
	private BigDecimal totalMarketValue2HKD = BigDecimal.ZERO;
	/**
	 * 港股总市值
	 */
	private BigDecimal hkMarketValue = BigDecimal.ZERO;
	/**
	 * 美股总市值
	 */
	private BigDecimal usMarketValue = BigDecimal.ZERO;
	/**
	 * A股总市值
	 */
	private BigDecimal aMarketValue = BigDecimal.ZERO;
	/**
	 * 持仓明细
	 */
	private List<EF01110005VO> ef01110005VOList;
}
