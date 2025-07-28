package com.minigod.zero.trade.sjmb.resp.brokerapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chen
 * @ClassName AccountAddResp.java
 * @Description TODO
 * @createTime 2024年02月02日 18:35:00
 */
@Data
public class AccountAddResp implements Serializable {

	private static final long serialVersionUID = 1L;

	private String brokerAccountId;

	private String accountId;

	public String getBrokerAccountId() {
		return brokerAccountId;
	}

	public void setBrokerAccountId(String brokerAccountId) {
		this.brokerAccountId = brokerAccountId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
