package com.minigod.zero.data.statistics.service;

import com.minigod.zero.data.vo.OrderIPOSummaryVO;
import com.minigod.zero.data.vo.OrderTotalAmountVO;
import com.minigod.zero.data.vo.OrderTotalCountVO;

import java.util.List;

/**
 * 订单统计服务接口
 *
 * @author eric
 * @since 2024-10-29 15:18:08
 */
public interface OrderStatisticsService {
	/**
	 * 查询用于选择客户买入资产的总金额，
	 * 按币种分组，其中订单类型为 1（买入）或 11（IPO），
	 * 且状态为 300, 310, 510 或 730。
	 *
	 * @return
	 */
	OrderTotalAmountVO getTotalAmountByCurrency();

	/**
	 * 按产品类型统计订单情况
	 * 统计各产品类型的买入、卖出、撤单、成交数量
	 *
	 * @return List<OrderTotalCountVO> 订单统计结果列表
	 */
	List<OrderTotalCountVO> getTotalCountByBusiType();

	/**
	 * 统计IPO订单情况
	 *
	 * @return
	 */
	List<OrderIPOSummaryVO> selectOrderStatistics();
}
