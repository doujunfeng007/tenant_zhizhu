package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.FinancingAccountAmount;
import com.minigod.zero.customer.entity.FundCapitalAccountAmount;
import com.minigod.zero.customer.entity.FundTradingAccountAmount;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
