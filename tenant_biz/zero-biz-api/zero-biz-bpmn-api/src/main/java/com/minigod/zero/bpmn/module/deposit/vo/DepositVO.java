package com.minigod.zero.bpmn.module.deposit.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/13 11:35
 * @description：fps入金参数
 */
@Data
public class DepositVO {
	/**
	 * 理财账号
	 */
	private String clientId;
	private Long custId;
	/**
	 * 收款账户id
	 */
	private Long payeeBankDetailId;
	/**
	 * 入金银行id
	 */
	private Long depositBankId;
	/**
	 * 入金账号
	 */
	private String depositAccount;
	/**
	 * 入金账户名称
	 */
	private String accountName;
	/**
	 * 入金账号确认
	 */
	private String confirmDepositAccount;
	/**
	 * 入金币种
	 */
	private String currency;
	/**
	 * 入金金额
	 */
	private BigDecimal depositAmount;
	/**
	 * 凭证
	 */
	private List<Long> accImgIds;
	/**
	 * {@link com.minigod.zero.biz.common.enums.BankAccountType}
	 */
	private Integer bankAccountType;
	/**
	 * 入金方式 {@link com.minigod.zero.oms.enums.SupportTypeEnum }
	 */
	private String supportType;

	/**
	 * 客户选择的银行机构号
	 */
	private String bankId;
}
