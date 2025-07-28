package com.minigod.zero.dbs.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * DBS银行流水推送
 *
 * @author sunline
 * @email aljqiang@163.com
 * @date 2020-03-02 16:21:13
 */
@Getter
@Setter
public class DbsIccBankFlowEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //自增ID
    private Long id;
    //流水号
    private String msgId;
    //机构编号
    private String orgId;
    //交易时间
    private Date timeStamp;
    //银行所在国
    private String ctry;
    //付款方式
    private String txnType;
    //客户流水号
    private String customerReference;
    //银行流水号
    private String txnRefId;
    //发起汇款日期
    @JSONField(format = "yyyy-MM-dd")
    private Date txnDate;
    //收到汇款日期
    @JSONField(format = "yyyy-MM-dd")
    private Date valueDt;
    //收款账户名称
    private String receiveAccName;
    //收款账户号码
    private String receiveAccNo;
    //收款虚拟账户名称
    private String receiveVirtualAccName;
    //收款虚拟账户号码
    private String receiveVirtualAccNo;
    //代理类型
    private String proxyType;
    //代理值
    private String proxyValue;
    //币种
    private String txnCcy;
    //到账金额
    private BigDecimal txnAmt;
    //汇款账户名称
    private String senderAccName;
    //汇款账户号码
    private String senderAccNo;
    //汇款银行ID
    private String senderBankId;
    //汇款银行清算代码
    private String senderBankCode;
    //汇款银行分行代码
    private String senderBranchCode;
    //付款明细说明
    private String paymentDetails;
    //汇款人付款代码
    private String purposeCode;
    //额外细节
    private String addtlInf;

    private Integer isCheck;
    private Integer isApply;
    private Date createTime;
    private Date updateTime;
    private String applicationId;
    private String assignDrafter;

}
