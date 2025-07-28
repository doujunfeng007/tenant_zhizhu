package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: sunline
 * @date: 2020/6/12
 * @version: v1.0
 */
@Data
public class EddaInfoCallProtocol {
	/**
	 * 预约号
	 */
	private String applicationId;
	/**
	 * 单笔限额
	 */
	private BigDecimal bankQuota;
	/**
	 * 授权状态 1授权中 2已授权 3授权失败 4 撤销授权
	 */
	private Integer eddaState;
	/**
	 * 失败原因
	 */
	private String eddaFailReason;
}
