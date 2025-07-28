package com.minigod.zero.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: sunline
 * @date: 2021/07/24
 * @version: v1.0
 */
@Data
public class ToPredictApplyVO implements Serializable {

    private Long predictIpoConfigId; //配置id
    private Long predictOrderId; // 预约新股订单id
    private String assetNamePredict; //预约股票名称
    private BigDecimal entranceFee; //预计入场费
    private Long userFinancingCeiling;//普通用户融资上限
    private Long vipFinancingCeiling;//VIP用户融资上限
    private Long userTotalFinancingCeiling;//普通用户融资上限总额
    private Long vipTotalFinancingCeiling;//VIP用户融资上限总额
    private BigDecimal remainFinancingBalance; //剩余融资金额
    private Long remainDays;// 剩余时间
    private Integer subscribed; //认购倍数
    private List<Integer> rateTimes; //认购倍数
    private Integer userSubscribed; //普通用户认购倍数
    private Integer vipSubscribed; //vip认购倍数
    private String stkType; //港股
    private Date beginPredictTime; //预约开始日期
    private Date endPredictTime; //预约截止日期
    private int applyStatus; //预约认购状态 1.可预约 2.预约成功(可以撤回) 3.预约失败(不可再预约) 4.已经提交排队 5.4.已撤回预约
    private String noticeMsg;//通知栏文案
}
