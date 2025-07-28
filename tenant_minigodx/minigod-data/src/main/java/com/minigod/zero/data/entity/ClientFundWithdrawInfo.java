package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户出金申请信息表
 * @TableName client_fund_withdraw_info
 */
public class ClientFundWithdrawInfo implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 流水号
     */
    private String applicationId;

    /**
     * 客户帐号
     */
    private String clientId;

    /**
     * 资金帐号
     */
    private String fundAccount;

    /**
     * 委托日期
     */
    private Date entrustTime;

    /**
     * 证件类型
     */
    private Integer idKind;

    /**
     * 证件号码
     */
    private String idNode;

    /**
     * 中文名
     */
    private String clientName;

    /**
     * 英文名
     */
    private String clientNameSpell;

    /**
     * 银行账号类型
     */
    private String bankAcctType;

    /**
     * 性别[0-男 1-女 2-其它]
     */
    private String sex;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]
     */
    private Integer transferType;

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    private String ccy;

    /**
     * 提取金额
     */
    private BigDecimal withdrawAmount;

    /**
     * 冻结资金
     */
    private BigDecimal frozenBalance;

    /**
     * 可提余额
     */
    private BigDecimal drawableBalance;

    /**
     * 手续费扣除方式[0-从提取金额中扣除 1-从余额中扣除]
     */
    private Integer deductWay;

    /**
     * 手续费
     */
    private BigDecimal chargeFee;

    /**
     * 实际提取金额
     */
    private BigDecimal actualAmount;

    /**
     * 已退款金额
     */
    private BigDecimal refundedAmount;

    /**
     * 已退款日期
     */
    private Date refundedDate;

    /**
     * 资金冻结日期
     */
    private Date initDate;

    /**
     * 资金冻结事务流水
     */
    private String frozenRefId;

    /**
     * 原手续费
     */
    private BigDecimal oldChargeFee;

    /**
     * 免除手续费标识标识[0-否 1-是]
     */
    private Integer freeFeeFlag;

    /**
     * 收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]
     */
    private Integer recvAccountType;

    /**
     * 收款银行代码
     */
    private String recvBankCode;

    /**
     * 收款bankId
     */
    private String recvBankId;

    /**
     * 收款银行名称
     */
    private String recvBankName;

    /**
     * 收款银行帐户
     */
    private String recvBankAcct;

    /**
     * 银行卡类型  1香港  2大陆
     */
    private Integer recvBankType;

    /**
     * 收款银行帐户名称
     */
    private String recvBankAcctName;

    /**
     * 收款SWIFT代码
     */
    private String recvSwiftCode;

    /**
     * 客户联系地址
     */
    private String recvContactAddress;

    /**
     * 收款银行开户支行名称
     */
    private String recvBankBranchName;

    /**
     * 开户支行代码
     */
    private String recvBankBranchCode;

    /**
     * 第三者收款标记[0-否 1-是]
     */
    private Integer thirdRecvFlag;

    /**
     * 与第三者收款人关系
     */
    private String thirdRecvReal;

    /**
     * 第三者收款原因
     */
    private String thirdRecvReason;

    /**
     * 付款方式[0-未知 1-香港银行普通转账 2-香港银行本地转账 3-电汇至香港以外银行 4-支票]
     */
    private Integer payWay;

    /**
     * 银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]
     */
    private Integer payType;

    /**
     * 付款银行代码
     */
    private String payBankCode;

    /**
     * 付款银行名称
     */
    private String payBankName;

    /**
     * 付款银行账户名称
     */
    private String payAccountName;

    /**
     * 付款银行账户号码
     */
    private String payBankAcct;

    /**
     * 付款银行信息流水号
     */
    private Long payBankId;

    /**
     * 银行状态[0-未提交 1-成功 2-失败]
     */
    private Integer bankState;

    /**
     * 银行事务id
     */
    private String bankRefId;

    /**
     * 银行响应编码
     */
    private String bankRtCode;

    /**
     * 银行响应消息
     */
    private String bankRtMsg;

    /**
     * 汇款编号·手工录入
     */
    private String remittanceId;

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    private Integer callbackStatus;

    /**
     * 柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]
     */
    private Integer gtBusinessStep;

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    private Integer gtDealStatus;

    /**
     * 柜台处理时间
     */
    private Date gtDealDate;

    /**
     * 柜台响应编码
     */
    private String gtRtCode;

    /**
     * 柜台响应消息
     */
    private String gtRtMsg;

    /**
     * 导出状态[0-未导出 1-已导出]
     */
    private Integer exportStatus;

    /**
     * 导出时间
     */
    private Date exportDate;

    /**
     * 打印状态[0-未打印 1-已打印]
     */
    private Integer printStatus;

    /**
     * 打印时间
     */
    private Date printDate;

    /**
     * 申请来源[1-客户提交  2-后台录入]
     */
    private Integer applySource;

    /**
     * 客服备注
     */
    private String custRemark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private Long updateUser;

    /**
     * 银行参考流水
     */
    private String bankReference;

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 组织 ID
     */
    private Long createDept;

    /**
     * 状态 10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败
     */
    private Integer status;

    /**
     * 删除
     */
    private Integer isDeleted;

    /**
     *
     */
    private Long userId;

    /**
     * 汇款凭证地址
     */
    private String remittanceVoucher;

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
     * 客户帐号
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * 客户帐号
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 资金帐号
     */
    public String getFundAccount() {
        return fundAccount;
    }

    /**
     * 资金帐号
     */
    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    /**
     * 委托日期
     */
    public Date getEntrustTime() {
        return entrustTime;
    }

    /**
     * 委托日期
     */
    public void setEntrustTime(Date entrustTime) {
        this.entrustTime = entrustTime;
    }

    /**
     * 证件类型
     */
    public Integer getIdKind() {
        return idKind;
    }

    /**
     * 证件类型
     */
    public void setIdKind(Integer idKind) {
        this.idKind = idKind;
    }

    /**
     * 证件号码
     */
    public String getIdNode() {
        return idNode;
    }

    /**
     * 证件号码
     */
    public void setIdNode(String idNode) {
        this.idNode = idNode;
    }

    /**
     * 中文名
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 中文名
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 英文名
     */
    public String getClientNameSpell() {
        return clientNameSpell;
    }

    /**
     * 英文名
     */
    public void setClientNameSpell(String clientNameSpell) {
        this.clientNameSpell = clientNameSpell;
    }

    /**
     * 银行账号类型
     */
    public String getBankAcctType() {
        return bankAcctType;
    }

    /**
     * 银行账号类型
     */
    public void setBankAcctType(String bankAcctType) {
        this.bankAcctType = bankAcctType;
    }

    /**
     * 性别[0-男 1-女 2-其它]
     */
    public String getSex() {
        return sex;
    }

    /**
     * 性别[0-男 1-女 2-其它]
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]
     */
    public Integer getTransferType() {
        return transferType;
    }

    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]
     */
    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    /**
     * 提取金额
     */
    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    /**
     * 提取金额
     */
    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    /**
     * 冻结资金
     */
    public BigDecimal getFrozenBalance() {
        return frozenBalance;
    }

    /**
     * 冻结资金
     */
    public void setFrozenBalance(BigDecimal frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    /**
     * 可提余额
     */
    public BigDecimal getDrawableBalance() {
        return drawableBalance;
    }

    /**
     * 可提余额
     */
    public void setDrawableBalance(BigDecimal drawableBalance) {
        this.drawableBalance = drawableBalance;
    }

    /**
     * 手续费扣除方式[0-从提取金额中扣除 1-从余额中扣除]
     */
    public Integer getDeductWay() {
        return deductWay;
    }

    /**
     * 手续费扣除方式[0-从提取金额中扣除 1-从余额中扣除]
     */
    public void setDeductWay(Integer deductWay) {
        this.deductWay = deductWay;
    }

    /**
     * 手续费
     */
    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    /**
     * 手续费
     */
    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    /**
     * 实际提取金额
     */
    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    /**
     * 实际提取金额
     */
    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    /**
     * 已退款金额
     */
    public BigDecimal getRefundedAmount() {
        return refundedAmount;
    }

    /**
     * 已退款金额
     */
    public void setRefundedAmount(BigDecimal refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    /**
     * 已退款日期
     */
    public Date getRefundedDate() {
        return refundedDate;
    }

    /**
     * 已退款日期
     */
    public void setRefundedDate(Date refundedDate) {
        this.refundedDate = refundedDate;
    }

    /**
     * 资金冻结日期
     */
    public Date getInitDate() {
        return initDate;
    }

    /**
     * 资金冻结日期
     */
    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    /**
     * 资金冻结事务流水
     */
    public String getFrozenRefId() {
        return frozenRefId;
    }

    /**
     * 资金冻结事务流水
     */
    public void setFrozenRefId(String frozenRefId) {
        this.frozenRefId = frozenRefId;
    }

    /**
     * 原手续费
     */
    public BigDecimal getOldChargeFee() {
        return oldChargeFee;
    }

    /**
     * 原手续费
     */
    public void setOldChargeFee(BigDecimal oldChargeFee) {
        this.oldChargeFee = oldChargeFee;
    }

    /**
     * 免除手续费标识标识[0-否 1-是]
     */
    public Integer getFreeFeeFlag() {
        return freeFeeFlag;
    }

    /**
     * 免除手续费标识标识[0-否 1-是]
     */
    public void setFreeFeeFlag(Integer freeFeeFlag) {
        this.freeFeeFlag = freeFeeFlag;
    }

    /**
     * 收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]
     */
    public Integer getRecvAccountType() {
        return recvAccountType;
    }

    /**
     * 收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]
     */
    public void setRecvAccountType(Integer recvAccountType) {
        this.recvAccountType = recvAccountType;
    }

    /**
     * 收款银行代码
     */
    public String getRecvBankCode() {
        return recvBankCode;
    }

    /**
     * 收款银行代码
     */
    public void setRecvBankCode(String recvBankCode) {
        this.recvBankCode = recvBankCode;
    }

    /**
     * 收款bankId
     */
    public String getRecvBankId() {
        return recvBankId;
    }

    /**
     * 收款bankId
     */
    public void setRecvBankId(String recvBankId) {
        this.recvBankId = recvBankId;
    }

    /**
     * 收款银行名称
     */
    public String getRecvBankName() {
        return recvBankName;
    }

    /**
     * 收款银行名称
     */
    public void setRecvBankName(String recvBankName) {
        this.recvBankName = recvBankName;
    }

    /**
     * 收款银行帐户
     */
    public String getRecvBankAcct() {
        return recvBankAcct;
    }

    /**
     * 收款银行帐户
     */
    public void setRecvBankAcct(String recvBankAcct) {
        this.recvBankAcct = recvBankAcct;
    }

    /**
     * 银行卡类型  1香港  2大陆
     */
    public Integer getRecvBankType() {
        return recvBankType;
    }

    /**
     * 银行卡类型  1香港  2大陆
     */
    public void setRecvBankType(Integer recvBankType) {
        this.recvBankType = recvBankType;
    }

    /**
     * 收款银行帐户名称
     */
    public String getRecvBankAcctName() {
        return recvBankAcctName;
    }

    /**
     * 收款银行帐户名称
     */
    public void setRecvBankAcctName(String recvBankAcctName) {
        this.recvBankAcctName = recvBankAcctName;
    }

    /**
     * 收款SWIFT代码
     */
    public String getRecvSwiftCode() {
        return recvSwiftCode;
    }

    /**
     * 收款SWIFT代码
     */
    public void setRecvSwiftCode(String recvSwiftCode) {
        this.recvSwiftCode = recvSwiftCode;
    }

    /**
     * 客户联系地址
     */
    public String getRecvContactAddress() {
        return recvContactAddress;
    }

    /**
     * 客户联系地址
     */
    public void setRecvContactAddress(String recvContactAddress) {
        this.recvContactAddress = recvContactAddress;
    }

    /**
     * 收款银行开户支行名称
     */
    public String getRecvBankBranchName() {
        return recvBankBranchName;
    }

    /**
     * 收款银行开户支行名称
     */
    public void setRecvBankBranchName(String recvBankBranchName) {
        this.recvBankBranchName = recvBankBranchName;
    }

    /**
     * 开户支行代码
     */
    public String getRecvBankBranchCode() {
        return recvBankBranchCode;
    }

    /**
     * 开户支行代码
     */
    public void setRecvBankBranchCode(String recvBankBranchCode) {
        this.recvBankBranchCode = recvBankBranchCode;
    }

    /**
     * 第三者收款标记[0-否 1-是]
     */
    public Integer getThirdRecvFlag() {
        return thirdRecvFlag;
    }

    /**
     * 第三者收款标记[0-否 1-是]
     */
    public void setThirdRecvFlag(Integer thirdRecvFlag) {
        this.thirdRecvFlag = thirdRecvFlag;
    }

    /**
     * 与第三者收款人关系
     */
    public String getThirdRecvReal() {
        return thirdRecvReal;
    }

    /**
     * 与第三者收款人关系
     */
    public void setThirdRecvReal(String thirdRecvReal) {
        this.thirdRecvReal = thirdRecvReal;
    }

    /**
     * 第三者收款原因
     */
    public String getThirdRecvReason() {
        return thirdRecvReason;
    }

    /**
     * 第三者收款原因
     */
    public void setThirdRecvReason(String thirdRecvReason) {
        this.thirdRecvReason = thirdRecvReason;
    }

    /**
     * 付款方式[0-未知 1-香港银行普通转账 2-香港银行本地转账 3-电汇至香港以外银行 4-支票]
     */
    public Integer getPayWay() {
        return payWay;
    }

    /**
     * 付款方式[0-未知 1-香港银行普通转账 2-香港银行本地转账 3-电汇至香港以外银行 4-支票]
     */
    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    /**
     * 银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 付款银行代码
     */
    public String getPayBankCode() {
        return payBankCode;
    }

    /**
     * 付款银行代码
     */
    public void setPayBankCode(String payBankCode) {
        this.payBankCode = payBankCode;
    }

    /**
     * 付款银行名称
     */
    public String getPayBankName() {
        return payBankName;
    }

    /**
     * 付款银行名称
     */
    public void setPayBankName(String payBankName) {
        this.payBankName = payBankName;
    }

    /**
     * 付款银行账户名称
     */
    public String getPayAccountName() {
        return payAccountName;
    }

    /**
     * 付款银行账户名称
     */
    public void setPayAccountName(String payAccountName) {
        this.payAccountName = payAccountName;
    }

    /**
     * 付款银行账户号码
     */
    public String getPayBankAcct() {
        return payBankAcct;
    }

    /**
     * 付款银行账户号码
     */
    public void setPayBankAcct(String payBankAcct) {
        this.payBankAcct = payBankAcct;
    }

    /**
     * 付款银行信息流水号
     */
    public Long getPayBankId() {
        return payBankId;
    }

    /**
     * 付款银行信息流水号
     */
    public void setPayBankId(Long payBankId) {
        this.payBankId = payBankId;
    }

    /**
     * 银行状态[0-未提交 1-成功 2-失败]
     */
    public Integer getBankState() {
        return bankState;
    }

    /**
     * 银行状态[0-未提交 1-成功 2-失败]
     */
    public void setBankState(Integer bankState) {
        this.bankState = bankState;
    }

    /**
     * 银行事务id
     */
    public String getBankRefId() {
        return bankRefId;
    }

    /**
     * 银行事务id
     */
    public void setBankRefId(String bankRefId) {
        this.bankRefId = bankRefId;
    }

    /**
     * 银行响应编码
     */
    public String getBankRtCode() {
        return bankRtCode;
    }

    /**
     * 银行响应编码
     */
    public void setBankRtCode(String bankRtCode) {
        this.bankRtCode = bankRtCode;
    }

    /**
     * 银行响应消息
     */
    public String getBankRtMsg() {
        return bankRtMsg;
    }

    /**
     * 银行响应消息
     */
    public void setBankRtMsg(String bankRtMsg) {
        this.bankRtMsg = bankRtMsg;
    }

    /**
     * 汇款编号·手工录入
     */
    public String getRemittanceId() {
        return remittanceId;
    }

    /**
     * 汇款编号·手工录入
     */
    public void setRemittanceId(String remittanceId) {
        this.remittanceId = remittanceId;
    }

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    public Integer getCallbackStatus() {
        return callbackStatus;
    }

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    public void setCallbackStatus(Integer callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

    /**
     * 柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]
     */
    public Integer getGtBusinessStep() {
        return gtBusinessStep;
    }

    /**
     * 柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]
     */
    public void setGtBusinessStep(Integer gtBusinessStep) {
        this.gtBusinessStep = gtBusinessStep;
    }

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    public Integer getGtDealStatus() {
        return gtDealStatus;
    }

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    public void setGtDealStatus(Integer gtDealStatus) {
        this.gtDealStatus = gtDealStatus;
    }

    /**
     * 柜台处理时间
     */
    public Date getGtDealDate() {
        return gtDealDate;
    }

    /**
     * 柜台处理时间
     */
    public void setGtDealDate(Date gtDealDate) {
        this.gtDealDate = gtDealDate;
    }

    /**
     * 柜台响应编码
     */
    public String getGtRtCode() {
        return gtRtCode;
    }

    /**
     * 柜台响应编码
     */
    public void setGtRtCode(String gtRtCode) {
        this.gtRtCode = gtRtCode;
    }

    /**
     * 柜台响应消息
     */
    public String getGtRtMsg() {
        return gtRtMsg;
    }

    /**
     * 柜台响应消息
     */
    public void setGtRtMsg(String gtRtMsg) {
        this.gtRtMsg = gtRtMsg;
    }

    /**
     * 导出状态[0-未导出 1-已导出]
     */
    public Integer getExportStatus() {
        return exportStatus;
    }

    /**
     * 导出状态[0-未导出 1-已导出]
     */
    public void setExportStatus(Integer exportStatus) {
        this.exportStatus = exportStatus;
    }

    /**
     * 导出时间
     */
    public Date getExportDate() {
        return exportDate;
    }

    /**
     * 导出时间
     */
    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    /**
     * 打印状态[0-未打印 1-已打印]
     */
    public Integer getPrintStatus() {
        return printStatus;
    }

    /**
     * 打印状态[0-未打印 1-已打印]
     */
    public void setPrintStatus(Integer printStatus) {
        this.printStatus = printStatus;
    }

    /**
     * 打印时间
     */
    public Date getPrintDate() {
        return printDate;
    }

    /**
     * 打印时间
     */
    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }

    /**
     * 申请来源[1-客户提交  2-后台录入]
     */
    public Integer getApplySource() {
        return applySource;
    }

    /**
     * 申请来源[1-客户提交  2-后台录入]
     */
    public void setApplySource(Integer applySource) {
        this.applySource = applySource;
    }

    /**
     * 客服备注
     */
    public String getCustRemark() {
        return custRemark;
    }

    /**
     * 客服备注
     */
    public void setCustRemark(String custRemark) {
        this.custRemark = custRemark;
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
     * 创建人
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
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
     * 修改人
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 修改人
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 银行参考流水
     */
    public String getBankReference() {
        return bankReference;
    }

    /**
     * 银行参考流水
     */
    public void setBankReference(String bankReference) {
        this.bankReference = bankReference;
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
     * 组织 ID
     */
    public Long getCreateDept() {
        return createDept;
    }

    /**
     * 组织 ID
     */
    public void setCreateDept(Long createDept) {
        this.createDept = createDept;
    }

    /**
     * 状态 10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态 10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     *
     */
    public Long getUserId() {
        return userId;
    }

    /**
     *
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 汇款凭证地址
     */
    public String getRemittanceVoucher() {
        return remittanceVoucher;
    }

    /**
     * 汇款凭证地址
     */
    public void setRemittanceVoucher(String remittanceVoucher) {
        this.remittanceVoucher = remittanceVoucher;
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
        ClientFundWithdrawInfo other = (ClientFundWithdrawInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getFundAccount() == null ? other.getFundAccount() == null : this.getFundAccount().equals(other.getFundAccount()))
            && (this.getEntrustTime() == null ? other.getEntrustTime() == null : this.getEntrustTime().equals(other.getEntrustTime()))
            && (this.getIdKind() == null ? other.getIdKind() == null : this.getIdKind().equals(other.getIdKind()))
            && (this.getIdNode() == null ? other.getIdNode() == null : this.getIdNode().equals(other.getIdNode()))
            && (this.getClientName() == null ? other.getClientName() == null : this.getClientName().equals(other.getClientName()))
            && (this.getClientNameSpell() == null ? other.getClientNameSpell() == null : this.getClientNameSpell().equals(other.getClientNameSpell()))
            && (this.getBankAcctType() == null ? other.getBankAcctType() == null : this.getBankAcctType().equals(other.getBankAcctType()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getTransferType() == null ? other.getTransferType() == null : this.getTransferType().equals(other.getTransferType()))
            && (this.getCcy() == null ? other.getCcy() == null : this.getCcy().equals(other.getCcy()))
            && (this.getWithdrawAmount() == null ? other.getWithdrawAmount() == null : this.getWithdrawAmount().equals(other.getWithdrawAmount()))
            && (this.getFrozenBalance() == null ? other.getFrozenBalance() == null : this.getFrozenBalance().equals(other.getFrozenBalance()))
            && (this.getDrawableBalance() == null ? other.getDrawableBalance() == null : this.getDrawableBalance().equals(other.getDrawableBalance()))
            && (this.getDeductWay() == null ? other.getDeductWay() == null : this.getDeductWay().equals(other.getDeductWay()))
            && (this.getChargeFee() == null ? other.getChargeFee() == null : this.getChargeFee().equals(other.getChargeFee()))
            && (this.getActualAmount() == null ? other.getActualAmount() == null : this.getActualAmount().equals(other.getActualAmount()))
            && (this.getRefundedAmount() == null ? other.getRefundedAmount() == null : this.getRefundedAmount().equals(other.getRefundedAmount()))
            && (this.getRefundedDate() == null ? other.getRefundedDate() == null : this.getRefundedDate().equals(other.getRefundedDate()))
            && (this.getInitDate() == null ? other.getInitDate() == null : this.getInitDate().equals(other.getInitDate()))
            && (this.getFrozenRefId() == null ? other.getFrozenRefId() == null : this.getFrozenRefId().equals(other.getFrozenRefId()))
            && (this.getOldChargeFee() == null ? other.getOldChargeFee() == null : this.getOldChargeFee().equals(other.getOldChargeFee()))
            && (this.getFreeFeeFlag() == null ? other.getFreeFeeFlag() == null : this.getFreeFeeFlag().equals(other.getFreeFeeFlag()))
            && (this.getRecvAccountType() == null ? other.getRecvAccountType() == null : this.getRecvAccountType().equals(other.getRecvAccountType()))
            && (this.getRecvBankCode() == null ? other.getRecvBankCode() == null : this.getRecvBankCode().equals(other.getRecvBankCode()))
            && (this.getRecvBankId() == null ? other.getRecvBankId() == null : this.getRecvBankId().equals(other.getRecvBankId()))
            && (this.getRecvBankName() == null ? other.getRecvBankName() == null : this.getRecvBankName().equals(other.getRecvBankName()))
            && (this.getRecvBankAcct() == null ? other.getRecvBankAcct() == null : this.getRecvBankAcct().equals(other.getRecvBankAcct()))
            && (this.getRecvBankType() == null ? other.getRecvBankType() == null : this.getRecvBankType().equals(other.getRecvBankType()))
            && (this.getRecvBankAcctName() == null ? other.getRecvBankAcctName() == null : this.getRecvBankAcctName().equals(other.getRecvBankAcctName()))
            && (this.getRecvSwiftCode() == null ? other.getRecvSwiftCode() == null : this.getRecvSwiftCode().equals(other.getRecvSwiftCode()))
            && (this.getRecvContactAddress() == null ? other.getRecvContactAddress() == null : this.getRecvContactAddress().equals(other.getRecvContactAddress()))
            && (this.getRecvBankBranchName() == null ? other.getRecvBankBranchName() == null : this.getRecvBankBranchName().equals(other.getRecvBankBranchName()))
            && (this.getRecvBankBranchCode() == null ? other.getRecvBankBranchCode() == null : this.getRecvBankBranchCode().equals(other.getRecvBankBranchCode()))
            && (this.getThirdRecvFlag() == null ? other.getThirdRecvFlag() == null : this.getThirdRecvFlag().equals(other.getThirdRecvFlag()))
            && (this.getThirdRecvReal() == null ? other.getThirdRecvReal() == null : this.getThirdRecvReal().equals(other.getThirdRecvReal()))
            && (this.getThirdRecvReason() == null ? other.getThirdRecvReason() == null : this.getThirdRecvReason().equals(other.getThirdRecvReason()))
            && (this.getPayWay() == null ? other.getPayWay() == null : this.getPayWay().equals(other.getPayWay()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()))
            && (this.getPayBankCode() == null ? other.getPayBankCode() == null : this.getPayBankCode().equals(other.getPayBankCode()))
            && (this.getPayBankName() == null ? other.getPayBankName() == null : this.getPayBankName().equals(other.getPayBankName()))
            && (this.getPayAccountName() == null ? other.getPayAccountName() == null : this.getPayAccountName().equals(other.getPayAccountName()))
            && (this.getPayBankAcct() == null ? other.getPayBankAcct() == null : this.getPayBankAcct().equals(other.getPayBankAcct()))
            && (this.getPayBankId() == null ? other.getPayBankId() == null : this.getPayBankId().equals(other.getPayBankId()))
            && (this.getBankState() == null ? other.getBankState() == null : this.getBankState().equals(other.getBankState()))
            && (this.getBankRefId() == null ? other.getBankRefId() == null : this.getBankRefId().equals(other.getBankRefId()))
            && (this.getBankRtCode() == null ? other.getBankRtCode() == null : this.getBankRtCode().equals(other.getBankRtCode()))
            && (this.getBankRtMsg() == null ? other.getBankRtMsg() == null : this.getBankRtMsg().equals(other.getBankRtMsg()))
            && (this.getRemittanceId() == null ? other.getRemittanceId() == null : this.getRemittanceId().equals(other.getRemittanceId()))
            && (this.getCallbackStatus() == null ? other.getCallbackStatus() == null : this.getCallbackStatus().equals(other.getCallbackStatus()))
            && (this.getGtBusinessStep() == null ? other.getGtBusinessStep() == null : this.getGtBusinessStep().equals(other.getGtBusinessStep()))
            && (this.getGtDealStatus() == null ? other.getGtDealStatus() == null : this.getGtDealStatus().equals(other.getGtDealStatus()))
            && (this.getGtDealDate() == null ? other.getGtDealDate() == null : this.getGtDealDate().equals(other.getGtDealDate()))
            && (this.getGtRtCode() == null ? other.getGtRtCode() == null : this.getGtRtCode().equals(other.getGtRtCode()))
            && (this.getGtRtMsg() == null ? other.getGtRtMsg() == null : this.getGtRtMsg().equals(other.getGtRtMsg()))
            && (this.getExportStatus() == null ? other.getExportStatus() == null : this.getExportStatus().equals(other.getExportStatus()))
            && (this.getExportDate() == null ? other.getExportDate() == null : this.getExportDate().equals(other.getExportDate()))
            && (this.getPrintStatus() == null ? other.getPrintStatus() == null : this.getPrintStatus().equals(other.getPrintStatus()))
            && (this.getPrintDate() == null ? other.getPrintDate() == null : this.getPrintDate().equals(other.getPrintDate()))
            && (this.getApplySource() == null ? other.getApplySource() == null : this.getApplySource().equals(other.getApplySource()))
            && (this.getCustRemark() == null ? other.getCustRemark() == null : this.getCustRemark().equals(other.getCustRemark()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getBankReference() == null ? other.getBankReference() == null : this.getBankReference().equals(other.getBankReference()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getCreateDept() == null ? other.getCreateDept() == null : this.getCreateDept().equals(other.getCreateDept()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRemittanceVoucher() == null ? other.getRemittanceVoucher() == null : this.getRemittanceVoucher().equals(other.getRemittanceVoucher()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getFundAccount() == null) ? 0 : getFundAccount().hashCode());
        result = prime * result + ((getEntrustTime() == null) ? 0 : getEntrustTime().hashCode());
        result = prime * result + ((getIdKind() == null) ? 0 : getIdKind().hashCode());
        result = prime * result + ((getIdNode() == null) ? 0 : getIdNode().hashCode());
        result = prime * result + ((getClientName() == null) ? 0 : getClientName().hashCode());
        result = prime * result + ((getClientNameSpell() == null) ? 0 : getClientNameSpell().hashCode());
        result = prime * result + ((getBankAcctType() == null) ? 0 : getBankAcctType().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getTransferType() == null) ? 0 : getTransferType().hashCode());
        result = prime * result + ((getCcy() == null) ? 0 : getCcy().hashCode());
        result = prime * result + ((getWithdrawAmount() == null) ? 0 : getWithdrawAmount().hashCode());
        result = prime * result + ((getFrozenBalance() == null) ? 0 : getFrozenBalance().hashCode());
        result = prime * result + ((getDrawableBalance() == null) ? 0 : getDrawableBalance().hashCode());
        result = prime * result + ((getDeductWay() == null) ? 0 : getDeductWay().hashCode());
        result = prime * result + ((getChargeFee() == null) ? 0 : getChargeFee().hashCode());
        result = prime * result + ((getActualAmount() == null) ? 0 : getActualAmount().hashCode());
        result = prime * result + ((getRefundedAmount() == null) ? 0 : getRefundedAmount().hashCode());
        result = prime * result + ((getRefundedDate() == null) ? 0 : getRefundedDate().hashCode());
        result = prime * result + ((getInitDate() == null) ? 0 : getInitDate().hashCode());
        result = prime * result + ((getFrozenRefId() == null) ? 0 : getFrozenRefId().hashCode());
        result = prime * result + ((getOldChargeFee() == null) ? 0 : getOldChargeFee().hashCode());
        result = prime * result + ((getFreeFeeFlag() == null) ? 0 : getFreeFeeFlag().hashCode());
        result = prime * result + ((getRecvAccountType() == null) ? 0 : getRecvAccountType().hashCode());
        result = prime * result + ((getRecvBankCode() == null) ? 0 : getRecvBankCode().hashCode());
        result = prime * result + ((getRecvBankId() == null) ? 0 : getRecvBankId().hashCode());
        result = prime * result + ((getRecvBankName() == null) ? 0 : getRecvBankName().hashCode());
        result = prime * result + ((getRecvBankAcct() == null) ? 0 : getRecvBankAcct().hashCode());
        result = prime * result + ((getRecvBankType() == null) ? 0 : getRecvBankType().hashCode());
        result = prime * result + ((getRecvBankAcctName() == null) ? 0 : getRecvBankAcctName().hashCode());
        result = prime * result + ((getRecvSwiftCode() == null) ? 0 : getRecvSwiftCode().hashCode());
        result = prime * result + ((getRecvContactAddress() == null) ? 0 : getRecvContactAddress().hashCode());
        result = prime * result + ((getRecvBankBranchName() == null) ? 0 : getRecvBankBranchName().hashCode());
        result = prime * result + ((getRecvBankBranchCode() == null) ? 0 : getRecvBankBranchCode().hashCode());
        result = prime * result + ((getThirdRecvFlag() == null) ? 0 : getThirdRecvFlag().hashCode());
        result = prime * result + ((getThirdRecvReal() == null) ? 0 : getThirdRecvReal().hashCode());
        result = prime * result + ((getThirdRecvReason() == null) ? 0 : getThirdRecvReason().hashCode());
        result = prime * result + ((getPayWay() == null) ? 0 : getPayWay().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        result = prime * result + ((getPayBankCode() == null) ? 0 : getPayBankCode().hashCode());
        result = prime * result + ((getPayBankName() == null) ? 0 : getPayBankName().hashCode());
        result = prime * result + ((getPayAccountName() == null) ? 0 : getPayAccountName().hashCode());
        result = prime * result + ((getPayBankAcct() == null) ? 0 : getPayBankAcct().hashCode());
        result = prime * result + ((getPayBankId() == null) ? 0 : getPayBankId().hashCode());
        result = prime * result + ((getBankState() == null) ? 0 : getBankState().hashCode());
        result = prime * result + ((getBankRefId() == null) ? 0 : getBankRefId().hashCode());
        result = prime * result + ((getBankRtCode() == null) ? 0 : getBankRtCode().hashCode());
        result = prime * result + ((getBankRtMsg() == null) ? 0 : getBankRtMsg().hashCode());
        result = prime * result + ((getRemittanceId() == null) ? 0 : getRemittanceId().hashCode());
        result = prime * result + ((getCallbackStatus() == null) ? 0 : getCallbackStatus().hashCode());
        result = prime * result + ((getGtBusinessStep() == null) ? 0 : getGtBusinessStep().hashCode());
        result = prime * result + ((getGtDealStatus() == null) ? 0 : getGtDealStatus().hashCode());
        result = prime * result + ((getGtDealDate() == null) ? 0 : getGtDealDate().hashCode());
        result = prime * result + ((getGtRtCode() == null) ? 0 : getGtRtCode().hashCode());
        result = prime * result + ((getGtRtMsg() == null) ? 0 : getGtRtMsg().hashCode());
        result = prime * result + ((getExportStatus() == null) ? 0 : getExportStatus().hashCode());
        result = prime * result + ((getExportDate() == null) ? 0 : getExportDate().hashCode());
        result = prime * result + ((getPrintStatus() == null) ? 0 : getPrintStatus().hashCode());
        result = prime * result + ((getPrintDate() == null) ? 0 : getPrintDate().hashCode());
        result = prime * result + ((getApplySource() == null) ? 0 : getApplySource().hashCode());
        result = prime * result + ((getCustRemark() == null) ? 0 : getCustRemark().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getBankReference() == null) ? 0 : getBankReference().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getCreateDept() == null) ? 0 : getCreateDept().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRemittanceVoucher() == null) ? 0 : getRemittanceVoucher().hashCode());
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
        sb.append(", entrustTime=").append(entrustTime);
        sb.append(", idKind=").append(idKind);
        sb.append(", idNode=").append(idNode);
        sb.append(", clientName=").append(clientName);
        sb.append(", clientNameSpell=").append(clientNameSpell);
        sb.append(", bankAcctType=").append(bankAcctType);
        sb.append(", sex=").append(sex);
        sb.append(", mobile=").append(mobile);
        sb.append(", transferType=").append(transferType);
        sb.append(", ccy=").append(ccy);
        sb.append(", withdrawAmount=").append(withdrawAmount);
        sb.append(", frozenBalance=").append(frozenBalance);
        sb.append(", drawableBalance=").append(drawableBalance);
        sb.append(", deductWay=").append(deductWay);
        sb.append(", chargeFee=").append(chargeFee);
        sb.append(", actualAmount=").append(actualAmount);
        sb.append(", refundedAmount=").append(refundedAmount);
        sb.append(", refundedDate=").append(refundedDate);
        sb.append(", initDate=").append(initDate);
        sb.append(", frozenRefId=").append(frozenRefId);
        sb.append(", oldChargeFee=").append(oldChargeFee);
        sb.append(", freeFeeFlag=").append(freeFeeFlag);
        sb.append(", recvAccountType=").append(recvAccountType);
        sb.append(", recvBankCode=").append(recvBankCode);
        sb.append(", recvBankId=").append(recvBankId);
        sb.append(", recvBankName=").append(recvBankName);
        sb.append(", recvBankAcct=").append(recvBankAcct);
        sb.append(", recvBankType=").append(recvBankType);
        sb.append(", recvBankAcctName=").append(recvBankAcctName);
        sb.append(", recvSwiftCode=").append(recvSwiftCode);
        sb.append(", recvContactAddress=").append(recvContactAddress);
        sb.append(", recvBankBranchName=").append(recvBankBranchName);
        sb.append(", recvBankBranchCode=").append(recvBankBranchCode);
        sb.append(", thirdRecvFlag=").append(thirdRecvFlag);
        sb.append(", thirdRecvReal=").append(thirdRecvReal);
        sb.append(", thirdRecvReason=").append(thirdRecvReason);
        sb.append(", payWay=").append(payWay);
        sb.append(", payType=").append(payType);
        sb.append(", payBankCode=").append(payBankCode);
        sb.append(", payBankName=").append(payBankName);
        sb.append(", payAccountName=").append(payAccountName);
        sb.append(", payBankAcct=").append(payBankAcct);
        sb.append(", payBankId=").append(payBankId);
        sb.append(", bankState=").append(bankState);
        sb.append(", bankRefId=").append(bankRefId);
        sb.append(", bankRtCode=").append(bankRtCode);
        sb.append(", bankRtMsg=").append(bankRtMsg);
        sb.append(", remittanceId=").append(remittanceId);
        sb.append(", callbackStatus=").append(callbackStatus);
        sb.append(", gtBusinessStep=").append(gtBusinessStep);
        sb.append(", gtDealStatus=").append(gtDealStatus);
        sb.append(", gtDealDate=").append(gtDealDate);
        sb.append(", gtRtCode=").append(gtRtCode);
        sb.append(", gtRtMsg=").append(gtRtMsg);
        sb.append(", exportStatus=").append(exportStatus);
        sb.append(", exportDate=").append(exportDate);
        sb.append(", printStatus=").append(printStatus);
        sb.append(", printDate=").append(printDate);
        sb.append(", applySource=").append(applySource);
        sb.append(", custRemark=").append(custRemark);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", bankReference=").append(bankReference);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", createDept=").append(createDept);
        sb.append(", status=").append(status);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", userId=").append(userId);
        sb.append(", remittanceVoucher=").append(remittanceVoucher);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
