package com.minigod.zero.cms.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: sunline
 * @date: 2019/5/9 9:34
 * @version: v1.0
 */
@Data
public class DepositBankToAccountVo implements Serializable{
    private static final long serialVersionUID = -5862141368137991107L;

	/**
	 * 收款账户号码（港币）
	 */
	private String hkd;
	/**
	 * 收款账户号码（美元）
	 */
    private String usd;
    private String hkdRemark;
    private String usdRemark;
}
