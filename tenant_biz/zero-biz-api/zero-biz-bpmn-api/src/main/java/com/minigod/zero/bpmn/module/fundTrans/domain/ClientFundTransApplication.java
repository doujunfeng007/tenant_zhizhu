package com.minigod.zero.bpmn.module.fundTrans.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
*@ClassName: ClientFundTransApplication
*@Description: ${description}
*@Author chenyu
*@Date 2024/12/11
*@Version 1.0
*
*/

/**
 * 资金调拨申请流程表
 */
@Data
@ApiModel(description = "资金调拨申请流程表")
public class ClientFundTransApplication implements Serializable {
    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    private Long id;

    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    private String applicationId;

    /**
     * 申请标题
     */
    @ApiModelProperty(value = "申请标题")
    private String applicationTitle;

    /**
     * 10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败
     */
    @ApiModelProperty(value = " 10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败")
    private Integer status;

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
     * 最后审核意见
     */
    @ApiModelProperty(value = "最后审核意见")
    private String approvalOpinion;

    /**
     * 最后审核时间
     */
    @ApiModelProperty(value = "最后审核时间")
    private Date approvalTime;

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    @ApiModelProperty(value = "调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]")
    private Integer callbackStatus;

    /**
     * 预约申请状态
     */
    @ApiModelProperty(value = "预约申请状态")
    private Integer applicationStatus;

    /**
     * 是否流程退回[0-否 1-是]
     */
    @ApiModelProperty(value = "是否流程退回[0-否 1-是]")
    private Boolean isBack;

    /**
     * 指定任务处理人
     */
    @ApiModelProperty(value = "指定任务处理人")
    private String assignDrafter;

    @ApiModelProperty(value = "")
    private String flowPath;

    /**
     * 加急处理[0-未加急 1-已加急]
     */
    @ApiModelProperty(value = "加急处理[0-未加急 1-已加急]")
    private Boolean fireAid;

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

    @ApiModelProperty(value = "")
    private String processInstanceId;

    @ApiModelProperty(value = "")
    private String taskId;

    @ApiModelProperty(value = "")
    private String deployId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createUser;

    @ApiModelProperty(value = "")
    private Date updateTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Long updateUser;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "")
    private String createDept;

    @ApiModelProperty(value = "")
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

}
