package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DBS edda入金流水表
 * @TableName client_edda_fund_application
 */
public class ClientEddaFundApplication implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 流水号
     */
    private String applicationId;

    /**
     * 交易账号
     */
    private String clientId;

    /**
     * 资金账号
     */
    private String fundAccount;

    /**
     * 状态 0待处理 1处理中 2银行处理失败 3银行处理成功，4入账失败  5入账成功
     */
    private Integer bankState;

    /**
     * 申请金额
     */
    private BigDecimal depositBalance;

    /**
     * 客户英文名
     */
    private String eName;

    /**
     * 客户中文名
     */
    private String cName;

    /**
     * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idCode;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 账户类型:1-港币 2-美元 3-人民币 0-综合账户
     */
    private String acctType;

    /**
     * 入金方式 1大陆 0香港 2其他
     */
    private Integer depositType;

    /**
     * 汇款银行
     */
    private String depositBank;

    /**
     * 汇款账号
     */
    private String depositNo;

    /**
     * 汇款账户名称
     */
    private String depositAccount;

    /**
     * 汇款银行代码
     */
    private String depositBankCode;

    /**
     * 汇款银行Id
     */
    private String depositBankId;

    /**
     * 收款银行(CUBP 字典表中维护的收款银行代码)
     */
    private String benefitBank;

    /**
     * 收款银行code
     */
    private String benefitBankCode;

    /**
     * 收款账号
     */
    private String benefitNo;

    /**
     * 收款账户名称
     */
    private String benefitAccount;

    /**
     * 申请时间
     */
    private Date applicationTime;

    /**
     * 币种代码 1港币 2美元 3人民币
     */
    private String moneyType;

    /**
     * 入金上送状态[0-未上送 1-已上送]
     */
    private Integer sendStatus;

    /**
     * 汇款方式[0-未知 1-网银汇款 2-支票汇款 3-fps 4edda 5fpsqr]
     */
    private Integer remittanceType;

    /**
     * swift_code
     */
    private String swiftCode;

    /**
     * 入金申请流水号
     */
    private String fundApplicationId;

    /**
     * 请求流水号
     */
    private String msgId;

    /**
     * edda申请编号
     */
    private String ddaRef;

    /**
     * 交易对账流水号
     */
    private String cusRef;

    /**
     * 请求时间
     */
    private Date reqTime;

    /**
     * 银行流水号
     */
    private String txnRefId;

    /**
     * 银行到账金额
     */
    private BigDecimal settlementAmt;

    /**
     * 银行到账日期
     */
    private Date settlementDt;

    /**
     * 拒绝代码
     */
    private String rejCorde;

    /**
     * 拒绝原因
     */
    private String rejDescription;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新用户
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 银行授权id
     */
    private Long eddaInfoId;

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
     * 交易账号
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 交易账号
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 资金账号
     */
    public String getFundAccount() {
        return fundAccount;
    }

    /**
     * 资金账号
     */
    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    /**
     * 状态 0待处理 1处理中 2银行处理失败 3银行处理成功，4入账失败  5入账成功
     */
    public Integer getBankState() {
        return bankState;
    }

    /**
     * 状态 0待处理 1处理中 2银行处理失败 3银行处理成功，4入账失败  5入账成功
     */
    public void setBankState(Integer bankState) {
        this.bankState = bankState;
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
     * 客户英文名
     */
    public String geteName() {
        return eName;
    }

    /**
     * 客户英文名
     */
    public void seteName(String eName) {
        this.eName = eName;
    }

    /**
     * 客户中文名
     */
    public String getcName() {
        return cName;
    }

    /**
     * 客户中文名
     */
    public void setcName(String cName) {
        this.cName = cName;
    }

    /**
     * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
     */
    public String getIdType() {
        return idType;
    }

    /**
     * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
     */
    public void setIdType(String idType) {
        this.idType = idType;
    }

    /**
     * 证件号码
     */
    public String getIdCode() {
        return idCode;
    }

    /**
     * 证件号码
     */
    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    /**
     * 电话号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 电话号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 电子邮件
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 账户类型:1-港币 2-美元 3-人民币 0-综合账户
     */
    public String getAcctType() {
        return acctType;
    }

    /**
     * 账户类型:1-港币 2-美元 3-人民币 0-综合账户
     */
    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    /**
     * 入金方式 1大陆 0香港 2其他
     */
    public Integer getDepositType() {
        return depositType;
    }

    /**
     * 入金方式 1大陆 0香港 2其他
     */
    public void setDepositType(Integer depositType) {
        this.depositType = depositType;
    }

    /**
     * 汇款银行
     */
    public String getDepositBank() {
        return depositBank;
    }

    /**
     * 汇款银行
     */
    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    /**
     * 汇款账号
     */
    public String getDepositNo() {
        return depositNo;
    }

    /**
     * 汇款账号
     */
    public void setDepositNo(String depositNo) {
        this.depositNo = depositNo;
    }

    /**
     * 汇款账户名称
     */
    public String getDepositAccount() {
        return depositAccount;
    }

    /**
     * 汇款账户名称
     */
    public void setDepositAccount(String depositAccount) {
        this.depositAccount = depositAccount;
    }

    /**
     * 汇款银行代码
     */
    public String getDepositBankCode() {
        return depositBankCode;
    }

    /**
     * 汇款银行代码
     */
    public void setDepositBankCode(String depositBankCode) {
        this.depositBankCode = depositBankCode;
    }

    /**
     * 汇款银行Id
     */
    public String getDepositBankId() {
        return depositBankId;
    }

    /**
     * 汇款银行Id
     */
    public void setDepositBankId(String depositBankId) {
        this.depositBankId = depositBankId;
    }

    /**
     * 收款银行(CUBP 字典表中维护的收款银行代码)
     */
    public String getBenefitBank() {
        return benefitBank;
    }

    /**
     * 收款银行(CUBP 字典表中维护的收款银行代码)
     */
    public void setBenefitBank(String benefitBank) {
        this.benefitBank = benefitBank;
    }

    /**
     * 收款银行code
     */
    public String getBenefitBankCode() {
        return benefitBankCode;
    }

    /**
     * 收款银行code
     */
    public void setBenefitBankCode(String benefitBankCode) {
        this.benefitBankCode = benefitBankCode;
    }

    /**
     * 收款账号
     */
    public String getBenefitNo() {
        return benefitNo;
    }

    /**
     * 收款账号
     */
    public void setBenefitNo(String benefitNo) {
        this.benefitNo = benefitNo;
    }

    /**
     * 收款账户名称
     */
    public String getBenefitAccount() {
        return benefitAccount;
    }

    /**
     * 收款账户名称
     */
    public void setBenefitAccount(String benefitAccount) {
        this.benefitAccount = benefitAccount;
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
     * 币种代码 1港币 2美元 3人民币
     */
    public String getMoneyType() {
        return moneyType;
    }

    /**
     * 币种代码 1港币 2美元 3人民币
     */
    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    /**
     * 入金上送状态[0-未上送 1-已上送]
     */
    public Integer getSendStatus() {
        return sendStatus;
    }

    /**
     * 入金上送状态[0-未上送 1-已上送]
     */
    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    /**
     * 汇款方式[0-未知 1-网银汇款 2-支票汇款 3-fps 4edda 5fpsqr]
     */
    public Integer getRemittanceType() {
        return remittanceType;
    }

    /**
     * 汇款方式[0-未知 1-网银汇款 2-支票汇款 3-fps 4edda 5fpsqr]
     */
    public void setRemittanceType(Integer remittanceType) {
        this.remittanceType = remittanceType;
    }

    /**
     * swift_code
     */
    public String getSwiftCode() {
        return swiftCode;
    }

    /**
     * swift_code
     */
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    /**
     * 入金申请流水号
     */
    public String getFundApplicationId() {
        return fundApplicationId;
    }

    /**
     * 入金申请流水号
     */
    public void setFundApplicationId(String fundApplicationId) {
        this.fundApplicationId = fundApplicationId;
    }

    /**
     * 请求流水号
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * 请求流水号
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * edda申请编号
     */
    public String getDdaRef() {
        return ddaRef;
    }

    /**
     * edda申请编号
     */
    public void setDdaRef(String ddaRef) {
        this.ddaRef = ddaRef;
    }

    /**
     * 交易对账流水号
     */
    public String getCusRef() {
        return cusRef;
    }

    /**
     * 交易对账流水号
     */
    public void setCusRef(String cusRef) {
        this.cusRef = cusRef;
    }

    /**
     * 请求时间
     */
    public Date getReqTime() {
        return reqTime;
    }

    /**
     * 请求时间
     */
    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    /**
     * 银行流水号
     */
    public String getTxnRefId() {
        return txnRefId;
    }

    /**
     * 银行流水号
     */
    public void setTxnRefId(String txnRefId) {
        this.txnRefId = txnRefId;
    }

    /**
     * 银行到账金额
     */
    public BigDecimal getSettlementAmt() {
        return settlementAmt;
    }

    /**
     * 银行到账金额
     */
    public void setSettlementAmt(BigDecimal settlementAmt) {
        this.settlementAmt = settlementAmt;
    }

    /**
     * 银行到账日期
     */
    public Date getSettlementDt() {
        return settlementDt;
    }

    /**
     * 银行到账日期
     */
    public void setSettlementDt(Date settlementDt) {
        this.settlementDt = settlementDt;
    }

    /**
     * 拒绝代码
     */
    public String getRejCorde() {
        return rejCorde;
    }

    /**
     * 拒绝代码
     */
    public void setRejCorde(String rejCorde) {
        this.rejCorde = rejCorde;
    }

    /**
     * 拒绝原因
     */
    public String getRejDescription() {
        return rejDescription;
    }

    /**
     * 拒绝原因
     */
    public void setRejDescription(String rejDescription) {
        this.rejDescription = rejDescription;
    }

    /**
     * 创建用户
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建用户
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 更新用户
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新用户
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
     * 银行授权id
     */
    public Long getEddaInfoId() {
        return eddaInfoId;
    }

    /**
     * 银行授权id
     */
    public void setEddaInfoId(Long eddaInfoId) {
        this.eddaInfoId = eddaInfoId;
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
        ClientEddaFundApplication other = (ClientEddaFundApplication) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getFundAccount() == null ? other.getFundAccount() == null : this.getFundAccount().equals(other.getFundAccount()))
            && (this.getBankState() == null ? other.getBankState() == null : this.getBankState().equals(other.getBankState()))
            && (this.getDepositBalance() == null ? other.getDepositBalance() == null : this.getDepositBalance().equals(other.getDepositBalance()))
            && (this.geteName() == null ? other.geteName() == null : this.geteName().equals(other.geteName()))
            && (this.getcName() == null ? other.getcName() == null : this.getcName().equals(other.getcName()))
            && (this.getIdType() == null ? other.getIdType() == null : this.getIdType().equals(other.getIdType()))
            && (this.getIdCode() == null ? other.getIdCode() == null : this.getIdCode().equals(other.getIdCode()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getAcctType() == null ? other.getAcctType() == null : this.getAcctType().equals(other.getAcctType()))
            && (this.getDepositType() == null ? other.getDepositType() == null : this.getDepositType().equals(other.getDepositType()))
            && (this.getDepositBank() == null ? other.getDepositBank() == null : this.getDepositBank().equals(other.getDepositBank()))
            && (this.getDepositNo() == null ? other.getDepositNo() == null : this.getDepositNo().equals(other.getDepositNo()))
            && (this.getDepositAccount() == null ? other.getDepositAccount() == null : this.getDepositAccount().equals(other.getDepositAccount()))
            && (this.getDepositBankCode() == null ? other.getDepositBankCode() == null : this.getDepositBankCode().equals(other.getDepositBankCode()))
            && (this.getDepositBankId() == null ? other.getDepositBankId() == null : this.getDepositBankId().equals(other.getDepositBankId()))
            && (this.getBenefitBank() == null ? other.getBenefitBank() == null : this.getBenefitBank().equals(other.getBenefitBank()))
            && (this.getBenefitBankCode() == null ? other.getBenefitBankCode() == null : this.getBenefitBankCode().equals(other.getBenefitBankCode()))
            && (this.getBenefitNo() == null ? other.getBenefitNo() == null : this.getBenefitNo().equals(other.getBenefitNo()))
            && (this.getBenefitAccount() == null ? other.getBenefitAccount() == null : this.getBenefitAccount().equals(other.getBenefitAccount()))
            && (this.getApplicationTime() == null ? other.getApplicationTime() == null : this.getApplicationTime().equals(other.getApplicationTime()))
            && (this.getMoneyType() == null ? other.getMoneyType() == null : this.getMoneyType().equals(other.getMoneyType()))
            && (this.getSendStatus() == null ? other.getSendStatus() == null : this.getSendStatus().equals(other.getSendStatus()))
            && (this.getRemittanceType() == null ? other.getRemittanceType() == null : this.getRemittanceType().equals(other.getRemittanceType()))
            && (this.getSwiftCode() == null ? other.getSwiftCode() == null : this.getSwiftCode().equals(other.getSwiftCode()))
            && (this.getFundApplicationId() == null ? other.getFundApplicationId() == null : this.getFundApplicationId().equals(other.getFundApplicationId()))
            && (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
            && (this.getDdaRef() == null ? other.getDdaRef() == null : this.getDdaRef().equals(other.getDdaRef()))
            && (this.getCusRef() == null ? other.getCusRef() == null : this.getCusRef().equals(other.getCusRef()))
            && (this.getReqTime() == null ? other.getReqTime() == null : this.getReqTime().equals(other.getReqTime()))
            && (this.getTxnRefId() == null ? other.getTxnRefId() == null : this.getTxnRefId().equals(other.getTxnRefId()))
            && (this.getSettlementAmt() == null ? other.getSettlementAmt() == null : this.getSettlementAmt().equals(other.getSettlementAmt()))
            && (this.getSettlementDt() == null ? other.getSettlementDt() == null : this.getSettlementDt().equals(other.getSettlementDt()))
            && (this.getRejCorde() == null ? other.getRejCorde() == null : this.getRejCorde().equals(other.getRejCorde()))
            && (this.getRejDescription() == null ? other.getRejDescription() == null : this.getRejDescription().equals(other.getRejDescription()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getEddaInfoId() == null ? other.getEddaInfoId() == null : this.getEddaInfoId().equals(other.getEddaInfoId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getFundAccount() == null) ? 0 : getFundAccount().hashCode());
        result = prime * result + ((getBankState() == null) ? 0 : getBankState().hashCode());
        result = prime * result + ((getDepositBalance() == null) ? 0 : getDepositBalance().hashCode());
        result = prime * result + ((geteName() == null) ? 0 : geteName().hashCode());
        result = prime * result + ((getcName() == null) ? 0 : getcName().hashCode());
        result = prime * result + ((getIdType() == null) ? 0 : getIdType().hashCode());
        result = prime * result + ((getIdCode() == null) ? 0 : getIdCode().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAcctType() == null) ? 0 : getAcctType().hashCode());
        result = prime * result + ((getDepositType() == null) ? 0 : getDepositType().hashCode());
        result = prime * result + ((getDepositBank() == null) ? 0 : getDepositBank().hashCode());
        result = prime * result + ((getDepositNo() == null) ? 0 : getDepositNo().hashCode());
        result = prime * result + ((getDepositAccount() == null) ? 0 : getDepositAccount().hashCode());
        result = prime * result + ((getDepositBankCode() == null) ? 0 : getDepositBankCode().hashCode());
        result = prime * result + ((getDepositBankId() == null) ? 0 : getDepositBankId().hashCode());
        result = prime * result + ((getBenefitBank() == null) ? 0 : getBenefitBank().hashCode());
        result = prime * result + ((getBenefitBankCode() == null) ? 0 : getBenefitBankCode().hashCode());
        result = prime * result + ((getBenefitNo() == null) ? 0 : getBenefitNo().hashCode());
        result = prime * result + ((getBenefitAccount() == null) ? 0 : getBenefitAccount().hashCode());
        result = prime * result + ((getApplicationTime() == null) ? 0 : getApplicationTime().hashCode());
        result = prime * result + ((getMoneyType() == null) ? 0 : getMoneyType().hashCode());
        result = prime * result + ((getSendStatus() == null) ? 0 : getSendStatus().hashCode());
        result = prime * result + ((getRemittanceType() == null) ? 0 : getRemittanceType().hashCode());
        result = prime * result + ((getSwiftCode() == null) ? 0 : getSwiftCode().hashCode());
        result = prime * result + ((getFundApplicationId() == null) ? 0 : getFundApplicationId().hashCode());
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getDdaRef() == null) ? 0 : getDdaRef().hashCode());
        result = prime * result + ((getCusRef() == null) ? 0 : getCusRef().hashCode());
        result = prime * result + ((getReqTime() == null) ? 0 : getReqTime().hashCode());
        result = prime * result + ((getTxnRefId() == null) ? 0 : getTxnRefId().hashCode());
        result = prime * result + ((getSettlementAmt() == null) ? 0 : getSettlementAmt().hashCode());
        result = prime * result + ((getSettlementDt() == null) ? 0 : getSettlementDt().hashCode());
        result = prime * result + ((getRejCorde() == null) ? 0 : getRejCorde().hashCode());
        result = prime * result + ((getRejDescription() == null) ? 0 : getRejDescription().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getEddaInfoId() == null) ? 0 : getEddaInfoId().hashCode());
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
        sb.append(", clientId=").append(clientId);
        sb.append(", fundAccount=").append(fundAccount);
        sb.append(", bankState=").append(bankState);
        sb.append(", depositBalance=").append(depositBalance);
        sb.append(", eName=").append(eName);
        sb.append(", cName=").append(cName);
        sb.append(", idType=").append(idType);
        sb.append(", idCode=").append(idCode);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", acctType=").append(acctType);
        sb.append(", depositType=").append(depositType);
        sb.append(", depositBank=").append(depositBank);
        sb.append(", depositNo=").append(depositNo);
        sb.append(", depositAccount=").append(depositAccount);
        sb.append(", depositBankCode=").append(depositBankCode);
        sb.append(", depositBankId=").append(depositBankId);
        sb.append(", benefitBank=").append(benefitBank);
        sb.append(", benefitBankCode=").append(benefitBankCode);
        sb.append(", benefitNo=").append(benefitNo);
        sb.append(", benefitAccount=").append(benefitAccount);
        sb.append(", applicationTime=").append(applicationTime);
        sb.append(", moneyType=").append(moneyType);
        sb.append(", sendStatus=").append(sendStatus);
        sb.append(", remittanceType=").append(remittanceType);
        sb.append(", swiftCode=").append(swiftCode);
        sb.append(", fundApplicationId=").append(fundApplicationId);
        sb.append(", msgId=").append(msgId);
        sb.append(", ddaRef=").append(ddaRef);
        sb.append(", cusRef=").append(cusRef);
        sb.append(", reqTime=").append(reqTime);
        sb.append(", txnRefId=").append(txnRefId);
        sb.append(", settlementAmt=").append(settlementAmt);
        sb.append(", settlementDt=").append(settlementDt);
        sb.append(", rejCorde=").append(rejCorde);
        sb.append(", rejDescription=").append(rejDescription);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", eddaInfoId=").append(eddaInfoId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
