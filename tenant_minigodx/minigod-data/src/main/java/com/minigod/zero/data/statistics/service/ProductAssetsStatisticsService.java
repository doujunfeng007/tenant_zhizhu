package com.minigod.zero.data.statistics.service;

import com.minigod.zero.core.tool.api.R;

import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/10/29 10:36
 * @description：产品资产统计
 */
public interface ProductAssetsStatisticsService {
	/**
	 * 产品成交top 5
	 *
	 * @return
	 */
	R selectProductAssetTop5();

	R countProduct();

}
