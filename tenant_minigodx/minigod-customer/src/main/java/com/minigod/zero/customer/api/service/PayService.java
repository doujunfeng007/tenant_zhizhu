package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.AmountDTO;
import com.minigod.zero.customer.dto.PayOrderDTO;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/26 10:48
 * @description：
 */
public interface PayService {
	/**
	 * 冻结金额（可用转冻结）
	 * @return
	 */
	R freezeAmount(AmountDTO amountDTO);
	/**
	 * 解冻金额
	 * @return
	 */
	R thawingAmount(AmountDTO amountDTO);
	/**
	 * 入金-
	 * @return
	 */
	R goldDeposit(AmountDTO amountDTO);
	/**
	 * 划扣可用金额
	 * @return
	 */
	R scratchButton(AmountDTO amountDTO);
	/**
	 * 资金流水
	 * @param currency
	 * @return
	 */
	R amountFlows(String currency,String starTime,String endTme,Integer pageSize,Integer pageIndex);

	/**
	 * 预支付
	 * @param payOrder
	 * @return
	 */
	R advancePayment(PayOrderDTO payOrder);

	/**
	 * 确认支付
	 * @param payOrder
	 * @return
	 */
	R confirmPayment(PayOrderDTO payOrder);

	/**
	 * 在途金额入金
	 * @param payOrder
	 * @return
	 */
	R transitedDeposit(PayOrderDTO payOrder);

	/**
	 * 在途转可用
	 * @param payOrder
	 * @return
	 */
	R transitedToAvailable(PayOrderDTO payOrder);
}
