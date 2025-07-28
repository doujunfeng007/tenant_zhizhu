package com.minigod.zero.customer.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金最新价
 * @TableName fund_latest_price
 */
public class FundLatestPriceEntity implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 基金标识
     */
    private String fundCode;

    /**
     * 币种
     */
    private String currency;

    /**
     * 价格日期
     */
    private Date priceDate;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 调整价格日期
     */
    private Date adjustedPriceDate;

    /**
     * 调整价格
     */
    private BigDecimal adjustedPrice;

    /**
     * 备注
     */
    private String remark;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Date lastUpdateTime;

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
     * 基金标识
     */
    public String getFundCode() {
        return fundCode;
    }

    /**
     * 基金标识
     */
    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
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
     * 价格日期
     */
    public Date getPriceDate() {
        return priceDate;
    }

    /**
     * 价格日期
     */
    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    /**
     * 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 调整价格日期
     */
    public Date getAdjustedPriceDate() {
        return adjustedPriceDate;
    }

    /**
     * 调整价格日期
     */
    public void setAdjustedPriceDate(Date adjustedPriceDate) {
        this.adjustedPriceDate = adjustedPriceDate;
    }

    /**
     * 调整价格
     */
    public BigDecimal getAdjustedPrice() {
        return adjustedPrice;
    }

    /**
     * 调整价格
     */
    public void setAdjustedPrice(BigDecimal adjustedPrice) {
        this.adjustedPrice = adjustedPrice;
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
     *
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     *
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     *
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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
        FundLatestPriceEntity other = (FundLatestPriceEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFundCode() == null ? other.getFundCode() == null : this.getFundCode().equals(other.getFundCode()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getPriceDate() == null ? other.getPriceDate() == null : this.getPriceDate().equals(other.getPriceDate()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getAdjustedPriceDate() == null ? other.getAdjustedPriceDate() == null : this.getAdjustedPriceDate().equals(other.getAdjustedPriceDate()))
            && (this.getAdjustedPrice() == null ? other.getAdjustedPrice() == null : this.getAdjustedPrice().equals(other.getAdjustedPrice()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFundCode() == null) ? 0 : getFundCode().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getPriceDate() == null) ? 0 : getPriceDate().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getAdjustedPriceDate() == null) ? 0 : getAdjustedPriceDate().hashCode());
        result = prime * result + ((getAdjustedPrice() == null) ? 0 : getAdjustedPrice().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fundCode=").append(fundCode);
        sb.append(", currency=").append(currency);
        sb.append(", priceDate=").append(priceDate);
        sb.append(", price=").append(price);
        sb.append(", adjustedPriceDate=").append(adjustedPriceDate);
        sb.append(", adjustedPrice=").append(adjustedPrice);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
