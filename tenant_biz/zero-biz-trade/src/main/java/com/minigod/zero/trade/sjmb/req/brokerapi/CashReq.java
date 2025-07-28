package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName CashReq.java
 * @Description TODO
 * @createTime 2024年02月01日 18:04:00
 */
@Data
public class CashReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 泰坦系统使用的账号ID
	 */
	private String accountId;
	/**
	 * 流水开始日期，格式：yyyy/MM/dd
	 */
	private String beginDate;
	/**
	 * 币种，参考返回值中的currency枚举类型
	 */
	private String currency;
	/**
	 * 流水结束日期，格式：yyyy/MM/dd
	 */
	private String endDate;
	/**
	 * 一页的条目数量，默认为10
	 */
	private Integer limit;
	/**
	 * 第?页，默认为1
	 */
	private Integer page;
	/**
	 * 资金组
	 */
	private String segmentId;
	/**
	 * 合约代码
	 */
	private String symbol;
	/**
	 * 资金流水子分类，参考返回值中的transSubType枚举类型
	 */
	private String transSubType;
	/**
	 * 资金流水分类，参考返回值中的transType枚举类型
	 */
	private String transType;

}
