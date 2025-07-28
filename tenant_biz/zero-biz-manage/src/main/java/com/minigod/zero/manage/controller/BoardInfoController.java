package com.minigod.zero.manage.controller;

import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.manage.entity.BoardInfoEntity;
import com.minigod.zero.manage.vo.BoardInfoVO;
import com.minigod.zero.manage.wrapper.BoardInfoWrapper;
import com.minigod.zero.manage.service.IBoardInfoService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.utils.DateUtil;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.boot.ctrl.ZeroController;

import java.util.Date;


/**
 * 公告信息表 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/boardInfo")
@Api(value = "公告信息表", tags = "公告信息表接口")
public class BoardInfoController extends ZeroController {

	private final IBoardInfoService boardInfoService;

	/**
	 * 公告信息表
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "公告信息表", notes = "公告信息表,根据不同的租户ID分页查询")
	public R<IPage<BoardInfoVO>> page(BoardInfoVO boardInfo, Query query) {
		String tenantId = AuthUtil.getTenantId();
		boardInfo.setTenantId(tenantId);
		IPage<BoardInfoVO> pages = boardInfoService.selectBoardInfoPage(Condition.getPage(query), boardInfo);
		Date now = DateUtil.now();
		IPage<BoardInfoVO> result = pages.convert(boardInfoVO -> {
			//这里的状态只是返回给前端展示,实际上数据库的状态需要手工去调整,不影响使用,app调用也是根据时间去判断的,否则需要一个定时任务去处理
			boolean isBetween = now.after(boardInfoVO.getStartTime()) && now.before(boardInfoVO.getEndTime());
			if (!isBetween) {
				boardInfoVO.setStatus(0);
			}
			return boardInfoVO;
		});
		return R.data(result);
	}

	/**
	 * 公告信息表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入boardInfo")
	public R<BoardInfoVO> detail(BoardInfoEntity boardInfo) {
		String tenantId = AuthUtil.getTenantId();
		boardInfo.setTenantId(tenantId);
		BoardInfoEntity detail = boardInfoService.getOne(Condition.getQueryWrapper(boardInfo));
		return R.data(BoardInfoWrapper.build().entityVO(detail));
	}


	/**
	 * 公告信息表 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入boardInfo")
	public R<Object> submit(@Valid @RequestBody BoardInfoEntity boardInfo) {
		String tenantId = AuthUtil.getTenantId();
		boardInfo.setTenantId(tenantId);
		return boardInfoService.submit(boardInfo);
	}

	/**
	 * 公告信息表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(boardInfoService.removeByIds(Func.toLongList(ids)));
	}
}
