package com.minigod.zero.manage.vo.simulatetrade;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 比赛排行榜数据
 */
@Data
public class CompetitionRankDataVO implements Serializable {
    private Integer id; //competition_profit_stats表的id
    private Integer competitionId; //比赛ID
    private String competitionName;//比赛名称
    private Date openAccountTime; //帐户审批通过时间
    private Date registerTime;//报名时间
    private Date registerStartTime; //报名开始时间
    private Date registerEndTime; //报名结束时间
    private Date startTime;//比赛开始时间
    private Date endTime;//比赛结束时间
    private String status;//比赛状态
    private Long userId;//用户ID
    private String tradeAccount; //客户帐号
    private String clientName; //客户姓名
    private String clientNameSpell; //英文名
    private String nickName; //客户呢称
    private String phoneNumber; //手机号
    private BigDecimal marketValue; //持仓总市值(港币)
    private BigDecimal enableFund; //可用资金(港币)
    private BigDecimal holdProfit; //浮动盈亏(港币)
    private BigDecimal totalProfit; //累计盈亏(港币)
    private BigDecimal todayProfit; //今日持仓盈亏
    private BigDecimal profit; //收益
    private BigDecimal profitRate; //收益率
    private Integer tradeCount; //买入交易次数
    private Long competitionDays; //参与比赛天数
    private Integer rank; //比赛名次
    private BigDecimal initFund; //初始资金
    private BigDecimal totalFund; //总资产
    private String currency; //货币
    private String market; //市场
    private Date openDate; //报名时间

}
