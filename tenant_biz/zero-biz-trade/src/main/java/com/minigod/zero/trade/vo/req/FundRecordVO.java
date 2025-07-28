package com.minigod.zero.trade.vo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-05-22 15:14
 * @Description: 资金流水
 */
@Data
public class FundRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资金账号
	 */
	private String capitalAccount;

	/**
	 * 币种类型
	 */
	private String moneyType;

	/**
	 * 开始时间
	 */
	private String beginDate;

	/**
	 * 结束时间
	 */
	private String endDate;
}
