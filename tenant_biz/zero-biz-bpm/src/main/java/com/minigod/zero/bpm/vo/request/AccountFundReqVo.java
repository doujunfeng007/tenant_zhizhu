package com.minigod.zero.bpm.vo.request;

import lombok.Data;

/**
 * @description:
 * @author: sunline
 * @date: 2018/5/21 14:48
 * @version: v1.0
 */
@Data
public class AccountFundReqVo {
    private static final long serialVersionUID = 7176809223264492495L;

	/**
	 * 资金类型 2：港币 1：美元
	 */
	private String moneyType;
	/**
	 * 资金账号
	 */
    private String capitalAccount;

}
