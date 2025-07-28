package com.minigod.zero.manage.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 更新规则奖励关联表参数
 *
 * @author eric
 * @since 2024-12-25 09:43:01
 */
@Data
public class OperateRuleRewardRelationReqVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer[] registerDeductionRewardIds;
	private Integer[] registerDeductionNums;
	private Integer registerDeductionDay;
	private String registerRewardCondition;
	private Integer openHqRewardId;
	private String openHqRewardName;
	private Integer accountOpeningCash;
	private Integer[] openDeductionRewardIds;
	private Integer[] openDeductionNums;
	private Integer openDeductionDay;
	private String invOpenHqRewardName;
	private Integer invOpenHqRewardId;
	private Long planId;
	private String planName;
	private Integer openValidDay;
	private Integer depositValidDay;
	private Integer transferValidDay;
	private Integer rewardValidDay;
	private Integer invOpenValidDay;
	private Integer invDepositValidDay;
	private Integer invTransferValidDay;
	private String openRewardCondition;
	private String invOpenRewardCondition;
	private Integer openCommissionRewardId;
	private Integer openStockRewardId;
	private Integer openDozenNewSecuritiesId;
	private Integer invOpenStockRewardId;
	private Integer invOpenCommissionRewardId;
	private Integer invOpenMoneyRewardId;
	private Integer invOpenDozenNewSecuritiesId;
	private Integer isBusinessReward;
	private Integer isTransferReward;

	BigDecimal[] depositStartAmounts;
	BigDecimal[] depositEndAmounts;
	private Integer[] depositMoneyRewardIds;
	private Integer[] depositCommissionRewardIds;
	private Integer[] depositStockRewardIds;
	private String[] depositRewardConditions;
	private String[] depositHqRewardNames;
	private Integer[] depositHqRewardIds;
	private Integer[] depositDozenNewSecuritiesIds;
	private Integer[] depositStockRewardNums;
	private Integer[] depositStocksOpenDays;

	private BigDecimal[] invDepositStartAmounts;
	private BigDecimal[] invDepositEndAmounts;
	private Integer[] invDepositMoneyRewardIds;
	private Integer[] invDepositCommissionRewardIds;
	private Integer[] invDepositStockRewardIds;
	private String[] invDepositRewardConditions;
	private String[] invDepositHqRewardNames;
	private Integer[] invDepositHqRewardIds;
	private Integer[] invDepositDozenNewSecuritiesIds;

	private BigDecimal[] transferStartAmounts;
	private BigDecimal[] transferEndAmounts;
	private Integer[] transferMoneyRewardIds;
	private Integer[] transferCommissionRewardIds;
	private Integer[] transferStockRewardIds;
	private String[] transferRewardConditions;
	private String[] transferHqRewardNames;
	private Integer[] transferHqRewardIds;
	private Integer[] transferDozenNewSecuritiesIds;
	private Integer[] transferStockRewardNums;
	private Integer[] transferStocksOpenDays;

	private BigDecimal[] invTransferStartAmounts;
	private BigDecimal[] invTransferEndAmounts;
	private Integer[] invTransferMoneyRewardIds;
	private Integer[] invTransferCommissionRewardIds;
	private Integer[] invTransferStockRewardIds;
	private String[] invTransferRewardConditions;
	private String[] invTransferHqRewardNames;
	private Integer[] invTransferHqRewardIds;
	private Integer[] invTransferDozenNewSecuritiesIds;

	private Integer[] openStocksOpenDays;
}
