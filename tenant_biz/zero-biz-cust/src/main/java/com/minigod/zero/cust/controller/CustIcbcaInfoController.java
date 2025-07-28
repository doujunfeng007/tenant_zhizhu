package com.minigod.zero.cust.controller;

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
import com.minigod.zero.cust.entity.CustIcbcaInfoEntity;
import com.minigod.zero.cust.vo.CustIcbcaInfoVO;
import com.minigod.zero.cust.service.ICustIcbcaInfoService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 工银客户信息表 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/icbcaInfo")
@Api(value = "工银客户信息表", tags = "工银客户信息表接口")
public class CustIcbcaInfoController extends ZeroController {

	private final ICustIcbcaInfoService icbcaInfoService;

	/**
	 * 工银客户信息表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入icbcaInfo")
	public R<IPage<CustIcbcaInfoVO>> page(CustIcbcaInfoVO icbcaInfo, Query query) {
		IPage<CustIcbcaInfoVO> pages = icbcaInfoService.selectCustIcbcaInfoPage(Condition.getPage(query), icbcaInfo);
		return R.data(pages);
	}

	/**
	 * 工银客户信息表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入icbcaInfo")
	public R<Object> save(@Valid @RequestBody CustIcbcaInfoEntity icbcaInfo) {
		return R.status(icbcaInfoService.save(icbcaInfo));
	}

	/**
	 * 工银客户信息表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入icbcaInfo")
	public R<Object> update(@Valid @RequestBody CustIcbcaInfoEntity icbcaInfo) {
		return R.status(icbcaInfoService.updateById(icbcaInfo));
	}

	/**
	 * 工银客户信息表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入icbcaInfo")
	public R<Object> submit(@Valid @RequestBody CustIcbcaInfoEntity icbcaInfo) {
		return R.status(icbcaInfoService.saveOrUpdate(icbcaInfo));
	}

	/**
	 * 工银客户信息表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(icbcaInfoService.deleteLogic(Func.toLongList(ids)));
	}


}
