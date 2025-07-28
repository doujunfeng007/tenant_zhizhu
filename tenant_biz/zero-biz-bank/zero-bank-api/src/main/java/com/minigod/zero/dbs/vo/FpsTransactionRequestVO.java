package com.minigod.zero.dbs.vo;


import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import lombok.Data;

import java.io.Serializable;

/**
 * Fps 交易请求报文
 *
 * @author chenyu
 * @date 2020-12-22
 */
@Data
public class FpsTransactionRequestVO extends DbsBaseRequestVO implements Serializable {

    // 自定义流水
    private String msgId;
    // 用户自定义流水
    private String customerReference;
    // 币种
    private String txnCcy;
    // 交易金额
    private double txnAmount;

    // 只传其一即可
    // 授权时业务生成的流水
    private String ddaRef;
    // 银行授权响应的数据
    private String mandateId;

    /**#########收款方信息###############*/
    //收款账号
    private String benefitAccountNo;
    //收款账户名称
    private String benefitAccountName;

    /**#########汇款方信息###############*/
    //汇款银行Id
    private String depositBankId;
    //存入银行账户
    private String depositAccountNo;
    //存入账户名称
    private String depositAccountName;

}
