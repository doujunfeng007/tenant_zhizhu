package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 341 策略下单
 * @author sunline
 *
 */
@Data
public class EF01100341Request extends GrmRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fundAccount;
	private String password;
	private String exchangeType;
	private String stockCode;
	private String entrustAmount;
	private String entrustPrice;
	private String entrustBs;
	private String entrustProp;
	private String conditionType;// 条件单类型(0-OQ订单;1-市价到价单;2-限价到价单)
	private String touchPrice;// LIT、MIT必填
	private String strategyType;// 0 normal就是正常的订单，当日失效的，1 GTD（Good Till Date）：在指定日前有效，2 GTC（Good Till Cancelled）：除非交易者自己取消，否则该委托“永久”有效。
	private String strategyEnddate;// 策略失效日期
}
