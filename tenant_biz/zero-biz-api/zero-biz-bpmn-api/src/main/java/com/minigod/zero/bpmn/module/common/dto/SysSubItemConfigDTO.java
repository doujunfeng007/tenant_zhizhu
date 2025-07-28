package com.minigod.zero.bpmn.module.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统配置子项参数对象
 *
 * @author eric
 * @since 2024-06-23 13:43:05
 */
@Data
public class SysSubItemConfigDTO {
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 配置项主键ID
	 */
	@ApiModelProperty(value = "配置项主键ID")
	private Long itemId;

	/**
	 * 配置子项文本
	 */
	@ApiModelProperty(value = "配置子项文本")
	private String subItemLabel;

	/**
	 * 配置子项值
	 */
	@ApiModelProperty(value = "配置子项值")
	private String subItemValue;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 多语言标识
	 */
	@ApiModelProperty(value = "多语言标识")
	private String lang;
}
