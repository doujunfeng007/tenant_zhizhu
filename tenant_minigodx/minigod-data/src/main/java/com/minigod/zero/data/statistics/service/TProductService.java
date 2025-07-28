package com.minigod.zero.data.statistics.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.data.vo.HldRateChangeVO;

import java.util.List;
import java.util.Map;

/**
 * 产品服务接口
 *
 * @author eric
 * @date 2024-10-31 15:25:08
 */
public interface TProductService {
	/**
	 * 统计不同产品类型的上下架数量
	 *
	 * @return
	 */
	R<Map<String, List<Map<String, Object>>>> countProductByTypeAndStatus();

	/**
	 * 正在IPO阶段的产品数量
	 */
	R<Long> ipoCountProduct();

	/**
	  * 统计活利得利率变更
	  *
	  * @param
	  * @return
	  */
	HldRateChangeVO hldRateChange();

	/**
	  * 产品到期提醒
	  *
	  * @param
	  * @return
	  */
	Integer countProductExpired();

	/**
	 * 产品到期提醒isin编码集合
	 *
	 * @param
	 * @return
	 */
	List<String> productExpiredIsin();

	/**
	  * 债券易派息提醒数量
	  *
	  * @param
	  * @return
	  */
	Integer countBondDividend();

	/**
	  * 债券易派息提醒产品编码集合
	  *
	  * @param
	  * @return
	  */
	List<String> bondDividendIsin();
}
