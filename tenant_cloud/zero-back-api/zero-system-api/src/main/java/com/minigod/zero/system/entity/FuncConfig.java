package com.minigod.zero.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能配置模块 实体类
 *
 * @author ZSDP
 * @since 2024-07-23
 */
@Data
@TableName("zero_func_config")
@ApiModel(value = "FuncConfig对象", description = "功能配置模块")
public class FuncConfig extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 菜单父主键
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "父主键")
	private Long parentId;

	/**
	 * 功能编号
	 */
	@ApiModelProperty(value = "功能编号")
	private String funcCode;

	/**
	 * 功能名称
	 */
	@ApiModelProperty(value = "功能名称")
	private String funcName;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	/**
	 * 是否隐藏
	 */
	@ApiModelProperty(value = "是否隐藏")
	private Integer isSelect;

}
