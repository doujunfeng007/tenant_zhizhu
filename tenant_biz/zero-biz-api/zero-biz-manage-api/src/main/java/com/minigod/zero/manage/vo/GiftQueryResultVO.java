package com.minigod.zero.manage.vo;

import com.minigod.zero.manage.entity.OperateActPlanEntity;
import com.minigod.zero.manage.entity.SnChannelGiftEntity;
import com.minigod.zero.manage.entity.UserChannelInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询渠道礼包详情返回对象
 *
 * @author eric
 * @since 2024-12-25 14:43:05
 */
@ApiModel(description = "查询渠道礼包详情返回对象")
@Data
public class GiftQueryResultVO implements Serializable {
	@ApiModelProperty(value = "渠道礼包详情")
	private SnChannelGiftEntity channelGift;
	@ApiModelProperty(value = "奖励规则")
	private List<RuleRewardVO> ruleRewards;
	@ApiModelProperty(value = "运营活动方案")
	private List<OperateActPlanEntity> planList;
	@ApiModelProperty(value = "渠道信息")
	private List<UserChannelInfoEntity> channelList;
}
