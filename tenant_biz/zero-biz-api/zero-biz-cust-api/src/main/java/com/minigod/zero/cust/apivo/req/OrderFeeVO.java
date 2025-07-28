package com.minigod.zero.cust.apivo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-05-31 14:50
 * @Description: 查询委托费用
 */
@Data
public class OrderFeeVO implements Serializable {
	private static final long serialVersionUID = -1L;

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
	 * 币种类别 0-人民币 1-美元 2-港币
	 */
	private String moneyType;
	/**
	 * 证券类别 0-股票
	 */
	private String stockType;
	/**
	 * 买卖方向 1-买入，2-卖出
	 */
	private String entrustBs;
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
	 * 委托属性 0-限价单(美股限价单)，d-竞价单，g-竞价限价单，h-限价单(港股限价单)，e-增强限价单，j-特别限价单，u-碎股单(港股碎股单)
	 */
	private String entrustProp;
	/**
	 * 委托编号，在改单计算费用时有用
	 */
	private String entrustNo;
}
