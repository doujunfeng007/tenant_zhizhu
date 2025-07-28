package com.minigod.zero.bpmn.module.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 系统通用配置子项
 *
 * @author eric
 * @since 2024-06-20 13:37:05
 */
@ApiModel("SysSubItemConfigVO")
@Data
public class SysSubItemConfigVO {
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

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

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "修改时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 更新人
	 */
	@ApiModelProperty(value = "修改人")
	@Size(max = 32, message = "修改人最大长度要小于 32")
	private Long updateUser;

	/**
	 * 创建人
	 */
	@ApiModelProperty(value = "创建人")
	@Size(max = 32, message = "创建人最大长度要小于 32")
	private Long createUser;
}
