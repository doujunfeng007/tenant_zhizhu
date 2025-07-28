package com.minigod.zero.data.report.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.data.common.factory.ExchangeRateFactory;
import com.minigod.zero.data.enums.CurrencyType;
import com.minigod.zero.data.mapper.AccountAssetSummaryReportMapper;
import com.minigod.zero.data.entity.AccountAssetSummaryReport;
import com.minigod.zero.data.report.service.CustomerFundDetailsReportService;
import com.minigod.zero.data.dto.CustomerFundDetailsReportDTO;
import com.minigod.zero.data.vo.AccountAssetSummaryReportVO;
import com.minigod.zero.data.vo.SummaryVO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import com.minigod.zero.data.vo.CustomerFinancialDetailsReportVO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 客户资金明细报表
 *
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/23 9:43
 * @description：
 */
@Service
public class CustomerFundDetailsReportServiceImpl implements CustomerFundDetailsReportService {

	private final ExchangeRateFactory exchangeRateFactory;
	private final AccountAssetSummaryReportMapper accountAssetSummaryReportMapper;

	public CustomerFundDetailsReportServiceImpl(ExchangeRateFactory exchangeRateFactory, AccountAssetSummaryReportMapper accountAssetSummaryReportMapper) {
		this.exchangeRateFactory = exchangeRateFactory;
		this.accountAssetSummaryReportMapper = accountAssetSummaryReportMapper;
	}

	/**
	 * 客户资金明细报表
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public CustomerFinancialDetailsReportVO financialDetailsReport(CustomerFundDetailsReportDTO dto) {
		if (!StringUtils.isEmpty(dto.getIds())) {
			dto.setIdList(Arrays.asList(dto.getIds().split(",")));
		}
		Page<AccountAssetSummaryReportVO> page = new Page<>(dto.getPageIndex(), dto.getPageSize());
		List<AccountAssetSummaryReport> list = accountAssetSummaryReportMapper.queryPage(page, dto);

		List<AccountAssetSummaryReportVO> pageList = list.stream().map(report->{
			AccountAssetSummaryReportVO reportVO = new AccountAssetSummaryReportVO();
			BeanUtil.copyProperties(report,reportVO);
			reportVO.setStatisticalTime(DateUtil.formatDate(report.getSettlementTime()));
			reportVO.setTodayBalance(report.getSameDayBalance());
			return reportVO;
		}).collect(Collectors.toList());

		CustomerFinancialDetailsReportVO report = new CustomerFinancialDetailsReportVO();
		report.setPage(page.setRecords(pageList));

		if (list.isEmpty()) {
			return report;
		}

		List<SummaryVO> summaryData = accountAssetSummaryReportMapper.selectCustomerFundDetailsSummary(dto);
		// 按币种汇总
		Map<CurrencyType, SummaryVO> summaryMap = summaryData.stream().collect(Collectors.toMap(
			summary -> CurrencyType.valueOf(summary.getCurrency()),
			Function.identity(),
			(s1, s2) -> s1,
			() -> new EnumMap<>(CurrencyType.class)
		));

		report.setHkd(summaryMap.get(CurrencyType.HKD));
		report.setUsd(summaryMap.get(CurrencyType.USD));
		report.setCny(summaryMap.get(CurrencyType.CNY));

		summaryTotal(report);
		return report;
	}

	/**
	 * 汇总结果汇率转换
	 *
	 * @param report
	 */
	private void summaryTotal(CustomerFinancialDetailsReportVO report) {
		Map<String, BigDecimal> totalMap = new HashedMap();

		for (CurrencyType currency : CurrencyType.values()) {
			SummaryVO summary = getSummaryForCurrency(report, currency);
			if (summary == null) continue;

			for (String field : Arrays.asList("settlement", "lastDayBalance", "depositBalance", "withdrawalBalance")) {
				BigDecimal amount = (BigDecimal) ReflectionUtils.getField(ReflectionUtils.findField(SummaryVO.class, field), summary);
				if (currency != CurrencyType.HKD) {
					amount = exchangeRateFactory.exchange(amount,currency.getCode(), CurrencyType.HKD.getCode());
				}
				totalMap.merge(field, amount, BigDecimal::add);
			}
		}

		report.setTotal(SummaryVO.buildSummary(
			totalMap.get("lastDayBalance").setScale(2, RoundingMode.HALF_UP),
			totalMap.get("depositBalance").setScale(2, RoundingMode.HALF_UP),
			totalMap.get("withdrawalBalance").setScale(2, RoundingMode.HALF_UP),
			totalMap.get("settlement").setScale(2, RoundingMode.HALF_UP)
		));
	}

	/**
	 * 获取指定币种的汇总结果
	 *
	 * @param report
	 * @param currency
	 * @return
	 */
	private SummaryVO getSummaryForCurrency(CustomerFinancialDetailsReportVO report, CurrencyType currency) {
		switch (currency) {
			case HKD:
				return report.getHkd();
			case USD:
				return report.getUsd();
			case CNY:
				return report.getCny();
			default:
				return null;
		}
	}
}
