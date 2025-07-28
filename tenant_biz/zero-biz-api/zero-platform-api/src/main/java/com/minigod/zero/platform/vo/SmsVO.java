/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.zero.platform.vo;

import com.minigod.zero.platform.entity.PlatformSms;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信配置表视图实体类
 *
 * @author zsdp
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SmsVO对象", description = "短信配置表")
public class SmsVO extends PlatformSms {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类名
	 */
	private String categoryName;

	/**
	 * 是否启用
	 */
	private String statusName;

}
