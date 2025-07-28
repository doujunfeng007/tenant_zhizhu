package com.minigod.zero.bpmn.module.withdraw.vo;

import lombok.Data;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/11 15:17
 * @description：提现退款信息
 */
@Data
public class WithdrawRefundVO extends WithdrawInfoVO{
	/**
	 * 提现申请流水ID
	 */
	private String withdrawApplicationId;

}
