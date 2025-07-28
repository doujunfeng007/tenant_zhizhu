package com.minigod.zero.customer.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/31 15:25
 * @description：
 */
@Data
public class CustomerFinancialDetailsReport {
	private Page page;
	private Summary hkd;
	private Summary usd;
	private Summary cny;
	private Summary total;

	@Data
	public
	class Summary{
		private BigDecimal settlement;
		private BigDecimal lastDayBalance;
		private BigDecimal depositBalance;
		private BigDecimal withdrawalBalance;
	}

	public Summary setSummary(BigDecimal lastDayBalance, BigDecimal depositBalance, BigDecimal withdrawalBalance, BigDecimal settlement){
		Summary summary = new Summary();
		summary.setDepositBalance(depositBalance);
		summary.setSettlement(settlement);
		summary.setLastDayBalance(lastDayBalance);
		summary.setWithdrawalBalance(withdrawalBalance);
		return summary;
	} 

}
