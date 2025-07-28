package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户入金申请信息表
 * @TableName client_fund_deposit_info
 */
public class ClientFundDepositInfo implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 流水号
     */
    private String applicationId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 中台入金记录ID
     */
    private Long bizId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 交易帐号
     */
    private String clientId;

    /**
     * 资金帐号
     */
    private String fundAccount;

    /**
     * SWIFT代码
     */
    private String swiftCode;

    /**
     * 银行卡类型[0-香港银行卡 1-大陆银行卡 2海外其他银行]
     */
    private Integer bankType;

    /**
     * 汇款方式[0-未知 1-网银汇款 2-支票汇款]
     */
    private Integer remittanceType;

    /**
     * 汇款银行
     */
    private String remittanceBankName;

    /**
     * 汇款账号
     */
    private String remittanceAccount;

    /**
     * 汇款账户名称
     */
    private String remittanceAccountName;

    /**
     * 汇款银行代码
     */
    private String remittanceBankCode;

    /**
     * 汇款银行机构号
     */
    private String remittanceBankId;

    /**
     * 收款银行
     */
    private String receivingBankName;

    /**
     * 收款银行代码
     */
    private String receivingBankCode;

    /**
     * 收款账号
     */
    private String receivingAccount;

    /**
     * 收款账户名称
     */
    private String receivingAccountName;

    /**
     * 联系地址
     */
    private String receivingAddress;

    /**
     * 申请金额
     */
    private BigDecimal depositBalance;

    /**
     * 申请时间
     */
    private Date applicationTime;

    /**
     * 币种代码[0-人民币 1-美元 2-港币]
     */
    private Integer moneyType;

    /**
     * 恒生银行编号
     */
    private String hsBankId;

    /**
     * 恒生银行帐户
     */
    private String hsBankAccount;

    /**
     * 恒生处理状态[0-未知 1-处理成功 2-处理失败]
     */
    private Integer hsDealStatus;

    /**
     * 入账时间
     */
    private Date entryTime;

    /**
     * 入账失败重试次数
     */
    private Integer retryCount;

    /**
     * 是否是有效首金
     */
    private String firstDepFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 实际到账金额
     */
    private BigDecimal receivingAmount;

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
     * 中台入金记录ID
     */
    public Long getBizId() {
        return bizId;
    }

    /**
     * 中台入金记录ID
     */
    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    /**
     * 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 交易帐号
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 交易帐号
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 资金帐号
     */
    public String getFundAccount() {
        return fundAccount;
    }

    /**
     * 资金帐号
     */
    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    /**
     * SWIFT代码
     */
    public String getSwiftCode() {
        return swiftCode;
    }

    /**
     * SWIFT代码
     */
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    /**
     * 银行卡类型[0-香港银行卡 1-大陆银行卡 2海外其他银行]
     */
    public Integer getBankType() {
        return bankType;
    }

    /**
     * 银行卡类型[0-香港银行卡 1-大陆银行卡 2海外其他银行]
     */
    public void setBankType(Integer bankType) {
        this.bankType = bankType;
    }

    /**
     * 汇款方式[0-未知 1-网银汇款 2-支票汇款]
     */
    public Integer getRemittanceType() {
        return remittanceType;
    }

    /**
     * 汇款方式[0-未知 1-网银汇款 2-支票汇款]
     */
    public void setRemittanceType(Integer remittanceType) {
        this.remittanceType = remittanceType;
    }

    /**
     * 汇款银行
     */
    public String getRemittanceBankName() {
        return remittanceBankName;
    }

    /**
     * 汇款银行
     */
    public void setRemittanceBankName(String remittanceBankName) {
        this.remittanceBankName = remittanceBankName;
    }

    /**
     * 汇款账号
     */
    public String getRemittanceAccount() {
        return remittanceAccount;
    }

    /**
     * 汇款账号
     */
    public void setRemittanceAccount(String remittanceAccount) {
        this.remittanceAccount = remittanceAccount;
    }

    /**
     * 汇款账户名称
     */
    public String getRemittanceAccountName() {
        return remittanceAccountName;
    }

    /**
     * 汇款账户名称
     */
    public void setRemittanceAccountName(String remittanceAccountName) {
        this.remittanceAccountName = remittanceAccountName;
    }

    /**
     * 汇款银行代码
     */
    public String getRemittanceBankCode() {
        return remittanceBankCode;
    }

    /**
     * 汇款银行代码
     */
    public void setRemittanceBankCode(String remittanceBankCode) {
        this.remittanceBankCode = remittanceBankCode;
    }

    /**
     * 汇款银行机构号
     */
    public String getRemittanceBankId() {
        return remittanceBankId;
    }

    /**
     * 汇款银行机构号
     */
    public void setRemittanceBankId(String remittanceBankId) {
        this.remittanceBankId = remittanceBankId;
    }

    /**
     * 收款银行
     */
    public String getReceivingBankName() {
        return receivingBankName;
    }

    /**
     * 收款银行
     */
    public void setReceivingBankName(String receivingBankName) {
        this.receivingBankName = receivingBankName;
    }

    /**
     * 收款银行代码
     */
    public String getReceivingBankCode() {
        return receivingBankCode;
    }

    /**
     * 收款银行代码
     */
    public void setReceivingBankCode(String receivingBankCode) {
        this.receivingBankCode = receivingBankCode;
    }

    /**
     * 收款账号
     */
    public String getReceivingAccount() {
        return receivingAccount;
    }

    /**
     * 收款账号
     */
    public void setReceivingAccount(String receivingAccount) {
        this.receivingAccount = receivingAccount;
    }

    /**
     * 收款账户名称
     */
    public String getReceivingAccountName() {
        return receivingAccountName;
    }

    /**
     * 收款账户名称
     */
    public void setReceivingAccountName(String receivingAccountName) {
        this.receivingAccountName = receivingAccountName;
    }

    /**
     * 联系地址
     */
    public String getReceivingAddress() {
        return receivingAddress;
    }

    /**
     * 联系地址
     */
    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    /**
     * 申请金额
     */
    public BigDecimal getDepositBalance() {
        return depositBalance;
    }

    /**
     * 申请金额
     */
    public void setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
    }

    /**
     * 申请时间
     */
    public Date getApplicationTime() {
        return applicationTime;
    }

    /**
     * 申请时间
     */
    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    /**
     * 币种代码[0-人民币 1-美元 2-港币]
     */
    public Integer getMoneyType() {
        return moneyType;
    }

    /**
     * 币种代码[0-人民币 1-美元 2-港币]
     */
    public void setMoneyType(Integer moneyType) {
        this.moneyType = moneyType;
    }

    /**
     * 恒生银行编号
     */
    public String getHsBankId() {
        return hsBankId;
    }

    /**
     * 恒生银行编号
     */
    public void setHsBankId(String hsBankId) {
        this.hsBankId = hsBankId;
    }

    /**
     * 恒生银行帐户
     */
    public String getHsBankAccount() {
        return hsBankAccount;
    }

    /**
     * 恒生银行帐户
     */
    public void setHsBankAccount(String hsBankAccount) {
        this.hsBankAccount = hsBankAccount;
    }

    /**
     * 恒生处理状态[0-未知 1-处理成功 2-处理失败]
     */
    public Integer getHsDealStatus() {
        return hsDealStatus;
    }

    /**
     * 恒生处理状态[0-未知 1-处理成功 2-处理失败]
     */
    public void setHsDealStatus(Integer hsDealStatus) {
        this.hsDealStatus = hsDealStatus;
    }

    /**
     * 入账时间
     */
    public Date getEntryTime() {
        return entryTime;
    }

    /**
     * 入账时间
     */
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * 入账失败重试次数
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     * 入账失败重试次数
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    /**
     * 是否是有效首金
     */
    public String getFirstDepFlag() {
        return firstDepFlag;
    }

    /**
     * 是否是有效首金
     */
    public void setFirstDepFlag(String firstDepFlag) {
        this.firstDepFlag = firstDepFlag;
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
     * 实际到账金额
     */
    public BigDecimal getReceivingAmount() {
        return receivingAmount;
    }

    /**
     * 实际到账金额
     */
    public void setReceivingAmount(BigDecimal receivingAmount) {
        this.receivingAmount = receivingAmount;
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
        ClientFundDepositInfo other = (ClientFundDepositInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getBizId() == null ? other.getBizId() == null : this.getBizId().equals(other.getBizId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getFundAccount() == null ? other.getFundAccount() == null : this.getFundAccount().equals(other.getFundAccount()))
            && (this.getSwiftCode() == null ? other.getSwiftCode() == null : this.getSwiftCode().equals(other.getSwiftCode()))
            && (this.getBankType() == null ? other.getBankType() == null : this.getBankType().equals(other.getBankType()))
            && (this.getRemittanceType() == null ? other.getRemittanceType() == null : this.getRemittanceType().equals(other.getRemittanceType()))
            && (this.getRemittanceBankName() == null ? other.getRemittanceBankName() == null : this.getRemittanceBankName().equals(other.getRemittanceBankName()))
            && (this.getRemittanceAccount() == null ? other.getRemittanceAccount() == null : this.getRemittanceAccount().equals(other.getRemittanceAccount()))
            && (this.getRemittanceAccountName() == null ? other.getRemittanceAccountName() == null : this.getRemittanceAccountName().equals(other.getRemittanceAccountName()))
            && (this.getRemittanceBankCode() == null ? other.getRemittanceBankCode() == null : this.getRemittanceBankCode().equals(other.getRemittanceBankCode()))
            && (this.getRemittanceBankId() == null ? other.getRemittanceBankId() == null : this.getRemittanceBankId().equals(other.getRemittanceBankId()))
            && (this.getReceivingBankName() == null ? other.getReceivingBankName() == null : this.getReceivingBankName().equals(other.getReceivingBankName()))
            && (this.getReceivingBankCode() == null ? other.getReceivingBankCode() == null : this.getReceivingBankCode().equals(other.getReceivingBankCode()))
            && (this.getReceivingAccount() == null ? other.getReceivingAccount() == null : this.getReceivingAccount().equals(other.getReceivingAccount()))
            && (this.getReceivingAccountName() == null ? other.getReceivingAccountName() == null : this.getReceivingAccountName().equals(other.getReceivingAccountName()))
            && (this.getReceivingAddress() == null ? other.getReceivingAddress() == null : this.getReceivingAddress().equals(other.getReceivingAddress()))
            && (this.getDepositBalance() == null ? other.getDepositBalance() == null : this.getDepositBalance().equals(other.getDepositBalance()))
            && (this.getApplicationTime() == null ? other.getApplicationTime() == null : this.getApplicationTime().equals(other.getApplicationTime()))
            && (this.getMoneyType() == null ? other.getMoneyType() == null : this.getMoneyType().equals(other.getMoneyType()))
            && (this.getHsBankId() == null ? other.getHsBankId() == null : this.getHsBankId().equals(other.getHsBankId()))
            && (this.getHsBankAccount() == null ? other.getHsBankAccount() == null : this.getHsBankAccount().equals(other.getHsBankAccount()))
            && (this.getHsDealStatus() == null ? other.getHsDealStatus() == null : this.getHsDealStatus().equals(other.getHsDealStatus()))
            && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
            && (this.getRetryCount() == null ? other.getRetryCount() == null : this.getRetryCount().equals(other.getRetryCount()))
            && (this.getFirstDepFlag() == null ? other.getFirstDepFlag() == null : this.getFirstDepFlag().equals(other.getFirstDepFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getReceivingAmount() == null ? other.getReceivingAmount() == null : this.getReceivingAmount().equals(other.getReceivingAmount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getBizId() == null) ? 0 : getBizId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getFundAccount() == null) ? 0 : getFundAccount().hashCode());
        result = prime * result + ((getSwiftCode() == null) ? 0 : getSwiftCode().hashCode());
        result = prime * result + ((getBankType() == null) ? 0 : getBankType().hashCode());
        result = prime * result + ((getRemittanceType() == null) ? 0 : getRemittanceType().hashCode());
        result = prime * result + ((getRemittanceBankName() == null) ? 0 : getRemittanceBankName().hashCode());
        result = prime * result + ((getRemittanceAccount() == null) ? 0 : getRemittanceAccount().hashCode());
        result = prime * result + ((getRemittanceAccountName() == null) ? 0 : getRemittanceAccountName().hashCode());
        result = prime * result + ((getRemittanceBankCode() == null) ? 0 : getRemittanceBankCode().hashCode());
        result = prime * result + ((getRemittanceBankId() == null) ? 0 : getRemittanceBankId().hashCode());
        result = prime * result + ((getReceivingBankName() == null) ? 0 : getReceivingBankName().hashCode());
        result = prime * result + ((getReceivingBankCode() == null) ? 0 : getReceivingBankCode().hashCode());
        result = prime * result + ((getReceivingAccount() == null) ? 0 : getReceivingAccount().hashCode());
        result = prime * result + ((getReceivingAccountName() == null) ? 0 : getReceivingAccountName().hashCode());
        result = prime * result + ((getReceivingAddress() == null) ? 0 : getReceivingAddress().hashCode());
        result = prime * result + ((getDepositBalance() == null) ? 0 : getDepositBalance().hashCode());
        result = prime * result + ((getApplicationTime() == null) ? 0 : getApplicationTime().hashCode());
        result = prime * result + ((getMoneyType() == null) ? 0 : getMoneyType().hashCode());
        result = prime * result + ((getHsBankId() == null) ? 0 : getHsBankId().hashCode());
        result = prime * result + ((getHsBankAccount() == null) ? 0 : getHsBankAccount().hashCode());
        result = prime * result + ((getHsDealStatus() == null) ? 0 : getHsDealStatus().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getRetryCount() == null) ? 0 : getRetryCount().hashCode());
        result = prime * result + ((getFirstDepFlag() == null) ? 0 : getFirstDepFlag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getReceivingAmount() == null) ? 0 : getReceivingAmount().hashCode());
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
        sb.append(", tenantId=").append(tenantId);
        sb.append(", bizId=").append(bizId);
        sb.append(", userId=").append(userId);
        sb.append(", clientId=").append(clientId);
        sb.append(", fundAccount=").append(fundAccount);
        sb.append(", swiftCode=").append(swiftCode);
        sb.append(", bankType=").append(bankType);
        sb.append(", remittanceType=").append(remittanceType);
        sb.append(", remittanceBankName=").append(remittanceBankName);
        sb.append(", remittanceAccount=").append(remittanceAccount);
        sb.append(", remittanceAccountName=").append(remittanceAccountName);
        sb.append(", remittanceBankCode=").append(remittanceBankCode);
        sb.append(", remittanceBankId=").append(remittanceBankId);
        sb.append(", receivingBankName=").append(receivingBankName);
        sb.append(", receivingBankCode=").append(receivingBankCode);
        sb.append(", receivingAccount=").append(receivingAccount);
        sb.append(", receivingAccountName=").append(receivingAccountName);
        sb.append(", receivingAddress=").append(receivingAddress);
        sb.append(", depositBalance=").append(depositBalance);
        sb.append(", applicationTime=").append(applicationTime);
        sb.append(", moneyType=").append(moneyType);
        sb.append(", hsBankId=").append(hsBankId);
        sb.append(", hsBankAccount=").append(hsBankAccount);
        sb.append(", hsDealStatus=").append(hsDealStatus);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", retryCount=").append(retryCount);
        sb.append(", firstDepFlag=").append(firstDepFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", receivingAmount=").append(receivingAmount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
