package com.minigod.zero.bpmn.module.pi.dto;

import lombok.Data;

/**
 * @author eric
 * @description: 银联四要素
 * @since 2025-06-03 15:47:12
 */
@Data
public class UnionPayFourDTO {
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 身份证号码
	 */
	private String idCode;
	/**
	 * 银行卡号
	 */
	private String bankNo;
	/**
	 * 银行卡预留的手机号
	 */
	private String bankMobile;
}
