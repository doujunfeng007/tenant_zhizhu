package com.minigod.zero.bpmn.module.paycategory.enums;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.enums.PayeeCategoryStatusEnum
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 15:02
 * @Version: 1.0
 */
public class PayeeCategoryStatusEnum {
	/**
	 * 支付状态 1未支付 2已支付
	 */
	public enum PayStatus{
		PAY_UN(1, "待支付"),
		PAY_SUCCEED(2, "已支付"),
		;
		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private PayStatus(int index, String value) {
			this.index = index;
			this.value = value;
		}
	}
	/**
	 * 订单状态 1未提交  2已提交
	 */
	public enum OrderStatus{
		UNCOMM(1, "未提交"),
		SUBMITTED(2, "已提交"),
		;
		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private OrderStatus(int index, String value) {
			this.index = index;
			this.value = value;
		}
	}

	/**
	 * 支付方式  1 现金  2活利得
	 */
	public enum PayWay{
		CASH(1, "现金"),
		HLD(2, "活利得"),
		;
		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private PayWay(int index, String value) {
			this.index = index;
			this.value = value;
		}
	}

	/**
	 * 收款人类别 1本人  2非本人
	 */
	public enum PayeeCategory{
		SELF(1, "本人"),
		NO_SELF(2, "非本人"),
		;
		private final int index;
		private final String value;

		public int getIndex() {
			return index;
		}

		public String getValue() {
			return value;
		}

		private PayeeCategory(int index, String value) {
			this.index = index;
			this.value = value;
		}
	}

}
