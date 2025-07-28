package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.AccountOpenApplicationEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 *  模型VO
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountOpenApplicationVO extends AccountOpenApplicationEntity {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("客户中文名")
	private String clientName;

	@ApiModelProperty("客户英文名")
	private String clientNameSpell;

	@ApiModelProperty("姓氏")
	private String familyName;

	@ApiModelProperty("名字")
	private String givenName;

	@ApiModelProperty("性别")
	private Integer sex;

	@ApiModelProperty("性别名称")
	private String sexName;

	@ApiModelProperty("证件号码")
	private String idNo;

	@ApiModelProperty("手机号区号")
	private String phoneArea;

	@ApiModelProperty("手机号")
	private String phoneNumber;

	@ApiModelProperty(value = "风险承受程度:[6=非常进取型 5=进取型 4=中度进取型 3=平稳型 2=中度保守型 1=保守型]")
	private Integer acceptRisk;

	@ApiModelProperty("风险等级到期时间")
	private Date expiryDate;

	@ApiModelProperty("风险承受程度名称")
	private String acceptRiskName;

	@ApiModelProperty("开户状态名称")
	private String applicationStatusName;

	@ApiModelProperty("手机区号+手机号码")
	private String phoneAreaNumber;

	@ApiModelProperty("开户客户来源渠道ID")
	private String sourceChannelId;

	@ApiModelProperty("开户客户来源渠道名称")
	private String sourceChannelName;

	@ApiModelProperty("审核人")
	private String nickName;

	@ApiModelProperty("开户方式")
	private Integer openAccountAccessWay;

	@ApiModelProperty("开户方式名称")
	private String openAccountAccessWayName;

	@ApiModelProperty("aml审核key")
	private String refKey;

	@ApiModelProperty("黑名单")
	private Integer blacklist;

	@ApiModelProperty("操作权限")
	private Integer dealPermissions;

	@ApiModelProperty("证件类型")
	private Integer idKind;

	@ApiModelProperty("证件类型名称")
	private String idKindName;

	@ApiModelProperty("邀请码")
	private String aeCode;

	@ApiModelProperty("交易账号")
	private String clientId;

	@ApiModelProperty("客户ID")
	private String userId;
}
