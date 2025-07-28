package com.minigod.zero.cust.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.cust.entity.CustSerialNumberEntity;
import com.minigod.zero.cust.service.ICustSerialNumberService;
import com.minigod.zero.cust.vo.CustSerialNumberVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 序列号管理 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/serialNumberManage")
@Api(value = "序列号管理", tags = "序列号管理接口")
public class CustSerialNumberController extends ZeroController {

	private final ICustSerialNumberService custSerialNumberService;

	/**
	 * 序列号管理 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入serialNumberManage")
	public R<IPage<CustSerialNumberVO>> page(CustSerialNumberVO serialNumberManage, Query query) {
		IPage<CustSerialNumberVO> pages = custSerialNumberService.selectCustSerialNumberManagePage(Condition.getPage(query), serialNumberManage);
		return R.data(pages);
	}

	/**
	 * 序列号管理 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入serialNumberManage")
	public R<Object> save(@Valid @RequestBody CustSerialNumberEntity serialNumberManage) {
		return R.status(custSerialNumberService.save(serialNumberManage));
	}

	/**
	 * 序列号管理 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入serialNumberManage")
	public R<Object> update(@Valid @RequestBody CustSerialNumberEntity serialNumberManage) {
		return R.status(custSerialNumberService.updateById(serialNumberManage));
	}

	/**
	 * 序列号管理 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入serialNumberManage")
	public R<Object> submit(@Valid @RequestBody CustSerialNumberEntity serialNumberManage) {
		return R.status(custSerialNumberService.saveOrUpdate(serialNumberManage));
	}

	/**
	 * 序列号管理 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(custSerialNumberService.deleteLogic(Func.toLongList(ids)));
	}


}
