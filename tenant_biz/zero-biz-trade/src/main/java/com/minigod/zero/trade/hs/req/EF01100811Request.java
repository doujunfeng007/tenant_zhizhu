package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;

import java.io.Serializable;

/**
 * 811 服务_周边_密码有效期校验
 * @author sunline
 *
 */
public class EF01100811Request extends GrmRequestVO implements Serializable {
	private static final long serialVersionUID = 53171133020057924L;
	//
	private String opEntrustWay;
	private String opStation;
	private String opBranchNo;
	private String operatorNo;
	private String branchNo;
	private String fundAccount;
	private String clientId;
	private String passwordType;
	private String password;
	//
	public String getOpEntrustWay() {
		return opEntrustWay;
	}
	public void setOpEntrustWay(String opEntrustWay) {
		this.opEntrustWay = opEntrustWay;
	}
	public String getOpStation() {
		return opStation;
	}
	public void setOpStation(String opStation) {
		this.opStation = opStation;
	}
	public String getOpBranchNo() {
		return opBranchNo;
	}
	public void setOpBranchNo(String opBranchNo) {
		this.opBranchNo = opBranchNo;
	}
	public String getOperatorNo() {
		return operatorNo;
	}
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPasswordType() {
		return passwordType;
	}
	public void setPasswordType(String passwordType) {
		this.passwordType = passwordType;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
