package com.minigod.zero.bpmn.module.exchange.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.exchange.dto.MoneyExchangeReqDTO;
import com.minigod.zero.bpmn.module.exchange.service.MoneyExchangeRateInfoService;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeRateInfoVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName MoneyExchangeController.java
 * @Author: linggr
 * @CreateDate: 2024/9/3 17:48
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/currencyExchange")
@Api(value = "货币兑换", tags = "货币兑换")
public class MoneyExchangeController extends ZeroController {

	private final MoneyExchangeRateInfoService moneyExchangeRateInfoService;

	/**
	 * 根据客户号获取货币兑换详情
	 */
	@ApiLog("根据客户号获取货币兑换详情")
	@GetMapping("/getInfoByTradeAccount")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入tradeAccount")
	public R getInfoByTradeAccount(@RequestParam("tradeAccount")String tradeAccount) {
		return  moneyExchangeRateInfoService.queryInfoByTradeAccount(tradeAccount);
	}

	@ApiLog("查询各币种兑换汇率")
	@GetMapping("/getMoneyExchangeRateInfo")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询各币种兑换汇率", notes = "无参")
	public R<List<CurrencyExchangeRateInfoVO>> getMoneyExchangeRateInfo() {
		List<CurrencyExchangeRateInfoVO> list = moneyExchangeRateInfoService.getMoneyExchangeRateInfo();
		return R.data(list);
	}

	@ApiLog("人工提交换汇提交")
	@PostMapping("/manual_money_exchange")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "人工提交换汇提交", notes = "传入moneyExchangeReqDTO")
	public R moneyExchange(@Valid @RequestBody MoneyExchangeReqDTO moneyExchangeReqDTO) {
		return moneyExchangeRateInfoService.manualMoneyExchange(moneyExchangeReqDTO);
	}

	/**
	 * 各币种可兑换余额
	 */
	@ApiLog("各币种可兑换余额")
	@GetMapping("/money_exchange_value")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "各币种可兑换余额", notes = "无参")
	public R moneyExchangeValue(@RequestParam("tradeAccount")String tradeAccount,@RequestParam("capitalAccount")String capitalAccount) {
		return moneyExchangeRateInfoService.moneyExchangeValue(tradeAccount,capitalAccount);
	}

}
