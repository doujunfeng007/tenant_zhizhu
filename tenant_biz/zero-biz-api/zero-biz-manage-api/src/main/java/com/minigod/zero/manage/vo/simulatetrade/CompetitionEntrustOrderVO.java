package com.minigod.zero.manage.vo.simulatetrade;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 模拟比赛交易委托
 */
@Data
public class CompetitionEntrustOrderVO implements Serializable {
    private Integer id; //entrust_order表的id
    private Long competitionId; //比赛ID
    private String competitionName;//比赛名称
    private String status;//比赛状态
    private Date endTime; //比赛结束日期
    private Long userId;//用户ID
    private String tradeAccount; //客户帐号
    private String clientName; //客户姓名
    private String clientNameSpell; //英文名
    private Date orderTime; //订单日期
    private Integer orderAction; //买卖方向：1买入，2卖出
    private String orderState; //订单状态
    private String stockName; //股票名称
    private String assetId; //股票代码
    private String stockCode; //股票
    private String market; //市场
    private Long orderQty; //委托数量
    private BigDecimal orderPrice; //委托价格
    private Integer tradeQty; //成交数量
    private BigDecimal tradePrice; //成交价格
    private Integer entrustType; //委托类型：1:app提交的委托，2:拆分（后台手工上传），2:派股（后台手工上传）
    private String orderActionName; //委托类型名称
    private String orderStateName; //订单状态名称
	private String tenantId;
}
