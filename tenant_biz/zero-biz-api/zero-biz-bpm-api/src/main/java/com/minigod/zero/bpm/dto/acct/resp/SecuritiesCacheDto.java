package com.minigod.zero.bpm.dto.acct.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class SecuritiesCacheDto implements Serializable {

	private Long custId;

	private String idKind;

	private String idCard;

	private String cnName;

	private String enName;

	private String tradeAccount;

	private String capitalAccount;

	private String fundAccount;

}
