package com.minigod.zero.bpm.vo.request;

import lombok.Data;

/**
 * @description:
 * @author: sunline
 * @date: 2019/6/25 10:19
 * @version: v1.0
 */
@Data
public class ClientDepositFundsCallBackProtocol {

	/**
	 * BPM后台的入金预约号
	 */
    private String applicationId;
	/**
	 * BPM后台的eddi预约号
	 */
	private String eddaFundApplicationId;
	/**
	 * 中台入金id
	 */
	private Long bizId;
	/**
	 * 入金状态 0已提交 1已受理 2已退回 3已完成
	 */
	private Integer depositStatus;
	/**
	 * 退回理由
	 */
    private String backReason;

}
