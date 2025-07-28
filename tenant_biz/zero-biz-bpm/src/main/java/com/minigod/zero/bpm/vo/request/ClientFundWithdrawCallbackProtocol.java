package com.minigod.zero.bpm.vo.request;

import lombok.Data;

/**
 * @description:
 * @author: sunline
 * @date: 2019/4/10 11:27
 * @version: v1.0
 */
@Data
public class ClientFundWithdrawCallbackProtocol {
	/**
	 * 预约号
	 */
	private String applicationId;
	/**
	 * 出金状态 0 已提交 1 已受理 2 已退回 3 已完成
	 */
	private Integer withdrawStatus;
	/**
	 * 退回理由
	 */
	private String backReason;
	/**
	 * 中台出金记录id
	 */
	private Long bizId;
}
