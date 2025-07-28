package com.minigod.zero.manage.vo.simulatetrade;

import lombok.Data;

/**
 * 委托订单表
 */
@Data
public class EntrustOrderVo {

    private static final long serialVersionUID = 1L;
    private String assetId;//股票id
    private Double orderPrice;//委托价格
    private Long orderQty;//委托数量
    private Integer orderAction;//买卖方向：1:买入（app端提交的委托），2:卖出（app端提交的委托），3:拆分（买入，web-oms后台手工提交的委托），4:派送（买入，web-oms后台手工提交的委托）
    private Long simuAccountId;//模拟交易帐号
    private Long competitionId; //模拟比赛ID

	private String sessionId;//会话ID
	private Long userId;//用户ID
	private Long timestamp;//时间戳
	private String sign;//签名
}
