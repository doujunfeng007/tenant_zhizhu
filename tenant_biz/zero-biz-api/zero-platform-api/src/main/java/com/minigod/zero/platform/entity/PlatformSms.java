/*
 * Copyright (c)  2022. ZSDP Inc. All rights reserved.
 */
package com.minigod.zero.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信配置表实体类
 *
 * @author zsdp
 */
@Data
@TableName("minigod_sms")
@EqualsAndHashCode(callSuper = true)
public class PlatformSms extends TenantEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 资源编号
	 */
	@ApiModelProperty(value = "资源编号")
	private String smsCode;

	/**
	 * 模板ID
	 */
	@ApiModelProperty(value = "模板ID")
	private String templateId;
	/**
	 * 分类
	 */
	@ApiModelProperty(value = "分类")
	private Integer category;
	/**
	 * accessKey
	 */
	@ApiModelProperty(value = "accessKey")
	private String accessKey;
	/**
	 * secretKey
	 */
	@ApiModelProperty(value = "secretKey")
	private String secretKey;
	/**
	 * regionId
	 */
	@ApiModelProperty(value = "regionId")
	private String regionId;
	/**
	 * 短信签名
	 */
	@ApiModelProperty(value = "短信签名")
	private String signName;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
}
