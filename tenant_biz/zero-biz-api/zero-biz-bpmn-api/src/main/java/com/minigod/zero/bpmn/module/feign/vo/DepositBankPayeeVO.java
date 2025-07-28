package com.minigod.zero.bpmn.module.feign.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jack
 * @Date：2024/9/11 16:20
 * @description：
 */
@Data
public class DepositBankPayeeVO {
	private Integer bankType;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 银行英文名
	 */
	private String bankEnName;
	/**
	 * 银行id
	 */
	private String bankId;
	/**
	 * 银行编号
	 */
	private String bankCode;
	/**
	 * 银行地址
	 */
	private String bankAddress;
	/**
	 *
	 */
	private String swiftCode;
	/**
	 * app 图标
	 */
	private String appIcon;
	/**
	 * pc 图标
	 */
	private String pcIcon;
	/**
	 * 入金银行绑定的收款银行id
	 */
	private Long payeeBankId;
	/**
	 * 入金银行绑定收款账户
	 */
	private Long payeeBankDetailId;
	/**
	 * 单笔限额
	 */
	private BigDecimal quota;
	/**
	 * 手续费
	 */
	private BigDecimal commission;

	/**
	 * 入金银行id
	 */
	private String depositId;
}
