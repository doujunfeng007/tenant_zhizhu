package com.minigod.zero.bpmn.module.exchange.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.exchange.service.ICustomerCurrencyExchangeInfoService;
import com.minigod.zero.bpmn.module.exchange.vo.CustomerCurrencyExchangeInfoVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chen
 * @ClassName CustomerCurrencyExchangeInfoController.java
 * @Description TODO
 * @createTime 2024年03月16日 16:29:00
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/currencyExchangeInfo")
@Api(value = "兑换信息", tags = "兑换信息")
public class CustomerCurrencyExchangeInfoController extends ZeroController {

	@Resource
	private ICustomerCurrencyExchangeInfoService currencyExchangeInfoService;

	/**
	 * 货币兑换信息详情查询
	 */
	@ApiLog("货币兑换信息详情查询")
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入applicationId")
	public R<CustomerCurrencyExchangeInfoVO> detail(@RequestParam("applicationId")String applicationId) {
		CustomerCurrencyExchangeInfoVO detail = currencyExchangeInfoService.queryDetailByApplication(applicationId);
		return R.data(detail);
	}
}
