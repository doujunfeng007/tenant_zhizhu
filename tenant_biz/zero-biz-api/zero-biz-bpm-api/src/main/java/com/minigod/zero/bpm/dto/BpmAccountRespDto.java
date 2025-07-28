package com.minigod.zero.bpm.dto;

import com.minigod.zero.bpm.entity.BpmCapitalInfoEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class BpmAccountRespDto implements Serializable {

	private BpmSecuritiesRespDto cust;

	private BpmTradeAcctRespDto acct;

	private BpmFundAcctRespDto fund;

	private Date utime;

	/**
	 * 资金账号列表
	 */
	private List<BpmCapitalInfoEntity> capitalList;

}
