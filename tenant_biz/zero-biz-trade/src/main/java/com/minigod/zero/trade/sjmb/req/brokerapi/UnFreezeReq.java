package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName UnFreezeReq.java
 * @Description TODO
 * @createTime 2024年03月01日 18:31:00
 */
@Data
public class UnFreezeReq implements Serializable {

	/**
	 * 冻结资金id
	 */
	private String lockedCashId;
}
