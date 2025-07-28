package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class EF01110104Request extends GrmRequestVO implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 委托编号（传要撤单委托的原委托编号）
	 */
    private String entrustNo;

    /**
     * 0	Order 下单
	 * 2	Cancel Order 撤单
	 * B	Modify Order 改单
	 */
    private String entrustType;
}
