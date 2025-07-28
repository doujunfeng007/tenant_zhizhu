package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 掌上智珠
 * @since 2023/7/28 17:23
 */
@Data
@TableName("oms_complex_simple_log")
@ApiModel(value = "ComplexSimpleLogEntity对象", description = "简繁转化对象")
public class ComplexSimpleLogEntity extends BaseEntity {
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
	@ApiModelProperty(value = "创建人姓名")
	private String createUserName;

}
