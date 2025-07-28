package com.minigod.zero.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PublishVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 查询的id
	 */
	@ApiModelProperty("主键")
    private Long id;
	/**
	 * 查询标题内容
	 */
	@ApiModelProperty("查询标题内容")
	private String key;
}
