package com.minigod.zero.data.statistics.service;

import com.minigod.zero.data.vo.DepositTotalAmountVO;

/**
 * @description: 客户入金总额统计服务
 * @author: eric
 * @since 2024-10-29 14:43:05
 */
public interface SecDepositFundsService {
	/**
	 * 统计客户入金总额
	 *
	 * @return 入金总额统计结果
	 */
	DepositTotalAmountVO calculateDepositTotalAmount();
}
