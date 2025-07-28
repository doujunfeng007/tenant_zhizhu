package com.minigod.zero.manage.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 掌上智珠
 * @since 2023/8/3 11:26
 */
@Data
public class ComPlexSimpleReplaceDto implements Serializable {
	@ApiModelProperty(value = "替换前")
	private String beforeReplace ;
	/**
	 * 替换后
	 */
	@ApiModelProperty(value = "替换后")
	private String afterReplace;
}
