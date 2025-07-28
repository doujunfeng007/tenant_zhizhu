package com.minigod.zero.manage.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 掌上智珠
 * @since 2023/7/28 17:41
 */
@Data
public class ComplexTranSimpleRequestVo implements Serializable {
	/**
	 * 替换前
	 */
	@ApiModelProperty(value = "替换前")
	private String beforeReplace ;
	/**
	 * 替换后
	 */
	@ApiModelProperty(value = "替换后")
	private String afterReplace;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "操作人")
	private String createUserName;

	@ApiModelProperty(value = "id")
	private Long id;
}
