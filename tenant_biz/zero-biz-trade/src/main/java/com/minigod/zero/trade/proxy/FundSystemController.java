package com.minigod.zero.trade.proxy;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IFundSystemService;
import com.minigod.zero.trade.vo.req.FundInfoVO;
import com.minigod.zero.trade.vo.req.FundRecordVO;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author:yanghu.luo
 * @create: 2023-05-22 14:39
 * @Description: 提供给基金调用接口
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/proxy/fundSystem")
public class FundSystemController {
	private final IFundSystemService fundSystemService;

	@GetMapping (value = "/getSystemStatus")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取柜台系统状态", notes = "")
	public R getSystemStatus() {
		return fundSystemService.getSystemStatus();
	}

	@PostMapping(value = "/getFundRecord")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取资金流水（当日+历史）", notes = "传入request")
	public R getFundRecord(@Valid @RequestBody FundRecordVO request) {
		return fundSystemService.getFundRecord(request);
	}

	@PostMapping(value = "/getFundDetail")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取单市场资金详情", notes = "传入request")
	public R getFundDetail(@Valid @RequestBody FundInfoVO request) {
		return fundSystemService.getFundDetail(request);
	}

	@GetMapping(value = "/getMoneyRate")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "获取汇率", notes = "传入request")
	public R getMoneyRate() {
		return fundSystemService.getMoneyRate();
	}
}
