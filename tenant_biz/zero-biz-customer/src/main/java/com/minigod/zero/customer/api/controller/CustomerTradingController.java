package com.minigod.zero.customer.api.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.CustomerTradingService;
import com.minigod.zero.customer.vo.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: com.minigod.zero.customer.api.controller.CustomerTransactionController
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/7 8:53
 * @Version: 1.0
 */
@RestController
@RequestMapping("/trading")
public class CustomerTradingController {


	@Autowired
	private CustomerTradingService customerTradingService;

	/**
	 * 基金交易流水
	 * @param customerFundTradingRecordsListVO
	 * @return
	 */
	@PostMapping("/fund/list")
	@ApiOperation(value = "基金交易流水(后台)", notes = "")
	public R fundTradingList(@RequestBody CustomerFundTradingRecordsListVO customerFundTradingRecordsListVO){
		return customerTradingService.fundTradingList(customerFundTradingRecordsListVO);
	}

	/**
	 * 基金持仓记录
	 * @param customerFundHoldingListVO
	 * @return
	 */
	@PostMapping("/fund/holding/list")
	@ApiOperation(value = "基金持仓记录(后台)", notes = "")
	public R fundHoldingList(@RequestBody CustomerFundHoldingListVO customerFundHoldingListVO){
		return customerTradingService.fundHoldingList(customerFundHoldingListVO);
	}



	/**
	 * 债券交易流水
	 * @param customerBondTradingRecordsListVO
	 * @return
	 */
	@PostMapping("/bond/list")
	@ApiOperation(value = "债券交易流水(后台)", notes = "")
	public R bondTradingList(@RequestBody CustomerBondTradingRecordsListVO customerBondTradingRecordsListVO){
		return customerTradingService.bondTradingList(customerBondTradingRecordsListVO);
	}


	/**
	 * 债券持仓记录
	 * @param customerBondHoldingListVO
	 * @return
	 */
	@PostMapping("/bond/holding/list")
	@ApiOperation(value = "债券持仓记录(后台)", notes = "")
	public R bondHoldingList(@RequestBody CustomerBondHoldingListVO customerBondHoldingListVO){
		return customerTradingService.bondHoldingList(customerBondHoldingListVO);
	}

	/**
	 * 活利得持仓
	 * @param customerHldHoldingListVO
	 * @return
	 */
	@PostMapping("/hld/holding/list")
	@ApiOperation(value = "活利得持仓(后台)", notes = "")
	public R hldHoldingList(@RequestBody CustomerHldHoldingListVO customerHldHoldingListVO){
		return customerTradingService.hldHoldingList(customerHldHoldingListVO);
	}

	/**
	 * 活利得交易流水
	 * @param customerHldTradingRecordsListVO
	 * @return
	 */
	@PostMapping("/hld/list")
	@ApiOperation(value = "活利得交易流水(后台)", notes = "")
	public R hldTradingList(@RequestBody CustomerHldTradingRecordsListVO customerHldTradingRecordsListVO){
		return customerTradingService.hldTradingList(customerHldTradingRecordsListVO);
	}
}
