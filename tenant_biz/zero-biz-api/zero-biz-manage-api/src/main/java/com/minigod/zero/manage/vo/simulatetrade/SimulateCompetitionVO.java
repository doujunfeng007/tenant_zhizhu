package com.minigod.zero.manage.vo.simulatetrade;

import lombok.Data;

import java.io.Serializable;

/**
 * 模拟比赛
 */
@Data
public class SimulateCompetitionVO implements Serializable {
    private Long id;//自增主键
    private String competitionName;//比赛名称
    private String conditionDescription;//参赛条件描述
    private String registerStartTime;//报名开始时间
    private String registerEndTime;//报名结束时间
    private String startTime;//比赛开始时间
    private String endTime;//比赛结束时间
    private String promulgateTime;//结果公布时间
    private String awardTime;//颁奖时间
    private Integer isEffective;//是否发布
    private Integer priority;//优先级
    private String contentDescription;//比赛描述
    private String awardDescription;//奖品描述
    private String attentionDescription;//注意事项描述
    private String createTime;//创建时间
    private String updateTime;//更新时间
    private Integer openMarketFlag;//开放市场标识[1-港股 2-美股]
    private Long initAmount; //初始资金
    private Integer tradeCount; //交易次数
    private String tradeAccount; //客户帐号
    private Integer filterType; //参赛者过滤类型，0:无限制, 1:白名单, 2:黑名单
    private String inviteCode; // 邀请码
    private Integer isInvite; // 是否启用邀请码，1-启用，0-禁止
	private String tenantId;//
}
