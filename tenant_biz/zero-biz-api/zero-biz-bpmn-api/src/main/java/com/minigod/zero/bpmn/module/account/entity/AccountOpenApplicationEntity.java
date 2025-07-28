package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;


/**
 *  客户开户申请表
 *
 * @author Chill
 */
@Data
@TableName("customer_account_open_application")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountOpenApplication对象", description = "")
public class AccountOpenApplicationEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * 申请标题
	 */
	@ApiModelProperty(value = "申请标题")
	private String applicationTitle;
	/**
	 * 当前节点名称
	 */
	@ApiModelProperty(value = "当前节点名称")
	private String currentNode;
	/**
	 * 审核结果[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
	 */
	@ApiModelProperty(value = "审核结果[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]")
	private Integer approveResult;
	/**
	 * 当前开户步奏 [enum OpenAccountStep]
	 */
	@ApiModelProperty(value = "当前开户步奏 [enum OpenAccountStep]")
	private Integer currentAccountOpenStep;
	/**
	 * 当前开户步奏处理状态[enum CommonProcessStatus]
	 */
	@ApiModelProperty(value = "当前开户步奏处理状态[enum CommonProcessStatus]")
	private Integer currentAccountOpenStepStatus;
	/**
	 * 开户结果状态[enum CommonProcessStatus]
	 */
	@ApiModelProperty(value = "开户结果状态[enum CommonProcessStatus]")
	private Integer accountOpenResultStatus;
	/**
	 * 最后审核人
	 */
	@ApiModelProperty(value = "最后审核人")
	private String lastApprovalUser;

	@ApiModelProperty(value = "最后审核时间")
	private Date lastApprovalTime;
	/**
	 * 最后审核意见
	 */
	@ApiModelProperty(value = "最后审核意见")
	private String approvalOpinion;
	/**
	 * 审核不通过的图片，JSON[{"imageLocation":1,"imageLocationType":1},{"imageLocation":2,"imageLocationType":2}]
	 */
	@ApiModelProperty(value = "审核不通过的图片，JSON[{\"imageLocation\":1,\"imageLocationType\":1},{\"imageLocation\":2,\"imageLocationType\":2}]")
	private String errorImages;
	/**
	 * 具体文本资料错误类型，JSON[1,2,3]
	 */
	@ApiModelProperty(value = "具体文本资料错误类型，JSON[1,2,3]")
	private String errorContentTypes;
	/**
	 * 开户结果回调结果状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
	 */
	@ApiModelProperty(value = "开户结果回调结果状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]")
	private Integer callbackStatus;
	/**
	 * 业务流程单据编号
	 */
	@ApiModelProperty(value = "业务流程单据编号")
	private String code;
	/**
	 * 流程发起人ID
	 */
	@ApiModelProperty(value = "流程发起人ID")
	private String startUserId;
	/**
	 * 流程发起时间
	 */
	@ApiModelProperty(value = "流程发起时间")
	private Date startTime;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 流程审批结果[1-同意 2-不同意 3-审批中]
	 */
	@ApiModelProperty(value = "流程审批结果[1-同意 2-不同意 3-审批中]")
	private String actResult;
	/**
	 * 预约申请状态[0-未知 1-初审中 2-复审中 3-终审中 4-预批成功 5-预批失败 6-已开户 7-已退回 8-已终止 9-已拒绝 10-已拒绝(加入黑名单) 11-预批中]
	 */
	@ApiModelProperty(value = "预约申请状态[0-未知 1-初审中 2-复审中 3-终审中 4-预批成功 5-预批失败 6-已开户 7-已退回 8-已终止 9-已拒绝 10-已拒绝(加入黑名单) 11-预批中]")
	private Integer applicationStatus;
	/**
	 * 是否流程退回[0-否 1-是]
	 */
	@ApiModelProperty(value = "是否流程退回[0-否 1-是]")
	private Integer isBack;
	/**
	 * 指定任务处理人
	 */
	@ApiModelProperty(value = "指定任务处理人")
	private String assignDrafter;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String flowPath;
	/**
	 * 见证人
	 */
	@ApiModelProperty(value = "见证人")
	private String witnessUser;
	/**
	 * 见证人类型
	 */
	@ApiModelProperty(value = "见证人类型")
	private String witnessesType;
	/**
	 * 牌照号码
	 */
	@ApiModelProperty(value = "牌照号码")
	private String licenseNumber;
	/**
	 * 提交审批人
	 */
	@ApiModelProperty(value = "提交审批人")
	private String submitApprovalUser;
	/**
	 * 加急处理[0-未加急 1-已加急]
	 */
	@ApiModelProperty(value = "加急处理[0-未加急 1-已加急]")
	private Integer fireAid;
	/**
	 * 其他退回理由描述
	 */
	@ApiModelProperty(value = "其他退回理由描述")
	private String otherReasons;
	/**
	 * 是否已经导入柜台[0、未导入  1、已导入]
	 */
	@ApiModelProperty(value = "是否已经导入柜台[0、未导入  1、已导入]")
	private Integer isExpCounter;
	/**
	 * 流程实例ID
	 */
	@ApiModelProperty(value = "流程实例ID")
	private String instanceId;
	/**
	 * 流程定义ID
	 */
	@ApiModelProperty(value = "流程定义ID")
	private String definitionId;
	/**
	 * 处理流程实例ID
	 */
	@ApiModelProperty(value = "处理流程实例ID")
	private String processInstanceId;
	/**
	 * 任务ID
	 */
	@ApiModelProperty(value = "任务ID")
	private String taskId;
	/**
	 * 部署ID
	 */
	@ApiModelProperty(value = "部署ID")
	private String deployId;
	/**
	 * aml审核key
	 */
	@ApiModelProperty(value = "aml审核key")
	private String refKey;
	/**
	 * 黑名单
	 */
	@ApiModelProperty(value = "黑名单")
	private Integer blacklist;

}
