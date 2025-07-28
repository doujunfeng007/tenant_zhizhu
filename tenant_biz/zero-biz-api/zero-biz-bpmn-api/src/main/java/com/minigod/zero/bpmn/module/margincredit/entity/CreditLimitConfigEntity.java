package com.minigod.zero.bpmn.module.margincredit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName CreditLimitConfig.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月09日 17:23:00
 */

/**
 * 信用额度配置表
 */
@ApiModel(description = "信用额度配置表")
@Data
@TableName(value = "credit_limit_config")
public class CreditLimitConfigEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 提额占比下限（%）
	 */
	@TableField(value = "lower_limit")
	@ApiModelProperty(value = "提额占比下限（%）")
	private BigDecimal lowerLimit;

	/**
	 * 降额占比上限（%）
	 */
	@TableField(value = "uper_limit")
	@ApiModelProperty(value = "降额占比上限（%）")
	private BigDecimal uperLimit;

	/**
	 * 提额比例（%）
	 */
	@TableField(value = "increase_rate")
	@ApiModelProperty(value = "提额比例（%）")
	private BigDecimal increaseRate;

	/**
	 * 降额比例（%）
	 */
	@TableField(value = "derating_rate")
	@ApiModelProperty(value = "降额比例（%）")
	private BigDecimal deratingRate;

}
