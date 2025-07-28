package com.minigod.zero.manage.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 扣减奖励
 *
 * @author eric
 * @since 2024-12-24 17:32:01
 */
@Data
public class DeductionReward implements Serializable {
	private static final long serialVersionUID = 1096367963975042677L;
	/**
	 * 奖励id
	 */
	private Long rewardId;
	/**
	 * 奖励数量
	 */
	private Integer rewardNum;
	/**
	 * 有效天数
	 */
	private Integer validityDay;
	/**
	 * 奖励金额
	 */
	private BigDecimal rewardMoney;
	/**
	 * 奖品名称
	 */
	private String rewardName;
}
