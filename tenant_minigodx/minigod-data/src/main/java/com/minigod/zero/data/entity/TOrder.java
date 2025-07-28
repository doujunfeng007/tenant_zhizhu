package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName t_order
 */
public class TOrder implements Serializable {
    /**
     * 订单标识
     */
    private Integer orderid;

    /**
     * 指令标识
     */
    private Integer instructionid;

    /**
     * 归集订单标识
     */
    private Integer nomineeorderid;

    /**
     * 子账户标识
     */
    private String subaccountid;

    /**
     * 基金代码
     */
    private String fundcode;

    /**
     * 基金isin
     */
    private String fundisin;

    /**
     * 基金名称
     */
    private String fundname;

    /**
     * 下单时基金风险等级
     */
    private Integer fundrisklevel;

    /**
     * 是否私有基金 0: 否 1:是
     */
    private Integer isprivate;

    /**
     * 类型,1:买;2:卖;3:交换买;4:交换卖;
     */
    private Integer type;

    /**
     * 方式,1:金额;2:数量;3:权重比例;
     */
    private Integer mode;

    /**
     * 业务类型  0 基金 1 活利得 2 债券易
     */
    private Integer busitype;

    /**
     * 币种
     */
    private String currency;

    /**
     * 金额，不含费用、不含利息
     */
    private BigDecimal amount;

    /**
     * 权重
     */
    private BigDecimal weight;

    /**
     * 份额
     */
    private BigDecimal quantity;

    /**
     * 提交日期
     */
    private Date submitteddate;

    /**
     * 取消日期
     */
    private Date canceleddate;

    /**
     * 授权日期
     */
    private Date authorizeddate;

    /**
     * 拒绝日期
     */
    private Date rejecteddate;

    /**
     * 归集日期
     */
    private Date pooleddate;

    /**
     * 发送日期
     */
    private Date placeddate;

    /**
     * 接受日期
     */
    private Date accepteddate;

    /**
     * 确认日期
     */
    private Date confirmeddate;

    /**
     * 清算日期
     */
    private Date settleddate;

    /**
     * 预期清算日期
     */
    private Date expectedsettleddate;

    /**
     * 有效清算日期
     */
    private Date effectivesettleddate;

    /**
     * 订单过期时间（默认到第二个交易日有效）
     */
    private Date expireddate;

    /**
     * 预估成交价格
     */
    private BigDecimal estimatednav;

    /**
     *
     */
    private BigDecimal estimatedamount;

    /**
     *
     */
    private BigDecimal estimatedquantity;

    /**
     * 活利得、债券易卖出应收利息，债券易买入应付利息
     */
    private BigDecimal estimatedgainloss;

    /**
     * 预估日期
     */
    private Date estimatedasofday;

    /**
     * 交易币种
     */
    private String transactioncurrency;

    /**
     *
     */
    private BigDecimal transactionamount;

    /**
     *
     */
    private BigDecimal transactionnetamount;

    /**
     *
     */
    private BigDecimal transactionquantity;

    /**
     *
     */
    private BigDecimal transactionnav;

    /**
     * 活利得、债券易卖出应收利息，债券易买入应付利息
     */
    private BigDecimal transactiongainloss;

    /**
     * 累计费用（部分成交）
     */
    private BigDecimal transactionfee;

    /**
     * 累计印花税（部分成交）
     */
    private BigDecimal transactionstamptax;

    /**
     * 累计佣金（部分成交）
     */
    private BigDecimal transactionbrokerage;

    /**
     *
     */
    private BigDecimal yfffee;

    /**
     *
     */
    private BigDecimal yffrate;

    /**
     *
     */
    private BigDecimal agentfee;

    /**
     *
     */
    private BigDecimal fundhousefee;

    /**
     *
     */
    private BigDecimal fundfee;

    /**
     *
     */
    private BigDecimal performancefee;

    /**
     * 交易印花税
     */
    private BigDecimal stamptax;

    /**
     * 交易佣金
     */
    private BigDecimal brokerage;

    /**
     * 100 已保存；200 已提交；201 已提交（未冻结）；230 已发送（ta）；270 已确认；300 已结算；310 部分成交；500 已撤销；510 部分撤销；600失败; 720 过期作废；730 部分作废
     */
    private Integer status;

    /**
     * 100 已保存；200 已提交；201 已提交（未冻结）；230 已发送（ta）；270 已确认；300 已结算；310 部分成交；500 已撤销；510 部分撤销；600失败; 720 过期作废；730 部分作废
     */
    private Integer tastatus;

    /**
     * 状态描述
     */
    private String statusdesc;

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
     * 订单标识
     */
    public Integer getOrderid() {
        return orderid;
    }

    /**
     * 订单标识
     */
    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    /**
     * 指令标识
     */
    public Integer getInstructionid() {
        return instructionid;
    }

    /**
     * 指令标识
     */
    public void setInstructionid(Integer instructionid) {
        this.instructionid = instructionid;
    }

    /**
     * 归集订单标识
     */
    public Integer getNomineeorderid() {
        return nomineeorderid;
    }

    /**
     * 归集订单标识
     */
    public void setNomineeorderid(Integer nomineeorderid) {
        this.nomineeorderid = nomineeorderid;
    }

    /**
     * 子账户标识
     */
    public String getSubaccountid() {
        return subaccountid;
    }

    /**
     * 子账户标识
     */
    public void setSubaccountid(String subaccountid) {
        this.subaccountid = subaccountid;
    }

    /**
     * 基金代码
     */
    public String getFundcode() {
        return fundcode;
    }

    /**
     * 基金代码
     */
    public void setFundcode(String fundcode) {
        this.fundcode = fundcode;
    }

    /**
     * 基金isin
     */
    public String getFundisin() {
        return fundisin;
    }

    /**
     * 基金isin
     */
    public void setFundisin(String fundisin) {
        this.fundisin = fundisin;
    }

    /**
     * 基金名称
     */
    public String getFundname() {
        return fundname;
    }

    /**
     * 基金名称
     */
    public void setFundname(String fundname) {
        this.fundname = fundname;
    }

    /**
     * 下单时基金风险等级
     */
    public Integer getFundrisklevel() {
        return fundrisklevel;
    }

    /**
     * 下单时基金风险等级
     */
    public void setFundrisklevel(Integer fundrisklevel) {
        this.fundrisklevel = fundrisklevel;
    }

    /**
     * 是否私有基金 0: 否 1:是
     */
    public Integer getIsprivate() {
        return isprivate;
    }

    /**
     * 是否私有基金 0: 否 1:是
     */
    public void setIsprivate(Integer isprivate) {
        this.isprivate = isprivate;
    }

    /**
     * 类型,1:买;2:卖;3:交换买;4:交换卖;
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类型,1:买;2:卖;3:交换买;4:交换卖;
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 方式,1:金额;2:数量;3:权重比例;
     */
    public Integer getMode() {
        return mode;
    }

    /**
     * 方式,1:金额;2:数量;3:权重比例;
     */
    public void setMode(Integer mode) {
        this.mode = mode;
    }

    /**
     * 业务类型  0 基金 1 活利得 2 债券易
     */
    public Integer getBusitype() {
        return busitype;
    }

    /**
     * 业务类型  0 基金 1 活利得 2 债券易
     */
    public void setBusitype(Integer busitype) {
        this.busitype = busitype;
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
     * 金额，不含费用、不含利息
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 金额，不含费用、不含利息
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 权重
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 权重
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 份额
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * 份额
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * 提交日期
     */
    public Date getSubmitteddate() {
        return submitteddate;
    }

    /**
     * 提交日期
     */
    public void setSubmitteddate(Date submitteddate) {
        this.submitteddate = submitteddate;
    }

    /**
     * 取消日期
     */
    public Date getCanceleddate() {
        return canceleddate;
    }

    /**
     * 取消日期
     */
    public void setCanceleddate(Date canceleddate) {
        this.canceleddate = canceleddate;
    }

    /**
     * 授权日期
     */
    public Date getAuthorizeddate() {
        return authorizeddate;
    }

    /**
     * 授权日期
     */
    public void setAuthorizeddate(Date authorizeddate) {
        this.authorizeddate = authorizeddate;
    }

    /**
     * 拒绝日期
     */
    public Date getRejecteddate() {
        return rejecteddate;
    }

    /**
     * 拒绝日期
     */
    public void setRejecteddate(Date rejecteddate) {
        this.rejecteddate = rejecteddate;
    }

    /**
     * 归集日期
     */
    public Date getPooleddate() {
        return pooleddate;
    }

    /**
     * 归集日期
     */
    public void setPooleddate(Date pooleddate) {
        this.pooleddate = pooleddate;
    }

    /**
     * 发送日期
     */
    public Date getPlaceddate() {
        return placeddate;
    }

    /**
     * 发送日期
     */
    public void setPlaceddate(Date placeddate) {
        this.placeddate = placeddate;
    }

    /**
     * 接受日期
     */
    public Date getAccepteddate() {
        return accepteddate;
    }

    /**
     * 接受日期
     */
    public void setAccepteddate(Date accepteddate) {
        this.accepteddate = accepteddate;
    }

    /**
     * 确认日期
     */
    public Date getConfirmeddate() {
        return confirmeddate;
    }

    /**
     * 确认日期
     */
    public void setConfirmeddate(Date confirmeddate) {
        this.confirmeddate = confirmeddate;
    }

    /**
     * 清算日期
     */
    public Date getSettleddate() {
        return settleddate;
    }

    /**
     * 清算日期
     */
    public void setSettleddate(Date settleddate) {
        this.settleddate = settleddate;
    }

    /**
     * 预期清算日期
     */
    public Date getExpectedsettleddate() {
        return expectedsettleddate;
    }

    /**
     * 预期清算日期
     */
    public void setExpectedsettleddate(Date expectedsettleddate) {
        this.expectedsettleddate = expectedsettleddate;
    }

    /**
     * 有效清算日期
     */
    public Date getEffectivesettleddate() {
        return effectivesettleddate;
    }

    /**
     * 有效清算日期
     */
    public void setEffectivesettleddate(Date effectivesettleddate) {
        this.effectivesettleddate = effectivesettleddate;
    }

    /**
     * 订单过期时间（默认到第二个交易日有效）
     */
    public Date getExpireddate() {
        return expireddate;
    }

    /**
     * 订单过期时间（默认到第二个交易日有效）
     */
    public void setExpireddate(Date expireddate) {
        this.expireddate = expireddate;
    }

    /**
     * 预估成交价格
     */
    public BigDecimal getEstimatednav() {
        return estimatednav;
    }

    /**
     * 预估成交价格
     */
    public void setEstimatednav(BigDecimal estimatednav) {
        this.estimatednav = estimatednav;
    }

    /**
     *
     */
    public BigDecimal getEstimatedamount() {
        return estimatedamount;
    }

    /**
     *
     */
    public void setEstimatedamount(BigDecimal estimatedamount) {
        this.estimatedamount = estimatedamount;
    }

    /**
     *
     */
    public BigDecimal getEstimatedquantity() {
        return estimatedquantity;
    }

    /**
     *
     */
    public void setEstimatedquantity(BigDecimal estimatedquantity) {
        this.estimatedquantity = estimatedquantity;
    }

    /**
     * 活利得、债券易卖出应收利息，债券易买入应付利息
     */
    public BigDecimal getEstimatedgainloss() {
        return estimatedgainloss;
    }

    /**
     * 活利得、债券易卖出应收利息，债券易买入应付利息
     */
    public void setEstimatedgainloss(BigDecimal estimatedgainloss) {
        this.estimatedgainloss = estimatedgainloss;
    }

    /**
     * 预估日期
     */
    public Date getEstimatedasofday() {
        return estimatedasofday;
    }

    /**
     * 预估日期
     */
    public void setEstimatedasofday(Date estimatedasofday) {
        this.estimatedasofday = estimatedasofday;
    }

    /**
     * 交易币种
     */
    public String getTransactioncurrency() {
        return transactioncurrency;
    }

    /**
     * 交易币种
     */
    public void setTransactioncurrency(String transactioncurrency) {
        this.transactioncurrency = transactioncurrency;
    }

    /**
     *
     */
    public BigDecimal getTransactionamount() {
        return transactionamount;
    }

    /**
     *
     */
    public void setTransactionamount(BigDecimal transactionamount) {
        this.transactionamount = transactionamount;
    }

    /**
     *
     */
    public BigDecimal getTransactionnetamount() {
        return transactionnetamount;
    }

    /**
     *
     */
    public void setTransactionnetamount(BigDecimal transactionnetamount) {
        this.transactionnetamount = transactionnetamount;
    }

    /**
     *
     */
    public BigDecimal getTransactionquantity() {
        return transactionquantity;
    }

    /**
     *
     */
    public void setTransactionquantity(BigDecimal transactionquantity) {
        this.transactionquantity = transactionquantity;
    }

    /**
     *
     */
    public BigDecimal getTransactionnav() {
        return transactionnav;
    }

    /**
     *
     */
    public void setTransactionnav(BigDecimal transactionnav) {
        this.transactionnav = transactionnav;
    }

    /**
     * 活利得、债券易卖出应收利息，债券易买入应付利息
     */
    public BigDecimal getTransactiongainloss() {
        return transactiongainloss;
    }

    /**
     * 活利得、债券易卖出应收利息，债券易买入应付利息
     */
    public void setTransactiongainloss(BigDecimal transactiongainloss) {
        this.transactiongainloss = transactiongainloss;
    }

    /**
     * 累计费用（部分成交）
     */
    public BigDecimal getTransactionfee() {
        return transactionfee;
    }

    /**
     * 累计费用（部分成交）
     */
    public void setTransactionfee(BigDecimal transactionfee) {
        this.transactionfee = transactionfee;
    }

    /**
     * 累计印花税（部分成交）
     */
    public BigDecimal getTransactionstamptax() {
        return transactionstamptax;
    }

    /**
     * 累计印花税（部分成交）
     */
    public void setTransactionstamptax(BigDecimal transactionstamptax) {
        this.transactionstamptax = transactionstamptax;
    }

    /**
     * 累计佣金（部分成交）
     */
    public BigDecimal getTransactionbrokerage() {
        return transactionbrokerage;
    }

    /**
     * 累计佣金（部分成交）
     */
    public void setTransactionbrokerage(BigDecimal transactionbrokerage) {
        this.transactionbrokerage = transactionbrokerage;
    }

    /**
     *
     */
    public BigDecimal getYfffee() {
        return yfffee;
    }

    /**
     *
     */
    public void setYfffee(BigDecimal yfffee) {
        this.yfffee = yfffee;
    }

    /**
     *
     */
    public BigDecimal getYffrate() {
        return yffrate;
    }

    /**
     *
     */
    public void setYffrate(BigDecimal yffrate) {
        this.yffrate = yffrate;
    }

    /**
     *
     */
    public BigDecimal getAgentfee() {
        return agentfee;
    }

    /**
     *
     */
    public void setAgentfee(BigDecimal agentfee) {
        this.agentfee = agentfee;
    }

    /**
     *
     */
    public BigDecimal getFundhousefee() {
        return fundhousefee;
    }

    /**
     *
     */
    public void setFundhousefee(BigDecimal fundhousefee) {
        this.fundhousefee = fundhousefee;
    }

    /**
     *
     */
    public BigDecimal getFundfee() {
        return fundfee;
    }

    /**
     *
     */
    public void setFundfee(BigDecimal fundfee) {
        this.fundfee = fundfee;
    }

    /**
     *
     */
    public BigDecimal getPerformancefee() {
        return performancefee;
    }

    /**
     *
     */
    public void setPerformancefee(BigDecimal performancefee) {
        this.performancefee = performancefee;
    }

    /**
     * 交易印花税
     */
    public BigDecimal getStamptax() {
        return stamptax;
    }

    /**
     * 交易印花税
     */
    public void setStamptax(BigDecimal stamptax) {
        this.stamptax = stamptax;
    }

    /**
     * 交易佣金
     */
    public BigDecimal getBrokerage() {
        return brokerage;
    }

    /**
     * 交易佣金
     */
    public void setBrokerage(BigDecimal brokerage) {
        this.brokerage = brokerage;
    }

    /**
     * 100 已保存；200 已提交；201 已提交（未冻结）；230 已发送（ta）；270 已确认；300 已结算；310 部分成交；500 已撤销；510 部分撤销；600失败; 720 过期作废；730 部分作废
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 100 已保存；200 已提交；201 已提交（未冻结）；230 已发送（ta）；270 已确认；300 已结算；310 部分成交；500 已撤销；510 部分撤销；600失败; 720 过期作废；730 部分作废
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 100 已保存；200 已提交；201 已提交（未冻结）；230 已发送（ta）；270 已确认；300 已结算；310 部分成交；500 已撤销；510 部分撤销；600失败; 720 过期作废；730 部分作废
     */
    public Integer getTastatus() {
        return tastatus;
    }

    /**
     * 100 已保存；200 已提交；201 已提交（未冻结）；230 已发送（ta）；270 已确认；300 已结算；310 部分成交；500 已撤销；510 部分撤销；600失败; 720 过期作废；730 部分作废
     */
    public void setTastatus(Integer tastatus) {
        this.tastatus = tastatus;
    }

    /**
     * 状态描述
     */
    public String getStatusdesc() {
        return statusdesc;
    }

    /**
     * 状态描述
     */
    public void setStatusdesc(String statusdesc) {
        this.statusdesc = statusdesc;
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
        TOrder other = (TOrder) that;
        return (this.getOrderid() == null ? other.getOrderid() == null : this.getOrderid().equals(other.getOrderid()))
            && (this.getInstructionid() == null ? other.getInstructionid() == null : this.getInstructionid().equals(other.getInstructionid()))
            && (this.getNomineeorderid() == null ? other.getNomineeorderid() == null : this.getNomineeorderid().equals(other.getNomineeorderid()))
            && (this.getSubaccountid() == null ? other.getSubaccountid() == null : this.getSubaccountid().equals(other.getSubaccountid()))
            && (this.getFundcode() == null ? other.getFundcode() == null : this.getFundcode().equals(other.getFundcode()))
            && (this.getFundisin() == null ? other.getFundisin() == null : this.getFundisin().equals(other.getFundisin()))
            && (this.getFundname() == null ? other.getFundname() == null : this.getFundname().equals(other.getFundname()))
            && (this.getFundrisklevel() == null ? other.getFundrisklevel() == null : this.getFundrisklevel().equals(other.getFundrisklevel()))
            && (this.getIsprivate() == null ? other.getIsprivate() == null : this.getIsprivate().equals(other.getIsprivate()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getMode() == null ? other.getMode() == null : this.getMode().equals(other.getMode()))
            && (this.getBusitype() == null ? other.getBusitype() == null : this.getBusitype().equals(other.getBusitype()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
            && (this.getSubmitteddate() == null ? other.getSubmitteddate() == null : this.getSubmitteddate().equals(other.getSubmitteddate()))
            && (this.getCanceleddate() == null ? other.getCanceleddate() == null : this.getCanceleddate().equals(other.getCanceleddate()))
            && (this.getAuthorizeddate() == null ? other.getAuthorizeddate() == null : this.getAuthorizeddate().equals(other.getAuthorizeddate()))
            && (this.getRejecteddate() == null ? other.getRejecteddate() == null : this.getRejecteddate().equals(other.getRejecteddate()))
            && (this.getPooleddate() == null ? other.getPooleddate() == null : this.getPooleddate().equals(other.getPooleddate()))
            && (this.getPlaceddate() == null ? other.getPlaceddate() == null : this.getPlaceddate().equals(other.getPlaceddate()))
            && (this.getAccepteddate() == null ? other.getAccepteddate() == null : this.getAccepteddate().equals(other.getAccepteddate()))
            && (this.getConfirmeddate() == null ? other.getConfirmeddate() == null : this.getConfirmeddate().equals(other.getConfirmeddate()))
            && (this.getSettleddate() == null ? other.getSettleddate() == null : this.getSettleddate().equals(other.getSettleddate()))
            && (this.getExpectedsettleddate() == null ? other.getExpectedsettleddate() == null : this.getExpectedsettleddate().equals(other.getExpectedsettleddate()))
            && (this.getEffectivesettleddate() == null ? other.getEffectivesettleddate() == null : this.getEffectivesettleddate().equals(other.getEffectivesettleddate()))
            && (this.getExpireddate() == null ? other.getExpireddate() == null : this.getExpireddate().equals(other.getExpireddate()))
            && (this.getEstimatednav() == null ? other.getEstimatednav() == null : this.getEstimatednav().equals(other.getEstimatednav()))
            && (this.getEstimatedamount() == null ? other.getEstimatedamount() == null : this.getEstimatedamount().equals(other.getEstimatedamount()))
            && (this.getEstimatedquantity() == null ? other.getEstimatedquantity() == null : this.getEstimatedquantity().equals(other.getEstimatedquantity()))
            && (this.getEstimatedgainloss() == null ? other.getEstimatedgainloss() == null : this.getEstimatedgainloss().equals(other.getEstimatedgainloss()))
            && (this.getEstimatedasofday() == null ? other.getEstimatedasofday() == null : this.getEstimatedasofday().equals(other.getEstimatedasofday()))
            && (this.getTransactioncurrency() == null ? other.getTransactioncurrency() == null : this.getTransactioncurrency().equals(other.getTransactioncurrency()))
            && (this.getTransactionamount() == null ? other.getTransactionamount() == null : this.getTransactionamount().equals(other.getTransactionamount()))
            && (this.getTransactionnetamount() == null ? other.getTransactionnetamount() == null : this.getTransactionnetamount().equals(other.getTransactionnetamount()))
            && (this.getTransactionquantity() == null ? other.getTransactionquantity() == null : this.getTransactionquantity().equals(other.getTransactionquantity()))
            && (this.getTransactionnav() == null ? other.getTransactionnav() == null : this.getTransactionnav().equals(other.getTransactionnav()))
            && (this.getTransactiongainloss() == null ? other.getTransactiongainloss() == null : this.getTransactiongainloss().equals(other.getTransactiongainloss()))
            && (this.getTransactionfee() == null ? other.getTransactionfee() == null : this.getTransactionfee().equals(other.getTransactionfee()))
            && (this.getTransactionstamptax() == null ? other.getTransactionstamptax() == null : this.getTransactionstamptax().equals(other.getTransactionstamptax()))
            && (this.getTransactionbrokerage() == null ? other.getTransactionbrokerage() == null : this.getTransactionbrokerage().equals(other.getTransactionbrokerage()))
            && (this.getYfffee() == null ? other.getYfffee() == null : this.getYfffee().equals(other.getYfffee()))
            && (this.getYffrate() == null ? other.getYffrate() == null : this.getYffrate().equals(other.getYffrate()))
            && (this.getAgentfee() == null ? other.getAgentfee() == null : this.getAgentfee().equals(other.getAgentfee()))
            && (this.getFundhousefee() == null ? other.getFundhousefee() == null : this.getFundhousefee().equals(other.getFundhousefee()))
            && (this.getFundfee() == null ? other.getFundfee() == null : this.getFundfee().equals(other.getFundfee()))
            && (this.getPerformancefee() == null ? other.getPerformancefee() == null : this.getPerformancefee().equals(other.getPerformancefee()))
            && (this.getStamptax() == null ? other.getStamptax() == null : this.getStamptax().equals(other.getStamptax()))
            && (this.getBrokerage() == null ? other.getBrokerage() == null : this.getBrokerage().equals(other.getBrokerage()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getTastatus() == null ? other.getTastatus() == null : this.getTastatus().equals(other.getTastatus()))
            && (this.getStatusdesc() == null ? other.getStatusdesc() == null : this.getStatusdesc().equals(other.getStatusdesc()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderid() == null) ? 0 : getOrderid().hashCode());
        result = prime * result + ((getInstructionid() == null) ? 0 : getInstructionid().hashCode());
        result = prime * result + ((getNomineeorderid() == null) ? 0 : getNomineeorderid().hashCode());
        result = prime * result + ((getSubaccountid() == null) ? 0 : getSubaccountid().hashCode());
        result = prime * result + ((getFundcode() == null) ? 0 : getFundcode().hashCode());
        result = prime * result + ((getFundisin() == null) ? 0 : getFundisin().hashCode());
        result = prime * result + ((getFundname() == null) ? 0 : getFundname().hashCode());
        result = prime * result + ((getFundrisklevel() == null) ? 0 : getFundrisklevel().hashCode());
        result = prime * result + ((getIsprivate() == null) ? 0 : getIsprivate().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getMode() == null) ? 0 : getMode().hashCode());
        result = prime * result + ((getBusitype() == null) ? 0 : getBusitype().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getSubmitteddate() == null) ? 0 : getSubmitteddate().hashCode());
        result = prime * result + ((getCanceleddate() == null) ? 0 : getCanceleddate().hashCode());
        result = prime * result + ((getAuthorizeddate() == null) ? 0 : getAuthorizeddate().hashCode());
        result = prime * result + ((getRejecteddate() == null) ? 0 : getRejecteddate().hashCode());
        result = prime * result + ((getPooleddate() == null) ? 0 : getPooleddate().hashCode());
        result = prime * result + ((getPlaceddate() == null) ? 0 : getPlaceddate().hashCode());
        result = prime * result + ((getAccepteddate() == null) ? 0 : getAccepteddate().hashCode());
        result = prime * result + ((getConfirmeddate() == null) ? 0 : getConfirmeddate().hashCode());
        result = prime * result + ((getSettleddate() == null) ? 0 : getSettleddate().hashCode());
        result = prime * result + ((getExpectedsettleddate() == null) ? 0 : getExpectedsettleddate().hashCode());
        result = prime * result + ((getEffectivesettleddate() == null) ? 0 : getEffectivesettleddate().hashCode());
        result = prime * result + ((getExpireddate() == null) ? 0 : getExpireddate().hashCode());
        result = prime * result + ((getEstimatednav() == null) ? 0 : getEstimatednav().hashCode());
        result = prime * result + ((getEstimatedamount() == null) ? 0 : getEstimatedamount().hashCode());
        result = prime * result + ((getEstimatedquantity() == null) ? 0 : getEstimatedquantity().hashCode());
        result = prime * result + ((getEstimatedgainloss() == null) ? 0 : getEstimatedgainloss().hashCode());
        result = prime * result + ((getEstimatedasofday() == null) ? 0 : getEstimatedasofday().hashCode());
        result = prime * result + ((getTransactioncurrency() == null) ? 0 : getTransactioncurrency().hashCode());
        result = prime * result + ((getTransactionamount() == null) ? 0 : getTransactionamount().hashCode());
        result = prime * result + ((getTransactionnetamount() == null) ? 0 : getTransactionnetamount().hashCode());
        result = prime * result + ((getTransactionquantity() == null) ? 0 : getTransactionquantity().hashCode());
        result = prime * result + ((getTransactionnav() == null) ? 0 : getTransactionnav().hashCode());
        result = prime * result + ((getTransactiongainloss() == null) ? 0 : getTransactiongainloss().hashCode());
        result = prime * result + ((getTransactionfee() == null) ? 0 : getTransactionfee().hashCode());
        result = prime * result + ((getTransactionstamptax() == null) ? 0 : getTransactionstamptax().hashCode());
        result = prime * result + ((getTransactionbrokerage() == null) ? 0 : getTransactionbrokerage().hashCode());
        result = prime * result + ((getYfffee() == null) ? 0 : getYfffee().hashCode());
        result = prime * result + ((getYffrate() == null) ? 0 : getYffrate().hashCode());
        result = prime * result + ((getAgentfee() == null) ? 0 : getAgentfee().hashCode());
        result = prime * result + ((getFundhousefee() == null) ? 0 : getFundhousefee().hashCode());
        result = prime * result + ((getFundfee() == null) ? 0 : getFundfee().hashCode());
        result = prime * result + ((getPerformancefee() == null) ? 0 : getPerformancefee().hashCode());
        result = prime * result + ((getStamptax() == null) ? 0 : getStamptax().hashCode());
        result = prime * result + ((getBrokerage() == null) ? 0 : getBrokerage().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTastatus() == null) ? 0 : getTastatus().hashCode());
        result = prime * result + ((getStatusdesc() == null) ? 0 : getStatusdesc().hashCode());
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
        sb.append(", orderid=").append(orderid);
        sb.append(", instructionid=").append(instructionid);
        sb.append(", nomineeorderid=").append(nomineeorderid);
        sb.append(", subaccountid=").append(subaccountid);
        sb.append(", fundcode=").append(fundcode);
        sb.append(", fundisin=").append(fundisin);
        sb.append(", fundname=").append(fundname);
        sb.append(", fundrisklevel=").append(fundrisklevel);
        sb.append(", isprivate=").append(isprivate);
        sb.append(", type=").append(type);
        sb.append(", mode=").append(mode);
        sb.append(", busitype=").append(busitype);
        sb.append(", currency=").append(currency);
        sb.append(", amount=").append(amount);
        sb.append(", weight=").append(weight);
        sb.append(", quantity=").append(quantity);
        sb.append(", submitteddate=").append(submitteddate);
        sb.append(", canceleddate=").append(canceleddate);
        sb.append(", authorizeddate=").append(authorizeddate);
        sb.append(", rejecteddate=").append(rejecteddate);
        sb.append(", pooleddate=").append(pooleddate);
        sb.append(", placeddate=").append(placeddate);
        sb.append(", accepteddate=").append(accepteddate);
        sb.append(", confirmeddate=").append(confirmeddate);
        sb.append(", settleddate=").append(settleddate);
        sb.append(", expectedsettleddate=").append(expectedsettleddate);
        sb.append(", effectivesettleddate=").append(effectivesettleddate);
        sb.append(", expireddate=").append(expireddate);
        sb.append(", estimatednav=").append(estimatednav);
        sb.append(", estimatedamount=").append(estimatedamount);
        sb.append(", estimatedquantity=").append(estimatedquantity);
        sb.append(", estimatedgainloss=").append(estimatedgainloss);
        sb.append(", estimatedasofday=").append(estimatedasofday);
        sb.append(", transactioncurrency=").append(transactioncurrency);
        sb.append(", transactionamount=").append(transactionamount);
        sb.append(", transactionnetamount=").append(transactionnetamount);
        sb.append(", transactionquantity=").append(transactionquantity);
        sb.append(", transactionnav=").append(transactionnav);
        sb.append(", transactiongainloss=").append(transactiongainloss);
        sb.append(", transactionfee=").append(transactionfee);
        sb.append(", transactionstamptax=").append(transactionstamptax);
        sb.append(", transactionbrokerage=").append(transactionbrokerage);
        sb.append(", yfffee=").append(yfffee);
        sb.append(", yffrate=").append(yffrate);
        sb.append(", agentfee=").append(agentfee);
        sb.append(", fundhousefee=").append(fundhousefee);
        sb.append(", fundfee=").append(fundfee);
        sb.append(", performancefee=").append(performancefee);
        sb.append(", stamptax=").append(stamptax);
        sb.append(", brokerage=").append(brokerage);
        sb.append(", status=").append(status);
        sb.append(", tastatus=").append(tastatus);
        sb.append(", statusdesc=").append(statusdesc);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
