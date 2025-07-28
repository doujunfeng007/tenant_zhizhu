package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoModifyApplyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户户资料修改申请记录查询视图实体
 *
 * @author eric
 * @since 2024-08-05 14:08:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountOpenInfoModifyApplyVO extends AccountOpenInfoModifyApplyEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 客户中文名
	 */
	@ApiModelProperty(value = "客户中文名")
	private String clientName;

	/**
	 * 客户英文名
	 */
	@ApiModelProperty(value = "客户英文名")
	private String clientNameEn;
	/**
	 * 客户中文名拼音
	 */
	@ApiModelProperty(value = "客户中文名拼音")
	private String clientNameSpell;

	/**
	 * 审核状态名称
	 */
	@ApiModelProperty(value = "审核状态名称")
	private String applyStatusName;

	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	private Integer sex;

	/**
	 * 开户接入方式: 1:H5开户 2:APP开户 3:线下开户
	 */
	@ApiModelProperty(value = "开户接入方式: 1:H5开户 2:APP开户 3:线下开户")
	private Integer openAccountAccessWay;

	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(value = "电子邮箱")
	private String email;
}
