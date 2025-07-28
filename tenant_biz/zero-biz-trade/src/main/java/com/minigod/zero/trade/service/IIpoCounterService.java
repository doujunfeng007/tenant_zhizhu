package com.minigod.zero.trade.service;

import com.minigod.zero.biz.common.vo.ipo.IPOClientApply;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.resp.IPOAppliesResponse;
import com.minigod.zero.trade.vo.resp.IPODetailsResponse;
import com.minigod.zero.trade.vo.resp.IPOListResponse;

import java.util.List;

/**
 * @author chen
 * @ClassName IIpoCounterService.java
 * @Description TODO
 * @createTime 2024年04月16日 16:11:00
 */
public interface IIpoCounterService {

	/**
	 * 查询ipo详情
	 * @param assetId
	 * @return
	 */
	R<IPODetailsResponse> queryIpoDetails(String assetId,String tradeAccount);

	/**
	 * 查询ipo股票列表
	 * @param ipoVO
	 * @return
	 */
	R<IPOListResponse> queryIpoStockList(IpoVO ipoVO);

	/**
	 * 申请ipo
	 * @param ipoVO
	 * @return
	 */
	R applyIpo(IpoVO ipoVO);

	/**
	 * 撤销ipo
	 */
	R applyCancel(IpoVO ipoVO);

	/**
	 * 认购记录
	 * @param ipoVO
	 * @return
	 */
	R<IPOAppliesResponse> queryIpoApplyList(IpoVO ipoVO);

	/**
	 * 中签记录
	 * @return
	 */
	R<List<IPOClientApply>> queryIpoWinningList();












}
