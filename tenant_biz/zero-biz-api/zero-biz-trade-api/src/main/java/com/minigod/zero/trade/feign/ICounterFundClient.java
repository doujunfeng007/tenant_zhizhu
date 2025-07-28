package com.minigod.zero.trade.feign;

import com.minigod.zero.trade.vo.sjmb.req.*;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.sjmb.req.*;
import com.minigod.zero.trade.vo.sjmb.resp.FundQueryVO;
import com.minigod.zero.trade.vo.sjmb.resp.JournalOrdersVO;
import com.minigod.zero.trade.vo.sjmb.resp.TotalAssetInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author:yanghu.luo
 * @create: 2023-04-26 10:14
 * @Description: 恒生登陆相关接口
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_TRADE_NAME
)
public interface ICounterFundClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/fund";
	String DEPOSIT = API_PREFIX + "/deposit";
	String WITHDRAWAL =API_PREFIX + "/withdrawal";
	String FREEZE =API_PREFIX + "/freeze";
	String UN_FREEZE =API_PREFIX + "/un_freeze";
	String UN_FREEZE_WITHDRAW =API_PREFIX + "/un_freeze_withdraw";
	String QUERY_FUND =API_PREFIX + "/selectByaccount";
	String QUERY_POSITION_LIST =API_PREFIX + "/customer_position_list";
	String QUERY_HISTORY_ORDERS =API_PREFIX + "/customer_hostory_orders";




	/**
	 *  入金
	 */
	@PostMapping(value =DEPOSIT)
	R<Object> deposit(@RequestBody FundDepositReq fundDepositReq);


	/**
	 *  出金
	 */
	@PostMapping(value =WITHDRAWAL)
	R<Object> withdrawal(@RequestBody FundWithdrawalReq withdrawalReq);


	/**
	 *  冻结
	 */
	@PostMapping(value =FREEZE)
	R<Object> freeze(@RequestBody FundFreezeReq fundFreezeReq);

	/**
	 *  解冻
	 */
	@PostMapping(value =UN_FREEZE)
	R<Object> unFreeze(@RequestBody FundUnFreezeReq fundUnFreezeReq);

	/**
	 * 解冻并扣款
	 * @param fundUnFreezeWithdrawReq
	 * @return
	 */
	@PostMapping(value =UN_FREEZE_WITHDRAW)
	R<Object> unFreezeWithdraw(@RequestBody FundUnFreezeWithdrawReq fundUnFreezeWithdrawReq);

	/**
	 * 查询币种余额
	 * @param tradeAccount
	 * @param capitalAccount
	 * @return
	 */
	@GetMapping(value =QUERY_FUND)
	R<FundQueryVO> queryFund(@RequestParam("tradeAccount") String tradeAccount,
								@RequestParam("capitalAccount") String capitalAccount);

	/**
	 * 查询持仓相关信息
	 * @param tradeAccount
	 * @param capitalAccount
	 * @return
	 */
	@GetMapping(value =QUERY_POSITION_LIST)
	R<TotalAssetInfoVO> customerPositionList(@RequestParam("tradeAccount") String tradeAccount,
								  @RequestParam("capitalAccount") String capitalAccount);

	/**
	 * 查询用户订单信息
	 * @param tradeAccount
	 * @param capitalAccount
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GetMapping(value =QUERY_HISTORY_ORDERS)
	R<List<JournalOrdersVO>> customerHistoryOrders(@RequestParam("tradeAccount") String tradeAccount,
												   @RequestParam("capitalAccount") String capitalAccount,
												   @RequestParam("startDate") String startDate,
												   @RequestParam("endDate") String endDate);
}
