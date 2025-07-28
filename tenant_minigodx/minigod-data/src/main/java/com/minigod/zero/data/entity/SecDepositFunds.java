package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 存入资金表
 * @TableName sec_deposit_funds
 */
public class SecDepositFunds implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 币种 1港币 2 美元
     */
    private Integer currency;

    /**
     * 银行 1大陆 2香港
     */
    private Integer bankType;

    /**
     * 客户号
     */
    private String clientId;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 存入资金账户
     */
    private String fundAccount;

    /**
     * 存入资金账户名称
     */
    private String fundAccountName;

    /**
     * 存入金额
     */
    private BigDecimal depositMoney;

    /**
     * 邀请人
     */
    private Integer inviter;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 状态 0已提交 1已受理 2已退回 3已完成 4已取消
     */
    private Integer state;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 是否全部加载 0 否 1 是
     */
    private Integer isFind;

    /**
     * 手续费
     */
    private BigDecimal chargeMoney;

    /**
     * 操作人
     */
    private String backPerson;

    /**
     * 退回理由
     */
    private String backReason;

    /**
     * 前端保存信息
     */
    private String info;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 收款账户号码
     */
    private String receivingAccount;

    /**
     * 收款人账户名
     */
    private String receivingAccountName;

    /**
     * 收款银行代码
     */
    private String receivingBankCode;

    /**
     * 收款人地址
     */
    private String receivingAddress;

    /**
     * 收款银行中文名
     */
    private String receivingBankNameCn;

    /**
     * 收款银行英文名
     */
    private String receivingBankNameEn;

    /**
     * 收款银行地址
     */
    private String receivingBankAddress;

    /**
     * SWIFT代码
     */
    private String swiftCode;

    /**
     * 是否发放奖
     */
    private Integer isReward;

    /**
     * 活动ID
     */
    private Integer actId;

    /**
     * 汇款银行名称
     */
    private String remittanceBankName;

    /**
     * 汇款银行账号
     */
    private String remittanceBankAccount;

    /**
     * 汇款户名(英文)
     */
    private String remittanceAccountNameEn;

    /**
     * 汇款银行代码
     */
    private String remittanceBankCode;

    /**
     * 汇款银行id(edda入金需要：汇款银行bankId)
     */
    private String remittanceBankId;

    /**
     * 回调状态
     */
    private Integer pushrecved;

    /**
     * 预约号
     */
    private String applicationId;

    /**
     * 回调错误次数
     */
    private Integer errCnt;

    /**
     * edda入金流水号
     */
    private String eddaApplicationId;

    /**
     * 实际到账金额
     */
    private BigDecimal settlementAmt;

    /**
     * 银行到账日期
     */
    private Date settlementDt;

    /**
     * 银证入金流水号
     */
    private String banksecApplicationId;

    /**
     * 银行业务流水号
     */
    private String txnRefId;

    /**
     * 银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]
     */
    private Integer bankAccountCategory;

    /**
     * app是否可撤销[0-否 1-是]
     */
    private Integer isCancel;

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 1:FPS,2:网银转账,3:支票转账,4:快捷入金,5:银证转账,6:EDDA
     */
    private Integer supportType;

    /**
     * 汇款银行swiftCode
     */
    private String remittanceSwiftCode;

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 币种 1港币 2 美元
     */
    public Integer getCurrency() {
        return currency;
    }

    /**
     * 币种 1港币 2 美元
     */
    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    /**
     * 银行 1大陆 2香港
     */
    public Integer getBankType() {
        return bankType;
    }

    /**
     * 银行 1大陆 2香港
     */
    public void setBankType(Integer bankType) {
        this.bankType = bankType;
    }

    /**
     * 客户号
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 客户号
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
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
     * 存入资金账户
     */
    public String getFundAccount() {
        return fundAccount;
    }

    /**
     * 存入资金账户
     */
    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    /**
     * 存入资金账户名称
     */
    public String getFundAccountName() {
        return fundAccountName;
    }

    /**
     * 存入资金账户名称
     */
    public void setFundAccountName(String fundAccountName) {
        this.fundAccountName = fundAccountName;
    }

    /**
     * 存入金额
     */
    public BigDecimal getDepositMoney() {
        return depositMoney;
    }

    /**
     * 存入金额
     */
    public void setDepositMoney(BigDecimal depositMoney) {
        this.depositMoney = depositMoney;
    }

    /**
     * 邀请人
     */
    public Integer getInviter() {
        return inviter;
    }

    /**
     * 邀请人
     */
    public void setInviter(Integer inviter) {
        this.inviter = inviter;
    }

    /**
     * 备注信息
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注信息
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 状态 0已提交 1已受理 2已退回 3已完成 4已取消
     */
    public Integer getState() {
        return state;
    }

    /**
     * 状态 0已提交 1已受理 2已退回 3已完成 4已取消
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 用户 ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户 ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 是否全部加载 0 否 1 是
     */
    public Integer getIsFind() {
        return isFind;
    }

    /**
     * 是否全部加载 0 否 1 是
     */
    public void setIsFind(Integer isFind) {
        this.isFind = isFind;
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

    /**
     * 操作人
     */
    public String getBackPerson() {
        return backPerson;
    }

    /**
     * 操作人
     */
    public void setBackPerson(String backPerson) {
        this.backPerson = backPerson;
    }

    /**
     * 退回理由
     */
    public String getBackReason() {
        return backReason;
    }

    /**
     * 退回理由
     */
    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    /**
     * 前端保存信息
     */
    public String getInfo() {
        return info;
    }

    /**
     * 前端保存信息
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 更新时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 更新时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 收款账户号码
     */
    public String getReceivingAccount() {
        return receivingAccount;
    }

    /**
     * 收款账户号码
     */
    public void setReceivingAccount(String receivingAccount) {
        this.receivingAccount = receivingAccount;
    }

    /**
     * 收款人账户名
     */
    public String getReceivingAccountName() {
        return receivingAccountName;
    }

    /**
     * 收款人账户名
     */
    public void setReceivingAccountName(String receivingAccountName) {
        this.receivingAccountName = receivingAccountName;
    }

    /**
     * 收款银行代码
     */
    public String getReceivingBankCode() {
        return receivingBankCode;
    }

    /**
     * 收款银行代码
     */
    public void setReceivingBankCode(String receivingBankCode) {
        this.receivingBankCode = receivingBankCode;
    }

    /**
     * 收款人地址
     */
    public String getReceivingAddress() {
        return receivingAddress;
    }

    /**
     * 收款人地址
     */
    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    /**
     * 收款银行中文名
     */
    public String getReceivingBankNameCn() {
        return receivingBankNameCn;
    }

    /**
     * 收款银行中文名
     */
    public void setReceivingBankNameCn(String receivingBankNameCn) {
        this.receivingBankNameCn = receivingBankNameCn;
    }

    /**
     * 收款银行英文名
     */
    public String getReceivingBankNameEn() {
        return receivingBankNameEn;
    }

    /**
     * 收款银行英文名
     */
    public void setReceivingBankNameEn(String receivingBankNameEn) {
        this.receivingBankNameEn = receivingBankNameEn;
    }

    /**
     * 收款银行地址
     */
    public String getReceivingBankAddress() {
        return receivingBankAddress;
    }

    /**
     * 收款银行地址
     */
    public void setReceivingBankAddress(String receivingBankAddress) {
        this.receivingBankAddress = receivingBankAddress;
    }

    /**
     * SWIFT代码
     */
    public String getSwiftCode() {
        return swiftCode;
    }

    /**
     * SWIFT代码
     */
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    /**
     * 是否发放奖
     */
    public Integer getIsReward() {
        return isReward;
    }

    /**
     * 是否发放奖
     */
    public void setIsReward(Integer isReward) {
        this.isReward = isReward;
    }

    /**
     * 活动ID
     */
    public Integer getActId() {
        return actId;
    }

    /**
     * 活动ID
     */
    public void setActId(Integer actId) {
        this.actId = actId;
    }

    /**
     * 汇款银行名称
     */
    public String getRemittanceBankName() {
        return remittanceBankName;
    }

    /**
     * 汇款银行名称
     */
    public void setRemittanceBankName(String remittanceBankName) {
        this.remittanceBankName = remittanceBankName;
    }

    /**
     * 汇款银行账号
     */
    public String getRemittanceBankAccount() {
        return remittanceBankAccount;
    }

    /**
     * 汇款银行账号
     */
    public void setRemittanceBankAccount(String remittanceBankAccount) {
        this.remittanceBankAccount = remittanceBankAccount;
    }

    /**
     * 汇款户名(英文)
     */
    public String getRemittanceAccountNameEn() {
        return remittanceAccountNameEn;
    }

    /**
     * 汇款户名(英文)
     */
    public void setRemittanceAccountNameEn(String remittanceAccountNameEn) {
        this.remittanceAccountNameEn = remittanceAccountNameEn;
    }

    /**
     * 汇款银行代码
     */
    public String getRemittanceBankCode() {
        return remittanceBankCode;
    }

    /**
     * 汇款银行代码
     */
    public void setRemittanceBankCode(String remittanceBankCode) {
        this.remittanceBankCode = remittanceBankCode;
    }

    /**
     * 汇款银行id(edda入金需要：汇款银行bankId)
     */
    public String getRemittanceBankId() {
        return remittanceBankId;
    }

    /**
     * 汇款银行id(edda入金需要：汇款银行bankId)
     */
    public void setRemittanceBankId(String remittanceBankId) {
        this.remittanceBankId = remittanceBankId;
    }

    /**
     * 回调状态
     */
    public Integer getPushrecved() {
        return pushrecved;
    }

    /**
     * 回调状态
     */
    public void setPushrecved(Integer pushrecved) {
        this.pushrecved = pushrecved;
    }

    /**
     * 预约号
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * 预约号
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * 回调错误次数
     */
    public Integer getErrCnt() {
        return errCnt;
    }

    /**
     * 回调错误次数
     */
    public void setErrCnt(Integer errCnt) {
        this.errCnt = errCnt;
    }

    /**
     * edda入金流水号
     */
    public String getEddaApplicationId() {
        return eddaApplicationId;
    }

    /**
     * edda入金流水号
     */
    public void setEddaApplicationId(String eddaApplicationId) {
        this.eddaApplicationId = eddaApplicationId;
    }

    /**
     * 实际到账金额
     */
    public BigDecimal getSettlementAmt() {
        return settlementAmt;
    }

    /**
     * 实际到账金额
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
     * 银证入金流水号
     */
    public String getBanksecApplicationId() {
        return banksecApplicationId;
    }

    /**
     * 银证入金流水号
     */
    public void setBanksecApplicationId(String banksecApplicationId) {
        this.banksecApplicationId = banksecApplicationId;
    }

    /**
     * 银行业务流水号
     */
    public String getTxnRefId() {
        return txnRefId;
    }

    /**
     * 银行业务流水号
     */
    public void setTxnRefId(String txnRefId) {
        this.txnRefId = txnRefId;
    }

    /**
     * 银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]
     */
    public Integer getBankAccountCategory() {
        return bankAccountCategory;
    }

    /**
     * 银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]
     */
    public void setBankAccountCategory(Integer bankAccountCategory) {
        this.bankAccountCategory = bankAccountCategory;
    }

    /**
     * app是否可撤销[0-否 1-是]
     */
    public Integer getIsCancel() {
        return isCancel;
    }

    /**
     * app是否可撤销[0-否 1-是]
     */
    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    /**
     * 租户 ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户 ID
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 1:FPS,2:网银转账,3:支票转账,4:快捷入金,5:银证转账,6:EDDA
     */
    public Integer getSupportType() {
        return supportType;
    }

    /**
     * 1:FPS,2:网银转账,3:支票转账,4:快捷入金,5:银证转账,6:EDDA
     */
    public void setSupportType(Integer supportType) {
        this.supportType = supportType;
    }

    /**
     * 汇款银行swiftCode
     */
    public String getRemittanceSwiftCode() {
        return remittanceSwiftCode;
    }

    /**
     * 汇款银行swiftCode
     */
    public void setRemittanceSwiftCode(String remittanceSwiftCode) {
        this.remittanceSwiftCode = remittanceSwiftCode;
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
        SecDepositFunds other = (SecDepositFunds) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getBankType() == null ? other.getBankType() == null : this.getBankType().equals(other.getBankType()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getBankName() == null ? other.getBankName() == null : this.getBankName().equals(other.getBankName()))
            && (this.getBankCode() == null ? other.getBankCode() == null : this.getBankCode().equals(other.getBankCode()))
            && (this.getFundAccount() == null ? other.getFundAccount() == null : this.getFundAccount().equals(other.getFundAccount()))
            && (this.getFundAccountName() == null ? other.getFundAccountName() == null : this.getFundAccountName().equals(other.getFundAccountName()))
            && (this.getDepositMoney() == null ? other.getDepositMoney() == null : this.getDepositMoney().equals(other.getDepositMoney()))
            && (this.getInviter() == null ? other.getInviter() == null : this.getInviter().equals(other.getInviter()))
            && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getIsFind() == null ? other.getIsFind() == null : this.getIsFind().equals(other.getIsFind()))
            && (this.getChargeMoney() == null ? other.getChargeMoney() == null : this.getChargeMoney().equals(other.getChargeMoney()))
            && (this.getBackPerson() == null ? other.getBackPerson() == null : this.getBackPerson().equals(other.getBackPerson()))
            && (this.getBackReason() == null ? other.getBackReason() == null : this.getBackReason().equals(other.getBackReason()))
            && (this.getInfo() == null ? other.getInfo() == null : this.getInfo().equals(other.getInfo()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getReceivingAccount() == null ? other.getReceivingAccount() == null : this.getReceivingAccount().equals(other.getReceivingAccount()))
            && (this.getReceivingAccountName() == null ? other.getReceivingAccountName() == null : this.getReceivingAccountName().equals(other.getReceivingAccountName()))
            && (this.getReceivingBankCode() == null ? other.getReceivingBankCode() == null : this.getReceivingBankCode().equals(other.getReceivingBankCode()))
            && (this.getReceivingAddress() == null ? other.getReceivingAddress() == null : this.getReceivingAddress().equals(other.getReceivingAddress()))
            && (this.getReceivingBankNameCn() == null ? other.getReceivingBankNameCn() == null : this.getReceivingBankNameCn().equals(other.getReceivingBankNameCn()))
            && (this.getReceivingBankNameEn() == null ? other.getReceivingBankNameEn() == null : this.getReceivingBankNameEn().equals(other.getReceivingBankNameEn()))
            && (this.getReceivingBankAddress() == null ? other.getReceivingBankAddress() == null : this.getReceivingBankAddress().equals(other.getReceivingBankAddress()))
            && (this.getSwiftCode() == null ? other.getSwiftCode() == null : this.getSwiftCode().equals(other.getSwiftCode()))
            && (this.getIsReward() == null ? other.getIsReward() == null : this.getIsReward().equals(other.getIsReward()))
            && (this.getActId() == null ? other.getActId() == null : this.getActId().equals(other.getActId()))
            && (this.getRemittanceBankName() == null ? other.getRemittanceBankName() == null : this.getRemittanceBankName().equals(other.getRemittanceBankName()))
            && (this.getRemittanceBankAccount() == null ? other.getRemittanceBankAccount() == null : this.getRemittanceBankAccount().equals(other.getRemittanceBankAccount()))
            && (this.getRemittanceAccountNameEn() == null ? other.getRemittanceAccountNameEn() == null : this.getRemittanceAccountNameEn().equals(other.getRemittanceAccountNameEn()))
            && (this.getRemittanceBankCode() == null ? other.getRemittanceBankCode() == null : this.getRemittanceBankCode().equals(other.getRemittanceBankCode()))
            && (this.getRemittanceBankId() == null ? other.getRemittanceBankId() == null : this.getRemittanceBankId().equals(other.getRemittanceBankId()))
            && (this.getPushrecved() == null ? other.getPushrecved() == null : this.getPushrecved().equals(other.getPushrecved()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getErrCnt() == null ? other.getErrCnt() == null : this.getErrCnt().equals(other.getErrCnt()))
            && (this.getEddaApplicationId() == null ? other.getEddaApplicationId() == null : this.getEddaApplicationId().equals(other.getEddaApplicationId()))
            && (this.getSettlementAmt() == null ? other.getSettlementAmt() == null : this.getSettlementAmt().equals(other.getSettlementAmt()))
            && (this.getSettlementDt() == null ? other.getSettlementDt() == null : this.getSettlementDt().equals(other.getSettlementDt()))
            && (this.getBanksecApplicationId() == null ? other.getBanksecApplicationId() == null : this.getBanksecApplicationId().equals(other.getBanksecApplicationId()))
            && (this.getTxnRefId() == null ? other.getTxnRefId() == null : this.getTxnRefId().equals(other.getTxnRefId()))
            && (this.getBankAccountCategory() == null ? other.getBankAccountCategory() == null : this.getBankAccountCategory().equals(other.getBankAccountCategory()))
            && (this.getIsCancel() == null ? other.getIsCancel() == null : this.getIsCancel().equals(other.getIsCancel()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getSupportType() == null ? other.getSupportType() == null : this.getSupportType().equals(other.getSupportType()))
            && (this.getRemittanceSwiftCode() == null ? other.getRemittanceSwiftCode() == null : this.getRemittanceSwiftCode().equals(other.getRemittanceSwiftCode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getBankType() == null) ? 0 : getBankType().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getBankName() == null) ? 0 : getBankName().hashCode());
        result = prime * result + ((getBankCode() == null) ? 0 : getBankCode().hashCode());
        result = prime * result + ((getFundAccount() == null) ? 0 : getFundAccount().hashCode());
        result = prime * result + ((getFundAccountName() == null) ? 0 : getFundAccountName().hashCode());
        result = prime * result + ((getDepositMoney() == null) ? 0 : getDepositMoney().hashCode());
        result = prime * result + ((getInviter() == null) ? 0 : getInviter().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getIsFind() == null) ? 0 : getIsFind().hashCode());
        result = prime * result + ((getChargeMoney() == null) ? 0 : getChargeMoney().hashCode());
        result = prime * result + ((getBackPerson() == null) ? 0 : getBackPerson().hashCode());
        result = prime * result + ((getBackReason() == null) ? 0 : getBackReason().hashCode());
        result = prime * result + ((getInfo() == null) ? 0 : getInfo().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getReceivingAccount() == null) ? 0 : getReceivingAccount().hashCode());
        result = prime * result + ((getReceivingAccountName() == null) ? 0 : getReceivingAccountName().hashCode());
        result = prime * result + ((getReceivingBankCode() == null) ? 0 : getReceivingBankCode().hashCode());
        result = prime * result + ((getReceivingAddress() == null) ? 0 : getReceivingAddress().hashCode());
        result = prime * result + ((getReceivingBankNameCn() == null) ? 0 : getReceivingBankNameCn().hashCode());
        result = prime * result + ((getReceivingBankNameEn() == null) ? 0 : getReceivingBankNameEn().hashCode());
        result = prime * result + ((getReceivingBankAddress() == null) ? 0 : getReceivingBankAddress().hashCode());
        result = prime * result + ((getSwiftCode() == null) ? 0 : getSwiftCode().hashCode());
        result = prime * result + ((getIsReward() == null) ? 0 : getIsReward().hashCode());
        result = prime * result + ((getActId() == null) ? 0 : getActId().hashCode());
        result = prime * result + ((getRemittanceBankName() == null) ? 0 : getRemittanceBankName().hashCode());
        result = prime * result + ((getRemittanceBankAccount() == null) ? 0 : getRemittanceBankAccount().hashCode());
        result = prime * result + ((getRemittanceAccountNameEn() == null) ? 0 : getRemittanceAccountNameEn().hashCode());
        result = prime * result + ((getRemittanceBankCode() == null) ? 0 : getRemittanceBankCode().hashCode());
        result = prime * result + ((getRemittanceBankId() == null) ? 0 : getRemittanceBankId().hashCode());
        result = prime * result + ((getPushrecved() == null) ? 0 : getPushrecved().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getErrCnt() == null) ? 0 : getErrCnt().hashCode());
        result = prime * result + ((getEddaApplicationId() == null) ? 0 : getEddaApplicationId().hashCode());
        result = prime * result + ((getSettlementAmt() == null) ? 0 : getSettlementAmt().hashCode());
        result = prime * result + ((getSettlementDt() == null) ? 0 : getSettlementDt().hashCode());
        result = prime * result + ((getBanksecApplicationId() == null) ? 0 : getBanksecApplicationId().hashCode());
        result = prime * result + ((getTxnRefId() == null) ? 0 : getTxnRefId().hashCode());
        result = prime * result + ((getBankAccountCategory() == null) ? 0 : getBankAccountCategory().hashCode());
        result = prime * result + ((getIsCancel() == null) ? 0 : getIsCancel().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getSupportType() == null) ? 0 : getSupportType().hashCode());
        result = prime * result + ((getRemittanceSwiftCode() == null) ? 0 : getRemittanceSwiftCode().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", currency=").append(currency);
        sb.append(", bankType=").append(bankType);
        sb.append(", clientId=").append(clientId);
        sb.append(", bankName=").append(bankName);
        sb.append(", bankCode=").append(bankCode);
        sb.append(", fundAccount=").append(fundAccount);
        sb.append(", fundAccountName=").append(fundAccountName);
        sb.append(", depositMoney=").append(depositMoney);
        sb.append(", inviter=").append(inviter);
        sb.append(", remarks=").append(remarks);
        sb.append(", state=").append(state);
        sb.append(", userId=").append(userId);
        sb.append(", isFind=").append(isFind);
        sb.append(", chargeMoney=").append(chargeMoney);
        sb.append(", backPerson=").append(backPerson);
        sb.append(", backReason=").append(backReason);
        sb.append(", info=").append(info);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", receivingAccount=").append(receivingAccount);
        sb.append(", receivingAccountName=").append(receivingAccountName);
        sb.append(", receivingBankCode=").append(receivingBankCode);
        sb.append(", receivingAddress=").append(receivingAddress);
        sb.append(", receivingBankNameCn=").append(receivingBankNameCn);
        sb.append(", receivingBankNameEn=").append(receivingBankNameEn);
        sb.append(", receivingBankAddress=").append(receivingBankAddress);
        sb.append(", swiftCode=").append(swiftCode);
        sb.append(", isReward=").append(isReward);
        sb.append(", actId=").append(actId);
        sb.append(", remittanceBankName=").append(remittanceBankName);
        sb.append(", remittanceBankAccount=").append(remittanceBankAccount);
        sb.append(", remittanceAccountNameEn=").append(remittanceAccountNameEn);
        sb.append(", remittanceBankCode=").append(remittanceBankCode);
        sb.append(", remittanceBankId=").append(remittanceBankId);
        sb.append(", pushrecved=").append(pushrecved);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", errCnt=").append(errCnt);
        sb.append(", eddaApplicationId=").append(eddaApplicationId);
        sb.append(", settlementAmt=").append(settlementAmt);
        sb.append(", settlementDt=").append(settlementDt);
        sb.append(", banksecApplicationId=").append(banksecApplicationId);
        sb.append(", txnRefId=").append(txnRefId);
        sb.append(", bankAccountCategory=").append(bankAccountCategory);
        sb.append(", isCancel=").append(isCancel);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", supportType=").append(supportType);
        sb.append(", remittanceSwiftCode=").append(remittanceSwiftCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
