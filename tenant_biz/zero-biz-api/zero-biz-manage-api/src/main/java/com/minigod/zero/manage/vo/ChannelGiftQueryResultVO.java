package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.OperateActPlanEntity;
import com.minigod.zero.manage.entity.SnChannelGiftEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查看渠道礼包详情
 *
 * @author eric
 * @since 2024-12-25 14:43:05
 */
@ApiModel(description = "渠道礼包详情")
@Data
public class ChannelGiftQueryResultVO implements Serializable {
	@ApiModelProperty(value = "渠道礼包详情")
	private SnChannelGiftEntity channelGift;
	@ApiModelProperty(value = "奖励规则")
	private List<RuleRewardVO> ruleRewards;
	@ApiModelProperty(value = "运营活动方案")
	private List<OperateActPlanEntity> planList;
}

