package com.minigod.zero.bpm.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.margincredit.vo.MarginCreditAdjustApplyDTO;
import com.minigod.zero.bpm.service.credit.MarginCreditService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 信用额度调整相关 控制器
 *
 * @author zejie.weng
 * @since 2023-05-23
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/marginCredit_api")
@Api(value = "信用额度调整相关", tags = "信用额度调整相关接口")
public class BpmMarginCreditController {

	private final MarginCreditService marginCreditService;

	/**
	 * 提交信用额度调整申请
	 */
	@PostMapping("/save_margin_credit_apply")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "提交信用额度调整申请", notes = "传入marginCreditAdjustApplyDTO")
	public R applyMarginCredit(@Valid @RequestBody MarginCreditAdjustApplyDTO marginCreditAdjustApplyDTO) {
		return marginCreditService.applyMarginCredit(marginCreditAdjustApplyDTO);
	}

	/**
	 * 获取客户信用额度申请状态
	 */
	@GetMapping("/get_credit_adjust_status")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取客户信用额度申请状态", notes = "无参")
	public R getCreditAdjustStatus() {
		return marginCreditService.getCreditAdjustStatus();
	}

	/**
	 * 获取客户信用额度申请列表
	 */
	@GetMapping("/get_credit_adjust_list")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "获取客户信用额度申请列表", notes = "无参")
	public R getCreditAdjustList() {
		return marginCreditService.getCreditAdjustList();
	}

	/**
	 * 获取客户信用额度设置
	 */
	@GetMapping("/get_client_credit_conf")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "获取客户信用额度设置", notes = "无参")
	public R getClientCreditConf() {
		return marginCreditService.getClientCreditConf();
	}


}
