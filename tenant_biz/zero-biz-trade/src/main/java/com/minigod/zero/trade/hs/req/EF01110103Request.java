package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class EF01110103Request extends GrmRequestVO implements Serializable {

	/**
	 * 柜台资金账号
	 */
	private String fundAccount;

	/**
	 * 交易类别 K-香港，1-上海A，D-上海B，2-深圳A，H-深证B，P-美股，t-沪股通，v-深港通
	 */
	private String exchangeType;

	/**
	 * 证券代码
	 */
	private String stockCode;

	/**
	 * 委托数量（传要撤单委托总的委托数量）
	 */
	private String entrustAmount;

	/**
	 * 委托价格
	 */
	private String entrustPrice;

	/**
	 * 买卖方向（1：买2：卖）
	 */
    private String entrustBs;

	/**
	 * 0	Order 下单
	 * 2	Cancel Order 撤单
	 * B	Modify Order 改单
	 */
    private String entrustType;

	/**
	 * 委托属性 0-限价单(美股限价单)，d-竞价单，g-竞价限价单，h-限价单(港股限价单)，e-增强限价单，j-特别限价单，u-碎股单(港股碎股单)
	 */
    private String entrustProp;

	/**
	 * 沽空类型（卖的时候必须要这个字段：目前传入‘N’表示不允许沽空）
	 */
    private String shortsellType;

	/**
	 * 对冲标志（默认传入‘B’默认传入即可，表示没有对冲）
	 */
    private String hedgeFlag;

	/**
	 * 交易阶段标识（0-正常订单交易（默认），1-盘前交易，2-暗盘交易,3-盘后交易）
	 * exchangeType = P-美股 时有效，默认0
	 */
    private String sessionType = "0";
}
