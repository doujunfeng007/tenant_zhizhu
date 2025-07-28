package com.minigod.zero.cust.apivo;

import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/29 15:27
 * @description：账户金额信息
 */
@Data
public class AccountBalanceVO {
	/**
	 * 理财账号金额
	 */
	private List<FinancingAccountAmount> financingAccountBalance;
}
