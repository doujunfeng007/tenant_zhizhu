package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户入金申请表
 * @TableName client_fund_deposit_application
 */
public class ClientFundDepositApplication implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 预约流水号
     */
    private String applicationId;

    /**
     * 申请标题
     */
    private String applicationTitle;

    /**
     * 当前节点名称
     */
    private String currentNode;

    /**
     * 最后审核人
     */
    private String lastApprovalUser;

    /**
     * 最后审批时间
     */
    private Date lastApprovalTime;

    /**
     * 最后审核意见
     */
    private String approvalOpinion;

    /**
     * 流程发起时间
     */
    private Date startTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 预约申请状态
     */
    private Integer applicationStatus;

    /**
     * 当前处理人
     */
    private String assignDrafter;

    /**
     * 流程节点流转轨迹
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
     * 流程实例ID
     */
    private String processInstanceId;

    /**
     * 当前任务ID
     */
    private String taskId;

    /**
     * 部署ID
     */
    private String deployId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 创建科室
     */
    private Long createDept;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建人 ID
     */
    private Long createUser;

    /**
     * 更新人 ID
     */
    private Long updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
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
     * 预约流水号
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * 预约流水号
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
     * 最后审批时间
     */
    public Date getLastApprovalTime() {
        return lastApprovalTime;
    }

    /**
     * 最后审批时间
     */
    public void setLastApprovalTime(Date lastApprovalTime) {
        this.lastApprovalTime = lastApprovalTime;
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
     * 流程发起时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 流程发起时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * 当前处理人
     */
    public String getAssignDrafter() {
        return assignDrafter;
    }

    /**
     * 当前处理人
     */
    public void setAssignDrafter(String assignDrafter) {
        this.assignDrafter = assignDrafter;
    }

    /**
     * 流程节点流转轨迹
     */
    public String getFlowPath() {
        return flowPath;
    }

    /**
     * 流程节点流转轨迹
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
     * 流程实例ID
     */
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * 流程实例ID
     */
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * 当前任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 当前任务ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 部署ID
     */
    public String getDeployId() {
        return deployId;
    }

    /**
     * 部署ID
     */
    public void setDeployId(String deployId) {
        this.deployId = deployId;
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
     * 创建科室
     */
    public Long getCreateDept() {
        return createDept;
    }

    /**
     * 创建科室
     */
    public void setCreateDept(Long createDept) {
        this.createDept = createDept;
    }

    /**
     * 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 创建人 ID
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 创建人 ID
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * 更新人 ID
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新人 ID
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
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
     * 是否删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除
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
        ClientFundDepositApplication other = (ClientFundDepositApplication) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getApplicationTitle() == null ? other.getApplicationTitle() == null : this.getApplicationTitle().equals(other.getApplicationTitle()))
            && (this.getCurrentNode() == null ? other.getCurrentNode() == null : this.getCurrentNode().equals(other.getCurrentNode()))
            && (this.getLastApprovalUser() == null ? other.getLastApprovalUser() == null : this.getLastApprovalUser().equals(other.getLastApprovalUser()))
            && (this.getLastApprovalTime() == null ? other.getLastApprovalTime() == null : this.getLastApprovalTime().equals(other.getLastApprovalTime()))
            && (this.getApprovalOpinion() == null ? other.getApprovalOpinion() == null : this.getApprovalOpinion().equals(other.getApprovalOpinion()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getApplicationStatus() == null ? other.getApplicationStatus() == null : this.getApplicationStatus().equals(other.getApplicationStatus()))
            && (this.getAssignDrafter() == null ? other.getAssignDrafter() == null : this.getAssignDrafter().equals(other.getAssignDrafter()))
            && (this.getFlowPath() == null ? other.getFlowPath() == null : this.getFlowPath().equals(other.getFlowPath()))
            && (this.getFireAid() == null ? other.getFireAid() == null : this.getFireAid().equals(other.getFireAid()))
            && (this.getInstanceId() == null ? other.getInstanceId() == null : this.getInstanceId().equals(other.getInstanceId()))
            && (this.getDefinitionId() == null ? other.getDefinitionId() == null : this.getDefinitionId().equals(other.getDefinitionId()))
            && (this.getProcessInstanceId() == null ? other.getProcessInstanceId() == null : this.getProcessInstanceId().equals(other.getProcessInstanceId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getDeployId() == null ? other.getDeployId() == null : this.getDeployId().equals(other.getDeployId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getCreateDept() == null ? other.getCreateDept() == null : this.getCreateDept().equals(other.getCreateDept()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getApplicationTitle() == null) ? 0 : getApplicationTitle().hashCode());
        result = prime * result + ((getCurrentNode() == null) ? 0 : getCurrentNode().hashCode());
        result = prime * result + ((getLastApprovalUser() == null) ? 0 : getLastApprovalUser().hashCode());
        result = prime * result + ((getLastApprovalTime() == null) ? 0 : getLastApprovalTime().hashCode());
        result = prime * result + ((getApprovalOpinion() == null) ? 0 : getApprovalOpinion().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getApplicationStatus() == null) ? 0 : getApplicationStatus().hashCode());
        result = prime * result + ((getAssignDrafter() == null) ? 0 : getAssignDrafter().hashCode());
        result = prime * result + ((getFlowPath() == null) ? 0 : getFlowPath().hashCode());
        result = prime * result + ((getFireAid() == null) ? 0 : getFireAid().hashCode());
        result = prime * result + ((getInstanceId() == null) ? 0 : getInstanceId().hashCode());
        result = prime * result + ((getDefinitionId() == null) ? 0 : getDefinitionId().hashCode());
        result = prime * result + ((getProcessInstanceId() == null) ? 0 : getProcessInstanceId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getDeployId() == null) ? 0 : getDeployId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getCreateDept() == null) ? 0 : getCreateDept().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
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
        sb.append(", currentNode=").append(currentNode);
        sb.append(", lastApprovalUser=").append(lastApprovalUser);
        sb.append(", lastApprovalTime=").append(lastApprovalTime);
        sb.append(", approvalOpinion=").append(approvalOpinion);
        sb.append(", startTime=").append(startTime);
        sb.append(", remark=").append(remark);
        sb.append(", applicationStatus=").append(applicationStatus);
        sb.append(", assignDrafter=").append(assignDrafter);
        sb.append(", flowPath=").append(flowPath);
        sb.append(", fireAid=").append(fireAid);
        sb.append(", instanceId=").append(instanceId);
        sb.append(", definitionId=").append(definitionId);
        sb.append(", processInstanceId=").append(processInstanceId);
        sb.append(", taskId=").append(taskId);
        sb.append(", deployId=").append(deployId);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", createDept=").append(createDept);
        sb.append(", status=").append(status);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
