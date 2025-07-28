
package com.minigod.zero.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.user.entity.Notice;
import com.minigod.zero.user.feign.INoticeClient;
import com.minigod.zero.user.service.INoticeService;
import com.minigod.zero.user.vo.NoticeVO;
import com.minigod.zero.user.wrapper.NoticeWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.mp.support.ZeroPage;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tenant.annotation.TenantDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@TenantDS
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/notice")
@AllArgsConstructor
@Api(value = "用户博客", tags = "博客接口")
public class NoticeController extends ZeroController {

	private final INoticeService noticeService;

	private final INoticeClient noticeClient;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入notice")
	public R<NoticeVO> detail(Notice notice) {
		Notice detail = noticeService.getOne(Condition.getQueryWrapper(notice));
		return R.data(NoticeWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "category", value = "公告类型", paramType = "query", dataType = "integer"),
		@ApiImplicitParam(name = "title", value = "公告标题", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入notice")
	public R<IPage<NoticeVO>> list(@ApiIgnore @RequestParam Map<String, Object> notice, Query query) {
		NoticeWrapper.build().noticeQuery(notice);
		IPage<Notice> pages = noticeService.page(Condition.getPage(query), Condition.getQueryWrapper(notice, Notice.class));
		return R.data(NoticeWrapper.build().pageVO(pages));
	}

	/**
	 * 多表联合查询自定义分页
	 */
	@GetMapping("/page")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "category", value = "公告类型", paramType = "query", dataType = "integer"),
		@ApiImplicitParam(name = "title", value = "公告标题", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入notice")
	public R<IPage<NoticeVO>> page(@ApiIgnore NoticeVO notice, Query query) {
		IPage<NoticeVO> pages = noticeService.selectNoticePage(Condition.getPage(query), notice);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入notice")
	public R save(@RequestBody Notice notice) {
		return R.status(noticeService.save(notice));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入notice")
	public R update(@RequestBody Notice notice) {
		return R.status(noticeService.updateById(notice));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入notice")
	public R submit(@RequestBody Notice notice) {
		return R.status(noticeService.saveOrUpdate(notice));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入notice")
	public R remove(@ApiParam(value = "主键集合") @RequestParam String ids) {
		boolean temp = noticeService.deleteLogic(Func.toLongList(ids));
		return R.status(temp);
	}

	/**
	 * 远程调用分页接口
	 */
	@GetMapping("/top")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "分页远程调用", notes = "传入current,size")
	public R<ZeroPage<Notice>> top(@ApiParam(value = "当前页") @RequestParam Integer current, @ApiParam(value = "每页显示条数") @RequestParam Integer size) {
		ZeroPage<Notice> page = noticeClient.top(current, size);
		return R.data(page);
	}

}
