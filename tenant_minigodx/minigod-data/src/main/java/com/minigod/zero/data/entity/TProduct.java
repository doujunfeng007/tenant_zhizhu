package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品表
 * @TableName t_product
 */
public class TProduct implements Serializable {
    /**
     *
     */
    private Integer productid;

    /**
     * 产品ISIN编码
     */
    private String isin;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 保证息率
     */
    private BigDecimal fixinterestrate;

    /**
     * 买入费率
     */
    private BigDecimal buyingrate;

    /**
     * 卖出费率
     */
    private BigDecimal sellingrate;

    /**
     * 币种
     */
    private String currency;

    /**
     * 默认单价
     */
    private BigDecimal nominalvalue;

    /**
     * 面值(默认100)
     */
    private BigDecimal facevalue;

    /**
     * 估值
     */
    private BigDecimal appraisement;

    /**
     * 发行份额
     */
    private BigDecimal unit;

    /**
     * 发行规模
     */
    private BigDecimal issueamount;

    /**
     * 交易/认购单位 最低认购份额 100份起，可+1
     */
    private BigDecimal tradingunit;

    /**
     * 发行日期
     */
    private Date issuedate;

    /**
     * 到期日即最后交易日
     */
    private Date maturitydate;

    /**
     * 产品风险等级 1-5
     */
    private Integer risklevel;

    /**
     * 利息计算天数
     */
    private Integer interestcalculationdays;

    /**
     * 清盘日
     */
    private Date liquidationdate;

    /**
     * 开盘时间
     */
    private Date marketopentime;

    /**
     * 收盘时间
     */
    private Date closingtime;

    /**
     * 发行人
     */
    private String issuer;

    /**
     * 产品描述或产品档案
     */
    private String productdescription;

    /**
     * 交易印花税
     */
    private BigDecimal stamptax;

    /**
     * 经纪佣金
     */
    private BigDecimal brokeragecharges;

    /**
     * 限价盘 1 是 0 否
     */
    private Integer limitorder;

    /**
     * 产品类型 64 活利得 65 债券易
     */
    private Integer type;

    /**
     * 产品审核状态 1 初次提交保存 2 上架待审核（点击上架申请） 3 通过即上架 4 拒绝上架 5 下架审核中 6 已下架 7 拒绝下架
     */
    private Integer auditstatus;

    /**
     * 0-不可交易, 1-可进行买卖交易, 2-仅支持购买, 3-仅支持出售
     */
    private Integer status;

    /**
     * 风险提示 1 正常 2 迟付利息 3 到期未兑付
     */
    private Integer riskwarning;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 最后更新人
     */
    private String lastupdatedby;

    /**
     * 同步状态 1 已同步 2 未同步 3 已更新 4 未更新
     */
    private Integer synchstate;

    /**
     * 0 未到期；1 到期并已经派息
     */
    private Integer maturitystatus;

    /**
     *
     */
    private Date createtime;

    /**
     *
     */
    private Date updatetime;

    /**
     * 结算日
     */
    private Date settlementdate;

    /**
     * 清算价格
     */
    private BigDecimal clearingvalue;

    /**
     * 上架时间
     */
    private Date listingtime;

    /**
     * 产品自定义表单字段
     */
    private String customerform;

    /**
     * 初始购买最小份额目前默认100份起
     */
    private BigDecimal mininitsubscriptionquantity;

    /**
     * IPO阶段最低申购份额
     */
    private BigDecimal ipominsubscriptionquantity;

    /**
     * 产品简称
     */
    private String productabbr;

    /**
     * 0 未清算, 1 已清算
     */
    private Integer clearingstatus;

    /**
     * 清算备注
     */
    private String clearingremark;

    /**
     * 赎回份额
     */
    private BigDecimal redemptionunit;

    /**
     * 机构账号
     */
    private String corporateaccount;

    /**
     * 底层债券 id
     */
    private Integer underlyingbondid;

    /**
     * 发行文件 url
     */
    private String documenturl;

    /**
     * 产品档案 url
     */
    private String archivesurl;

    /**
     * 下一息率调整日
     */
    private Date nextinterestdate;

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Integer getProductid() {
        return productid;
    }

    /**
     *
     */
    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    /**
     * 产品ISIN编码
     */
    public String getIsin() {
        return isin;
    }

    /**
     * 产品ISIN编码
     */
    public void setIsin(String isin) {
        this.isin = isin;
    }

    /**
     * 产品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 产品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 保证息率
     */
    public BigDecimal getFixinterestrate() {
        return fixinterestrate;
    }

    /**
     * 保证息率
     */
    public void setFixinterestrate(BigDecimal fixinterestrate) {
        this.fixinterestrate = fixinterestrate;
    }

    /**
     * 买入费率
     */
    public BigDecimal getBuyingrate() {
        return buyingrate;
    }

    /**
     * 买入费率
     */
    public void setBuyingrate(BigDecimal buyingrate) {
        this.buyingrate = buyingrate;
    }

    /**
     * 卖出费率
     */
    public BigDecimal getSellingrate() {
        return sellingrate;
    }

    /**
     * 卖出费率
     */
    public void setSellingrate(BigDecimal sellingrate) {
        this.sellingrate = sellingrate;
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
     * 默认单价
     */
    public BigDecimal getNominalvalue() {
        return nominalvalue;
    }

    /**
     * 默认单价
     */
    public void setNominalvalue(BigDecimal nominalvalue) {
        this.nominalvalue = nominalvalue;
    }

    /**
     * 面值(默认100)
     */
    public BigDecimal getFacevalue() {
        return facevalue;
    }

    /**
     * 面值(默认100)
     */
    public void setFacevalue(BigDecimal facevalue) {
        this.facevalue = facevalue;
    }

    /**
     * 估值
     */
    public BigDecimal getAppraisement() {
        return appraisement;
    }

    /**
     * 估值
     */
    public void setAppraisement(BigDecimal appraisement) {
        this.appraisement = appraisement;
    }

    /**
     * 发行份额
     */
    public BigDecimal getUnit() {
        return unit;
    }

    /**
     * 发行份额
     */
    public void setUnit(BigDecimal unit) {
        this.unit = unit;
    }

    /**
     * 发行规模
     */
    public BigDecimal getIssueamount() {
        return issueamount;
    }

    /**
     * 发行规模
     */
    public void setIssueamount(BigDecimal issueamount) {
        this.issueamount = issueamount;
    }

    /**
     * 交易/认购单位 最低认购份额 100份起，可+1
     */
    public BigDecimal getTradingunit() {
        return tradingunit;
    }

    /**
     * 交易/认购单位 最低认购份额 100份起，可+1
     */
    public void setTradingunit(BigDecimal tradingunit) {
        this.tradingunit = tradingunit;
    }

    /**
     * 发行日期
     */
    public Date getIssuedate() {
        return issuedate;
    }

    /**
     * 发行日期
     */
    public void setIssuedate(Date issuedate) {
        this.issuedate = issuedate;
    }

    /**
     * 到期日即最后交易日
     */
    public Date getMaturitydate() {
        return maturitydate;
    }

    /**
     * 到期日即最后交易日
     */
    public void setMaturitydate(Date maturitydate) {
        this.maturitydate = maturitydate;
    }

    /**
     * 产品风险等级 1-5
     */
    public Integer getRisklevel() {
        return risklevel;
    }

    /**
     * 产品风险等级 1-5
     */
    public void setRisklevel(Integer risklevel) {
        this.risklevel = risklevel;
    }

    /**
     * 利息计算天数
     */
    public Integer getInterestcalculationdays() {
        return interestcalculationdays;
    }

    /**
     * 利息计算天数
     */
    public void setInterestcalculationdays(Integer interestcalculationdays) {
        this.interestcalculationdays = interestcalculationdays;
    }

    /**
     * 清盘日
     */
    public Date getLiquidationdate() {
        return liquidationdate;
    }

    /**
     * 清盘日
     */
    public void setLiquidationdate(Date liquidationdate) {
        this.liquidationdate = liquidationdate;
    }

    /**
     * 开盘时间
     */
    public Date getMarketopentime() {
        return marketopentime;
    }

    /**
     * 开盘时间
     */
    public void setMarketopentime(Date marketopentime) {
        this.marketopentime = marketopentime;
    }

    /**
     * 收盘时间
     */
    public Date getClosingtime() {
        return closingtime;
    }

    /**
     * 收盘时间
     */
    public void setClosingtime(Date closingtime) {
        this.closingtime = closingtime;
    }

    /**
     * 发行人
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * 发行人
     */
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    /**
     * 产品描述或产品档案
     */
    public String getProductdescription() {
        return productdescription;
    }

    /**
     * 产品描述或产品档案
     */
    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
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
     * 经纪佣金
     */
    public BigDecimal getBrokeragecharges() {
        return brokeragecharges;
    }

    /**
     * 经纪佣金
     */
    public void setBrokeragecharges(BigDecimal brokeragecharges) {
        this.brokeragecharges = brokeragecharges;
    }

    /**
     * 限价盘 1 是 0 否
     */
    public Integer getLimitorder() {
        return limitorder;
    }

    /**
     * 限价盘 1 是 0 否
     */
    public void setLimitorder(Integer limitorder) {
        this.limitorder = limitorder;
    }

    /**
     * 产品类型 64 活利得 65 债券易
     */
    public Integer getType() {
        return type;
    }

    /**
     * 产品类型 64 活利得 65 债券易
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 产品审核状态 1 初次提交保存 2 上架待审核（点击上架申请） 3 通过即上架 4 拒绝上架 5 下架审核中 6 已下架 7 拒绝下架
     */
    public Integer getAuditstatus() {
        return auditstatus;
    }

    /**
     * 产品审核状态 1 初次提交保存 2 上架待审核（点击上架申请） 3 通过即上架 4 拒绝上架 5 下架审核中 6 已下架 7 拒绝下架
     */
    public void setAuditstatus(Integer auditstatus) {
        this.auditstatus = auditstatus;
    }

    /**
     * 0-不可交易, 1-可进行买卖交易, 2-仅支持购买, 3-仅支持出售
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0-不可交易, 1-可进行买卖交易, 2-仅支持购买, 3-仅支持出售
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 风险提示 1 正常 2 迟付利息 3 到期未兑付
     */
    public Integer getRiskwarning() {
        return riskwarning;
    }

    /**
     * 风险提示 1 正常 2 迟付利息 3 到期未兑付
     */
    public void setRiskwarning(Integer riskwarning) {
        this.riskwarning = riskwarning;
    }

    /**
     * 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 最后更新人
     */
    public String getLastupdatedby() {
        return lastupdatedby;
    }

    /**
     * 最后更新人
     */
    public void setLastupdatedby(String lastupdatedby) {
        this.lastupdatedby = lastupdatedby;
    }

    /**
     * 同步状态 1 已同步 2 未同步 3 已更新 4 未更新
     */
    public Integer getSynchstate() {
        return synchstate;
    }

    /**
     * 同步状态 1 已同步 2 未同步 3 已更新 4 未更新
     */
    public void setSynchstate(Integer synchstate) {
        this.synchstate = synchstate;
    }

    /**
     * 0 未到期；1 到期并已经派息
     */
    public Integer getMaturitystatus() {
        return maturitystatus;
    }

    /**
     * 0 未到期；1 到期并已经派息
     */
    public void setMaturitystatus(Integer maturitystatus) {
        this.maturitystatus = maturitystatus;
    }

    /**
     *
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     *
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     *
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     *
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * 结算日
     */
    public Date getSettlementdate() {
        return settlementdate;
    }

    /**
     * 结算日
     */
    public void setSettlementdate(Date settlementdate) {
        this.settlementdate = settlementdate;
    }

    /**
     * 清算价格
     */
    public BigDecimal getClearingvalue() {
        return clearingvalue;
    }

    /**
     * 清算价格
     */
    public void setClearingvalue(BigDecimal clearingvalue) {
        this.clearingvalue = clearingvalue;
    }

    /**
     * 上架时间
     */
    public Date getListingtime() {
        return listingtime;
    }

    /**
     * 上架时间
     */
    public void setListingtime(Date listingtime) {
        this.listingtime = listingtime;
    }

    /**
     * 产品自定义表单字段
     */
    public String getCustomerform() {
        return customerform;
    }

    /**
     * 产品自定义表单字段
     */
    public void setCustomerform(String customerform) {
        this.customerform = customerform;
    }

    /**
     * 初始购买最小份额目前默认100份起
     */
    public BigDecimal getMininitsubscriptionquantity() {
        return mininitsubscriptionquantity;
    }

    /**
     * 初始购买最小份额目前默认100份起
     */
    public void setMininitsubscriptionquantity(BigDecimal mininitsubscriptionquantity) {
        this.mininitsubscriptionquantity = mininitsubscriptionquantity;
    }

    /**
     * IPO阶段最低申购份额
     */
    public BigDecimal getIpominsubscriptionquantity() {
        return ipominsubscriptionquantity;
    }

    /**
     * IPO阶段最低申购份额
     */
    public void setIpominsubscriptionquantity(BigDecimal ipominsubscriptionquantity) {
        this.ipominsubscriptionquantity = ipominsubscriptionquantity;
    }

    /**
     * 产品简称
     */
    public String getProductabbr() {
        return productabbr;
    }

    /**
     * 产品简称
     */
    public void setProductabbr(String productabbr) {
        this.productabbr = productabbr;
    }

    /**
     * 0 未清算, 1 已清算
     */
    public Integer getClearingstatus() {
        return clearingstatus;
    }

    /**
     * 0 未清算, 1 已清算
     */
    public void setClearingstatus(Integer clearingstatus) {
        this.clearingstatus = clearingstatus;
    }

    /**
     * 清算备注
     */
    public String getClearingremark() {
        return clearingremark;
    }

    /**
     * 清算备注
     */
    public void setClearingremark(String clearingremark) {
        this.clearingremark = clearingremark;
    }

    /**
     * 赎回份额
     */
    public BigDecimal getRedemptionunit() {
        return redemptionunit;
    }

    /**
     * 赎回份额
     */
    public void setRedemptionunit(BigDecimal redemptionunit) {
        this.redemptionunit = redemptionunit;
    }

    /**
     * 机构账号
     */
    public String getCorporateaccount() {
        return corporateaccount;
    }

    /**
     * 机构账号
     */
    public void setCorporateaccount(String corporateaccount) {
        this.corporateaccount = corporateaccount;
    }

    /**
     * 底层债券 id
     */
    public Integer getUnderlyingbondid() {
        return underlyingbondid;
    }

    /**
     * 底层债券 id
     */
    public void setUnderlyingbondid(Integer underlyingbondid) {
        this.underlyingbondid = underlyingbondid;
    }

    /**
     * 发行文件 url
     */
    public String getDocumenturl() {
        return documenturl;
    }

    /**
     * 发行文件 url
     */
    public void setDocumenturl(String documenturl) {
        this.documenturl = documenturl;
    }

    /**
     * 产品档案 url
     */
    public String getArchivesurl() {
        return archivesurl;
    }

    /**
     * 产品档案 url
     */
    public void setArchivesurl(String archivesurl) {
        this.archivesurl = archivesurl;
    }

    /**
     * 下一息率调整日
     */
    public Date getNextinterestdate() {
        return nextinterestdate;
    }

    /**
     * 下一息率调整日
     */
    public void setNextinterestdate(Date nextinterestdate) {
        this.nextinterestdate = nextinterestdate;
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
        TProduct other = (TProduct) that;
        return (this.getProductid() == null ? other.getProductid() == null : this.getProductid().equals(other.getProductid()))
            && (this.getIsin() == null ? other.getIsin() == null : this.getIsin().equals(other.getIsin()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFixinterestrate() == null ? other.getFixinterestrate() == null : this.getFixinterestrate().equals(other.getFixinterestrate()))
            && (this.getBuyingrate() == null ? other.getBuyingrate() == null : this.getBuyingrate().equals(other.getBuyingrate()))
            && (this.getSellingrate() == null ? other.getSellingrate() == null : this.getSellingrate().equals(other.getSellingrate()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getNominalvalue() == null ? other.getNominalvalue() == null : this.getNominalvalue().equals(other.getNominalvalue()))
            && (this.getFacevalue() == null ? other.getFacevalue() == null : this.getFacevalue().equals(other.getFacevalue()))
            && (this.getAppraisement() == null ? other.getAppraisement() == null : this.getAppraisement().equals(other.getAppraisement()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getIssueamount() == null ? other.getIssueamount() == null : this.getIssueamount().equals(other.getIssueamount()))
            && (this.getTradingunit() == null ? other.getTradingunit() == null : this.getTradingunit().equals(other.getTradingunit()))
            && (this.getIssuedate() == null ? other.getIssuedate() == null : this.getIssuedate().equals(other.getIssuedate()))
            && (this.getMaturitydate() == null ? other.getMaturitydate() == null : this.getMaturitydate().equals(other.getMaturitydate()))
            && (this.getRisklevel() == null ? other.getRisklevel() == null : this.getRisklevel().equals(other.getRisklevel()))
            && (this.getInterestcalculationdays() == null ? other.getInterestcalculationdays() == null : this.getInterestcalculationdays().equals(other.getInterestcalculationdays()))
            && (this.getLiquidationdate() == null ? other.getLiquidationdate() == null : this.getLiquidationdate().equals(other.getLiquidationdate()))
            && (this.getMarketopentime() == null ? other.getMarketopentime() == null : this.getMarketopentime().equals(other.getMarketopentime()))
            && (this.getClosingtime() == null ? other.getClosingtime() == null : this.getClosingtime().equals(other.getClosingtime()))
            && (this.getIssuer() == null ? other.getIssuer() == null : this.getIssuer().equals(other.getIssuer()))
            && (this.getProductdescription() == null ? other.getProductdescription() == null : this.getProductdescription().equals(other.getProductdescription()))
            && (this.getStamptax() == null ? other.getStamptax() == null : this.getStamptax().equals(other.getStamptax()))
            && (this.getBrokeragecharges() == null ? other.getBrokeragecharges() == null : this.getBrokeragecharges().equals(other.getBrokeragecharges()))
            && (this.getLimitorder() == null ? other.getLimitorder() == null : this.getLimitorder().equals(other.getLimitorder()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getAuditstatus() == null ? other.getAuditstatus() == null : this.getAuditstatus().equals(other.getAuditstatus()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getRiskwarning() == null ? other.getRiskwarning() == null : this.getRiskwarning().equals(other.getRiskwarning()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getLastupdatedby() == null ? other.getLastupdatedby() == null : this.getLastupdatedby().equals(other.getLastupdatedby()))
            && (this.getSynchstate() == null ? other.getSynchstate() == null : this.getSynchstate().equals(other.getSynchstate()))
            && (this.getMaturitystatus() == null ? other.getMaturitystatus() == null : this.getMaturitystatus().equals(other.getMaturitystatus()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getSettlementdate() == null ? other.getSettlementdate() == null : this.getSettlementdate().equals(other.getSettlementdate()))
            && (this.getClearingvalue() == null ? other.getClearingvalue() == null : this.getClearingvalue().equals(other.getClearingvalue()))
            && (this.getListingtime() == null ? other.getListingtime() == null : this.getListingtime().equals(other.getListingtime()))
            && (this.getCustomerform() == null ? other.getCustomerform() == null : this.getCustomerform().equals(other.getCustomerform()))
            && (this.getMininitsubscriptionquantity() == null ? other.getMininitsubscriptionquantity() == null : this.getMininitsubscriptionquantity().equals(other.getMininitsubscriptionquantity()))
            && (this.getIpominsubscriptionquantity() == null ? other.getIpominsubscriptionquantity() == null : this.getIpominsubscriptionquantity().equals(other.getIpominsubscriptionquantity()))
            && (this.getProductabbr() == null ? other.getProductabbr() == null : this.getProductabbr().equals(other.getProductabbr()))
            && (this.getClearingstatus() == null ? other.getClearingstatus() == null : this.getClearingstatus().equals(other.getClearingstatus()))
            && (this.getClearingremark() == null ? other.getClearingremark() == null : this.getClearingremark().equals(other.getClearingremark()))
            && (this.getRedemptionunit() == null ? other.getRedemptionunit() == null : this.getRedemptionunit().equals(other.getRedemptionunit()))
            && (this.getCorporateaccount() == null ? other.getCorporateaccount() == null : this.getCorporateaccount().equals(other.getCorporateaccount()))
            && (this.getUnderlyingbondid() == null ? other.getUnderlyingbondid() == null : this.getUnderlyingbondid().equals(other.getUnderlyingbondid()))
            && (this.getDocumenturl() == null ? other.getDocumenturl() == null : this.getDocumenturl().equals(other.getDocumenturl()))
            && (this.getArchivesurl() == null ? other.getArchivesurl() == null : this.getArchivesurl().equals(other.getArchivesurl()))
            && (this.getNextinterestdate() == null ? other.getNextinterestdate() == null : this.getNextinterestdate().equals(other.getNextinterestdate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProductid() == null) ? 0 : getProductid().hashCode());
        result = prime * result + ((getIsin() == null) ? 0 : getIsin().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFixinterestrate() == null) ? 0 : getFixinterestrate().hashCode());
        result = prime * result + ((getBuyingrate() == null) ? 0 : getBuyingrate().hashCode());
        result = prime * result + ((getSellingrate() == null) ? 0 : getSellingrate().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getNominalvalue() == null) ? 0 : getNominalvalue().hashCode());
        result = prime * result + ((getFacevalue() == null) ? 0 : getFacevalue().hashCode());
        result = prime * result + ((getAppraisement() == null) ? 0 : getAppraisement().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getIssueamount() == null) ? 0 : getIssueamount().hashCode());
        result = prime * result + ((getTradingunit() == null) ? 0 : getTradingunit().hashCode());
        result = prime * result + ((getIssuedate() == null) ? 0 : getIssuedate().hashCode());
        result = prime * result + ((getMaturitydate() == null) ? 0 : getMaturitydate().hashCode());
        result = prime * result + ((getRisklevel() == null) ? 0 : getRisklevel().hashCode());
        result = prime * result + ((getInterestcalculationdays() == null) ? 0 : getInterestcalculationdays().hashCode());
        result = prime * result + ((getLiquidationdate() == null) ? 0 : getLiquidationdate().hashCode());
        result = prime * result + ((getMarketopentime() == null) ? 0 : getMarketopentime().hashCode());
        result = prime * result + ((getClosingtime() == null) ? 0 : getClosingtime().hashCode());
        result = prime * result + ((getIssuer() == null) ? 0 : getIssuer().hashCode());
        result = prime * result + ((getProductdescription() == null) ? 0 : getProductdescription().hashCode());
        result = prime * result + ((getStamptax() == null) ? 0 : getStamptax().hashCode());
        result = prime * result + ((getBrokeragecharges() == null) ? 0 : getBrokeragecharges().hashCode());
        result = prime * result + ((getLimitorder() == null) ? 0 : getLimitorder().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getAuditstatus() == null) ? 0 : getAuditstatus().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRiskwarning() == null) ? 0 : getRiskwarning().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getLastupdatedby() == null) ? 0 : getLastupdatedby().hashCode());
        result = prime * result + ((getSynchstate() == null) ? 0 : getSynchstate().hashCode());
        result = prime * result + ((getMaturitystatus() == null) ? 0 : getMaturitystatus().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getSettlementdate() == null) ? 0 : getSettlementdate().hashCode());
        result = prime * result + ((getClearingvalue() == null) ? 0 : getClearingvalue().hashCode());
        result = prime * result + ((getListingtime() == null) ? 0 : getListingtime().hashCode());
        result = prime * result + ((getCustomerform() == null) ? 0 : getCustomerform().hashCode());
        result = prime * result + ((getMininitsubscriptionquantity() == null) ? 0 : getMininitsubscriptionquantity().hashCode());
        result = prime * result + ((getIpominsubscriptionquantity() == null) ? 0 : getIpominsubscriptionquantity().hashCode());
        result = prime * result + ((getProductabbr() == null) ? 0 : getProductabbr().hashCode());
        result = prime * result + ((getClearingstatus() == null) ? 0 : getClearingstatus().hashCode());
        result = prime * result + ((getClearingremark() == null) ? 0 : getClearingremark().hashCode());
        result = prime * result + ((getRedemptionunit() == null) ? 0 : getRedemptionunit().hashCode());
        result = prime * result + ((getCorporateaccount() == null) ? 0 : getCorporateaccount().hashCode());
        result = prime * result + ((getUnderlyingbondid() == null) ? 0 : getUnderlyingbondid().hashCode());
        result = prime * result + ((getDocumenturl() == null) ? 0 : getDocumenturl().hashCode());
        result = prime * result + ((getArchivesurl() == null) ? 0 : getArchivesurl().hashCode());
        result = prime * result + ((getNextinterestdate() == null) ? 0 : getNextinterestdate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productid=").append(productid);
        sb.append(", isin=").append(isin);
        sb.append(", name=").append(name);
        sb.append(", fixinterestrate=").append(fixinterestrate);
        sb.append(", buyingrate=").append(buyingrate);
        sb.append(", sellingrate=").append(sellingrate);
        sb.append(", currency=").append(currency);
        sb.append(", nominalvalue=").append(nominalvalue);
        sb.append(", facevalue=").append(facevalue);
        sb.append(", appraisement=").append(appraisement);
        sb.append(", unit=").append(unit);
        sb.append(", issueamount=").append(issueamount);
        sb.append(", tradingunit=").append(tradingunit);
        sb.append(", issuedate=").append(issuedate);
        sb.append(", maturitydate=").append(maturitydate);
        sb.append(", risklevel=").append(risklevel);
        sb.append(", interestcalculationdays=").append(interestcalculationdays);
        sb.append(", liquidationdate=").append(liquidationdate);
        sb.append(", marketopentime=").append(marketopentime);
        sb.append(", closingtime=").append(closingtime);
        sb.append(", issuer=").append(issuer);
        sb.append(", productdescription=").append(productdescription);
        sb.append(", stamptax=").append(stamptax);
        sb.append(", brokeragecharges=").append(brokeragecharges);
        sb.append(", limitorder=").append(limitorder);
        sb.append(", type=").append(type);
        sb.append(", auditstatus=").append(auditstatus);
        sb.append(", status=").append(status);
        sb.append(", riskwarning=").append(riskwarning);
        sb.append(", creator=").append(creator);
        sb.append(", lastupdatedby=").append(lastupdatedby);
        sb.append(", synchstate=").append(synchstate);
        sb.append(", maturitystatus=").append(maturitystatus);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", settlementdate=").append(settlementdate);
        sb.append(", clearingvalue=").append(clearingvalue);
        sb.append(", listingtime=").append(listingtime);
        sb.append(", customerform=").append(customerform);
        sb.append(", mininitsubscriptionquantity=").append(mininitsubscriptionquantity);
        sb.append(", ipominsubscriptionquantity=").append(ipominsubscriptionquantity);
        sb.append(", productabbr=").append(productabbr);
        sb.append(", clearingstatus=").append(clearingstatus);
        sb.append(", clearingremark=").append(clearingremark);
        sb.append(", redemptionunit=").append(redemptionunit);
        sb.append(", corporateaccount=").append(corporateaccount);
        sb.append(", underlyingbondid=").append(underlyingbondid);
        sb.append(", documenturl=").append(documenturl);
        sb.append(", archivesurl=").append(archivesurl);
        sb.append(", nextinterestdate=").append(nextinterestdate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
