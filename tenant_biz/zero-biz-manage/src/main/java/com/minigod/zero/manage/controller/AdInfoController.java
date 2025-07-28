package com.minigod.zero.manage.controller;

import com.minigod.zero.manage.entity.AdInfoEntity;
import com.minigod.zero.manage.service.IAdInfoService;
import com.minigod.zero.manage.vo.AdInfoVO;
import com.minigod.zero.manage.wrapper.AdInfoWrapper;
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

import static com.minigod.zero.core.secure.utils.AuthUtil.getTenantId;


/**
 * 广告信息表 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/adInfo")
@Api(value = "广告信息表", tags = "广告信息表接口")
public class AdInfoController extends ZeroController {

	private final IAdInfoService adInfoService;

	/**
	 * 广告信息表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入adInfo")
	public R<AdInfoVO> detail(AdInfoEntity adInfo) {
		adInfo.setTenantId(getTenantId());
		AdInfoEntity detail = adInfoService.getOne(Condition.getQueryWrapper(adInfo));
		return R.data(AdInfoWrapper.build().entityVO(detail));
	}

	/**
	 * 广告信息表 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入adInfo")
	public R<IPage<AdInfoVO>> page(AdInfoVO adInfo, Query query) {
		adInfo.setTenantId(getTenantId());
		IPage<AdInfoVO> pages = adInfoService.selectAdInfoPage(Condition.getPage(query), adInfo);
		return R.data(pages);
	}


	/**
	 * 广告信息表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入adInfo")
	public R<Object> submit(@Valid @RequestBody AdInfoVO adInfo) {
		adInfo.setTenantId(getTenantId());
		return adInfoService.submit(adInfo);
	}

	/**
	 * 广告信息表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(adInfoService.removeByIds(Func.toLongList(ids)));
	}
}
