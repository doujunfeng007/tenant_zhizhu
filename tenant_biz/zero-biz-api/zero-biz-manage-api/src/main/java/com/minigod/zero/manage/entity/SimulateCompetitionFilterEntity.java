package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 模拟比赛参赛帐号过滤（白名单与黑名单）
 */

@Data
@TableName("simulate_competition_filter")
@ApiModel(value = "SimulateCompetitionFilter对象", description = "模拟比赛参赛帐号过滤表")
public class SimulateCompetitionFilterEntity {
	private Long id;//主键
	private Long competitionId;//模拟比赛id
	private Long userId;//用户id
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
}
