package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 客户入金申请表 实体类
 *
 * @author taro
 * @since 2024-02-29
 */
@Data
@TableName("client_fund_deposit_application")
@ApiModel(value = "FundDepositApplication对象", description = "客户入金申请表")
public class FundDepositApplicationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    private Long id;
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
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 创建科室
     */
    @ApiModelProperty(value = "创建科室")
    private Long createDept;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;
    /**
     * 创建人 ID
     */
    @ApiModelProperty(value = "创建人 ID")
    private Long createUser;
    /**
     * 更新人 ID
     */
    @ApiModelProperty(value = "更新人 ID")
    private Long updateUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Integer isDeleted;

}
