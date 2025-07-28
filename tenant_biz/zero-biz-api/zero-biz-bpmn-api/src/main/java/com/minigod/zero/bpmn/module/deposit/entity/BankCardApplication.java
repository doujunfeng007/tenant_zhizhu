package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
*@ClassName: BankCardApplication
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/11
*@Version 1.0
*
*/
/**
 * 银行卡审核申请表
 */
@ApiModel(description="银行卡审核申请表")
@Data
@TableName(value = "client_bank_card_application")
public class BankCardApplication implements Serializable {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="自增ID")
    @NotNull(message = "自增ID不能为null")
    private Long id;

    /**
     * 预约流水号
     */
    @TableField(value = "application_id")
    @ApiModelProperty(value="预约流水号")
    @Size(max = 32,message = "预约流水号最大长度要小于 32")
    @NotBlank(message = "预约流水号不能为空")
    private String applicationId;

    /**
     * 申请标题
     */
    @TableField(value = "application_title")
    @ApiModelProperty(value="申请标题")
    @Size(max = 50,message = "申请标题最大长度要小于 50")
    @NotBlank(message = "申请标题不能为空")
    private String applicationTitle;

    /**
     * 当前节点名称
     */
    @TableField(value = "current_node")
    @ApiModelProperty(value="当前节点名称")
    @Size(max = 50,message = "当前节点名称最大长度要小于 50")
    private String currentNode;

    /**
     * 最后审核人
     */
    @TableField(value = "last_approval_user")
    @ApiModelProperty(value="最后审核人")
    @Size(max = 32,message = "最后审核人最大长度要小于 32")
    private String lastApprovalUser;

    /**
     * 最后审批时间
     */
    @TableField(value = "last_approval_time")
    @ApiModelProperty(value="最后审批时间")
    private Date lastApprovalTime;

    /**
     * 最后审核意见
     */
    @TableField(value = "approval_opinion")
    @ApiModelProperty(value="最后审核意见")
    @Size(max = 5000,message = "最后审核意见最大长度要小于 5000")
    private String approvalOpinion;

    /**
     * 流程发起时间
     */
    @TableField(value = "start_time")
    @ApiModelProperty(value="流程发起时间")
    @NotNull(message = "流程发起时间不能为null")
    private Date startTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    @Size(max = 255,message = "备注最大长度要小于 255")
    private String remark;

    /**
     * 预约申请状态
     */
    @TableField(value = "application_status")
    @ApiModelProperty(value="预约申请状态")
    private Integer applicationStatus;

    /**
     * 当前处理人
     */
    @TableField(value = "assign_drafter")
    @ApiModelProperty(value="当前处理人")
    @Size(max = 32,message = "当前处理人最大长度要小于 32")
    private String assignDrafter;

    /**
     * 流程节点流转轨迹
     */
    @TableField(value = "flow_path")
    @ApiModelProperty(value="流程节点流转轨迹")
    private String flowPath;

    /**
     * 加急处理[0-未加急 1-已加急]
     */
    @TableField(value = "fire_aid")
    @ApiModelProperty(value="加急处理[0-未加急 1-已加急]")
    private Integer fireAid;

    /**
     * 流程实例ID
     */
    @TableField(value = "instance_id")
    @ApiModelProperty(value="流程实例ID")
    @Size(max = 64,message = "流程实例ID最大长度要小于 64")
    private String instanceId;

    /**
     * 流程定义ID
     */
    @TableField(value = "definition_id")
    @ApiModelProperty(value="流程定义ID")
    @Size(max = 64,message = "流程定义ID最大长度要小于 64")
    private String definitionId;

    /**
     * 流程实例ID
     */
    @TableField(value = "process_instance_id")
    @ApiModelProperty(value="流程实例ID")
    @Size(max = 255,message = "流程实例ID最大长度要小于 255")
    private String processInstanceId;

    /**
     * 当前任务ID
     */
    @TableField(value = "task_id")
    @ApiModelProperty(value="当前任务ID")
    @Size(max = 255,message = "当前任务ID最大长度要小于 255")
    private String taskId;

    /**
     * 部署ID
     */
    @TableField(value = "deploy_id")
    @ApiModelProperty(value="部署ID")
    @Size(max = 255,message = "部署ID最大长度要小于 255")
    private String deployId;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID")
    @Size(max = 30,message = "租户ID最大长度要小于 30")
    @NotBlank(message = "租户ID不能为空")
    private String tenantId;

    /**
     * 创建科室
     */
    @TableField(value = "create_dept")
    @ApiModelProperty(value="创建科室")
    private Long createDept;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态")
    private Integer status;

    /**
     * 创建人 ID
     */
    @TableField(value = "create_user")
    @ApiModelProperty(value="创建人 ID")
    private Long createUser;

    /**
     * 更新人 ID
     */
    @TableField(value = "update_user")
    @ApiModelProperty(value="更新人 ID")
    private Long updateUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_deleted")
    @ApiModelProperty(value="是否删除")
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}
