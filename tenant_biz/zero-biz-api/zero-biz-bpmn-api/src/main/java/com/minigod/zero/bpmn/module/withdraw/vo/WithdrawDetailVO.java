package com.minigod.zero.bpmn.module.withdraw.vo;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/13 15:27
 * @description：
 */
@Data
public class WithdrawDetailVO extends WithdrawInfoVO{
	/**
	 * 是否第三者收款[0-否 1-是]
	 */
	private Integer thirdRecvFlag;
	/**
	 * 与第三者收款人关系
	 */
	private String thirdRecvReal;

	/**
	 * 第三者收款原因
	 */
	private String thirdRecvReason;
}
