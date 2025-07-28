package com.minigod.zero.trade.service;

import com.minigod.zero.biz.common.cache.PredictApplyCanVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.entity.IpoFinanceQueueOrderEntity;
import com.minigod.zero.trade.vo.resp.IPODetailsResponse;

import java.util.List;

public interface IIpoService {
	// 新股提交认购申请
	R<Object> applySub(IpoVO ipoVO);

	R<IPODetailsResponse> getIPODetailsResponse(String assetId,String tradeAccount);
	// 撤销
	R<Object> applyCancel(IpoVO ipoVO);
	// 查询去认购页面信息
	R<Object> toApply(IpoVO params);
	// 获取上架的预约认购列表
	List<PredictApplyCanVO> canPredictApplyNum();

	public R<Object> applyCommit(boolean isLoan, IpoVO ipoVO, String paramJson, Object record);

	R<Object> createIpoQueueOrder(IpoFinanceQueueOrderEntity order);
}
