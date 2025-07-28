package com.minigod.zero.data.statistics.service.impl;

import com.minigod.zero.data.common.factory.ExchangeRateFactory;
import com.minigod.zero.data.enums.CurrencyType;
import com.minigod.zero.data.mapper.DepositAndWithdrawalFundsSummaryMapper;
import com.minigod.zero.data.statistics.service.SecDepositFundsService;
import com.minigod.zero.data.vo.DepositTotalAmountVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @description: 客户入金总额统计服务
 * @author: eric
 * @since 2024-10-29 14:43:05
 */
@Slf4j
@Service
public class SecDepositFundsServiceImpl implements SecDepositFundsService {
	private final DepositAndWithdrawalFundsSummaryMapper depositAndWithdrawalFundsSummaryMapper;
	private final ExchangeRateFactory exchangeRateFactory;

	public SecDepositFundsServiceImpl(DepositAndWithdrawalFundsSummaryMapper depositAndWithdrawalFundsSummaryMapper, ExchangeRateFactory exchangeRateFactory) {
		this.depositAndWithdrawalFundsSummaryMapper = depositAndWithdrawalFundsSummaryMapper;
		this.exchangeRateFactory = exchangeRateFactory;
	}

	/**
	 * 统计客户入金总额
	 *
	 * @return 入金总额统计结果
	 */
	@Override
	public DepositTotalAmountVO calculateDepositTotalAmount() {
		// 1. 查询各币种入金总额
		List<Map<String, Object>> totalDepositAmounts = depositAndWithdrawalFundsSummaryMapper.selectDepositTotalAmount();

		// 2. 初始化结果对象
		DepositTotalAmountVO result = new DepositTotalAmountVO();
		BigDecimal totalHkdDepositAmount = BigDecimal.ZERO;

		// 3. 遍历结果并分类统计
		for (Map<String, Object> amount : totalDepositAmounts) {
			Integer currency = (Integer) amount.get("currency");
			BigDecimal totalAmount = (BigDecimal) amount.get("totalAmount");

			switch (currency) {
				case 1: // HKD
					result.setHkdAmount(totalAmount);
					totalHkdDepositAmount = totalHkdDepositAmount.add(totalAmount);
					break;
				case 2: // USD
					result.setUsdAmount(totalAmount);
					// 转换USD到HKD
					BigDecimal usdToHkd = exchangeRateFactory.exchange(totalAmount, CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());
					totalHkdDepositAmount = totalHkdDepositAmount.add(usdToHkd);
					break;
				case 3: // CNY
					result.setCnyAmount(totalAmount);
					// 转换CNY到HKD
					BigDecimal cnyToHkd = exchangeRateFactory.exchange(totalAmount, CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());
					totalHkdDepositAmount = totalHkdDepositAmount.add(cnyToHkd);
					break;
				default:
					log.warn("不支持的币种: {}", currency);
			}
		}

		// 4. 设置港币入金总额
		result.setTotalHkdAmount(totalHkdDepositAmount);
		return result;
	}
}
