package com.minigod.zero.bpm.vo;

import lombok.Data;

/**
 * @author chen
 * @ClassName CustOpenAccountInfo.java
 * @Description TODO
 * @createTime 2024年03月22日 20:20:00
 */

@Data
public class CustOpenAccountInfo {
	private static final long serialVersionUID = 1L;

	private Long custId;

	private String custName;

	private String custNameSpell;

	private String tradeAccount;

	private String capitalAccount;


}
