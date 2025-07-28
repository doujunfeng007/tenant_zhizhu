package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户理财账户表
 * @TableName customer_financing_account
 */
public class CustomerFinancingAccountEntity implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 客户号（个人/授权人）
     */
    private Long custId;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 理财账户id
     */
    private String accountId;

    /**
     * 理财账户交易密码
     */
    private String password;

    /**
     * 账户类型，0个人户，1机构户
     */
    private Integer accountType;

    /**
     * 0正常已激活，4未开户，5预批户
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 在途金额
     */
    private BigDecimal transitedAmount;

    /**
     *
     */
    private Integer isDeleted;

    /**
     * 修改交易密码时间
     */
    private Date updatePwdTime;

    /**
     * 理财账户激活时间
     */
    private Date activationTime;

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 客户号（个人/授权人）
     */
    public Long getCustId() {
        return custId;
    }

    /**
     * 客户号（个人/授权人）
     */
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    /**
     * 租户id
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户id
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 理财账户id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 理财账户id
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 理财账户交易密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 理财账户交易密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 账户类型，0个人户，1机构户
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * 账户类型，0个人户，1机构户
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    /**
     * 0正常已激活，4未开户，5预批户
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0正常已激活，4未开户，5预批户
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 在途金额
     */
    public BigDecimal getTransitedAmount() {
        return transitedAmount;
    }

    /**
     * 在途金额
     */
    public void setTransitedAmount(BigDecimal transitedAmount) {
        this.transitedAmount = transitedAmount;
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

    /**
     * 修改交易密码时间
     */
    public Date getUpdatePwdTime() {
        return updatePwdTime;
    }

    /**
     * 修改交易密码时间
     */
    public void setUpdatePwdTime(Date updatePwdTime) {
        this.updatePwdTime = updatePwdTime;
    }

    /**
     * 理财账户激活时间
     */
    public Date getActivationTime() {
        return activationTime;
    }

    /**
     * 理财账户激活时间
     */
    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
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
        CustomerFinancingAccountEntity other = (CustomerFinancingAccountEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustId() == null ? other.getCustId() == null : this.getCustId().equals(other.getCustId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getTransitedAmount() == null ? other.getTransitedAmount() == null : this.getTransitedAmount().equals(other.getTransitedAmount()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getUpdatePwdTime() == null ? other.getUpdatePwdTime() == null : this.getUpdatePwdTime().equals(other.getUpdatePwdTime()))
            && (this.getActivationTime() == null ? other.getActivationTime() == null : this.getActivationTime().equals(other.getActivationTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustId() == null) ? 0 : getCustId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getTransitedAmount() == null) ? 0 : getTransitedAmount().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getUpdatePwdTime() == null) ? 0 : getUpdatePwdTime().hashCode());
        result = prime * result + ((getActivationTime() == null) ? 0 : getActivationTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", custId=").append(custId);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", accountId=").append(accountId);
        sb.append(", password=").append(password);
        sb.append(", accountType=").append(accountType);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", transitedAmount=").append(transitedAmount);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", updatePwdTime=").append(updatePwdTime);
        sb.append(", activationTime=").append(activationTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
