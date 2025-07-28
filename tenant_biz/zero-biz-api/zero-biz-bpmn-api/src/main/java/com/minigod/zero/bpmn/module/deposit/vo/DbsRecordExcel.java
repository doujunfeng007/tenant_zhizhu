package com.minigod.zero.bpmn.module.deposit.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
/**
 * @ClassName: DbsRecordExcel
 * @Description: dbs银行流水表格
 * @Author chenyu
 * @Date 2024/9/25
 * @Version 1.0
 */
public class DbsRecordExcel {
    @ExcelProperty("参考编号")
    private String referenceNo;
    @ExcelProperty("银行名称")
    private String bankName;
    @ExcelProperty("银行账号")
    private String accNo;
    @ExcelProperty("银行账户名")
    private String accName;
    @ExcelProperty("子账户名称")
    private String subAccName;
    @ExcelProperty("子账户账号")
    private String subAccNo;
    @ExcelProperty("币种")
    private String currency;
    @ExcelProperty("日期")
    private String valueDate;
    @ExcelProperty("处理时间")
    private String processingTime;
    @ExcelProperty("消息ID")
    private String msgId;
    @ExcelProperty("客户参考编号")
    private String customerReference;
    @ExcelProperty("转账类型")
    private String txnType;
    @ExcelProperty("汇款账户名称")
    private String senderAccName;
    @ExcelProperty("汇款账户号码")
    private String senderAccNo;
    @ExcelProperty("汇款账户银行代码")
    private String senderBankId;
    @ExcelProperty("汇款金额")
    private BigDecimal actualMoney;

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime!=null?processingTime.trim():processingTime;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo!=null?referenceNo.trim():referenceNo;
    }



    public void setBankName(String bankName) {
        this.bankName = bankName!=null?bankName.trim():bankName;
    }


    public void setAccNo(String accNo) {
        this.accNo = accNo!=null?accNo.trim():accNo;
    }



    public void setAccName(String accName) {
        this.accName = accName!=null?accName.trim():accName;
    }



    public void setSubAccName(String subAccName) {
        this.subAccName = subAccName!=null?subAccName.trim():subAccName;
    }



    public void setSubAccNo(String subAccNo) {
        this.subAccNo = subAccNo!=null?subAccNo.trim():subAccNo;
    }



    public void setCurrency(String currency) {
        this.currency = currency!=null?currency.trim():currency;
    }



    public void setValueDate(String valueDate) {
        this.valueDate = valueDate!=null?valueDate.trim():valueDate;
    }



    public void setMsgId(String msgId) {
        this.msgId = msgId!=null?msgId.trim():msgId;
    }



    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference!=null?customerReference.trim():customerReference;
    }



    public void setTxnType(String txnType) {
        this.txnType = txnType!=null?txnType.trim():txnType;
    }



    public void setSenderAccName(String senderAccName) {
        this.senderAccName = senderAccName!=null?senderAccName.trim():senderAccName;
    }


    public void setSenderAccNo(String senderAccNo) {
        this.senderAccNo = senderAccNo!=null?senderAccNo.trim():senderAccNo;
    }



    public void setSenderBankId(String senderBankId) {
        this.senderBankId = senderBankId!=null?senderBankId.trim():senderBankId;
    }



    public void setActualMoney(BigDecimal actualMoney) {
        this.actualMoney = actualMoney;
    }
}
