package com.minigod.zero.bpmn.module.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统配置项参数对象
 *
 * @author eric
 * @since 2024-06-20 13:42:08
 */
@Data
public class SysItemConfigDTO {
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 配置项ID
	 */
	@ApiModelProperty(value = "配置项ID")
	private Long itemId;

	/**
	 * 配置项类型
	 */
	@ApiModelProperty(value = "配置项类型")
	private String itemType;

	/**
	 * 配置项文本
	 */
	@ApiModelProperty(value = "配置项文本")
	private String itemLabel;

	/**
	 * 配置项值
	 */
	@ApiModelProperty(value = "配置项值")
	private String itemValue;

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
