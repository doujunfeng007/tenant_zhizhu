package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 343 策略下单
 * @author sunline
 *
 */
@Data
public class EF01100343Request extends GrmRequestVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fundAccount;
	private String password;
	private String exchangeType;
	private String entrustNoFirst;
	private String entrustAmount;
	private String entrustPrice;
	private String conditionType;// 条件单类型(0:OQ,1:MIT,2:LIT)
}
