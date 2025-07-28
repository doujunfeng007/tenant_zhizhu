package com.minigod.zero.data.statistics.service.impl;

import com.minigod.common.utils.DateUtils;
import com.minigod.zero.data.common.factory.ExchangeRateFactory;
import com.minigod.zero.data.dto.DepositAndWithdrawalStatisticsDTO;
import com.minigod.zero.data.enums.CurrencyType;
import com.minigod.zero.data.mapper.ClientEddaFundApplicationMapper;
import com.minigod.zero.data.mapper.DepositAndWithdrawalFundsSummaryMapper;
import com.minigod.zero.data.statistics.service.DepositAndWithdrawalStatisticsService;
import com.minigod.zero.data.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * 出入金统计及每日净入金总额统计
 *
 * @author eric
 * @since 2024-10-30 11:06:05
 */
@Slf4j
@Service
public class DepositAndWithdrawalStatisticsServiceImpl implements DepositAndWithdrawalStatisticsService {
	private final ExchangeRateFactory exchangeRateFactory;
	private final DepositAndWithdrawalFundsSummaryMapper fundsSummaryMapper;
	private final ClientEddaFundApplicationMapper eddaFundApplicationMapper;

	public DepositAndWithdrawalStatisticsServiceImpl(ExchangeRateFactory exchangeRateFactory,
													 DepositAndWithdrawalFundsSummaryMapper fundsSummaryMapper,
													 ClientEddaFundApplicationMapper eddaFundApplicationMapper) {
		this.exchangeRateFactory = exchangeRateFactory;
		this.fundsSummaryMapper = fundsSummaryMapper;
		this.eddaFundApplicationMapper = eddaFundApplicationMapper;
	}

	private static final Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> DEPOSIT_HANDLERS = initDepositHandlers();
	private static final Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> WITHDRAWAL_HANDLERS = initWithdrawalHandlers();

	private static final Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> DEPOSIT_TODAY_HANDLERS = initDepositTodayHandlers();
	private static final Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> WITHDRAWAL_TODAY_HANDLERS = initWithdrawalTodayHandlers();

	/**
	 * 出入金统计
	 *
	 * @return
	 */
	@Override
	public DepositAndWithdrawalFundsSummaryVO getDepositAmountAndTotalAmountByDay(DepositAndWithdrawalStatisticsDTO dto) {
		//查询并处理汇总数据
		CompletableFuture<DepositAndWithdrawalFundsSummaryVO> summaryFuture = CompletableFuture.supplyAsync(() -> {
			DepositAndWithdrawalFundsSummaryVO summaryVO = new DepositAndWithdrawalFundsSummaryVO();
			List<DepositAndWithdrawalFundsRecordVO> reportList = fundsSummaryMapper.queryDepositAndWithdrawalFundsSummary(dto);
			reportList.forEach(report -> this.processReportSummary(report, summaryVO));
			reportList.forEach(report -> this.processTodayReportSummary(report, summaryVO));
			this.setDayRecordsHKD(dto.getStartTime(), dto.getEndTime(), reportList, summaryVO);
			return summaryVO;
		});

		DepositAndWithdrawalFundsSummaryVO summaryVO = summaryFuture.join();
		this.setTotalDepositAmount(summaryVO);
		this.setTotalWithdrawalAmount(summaryVO);
		this.setTotalIncreaseTotalHKD(summaryVO);
		this.setTodayDepositAmount(summaryVO);
		this.setTodayWithdrawalAmount(summaryVO);

		//计算今日净增金额和今日净出金额
		summaryVO.setTodayIncreaseTotalHKD(summaryVO.getTodayTotalDepositHKD().subtract(summaryVO.getTodayTotalWithdrawalHKD()));
		summaryVO.setTodayDecreaseTotalHKD(summaryVO.getTodayTotalWithdrawalHKD().subtract(summaryVO.getTodayTotalDepositHKD()));
		return summaryVO;
	}

	/**
	 * 统计出入金申请笔数
	 */
	@Override
	public DepositAndWithdrawalApplyCountVO countDepositAndWithdrawalApply() {
		//Long eddaDepositApplyCount = eddaFundApplicationMapper.countEDDADepositApply();
		Long depositApplyCount = fundsSummaryMapper.countDepositApply();
		Long withdrawalApplyCount = fundsSummaryMapper.countWithdrawalApply();
		// 不需要再单独加上EDDA入金的，在depositApplyCount中已经包含了EDDA入金的记录
		// Long totalDepositApplyCount = depositApplyCount + eddaDepositApplyCount;
		Long totalDepositApplyCount = depositApplyCount;
		DepositAndWithdrawalApplyCountVO applyCountVO = new DepositAndWithdrawalApplyCountVO();
		applyCountVO.setDepositApplyCount(totalDepositApplyCount);
		applyCountVO.setWithdrawalApplyCount(withdrawalApplyCount);

		return applyCountVO;
	}

	/**
	 * 统计出金总数
	 */
	@Override
	public WithdrawalTotalAmountVO countWithdrawalTotalAmount() {
		List<Map<String, Object>> totalWithdrawalAmounts = fundsSummaryMapper.selectWithdrawalTotalAmount();
		// 2. 初始化结果对象
		WithdrawalTotalAmountVO result = new WithdrawalTotalAmountVO();
		BigDecimal totalHkdWithdrawalAmount = BigDecimal.ZERO;

		//4.遍历结果并分类统计
		for (Map<String, Object> amount : totalWithdrawalAmounts) {
			String currency = (String) amount.get("currency");
			BigDecimal totalAmount = (BigDecimal) amount.get("totalAmount");

			switch (currency) {
				case "HKD":
					result.setHkdAmount(totalAmount);
					totalHkdWithdrawalAmount = totalHkdWithdrawalAmount.add(totalAmount);
					break;
				case "USD":
					result.setUsdAmount(totalAmount);
					// 转换USD到HKD
					BigDecimal usdToHkd = exchangeRateFactory.exchange(totalAmount, CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());
					totalHkdWithdrawalAmount = totalHkdWithdrawalAmount.add(usdToHkd);
					break;
				case "CNY":
					result.setCnyAmount(totalAmount);
					// 转换CNY到HKD
					BigDecimal cnyToHkd = exchangeRateFactory.exchange(totalAmount, CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());
					totalHkdWithdrawalAmount = totalHkdWithdrawalAmount.add(cnyToHkd);
					break;
				default:
					log.warn("不支持的币种: {}", currency);
			}
		}
		// 4. 设置港币入金总额
		result.setTotalHkdAmount(totalHkdWithdrawalAmount);
		return result;
	}

	/**
	 * 初始化入金报表处理器
	 *
	 * @return
	 */
	private static Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> initDepositHandlers() {
		Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> handlers = new HashMap<>();
		handlers.put(CurrencyType.HKD.getCode(), (vo, amount) -> vo.setDepositHKD(updateAmount(vo.getDepositHKD(), amount)));
		handlers.put(CurrencyType.USD.getCode(), (vo, amount) -> vo.setDepositUSD(updateAmount(vo.getDepositUSD(), amount)));
		handlers.put(CurrencyType.CNY.getCode(), (vo, amount) -> vo.setDepositCNY(updateAmount(vo.getDepositCNY(), amount)));
		return Collections.unmodifiableMap(handlers);
	}

	/**
	 * 初始化出金报表处理器
	 *
	 * @return
	 */
	private static Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> initWithdrawalHandlers() {
		Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> handlers = new HashMap<>();
		handlers.put(CurrencyType.HKD.getCode(), (vo, amount) -> vo.setWithdrawalHKD(updateAmount(vo.getWithdrawalHKD(), amount)));
		handlers.put(CurrencyType.USD.getCode(), (vo, amount) -> vo.setWithdrawalUSD(updateAmount(vo.getWithdrawalUSD(), amount)));
		handlers.put(CurrencyType.CNY.getCode(), (vo, amount) -> vo.setWithdrawalCNY(updateAmount(vo.getWithdrawalCNY(), amount)));
		return Collections.unmodifiableMap(handlers);
	}

	/**
	 * 初始化今日入金报表处理器
	 *
	 * @return
	 */
	private static Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> initDepositTodayHandlers() {
		Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> handlers = new HashMap<>();
		handlers.put(CurrencyType.HKD.getCode(), (vo, amount) -> vo.setTodayDepositHKD(vo.getTodayDepositHKD().add(amount)));
		handlers.put(CurrencyType.USD.getCode(), (vo, amount) -> vo.setTodayDepositUSD(vo.getTodayDepositUSD().add(amount)));
		handlers.put(CurrencyType.CNY.getCode(), (vo, amount) -> vo.setTodayDepositCNY(vo.getTodayDepositCNY().add(amount)));
		return Collections.unmodifiableMap(handlers);
	}

	/**
	 * 初始化今日出金报表处理器
	 *
	 * @return
	 */
	private static Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> initWithdrawalTodayHandlers() {
		Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> handlers = new HashMap<>();
		handlers.put(CurrencyType.HKD.getCode(), (vo, amount) -> vo.setTodayWithdrawalHKD(vo.getTodayWithdrawalHKD().add(amount)));
		handlers.put(CurrencyType.USD.getCode(), (vo, amount) -> vo.setTodayWithdrawalUSD(vo.getTodayWithdrawalUSD().add(amount)));
		handlers.put(CurrencyType.CNY.getCode(), (vo, amount) -> vo.setTodayWithdrawalCNY(vo.getTodayWithdrawalCNY().add(amount)));
		return Collections.unmodifiableMap(handlers);
	}

	/**
	 * 处理出金和入金汇总数据
	 *
	 * @param report
	 * @param reportVO
	 */
	private void processReportSummary(DepositAndWithdrawalFundsRecordVO report, DepositAndWithdrawalFundsSummaryVO reportVO) {
		String currency = report.getCurrency();
		BigDecimal amount = report.getAmount() == null ? BigDecimal.ZERO : report.getAmount();

		Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> handlers =
			report.getType() == 1 ? DEPOSIT_HANDLERS : WITHDRAWAL_HANDLERS;

		handlers.getOrDefault(currency, (vo, a) -> {
		}).accept(reportVO, amount);
	}

	/**
	 * 处理今日出金和入金汇总数据
	 *
	 * @param report
	 * @param reportVO
	 */
	private void processTodayReportSummary(DepositAndWithdrawalFundsRecordVO report, DepositAndWithdrawalFundsSummaryVO reportVO) {
		if (!isToday(report.getCreateTime())) {
			return;
		}
		String currency = report.getCurrency();
		BigDecimal amount = report.getAmount() == null ? BigDecimal.ZERO : report.getAmount();

		Map<String, BiConsumer<DepositAndWithdrawalFundsSummaryVO, BigDecimal>> handlers =
			report.getType() == 1 ? DEPOSIT_TODAY_HANDLERS : WITHDRAWAL_TODAY_HANDLERS;

		handlers.getOrDefault(currency, (vo, a) -> {
		}).accept(reportVO, amount);
	}

	/**
	 * 判断日期是否为今天
	 *
	 * @param date
	 * @return
	 */
	private boolean isToday(String date) {
		String today = DateUtils.getDate();
		String reportDate = DateUtils.convertToYMD(date);
		return today.equals(reportDate);
	}

	/**
	 * 存入金额汇总
	 *
	 * @param reportVO
	 */
	private void setTotalDepositAmount(DepositAndWithdrawalFundsSummaryVO reportVO) {
		BigDecimal usdToHkd =
			exchangeRateFactory.exchange(reportVO.getDepositUSD(), CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());
		BigDecimal cnyToHkd =
			exchangeRateFactory.exchange(reportVO.getDepositCNY(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());

		reportVO.setTotalDepositHKD(reportVO.getDepositHKD().add(updateAmount(usdToHkd, cnyToHkd)));
	}

	/**
	 * 出金金额汇总
	 *
	 * @param reportVO
	 */
	private void setTotalWithdrawalAmount(DepositAndWithdrawalFundsSummaryVO reportVO) {
		BigDecimal usdToHkd =
			exchangeRateFactory.exchange(reportVO.getWithdrawalUSD(), CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());
		BigDecimal cnyToHkd =
			exchangeRateFactory.exchange(reportVO.getWithdrawalCNY(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());

		reportVO.setTotalWithdrawalHKD(reportVO.getWithdrawalHKD().add(updateAmount(usdToHkd, cnyToHkd)));
	}

	/**
	 * 入金总金额-出金总金额
	 *
	 * @param reportVO
	 */
	private void setTotalIncreaseTotalHKD(DepositAndWithdrawalFundsSummaryVO reportVO) {
		reportVO.setTotalDepositHKD(reportVO.getTotalDepositHKD().subtract(reportVO.getTotalWithdrawalHKD()));
	}

	/**
	 * 今日入金金额汇总
	 *
	 * @param reportVO
	 */
	private void setTodayDepositAmount(DepositAndWithdrawalFundsSummaryVO reportVO) {
		BigDecimal usdToHkd = exchangeRateFactory.exchange(reportVO.getTodayDepositUSD(), CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());
		BigDecimal cnyToHkd = exchangeRateFactory.exchange(reportVO.getTodayDepositCNY(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());

		reportVO.setTodayTotalDepositHKD(reportVO.getTodayTotalDepositHKD().add(reportVO.getTodayDepositHKD()).add(updateAmount(usdToHkd, cnyToHkd)));
	}

	/**
	 * 今日出金金额汇总
	 *
	 * @param reportVO
	 */
	private void setTodayWithdrawalAmount(DepositAndWithdrawalFundsSummaryVO reportVO) {
		BigDecimal usdToHkd = exchangeRateFactory.exchange(reportVO.getTodayWithdrawalUSD(), CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());
		BigDecimal cnyToHkd = exchangeRateFactory.exchange(reportVO.getTodayWithdrawalCNY(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());

		reportVO.setTodayTotalWithdrawalHKD(reportVO.getTodayTotalWithdrawalHKD().add(reportVO.getTodayWithdrawalHKD()).add(updateAmount(usdToHkd, cnyToHkd)));
	}

	/**
	 * 更新金额
	 *
	 * @param currentAmount
	 * @param amountToAdd
	 * @return
	 */
	private static BigDecimal updateAmount(BigDecimal currentAmount, BigDecimal amountToAdd) {
		return currentAmount.add(amountToAdd);
	}

	/**
	 * 按日为单位分组计算净入金记录
	 *
	 * @param startTime
	 * @param endTime
	 * @param dayRecords
	 * @param summaryVO
	 */
	private void setDayRecordsHKD(String startTime, String endTime, List<DepositAndWithdrawalFundsRecordVO> dayRecords, DepositAndWithdrawalFundsSummaryVO summaryVO) {
		// 生成startTime和endTime之间的所有日期
		List<String> allDates = DateUtils.getDaysBetween(startTime, endTime);

		// 按日期分组记录
		Map<String, List<DepositAndWithdrawalFundsRecordVO>> dayDepositRecordsMap = dayRecords.stream()
			.filter(record -> record.getType().equals(1))
			.collect(Collectors.groupingBy(record -> DateUtils.convertToYMD(record.getCreateTime())));

		Map<String, List<DepositAndWithdrawalFundsRecordVO>> dayWithdrawalRecordsMap = dayRecords.stream()
			.filter(record -> record.getType().equals(2))
			.collect(Collectors.groupingBy(record -> DateUtils.convertToYMD(record.getCreateTime())));

		// 初始化累计金额
		BigDecimal cumulativeDepositHKD = BigDecimal.ZERO;
		BigDecimal cumulativeWithdrawalHKD = BigDecimal.ZERO;

		List<DepositAndWithdrawalFundsSummaryVO.DayDepositRecords> summaryDayDepositRecords = new ArrayList<>();
		List<DepositAndWithdrawalFundsSummaryVO.DayWithdrawalRecords> summaryDayWithdrawalRecords = new ArrayList<>();

		// 处理范围内的每个日期
		for (String currentDate : allDates) {
			// 计算当前日期的入金金额
			BigDecimal dayDepositHKD = dayDepositRecordsMap.getOrDefault(currentDate, new ArrayList<>()).stream()
				.map(record -> {
					switch (record.getCurrency()) {
						case "HKD":
							return record.getAmount();
						case "USD":
							return exchangeRateFactory.exchange(record.getAmount(), CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());
						case "CNY":
							return exchangeRateFactory.exchange(record.getAmount(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());
						default:
							return BigDecimal.ZERO;
					}
				})
				.reduce(BigDecimal.ZERO, BigDecimal::add);

			// 计算当前日期的出金金额
			BigDecimal dayWithdrawalHKD = dayWithdrawalRecordsMap.getOrDefault(currentDate, new ArrayList<>()).stream()
				.map(record -> {
					switch (record.getCurrency()) {
						case "HKD":
							return record.getAmount();
						case "USD":
							return exchangeRateFactory.exchange(record.getAmount(), CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());
						case "CNY":
							return exchangeRateFactory.exchange(record.getAmount(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());
						default:
							return BigDecimal.ZERO;
					}
				})
				.reduce(BigDecimal.ZERO, BigDecimal::add);

			 // 更新当天累计入金金额
			 cumulativeDepositHKD = cumulativeDepositHKD.add(dayDepositHKD.subtract(dayWithdrawalHKD));
			 // 更新当天累计出金金额
			 cumulativeWithdrawalHKD = cumulativeWithdrawalHKD.add(dayWithdrawalHKD);

			// 当天入金记录
			DepositAndWithdrawalFundsSummaryVO.DayDepositRecords depositRecord = new DepositAndWithdrawalFundsSummaryVO.DayDepositRecords();
			depositRecord.setDay(currentDate);
			// 当天累计入金金额
			depositRecord.setDepositHKD(cumulativeDepositHKD);
			summaryDayDepositRecords.add(depositRecord);
			// 当天出金记录
			DepositAndWithdrawalFundsSummaryVO.DayWithdrawalRecords withdrawalRecord = new DepositAndWithdrawalFundsSummaryVO.DayWithdrawalRecords();
			withdrawalRecord.setDay(currentDate);
			// 当天累计出金金额
			withdrawalRecord.setWithdrawalHKD(cumulativeWithdrawalHKD);
			summaryDayWithdrawalRecords.add(withdrawalRecord);
		}
		summaryVO.setDayDepositRecords(summaryDayDepositRecords);
		summaryVO.setDayWithdrawalRecords(summaryDayWithdrawalRecords);
	}
}
