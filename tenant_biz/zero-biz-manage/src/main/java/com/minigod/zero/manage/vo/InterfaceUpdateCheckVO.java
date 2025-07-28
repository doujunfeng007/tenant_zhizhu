package com.minigod.zero.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class InterfaceUpdateCheckVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 检测标识集
	 */
	@ApiModelProperty(value = "检测标识集")
	private Map<String, Long> infList;
}
