package com.minigod.zero.data.statistics.service.impl;

import com.minigod.zero.data.common.factory.ExchangeRateFactory;
import com.minigod.zero.data.enums.CurrencyType;
import com.minigod.zero.data.mapper.OrderMapper;
import com.minigod.zero.data.statistics.service.OrderStatisticsService;
import com.minigod.zero.data.vo.OrderIPOSummaryVO;
import com.minigod.zero.data.vo.OrderStatisticsVO;
import com.minigod.zero.data.vo.OrderTotalAmountVO;

import com.minigod.zero.data.vo.OrderTotalCountVO;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单统计服务接口
 *
 * @author eric
 * @since 2024-10-29 15:18:08
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderStatisticsService {

	private final OrderMapper orderMapper;
	private final ExchangeRateFactory exchangeRateFactory;

	public OrderServiceImpl(OrderMapper orderMapper, ExchangeRateFactory exchangeRateFactory) {
		this.orderMapper = orderMapper;
		this.exchangeRateFactory = exchangeRateFactory;
	}

	/**
	 * 客户买入资产的总金额，
	 * 按币种分组，其中订单类型为 1（买入）或 11（IPO），
	 * 且状态为 300, 310, 510 或 730。
	 *
	 * @return
	 */
	@Override
	public OrderTotalAmountVO getTotalAmountByCurrency() {
		// 1. 查询各币种订单总额
		List<Map<String, Object>> totalAmounts = orderMapper.selectTotalAmountByCurrency();

		// 2. 初始化结果对象
		OrderTotalAmountVO result = new OrderTotalAmountVO();
		BigDecimal totalHkdAmount = BigDecimal.ZERO;

		// 3. 遍历结果并分类统计
		for (Map<String, Object> amount : totalAmounts) {
			String currency = (String) amount.get("currency");
			BigDecimal totalAmount = (BigDecimal) amount.get("totalAmount");
			if (!StringUtils.isEmpty(currency) && CurrencyType.getByCode(currency) != null) {
				switch (CurrencyType.getByCode(currency)) {
					case HKD:
						result.setHkdAmount(totalAmount);
						totalHkdAmount = totalHkdAmount.add(totalAmount);
						break;
					case USD:
						result.setUsdAmount(totalAmount);
						// 转换USD到HKD
						BigDecimal usdToHkd = exchangeRateFactory.exchange(totalAmount, CurrencyType.USD.getCode(), CurrencyType.HKD.getCode());
						totalHkdAmount = totalHkdAmount.add(usdToHkd);
						break;
					case CNY:
						result.setCnyAmount(totalAmount);
						// 转换CNY到HKD
						BigDecimal cnyToHkd = exchangeRateFactory.exchange(totalAmount, CurrencyType.CNY.getCode(), CurrencyType.HKD.getCode());
						totalHkdAmount = totalHkdAmount.add(cnyToHkd);
						break;
					default:
						log.warn("不支持的币种: {}", currency);
				}
			} else {
				log.warn("币种存在错误,当前记录无法正确统计,币种:{},金额:{}", currency, totalAmount);
			}
		}

		// 4. 设置港币总额
		result.setTotalHkdAmount(totalHkdAmount);
		return result;
	}

	/**
	 * 按产品类型统计订单情况
	 * 统计各产品类型的买入、卖出、撤单、成交数量
	 *
	 * @return List<OrderTotalCountVO> 订单统计结果列表
	 */
	@Override
	public List<OrderTotalCountVO> getTotalCountByBusiType() {
		return orderMapper.selectOrderStatsByBusiType();
	}

	/**
	 * 统计IPO订单情况
	 *
	 * @return
	 */
	@Override
	public List<OrderIPOSummaryVO> selectOrderStatistics() {
		List<OrderStatisticsVO> orderStatistics = orderMapper.selectOrderStatistics();

		// 首先按ISIN分组
		Map<String, List<OrderStatisticsVO>> isinGroups = new HashMap<>();
		for (OrderStatisticsVO stats : orderStatistics) {
			isinGroups.computeIfAbsent(stats.getIsin(), k -> new ArrayList<>()).add(stats);
		}

		List<OrderIPOSummaryVO> summaryList = new ArrayList<>();
		// 对每个ISIN组进行处理
		for (Map.Entry<String, List<OrderStatisticsVO>> entry : isinGroups.entrySet()) {
			String isin = entry.getKey();
			List<OrderStatisticsVO> isinOrders = entry.getValue();

			OrderIPOSummaryVO summary = new OrderIPOSummaryVO();
			summary.setIsin(isin);
			summary.setSubscribeCount(isinOrders.size());
			summary.setDaysToIssueDate(isinOrders.get(0).getDaysToIssueDate());

			// 计算转换后的总金额
			BigDecimal totalHkdAmount = BigDecimal.ZERO;
			for (OrderStatisticsVO order : isinOrders) {
				BigDecimal hkdAmount;
				switch (order.getCurrency()) {
					case "HKD":
						hkdAmount = order.getTotalAmount();
						break;
					case "USD":
						hkdAmount = exchangeRateFactory.exchange(
							order.getTotalAmount(),
							CurrencyType.USD.getCode(),
							CurrencyType.HKD.getCode()
						);
						break;
					case "CNY":
						hkdAmount = exchangeRateFactory.exchange(
							order.getTotalAmount(),
							CurrencyType.CNY.getCode(),
							CurrencyType.HKD.getCode()
						);
						break;
					default:
						continue;
				}
				totalHkdAmount = totalHkdAmount.add(hkdAmount);
			}
			summary.setTotalAmount(totalHkdAmount);
			summaryList.add(summary);
		}
		return summaryList;
	}
}
