package com.minigod.zero.customer.dto;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.dto.DefaultRegisterDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/9/19 17:23
 * @Version: 1.0
 */
@Data
public class DefaultRegisterDTO {

	private String phone;

	private String areaCode;

	private String tenantId;
	/**
	 * 渠道id
	 */
	private Long channelId;
	/**
	 * 经纪人id
	 */
	private Long brokerId;

}
