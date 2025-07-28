package com.minigod.zero.manage.dto;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/16 14:14
 * @description：
 */
@Data
public class SmsDTO {
	private Integer templateCode;
	private String language;
	private String templateParam;
	private String cellPhone;
	private String custId;
}
