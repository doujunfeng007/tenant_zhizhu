package com.minigod.zero.data.mapper;

import com.minigod.zero.data.vo.OrderStatisticsVO;
import com.minigod.zero.data.vo.OrderTotalCountVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 客户订单表Mapper层
 *
 * @author: eric
 * @date: 2024-10-29 15:17:08
 */
@Mapper
public interface OrderMapper {

	/**
	 * 客户买入资产的总金额，
	 * 按币种分组，其中订单类型为 1（买入）或 11（IPO），
	 * 且状态为 300-已结算, 310-部分成交, 510-部分撤销 或 730-部分作废。
	 *
	 * @return
	 */
	List<Map<String, Object>> selectTotalAmountByCurrency();

	/**
	 * 按产品类型统计订单情况
	 * 统计各产品类型的买入、卖出、撤单、成交数量
	 *
	 * @return List<Map < String, Object>> 包含以下字段:
	 * - busitype: 产品类型 (0:基金 1:活利得 2:债券易)
	 * - buy_count: 买入数量 (type=1或3 1:买;3:交换买)
	 * - sell_count: 卖出数量 (type=2或4 2:卖;4:交换卖)
	 * - cancel_count: 撤单数量 (status=500或510 500-已撤销 510-部分撤销)
	 * - deal_count: 成交数量 (status=300 300-已结算)
	 */
	List<OrderTotalCountVO> selectOrderStatsByBusiType();

	/**
	 * 统计IPO订单情况
	 *
	 * @return
	 */
	List<OrderStatisticsVO> selectOrderStatistics();
}
