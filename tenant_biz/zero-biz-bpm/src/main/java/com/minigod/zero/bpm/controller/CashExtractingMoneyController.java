package com.minigod.zero.bpm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.entity.CashExtractingMoneyEntity;
import com.minigod.zero.bpm.vo.CashExtractingMoneyVO;
import com.minigod.zero.bpm.service.ICashExtractingMoneyService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 提取资金表 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/cashExtractingMoney")
@Api(value = "提取资金表", tags = "提取资金表接口")
public class CashExtractingMoneyController extends ZeroController {

	private final ICashExtractingMoneyService cashExtractingMoneyService;

	/**
	 * 提取资金表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入cashExtractingMoney")
	public R<CashExtractingMoneyEntity> detail(CashExtractingMoneyEntity cashExtractingMoney) {
		CashExtractingMoneyEntity detail = cashExtractingMoneyService.getOne(Condition.getQueryWrapper(cashExtractingMoney));
		return R.data(detail);
	}
	/**
	 * 提取资金表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入cashExtractingMoney")
	public R<IPage<CashExtractingMoneyEntity>> list(CashExtractingMoneyEntity cashExtractingMoney, Query query) {
		IPage<CashExtractingMoneyEntity> pages = cashExtractingMoneyService.page(Condition.getPage(query), Condition.getQueryWrapper(cashExtractingMoney));
		return R.data(pages);
	}

	/**
	 * 提取资金表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cashExtractingMoney")
	public R<IPage<CashExtractingMoneyVO>> page(CashExtractingMoneyVO cashExtractingMoney, Query query) {
		IPage<CashExtractingMoneyVO> pages = cashExtractingMoneyService.selectCashExtractingMoneyPage(Condition.getPage(query), cashExtractingMoney);
		return R.data(pages);
	}

	/**
	 * 提取资金表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入cashExtractingMoney")
	public R<Object> save(@Valid @RequestBody CashExtractingMoneyEntity cashExtractingMoney) {
		return R.status(cashExtractingMoneyService.save(cashExtractingMoney));
	}

	/**
	 * 提取资金表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入cashExtractingMoney")
	public R<Object> update(@Valid @RequestBody CashExtractingMoneyEntity cashExtractingMoney) {
		return R.status(cashExtractingMoneyService.updateById(cashExtractingMoney));
	}

	/**
	 * 提取资金表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入cashExtractingMoney")
	public R<Object> submit(@Valid @RequestBody CashExtractingMoneyEntity cashExtractingMoney) {
		return R.status(cashExtractingMoneyService.saveOrUpdate(cashExtractingMoney));
	}

	/**
	 * 提取资金表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cashExtractingMoneyService.removeByIds(Func.toLongList(ids)));
	}


}
