package com.minigod.zero.trade.service;

import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.biz.common.vo.mkt.ApplyWaitListingVO;
import com.minigod.zero.biz.common.vo.mkt.IpoStockDetailVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoReqVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * ipo新股服务
 */
public interface IIpoStockService {

	/**
	 * 查询上市新股信息
	 */
	R findUpNewStocks(String request);

	/**
	 * 查询上架的可预约认购数量
	 */
	R canPredictApplyNum(IpoReqVO ipoReqVO);

	/**
	 * 可认购列表
	 */
	R getCanApplyList(IpoVO ipoVO);

	/**
	 * 今日可认购列表
	 */
	R getTodayCanApplyList(CommonReqVO<IpoVO> request);

	/**
	 * 待上市购列表
	 */
	R<List<ApplyWaitListingVO>> getWaitListing(IpoVO ipoVO);

	/**
	 * 个股详情
	 */
	R<IpoStockDetailVO> getStockDetail(IpoVO ipoVO);

	/**
	 * 获取0本金认购股票代码
	 */
	R getZeroPrincipalAssetId(String request);

	/**
	 * 查询上市首日涨跌幅最好的一只
	 */
	R ipoOptimization(String request);

	/**
	 * 查询可预约认购股票列表
	 *
	 * @return
	 */
	R canPredictApply(IpoReqVO ipoReqVO);

	/**
	 * 获取 去预约认购的数据
	 *
	 * @return
	 */
	R toPredictApply(IpoReqVO ipoReqVO);

	/**
	 * 提交预约认购
	 */
	R predictApplySub(IpoReqVO ipoReqVO);

	/**
	 * 撤销预约认购
	 */
	R cancelPredictApply(IpoReqVO ipoReqVO);

	/**
	 * 查询预约认购记录
	 */
	R predictApplyInfo(IpoReqVO ipoReqVO);

	/**
	 * 获取单个股票预约详情
	 */
	R predictApplyInfoById(IpoReqVO ipoReqVO);
}
