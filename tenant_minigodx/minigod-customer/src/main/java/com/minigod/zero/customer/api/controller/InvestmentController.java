package com.minigod.zero.customer.api.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.MyInvestmentService;
import com.minigod.zero.customer.vo.CheckTradePasswordVO;
import com.minigod.zero.customer.vo.CustBondTradingListVO;
import com.minigod.zero.customer.vo.CustHldTradingListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/6 21:13
 * @description： 投资相关
 */
@RestController
public class  InvestmentController {
	@Autowired
	private MyInvestmentService myInvestmentService;

	/**
	 * 我的账户收益
	 * @param accountType
	 * @param currency
	 * @return
	 */
	@GetMapping("/account_income")
	public R accountIncome(Integer accountType,String currency){
		return myInvestmentService.getAccountIncome(accountType,currency);
	}

	/**
	 * 我的持仓-基金
	 * @return
	 */
	@GetMapping("/fund_holding")
	public R fundHolding(Integer pageIndex,Integer pageSize){
		return myInvestmentService.getCustomerFundHolding(pageIndex, pageSize);
	}

	/**
	 * 活利得持仓
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/hld_holding")
	public R hldHolding(Integer pageIndex,Integer pageSize){
		return myInvestmentService.hldHolding(pageIndex, pageSize);
	}


	/**
	 * 我的总资产
	 * @return
	 */
	@GetMapping("/total_assets")
	public R totalAssets(String currency){
		return myInvestmentService.totalAssets(currency);
	}

	/**
	 * 基金交易记录
	 * @param type
	 * @return
	 */
	@GetMapping("/fund_trading/list")
	public R fundTradingList(@RequestParam(name = "type",required = false) String type ,
							 @RequestParam(name = "pageIndex",defaultValue = "1") Integer pageIndex,
							 @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize){
		return myInvestmentService.fundTradingList(type,pageIndex,pageSize);
	}


	/**
	 * 活利得交易记录
	 * @param custHldTradingListVO
	 * @return
	 */
	@GetMapping("/hld_trading/list")
	public R hldTradingList(CustHldTradingListVO custHldTradingListVO){
		return myInvestmentService.hldTradingList(custHldTradingListVO);
	}

	/**
	 * 债券交易记录
	 * @param custBondTradingListVO
	 * @return
	 */
	@GetMapping("/bond_trading/list")
	public R bondTradingList(CustBondTradingListVO custBondTradingListVO){
		return myInvestmentService.bondTradingList(custBondTradingListVO);
	}

	/**
	 * 总资产7天数据
	 * @param currency
	 * @return
	 */
	@GetMapping("/total_asserts/list")
	public R assertsHistory(String currency){
		return myInvestmentService.assertsHistory(currency);
	}

	/**
	 * 持仓资产7天数据
	 * @param currency
	 * @return
	 */
	@GetMapping("/positions_asserts/list")
	public R positionsAsserts(String currency){
		return myInvestmentService.positionsAsserts(currency);
	}


	@PostMapping("/check_trade_password")
	public R checkTradePassword(@RequestBody CheckTradePasswordVO checkTradePasswordVO){
		return myInvestmentService.checkTradePassword(checkTradePasswordVO.getExAccountId(), checkTradePasswordVO.getToken());
	}

}
