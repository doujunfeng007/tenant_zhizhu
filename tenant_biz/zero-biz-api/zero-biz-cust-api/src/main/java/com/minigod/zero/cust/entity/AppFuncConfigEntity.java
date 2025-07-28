package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * APP端功能配置表 实体类
 *
 * @author 掌上智珠
 * @since 2023-06-06
 */
@Data
@TableName("bpm_app_func_config")
@ApiModel(value = "AppFuncConfigEntity对象", description = "APP端功能配置表")
public class AppFuncConfigEntity implements Serializable {

	@ApiModelProperty("主键id")
	@TableId(
		value = "id"
	)
	private Long id;

	/**
	 * 功能编码
	 */
	@ApiModelProperty(value = "功能编码")
	private String funcCode;
	/**
	 * 功能名称
	 */
	@ApiModelProperty(value = "功能名称")
	private String funcName;
	/**
	 * 功能地址
	 */
	@ApiModelProperty(value = "功能地址")
	private String funcPath;

	/**
	 * 类型：1-分类 2-功能
	 */
	@ApiModelProperty(value = "类型：1-分类 2-功能")
	private Integer configType;

	/**
	 * 父级ID
	 */
	@ApiModelProperty(value = "父级ID")
	private Long parentId;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态：0-无效/禁用 1-有效/启用")
	private Integer status;

	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("创建人")
	private Long createUser;

	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("更新人")
	private Long updateUser;

	@TableLogic
	@ApiModelProperty("是否已删除")
	private Integer isDeleted;

}
