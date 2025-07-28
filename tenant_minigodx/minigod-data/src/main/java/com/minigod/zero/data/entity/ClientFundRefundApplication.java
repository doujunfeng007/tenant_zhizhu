package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户退款申请信息
 * @TableName client_fund_refund_application
 */
public class ClientFundRefundApplication implements Serializable {
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
     * 取款流水号
     */
    private String withdrawApplicationId;

    /**
     * 客户帐号
     */
    private String clientId;

    /**
     * 交易帐号
     */
    private String fundAccount;

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    private String ccy;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 手续费
     */
    private BigDecimal refundBankFee;

    /**
     * 退款净金额
     */
    private BigDecimal netRefundAmount;

    /**
     * 退款方式[0-无手续费 1-有手续费]
     */
    private Integer refundType;

    /**
     * 柜台业务处理步骤[1-资金冻结 2-资金取出 3-资金解冻 4-资金增加]
     */
    private Integer gtBusinessStep;

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    private Integer gtDealStatus;

    /**
     * 柜台处理时间
     */
    private Date gtDealDate;

    /**
     * 柜台响应编码
     */
    private String gtRtCode;

    /**
     * 柜台响应消息
     */
    private String gtMsg;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 业务流程状态[0-审核中 , 1退款中，2 退款成功，3退款失败 4 取消 5 拒绝]
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
     * 审核结果回调结果状态[0-新建 1-申请退款 2-退款完成 3-已拒绝 4-已取消]
     */
    private Integer callbackStatus;

    /**
     * 预约申请状态[0-开始 1-审核 2-退款 99-完成]
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
     * 租户 ID
     */
    private String tenantId;

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
     * 取款流水号
     */
    public String getWithdrawApplicationId() {
        return withdrawApplicationId;
    }

    /**
     * 取款流水号
     */
    public void setWithdrawApplicationId(String withdrawApplicationId) {
        this.withdrawApplicationId = withdrawApplicationId;
    }

    /**
     * 客户帐号
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 客户帐号
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 交易帐号
     */
    public String getFundAccount() {
        return fundAccount;
    }

    /**
     * 交易帐号
     */
    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    /**
     * 退款金额
     */
    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    /**
     * 退款金额
     */
    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**
     * 手续费
     */
    public BigDecimal getRefundBankFee() {
        return refundBankFee;
    }

    /**
     * 手续费
     */
    public void setRefundBankFee(BigDecimal refundBankFee) {
        this.refundBankFee = refundBankFee;
    }

    /**
     * 退款净金额
     */
    public BigDecimal getNetRefundAmount() {
        return netRefundAmount;
    }

    /**
     * 退款净金额
     */
    public void setNetRefundAmount(BigDecimal netRefundAmount) {
        this.netRefundAmount = netRefundAmount;
    }

    /**
     * 退款方式[0-无手续费 1-有手续费]
     */
    public Integer getRefundType() {
        return refundType;
    }

    /**
     * 退款方式[0-无手续费 1-有手续费]
     */
    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    /**
     * 柜台业务处理步骤[1-资金冻结 2-资金取出 3-资金解冻 4-资金增加]
     */
    public Integer getGtBusinessStep() {
        return gtBusinessStep;
    }

    /**
     * 柜台业务处理步骤[1-资金冻结 2-资金取出 3-资金解冻 4-资金增加]
     */
    public void setGtBusinessStep(Integer gtBusinessStep) {
        this.gtBusinessStep = gtBusinessStep;
    }

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    public Integer getGtDealStatus() {
        return gtDealStatus;
    }

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    public void setGtDealStatus(Integer gtDealStatus) {
        this.gtDealStatus = gtDealStatus;
    }

    /**
     * 柜台处理时间
     */
    public Date getGtDealDate() {
        return gtDealDate;
    }

    /**
     * 柜台处理时间
     */
    public void setGtDealDate(Date gtDealDate) {
        this.gtDealDate = gtDealDate;
    }

    /**
     * 柜台响应编码
     */
    public String getGtRtCode() {
        return gtRtCode;
    }

    /**
     * 柜台响应编码
     */
    public void setGtRtCode(String gtRtCode) {
        this.gtRtCode = gtRtCode;
    }

    /**
     * 柜台响应消息
     */
    public String getGtMsg() {
        return gtMsg;
    }

    /**
     * 柜台响应消息
     */
    public void setGtMsg(String gtMsg) {
        this.gtMsg = gtMsg;
    }

    /**
     * 退款原因
     */
    public String getRefundReason() {
        return refundReason;
    }

    /**
     * 退款原因
     */
    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    /**
     * 业务流程状态[0-审核中 , 1退款中，2 退款成功，3退款失败 4 取消 5 拒绝]
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 业务流程状态[0-审核中 , 1退款中，2 退款成功，3退款失败 4 取消 5 拒绝]
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
     * 审核结果回调结果状态[0-新建 1-申请退款 2-退款完成 3-已拒绝 4-已取消]
     */
    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    /**
     * 审核结果回调结果状态[0-新建 1-申请退款 2-退款完成 3-已拒绝 4-已取消]
     */
    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

    /**
     * 预约申请状态[0-开始 1-审核 2-退款 99-完成]
     */
    public Integer getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * 预约申请状态[0-开始 1-审核 2-退款 99-完成]
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
     * 租户 ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户 ID
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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
        ClientFundRefundApplication other = (ClientFundRefundApplication) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getApplicationTitle() == null ? other.getApplicationTitle() == null : this.getApplicationTitle().equals(other.getApplicationTitle()))
            && (this.getWithdrawApplicationId() == null ? other.getWithdrawApplicationId() == null : this.getWithdrawApplicationId().equals(other.getWithdrawApplicationId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getFundAccount() == null ? other.getFundAccount() == null : this.getFundAccount().equals(other.getFundAccount()))
            && (this.getCcy() == null ? other.getCcy() == null : this.getCcy().equals(other.getCcy()))
            && (this.getRefundAmount() == null ? other.getRefundAmount() == null : this.getRefundAmount().equals(other.getRefundAmount()))
            && (this.getRefundBankFee() == null ? other.getRefundBankFee() == null : this.getRefundBankFee().equals(other.getRefundBankFee()))
            && (this.getNetRefundAmount() == null ? other.getNetRefundAmount() == null : this.getNetRefundAmount().equals(other.getNetRefundAmount()))
            && (this.getRefundType() == null ? other.getRefundType() == null : this.getRefundType().equals(other.getRefundType()))
            && (this.getGtBusinessStep() == null ? other.getGtBusinessStep() == null : this.getGtBusinessStep().equals(other.getGtBusinessStep()))
            && (this.getGtDealStatus() == null ? other.getGtDealStatus() == null : this.getGtDealStatus().equals(other.getGtDealStatus()))
            && (this.getGtDealDate() == null ? other.getGtDealDate() == null : this.getGtDealDate().equals(other.getGtDealDate()))
            && (this.getGtRtCode() == null ? other.getGtRtCode() == null : this.getGtRtCode().equals(other.getGtRtCode()))
            && (this.getGtMsg() == null ? other.getGtMsg() == null : this.getGtMsg().equals(other.getGtMsg()))
            && (this.getRefundReason() == null ? other.getRefundReason() == null : this.getRefundReason().equals(other.getRefundReason()))
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
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getApplicationTitle() == null) ? 0 : getApplicationTitle().hashCode());
        result = prime * result + ((getWithdrawApplicationId() == null) ? 0 : getWithdrawApplicationId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getFundAccount() == null) ? 0 : getFundAccount().hashCode());
        result = prime * result + ((getCcy() == null) ? 0 : getCcy().hashCode());
        result = prime * result + ((getRefundAmount() == null) ? 0 : getRefundAmount().hashCode());
        result = prime * result + ((getRefundBankFee() == null) ? 0 : getRefundBankFee().hashCode());
        result = prime * result + ((getNetRefundAmount() == null) ? 0 : getNetRefundAmount().hashCode());
        result = prime * result + ((getRefundType() == null) ? 0 : getRefundType().hashCode());
        result = prime * result + ((getGtBusinessStep() == null) ? 0 : getGtBusinessStep().hashCode());
        result = prime * result + ((getGtDealStatus() == null) ? 0 : getGtDealStatus().hashCode());
        result = prime * result + ((getGtDealDate() == null) ? 0 : getGtDealDate().hashCode());
        result = prime * result + ((getGtRtCode() == null) ? 0 : getGtRtCode().hashCode());
        result = prime * result + ((getGtMsg() == null) ? 0 : getGtMsg().hashCode());
        result = prime * result + ((getRefundReason() == null) ? 0 : getRefundReason().hashCode());
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
        sb.append(", withdrawApplicationId=").append(withdrawApplicationId);
        sb.append(", clientId=").append(clientId);
        sb.append(", fundAccount=").append(fundAccount);
        sb.append(", ccy=").append(ccy);
        sb.append(", refundAmount=").append(refundAmount);
        sb.append(", refundBankFee=").append(refundBankFee);
        sb.append(", netRefundAmount=").append(netRefundAmount);
        sb.append(", refundType=").append(refundType);
        sb.append(", gtBusinessStep=").append(gtBusinessStep);
        sb.append(", gtDealStatus=").append(gtDealStatus);
        sb.append(", gtDealDate=").append(gtDealDate);
        sb.append(", gtRtCode=").append(gtRtCode);
        sb.append(", gtMsg=").append(gtMsg);
        sb.append(", refundReason=").append(refundReason);
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
