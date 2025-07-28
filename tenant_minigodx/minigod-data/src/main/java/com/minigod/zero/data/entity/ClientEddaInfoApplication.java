package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DBS edda授权申请表
 * @TableName client_edda_info_application
 */
public class ClientEddaInfoApplication implements Serializable {
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
     * 电话号码
     */
    private String mobile;

    /**
     * 证件号码
     */
    private String idCode;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 银行 1大陆 2香港 3其他
     */
    private Integer bankType;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行bankid
     */
    private String bankId;

    /**
     * 存入银行账户
     */
    private String depositAccount;

    /**
     * 存入账户名称
     */
    private String depositAccountName;

    /**
     * 存入账户类型:[1-港币 2-美元 3-人民币 0-综合账户]
     */
    private Integer depositAccountType;

    /**
     * 银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
     */
    private Integer bankIdKind;

    /**
     * 银行开户证件号码
     */
    private String bankIdNo;

    /**
     * 收款银行名称
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
     * 数据状态 0 用户自己删除 1正常 2更新删除
     */
    private Integer dataState;

    /**
     * 状态 0未提交(待审核) 1已审核 2授权中 3授权失败(审核失败) 4已授权 5撤销授权
     */
    private Integer eddaState;

    /**
     * 请求流水号
     */
    private String msgId;

    /**
     * 申请编号
     */
    private String ddaRef;

    /**
     * edda请求时间
     */
    private Date reqTime;

    /**
     * 事务id
     */
    private String txnRefId;

    /**
     * 授权ID
     */
    private String mandateId;

    /**
     * 币种
     */
    private String amtCcy;

    /**
     * 拒绝代码
     */
    private String rejCorde;

    /**
     * 拒绝原因
     */
    private String rejDescription;

    /**
     * 授权生效日
     */
    private Date effDate;

    /**
     * 授权到期日
     */
    private Date expDate;

    /**
     * 单笔限额
     */
    private BigDecimal maxAmt;

    /**
     * 数据撤销时间
     */
    private Date deleteTime;

    /**
     * 数据撤销用户
     */
    private String deleteUser;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新用户
     */
    private String updateUser;

    /**
     * 删除时间
     */
    private Date deleTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     *
     */
    private String birthday;

    /**
     *
     */
    private Integer bankIdKindStatus;

    /**
     * 请求时间
     */
    private Date applicationTime;

    /**
     * 银行app端图标
     */
    private String appIcon;

    /**
     * 入金银行配置id
     */
    private Long secDepositBankId;

    /**
     * 分行号
     */
    private String bankIdQuick;

    /**
     * 手续费
     */
    private BigDecimal chargeMoney;

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
     * 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 银行 1大陆 2香港 3其他
     */
    public Integer getBankType() {
        return bankType;
    }

    /**
     * 银行 1大陆 2香港 3其他
     */
    public void setBankType(Integer bankType) {
        this.bankType = bankType;
    }

    /**
     * 银行名称
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 银行代码
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * 银行代码
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 银行bankid
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 银行bankid
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    /**
     * 存入银行账户
     */
    public String getDepositAccount() {
        return depositAccount;
    }

    /**
     * 存入银行账户
     */
    public void setDepositAccount(String depositAccount) {
        this.depositAccount = depositAccount;
    }

    /**
     * 存入账户名称
     */
    public String getDepositAccountName() {
        return depositAccountName;
    }

    /**
     * 存入账户名称
     */
    public void setDepositAccountName(String depositAccountName) {
        this.depositAccountName = depositAccountName;
    }

    /**
     * 存入账户类型:[1-港币 2-美元 3-人民币 0-综合账户]
     */
    public Integer getDepositAccountType() {
        return depositAccountType;
    }

    /**
     * 存入账户类型:[1-港币 2-美元 3-人民币 0-综合账户]
     */
    public void setDepositAccountType(Integer depositAccountType) {
        this.depositAccountType = depositAccountType;
    }

    /**
     * 银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
     */
    public Integer getBankIdKind() {
        return bankIdKind;
    }

    /**
     * 银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
     */
    public void setBankIdKind(Integer bankIdKind) {
        this.bankIdKind = bankIdKind;
    }

    /**
     * 银行开户证件号码
     */
    public String getBankIdNo() {
        return bankIdNo;
    }

    /**
     * 银行开户证件号码
     */
    public void setBankIdNo(String bankIdNo) {
        this.bankIdNo = bankIdNo;
    }

    /**
     * 收款银行名称
     */
    public String getBenefitBank() {
        return benefitBank;
    }

    /**
     * 收款银行名称
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
     * 数据状态 0 用户自己删除 1正常 2更新删除
     */
    public Integer getDataState() {
        return dataState;
    }

    /**
     * 数据状态 0 用户自己删除 1正常 2更新删除
     */
    public void setDataState(Integer dataState) {
        this.dataState = dataState;
    }

    /**
     * 状态 0未提交(待审核) 1已审核 2授权中 3授权失败(审核失败) 4已授权 5撤销授权
     */
    public Integer getEddaState() {
        return eddaState;
    }

    /**
     * 状态 0未提交(待审核) 1已审核 2授权中 3授权失败(审核失败) 4已授权 5撤销授权
     */
    public void setEddaState(Integer eddaState) {
        this.eddaState = eddaState;
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
     * 申请编号
     */
    public String getDdaRef() {
        return ddaRef;
    }

    /**
     * 申请编号
     */
    public void setDdaRef(String ddaRef) {
        this.ddaRef = ddaRef;
    }

    /**
     * edda请求时间
     */
    public Date getReqTime() {
        return reqTime;
    }

    /**
     * edda请求时间
     */
    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    /**
     * 事务id
     */
    public String getTxnRefId() {
        return txnRefId;
    }

    /**
     * 事务id
     */
    public void setTxnRefId(String txnRefId) {
        this.txnRefId = txnRefId;
    }

    /**
     * 授权ID
     */
    public String getMandateId() {
        return mandateId;
    }

    /**
     * 授权ID
     */
    public void setMandateId(String mandateId) {
        this.mandateId = mandateId;
    }

    /**
     * 币种
     */
    public String getAmtCcy() {
        return amtCcy;
    }

    /**
     * 币种
     */
    public void setAmtCcy(String amtCcy) {
        this.amtCcy = amtCcy;
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
     * 授权生效日
     */
    public Date getEffDate() {
        return effDate;
    }

    /**
     * 授权生效日
     */
    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    /**
     * 授权到期日
     */
    public Date getExpDate() {
        return expDate;
    }

    /**
     * 授权到期日
     */
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    /**
     * 单笔限额
     */
    public BigDecimal getMaxAmt() {
        return maxAmt;
    }

    /**
     * 单笔限额
     */
    public void setMaxAmt(BigDecimal maxAmt) {
        this.maxAmt = maxAmt;
    }

    /**
     * 数据撤销时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 数据撤销时间
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * 数据撤销用户
     */
    public String getDeleteUser() {
        return deleteUser;
    }

    /**
     * 数据撤销用户
     */
    public void setDeleteUser(String deleteUser) {
        this.deleteUser = deleteUser;
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
     * 删除时间
     */
    public Date getDeleTime() {
        return deleTime;
    }

    /**
     * 删除时间
     */
    public void setDeleTime(Date deleTime) {
        this.deleTime = deleTime;
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
     *
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     *
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     *
     */
    public Integer getBankIdKindStatus() {
        return bankIdKindStatus;
    }

    /**
     *
     */
    public void setBankIdKindStatus(Integer bankIdKindStatus) {
        this.bankIdKindStatus = bankIdKindStatus;
    }

    /**
     * 请求时间
     */
    public Date getApplicationTime() {
        return applicationTime;
    }

    /**
     * 请求时间
     */
    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    /**
     * 银行app端图标
     */
    public String getAppIcon() {
        return appIcon;
    }

    /**
     * 银行app端图标
     */
    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    /**
     * 入金银行配置id
     */
    public Long getSecDepositBankId() {
        return secDepositBankId;
    }

    /**
     * 入金银行配置id
     */
    public void setSecDepositBankId(Long secDepositBankId) {
        this.secDepositBankId = secDepositBankId;
    }

    /**
     * 分行号
     */
    public String getBankIdQuick() {
        return bankIdQuick;
    }

    /**
     * 分行号
     */
    public void setBankIdQuick(String bankIdQuick) {
        this.bankIdQuick = bankIdQuick;
    }

    /**
     * 手续费
     */
    public BigDecimal getChargeMoney() {
        return chargeMoney;
    }

    /**
     * 手续费
     */
    public void setChargeMoney(BigDecimal chargeMoney) {
        this.chargeMoney = chargeMoney;
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
        ClientEddaInfoApplication other = (ClientEddaInfoApplication) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getFundAccount() == null ? other.getFundAccount() == null : this.getFundAccount().equals(other.getFundAccount()))
            && (this.geteName() == null ? other.geteName() == null : this.geteName().equals(other.geteName()))
            && (this.getcName() == null ? other.getcName() == null : this.getcName().equals(other.getcName()))
            && (this.getIdType() == null ? other.getIdType() == null : this.getIdType().equals(other.getIdType()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getIdCode() == null ? other.getIdCode() == null : this.getIdCode().equals(other.getIdCode()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getBankType() == null ? other.getBankType() == null : this.getBankType().equals(other.getBankType()))
            && (this.getBankName() == null ? other.getBankName() == null : this.getBankName().equals(other.getBankName()))
            && (this.getBankCode() == null ? other.getBankCode() == null : this.getBankCode().equals(other.getBankCode()))
            && (this.getBankId() == null ? other.getBankId() == null : this.getBankId().equals(other.getBankId()))
            && (this.getDepositAccount() == null ? other.getDepositAccount() == null : this.getDepositAccount().equals(other.getDepositAccount()))
            && (this.getDepositAccountName() == null ? other.getDepositAccountName() == null : this.getDepositAccountName().equals(other.getDepositAccountName()))
            && (this.getDepositAccountType() == null ? other.getDepositAccountType() == null : this.getDepositAccountType().equals(other.getDepositAccountType()))
            && (this.getBankIdKind() == null ? other.getBankIdKind() == null : this.getBankIdKind().equals(other.getBankIdKind()))
            && (this.getBankIdNo() == null ? other.getBankIdNo() == null : this.getBankIdNo().equals(other.getBankIdNo()))
            && (this.getBenefitBank() == null ? other.getBenefitBank() == null : this.getBenefitBank().equals(other.getBenefitBank()))
            && (this.getBenefitBankCode() == null ? other.getBenefitBankCode() == null : this.getBenefitBankCode().equals(other.getBenefitBankCode()))
            && (this.getBenefitNo() == null ? other.getBenefitNo() == null : this.getBenefitNo().equals(other.getBenefitNo()))
            && (this.getBenefitAccount() == null ? other.getBenefitAccount() == null : this.getBenefitAccount().equals(other.getBenefitAccount()))
            && (this.getDataState() == null ? other.getDataState() == null : this.getDataState().equals(other.getDataState()))
            && (this.getEddaState() == null ? other.getEddaState() == null : this.getEddaState().equals(other.getEddaState()))
            && (this.getMsgId() == null ? other.getMsgId() == null : this.getMsgId().equals(other.getMsgId()))
            && (this.getDdaRef() == null ? other.getDdaRef() == null : this.getDdaRef().equals(other.getDdaRef()))
            && (this.getReqTime() == null ? other.getReqTime() == null : this.getReqTime().equals(other.getReqTime()))
            && (this.getTxnRefId() == null ? other.getTxnRefId() == null : this.getTxnRefId().equals(other.getTxnRefId()))
            && (this.getMandateId() == null ? other.getMandateId() == null : this.getMandateId().equals(other.getMandateId()))
            && (this.getAmtCcy() == null ? other.getAmtCcy() == null : this.getAmtCcy().equals(other.getAmtCcy()))
            && (this.getRejCorde() == null ? other.getRejCorde() == null : this.getRejCorde().equals(other.getRejCorde()))
            && (this.getRejDescription() == null ? other.getRejDescription() == null : this.getRejDescription().equals(other.getRejDescription()))
            && (this.getEffDate() == null ? other.getEffDate() == null : this.getEffDate().equals(other.getEffDate()))
            && (this.getExpDate() == null ? other.getExpDate() == null : this.getExpDate().equals(other.getExpDate()))
            && (this.getMaxAmt() == null ? other.getMaxAmt() == null : this.getMaxAmt().equals(other.getMaxAmt()))
            && (this.getDeleteTime() == null ? other.getDeleteTime() == null : this.getDeleteTime().equals(other.getDeleteTime()))
            && (this.getDeleteUser() == null ? other.getDeleteUser() == null : this.getDeleteUser().equals(other.getDeleteUser()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getDeleTime() == null ? other.getDeleTime() == null : this.getDeleTime().equals(other.getDeleTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getBankIdKindStatus() == null ? other.getBankIdKindStatus() == null : this.getBankIdKindStatus().equals(other.getBankIdKindStatus()))
            && (this.getApplicationTime() == null ? other.getApplicationTime() == null : this.getApplicationTime().equals(other.getApplicationTime()))
            && (this.getAppIcon() == null ? other.getAppIcon() == null : this.getAppIcon().equals(other.getAppIcon()))
            && (this.getSecDepositBankId() == null ? other.getSecDepositBankId() == null : this.getSecDepositBankId().equals(other.getSecDepositBankId()))
            && (this.getBankIdQuick() == null ? other.getBankIdQuick() == null : this.getBankIdQuick().equals(other.getBankIdQuick()))
            && (this.getChargeMoney() == null ? other.getChargeMoney() == null : this.getChargeMoney().equals(other.getChargeMoney()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getFundAccount() == null) ? 0 : getFundAccount().hashCode());
        result = prime * result + ((geteName() == null) ? 0 : geteName().hashCode());
        result = prime * result + ((getcName() == null) ? 0 : getcName().hashCode());
        result = prime * result + ((getIdType() == null) ? 0 : getIdType().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getIdCode() == null) ? 0 : getIdCode().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getBankType() == null) ? 0 : getBankType().hashCode());
        result = prime * result + ((getBankName() == null) ? 0 : getBankName().hashCode());
        result = prime * result + ((getBankCode() == null) ? 0 : getBankCode().hashCode());
        result = prime * result + ((getBankId() == null) ? 0 : getBankId().hashCode());
        result = prime * result + ((getDepositAccount() == null) ? 0 : getDepositAccount().hashCode());
        result = prime * result + ((getDepositAccountName() == null) ? 0 : getDepositAccountName().hashCode());
        result = prime * result + ((getDepositAccountType() == null) ? 0 : getDepositAccountType().hashCode());
        result = prime * result + ((getBankIdKind() == null) ? 0 : getBankIdKind().hashCode());
        result = prime * result + ((getBankIdNo() == null) ? 0 : getBankIdNo().hashCode());
        result = prime * result + ((getBenefitBank() == null) ? 0 : getBenefitBank().hashCode());
        result = prime * result + ((getBenefitBankCode() == null) ? 0 : getBenefitBankCode().hashCode());
        result = prime * result + ((getBenefitNo() == null) ? 0 : getBenefitNo().hashCode());
        result = prime * result + ((getBenefitAccount() == null) ? 0 : getBenefitAccount().hashCode());
        result = prime * result + ((getDataState() == null) ? 0 : getDataState().hashCode());
        result = prime * result + ((getEddaState() == null) ? 0 : getEddaState().hashCode());
        result = prime * result + ((getMsgId() == null) ? 0 : getMsgId().hashCode());
        result = prime * result + ((getDdaRef() == null) ? 0 : getDdaRef().hashCode());
        result = prime * result + ((getReqTime() == null) ? 0 : getReqTime().hashCode());
        result = prime * result + ((getTxnRefId() == null) ? 0 : getTxnRefId().hashCode());
        result = prime * result + ((getMandateId() == null) ? 0 : getMandateId().hashCode());
        result = prime * result + ((getAmtCcy() == null) ? 0 : getAmtCcy().hashCode());
        result = prime * result + ((getRejCorde() == null) ? 0 : getRejCorde().hashCode());
        result = prime * result + ((getRejDescription() == null) ? 0 : getRejDescription().hashCode());
        result = prime * result + ((getEffDate() == null) ? 0 : getEffDate().hashCode());
        result = prime * result + ((getExpDate() == null) ? 0 : getExpDate().hashCode());
        result = prime * result + ((getMaxAmt() == null) ? 0 : getMaxAmt().hashCode());
        result = prime * result + ((getDeleteTime() == null) ? 0 : getDeleteTime().hashCode());
        result = prime * result + ((getDeleteUser() == null) ? 0 : getDeleteUser().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getDeleTime() == null) ? 0 : getDeleTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getBankIdKindStatus() == null) ? 0 : getBankIdKindStatus().hashCode());
        result = prime * result + ((getApplicationTime() == null) ? 0 : getApplicationTime().hashCode());
        result = prime * result + ((getAppIcon() == null) ? 0 : getAppIcon().hashCode());
        result = prime * result + ((getSecDepositBankId() == null) ? 0 : getSecDepositBankId().hashCode());
        result = prime * result + ((getBankIdQuick() == null) ? 0 : getBankIdQuick().hashCode());
        result = prime * result + ((getChargeMoney() == null) ? 0 : getChargeMoney().hashCode());
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
        sb.append(", eName=").append(eName);
        sb.append(", cName=").append(cName);
        sb.append(", idType=").append(idType);
        sb.append(", mobile=").append(mobile);
        sb.append(", idCode=").append(idCode);
        sb.append(", email=").append(email);
        sb.append(", bankType=").append(bankType);
        sb.append(", bankName=").append(bankName);
        sb.append(", bankCode=").append(bankCode);
        sb.append(", bankId=").append(bankId);
        sb.append(", depositAccount=").append(depositAccount);
        sb.append(", depositAccountName=").append(depositAccountName);
        sb.append(", depositAccountType=").append(depositAccountType);
        sb.append(", bankIdKind=").append(bankIdKind);
        sb.append(", bankIdNo=").append(bankIdNo);
        sb.append(", benefitBank=").append(benefitBank);
        sb.append(", benefitBankCode=").append(benefitBankCode);
        sb.append(", benefitNo=").append(benefitNo);
        sb.append(", benefitAccount=").append(benefitAccount);
        sb.append(", dataState=").append(dataState);
        sb.append(", eddaState=").append(eddaState);
        sb.append(", msgId=").append(msgId);
        sb.append(", ddaRef=").append(ddaRef);
        sb.append(", reqTime=").append(reqTime);
        sb.append(", txnRefId=").append(txnRefId);
        sb.append(", mandateId=").append(mandateId);
        sb.append(", amtCcy=").append(amtCcy);
        sb.append(", rejCorde=").append(rejCorde);
        sb.append(", rejDescription=").append(rejDescription);
        sb.append(", effDate=").append(effDate);
        sb.append(", expDate=").append(expDate);
        sb.append(", maxAmt=").append(maxAmt);
        sb.append(", deleteTime=").append(deleteTime);
        sb.append(", deleteUser=").append(deleteUser);
        sb.append(", remark=").append(remark);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", deleTime=").append(deleTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", birthday=").append(birthday);
        sb.append(", bankIdKindStatus=").append(bankIdKindStatus);
        sb.append(", applicationTime=").append(applicationTime);
        sb.append(", appIcon=").append(appIcon);
        sb.append(", secDepositBankId=").append(secDepositBankId);
        sb.append(", bankIdQuick=").append(bankIdQuick);
        sb.append(", chargeMoney=").append(chargeMoney);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
