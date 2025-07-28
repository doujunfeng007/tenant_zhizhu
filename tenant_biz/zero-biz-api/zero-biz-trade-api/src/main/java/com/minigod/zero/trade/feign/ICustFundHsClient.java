package com.minigod.zero.trade.feign;

import com.minigod.zero.trade.vo.resp.AssetDetailVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: zejie.weng
 * @create: 2023-5-31 19:22:55
 * @Description: 恒生帐户服务相关接口
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_TRADE_NAME
)
public interface ICustFundHsClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/hs_fund";
	String GET_FUND_DETAIL = API_PREFIX + "/get_fund_detail";
	String GET_HOLDINGS_LIST = API_PREFIX + "/get_holdings_list";


	/**
	 * 获取客户资产
	 */
	@PostMapping(GET_FUND_DETAIL)
	R<AssetDetailVO> getFundDetail(@RequestParam Long custId, @RequestParam String fundAccount);

	/**
	 * 获取客户持仓股票列表
	 */
	@PostMapping(GET_HOLDINGS_LIST)
	R<List<String>> getHoldingsList(@RequestParam Long custId);

}
