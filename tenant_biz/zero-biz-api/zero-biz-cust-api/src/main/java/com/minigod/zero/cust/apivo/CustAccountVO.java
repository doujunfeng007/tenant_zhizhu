package com.minigod.zero.cust.apivo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustAccountVO implements Serializable {

	private String tradeAccount;

	private String acctType;

	private String capitalAccount;

	private String accountType;

	private String fundAccount;

	private String appPermission;

}
