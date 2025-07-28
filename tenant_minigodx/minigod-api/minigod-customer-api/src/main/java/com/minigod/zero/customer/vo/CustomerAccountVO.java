package com.minigod.zero.customer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 这个接口为了兼容老接口，不用前端处理才返回这个格式
 */
@Data
public class CustomerAccountVO implements Serializable {

	private Securities cust;

	private TradeAccount acct;

	private FundAccount fund;

	public void setCust(Securities cust) {
		this.cust = cust;
	}

	public void setAcct(TradeAccount acct) {
		this.acct = acct;
	}

	public void setFund(FundAccount fund) {
		this.fund = fund;
	}


	@Data
	public class Securities{
		private Long custId;
		/**
		 * 名字拼音
		 */
		private String custNameSpell;
		private String accountLevel;
		private String custName;
		private Integer fundAccountType;
		/**
		 * 英文名
		 */
		private String givenNameSpell;
		/**
		 * 英文姓
		 */
		private String familyNameSpell;
		private Integer gender;
		private String phoneArea;
		private String phoneNumber;
		private String email;
	}

	@Data
	public class TradeAccount{
		private String tradeAccount;
		private String capitalAccount;
		private Integer accountType;
		private Long custId;
	}

	@Data
	public class FundAccount{
		private String fundAccount;
		private String fundAccountMain;
		private Integer riskType;
		private Integer fundAccountType;
		private Integer retestSts;
	}

	public Securities cust(){
		Securities securities = new Securities();
		return securities;
	}


	public TradeAccount acct(){
		TradeAccount account = new TradeAccount();
		return account;
	}

	public FundAccount fund(){
		FundAccount account = new FundAccount();
		return account;
	}
}
