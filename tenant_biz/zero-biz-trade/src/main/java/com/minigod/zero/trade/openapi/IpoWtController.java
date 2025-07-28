package com.minigod.zero.trade.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.mkt.cache.ApplyCanVO;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.biz.common.vo.mkt.ApplyWaitListingVO;
import com.minigod.zero.biz.common.vo.mkt.IpoStockDetailVO;
import com.minigod.zero.biz.common.vo.mkt.request.IpoVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IIpoStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/wt/trade/ipo_api")
@Api(value = "IPO相关接口", tags = "IPO相关接口")
public class IpoWtController {
	private final IIpoStockService ipoStockService;

	@PostMapping(value = "/can_apply")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "查询可认购列表", notes = "传入request")
	public R<List<ApplyCanVO>> canApply(@Valid @RequestBody CommonReqVO<IpoVO> request) {
		return ipoStockService.getCanApplyList(request.getParams());
	}

	@PostMapping(value = "/today_can_apply")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "查询今日可认购股票信息", notes = "传入request")
	public R todayCanApply(@Valid @RequestBody CommonReqVO<IpoVO> request) {
		return ipoStockService.getTodayCanApplyList(request);
	}

	@PostMapping(value = "/wait_listing")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "待上市列表", notes = "传入request")
	public R<List<ApplyWaitListingVO>> getWaitListing(@Valid @RequestBody CommonReqVO<IpoVO> request) {
		return ipoStockService.getWaitListing(request.getParams());
	}

	@PostMapping(value = "/stock_detail")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "获取新股详情", notes = "传入request")
	public R<IpoStockDetailVO> stockDetail(@Valid @RequestBody CommonReqVO<IpoVO> request) {
		return ipoStockService.getStockDetail(request.getParams());
	}

}
