package com.minigod.zero.biz.common.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * CUST将新注册的授权人推送给BPM
 * @date 2023/6/3
 */
@Data
public class ConnecterCust implements Serializable {

	/**
	 * 用户号
	 */
	private Long custId;

	/**
	 * 客户类型：1-普通个户 4-公司授权人 5-ESOP户
	 */
	private Integer custType;

	/**
	 * 交易账号
	 */
	private String tradeAccount;

	/**
	 * 授权人ID
	 */
	private Long authorId;


}
