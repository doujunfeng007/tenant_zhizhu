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
import com.minigod.zero.bpm.entity.CashRecordBusinessTypeEntity;
import com.minigod.zero.bpm.vo.CashRecordBusinessTypeVO;
import com.minigod.zero.bpm.service.ICashRecordBusinessTypeService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 业务查询归属 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/cashRecordBusinessType")
@Api(value = "业务查询归属", tags = "业务查询归属接口")
public class CashRecordBusinessTypeController extends ZeroController {

	private final ICashRecordBusinessTypeService cashRecordBusinessTypeService;

	/**
	 * 业务查询归属 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入cashRecordBusinessType")
	public R<CashRecordBusinessTypeEntity> detail(CashRecordBusinessTypeEntity cashRecordBusinessType) {
		CashRecordBusinessTypeEntity detail = cashRecordBusinessTypeService.getOne(Condition.getQueryWrapper(cashRecordBusinessType));
		return R.data(detail);
	}
	/**
	 * 业务查询归属 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入cashRecordBusinessType")
	public R<IPage<CashRecordBusinessTypeEntity>> list(CashRecordBusinessTypeEntity cashRecordBusinessType, Query query) {
		IPage<CashRecordBusinessTypeEntity> pages = cashRecordBusinessTypeService.page(Condition.getPage(query), Condition.getQueryWrapper(cashRecordBusinessType));
		return R.data(pages);
	}

	/**
	 * 业务查询归属 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cashRecordBusinessType")
	public R<IPage<CashRecordBusinessTypeVO>> page(CashRecordBusinessTypeVO cashRecordBusinessType, Query query) {
		IPage<CashRecordBusinessTypeVO> pages = cashRecordBusinessTypeService.selectCashRecordBusinessTypePage(Condition.getPage(query), cashRecordBusinessType);
		return R.data(pages);
	}

	/**
	 * 业务查询归属 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入cashRecordBusinessType")
	public R<Object> save(@Valid @RequestBody CashRecordBusinessTypeEntity cashRecordBusinessType) {
		return R.status(cashRecordBusinessTypeService.save(cashRecordBusinessType));
	}

	/**
	 * 业务查询归属 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入cashRecordBusinessType")
	public R<Object> update(@Valid @RequestBody CashRecordBusinessTypeEntity cashRecordBusinessType) {
		return R.status(cashRecordBusinessTypeService.updateById(cashRecordBusinessType));
	}

	/**
	 * 业务查询归属 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入cashRecordBusinessType")
	public R<Object> submit(@Valid @RequestBody CashRecordBusinessTypeEntity cashRecordBusinessType) {
		return R.status(cashRecordBusinessTypeService.saveOrUpdate(cashRecordBusinessType));
	}

	/**
	 * 业务查询归属 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cashRecordBusinessTypeService.removeByIds(Func.toLongList(ids)));
	}


}
