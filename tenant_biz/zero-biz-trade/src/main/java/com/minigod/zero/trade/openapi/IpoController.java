package com.minigod.zero.trade.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoReqVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IIpoApplyService;
import com.minigod.zero.trade.service.IIpoCounterService;
import com.minigod.zero.trade.service.IIpoF10Service;
import com.minigod.zero.trade.service.IIpoStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/trade/ipo_api")
@Api(value = "IPO相关接口", tags = "IPO相关接口")
public class IpoController {
	private final IIpoStockService ipoStockService;
	private final IIpoF10Service ipoF10Service;
	private final IIpoApplyService ipoApplyService;
	private final IIpoCounterService iIpoCounterService;

	@PostMapping(value = "/find_upnew_stocks")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询上市新股信息", notes = "传入request")
	public R findUpNewStocks(@Valid @RequestBody String request) {
		return ipoStockService.findUpNewStocks(request);
	}

	@PostMapping(value = "/can_predict_apply_num")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询上架的可预约认购数量", notes = "传入request")
	public R canPredictApplyNum(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoStockService.canPredictApplyNum(ipoReqVO);
	}

	@PostMapping(value = "/apply_info")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询认购记录", notes = "传入request")
	public R getApplyRecord(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoApplyService.getApplyList(ipoReqVO);
	}

	@PostMapping(value = "/get_zero_principal_assetId")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取0本金认购股票代码", notes = "传入request")
	public R getZeroPrincipalAssetId(@Valid @RequestBody String request) {
		return ipoStockService.getZeroPrincipalAssetId(request);
	}


	@PostMapping(value = "/to_apply")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "去认购", notes = "传入request")
	public R toApply(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoApplyService.toApply(ipoReqVO);
	}

	@PostMapping(value = "/apply_sub")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "提交认购", notes = "传入request")
	public R applyCommit(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoApplyService.applyCommit(ipoReqVO);
	}

	@PostMapping(value = "/apply_cancel")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "撤销认购", notes = "传入request")
	public R applyCancel(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoApplyService.applyCancel(ipoReqVO);
	}

	@PostMapping(value = "/valid_account_apply")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "是否已认购某股票", notes = "传入request")
	public R checkApplyByAssetId(@Valid @RequestBody String request) {
		return ipoApplyService.checkApplyByAssetId(request);
	}

	@PostMapping(value = "/stock_holder")
	@ApiOperationSupport(order = 19)
	@ApiOperation(value = "股东信息", notes = "传入request")
	public R stockHolderInfo(@Valid @RequestBody String request) {
		return ipoF10Service.stockHolderInfo(request);
	}

	@PostMapping(value = "/find_placing_result")
	@ApiOperationSupport(order = 22)
	@ApiOperation(value = "配售结果", notes = "传入request")
	public R placingResult(@Valid @RequestBody CommonReqVO<IpoVO> request) {
		return ipoF10Service.placingResult(request.getParams());
	}

	@PostMapping(value = "/can_predict_apply")
	@ApiOperationSupport(order = 26)
	@ApiOperation(value = "查询可预约认购股票列表", notes = "查询可预约认购股票列表")
	public R canPredictApply(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoStockService.canPredictApply(ipoReqVO);
	}

	@PostMapping(value = "/to_predict_apply")
	@ApiOperationSupport(order = 27)
	@ApiOperation(value = "获取 去预约认购的数据", notes = "获取 去预约认购的数据")
	public R toPredictApply(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoStockService.toPredictApply(ipoReqVO);
	}

	@PostMapping(value = "/predict_apply_sub")
	@ApiOperationSupport(order = 28)
	@ApiOperation(value = "提交预约认购", notes = "提交预约认购")
	public R predictApplySub(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoStockService.predictApplySub(ipoReqVO);
	}

	@PostMapping(value = "/cancel_predict_apply")
	@ApiOperationSupport(order = 29)
	@ApiOperation(value = "撤销预约认购", notes = "撤销预约认购")
	public R cancelPredictApply(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoStockService.cancelPredictApply(ipoReqVO);
	}

	@PostMapping(value = "/predict_apply_info")
	@ApiOperationSupport(order = 30)
	@ApiOperation(value = "查询预约认购记录", notes = "查询预约认购记录")
	public R predictApplyInfo(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoStockService.predictApplyInfo(ipoReqVO);
	}

	@PostMapping(value = "/predict_apply_info_by_id")
	@ApiOperationSupport(order = 31)
	@ApiOperation(value = "获取单个股票预约详情", notes = "获取单个股票预约详情")
	public R predictApplyInfoById(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoStockService.predictApplyInfoById(ipoReqVO);
	}

	@PostMapping(value = "/apply_detail_info")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询认购详情", notes = "传入request")
	public R getApplyDetailInfo(@Valid @RequestBody IpoReqVO ipoReqVO) {
		return ipoApplyService.getApplyDetailInfo(ipoReqVO);
	}

	@GetMapping(value = "/query_ipo_detail")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询ipo详情", notes = "传入request")
	public R getQueryIpoDetails(@RequestParam("assetId") String assetId,@RequestParam("tradeAccount") String tradeAccount) {
		return iIpoCounterService.queryIpoDetails(assetId,tradeAccount);
	}

	@PostMapping(value = "/query_ipo_stock_list")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "查询ipo列表", notes = "传入request")
	public R queryIpoStockList(@Valid @RequestBody IpoVO ipoVO) {
		return iIpoCounterService.queryIpoStockList(ipoVO);
	}
	@PostMapping(value = "/app_ly_Ipo")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "申请购买ipo", notes = "传入request")
	public R applyIpo(@Valid @RequestBody IpoVO ipoVO) {
		return iIpoCounterService.applyIpo(ipoVO);
	}
	@PostMapping(value = "/app_cancel_Ipo")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "撤销ipo", notes = "传入request")
	public R applyCancel(@Valid @RequestBody IpoVO ipoVO) {
		return iIpoCounterService.applyCancel(ipoVO);
	}
	@PostMapping(value = "/query_ipo_apply_list")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "认购记录", notes = "传入request")
	public R queryIpoApplyList(@Valid @RequestBody IpoVO ipoVO) {
		return iIpoCounterService.queryIpoApplyList(ipoVO);
	}
}
