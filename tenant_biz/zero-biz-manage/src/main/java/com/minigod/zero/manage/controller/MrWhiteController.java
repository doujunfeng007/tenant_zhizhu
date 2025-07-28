package com.minigod.zero.manage.controller;

import com.minigod.zero.manage.entity.MrWhiteEntity;
import com.minigod.zero.manage.vo.MrWhiteVO;
import com.minigod.zero.manage.wrapper.MrWhiteWrapper;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.utils.StringUtil;
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
import com.minigod.zero.manage.service.IMrWhiteService;
import com.minigod.zero.core.boot.ctrl.ZeroController;

/**
 * MR白名单 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX  +"/mrWhite")
@Api(value = "MR白名单", tags = "MR白名单接口")
public class MrWhiteController extends ZeroController {

	private final IMrWhiteService mrWhiteService;

	/**
	 * MR白名单 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入mrWhite")
	public R<MrWhiteVO> detail(MrWhiteEntity mrWhite) {
		MrWhiteEntity detail = mrWhiteService.getOne(Condition.getQueryWrapper(mrWhite));
		return R.data(MrWhiteWrapper.build().entityVO(detail));
	}

	/**
	 * MR白名单 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入mrWhite")
	public R<IPage<MrWhiteVO>> page(MrWhiteVO mrWhite, Query query) {
		if (mrWhite.getStatus()!=null&&mrWhite.getStatus()==2){
			mrWhite.setStatus(0);
		}
		IPage<MrWhiteVO> pages = mrWhiteService.selectMrWhitePage(Condition.getPage(query), mrWhite);
		return R.data(pages);
	}


	/**
	 * MR白名单 修改状态
	 */
	@PostMapping("/updateStatus")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入mrWhite")
	public R<Object> updateStatus(@Valid @RequestBody MrWhiteEntity mrWhite) {
		if (null == mrWhite.getId()) {
			return R.fail("参数错误");
		}
		MrWhiteEntity entity = mrWhiteService.getById(mrWhite.getId());
		if (entity.getStatus()!=null && 0 == entity.getStatus()) {
			entity.setStatus(1);
		} else {
			entity.setStatus(0);
		}
		return R.status(mrWhiteService.updateById(entity));
	}

	/**
	 * MR白名单 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入mrWhite")
	public R<Object> submit(@Valid @RequestBody MrWhiteVO mrWhite) {
		if (StringUtil.isBlank(mrWhite.getUserIds())) {
			return R.fail("参数不能为空");
		}
		return mrWhiteService.submit(mrWhite);
	}

	/**
	 * MR白名单 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(mrWhiteService.deleteLogic(Func.toLongList(ids)));
	}


}
