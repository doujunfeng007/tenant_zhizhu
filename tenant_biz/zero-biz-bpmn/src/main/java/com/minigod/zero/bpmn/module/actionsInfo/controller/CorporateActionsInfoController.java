package com.minigod.zero.bpmn.module.actionsInfo.controller;

import com.minigod.zero.bpmn.module.actionsInfo.service.ICorporateActionsInfoService;
import com.minigod.zero.bpmn.module.actionsInfo.wrapper.ActionsInfoWrapper;
import com.minigod.zero.bpmn.module.actionsInfo.bo.ActionsInfoBo;
import com.minigod.zero.bpmn.module.actionsInfo.entity.CorporateActionsInfoEntity;
import com.minigod.zero.bpmn.module.actionsInfo.vo.CorporateActionsInfoVO;
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

import java.util.Date;

/**
 * 公司行动记录表 控制器
 *
 * @author wengzejie
 * @since 2024-03-13
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/corporate_action")
@Api(value = "公司行动记录表", tags = "公司行动记录表接口")
public class CorporateActionsInfoController extends ZeroController {

	private final ICorporateActionsInfoService actionsInfoService;

	/**
	 * 公司行动记录表 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入actionsInfo")
	public R<CorporateActionsInfoVO> detail(CorporateActionsInfoEntity actionsInfo) {
		CorporateActionsInfoEntity detail = actionsInfoService.getOne(Condition.getQueryWrapper(actionsInfo));
		return R.data(ActionsInfoWrapper.build().entityVO(detail));
	}

	/**
	 * 公司行动记录表 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入actionsInfo")
	public R<IPage<CorporateActionsInfoVO>> list(ActionsInfoBo bo, Query query) {
		IPage<CorporateActionsInfoVO> pages = actionsInfoService.selectActionsInfoPage(Condition.getPage(query), bo);
		if (pages != null && pages.getRecords() != null && pages.getRecords().size() > 0) {
			pages.getRecords().forEach(item -> {
				if (item.getStatus() != null) {
					//状态[0-未处理 1-已处理 2-已完成 3-已退回]
					switch (item.getStatus()) {
						case 0:
							item.setStatusName("未处理");
							break;
						case 1:
							item.setStatusName("已处理");
							break;
						case 2:
							item.setStatusName("已完成");
							break;
						case 3:
							item.setStatusName("已退回");
							break;
						default:
							item.setStatusName("未知状态");
					}
				}
			});
		}
		return R.data(pages);
	}

	/**
	 * 公司行动记录表 自定义分页
	 */
	@GetMapping("/checkList")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "处理分页", notes = "传入actionsInfo")
	public R<IPage<CorporateActionsInfoVO>> checkList(ActionsInfoBo bo, Query query) {
		// 设置查询未处理记录
		bo.setStatus(0);
		IPage<CorporateActionsInfoVO> pages = actionsInfoService.selectActionsInfoPage(Condition.getPage(query), bo);
		if (pages != null && pages.getRecords() != null && pages.getRecords().size() > 0) {
			pages.getRecords().forEach(item -> {
				if (item.getStatus() != null) {
					//状态[0-未处理 1-已处理 2-已完成 3-已退回]
					switch (item.getStatus()) {
						case 0:
							item.setStatusName("未处理");
							break;
						case 1:
							item.setStatusName("已处理");
							break;
						case 2:
							item.setStatusName("已完成");
							break;
						case 3:
							item.setStatusName("已退回");
							break;
						default:
							item.setStatusName("未知状态");
					}
				}
			});
		}
		return R.data(pages);
	}

	/**
	 * 公司行动记录表 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入actionsInfo")
	public R<Object> save(@Valid @RequestBody CorporateActionsInfoEntity actionsInfo) {
		return R.status(actionsInfoService.save(actionsInfo));
	}

	/**
	 * 公司行动记录表 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入actionsInfo")
	public R<Object> update(@Valid @RequestBody CorporateActionsInfoEntity actionsInfo) {
		return R.status(actionsInfoService.updateById(actionsInfo));
	}

	/**
	 * 公司行动记录表 提交
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "提交", notes = "传入actionsInfo")
	public R<Object> submit(@Valid @RequestBody CorporateActionsInfoEntity actionsInfo) {
		actionsInfo.setStatus(2);// 已完成
		actionsInfo.setUpdateTime(new Date());
		return R.status(actionsInfoService.updateById(actionsInfo));
	}

	/**
	 * 公司行动记录表 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(actionsInfoService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 公司行动记录表 退回
	 */
	@PostMapping("/doBack")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "退回", notes = "传入actionsInfo")
	public R<Object> doBack(@Valid @RequestBody CorporateActionsInfoEntity actionsInfo) {
		actionsInfo.setStatus(3);// 已退回
		actionsInfo.setUpdateTime(new Date());
		return R.status(actionsInfoService.updateById(actionsInfo));
	}


}
