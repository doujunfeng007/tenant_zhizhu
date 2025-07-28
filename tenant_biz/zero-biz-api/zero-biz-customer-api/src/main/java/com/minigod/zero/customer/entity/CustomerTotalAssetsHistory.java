package com.minigod.zero.customer.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户资产历史
 * @TableName customer_total_assets_history
 */
public class CustomerTotalAssetsHistory implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 理财账号
     */
    private String accountId;

    /**
     * 用户id
     */
    private Long custId;

    /**
     * 港币总资产
     */
    private BigDecimal hkdAssets;

    /**
     * 人民币总资产
     */
    private BigDecimal cnyAssets;

    /**
     * 美元总资产
     */
    private BigDecimal usdAssets;

    /**
     * 统计时间
     */
    private String statisticalTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 港币现金总资产
     */
    private BigDecimal hkdCashAssets;

    /**
     * 人民币现金总资产
     */
    private BigDecimal cnyCashAssets;

    /**
     * 美元现金总资产
     */
    private BigDecimal usdCashAssets;

    /**
     * 港币持仓总资产
     */
    private BigDecimal hkdPositionAssets;

    /**
     * 人民币持仓总资产
     */
    private BigDecimal cnyPositionAssets;

    /**
     * 美元持仓总资产
     */
    private BigDecimal usdPositionAssets;

    /**
     *
     */
    private Integer isDeleted;

    /**
     * 人民币收益资产
     */
    private BigDecimal hkdIncomeAssets;

    /**
     * 人民币收益资产
     */
    private BigDecimal cnyIncomeAssets;

    /**
     * 美元收益资产
     */
    private BigDecimal usdIncomeAssets;

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
     * 用户id
     */
    public Long getCustId() {
        return custId;
    }

    /**
     * 用户id
     */
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    /**
     * 港币总资产
     */
    public BigDecimal getHkdAssets() {
        return hkdAssets;
    }

    /**
     * 港币总资产
     */
    public void setHkdAssets(BigDecimal hkdAssets) {
        this.hkdAssets = hkdAssets;
    }

    /**
     * 人民币总资产
     */
    public BigDecimal getCnyAssets() {
        return cnyAssets;
    }

    /**
     * 人民币总资产
     */
    public void setCnyAssets(BigDecimal cnyAssets) {
        this.cnyAssets = cnyAssets;
    }

    /**
     * 美元总资产
     */
    public BigDecimal getUsdAssets() {
        return usdAssets;
    }

    /**
     * 美元总资产
     */
    public void setUsdAssets(BigDecimal usdAssets) {
        this.usdAssets = usdAssets;
    }

    /**
     * 统计时间
     */
    public String getStatisticalTime() {
        return statisticalTime;
    }

    /**
     * 统计时间
     */
    public void setStatisticalTime(String statisticalTime) {
        this.statisticalTime = statisticalTime;
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
     * 港币现金总资产
     */
    public BigDecimal getHkdCashAssets() {
        return hkdCashAssets;
    }

    /**
     * 港币现金总资产
     */
    public void setHkdCashAssets(BigDecimal hkdCashAssets) {
        this.hkdCashAssets = hkdCashAssets;
    }

    /**
     * 人民币现金总资产
     */
    public BigDecimal getCnyCashAssets() {
        return cnyCashAssets;
    }

    /**
     * 人民币现金总资产
     */
    public void setCnyCashAssets(BigDecimal cnyCashAssets) {
        this.cnyCashAssets = cnyCashAssets;
    }

    /**
     * 美元现金总资产
     */
    public BigDecimal getUsdCashAssets() {
        return usdCashAssets;
    }

    /**
     * 美元现金总资产
     */
    public void setUsdCashAssets(BigDecimal usdCashAssets) {
        this.usdCashAssets = usdCashAssets;
    }

    /**
     * 港币持仓总资产
     */
    public BigDecimal getHkdPositionAssets() {
        return hkdPositionAssets;
    }

    /**
     * 港币持仓总资产
     */
    public void setHkdPositionAssets(BigDecimal hkdPositionAssets) {
        this.hkdPositionAssets = hkdPositionAssets;
    }

    /**
     * 人民币持仓总资产
     */
    public BigDecimal getCnyPositionAssets() {
        return cnyPositionAssets;
    }

    /**
     * 人民币持仓总资产
     */
    public void setCnyPositionAssets(BigDecimal cnyPositionAssets) {
        this.cnyPositionAssets = cnyPositionAssets;
    }

    /**
     * 美元持仓总资产
     */
    public BigDecimal getUsdPositionAssets() {
        return usdPositionAssets;
    }

    /**
     * 美元持仓总资产
     */
    public void setUsdPositionAssets(BigDecimal usdPositionAssets) {
        this.usdPositionAssets = usdPositionAssets;
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
     * 人民币收益资产
     */
    public BigDecimal getHkdIncomeAssets() {
        return hkdIncomeAssets;
    }

    /**
     * 人民币收益资产
     */
    public void setHkdIncomeAssets(BigDecimal hkdIncomeAssets) {
        this.hkdIncomeAssets = hkdIncomeAssets;
    }

    /**
     * 人民币收益资产
     */
    public BigDecimal getCnyIncomeAssets() {
        return cnyIncomeAssets;
    }

    /**
     * 人民币收益资产
     */
    public void setCnyIncomeAssets(BigDecimal cnyIncomeAssets) {
        this.cnyIncomeAssets = cnyIncomeAssets;
    }

    /**
     * 美元收益资产
     */
    public BigDecimal getUsdIncomeAssets() {
        return usdIncomeAssets;
    }

    /**
     * 美元收益资产
     */
    public void setUsdIncomeAssets(BigDecimal usdIncomeAssets) {
        this.usdIncomeAssets = usdIncomeAssets;
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
        CustomerTotalAssetsHistory other = (CustomerTotalAssetsHistory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getCustId() == null ? other.getCustId() == null : this.getCustId().equals(other.getCustId()))
            && (this.getHkdAssets() == null ? other.getHkdAssets() == null : this.getHkdAssets().equals(other.getHkdAssets()))
            && (this.getCnyAssets() == null ? other.getCnyAssets() == null : this.getCnyAssets().equals(other.getCnyAssets()))
            && (this.getUsdAssets() == null ? other.getUsdAssets() == null : this.getUsdAssets().equals(other.getUsdAssets()))
            && (this.getStatisticalTime() == null ? other.getStatisticalTime() == null : this.getStatisticalTime().equals(other.getStatisticalTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getHkdCashAssets() == null ? other.getHkdCashAssets() == null : this.getHkdCashAssets().equals(other.getHkdCashAssets()))
            && (this.getCnyCashAssets() == null ? other.getCnyCashAssets() == null : this.getCnyCashAssets().equals(other.getCnyCashAssets()))
            && (this.getUsdCashAssets() == null ? other.getUsdCashAssets() == null : this.getUsdCashAssets().equals(other.getUsdCashAssets()))
            && (this.getHkdPositionAssets() == null ? other.getHkdPositionAssets() == null : this.getHkdPositionAssets().equals(other.getHkdPositionAssets()))
            && (this.getCnyPositionAssets() == null ? other.getCnyPositionAssets() == null : this.getCnyPositionAssets().equals(other.getCnyPositionAssets()))
            && (this.getUsdPositionAssets() == null ? other.getUsdPositionAssets() == null : this.getUsdPositionAssets().equals(other.getUsdPositionAssets()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getHkdIncomeAssets() == null ? other.getHkdIncomeAssets() == null : this.getHkdIncomeAssets().equals(other.getHkdIncomeAssets()))
            && (this.getCnyIncomeAssets() == null ? other.getCnyIncomeAssets() == null : this.getCnyIncomeAssets().equals(other.getCnyIncomeAssets()))
            && (this.getUsdIncomeAssets() == null ? other.getUsdIncomeAssets() == null : this.getUsdIncomeAssets().equals(other.getUsdIncomeAssets()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getCustId() == null) ? 0 : getCustId().hashCode());
        result = prime * result + ((getHkdAssets() == null) ? 0 : getHkdAssets().hashCode());
        result = prime * result + ((getCnyAssets() == null) ? 0 : getCnyAssets().hashCode());
        result = prime * result + ((getUsdAssets() == null) ? 0 : getUsdAssets().hashCode());
        result = prime * result + ((getStatisticalTime() == null) ? 0 : getStatisticalTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getHkdCashAssets() == null) ? 0 : getHkdCashAssets().hashCode());
        result = prime * result + ((getCnyCashAssets() == null) ? 0 : getCnyCashAssets().hashCode());
        result = prime * result + ((getUsdCashAssets() == null) ? 0 : getUsdCashAssets().hashCode());
        result = prime * result + ((getHkdPositionAssets() == null) ? 0 : getHkdPositionAssets().hashCode());
        result = prime * result + ((getCnyPositionAssets() == null) ? 0 : getCnyPositionAssets().hashCode());
        result = prime * result + ((getUsdPositionAssets() == null) ? 0 : getUsdPositionAssets().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getHkdIncomeAssets() == null) ? 0 : getHkdIncomeAssets().hashCode());
        result = prime * result + ((getCnyIncomeAssets() == null) ? 0 : getCnyIncomeAssets().hashCode());
        result = prime * result + ((getUsdIncomeAssets() == null) ? 0 : getUsdIncomeAssets().hashCode());
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
        sb.append(", custId=").append(custId);
        sb.append(", hkdAssets=").append(hkdAssets);
        sb.append(", cnyAssets=").append(cnyAssets);
        sb.append(", usdAssets=").append(usdAssets);
        sb.append(", statisticalTime=").append(statisticalTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", hkdCashAssets=").append(hkdCashAssets);
        sb.append(", cnyCashAssets=").append(cnyCashAssets);
        sb.append(", usdCashAssets=").append(usdCashAssets);
        sb.append(", hkdPositionAssets=").append(hkdPositionAssets);
        sb.append(", cnyPositionAssets=").append(cnyPositionAssets);
        sb.append(", usdPositionAssets=").append(usdPositionAssets);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", hkdIncomeAssets=").append(hkdIncomeAssets);
        sb.append(", cnyIncomeAssets=").append(cnyIncomeAssets);
        sb.append(", usdIncomeAssets=").append(usdIncomeAssets);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
