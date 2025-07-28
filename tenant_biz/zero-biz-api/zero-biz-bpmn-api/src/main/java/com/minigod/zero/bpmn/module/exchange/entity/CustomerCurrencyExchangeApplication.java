package com.minigod.zero.bpmn.module.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName CustomerCurrencyExchangeApplication.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月15日 18:50:00
 */

/**
 * 货币兑换申请流程表
 */
@ApiModel(description = "货币兑换申请流程表")
@Data
@TableName(value = "customer_currency_exchange_application")
public class CustomerCurrencyExchangeApplication extends BaseEntity {

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
	 * 最后审核时间
	 */
	@TableField(value = "last_approval_time")
	@ApiModelProperty(value = "最后审核时间")
	private Date lastApprovalTime;

	/**
	 * 审核意见
	 */
	@TableField(value = "approval_opinion")
	@ApiModelProperty(value = "审核意见")
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
	 * 当前任务ID
	 */
	@TableField(value = "task_id")
	@ApiModelProperty(value = "当前任务ID")
	private String taskId;

	/**
	 * 部署ID
	 */
	@TableField(value = "deploy_id")
	@ApiModelProperty(value = "部署ID")
	private String deployId;

	@TableField(value = "tenant_id")
	@ApiModelProperty(value = "租户id")
	private String tenantId;

}
