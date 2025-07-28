package com.minigod.zero.bpmn.module.feign.dto;

import lombok.Data;

/**
 * 线下开户注册APP用户
 *
 * @author eric
 * @date 2024-06-13 15:41:12
 */
@Data
public class OfflineOpenAccountDTO {
	/**
	 * 手机号码
	 */
	private String cellPhone;
	/**
	 * 国家区号
	 */
	private String areaCode;
	/**
	 * 租户ID
	 */
	private String tenantId;
}
