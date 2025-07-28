package com.minigod.zero.dbs.vo;

import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import lombok.Data;

import java.io.Serializable;


/**
 * EDDA 请求授权报文
 *
 * @author chenyu
 * @date 2020-12-21
 */
@Data
public class EddaTransactionEnquiryRequestVO extends DbsBaseRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;

	//请求流水号
	private String msgId;

	// txnRefId 和 originalMsgId 允许只填写一个
	//原授权请求MsgId
	private String originalMsgId;
	//Edda setup返回的txnRefId
	private String txnRefId;

	//币种
	private String amtCcy;

	/**#########收款方信息###############*/
	//收款账号
	private String benefitAccountNo;

}
