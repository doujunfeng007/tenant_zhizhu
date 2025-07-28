package com.minigod.zero.manage.vo.simulatetrade;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 比赛持仓数据
 */
@Data
public class CompetitionPositionDataVO implements Serializable {
    private Integer id; //hold_stock表的id
    private Date openDate; //报名时间
    private Long userId; //用户ID
    private String tradeAccount; //客户帐号
    private String clientName; //客户姓名
    private String clientNameSpell; //英文名
    private String phoneNumber; //手机号
    private String competitionName; //比赛名称
    private String competitionId; //模拟比赛编号
    private String stockName; //股票名称
    private String assetId; //股票代码
    private Integer qty; //持有股数
    private BigDecimal costPrice; //成本价
}
