package com.minigod.zero.customer.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户基金持仓表
 * @TableName customer_fund_holding_records
 */
public class CustomerFundHoldingRecordsEntity implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 持仓标识
     */
    private String holdingId;

    /**
     * 资金账号
     */
    private String subAccountId;

    /**
     * 基金代码
     */
    private String fundCode;

	/**
	 * 基金名称
	 */
	private String fundName;

    /**
     * 总份额
     */
    private BigDecimal totalQuantity;

    /**
     * 可用份额
     */
    private BigDecimal availableQuantity;

    /**
     * 冻结份额
     */
    private BigDecimal frozenQuantity;

    /**
     * 在途份额
     */
    private BigDecimal transitedQuantity;

    /**
     * 可取份额
     */
    private BigDecimal withdrawQuantity;

    /**
     * 平均成本
     */
    private BigDecimal averageCost;

    /**
     * 摊薄平均成本，包含赎回
     */
    private BigDecimal dilutedCost;

    /**
     * 货币类型
     */
    private String currency;

    /**
     * 总费用
     */
    private BigDecimal totalFee;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     *
     */
    private Integer isDeleted;

	/**
	 * 以实现盈亏（卖出份额盈利）
	 */
	private BigDecimal realizedGainLoss;

	private static final long serialVersionUID = 1L;

	public BigDecimal getRealizedGainLoss() {
		return realizedGainLoss;
	}

	public void setRealizedGainLoss(BigDecimal realizedGainLoss) {
		this.realizedGainLoss = realizedGainLoss;
	}


	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

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
     * 持仓标识
     */
    public String getHoldingId() {
        return holdingId;
    }

    /**
     * 持仓标识
     */
    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
    }

    /**
     * 资金账号
     */
    public String getSubAccountId() {
        return subAccountId;
    }

    /**
     * 资金账号
     */
    public void setSubAccountId(String subAccountId) {
        this.subAccountId = subAccountId;
    }

    /**
     * 基金代码
     */
    public String getFundCode() {
        return fundCode;
    }

    /**
     * 基金代码
     */
    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    /**
     * 总份额
     */
    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * 总份额
     */
    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    /**
     * 可用份额
     */
    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * 可用份额
     */
    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    /**
     * 冻结份额
     */
    public BigDecimal getFrozenQuantity() {
        return frozenQuantity;
    }

    /**
     * 冻结份额
     */
    public void setFrozenQuantity(BigDecimal frozenQuantity) {
        this.frozenQuantity = frozenQuantity;
    }

    /**
     * 在途份额
     */
    public BigDecimal getTransitedQuantity() {
        return transitedQuantity;
    }

    /**
     * 在途份额
     */
    public void setTransitedQuantity(BigDecimal transitedQuantity) {
        this.transitedQuantity = transitedQuantity;
    }

    /**
     * 可取份额
     */
    public BigDecimal getWithdrawQuantity() {
        return withdrawQuantity;
    }

    /**
     * 可取份额
     */
    public void setWithdrawQuantity(BigDecimal withdrawQuantity) {
        this.withdrawQuantity = withdrawQuantity;
    }

    /**
     * 平均成本
     */
    public BigDecimal getAverageCost() {
        return averageCost;
    }

    /**
     * 平均成本
     */
    public void setAverageCost(BigDecimal averageCost) {
        this.averageCost = averageCost;
    }

    /**
     * 摊薄平均成本，包含赎回
     */
    public BigDecimal getDilutedCost() {
        return dilutedCost;
    }

    /**
     * 摊薄平均成本，包含赎回
     */
    public void setDilutedCost(BigDecimal dilutedCost) {
        this.dilutedCost = dilutedCost;
    }

    /**
     * 货币类型
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 货币类型
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 总费用
     */
    public BigDecimal getTotalFee() {
        return totalFee;
    }

    /**
     * 总费用
     */
    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
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
        CustomerFundHoldingRecordsEntity other = (CustomerFundHoldingRecordsEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHoldingId() == null ? other.getHoldingId() == null : this.getHoldingId().equals(other.getHoldingId()))
            && (this.getSubAccountId() == null ? other.getSubAccountId() == null : this.getSubAccountId().equals(other.getSubAccountId()))
            && (this.getFundCode() == null ? other.getFundCode() == null : this.getFundCode().equals(other.getFundCode()))
            && (this.getTotalQuantity() == null ? other.getTotalQuantity() == null : this.getTotalQuantity().equals(other.getTotalQuantity()))
            && (this.getAvailableQuantity() == null ? other.getAvailableQuantity() == null : this.getAvailableQuantity().equals(other.getAvailableQuantity()))
            && (this.getFrozenQuantity() == null ? other.getFrozenQuantity() == null : this.getFrozenQuantity().equals(other.getFrozenQuantity()))
            && (this.getTransitedQuantity() == null ? other.getTransitedQuantity() == null : this.getTransitedQuantity().equals(other.getTransitedQuantity()))
            && (this.getWithdrawQuantity() == null ? other.getWithdrawQuantity() == null : this.getWithdrawQuantity().equals(other.getWithdrawQuantity()))
            && (this.getAverageCost() == null ? other.getAverageCost() == null : this.getAverageCost().equals(other.getAverageCost()))
            && (this.getDilutedCost() == null ? other.getDilutedCost() == null : this.getDilutedCost().equals(other.getDilutedCost()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getTotalFee() == null ? other.getTotalFee() == null : this.getTotalFee().equals(other.getTotalFee()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHoldingId() == null) ? 0 : getHoldingId().hashCode());
        result = prime * result + ((getSubAccountId() == null) ? 0 : getSubAccountId().hashCode());
        result = prime * result + ((getFundCode() == null) ? 0 : getFundCode().hashCode());
        result = prime * result + ((getTotalQuantity() == null) ? 0 : getTotalQuantity().hashCode());
        result = prime * result + ((getAvailableQuantity() == null) ? 0 : getAvailableQuantity().hashCode());
        result = prime * result + ((getFrozenQuantity() == null) ? 0 : getFrozenQuantity().hashCode());
        result = prime * result + ((getTransitedQuantity() == null) ? 0 : getTransitedQuantity().hashCode());
        result = prime * result + ((getWithdrawQuantity() == null) ? 0 : getWithdrawQuantity().hashCode());
        result = prime * result + ((getAverageCost() == null) ? 0 : getAverageCost().hashCode());
        result = prime * result + ((getDilutedCost() == null) ? 0 : getDilutedCost().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getTotalFee() == null) ? 0 : getTotalFee().hashCode());
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
        sb.append(", holdingId=").append(holdingId);
        sb.append(", subAccountId=").append(subAccountId);
        sb.append(", fundCode=").append(fundCode);
        sb.append(", totalQuantity=").append(totalQuantity);
        sb.append(", availableQuantity=").append(availableQuantity);
        sb.append(", frozenQuantity=").append(frozenQuantity);
        sb.append(", transitedQuantity=").append(transitedQuantity);
        sb.append(", withdrawQuantity=").append(withdrawQuantity);
        sb.append(", averageCost=").append(averageCost);
        sb.append(", dilutedCost=").append(dilutedCost);
        sb.append(", currency=").append(currency);
        sb.append(", totalFee=").append(totalFee);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
