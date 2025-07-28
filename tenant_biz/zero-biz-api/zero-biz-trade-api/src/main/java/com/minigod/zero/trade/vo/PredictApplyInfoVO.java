package com.minigod.zero.trade.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: TODO 可预约的 新股信息
 * @author: sunline
 * @date: 2021/07/24
 * @version: v1.0
 */
@Data
public class PredictApplyInfoVO implements Serializable {

    private Long predictIpoConfigId; //配置id
    private Long predictOrderId; // 预约新股订单id
    private String assetNamePredict; //预约股票名称
    private BigDecimal predictIpoFinanceAmount; //预约融资金额
    private Integer subscribed; //认购倍数
    private String stkType; //港股
    private Date predictApplyTime; //预约申请时间
    private Integer applyStatus; //预约认购状态 1.可预约 2.预约成功(可以撤回) 3.预约失败(不可再预约) 4.已经提交排队 5.已撤回预约
    private String errCode; // 加入排队失败原因
}
