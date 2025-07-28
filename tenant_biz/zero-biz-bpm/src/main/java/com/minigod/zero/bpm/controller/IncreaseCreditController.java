package com.minigod.zero.bpm.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpm.service.credit.MarginCreditService;
import com.minigod.zero.bpmn.module.margincredit.vo.MarginCreditAdjustApplyDTO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author chen
 * @ClassName IncreaseCreditController.java
 * @Description TODO
 * @createTime 2024年03月21日 19:16:00
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/marginCredit")
@Api(value = "信用额度", tags = "信用额度")
public class IncreaseCreditController extends ZeroController {

	private final MarginCreditService marginCreditService;


	/**
	 * 根据客户号获取详情
	 */
	@GetMapping("/getInfoByTradeAccount")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tradeAccount")
	public R getInfoByTradeAccount(@RequestParam("tradeAccount")String tradeAccount) {
		return  marginCreditService.queryDetailByApplication(tradeAccount);
	}


	/**
	 * 手工提交信用额度调整申请
	 */
	@PostMapping("/manual_margin_credit_apply")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "手工提交信用额度调整申请", notes = "传入marginCreditAdjustApplyDTO")
	public R applyMarginCredit(@Valid @RequestBody MarginCreditAdjustApplyDTO marginCreditAdjustApplyDTO) {
		return marginCreditService.manualSumbit(marginCreditAdjustApplyDTO);
	}

}
