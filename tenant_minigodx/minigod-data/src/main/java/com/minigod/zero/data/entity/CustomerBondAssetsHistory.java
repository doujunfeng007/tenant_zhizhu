package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户债券易资产历史记录
 * @TableName customer_bond_assets_history
 */
public class CustomerBondAssetsHistory implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long custId;

    /**
     * 理财账号
     */
    private String accountId;

    /**
     * 港币市值
     */
    private BigDecimal hkdMarketValue;

    /**
     * 人民币市值
     */
    private BigDecimal cnyMarketValue;

    /**
     * 美元市值
     */
    private BigDecimal usdMarketValue;

    /**
     * 统计时间
     */
    private String statisticalTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *
     */
    private Integer isDeleted;

    /**
     * 港币收益
     */
    private BigDecimal hkdIncome;

    /**
     * 人民币收益
     */
    private BigDecimal cnyIncome;

    /**
     * 美元收益
     */
    private BigDecimal usdIncome;

    /**
     * 换汇后港币市值
     */
    private BigDecimal targetHkdMarketValue;

    /**
     * 换汇后人民币市值
     */
    private BigDecimal targetCnyMarketValue;

    /**
     * 换会后美金市值
     */
    private BigDecimal targetUsdMarketValue;

    /**
     * 换会后港币收益
     */
    private BigDecimal targetHkdIncome;

    /**
     * 换汇后人民币收益
     */
    private BigDecimal targetCnyIncome;

    /**
     * 换会后美金收益
     */
    private BigDecimal targetUsdIncome;

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
     * 港币市值
     */
    public BigDecimal getHkdMarketValue() {
        return hkdMarketValue;
    }

    /**
     * 港币市值
     */
    public void setHkdMarketValue(BigDecimal hkdMarketValue) {
        this.hkdMarketValue = hkdMarketValue;
    }

    /**
     * 人民币市值
     */
    public BigDecimal getCnyMarketValue() {
        return cnyMarketValue;
    }

    /**
     * 人民币市值
     */
    public void setCnyMarketValue(BigDecimal cnyMarketValue) {
        this.cnyMarketValue = cnyMarketValue;
    }

    /**
     * 美元市值
     */
    public BigDecimal getUsdMarketValue() {
        return usdMarketValue;
    }

    /**
     * 美元市值
     */
    public void setUsdMarketValue(BigDecimal usdMarketValue) {
        this.usdMarketValue = usdMarketValue;
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
     * 港币收益
     */
    public BigDecimal getHkdIncome() {
        return hkdIncome;
    }

    /**
     * 港币收益
     */
    public void setHkdIncome(BigDecimal hkdIncome) {
        this.hkdIncome = hkdIncome;
    }

    /**
     * 人民币收益
     */
    public BigDecimal getCnyIncome() {
        return cnyIncome;
    }

    /**
     * 人民币收益
     */
    public void setCnyIncome(BigDecimal cnyIncome) {
        this.cnyIncome = cnyIncome;
    }

    /**
     * 美元收益
     */
    public BigDecimal getUsdIncome() {
        return usdIncome;
    }

    /**
     * 美元收益
     */
    public void setUsdIncome(BigDecimal usdIncome) {
        this.usdIncome = usdIncome;
    }

    /**
     * 换汇后港币市值
     */
    public BigDecimal getTargetHkdMarketValue() {
        return targetHkdMarketValue;
    }

    /**
     * 换汇后港币市值
     */
    public void setTargetHkdMarketValue(BigDecimal targetHkdMarketValue) {
        this.targetHkdMarketValue = targetHkdMarketValue;
    }

    /**
     * 换汇后人民币市值
     */
    public BigDecimal getTargetCnyMarketValue() {
        return targetCnyMarketValue;
    }

    /**
     * 换汇后人民币市值
     */
    public void setTargetCnyMarketValue(BigDecimal targetCnyMarketValue) {
        this.targetCnyMarketValue = targetCnyMarketValue;
    }

    /**
     * 换会后美金市值
     */
    public BigDecimal getTargetUsdMarketValue() {
        return targetUsdMarketValue;
    }

    /**
     * 换会后美金市值
     */
    public void setTargetUsdMarketValue(BigDecimal targetUsdMarketValue) {
        this.targetUsdMarketValue = targetUsdMarketValue;
    }

    /**
     * 换会后港币收益
     */
    public BigDecimal getTargetHkdIncome() {
        return targetHkdIncome;
    }

    /**
     * 换会后港币收益
     */
    public void setTargetHkdIncome(BigDecimal targetHkdIncome) {
        this.targetHkdIncome = targetHkdIncome;
    }

    /**
     * 换汇后人民币收益
     */
    public BigDecimal getTargetCnyIncome() {
        return targetCnyIncome;
    }

    /**
     * 换汇后人民币收益
     */
    public void setTargetCnyIncome(BigDecimal targetCnyIncome) {
        this.targetCnyIncome = targetCnyIncome;
    }

    /**
     * 换会后美金收益
     */
    public BigDecimal getTargetUsdIncome() {
        return targetUsdIncome;
    }

    /**
     * 换会后美金收益
     */
    public void setTargetUsdIncome(BigDecimal targetUsdIncome) {
        this.targetUsdIncome = targetUsdIncome;
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
        CustomerBondAssetsHistory other = (CustomerBondAssetsHistory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustId() == null ? other.getCustId() == null : this.getCustId().equals(other.getCustId()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getHkdMarketValue() == null ? other.getHkdMarketValue() == null : this.getHkdMarketValue().equals(other.getHkdMarketValue()))
            && (this.getCnyMarketValue() == null ? other.getCnyMarketValue() == null : this.getCnyMarketValue().equals(other.getCnyMarketValue()))
            && (this.getUsdMarketValue() == null ? other.getUsdMarketValue() == null : this.getUsdMarketValue().equals(other.getUsdMarketValue()))
            && (this.getStatisticalTime() == null ? other.getStatisticalTime() == null : this.getStatisticalTime().equals(other.getStatisticalTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getHkdIncome() == null ? other.getHkdIncome() == null : this.getHkdIncome().equals(other.getHkdIncome()))
            && (this.getCnyIncome() == null ? other.getCnyIncome() == null : this.getCnyIncome().equals(other.getCnyIncome()))
            && (this.getUsdIncome() == null ? other.getUsdIncome() == null : this.getUsdIncome().equals(other.getUsdIncome()))
            && (this.getTargetHkdMarketValue() == null ? other.getTargetHkdMarketValue() == null : this.getTargetHkdMarketValue().equals(other.getTargetHkdMarketValue()))
            && (this.getTargetCnyMarketValue() == null ? other.getTargetCnyMarketValue() == null : this.getTargetCnyMarketValue().equals(other.getTargetCnyMarketValue()))
            && (this.getTargetUsdMarketValue() == null ? other.getTargetUsdMarketValue() == null : this.getTargetUsdMarketValue().equals(other.getTargetUsdMarketValue()))
            && (this.getTargetHkdIncome() == null ? other.getTargetHkdIncome() == null : this.getTargetHkdIncome().equals(other.getTargetHkdIncome()))
            && (this.getTargetCnyIncome() == null ? other.getTargetCnyIncome() == null : this.getTargetCnyIncome().equals(other.getTargetCnyIncome()))
            && (this.getTargetUsdIncome() == null ? other.getTargetUsdIncome() == null : this.getTargetUsdIncome().equals(other.getTargetUsdIncome()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustId() == null) ? 0 : getCustId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getHkdMarketValue() == null) ? 0 : getHkdMarketValue().hashCode());
        result = prime * result + ((getCnyMarketValue() == null) ? 0 : getCnyMarketValue().hashCode());
        result = prime * result + ((getUsdMarketValue() == null) ? 0 : getUsdMarketValue().hashCode());
        result = prime * result + ((getStatisticalTime() == null) ? 0 : getStatisticalTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getHkdIncome() == null) ? 0 : getHkdIncome().hashCode());
        result = prime * result + ((getCnyIncome() == null) ? 0 : getCnyIncome().hashCode());
        result = prime * result + ((getUsdIncome() == null) ? 0 : getUsdIncome().hashCode());
        result = prime * result + ((getTargetHkdMarketValue() == null) ? 0 : getTargetHkdMarketValue().hashCode());
        result = prime * result + ((getTargetCnyMarketValue() == null) ? 0 : getTargetCnyMarketValue().hashCode());
        result = prime * result + ((getTargetUsdMarketValue() == null) ? 0 : getTargetUsdMarketValue().hashCode());
        result = prime * result + ((getTargetHkdIncome() == null) ? 0 : getTargetHkdIncome().hashCode());
        result = prime * result + ((getTargetCnyIncome() == null) ? 0 : getTargetCnyIncome().hashCode());
        result = prime * result + ((getTargetUsdIncome() == null) ? 0 : getTargetUsdIncome().hashCode());
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
        sb.append(", hkdMarketValue=").append(hkdMarketValue);
        sb.append(", cnyMarketValue=").append(cnyMarketValue);
        sb.append(", usdMarketValue=").append(usdMarketValue);
        sb.append(", statisticalTime=").append(statisticalTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", hkdIncome=").append(hkdIncome);
        sb.append(", cnyIncome=").append(cnyIncome);
        sb.append(", usdIncome=").append(usdIncome);
        sb.append(", targetHkdMarketValue=").append(targetHkdMarketValue);
        sb.append(", targetCnyMarketValue=").append(targetCnyMarketValue);
        sb.append(", targetUsdMarketValue=").append(targetUsdMarketValue);
        sb.append(", targetHkdIncome=").append(targetHkdIncome);
        sb.append(", targetCnyIncome=").append(targetCnyIncome);
        sb.append(", targetUsdIncome=").append(targetUsdIncome);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
