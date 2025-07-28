package com.minigod.zero.bpmn.module.exchange.feign;

import com.minigod.zero.bpmn.module.exchange.entity.CurrencyExchangeRateInfo;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeInfo;
import com.minigod.zero.bpmn.module.exchange.vo.CustomerCurrencyExchangeInfoVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @ClassName ICurrencyExchangeClient.java
 * @Description 货币兑换
 * @createTime 2024年03月09日 11:09:00
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface ICurrencyExchangeClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/currencyExchange";

	String QUERY_MONEY_RATE = API_PREFIX + "/getMoneyRate";

	String CUST_SUBMIT_EXCHANGE = API_PREFIX + "/submit";

	String CURRENCY_EXCHANGE = API_PREFIX + "/exchange";

	String ACCOUNT_CURRENCY_EXCHANGE = API_PREFIX + "/exchange/account";

	String CURRENCY_EXCHANGE_CANCEL = API_PREFIX + "/cancel";

	String CURRENCY_EXCHANGE_QUERY_LIST=API_PREFIX + "/queryExchangeList";

	/**
	 * 查询\币种汇率
	 * @return
	 */
	@GetMapping(QUERY_MONEY_RATE)
	List<CurrencyExchangeRateInfo> getMoneyRate();

	/**
	 * 提交货币兑换
	 * @param customerCurrencyExchangeInfo
	 * @return
	 */
	@PostMapping(CUST_SUBMIT_EXCHANGE)
	R submit(@RequestBody CustomerCurrencyExchangeInfo customerCurrencyExchangeInfo);

	/**
	 * 兑换流程
	 * @param map
	 * @return
	 */
	@PostMapping(CURRENCY_EXCHANGE)
	R exchangeJob(Map<String, Object> map);

	@PostMapping(ACCOUNT_CURRENCY_EXCHANGE)
	R accountDepositJob(Map<String, Object> map);

	/**
	 * 撤销
	 * @param applicationId
	 * @return
	 */
	@PostMapping(CURRENCY_EXCHANGE_CANCEL)
	R cancel(@RequestParam("applicationId")String applicationId);

	@GetMapping(CURRENCY_EXCHANGE_QUERY_LIST)
	List<CustomerCurrencyExchangeInfoVO> queryExchangeList(@SpringQueryMap CustomerCurrencyExchangeInfo customerCurrencyExchangeInfo);
}
