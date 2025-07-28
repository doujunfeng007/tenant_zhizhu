package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户现金资产历史记录
 * @TableName customer_cash_assets_history
 */
public class CustomerCashAssetsHistory implements Serializable {
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
     * 交易账号账号
     */
    private String tradeAccount;

    /**
     * 子账号账号
     */
    private String subAccount;

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
     * 换汇后港币总资产
     */
    private BigDecimal targetHkdAssets;

    /**
     * 换汇后人民币总资产
     */
    private BigDecimal targetCnyAssets;

    /**
     * 换会后美元总资产
     */
    private BigDecimal targetUsdAssets;

    /**
     * 港币总资产
     */
    private BigDecimal hkdAssets;

    /**
     * 港币可用金额
     */
    private BigDecimal hkdAvailable;

    /**
     * 港币冻结
     */
    private BigDecimal hkdFreeze;

    /**
     * 港币在途
     */
    private BigDecimal hkdIntransit;

    /**
     * 美元总资产
     */
    private BigDecimal usdAssets;

    /**
     * 美元可用金额
     */
    private BigDecimal usdAvailable;

    /**
     * 美元冻结
     */
    private BigDecimal usdFreeze;

    /**
     * 美元在途
     */
    private BigDecimal usdIntransit;

    /**
     * 人民币总资产
     */
    private BigDecimal cnyAssets;

    /**
     * 人民币可用金额
     */
    private BigDecimal cnyAvailable;

    /**
     * 人民币冻结
     */
    private BigDecimal cnyFreeze;

    /**
     * 人民币在途
     */
    private BigDecimal cnyIntransit;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	private String customerName;

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
     * 交易账号账号
     */
    public String getTradeAccount() {
        return tradeAccount;
    }

    /**
     * 交易账号账号
     */
    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    /**
     * 子账号账号
     */
    public String getSubAccount() {
        return subAccount;
    }

    /**
     * 子账号账号
     */
    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
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
     * 换汇后港币总资产
     */
    public BigDecimal getTargetHkdAssets() {
        return targetHkdAssets;
    }

    /**
     * 换汇后港币总资产
     */
    public void setTargetHkdAssets(BigDecimal targetHkdAssets) {
        this.targetHkdAssets = targetHkdAssets;
    }

    /**
     * 换汇后人民币总资产
     */
    public BigDecimal getTargetCnyAssets() {
        return targetCnyAssets;
    }

    /**
     * 换汇后人民币总资产
     */
    public void setTargetCnyAssets(BigDecimal targetCnyAssets) {
        this.targetCnyAssets = targetCnyAssets;
    }

    /**
     * 换会后美元总资产
     */
    public BigDecimal getTargetUsdAssets() {
        return targetUsdAssets;
    }

    /**
     * 换会后美元总资产
     */
    public void setTargetUsdAssets(BigDecimal targetUsdAssets) {
        this.targetUsdAssets = targetUsdAssets;
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
     * 港币可用金额
     */
    public BigDecimal getHkdAvailable() {
        return hkdAvailable;
    }

    /**
     * 港币可用金额
     */
    public void setHkdAvailable(BigDecimal hkdAvailable) {
        this.hkdAvailable = hkdAvailable;
    }

    /**
     * 港币冻结
     */
    public BigDecimal getHkdFreeze() {
        return hkdFreeze;
    }

    /**
     * 港币冻结
     */
    public void setHkdFreeze(BigDecimal hkdFreeze) {
        this.hkdFreeze = hkdFreeze;
    }

    /**
     * 港币在途
     */
    public BigDecimal getHkdIntransit() {
        return hkdIntransit;
    }

    /**
     * 港币在途
     */
    public void setHkdIntransit(BigDecimal hkdIntransit) {
        this.hkdIntransit = hkdIntransit;
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
     * 美元可用金额
     */
    public BigDecimal getUsdAvailable() {
        return usdAvailable;
    }

    /**
     * 美元可用金额
     */
    public void setUsdAvailable(BigDecimal usdAvailable) {
        this.usdAvailable = usdAvailable;
    }

    /**
     * 美元冻结
     */
    public BigDecimal getUsdFreeze() {
        return usdFreeze;
    }

    /**
     * 美元冻结
     */
    public void setUsdFreeze(BigDecimal usdFreeze) {
        this.usdFreeze = usdFreeze;
    }

    /**
     * 美元在途
     */
    public BigDecimal getUsdIntransit() {
        return usdIntransit;
    }

    /**
     * 美元在途
     */
    public void setUsdIntransit(BigDecimal usdIntransit) {
        this.usdIntransit = usdIntransit;
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
     * 人民币可用金额
     */
    public BigDecimal getCnyAvailable() {
        return cnyAvailable;
    }

    /**
     * 人民币可用金额
     */
    public void setCnyAvailable(BigDecimal cnyAvailable) {
        this.cnyAvailable = cnyAvailable;
    }

    /**
     * 人民币冻结
     */
    public BigDecimal getCnyFreeze() {
        return cnyFreeze;
    }

    /**
     * 人民币冻结
     */
    public void setCnyFreeze(BigDecimal cnyFreeze) {
        this.cnyFreeze = cnyFreeze;
    }

    /**
     * 人民币在途
     */
    public BigDecimal getCnyIntransit() {
        return cnyIntransit;
    }

    /**
     * 人民币在途
     */
    public void setCnyIntransit(BigDecimal cnyIntransit) {
        this.cnyIntransit = cnyIntransit;
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
        CustomerCashAssetsHistory other = (CustomerCashAssetsHistory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCustId() == null ? other.getCustId() == null : this.getCustId().equals(other.getCustId()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getTradeAccount() == null ? other.getTradeAccount() == null : this.getTradeAccount().equals(other.getTradeAccount()))
            && (this.getSubAccount() == null ? other.getSubAccount() == null : this.getSubAccount().equals(other.getSubAccount()))
            && (this.getStatisticalTime() == null ? other.getStatisticalTime() == null : this.getStatisticalTime().equals(other.getStatisticalTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getTargetHkdAssets() == null ? other.getTargetHkdAssets() == null : this.getTargetHkdAssets().equals(other.getTargetHkdAssets()))
            && (this.getTargetCnyAssets() == null ? other.getTargetCnyAssets() == null : this.getTargetCnyAssets().equals(other.getTargetCnyAssets()))
            && (this.getTargetUsdAssets() == null ? other.getTargetUsdAssets() == null : this.getTargetUsdAssets().equals(other.getTargetUsdAssets()))
            && (this.getHkdAssets() == null ? other.getHkdAssets() == null : this.getHkdAssets().equals(other.getHkdAssets()))
            && (this.getHkdAvailable() == null ? other.getHkdAvailable() == null : this.getHkdAvailable().equals(other.getHkdAvailable()))
            && (this.getHkdFreeze() == null ? other.getHkdFreeze() == null : this.getHkdFreeze().equals(other.getHkdFreeze()))
            && (this.getHkdIntransit() == null ? other.getHkdIntransit() == null : this.getHkdIntransit().equals(other.getHkdIntransit()))
            && (this.getUsdAssets() == null ? other.getUsdAssets() == null : this.getUsdAssets().equals(other.getUsdAssets()))
            && (this.getUsdAvailable() == null ? other.getUsdAvailable() == null : this.getUsdAvailable().equals(other.getUsdAvailable()))
            && (this.getUsdFreeze() == null ? other.getUsdFreeze() == null : this.getUsdFreeze().equals(other.getUsdFreeze()))
            && (this.getUsdIntransit() == null ? other.getUsdIntransit() == null : this.getUsdIntransit().equals(other.getUsdIntransit()))
            && (this.getCnyAssets() == null ? other.getCnyAssets() == null : this.getCnyAssets().equals(other.getCnyAssets()))
            && (this.getCnyAvailable() == null ? other.getCnyAvailable() == null : this.getCnyAvailable().equals(other.getCnyAvailable()))
            && (this.getCnyFreeze() == null ? other.getCnyFreeze() == null : this.getCnyFreeze().equals(other.getCnyFreeze()))
            && (this.getCnyIntransit() == null ? other.getCnyIntransit() == null : this.getCnyIntransit().equals(other.getCnyIntransit()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustId() == null) ? 0 : getCustId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getTradeAccount() == null) ? 0 : getTradeAccount().hashCode());
        result = prime * result + ((getSubAccount() == null) ? 0 : getSubAccount().hashCode());
        result = prime * result + ((getStatisticalTime() == null) ? 0 : getStatisticalTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getTargetHkdAssets() == null) ? 0 : getTargetHkdAssets().hashCode());
        result = prime * result + ((getTargetCnyAssets() == null) ? 0 : getTargetCnyAssets().hashCode());
        result = prime * result + ((getTargetUsdAssets() == null) ? 0 : getTargetUsdAssets().hashCode());
        result = prime * result + ((getHkdAssets() == null) ? 0 : getHkdAssets().hashCode());
        result = prime * result + ((getHkdAvailable() == null) ? 0 : getHkdAvailable().hashCode());
        result = prime * result + ((getHkdFreeze() == null) ? 0 : getHkdFreeze().hashCode());
        result = prime * result + ((getHkdIntransit() == null) ? 0 : getHkdIntransit().hashCode());
        result = prime * result + ((getUsdAssets() == null) ? 0 : getUsdAssets().hashCode());
        result = prime * result + ((getUsdAvailable() == null) ? 0 : getUsdAvailable().hashCode());
        result = prime * result + ((getUsdFreeze() == null) ? 0 : getUsdFreeze().hashCode());
        result = prime * result + ((getUsdIntransit() == null) ? 0 : getUsdIntransit().hashCode());
        result = prime * result + ((getCnyAssets() == null) ? 0 : getCnyAssets().hashCode());
        result = prime * result + ((getCnyAvailable() == null) ? 0 : getCnyAvailable().hashCode());
        result = prime * result + ((getCnyFreeze() == null) ? 0 : getCnyFreeze().hashCode());
        result = prime * result + ((getCnyIntransit() == null) ? 0 : getCnyIntransit().hashCode());
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
        sb.append(", tradeAccount=").append(tradeAccount);
        sb.append(", subAccount=").append(subAccount);
        sb.append(", statisticalTime=").append(statisticalTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", targetHkdAssets=").append(targetHkdAssets);
        sb.append(", targetCnyAssets=").append(targetCnyAssets);
        sb.append(", targetUsdAssets=").append(targetUsdAssets);
        sb.append(", hkdAssets=").append(hkdAssets);
        sb.append(", hkdAvailable=").append(hkdAvailable);
        sb.append(", hkdFreeze=").append(hkdFreeze);
        sb.append(", hkdIntransit=").append(hkdIntransit);
        sb.append(", usdAssets=").append(usdAssets);
        sb.append(", usdAvailable=").append(usdAvailable);
        sb.append(", usdFreeze=").append(usdFreeze);
        sb.append(", usdIntransit=").append(usdIntransit);
        sb.append(", cnyAssets=").append(cnyAssets);
        sb.append(", cnyAvailable=").append(cnyAvailable);
        sb.append(", cnyFreeze=").append(cnyFreeze);
        sb.append(", cnyIntransit=").append(cnyIntransit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
