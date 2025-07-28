package com.minigod.zero.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.system.entity.TradeKey;
import com.minigod.zero.system.service.ITradeKeyService;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;



/**
 *  控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/blade-test/trade_key")
@Api(value = "", tags = "")
public class TradeKeyController extends ZeroController {

	private final ITradeKeyService trade_keyService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入trade_key")
	public R<TradeKey> detail(TradeKey trade_key) {
		TradeKey detail = trade_keyService.getOne(Condition.getQueryWrapper(trade_key));
		return R.data(detail);
	}

	/**
	 * 分页 代码自定义代号
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入trade_key")
	public R<IPage<TradeKey>> list(TradeKey trade_key, Query query) {
		IPage<TradeKey> pages = trade_keyService.page(Condition.getPage(query), Condition.getQueryWrapper(trade_key));
		return R.data(pages);
	}

	/**
	 * 新增 代码自定义代号
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入trade_key")
	public R save(@Valid @RequestBody TradeKey trade_key) {
		return R.status(trade_keyService.save(trade_key));
	}

	/**
	 * 修改 代码自定义代号
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入trade_key")
	public R update(@Valid @RequestBody TradeKey trade_key) {
		return R.status(trade_keyService.updateById(trade_key));
	}

	/**
	 * 新增或修改 代码自定义代号
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入trade_key")
	public R submit(@Valid @RequestBody TradeKey trade_key) {
		return R.status(trade_keyService.saveOrUpdate(trade_key));
	}


	/**
	 * 删除 代码自定义代号
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(trade_keyService.deleteLogic(Func.toLongList(ids)));
	}

}
