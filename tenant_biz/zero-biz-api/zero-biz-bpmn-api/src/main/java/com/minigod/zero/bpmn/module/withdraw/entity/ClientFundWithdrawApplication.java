package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 客户出金申请流程信息对象 client_fund_withdraw_application
 *
 * @author chenyu
 * @title ClientFundWithdrawApplication
 * @date 2023-04-04 20:21
 * @description
 */
@Data
@TableName("client_fund_withdraw_application")
public class ClientFundWithdrawApplication  {

    /**
     * 自增ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 流水号
     */
    private String applicationId;
    /**
     * 申请标题
     */
    private String applicationTitle;
    /**
     * 业务流程状态[1=草稿 2=审批中 3=结束]
     */
    private Integer status;
    /**
     * 当前节点名称
     */
    private String currentNode;
    /**
     * 最后审核人
     */
    private String lastApprovalUser;
    /**
     * 最后审核意见
     */
    private String approvalOpinion;
    /**
     * 最后审核时间
     */
    private Date approvalTime;
    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    private Integer callbackStatus;
    /**
     * 预约申请状态[0-内部拒绝 1-已提交 2-已受理 3-已退回  4-已完成 5-已取消 6-已拒绝 7-已审核 8-申请退款 9-退款完成 13-已复核 11-DBS打款中 12-DBS打款失败]
     */
    private Integer applicationStatus;
    /**
     * 是否流程退回[0-否 1-是]
     */
    private Integer isBack;
    /**
     * 指定任务处理人
     */
    private String assignDrafter;
    /**
     *
     */
    private String flowPath;
    /**
     * 加急处理[0-未加急 1-已加急]
     */
    private Integer fireAid;
    /**
     * 流程实例ID
     */
    private String instanceId;
    /**
     * 流程定义ID
     */
    private String definitionId;
    /**
     *
     */
    private String processInstanceId;
    /**
     *
     */
    private String taskId;
    /**
     *
     */
    private String deployId;

    private String tenantId;
    private Long createUser;
    private Long updateUser;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
    private Long createDept;


}
