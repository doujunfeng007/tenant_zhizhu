package com.minigod.zero.bpm.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.StockTransferDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.resp.StockTransferRespDto;
import com.minigod.zero.bpm.service.api.ITransferredStockService;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.CashTransferredStockDto;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 转股相关 控制器
 *
 * @author zejie.weng
 * @since 2023-05-26
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/stock_api")
@Api(value = "转股相关接口", tags = "转股相关接口")
@Slf4j
public class BpmStockController {

	private final ITransferredStockService transferredStockService;

	/**
	 * 保存股票转仓申请
	 */
	@PostMapping("/save_transferred_stock")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "保存股票转仓申请", notes = "传入cashTransferredStockDTO")
	public R saveTransferredStock(@RequestBody CashTransferredStockDto dto) {
		return transferredStockService.saveTransferredStock(dto);
	}

	/**
	 * 查询股票转仓记录
	 */
	@GetMapping("/find_transferred_stock_record")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询股票转仓记录", notes = "传入cashTransferredStockDTO")
	public R<List<StockTransferRespDto>> findTransFerredStockRecord(StockTransferDto dto) {
		return transferredStockService.findTransFerredStockRecord(dto);
	}

}
