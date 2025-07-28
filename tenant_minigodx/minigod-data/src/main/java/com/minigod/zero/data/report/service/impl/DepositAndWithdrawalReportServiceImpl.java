package com.minigod.zero.data.report.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.common.utils.DateUtils;
import com.minigod.zero.data.common.factory.ExchangeRateFactory;
import com.minigod.zero.data.enums.BpmCommonEnum;
import com.minigod.zero.data.enums.CurrencyType;
import com.minigod.zero.data.enums.SupportTypeEnum;
import com.minigod.zero.data.enums.SystemCommonEnum;
import com.minigod.zero.data.mapper.SecDepositFundsMapper;
import com.minigod.zero.data.report.service.DepositAndWithdrawalReportService;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.data.dto.DepositAndWithdrawalReportDTO;
import org.springframework.stereotype.Service;
import com.minigod.zero.data.vo.DepositAndWithdrawalFundsReportVO;
import com.minigod.zero.data.vo.ReportVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @Title: 客戶出入金报表
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/26 17:02
 * @description：
 */
@Service
public class DepositAndWithdrawalReportServiceImpl implements DepositAndWithdrawalReportService {
	private final ExchangeRateFactory exchangeRateFactory;
	private final SecDepositFundsMapper secDepositFundsMapper;

	public DepositAndWithdrawalReportServiceImpl(ExchangeRateFactory exchangeRateFactory,
												 SecDepositFundsMapper secDepositFundsMapper) {
		this.exchangeRateFactory = exchangeRateFactory;
		this.secDepositFundsMapper = secDepositFundsMapper;
	}

	private static final Map<String, BiConsumer<ReportVO, BigDecimal>> DEPOSIT_HANDLERS = initDepositHandlers();
	private static final Map<String, BiConsumer<ReportVO, BigDecimal>> WITHDRAWAL_HANDLERS = initWithdrawalHandlers();

	/**
	 * 初始化入金报表处理器
	 *
	 * @return
	 */
	private static Map<String, BiConsumer<ReportVO, BigDecimal>> initDepositHandlers() {
		Map<String, BiConsumer<ReportVO, BigDecimal>> handlers = new HashMap<>();
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
	private static Map<String, BiConsumer<ReportVO, BigDecimal>> initWithdrawalHandlers() {
		Map<String, BiConsumer<ReportVO, BigDecimal>> handlers = new HashMap<>();
		handlers.put(CurrencyType.HKD.getCode(), (vo, amount) -> vo.setWithdrawalHKD(updateAmount(vo.getWithdrawalHKD(), amount)));
		handlers.put(CurrencyType.USD.getCode(), (vo, amount) -> vo.setWithdrawalUSD(updateAmount(vo.getWithdrawalUSD(), amount)));
		handlers.put(CurrencyType.CNY.getCode(), (vo, amount) -> vo.setWithdrawalCNY(updateAmount(vo.getWithdrawalCNY(), amount)));
		return Collections.unmodifiableMap(handlers);
	}

	/**
	 * 出入金报表
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public ReportVO depositAndWithdrawalReport(DepositAndWithdrawalReportDTO dto) {
		// 处理 applicationIds 并设置到 dto
		List<String> applicationIdList = StringUtil.isNotBlank(dto.getApplicationIds()) ?
			Arrays.asList(dto.getApplicationIds().split(",")) : null;
		dto.setApplicationIdList(applicationIdList);

		// 任务1: 查询并处理主要列表
		Page<DepositAndWithdrawalFundsReportVO> page = new Page<>(dto.getPageIndex(), dto.getPageSize());
		CompletableFuture<List<DepositAndWithdrawalFundsReportVO>> listFuture = CompletableFuture.supplyAsync(() -> {
			List<DepositAndWithdrawalFundsReportVO> list = secDepositFundsMapper.queryReportPage(page, dto);
			if (CollectionUtil.isNotEmpty(list)) {
				list.forEach(this::processReport);
			}
			return list;
		});

		// 任务2: 查询并处理汇总数据
		CompletableFuture<ReportVO> summaryFuture = CompletableFuture.supplyAsync(() -> {
			ReportVO reportVO = new ReportVO();
			List<DepositAndWithdrawalFundsReportVO> reportList = secDepositFundsMapper.queryReportPage(null, dto);
			reportList.forEach(report -> processReportSummary(report, reportVO));
			return reportVO;
		});

		// 等待两个任务完成并组合结果
		CompletableFuture<ReportVO> resultFuture = listFuture.thenCombine(summaryFuture, (list, reportVO) -> {
			if (CollectionUtil.isEmpty(list)) {
				return new ReportVO();
			}
			reportVO.setPage(page.setRecords(list));
			return reportVO;
		});

		ReportVO reportVO = resultFuture.join();
		setTotalDepositAmount(reportVO);
		setTotalWithdrawalAmount(reportVO);
		return reportVO;
	}

	/**
	 * 处理资金数据报表
	 *
	 * @param report
	 */
	private void processReport(DepositAndWithdrawalFundsReportVO report) {
		try {
			if (report.getType() == 1) {
				processDepositReport(report);
			} else if (report.getType() == 2) {
				processWithdrawalReport(report);
			}
			report.setAmount(report.getAmount().setScale(2, RoundingMode.HALF_UP));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理存入资金数据报表
	 *
	 * @param report
	 */
	private void processDepositReport(DepositAndWithdrawalFundsReportVO report) {
		report.setTypeName("存入");
		if (report.getSupportType() != null &&
			Integer.valueOf(SupportTypeEnum.EDDA.getType()) == report.getSupportType()) {
			processEddaDeposit(report);
		} else {
			processOtherDeposit(report);
		}
	}

	/**
	 * 处理EDDA存入资金数据报表
	 *
	 * @param report
	 */
	private void processEddaDeposit(DepositAndWithdrawalFundsReportVO report) {
		report.setSupportTypeName(SupportTypeEnum.EDDA.getDesc());
		report.setStatusName(SystemCommonEnum.EDDABankStateTypeEnum.getDesc(report.getStatus()));
	}

	/**
	 * 处理其他存入资金数据报表
	 *
	 * @param report
	 */
	private void processOtherDeposit(DepositAndWithdrawalFundsReportVO report) {
		report.setSupportTypeName(SupportTypeEnum.getDesc(report.getSupportType()));
		if (report.getStatus() != null) {
			BpmCommonEnum.FundDepositStatus status = BpmCommonEnum.FundDepositStatus.valueOf(report.getStatus());
			report.setStatusName(status != null ? status.getDesc() : null);
		}
	}

	/**
	 * 处理出金数据报表
	 *
	 * @param report
	 */
	private void processWithdrawalReport(DepositAndWithdrawalFundsReportVO report) {
		report.setTypeName("提取");
		BpmCommonEnum.FundWithdrawStatus status = BpmCommonEnum.FundWithdrawStatus.valueOf(report.getStatus());
		report.setStatusName(status != null ? status.getDesc() : null);
		report.setSupportTypeName(SystemCommonEnum.TransferTypeEnum.getName(report.getSupportType()));
	}

	/**
	 * 处理报表汇总数据
	 *
	 * @param report
	 * @param reportVO
	 */
	private void processReportSummary(DepositAndWithdrawalFundsReportVO report, ReportVO reportVO) {
		String currency = report.getCurrency();
		BigDecimal amount = report.getAmount();

		Map<String, BiConsumer<ReportVO, BigDecimal>> handlers =
			report.getType() == 1 ? DEPOSIT_HANDLERS : WITHDRAWAL_HANDLERS;

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
	private void setTotalDepositAmount(ReportVO reportVO) {
		BigDecimal usdToHkd =
			exchangeRateFactory.exchange(reportVO.getDepositUSD(), CurrencyType.USD.getCode(),CurrencyType.HKD.getCode());
		BigDecimal cnyToHkd =
			exchangeRateFactory.exchange(reportVO.getDepositCNY(), CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());

		reportVO.setTotalDepositHKD(reportVO.getDepositHKD().add(updateAmount(usdToHkd, cnyToHkd)));
	}

	/**
	 * 出金金额汇总
	 *
	 * @param reportVO
	 */
	private void setTotalWithdrawalAmount(ReportVO reportVO) {
		BigDecimal usdToHkd =
			exchangeRateFactory.exchange(reportVO.getWithdrawalUSD(), CurrencyType.USD.getCode(),CurrencyType.HKD.getCode() );
		BigDecimal cnyToHkd =
			exchangeRateFactory.exchange( reportVO.getWithdrawalCNY(), CurrencyType.CNY.getCode(),CurrencyType.HKD.getCode());

		reportVO.setTotalWithdrawalHKD(reportVO.getWithdrawalHKD().add(updateAmount(usdToHkd, cnyToHkd)));
	}

	/**
	 * 更新金额
	 *
	 * @param currentAmount
	 * @param amountToAdd
	 * @return
	 */
	private static BigDecimal updateAmount(BigDecimal currentAmount, BigDecimal amountToAdd) {
		return currentAmount.add(amountToAdd).setScale(2, RoundingMode.HALF_UP);
	}
}
