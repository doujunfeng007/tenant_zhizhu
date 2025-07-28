package com.minigod.zero.manage.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/11 14:00
 * @description：收、付款银行
 */
@Data
public class ReceivingBankVO {
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
	 * 分行号集合,逗号相隔
	 */
	private String bankIdQuick;
	/**
	 * 银行地址
	 */
	private String bankAddress;
	/**
	 *
	 */
	private String swiftCode;
	/**
	 * 收款账户
	 */
	private String account;
	/**
	 * 收款账户名
	 */
	private String accountName;

	private String fpsID;
	/**
	 * 单笔限额
	 */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private BigDecimal maxAmt;
	/**
	 * 入金银行绑定的收款银行id
	 */
	private Long payeeBankId;
	/**
	 * 入金银行绑定收款账户
	 */
	private Long payeeBankDetailId;
}
