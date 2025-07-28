package com.minigod.zero.data.statistics.service;

import com.minigod.zero.data.dto.DepositAndWithdrawalStatisticsDTO;
import com.minigod.zero.data.vo.DepositAndWithdrawalApplyCountVO;
import com.minigod.zero.data.vo.DepositAndWithdrawalFundsSummaryVO;
import com.minigod.zero.data.vo.WithdrawalTotalAmountVO;

/**
 * 出入金统计及每日净入金总额统计
 *
 * @author eric
 * @since 2024-10-30 11:06:05
 */
public interface DepositAndWithdrawalStatisticsService {
	/**
	 * 出入金统计
	 *
	 * @return
	 */
	DepositAndWithdrawalFundsSummaryVO getDepositAmountAndTotalAmountByDay(DepositAndWithdrawalStatisticsDTO dto);

	/**
	 * 统计出入金申请笔数
	 */
	DepositAndWithdrawalApplyCountVO countDepositAndWithdrawalApply();

	/**
	 * 统计出金总数
	 */
	WithdrawalTotalAmountVO countWithdrawalTotalAmount();
}
