package com.minigod.zero.cust.apivo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author:yanghu.luo
 * @create: 2023-10-23 10:41
 * @Description: 用户交易推送信息
 */
@Data
public class CustTradePushInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long custId;

	/**
	 * 交易账号
	 */
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	private String capitalAccount;

	/**
	 * 中文姓名
	 */
	private String custName;

	/**
	 * 英文名
	 */
	private String givenNameSpell;

	/**
	 * 英文姓
	 */
	private String familyNameSpell;

	/**
	 * 邮箱
	 */
	private String email;
}
