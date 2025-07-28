package com.minigod.zero.bpm.vo.request;

import lombok.Data;

@Data
public class ClientEddaFundsCallBackProtocol {
	/**
	 * 中台入金id
	 */
	private Long bizId;
	/**
	 * 预约号
	 */
    private String applicationId;
	/**
	 * edda入金流水号
	 */
	private String eddaApplicationId;
}
