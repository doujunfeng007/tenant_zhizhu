package com.minigod.zero.manage.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author eric
 * @description: 领取股票
 * @since 2024-12-24 17:33:08
 */
@Data
public class GivenTheStockVO implements Serializable {
	private static final long serialVersionUID = 7093683670205167053L;
	/**
	 * 股票名称
	 */
	private String stkName;
	/**
	 * 股票数量
	 */
	private Integer stkQuantity;
	/**
	 * 股票奖励id
	 */
	private Long stockRewardId;
	/**
	 * 股票奖励有效期
	 */
	private Integer stocksOpenDay;
}
