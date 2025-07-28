package com.minigod.zero.customer.back.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.vo.CustomerTradeAccountVO;
import com.minigod.zero.trade.vo.sjmb.resp.JournalOrdersVO;
import com.minigod.zero.trade.vo.sjmb.resp.TotalAssetInfoVO;

import java.util.List;

/**
 * @author chen
 * @ClassName IStockService.java
 * @Description TODO
 * @createTime 2025年01月02日 17:47:00
 */
public interface IStockService {
	/**
	 * 查询股票账户相关信息
	 * @param custId
	 * @param businessType
	 * @return
	 */
	R<CustomerTradeAccountVO> customerAccountInfo(Long custId,String businessType);

	/**
	 * 查询股票资金相关信息
	 * @param custId
	 * @return
	 */
	R<TotalAssetInfoVO> customerPositionList(Long custId);

	R<List<JournalOrdersVO>> distributionRecords(Long custId, String startDate, String endDate);
}
