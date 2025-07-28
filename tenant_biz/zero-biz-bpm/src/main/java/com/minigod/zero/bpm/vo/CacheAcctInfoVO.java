package com.minigod.zero.bpm.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheAcctInfoVO implements Serializable {

	private Long custId;

	private String idKind;

	private String idCard;

	private String custName;

	private String custNameSpell;

	private String tradeAccount;

	private String capitalAccount;

	private String fundAccount;

	//账户状态[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]
	private String status;
}
