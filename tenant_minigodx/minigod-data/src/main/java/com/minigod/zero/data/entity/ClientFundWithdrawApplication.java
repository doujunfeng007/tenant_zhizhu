package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户出金申请流程信息
 * @TableName client_fund_withdraw_application
 */
public class ClientFundWithdrawApplication implements Serializable {
    /**
     * 自增ID
     */
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
     *  10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败
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
     * 预约申请状态
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     *
     */
    private String createDept;

    /**
     *
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 流水号
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * 流水号
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * 申请标题
     */
    public String getApplicationTitle() {
        return applicationTitle;
    }

    /**
     * 申请标题
     */
    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    /**
     *  10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *  10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 当前节点名称
     */
    public String getCurrentNode() {
        return currentNode;
    }

    /**
     * 当前节点名称
     */
    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    /**
     * 最后审核人
     */
    public String getLastApprovalUser() {
        return lastApprovalUser;
    }

    /**
     * 最后审核人
     */
    public void setLastApprovalUser(String lastApprovalUser) {
        this.lastApprovalUser = lastApprovalUser;
    }

    /**
     * 最后审核意见
     */
    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    /**
     * 最后审核意见
     */
    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

    /**
     * 最后审核时间
     */
    public Date getApprovalTime() {
        return approvalTime;
    }

    /**
     * 最后审核时间
     */
    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

    /**
     * 预约申请状态
     */
    public Integer getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * 预约申请状态
     */
    public void setApplicationStatus(Integer applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    /**
     * 是否流程退回[0-否 1-是]
     */
    public Integer getIsBack() {
        return isBack;
    }

    /**
     * 是否流程退回[0-否 1-是]
     */
    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
    }

    /**
     * 指定任务处理人
     */
    public String getAssignDrafter() {
        return assignDrafter;
    }

    /**
     * 指定任务处理人
     */
    public void setAssignDrafter(String assignDrafter) {
        this.assignDrafter = assignDrafter;
    }

    /**
     *
     */
    public String getFlowPath() {
        return flowPath;
    }

    /**
     *
     */
    public void setFlowPath(String flowPath) {
        this.flowPath = flowPath;
    }

    /**
     * 加急处理[0-未加急 1-已加急]
     */
    public Integer getFireAid() {
        return fireAid;
    }

    /**
     * 加急处理[0-未加急 1-已加急]
     */
    public void setFireAid(Integer fireAid) {
        this.fireAid = fireAid;
    }

    /**
     * 流程实例ID
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * 流程实例ID
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * 流程定义ID
     */
    public String getDefinitionId() {
        return definitionId;
    }

    /**
     * 流程定义ID
     */
    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    /**
     *
     */
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     *
     */
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     *
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     *
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     *
     */
    public String getDeployId() {
        return deployId;
    }

    /**
     *
     */
    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 修改人
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 修改人
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 租户ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户ID
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     *
     */
    public String getCreateDept() {
        return createDept;
    }

    /**
     *
     */
    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }

    /**
     *
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     *
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ClientFundWithdrawApplication other = (ClientFundWithdrawApplication) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getApplicationTitle() == null ? other.getApplicationTitle() == null : this.getApplicationTitle().equals(other.getApplicationTitle()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCurrentNode() == null ? other.getCurrentNode() == null : this.getCurrentNode().equals(other.getCurrentNode()))
            && (this.getLastApprovalUser() == null ? other.getLastApprovalUser() == null : this.getLastApprovalUser().equals(other.getLastApprovalUser()))
            && (this.getApprovalOpinion() == null ? other.getApprovalOpinion() == null : this.getApprovalOpinion().equals(other.getApprovalOpinion()))
            && (this.getApprovalTime() == null ? other.getApprovalTime() == null : this.getApprovalTime().equals(other.getApprovalTime()))
            && (this.getCallbackStatus() == null ? other.getCallbackStatus() == null : this.getCallbackStatus().equals(other.getCallbackStatus()))
            && (this.getApplicationStatus() == null ? other.getApplicationStatus() == null : this.getApplicationStatus().equals(other.getApplicationStatus()))
            && (this.getIsBack() == null ? other.getIsBack() == null : this.getIsBack().equals(other.getIsBack()))
            && (this.getAssignDrafter() == null ? other.getAssignDrafter() == null : this.getAssignDrafter().equals(other.getAssignDrafter()))
            && (this.getFlowPath() == null ? other.getFlowPath() == null : this.getFlowPath().equals(other.getFlowPath()))
            && (this.getFireAid() == null ? other.getFireAid() == null : this.getFireAid().equals(other.getFireAid()))
            && (this.getInstanceId() == null ? other.getInstanceId() == null : this.getInstanceId().equals(other.getInstanceId()))
            && (this.getDefinitionId() == null ? other.getDefinitionId() == null : this.getDefinitionId().equals(other.getDefinitionId()))
            && (this.getProcessInstanceId() == null ? other.getProcessInstanceId() == null : this.getProcessInstanceId().equals(other.getProcessInstanceId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getDeployId() == null ? other.getDeployId() == null : this.getDeployId().equals(other.getDeployId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getCreateDept() == null ? other.getCreateDept() == null : this.getCreateDept().equals(other.getCreateDept()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getApplicationTitle() == null) ? 0 : getApplicationTitle().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCurrentNode() == null) ? 0 : getCurrentNode().hashCode());
        result = prime * result + ((getLastApprovalUser() == null) ? 0 : getLastApprovalUser().hashCode());
        result = prime * result + ((getApprovalOpinion() == null) ? 0 : getApprovalOpinion().hashCode());
        result = prime * result + ((getApprovalTime() == null) ? 0 : getApprovalTime().hashCode());
        result = prime * result + ((getCallbackStatus() == null) ? 0 : getCallbackStatus().hashCode());
        result = prime * result + ((getApplicationStatus() == null) ? 0 : getApplicationStatus().hashCode());
        result = prime * result + ((getIsBack() == null) ? 0 : getIsBack().hashCode());
        result = prime * result + ((getAssignDrafter() == null) ? 0 : getAssignDrafter().hashCode());
        result = prime * result + ((getFlowPath() == null) ? 0 : getFlowPath().hashCode());
        result = prime * result + ((getFireAid() == null) ? 0 : getFireAid().hashCode());
        result = prime * result + ((getInstanceId() == null) ? 0 : getInstanceId().hashCode());
        result = prime * result + ((getDefinitionId() == null) ? 0 : getDefinitionId().hashCode());
        result = prime * result + ((getProcessInstanceId() == null) ? 0 : getProcessInstanceId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getDeployId() == null) ? 0 : getDeployId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getCreateDept() == null) ? 0 : getCreateDept().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", applicationTitle=").append(applicationTitle);
        sb.append(", status=").append(status);
        sb.append(", currentNode=").append(currentNode);
        sb.append(", lastApprovalUser=").append(lastApprovalUser);
        sb.append(", approvalOpinion=").append(approvalOpinion);
        sb.append(", approvalTime=").append(approvalTime);
        sb.append(", callbackStatus=").append(callbackStatus);
        sb.append(", applicationStatus=").append(applicationStatus);
        sb.append(", isBack=").append(isBack);
        sb.append(", assignDrafter=").append(assignDrafter);
        sb.append(", flowPath=").append(flowPath);
        sb.append(", fireAid=").append(fireAid);
        sb.append(", instanceId=").append(instanceId);
        sb.append(", definitionId=").append(definitionId);
        sb.append(", processInstanceId=").append(processInstanceId);
        sb.append(", taskId=").append(taskId);
        sb.append(", deployId=").append(deployId);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", createDept=").append(createDept);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
