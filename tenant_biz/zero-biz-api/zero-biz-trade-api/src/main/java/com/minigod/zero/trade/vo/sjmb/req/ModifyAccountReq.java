package com.minigod.zero.trade.vo.sjmb.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wengzejie
 * @ClassName ModifyAccountReq.java
 * @Description TODO
 * @createTime 2024年12月18日18:34:42
 */
@Data
public class ModifyAccountReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户号
	 */
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	private String fundAccount;

	/**
	 * 电子邮箱
	 */
	private String email;

	/**
	 * 住宅地址
	 */
	private String familyAddress;

	/**
	 * 通讯地址
	 */
	private String contactAddress;
}
