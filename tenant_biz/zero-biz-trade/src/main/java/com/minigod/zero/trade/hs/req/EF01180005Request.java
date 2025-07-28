package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 查询条件单
 *
 * @author sunline
 */
public class EF01180005Request extends GrmRequestVO implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private String opStation;
	private String functionId;
	private String fundAccount;
	private String password;
	private String exchangeType;

	public String getOpStation() {
		return opStation;
	}

	public void setOpStation(String opStation) {
		this.opStation = opStation;
	}

	@Override
	public String getFunctionId() {
		return functionId;
	}

	@Override
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

}
