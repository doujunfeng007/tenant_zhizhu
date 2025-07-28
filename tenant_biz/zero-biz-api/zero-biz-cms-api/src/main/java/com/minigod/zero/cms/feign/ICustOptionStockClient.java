package com.minigod.zero.cms.feign;

import com.minigod.zero.cms.vo.OptionalGroupNewRequest;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
	value = AppConstant.SERVICE_BIZ_CMS_NAME
)
public interface ICustOptionStockClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/stock";
	String FIND_OPT_STK_BY_USER_ID = API_PREFIX + "/getCustOptionStocks";
	String OPTION_STOCK_ADD = API_PREFIX + "/optionStockAdd";


	/**
	 * 获取自选股票
	 * @return
	 */
	@GetMapping(FIND_OPT_STK_BY_USER_ID)
	R<Object> getCustOptionStocks();

	/**
	 * 加入自选股票
	 * @return
	 */
	@PostMapping(OPTION_STOCK_ADD)
	R<Object> optionStockAdd(@RequestBody OptionalGroupNewRequest req);
}
