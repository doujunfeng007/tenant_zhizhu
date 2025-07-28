package com.minigod.zero.trade.vo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-05-22 17:09
 * @Description: 单市场资金
 */
@Data
public class FundInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 资金账号
	 */
	private String capitalAccount;

	/**
	 * 币种类型
	 */
	private String moneyType;
}
