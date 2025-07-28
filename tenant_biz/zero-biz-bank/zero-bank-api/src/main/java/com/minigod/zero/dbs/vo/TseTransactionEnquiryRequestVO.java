package com.minigod.zero.dbs.vo;

import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * TSE 汇款交易信息查询
 *
 * @author chenyu
 * @date 2022-09-11
 */
@Data
public class TseTransactionEnquiryRequestVO extends DbsBaseRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;

	//请求流水号
	private String msgId;
	// 银行账号
    private String enqAccountNo;
    // 客户流水号
    private String customerReference;
    // 银行付款流水号
    private String paymentReference;
	// txnRefId
	private String txnRefId;
	//refId
	private String refId;
    // enqType RET-实时支付 REM-汇款
    private String enqType;

	/**
	 * 模拟报文使用
	 */
	private BigDecimal txnAmount;
    private String txnCcy;

}
