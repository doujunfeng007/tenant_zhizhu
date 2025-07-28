package com.minigod.zero.cust.controller;

import com.minigod.zero.cust.entity.CustCityLoginEntity;
import com.minigod.zero.cust.vo.CustCityLoginVO;
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
import com.minigod.zero.cust.service.ICustCityLoginService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 登录地记录表 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/cityLogin")
@Api(value = "登录地记录表", tags = "登录地记录表接口")
public class CustCityLoginController extends ZeroController {

	private final ICustCityLoginService cityLoginService;

	/**
	 * 登录地记录表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入cityLogin")
	public R<IPage<CustCityLoginVO>> page(CustCityLoginVO cityLogin, Query query) {
		IPage<CustCityLoginVO> pages = cityLoginService.selectCustCityLoginPage(Condition.getPage(query), cityLogin);
		return R.data(pages);
	}

	/**
	 * 登录地记录表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入cityLogin")
	public R<Object> save(@Valid @RequestBody CustCityLoginEntity cityLogin) {
		return R.status(cityLoginService.save(cityLogin));
	}

	/**
	 * 登录地记录表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入cityLogin")
	public R<Object> update(@Valid @RequestBody CustCityLoginEntity cityLogin) {
		return R.status(cityLoginService.updateById(cityLogin));
	}

	/**
	 * 登录地记录表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入cityLogin")
	public R<Object> submit(@Valid @RequestBody CustCityLoginEntity cityLogin) {
		return R.status(cityLoginService.saveOrUpdate(cityLogin));
	}

	/**
	 * 登录地记录表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(cityLoginService.deleteLogic(Func.toLongList(ids)));
	}


}
