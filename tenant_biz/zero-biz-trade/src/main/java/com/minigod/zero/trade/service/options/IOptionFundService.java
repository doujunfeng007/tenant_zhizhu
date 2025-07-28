package com.minigod.zero.trade.service.options;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.fund.vo.request.FundInfoRequest;
import com.minigod.zero.trade.fund.vo.request.HistoryOrdersRequest;
import com.minigod.zero.trade.fund.vo.request.JournalOrdersRequest;
import com.minigod.zero.trade.fund.vo.request.TotalAccountRequest;
import com.minigod.zero.trade.vo.sjmb.resp.AssetInfoVO;

/**
 * @author chen
 * @ClassName IOptionFundService.java
 * @Description TODO
 * @createTime 2024年09月27日 14:08:00
 */
public interface IOptionFundService {
	/**
	 * 查询期权资产信息
	 * @param request
	 * @return
	 */
	AssetInfoVO getTotalAccount(TotalAccountRequest request);

	/**
	 * 查询资产汇总和持仓列表
	 * @param request
	 * @return
	 */
	R getFundInfo(FundInfoRequest request);

	/**
	 * 当日委托列表
	 * @param request
	 * @return
	 */
	R getJournalOrders(JournalOrdersRequest request);

	/**
	 * 历史委托
	 * @param request
	 * @return
	 */
	R getHistoryOrders(HistoryOrdersRequest request);
}
