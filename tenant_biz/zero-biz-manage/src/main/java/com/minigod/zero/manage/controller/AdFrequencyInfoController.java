package com.minigod.zero.manage.controller;

import com.minigod.zero.manage.entity.AdFrequencyInfoEntity;
import com.minigod.zero.manage.service.IAdFrequencyInfoService;
import com.minigod.zero.manage.vo.AdFrequencyInfoVO;
import com.minigod.zero.manage.wrapper.AdFrequencyInfoWrapper;
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
 *  广告频率表 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/adFrequencyInfo")
@Api(value = " 广告频率表", tags = " 广告频率表接口")
public class AdFrequencyInfoController extends ZeroController {

	private final IAdFrequencyInfoService adFrequencyInfoService;

	/**
	 *  广告频率表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入adFrequencyInfo")
	public R<AdFrequencyInfoVO> detail(AdFrequencyInfoEntity adFrequencyInfo) {
		AdFrequencyInfoEntity detail = adFrequencyInfoService.getOne(Condition.getQueryWrapper(adFrequencyInfo));
		return R.data(AdFrequencyInfoWrapper.build().entityVO(detail));
	}
	/**
	 *  广告频率表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入adFrequencyInfo")
	public R<IPage<AdFrequencyInfoVO>> list(AdFrequencyInfoEntity adFrequencyInfo, Query query) {
		IPage<AdFrequencyInfoEntity> pages = adFrequencyInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(adFrequencyInfo));
		return R.data(AdFrequencyInfoWrapper.build().pageVO(pages));
	}

	/**
	 *  广告频率表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入adFrequencyInfo")
	public R<IPage<AdFrequencyInfoVO>> page(AdFrequencyInfoVO adFrequencyInfo, Query query) {
		IPage<AdFrequencyInfoVO> pages = adFrequencyInfoService.selectAdFrequencyInfoPage(Condition.getPage(query), adFrequencyInfo);
		return R.data(pages);
	}

	/**
	 *  广告频率表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入adFrequencyInfo")
	public R<Object> save(@Valid @RequestBody AdFrequencyInfoEntity adFrequencyInfo) {
		return R.status(adFrequencyInfoService.save(adFrequencyInfo));
	}

	/**
	 *  广告频率表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入adFrequencyInfo")
	public R<Object> update(@Valid @RequestBody AdFrequencyInfoEntity adFrequencyInfo) {
		return R.status(adFrequencyInfoService.updateById(adFrequencyInfo));
	}

	/**
	 *  广告频率表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入adFrequencyInfo")
	public R<Object> submit(@Valid @RequestBody AdFrequencyInfoEntity adFrequencyInfo) {
		return R.status(adFrequencyInfoService.saveOrUpdate(adFrequencyInfo));
	}

	/**
	 *  广告频率表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(adFrequencyInfoService.removeByIds(Func.toLongList(ids)));
	}


}
