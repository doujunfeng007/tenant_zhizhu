package com.minigod.zero.customer.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/24 10:52
 * @description：交易类型
 */
public enum ThawingType {
	DEFAULT(-1,"未知"),
	GOLD_DEPOSIT(0,"入金"),
	FREEZE(1,"出金申请"),
	REDUCE_FREEZE(2,"出金申请撤回"),
	FREEZE_TO_AVAILABLE(3,"货币兑换"),
	SCRATCH_BUTTON(4,"划扣"),
	BUY_HLD(5,"购买活利得"),
	BUY_BOND(6,"购买债券易"),
	BUY_STOCK(7,"购买股票"),
	SELL_HLD(8,"卖出活利得"),
	SELL_BOND(9,"卖出债券易"),
	SELL_STOCK(10,"卖出股票"),
	FUND_REDEMPTION(11,"基金赎回"),
	FUND_PURCHASE(12,"基金申购"),
	CURRENCY_EXCHANGE_FREEZE(13,"货币兑换"),
	CURRENCY_EXCHANGE_DEPOSIT(14,"货币兑换"),
	CURRENCY_EXCHANGE_SUB_FREEZE(15,"货币兑换"),
	INTEREST_PAYMENT(16,"支付利息"),
	MATURE_PRINCIPAL_REPAYMENT(17,"到期还本"),
	INTEREST_REFUND(18,"债券易利息退还"),
	MARKET_PAYMENT(19,"行情付费"),
	WITHDRAWAL_OF_FUNDS(20,"提现"),
	HLD_CANCEL_ORDER(21,"活利得撤单"),
	BOND_CANCEL_ORDER(22,"债券易撤单"),
	REFUND(23,"汇款失败退款"),
	HLD_TRANSACTION_FAILED(24,"活利得成交失败退回"),
	BOND_TRANSACTION_FAILED(25,"债券易成交失败退回"),
	EDDA_GOLD_DEPOSIT(26,"EDDA存入"),
	FPS_GOLD_DEPOSIT(27,"FPS存入"),
	E_Bank_GOLD_DEPOSIT(28,"网银转账存入"),
	WIRE_TRANSFER_WITHDRAWAL(29,"电汇"),
	ORDINARY_TRANSFER_WITHDRAWAL(30,"普通转账"),
	LOCAL_TRANSFER_WITHDRAWAL(31,"本地转账"),
	OPEN_ACCOUNT_DEPOSIT(32,"开户存入"),
	WITHDRAWALS_REFUSED_RETURN(33,"提现退还"),
	MANUAL_DEPOSIT(34,"手工入金"),
	SMARTWALLET_INTEREST(35,"活利得利息"),
	EASYBOND_INTEREST(36,"债券易利息"),
	SMARTWALLET_COMMISSION(37,"购买活利得手续费"),
	EASYBOND_COMMISSION(38,"购买债券易手续费"),
	FUND_SUBSCRIPTION_AMOUNT(39,"基金申购金额"),
	FUND_SUBSCRIPTION_FEES(40,"基金申购费用"),
	FUND_REDEMPTION_AMOUNT(41,"基金赎回金额"),
	FUND_REDEMPTION_FEE(42,"基金赎回费用"),
	FUND_CASH_DIVIDENDS(43,"基金现金分红"),
	FUND_CASH_DIVIDENDS_FEE(44,"基金现金分红费用"),
	FUND_LIQUIDATION(45,"基金清算"),
	FUND_LIQUIDATION_FEE(46,"基金清算费用"),
	TRADING_EXCHANGE(47,"交易换汇"),
	FUND_SUBSCRIPTION_CANCEL(48,"基金申购取消"),
	FUND_SUBSCRIPTION_FAIL(49,"基金申购失败退回"),
	FUND_REDEMPTION_CANCEL(50,"基金赎回取消"),
	FUND_REDEMPTION_FAIL(51,"基金赎回失败退回"),
	FUND_SUBSCRIPTION_FEES__CANCEL(52,"基金申购取消费用退回"),
	FUND_SUBSCRIPTION_FEES__FAIL(53,"基金申购失败费用退回"),
	FUND_REDEMPTION_FEE_CANCEL(54,"基金赎回取消费用退回"),
	FUND_REDEMPTION_FEE_FAIL(55,"基金赎回失败费用退回"),
	WITHDRAWALS_REFUND_FAILED_DEPOSIT(56,"出金失败退款"),
	BUY_OTC(57,"购买债券"),
	SELL_OTC(59,"卖出债券"),
	OTC_INTEREST_REFUND(60,"债券利息退还"),
	OTC_TRANSACTION_FAILED(61,"债券成交失败退回"),
	OTC_INTEREST(62,"债券利息"),
	OTC_COMMISSION(63,"购买债券手续费"),
	OTC_CANCEL_ORDER(64,"债券撤单");
	private Integer code;
	private String desc;

	ThawingType(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode(){
		return code;
	}

	public String getDesc() {
		return desc;
	}


	public static ThawingType getByCode(Integer code){
		if (code == null){
			return DEFAULT;
		}
		for (ThawingType type: ThawingType.values()){
			if (type.getCode().equals(code)){
				return type;
			}
		}
		return DEFAULT;
	}

}
