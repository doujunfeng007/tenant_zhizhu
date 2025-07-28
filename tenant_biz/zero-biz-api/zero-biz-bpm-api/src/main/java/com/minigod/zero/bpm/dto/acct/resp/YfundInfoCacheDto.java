package com.minigod.zero.bpm.dto.acct.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class YfundInfoCacheDto implements Serializable {

	private Integer custId;
	private String tradeAccount;
	private String fundAccount;
	private String fundAccountMain;
	private Integer fundOperType;
	private Integer accountStatus;
	private Date createTime;

}
