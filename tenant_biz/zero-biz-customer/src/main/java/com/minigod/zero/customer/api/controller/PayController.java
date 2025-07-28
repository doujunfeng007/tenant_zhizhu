package com.minigod.zero.customer.api.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.AmountDTO;
import com.minigod.zero.customer.dto.PayOrderDTO;
import com.minigod.zero.customer.api.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/8 20:38
 * @description：金额相关接口
 */
@RestController
public class PayController {

	@Autowired
	private PayService payService;
	/**
	 *
	 * 冻结金额（可用转冻结）
	 * @return
	 */
	@PostMapping("/fund/freeze_amount")
	public R freezeAmount(@RequestBody AmountDTO amountDTO) {
		return payService.freezeAmount(amountDTO);
	}

	/**
	 * 基金解冻金额
	 * @return
	 */
	@PostMapping("/fund/thawing_amount")
	public R thawingAmount(@RequestBody AmountDTO amountDTO) {
		return payService.thawingAmount(amountDTO);
	}

	/**
	 * 入金
	 * @return
	 */
	@PostMapping("/fund/gold_deposit")
	public R goldDeposit(@RequestBody AmountDTO amountDTO) {
		return payService.goldDeposit(amountDTO);
	}

	/**
	 * 划扣可用金额
	 * @return
	 */
	@PostMapping("/fund/scratch_button")
	public R scratchButton(@RequestBody AmountDTO amountDTO) {
		return payService.scratchButton(amountDTO);
	}

	/**
	 * 资金流水
	 * currency 币种
	 */
	@GetMapping("/amount_flows")
	public R amountFlows(String currency,String starTime,String endTme,Integer pageSize,Integer pageIndex){
		return payService.amountFlows(currency,starTime,endTme,pageSize,pageIndex);
	}

	/**
	 * 1.预支付 可用 转冻结
	 * @return
	 */
	@PostMapping("/pay/advance_payment")
	public R advancePayment(@RequestBody PayOrderDTO payOrder){
		return payService.advancePayment(payOrder);
	}

	/**
	 * 2.确认支付
	 * 		2.1 全部扣除冻结				（全部成交）
	 * 		2.2 部分扣除，部分冻结不动		（部分成交）
	 * 		2.3 部分扣除，部分转回可用		（部分成交，部分撤回）
	 **/
	@PostMapping("/pay/confirm_payment")
	public R confirmPayment(@RequestBody PayOrderDTO payOrder){
		return payService.confirmPayment(payOrder);
	}

	/**
	 * 在途入金
	 * @param payOrder
	 * @return
	 */
	@PostMapping("/pay/transited_deposit")
	public R transitedDeposit(@RequestBody PayOrderDTO payOrder){
		return payService.transitedDeposit(payOrder);
	}

	/**
	 * 在途转可用
	 * @param payOrder
	 * @return
	 */
	@PostMapping("/pay/transited_to_available")
	public R transitedToAvailable(@RequestBody PayOrderDTO payOrder){
		return payService.transitedToAvailable(payOrder);
	}
}
