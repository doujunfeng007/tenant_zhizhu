package com.minigod.zero.cust.apivo.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName TenantCustInfoVO.java
 * @Description TODO
 * @createTime 2024年03月05日 10:34:00
 */
@Data
public class TenantCustInfoVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 已分配用户数
	 */
	private long assignedCount;

	/**
	 * 可扩展用户数
	 */
	private long scalableCount;


	/**
	 * 休眠用户数
	 */
	private long hibernateCount;

	/**
	 * 过期时间
	 */
	private String expireTime;




}
