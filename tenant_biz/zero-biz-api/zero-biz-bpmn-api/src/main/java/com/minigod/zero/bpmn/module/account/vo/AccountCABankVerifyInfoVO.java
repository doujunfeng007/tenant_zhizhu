package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * CA银行四要素认证情况
 *
 * @author eric
 * @since 2024-07-10 19:39:05
 */
@Data
public class AccountCABankVerifyInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	@ApiModelProperty(value = "流程实例ID")
	private String instanceId;
	@ApiModelProperty(value = "流程定义ID")
	private String definitionId;
	@ApiModelProperty(value = "处理实例ID")
	private String processInstanceId;
	@ApiModelProperty(value = "任务ID")
	private String taskId;
	@ApiModelProperty(value = "用户号")
	private Long userId;
	@ApiModelProperty(value = "手机号码区号")
	private String phoneArea;
	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;
	@ApiModelProperty("手机区号+手机号码")
	private String phoneAreaNumber;
	@ApiModelProperty(value = "身份证号码")
	private String idNo;
	@ApiModelProperty(value = "银行卡号")
	private String bankCard;
	@ApiModelProperty(value = "客户中文名")
	private String clientName;
	@ApiModelProperty(value = "状态[0-不通过 1-通过]")
	private String verifyStatus;
	@ApiModelProperty(value = "验证次数")
	private Integer verifyCount;
	@ApiModelProperty(value = "验证时间")
	private String verifyTime;
	@ApiModelProperty(value = "验证结果")
	private String verifyReason;
	@ApiModelProperty(value = "用户中文名拼音")
	private String clientNameSpell;
	@ApiModelProperty(value = "英文名字")
	private String givenNameSpell;
	@ApiModelProperty(value = "英文姓")
	private String familyNameSpell;
	@ApiModelProperty(value = "性别[0-女 1-男]")
	private String sex;
}
