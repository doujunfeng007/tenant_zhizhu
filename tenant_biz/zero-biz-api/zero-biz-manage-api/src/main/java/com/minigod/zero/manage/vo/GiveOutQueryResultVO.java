package com.minigod.zero.manage.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnActiveConfigItemEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询奖励信息
 *
 * @author eric
 * @since 2024-12-25 17:13:05
 */
@ApiModel(description = "查询奖励信息")
@Data
public class GiveOutQueryResultVO implements Serializable {
	@ApiModelProperty(value = "奖品发放详情")
	private IPage<RewardGiveOutVO> page;
	@ApiModelProperty(value = "奖品列表")
	List<SnActiveConfigItemEntity> rewardList;

}
