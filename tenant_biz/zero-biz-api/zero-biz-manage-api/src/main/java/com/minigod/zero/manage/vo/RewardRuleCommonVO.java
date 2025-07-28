package com.minigod.zero.manage.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description:奖励规则
 * @Author: eric
 * @since 2024-12-24 17:34:05
 */
@Data
public class RewardRuleCommonVO implements Serializable {
	private static final long serialVersionUID = 6321054376579536278L;
	/**
	 * 主键ID
	 */
	private Integer id;
	/**
	 * 开始金额
	 */
	private String startAmount;
	/**
	 * 结束金额
	 */
	private String endAmount;
	/**
	 * 免佣天数
	 */
	private Integer commissionDay;
	/**
	 * 奖励金额
	 */
	private BigDecimal rewardMoney;
	/**
	 * 股票数量
	 */
	private Integer stkQuantity;
	/**
	 * 股票名称
	 */
	private String stkName;
	/**
	 * 佣金奖励ID
	 */
	private Long commissionRewardId;
	/**
	 * 股票奖励ID
	 */
	private Long stockRewardId;
	private Long moneyRewardId;
	private Long hqRewardId;
	private String hqRewardName;
	/**
	 * 奖励条件
	 */
	private String rewardCondition;
	/**
	 * 扣减奖励
	 */
	private List<DeductionReward> deductionRewardList;
	/**
	 * 赠送股票
	 */
	private List<GivenTheStockVO> givenTheStockList;
}
