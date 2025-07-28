package com.minigod.zero.manage.controller;

import com.minigod.zero.manage.entity.AdCustActInfoEntity;
import com.minigod.zero.manage.service.IAdCustActInfoService;
import com.minigod.zero.manage.vo.AdCustActInfoVO;
import com.minigod.zero.manage.wrapper.AdCustActInfoWrapper;
import com.minigod.zero.core.launch.constant.AppConstant;
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
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * 广告用户活动信息表 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/adCustActInfo")
@Api(value = "广告用户活动信息表", tags = "广告用户活动信息表接口")
public class AdCustActInfoController extends ZeroController {

	private final IAdCustActInfoService adCustActInfoService;

	/**
	 * 广告用户活动信息表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入adCustActInfo")
	public R<AdCustActInfoVO> detail(AdCustActInfoEntity adCustActInfo) {
		AdCustActInfoEntity detail = adCustActInfoService.getOne(Condition.getQueryWrapper(adCustActInfo));
		return R.data(AdCustActInfoWrapper.build().entityVO(detail));
	}
	/**
	 * 广告用户活动信息表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入adCustActInfo")
	public R<IPage<AdCustActInfoVO>> list(AdCustActInfoEntity adCustActInfo, Query query) {
		IPage<AdCustActInfoEntity> pages = adCustActInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(adCustActInfo));
		return R.data(AdCustActInfoWrapper.build().pageVO(pages));
	}

	/**
	 * 广告用户活动信息表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入adCustActInfo")
	public R<IPage<AdCustActInfoVO>> page(AdCustActInfoVO adCustActInfo, Query query) {
		IPage<AdCustActInfoVO> pages = adCustActInfoService.selectAdCustActInfoPage(Condition.getPage(query), adCustActInfo);
		return R.data(pages);
	}

	/**
	 * 广告用户活动信息表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入adCustActInfo")
	public R<Object> save(@Valid @RequestBody AdCustActInfoEntity adCustActInfo) {
		return R.status(adCustActInfoService.save(adCustActInfo));
	}

	/**
	 * 广告用户活动信息表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入adCustActInfo")
	public R<Object> update(@Valid @RequestBody AdCustActInfoEntity adCustActInfo) {
		return R.status(adCustActInfoService.updateById(adCustActInfo));
	}

	/**
	 * 广告用户活动信息表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入adCustActInfo")
	public R<Object> submit(@Valid @RequestBody AdCustActInfoEntity adCustActInfo) {
		return R.status(adCustActInfoService.saveOrUpdate(adCustActInfo));
	}

	/**
	 * 广告用户活动信息表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(adCustActInfoService.removeByIds(Func.toLongList(ids)));
	}


}
