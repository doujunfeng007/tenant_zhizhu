package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.manage.entity.HotRecommendEntity;
import com.minigod.zero.manage.service.IHotRecommendService;
import com.minigod.zero.manage.vo.HotRecommendVO;
import com.minigod.zero.manage.wrapper.HotRecommendWrapper;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 热门推荐股票 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/hotRecommend")
@Api(value = "热门推荐股票", tags = "热门推荐股票接口")
public class HotRecommendController extends ZeroController {

	private final IHotRecommendService hotRecommendService;

	/**
	 * 热门推荐股票 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入hotRecommend")
	public R<HotRecommendVO> detail(HotRecommendEntity hotRecommend) {
		HotRecommendEntity detail = hotRecommendService.getOne(Condition.getQueryWrapper(hotRecommend));
		return R.data(HotRecommendWrapper.build().entityVO(detail));
	}
	/**
	 * 热门推荐股票 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入hotRecommend")
	public R<IPage<HotRecommendVO>> list(HotRecommendEntity hotRecommend, Query query) {
		IPage<HotRecommendEntity> pages = hotRecommendService.page(Condition.getPage(query), Condition.getQueryWrapper(hotRecommend));
		return R.data(HotRecommendWrapper.build().pageVO(pages));
	}

	/**
	 * 热门推荐股票 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入hotRecommend")
	public R<IPage<HotRecommendVO>> page(HotRecommendVO hotRecommend, Query query) {
		IPage<HotRecommendVO> pages = hotRecommendService.selectHotRecommendPage(Condition.getPage(query), hotRecommend);
		return R.data(pages);
	}

	/**
	 * 热门推荐股票 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入hotRecommend")
	public R<Object> save(@Valid @RequestBody HotRecommendEntity hotRecommend) {
		return R.status(hotRecommendService.save(hotRecommend));
	}

	/**
	 * 热门推荐股票 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入hotRecommend")
	public R<Object> update(@Valid @RequestBody HotRecommendEntity hotRecommend) {
		return R.status(hotRecommendService.updateById(hotRecommend));
	}

	/**
	 * 热门推荐股票 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入hotRecommend")
	public R<Object> submit(@Valid @RequestBody HotRecommendEntity hotRecommend) {
		return R.status(hotRecommendService.saveOrUpdate(hotRecommend));
	}

	/**
	 * 热门推荐股票 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(hotRecommendService.deleteLogic(Func.toLongList(ids)));
	}


}
