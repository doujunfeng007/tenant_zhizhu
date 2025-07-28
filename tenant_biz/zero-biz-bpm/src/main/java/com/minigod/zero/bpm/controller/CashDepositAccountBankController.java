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
import com.minigod.zero.bpm.entity.CashDepositAccountBankEntity;
import com.minigod.zero.bpm.vo.CashDepositAccountBankVO;
import com.minigod.zero.bpm.service.ICashDepositAccountBankService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 客户银行卡记录 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/cashDepositAccountBank")
@Api(value = "客户银行卡记录", tags = "客户银行卡记录接口")
public class CashDepositAccountBankController extends ZeroController {

	private final ICashDepositAccountBankService cashDepositAccountBankService;

	/**
	 * 客户银行卡记录 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入cashDepositAccountBank")
	public R<CashDepositAccountBankEntity> detail(CashDepositAccountBankEntity cashDepositAccountBank) {
		CashDepositAccountBankEntity detail = cashDepositAccountBankService.getOne(Condition.getQueryWrapper(cashDepositAccountBank));
		return R.data(detail);
	}
	/**
	 * 客户银行卡记录 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入cashDepositAccountBank")
	public R<IPage<CashDepositAccountBankEntity>> list(CashDepositAccountBankEntity cashDepositAccountBank, Query query) {
		IPage<CashDepositAccountBankEntity> pages = cashDepositAccountBankService.page(Condition.getPage(query), Condition.getQueryWrapper(cashDepositAccountBank));
		return R.data(pages);
	}

	/**
	 * 客户银行卡记录 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cashDepositAccountBank")
	public R<IPage<CashDepositAccountBankVO>> page(CashDepositAccountBankVO cashDepositAccountBank, Query query) {
		IPage<CashDepositAccountBankVO> pages = cashDepositAccountBankService.selectCashDepositAccountBankPage(Condition.getPage(query), cashDepositAccountBank);
		return R.data(pages);
	}

	/**
	 * 客户银行卡记录 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入cashDepositAccountBank")
	public R<Object> save(@Valid @RequestBody CashDepositAccountBankEntity cashDepositAccountBank) {
		return R.status(cashDepositAccountBankService.save(cashDepositAccountBank));
	}

	/**
	 * 客户银行卡记录 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入cashDepositAccountBank")
	public R<Object> update(@Valid @RequestBody CashDepositAccountBankEntity cashDepositAccountBank) {
		return R.status(cashDepositAccountBankService.updateById(cashDepositAccountBank));
	}

	/**
	 * 客户银行卡记录 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入cashDepositAccountBank")
	public R<Object> submit(@Valid @RequestBody CashDepositAccountBankEntity cashDepositAccountBank) {
		return R.status(cashDepositAccountBankService.saveOrUpdate(cashDepositAccountBank));
	}

	/**
	 * 客户银行卡记录 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cashDepositAccountBankService.removeByIds(Func.toLongList(ids)));
	}


}
