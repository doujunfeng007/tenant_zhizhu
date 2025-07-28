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
import com.minigod.zero.bpm.entity.CashDepositFundsEntity;
import com.minigod.zero.bpm.vo.CashDepositFundsVO;
import com.minigod.zero.bpm.service.ICashDepositFundsService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 存入资金表 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/cashDepositFunds")
@Api(value = "存入资金表", tags = "存入资金表接口")
public class CashDepositFundsController extends ZeroController {

	private final ICashDepositFundsService cashDepositFundsService;

	/**
	 * 存入资金表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入cashDepositFunds")
	public R<CashDepositFundsEntity> detail(CashDepositFundsEntity cashDepositFunds) {
		CashDepositFundsEntity detail = cashDepositFundsService.getOne(Condition.getQueryWrapper(cashDepositFunds));
		return R.data(detail);
	}
	/**
	 * 存入资金表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入cashDepositFunds")
	public R<IPage<CashDepositFundsEntity>> list(CashDepositFundsEntity cashDepositFunds, Query query) {
		IPage<CashDepositFundsEntity> pages = cashDepositFundsService.page(Condition.getPage(query), Condition.getQueryWrapper(cashDepositFunds));
		return R.data(pages);
	}

	/**
	 * 存入资金表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cashDepositFunds")
	public R<IPage<CashDepositFundsVO>> page(CashDepositFundsVO cashDepositFunds, Query query) {
		IPage<CashDepositFundsVO> pages = cashDepositFundsService.selectCashDepositFundsPage(Condition.getPage(query), cashDepositFunds);
		return R.data(pages);
	}

	/**
	 * 存入资金表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入cashDepositFunds")
	public R<Object> save(@Valid @RequestBody CashDepositFundsEntity cashDepositFunds) {
		return R.status(cashDepositFundsService.save(cashDepositFunds));
	}

	/**
	 * 存入资金表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入cashDepositFunds")
	public R<Object> update(@Valid @RequestBody CashDepositFundsEntity cashDepositFunds) {
		return R.status(cashDepositFundsService.updateById(cashDepositFunds));
	}

	/**
	 * 存入资金表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入cashDepositFunds")
	public R<Object> submit(@Valid @RequestBody CashDepositFundsEntity cashDepositFunds) {
		return R.status(cashDepositFundsService.saveOrUpdate(cashDepositFunds));
	}

	/**
	 * 存入资金表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cashDepositFundsService.removeByIds(Func.toLongList(ids)));
	}


}
