package com.minigod.zero.bpmn.module.margincredit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName IncreaseCreditLimitApplication.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月09日 17:24:00
 */

/**
 * 信用额度审批信息表
 */
@ApiModel(description = "信用额度审批信息表")
@Data
@TableName(value = "increase_credit_limit_application")
public class IncreaseCreditLimitApplicationEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@TableField(value = "application_id")
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;

	/**
	 * 申请标题
	 */
	@TableField(value = "application_title")
	@ApiModelProperty(value = "申请标题")
	private String applicationTitle;

	/**
	 * 当前节点名称
	 */
	@TableField(value = "current_node")
	@ApiModelProperty(value = "当前节点名称")
	private String currentNode;

	/**
	 * 最后审核人
	 */
	@TableField(value = "last_approval_user")
	@ApiModelProperty(value = "最后审核人")
	private String lastApprovalUser;

	/**
	 * 最后审批时间
	 */
	@TableField(value = "last_approval_time")
	@ApiModelProperty(value = "最后审批时间")
	private Date lastApprovalTime;

	/**
	 * 最后审核意见
	 */
	@TableField(value = "approval_opinion")
	@ApiModelProperty(value = "最后审核意见")
	private String approvalOpinion;

	/**
	 * 流程发起时间
	 */
	@TableField(value = "start_time")
	@ApiModelProperty(value = "流程发起时间")
	private Date startTime;

	/**
	 * 备注
	 */
	@TableField(value = "remark")
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 预约申请状态
	 */
	@TableField(value = "application_status")
	@ApiModelProperty(value = "预约申请状态")
	private Integer applicationStatus;

	/**
	 * 当前处理人
	 */
	@TableField(value = "assign_drafter")
	@ApiModelProperty(value = "当前处理人")
	private String assignDrafter;

	/**
	 * 流程节点流转轨迹
	 */
	@TableField(value = "flow_path")
	@ApiModelProperty(value = "流程节点流转轨迹")
	private String flowPath;

	/**
	 * 0-未加急 1-已加急
	 */
	@TableField(value = "fire_aid")
	@ApiModelProperty(value = "0-未加急 1-已加急")
	private Integer fireAid;

	/**
	 * 流程实例ID
	 */
	@TableField(value = "instance_id")
	@ApiModelProperty(value = "流程实例ID")
	private String instanceId;

	/**
	 * 流程定义ID
	 */
	@TableField(value = "definition_id")
	@ApiModelProperty(value = "流程定义ID")
	private String definitionId;

	/**
	 * 流程实例ID
	 */
	@TableField(value = "process_instance_id")
	@ApiModelProperty(value = "流程实例ID")
	private String processInstanceId;

	/**
	 * 当前任务id
	 */
	@TableField(value = "task_id")
	@ApiModelProperty(value = "当前任务id")
	private String taskId;

	/**
	 * 部署id
	 */
	@TableField(value = "deploy_id")
	@ApiModelProperty(value = "部署id")
	private String deployId;

}
