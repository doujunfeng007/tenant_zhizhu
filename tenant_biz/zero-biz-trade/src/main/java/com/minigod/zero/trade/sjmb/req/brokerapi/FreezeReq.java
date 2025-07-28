package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName FreezeReq.java
 * @Description TODO
 * @createTime 2024年03月01日 18:02:00
 */
@Data
public class FreezeReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 资金账号
	 */
	private String accountId;
	/**
	 * 业务日期
	 */
	private String businessDate;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 过期时间戳
	 */
	private Long expireTime;
	/**
	 * 冻结金额
	 */
	private double lockedAmount;
	/**
	 * 冻结原因
	 */
	private String reason;
	/**
	 * 资金组id
	 */
	private String segmentId;
	/**
	 * 业务类型
	 */
	private String transSubType;
}
