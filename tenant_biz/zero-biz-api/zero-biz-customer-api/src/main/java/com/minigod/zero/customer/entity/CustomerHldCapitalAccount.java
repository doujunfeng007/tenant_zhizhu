package com.minigod.zero.customer.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 活利得子账户信息表
 * @TableName customer_hld_capital_account
 */
public class CustomerHldCapitalAccount implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 交易账户
     */
    private String tradeAccount;

    /**
     * 子账户
     */
    private String subAccount;

    /**
     * 是否默认账号：0-不是 1-是
     */
    private Integer isMaster;

    /**
     * 账户状态：[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]
     */
    private Integer accountStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 账户类型[0=现金账户 M=保证金账户]
     */
    private String accountType;

    /**
     *
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 交易账户
     */
    public String getTradeAccount() {
        return tradeAccount;
    }

    /**
     * 交易账户
     */
    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    /**
     * 子账户
     */
    public String getSubAccount() {
        return subAccount;
    }

    /**
     * 子账户
     */
    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    /**
     * 是否默认账号：0-不是 1-是
     */
    public Integer getIsMaster() {
        return isMaster;
    }

    /**
     * 是否默认账号：0-不是 1-是
     */
    public void setIsMaster(Integer isMaster) {
        this.isMaster = isMaster;
    }

    /**
     * 账户状态：[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]
     */
    public Integer getAccountStatus() {
        return accountStatus;
    }

    /**
     * 账户状态：[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]
     */
    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
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
     * 账户类型[0=现金账户 M=保证金账户]
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * 账户类型[0=现金账户 M=保证金账户]
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
        CustomerHldCapitalAccount other = (CustomerHldCapitalAccount) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTradeAccount() == null ? other.getTradeAccount() == null : this.getTradeAccount().equals(other.getTradeAccount()))
            && (this.getSubAccount() == null ? other.getSubAccount() == null : this.getSubAccount().equals(other.getSubAccount()))
            && (this.getIsMaster() == null ? other.getIsMaster() == null : this.getIsMaster().equals(other.getIsMaster()))
            && (this.getAccountStatus() == null ? other.getAccountStatus() == null : this.getAccountStatus().equals(other.getAccountStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTradeAccount() == null) ? 0 : getTradeAccount().hashCode());
        result = prime * result + ((getSubAccount() == null) ? 0 : getSubAccount().hashCode());
        result = prime * result + ((getIsMaster() == null) ? 0 : getIsMaster().hashCode());
        result = prime * result + ((getAccountStatus() == null) ? 0 : getAccountStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
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
        sb.append(", tradeAccount=").append(tradeAccount);
        sb.append(", subAccount=").append(subAccount);
        sb.append(", isMaster=").append(isMaster);
        sb.append(", accountStatus=").append(accountStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", accountType=").append(accountType);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
