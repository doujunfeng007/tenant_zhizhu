package com.minigod.zero.bpmn.module.margincredit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName IncreaseCreditLimit.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月09日 17:24:00
 */

/**
 * 信用额度申请信息表
 */
@ApiModel(description = "信用额度申请信息表")
@Data
@TableName(value = "increase_credit_limit")
public class IncreaseCreditLimitEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易账号
	 */
	@TableField(value = "trade_account")
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	@TableField(value = "fund_account")
	@ApiModelProperty(value = "资金账号")
	private String fundAccount;

	/**
	 * 申请信用额度
	 */
	@TableField(value = "apply_quota")
	@ApiModelProperty(value = "申请信用额度")
	private BigDecimal applyQuota;

	/**
	 * 信用比例
	 */
	@TableField(value = "credit_rating")
	@ApiModelProperty(value = "信用比例")
	private BigDecimal creditRating;

	/**
	 * 退回理由
	 */
	@TableField(value = "back_reason")
	@ApiModelProperty(value = "退回理由")
	private String backReason;

	/**
	 * 最终发往柜台的额度
	 */
	@TableField(value = "hk_limit_val")
	@ApiModelProperty(value = "最终发往恒生的额度")
	private BigDecimal hkLimitVal;

	/**
	 * 客户姓名
	 */
	@TableField(value = "client_name")
	@ApiModelProperty(value = "客户姓名")
	private String clientName;

	/**
	 * 英文名
	 */
	@TableField(value = "client_name_spell")
	@ApiModelProperty(value = "英文名")
	private String clientNameSpell;

	/**
	 * 当前信用额度
	 */
	@TableField(value = "current_line_credit")
	@ApiModelProperty(value = "当前信用额度")
	private BigDecimal currentLineCredit;

	/**
	 * 已使用信用额度
	 */
	@TableField(value = "use_line_credit")
	@ApiModelProperty(value = "已使用信用额度")
	private BigDecimal useLineCredit;

	/**
	 * 预约号
	 */
	@TableField(value = "application_id")
	@ApiModelProperty(value = "预约号")
	private String applicationId;

	/**
	 * 用户号
	 */
	@TableField(value = "cust_id")
	@ApiModelProperty(value = "用户号")
	private Long custId;

	/**
	 * 申请类型[1-线上申请 2-手工申请]
	 */
	@TableField(value = "apply_type")
	@ApiModelProperty(value = "申请类型[1-线上申请 2-手工申请]")
	private Integer applyType;

	/**
	 * 租户id
	 */
	@TableField(value = "tenant_id")
	@ApiModelProperty(value = "租户id")
	private String tenantId;

}
