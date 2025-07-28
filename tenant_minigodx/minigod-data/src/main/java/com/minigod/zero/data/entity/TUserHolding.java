package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName t_user_holding
 */
public class TUserHolding implements Serializable {
    /**
     * 持仓标识
     */
    private Integer holdingid;

    /**
     * 基金账号
     */
    private String subaccountid;

    /**
     * 基金/现金id
     */
    private String fundcode;

    /**
     * 持仓类型，1：fund；2：cash；3：icp
     */
    private Integer holdingtype;

    /**
     * 持有总份额/金额
     */
    private BigDecimal totalquantity;

    /**
     * 持有可用份额/金额
     */
    private BigDecimal availablequantity;

    /**
     * 冻结份额/金额
     */
    private BigDecimal frozenquantity;

    /**
     * 在途份额/金额
     */
    private BigDecimal transitedquantity;

    /**
     * 可取金额
     */
    private BigDecimal withdrawquantity;

    /**
     * 平均成本
     */
    private BigDecimal averagecost;

    /**
     * 摊薄成本
     */
    private BigDecimal dilutedcost;

    /**
     * 货币类型
     */
    private String currency;

    /**
     * 累计买入金额
     */
    private BigDecimal accumulatedbuyamount;

    /**
     * 累计派息金额
     */
    private BigDecimal accumulatedcashdividends;

    /**
     * 已实现盈亏
     */
    private BigDecimal realizedgainloss;

    /**
     * 活利得累计总收益
     */
    private BigDecimal totalgainloss;

    /**
     * 总费用
     */
    private BigDecimal totalfee;

    /**
     *
     */
    private Integer flowid;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改时间
     */
    private Date updatetime;

    private static final long serialVersionUID = 1L;

    /**
     * 持仓标识
     */
    public Integer getHoldingid() {
        return holdingid;
    }

    /**
     * 持仓标识
     */
    public void setHoldingid(Integer holdingid) {
        this.holdingid = holdingid;
    }

    /**
     * 基金账号
     */
    public String getSubaccountid() {
        return subaccountid;
    }

    /**
     * 基金账号
     */
    public void setSubaccountid(String subaccountid) {
        this.subaccountid = subaccountid;
    }

    /**
     * 基金/现金id
     */
    public String getFundcode() {
        return fundcode;
    }

    /**
     * 基金/现金id
     */
    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    /**
     * 持仓类型，1：fund；2：cash；3：icp
     */
    public Integer getHoldingtype() {
        return holdingtype;
    }

    /**
     * 持仓类型，1：fund；2：cash；3：icp
     */
    public void setHoldingtype(Integer holdingtype) {
        this.holdingtype = holdingtype;
    }

    /**
     * 持有总份额/金额
     */
    public BigDecimal getTotalquantity() {
        return totalquantity;
    }

    /**
     * 持有总份额/金额
     */
    public void setTotalquantity(BigDecimal totalquantity) {
        this.totalquantity = totalquantity;
    }

    /**
     * 持有可用份额/金额
     */
    public BigDecimal getAvailablequantity() {
        return availablequantity;
    }

    /**
     * 持有可用份额/金额
     */
    public void setAvailablequantity(BigDecimal availablequantity) {
        this.availablequantity = availablequantity;
    }

    /**
     * 冻结份额/金额
     */
    public BigDecimal getFrozenquantity() {
        return frozenquantity;
    }

    /**
     * 冻结份额/金额
     */
    public void setFrozenquantity(BigDecimal frozenquantity) {
        this.frozenquantity = frozenquantity;
    }

    /**
     * 在途份额/金额
     */
    public BigDecimal getTransitedquantity() {
        return transitedquantity;
    }

    /**
     * 在途份额/金额
     */
    public void setTransitedquantity(BigDecimal transitedquantity) {
        this.transitedquantity = transitedquantity;
    }

    /**
     * 可取金额
     */
    public BigDecimal getWithdrawquantity() {
        return withdrawquantity;
    }

    /**
     * 可取金额
     */
    public void setWithdrawquantity(BigDecimal withdrawquantity) {
        this.withdrawquantity = withdrawquantity;
    }

    /**
     * 平均成本
     */
    public BigDecimal getAveragecost() {
        return averagecost;
    }

    /**
     * 平均成本
     */
    public void setAveragecost(BigDecimal averagecost) {
        this.averagecost = averagecost;
    }

    /**
     * 摊薄成本
     */
    public BigDecimal getDilutedcost() {
        return dilutedcost;
    }

    /**
     * 摊薄成本
     */
    public void setDilutedcost(BigDecimal dilutedcost) {
        this.dilutedcost = dilutedcost;
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
     * 累计买入金额
     */
    public BigDecimal getAccumulatedbuyamount() {
        return accumulatedbuyamount;
    }

    /**
     * 累计买入金额
     */
    public void setAccumulatedbuyamount(BigDecimal accumulatedbuyamount) {
        this.accumulatedbuyamount = accumulatedbuyamount;
    }

    /**
     * 累计派息金额
     */
    public BigDecimal getAccumulatedcashdividends() {
        return accumulatedcashdividends;
    }

    /**
     * 累计派息金额
     */
    public void setAccumulatedcashdividends(BigDecimal accumulatedcashdividends) {
        this.accumulatedcashdividends = accumulatedcashdividends;
    }

    /**
     * 已实现盈亏
     */
    public BigDecimal getRealizedgainloss() {
        return realizedgainloss;
    }

    /**
     * 已实现盈亏
     */
    public void setRealizedgainloss(BigDecimal realizedgainloss) {
        this.realizedgainloss = realizedgainloss;
    }

    /**
     * 活利得累计总收益
     */
    public BigDecimal getTotalgainloss() {
        return totalgainloss;
    }

    /**
     * 活利得累计总收益
     */
    public void setTotalgainloss(BigDecimal totalgainloss) {
        this.totalgainloss = totalgainloss;
    }

    /**
     * 总费用
     */
    public BigDecimal getTotalfee() {
        return totalfee;
    }

    /**
     * 总费用
     */
    public void setTotalfee(BigDecimal totalfee) {
        this.totalfee = totalfee;
    }

    /**
     *
     */
    public Integer getFlowid() {
        return flowid;
    }

    /**
     *
     */
    public void setFlowid(Integer flowid) {
        this.flowid = flowid;
    }

    /**
     * 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 修改时间
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * 修改时间
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
        TUserHolding other = (TUserHolding) that;
        return (this.getHoldingid() == null ? other.getHoldingid() == null : this.getHoldingid().equals(other.getHoldingid()))
            && (this.getSubaccountid() == null ? other.getSubaccountid() == null : this.getSubaccountid().equals(other.getSubaccountid()))
            && (this.getFundcode() == null ? other.getFundcode() == null : this.getFundcode().equals(other.getFundcode()))
            && (this.getHoldingtype() == null ? other.getHoldingtype() == null : this.getHoldingtype().equals(other.getHoldingtype()))
            && (this.getTotalquantity() == null ? other.getTotalquantity() == null : this.getTotalquantity().equals(other.getTotalquantity()))
            && (this.getAvailablequantity() == null ? other.getAvailablequantity() == null : this.getAvailablequantity().equals(other.getAvailablequantity()))
            && (this.getFrozenquantity() == null ? other.getFrozenquantity() == null : this.getFrozenquantity().equals(other.getFrozenquantity()))
            && (this.getTransitedquantity() == null ? other.getTransitedquantity() == null : this.getTransitedquantity().equals(other.getTransitedquantity()))
            && (this.getWithdrawquantity() == null ? other.getWithdrawquantity() == null : this.getWithdrawquantity().equals(other.getWithdrawquantity()))
            && (this.getAveragecost() == null ? other.getAveragecost() == null : this.getAveragecost().equals(other.getAveragecost()))
            && (this.getDilutedcost() == null ? other.getDilutedcost() == null : this.getDilutedcost().equals(other.getDilutedcost()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getAccumulatedbuyamount() == null ? other.getAccumulatedbuyamount() == null : this.getAccumulatedbuyamount().equals(other.getAccumulatedbuyamount()))
            && (this.getAccumulatedcashdividends() == null ? other.getAccumulatedcashdividends() == null : this.getAccumulatedcashdividends().equals(other.getAccumulatedcashdividends()))
            && (this.getRealizedgainloss() == null ? other.getRealizedgainloss() == null : this.getRealizedgainloss().equals(other.getRealizedgainloss()))
            && (this.getTotalgainloss() == null ? other.getTotalgainloss() == null : this.getTotalgainloss().equals(other.getTotalgainloss()))
            && (this.getTotalfee() == null ? other.getTotalfee() == null : this.getTotalfee().equals(other.getTotalfee()))
            && (this.getFlowid() == null ? other.getFlowid() == null : this.getFlowid().equals(other.getFlowid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHoldingid() == null) ? 0 : getHoldingid().hashCode());
        result = prime * result + ((getSubaccountid() == null) ? 0 : getSubaccountid().hashCode());
        result = prime * result + ((getFundcode() == null) ? 0 : getFundcode().hashCode());
        result = prime * result + ((getHoldingtype() == null) ? 0 : getHoldingtype().hashCode());
        result = prime * result + ((getTotalquantity() == null) ? 0 : getTotalquantity().hashCode());
        result = prime * result + ((getAvailablequantity() == null) ? 0 : getAvailablequantity().hashCode());
        result = prime * result + ((getFrozenquantity() == null) ? 0 : getFrozenquantity().hashCode());
        result = prime * result + ((getTransitedquantity() == null) ? 0 : getTransitedquantity().hashCode());
        result = prime * result + ((getWithdrawquantity() == null) ? 0 : getWithdrawquantity().hashCode());
        result = prime * result + ((getAveragecost() == null) ? 0 : getAveragecost().hashCode());
        result = prime * result + ((getDilutedcost() == null) ? 0 : getDilutedcost().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getAccumulatedbuyamount() == null) ? 0 : getAccumulatedbuyamount().hashCode());
        result = prime * result + ((getAccumulatedcashdividends() == null) ? 0 : getAccumulatedcashdividends().hashCode());
        result = prime * result + ((getRealizedgainloss() == null) ? 0 : getRealizedgainloss().hashCode());
        result = prime * result + ((getTotalgainloss() == null) ? 0 : getTotalgainloss().hashCode());
        result = prime * result + ((getTotalfee() == null) ? 0 : getTotalfee().hashCode());
        result = prime * result + ((getFlowid() == null) ? 0 : getFlowid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", holdingid=").append(holdingid);
        sb.append(", subaccountid=").append(subaccountid);
        sb.append(", fundcode=").append(fundcode);
        sb.append(", holdingtype=").append(holdingtype);
        sb.append(", totalquantity=").append(totalquantity);
        sb.append(", availablequantity=").append(availablequantity);
        sb.append(", frozenquantity=").append(frozenquantity);
        sb.append(", transitedquantity=").append(transitedquantity);
        sb.append(", withdrawquantity=").append(withdrawquantity);
        sb.append(", averagecost=").append(averagecost);
        sb.append(", dilutedcost=").append(dilutedcost);
        sb.append(", currency=").append(currency);
        sb.append(", accumulatedbuyamount=").append(accumulatedbuyamount);
        sb.append(", accumulatedcashdividends=").append(accumulatedcashdividends);
        sb.append(", realizedgainloss=").append(realizedgainloss);
        sb.append(", totalgainloss=").append(totalgainloss);
        sb.append(", totalfee=").append(totalfee);
        sb.append(", flowid=").append(flowid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
