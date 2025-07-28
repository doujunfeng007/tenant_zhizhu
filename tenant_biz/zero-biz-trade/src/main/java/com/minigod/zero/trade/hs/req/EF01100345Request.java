package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 345 查询策略单
 * @author sunline
 *
 */
@Data
public class EF01100345Request extends GrmRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fundAccount;
	private String actionIn = "0";// 0:查询多条,1:查询单条
	private String conditionType = "2";// 策略类型（数据字典641016 0-OQ订单;1-市价到价单;2-限价到价单）
	private String exchangeType;// 查询单条必填,查询多条无效
	private String entrustNo;// 委托编号,查询单条必填,查询多条无效
	private String initDate;// 交易日期 查询单条必填,查询多条无效
}
