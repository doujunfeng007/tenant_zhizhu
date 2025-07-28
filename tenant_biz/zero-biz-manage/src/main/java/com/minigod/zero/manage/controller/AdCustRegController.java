package com.minigod.zero.manage.controller;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.manage.entity.AdCustRegEntity;
import com.minigod.zero.manage.wrapper.AdCustRegWrapper;
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
import com.minigod.zero.manage.vo.AdCustRegVO;
import com.minigod.zero.manage.service.IAdCustRegService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 广告客户记录表 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/adCustReg")
@Api(value = "广告客户记录表", tags = "广告客户记录表接口")
public class AdCustRegController extends ZeroController {

	private final IAdCustRegService adCustRegService;

	/**
	 * 广告客户记录表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入adCustReg")
	public R<AdCustRegVO> detail(AdCustRegEntity adCustReg) {
		AdCustRegEntity detail = adCustRegService.getOne(Condition.getQueryWrapper(adCustReg));
		return R.data(AdCustRegWrapper.build().entityVO(detail));
	}
	/**
	 * 广告客户记录表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入adCustReg")
	public R<IPage<AdCustRegVO>> list(AdCustRegEntity adCustReg, Query query) {
		IPage<AdCustRegEntity> pages = adCustRegService.page(Condition.getPage(query), Condition.getQueryWrapper(adCustReg));
		return R.data(AdCustRegWrapper.build().pageVO(pages));
	}

	/**
	 * 广告客户记录表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入adCustReg")
	public R<IPage<AdCustRegVO>> page(AdCustRegVO adCustReg, Query query) {
		IPage<AdCustRegVO> pages = adCustRegService.selectAdCustRegPage(Condition.getPage(query), adCustReg);
		return R.data(pages);
	}

	/**
	 * 广告客户记录表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入adCustReg")
	public R<Object> save(@Valid @RequestBody AdCustRegEntity adCustReg) {
		return R.status(adCustRegService.save(adCustReg));
	}

	/**
	 * 广告客户记录表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入adCustReg")
	public R<Object> update(@Valid @RequestBody AdCustRegEntity adCustReg) {
		return R.status(adCustRegService.updateById(adCustReg));
	}

	/**
	 * 广告客户记录表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入adCustReg")
	public R<Object> submit(@Valid @RequestBody AdCustRegEntity adCustReg) {
		return R.status(adCustRegService.saveOrUpdate(adCustReg));
	}

	/**
	 * 广告客户记录表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(adCustRegService.removeByIds(Func.toLongList(ids)));
	}


}
