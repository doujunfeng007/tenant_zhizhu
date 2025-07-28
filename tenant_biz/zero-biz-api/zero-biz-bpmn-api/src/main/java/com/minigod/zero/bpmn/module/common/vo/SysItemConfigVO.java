package com.minigod.zero.bpmn.module.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 系统通用项配置
 *
 * @author eric
 * @since 2024-06-20 13:30:25
 */
@ApiModel("SysItemConfigVO")
@Data
public class SysItemConfigVO {
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

	@ApiModelProperty(value = "配置子项")
	private List<SysSubItemConfigVO> sysSubItemConfigVOs;
}
