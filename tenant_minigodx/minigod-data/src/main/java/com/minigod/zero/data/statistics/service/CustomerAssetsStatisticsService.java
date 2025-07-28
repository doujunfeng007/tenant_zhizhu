package com.minigod.zero.data.statistics.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.vo.AccumulatedProfitTotalVO;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/28 19:56
 * @description：客户资产类统计
 */
public interface CustomerAssetsStatisticsService {
	/**
	 * 大客户前十
	 * @return
	 */
	R majorAccountTop10();


	/**
	 * 获取按币种统计的累计收益
	 * @return 累计收益统计列表
	 */
	AccumulatedProfitTotalVO getAccumulatedProfitByCurrency();
}
