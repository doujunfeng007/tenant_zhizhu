package com.minigod.zero.bpmn.module.feign.enums;

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
	BUY_HLD(5,"买入活利得"),
	BUY_BOND(6,"买入债券易"),
	BUY_STOCK(7,"买入股票"),
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
	WITHDRAWAL_OF_FUNDS(20,"出金"),
	HLD_CANCEL_ORDER(21,"活利得撤单"),
	BOND_CANCEL_ORDER(22,"债券易撤单"),
	REFUND(23,"汇款失败退款"),
	HLD_TRANSACTION_FAILED(24,"活力得成交失败退回"),
	BOND_TRANSACTION_FAILED(25,"债券易成交失败退回"),
	EDDA_GOLD_DEPOSIT(26,"EDDA存入"),
	FPS_GOLD_DEPOSIT(27,"FPS存入"),
	E_Bank_GOLD_DEPOSIT(28,"网银转账存入"),
	WIRE_TRANSFER_WITHDRAWAL(29,"电汇"),
	ORDINARY_TRANSFER_WITHDRAWAL(30,"普通转账"),
	LOCAL_TRANSFER_WITHDRAWAL(31,"本地转账"),
	OPEN_ACCOUNT_DEPOSIT(32,"开户存入"),
	WITHDRAWALS_REFUSED_RETURN(33,"提现退还"),
	TRADE_BUY_OPTIONS_ORDER(35,"期权交易买入"),
	TRADE_CANCEL_OPTIONS_ORDER(36,"期权交易取消"),
	WITHDRAWALS_REFUND_FAILED_DEPOSIT(56,"出金失败,退款入金");



	private Integer code;
	private String desc;

	ThawingType(Integer code, String desc){
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
		for (ThawingType type: ThawingType.values()){
			if (type.getCode().equals(code)){
				return type;
			}
		}
		return null;
	}

}
