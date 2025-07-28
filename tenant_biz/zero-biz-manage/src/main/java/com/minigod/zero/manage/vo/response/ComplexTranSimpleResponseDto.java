package com.minigod.zero.manage.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 掌上智珠
 * @since 2023/7/28 17:41
 */
@Data
public class ComplexTranSimpleResponseDto implements Serializable {

	@ApiModelProperty(value = "id")
	private Long id ;
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

	/**
	 * 智珠过滤新闻资讯
	 */
	@ApiModelProperty(value = "操作人姓名")
	private String createUserName;

	@ApiModelProperty(value = "状态")
	private Integer status;


	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "创建时间")
	private Date updateTime;

	/**
	 * 智珠过滤新闻资讯
	 */
	@ApiModelProperty(value = "操作人姓名")
	private String updateUserName;
}
