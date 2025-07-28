package com.minigod.zero.bpmn.module.pi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 专业投资者FI申请记录 实体类
 *
 * @author eric
 * @since 2024-05-07 13:59:20
 */
@Data
@TableName("professional_investor_pi_application")
@ApiModel(value = "ProfessionalInvestorPIApplication对象", description = "专业投资者FI申请记录表")
public class ProfessionalInvestorPIApplicationEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "申请流水号")
	private String applicationId;

	/**
	 * 申请类型  1提交/办理   2撤销
	 */
	//@ApiModelProperty(value = "申请类型  1提交/办理   2撤销")
	//private Integer applicationType;

	/**
	 * 申请标题
	 */
	@ApiModelProperty(value = "申请标题")
	private String applicationTitle ;

	/**
	 * 当前节点名称
	 */
	@ApiModelProperty(value = "当前节点名称")
	private String currentNode;
	/**
	 * 最后审核人
	 */
	@ApiModelProperty(value = "最后审核人")
	private String lastApprovalUser;
	/**
	 * 最后审批时间
	 */
	@ApiModelProperty(value = "最后审批时间")
	private Date lastApprovalTime;
	/**
	 * 最后审核意见
	 */
	@ApiModelProperty(value = "最后审核意见")
	private String approvalOpinion;
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
	 * 预约申请状态
	 */
	@ApiModelProperty(value = "预约申请状态")
	private Integer applicationStatus;
	/**
	 * 当前处理人
	 */
	@ApiModelProperty(value = "当前处理人")
	private String assignDrafter;
	/**
	 * 流程节点流转轨迹
	 */
	@ApiModelProperty(value = "流程节点流转轨迹")
	private String flowPath;
	/**
	 * 加急处理[0-未加急 1-已加急]
	 */
	@ApiModelProperty(value = "加急处理[0-未加急 1-已加急]")
	private Integer fireAid;
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
	 * 流程实例ID
	 */
	@ApiModelProperty(value = "流程实例ID")
	private String processInstanceId;
	/**
	 * 当前任务ID
	 */
	@ApiModelProperty(value = "当前任务ID")
	private String taskId;
	/**
	 * 部署ID
	 */
	@ApiModelProperty(value = "部署ID")
	private String deployId;
}
