package com.minigod.zero.customer.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户资金账号信息表
 * @TableName customer_stock_capital_account
 */
public class CustomerStockCapitalAccountEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 交易账号
     */
    private String tradeAccount;

    /**
     * 资金账号
     */
    private String capitalAccount;

    /**
     * 账户类型[0=现金账户 M=保证金账户]
     */
    private String accountType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否是主账号：0-不是 1-是
     */
    private Integer isMaster;

    /**
     * 账号状态：0-无效 1-有效
     */
    private Integer status;

    /**
     * 是否已删除：1-已删除
     */
    private Integer isDeleted;

    /**
     * 可取金额
     */
    private BigDecimal availableAmount;

    /**
     * 冻结金额
     */
    private BigDecimal freezeAmount;

    /**
     * 在途金额
     */
    private BigDecimal transitedAmount;

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
     * 交易账号
     */
    public String getTradeAccount() {
        return tradeAccount;
    }

    /**
     * 交易账号
     */
    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    /**
     * 资金账号
     */
    public String getCapitalAccount() {
        return capitalAccount;
    }

    /**
     * 资金账号
     */
    public void setCapitalAccount(String capitalAccount) {
        this.capitalAccount = capitalAccount;
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
     * 是否是主账号：0-不是 1-是
     */
    public Integer getIsMaster() {
        return isMaster;
    }

    /**
     * 是否是主账号：0-不是 1-是
     */
    public void setIsMaster(Integer isMaster) {
        this.isMaster = isMaster;
    }

    /**
     * 账号状态：0-无效 1-有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 账号状态：0-无效 1-有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 是否已删除：1-已删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否已删除：1-已删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 可取金额
     */
    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    /**
     * 可取金额
     */
    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    /**
     * 冻结金额
     */
    public BigDecimal getFreezeAmount() {
        return freezeAmount;
    }

    /**
     * 冻结金额
     */
    public void setFreezeAmount(BigDecimal freezeAmount) {
        this.freezeAmount = freezeAmount;
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
        CustomerStockCapitalAccountEntity other = (CustomerStockCapitalAccountEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTradeAccount() == null ? other.getTradeAccount() == null : this.getTradeAccount().equals(other.getTradeAccount()))
            && (this.getCapitalAccount() == null ? other.getCapitalAccount() == null : this.getCapitalAccount().equals(other.getCapitalAccount()))
            && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsMaster() == null ? other.getIsMaster() == null : this.getIsMaster().equals(other.getIsMaster()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getAvailableAmount() == null ? other.getAvailableAmount() == null : this.getAvailableAmount().equals(other.getAvailableAmount()))
            && (this.getFreezeAmount() == null ? other.getFreezeAmount() == null : this.getFreezeAmount().equals(other.getFreezeAmount()))
            && (this.getTransitedAmount() == null ? other.getTransitedAmount() == null : this.getTransitedAmount().equals(other.getTransitedAmount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTradeAccount() == null) ? 0 : getTradeAccount().hashCode());
        result = prime * result + ((getCapitalAccount() == null) ? 0 : getCapitalAccount().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getIsMaster() == null) ? 0 : getIsMaster().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAvailableAmount() == null) ? 0 : getAvailableAmount().hashCode());
        result = prime * result + ((getFreezeAmount() == null) ? 0 : getFreezeAmount().hashCode());
        result = prime * result + ((getTransitedAmount() == null) ? 0 : getTransitedAmount().hashCode());
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
        sb.append(", capitalAccount=").append(capitalAccount);
        sb.append(", accountType=").append(accountType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isMaster=").append(isMaster);
        sb.append(", status=").append(status);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", availableAmount=").append(availableAmount);
        sb.append(", freezeAmount=").append(freezeAmount);
        sb.append(", transitedAmount=").append(transitedAmount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
