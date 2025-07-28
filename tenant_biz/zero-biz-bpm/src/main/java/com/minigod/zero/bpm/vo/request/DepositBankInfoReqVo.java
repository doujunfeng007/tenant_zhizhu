package com.minigod.zero.bpm.vo.request;

import lombok.Data;

/**
 * @description:
 * @author: sunline
 * @date: 2019/5/9 9:56
 * @version: v1.0
 */
@Data
public class DepositBankInfoReqVo {

	/**
	 * 入金银行卡类型 1:香港银行卡 2:大陆银行卡
	 */
	private Integer bankType;
	/**
	 * 入金方式 1-fps 2-网银 3-支票
	 */
	private Integer depositType;
	/**
	 * 银行卡key
	 */
	private String key;

}
