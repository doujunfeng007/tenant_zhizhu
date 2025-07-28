package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("simulate_competition")
@ApiModel(value = "SimulateCompetition对象", description = "模拟比赛表")
public class SimulateCompetitionEntity {
	private Long id; //比赛ID
	private String competitionName; //比赛名称
	private String conditionDescription; //参赛条件描述
	private Date registerStartTime; //报名开始时间
	private Date registerEndTime; //报名结束时间
	private Date startTime; //举办开始时间
	private Date endTime; //举办结束时间
	private Date promulgateTime; //结果公布时间
	private Date awardTime; //颁奖时间
	private Integer isEffective; //是否有效
	private Integer priority; //优先级
	private String contentDescription; //内容描述
	private String attentionDescription; //注意事项描述
	private String awardDescription; //奖品描述
	private Date createTime; //创建时间
	private Date updateTime; //更新时间
	private Integer openMarketFlag; //开放市场标识[1-港股 2-美股]
	private Integer filterType; //参赛者过滤类型，0:无限制, 1:白名单, 2:黑名单
	private String inviteCode; // 邀请码
	private Integer isInvite; // 是否启用邀请码，1-启用，0-禁止
	private String tenantId; // 租户号

	private long initAmount; // 初始资金
	private Integer tradeCount; // 交易次数
	private Boolean editable; // 是否可编辑标识，报名开始时间之前为true
	private Boolean tradeCountEditable; // 是否可编辑标识2，举办开始时间之前为true
}
