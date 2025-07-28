package com.minigod.zero.trade.service;

import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.tool.api.R;

/**
 * ipo新股F10服务
 */
public interface IIpoF10Service {
	/**
	 * 新股详情
	 */
	R getStockDetail(String request);

	/**
	 * 股东信息
	 */
	R stockHolderInfo(String request);

	/**
	 * 配售结果
	 */
	R placingResult(IpoVO ipoVO);
}
