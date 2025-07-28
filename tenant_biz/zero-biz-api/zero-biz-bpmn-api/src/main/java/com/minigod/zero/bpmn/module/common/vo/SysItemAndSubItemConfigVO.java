package com.minigod.zero.bpmn.module.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 系统配置和子项配置
 *
 * @author eric
 * @since 2024-06-20 13:29:00
 */
@ApiModel("系统配置和子项配置")
@Data
public class SysItemAndSubItemConfigVO {
	@ApiModelProperty(value = "配置项")
	private SysItemConfigVO sysItemConfigVO;
}
