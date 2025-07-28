package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 342 策略改单
 * @author sunline
 *
 */
@Data
public class EF01100342Request extends GrmRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fundAccount;
	private String password;
	private String exchangeType;
	private String entrustNoFirst;
	private String entrustAmount;
	private String entrustPrice;
	private String conditionType;// 条件单类型(0:OQ,1:MIT,2:LIT)
	private String touchPrice2;// 触发价格2，LIT、MIT必填,如果此委托单已经触发此参数将会无效
}
