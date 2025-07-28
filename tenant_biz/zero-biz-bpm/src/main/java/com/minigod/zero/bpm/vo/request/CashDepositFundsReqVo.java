package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description:
 * @author: sunline
 * @date: 2018/5/21 10:42
 * @version: v1.0
 */
@Data
public class CashDepositFundsReqVo implements Serializable {
    private static final long serialVersionUID = -1998808882116184014L;

	/**
	 * 币种 1港币 2 美元
	 */
	private Integer currency;

	/**
	 * 银行 1大陆 2香港 3其他
	 */
    private Integer bankType;

	/**
	 * 交易账户
	 */
	private String clientId;

	/**
	 * 银行名称：FPS转数快；快捷入账
	 */
    private String bankName;

	/**
	 * 银行代码：FPS     ； EDDA
	 */
	private String bankCode;

	/**
	 * 存入账户
	 */
    private String depositAccount;

	/**
	 * 存入账户名称
	 */
	private String depositAccountName;

	/**
	 * 存入金额
	 */
	private BigDecimal depositMoney;

	/**
	 * 备注信息
	 */
	private String remarks;

	/**
	 * 手续费
	 */
	private BigDecimal chargeMoney;

	/**
	 * 上传凭证图片ID
	 */
	private Long accImgId;

	/**
	 * 收款账户号码
	 */
    private String getAccount;

	/**
	 * 收款人账户名
	 */
	private String getAccountName;

	/**
	 * 收款人地址
	 */
    private String getAddress;

	/**
	 * 收款银行中文名
	 */
	private String getBankNameCn;

	/**
	 * 收款银行英文名
	 */
    private String getBankNameEn;

	/**
	 * 收款银行编码
	 */
	private String getBankCode;

	/**
	 * 收款银行地址
	 */
    private String getBankAddress;

	/**
	 * SWIFT代码
	 */
	private String swiftCode;

	/**
	 * 汇款银行名称
	 */
    private String remittanceBankName;

	/**
	 * 汇款账号
	 */
	private String remittanceBankAccount;

	/**
	 * 汇款银行户名
	 */
    private String remittanceAccountNameEn;

	/**
	 * 上传凭证图片ID
	 */
	private Long accImgIdA;

	/**
	 * 活动ID
	 */
    private Integer actId;

	/**
	 * 汇款银行cord简称
	 */
	private String remittanceBankCorde;

	/**
	 * edda入金需要：汇款银行bankId
	 */
    private String remittanceBankId;
}
