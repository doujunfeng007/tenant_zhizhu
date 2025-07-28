package com.minigod.zero.data.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户用户数统概览
 *
 * @author eric
 * @date 2024-10-29 10:24:25
 */
@Data
@ApiModel(value = "CustomerTotalCountVO", description = "客户用户数统概览")
public class CustomerTotalCountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	//已经注册的客户数
	@ApiModelProperty(value = "已经注册的客户数")
	private Integer registeredCount;
	//预批户
	@ApiModelProperty(value = "预批户")
	private Integer preApprovedCount;
	//已经开户的客户数
	@ApiModelProperty(value = "已经开户的客户数")
	private Integer openedCount;
	//已经入金的客户数
	@ApiModelProperty(value = "已经入金的客户数")
	private Integer depositedCount;
	//已经PI认证的客户数
	@ApiModelProperty(value = "已经PI认证的客户数")
	private Integer piCount;
}
