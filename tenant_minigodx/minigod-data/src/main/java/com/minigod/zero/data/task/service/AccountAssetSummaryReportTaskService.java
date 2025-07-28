package com.minigod.zero.data.task.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.data.entity.AccountAssetSummaryReport;
import com.minigod.zero.data.entity.CustomerCashAssetsHistory;
import com.minigod.zero.data.entity.FinancingAccountAmountFlows;
import com.minigod.zero.data.enums.CurrencyType;
import com.minigod.zero.data.enums.ThawingType;
import com.minigod.zero.data.mapper.AccountAssetSummaryReportMapper;
import com.minigod.zero.data.mapper.CustomerCashAssetsHistoryMapper;
import com.minigod.zero.data.mapper.FinancingAccountAmountFlowsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 10:39
 * @description：
 */
@Slf4j
@Service
public class AccountAssetSummaryReportTaskService {
	@Autowired
	private AccountAssetSummaryReportMapper accountAssetSummaryReportMapper;
	@Autowired
	private CustomerCashAssetsHistoryMapper customerCashAssetsHistoryMapper;
	@Autowired
	private FinancingAccountAmountFlowsMapper financingAccountAmountFlowsMapper;

	/**
	 * 客户账户资产汇总数据生成
	 */
	public R<Void> accountAssetSummaryReport() {
		List<AccountAssetSummaryReport> allProcessedReports = null;
		Long star = System.currentTimeMillis();
		try {
			String statisticalTime = accountAssetSummaryReportMapper.getMaxFlingDate();
			List<CustomerCashAssetsHistory> cashAssetsHistoryList =
				customerCashAssetsHistoryMapper.selectCashAssetsHistoryByStatisticalTime(statisticalTime);

			if (cashAssetsHistoryList.isEmpty()) {
				log.info("客户账户资产汇总,据上一次统计时间:{}之后已经没有最新数据，请先同步用户现金资产数据!", statisticalTime);
				return R.success(String.format("客户账户资产汇总,据上一次统计时间:%s之后已经没有最新数据，请先同步用户现金资产数据!", statisticalTime));
			}

			Map<String, List<AccountAssetSummaryReport>> currencyAssetMap = processCurrencyAssets(cashAssetsHistoryList);

			CompletableFuture<List<AccountAssetSummaryReport>> hkdFuture = processAssetFuture(currencyAssetMap.get(CurrencyType.HKD.getCode()));
			CompletableFuture<List<AccountAssetSummaryReport>> usdFuture = processAssetFuture(currencyAssetMap.get(CurrencyType.USD.getCode()));
			CompletableFuture<List<AccountAssetSummaryReport>> cnyFuture = processAssetFuture(currencyAssetMap.get(CurrencyType.CNY.getCode()));

			CompletableFuture.allOf(hkdFuture, usdFuture, cnyFuture).join();

			allProcessedReports = Stream.of(hkdFuture, usdFuture, cnyFuture)
				.flatMap(future -> future.join().stream())
				.collect(Collectors.toList());

			saveProcessedReports(allProcessedReports);

			log.info("账户资产汇总报告处理完成，共处理: {} 条记录,耗时: {} 秒", allProcessedReports.size(), (System.currentTimeMillis() - star) / 1000);
			return R.success(String.format("账户资产汇总报告处理完成，共处理: %s 条记录,耗时: %s 秒", allProcessedReports.size(), (System.currentTimeMillis() - star) / 1000));
		} catch (Exception e) {
			log.error("账户资产汇总处理失败->{}", e.getMessage());
			return R.fail(String.format("账户资产汇总处理失败->%s", e.getMessage()));
		}
	}

	/**
	 * 保存处理后的报表数据
	 *
	 * @param reports
	 */
	private void saveProcessedReports(List<AccountAssetSummaryReport> reports) {
		try {
			int batchSize = 1000; // 可以根据实际情况调整批量大小
			for (int i = 0; i < reports.size(); i += batchSize) {
				List<AccountAssetSummaryReport> batch = reports.subList(i, Math.min(i + batchSize, reports.size()));
				accountAssetSummaryReportMapper.batchInsertOrUpdate(batch);
			}
			log.info("成功保存 {} 条账户资产汇总记录", reports.size());
		} catch (Exception e) {
			log.error("保存账户资产汇总记录时发生错误", e);
			throw new RuntimeException("保存账户资产汇总记录失败", e);
		}
	}

	/**
	 * 处理不同币种的报表数据
	 *
	 * @param cashAssetsHistoryList
	 * @return
	 */
	private Map<String, List<AccountAssetSummaryReport>> processCurrencyAssets(List<CustomerCashAssetsHistory> cashAssetsHistoryList) {
		return cashAssetsHistoryList.stream()
			.flatMap(history -> Stream.of(CurrencyType.values())
				.map(currency -> createAccountAssetSummaryReportVO(history, currency)))
			.collect(Collectors.groupingBy(AccountAssetSummaryReport::getCurrency));
	}

	/**
	 * 创建账户资产汇总报表对象
	 *
	 * @param history
	 * @param currency
	 * @return
	 */
	private AccountAssetSummaryReport createAccountAssetSummaryReportVO(CustomerCashAssetsHistory history, CurrencyType currency) {
		AccountAssetSummaryReport reportVO = new AccountAssetSummaryReport();
		reportVO.setAccountType("现金账户");
		reportVO.setCurrency(currency.getCode());
		reportVO.setAccountId(history.getAccountId());
		reportVO.setCustomerName(history.getCustomerName());
		reportVO.setSettlementTime(DateUtil.parseDate(history.getStatisticalTime()));
		reportVO.setSameDayBalance(getCurrencyBalance(history, currency));
		return reportVO;
	}

	/**
	 * 获取指定币种的余额
	 *
	 * @param history
	 * @param currency
	 * @return
	 */
	private BigDecimal getCurrencyBalance(CustomerCashAssetsHistory history, CurrencyType currency) {
		if (history == null || currency == null) {
			return BigDecimal.ZERO;
		}
		switch (currency) {
			case HKD:
				return history.getHkdAssets();
			case USD:
				return history.getUsdAssets();
			case CNY:
				return history.getCnyAssets();
			default:
				return BigDecimal.ZERO;
		}
	}

	/**
	 * 处理报表数据
	 *
	 * @param assetList
	 * @return
	 */
	private CompletableFuture<List<AccountAssetSummaryReport>> processAssetFuture(List<AccountAssetSummaryReport> assetList) {
		return CompletableFuture.supplyAsync(() -> {
			assetList.forEach(reportVO -> {
				setLastDayBalance(reportVO);
				setSettlementAmount(reportVO);
			});
			return assetList;
		});
	}

	/**
	 * 设置前一天（T-1）余额
	 *
	 * @param reportVO
	 */
	private void setLastDayBalance(AccountAssetSummaryReport reportVO) {
		String lastDay = DateUtil.plusDays(reportVO.getSettlementTime(), -1, "yyyy-MM-dd");
		CustomerCashAssetsHistory cashAssetsHistory =
			customerCashAssetsHistoryMapper.selectByAccountIdAndStatisticalTime(reportVO.getAccountId(), lastDay);

		BigDecimal lastDayBalance = getCurrencyBalance(cashAssetsHistory, CurrencyType.valueOf(reportVO.getCurrency()));
		reportVO.setLastDayBalance(lastDayBalance.setScale(2, RoundingMode.HALF_UP));
	}

	/**
	 * 设置结算金额
	 *
	 * @param reportVO
	 */
	private void setSettlementAmount(AccountAssetSummaryReport reportVO) {
		List<FinancingAccountAmountFlows> flowsList = financingAccountAmountFlowsMapper.getTodayFlows(
			reportVO.getAccountId(),
			reportVO.getCurrency(),
			DateUtil.formatDate(reportVO.getSettlementTime()));

		BigDecimal settlement = Optional.of(flowsList)
			.filter(flows -> !flows.isEmpty())
			.map(this::calculateSettlement)
			.orElse(BigDecimal.ZERO);

		reportVO.setSettlement(settlement);
		reportVO.setDepositBalance(calculateBalance(flowsList, this::isDepositFlow));
		reportVO.setWithdrawalBalance(calculateBalance(flowsList, this::isWithdrawalFlow));
	}

	/**
	 * 计算结算金额
	 *
	 * @param flowsList
	 * @return
	 */
	private BigDecimal calculateSettlement(List<FinancingAccountAmountFlows> flowsList) {
		BigDecimal depositBalance = calculateBalance(flowsList, this::isDepositFlow);
		BigDecimal withdrawalBalance = calculateBalance(flowsList, this::isWithdrawalFlow);
		return depositBalance.subtract(withdrawalBalance);
	}

	/**
	 * 计算余额
	 *
	 * @param flowsList
	 * @param flowTypePredicate
	 * @return
	 */
	private BigDecimal calculateBalance(List<FinancingAccountAmountFlows> flowsList, Predicate<ThawingType> flowTypePredicate) {
		return flowsList.stream()
			.filter(flow -> flowTypePredicate.test(ThawingType.getByCode(flow.getBusinessType())))
			.map(FinancingAccountAmountFlows::getAmount)
			.reduce(BigDecimal.ZERO, BigDecimal::add)
			.setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * 判断是否为入金业务类型
	 *
	 * @param businessType
	 * @return
	 */
	private boolean isDepositFlow(ThawingType businessType) {
		return DEPOSIT_TYPES.contains(businessType);
	}

	/**
	 * 判断是否为出金业务类型
	 *
	 * @param businessType
	 * @return
	 */
	private boolean isWithdrawalFlow(ThawingType businessType) {
		return WITHDRAWAL_TYPES.contains(businessType);
	}

	/**
	 * 入金业务类型
	 */
	private static final EnumSet<ThawingType> DEPOSIT_TYPES = EnumSet.of(
		ThawingType.EDDA_GOLD_DEPOSIT,
		ThawingType.FPS_GOLD_DEPOSIT,
		ThawingType.E_Bank_GOLD_DEPOSIT,
		ThawingType.OPEN_ACCOUNT_DEPOSIT,
		ThawingType.MANUAL_DEPOSIT,
		ThawingType.WITHDRAWALS_REFUND_FAILED_DEPOSIT
	);

	/**
	 * 出金业务类型
	 */
	private static final EnumSet<ThawingType> WITHDRAWAL_TYPES = EnumSet.of(
		ThawingType.WIRE_TRANSFER_WITHDRAWAL,
		ThawingType.ORDINARY_TRANSFER_WITHDRAWAL,
		ThawingType.LOCAL_TRANSFER_WITHDRAWAL
	);
}
