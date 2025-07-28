package com.minigod.zero.bpmn.module.stock.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 股票增开申请流程表 实体类
 * @author Administrator
 */
@Data
@TableName("customer_account_stock_application")
@ApiModel(value = "CustomerAccountStockApplication对象", description = "股票增开申请流程表")
public class CustomerAccountStockApplicationEntity extends AppEntity {


	@ApiModelProperty("预约流水号")
	private String applicationId;

	@ApiModelProperty("申请标题")
	private String applicationTitle;

	@ApiModelProperty("当前节点名称")
	private String currentNode;

	@ApiModelProperty("最后审核人")
	private String lastApprovalUser;

	@ApiModelProperty("最后审核时间")
	private Date lastApprovalTime;

	@ApiModelProperty("审核意见")
	private String approvalOpinion;

	@ApiModelProperty("流程发起时间")
	private Date startTime;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("预约申请状态")
	private Integer applicationStatus;

	@ApiModelProperty("当前处理人")
	private String assignDrafter;

	@ApiModelProperty("流程节点流转轨迹")
	private String flowPath;

	@ApiModelProperty("流程实例ID")
	private String instanceId;

	@ApiModelProperty("流程定义ID")
	private String definitionId;

	@ApiModelProperty("流程实例ID")
	private String processInstanceId;

	@ApiModelProperty("当前任务ID")
	private String taskId;

	@ApiModelProperty("部署ID")
	private String deployId;



	@ApiModelProperty("创建部门")
	private String createDept;

	@ApiModelProperty("创建人")
	private Long createUser;


	@ApiModelProperty("状态")
	private Integer status;

	@TableLogic
	@ApiModelProperty("是否已删除")
	private Integer isDeleted;

	@ApiModelProperty("租户id")
	private String tenantId;

	private static final long serialVersionUID = 1L;
}
