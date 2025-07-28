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
import com.minigod.zero.bpm.entity.CashEddaInfoEntity;
import com.minigod.zero.bpm.vo.CashEddaInfoVO;
import com.minigod.zero.bpm.service.ICashEddaInfoService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * edda申请流水表 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/cashEddaInfo")
@Api(value = "edda申请流水表", tags = "edda申请流水表接口")
public class CashEddaInfoController extends ZeroController {

	private final ICashEddaInfoService cashEddaInfoService;

	/**
	 * edda申请流水表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入cashEddaInfo")
	public R<CashEddaInfoEntity> detail(CashEddaInfoEntity cashEddaInfo) {
		CashEddaInfoEntity detail = cashEddaInfoService.getOne(Condition.getQueryWrapper(cashEddaInfo));
		return R.data(detail);
	}
	/**
	 * edda申请流水表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入cashEddaInfo")
	public R<IPage<CashEddaInfoEntity>> list(CashEddaInfoEntity cashEddaInfo, Query query) {
		IPage<CashEddaInfoEntity> pages = cashEddaInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(cashEddaInfo));
		return R.data(pages);
	}

	/**
	 * edda申请流水表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cashEddaInfo")
	public R<IPage<CashEddaInfoVO>> page(CashEddaInfoVO cashEddaInfo, Query query) {
		IPage<CashEddaInfoVO> pages = cashEddaInfoService.selectCashEddaInfoPage(Condition.getPage(query), cashEddaInfo);
		return R.data(pages);
	}

	/**
	 * edda申请流水表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入cashEddaInfo")
	public R<Object> save(@Valid @RequestBody CashEddaInfoEntity cashEddaInfo) {
		return R.status(cashEddaInfoService.save(cashEddaInfo));
	}

	/**
	 * edda申请流水表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入cashEddaInfo")
	public R<Object> update(@Valid @RequestBody CashEddaInfoEntity cashEddaInfo) {
		return R.status(cashEddaInfoService.updateById(cashEddaInfo));
	}

	/**
	 * edda申请流水表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入cashEddaInfo")
	public R<Object> submit(@Valid @RequestBody CashEddaInfoEntity cashEddaInfo) {
		return R.status(cashEddaInfoService.saveOrUpdate(cashEddaInfo));
	}

	/**
	 * edda申请流水表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cashEddaInfoService.removeByIds(Func.toLongList(ids)));
	}


}
