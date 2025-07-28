package com.minigod.zero.dbs.vo;

import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * EDDA 请求授权报文
 *
 * @author chenyu
 * @date 2020-12-21
 */
@Data
public class EddaInfoRequestVO extends DbsBaseRequestVO implements Serializable {

	private static final long serialVersionUID = 1L;

	//请求流水号
	private String msgId;
	//申请编号
	private String ddaRef;
	//授权生效日
	private String effDate;
	//授权到期日
	private String expDate;
	//单笔限额
	private BigDecimal maxAmt;
	//币种
	private String amtCcy;

	/**#########收款方信息###############*/

	//收款银行
	private String benefitBank;
	//收款银行code
	private String benefitBankCode;
	//收款账号
	private String benefitAccountNo;
	//收款账户名称
	private String benefitAccount;

	/**#########汇款方信息###############*/
	// 银行编码
	private String depositBankId;
	//存入银行账户
	private String depositAccountNo;
	//存入账户名称
	private String depositAccountName;
	//银行开户证件类型:
	// 1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
	private Integer bankIdKind;
	//银行开户证件号码
	private String bankIdNo;

}
