package com.minigod.zero.dbs.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CamtItemReportResponseVO implements Serializable {

    // 转入或转出 [借-CRDT 贷-DBIT]
    private String creditDebitIndicator;
    // 状态
    private String Status;
    // 记账日期时间 格式: 2018-12-11T19:20:30+01:00
    private String bookingDate;
    // 起息日期 格式：2020-09-20
    private String valueDate;
    // 账户资信证明编号
    private String accountServicerReference;
    // 金额
    private String amount;
    // 币种
    private String ccy;

    // 客户业务流水 批量处理流水号 发送人流水号 支票流水号
    // 默认值 NONREF-非以上值
    private String endToEndIdentification;
    // EDDA流水/Mandate Id
    private String mandateIdentification;
    // 支票号码
    private String chequeNumber;

    // 交易详情

    // 存在换汇情况
    // 指示金额和币种
    private String instructedAmount;
    private String instructedCcy;

    // 原币种和目标币种
    private String sourceCurrency;
    private String targetCurrency;

    // 交换汇率
    private String exchangeRate;
    // 换汇合约编号
    private String contractIdentification;

    // Debtor方账户
    // Debtor方账户名
    private String debtorName;
    // Debtor方账户地区国家
    private String debtorCountry;
    // Debtor方IBAN账户号码
    private String debtorIbanNO;
    // Debtor方账户号码
    private String debtorAccountNo;

    // Creditor方账户
    // Creditor方账户名
    private String creditorName;
    // Creditor方账户地区国家
    private String creditorCountry;
    // Creditor方IBAN账户号码
    private String creditorIbanNO;
    // Creditor方账户号码
    private String creditorAccountNo;

    // 附加信息
    private String additionalEntryInformation;

}
