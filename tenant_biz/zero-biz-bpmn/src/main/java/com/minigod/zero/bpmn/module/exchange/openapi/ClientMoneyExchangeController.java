package com.minigod.zero.bpmn.module.exchange.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpm.vo.MoneyExchangeInfoVO;
import com.minigod.zero.bpmn.module.exchange.dto.ExchangeQueryReq;
import com.minigod.zero.bpmn.module.exchange.dto.MoneyExchangeReqDTO;
import com.minigod.zero.bpmn.module.exchange.service.MoneyExchangeRateInfoService;
import com.minigod.zero.bpmn.module.exchange.vo.CurrencyExchangeRateInfoVO;
import com.minigod.zero.bpmn.module.exchange.vo.CustomerCurrencyExchangeInfoVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 换汇相关 控制器
 *
 * @author zejie.weng
 * @since 2023-05-23
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/moneyExchange_api")
@Api(value = "换汇相关", tags = "换汇相关接口")
public class ClientMoneyExchangeController {

	private final MoneyExchangeRateInfoService moneyExchangeRateInfoService;

	/**
	 * 查询各币种兑换汇率
	 */
	@ApiLog("查询各币种兑换汇率")
	@GetMapping("/getMoneyExchangeRateInfo")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询各币种兑换汇率", notes = "无参")
	public R<List<CurrencyExchangeRateInfoVO>> getMoneyExchangeRateInfo() {
		List<CurrencyExchangeRateInfoVO> list = moneyExchangeRateInfoService.getMoneyExchangeRateInfo();
		return R.data(list);
	}

	/**
	 * 换汇提交
	 */
	@ApiLog("换汇提交")
	@PostMapping("/money_exchange")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "换汇提交", notes = "传入moneyExchangeReqDTO")
	public R moneyExchange(@Valid @RequestBody MoneyExchangeReqDTO moneyExchangeReqDTO) {
		return moneyExchangeRateInfoService.moneyExchange(moneyExchangeReqDTO);
	}

	/**
	 * 换汇撤销
	 */
	@ApiLog("换汇撤销")
	@PostMapping("/cancel")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "换汇撤销", notes = "applicationId")
	public R cancel(@Valid @RequestBody ExchangeQueryReq exchangeQueryReq) {
		return moneyExchangeRateInfoService.cancel(exchangeQueryReq);
	}

	/**
	 * 查询用户换汇兑换记录
	 */
	@ApiLog("查询用户换汇兑换记录")
	@GetMapping("/getPersonalMoneyExchangeInfo")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "查询用户换汇兑换记录", notes = "无参")
	public R<List<CustomerCurrencyExchangeInfoVO>> getPersonalMoneyExchangeInfo(ExchangeQueryReq exchangeQueryReq) {
		List<CustomerCurrencyExchangeInfoVO> list = moneyExchangeRateInfoService.getPersonalMoneyExchangeInfo(exchangeQueryReq);
		return R.data(list);
	}

	/**
	 * 查询换汇权限
	 */
	@ApiLog("查询换汇权限")
	@GetMapping("/money_exchange_auth")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "换汇权限", notes = "无参")
	public R<Map<String, Integer>> moneyExchangeAuth() {
		Map<String, Integer> map = moneyExchangeRateInfoService.moneyExchangeAuth();
		return R.data(map);
	}

	/**
	 * 查询各币种可兑换余额
	 */
	@ApiLog("查询各币种可兑换余额")
	@GetMapping("/money_exchange_value")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "各币种可兑换余额", notes = "无参")
	public R moneyExchangeValue() {
		return moneyExchangeRateInfoService.moneyExchangeValue();
	}

	/**
	 * 换汇记录-存在未审批的数据时返回最新一条未审批的数据
	 */
	@ApiLog("查询换汇记录-存在未审批的数据时返回最新一条未审批的数据")
	@GetMapping("/money_exchange_warn")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "换汇记录", notes = "存在未审批的数据时返回最新一条未审批的数据")
	public R<MoneyExchangeInfoVO> moneyExchangeWarn() {
		return R.data(moneyExchangeRateInfoService.moneyExchangeWarn());
	}


}
