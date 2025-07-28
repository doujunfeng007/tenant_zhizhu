package com.minigod.zero.customer.fegin;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.common.CustomerBrokerConstants;
import com.minigod.zero.customer.dto.BrokerOrderInfoDTO;
import com.minigod.zero.customer.dto.OrderInfoDTO;
import com.minigod.zero.customer.vo.CustAssetsQuarterVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * broker client interface
 */
@FeignClient(value = "minigod-customer")
public interface ICustomerBrokerClient {

	/**
	 * 查询活利得订单列表
	 * @return
	 */
	@PostMapping(CustomerBrokerConstants.HLD_ORDER_INFO_LIST)
	R<List<BrokerOrderInfoDTO>> selHldOrderInfoList(@RequestBody OrderInfoDTO orderInfoDTO);
	/**
	 * 查询债券易订单列表
	 * @return
	 */
	@PostMapping(CustomerBrokerConstants.BOND_ORDER_INFO_LIST)
	R<List<BrokerOrderInfoDTO>> selBondOrderInfoList(@RequestBody OrderInfoDTO orderInfoDTO);


	/**
	 * 获取某个时间的所处某月的最后一个交易日 yyyy-MM-dd
	 * @param date
	 * @return
	 */
	@GetMapping(CustomerBrokerConstants.LATEST_TRADE_DATE)
	R<String> getLatestTradeDate(@RequestParam("date") Date date);

	/**
	 * 获取某一时间的最近交易日(自己或者大于该时间的第一个交易日) yyyy-MM-dd
	 * @param date
	 * @return
	 */
	@GetMapping(CustomerBrokerConstants.BASE_TRADE_DATE)
	R<String> getBaseTradeDate(@RequestParam("date") Date date);

	/**
	 * 分组获取账户的hld资产信息
	 * @return
	 */
	@GetMapping(CustomerBrokerConstants.BROKER_HLD_ASSET_INFO)
	R<List<CustAssetsQuarterVO>> selHldSumQuarter(@RequestParam("startTime") Date startTime, @RequestParam("endTime") Date endTime);
	/**
	 * 分组获取账户的bond资产信息
	 * @return
	 */
	@GetMapping(CustomerBrokerConstants.BROKER_BOND_ASSET_INFO)
	R<List<CustAssetsQuarterVO>> selBondSumQuarter(@RequestParam("startTime") Date startTime, @RequestParam("endTime") Date endTime);


}
