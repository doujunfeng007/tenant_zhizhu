package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 理财账户金额流水
 * @TableName financing_account_amount_flows
 */
public class FinancingAccountAmountFlows implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 理财账户账号
     */
    private String accountId;

    /**
     * 操作前金额
     */
    private BigDecimal beforeAmount;

    /**
     * 操作金额
     */
    private BigDecimal amount;

    /**
     * 操作后金额
     */
    private BigDecimal afterAmount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 说明
     */
    private String remark;

    /**
     * 账户类型1现金可用账户，2现金冻结账户，3现金在途账户
     */
    private Integer type;

    /**
     * 参考枚举ThawingType
     */
    private Integer businessType;

    /**
     * 业务id
     */
    private Integer accountBusinessFlowId;

    /**
     * 1.可用存入2.可用扣除3.可用转冻结4.冻结扣除5.冻结转可用
     */
    private Integer operationType;

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 理财账户账号
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 理财账户账号
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 操作前金额
     */
    public BigDecimal getBeforeAmount() {
        return beforeAmount;
    }

    /**
     * 操作前金额
     */
    public void setBeforeAmount(BigDecimal beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    /**
     * 操作金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 操作金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 操作后金额
     */
    public BigDecimal getAfterAmount() {
        return afterAmount;
    }

    /**
     * 操作后金额
     */
    public void setAfterAmount(BigDecimal afterAmount) {
        this.afterAmount = afterAmount;
    }

    /**
     * 币种
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 币种
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 操作时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 操作时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 账户类型1现金可用账户，2现金冻结账户，3现金在途账户
     */
    public Integer getType() {
        return type;
    }

    /**
     * 账户类型1现金可用账户，2现金冻结账户，3现金在途账户
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 参考枚举ThawingType
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * 参考枚举ThawingType
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * 业务id
     */
    public Integer getAccountBusinessFlowId() {
        return accountBusinessFlowId;
    }

    /**
     * 业务id
     */
    public void setAccountBusinessFlowId(Integer accountBusinessFlowId) {
        this.accountBusinessFlowId = accountBusinessFlowId;
    }

    /**
     * 1.可用存入2.可用扣除3.可用转冻结4.冻结扣除5.冻结转可用
     */
    public Integer getOperationType() {
        return operationType;
    }

    /**
     * 1.可用存入2.可用扣除3.可用转冻结4.冻结扣除5.冻结转可用
     */
    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
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
        FinancingAccountAmountFlows other = (FinancingAccountAmountFlows) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getBeforeAmount() == null ? other.getBeforeAmount() == null : this.getBeforeAmount().equals(other.getBeforeAmount()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getAfterAmount() == null ? other.getAfterAmount() == null : this.getAfterAmount().equals(other.getAfterAmount()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getBusinessType() == null ? other.getBusinessType() == null : this.getBusinessType().equals(other.getBusinessType()))
            && (this.getAccountBusinessFlowId() == null ? other.getAccountBusinessFlowId() == null : this.getAccountBusinessFlowId().equals(other.getAccountBusinessFlowId()))
            && (this.getOperationType() == null ? other.getOperationType() == null : this.getOperationType().equals(other.getOperationType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getBeforeAmount() == null) ? 0 : getBeforeAmount().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getAfterAmount() == null) ? 0 : getAfterAmount().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getBusinessType() == null) ? 0 : getBusinessType().hashCode());
        result = prime * result + ((getAccountBusinessFlowId() == null) ? 0 : getAccountBusinessFlowId().hashCode());
        result = prime * result + ((getOperationType() == null) ? 0 : getOperationType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountId=").append(accountId);
        sb.append(", beforeAmount=").append(beforeAmount);
        sb.append(", amount=").append(amount);
        sb.append(", afterAmount=").append(afterAmount);
        sb.append(", currency=").append(currency);
        sb.append(", createTime=").append(createTime);
        sb.append(", remark=").append(remark);
        sb.append(", type=").append(type);
        sb.append(", businessType=").append(businessType);
        sb.append(", accountBusinessFlowId=").append(accountBusinessFlowId);
        sb.append(", operationType=").append(operationType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
