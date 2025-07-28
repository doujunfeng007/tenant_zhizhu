package com.minigod.zero.data.statistics.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.dto.DepositAndWithdrawalStatisticsDTO;
import com.minigod.zero.data.statistics.service.DepositAndWithdrawalStatisticsService;
import com.minigod.zero.data.statistics.service.SecDepositFundsService;
import com.minigod.zero.data.vo.DepositAndWithdrawalApplyCountVO;
import com.minigod.zero.data.vo.DepositAndWithdrawalFundsSummaryVO;
import com.minigod.zero.data.vo.DepositTotalAmountVO;
import com.minigod.zero.data.vo.WithdrawalTotalAmountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户资金数据统计
 *
 * @author eric
 * @date 2024/10/29
 */
@Slf4j
@RestController
@RequestMapping("/statistics/fund")
@Api(tags = "用户资金数据统计", value = "用户资金数据统计")
public class ClientFundStatisticsController {

	private final SecDepositFundsService secDepositFundsService;
	private final DepositAndWithdrawalStatisticsService depositAndWithdrawalStatisticsService;

	public ClientFundStatisticsController(SecDepositFundsService secDepositFundsService,
										  DepositAndWithdrawalStatisticsService depositAndWithdrawalStatisticsService) {
		this.secDepositFundsService = secDepositFundsService;
		this.depositAndWithdrawalStatisticsService = depositAndWithdrawalStatisticsService;
	}

	/**
	 * 客户入金统计
	 *
	 * @return
	 */
	@ApiOperation(value = "已入金总资金")
	@GetMapping("/deposit-total-amount")
	public R<DepositTotalAmountVO> statistics() {
		DepositTotalAmountVO depositTotalAmountVO = secDepositFundsService.calculateDepositTotalAmount();
		WithdrawalTotalAmountVO withdrawalTotalAmountVO = depositAndWithdrawalStatisticsService.countWithdrawalTotalAmount();
		log.info("入金总额：{}", depositTotalAmountVO);
		log.info("出金总额：{}", withdrawalTotalAmountVO);
		depositTotalAmountVO.setTotalHkdAmount(depositTotalAmountVO.getTotalHkdAmount().subtract(withdrawalTotalAmountVO.getTotalHkdAmount()));
		return R.data(depositTotalAmountVO);
	}

	/**
	 * 入金明细及总数统计
	 *
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "入金明细及总数统计")
	@PostMapping("/deposit-day-amount")
	public R<DepositAndWithdrawalFundsSummaryVO> statisticsDay(@RequestBody DepositAndWithdrawalStatisticsDTO dto) {
		return R.data(depositAndWithdrawalStatisticsService.getDepositAmountAndTotalAmountByDay(dto));
	}

	/**
	 * 出入金申请笔数统计
	 *
	 * @return
	 */
	@ApiOperation(value = "出入金申请笔数统计")
	@GetMapping("/deposit-withdrawal-apply-count")
	public R<DepositAndWithdrawalApplyCountVO> countDepositAndWithdrawalApply() {
		return R.data(depositAndWithdrawalStatisticsService.countDepositAndWithdrawalApply());
	}
}
