package com.minigod.zero.platform.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 17:35
 * @description：
 */
@Data
public class CustomerPositionsVO {
	/**
     * 总市值
	 */
	private BigDecimal marketValue;
	/**
	 * 港币总市值
	 */
	private BigDecimal HDKMarketValue;
	/**
	 * 美元总市值
	 */
	private BigDecimal USDMarketValue;
	/**
	 * 人民币总市值
	 */
	private BigDecimal CNYMarketValue;
	/**
	 * 累计收益
	 */
	private BigDecimal totalIncome;

	public CustomerPositionsVO(JSONObject position){
		this.marketValue = position.getBigDecimal("marketValue");
		this.HDKMarketValue = position.getBigDecimal("marketValueHKD");
		this.USDMarketValue = position.getBigDecimal("marketValueUSD");
		this.CNYMarketValue = position.getBigDecimal("marketValueCNY");
		this.totalIncome = position.getBigDecimal("totalGainLoss");
	}
}
