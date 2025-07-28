package com.minigod.zero.bpmn.module.account.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 开户退回给客户信息
 *
 * @author eric
 * @since 2024-07-10 19:47:15
 */
@Data
public class AccountBackInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	@ApiModelProperty(value = "流程实例ID")
	private String instanceId;
	@ApiModelProperty(value = "流程定义ID")
	private String definitionId;
	@ApiModelProperty(value = "处理流程实例ID")
	private String processInstanceId;
	@ApiModelProperty(value = "任务ID")
	private String taskId;
	@ApiModelProperty(value = "客户号")
	private Long userId;
	@ApiModelProperty(value = "开户方式")
	private String accessWay;
	@ApiModelProperty(value = "证件类型")
	private String idKind;
	@ApiModelProperty(value = "证件号码")
	private String idNo;
	@ApiModelProperty(value = "客户英文名")
	private String clientNameSpell;
	@ApiModelProperty(value = "客户中文名")
	private String clientName;
	@ApiModelProperty(value = "英文名字")
	private String givenNameSpell;
	@ApiModelProperty(value = "英文姓")
	private String familyNameSpell;
	@ApiModelProperty(value = "性别")
	private String sex;
	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;
	@ApiModelProperty(value = "手机号码区号")
	private String phoneArea;
	@ApiModelProperty("手机区号+手机号码")
	private String phoneAreaNumber;
	@ApiModelProperty(value = "状态")
	private String status;
	@ApiModelProperty(value = "开户时间")
	private Date openAccountTime;
	@ApiModelProperty(value = "预约时间")
	private Date applicationTime;
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
