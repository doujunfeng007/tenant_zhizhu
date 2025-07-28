package com.minigod.zero.trade.afe.resp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author chen
 * @ClassName CashMovementQueryResponse.java
 * @Description TODO
 * @createTime 2024年04月26日 17:55:00
 */
@Data
public class CashMovementQueryResponse {

	@JSONField(name = "CASH_MOVEMENT")
	private List<CashMovement> cashMovementList;

	@Data
	public static class CashMovement {

		@JSONField(name = "TRANSACTION_DATE")
		private String transactionDate;

		@JSONField(name = "REF_NO")
		private String refNo;

		@JSONField(name = "MOVEMENT_TYPE")
		private String movementType;

		@JSONField(name = "VALUE_DATE")
		private String valueDate;

		@JSONField(name = "BANK")
		private String bank;

		@JSONField(name = "ACCT_NO")
		private String acctNo;

		@JSONField(name = "NARRATIVE")
		private String narrative;

		@JSONField(name = "AMOUNT")
		private String amount;

		@JSONField(name = "CURRENCY")
		private String currency;

		@JSONField(name = "REMARK")
		private String remark;

		@JSONField(name = "TRANSACTION_TYPE")
		private String transactionType;

		@JSONField(name = "STATUS")
		private String status;

		@JSONField(name = "Msg_ID")
		private String msgId;
	}
}
