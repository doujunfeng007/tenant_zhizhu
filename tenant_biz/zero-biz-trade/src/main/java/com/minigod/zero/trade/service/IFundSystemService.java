package com.minigod.zero.trade.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.req.FundInfoVO;
import com.minigod.zero.trade.vo.req.FundRecordVO;

/**
 * @author:yanghu.luo
 * @create: 2023-05-22 14:43
 * @Description: 提供给基金调用接口
 */
public interface IFundSystemService {
	/**
	 * 获取柜台系统状态
	 */
	R getSystemStatus();

	/**
	 * 获取资金流水（当日+历史）
	 */
	R getFundRecord(FundRecordVO request);

	/**
	 * 获取单市场资金详情
	 */
	R getFundDetail(FundInfoVO request);

	/**
	 * 获取汇率
	 */
	R getMoneyRate();
}
