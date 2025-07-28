package com.minigod.zero.trade.vo.sjmb.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName OrderFeeVO.java
 * @Description 订单费用
 * @createTime 2024年04月30日 18:34:00
 */
@Data
public class OrderFeeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 资金账号
	 */
	private String capitalAccount;
	/**
	 * 交易账号
	 */
	private String tradeAccount;
	/**
	 * 资产ID
	 */
	private String assetId;
	/**
	 * 币种类别 HKD USD CNY
	 */
	private String moneyType;
	/**
	 * 证券类别 0-股票
	 */
	private String stockType;
	/**
	 * 买卖方向[B-买入 S-卖出]
	 */
	private String bsFlag;
	/**
	 * 委托数量
	 */
	private String entrustAmount;
	/**
	 * 委托价格
	 */
	private String entrustPrice;
	/**
	 * 交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易） market = US-美国市场 时有效，默认0
	 */
	private String sessionType;
	/**
	 * 委托属性
	 */
	private String entrustProp;
	/**
	 * 委托编号，在改单计算费用时有用
	 */
	private String entrustNo;
}
