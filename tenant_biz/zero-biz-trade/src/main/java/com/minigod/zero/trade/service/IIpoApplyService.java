package com.minigod.zero.trade.service;

import com.minigod.zero.biz.common.vo.mkt.request.ApplyInfoVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoReqVO;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * ipo认购服务
 */
public interface IIpoApplyService {
	/**
	 * 去认购
	 */
	R toApply(IpoReqVO ipoReqVO);

	/**
	 * 提交认购
	 */
	R applyCommit(IpoReqVO ipoReqVO);

	/**
	 * 撤销认购
	 */
	R applyCancel(IpoReqVO ipoReqVO);

	/**
	 * 认购记录
	 */
	R getApplyList(IpoReqVO ipoReqVO);

	/**
	 * 是否已认购某股票
	 */
	R checkApplyByAssetId(String request);

	/**
	 * 查询认购记录
	 */
	List<ApplyInfoVO> findApplyRecord(String fundAccount, String tradeAccount);

	/**
	 * 认购详情
	 * @param ipoReqVO
	 * @return
	 */
	R getApplyDetailInfo(IpoReqVO ipoReqVO);
}
