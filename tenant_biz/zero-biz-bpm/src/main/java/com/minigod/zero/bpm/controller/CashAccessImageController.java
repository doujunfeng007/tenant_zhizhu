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
import com.minigod.zero.bpm.entity.CashAccessImageEntity;
import com.minigod.zero.bpm.vo.CashAccessImageVO;
import com.minigod.zero.bpm.service.ICashAccessImageService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 存取资金图片表 控制器
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/cashAccessImage")
@Api(value = "存取资金图片表", tags = "存取资金图片表接口")
public class CashAccessImageController extends ZeroController {

	private final ICashAccessImageService cashAccessImageService;

	/**
	 * 存取资金图片表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入cashAccessImage")
	public R<CashAccessImageEntity> detail(CashAccessImageEntity cashAccessImage) {
		CashAccessImageEntity detail = cashAccessImageService.getOne(Condition.getQueryWrapper(cashAccessImage));
		return R.data(detail);
	}
	/**
	 * 存取资金图片表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入cashAccessImage")
	public R<IPage<CashAccessImageEntity>> list(CashAccessImageEntity cashAccessImage, Query query) {
		IPage<CashAccessImageEntity> pages = cashAccessImageService.page(Condition.getPage(query), Condition.getQueryWrapper(cashAccessImage));
		return R.data(pages);
	}

	/**
	 * 存取资金图片表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cashAccessImage")
	public R<IPage<CashAccessImageVO>> page(CashAccessImageVO cashAccessImage, Query query) {
		IPage<CashAccessImageVO> pages = cashAccessImageService.selectCashAccessImagePage(Condition.getPage(query), cashAccessImage);
		return R.data(pages);
	}

	/**
	 * 存取资金图片表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入cashAccessImage")
	public R<Object> save(@Valid @RequestBody CashAccessImageEntity cashAccessImage) {
		return R.status(cashAccessImageService.save(cashAccessImage));
	}

	/**
	 * 存取资金图片表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入cashAccessImage")
	public R<Object> update(@Valid @RequestBody CashAccessImageEntity cashAccessImage) {
		return R.status(cashAccessImageService.updateById(cashAccessImage));
	}

	/**
	 * 存取资金图片表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入cashAccessImage")
	public R<Object> submit(@Valid @RequestBody CashAccessImageEntity cashAccessImage) {
		return R.status(cashAccessImageService.saveOrUpdate(cashAccessImage));
	}

	/**
	 * 存取资金图片表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cashAccessImageService.removeByIds(Func.toLongList(ids)));
	}


}
