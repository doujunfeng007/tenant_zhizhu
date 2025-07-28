package com.minigod.zero.manage.dto;

import com.minigod.zero.manage.entity.OperateActPlanEntity;
import com.minigod.zero.manage.vo.RewardRuleCommonVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 进入规则奖品修改页面数据模型
 *
 * @author eric
 * @since 2024-12-24 16:01:15
 */
@Data
@ApiModel(value = "进入规则奖品修改页面数据模型")
public class RewardRuleCommonDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 奖励方案
	 */
	@ApiModelProperty(value = "奖励方案")
	OperateActPlanEntity operateActPlan;
	/**
	 * 注册奖励
	 */
	@ApiModelProperty(value = "注册奖励")
	private RewardRuleCommonVO registerReward;
	/**
	 * 开户奖励
	 */
	@ApiModelProperty(value = "开户奖励")
	private RewardRuleCommonVO openReward;
	/**
	 * 邀请开户奖励
	 */
	@ApiModelProperty(value = "邀请开户奖励")
	private RewardRuleCommonVO invOpenReward;
	/**
	 * 入金奖励
	 */
	@ApiModelProperty(value = "入金奖励")
	private List<RewardRuleCommonVO> depositReward;
	/**
	 * 邀请入金奖励
	 */
	@ApiModelProperty(value = "邀请入金奖励")
	private List<RewardRuleCommonVO> invDepositReward;

	/**
	 * 转仓奖励
	 */
	@ApiModelProperty(value = "转仓奖励")
	private List<RewardRuleCommonVO> transferReward;
	/**
	 * 邀请转仓奖励
	 */
	@ApiModelProperty(value = "邀请转仓奖励")
	private List<RewardRuleCommonVO> invTransferReward;
}
