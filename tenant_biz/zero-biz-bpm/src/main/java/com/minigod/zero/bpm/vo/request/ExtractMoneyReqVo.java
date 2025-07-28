package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: TODO
 * @author: sunline
 * @date: 2018/5/21 16:14
 * @version: v1.0
 */
@Data
public class ExtractMoneyReqVo implements Serializable {
    private static final long serialVersionUID = 7210164274710045186L;

	/**
	 * 提取账户(资金账户)
	 */
    private String extAccount;

	/**
	 * 提取账户名称
	 */
	private String extAccountName;
	/**
	 * 交易账户
	 */
    private String clientId;
	/**
	 * 提取方式 1大陆银行 2香港银行
	 */
	private Integer extMethod;
	/**
	 * 收款银行名称
	 */
    private String bankName;
	/**
	 * 收款银行代码
	 */
	private String bankCode;
	/**
	 * 电讯码
	 */
    private String swiftCode;
	/**
	 * 收款人
	 */
	private String payee;
	/**
	 * 联系地址
	 */
    private String address;
	/**
	 * 银行账号
	 */
	private String bankAccount;
	/**
	 * 可提金额
	 */
    private BigDecimal availableAmount;
	/**
	 * 提取金额
	 */
	private BigDecimal extractionAmount;
	/**
	 * 币种 1港币 2美元
	 */
    private Integer currency;
	/**
	 * 手续费
	 */
	private BigDecimal chargeMoney;
	/**
	 * 出金凭证图片id
	 */
    private String extractMoneyImgIds;
	/**
	 * 银行机构号
	 */
	private String bankId;
	/**
	 * 出金方式[0-银证转账 1-普通转账 2-快速转账]
	 */
    private Integer busType;
	/**
	 * (取消出金)出金id
	 */
	private Long id;
}
