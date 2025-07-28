package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName account_business_flow
 */
public class AccountBusinessFlow implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 客户id
     */
    private Long custId;

    /**
     * 理财账号
     */
    private String accountId;

    /**
     * 调用方业务id
     */
    private String businessId;

    /**
     * 业务金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 业务时间
     */
    private Date createTime;

    /**
     * 0.预付款，1.付款成功，2.取消付款，3.部分付款
     */
    private Integer status;

    /**
     * 付款金额
     */
    private BigDecimal payAmount;

    /**
     * 退还金额
     */
    private BigDecimal refundingAmount;

    /**
     * 来源
     */
    private String source;

    /**
     * 跨币种交易时的计算汇率
     */
    private String exchangeRate;

    /**
     *
     */
    private Integer businessType;

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 客户id
     */
    public Long getCustId() {
        return custId;
    }

    /**
     * 客户id
     */
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    /**
     * 理财账号
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 理财账号
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 调用方业务id
     */
    public String getBusinessId() {
        return businessId;
    }

    /**
     * 调用方业务id
     */
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    /**
     * 业务金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 业务金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
     * 业务时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 业务时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 0.预付款，1.付款成功，2.取消付款，3.部分付款
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0.预付款，1.付款成功，2.取消付款，3.部分付款
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 付款金额
     */
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    /**
     * 付款金额
     */
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 退还金额
     */
    public BigDecimal getRefundingAmount() {
        return refundingAmount;
    }

    /**
     * 退还金额
     */
    public void setRefundingAmount(BigDecimal refundingAmount) {
        this.refundingAmount = refundingAmount;
    }

    /**
     * 来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 来源
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 跨币种交易时的计算汇率
     */
    public String getExchangeRate() {
        return exchangeRate;
    }

    /**
     * 跨币种交易时的计算汇率
     */
    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     *
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     *
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
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
        AccountBusinessFlow other = (AccountBusinessFlow) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustId() == null ? other.getCustId() == null : this.getCustId().equals(other.getCustId()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPayAmount() == null ? other.getPayAmount() == null : this.getPayAmount().equals(other.getPayAmount()))
            && (this.getRefundingAmount() == null ? other.getRefundingAmount() == null : this.getRefundingAmount().equals(other.getRefundingAmount()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getExchangeRate() == null ? other.getExchangeRate() == null : this.getExchangeRate().equals(other.getExchangeRate()))
            && (this.getBusinessType() == null ? other.getBusinessType() == null : this.getBusinessType().equals(other.getBusinessType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustId() == null) ? 0 : getCustId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getBusinessId() == null) ? 0 : getBusinessId().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPayAmount() == null) ? 0 : getPayAmount().hashCode());
        result = prime * result + ((getRefundingAmount() == null) ? 0 : getRefundingAmount().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getExchangeRate() == null) ? 0 : getExchangeRate().hashCode());
        result = prime * result + ((getBusinessType() == null) ? 0 : getBusinessType().hashCode());
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
        sb.append(", accountId=").append(accountId);
        sb.append(", businessId=").append(businessId);
        sb.append(", amount=").append(amount);
        sb.append(", currency=").append(currency);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", refundingAmount=").append(refundingAmount);
        sb.append(", source=").append(source);
        sb.append(", exchangeRate=").append(exchangeRate);
        sb.append(", businessType=").append(businessType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
